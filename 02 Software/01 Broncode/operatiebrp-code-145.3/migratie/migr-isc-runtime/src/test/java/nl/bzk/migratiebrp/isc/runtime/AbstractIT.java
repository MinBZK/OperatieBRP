/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.UUID;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.management.JMException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import nl.bzk.migratiebrp.bericht.model.Bericht;
import nl.bzk.migratiebrp.bericht.model.BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.isc.runtime.handler.JmsUtil;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmInvoker;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmInvoker.JbpmExecution;
import nl.bzk.migratiebrp.isc.runtime.message.Message;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal.Strategie;
import nl.bzk.migratiebrp.util.common.operatie.HerhaalException;
import org.hibernate.jdbc.ReturningWork;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class AbstractIT {

    protected static final String LEVERING_QUEUE = "levering";
    protected static final String NOTIFICATIE_QUEUE = "notificatie";
    protected static final String SYNC_ANTWOORD_QUEUE = "sync.antwoord";
    protected static final String SYNC_VERZOEK_QUEUE = "sync.verzoek";
    protected static final String VOISC_ONTVANGST_QUEUE = "voisc.ontvangst";
    protected static final String VOISC_VERZENDEN_QUEUE = "voisc.verzenden";

    private static final Logger LOG = LoggerFactory.getLogger();
    private static Properties portProperties = new Properties();
    private static GenericXmlApplicationContext databaseContext;
    private static GenericXmlApplicationContext routeringContext;
    private static GenericXmlApplicationContext registerContext;
    private JmsTemplate jmsTemplate;
    private Main subject;
    private GenericXmlApplicationContext testContext;

    @Autowired
    private MBeanServerConnection iscMBeanServerConnection;

    @BeforeClass
    public static void startDependencies() throws IOException {
        try (ServerSocket databasePort = new ServerSocket(0)) {
            LOG.info("Configuring database to port: " + databasePort.getLocalPort());
            portProperties.setProperty("test.database.port", Integer.toString(databasePort.getLocalPort()));
        }
        try (ServerSocket iscDatabasePort = new ServerSocket(0)) {
            LOG.info("Configuring database to port: " + iscDatabasePort.getLocalPort());
            portProperties.setProperty("test.database.isc.port", Integer.toString(iscDatabasePort.getLocalPort()));
        }
        try (ServerSocket routeringPort = new ServerSocket(0)) {
            LOG.info("Configuring activemq to port: " + routeringPort.getLocalPort());
            portProperties.setProperty("test.routering.port", Integer.toString(routeringPort.getLocalPort()));
        }
        try (ServerSocket jmxPort = new ServerSocket(0)) {
            LOG.info("Configuring jmx to port: " + jmxPort.getLocalPort());
            portProperties.setProperty("test.jmx.port", Integer.toString(jmxPort.getLocalPort()));
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

        // Start Register (SYNC)
        registerContext = new GenericXmlApplicationContext();
        registerContext.load("classpath:test-embedded-register.xml", "classpath:test-partijregister-dummy.xml");
        registerContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        registerContext.refresh();
    }

    @AfterClass
    public static void stopTestContext() {
        if (registerContext != null) {
            try {
                registerContext.close();
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
    public void setConnectionFactory(final ConnectionFactory connectionFactory) {
        jmsTemplate = new JmsTemplate(connectionFactory);
    }

    @Before
    public void start() {
        final Properties iscProperties = new Properties();

        iscProperties.setProperty("routering.activemq.url", "tcp://localhost:" + portProperties.getProperty("test.routering.port"));
        iscProperties.setProperty("jbpm.scheduler.idlewaittime", "1000");
        iscProperties.setProperty("isc.jmx.serialize.port", portProperties.getProperty("test.jmx.port"));
        iscProperties.setProperty("test.database.port", portProperties.getProperty("test.database.port"));
        iscProperties.setProperty("test.database.isc.port", portProperties.getProperty("test.database.isc.port"));

        subject = new Main(new PropertiesPropertySource("configuration", iscProperties));

        LOG.info("Before test; starting context");
        try {
            subject.start();
        } catch (final Exception e) {
            LOG.error("Fout tijdens opstarten applicatie", e);
            throw e;
        }
        LOG.info("Before test; context started");

        // Create test context
        testContext = new GenericXmlApplicationContext();
        testContext.load("classpath:test_context.xml");
        testContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        testContext.refresh();

        testContext.getAutowireCapableBeanFactory().autowireBean(this);
        Assert.assertNotNull(jmsTemplate);
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

    @SuppressWarnings("unchecked")
    protected <T extends Bericht> T expectMessage(final String destinationName, final Class<T> clazz) {
        jmsTemplate.setReceiveTimeout(10000);
        Message message;
        try {
            message = JmsUtil.leesMessage(jmsTemplate.receive(destinationName));
        } catch (JmsException | JMSException e) {
            throw new IllegalArgumentException(e);
        }
        Assert.assertNotNull("Geen bericht ontvangen", message);
        final Bericht bericht = leesBericht(destinationName, message);
        Assert.assertTrue(clazz.isAssignableFrom(bericht.getClass()));
        return (T) bericht;
    }

    private Bericht leesBericht(final String destinationName, final Message message) {
        final BerichtFactory berichtFactory;
        switch (destinationName) {
            case VOISC_ONTVANGST_QUEUE:
            case VOISC_VERZENDEN_QUEUE:
                berichtFactory = new Lo3BerichtFactory();
                break;
            case SYNC_ANTWOORD_QUEUE:
            case SYNC_VERZOEK_QUEUE:
                berichtFactory = SyncBerichtFactory.SINGLETON;
                break;
            // case VOISC_INBOUND_QUEUE:
            // case VOISC_OUTBOUND_QUEUE:
            // berichtFactory = VoiscBerichtFactory.SINGLETON;
            // break;
            default:
                throw new IllegalArgumentException("Queue '" + destinationName + "' onbekend.");
        }

        final Bericht result = berichtFactory.getBericht(message.getContent());
        result.setMessageId(message.getMessageId());
        result.setCorrelationId(message.getCorrelatieId());
        if (result instanceof Lo3Bericht) {
            ((Lo3Bericht) result).setBronPartijCode(message.getOriginator());
            ((Lo3Bericht) result).setDoelPartijCode(message.getRecipient());
        }
        return result;

    }

    protected void putMessage(final String destinationName, final Bericht bericht) {
        putMessage(destinationName, bericht, null, null);
    }

    /*
     * *** CONVENIENCE
     * ***************************************************************************************
     */

    protected void putMessage(final String destinationName, final Bericht bericht, final Long msSequenceNumber, final Long administratieveHandelingId) {
        jmsTemplate.send(destinationName, (MessageCreator) session -> {
            try {
                final TextMessage message = session.createTextMessage(bericht.format());
                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, bericht.getMessageId());
                message.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, bericht.getCorrelationId());
                if (bericht instanceof Lo3Bericht) {
                    message.setStringProperty(JMSConstants.BERICHT_ORIGINATOR, ((Lo3Bericht) bericht).getBronPartijCode());
                    message.setStringProperty(JMSConstants.BERICHT_RECIPIENT, ((Lo3Bericht) bericht).getDoelPartijCode());
                }
                if (msSequenceNumber != null) {
                    message.setStringProperty(JMSConstants.BERICHT_MS_SEQUENCE_NUMBER, msSequenceNumber.toString());
                }
                if (administratieveHandelingId != null) {
                    message.setStringProperty(JMSConstants.BERICHT_ADMINISTRATIEVE_HANDELING_ID, administratieveHandelingId.toString());
                }

                return message;
            } catch (final BerichtInhoudException e) {
                throw new JmsException("Probleem bij formatten bericht", e) {
                    private static final long serialVersionUID = 1L;
                };
            }
        });
    }

    protected Main getSubject() {
        return subject;
    }

    protected <T> T executeInSqlInJbpmInTransaction(final ReturningWork<T> work) {
        return executeInJbpmInTransaction(jbpmContext -> {
            final org.hibernate.Session session =
                    (org.hibernate.Session) jbpmContext.getServices().getPersistenceService().getCustomSession(org.hibernate.Session.class);
            return session.doReturningWork(work);
        });
    }

    protected <T> T executeInJbpmInTransaction(final JbpmExecution<T> work) {
        return executeInTransaction(status -> {
            final JbpmInvoker jbpmInvoker = subject.getContext().getBean(JbpmInvoker.class);
            return jbpmInvoker.executeInContext(work);
        });
    }

    /*
     * *** PROCESSEN
     * ***************************************************************************************
     */

    protected <T> T executeInTransaction(final TransactionCallback<T> work) {
        final PlatformTransactionManager transactionManager = subject.getContext().getBean(PlatformTransactionManager.class);
        final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return transactionTemplate.execute(work);
    }

    public void controleerAlleProcessenBeeindigd() {
        try {
            new Herhaal(1000, 10, Strategie.REGELMATIG).herhaal(() -> {
                final int aantal = bepaalAantalLopendeProcessen();
                if (aantal != 0) {
                    throw new IllegalStateException("Aantal lopende processen was niet 0, maar " + aantal);
                }
            });
        } catch (final HerhaalException e) {
            Assert.fail("Er zijn nog lopende processen.");
        }
    }

    /*
     * *** BERICHTEN
     * ***************************************************************************************
     */

    protected int bepaalAantalLopendeProcessen() {
        return executeInSqlInJbpmInTransaction(connection -> {
            final ResultSet result = connection.createStatement().executeQuery("SELECT count(*) FROM jbpm_processinstance WHERE end_ IS NULL");
            if (!result.next()) {
                throw new IllegalArgumentException("Query op lopende processen gaf geen resultaat!");
            }

            return result.getInt(1);
        });
    }

    /* *** JMX ******************************************************************************* */

    protected String generateMessageId() {
        return UUID.randomUUID().toString();
    }

    protected void triggerOpschoner() {
        try {
            iscMBeanServerConnection.invoke(new ObjectName("nl.bzk.migratiebrp.isc:name=OPSCHONER"), "opschonen", null, null);
        } catch (JMException | IOException e) {
            throw new IllegalArgumentException("Kan opschonen niet triggeren via JMX", e);
        }
    }

    protected void triggerTelling() {
        try {
            iscMBeanServerConnection.invoke(new ObjectName("nl.bzk.migratiebrp.isc:name=TELLING"), "bijwerkenTellingen", null, null);
        } catch (JMException | IOException e) {
            throw new IllegalArgumentException("Kan telling niet triggeren via JMX", e);
        }
    }
}
