/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.attribuuttype.DatumTijd;
import nl.bzk.copy.model.attribuuttype.JaNee;
import org.springframework.jdbc.core.RowMapper;

/**
 */
public abstract class AbstractRowMapper<T> implements RowMapper<T> {

    protected String prefix;

    public AbstractRowMapper(String prefix) {
        this.prefix = prefix;
    }

    public AbstractRowMapper() {
        this("");
    }

    protected Integer integerValue(ResultSet rs, String column) throws SQLException {
        String name = this.prefix + column;
        return rs.getObject(name) != null ? rs.getInt(name) : -1;
    }

    protected String stringValue(ResultSet rs, String column) throws SQLException {
        return rs.getString(this.prefix + column);
    }

    protected Short shortValue(ResultSet rs, String column) throws SQLException {
        String name = this.prefix + column;
        return rs.getObject(name) != null ? rs.getShort(name) : -1;
    }

    protected Long longValue(ResultSet rs, String column) throws SQLException {
        String name = this.prefix + column;
        return rs.getObject(name) != null ? rs.getLong(name) : -1L;
    }

    protected Datum datumValue(ResultSet rs, String column) throws SQLException {
        String name = this.prefix + column;
        return rs.getObject(name) != null ? new Datum(rs.getInt(name)) : null;
    }

    protected DatumTijd datumTijdValue(ResultSet rs, String column) throws SQLException {
        String name = this.prefix + column;
        return rs.getObject(name) != null ? new DatumTijd(rs.getDate(name)) : null;
    }

    protected JaNee jaNee(ResultSet rs, String column) throws SQLException {
        String name = this.prefix + column;
        return rs.getObject(name) != null ? (rs.getBoolean(name) ? JaNee.Ja : JaNee.Nee) : null;
    }
}
