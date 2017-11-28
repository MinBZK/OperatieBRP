/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

/**
 * {@link BrpServiceRuntimeException} is het supertype van alle BRP servicefouten.
 */
public class BrpServiceRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -2468384565044139248L;

    /**
     * @param message message
     */
    public BrpServiceRuntimeException(final String message) {
        super(message);
    }

    /**
     * @param cause cause
     */
    public BrpServiceRuntimeException(final Exception cause) {
        super(cause);
    }

    /**
     * @param message message
     * @param cause cause
     */
    public BrpServiceRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
