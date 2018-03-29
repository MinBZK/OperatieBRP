/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.levering.lo3.mapper.AbstractMapper;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.AbstractBrpGroepConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Basis mutatie verwerker voor relaties (huwelijk of geregistreerd partnerschap).
 * @param <G> groep type
 */
public abstract class AbstractRelatieMutatieVerwerker<G extends BrpGroepInhoud> extends AbstractFormeelMutatieVerwerker<Lo3HuwelijkOfGpInhoud, G> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final int CATEGORIE_6 = 6;

    @Autowired
    private VerConvRepository verConvRepository;

    private final AttribuutElement redenEindeElement;

    /**
     * Constructor.
     * @param mapper mapper
     * @param converteerder converteerder
     * @param attribuutConverteerder attributen converteerder
     * @param historieNabewerking nabewerking
     * @param groepElement groep element
     * @param redenEindeElement reden einde attribuut element
     */
    protected AbstractRelatieMutatieVerwerker(final AbstractMapper<G> mapper, final AbstractBrpGroepConverteerder<G, Lo3HuwelijkOfGpInhoud> converteerder,
                                              final BrpAttribuutConverteerder attribuutConverteerder,
                                              final HistorieNabewerking<Lo3HuwelijkOfGpInhoud, G> historieNabewerking,
                                              final GroepElement groepElement, final AttribuutElement redenEindeElement) {
        super(mapper, converteerder, attribuutConverteerder, historieNabewerking, groepElement, LOGGER);
        this.redenEindeElement = redenEindeElement;
    }

    /**
     * Voor GBA leveringen moeten records op de relatie worden genegeerd als deze een actie inhoud
     * hebben met een herkomst (lo3voorkomen) met categorie 06. Voor BRP dient namelijk een
     * huwelijk/partnerschap te worden ontbonden als de persoon overlijd (cat06); voor GBA niet. Dit
     * wordt in de conversie geregistreerd door het ontbinden van het huwelijk/partnerschap te
     * koppelen aan de actie die wordt gemaakt voor het overlijden.
     */
    @Override
    protected final Collection<MetaRecord> filterHistorieSet(final Collection<MetaRecord> historieSet, final List<Long> acties) {
        // Bepaal actie inhoud die aan administratieve handeling hangt.
        final Actie actueel = bepaalActieInhoud(historieSet, acties);

        // Bepaal of deze via lo3 herkomst uit cat 8 komt
        final Lo3Voorkomen lo3Voorkomen = actueel == null ? null : verConvRepository.zoekLo3VoorkomenVoorActie(actueel.getId());
        if ((lo3Voorkomen != null) && isVoorkomenUitCategorie6(lo3Voorkomen)) {
            // Zo ja, hele set niet gebruiken
            LOGGER.info("Historie negeren; actuele actie inhoud is gekoppeld aan categorie 06");
            return Collections.emptySet();
        }

        // Zo nee, set filteren waarbij alle records met reden einde 'omzetting' worden genegeerd.
        final Set<MetaRecord> resultaat = new HashSet<>();
        for (final MetaRecord historie : historieSet) {
            final MetaAttribuut redenEinde = historie.getAttribuut(redenEindeElement);
            if ((redenEinde == null) || (redenEinde.getWaarde() == null) || !Character.valueOf('Z').equals(redenEinde.<Character>getWaarde())) {
                resultaat.add(historie);
            }
        }

        return resultaat;
    }

    private Actie bepaalActieInhoud(final Collection<MetaRecord> historieSet, final List<Long> acties) {
        for (final MetaRecord historie : historieSet) {
            final Actie actieInhoud = historie.getActieInhoud();
            if ((actieInhoud != null) && acties.contains(actieInhoud.getId())) {
                return actieInhoud;
            }
        }
        return null;
    }

    private boolean isVoorkomenUitCategorie6(final Lo3Voorkomen lo3Voorkomen) {
        return (lo3Voorkomen.getCategorie() != null) && (CATEGORIE_6 == Integer.parseInt(lo3Voorkomen.getCategorie()));

    }

    /**
     * Bij relatie (huwelijken) is het meest relevante record de ontbinding met de meest recente ts
     * reg.
     * @param vervallen de vervallen records
     * @return het meeste relevante record
     */
    @Override
    protected final MetaRecord bepaalMeestRelevanteVervallenRecord(final List<MetaRecord> vervallen) {
        Collections.sort(vervallen, new HisRelatieModelComparator());
        return vervallen.get(vervallen.size() - 1);
    }

    /**
     * Sorteert op basis van tijdstip registratie (meeste recente ts reg komt als eerst in de
     * lijst).
     */
    private static final class HisRelatieModelComparator implements Comparator<MetaRecord>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final MetaRecord arg0, final MetaRecord arg1) {
            final Integer datumEinde0 = arg0.getDatumEindeGeldigheid();
            final Integer datumEinde1 = arg1.getDatumEindeGeldigheid();

            int result;
            if (datumEinde0 == null) {
                if (datumEinde1 == null) {
                    result = 0;
                } else {
                    result = -1;
                }
            } else {
                if (datumEinde1 == null) {
                    result = 1;
                } else {
                    result = 0;
                }
            }

            if (result == 0) {
                result = arg0.getTijdstipRegistratie().compareTo(arg1.getTijdstipRegistratie());
            }

            return result;
        }
    }

}
