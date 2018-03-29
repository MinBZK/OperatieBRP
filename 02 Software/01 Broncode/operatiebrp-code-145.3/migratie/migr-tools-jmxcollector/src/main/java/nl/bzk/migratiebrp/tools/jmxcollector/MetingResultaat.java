/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.jmxcollector;

import java.time.Instant;

/**
 * Resultaat van een meting. Meting is altijd op het moment nu.
 */
class MetingResultaat {

    private final String resultaat;

    MetingResultaat(final String naam, final String waarde) {
        final long unixEpoch = Instant.now().getEpochSecond();
        resultaat = String.format("%s %s %d\n", naam, waarde, unixEpoch);
    }

    String getResultaat() {
        return resultaat;
    }
}
