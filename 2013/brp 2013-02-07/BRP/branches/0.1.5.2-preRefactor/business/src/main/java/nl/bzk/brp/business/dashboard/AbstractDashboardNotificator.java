/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dashboard;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.ResultaatCode;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.dashboard.AbstractBerichtRequest;
import nl.bzk.brp.model.dashboard.BerichtKenmerken;
import nl.bzk.brp.model.dashboard.Gemeente;
import nl.bzk.brp.model.dashboard.Melding;
import nl.bzk.brp.model.dashboard.MeldingSoort;
import nl.bzk.brp.model.dashboard.Verwerking;
import nl.bzk.brp.model.dashboard.VerwerkingStatus;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.OverruleMelding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;


/**
 * Superclass van notificators voor het verzenden van notificatieberichten naar de Dashboard applicatie.
 * Deze notificators moeten in de spring configuratie opgenomen worden bij de {@link DashboardNotificatieStap}.
 *
 * @param <T> Het inkomend berichttype dat de notificator moet kunnen verwerken
 * @param <U> Het uitgaand berichttype dat de notificator moet versturen naar de Dashboard applicatie
 */
public abstract class AbstractDashboardNotificator<T extends AbstractBijhoudingsBericht,
        U extends AbstractBerichtRequest>
{

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDashboardNotificator.class);

    private URI                 dashboardUri;

    @Inject
    private RestTemplate        restTemplate;

    @Inject
    private PersoonRepository   persoonRepository;

    private final ThreadLocal<AbstractBerichtRequest> berichtRequest = new ThreadLocal<AbstractBerichtRequest>();

    /**
     * Controleer of een inkomend bericht verwerkt kan worden door deze notificator.
     *
     * @param bericht het inkomend bericht waarvoor een notificator wordt gezocht
     * @return true als dit de notificator is voor het gegeven bericht
     */
    protected abstract boolean kanVerwerken(AbstractBijhoudingsBericht bericht);

    /**
     * Creeer een notificatie bericht met de gegevens zoals bekend voorafgaand aan de bijhouding.
     *
     * @param bericht het inkomend bericht
     * @param context de context rond een BRP bericht
     * @return het notificatie bericht voor de Dashboard applicatie
     */
    protected abstract U creeerDashboardRequest(final T bericht, final BerichtContext context);

    /**
     * Voltooi een notificatie bericht met de gegevens zoals bekend na afloop van de bijhouding.
     *
     * @param berichtRequest het notificatie bericht voor de Dashboard applicatie
     * @param bericht het inkomend bericht
     * @param context de context rond een BRP bericht
     * @param resultaat het resultaat van de bijhouding
     */
    protected abstract void voltooiDashboardRequest(final U berichtRequest, final T bericht, final BerichtContext context,
            BerichtResultaat resultaat);

    /**
     * Mogelijkheid om de database te lezen voorafgaand aan de bijhouding.
     *
     * @param bericht het inkomend bericht
     * @param context de context rond een BRP bericht
     */
    @SuppressWarnings("unchecked")
    public void voorbereiden(final AbstractBijhoudingsBericht bericht, final BerichtContext context) {
        AbstractBerichtRequest request = null;
        try {
            request = creeerDashboardRequest((T) bericht, context);

            BerichtKenmerken kenmerken = new BerichtKenmerken();
            Gemeente verzendendePartij = new Gemeente(context.getPartij());

            kenmerken.setVerzendendePartij(verzendendePartij);
            kenmerken.setBurgerZakenModuleNaam(bericht.getBerichtStuurgegevens().getApplicatie());
            kenmerken.setPrevalidatie(bericht.getBerichtStuurgegevens().isPrevalidatieBericht());
            request.setKenmerken(kenmerken);

        } catch (final RuntimeException e) {
            LOGGER.warn("opbouw bericht voor notificatie van bijhouding aan dashboard mislukt", e);
        } finally {
            berichtRequest.set(request);
        }
    }

    /**
     * Stuur een notificatiebericht naar de Dashboard applicatie.
     *
     * @param bericht het inkomend bericht
     * @param context de context rond een BRP bericht
     * @param resultaat het resultaat van de bijhouding
     */
    @SuppressWarnings("unchecked")
    public void notificeerDashboard(final AbstractBijhoudingsBericht bericht, final BerichtContext context,
            final BerichtResultaat resultaat)
    {
        AbstractBerichtRequest request;
        try {
            request = berichtRequest.get();
            voltooiDashboardRequest((U) request, (T) bericht, context, resultaat);

            BerichtKenmerken kenmerken = new BerichtKenmerken();
            Gemeente verzendendePartij = new Gemeente(context.getPartij());

            kenmerken.setVerzendendePartij(verzendendePartij);
            kenmerken.setBurgerZakenModuleNaam(bericht.getBerichtStuurgegevens().getApplicatie());
            kenmerken.setPrevalidatie(bericht.getBerichtStuurgegevens().isPrevalidatieBericht());
            request.setKenmerken(kenmerken);

            Verwerking verwerking = new Verwerking();
            verwerking.setVerwerkingsmoment(Calendar.getInstance());
            if (ResultaatCode.GOED.equals(resultaat.getResultaatCode())) {
                verwerking.setStatus(VerwerkingStatus.GESLAAGD);
            } else {
                verwerking.setStatus(VerwerkingStatus.MISLUKT);
            }
            request.setVerwerking(verwerking);

            setMeldingen(resultaat, request);

        } catch (final RuntimeException e) {
            LOGGER.warn("opbouw bericht voor notificatie van bijhouding aan dashboard mislukt", e);
            request = null;
        } finally {
            berichtRequest.remove();
        }

        if (request != null) {
            try {
                verzendNotificatie((U) request);
            } catch (final RuntimeException e) {
                LOGGER.warn(String.format("notificatie van bijhouding aan dashboard mislukt:%nURI=%s%nResultaat=%s",
                        getDashboardUri().toASCIIString(), e.getMessage()));
            }
        }
    }

    private void setMeldingen(final BerichtResultaat resultaat, final AbstractBerichtRequest request) {
        if (resultaat.getMeldingen() != null) {
            Set<MeldingCode> overrules = getOverrules(resultaat.getOverruleMeldingen());
            request.setMeldingen(getMeldingen(resultaat.getMeldingen(), overrules, request));
        }
    }

    private List<Melding> getMeldingen(final List<nl.bzk.brp.model.validatie.Melding> meldingen,
        final Set<MeldingCode> overrules, final AbstractBerichtRequest request)
    {
        List<Melding> resultaat = new ArrayList<Melding>();
        for (nl.bzk.brp.model.validatie.Melding brpMelding : meldingen) {
            Melding melding = new Melding(brpMelding);
            if (overrules.contains(brpMelding.getCode())) {
                melding.setSoort(MeldingSoort.GENEGEERDE_FOUT);
            }
            resultaat.add(melding);
        }
        return resultaat;
    }

    private Set<MeldingCode> getOverrules(final List<OverruleMelding> overruleMeldingen) {
        Set<MeldingCode> overrules = new HashSet<MeldingCode>();
        if (overruleMeldingen != null) {
            for (OverruleMelding overruleMelding : overruleMeldingen) {
                overrules.add(MeldingCode.valueOf(overruleMelding.getCode()));
            }
        }
        return overrules;
    }

    /**
     * Verzend een notificatie naar de Dashboard applicatie.
     *
     * @param request het notificatie bericht
     */
    protected abstract void verzendNotificatie(U request);

    public URI getDashboardUri() {
        return dashboardUri;
    }

    public void setDashboardUri(final URI dashboardUri) {
        this.dashboardUri = dashboardUri;
    }

    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }

    protected PersoonRepository getPersoonRepository() {
        return persoonRepository;
    }

    /**
     * Haal de persoon op uit de database die overeen komt met een persoon uit het bericht.
     *
     * @param personen lijst met personen uit het inkomend bericht
     * @param index de index van een personen uit de gegeven lijst
     * @return de persoon zoals die opgeslagen is in de database of null als deze niet bestaat
     */
    protected Persoon laadPersoon(final List<?> personen, final int index) {
        Persoon resultaat;
        if (personen.size() <= index) {
            resultaat = null;
        } else {
            Persoon persoonIn = (Persoon) personen.get(index);
            String burgerservicenummer = persoonIn.getIdentificatienummers().getBurgerservicenummer();
            resultaat = getPersoonRepository().haalPersoonOpMetBurgerservicenummer(burgerservicenummer);
        }
        return resultaat;
    }

}
