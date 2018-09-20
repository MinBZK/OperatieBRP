/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.exceptie;

/**
 * Persoon niet gevonden.
 */
public class PersoonNietGevondenExceptie extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Instantieert een nieuwe Persoon niet gevonden exceptie.
     */
    public PersoonNietGevondenExceptie() {
        super();
    }

    /**
     * Instantieert een nieuwe Persoon niet gevonden exceptie.
     *
     * @param message het bericht
     * @param cause de oorzaak
     */
    public PersoonNietGevondenExceptie(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantieert een nieuwe Persoon niet gevonden exceptie.
     *
     * @param message het bericht
     */
    public PersoonNietGevondenExceptie(final String message) {
        super(message);
    }

    /**
     * Instantieert een nieuwe Persoon niet gevonden exceptie.
     *
     * @param cause de oorzaak
     */
    public PersoonNietGevondenExceptie(final Throwable cause) {
        super(cause);
    }

}
