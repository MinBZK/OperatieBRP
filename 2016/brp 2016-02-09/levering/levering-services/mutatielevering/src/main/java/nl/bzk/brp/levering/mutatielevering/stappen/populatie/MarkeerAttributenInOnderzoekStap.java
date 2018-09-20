/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import java.util.List;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.AbstractAfnemerVerwerkingStap;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;

/**
 * Markeer attributen welke in onderzoek zijn, op basis van een onderzoek administratieve handeling.
 */
@Regels(Regel.R1319)
public class MarkeerAttributenInOnderzoekStap extends AbstractAfnemerVerwerkingStap {
    /**
     * De Constante LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public boolean voerStapUit(final LeveringautorisatieStappenOnderwerp onderwerp, final LeveringsautorisatieVerwerkingContext context,
        final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        for (final SynchronisatieBericht leveringBericht : context.getLeveringBerichten()) {

            for (final PersoonHisVolledigView bijgehoudenPersoon : leveringBericht.getAdministratieveHandeling().getBijgehoudenPersonen()) {
                for (final PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig : bijgehoudenPersoon.getOnderzoeken()) {
                    if (SoortSynchronisatie.MUTATIEBERICHT.equals(leveringBericht.geefSoortSynchronisatie())) {
                        zetAttributenInOnderzoek(bijgehoudenPersoon, persoonOnderzoekHisVolledig, context, leveringBericht);
                    }
                }
            }
        }
        return DOORGAAN;
    }

    private void zetAttributenInOnderzoek(final PersoonHisVolledigView bijgehoudenPersoon, final PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig,
        final LeveringsautorisatieVerwerkingContext context, final SynchronisatieBericht leveringBericht)
    {
        final HisOnderzoekModel actueleRecord = persoonOnderzoekHisVolledig.getOnderzoek().getOnderzoekHistorie()
            .getActueleRecord();
        if (actueleRecord == null) {
            LOGGER.warn("Geen actueel record voor persoon onderzoek {}", persoonOnderzoekHisVolledig.getID());
            return;
        }
        final AdministratieveHandelingModel administratieveHandelingModel = actueleRecord.getVerantwoordingInhoud().getAdministratieveHandeling();
        if (!(context.getAdministratieveHandeling().getID().equals(administratieveHandelingModel.getID())
            && isOnderzoekAdministratieveHandeling(leveringBericht.getAdministratieveHandeling().getSoort())))
        {
            return;
        }

        for (final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig : persoonOnderzoekHisVolledig.getOnderzoek().getGegevensInOnderzoek()) {
            boolean attribuutInOnderzoekGezet = false;
            if (context.getPersoonOnderzoekenMap().containsKey(bijgehoudenPersoon.getID())) {
                final List<Attribuut> attributenInOnderzoek =
                    context.getPersoonOnderzoekenMap().get(bijgehoudenPersoon.getID()).get(gegevenInOnderzoekHisVolledig.getID());

                if (attributenInOnderzoek != null) {
                    for (final Attribuut attribuutInOnderzoek : attributenInOnderzoek) {
                        attribuutInOnderzoek.setInOnderzoek(true);
                        attribuutInOnderzoekGezet = true;
                    }
                } else {
                    LOGGER.warn("Er zijn geen attributen in onderzoek gevonden voor persoon met id: {} en gegeven in onderzoek met id: {}",
                        bijgehoudenPersoon.getID(),
                        gegevenInOnderzoekHisVolledig.getID());
                }
            }

            if (!attribuutInOnderzoekGezet) {
                LOGGER.warn("Attribuut voor voorkomen in onderzoek {} kon niet in onderzoek worden gezet.", gegevenInOnderzoekHisVolledig.getID());
            }
        }
    }


    private boolean isOnderzoekAdministratieveHandeling(final SoortAdministratieveHandeling soort) {
        boolean isOnderzoekAdministratieveHandeling = soort == SoortAdministratieveHandeling.AANVANG_ONDERZOEK;
        isOnderzoekAdministratieveHandeling |= soort == SoortAdministratieveHandeling.BEEINDIGING_ONDERZOEK;
        isOnderzoekAdministratieveHandeling |= soort == SoortAdministratieveHandeling.WIJZIGING_ONDERZOEK;
        // TODO Voor GBA bijhoudingen weten we niet of het een onderzoek handeling is, hoe gaan we hiermee om?
        isOnderzoekAdministratieveHandeling |= soort == SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL;
        return isOnderzoekAdministratieveHandeling;
    }
}
