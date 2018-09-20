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
import nl.bzk.brp.business.dto.bijhouding.CorrectieAdresBericht;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.dashboard.Adres;
import nl.bzk.brp.model.dashboard.AdresCorrectieBerichtRequest;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;


/**
 * Notificator implementatie voor een verhuisbericht.
 */
public class AdrescorrectieNotificator extends
        AbstractDashboardNotificator<CorrectieAdresBericht, AdresCorrectieBerichtRequest>
{

    @Override
    protected boolean kanVerwerken(final BijhoudingsBericht bericht) {
        return bericht instanceof CorrectieAdresBericht;
    }

    @Override
    protected AdresCorrectieBerichtRequest creeerDashboardRequest(final CorrectieAdresBericht bericht,
            final BerichtContext context)
    {
        return new AdresCorrectieBerichtRequest();
    }

    @Override
    protected void voltooiDashboardRequest(final AdresCorrectieBerichtRequest request,
            final CorrectieAdresBericht bericht, final BerichtContext context, final BerichtResultaat resultaat)
    {
        List<Adres> adressen = new ArrayList<Adres>();
        request.setAdressen(adressen);
        for (Actie actie : bericht.getBrpActies()) {
            voegActieToeAanRequest(request, actie);
        }
    }

    /**
     * Lees de data uit de gegeven actie en voeg deze toe aan het gegeven request.
     *
     * @param request het request dat uitgebreidt moet worden met data uit de actie
     * @param actie de actie die toegevoegd moet vorden
     */
    private void voegActieToeAanRequest(final AdresCorrectieBerichtRequest request, final Actie actie) {
        final List<RootObject> rootObjecten = actie.getRootObjecten();

        Persoon persoon = laadPersoon(rootObjecten, 0);
        request.setPersoon(new nl.bzk.brp.model.dashboard.Persoon(persoon));

        Persoon persoonBericht = (Persoon) rootObjecten.get(0);
        Adres adres = new Adres(persoonBericht.getAdressen().iterator().next());
        adres.setDatumAanvang(actie.getDatumAanvangGeldigheid().getWaarde());

        Datum datumEindeGeldigheid = actie.getDatumEindeGeldigheid();
        Integer datumEinde;
        if (datumEindeGeldigheid == null) {
            datumEinde = null;
        } else {
            datumEinde = datumEindeGeldigheid.getWaarde();
        }
        adres.setDatumEinde(datumEinde);

        request.getAdressen().add(adres);
    }

}
