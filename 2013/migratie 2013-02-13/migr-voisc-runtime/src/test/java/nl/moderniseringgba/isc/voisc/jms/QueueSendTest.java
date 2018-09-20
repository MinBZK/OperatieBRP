/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.jms;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import nl.moderniseringgba.isc.esb.message.JMSConstants;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("isc-voisc-jms-test.xml")
public class QueueSendTest {

    @Inject
    private JmsTemplate jmsTemplate;

    @Inject
    @Named("vospgVerzendenQueue")
    private Destination vospgVerzendenQueue;

    @Inject
    @Named("vospgOntvangstQueue")
    private Destination vospgOntvangstQueue;

    /**
     * Stuur een bericht en verwerk dit bericht. Dit bericht wordt daarna opgeslagen in de log.
     */
    @Test
    public void sendFromESBTest() {
        assertNotNull(jmsTemplate);
        assertNotNull(vospgVerzendenQueue);
        final String messageId = "AA0000000099";
        final String correlatieId = "000000000006";
        final String bericht = "00000000Vb0100020Dit was een vraagjey";

        jmsTemplate.send(vospgVerzendenQueue, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final Message message = session.createTextMessage(bericht);
                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, messageId);
                message.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, correlatieId);
                message.setStringProperty(JMSConstants.BERICHT_ORIGINATOR, "1904");
                message.setStringProperty(JMSConstants.BERICHT_RECIPIENT, "1902");

                return message;
            }
        });
    }

    @Test
    public void receiveFromESBTest() {
        assertNotNull(jmsTemplate);
        assertNotNull(vospgOntvangstQueue);

        Message message = jmsTemplate.receive(vospgOntvangstQueue);
        while (message != null) {
            try {
                final String jmsBericht = ((TextMessage) message).getText();
                System.out.println(jmsBericht);
            } catch (final JMSException e) {
                e.printStackTrace();
            }
            message = jmsTemplate.receive(vospgOntvangstQueue);
        }
    }
}
