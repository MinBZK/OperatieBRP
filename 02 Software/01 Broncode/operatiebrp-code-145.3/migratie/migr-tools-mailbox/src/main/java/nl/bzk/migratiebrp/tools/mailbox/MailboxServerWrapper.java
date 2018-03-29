/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxException;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Mailbox server wrapper om de mailbox server in een sprign omgeving te kunnen gebruiken als test.
 */
@Component
public final class MailboxServerWrapper implements InitializingBean, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int SERVER_STARTUP_TIMEOUT = 5;
    private static final int DEFAULT_TIMEOUT = 1000;
    private static final int DEFAULT_PORT = 1212;

    private int timeout = DEFAULT_TIMEOUT;

    @Value("${mailbox.port}")
    private int port = DEFAULT_PORT;

    @Value("${mailbox.tls.allowed_protocols:TLSv1.2}")
    private String[] tlsAllowedProtocols;

    @Value("${mailbox.tls.allowed_cipher_suites:TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256}")
    private String[] tlsAllowedCipherSuites;

    private MailboxFactory mailboxFactory;
    private MailboxServerThread mailboxThread;

    /**
     * Geef de waarde van port.
     * @return port
     */
    public int getPort() {
        return port;
    }

    /**
     * Geef de waarde van timeout.
     * @return timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Zet de waarde van timeout.
     * @param timeout timeout
     */
    public void setTimeout(final int timeout) {
        this.timeout = timeout;
    }

    /**
     * Zet de mailbox factory.
     * @param mailboxFactory mailbox factory
     */
    @Inject
    public void setMailboxFactory(final MailboxFactory mailboxFactory) {
        this.mailboxFactory = mailboxFactory;
    }

    @Override
    public void afterPropertiesSet() throws MailboxException, InterruptedException {
        LOG.info("Starting mailbox server...");
        final CountDownLatch startSignal = new CountDownLatch(1);
        mailboxThread = new MailboxServerThread(startSignal, port, timeout, mailboxFactory, tlsAllowedProtocols, tlsAllowedCipherSuites);
        mailboxThread.start();
        if (!startSignal.await(SERVER_STARTUP_TIMEOUT, TimeUnit.SECONDS)) {
            LOG.warn("Mailbox server failed to start within {} seconds", SERVER_STARTUP_TIMEOUT);
        }
    }

    @Override
    public void destroy() throws InterruptedException {
        LOG.info("Shutting down mailbox server...");
        mailboxThread.getMailboxServer().stop();
        LOG.info("Waiting for mailbox server to shutdown ...");
        mailboxThread.join();
        LOG.info("Mailbox server shutdown.");
    }

    /**
     * Geef de waarde van mailbox server.
     * @return mailbox server
     */
    public MailboxServer getMailboxServer() {
        return mailboxThread.getMailboxServer();
    }

    /**
     * Mailbox server thread.
     */
    public static final class MailboxServerThread extends Thread {
        private final MailboxServer server;
        private final int port;
        private final int timeout;

        /**
         * Constructor.
         * @param startSignal Synchronisatie hulpmiddel om te bepalen of de mailbox server gestart is.
         * @param port port
         * @param timeout timeout
         * @param mailboxFactory De te gebruiken mailboxFactory.
         * @param tlsAllowedProtocols list of allowed TLS protocols
         * @param tlsAllowedCipherSuites list of allowed TLS cipher suites
         */
        MailboxServerThread(
                final CountDownLatch startSignal,
                final int port,
                final int timeout,
                final MailboxFactory mailboxFactory,
                final String[] tlsAllowedProtocols,
                final String[] tlsAllowedCipherSuites) {
            server = new MailboxServer(startSignal, mailboxFactory, tlsAllowedProtocols, tlsAllowedCipherSuites);
            this.port = port;
            this.timeout = timeout;
        }

        @Override
        public void run() {
            server.run(port, timeout);
        }

        /**
         * Geef de waarde van mailbox server.
         * @return mailbox server
         */
        MailboxServer getMailboxServer() {
            return server;
        }
    }

}
