/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.controle.check;

import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Executable.
 */
public final class Main {
    
    private static final Logger LOG = LoggerFactory.getLogger();
    
    private Main() {
        // Niet instantieerbaar.
    }

    /**
     * Run.
     * 
     * @param args
     *            commandline arguments
     */
    public static void main(final String[] args) {
        final CheckFactory factory = new CheckFactory();
        final Check check = factory.createCheck(args);
        if (check == null) {
            LOG.info("Usage: check functie <param_verplicht> [param_optioneel]");
            LOG.info("Functie 'ping' <host>");
            LOG.info("Functie 'db' <protocol> <host> <database> [user] [password]");
        } else {
            check.check();
        }
    }
}
