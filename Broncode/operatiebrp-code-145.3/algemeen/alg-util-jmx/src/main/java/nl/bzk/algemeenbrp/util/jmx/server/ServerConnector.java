/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.MBeanServer;
import javax.management.remote.JMXAuthenticator;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXServiceURL;
import nl.bzk.algemeenbrp.util.jmx.access.JMXAccessController;
import nl.bzk.algemeenbrp.util.jmx.environment.Environment;
import nl.bzk.algemeenbrp.util.jmx.socket.JMXSocketFactory;

/**
 * Server connector.
 */
final class ServerConnector extends JMXConnectorServer {

    private static final Logger LOGGER = Logger.getLogger(ServerConnector.class.getName());

    private JMXServiceURL url;
    private final Map<String, ?> environment;
    private Thread serverListenerThread;
    private ServerListener serverListener;

    /**
     * Create a new server connector.
     * @param url jmx service url
     * @param environment jmx environment
     * @param server mbean server
     */
    ServerConnector(final JMXServiceURL url, final Map<String, ?> environment, final MBeanServer server) {
        super(server);
        this.url = url;
        this.environment = environment == null ? new HashMap<>() : new HashMap<>(environment);
    }

    @Override
    public JMXServiceURL getAddress() {
        return url;
    }

    void updateAddress(final int localPort) {
        try {
            url = new JMXServiceURL(url.getProtocol(), url.getHost(), localPort);
        } catch (final MalformedURLException e) {
            LOGGER.log(Level.INFO, "Could not update url in JMXConnectorServer to reflect bound port", e);
        }
    }

    @Override
    public Map<String, ?> getAttributes() {
        return Collections.unmodifiableMap(environment);
    }

    @Override
    public boolean isActive() {
        return serverListenerThread != null && serverListenerThread.isAlive();
    }

    @Override
    public synchronized void start() throws IOException {
        if (serverListenerThread != null) {
            return;
        }

        final JMXSocketFactory socketFactory = Environment.determineSocketFactory(environment);
        final JMXAuthenticator authenticator = Environment.determineAuthenticator(environment);
        final JMXAccessController accessController = Environment.determineAccessController(environment);
        final int threadPriority = Environment.determineThreadPriority(environment);
        serverListener = new ServerListener(this, socketFactory, authenticator, accessController, threadPriority);
        serverListenerThread = new Thread(serverListener, "simple-jmx-server-" + serverListener.getServerId());
        serverListenerThread.setPriority(threadPriority);
        serverListenerThread.start();
    }

    @Override
    public void stop() throws IOException {
        LOGGER.log(Level.FINE, "Stopping; interrupting listener thread");
        serverListener.stop();
        try {
            LOGGER.log(Level.FINE, "Joining listener thread");
            serverListenerThread.join();
        } catch (final InterruptedException e) {
            LOGGER.log(Level.FINE, "Stop interrupted");
            Thread.currentThread().interrupt();
        }
        LOGGER.log(Level.FINE, "Server stopped");
    }

}
