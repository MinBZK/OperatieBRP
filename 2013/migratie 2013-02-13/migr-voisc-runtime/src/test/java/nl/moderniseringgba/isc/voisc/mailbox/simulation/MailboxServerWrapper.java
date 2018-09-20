/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.mailbox.simulation;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Mailbox server wrapper om de mailbox server in een sprign omgeving te kunnen gebruiken als test.
 */
public class MailboxServerWrapper implements InitializingBean, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger();

    private int port = 1212;
    private int timeout = 1000;

    private MailboxServerThread mailboxThread;

    public final int getPort() {
        return port;
    }

    public final void setPort(final int port) {
        this.port = port;
    }

    public final int getTimeout() {
        return timeout;
    }

    public final void setTimeout(final int timeout) {
        this.timeout = timeout;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("Starting mailbox server...");
        mailboxThread = new MailboxServerThread(port, timeout);
        mailboxThread.start();

        // todo: wait until mailboxThread.isInitialized is true
    }

    @Override
    public void destroy() throws Exception {
        LOG.info("Shutting down mailbox server...");
        mailboxThread.getMailboxServer().stop();
        LOG.info("Waiting for mailbox server to shutdown ...");
        mailboxThread.join();
        LOG.info("Mailbox server shutdown.");
    }

    public MailboxServer getMailboxServer() {
        return mailboxThread.getMailboxServer();
    }

    public static class MailboxServerThread extends Thread {
        private final MailboxServer server;
        private final int port;
        private final int timeout;

        MailboxServerThread(final int port, final int timeout) {
            server = new MailboxServer();
            this.port = port;
            this.timeout = timeout;
        }

        @Override
        public void run() {
            server.run(port, timeout);
        }

        public MailboxServer getMailboxServer() {
            return server;
        }
    }

}
