/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox;

import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxException;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxFactory;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Mailbox server wrapper om de mailbox server in een sprign omgeving te kunnen gebruiken als test.
 */
public final class MailboxServerWrapper implements InitializingBean, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int DEFAULT_TIMEOUT = 1000;
    private static final int DEFAULT_PORT = 1212;

    private int port = DEFAULT_PORT;
    private int timeout = DEFAULT_TIMEOUT;

    private MailboxFactory mailboxFactory;
    private MailboxServerThread mailboxThread;

    /**
     * Geef de waarde van port.
     *
     * @return port
     */
    public int getPort() {
        return port;
    }

    /**
     * Zet de waarde van port.
     *
     * @param port
     *            port
     */
    public void setPort(final int port) {
        this.port = port;
    }

    /**
     * Geef de waarde van timeout.
     *
     * @return timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Zet de waarde van timeout.
     *
     * @param timeout
     *            timeout
     */
    public void setTimeout(final int timeout) {
        this.timeout = timeout;
    }

    /**
     * Zet de mailbox factory.
     *
     * @param mailboxFactory
     *            mailbox factory
     */
    public void setMailboxFactory(final MailboxFactory mailboxFactory) {
        this.mailboxFactory = mailboxFactory;
    }

    @Override
    public void afterPropertiesSet() throws MailboxException {
        LOG.info("Starting mailbox server...");
        mailboxThread = new MailboxServerThread(port, timeout, mailboxFactory);
        mailboxThread.start();

        // todo: wait until mailboxThread.isInitialized is true
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
     *
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
         *
         * @param port
         *            port
         * @param timeout
         *            timeout
         * @param mailboxFactory
         *            De te gebruiken mailboxFactory.
         */
        MailboxServerThread(final int port, final int timeout, final MailboxFactory mailboxFactory) {
            server = new MailboxServer(mailboxFactory);
            this.port = port;
            this.timeout = timeout;
        }

        @Override
        public void run() {
            server.run(port, timeout);
        }

        /**
         * Geef de waarde van mailbox server.
         *
         * @return mailbox server
         */
        public MailboxServer getMailboxServer() {
            return server;
        }
    }

}
