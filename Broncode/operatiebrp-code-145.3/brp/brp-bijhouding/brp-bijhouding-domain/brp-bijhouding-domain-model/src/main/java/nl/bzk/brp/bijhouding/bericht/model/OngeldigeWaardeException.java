/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

/**
 * Fouten tijdens het parsen van String waarden om te komen tot een instantie van een object uit het bijhoudingsmodel.
 */
public class OngeldigeWaardeException extends Exception {

    /**
     * Maakt een OngeldigeWaardeException object.
     *
     * @param message het foutbericht
     */
    public OngeldigeWaardeException(final String message) {
        super(message);
    }

    /**
     * Maakt een OngeldigeWaardeException object.
     *
     * @param message het foutbericht
     * @param cause de bron exceptie die tot deze fout heeft geleid
     */
    public OngeldigeWaardeException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
