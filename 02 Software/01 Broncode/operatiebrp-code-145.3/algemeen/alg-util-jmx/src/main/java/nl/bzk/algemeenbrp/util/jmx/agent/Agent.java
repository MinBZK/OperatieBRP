/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.agent;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXServiceURL;
import nl.bzk.algemeenbrp.util.jmx.SimpleJmx;
import nl.bzk.algemeenbrp.util.jmx.access.PropertiesAccessController;
import nl.bzk.algemeenbrp.util.jmx.authenticator.ExternalAuthenticator;
import nl.bzk.algemeenbrp.util.jmx.authenticator.PropertiesAuthenticator;
import nl.bzk.algemeenbrp.util.jmx.environment.Environment;
import nl.bzk.algemeenbrp.util.jmx.server.ServerProvider;
import nl.bzk.algemeenbrp.util.jmx.utils.PropertiesFileLoader;

/**
 * Agent class to startup the JMXConnectionServer
 */
public class Agent {

    private static final Logger LOGGER = Logger.getLogger(Agent.class.getName());

    public static final String ARGUMENT_HOST = "host";
    public static final String ARGUMENT_PORT = "port";
    public static final String ARGUMENT_LOGIN_CONFIG = "login.config";
    public static final String ARGUMENT_PASSWORD_FILE = "password.file";
    public static final String ARGUMENT_ACCESS_FILE = "access.file";

    private Agent() {
        throw new IllegalStateException("Do not instantiate");
    }

    /**
     * Starting point when used as a java agent loaded at startup
     * @param agentArgs arguments
     * @throws IOException when an I/O error occurs starting the jmx connector server.
     */
    public static void premain(final String agentArgs) throws IOException {
        agentmain(agentArgs);
    }

    /**
     * Starting point when used as a java agent loaded after startup
     * @param agentArgs argument
     * @throws IOException when an I/O error occurs starting the jmx connector server.
     */
    public static void agentmain(final String agentArgs) throws IOException {
        LOGGER.log(Level.FINE, "Configuring Simple-JMX server connector");
        final Map<String, String> arguments = ArgumentSplitter.split(agentArgs);

        // Connection
        final String host = arguments.getOrDefault(ARGUMENT_HOST, "0.0.0.0");
        final int port = Integer.parseInt(arguments.getOrDefault(ARGUMENT_PORT, "3481"));
        final JMXServiceURL url = new JMXServiceURL(SimpleJmx.PROTOCOL, host, port);

        // Environment
        final Map<String, Object> environment = new HashMap<>();

        // Environment - authentication
        if (arguments.containsKey(ARGUMENT_LOGIN_CONFIG)) {
            environment.put(Environment.KEY_AUTHENTICATOR, new ExternalAuthenticator(arguments.get(ARGUMENT_LOGIN_CONFIG)));
        } else if (arguments.containsKey(ARGUMENT_PASSWORD_FILE)) {
            environment.put(Environment.KEY_AUTHENTICATOR, new PropertiesAuthenticator(new PropertiesFileLoader(arguments.get(ARGUMENT_PASSWORD_FILE))));
        }

        // Environment - access control
        if (arguments.containsKey(ARGUMENT_ACCESS_FILE)) {
            environment.put(Environment.KEY_ACCESSCONTROLLER, new PropertiesAccessController(new PropertiesFileLoader(arguments.get(ARGUMENT_ACCESS_FILE))));
        }

        // MBean server
        final MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();

        // Server
        LOGGER.log(Level.FINE, "Starting Simple-JMX server connector");
        final JMXConnectorServer server = new ServerProvider().newJMXConnectorServer(url, environment, mbeanServer);
        server.start();

        LOGGER.log(Level.FINE, "Registering Simple-JMX server shutdown hook");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                LOGGER.log(Level.FINE, "Simple-JMX server connector stopping");
                server.stop();
                LOGGER.log(Level.FINE, "Simple-JMX server connector stopped");
            } catch (final IOException e) {
                LOGGER.log(Level.FINE, "Exception occurred during shutdown of Simple-JMX server.", e);
            }
        }, "simple-jmx-shutdown"));
        LOGGER.log(Level.FINE, "Simple-JMX server connector started");
    }
}
