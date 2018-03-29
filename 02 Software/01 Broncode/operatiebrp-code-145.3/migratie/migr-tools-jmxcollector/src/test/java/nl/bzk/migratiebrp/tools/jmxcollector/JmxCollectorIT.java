/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.jmxcollector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXServiceURL;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JmxCollectorIT {

    private static final Logger LOGGER = Logger.getLogger(JmxCollectorIT.class.getName());

    @Test
    public void test() throws Exception, MetingException {
        LOGGER.log(Level.INFO, "Startup...");
        try (final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:test-context.xml"})) {
            context.registerShutdownHook();

            final JMXConnectorServer testServerConnectorSsl = context.getBean("testServerConnector", JMXConnectorServer.class);
            final int testServerConnectorSslPort = testServerConnectorSsl.getAddress().getPort();

            // Setup client
            final Map<String, Object> environment = new HashMap<>();
            environment.put(JMXConnector.CREDENTIALS, new String[]{"admin", "admin"});

            try (final JMXConnector jmxc =
                         JMXConnectorFactory.connect(new JMXServiceURL("service:jmx:simple://localhost:" + testServerConnectorSslPort), environment)) {
                final MBeanServerConnection serverConnection = jmxc.getMBeanServerConnection();

                final Meting meting = new Meting("testmeting", "nl.bzk.algemeen.test:name=TEST", "ReadonlyAttribuut");
                final List<MetingResultaat> metingResultaat = meting.haalMetingOp(serverConnection);
                Assert.assertNotNull(metingResultaat.get(0).getResultaat());
                Assert.assertTrue(metingResultaat.get(0).getResultaat().startsWith("testmeting.ReadonlyAttribuut 42"));
                Assert.assertEquals("testmeting", meting.getNaam());
            }

            LOGGER.log(Level.INFO, "Shutdown...");
        }
        LOGGER.log(Level.INFO, "Done...");
    }

    @Test(expected = MetingException.class)
    public void testNietBestaandAttribuut() throws Exception, MetingException {
        LOGGER.log(Level.INFO, "Startup...");
        try (final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:test-context.xml"})) {
            context.registerShutdownHook();

            final JMXConnectorServer testServerConnectorSsl = context.getBean("testServerConnector", JMXConnectorServer.class);
            final int testServerConnectorSslPort = testServerConnectorSsl.getAddress().getPort();

            // Setup client
            final Map<String, Object> environment = new HashMap<>();
            environment.put(JMXConnector.CREDENTIALS, new String[]{"admin", "admin"});

            try (final JMXConnector jmxc =
                         JMXConnectorFactory.connect(new JMXServiceURL("service:jmx:simple://localhost:" + testServerConnectorSslPort), environment)) {
                final MBeanServerConnection serverConnection = jmxc.getMBeanServerConnection();

                final Meting meting = new Meting("testmeting", "nl.bzk.algemeen.test:name=BESTAATNIET", "ReadonlyAttribuut");
                meting.haalMetingOp(serverConnection);
            }
            LOGGER.log(Level.INFO, "Shutdown...");
        }
        LOGGER.log(Level.INFO, "Done...");
    }

    @Test
    public void testLeegAttribuut() throws Exception, MetingException {
        LOGGER.log(Level.INFO, "Startup...");
        try (final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:test-context.xml"})) {
            context.registerShutdownHook();

            final JMXConnectorServer testServerConnectorSsl = context.getBean("testServerConnector", JMXConnectorServer.class);
            final int testServerConnectorSslPort = testServerConnectorSsl.getAddress().getPort();

            // Setup client
            final Map<String, Object> environment = new HashMap<>();
            environment.put(JMXConnector.CREDENTIALS, new String[]{"admin", "admin"});

            try (final JMXConnector jmxc =
                         JMXConnectorFactory.connect(new JMXServiceURL("service:jmx:simple://localhost:" + testServerConnectorSslPort), environment)) {
                final MBeanServerConnection serverConnection = jmxc.getMBeanServerConnection();

                final Meting meting = new Meting("testmeting", "nl.bzk.algemeen.test:name=TEST", "NullAttribuut");
                meting.haalMetingOp(serverConnection);
                final List<MetingResultaat> metingResultaat = meting.haalMetingOp(serverConnection);
                Assert.assertNotNull(metingResultaat.get(0).getResultaat());
            }
            LOGGER.log(Level.INFO, "Shutdown...");
        }
        LOGGER.log(Level.INFO, "Done...");
    }
}

