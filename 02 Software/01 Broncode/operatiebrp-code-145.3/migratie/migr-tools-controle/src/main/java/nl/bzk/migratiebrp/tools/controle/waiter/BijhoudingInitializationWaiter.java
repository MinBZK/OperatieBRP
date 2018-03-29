/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.controle.waiter;

/**
 * Controle of de BIJHOUDING is gestart.
 */
public final class BijhoudingInitializationWaiter extends AbstractInitializationWaiter {

    private BijhoudingInitializationWaiter() {
        super(new InitializationConfig("BIJHOUDING").withSystemConfig());
    }

    /**
     * Run waiter.
     * @param args arguments
     */
    public static void main(final String[] args) {
        new BijhoudingInitializationWaiter().check();
    }

}
