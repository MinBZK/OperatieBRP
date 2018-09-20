/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.levering.lo3.mapper.AbstractFormeelMapper;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.FormeleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.logisch.verconv.LO3Voorkomen;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Basis mutatie verwerker voor relaties (huwelijk of geregistreerd partnerschap).
 *
 * @param <G>
 *            groep type
 */
public abstract class AbstractRelatieMutatieVerwerker<G extends BrpGroepInhoud>
        extends AbstractFormeelMutatieVerwerker<Lo3HuwelijkOfGpInhoud, G, HisRelatieModel>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final FormeleHistorieSet<HisRelatieModel> EMPTY_SET = new FormeleHistorieSetImpl<>(new HashSet<HisRelatieModel>());

    @Autowired
    private VerConvRepository verConvRepository;

    /**
     * Constructor.
     *
     * @param mapper
     *            mapper
     * @param converteerder
     *            converteerder
     * @param historieNabewerking
     *            nabewerking
     * @param groepElement
     *            groep element
     */
    protected AbstractRelatieMutatieVerwerker(
        final AbstractFormeelMapper<?, HisRelatieModel, G> mapper,
        final BrpGroepConverteerder<G, Lo3HuwelijkOfGpInhoud> converteerder,
        final HistorieNabewerking<G> historieNabewerking,
        final ElementEnum groepElement)
    {
        super(mapper, converteerder, historieNabewerking, groepElement);
    }

    /**
     * Voor GBA leveringen moeten records op de relatie worden genegeerd als deze een actie inhoud hebben met een
     * herkomst (lo3voorkomen) met categorie 06. Voor BRP dient namelijk een huwelijk/partnerschap te worden ontbonden
     * als de persoon overlijd (cat06); voor GBA niet. Dit wordt in de conversie geregistreerd door het ontbinden van
     * het huwelijk/partnerschap te koppelen aan de actie die wordt gemaakt voor het overlijden.
     */
    @Override
    protected final FormeleHistorieSet<HisRelatieModel> filterHistorieSet(final FormeleHistorieSet<HisRelatieModel> historieSet, final List<Long> acties) {
        // Bepaal actie inhoud die aan administratieve handeling hangt.
        ActieModel actueel = null;
        for (final HisRelatieModel historie : historieSet) {
            final ActieModel actieInhoud = historie.getVerantwoordingInhoud();
            if (actieInhoud != null && acties.contains(actieInhoud.getID())) {
                actueel = actieInhoud;
                break;
            }
        }

        // Bepaal of deze via lo3 herkomst uit cat 8 komt
        final LO3Voorkomen lo3Voorkomen = verConvRepository.zoekLo3VoorkomenVoorActie(actueel);
        if (lo3Voorkomen != null && lo3Voorkomen.getLO3Categorie() != null && "06".equals(lo3Voorkomen.getLO3Categorie().getWaarde())) {
            // Zo ja, hele set niet gebruiken
            LOGGER.info("Historie negeren; actuele actie inhoud is gekoppeld aan categorie 06");
            return EMPTY_SET;
        }

        // Zo nee, set filteren waarbij alle records met reden einde 'omzetting' worden genegeerd.
        final Set<HisRelatieModel> resultaat = new HashSet<>();
        for (final HisRelatieModel historie : historieSet) {
            if (historie.getRedenEinde() == null
                || historie.getRedenEinde().getWaarde() == null
                || historie.getRedenEinde().getWaarde().getCode() == null
                || !RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_OMZETTING_SOORT_VERBINTENIS_CODE_STRING.equals(
                    historie.getRedenEinde().getWaarde().getCode().getWaarde()))
            {
                resultaat.add(historie);
            }
        }

        return new FormeleHistorieSetImpl<>(resultaat);
    }

    /**
     * Bij relatie (huwelijken) is het meest relevante record de ontbinding met de meest recente ts reg.
     *
     * @param vervallen
     *            de vervallen records
     * @return het meeste relevante record
     */
    @Override
    protected final HisRelatieModel bepaalMeestRelevanteVervallenRecord(final List<HisRelatieModel> vervallen) {
        Collections.sort(vervallen, new HisRelatieModelComparator());
        return vervallen.get(vervallen.size() - 1);
    }

    /**
     * Sorteert op basis van tijdstip registratie (meeste recente ts reg komt als eerst in de lijst).
     */
    private static final class HisRelatieModelComparator implements Comparator<HisRelatieModel>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final HisRelatieModel arg0, final HisRelatieModel arg1) {
            final Integer datumEinde0 = arg0.getDatumEinde() == null ? null : arg0.getDatumEinde().getWaarde();
            final Integer datumEinde1 = arg1.getDatumEinde() == null ? null : arg1.getDatumEinde().getWaarde();

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
                result = arg0.getFormeleHistorie().getTijdstipRegistratie().compareTo(arg1.getFormeleHistorie().getTijdstipRegistratie());
            }

            return result;
        }
    }

}
