/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

/**
 * Checked Exceptie wrapper voor ExpressieRuntimeException.
 */
public class ExpressieException extends Exception {

    private static final long serialVersionUID = -8528428980626445919L;

    /**
     * Constructor.
     *
     * @param e de runtime evaluatie exceptie
     */
    public ExpressieException(final Exception e) {
        this(e.getMessage(), e);
    }

    /**
     * Constructor.
     *
     * @param message de foutmelding
     * @param cause   de root cause.
     */
    public ExpressieException(final String message, final Exception cause) {
        super(message, cause);
    }
}
