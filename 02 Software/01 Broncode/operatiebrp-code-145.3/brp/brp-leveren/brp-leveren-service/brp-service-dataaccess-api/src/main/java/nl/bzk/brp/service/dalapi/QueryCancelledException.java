/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.dalapi;

/**
 * QueryCancelledException.
 */
public final class QueryCancelledException extends QueryNietUitgevoerdException {


    private static final long serialVersionUID = 1554554426792867450L;
    /**
     * Standaard melding.
     */
    public static final String STANDAARD_MELDING = "query die is uitgevoerd is cancelled omdat max duur is overschreven";

    /**
     * @param cause cause
     */
    public QueryCancelledException(final Throwable cause) {
        super(STANDAARD_MELDING, cause);
    }
}
