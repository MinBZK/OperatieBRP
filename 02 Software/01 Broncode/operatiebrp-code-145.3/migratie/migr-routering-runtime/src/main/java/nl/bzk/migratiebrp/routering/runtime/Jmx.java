/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.routering.runtime;

import java.io.IOException;
import java.net.URISyntaxException;
import javax.jms.JMSException;

/**
 * Jmx interface.
 */
public interface Jmx {

    /**
     * De applicatie afsluiten via {@link Main#stop()}.
     */
    void afsluiten();

    /**
     * Berichten van een DLQ opnieuw aanbieden.
     * @param queueNaam queue naam (let op: NIET de DLQ naam, maar de 'gewone' queue naam)
     * @return aantal berichten
     * @throws JMSException bij JMS fouten
     * @throws URISyntaxException als geen verbinding gemaakt kan worden met de BrokerService
     * @throws IOException bij verbindingsfouten met de BrokerService
     */
    long redeliverDlq(String queueNaam) throws JMSException, IOException, URISyntaxException;
}
