/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen.persoon;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.levering.business.bepalers.BetrokkenheidMagLeverenBepalerService;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieBerichtContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonBericht;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtValidatieStap;

/**
 * Stap die ervoor zorgt dat er magLeveren vlaggen gezet worden op Persoon, Betrokkenheid en Relatie objecten.
 */
public class BetrokkenheidBepaalMagLeverenStap extends AbstractBerichtValidatieStap<GeefSynchronisatiePersoonBericht,
    GeefSynchronisatiePersoonBericht, SynchronisatieBerichtContext, SynchronisatieResultaat>
{

    @Inject
    private BetrokkenheidMagLeverenBepalerService betrokkenheidMagLeverenBepalerService;

    @Override
    public final boolean voerStapUit(final GeefSynchronisatiePersoonBericht geefSynchronisatiePersoonBericht,
        final SynchronisatieBerichtContext context, final SynchronisatieResultaat synchronisatieResultaat)
    {
        final VolledigBericht volledigBericht = context.getVolledigBericht();
        final List<PersoonHisVolledigView> persoonHisVolledigViews = volledigBericht.getAdministratieveHandeling().getBijgehoudenPersonen();
        final Leveringinformatie leveringAutorisatie = context.getLeveringinformatie();
        for (final PersoonHisVolledigView persoonHisVolledigView : persoonHisVolledigViews) {
            betrokkenheidMagLeverenBepalerService.bepaalMagLeveren(persoonHisVolledigView, leveringAutorisatie.getDienst(), false);
        }

        return DOORGAAN;
    }
}
