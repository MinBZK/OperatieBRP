/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.register.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.support.GenericXmlApplicationContext;

public abstract class AbstractIT {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static Properties portProperties = new Properties();
    private static GenericXmlApplicationContext routeringContext;

    private GenericXmlApplicationContext testContext;

    private final List<String> contextResources = new ArrayList<>();

    protected AbstractIT() {
        contextResources.add("classpath:test-context.xml");
    }

    protected AbstractIT(final String... contextResources) {
        this();
        this.contextResources.addAll(Arrays.asList(contextResources));
    }

    @BeforeClass
    public static void startDependencies() throws IOException {
        try (ServerSocket routeringPort = new ServerSocket(0)) {
            LOG.info("Configuring activemq to port: " + routeringPort.getLocalPort());
            portProperties.setProperty("test.routering.port", Integer.toString(routeringPort.getLocalPort()));
        }

        final Properties routeringProperties = new Properties();
        routeringProperties.setProperty("routering.activemq.url", "nio://localhost:" + portProperties.getProperty("test.routering.port"));
        routeringProperties.setProperty("routering.activemq.data.directory", "target/activemq-data");
        routeringProperties.setProperty("routering.activemq.kahadb.directory", "target/activemq-kahadb");
        routeringProperties.setProperty("routering.activemq.scheduler.directory", "target/activemq-scheduler");

        routeringProperties.setProperty("routering.activemq.redelivery.maximum", "5");
        routeringProperties.setProperty("routering.activemq.redelivery.initial.delay", "5000");
        routeringProperties.setProperty("routering.activemq.redelivery.delay", "5000");
        routeringProperties.setProperty("routering.jmx.serialize.port", "0");

        // Start RouteringCentrale
        LOG.info("Starting embedded routering centrale ...");
        routeringContext = new GenericXmlApplicationContext();
        routeringContext.load("classpath:routering-runtime.xml");
        routeringContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", routeringProperties));
        routeringContext.refresh();
    }

    @Before
    public void injectDependencies() {
        // Create test context
        LOG.info("Starting test context ({}) ...", contextResources);
        testContext = new GenericXmlApplicationContext();
        testContext.load(contextResources.toArray(new String[]{}));
        testContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
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
    }

}
