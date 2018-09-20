/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dashboard;

import java.net.URI;

import javax.inject.Inject;

import nl.bzk.brp.model.dashboard.NotificatieRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * De DashboardNotificationService zodat we een asynchrone call kunnen doen naar de dashboard notificatie rest service.
 * Deze aparte service class is nodig omdat de @Async annotatie alleen werkt binnen component scans en de bericht
 * verwerking stappen nog via xml configuratie werkt.
 */
@Service
public class DashboardNotificatieServiceImpl implements DashboardNotificatieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardNotificatieServiceImpl.class);

    @Inject
    private RestTemplate        restTemplate;

    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }

    @Override
    @Async
    public void notificeerDashboard(final URI notificatieUrl, final NotificatieRequest request) {
        try {
            restTemplate.put(notificatieUrl, request);
            LOGGER.info("Notificatie verstuurd naar Dashboard voor request: " + request);
        } catch (final RuntimeException e) {
            LOGGER.warn(String.format("notificatie van bijhouding aan dashboard mislukt:%nURI=%s%nResultaat=%s",
                    notificatieUrl.toASCIIString(), e.getMessage()));
        }
    }
}
