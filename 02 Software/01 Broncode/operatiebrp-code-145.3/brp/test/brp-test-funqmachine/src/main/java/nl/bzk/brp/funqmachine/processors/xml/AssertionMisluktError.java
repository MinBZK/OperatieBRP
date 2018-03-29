/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.processors.xml;

/**
 * Assertion error als de assertion mislukt is.
 */
public class AssertionMisluktError extends AssertionError {
    /**
     * Constructor met alleen een melding.
     * @param message de melding
     */
    public AssertionMisluktError(final String message) {
        super(message);
    }

    /**
     * Constructor met zowel een melding als de oorspronkelijke exceptie.
     * @param message de melding
     * @param throwable de oorspronkelijke exceptie.
     */
    public AssertionMisluktError(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
