/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.NotificationListener;
import nl.bzk.algemeenbrp.util.jmx.exception.RequestTimedOutException;
import nl.bzk.algemeenbrp.util.jmx.message.Message;
import nl.bzk.algemeenbrp.util.jmx.message.Notification;
import nl.bzk.algemeenbrp.util.jmx.message.Request;
import nl.bzk.algemeenbrp.util.jmx.message.Response;
import nl.bzk.algemeenbrp.util.jmx.stream.MessageInputStream;

/**
 * Listener for client connections.
 */
final class ClientListener implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ClientListener.class.getName());

    private final MessageInputStream input;
    private final int requestTimeout;
    private boolean stop = false;

    // This looks like a potential area for a memory leak if no response is ever received the request-id
    // and waiter will be registered for ever. However this seems very unlikely as the TCP protocol is
    // reliable and an answer should always be received (even if it is later than the client timeout).
    // If the server connection is dropped the whole client connection will be stopped and discarded.
    private final Map<String, FutureResponse> requests = new HashMap<>();
    private final Map<String, NotificationListenerData> notificationListeners = new HashMap<>();

    /**
     * Create a new client listener.
     * @param input input stream
     */
    ClientListener(final MessageInputStream input, final int requestTimeout) {
        this.input = input;
        this.requestTimeout = requestTimeout;
    }

    /**
     * Register a notification listener.
     * @param notificationListener the listener to request
     * @param handback handback given back to the listener
     * @return unique identification for notification listener
     */
    String registerNotificationListener(final NotificationListener notificationListener, final Object handback) {
        final String notificationListenerId = UUID.randomUUID().toString();
        notificationListeners.put(notificationListenerId, new NotificationListenerData(notificationListener, handback));
        return notificationListenerId;
    }

    /**
     * Remove a notification listener.
     * @param notificationListenerId the unique identification of the notification listener
     */
    void removeNotificationListener(final String notificationListenerId) {
        notificationListeners.remove(notificationListenerId);
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                LOGGER.log(Level.FINE, "Waiting for message");
                final Message message = input.read();
                LOGGER.log(Level.FINE, "Received message: {0}", message);

                if (message instanceof Response) {
                    LOGGER.log(Level.FINE, "Handling response");
                    handleResponse((Response) message);
                } else if (message instanceof Notification) {
                    LOGGER.log(Level.FINE, "Handling notification");
                    handleNotification((Notification) message);
                } else {
                    LOGGER.log(Level.WARNING, "Received unknown message type: {0}", message.getClass().getName());
                }
            } catch (final IOException e) {
                // When stopping we need to close the socket; that results in an
                // IOException from the inputstream.read which we don't count as
                // an error we need to report
                if (!stop) {
                    LOGGER.log(Level.SEVERE, "Unexpected exception when handling response/notification", e);
                    stop = true;
                }
            }
        }
    }

    /**
     * Stop the listener.
     */
    void stop() {
        stop = true;
    }

    /**
     * Is the listener stopped? A listener is stopped when the {@link #stop}
     * method has been called, or when a exception has occurred that the
     * listener cannot recover from.
     * @return true, if the listener has stopped, else false
     */
    boolean isStopped() {
        return stop;
    }

    /**
     * Register a new request and returns the 'future' (functions like a Future
     * but cannot be cancelled) to retrieve the result.
     * @param request request
     * @return future result
     * @throws IOException if an I/O exception occurs when registering the request
     */
    FutureResponse registerRequest(final Request request) throws IOException {
        if (stop) {
            throw new IOException("Client listener is stopped");
        }

        synchronized (requests) {
            final FutureResponse result = new FutureResponse(requestTimeout);
            requests.put(request.getRequestId(), result);
            return result;
        }
    }

    private void handleResponse(final Response receivedResponse) {
        final String requestId = receivedResponse.getRequestId();
        synchronized (requests) {
            final FutureResponse waiter = requests.remove(requestId);
            if (waiter == null) {
                LOGGER.log(Level.INFO,
                        "Response received for an unknown request (could be timed out). Ignoring response.");
            } else {
                waiter.registerResponse(receivedResponse);
            }
        }
    }

    private void handleNotification(final Notification receivedNotification) {
        final NotificationListenerData listener = notificationListeners
                .get(receivedNotification.getNotificationListenerId());
        if (listener == null) {
            LOGGER.log(Level.INFO,
                    "Notification received for an unknown notification listener. Ignoring notification.");
            return;
        }

        listener.getNotificationListener().handleNotification(receivedNotification.getNotification(),
                listener.getHandback());
    }

    /**
     * Notification listener data.
     */
    private static final class NotificationListenerData {
        private final NotificationListener notificationListener;
        private final Object handback;

        NotificationListenerData(final NotificationListener notificationListener, final Object handback) {
            this.notificationListener = notificationListener;
            this.handback = handback;
        }

        NotificationListener getNotificationListener() {
            return notificationListener;
        }

        Object getHandback() {
            return handback;
        }
    }

    /**
     * Future response.
     */
    static final class FutureResponse {

        private final CountDownLatch latch = new CountDownLatch(1);
        private final int requestTimeout;

        private Response response;

        FutureResponse(final int requestTimeout) {
            this.requestTimeout = requestTimeout;
        }

        /**
         * Signal that the response has been received.
         * @param response response
         */
        void registerResponse(final Response response) {
            this.response = response;
            latch.countDown();
        }

        /**
         * Return the response, blocking until it has been received.
         * @return the response
         * @throws IOException if an error occurs when waiting for the response (timeout or interrupted)
         */
        Response getResponse() throws IOException {
            try {
                if (latch.await(requestTimeout, TimeUnit.SECONDS)) {
                    return response;
                } else {
                    throw new RequestTimedOutException();
                }
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
    }
}
