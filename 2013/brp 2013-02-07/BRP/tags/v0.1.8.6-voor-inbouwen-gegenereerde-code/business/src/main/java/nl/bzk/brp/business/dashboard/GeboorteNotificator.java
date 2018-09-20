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
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.dashboard.GeboorteBerichtRequest;
import nl.bzk.brp.model.dashboard.VerhuisBerichtRequest;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;


/**
 * Notificator implementatie voor een geboortebericht.
 */
public class GeboorteNotificator extends
        AbstractDashboardNotificator<InschrijvingGeboorteBericht, GeboorteBerichtRequest>
{

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    @Override
    protected void verzendNotificatie(final GeboorteBerichtRequest request) {
        getRestTemplate().postForEntity(getDashboardUri(), request, VerhuisBerichtRequest.class);
    }

    /**
     * Bepaal het woonadres van de nieuwgeborene voor het notificatie bericht.
     *
     * @param resultaat het resultaat van de bijhouding
     * @param nieuwgeborene de nieuwgeborene uit de database
     * @param ouder1 de eerste ouder uit het inkomend bericht
     * @param ouder2 de tweede ouder uit het inkomend bericht
     * @return het adres van de nieuwgeborene indien aanwezig, anders het adres van de eerste vrouwelijke ouder
     */
    private PersoonAdres getPersoonAdres(final BerichtResultaat resultaat, final Persoon nieuwgeborene,
            final Persoon ouder1, final Persoon ouder2)
    {
        PersoonAdres adres;
        if (!resultaat.bevatVerwerkingStoppendeFouten() && getPersoonAdres(nieuwgeborene) != null) {
            adres = getPersoonAdres(nieuwgeborene);
        } else {
            PersoonAdres adresOuder1 = getPersoonAdres(ouder1);
            PersoonAdres adresOuder2 = getPersoonAdres(ouder2);
            if (adresOuder1 != null && adresOuder2 != null) {
                if (!isVrouw(ouder1) && isVrouw(ouder2)) {
                    adres = adresOuder2;
                } else {
                    adres = adresOuder1;
                }
            } else if (adresOuder1 != null) {
                adres = adresOuder1;
            } else {
                adres = adresOuder2;
            }
        }
        return adres;
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
        return bericht instanceof InschrijvingGeboorteBericht;
    }

    @Override
    protected GeboorteBerichtRequest creeerDashboardRequest(final InschrijvingGeboorteBericht bericht,
            final BerichtContext context)
    {
        return new GeboorteBerichtRequest();
    }

    @Override
    protected void voltooiDashboardRequest(final GeboorteBerichtRequest request,
            final InschrijvingGeboorteBericht bericht, final BerichtContext context, final BerichtResultaat resultaat)
    {
        Relatie familie = (Relatie) bericht.getBrpActies().get(0).getRootObjecten().get(0);
        Persoon nieuwgeborene = getPersonen(familie, SoortBetrokkenheid.KIND).get(0);
        List<Persoon> ouders = getPersonen(familie, SoortBetrokkenheid.OUDER);
        Persoon ouder1 = laadPersoon(ouders, 0);
        Persoon ouder2 = laadPersoon(ouders, 1);

        request.setNieuwgeborene(new nl.bzk.brp.model.dashboard.Persoon(nieuwgeborene));
        verrijk(request.getNieuwgeborene());

        PersoonAdres persoonAdres = getPersoonAdres(resultaat, nieuwgeborene, ouder1, ouder2);
        nl.bzk.brp.model.dashboard.Adres nieuwAdres;
        if (persoonAdres == null) {
            nieuwAdres = null;
        } else {
            nieuwAdres = new nl.bzk.brp.model.dashboard.Adres(persoonAdres);
        }
        request.setAdresNieuwgeborene(nieuwAdres);

        if (ouder1 != null) {
            request.setOuder1(new nl.bzk.brp.model.dashboard.Persoon(ouder1));
        }
        if (ouder2 != null) {
            request.setOuder2(new nl.bzk.brp.model.dashboard.Persoon(ouder2));
        }
    }

    /**
     * Vul de ontbrekende gegevens uit het request bericht aan met data uit de database indien ze afleidbaar zijn.
     * @param persoon persoon uit request bericht
     */
    private void verrijk(final nl.bzk.brp.model.dashboard.Persoon persoon) {
        if (persoon.getGemeenteGeboorte() != null && persoon.getGemeenteGeboorte().getCode() != null) {
            Partij gemeente = referentieDataRepository.vindGemeenteOpCode(
                    new Gemeentecode(Short.parseShort(persoon.getGemeenteGeboorte().getCode())));
            if (gemeente != null) {
                persoon.getGemeenteGeboorte().setNaam(gemeente.getNaam().getWaarde());
            }
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
