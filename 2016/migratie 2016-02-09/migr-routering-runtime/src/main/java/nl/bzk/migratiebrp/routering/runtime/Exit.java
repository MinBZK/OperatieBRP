/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.routering.runtime;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.management.JMException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Shutdown VOISC.
 */
public final class Exit {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private Exit() {
        // Niet instantieerbaar
    }

    /**
     * Execute.
     *
     * @param args
     *            arguments
     * @throws IOException
     *             bij communicatie fouten
     * @throws JMException
     *             bij JMX fouten
     */
    public static void main(final String[] args) throws IOException, JMException {
        LOGGER.info("Exit");
        final Properties properties = new Properties();
        try (InputStream is = Exit.class.getResourceAsStream("/routering-runtime.properties")) {
            properties.load(is);
        }

        final String registryPort = properties.getProperty("routering.rmi.registry", "1099");
        final String serverPort = properties.getProperty("routering.jmx.service", "9875");
        final String url = "service:jmx:rmi://localhost:" + serverPort + "/jndi/rmi://localhost:" + registryPort + "/jmxrmi";
        LOGGER.info("URL: {}", url);

        final JMXServiceURL jmxUrl = new JMXServiceURL(url);

        final JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxUrl, null);
        final MBeanServerConnection serverConnection = jmxConnector.getMBeanServerConnection();
        final ObjectName name = new ObjectName("nl.bzk.migratiebrp.routering:name=ROUTERING");

        LOGGER.info("Invoke");
        serverConnection.invoke(name, "afsluiten", null, null);
        LOGGER.info("Done");
    }
}
