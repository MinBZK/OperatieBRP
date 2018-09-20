/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.controle.check;

import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Factory.
 */
public final class CheckFactory {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Create check.
     *
     * @param args
     *            command line arguments
     * @return check
     */
    Check createCheck(final String[] args) {
        if (args == null || args.length == 0) {
            return null;
        }

        final String functie = args[0].toLowerCase();

        final Check result;
        switch (functie) {
            case "db":
                result = createDb(args);
                break;
            case "ping":
                result = createPing(args);
                break;
            default:
                LOG.error("Onbekend functie: " + functie);
                result = null;
                break;
        }
        return result;
    }

    private Check createPing(final String[] args) {
        if (args.length < 2) {
            LOG.error("Te weinig parameters voor functie 'ping'");
            return null;
        }

        return new CheckPing(args[1]);
    }

    private Check createDb(final String[] args) {
        if (args.length < 4) {
            LOG.error("Te weinig parameters voor functie 'db'");
            return null;
        }

        final CheckDatabase database = new CheckDatabase(args[1], args[2], args[3]);
        if (args.length >= 6) {
            database.setUser(args[4]);
            database.setPassword(args[5]);
        }

        return database;
    }
}
