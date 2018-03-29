/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.writer;

/**
 * Fouten tijdens het schrijven van XML voor bijhoudingsberichten.
 */
public class WriteException extends Exception {

    /**
     * Maakt een WriteException object.
     *
     * @param message het foutbericht
     */
    public WriteException(final String message) {
        super(message);
    }

    /**
     * Maakt een WriteException object.
     *
     * @param message het foutbericht
     * @param cause de bron exceptie die tot deze fout heeft geleid
     */
    public WriteException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
