/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.remote.JMXAuthenticator;
import nl.bzk.algemeenbrp.util.jmx.access.JMXAccessController;
import nl.bzk.algemeenbrp.util.jmx.socket.JMXSocketFactory;
import nl.bzk.algemeenbrp.util.jmx.utils.IOUtils;

/**
 * Server listener.
 */
final class ServerListener implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ServerListener.class.getName());

    private static final AtomicInteger SERVER_ID = new AtomicInteger(1);

    private final int serverId;
    private final AtomicInteger serverConnectionId = new AtomicInteger(1);
    private final ServerConnector serverConnector;
    private final JMXAuthenticator authenticator;
    private final JMXAccessController accessController;

    private boolean stop = false;

    private final ExecutorService executorService;
    private final ServerSocket serverSocket;

    /**
     * Create a new server listener.
     * @param serverConnector connector
     * @param socketFactory socket factory
     * @param authenticator authenticator
     * @param accessController access controller
     * @param threadPriority  thread priority
     * @throws IOException if an I/O error occurs when constructing the server listener
     */
    ServerListener(final ServerConnector serverConnector, final JMXSocketFactory socketFactory, final JMXAuthenticator authenticator,
                   final JMXAccessController accessController, final int threadPriority) throws IOException {
        this.serverConnector = serverConnector;
        this.authenticator = authenticator;
        this.accessController = accessController;
        serverId = SERVER_ID.getAndIncrement();

        // Setup executor service
        final ThreadFactory threadFactory = new ConnectionThreadFactory(serverId, threadPriority);
        executorService = Executors.newCachedThreadPool(threadFactory);

        // Setup server socket
        serverSocket = socketFactory.createServerSocket(serverConnector.getAddress());
        serverConnector.updateAddress(serverSocket.getLocalPort());
        serverSocket.setSoTimeout(0);
    }

    int getServerId() {
        return serverId;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                LOGGER.log(Level.FINE, "Waiting for new client connection");
                executorService.submit(new ServerConnection(serverSocket.accept(), createConnectionId(), authenticator, accessController,
                        serverConnector.getMBeanServer()));
            } catch (final SocketException e) {
                if (stop) {
                    // Expected
                    LOGGER.log(Level.FINE, "Server shutting down");
                } else {
                    LOGGER.log(Level.SEVERE, "Unexpected error during accept of clients", e);
                }
            } catch (final IOException e) {
                LOGGER.log(Level.SEVERE, "Unexpected error during accept of clients", e);
            }
        }

        executorService.shutdownNow();
    }


    private String createConnectionId() {
        return serverId + "-" + serverConnectionId.getAndIncrement();
    }

    void stop() {
        stop = true;
        IOUtils.closeSilently(serverSocket);
    }

    boolean isStopped() {
        return stop;
    }

    /**
     * Thread factory (based op {@link Executors.DefaultThreadFactory}) that
     * names the threads.
     */
    private static final class ConnectionThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final int threadPriority;
        private final String namePrefix;

        /**
         * Constructor.
         * @param serverId server id
         */
        ConnectionThreadFactory(final int serverId, final int threadPriority) {
            final SecurityManager s = System.getSecurityManager();
            group = s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "simple-jmx-server-" + serverId + "-thread-";
            this.threadPriority = threadPriority;
        }

        @Override
        public Thread newThread(final Runnable r) {
            final Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement());
            t.setDaemon(false);
            t.setPriority(threadPriority);

            return t;
        }

    }

}
