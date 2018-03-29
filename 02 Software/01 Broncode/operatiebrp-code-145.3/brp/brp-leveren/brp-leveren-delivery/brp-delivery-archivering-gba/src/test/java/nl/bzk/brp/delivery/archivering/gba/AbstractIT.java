/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.archivering.gba;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.Properties;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import org.dbunit.DatabaseUnitException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class AbstractIT {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static Properties portProperties = new Properties();
    private static GenericXmlApplicationContext databaseContext;
    private static GenericXmlApplicationContext messagebrokerContext;

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

        portProperties.setProperty("jdbc.archivering.database.port", portProperties.getProperty("test.database.port"));
        portProperties.setProperty("brp.jms.broker.url", "tcp://0.0.0.0:" + portProperties.getProperty("test.messagebroker.port"));
        portProperties.setProperty("brp.jms.client.url", "tcp://localhost:" + portProperties.getProperty("test.messagebroker.port"));

        // Start DB
        databaseContext = new GenericXmlApplicationContext();
        databaseContext.load("classpath:brp-delivery-archivering-gba-database-test-context.xml");
        databaseContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        databaseContext.refresh();

        // Start messagebroker
        messagebrokerContext = new GenericXmlApplicationContext();
        messagebrokerContext.load("classpath:brp-delivery-archivering-gba-broker-test-context.xml");
        messagebrokerContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        messagebrokerContext.refresh();
    }

    @AfterClass
    public static void stopTestContext() {
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

    /**
     * Dynamisch poorten voor resources bepalen.
     */
    public static final class PortInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(final ConfigurableApplicationContext applicationContext) {
            applicationContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        }
    }
}
