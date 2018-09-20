/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.command;

import nl.bzk.migratiebrp.isc.jbpm.command.client.CommandClient;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmInvoker;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;

public class AbstractIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static GenericXmlApplicationContext databaseContext;
    private static GenericXmlApplicationContext commandContext;
    private static GenericXmlApplicationContext testContext;

    protected JbpmInvoker jbpmInvoker;

    @Autowired
    protected CommandClient commandClient;

    @BeforeClass
    public static void startDependencies() {
        // Start DB
        LOG.info("Starten DATABASE context ...");
        databaseContext = new GenericXmlApplicationContext();
        databaseContext.load("classpath:test-embedded-database.xml");
        databaseContext.refresh();
        LOG.info("DATABASE context gestart.");

        // Start COMMAND
        LOG.info("Starten COMMAND context ...");
        commandContext = new GenericXmlApplicationContext();
        commandContext.load("classpath:test-embedded-command.xml");
        commandContext.refresh();
        LOG.info("COMMAND context gestart.");

        // Create test context
        LOG.info("Starten TEST context ...");
        testContext = new GenericXmlApplicationContext();
        testContext.load("classpath:test-context.xml");
        testContext.refresh();
        LOG.info("TEST context gestart.");
    }

    @Before
    public void injectDependencies() {
        jbpmInvoker = commandContext.getBean(JbpmInvoker.class);
        testContext.getAutowireCapableBeanFactory().autowireBean(this);
        commandClient = testContext.getBean(CommandClient.class);
        Assert.assertNotNull(commandClient);
    }

    @AfterClass
    public static void stopTestContext() {
        if (testContext != null) {
            try {
                LOG.info("Stoppen TEST context ...");
                testContext.close();
                LOG.info("TEST context gestopt.");
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten TEST context", e);
            }
        }
        if (commandContext != null) {
            try {
                LOG.info("Stoppen COMMAND context ...");
                commandContext.close();
                LOG.info("COMMAND context gestopt.");
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten COMMAND context", e);
            }
        }
        if (databaseContext != null) {
            try {
                LOG.info("Stoppen DATABASE context ...");
                databaseContext.close();
                LOG.info("DATABASE context gestopt.");
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten DATABASE context", e);
            }
        }
    }

}
