/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime;

import java.io.Closeable;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.support.GenericXmlApplicationContext;

public class AbstractIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static GenericXmlApplicationContext databaseContext;
    private static GenericXmlApplicationContext hornetqContext;
    private static GenericXmlApplicationContext testContext;

    @BeforeClass
    public static void startDependencies() {
        databaseContext = startContext("DATABASE", "classpath:test-embedded-database.xml");
        hornetqContext = startContext("HORNETQ", "classpath:test-embedded-hornetq.xml");
        testContext = startContext("TEST", "classpath:test-context.xml");
    }

    @Before
    public void injectDependencies() {
        testContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Before
    public void start() {
        LOGGER.info("Starten MAIN (before test)");
        Main.main(new String[] {});
        LOGGER.info("MAIN gestart (before test)");
    }

    @After
    public void shutdown() throws InterruptedException {
        LOGGER.info("Stoppen MAIN (after test)");
        Main.stop();
        LOGGER.info("MAIN gestopt (after test)");
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
