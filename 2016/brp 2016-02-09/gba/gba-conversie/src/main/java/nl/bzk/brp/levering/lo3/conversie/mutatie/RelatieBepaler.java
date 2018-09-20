/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkGeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import org.springframework.stereotype.Component;

/**
 * Analiseer relaties om te bepalen welke BRP relaties samengevoegd kunnen worden tot 1 GBA relatie (in verband met het
 * verschil tussen omzettingen van huwelijken naar geregistreerd partnerschappen (en vice versa) in GBA en BRP).
 */
@Component
public final class RelatieBepaler {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Bepaal de relatie mapping, waarbij omzettingen van huwelijken in geregistreerd partnerschappen en vice versa
     * worden gezien als dezelfde relatie en dus dezelfde relatieMapping dienen te krijgen.
     *
     * @param relaties
     *            alle huwelijken en geregistreerd partnerschappen
     * @return relatie mapping tbv gba categorie 05
     */
    public Map<Integer, String> bepaalRelatieMapping(final Set<? extends HuwelijkGeregistreerdPartnerschapHisVolledig> relaties) {
        List<Set<Integer>> relatieSets = new ArrayList<>();

        for (final HuwelijkGeregistreerdPartnerschapHisVolledig relatie : relaties) {
            LOGGER.debug("Verwerk relatie: {}", relatie.getID());

            if (isBeeindigdMetRedenOmzetting(relatie)) {
                verwerkInRelatieSets(relatieSets, relatie.getID(), zoekGerelateerdeRelatie(relatie, relaties));

            } else {
                verwerkInRelatieSets(relatieSets, relatie.getID());
            }

            LOGGER.debug("{}", relatieSets);
        }

        relatieSets = consolideerSets(relatieSets);
        LOGGER.debug("Consolideer sets: {}", relatieSets);

        return maakMapping(relatieSets);
    }

    private boolean isBeeindigdMetRedenOmzetting(final HuwelijkGeregistreerdPartnerschapHisVolledig relatie) {
        return relatie.getRelatieHistorie() != null
               && relatie.getRelatieHistorie().getActueleRecord() != null
               && isBeeindigdMetRedenOmzetting(relatie.getRelatieHistorie().getActueleRecord());
    }

    private boolean isBeeindigdMetRedenOmzetting(final HisRelatieModel relatie) {
        return relatie.getRedenEinde() != null
               && relatie.getRedenEinde().getWaarde() != null
               && relatie.getRedenEinde().getWaarde().getCode() != null
               && RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_OMZETTING_SOORT_VERBINTENIS_CODE_STRING.equals(
                   relatie.getRedenEinde().getWaarde().getCode().getWaarde());
    }

    private Integer zoekGerelateerdeRelatie(
        final HuwelijkGeregistreerdPartnerschapHisVolledig relatieBeeindigdMetRedenOmzetting,
        final Set<? extends HuwelijkGeregistreerdPartnerschapHisVolledig> alleRelaties)
    {
        final Set<Long> actieIds = bepaalActieInhoudIdsVoorOmzetting(relatieBeeindigdMetRedenOmzetting);
        for (final HuwelijkGeregistreerdPartnerschapHisVolledig relatie : alleRelaties) {
            if (relatie.getID().equals(relatieBeeindigdMetRedenOmzetting.getID())) {
                // De beeindigde relatie zelf.
                continue;
            }

            if (bevatAanvangMetActieInhoudMetId(relatie, actieIds)) {
                return relatie.getID();
            }
        }

        throw new IllegalStateException(
            "Relatie gevonden met reden einde 'omzetting', maar geen nieuwe relatie kunnen vinden om te koppelen voor GBA levering.");
    }

    private Set<Long> bepaalActieInhoudIdsVoorOmzetting(final HuwelijkGeregistreerdPartnerschapHisVolledig relatieBeeindigdMetRedenOmzetting) {
        final Set<Long> resultaat = new HashSet<>();
        for (final HisRelatieModel hisRelatie : relatieBeeindigdMetRedenOmzetting.getRelatieHistorie()) {
            if (hisRelatie.getVerantwoordingVerval() == null && isBeeindigdMetRedenOmzetting(hisRelatie)) {
                resultaat.add(hisRelatie.getVerantwoordingInhoud().getID());
            }
        }

        return resultaat;
    }

    private boolean bevatAanvangMetActieInhoudMetId(final HuwelijkGeregistreerdPartnerschapHisVolledig relatie, final Set<Long> actieIds) {
        for (final HisRelatieModel hisRelatie : relatie.getRelatieHistorie()) {
            if (actieIds.contains(hisRelatie.getVerantwoordingInhoud().getID())) {
                return true;
            }
        }
        return false;
    }

    private void verwerkInRelatieSets(final List<Set<Integer>> relatieSets, final Integer id1, final Integer id2) {
        LOGGER.debug("verwerkInRelatieSets: {}, {}", id1, id2);

        for (final Set<Integer> relatieSet : relatieSets) {
            if (relatieSet.contains(id1) || relatieSet.contains(id2)) {
                relatieSet.add(id1);
                relatieSet.add(id2);
                return;
            }
        }

        final Set<Integer> relatieSet = new TreeSet<>();
        relatieSet.add(id1);
        relatieSet.add(id2);
        relatieSets.add(relatieSet);
    }

    private void verwerkInRelatieSets(final List<Set<Integer>> relatieSets, final Integer id) {
        LOGGER.debug("verwerkInRelatieSets: {}", id);
        for (final Set<Integer> relatieSet : relatieSets) {
            if (relatieSet.contains(id)) {
                return;
            }
        }

        final Set<Integer> relatieSet = new TreeSet<>();
        relatieSet.add(id);
        relatieSets.add(relatieSet);
    }

    private List<Set<Integer>> consolideerSets(final List<Set<Integer>> relatieSets) {
        final List<Set<Integer>> resultaat = new ArrayList<>();

        for (final Set<Integer> relatieSet : relatieSets) {
            consolideerSet(resultaat, relatieSet);
        }

        return resultaat;

    }

    private void consolideerSet(final List<Set<Integer>> resultaat, final Set<Integer> relatieSet) {
        for (final Set<Integer> resultaatSet : resultaat) {
            if (resultaatSet.removeAll(relatieSet)) {
                resultaatSet.addAll(relatieSet);
                return;
            }
        }

        resultaat.add(relatieSet);
    }

    private Map<Integer, String> maakMapping(final List<Set<Integer>> relatieSets) {
        final Map<Integer, String> resultaat = new HashMap<>();
        for (final Set<Integer> relatieSet : relatieSets) {
            final String relatieMapping = maakMapping(relatieSet);
            for (final Integer relatieId : relatieSet) {
                resultaat.put(relatieId, relatieMapping);
            }
        }
        return resultaat;
    }

    private String maakMapping(final Set<Integer> relatieSet) {
        final StringBuilder resultaat = new StringBuilder();
        for (final Integer relatieId : relatieSet) {
            if (resultaat.length() != 0) {
                resultaat.append("+");
            }
            resultaat.append(relatieId);
        }
        return resultaat.toString();
    }
}
