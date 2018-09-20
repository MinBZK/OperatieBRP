/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarlo3;

import java.io.Closeable;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.sql.DataSource;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;

public class AbstractIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static GenericXmlApplicationContext databaseContext;
    private static GenericXmlApplicationContext hornetqContext;
    private static GenericXmlApplicationContext testContext;

    @Autowired
    @Named("brpDataSource")
    protected DataSource brpDataSource;

    @Autowired
    protected ConnectionFactory connectionFactory;

    @BeforeClass
    public static void startDependencies() {
        databaseContext = startContext("DATABASE", "classpath:test-embedded-database.xml");
        hornetqContext = startContext("HORNETQ", "classpath:test-embedded-hornetq.xml");
        testContext = startContext("TEST", "classpath:test-context.xml");
    }

    @Before
    public void injectDependencies() {
        testContext.getAutowireCapableBeanFactory().autowireBean(this);
        Assert.assertNotNull("BRP DataSource niet gekoppeld", brpDataSource);
        Assert.assertNotNull("ConnectionFactory niet gekoppeld", connectionFactory);
    }

    @AfterClass
    public static void stopTestContext() {
        closeContext("TEST", testContext);
        closeContext("HORNETQ", hornetqContext);
        closeContext("DATABASE", databaseContext);
    }

    private static GenericXmlApplicationContext startContext(final String name, final String... configurations) {
        LOGGER.info("Starten {} ...", name);

        final GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.load(configurations);
        context.refresh();

        LOGGER.info("{} gestart", name);

        return context;
    }

    private static void closeContext(final String name, final Closeable context) {
        if (context != null) {
            try {
                LOGGER.info("Stoppen {} context ...", name);
                context.close();
                LOGGER.info("{} context gestopt", name);
            } catch (final Exception e) {
                LOGGER.warn("Probleem bij sluiten {} context", name, e);
            }
        }
    }
}
