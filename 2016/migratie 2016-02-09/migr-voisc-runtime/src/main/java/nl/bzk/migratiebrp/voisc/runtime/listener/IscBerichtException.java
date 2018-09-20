/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime.listener;

/**
 * Fout bij het verwerken van een bericht van ISC.
 */
public class IscBerichtException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param cause
     *            cause
     */
    public IscBerichtException(final Exception cause) {
        super(cause);
    }

    /**
     * Constructor.
     *
     * @param message
     *            message
     */
    public IscBerichtException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     * 
     * @param message
     *            message
     * @param cause
     *            cause
     */
    public IscBerichtException(final String message, final Exception cause) {
        super(message, cause);
    }

}
