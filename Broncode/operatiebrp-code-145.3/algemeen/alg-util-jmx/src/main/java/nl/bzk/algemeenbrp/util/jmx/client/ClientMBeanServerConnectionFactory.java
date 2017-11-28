/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.management.MBeanServerConnection;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import nl.bzk.algemeenbrp.util.jmx.message.Request;
import nl.bzk.algemeenbrp.util.jmx.message.RequestAddNotificationListener;
import nl.bzk.algemeenbrp.util.jmx.message.RequestExecute;
import nl.bzk.algemeenbrp.util.jmx.message.Response;

/**
 * Client MBeanServerConnection factory.
 */
final class ClientMBeanServerConnectionFactory {

    private static final Class<?>[] ADD_LISTENER_REMOTE = {ObjectName.class, ObjectName.class,
            NotificationFilter.class, Object.class};
    private static final Class<?>[] ADD_LISTENER_LOCAL = {ObjectName.class, NotificationListener.class,
            NotificationFilter.class, Object.class};

    private static final int ADD_LISTENER_PARAMETER_NAME = 0;
    private static final int ADD_LISTENER_PARAMETER_LISTENER = 1;
    private static final int ADD_LISTENER_PARAMETER_FILTER = 2;
    private static final int ADD_LISTENER_PARAMETER_HANDBACK = 3;

    /**
     * Create a new client mbean server connection factory.
     */
    ClientMBeanServerConnectionFactory() {
    }

    /**
     * Create a new mbean server connection.
     * @param clientConnection client connection
     * @return mbean server connection
     */
    MBeanServerConnection createConnection(final ClientConnection clientConnection) {
        return (MBeanServerConnection) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{MBeanServerConnection.class}, new MBeanServerConnectionProxy(clientConnection));
    }

    /**
     * MBeanServerConnection proxy.
     */
    private static class MBeanServerConnectionProxy implements InvocationHandler {

        private static final Map<String, Handler> HANDLERS = new HashMap<>();

        static {
            HANDLERS.put("createMBean", RequestHandler::handleExecute);
            HANDLERS.put("unregisterMBean", RequestHandler::handleExecute);
            HANDLERS.put("getObjectInstance", RequestHandler::handleExecute);
            HANDLERS.put("queryMBeans", RequestHandler::handleExecute);
            HANDLERS.put("queryNames", RequestHandler::handleExecute);
            HANDLERS.put("isRegistered", RequestHandler::handleExecute);
            HANDLERS.put("getMBeanCount", RequestHandler::handleExecute);
            HANDLERS.put("getAttribute", RequestHandler::handleExecute);
            HANDLERS.put("getAttributes", RequestHandler::handleExecute);
            HANDLERS.put("setAttribute", RequestHandler::handleExecute);
            HANDLERS.put("setAttributes", RequestHandler::handleExecute);
            HANDLERS.put("invoke", RequestHandler::handleExecute);
            HANDLERS.put("getDefaultDomain", RequestHandler::handleExecute);
            HANDLERS.put("getDomains", RequestHandler::handleExecute);
            HANDLERS.put("getMBeanInfo", RequestHandler::handleExecute);
            HANDLERS.put("isInstanceOf", RequestHandler::handleExecute);
            HANDLERS.put("addNotificationListener", RequestHandler::handleAddNotificationListener);
        }

        private final ClientConnection clientConnection;

        /**
         * Create a new mbean server connection proxy.
         * @param clientConnection client connection
         */
        MBeanServerConnectionProxy(final ClientConnection clientConnection) {
            this.clientConnection = clientConnection;
        }

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Exception {
            final Handler handler = HANDLERS.get(method.getName());
            if (handler == null) {
                throw new UnsupportedOperationException(method.getName() + " not supported");
            }

            return handler.handle(clientConnection, method, args);
        }
    }

    /**
     * Request handler.
     */
    private static final class RequestHandler {

        private RequestHandler() {
            // Not instantiated
        }

        private static Object handleExecute(final ClientConnection clientConnection, final Method method,
                                            final Object[] args) throws Exception {
            final Request request = new RequestExecute(method.getName(), method.getParameterTypes(), args);
            return handleRequest(clientConnection, request);
        }

        private static Object handleRequest(final ClientConnection clientConnection, final Request request)
                throws Exception {
            final Response response = clientConnection.handleRequest(request);

            if (response.getException() == null) {
                return response.getResult();
            } else {
                throw response.getException();
            }
        }

        private static Object handleAddNotificationListener(final ClientConnection clientConnection,
                                                            final Method method, final Object[] args) throws Exception {
            if (Arrays.equals(ADD_LISTENER_LOCAL, method.getParameterTypes())) {
                // The notification listener is an object in this JVM
                final ObjectName name = (ObjectName) args[ADD_LISTENER_PARAMETER_NAME];
                final NotificationFilter filter = (NotificationFilter) args[ADD_LISTENER_PARAMETER_FILTER];

                final String notificationListenerId = clientConnection.registerNotificationListener(
                        (NotificationListener) args[ADD_LISTENER_PARAMETER_LISTENER],
                        args[ADD_LISTENER_PARAMETER_HANDBACK]);

                final Request request = new RequestAddNotificationListener(notificationListenerId, name, filter);
                try {
                    return handleRequest(clientConnection, request);
                } catch (final Exception e) {
                    clientConnection.removeNotificationListener(notificationListenerId);
                    throw e;
                }
            } else if (Arrays.equals(ADD_LISTENER_REMOTE, method.getParameterTypes())) {
                // The notification listener is a remote object
                return handleExecute(clientConnection, method, args);
            } else {
                // Should not happen
                throw new IllegalArgumentException("Unknown method parameters for addNotificationListener");
            }
        }
    }

    /**
     * Handler.
     */
    @FunctionalInterface
    private interface Handler {

        /**
         * Handle the method call.
         * @param connection client connection
         * @param method method
         * @param args arguments
         * @return result
         * @throws Exception on exception
         */
        Object handle(ClientConnection connection, Method method, Object[] args) throws Exception;
    }
}
