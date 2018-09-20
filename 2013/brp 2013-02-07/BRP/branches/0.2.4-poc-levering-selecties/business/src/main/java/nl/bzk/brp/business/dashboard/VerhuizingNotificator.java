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
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.dashboard.VerhuisBerichtRequest;
import nl.bzk.brp.model.objecttype.logisch.Persoon;


/**
 * Notificator implementatie voor een verhuisbericht.
 */
public class VerhuizingNotificator extends AbstractDashboardNotificator<VerhuizingBericht, VerhuisBerichtRequest> {

    @Override
    protected boolean kanVerwerken(final AbstractBijhoudingsBericht bericht) {
        return bericht instanceof VerhuizingBericht;
    }

    @Override
    protected VerhuisBerichtRequest creeerDashboardRequest(final VerhuizingBericht bericht,
            final BerichtContext context)
    {
        VerhuisBerichtRequest request = new VerhuisBerichtRequest();
        final List<RootObject> rootObjecten = bericht.getBrpActies().get(0).getRootObjecten();

        Persoon persoon = laadPersoon(rootObjecten, 0);

        nl.bzk.brp.model.dashboard.Persoon persoonUit = new nl.bzk.brp.model.dashboard.Persoon(persoon);
        request.setPersoon(persoonUit);

        nl.bzk.brp.model.dashboard.Adres oudAdres =
            new nl.bzk.brp.model.dashboard.Adres(persoon.getAdressen().iterator().next());

        request.setOudAdres(oudAdres);

        return request;
    }

    @Override
    protected void voltooiDashboardRequest(final VerhuisBerichtRequest request, final VerhuizingBericht bericht,
            final BerichtContext context, final BerichtVerwerkingsResultaat resultaat)
    {
        final List<RootObject> rootObjecten = bericht.getBrpActies().get(0).getRootObjecten();

        Persoon persoon = laadPersoon(rootObjecten, 0);

        nl.bzk.brp.model.dashboard.Adres nieuwAdres =
            new nl.bzk.brp.model.dashboard.Adres(persoon.getAdressen().iterator().next());

        request.setNieuwAdres(nieuwAdres);
    }

}
