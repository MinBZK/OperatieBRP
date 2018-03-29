/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.routering.runtime;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class MainIT extends AbstractIT {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Test
    public void test() throws InterruptedException {
        for (int i = 13; i > 0; i--) {
            LOGGER.info("Sleeping for " + i + " seconds...");
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }

    @Test
    public void testRedeliverDlq() throws Exception {
        // Connection
        final ActiveMQConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("nio://localhost:" + portProperties.getProperty("test.routering.port") + "?jms.prefetchPolicy.all=1");
        // Geen client side redeliveries
        connectionFactory.getRedeliveryPolicy().setMaximumRedeliveries(0);


        // final JmsTransactionManager transactionManager = new
        // JmsTransactionManager(connectionFactory);

        final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setSessionTransacted(true);

        // Admin connection
        final ActiveMQConnectionFactory adminConnectionFactory =
                new ActiveMQConnectionFactory("nio://localhost:" + portProperties.getProperty("test.routering.port") + "?jms.prefetchPolicy.all=1");
        adminConnectionFactory.setUserName("admin");
        adminConnectionFactory.setPassword("admin");

        final JmsTemplate adminJmsTemplate = new JmsTemplate(adminConnectionFactory);
        adminJmsTemplate.setSessionTransacted(true);

        System.out.println("START");
        browse(adminJmsTemplate, "sync.verzoek");
        browse(adminJmsTemplate, "sync.verzoek.dlq");

        // Bericht op 'sync.verzoek' zetten
        jmsTemplate.send("sync.verzoek", (MessageCreator) session -> session.createTextMessage("Blablabla"));

        System.out.println("BERICHT OP QUEUE GEZET");
        browse(adminJmsTemplate, "sync.verzoek");
        browse(adminJmsTemplate, "sync.verzoek.dlq");

        LOGGER.info("\n\n\nFIND ME: BERICHT OP QUEUE GEZET\n\n");
        // Falen, falen en falen totdat het bericht er niet meer is (en hopelijk op de DLQ staat)
        final AtomicInteger counter = new AtomicInteger(0);
        boolean done = false;
        while (!done) {
            LOGGER.info("\n\n\nFIND ME: START\n\n");
            try {
                jmsTemplate.execute(session -> {
                    final Destination destination = jmsTemplate.getDestinationResolver().resolveDestinationName(session, "sync.verzoek", false);
                    final MessageConsumer consumer = session.createConsumer(destination, null);

                    LOGGER.info("\n\n\nFIND ME: RECEIVE\n\n");

                    final Message message = consumer.receive(3000);
                    LOGGER.info("Message: " + message);
                    if (message == null) {
                        LOGGER.info("\n\n\nFIND ME: NO MESSAGE\n\n");
                        return null;
                    }

                    counter.incrementAndGet();
                    LOGGER.info("\n\n\nFIND ME: ROLLBACK\n\n");
                    session.rollback();
                    LOGGER.info("\n\n\nFIND ME: END ROLLBACK\n\n");
                    throw new JMSException("Bericht ontvangen");
                }, true);
                done = true;
            } catch (final JmsException e) {
                LOGGER.info("Exception: " + e.getMessage());
            }
            LOGGER.info("\n\n\nFIND ME: END\n\n");
        }
        LOGGER.info("\n\n\nFIND ME: BERICHT VERWERKT\n\n");

        System.out.println("BERICHT 'VERWERKT'");
        browse(adminJmsTemplate, "sync.verzoek");
        browse(adminJmsTemplate, "sync.verzoek.dlq");

        // Check queue leeg
        jmsTemplate.setReceiveTimeout(100);
        Assert.assertNull("Geen bericht verwacht op SYNC.VERZOEK, maar wel ontvangen", jmsTemplate.receive("sync.verzoek"));
        // Aantal herhaling is 3; 1 (originele delivery) + 2 (redeliveries)
        Assert.assertEquals("Aantal herhalingen niet correct", 3, counter.get());

        // Call redeliver DLQ via JMX
        final MBeanServerConnection jmxConnection = testContext.getBean("routeringJmxConnector", MBeanServerConnection.class);
        jmxConnection.invoke(new ObjectName("nl.bzk.migratiebrp.routering:name=ROUTERING"), "redeliverDlq", new Object[]{"sync.verzoek"},
                new String[]{"java.lang.String"});

        System.out.println("REDELIVER UITGEVOERD");
        browse(adminJmsTemplate, "sync.verzoek");
        browse(adminJmsTemplate, "sync.verzoek.dlq");

        // Check bericht op queue
        jmsTemplate.setReceiveTimeout(100);
        final Message message = jmsTemplate.receive("sync.verzoek");
        Assert.assertNotNull("Verwacht bericht niet gevonden op SYNC.VERZOEK", message);
        Assert.assertEquals("Bericht inhoud niet correct", "Blablabla", ((TextMessage) message).getText());

        System.out.println("EINDE");
        browse(adminJmsTemplate, "sync.verzoek");
        browse(adminJmsTemplate, "sync.verzoek.dlq");
    }
}
