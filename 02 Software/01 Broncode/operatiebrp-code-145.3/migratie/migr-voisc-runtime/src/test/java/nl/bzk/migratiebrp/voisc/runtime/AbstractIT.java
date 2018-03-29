/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.management.JMException;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxEntry;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxException;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

public class AbstractIT {

    protected static final String VOISC_ONTVANGST_QUEUE = "voisc.ontvangst";
    protected static final String VOISC_VERZENDEN_QUEUE = "voisc.verzenden";
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final Logger LOG = LoggerFactory.getLogger();
    private static Properties portProperties = new Properties();
    private static GenericXmlApplicationContext databaseContext;
    private static GenericXmlApplicationContext routeringContext;
    private static GenericXmlApplicationContext registerContext;
    private static GenericXmlApplicationContext mailboxContext;
    private MailboxFactory mailboxFactory;
    private DataSource voiscDataSource;
    private JmsTemplate jmsTemplate;
    private VoiscMain subject;
    private GenericXmlApplicationContext testContext;

    @BeforeClass
    public static void startDependencies() throws IOException {
        try (ServerSocket databasePort = new ServerSocket(0)) {
            LOGGER.info("Configuring database to port: " + databasePort.getLocalPort());
            portProperties.setProperty("test.database.port", Integer.toString(databasePort.getLocalPort()));
        }
        try (ServerSocket routeringPort = new ServerSocket(0)) {
            LOGGER.info("Configuring activemq to port: " + routeringPort.getLocalPort());
            portProperties.setProperty("test.routering.port", Integer.toString(routeringPort.getLocalPort()));
        }
        try (ServerSocket mailboxPort = new ServerSocket(0)) {
            LOGGER.info("Configuring mailbox to port: " + mailboxPort.getLocalPort());
            portProperties.setProperty("test.mailbox.port", Integer.toString(mailboxPort.getLocalPort()));
        }

        // Start DB
        LOG.info("Starten DATABASE ...");
        databaseContext = new GenericXmlApplicationContext();
        databaseContext.load("classpath:test-embedded-database.xml");
        databaseContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        databaseContext.refresh();
        LOG.info("DATABASE gestart");

        // Start RouteringCentrale
        LOG.info("Starten ROUTERING ...");
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
        LOG.info("ROUTERING gestart");

        // Start Register (SYNC)
        LOG.info("Starten REGISTER ...");
        registerContext = new GenericXmlApplicationContext();
        registerContext.load("classpath:test-embedded-register.xml", "classpath:test-partijregister-dummy.xml");
        registerContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        registerContext.refresh();
        LOG.info("REGISTER gestart");

        stopMailboxContext();
        startMailboxContext();
    }

    @AfterClass
    public static void stopTestContext() {
        stopMailboxContext();
        if (registerContext != null) {
            try {
                LOG.info("Stoppen REGISTER context ...");
                registerContext.close();
                LOG.info("REGISTER context gestopt");
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten REGISTER context", e);
            }
        }
        if (routeringContext != null) {
            try {
                LOG.info("Stoppen ROUTERING context ...");
                routeringContext.close();
                LOG.info("ROUTERING context gestopt");
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten ROUTERING context", e);
            }
        }
        if (databaseContext != null) {
            try {
                LOG.info("Stoppen DATABASE context ...");
                databaseContext.close();
                LOG.info("DATABASE context gestopt");
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten DATABASE context", e);
            }
        }
    }

    public static void stopMailboxContext() {
        if (mailboxContext != null) {
            try {
                LOG.info("Stoppen MAILBOX context ...");
                mailboxContext.close();
                LOG.info("MAILBOX context gestopt");
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten MAILBOX context", e);
            }
            mailboxContext = null;
        }
    }

    public static void startMailboxContext() {
        if (mailboxContext == null) {
            // Start Mailbox
            LOG.info("Starten MAILBOX ...");
            final Properties mailboxProperties = new Properties();
            mailboxProperties.setProperty("mailbox.port", portProperties.getProperty("test.mailbox.port"));
            mailboxProperties.setProperty("mailbox.factory.type", "memory");
            mailboxProperties.setProperty("mailbox.jmx.serialize.port", "0");
            mailboxContext = new GenericXmlApplicationContext();
            mailboxContext.load("classpath:tools-mailbox.xml");
            mailboxContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", mailboxProperties));
            mailboxContext.refresh();
            LOG.info("MAILBOX gestart");
        }
    }

    @Autowired
    public void setConnectionFactory(final ConnectionFactory connectionFactory) {
        jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setReceiveTimeout(5000);
    }

    @Before
    public void start() throws IOException, SchedulerException, JMSException, MailboxException {
        LOG.info("Starten MAIN (before test)");

        final Properties voiscProperties = new Properties();
        voiscProperties.setProperty("mailbox.host", "localhost");
        voiscProperties.setProperty("mailbox.port", portProperties.getProperty("test.mailbox.port"));
        voiscProperties.setProperty("mailbox.ssl.keystore.password", "changeit");
        voiscProperties.setProperty("mailbox.ssl.key.password", "changeit");
        voiscProperties.setProperty("mailbox.ssl.truststore.password", "changeit");
        voiscProperties.setProperty("routering.activemq.url", "tcp://localhost:" + portProperties.getProperty("test.routering.port"));
        voiscProperties.setProperty("voisc.jmx.serialize.port", "0");

        voiscProperties.setProperty("voisc.hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        voiscProperties.setProperty("test.database.port", portProperties.getProperty("test.database.port"));
        voiscProperties.setProperty("atomikos.base.dir", "target/atomikos");

        subject = new VoiscMain(new PropertiesPropertySource("configuration", voiscProperties));
        subject.start();
        LOG.info("MAIN gestart (before test)");

        // Create test context
        LOG.info("Starten TEST context ...");
        testContext = new GenericXmlApplicationContext();
        testContext.load("classpath:test-context.xml");
        testContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        testContext.refresh();
        LOG.info("TEST context gestart");

        testContext.getAutowireCapableBeanFactory().autowireBean(this);

        voiscDataSource = databaseContext.getBean("initializeVoiscDataSource", DataSource.class);
        mailboxFactory = mailboxContext.getBean("mailboxFactory", MailboxFactory.class);

        mailboxFactory.deleteAll();

        voiscOpruimenBerichten();
        opruimenQueues();

    }

    @After
    public void shutdown() throws IOException, JMException {
        LOG.info("Stoppen MAIN (after test)");
        subject.stop();
        LOG.info("MAIN gestopt (after test)");

        if (testContext != null) {
            try {
                LOG.info("Stoppen TEST context ...");
                testContext.close();
                LOG.info("TEST context gestopt");
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten TEST context", e);
            }
        }
    }

    protected void voiscOpruimenBerichten() {
        final JdbcTemplate template = new JdbcTemplate(voiscDataSource);
        template.execute("truncate table voisc.bericht");
    }

    private void opruimenQueues() {
        cleanQueue(VOISC_ONTVANGST_QUEUE);
        cleanQueue(VOISC_VERZENDEN_QUEUE);
    }

    protected MailboxFactory getMailboxFactory() {
        return mailboxFactory;
    }

    protected DataSource getVoiscDataSource() {
        return voiscDataSource;
    }

    /* *** CONVENIENCE (MAILBOX) *** */

    protected void sendToMailbox(final String fromMailboxnr, final String toMailboxnr, final String messageId, final String crossReferenceId,
                                 final String message) throws MailboxException {

        final Mailbox toMailbox = mailboxFactory.getMailbox(toMailboxnr);
        toMailbox.open();

        final MailboxEntry entry = new MailboxEntry();
        entry.setStatus(MailboxEntry.STATUS_NEW);
        entry.setOriginatorOrRecipient(fromMailboxnr);
        entry.setMesg(message);
        entry.setMessageId(messageId);
        entry.setCrossReference(crossReferenceId);

        toMailbox.addEntry(entry);

        toMailbox.save();
        toMailbox.close();
    }

    /* *** CONVENIENCE (QUEUE) *** */

    protected void sendMessageFromIsc(final String berichtInhoud, final String messageId, final String correlationId, final String verzendendePartij,
                                      final String ontvangendePartij) {
        try {
            jmsTemplate.send(VOISC_VERZENDEN_QUEUE, session -> {
                final Message message = session.createTextMessage(berichtInhoud);
                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, messageId);
                if (correlationId != null) {
                    message.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, correlationId);
                }
                message.setStringProperty(JMSConstants.BERICHT_ORIGINATOR, verzendendePartij);
                message.setStringProperty(JMSConstants.BERICHT_RECIPIENT, ontvangendePartij);
                return message;
            });
        } catch (final JmsException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected Message expectMessage(final String destinationName) {
        jmsTemplate.setReceiveTimeout(10000);
        Message message;
        try {
            message = jmsTemplate.receive(destinationName);
        } catch (final JmsException e) {
            throw new IllegalArgumentException(e);
        }
        Assert.assertNotNull("Geen bericht ontvangen", message);
        return message;
    }

    protected void cleanQueue(final String destinationName) {
        jmsTemplate.setReceiveTimeout(500);
        Message message;
        do {
            try {
                message = jmsTemplate.receive(destinationName);
            } catch (final JmsException e) {
                throw new IllegalArgumentException(e);
            }
        } while (message != null);

    }

    protected String getContent(final Message message) throws JMSException {
        // content
        final String content;
        if (message instanceof TextMessage) {
            content = ((TextMessage) message).getText();
        } else {
            throw new IllegalArgumentException("Message type niet ondersteund: " + message.getClass());
        }
        return content;
    }

    protected MailboxEntry getEntryBlocking(final Mailbox mailbox, final String status) throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<MailboxEntry> callable = () -> {
            while (true) {
                try {
                    mailbox.open();

                    Mailbox.FilterResult filterResult = mailbox.filterInbox(status, -1, 141);
                    if (filterResult.getEntries().size() > 0) {
                        return filterResult.getEntries().get(0);
                    }
                } catch (MailboxException e) {
                    LOG.error("Kan mailbox niet openen", e);
                    return null;
                } finally {
                    mailbox.close();
                }
            }
        };

        return executor.submit(callable).get(10, TimeUnit.SECONDS);
    }
}
