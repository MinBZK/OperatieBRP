/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.controle.waiter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.management.JMException;
import javax.management.MBeanServerConnection;

/**
 * Controle of de MAILBOX is gestart.
 */
public final class MailboxInitializationWaiter extends AbstractInitializationWaiter {

    private MailboxInitializationWaiter() {
        super(new InitializationConfig("MAILBOX").withSystemConfig());
    }

    /**
     * Run waiter.
     * @param args arguments
     */
    public static void main(final String[] args) {
        new MailboxInitializationWaiter().check();
    }

    @Override
    protected void check(final MBeanServerConnection connection) throws JMException, IOException {
        checkMBeanInfo(connection, "nl.bzk.migratiebrp.mailbox:name=MAILBOX");
    }

    @Override
    protected void check(final Connection connection) throws SQLException {
        checkQuery(connection, "SELECT count(*) FROM mailbox.mailbox");
    }
}
