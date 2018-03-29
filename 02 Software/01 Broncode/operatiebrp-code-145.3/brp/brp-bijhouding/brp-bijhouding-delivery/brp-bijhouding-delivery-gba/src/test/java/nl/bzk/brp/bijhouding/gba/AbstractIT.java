/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.gba;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.Properties;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.test.dal.AbstractDBUnitUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
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

public class AbstractIT {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Properties portProperties = new Properties();
    private static GenericXmlApplicationContext databaseContext;
    private static GenericXmlApplicationContext messagebrokerContext;
    private JmsTemplate jmsTemplate;
    private GenericXmlApplicationContext subjectContext;
    private GenericXmlApplicationContext testContext;

    @BeforeClass
    public static void startDependencies() throws IOException {
        try (ServerSocket databasePort = new ServerSocket(0)) {
            LOG.info("Configuring database to port: " + databasePort.getLocalPort());
            portProperties.setProperty("test.database.port", Integer.toString(databasePort.getLocalPort()));
        }
        try (ServerSocket brokerPort = new ServerSocket(0)) {
            LOG.info("Configuring messagebroker to port: " + brokerPort.getLocalPort());
            portProperties.setProperty("test.messagebroker.port", Integer.toString(brokerPort.getLocalPort()));
        }

        // Start DB
        LOG.info("Starten DATABASE context");
        databaseContext = new GenericXmlApplicationContext();
        databaseContext.load("classpath:test-embedded-database.xml");
        databaseContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        databaseContext.refresh();

        // Start messagebroker
        LOG.info("Starten MESSAGEBROKER context");
        messagebrokerContext = new GenericXmlApplicationContext();
        messagebrokerContext.load("classpath:test-embedded-messagebroker.xml");
        messagebrokerContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        messagebrokerContext.refresh();
    }

    @AfterClass
    public static void stopTestContext() {
        LOG.info("Sluiten MESSAGEBROKER context");
        if (messagebrokerContext != null) {
            try {
                messagebrokerContext.close();
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten MESASGEBROKER context", e);
            }
        }
        LOG.info("Sluiten DATABASE context");
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
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.setReceiveTimeout(20000);
    }

    @Before
    public void start() {
        // Start test subject
        final Properties subjectProperties = new Properties();
        subjectProperties.setProperty("brp.bijhouding.database.driver", "org.hsqldb.jdbc.pool.JDBCXADataSource");
        subjectProperties.setProperty("brp.bijhouding.database.host", "localhost");
        subjectProperties.setProperty("brp.bijhouding.database.port", portProperties.getProperty("test.database.port"));
        subjectProperties.setProperty("brp.bijhouding.database.name", "brp");
        subjectProperties.setProperty("brp.bijhouding.database.username", "sa");
        subjectProperties.setProperty("brp.bijhouding.database.password", "");
        subjectProperties.setProperty("jdbc.archivering.database.driver", "org.hsqldb.jdbc.pool.JDBCXADataSource");
        subjectProperties.setProperty("jdbc.archivering.database.host", "localhost");
        subjectProperties.setProperty("jdbc.archivering.database.port", portProperties.getProperty("test.database.port"));
        subjectProperties.setProperty("jdbc.archivering.database.name", "brp");
        subjectProperties.setProperty("jdbc.archivering.username", "sa");
        subjectProperties.setProperty("jdbc.archivering.password", "");

        subjectProperties.setProperty("brp.jms.client.url", "tcp://localhost:" + portProperties.getProperty("test.messagebroker.port"));

        LOG.info("Starten SUBJECT context");
        subjectContext = new GenericXmlApplicationContext();
        subjectContext.load("classpath:brp-bijhouding-delivery-gba-context.xml");
        subjectContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", subjectProperties));
        subjectContext.refresh();

        // Create test context
        LOG.info("Starten TEST context");
        testContext = new GenericXmlApplicationContext();
        testContext.load("classpath:test-context.xml");
        testContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        testContext.refresh();

        testContext.getAutowireCapableBeanFactory().autowireBean(this);
        Assert.assertNotNull(jmsTemplate);
    }

    @After
    public void shutdown() throws InterruptedException {
        LOG.info("Sluiten TEST context");
        if (testContext != null) {
            try {
                testContext.close();
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten TEST context", e);
            }
        }

        LOG.info("Sluiten SUBJECT context");
        if (subjectContext != null) {
            try {
                subjectContext.close();
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten SUBJECT context", e);
            }
        }
    }

    protected void dbUnitInsert(final String... dbUnitResources) throws DatabaseUnitException, SQLException {
        final AbstractDBUnitUtil dbunit = databaseContext.getAutowireCapableBeanFactory().getBean(AbstractDBUnitUtil.class);
        dbunit.setInMemory();
        final IDatabaseConnection connection = dbunit.createConnection();
        try {
            dbunit.insert(dbunit.createConnection(), AbstractIT.class, dbUnitResources);
        } finally {
            connection.close();
        }
    }

    protected void dbUnitExpect(final String... dbUnitResources) throws DatabaseUnitException, SQLException {
        final AbstractDBUnitUtil dbunit = databaseContext.getAutowireCapableBeanFactory().getBean(AbstractDBUnitUtil.class);
        dbunit.setInMemory();

        dbunit.compareExpectedWithActual(this.getClass(), dbUnitResources);
    }

    protected void putMessage(final String destinationName, final String bericht, final String berichtReferentie) {
        jmsTemplate.send(destinationName, (MessageCreator) session -> {
            final TextMessage message = session.createTextMessage(bericht);
            message.setStringProperty("iscBerichtReferentie", berichtReferentie);
            return message;
        });
    }

    protected String expectMessage(final String destinationName) {
        final Message message;
        try {
            jmsTemplate.setReceiveTimeout(30000);
            message = jmsTemplate.receive(destinationName);
            Assert.assertNotNull("Geen bericht ontvangen op " + destinationName, message);
            Assert.assertTrue("Bericht is niet een TextMessage", message instanceof TextMessage);
            return ((TextMessage) message).getText();
        } catch (final JmsException | JMSException e) {
            throw new IllegalArgumentException(e);
        }
    }


}
