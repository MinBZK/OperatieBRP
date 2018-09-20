/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.excepties;

/**
 * Root exceptie voor bijhouding fouten.
 */
public class BijhoudingExceptie extends RuntimeException {

    /**
     * Default constructor.
     */
    public BijhoudingExceptie() {
    }

    /**
     * Constructor met een melding.
     * @param message de melding
     */
    public BijhoudingExceptie(final String message) {
        super(message);
    }

    /**
     * Constructor met een melding en oorzaak.
     * @param message de melding
     * @param cause de oorzaak
     */
    public BijhoudingExceptie(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor met een oorzaak.
     * @param cause de oorzaak
     */
    public BijhoudingExceptie(final Throwable cause) {
        super(cause);
    }
}
