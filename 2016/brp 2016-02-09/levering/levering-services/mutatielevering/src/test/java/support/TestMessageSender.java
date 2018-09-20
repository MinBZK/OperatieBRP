/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package support;

import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import nl.bzk.brp.model.internbericht.AdministratieveHandelingVerwerktOpdracht;
import nl.bzk.brp.serialisatie.JsonStringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Hulp class voor het verzenden van JMS berichten met een administratieve handeling id.
 */
public class TestMessageSender {

    @Autowired
    @Qualifier("senderJmsTemplate")
    private JmsTemplate jmsTemplate;

    @Autowired
    @Named("AHQueue")
    private Queue queue;

    private final JsonStringSerializer<AdministratieveHandelingVerwerktOpdracht> serialiseerder = new
        JsonStringSerializer<>(AdministratieveHandelingVerwerktOpdracht.class);

    /**
     * Zet een message in een queue/topic.
     *
     * @param opdracht De opdracht.
     */
    public final void sendMessage(final AdministratieveHandelingVerwerktOpdracht opdracht) {
        final String opdrachtString = serialiseerder.serialiseerNaarString(opdracht);

        final MessageCreator messageCreator = new MessageCreator() {
            @Override
            public final Message createMessage(final Session session) throws JMSException {
                return session.createTextMessage(opdrachtString);
            }
        };

        jmsTemplate.send(queue, messageCreator);
    }
}
