/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.init.logging.runtime.service.LoggingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:synchronisatie-logging-beans-test.xml", "classpath:synchronisatie-logging-jms-test.xml"})
public class TerugConversieMessageListenerTest {

    private static final String QUERY_RESPONSE_BERICHT_FORMAT =
            "<queryResponse xmlns=\"http://www.bzk.nl/migratiebrp/SYNC/0001\"><status>Ok</status><lo3Pl>%s</lo3Pl></queryResponse>";

    @Inject
    private LoggingService service;

    @Inject
    private JmsTemplate jmsTemplate;

    @Inject
    @Named("initVullingNaarLo3QueueHornet")
    private Destination initVullingNaarLo3Queue;

    /**
     * Stuur een bericht en verwerk dit bericht. Dit bericht wordt daarna opgeslagen in de log.
     */
    @Test
    public void sendAndDiffTest() {
        assertNotNull(jmsTemplate);
        assertNotNull(initVullingNaarLo3Queue);
        final String lg01Bericht = service.zoekInitVullingLog("3832803548").getLo3Bericht();
        final String messageId = "3832803548";
        jmsTemplate.send(initVullingNaarLo3Queue, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final Message message = session.createTextMessage(String.format(QUERY_RESPONSE_BERICHT_FORMAT, lg01Bericht));
                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, messageId);
                return message;
            }
        });
        service.zoekInitVullingLog("3832803548");
    }

    /**
     * Stuur een bericht zonder message id, deze wordt genegeerd.
     */
    @Test
    public void sendWithoutMessageIdTest() {
        assertNotNull(jmsTemplate);
        assertNotNull(initVullingNaarLo3Queue);
        final String lg01Bericht = service.zoekInitVullingLog("3832803548").getLo3Bericht();

        jmsTemplate.send(initVullingNaarLo3Queue, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final Message message = session.createTextMessage(String.format(QUERY_RESPONSE_BERICHT_FORMAT, lg01Bericht));

                return message;
            }
        });
        service.zoekInitVullingLog("3832803548");
    }

    /**
     * Stuur een bericht zonder valide message id, deze wordt genegeerd.
     */
    @Test
    public void sendWithWrongMessageIdTest() {
        assertNotNull(jmsTemplate);
        assertNotNull(initVullingNaarLo3Queue);
        final String lg01Bericht = service.zoekInitVullingLog("3832803548").getLo3Bericht();

        jmsTemplate.send(initVullingNaarLo3Queue, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final Message message = session.createTextMessage(String.format(QUERY_RESPONSE_BERICHT_FORMAT, lg01Bericht));
                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, "Blaat");
                return message;
            }
        });
        service.zoekInitVullingLog("3832803548");
    }

    /**
     * Stuur een bericht zonder bestaand anr, deze wordt genegeerd.
     */
    @Test
    public void sendWithoutExistingAnrTest() {
        assertNotNull(jmsTemplate);
        assertNotNull(initVullingNaarLo3Queue);
        final String lg01Bericht = service.zoekInitVullingLog("3832803548").getLo3Bericht();

        jmsTemplate.send(initVullingNaarLo3Queue, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final Message message = session.createTextMessage(String.format(QUERY_RESPONSE_BERICHT_FORMAT, lg01Bericht));
                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, "8");
                return message;
            }
        });
        service.zoekInitVullingLog("3832803548");
    }
}
