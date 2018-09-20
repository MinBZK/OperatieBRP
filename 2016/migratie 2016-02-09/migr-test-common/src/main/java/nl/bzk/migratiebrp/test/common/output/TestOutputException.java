/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.output;

/**
 * Exception bij output problemen.
 */
public final class TestOutputException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * 
     * @param message
     *            De melding.
     * @param cause
     *            De oorzaak van de exceptie.
     */
    public TestOutputException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
