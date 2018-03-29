/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.notificatie;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.springframework.jms.core.JmsTemplate;

/**
 * MessageHandler voor BRP notificaties.
 */
public class NotificatieMessageHandler implements MessageListener {

    private final Destination destination;
    private final JmsTemplate jmsTemplate;

    /**
     * Constructor.
     * @param destination destination (queue sync antwoord)
     * @param connectionFactory queue connection factory
     */
    @Inject
    public NotificatieMessageHandler(@Named("queueNotificatie") final Destination destination,
                                     @Named("queueConnectionFactory") final ConnectionFactory connectionFactory) {
        this.destination = destination;
        this.jmsTemplate = new JmsTemplate(connectionFactory);
    }
    @Override
    public void onMessage(final Message message) {
        jmsTemplate.send(destination, session -> message);
    }

}
