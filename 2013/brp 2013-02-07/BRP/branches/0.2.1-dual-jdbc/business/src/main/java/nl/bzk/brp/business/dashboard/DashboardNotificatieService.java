/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dashboard;

import java.net.URI;

import nl.bzk.brp.model.dashboard.NotificatieRequest;
import org.springframework.scheduling.annotation.Async;


/**
 * Dashboard notificatie service interface, welke een asynchrone call stuurt naar de RESTAPI van het dashboard.
 * Deze service is toegevoegd omdat de Async annotatie alleen werkt bij een component dat via component scan is
 * gecreeerd.
 */
public interface DashboardNotificatieService {

    /**
     * Verstuurt de notificatie asynchroon naar het dashboard.
     * Voor het uitvoeren van deze methode wordt een aparte thread gebruikt.
     *
     * @param notificatieUrl URL van de dashboard-restapi service
     * @param request de notificatie
     */
    @Async
    void notificeerDashboard(final URI notificatieUrl, final NotificatieRequest request);

}
