/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.remote.JMXConnectionNotification;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXServiceURL;
import javax.security.auth.Subject;
import nl.bzk.algemeenbrp.util.jmx.environment.Environment;
import nl.bzk.algemeenbrp.util.jmx.socket.JMXSocketFactory;

/**
 * JMX Client connector.
 */
final class ClientConnector implements JMXConnector {

    private static final Logger LOGGER = Logger.getLogger(ClientConnector.class.getName());

    private static final ClientMBeanServerConnectionFactory FACTORY = new ClientMBeanServerConnectionFactory();
    private static final AtomicLong NOTIFICATION_SEQUENCE = new AtomicLong(1);

    private final JMXServiceURL serviceUrl;
    private final Map<String, ?> baseEnvironment;
    private final NotificationBroadcasterSupport notificationBroadcaster = new NotificationBroadcasterSupport(Executors.newCachedThreadPool());

    private ClientConnection clientConnection;


    /**
     * Create a new client connector.
     * @param serviceUrl jmx service url
     * @param baseEnvironment jmx environment
     */
    ClientConnector(final JMXServiceURL serviceUrl, final Map<String, ?> baseEnvironment) {
        this.serviceUrl = serviceUrl;
        this.baseEnvironment = baseEnvironment;
    }

    @Override
    public synchronized void close() throws IOException {
        if (clientConnection != null) {
            clientConnection.close();
            clientConnection = null;
        }
    }

    @Override
    public synchronized void connect() throws IOException {
        connect(null);
    }

    @Override
    public synchronized void connect(final Map<String, ?> connectEnvironment) throws IOException {
        LOGGER.log(Level.FINE, "connect(environment={0})", connectEnvironment);
        if (clientConnection == null) {
            final Map<String, Object> environment = new HashMap<>();
            if (baseEnvironment != null) {
                environment.putAll(baseEnvironment);
            }
            if (connectEnvironment != null) {
                environment.putAll(connectEnvironment);
            }

            LOGGER.log(Level.FINE, "Creating client connection");
            final JMXSocketFactory socketFactory = Environment.determineSocketFactory(environment);
            final Object credentials = Environment.determineCredentials(environment);
            final int requestTimeout = Environment.determineRequestTimeout(environment);
            clientConnection = new ClientConnection(this, socketFactory, serviceUrl, credentials, requestTimeout);
            try {
                LOGGER.log(Level.FINE, "Connecting client connection");
                clientConnection.connect();
            } catch (final RuntimeException | IOException e) {
                LOGGER.log(Level.FINE, "Exception during connect", e);
                // The JMXConnectorFactory class does not close the JMXConnector
                // if the connect has failed
                clientConnection.close();
                clientConnection = null;
                throw e;
            }
        }
        LOGGER.log(Level.FINE, "Client connection connected");
    }

    @Override
    public String getConnectionId() throws IOException {
        if (clientConnection == null) {
            throw new IOException("Not connected");
        }
        return clientConnection.getConnectionId();
    }

    @Override
    public MBeanServerConnection getMBeanServerConnection() throws IOException {
        return getMBeanServerConnection(null);
    }

    @Override
    public MBeanServerConnection getMBeanServerConnection(final Subject subject) throws IOException {
        // TODO: handle subject delegation
        if (clientConnection == null) {
            throw new IOException("Not connected");
        }
        return FACTORY.createConnection(clientConnection);
    }

    /**
     * Send a connection notification to registered connection notification listeners.
     * @param type notification type
     * @param connectionId connection id
     */
    void sendConnectionNotification(final String type, final String connectionId) {
        notificationBroadcaster.sendNotification(new JMXConnectionNotification(type, "", connectionId, NOTIFICATION_SEQUENCE.getAndIncrement(), null, null));
    }

    @Override
    public void addConnectionNotificationListener(final NotificationListener listener, final NotificationFilter filter,
                                                  final Object handback) {
        notificationBroadcaster.addNotificationListener(listener, filter, handback);
    }

    @Override
    public void removeConnectionNotificationListener(final NotificationListener listener) throws ListenerNotFoundException {
        notificationBroadcaster.removeNotificationListener(listener);
    }

    @Override
    public void removeConnectionNotificationListener(final NotificationListener listener, final NotificationFilter filter,
                                                     final Object handback) throws ListenerNotFoundException {
        notificationBroadcaster.removeNotificationListener(listener, filter, handback);
    }

}
