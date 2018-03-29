/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.controle.waiter;

import java.io.IOException;
import javax.management.JMException;
import javax.management.MBeanServerConnection;

/**
 * Controle of de BRP_SELECTIE is gestart.
 */
public final class BrpSelectieInitializationWaiter extends AbstractInitializationWaiter {

    private BrpSelectieInitializationWaiter() {
        super(new InitializationConfig("BRP_SELECTIE").withSystemConfig());
    }

    /**
     * Run waiter.
     * @param args arguments
     */
    public static void main(final String[] args) {
        new BrpSelectieInitializationWaiter().check();
    }

    @Override
    protected void check(final MBeanServerConnection connection) throws JMException, IOException {
        checkMBeanInfo(connection, "selectie-caches:name=Caches");
    }
}
