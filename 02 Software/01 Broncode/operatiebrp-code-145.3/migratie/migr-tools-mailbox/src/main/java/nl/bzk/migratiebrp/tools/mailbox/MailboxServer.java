/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxFactory;

/**
 * LO3 MailboxServer Simulator.
 */
public final class MailboxServer {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String DEFAULT_PASSWORD = "changeit";

    // 3 minuten read timeout als er een verbinding is.
    private static final int MAX_READ_TIMEOUT = 180_000;

    // Geef de client 5 seconden voordat de connectie wordt gesloten na een logout
    private static final long LINGER_TIMEOUT = 5000;
    private final MailboxFactory mbFactory;
    private final CountDownLatch startSignal;
    private String[] tlsAllowedProtocols;
    private String[] tlsAllowedCipherSuites;
    private String name;
    private boolean keepOnRunning = true;

    /**
     * Constructor waarbij de mailboxFactory implementatie wordt gezet.
     * @param startSignal Synchronisatie hulpmiddel om te bepalen of de mailbox server gestart is.
     * @param mbFactory De te gebruiken mailboxFactory.
     * @param tlsAllowedProtocols list of allowed TLS protocols
     * @param tlsAllowedCipherSuites list of allowed TLS cipher suites
     */
    public MailboxServer(
            final CountDownLatch startSignal,
            final MailboxFactory mbFactory,
            final String[] tlsAllowedProtocols,
            final String[] tlsAllowedCipherSuites) {
        this.startSignal = startSignal;
        this.mbFactory = mbFactory;
        this.tlsAllowedProtocols = Arrays.copyOf(tlsAllowedProtocols, tlsAllowedProtocols.length);
        this.tlsAllowedCipherSuites = Arrays.copyOf(tlsAllowedCipherSuites, tlsAllowedCipherSuites.length);
    }

    /**
     * Geef de waarde van mailbox factory.
     * @return mailbox factory
     */
    public MailboxFactory getMailboxFactory() {
        return mbFactory;
    }

    /**
     * Open an SSL port, wait for clients and handle them.
     * @param port Port to listen to
     * @param listenTimeoutMillis After which the server stops waiting and exits
     */
    public void run(final int port, final int listenTimeoutMillis) {
        SSLServerSocket listen = null;
        try {
            final ServerSocketFactory factory = setupServerSocketFactory();

            // Open listen socket

            listen = (SSLServerSocket) factory.createServerSocket(port);
            listen.setSoTimeout(listenTimeoutMillis);

            listen.setEnabledProtocols(filter(tlsAllowedProtocols, listen.getSupportedProtocols()));
            listen.setEnabledCipherSuites(filter(tlsAllowedCipherSuites, listen.getSupportedCipherSuites()));

            LOGGER.info("MBS Started on port: " + port);
            // Only stop with an Exception occurred during setup of SSL or else continue until Ctrl-C
            while (keepOnRunning) {
                startSignal.countDown();
                LOGGER.debug("Waiting for connection ... (timeout " + listenTimeoutMillis + "ms)");
                runMailboxThread(listen);
            }

        } catch (final IOException e) {
            LOGGER.error("Fout tijdens wachten op connectie", e);
        } catch (final GeneralSecurityException e) {
            LOGGER.error("Fout tijdens initialisatie SSL", e);
        } finally {
            if (listen != null) {
                try {
                    listen.close();
                } catch (IOException e) {
                    LOGGER.debug(e.getMessage(), e);
                }

            }
        }

        LOGGER.info("Stopped");
    }

    private String[] filter(final String[] allAllowed, final String[] allSupported) {
        final List<String> result = new ArrayList<>();
        final List<String> supportedList = Arrays.asList(allSupported);

        for (final String allowed : allAllowed) {
            if (supportedList.contains(allowed)) {
                result.add(allowed);
            }
        }

        if (result.isEmpty()) {
            LOGGER.warn("Allowed {0} not supported {1}", Arrays.asList(allAllowed), supportedList);
        }

        return result.toArray(new String[]{});
    }

    private void runMailboxThread(final SSLServerSocket listen) throws IOException {
        try {
            new ServerThread(listen.accept(), mbFactory).start();
        } catch (final SocketTimeoutException e) {
            LOGGER.debug("Connection listen timeout. Looping ...", e);
            // Expected, loop to listen again
        }
    }

    private ServerSocketFactory setupServerSocketFactory() throws GeneralSecurityException {
        try (InputStream keystoreStream = getKeystoreStream()) {
            final char[] keystorepass = DEFAULT_PASSWORD.toCharArray();
            final char[] keypassword = DEFAULT_PASSWORD.toCharArray();

            final KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(keystoreStream, keystorepass);

            final KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, keypassword);

            final SSLContext sslcontext = SSLContext.getInstance(tlsAllowedProtocols[0]);
            sslcontext.init(kmf.getKeyManagers(), null, null);

            return sslcontext.getServerSocketFactory();
        } catch (final IOException e) {
            throw new GeneralSecurityException("Kan keystore niet lezen", e);
        }
    }

    /**
     * Geef de waarde van keystore stream.
     * @return keystore stream
     * @throws FileNotFoundException the file not found exception
     */
    private InputStream getKeystoreStream() throws FileNotFoundException {
        final String keystore = System.getProperty("keystore", "keystores/keystore.jks");
        LOGGER.info("Loading certificates from: {}", keystore);

        final File keyStoreFile = new File(keystore);
        final InputStream keystoreStream;
        if (keyStoreFile.exists()) {
            keystoreStream = new FileInputStream(keyStoreFile);
        } else {
            keystoreStream = MailboxServer.class.getClassLoader().getResourceAsStream(keystore);
        }

        if (keystoreStream == null) {
            LOGGER.error("Geen keystore gevonden '{}'", keystore);
            throw new IllegalArgumentException("Geen keystore gevonden.");
        }

        return keystoreStream;

    }

    /**
     * Stop.
     */
    public void stop() {
        keepOnRunning = false;
    }

    /**
     * Geef de waarde van name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Zet de waarde van name.
     * @param name name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Server thread.
     */
    public static final class ServerThread extends Thread {

        private final Socket connection;
        private final MailboxFactory mbFactory;

        /**
         * Constructor.
         * @param connection connection
         * @param mbFactory mailbox factory
         */
        public ServerThread(final Socket connection, final MailboxFactory mbFactory) {
            super("MailboxServerThread");
            this.connection = connection;
            this.mbFactory = mbFactory;
        }

        @Override
        public void run() {
            try {
                connection.setSoTimeout(MAX_READ_TIMEOUT);

                LOGGER.debug("Accepted client: " + connection);

                final MailboxProcess client = new MailboxProcess(connection, mbFactory);
                ((SSLSocket) connection).startHandshake();

                client.process();
                wacht5Seconden();

                // Close connection.
                connection.close();
            } catch (final IOException e) {
                LOGGER.info("IOEXception", e);
                // Ignore
            }
        }

        private void wacht5Seconden() {
            // 5 seconden voor de client om de boel te lezen voordat we de connectie sluiten.
            try {
                TimeUnit.MILLISECONDS.sleep(LINGER_TIMEOUT);
            } catch (final InterruptedException e) {
                LOGGER.debug("Interrupted", e);
                Thread.currentThread().interrupt();
                // No problem
            }
        }
    }
}
