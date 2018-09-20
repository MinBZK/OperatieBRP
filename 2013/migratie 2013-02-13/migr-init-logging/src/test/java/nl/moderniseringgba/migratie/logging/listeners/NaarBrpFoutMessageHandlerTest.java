/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.logging.listeners;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.JMSConstants;
import nl.moderniseringgba.isc.esb.message.sync.SyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.moderniseringgba.migratie.logging.service.LoggingService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:synchronisatie-logging-beans-test.xml",
        "classpath:synchronisatie-logging-jms-test.xml" })
public class NaarBrpFoutMessageHandlerTest {

    @Inject
    private LoggingService service;

    @Inject
    private JmsTemplate jmsTemplate;

    @Inject
    @Named("initVullingNaarBrpResponseQueueHornet")
    private Destination initVullingNaarBrpResponseQueue;

    /**
     * Stuur een bericht en verwerk dit bericht. Dit bericht wordt daarna opgeslagen in de log.
     */
    @Test
    public void sendAndDiffTest() {
        assertNotNull(jmsTemplate);
        assertNotNull(initVullingNaarBrpResponseQueue);
        final String correlationId = "3832803548";
        final SyncBericht bericht =
                new SynchroniseerNaarBrpAntwoordBericht(correlationId, StatusType.FOUT, "Fout opgetreden PRE001");

        jmsTemplate.send(initVullingNaarBrpResponseQueue, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {

                try {
                    final Message message = session.createTextMessage(bericht.format());
                    message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, correlationId);
                    return message;
                } catch (final BerichtInhoudException exceptie) {
                    return null;
                }
            }
        });
        service.findLog(Long.valueOf(3832803548L));
    }

    /**
     * Stuur een bericht zonder message id, deze wordt genegeerd.
     */
    @Test
    public void sendWithoutMessageIdTest() {
        assertNotNull(jmsTemplate);
        assertNotNull(initVullingNaarBrpResponseQueue);
        final String correlationId = "3832803548";
        final SyncBericht bericht =
                new SynchroniseerNaarBrpAntwoordBericht(correlationId, StatusType.FOUT, "Fout opgetreden PRE001");

        jmsTemplate.send(initVullingNaarBrpResponseQueue, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {

                try {
                    final Message message = session.createTextMessage(bericht.format());

                    return message;
                } catch (final BerichtInhoudException exceptie) {
                    return null;
                }

            }
        });
        service.findLog(Long.valueOf(3832803548L));
    }
}
