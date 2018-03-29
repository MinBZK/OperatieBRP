/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.vrijbericht.gba;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import org.dbunit.DatabaseUnitException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

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
        databaseContext.load("classpath:vrijbericht-gba-delivery-database-test-context.xml");
        databaseContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        databaseContext.refresh();

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(databaseContext.getBean("initializeBrpDataSource", DataSource.class));
        jdbcTemplate.execute("update kern.partij set datovergangnaarbrp = 20000101, afleverpuntvrijber='local' where code = '060301'");
        jdbcTemplate.execute("update kern.his_partij set datovergangnaarbrp = 20000101 where partij in (select id from kern.partij where code = '060301')");
        jdbcTemplate.execute("update kern.his_partijvrijber set afleverpuntvrijber='local' where partij in (select id from kern.partij where code = '060301')");

        jdbcTemplate.execute("update kern.partij set datovergangnaarbrp = 20000101 where code = '060601'");
        jdbcTemplate.execute("update kern.his_partij set datovergangnaarbrp = 20000101 where partij in (select id from kern.partij where code = '060601')");

        // Start messagebroker
        messagebrokerContext = new GenericXmlApplicationContext();
        messagebrokerContext.load("classpath:vrijbericht-gba-delivery-broker-test-context.xml");
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
    public void start() throws Exception {
        // Create test context
        final Properties properties = new Properties();
        properties.setProperty("jdbc.master.driverClassName", "org.hsqldb.jdbc.JDBCDriver");
        properties.setProperty("jdbc.master.url", String.format("jdbc:hsqldb:hsql://localhost:%s/brp", portProperties.getProperty("test.database.port")));
        properties.setProperty("jdbc.master.username", "sa");
        properties.setProperty("jdbc.master.password", "");
        properties.setProperty("brp.jms.client.url", portProperties.getProperty("brp.jms.client.url"));

        properties.setProperty("jdbc.archivering.database.driver", "org.hsqldb.jdbc.pool.JDBCXADataSource");
        properties.setProperty("jdbc.archivering.database.host", "localhost");
        properties.setProperty("jdbc.archivering.database.port", portProperties.getProperty("test.database.port"));
        properties.setProperty("jdbc.archivering.database.name", "brp");
        properties.setProperty("jdbc.archivering.username", "sa");
        properties.setProperty("jdbc.archivering.password", "");

        testContext = new GenericXmlApplicationContext();
        testContext.load("classpath:vrijbericht-gba-delivery-test-context.xml");
        testContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", properties));
        testContext.refresh();

        testContext.getAutowireCapableBeanFactory().autowireBean(this);
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
}
