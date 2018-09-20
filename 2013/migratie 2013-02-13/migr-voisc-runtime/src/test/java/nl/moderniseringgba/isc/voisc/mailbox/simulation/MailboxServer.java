/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.mailbox.simulation;

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

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

/**
 * LO3 MailboxServer Simulator.
 * 
 */
public class MailboxServer {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final int DEFAULT_PORT = 1212;

    // 3 minuten read timeout als er een verbinding is.
    private static final int MAX_READ_TIMEOUT = 180000;

    private final MailboxFactory mbFactory;

    private String name;

    private boolean keepOnRunning = true;

    public MailboxServer() {
        mbFactory = new MailboxFactory();
    }

    public MailboxFactory getMailboxFactory() {
        return mbFactory;
    }

    /**
     * Open an SSL port, wait for clients and handle them. Currently only supports on concurrent client.
     * 
     * @param port
     *            Port to listen to
     * @param listenTimeoutMillis
     *            After which the server stops waiting and exits
     */
    public int run(final int port, final int listenTimeoutMillis) {
        final int ret = 1;

        InputStream keystoreStream = null;
        SSLServerSocket listen = null;
        try {
            LOGGER.info("Open connectie met de database ...");
            LOGGER.info("Open SSL socket ...");
            final String keystore = System.getProperty("keystore", "keystores/keystore.jks");
            final char keystorepass[] = "changeit".toCharArray();
            final char keypassword[] = "changeit".toCharArray();

            final KeyStore ks = KeyStore.getInstance("JKS");
            keystoreStream = MailboxServer.class.getClassLoader().getResourceAsStream(keystore);
            ks.load(keystoreStream, keystorepass);

            final KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, keypassword);

            final SSLContext sslcontext = SSLContext.getInstance("SSLv3");
            sslcontext.init(kmf.getKeyManagers(), null, null);

            final ServerSocketFactory factory = sslcontext.getServerSocketFactory();
            // Open listen socket
            listen = (SSLServerSocket) factory.createServerSocket(port);
            listen.setSoTimeout(listenTimeoutMillis);

            final String[] enabledCipherSuites = { "SSL_RSA_WITH_3DES_EDE_CBC_SHA" };
            listen.setEnabledCipherSuites(enabledCipherSuites);

            LOGGER.info("Protocols ("
                    + listen.getEnabledProtocols().length
                    + "): "
                    + (listen.getEnabledProtocols().length <= 0 ? "No protocols" : printAllProtocols(listen
                            .getEnabledProtocols())));
            LOGGER.info("Server socket created for port " + port);

            LOGGER.info("MBS Started on port: " + port);
            // Only stop with an Exception occurred during setup of SSL or else continue until Ctrl-C
            while (keepOnRunning) {
                final String startMessage = "Waiting for connection ... (timeout " + listenTimeoutMillis + "ms)";
                LOGGER.info(startMessage);
                try {
                    final Socket connection = listen.accept();
                    connection.setSoTimeout(MAX_READ_TIMEOUT);
                    LOGGER.info("Accepted client: " + connection);

                    final MailboxProcess client = new MailboxProcess(connection);
                    client.setMailboxFactory(mbFactory);

                    final String connectMessage = "New connection: " + client;
                    LOGGER.info(connectMessage);

                    LOGGER.info("Connection details: " + ((SSLSocket) connection).getSession().getCipherSuite());
                    client.process();

                    connection.close();
                } catch (final SocketTimeoutException e) {
                    // Expected, loop to listen again
                }
            }
        } catch (final IOException e) {
            LOGGER.error("IOException", e);
        } catch (final GeneralSecurityException e) {
            LOGGER.error("GeneralSecurityException", e);
        } finally {
            if (listen != null) {
                try {
                    listen.close();
                } catch (final IOException e) {
                    LOGGER.error("IOException closing socket", e);
                }
            }
            if (keystoreStream != null) {
                try {
                    keystoreStream.close();
                } catch (final IOException ioe) {
                    LOGGER.error("IOException closing keystore", ioe);
                }
            }
        }
        final String stopMessage = "Stopped with status: " + ret;
        LOGGER.info(stopMessage);
        return ret;
    }// run

    private String printAllProtocols(final String[] enabledProtocols) {
        final StringBuffer protocols = new StringBuffer();
        for (final String protocol : enabledProtocols) {
            protocols.append(" " + protocol + " ");
        }
        return protocols.toString();
    }

    public void stop() {
        keepOnRunning = false;
    }

    /**
     * The main loop.
     * 
     * @param args
     */
    public static void main(final String args[]) {
        LOGGER.info("Mailbox Server Simulator");
        int ret = 0;
        String portStr = null;
        int port = -1;
        switch (args.length) {
            case 0:
                // Use the default
                port = DEFAULT_PORT;
                break;
            case 1:
                // Argument given
                portStr = args[0];
                try {
                    port = Integer.parseInt(portStr);
                } catch (final NumberFormatException e) {
                    LOGGER.error("Geen geldig poort nummer: " + e);
                    ret = -1;
                }
                break;
            default:
                usage();
                ret = -2;
        }

        if (ret == 0) {
            // Default no timeout;
            final int listenTimeoutMillis = 0;
            LOGGER.info("Start Mailbox Server Simulator op poort: " + port + " met timeout(" + listenTimeoutMillis
                    + " ms)");
            final MailboxServer server = new MailboxServer();
            ret = server.run(port, listenTimeoutMillis);
        }
        LOGGER.info("Mailbox Server Simulator gestopt met status: " + ret);
        System.exit(ret);
    }

    private static void usage() {
        System.out.println("gebruik: java nl.moderniseringgba.isc.voisc.mailbox.simulation.MailboxServer [<poort>]");
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
