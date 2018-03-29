/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.Properties;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.synchronisatie.runtime.Main.Modus;
import org.dbunit.DatabaseUnitException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

public class AbstractIT {

    protected static final String ARCHIVERING_QUEUE = "archivering";
    protected static final String SYNC_VERZOEK_QUEUE = "sync.verzoek";
    protected static final String SYNC_ANTWOORD_QUEUE = "sync.antwoord";
    protected static final String IV_SYNC_VERZOEK_QUEUE = "iv.request";
    protected static final String IV_SYNC_ANTWOORD_QUEUE = "iv.resposne";
    protected static final String VOISC_ONTVANGST_QUEUE = "voisc.ontvangst";
    protected static final String VOISC_VERZENDEN_QUEUE = "voisc.verzenden";
    protected static final String PARTIJ_VERZOEK_QUEUE = "partij.verzoek";
    protected static final String PARTIJ_REGISTER_TOPIC = "partij.register";

    private static final Logger LOG = LoggerFactory.getLogger();
    protected static GenericXmlApplicationContext databaseContext;
    private static Properties portProperties = new Properties();
    private static GenericXmlApplicationContext routeringContext;
    private static GenericXmlApplicationContext brpRouteringContext;

    private final Modus modus;
    protected JmsTemplate jmsTemplate;
    private JmsTemplate brpJmsTemplate;
    private JmsTemplate partijregisterJmsTemplate;
    private Main subject;
    private GenericXmlApplicationContext testContext;

    public AbstractIT(final Modus modus) {
        this.modus = modus;
    }

    @BeforeClass
    public static void startDependencies() throws IOException, DatabaseUnitException, SQLException {
        try (ServerSocket databasePort = new ServerSocket(0)) {
            LOG.info("Configuring database to port: " + databasePort.getLocalPort());
            portProperties.setProperty("test.database.port", Integer.toString(databasePort.getLocalPort()));
        }
        try (ServerSocket routeringPort = new ServerSocket(0)) {
            LOG.info("Configuring activemq to port: " + routeringPort.getLocalPort());
            portProperties.setProperty("test.routering.port", Integer.toString(routeringPort.getLocalPort()));
        }
        try (ServerSocket messagebrokerPort = new ServerSocket(0)) {
            LOG.info("Configuring messagebroker to port: " + messagebrokerPort.getLocalPort());
            portProperties.setProperty("test.messagebroker.port", Integer.toString(messagebrokerPort.getLocalPort()));
        }

        // Start DB
        databaseContext = new GenericXmlApplicationContext();
        databaseContext.load("classpath:test-embedded-database.xml");
        databaseContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        databaseContext.refresh();

        // Start RouteringCentrale
        final Properties routeringProperties = new Properties();
        routeringProperties.setProperty("routering.activemq.url", "nio://localhost:" + portProperties.getProperty("test.routering.port"));
        routeringProperties.setProperty("routering.activemq.data.directory", "target/activemq-data");
        routeringProperties.setProperty("routering.activemq.kahadb.directory", "target/activemq-kahadb");
        routeringProperties.setProperty("routering.activemq.scheduler.directory", "target/activemq-scheduler");

        routeringProperties.setProperty("routering.activemq.redelivery.maximum", "5");
        routeringProperties.setProperty("routering.activemq.redelivery.initial.delay", "5000");
        routeringProperties.setProperty("routering.activemq.redelivery.delay", "5000");
        routeringProperties.setProperty("routering.jmx.serialize.port", "0");

        routeringContext = new GenericXmlApplicationContext();
        routeringContext.load("classpath:routering-runtime.xml");
        routeringContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", routeringProperties));
        routeringContext.refresh();

        // Start RouteringCentrale
        brpRouteringContext = new GenericXmlApplicationContext();
        brpRouteringContext.load("classpath:test-embedded-brp.xml");
        brpRouteringContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        brpRouteringContext.refresh();
    }

    @AfterClass
    public static void stopTestContext() {
        if (brpRouteringContext != null) {
            try {
                brpRouteringContext.close();
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten REGISTER context", e);
            }
        }
        if (routeringContext != null) {
            try {
                routeringContext.close();
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten ROUTERING context", e);
            }
        }
        if (databaseContext != null) {
            try {
                databaseContext.close();
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten DATABASE context", e);
            }
        }
    }

    @Autowired
    @Named(value = "connectionFactory")
    public void setConnectionFactory(final ConnectionFactory connectionFactory) {
        jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setReceiveTimeout(10000);
    }

    @Autowired
    @Named(value = "partijConnectionFactory")
    public void setPartijConnectionFactory(final ConnectionFactory connectionFactory) {
        partijregisterJmsTemplate = new JmsTemplate(connectionFactory);
        partijregisterJmsTemplate.setPubSubDomain(true);
        partijregisterJmsTemplate.setReceiveTimeout(20000);
    }

    @Autowired
    @Named(value = "brpConnectionFactory")
    public void setBrpConnectionFactory(final ConnectionFactory connectionFactory) {
        brpJmsTemplate = new JmsTemplate(connectionFactory);
        brpJmsTemplate.setReceiveTimeout(10000);
    }

    @Before
    public void start() throws Exception {
        LOG.info("Before test; starting context");
        final Properties syncProperties = new Properties();
        if (modus == Modus.INITIELEVULLING) {
            syncProperties.setProperty("sync.queue.verzoek", "iv.request");
            syncProperties.setProperty("sync.queue.antwoord", "iv.response");

            // atomikos.max.timeout=300000
            // atomikos.default.timeout=30000
            // atomikos.max.actives=50
            // #atomikos.unique.name=synchronisatie-runtime
            syncProperties.setProperty("atomikos.base.dir", "work/atomikos");

            syncProperties.setProperty("synchronisatie.dal.dialect", "org.hibernate.dialect.PostgreSQL82Dialect");
        } else if (modus == Modus.SYNCHRONISATIE) {
            syncProperties.setProperty("sync.queue.url", "tcp://localhost:" + portProperties.getProperty("test.routering.port"));
            syncProperties.setProperty("brp.queue.url", "tcp://localhost:" + portProperties.getProperty("test.messagebroker.port"));

            syncProperties.setProperty("atomikos.base.dir", "target/atomikos");
        }
        syncProperties.setProperty("synchronisatie.jmx.serialize.port", "0");
        syncProperties.setProperty("test.database.port", portProperties.getProperty("test.database.port"));

        subject = new Main(modus, new PropertiesPropertySource("configuration", syncProperties));
        subject.start();

        LOG.info("Before test; context started");

        // Create test context
        testContext = new GenericXmlApplicationContext();
        testContext.load("classpath:test-context.xml");
        testContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        testContext.refresh();

        testContext.getAutowireCapableBeanFactory().autowireBean(this);
        Assert.assertNotNull(jmsTemplate);
        Assert.assertNotNull(brpJmsTemplate);
    }


    @After
    public void shutdown() throws InterruptedException {
        if (testContext != null) {
            try {
                testContext.close();
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten TEST context", e);
            }
        }

        if (subject != null) {
            LOG.info("After test; closing context");
            subject.stop();
            LOG.info("After test; context closed");
        }
    }

    protected void putMessage(final String destinationName, final String bericht, final String messageId) {
        jmsTemplate.send(destinationName, session -> {
            final TextMessage message = session.createTextMessage(bericht);
            message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, messageId);

            return message;
        });
    }

    protected void putBrpMessage(final String destinationName, final String bericht, final String messageId) {
        brpJmsTemplate.send(destinationName, session -> {
            final TextMessage message = session.createTextMessage(bericht);
            message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, messageId);

            return message;
        });
    }

    protected Message expectMessage(final String destinationName) {
        Message message;
        try {
            message = jmsTemplate.receive(destinationName);
        } catch (final JmsException e) {
            throw new IllegalArgumentException(e);
        }
        Assert.assertNotNull("Bericht verwacht", message);
        return message;
    }

    protected Message expectPartijregisterMessage() {
        Message message;
        try {
            message = partijregisterJmsTemplate.receive(PARTIJ_REGISTER_TOPIC);
        } catch (final JmsException e) {
            throw new IllegalArgumentException(e);
        }
        Assert.assertNotNull("Bericht verwacht", message);
        return message;
    }

    protected Message expectBrpMessage(final String destinationName) {
        Message message;
        try {
            message = brpJmsTemplate.receive(destinationName);
        } catch (final JmsException e) {
            throw new IllegalArgumentException(e);
        }
        Assert.assertNotNull("Bericht verwacht", message);
        return message;
    }

    protected String getContent(final Message message) throws JMSException {
        if (message instanceof TextMessage) {
            return ((TextMessage) message).getText();
        } else {
            throw new IllegalArgumentException("BerichtType niet ondersteund");
        }
    }
}
