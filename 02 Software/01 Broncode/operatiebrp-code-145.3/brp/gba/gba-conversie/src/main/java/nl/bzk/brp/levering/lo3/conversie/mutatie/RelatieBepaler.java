/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.levering.lo3.mapper.GeregistreerdPartnerschapRelatieMapper;
import nl.bzk.brp.levering.lo3.mapper.HuwelijkRelatieMapper;
import nl.bzk.brp.levering.lo3.util.MetaModelUtil;
import org.springframework.stereotype.Component;

/**
 * Analiseer relaties om te bepalen welke BRP relaties samengevoegd kunnen worden tot 1 GBA relatie
 * (in verband met het verschil tussen omzettingen van huwelijken naar geregistreerd partnerschappen
 * (en vice versa) in GBA en BRP).
 */
@Component
public final class RelatieBepaler {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Bepaal de relatie mapping, waarbij omzettingen van huwelijken in geregistreerd
     * partnerschappen en vice versa worden gezien als dezelfde relatie en dus dezelfde
     * relatieMapping dienen te krijgen.
     * @param huwelijken alle huwelijken
     * @param partnerschappen alle geregistreerde partnerschappen
     * @return relatie mapping tbv gba categorie 05
     */
    public Map<Long, String> bepaalRelatieMapping(final Collection<MetaObject> huwelijken, final Collection<MetaObject> partnerschappen) {
        List<Set<Long>> relatieSets = new ArrayList<>();

        for (final MetaObject huwelijk : huwelijken) {
            LOGGER.debug("Verwerk huwelijk: {}", huwelijk.getObjectsleutel());

            if (isBeeindigdMetRedenOmzetting(huwelijk, HuwelijkRelatieMapper.GROEP_ELEMENT, HuwelijkRelatieMapper.REDEN_EINDE_ELEMENT)) {
                verwerkInRelatieSets(relatieSets, huwelijk.getObjectsleutel(), zoekGerelateerdeRelatie(huwelijk, HuwelijkRelatieMapper.GROEP_ELEMENT,
                        HuwelijkRelatieMapper.REDEN_EINDE_ELEMENT, partnerschappen, GeregistreerdPartnerschapRelatieMapper.GROEP_ELEMENT));

            } else {
                verwerkInRelatieSets(relatieSets, huwelijk.getObjectsleutel());
            }

            LOGGER.debug("Relatie sets na verwerken huwelijk: {}", relatieSets);
        }
        for (final MetaObject partnerschap : partnerschappen) {
            LOGGER.debug("Verwerk partnerschappen: {}", partnerschap.getObjectsleutel());

            if (isBeeindigdMetRedenOmzetting(partnerschap, GeregistreerdPartnerschapRelatieMapper.GROEP_ELEMENT,
                    GeregistreerdPartnerschapRelatieMapper.REDEN_EINDE_ELEMENT)) {
                verwerkInRelatieSets(relatieSets, partnerschap.getObjectsleutel(),
                        zoekGerelateerdeRelatie(partnerschap, GeregistreerdPartnerschapRelatieMapper.GROEP_ELEMENT,
                                GeregistreerdPartnerschapRelatieMapper.REDEN_EINDE_ELEMENT, huwelijken, HuwelijkRelatieMapper.GROEP_ELEMENT));

            } else {
                verwerkInRelatieSets(relatieSets, partnerschap.getObjectsleutel());
            }

            LOGGER.debug("Relatie sets na verwerken partnerschap: {}", relatieSets);
        }

        relatieSets = consolideerSets(relatieSets);
        LOGGER.debug("Consolideer sets: {}", relatieSets);

        return maakMapping(relatieSets);
    }

    private boolean isBeeindigdMetRedenOmzetting(final MetaObject verbintenis, final GroepElement groepElement, final AttribuutElement redenEindeElement) {
        final MetaRecord actueelRecord = MetaModelUtil.getActueleRecord(verbintenis, groepElement);
        final MetaAttribuut redenEindeRelatie = actueelRecord == null ? null : actueelRecord.getAttribuut(redenEindeElement);

        return (redenEindeRelatie != null) && "Z".equals(redenEindeRelatie.getWaarde());
    }

    private Long zoekGerelateerdeRelatie(final MetaObject relatieBeeindigdMetRedenOmzetting, final GroepElement beeindigdeGroepElement,
                                         final AttribuutElement redenEindeElement, final Collection<MetaObject> alleAndereVerbintenissen,
                                         final GroepElement andereGroepElement) {
        // Verzamel alle active inhoud IDs (waarbij reden einde gevuld is)
        final List<Long> actieInhoudIdsVanBeeindigdeRelatie = new ArrayList<>();
        for (final MetaRecord his : relatieBeeindigdMetRedenOmzetting.getGroep(beeindigdeGroepElement).getRecords()) {
            final MetaAttribuut redenEindeRelatie = his.getAttribuut(redenEindeElement);
            if ((redenEindeRelatie != null) && "Z".equals(redenEindeRelatie.getWaarde())) {
                actieInhoudIdsVanBeeindigdeRelatie.add(his.getActieInhoud().getId());
            }
        }

        // Zoek gekoppelde relatie
        for (final MetaObject relatie : alleAndereVerbintenissen) {
            if (bevatAanvangMetActieInhoudMetId(relatie, andereGroepElement, actieInhoudIdsVanBeeindigdeRelatie)) {
                return relatie.getObjectsleutel();
            }
        }

        throw new IllegalStateException(
                "Relatie gevonden met reden einde 'omzetting', maar geen nieuwe relatie kunnen vinden om te koppelen voor GBA levering.");
    }

    private boolean bevatAanvangMetActieInhoudMetId(final MetaObject relatie, final GroepElement groepElement, final List<Long> actieIds) {

        for (final MetaRecord hisRelatie : MetaModelUtil.getRecords(relatie, groepElement)) {
            if (actieIds.contains(hisRelatie.getActieInhoud().getId())) {
                return true;
            }
        }
        return false;
    }

    private void verwerkInRelatieSets(final List<Set<Long>> relatieSets, final Long id1, final Long id2) {
        LOGGER.debug("verwerkInRelatieSets: {}, {}", id1, id2);

        for (final Set<Long> relatieSet : relatieSets) {
            if (relatieSet.contains(id1) || relatieSet.contains(id2)) {
                relatieSet.add(id1);
                relatieSet.add(id2);
                return;
            }
        }

        final Set<Long> relatieSet = new TreeSet<>();
        relatieSet.add(id1);
        relatieSet.add(id2);
        relatieSets.add(relatieSet);
    }

    private void verwerkInRelatieSets(final List<Set<Long>> relatieSets, final Long id) {
        LOGGER.debug("verwerkInRelatieSets: {}", id);
        for (final Set<Long> relatieSet : relatieSets) {
            if (relatieSet.contains(id)) {
                return;
            }
        }

        final Set<Long> relatieSet = new TreeSet<>();
        relatieSet.add(id);
        relatieSets.add(relatieSet);
    }

    private List<Set<Long>> consolideerSets(final List<Set<Long>> relatieSets) {
        final List<Set<Long>> resultaat = new ArrayList<>();

        for (final Set<Long> relatieSet : relatieSets) {
            consolideerSet(resultaat, relatieSet);
        }

        return resultaat;

    }

    private void consolideerSet(final List<Set<Long>> resultaat, final Set<Long> relatieSet) {
        for (final Set<Long> resultaatSet : resultaat) {
            if (resultaatSet.removeAll(relatieSet)) {
                resultaatSet.addAll(relatieSet);
                return;
            }
        }

        resultaat.add(relatieSet);
    }

    private Map<Long, String> maakMapping(final List<Set<Long>> relatieSets) {
        final Map<Long, String> resultaat = new HashMap<>();
        for (final Set<Long> relatieSet : relatieSets) {
            final String relatieMapping = maakMapping(relatieSet);
            for (final Long relatieId : relatieSet) {
                resultaat.put(relatieId, relatieMapping);
            }
        }
        return resultaat;
    }

    private String maakMapping(final Set<Long> relatieSet) {
        final StringBuilder resultaat = new StringBuilder();
        for (final Long relatieId : relatieSet) {
            if (resultaat.length() != 0) {
                resultaat.append("+");
            }
            resultaat.append(relatieId);
        }
        return resultaat.toString();
    }

}
