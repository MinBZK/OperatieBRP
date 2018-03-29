/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemerindicatie.gba;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.Properties;
import java.util.stream.Stream;
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
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Base64Utils;

public class AbstractIT {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static Properties portProperties = new Properties();
    private static GenericXmlApplicationContext databaseContext;
    private static GenericXmlApplicationContext messagebrokerContext;
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

        portProperties.setProperty("jdbc.master.database.port", portProperties.getProperty("test.database.port"));
        portProperties.setProperty("jdbc.archivering.database.port", portProperties.getProperty("test.database.port"));
        portProperties.setProperty("brp.jms.broker.url", "tcp://0.0.0.0:" + portProperties.getProperty("test.messagebroker.port"));
        portProperties.setProperty("brp.jms.client.url", "tcp://localhost:" + portProperties.getProperty("test.messagebroker.port"));

        // Start DB
        databaseContext = new GenericXmlApplicationContext();
        databaseContext.load("classpath:afnemerindicatie-gba-delivery-database-test-context.xml");
        databaseContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        databaseContext.refresh();

        AbstractDBUnitUtil dbunit = databaseContext.getAutowireCapableBeanFactory().getBean(AbstractDBUnitUtil.class);
        dbunit.setInMemory();
        dbunit.insert(dbunit.createConnection(), AbstractIT.class, "PersoonTestData.xml", "testdata-autaut.xml");

        // Start messagebroker
        messagebrokerContext = new GenericXmlApplicationContext();
        messagebrokerContext.load("classpath:afnemerindicatie-gba-delivery-broker-test-context.xml");
        messagebrokerContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        messagebrokerContext.refresh();
    }

    @AfterClass
    public static void stopTestContext() {
        if (databaseContext != null) {
            try {
                databaseContext.close();
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten DATABASE context", e);
            }
        }
        if (messagebrokerContext != null) {
            try {
                messagebrokerContext.close();
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten MESSAGEBROKER context", e);
            }
        }
    }

    @Before
    public void start() throws DatabaseUnitException, SQLException {
        // Create test context
        testContext = new GenericXmlApplicationContext();
        testContext.load("classpath:afnemerindicatie-gba-delivery-test-context.xml");
        testContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        testContext.refresh();

        testContext.getAutowireCapableBeanFactory().autowireBean(this);
        Stream.of(3000020).forEach(this::logPersoonBlob);
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

    private void logPersoonBlob(int persoonId) {
        JtaTransactionManager
                transactionManager =
                testContext.getAutowireCapableBeanFactory().getBean("masterTransactionManager", JtaTransactionManager.class);
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager, new DefaultTransactionDefinition());

        PersoonRepository persoonRepository = testContext.getAutowireCapableBeanFactory().getBean(PersoonRepository.class);
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
}
