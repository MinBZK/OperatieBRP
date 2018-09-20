/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dashboard;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.HuwelijkEnGeregistreerdPartnerschapBericht;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.dashboard.HuwelijkBerichtRequest;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;


/**
 * Notificator implementatie voor een huwelijk of geregistreerd partnerschap.
 */
public class HuwelijkNotificator extends
        AbstractDashboardNotificator<HuwelijkEnGeregistreerdPartnerschapBericht, HuwelijkBerichtRequest>
{

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    @Override
    protected void verzendNotificatie(final HuwelijkBerichtRequest request) {
        getRestTemplate().postForEntity(getDashboardUri(), request, HuwelijkBerichtRequest.class);
    }

    /**
     * @param persoon persoon waarvan het geslacht moet worden bepaald
     * @return true als het een vrouw is, anders false
     */
    private boolean isVrouw(final Persoon persoon) {
        return persoon.getGeslachtsaanduiding() != null
            && Geslachtsaanduiding.VROUW.equals(persoon.getGeslachtsaanduiding().getGeslachtsaanduiding());
    }

    /**
     * @param persoon persoon waaruit het adres moet worden gelezen
     * @return het adres uit de persoon of null indien niet ingevuld
     */
    private PersoonAdres getPersoonAdres(final Persoon persoon) {
        PersoonAdres resultaat;
        if (persoon != null && persoon.getAdressen() != null && persoon.getAdressen().size() > 0) {
            resultaat = persoon.getAdressen().iterator().next();
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    @Override
    protected boolean kanVerwerken(final AbstractBijhoudingsBericht bericht) {
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
        Relatie familie = (Relatie) bericht.getBrpActies().get(0).getRootObjecten().get(0);
        List<Persoon> partners = getPersonen(familie, SoortBetrokkenheid.PARTNER);
        Persoon persoon1 = laadPersoon(partners, 0);
        Persoon persoon2 = laadPersoon(partners, 1);

        PersoonAdres persoonAdres = getPersoonAdres(persoon1);
        nl.bzk.brp.model.dashboard.Adres nieuwAdres;
        if (persoonAdres == null) {
            nieuwAdres = null;
        } else {
            nieuwAdres = new nl.bzk.brp.model.dashboard.Adres(persoonAdres);
        }
        request.setAdres(nieuwAdres);

        if (persoon1 != null) {
            request.setPersoon1(new nl.bzk.brp.model.dashboard.Persoon(persoon1));
        }
        if (persoon2 != null) {
            request.setPersoon2(new nl.bzk.brp.model.dashboard.Persoon(persoon2));
        }
    }

    /**
     * @param familie de familierelatie
     * @param soort de soort betrokkenheid
     * @return alle personen uit de gegeven relatie met de gegeven soort betrokkenheid
     */
    private List<Persoon> getPersonen(final Relatie familie, final SoortBetrokkenheid soort) {
        List<Persoon> resultaat = new ArrayList<Persoon>();
        for (Betrokkenheid betrokkenheid : familie.getBetrokkenheden()) {
            if (soort.equals(betrokkenheid.getRol())) {
                resultaat.add(betrokkenheid.getBetrokkene());
            }
        }
        return resultaat;
    }

}
