/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dashboard;

import java.util.List;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;


/**
 * Verwerkingstap die voor elk bijhoudingsbericht een notificatie stuurt naar de dashboard applicatie.
 */
public class DashboardNotificatieStap
        extends AbstractBerichtVerwerkingsStap<AbstractBijhoudingsBericht, BerichtVerwerkingsResultaat>
{

    private List<AbstractDashboardNotificator<?, ?>> notificators;

    private boolean                                  actief;

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final AbstractBijhoudingsBericht bericht,
                                                     final BerichtContext context,
                                                     final BerichtVerwerkingsResultaat resultaat)
    {
        if (actief) {
            for (AbstractDashboardNotificator<?, ?> notificator : notificators) {
                if (notificator.kanVerwerken(bericht)) {
                    notificator.voorbereiden(bericht, context);
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void naVerwerkingsStapVoorBericht(final AbstractBijhoudingsBericht bericht, final BerichtContext context,
            final BerichtVerwerkingsResultaat resultaat)
    {
        if (actief) {
            for (AbstractDashboardNotificator<?, ?> notificator : notificators) {
                if (notificator.kanVerwerken(bericht)) {
                    notificator.notificeerDashboard(bericht, context, resultaat);
                    break;
                }
            }
        }
    }

    public void setNotificators(final List<AbstractDashboardNotificator<?, ?>> notificators) {
        this.notificators = notificators;
    }

    public void setActief(final boolean waarde) {
        actief = waarde;
    }

}
