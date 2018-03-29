/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.server;

import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.InstanceNotFoundException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.remote.JMXAuthenticator;
import javax.security.auth.Subject;
import nl.bzk.algemeenbrp.util.jmx.access.JMXAccessController;
import nl.bzk.algemeenbrp.util.jmx.exception.InvalidCredentialsException;
import nl.bzk.algemeenbrp.util.jmx.exception.NotLoggedOnException;
import nl.bzk.algemeenbrp.util.jmx.exception.UnknownRequestException;
import nl.bzk.algemeenbrp.util.jmx.message.Message;
import nl.bzk.algemeenbrp.util.jmx.message.Notification;
import nl.bzk.algemeenbrp.util.jmx.message.Request;
import nl.bzk.algemeenbrp.util.jmx.message.RequestAddNotificationListener;
import nl.bzk.algemeenbrp.util.jmx.message.RequestExecute;
import nl.bzk.algemeenbrp.util.jmx.message.RequestLogoff;
import nl.bzk.algemeenbrp.util.jmx.message.RequestLogon;
import nl.bzk.algemeenbrp.util.jmx.message.Response;
import nl.bzk.algemeenbrp.util.jmx.stream.MessageException;
import nl.bzk.algemeenbrp.util.jmx.stream.MessageInputStream;
import nl.bzk.algemeenbrp.util.jmx.stream.MessageOutputStream;
import nl.bzk.algemeenbrp.util.jmx.utils.IOUtils;

/**
 * Server connection.
 */
final class ServerConnection implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ServerConnection.class.getName());

    private final Socket socket;
    private final String connectionId;
    private final JMXAuthenticator authenticator;
    private final JMXAccessController accessController;

    private final MessageInputStream input;
    private final MessageOutputStream output;

    private final MBeanServer mBeanServer;
    private final List<NotificationSender> notificationSenders = new ArrayList<>();

    private Subject subject;
    private boolean stop = false;

    /**
     * Create a new server connection.
     * @param socket server socket to communicate via
     * @param connectionId connection id for this connection
     * @param authenticator authenticator
     * @param mBeanServer mbean server
     */
    ServerConnection(final Socket socket, final String connectionId, final JMXAuthenticator authenticator,
                     final JMXAccessController accessController, final MBeanServer mBeanServer) throws IOException {
        this.socket = socket;
        this.connectionId = connectionId;
        this.authenticator = authenticator;
        this.accessController = accessController;

        // The socket InputStream and OutputStream are not closed directly. They
        // are closed via method calls on the socket itself.
        input = new MessageInputStream(socket.getInputStream());
        output = new MessageOutputStream(socket.getOutputStream());

        this.mBeanServer = mBeanServer;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                // Receive request
                final Message message = input.read();

                if (message instanceof Request) {
                    // Handle request
                    final Response response = handleRequest((Request) message);

                    // Send response
                    sendResponse((Request) message, response);
                } else {
                    LOGGER.log(Level.WARNING, "Received unknown message type: {0}", message.getClass().getName());
                }
            } catch (final EOFException e) {
                LOGGER.log(Level.FINE, "Client closed connection.", e);
                stop = true;
            } catch (final IOException e) {
                LOGGER.log(Level.WARNING, "Unexpected exception.", e);
                stop = true;
            }
        }

        LOGGER.log(Level.FINE, "Stopping server connection.");

        // Remove all remote notification listeners we registered
        for (final NotificationSender notificationSender : notificationSenders) {
            try {
                mBeanServer.removeNotificationListener(notificationSender.getObjectName(), notificationSender);
            } catch (final ListenerNotFoundException | InstanceNotFoundException e) {
                LOGGER.log(Level.FINE, "Cleanup of notification listener failed", e);
            }
        }

        // Shutdown
        IOUtils.closeSilently(socket);
        LOGGER.log(Level.FINE, "Server connection closed.");
    }

    private void sendResponse(final Request message, final Response response) throws IOException {
        try {
            output.write(response);
        } catch(final MessageException e) {
            final Response messageExceptionResponse = new Response(message.getRequestId(), e);
            output.write(messageExceptionResponse);
        }
    }

    boolean isStopped() {
        return stop;
    }

    private Response handleRequest(final Request request) {
        final Response response;
        if (request instanceof RequestLogon) {
            LOGGER.log(Level.FINE, "Handling logon request");
            response = handleLogon((RequestLogon) request);
            LOGGER.log(Level.FINE, "Logged on: {0}", subject);
        } else if (request instanceof RequestLogoff) {
            LOGGER.log(Level.FINE, "Handling logoff request");
            response = new Response(request.getRequestId(), null);
            stop = true;
            LOGGER.log(Level.FINE, "Logged off");
        } else {
            // Check logged on
            if (subject == null) {
                response = new Response(request.getRequestId(), new NotLoggedOnException());
            } else {
                if (request instanceof RequestExecute) {
                    LOGGER.log(Level.FINE, "Handling execute request: {0}", request);
                    response = handleExecute((RequestExecute) request);
                    LOGGER.log(Level.FINE, "Execute response: {0}", response);
                } else if (request instanceof RequestAddNotificationListener) {
                    LOGGER.log(Level.FINE, "Handling add notification request: {0}", request);
                    response = handleAddNotificationListener((RequestAddNotificationListener) request);
                    LOGGER.log(Level.FINE, "Add notification response: {0}", response);
                } else {
                    LOGGER.log(Level.WARNING, "Received unknown request type: {0}", request.getClass().getName());
                    response = new Response(request.getRequestId(), new UnknownRequestException());
                }
            }
        }
        return response;
    }

    /**
     * Handle logon.
     * @param request request
     * @return response
     */
    private Response handleLogon(final RequestLogon request) {
        try {
            subject = authenticator.authenticate(request.getCredentials());
            return new Response(request.getRequestId(), connectionId);
        } catch (final SecurityException e) {
            LOGGER.log(Level.WARNING, "Invalid logon attempt.", e);
            return new Response(request.getRequestId(), new InvalidCredentialsException());
        }
    }

    /**
     * Handle execute.
     * @param request request
     * @return response
     */
    private Response handleExecute(final RequestExecute request) {
        // Check method
        final String methodName = request.getMethodName();
        final Method method;
        try {
            // Check the method is in the MBeanServerConnection class
            MBeanServerConnection.class.getMethod(methodName, request.getParameterClasses());
            // Get the 'real' method to invoke
            method = mBeanServer.getClass().getMethod(methodName, request.getParameterClasses());
        } catch (final ReflectiveOperationException e) {
            LOGGER.log(Level.WARNING, "Illegal method: " + methodName, e);
            return new Response(request.getRequestId(), new SecurityException("Illegal method"));
        }

        // Check access
        try {
            accessController.checkAccess(subject, request.getMethodName(), request.getParameterValues());
        } catch (final SecurityException e) {
            LOGGER.log(Level.WARNING, "No access to: " + methodName, e);
            return new Response(request.getRequestId(), new SecurityException("Illegal access"));
        }

        // Invoke
        Response response = null;
        try {
            final Object result = method.invoke(mBeanServer, request.getParameterValues());
            response = new Response(request.getRequestId(), result);
        } catch (final InvocationTargetException e) {
            if (e.getCause() instanceof Exception) {
                response = new Response(request.getRequestId(), (Exception) e.getCause());
            } else {
                response = new Response(request.getRequestId(), e);
            }
        } catch (final ReflectiveOperationException e) {
            return new Response(request.getRequestId(), e);
        }

        return response;
    }

    /**
     * Handle adding a notification listener.
     * @param request request
     * @return response
     */
    private Response handleAddNotificationListener(final RequestAddNotificationListener request) {
        // Listener
        final NotificationSender notificationSender = new NotificationSender(request.getNotificationListenerId(),
                request.getName());

        // Check access
        try {
            accessController.checkAccess(subject, "addNotificationListener", new Object[]{request.getName(), notificationSender, request.getFilter(), null});
        } catch (final SecurityException e) {
            LOGGER.log(Level.WARNING, "No access to: addNotificationListener", e);
            return new Response(request.getRequestId(), new SecurityException("Illegal access"));
        }

        // Invoke
        try {
            mBeanServer.addNotificationListener(request.getName(), notificationSender, request.getFilter(), null);
            notificationSenders.add(notificationSender);
            return new Response(request.getRequestId(), null);
        } catch (final OperationsException e) {
            return new Response(request.getRequestId(), e);
        }
    }

    /**
     * Notification listener that sends the notification to the client.
     */
    private class NotificationSender implements NotificationListener {

        private final String notificationListenerId;
        private final ObjectName objectName;

        /**
         * Constructor.
         * @param notificationListenerId notification listener id sent with the {@link Notification}
         * @param objectName object name
         */
        NotificationSender(final String notificationListenerId, final ObjectName objectName) {
            this.notificationListenerId = notificationListenerId;
            this.objectName = objectName;
        }

        /**
         * @return object name
         */
        ObjectName getObjectName() {
            return objectName;
        }

        @Override
        public void handleNotification(final javax.management.Notification notification, final Object handback) {
            LOGGER.log(Level.FINE, "Received notification");
            if (stop) {
                // Do not send notifications anymore when the server connection
                // is stopped.
                return;
            }
            final Notification response = new Notification(notificationListenerId, notification);
            try {
                LOGGER.log(Level.FINE, "Sending notification");
                output.write(response);
                LOGGER.log(Level.FINE, "Notification sent");
            } catch (final IOException e) {
                LOGGER.log(Level.WARNING,
                        "Unexpected exception during sending notification. Stopping server connection.", e);
                stop = true;
            }
        }
    }
}
