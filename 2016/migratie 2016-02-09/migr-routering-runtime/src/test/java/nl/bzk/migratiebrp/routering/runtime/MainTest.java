/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.routering.runtime;

import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.management.JMException;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.SessionCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-embedded-database.xml" })
public class MainTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Before
    public void start() {
        Main.main(null);
    }

    @Test
    public void test() throws InterruptedException {
        Assert.assertNotNull(Main.getContext());

        for (int i = 13; i > 0; i--) {
            LOGGER.info("Sleeping for " + i + " seconds...");
            Thread.sleep(1000);
        }
    }

    @Test
    public void testRedeliverDlq() throws Exception {
        Assert.assertNotNull(Main.getContext());

        // Connection
        final ApplicationContext context = Main.getContext();
        final BrokerService broker = context.getBean("routeringCentrale", BrokerService.class);
        final ActiveMQConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(broker.getTransportConnectors().get(0).getConnectUri() + "?jms.prefetchPolicy.all=1");
        // final JmsTransactionManager transactionManager = new JmsTransactionManager(connectionFactory);

        final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setSessionTransacted(true);

        // Admin connection
        final ActiveMQConnectionFactory adminConnectionFactory =
                new ActiveMQConnectionFactory(broker.getTransportConnectors().get(0).getConnectUri() + "?jms.prefetchPolicy.all=1");
        adminConnectionFactory.setUserName("admin");
        adminConnectionFactory.setPassword("admin");

        final JmsTemplate adminJmsTemplate = new JmsTemplate(adminConnectionFactory);
        adminJmsTemplate.setSessionTransacted(true);

        System.out.println("START");
        browse(adminJmsTemplate, "sync.verzoek");
        browse(adminJmsTemplate, "sync.verzoek.dlq");

        // Bericht op 'sync.verzoek' zetten
        jmsTemplate.send("sync.verzoek", new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                return session.createTextMessage("Blablabla");
            }
        });

        System.out.println("BERICHT OP QUEUE GEZET");
        browse(adminJmsTemplate, "sync.verzoek");
        browse(adminJmsTemplate, "sync.verzoek.dlq");

        // Falen, falen en falen totdat het bericht er niet meer is (en hopelijk op de DLQ staat)
        final AtomicInteger counter = new AtomicInteger(0);
        boolean done = false;
        while (!done) {
            try {
                jmsTemplate.execute(new SessionCallback<Object>() {
                    @Override
                    public Object doInJms(final Session session) throws JMSException {
                        final Destination destination = jmsTemplate.getDestinationResolver().resolveDestinationName(session, "sync.verzoek", false);
                        final MessageConsumer consumer = session.createConsumer(destination, null);

                        final Message message = consumer.receive(1000);
                        System.out.println("Message: " + message);
                        if (message == null) {
                            return null;
                        }

                        counter.incrementAndGet();
                        session.rollback();
                        throw new JMSException("Bericht ontvangen");
                    }
                }, true);
                done = true;
            } catch (final JmsException e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }

        System.out.println("BERICHT 'VERWERKT'");
        browse(adminJmsTemplate, "sync.verzoek");
        browse(adminJmsTemplate, "sync.verzoek.dlq");

        // Check queue leeg
        jmsTemplate.setReceiveTimeout(100);
        Assert.assertNull("Geen bericht verwacht op SYNC.VERZOEK, maar wel ontvangen", jmsTemplate.receive("sync.verzoek"));
        Assert.assertEquals("Aantal herhalingen niet correct", 7, counter.get());

        // Redeliver DLQ
        final Jmx jmx = new JmxImpl();
        jmx.redeliverDlq("sync.verzoek");

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

    @After
    public void shutdown() throws IOException, JMException {
        Main.stop();
    }

    private void browse(final JmsTemplate jmsTemplate, final String queue) {
        final AtomicInteger counter = new AtomicInteger();
        jmsTemplate.browse(queue, new BrowserCallback<Void>() {
            @Override
            public Void doInJms(final Session session, final QueueBrowser browser) throws JMSException {
                final Enumeration e = browser.getEnumeration();
                while (e.hasMoreElements()) {
                    final Object message = e.nextElement();
                    System.out.println("Message on " + browser.getQueue() + ": " + message);
                    counter.getAndIncrement();
                }
                return null;
            }
        });
        System.out.println(counter.get() + " bericten op " + queue);
    }
}
