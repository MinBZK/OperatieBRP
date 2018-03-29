/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.jmxcollector;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXServiceURL;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Test voor meter.
 */
@RunWith(MockitoJUnitRunner.class)
public class MeterTest {

    private static final Logger LOGGER = Logger.getLogger(MeterTest.class.getName());

    @Mock
    private Metingen metingen;

    @Mock
    private ApplicationContext mockContext;

    @Test
    public void test() throws Exception, MetingException {
        LOGGER.log(Level.INFO, "Startup...");

        final GraphiteMockServer graphiteMockServer = new GraphiteMockServer();
        try {
            final Thread thread = new Thread(graphiteMockServer);
            thread.start();

            try (final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:test-context.xml"})) {
                context.registerShutdownHook();

                final JMXConnectorServer testServerConnectorSsl = context.getBean("testServerConnector", JMXConnectorServer.class);
                final int testServerConnectorSslPort = testServerConnectorSsl.getAddress().getPort();

                // Setup client
                final Map<String, Object> environment = new HashMap<>();
                environment.put(JMXConnector.CREDENTIALS, new String[]{"admin", "admin"});

                try (final JMXConnector jmxc =
                             JMXConnectorFactory.connect(new JMXServiceURL("service:jmx:simple://localhost:" + testServerConnectorSslPort), environment)) {
                    // final MBeanServerConnection serverConnection = jmxc.getMBeanServerConnection();

                    final Meting meting = new Meting("testmeting", "nl.bzk.algemeen.test:name=TEST", "ReadonlyAttribuut", "WritableAttribuut");
                    final Map<String, List<Meting>> metingMap = new HashMap<>();
                    metingMap.put("test", Collections.singletonList(meting));
                    Mockito.when(metingen.getMetingenMap()).thenReturn(metingMap);
                    Mockito.when(mockContext.getBean("test", JMXConnector.class)).thenReturn(jmxc);
                    final Meter meter = new Meter(mockContext, metingen, "localhost", graphiteMockServer.getPort());

                    meter.voerMetingenUit();

                    // even wachten anders worden de resultaten te snel gehaald.
                    Thread.sleep(1000);

                    Assert.assertEquals(2, graphiteMockServer.getOntvangenMetingen().size());
                    Assert.assertTrue(graphiteMockServer.getOntvangenMetingen().get(0).startsWith("testmeting.ReadonlyAttribuut 42"));

                }
                LOGGER.log(Level.INFO, "Shutdown...");
            }
        } finally {
            graphiteMockServer.stop();
        }
        LOGGER.log(Level.INFO, "Done...");
    }

    @Test
    public void testFout() throws Exception, MetingException {
        LOGGER.log(Level.INFO, "Startup...");

        final GraphiteMockServer graphiteMockServer = new GraphiteMockServer();
        try {
            final Thread thread = new Thread(graphiteMockServer);
            thread.start();

            try (final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:test-context.xml"})) {
                context.registerShutdownHook();

                final JMXConnectorServer testServerConnectorSsl = context.getBean("testServerConnector", JMXConnectorServer.class);
                final int testServerConnectorSslPort = testServerConnectorSsl.getAddress().getPort();

                // Setup client
                final Map<String, Object> environment = new HashMap<>();
                environment.put(JMXConnector.CREDENTIALS, new String[]{"admin", "admin"});

                try (final JMXConnector jmxc =
                             JMXConnectorFactory
                                     .newJMXConnector(new JMXServiceURL("service:jmx:simple://localhost:" + testServerConnectorSslPort), environment)) {
                    //final MBeanServerConnection serverConnection = jmxc.getMBeanServerConnection();

                    final Meting meting = new Meting("testmeting", "nl.bzk.algemeen.test:name=BESTAATNIET", "ReadonlyAttribuut");
                    final Map<String, List<Meting>> metingMap = new HashMap<>();
                    metingMap.put("test", Collections.singletonList(meting));
                    Mockito.when(metingen.getMetingenMap()).thenReturn(metingMap);
                    Mockito.when(mockContext.getBean("test", JMXConnector.class)).thenReturn(jmxc);
                    final Meter meter = new Meter(mockContext, metingen, "localhost", graphiteMockServer.getPort());

                    meter.voerMetingenUit();

                    Assert.assertEquals(0, graphiteMockServer.getOntvangenMetingen().size());

                }
                LOGGER.log(Level.INFO, "Shutdown...");
            }
        } finally {
            graphiteMockServer.stop();
        }
        LOGGER.log(Level.INFO, "Done...");
    }
}
