/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.repository.jdbc;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Rowset util.
 */
public interface RowSetUtil {

    /**
     * Get an integer (uses {@link SqlRowSet#wasNull()} to check for null) column.
     * @param rowSet rowset
     * @param columnLabel column
     * @return value
     * @throws SQLException on errors
     */
    static Integer getInteger(final SqlRowSet rowSet, final String columnLabel) {
        final Integer result = rowSet.getInt(columnLabel);
        return rowSet.wasNull() ? null : result;
    }

    /**
     * Get an long (uses {@link SqlRowSet#wasNull()} to check for null) column.
     * @param rowSet rowset
     * @param columnLabel column
     * @return value
     * @throws SQLException on errors
     */
    static Long getLong(final SqlRowSet rowSet, final String columnLabel) {
        final Long result = rowSet.getLong(columnLabel);
        return rowSet.wasNull() ? null : result;
    }

    /**
     * Get an timestamp as local date time (uses {@link SqlRowSet#wasNull()} to check for null) column.
     * @param rowSet rowset
     * @param columnLabel column
     * @return value
     * @throws SQLException on errors
     */
    static LocalDateTime getTimestampAsLocalDateTime(final SqlRowSet rowSet, final String columnLabel) {
        final Timestamp result = rowSet.getTimestamp(columnLabel);
        return rowSet.wasNull() ? null : result.toLocalDateTime();
    }
}
