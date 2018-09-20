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
import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxFactory;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * LO3 MailboxServer Simulator.
 */
public final class MailboxServer {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String DEFAULT_PASSWORD = "changeit";

    // 3 minuten read timeout als er een verbinding is.
    private static final int MAX_READ_TIMEOUT = 180000;

    // Geef de client 5 seconden voordat de connectie wordt gesloten na een logout
    private static final long LINGER_TIMEOUT = 5000;

    private final MailboxFactory mbFactory;

    private String name;

    private boolean keepOnRunning = true;

    /**
     * Constructor waarbij de mailboxFactory implementatie wordt gezet.
     *
     * @param mbFactory
     *            De te gebruiken mailboxFactory.
     */
    public MailboxServer(final MailboxFactory mbFactory) {
        this.mbFactory = mbFactory;
    }

    /**
     * Geef de waarde van mailbox factory.
     *
     * @return mailbox factory
     */
    public MailboxFactory getMailboxFactory() {
        return mbFactory;
    }

    /**
     * Open an SSL port, wait for clients and handle them.
     *
     * @param port
     *            Port to listen to
     * @param listenTimeoutMillis
     *            After which the server stops waiting and exits
     */
    public void run(final int port, final int listenTimeoutMillis) {
        try {
            final ServerSocketFactory factory = setupServerSocketFactory();

            // Open listen socket
            try (SSLServerSocket listen = (SSLServerSocket) factory.createServerSocket(port)) {
                listen.setSoTimeout(listenTimeoutMillis);

                final String[] enabledCipherSuites = {"SSL_RSA_WITH_3DES_EDE_CBC_SHA" };
                listen.setEnabledCipherSuites(enabledCipherSuites);

                LOGGER.info("MBS Started on port: " + port);
                // Only stop with an Exception occurred during setup of SSL or else continue until Ctrl-C
                while (keepOnRunning) {
                    LOGGER.debug("Waiting for connection ... (timeout " + listenTimeoutMillis + "ms)");
                    runMailboxThread(listen);
                }
            } catch (final IOException e) {
                LOGGER.error("Fout tijdens wachten op connectie", e);
            }
        } catch (final GeneralSecurityException e) {
            LOGGER.error("Fout tijdens initialisatie SSL", e);
        }

        LOGGER.info("Stopped");
    }

    private void runMailboxThread(final SSLServerSocket listen) throws IOException {
        try {
            new ServerThread(listen.accept(), mbFactory).start();
        } catch (final SocketTimeoutException e) {
            LOGGER.debug("Connection listen timeout. Looping ...");
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

            final SSLContext sslcontext = SSLContext.getInstance("SSLv3");
            sslcontext.init(kmf.getKeyManagers(), null, null);

            return sslcontext.getServerSocketFactory();
        } catch (final IOException e) {
            throw new GeneralSecurityException("Kan keystore niet lezen", e);
        }
    }

    /**
     * Geef de waarde van keystore stream.
     *
     * @return keystore stream
     * @throws FileNotFoundException
     *             the file not found exception
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
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Zet de waarde van name.
     *
     * @param name
     *            name
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
         *
         * @param connection
         *            connection
         * @param mbFactory
         *            mailbox factory
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

                // 5 seconden voor de client om de boel te lezen voordat we de connectie sluiten.
                try {
                    Thread.sleep(LINGER_TIMEOUT);
                } catch (final InterruptedException e) {
                    LOGGER.debug("Interrupted", e);
                    // No problem
                }

                // Close connection.
                connection.close();
            } catch (final IOException e) {
                LOGGER.info("IOEXception", e);
                // Ignore
            }
        }
    }
}
