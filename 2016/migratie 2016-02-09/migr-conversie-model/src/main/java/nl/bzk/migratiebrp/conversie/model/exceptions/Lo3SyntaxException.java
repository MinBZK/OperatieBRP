/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.exceptions;

/**
 * Exception wordt gegooid als de syntax van een LO3-bericht niet klopt.
 */
public final class Lo3SyntaxException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -2654886075134285422L;

    /**
     * Maakt een Lo3SyntaxException met de onderliggende exception als message.
     * 
     * @param throwable
     *            de exception die de Lo3SyntaxException heeft veroorzaakt
     */
    public Lo3SyntaxException(final Throwable throwable) {
        super(throwable);
    }
}
