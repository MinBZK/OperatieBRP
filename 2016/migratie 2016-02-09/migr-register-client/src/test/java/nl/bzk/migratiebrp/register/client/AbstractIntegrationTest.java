/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.register.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.support.GenericXmlApplicationContext;

public abstract class AbstractIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static GenericXmlApplicationContext databaseContext;
    private static GenericXmlApplicationContext routeringContext;
    private GenericXmlApplicationContext testContext;

    private final List<String> contextResources = new ArrayList<>();

    protected AbstractIntegrationTest() {
        contextResources.add("classpath:test-context.xml");
    }

    protected AbstractIntegrationTest(final String... contextResources) {
        this();
        this.contextResources.addAll(Arrays.asList(contextResources));
    }

    @BeforeClass
    public static void startDependencies() {
        // Start DB
        LOG.info("Starting embedded database server ...");
        databaseContext = new GenericXmlApplicationContext();
        databaseContext.load("classpath:test-embedded-database.xml");
        databaseContext.refresh();

        // Start RouteringCentrale
        LOG.info("Starting embedded routering centrale ...");
        routeringContext = new GenericXmlApplicationContext();
        routeringContext.load("classpath:routering-runtime.xml");
        routeringContext.refresh();
    }

    @Before
    public void injectDependencies() {

        // Create test context
        LOG.info("Starting test context ({}) ...", contextResources);
        testContext = new GenericXmlApplicationContext();
        testContext.load(contextResources.toArray(new String[] {}));
        testContext.refresh();

        testContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @After
    public void stopTestContext() {
        testContext.close();
    }

    @AfterClass
    public static void stopDependencies() {
        routeringContext.close();
        databaseContext.close();
    }

}
