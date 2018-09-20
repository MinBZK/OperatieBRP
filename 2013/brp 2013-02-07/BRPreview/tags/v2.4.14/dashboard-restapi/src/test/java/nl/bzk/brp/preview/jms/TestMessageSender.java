/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;

import nl.bzk.brp.preview.service.AdministratieveHandelingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * TODO: Add documentation
 */
public class TestMessageSender {

    @Autowired
    @Qualifier("senderJmsTemplate")
    private JmsTemplate jmsTemplate;

    @Autowired
    private Topic queue;

    public void sendMessage(final Long handelingId) {
        MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                Message message = session.createMessage();

                message.setLongProperty(AdministratieveHandelingServiceImpl.JMS_MESSAGE_ACTION_ID, handelingId);

                return message;
            }
        };

        jmsTemplate.send(queue, messageCreator);
    }
}
