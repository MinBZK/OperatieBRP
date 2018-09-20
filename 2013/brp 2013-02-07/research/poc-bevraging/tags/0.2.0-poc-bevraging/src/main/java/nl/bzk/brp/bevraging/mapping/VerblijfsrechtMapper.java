/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.Omschrijving;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Verblijfsrecht;
import org.springframework.jdbc.core.RowMapper;

/**
 */
public class VerblijfsrechtMapper extends AbstractRowMapper<Verblijfsrecht> {

    public VerblijfsrechtMapper(final String prefix) {
        super(prefix);
    }

    @Override
    public Verblijfsrecht mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        Verblijfsrecht model = new Verblijfsrecht();

        model.setId(shortValue(rs, "id"));
        model.setOmschrijving(omschrijving(rs, "oms"));

        return model;
    }

    private Omschrijving omschrijving(ResultSet rs, String column) throws SQLException {
        return stringValue(rs, "oms") != null ? new Omschrijving(stringValue(rs, "oms")) : null;
    }
}
