/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dashboard;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.HuwelijkEnGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.dashboard.HuwelijkBerichtRequest;
import nl.bzk.brp.model.dashboard.Plaats;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;


/**
 * Notificator implementatie voor een huwelijk of geregistreerd partnerschap.
 */
public class HuwelijkNotificator extends
        AbstractDashboardNotificator<HuwelijkEnGeregistreerdPartnerschapBericht, HuwelijkBerichtRequest>
{

    @Override
    protected void verzendNotificatie(final HuwelijkBerichtRequest request) {
        getRestTemplate().postForEntity(getDashboardUri(), request, HuwelijkBerichtRequest.class);
    }

    @Override
    protected boolean kanVerwerken(final BijhoudingsBericht bericht) {
        return bericht instanceof HuwelijkEnGeregistreerdPartnerschapBericht;
    }

    @Override
    protected HuwelijkBerichtRequest creeerDashboardRequest(final HuwelijkEnGeregistreerdPartnerschapBericht bericht,
            final BerichtContext context)
    {
        return new HuwelijkBerichtRequest();
    }

    @Override
    protected void voltooiDashboardRequest(final HuwelijkBerichtRequest request,
            final HuwelijkEnGeregistreerdPartnerschapBericht bericht, final BerichtContext context,
            final BerichtResultaat resultaat)
    {
        Relatie huwelijk = (Relatie) bericht.getBrpActies().get(0).getRootObjecten().get(0);
        List<Persoon> partners = getPersonen(huwelijk, SoortBetrokkenheid.PARTNER);
        Persoon persoon1 = laadPersoon(partners, 0);
        Persoon persoon2 = laadPersoon(partners, 1);

        request.setDatumAanvang(huwelijk.getGegevens().getDatumAanvang().getWaarde());

        if (huwelijk.getGegevens().getWoonPlaatsAanvang() != null) {
            request.setPlaats(new Plaats(huwelijk.getGegevens().getWoonPlaatsAanvang()));
        }

        if (persoon1 != null) {
            request.setPersoon1(new nl.bzk.brp.model.dashboard.Persoon(persoon1));
        }
        if (persoon2 != null) {
            request.setPersoon2(new nl.bzk.brp.model.dashboard.Persoon(persoon2));
        }
    }

    /**
     * @param huwelijk het huwelijk of het parnerschap
     * @param soort de soort betrokkenheid
     * @return alle personen uit de gegeven relatie met de gegeven soort betrokkenheid
     */
    private List<Persoon> getPersonen(final Relatie huwelijk, final SoortBetrokkenheid soort) {
        List<Persoon> resultaat = new ArrayList<Persoon>();
        for (Betrokkenheid betrokkenheid : huwelijk.getBetrokkenheden()) {
            if (soort.equals(betrokkenheid.getRol())) {
                resultaat.add(betrokkenheid.getBetrokkene());
            }
        }
        return resultaat;
    }

}
