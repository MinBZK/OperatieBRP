/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd.exception;

/**
 * ParseException.
 */
public class ParseException extends RuntimeException {

    private static final long serialVersionUID = -6436100579160950619L;

    /**
     * Creates exception.
     */
    public ParseException() {
        super();
    }

    /**
     * Creates exception with inner exception.
     * @param t inner exception
     */
    public ParseException(final Throwable t) {
        super(t);
    }

    /**
     * Creates exception with message.
     * @param message message
     */
    public ParseException(final String message) {
        super(message);
    }
}
