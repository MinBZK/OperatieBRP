/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging.levering;

import javax.inject.Inject;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.levering.business.bepalers.BetrokkenheidMagLeverenBepalerService;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;

/**
 * Stap die ervoor zorgt dat er magLeveren vlaggen gezet worden op Persoon, Betrokkenheid en Relatie objecten.
 */
public class BetrokkenheidBepaalMagLeverenStap extends AbstractBerichtVerwerkingStap<BevragingsBericht, BevragingBerichtContextBasis, BevragingResultaat> {

    @Inject
    private BetrokkenheidMagLeverenBepalerService betrokkenheidMagLeverenBepalerService;

    @Override
    public final boolean voerStapUit(final BevragingsBericht bevragingsBericht, final BevragingBerichtContextBasis context,
        final BevragingResultaat resultaat)
    {
        for (final PersoonHisVolledigView persoon : resultaat.getGevondenPersonen()) {
            betrokkenheidMagLeverenBepalerService.bepaalMagLeveren(persoon, context.getLeveringinformatie().getDienst(), false);
        }
        return DOORGAAN;
    }
}
