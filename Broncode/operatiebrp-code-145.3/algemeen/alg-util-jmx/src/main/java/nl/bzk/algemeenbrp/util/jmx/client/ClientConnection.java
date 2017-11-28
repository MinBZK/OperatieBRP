/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.client;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.NotificationListener;
import javax.management.remote.JMXConnectionNotification;
import javax.management.remote.JMXServiceURL;
import nl.bzk.algemeenbrp.util.jmx.message.Request;
import nl.bzk.algemeenbrp.util.jmx.message.RequestLogoff;
import nl.bzk.algemeenbrp.util.jmx.message.RequestLogon;
import nl.bzk.algemeenbrp.util.jmx.message.Response;
import nl.bzk.algemeenbrp.util.jmx.socket.JMXSocketFactory;
import nl.bzk.algemeenbrp.util.jmx.stream.MessageInputStream;
import nl.bzk.algemeenbrp.util.jmx.stream.MessageOutputStream;
import nl.bzk.algemeenbrp.util.jmx.utils.IOUtils;

/**
 * Client connection.
 */
final class ClientConnection {

    private static final Logger LOGGER = Logger.getLogger(ClientConnection.class.getName());

    private final ClientConnector connector;
    private final JMXSocketFactory socketFactory;
    private final JMXServiceURL serviceUrl;
    private final Object credentials;
    private final int requestTimeout;

    private Socket socket;
    private MessageOutputStream output;

    private ClientListener clientListener;
    private Thread clientListenerThread;

    private String connectionId;

    /**
     * Create a new client connection.
     * @param connector client connector (to send notifications)
     * @param serviceUrl jmx service url
     */
    ClientConnection(final ClientConnector connector, final JMXSocketFactory socketFactory, final JMXServiceURL serviceUrl, final Object credentials,
                     final int requestTimeout) {
        this.connector = connector;
        this.socketFactory = socketFactory;
        this.serviceUrl = serviceUrl;
        this.credentials = credentials;
        this.requestTimeout = requestTimeout;
    }

    /**
     * Get the connection id returned when the logon command is executed.
     * @return connection id
     * @throws IOException if the connection is not connected
     */
    String getConnectionId() throws IOException {
        if (connectionId == null) {
            throw new IOException("Not connected");
        }
        return connectionId;
    }

    /**
     * Connect the connection to the server.
     * @throws IOException if an I/O error occurs when connecting
     */
    void connect() throws IOException {
        LOGGER.log(Level.FINE, "Connecting to {0}:{1,number,#####} ...",
                new Object[]{serviceUrl.getHost(), serviceUrl.getPort()});
        socket = socketFactory.createSocket(serviceUrl);
        socket.setSoTimeout(0);

        // The socket InputStream and OutputStream are not closed directly. They
        // are shutdown and closed via method calls on the socket itself.
        output = new MessageOutputStream(socket.getOutputStream());

        LOGGER.log(Level.FINE, "Starting receiver");
        clientListener = new ClientListener(new MessageInputStream(socket.getInputStream()), requestTimeout);
        clientListenerThread = new Thread(clientListener, "jmx-client-receiver");
        clientListenerThread.start();

        LOGGER.log(Level.FINE, "Sending logon request");
        final RequestLogon logon = new RequestLogon(credentials);

        LOGGER.log(Level.FINE, "Handling logon response");
        final Response logonResponse = handleRequest(logon);
        if (logonResponse.getException() != null) {
            LOGGER.log(Level.FINE, "Logon failed");
            throw new IOException("Could not logon", logonResponse.getException());
        }
        connectionId = (String) logonResponse.getResult();

        LOGGER.log(Level.FINE, "Connected; connectionId = {0}", connectionId);
        connector.sendConnectionNotification(JMXConnectionNotification.OPENED, connectionId);
    }

    /**
     * Close the connection.
     * @throws IOException if an I/O error occurs when closing the connection
     */
    void close() throws IOException {
        LOGGER.log(Level.FINE, "Sending logoff");
        try {
            handleRequest(new RequestLogoff());
        } catch (final IOException e) {
            LOGGER.log(Level.WARNING, "Unexpected exception when logging off", e);
        }

        LOGGER.log(Level.FINE, "Stopping client listener");
        clientListener.stop();

        IOUtils.closeSilently(socket);
        LOGGER.log(Level.FINE, "Closed");
        if (connectionId != null) {
            // Only send closed notification when we could connect succesfully
            connector.sendConnectionNotification(JMXConnectionNotification.CLOSED, connectionId);
        }
    }

    /**
     * Handle request.
     * @param request request
     * @return response
     * @throws IOException if an I/O error occurs when handling the request
     */
    Response handleRequest(final Request request) throws IOException {
        if (!clientListenerThread.isAlive()) {
            throw new IOException("Listener not running");
        }
        final ClientListener.FutureResponse waiter = clientListener.registerRequest(request);
        send(request);
        return waiter.getResponse();
    }

    private void send(final Request request) throws IOException {
        LOGGER.log(Level.FINE, "Sending request: {0}", request);
        synchronized (output) {
            output.write(request);
        }
        LOGGER.log(Level.FINE, "Request sent", request);
    }

    /**
     * Register a notification listener.
     * @param notificationListener the listener to request
     * @param handback handback given back to the listener
     * @return unique identification for notification listener
     */
    String registerNotificationListener(final NotificationListener notificationListener, final Object handback) {
        return clientListener.registerNotificationListener(notificationListener, handback);
    }

    /**
     * Remove a notification listener.
     * @param notificationListenerId the unique identification of the notifcation listener
     */
    void removeNotificationListener(final String notificationListenerId) {
        clientListener.removeNotificationListener(notificationListenerId);
    }

}
