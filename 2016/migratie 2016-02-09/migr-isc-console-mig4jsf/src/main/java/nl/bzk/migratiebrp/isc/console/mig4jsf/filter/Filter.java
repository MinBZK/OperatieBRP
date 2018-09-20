/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.filter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.hibernate.Criteria;

/**
 * Filter.
 */
public interface Filter {

    /**
     * Apply the filter to the given criteria.
     * 
     * @param criteria
     *            hibernate criteria
     * @throws UnsupportedOperationException
     *             when this filter does not support this kind of query
     */
    void applyFilter(Criteria criteria);

    /**
     * Construct a where clause for a direct SQL query.
     * 
     * @param startType
     *            what to start the clause with
     * @return where clause, empty if no restrictions, contains start type if not empty.
     * @throws UnsupportedOperationException
     *             when this filter does not support this kind of query
     */
    String getWhereClause(StartType startType);

    /**
     * Set the values on a statement for the where clause constructed by {@link #getWhereClause()}.
     * 
     * @param statement
     *            statement to set values on
     * @param startingIndex
     *            starting index for setting parameters
     * @throws SQLException
     *             on SQL errors
     * @throws UnsupportedOperationException
     *             when this filter does not support this kind of query
     */
    void setWhereClause(PreparedStatement statement, int startingIndex) throws SQLException;

    /**
     * Where clause query start.
     */
    public static enum StartType {
        /** where. */
        WHERE,
        /** and. */
        AND,
        /** nothing. */
        NONE;
    }
}
