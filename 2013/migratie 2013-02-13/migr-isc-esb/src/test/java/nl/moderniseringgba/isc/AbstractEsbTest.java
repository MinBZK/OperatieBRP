/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import nl.moderniseringgba.isc.esb.message.JMSConstants;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Basis voor een test die loopt via de connectivity van de ESB.
 * 
 * Let op: **&#47;*EsbTest.java is geexclude als automatische unittest in maven.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:isc-test-infra.xml")
public abstract class AbstractEsbTest {

    @Inject
    private JmsTemplate jmsTemplate;

    @Inject
    @Named("brpOntvangstQueue")
    private Destination brpOntvangst;

    @Inject
    @Named("brpVerzendenQueue")
    private Destination brpVerzenden;

    @Inject
    @Named("mviOntvangstQueue")
    private Destination mviOntvangst;

    @Inject
    @Named("mviVerzendenQueue")
    private Destination mviVerzenden;

    @Inject
    @Named("vospgOntvangstQueue")
    private Destination vospgOntvangst;

    @Inject
    @Named("vospgVerzendenQueue")
    private Destination vospgVerzenden;

    @Inject
    @Named("syncRequestQueue")
    private Destination syncRequest;

    @Inject
    @Named("syncResponseQueue")
    private Destination syncResponse;

    protected void verstuurBrpBericht(final Bericht bericht) {
        sendMessage(brpOntvangst, bericht);
    }

    protected void verstuurVospgBericht(final Bericht bericht) {
        sendMessage(vospgOntvangst, bericht);
    }

    protected void verstuurMviBericht(final Bericht bericht) {
        sendMessage(mviOntvangst, bericht);
    }

    protected void verstuurSyncRequest(final Bericht bericht) {
        sendMessage(syncRequest, bericht);
    }

    protected void verstuurSyncBericht(final Bericht bericht) {
        sendMessage(syncResponse, bericht);
    }

    protected Bericht ontvangBrpBericht() {
        return receiveMessage(brpVerzenden, null);
    }

    protected Bericht ontvangBrpBericht(final String correlationId) {
        return receiveMessage(brpVerzenden, JMSConstants.CORRELATIE_REFERENTIE + "  = '" + correlationId + "'");
    }

    protected Bericht ontvangVospgBericht() {
        return receiveMessage(vospgVerzenden, null);
    }

    protected Bericht ontvangVospgBericht(final String correlationId) {
        return receiveMessage(vospgVerzenden, JMSConstants.CORRELATIE_REFERENTIE + "  = '" + correlationId + "'");
    }

    protected Bericht ontvangMviBericht() {
        return receiveMessage(mviVerzenden, null);
    }

    protected Bericht ontvangMviBericht(final String correlationId) {
        return receiveMessage(mviVerzenden, JMSConstants.CORRELATIE_REFERENTIE + "  = '" + correlationId + "'");
    }

    protected Bericht ontvangSyncBericht() {
        return receiveMessage(syncRequest, null);
    }

    protected Bericht ontvangSyncBericht(final String correlationId) {
        return receiveMessage(syncRequest, JMSConstants.CORRELATIE_REFERENTIE + "  = '" + correlationId + "'");
    }

    private void sendMessage(final Destination destination, final Bericht bericht) {
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final Message message = session.createTextMessage(bericht.getInhoud());
                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, bericht.getMessageId());
                message.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, bericht.getCorrelatieId());
                message.setStringProperty(JMSConstants.BERICHT_ORIGINATOR, bericht.getOriginator());
                message.setStringProperty(JMSConstants.BERICHT_RECIPIENT, bericht.getRecipient());

                return message;
            }
        });
    }

    @Before
    public void clearQueues() {
        System.out.println("Opschonen queues");

        clearQueue(brpVerzenden);
        clearQueue(brpOntvangst);
        clearQueue(mviVerzenden);
        clearQueue(mviOntvangst);
        clearQueue(vospgVerzenden);
        clearQueue(vospgOntvangst);
        clearQueue(syncResponse);
        clearQueue(syncRequest);
    }

    private void clearQueue(final Destination destination) {
        final long timeout = jmsTemplate.getReceiveTimeout();

        try {
            jmsTemplate.setReceiveTimeout(250);
            while (jmsTemplate.receive(destination) != null) {
                System.out.println("Cleared message");
            }
        } finally {
            jmsTemplate.setReceiveTimeout(timeout);
        }

    }

    private Bericht receiveMessage(final Destination destination, final String messageSelector) {
        final Message message = jmsTemplate.receiveSelected(destination, messageSelector);

        if (message == null) {
            return null;
        }

        final Bericht bericht = new Bericht();

        try {
            bericht.setMessageId(message.getStringProperty(JMSConstants.BERICHT_REFERENTIE));
            bericht.setCorrelatieId(message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE));
            bericht.setOriginator(message.getStringProperty(JMSConstants.BERICHT_ORIGINATOR));
            bericht.setRecipient(message.getStringProperty(JMSConstants.BERICHT_RECIPIENT));

            if (message instanceof TextMessage) {
                bericht.setInhoud(((TextMessage) message).getText());
            } else {
                throw new RuntimeException("Onbekend bericht type: " + message.getClass().getName());
            }
        } catch (final JMSException e) {
            throw new RuntimeException(e);
        }

        return bericht;
    }

    protected String readResourceAsString(final String resource) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        final InputStream is = this.getClass().getResourceAsStream(resource);
        final byte[] buffer = new byte[4096];
        int length;

        try {
            while ((length = is.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return new String(baos.toByteArray());
    }

    public static class Bericht {
        private String messageId;
        private String correlatieId;
        private String originator;
        private String recipient;
        private String inhoud;

        public Bericht() {
        }

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(final String messageId) {
            this.messageId = messageId;
        }

        public String getCorrelatieId() {
            return correlatieId;
        }

        public void setCorrelatieId(final String correlatieId) {
            this.correlatieId = correlatieId;
        }

        public String getOriginator() {
            return originator;
        }

        public void setOriginator(final String originator) {
            this.originator = originator;
        }

        public String getRecipient() {
            return recipient;
        }

        public void setRecipient(final String recipient) {
            this.recipient = recipient;
        }

        public String getInhoud() {
            return inhoud;
        }

        public void setInhoud(final String inhoud) {
            this.inhoud = inhoud;
        }

        @Override
        public String toString() {
            return "Bericht [messageId=" + messageId + ", correlatieId=" + correlatieId + ", originator="
                    + originator + ", recipient=" + recipient + ", inhoud=" + inhoud + "]";
        }

    }
}
