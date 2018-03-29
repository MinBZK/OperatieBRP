/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.routering.runtime;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import javax.management.JMException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsTemplate;

public abstract class AbstractIT {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    protected static Properties portProperties = new Properties();

    private Main subject;
    protected GenericXmlApplicationContext testContext;

    @BeforeClass
    public static void startDependencies() throws IOException {
        try (ServerSocket routeringPort = new ServerSocket(0)) {
            LOGGER.info("Configuring activemq to port: " + routeringPort.getLocalPort());
            portProperties.setProperty("test.routering.port", Integer.toString(routeringPort.getLocalPort()));
        }
        try (ServerSocket jmxPort = new ServerSocket(0)) {
            LOGGER.info("Configuring jmx to port: " + jmxPort.getLocalPort());
            portProperties.setProperty("test.jmx.port", Integer.toString(jmxPort.getLocalPort()));
        }
    }

    @Before
    public void start() {
        final Properties routeringProperties = new Properties();
        routeringProperties.setProperty("routering.activemq.url", "nio://localhost:" + portProperties.getProperty("test.routering.port"));
        routeringProperties.setProperty("routering.activemq.data.directory", "target/activemq-data");
        routeringProperties.setProperty("routering.activemq.kahadb.directory", "target/activemq-kahadb");
        routeringProperties.setProperty("routering.activemq.scheduler.directory", "target/activemq-scheduler");

        routeringProperties.setProperty("routering.activemq.redelivery.maximum", "2");
        routeringProperties.setProperty("routering.activemq.redelivery.initial.delay", "1000");
        routeringProperties.setProperty("routering.activemq.redelivery.delay", "1000");
        routeringProperties.setProperty("routering.jmx.serialize.port", portProperties.getProperty("test.jmx.port"));

        subject = new Main(new PropertiesPropertySource("configuration", routeringProperties));
        try {
            subject.start();
        } catch (final Exception e) {
            LOGGER.error("Fout tijdens opstarten applicatie", e);
            throw e;
        }

        // Create test context
        testContext = new GenericXmlApplicationContext();
        testContext.load("classpath:test-context.xml");
        testContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        testContext.refresh();
    }

    @After
    public void shutdown() throws IOException, JMException {
        if (testContext != null) {
            try {
                testContext.close();
            } catch (final Exception e) {
                LOGGER.warn("Probleem bij sluiten TEST context", e);
            }
        }

        subject.stop();
    }

    protected void browse(final JmsTemplate jmsTemplate, final String queue) {
        final AtomicInteger counter = new AtomicInteger();
        jmsTemplate.browse(queue, (BrowserCallback<Void>) (session, browser) -> {
            final Enumeration e = browser.getEnumeration();
            while (e.hasMoreElements()) {
                final Object message = e.nextElement();
                LOGGER.info("Message on " + browser.getQueue() + ": " + message);
                counter.getAndIncrement();
            }
            return null;
        });
        LOGGER.info(counter.get() + " berichten op " + queue);
    }
}
