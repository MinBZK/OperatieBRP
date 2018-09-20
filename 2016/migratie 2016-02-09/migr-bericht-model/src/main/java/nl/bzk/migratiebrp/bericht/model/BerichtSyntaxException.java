/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model;

/**
 * Exception om aan te geven dat een bericht niet geparsed kan worden (Pf02).
 */
public class BerichtSyntaxException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param message
     *            message
     */
    public BerichtSyntaxException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     * 
     * @param message
     *            message
     * @param rootCause
     *            root cause
     */
    public BerichtSyntaxException(final String message, final Throwable rootCause) {
        super(message, rootCause);
    }
}
