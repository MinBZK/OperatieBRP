/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import javax.inject.Inject;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContext;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesResultaat;
import nl.bzk.brp.levering.business.bepalers.BetrokkenheidMagLeverenBepalerService;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;

/**
 * Stap die ervoor zorgt dat er magLeveren vlaggen gezet worden op Persoon, Betrokkenheid en Relatie objecten.
 */
public class BetrokkenheidBepaalMagLeverenStap extends AbstractBerichtVerwerkingStap<RegistreerAfnemerindicatieBericht,
    OnderhoudAfnemerindicatiesBerichtContext, OnderhoudAfnemerindicatiesResultaat>
{
    @Inject
    private BetrokkenheidMagLeverenBepalerService betrokkenheidMagLeverenBepalerService;

    @Override
    public final boolean voerStapUit(final RegistreerAfnemerindicatieBericht registreerAfnemerindicatieBericht,
        final OnderhoudAfnemerindicatiesBerichtContext context,
        final OnderhoudAfnemerindicatiesResultaat onderhoudAfnemerindicatiesResultaat)
    {
        final VolledigBericht volledigBericht = context.getVolledigBericht();
        final PersoonHisVolledigView persoonView =
            volledigBericht.getAdministratieveHandeling().getBijgehoudenPersonen().get(0);

        final Leveringinformatie leveringAutorisatie = context.getLeveringinformatie();
        betrokkenheidMagLeverenBepalerService.bepaalMagLeveren(persoonView, leveringAutorisatie.getDienst(), false);
        return DOORGAAN;
    }
}
