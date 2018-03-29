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
 * Controle of de VOISC is gestart.
 */
public final class VoiscInitializationWaiter extends AbstractInitializationWaiter {

    private VoiscInitializationWaiter() {
        super(new InitializationConfig("VOISC").withSystemConfig());
    }

    /**
     * Run waiter.
     * @param args arguments
     */
    public static void main(final String[] args) {
        new VoiscInitializationWaiter().check();
    }

    @Override
    protected void check(final MBeanServerConnection connection) throws JMException, IOException {
        checkMBeanInfo(connection, "nl.bzk.migratiebrp.voisc:name=VOISC");
        checkMBeanInfo(connection, "net.sf.ehcache:type=CacheManager,name=VoiscCacheManager");
    }

    @Override
    protected void check(final Connection connection) throws SQLException {
        checkQuery(connection, "SELECT count(*) FROM voisc.lo3_mailbox");
    }
}
