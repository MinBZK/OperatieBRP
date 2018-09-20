/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.exception;

/**
 * Exception in de verwerking van de service.
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param message
     *            melding
     */
    public ServiceException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause
     *            oorzaak
     */
    public ServiceException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     *
     * @param message
     *            melding
     * @param cause
     *            oorzaak
     */
    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
