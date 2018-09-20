/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dashboard;

import java.net.URI;
import java.util.Calendar;

import javax.inject.Inject;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.ResultaatCode;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.dashboard.BerichtKenmerken;
import nl.bzk.brp.model.dashboard.Gemeente;
import nl.bzk.brp.model.dashboard.VerhuisBerichtRequest;
import nl.bzk.brp.model.dashboard.Verwerking;
import nl.bzk.brp.model.dashboard.VerwerkingStatus;
import nl.bzk.brp.model.logisch.Persoon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


/**
 * Verwerkingstap voor elk bijhoudingsbericht een notificatie stuurt naar de dashboard applicatie.
 */
public class DashboardNotificatieStap extends AbstractBerichtVerwerkingsStap<BijhoudingsBericht, BerichtResultaat> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardNotificatieStap.class);

    @Inject
    private RestTemplate        restTemplate;

    @Inject
    private PersoonRepository   persoonRepository;

    private boolean             isVerhuizingNotificatieActief;

    private URI                 dashboardUri;

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final BijhoudingsBericht bericht, final BerichtContext context,
            final BerichtResultaat resultaat)
    {
        if (isVerhuizingNotificatieActief && bericht instanceof VerhuizingBericht) {
            VerhuisBerichtRequest request = new VerhuisBerichtRequest();

            BerichtKenmerken kenmerken = new BerichtKenmerken();
            Gemeente verzendendePartij = new Gemeente(context.getPartij());

            kenmerken.setVerzendendePartij(verzendendePartij);
            kenmerken.setBurgerZakenModuleNaam(bericht.getBerichtStuurgegevens().getApplicatie());

            // TODO friso: hardcoded false omdat de BerichtStuurgegevens class nog geen prevalidatie vlag heeft
            kenmerken.setPrevalidatie(false);
            request.setKenmerken(kenmerken);

            final Verwerking verwerking = new Verwerking();
            verwerking.setVerwerkingsmoment(Calendar.getInstance());
            if (ResultaatCode.GOED.equals(resultaat.getResultaatCode())) {
                verwerking.setStatus(VerwerkingStatus.GESLAAGD);
            } else {
                verwerking.setStatus(VerwerkingStatus.MISLUKT);
            }
            request.setVerwerking(verwerking);

            final Persoon persoonIn = (Persoon) bericht.getBrpActies().get(0).getRootObjecten().get(0);

            Persoon persoon =
                persoonRepository.haalPersoonOpMetBurgerservicenummer(persoonIn.getIdentificatienummers()
                        .getBurgerservicenummer());

            nl.bzk.brp.model.dashboard.Persoon persoonUit = new nl.bzk.brp.model.dashboard.Persoon(persoon);
            request.setPersoon(persoonUit);

            nl.bzk.brp.model.dashboard.Adres nieuwAdres =
                new nl.bzk.brp.model.dashboard.Adres(persoonIn.getAdressen().iterator().next());

            // TODO friso: oudadres ophalen uit database
            request.setOudAdres(nieuwAdres);

            request.setNieuwAdres(nieuwAdres);

            try {
                ResponseEntity<VerhuisBerichtRequest> response =
                    restTemplate.postForEntity(dashboardUri, request, VerhuisBerichtRequest.class);
            } catch (Exception e) {
                LOGGER.warn(String.format("notificatie van bijhouding aan dashboard mislukt:\nURI=%s\nResultaat=%s",
                        dashboardUri.toASCIIString(), e.getMessage()));
            }
        }
        return true;
    }

    public void setVerhuizingNotificatieActief(final boolean waarde) {
        isVerhuizingNotificatieActief = waarde;
    }

    public void setDashboardUri(final URI dashboardUri) {
        this.dashboardUri = dashboardUri;
    }

}
