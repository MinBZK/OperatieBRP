/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.queue;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.jms.client.HornetQJMSConnectionFactory;

/**
 * Factory.
 */
public final class Drivers {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final Map<String, DriverLoader> CONTEXTS;

    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_HORNETQ_PORT = 5445;
    private static final int DEFAULT_ACTIVEMQ_PORT = 61_616;

    static {
        CONTEXTS = new HashMap<>();
        CONTEXTS.put("hornetq", Drivers::loadHornetQ);
        CONTEXTS.put("activemq", Drivers::loadActiveMQ);
    }

    private Drivers() {
    }

    /**
     * Create a JMS driver.
     * @param driverType type
     * @param host host
     * @param port port
     * @param quiet quiet
     * @return driver
     */
    public static Driver createDriver(final String driverType, final String host, final Integer port, final boolean quiet) {
        final DriverLoader loader = CONTEXTS.get(driverType);
        if (loader == null) {
            throw new IllegalArgumentException("Unknown driverType '" + driverType + "' (supported types=" + CONTEXTS.keySet() + ").");
        }

        Driver result = loader.loadDriver(host, port);
        result.setQuiet(quiet);

        return result;
    }

    private static Driver loadHornetQ(String host, Integer port) {
        final Map<String, Object> configuration = new HashMap<>();
        configuration.put("host", host == null ? DEFAULT_HOST : host);
        configuration.put("port", port == null ? DEFAULT_HORNETQ_PORT : port);
        TransportConfiguration transportConfiguration = new TransportConfiguration("org.hornetq.core.remoting.impl.netty.NettyConnectorFactory", configuration);

        ConnectionFactory connectionFactory = new HornetQJMSConnectionFactory(false, transportConfiguration);

        return new BaseDriver(connectionFactory);
    }

    private static Driver loadActiveMQ(String host, Integer port) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://" + (host == null ? DEFAULT_HOST : host) + ":" + (port == null ? DEFAULT_ACTIVEMQ_PORT : port));
        connectionFactory.getRedeliveryPolicy().setMaximumRedeliveries(0);

        return new BaseDriver(connectionFactory);
    }

    /**
     * Driver loader.
     */
    @FunctionalInterface
    private interface DriverLoader {
        Driver loadDriver(String host, Integer port);
    }

    /**
     * Driver based on a Jms template.
     */
    private static class BaseDriver implements Driver {

        private boolean quiet;
        private ConnectionFactory connectionFactory;

        BaseDriver(final ConnectionFactory connectionFactory) {
            this.connectionFactory = connectionFactory;
        }

        @Override
        public void setQuiet(final boolean quiet) {
            this.quiet = quiet;
        }

        private void browse(final String queue, final String selector, final QueueBrowserCallback callback) {
            Connection connection = null;
            Session session = null;
            QueueBrowser browser = null;
            try {
                connection = connectionFactory.createConnection();
                connection.start();
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                browser = selector == null ? session.createBrowser(session.createQueue(queue)) : session.createBrowser(session.createQueue(queue), selector);
                callback.browse(session, browser);
            } catch (JMSException e) {
                LOG.error("Exception during list", e);
            } finally {
                if (browser != null) {
                    try {
                        browser.close();
                    } catch (JMSException e) {
                        LOG.debug("Exception during browser close", e);
                    }
                }
                if (session != null) {
                    try {
                        session.close();
                    } catch (JMSException e) {
                        LOG.debug("Exception during session close", e);
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (JMSException e) {
                        LOG.debug("Exception during connection close", e);
                    }
                }
            }
        }

        @Override
        public void count(final String queue) {
            if (!quiet) {
                LOG.info("Counting messages on queue '{}'.", queue);
            }
            final AtomicInteger count = new AtomicInteger(0);
            browse(queue, null, (session, browser) -> {
                final Enumeration messageEnumerator = browser.getEnumeration();
                while (messageEnumerator.hasMoreElements()) {
                    messageEnumerator.nextElement();
                    count.incrementAndGet();
                }
            });
            LOG.info("{}", count.get());
        }

        @Override
        public void list(final String queue) {
            if (!quiet) {
                LOG.info("Listing messages on queue '" + queue + "'.");
            }

            browse(queue, null, (session, browser) -> {
                final Enumeration messageEnumerator = browser.getEnumeration();
                while (messageEnumerator.hasMoreElements()) {
                    final Message message = (Message) messageEnumerator.nextElement();
                    LOG.info(message.getJMSMessageID());
                }
            });
        }

        @Override
        public void show(final String queue, final String jmsId) {
            if (!quiet) {
                LOG.info("Showing message '{}' on queue '{}'.", jmsId, queue);
            }
            browse(queue, "JMSMessageID='ID:" + jmsId + "'", (session, browser) -> {
                final Enumeration messageEnumerator = browser.getEnumeration();
                if (messageEnumerator.hasMoreElements()) {
                    final Message message = (Message) messageEnumerator.nextElement();
                    LOG.info(readMessageContents(message));
                } else {
                    if (!quiet) {
                        throw new IllegalStateException("No messages returned for JMSMessageID.");
                    }
                }

                if (messageEnumerator.hasMoreElements()) {
                    throw new IllegalStateException("Multiple messages returned for JMSMessageID.");
                }
            });
        }

        private String readMessageContents(final Message message) throws JMSException {
            if (message instanceof TextMessage) {
                return ((TextMessage) message).getText();
            } else {
                throw new IllegalArgumentException("Unknown message type");
            }
        }
    }

    /**
     * Queue browser callback.
     */
    private interface QueueBrowserCallback {
        void browse(Session session, QueueBrowser browser) throws JMSException;
    }


}
