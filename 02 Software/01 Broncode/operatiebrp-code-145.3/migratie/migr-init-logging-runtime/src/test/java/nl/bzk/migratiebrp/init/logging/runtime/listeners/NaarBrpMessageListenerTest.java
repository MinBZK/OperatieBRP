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
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.init.logging.runtime.service.LoggingService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:synchronisatie-logging-beans-test.xml", "classpath:synchronisatie-logging-jms-test.xml"})
public class NaarBrpMessageListenerTest {

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
        final SyncBericht bericht = new SynchroniseerNaarBrpAntwoordBericht();
        bericht.setCorrelationId(correlationId);
        // correlationId, "Fout opgetreden PRE001");

        jmsTemplate.send(initVullingNaarBrpResponseQueue, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {

                try {
                    final Message message = session.createTextMessage(bericht.format());
                    message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, "123465789");
                    message.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, correlationId);
                    return message;
                } catch (final BerichtInhoudException exceptie) {
                    return null;
                }
            }
        });
        Assert.assertNotNull(service.zoekInitVullingLog(correlationId));
    }

    /**
     * Stuur een bericht zonder message id, deze wordt genegeerd.
     */
    @Test
    public void sendWithoutMessageIdTest() {
        assertNotNull(jmsTemplate);
        assertNotNull(initVullingNaarBrpResponseQueue);
        final String correlationId = "3832803548";
        // final SyncBericht bericht = new SynchronisatieFoutBericht(correlationId, "Fout opgetreden PRE001");
        final SyncBericht bericht = new SynchroniseerNaarBrpAntwoordBericht();
        bericht.setCorrelationId(correlationId);

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
        Assert.assertNotNull(service.zoekInitVullingLog("3832803548"));
    }
}
