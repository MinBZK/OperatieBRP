/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

/**
 * Fout bij het uitvoeren van een actie.
 */
public final class ActionException extends Exception {

    /**
     * Constructor.
     * @param message melding
     */
    public ActionException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message melding
     * @param cause oorzaak
     */
    public ActionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     * @param cause oorzaak
     */
    public ActionException(Throwable cause) {
        super(cause);
    }
}
