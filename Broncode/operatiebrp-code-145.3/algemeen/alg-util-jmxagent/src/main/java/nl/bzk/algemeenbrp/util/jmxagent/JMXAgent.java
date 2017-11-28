/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmxagent;

import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import nl.bzk.algemeenbrp.util.jmx.SimpleJmx;
import nl.bzk.algemeenbrp.util.jmx.access.AllAccessController;
import nl.bzk.algemeenbrp.util.jmx.authenticator.PropertiesAuthenticator;

/**
 * Agent om de JMX Server Connector te starten.
 */
public class JMXAgent {

    private static final String DEFAULT_HOST = "0.0.0.0";
    private static final String DEFAULT_PORT = "3481";

    private JMXConnectorServer connectorServer;

    /**
     * Start de JMX Server Connector.
     */
    public final void start() {
        try {
            connectorServer = createConnectorServer();
            connectorServer.start();
        } catch (final IOException ex) {
            throw new JmxServerConnectorException("Kan JMX Server Connector niet starten", ex);
        }
    }

    /**
     * Stop de JMX Server Connector.
     */
    public final void stop() {
        if (connectorServer != null) {
            try {
                connectorServer.stop();
            } catch (final IOException ex) {
                throw new JmxServerConnectorException("Kan JMX Server Connector niet stoppen", ex);
            }
        }
    }

    /**
     * Maak een connector server instantie.
     *
     * @return connector server
     * @throws IOException bij fouten
     */
    private JMXConnectorServer createConnectorServer() throws IOException {
        final Properties configuration = readConfiguration();
        final MBeanServer server = locateMBeanServer();

        final JMXServiceURL url = new JMXServiceURL(SimpleJmx.PROTOCOL,
                configuration.getProperty("jmx.host", DEFAULT_HOST), Integer.parseInt(configuration.getProperty("jmx.port", DEFAULT_PORT)));

        final Map<String,Object> environment = new HashMap<>();
        Properties authentication = new Properties();
        authentication.setProperty("admin", "admin");
        environment.put("jmx.remote.authenticator", new PropertiesAuthenticator(authentication));
        environment.put("jmx.remote.accesscontroller", new AllAccessController());

        return JMXConnectorServerFactory.newJMXConnectorServer(url, environment, server);
    }

    /**
     * Lees de configuratie uit jmx.properties.
     *
     * @return configuratie
     * @throws IOException bij lees fouten, geen fout indien de file niet bestaat
     */
    private Properties readConfiguration() throws IOException {
        final Properties result = new Properties();
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("jmx.properties")) {
            if (input != null) {
                result.load(input);
            }
        }
        return result;
    }

    /**
     * Bepaal de MBeanServer.
     *
     * @return platform mbean server
     */
    private MBeanServer locateMBeanServer() {
        return ManagementFactory.getPlatformMBeanServer();
    }

    /**
     * JMX Server Connector exception.
     */
    public static final class JmxServerConnectorException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        /**
         * Constructor.
         *
         * @param message melding
         * @param cause oorzaak
         */
        public JmxServerConnectorException(final String message, final Throwable cause) {
            super(message, cause);
        }

    }
}
