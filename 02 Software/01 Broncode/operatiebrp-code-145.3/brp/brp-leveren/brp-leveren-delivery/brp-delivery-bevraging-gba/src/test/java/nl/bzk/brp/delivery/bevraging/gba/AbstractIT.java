/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.Properties;
import java.util.stream.Stream;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
import nl.bzk.algemeenbrp.test.dal.AbstractDBUnitUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import nl.bzk.brp.service.dalapi.PersoonRepository;
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
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Base64Utils;

public class AbstractIT {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static Properties portProperties = new Properties();
    private static GenericXmlApplicationContext databaseContext;
    private static GenericXmlApplicationContext messagebrokerContext;
    private static GenericXmlApplicationContext subjectContext;
    private JmsTemplate jmsTemplate;
    private GenericXmlApplicationContext testContext;

    @BeforeClass
    public static void startDependencies() throws IOException, DatabaseUnitException, SQLException {
        try (ServerSocket databasePort = new ServerSocket(0)) {
            LOG.info("Configuring database to port: " + databasePort.getLocalPort());
            portProperties.setProperty("test.database.port", Integer.toString(databasePort.getLocalPort()));
        }
        try (ServerSocket brokerPort = new ServerSocket(0)) {
            LOG.info("Configuring messagebroker to port: " + brokerPort.getLocalPort());
            portProperties.setProperty("test.messagebroker.port", Integer.toString(brokerPort.getLocalPort()));
        }

        // Start DB
        databaseContext = new GenericXmlApplicationContext();
        databaseContext.load("classpath:test-embedded-database.xml");
        databaseContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        databaseContext.refresh();

        AbstractDBUnitUtil dbunit = databaseContext.getAutowireCapableBeanFactory().getBean(AbstractDBUnitUtil.class);
        dbunit.setInMemory();
        dbunit.insert(dbunit.createConnection(), AbstractIT.class, "common-fixtures.xml", "adresvraag-fixtures.xml", "persoonsvraag-fixtures.xml");

        // Start messagebroker
        messagebrokerContext = new GenericXmlApplicationContext();
        messagebrokerContext.load("classpath:test-embedded-messagebroker.xml");
        messagebrokerContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        messagebrokerContext.refresh();

        // Start test subject
        final Properties subjectProperties = new Properties();
        subjectProperties.setProperty("brp.bevraging.database.driver", "org.hsqldb.jdbc.pool.JDBCXADataSource");
        subjectProperties.setProperty("brp.bevraging.database.host", "localhost");
        subjectProperties.setProperty("brp.bevraging.database.port", portProperties.getProperty("test.database.port"));
        subjectProperties.setProperty("brp.bevraging.database.name", "brp");
        subjectProperties.setProperty("brp.bevraging.database.username", "sa");
        subjectProperties.setProperty("brp.bevraging.database.password", "");

        subjectProperties.setProperty("jdbc.archivering.database.port", portProperties.getProperty("test.database.port"));
        subjectProperties.setProperty("jdbc.protocollering.database.port", portProperties.getProperty("test.database.port"));

        subjectProperties.setProperty("brp.jms.client.url", "tcp://localhost:" + portProperties.getProperty("test.messagebroker.port"));

        subjectContext = new GenericXmlApplicationContext();
        subjectContext.load("classpath:brp-delivery-bevraging-gba.xml");
        subjectContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", subjectProperties));
        subjectContext.refresh();

        Stream.of(
                3000020, 3000021, 3000022, 3000023, 3000024, 3000025, 5000020, 5000021, 5000022, 5000023, 5000024, 5000025, 5000026, 5000027, 5000028,
                5000029, 5000030, 5000031, 5000032, 5000033, 5000034
        ).forEach(AbstractIT::logPersoonBlob);
    }

    @AfterClass
    public static void stopTestContext() {
        if (subjectContext != null) {
            try {
                subjectContext.close();
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten SUBJECT context", e);
            }
        }
        if (messagebrokerContext != null) {
            try {
                messagebrokerContext.close();
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten MESSAGEBROKER context", e);
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

    private static void logPersoonBlob(int persoonId) {
        JtaTransactionManager
                transactionManager =
                subjectContext.getAutowireCapableBeanFactory().getBean("masterTransactionManager", JtaTransactionManager.class);
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager, new DefaultTransactionDefinition());

        PersoonRepository persoonRepository = subjectContext.getAutowireCapableBeanFactory().getBean(PersoonRepository.class);
        transactionTemplate.execute(action -> {
            final Persoon persoon = persoonRepository.haalPersoonOp(persoonId);
            try {
                LOG.info("Persoonblob met id " + persoonId + ": " + Base64Utils.encodeToString(Blobber.toJsonBytes(Blobber.maakBlob(persoon))));
                return null;
            } catch (final BlobException e) {
                throw new IllegalArgumentException(e);
            }
        });
    }

    @Autowired
    public void setConnectionFactory(final ConnectionFactory connectionFactory) {
        jmsTemplate = new JmsTemplate(connectionFactory);
    }

    @Before
    public void start() throws BlobException {
        // Create test context
        testContext = new GenericXmlApplicationContext();
        testContext.load("classpath:test-context.xml");
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
    }

    void putMessage(final String destinationName, final String bericht, final String berichtReferentie) {
        jmsTemplate.send(destinationName, session -> {
            final TextMessage message = session.createTextMessage(bericht);
            message.setStringProperty("iscBerichtReferentie", berichtReferentie);
            return message;
        });
    }

    String expectMessage(final String destinationName) {
        jmsTemplate.setReceiveTimeout(5000);
        Message message;
        try {
            message = jmsTemplate.receive(destinationName);
            Assert.assertNotNull("Geen bericht ontvangen", message);
            Assert.assertTrue("Bericht is niet een TextMessage", message instanceof TextMessage);
            return ((TextMessage) message).getText();
        } catch (final JmsException | JMSException e) {
            throw new IllegalArgumentException(e);
        }
    }

    void expectNoMessage(final String destinationName) {
        jmsTemplate.setReceiveTimeout(5000);
        Message message;
        try {
            message = jmsTemplate.receive(destinationName);
            Assert.assertNull("Toch een bericht ontvangen", message);
        } catch (final JmsException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void clearQueue(final String destinationName) {
        jmsTemplate.setReceiveTimeout(100);
        Message message;
        do {
            try {
                message = jmsTemplate.receive(destinationName);
            } catch (final JmsException e) {
                throw new IllegalArgumentException(e);
            }
        } while (message != null);
    }
}
