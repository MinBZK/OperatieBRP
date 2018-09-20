/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import javax.inject.Inject;

import nl.bzk.brp.levering.business.bepalers.BetrokkenheidMagLeverenBepalerService;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.AbstractAfnemerVerwerkingStap;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.SynchronisatieBericht;


/**
 * Stap die ervoor zorgt dat er magLeveren vlaggen gezet worden op Persoon, Betrokkenheid en Relatie objecten.
 */
public class BetrokkenheidBepaalMagLeverenStap extends AbstractAfnemerVerwerkingStap {

    @Inject
    private BetrokkenheidMagLeverenBepalerService betrokkenheidMagLeverenBepalerService;

    @Override
    public final boolean voerStapUit(final LeveringautorisatieStappenOnderwerp leveringautorisatieStappenOnderwerp, final LeveringsautorisatieVerwerkingContext context,
                                     final LeveringautorisatieVerwerkingResultaat leveringautorisatieVerwerkingResultaat)
    {
        final Leveringinformatie leveringAutorisatie = leveringautorisatieStappenOnderwerp.getLeveringinformatie();
        for (final SynchronisatieBericht leveringBericht : context.getLeveringBerichten()) {
            if (SoortSynchronisatie.MUTATIEBERICHT.equals(leveringBericht.geefSoortSynchronisatie())) {
                for (final PersoonHisVolledigView bijgehoudenPersoon : leveringBericht.getAdministratieveHandeling().getBijgehoudenPersonen()) {
                    betrokkenheidMagLeverenBepalerService.bepaalMagLeveren(bijgehoudenPersoon, leveringAutorisatie.getDienst(), true);
                }
            } else if (SoortSynchronisatie.VOLLEDIGBERICHT.equals(leveringBericht.geefSoortSynchronisatie())) {
                for (final PersoonHisVolledigView bijgehoudenPersoon : leveringBericht.getAdministratieveHandeling().getBijgehoudenPersonen()) {
                    betrokkenheidMagLeverenBepalerService.bepaalMagLeveren(bijgehoudenPersoon, leveringAutorisatie.getDienst(), false);
                }
            }
        }
        return DOORGAAN;
    }
}
