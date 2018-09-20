/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jbpm;

/**
 * Fout tijdens JBPM operaties.
 */
public final class JbpmHelperException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param message
     *            melding
     */
    public JbpmHelperException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     * 
     * @param message
     *            melding
     * @param cause
     *            oorzaak
     */
    public JbpmHelperException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
