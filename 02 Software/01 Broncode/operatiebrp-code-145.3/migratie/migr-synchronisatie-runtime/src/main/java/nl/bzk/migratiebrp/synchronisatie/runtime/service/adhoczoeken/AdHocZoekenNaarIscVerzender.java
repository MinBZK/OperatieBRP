/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.adhoczoeken;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import nl.bzk.migratiebrp.bericht.model.Bericht;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Verzender van AdHoc zoek antwoorden naar ISC.
 */
@Component
class AdHocZoekenNaarIscVerzender {

    private final JmsTemplate jmsTemplate;
    private final Destination destination;

    /**
     * Constructor.
     * @param jmsTemplate de jmstemplate voor het versturen van berichten
     * @param destination waar de berichten naar toe moeten worden gestuurd
     */
    @Inject
    AdHocZoekenNaarIscVerzender(@Named("queueJmsTemplate") final JmsTemplate jmsTemplate,
                                @Named("queueSyncAntwoord") final Destination destination) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }

    /**
     * Verstuur het antwoord naar Isc.
     * @param bericht het te versturen bericht
     */
    void verstuurAdHocZoekenAntwoord(final Bericht bericht) {
        jmsTemplate.send(destination, new AdHocZoekAntwoordNaarIscMessageCreator(bericht));
    }
}
