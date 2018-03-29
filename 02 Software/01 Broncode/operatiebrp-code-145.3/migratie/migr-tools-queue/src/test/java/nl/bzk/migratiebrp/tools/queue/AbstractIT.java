/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.queue;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;
import javax.jms.ConnectionFactory;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;

public class AbstractIT {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static GenericXmlApplicationContext hornetqContext;
    private static GenericXmlApplicationContext testContext;

    private static final Properties portProperties = new Properties();

    @Autowired
    protected ConnectionFactory connectionFactory;

    @BeforeClass
    public static void startDependencies() throws IOException {
        try (ServerSocket hornetqPort = new ServerSocket(0)) {
            LOGGER.info("Configuring hornetq to port: " + hornetqPort.getLocalPort());
            portProperties.setProperty("test.hornetq.port", Integer.toString(hornetqPort.getLocalPort()));
        }

        hornetqContext = startContext("HORNETQ", portProperties, "classpath:test-embedded-hornetq.xml");
        testContext = startContext("TEST", portProperties, "classpath:test-context.xml");
    }

    @Before
    public void injectDependencies() {
        testContext.getAutowireCapableBeanFactory().autowireBean(this);
        Assert.assertNotNull("ConnectionFactory niet gekoppeld", connectionFactory);
    }

    @AfterClass
    public static void stopTestContext() {
        closeContext("TEST", testContext);
        closeContext("HORNETQ", hornetqContext);
    }

    private static GenericXmlApplicationContext startContext(final String name, final Properties properties, final String... configurations) {
        LOGGER.info("Starten {} ...", name);

        final GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.load(configurations);
        context.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", properties));
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

    protected Properties getPortProperties() {
        return portProperties;
    }
}
