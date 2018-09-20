/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.objecttype.operationeel.statisch.SoortPersoon;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 */
public class SoortPersoonMapper implements RowMapper<SoortPersoon> {

    private static final SoortPersoon[] soortPersoonArray = SoortPersoon.values();

    public SoortPersoonMapper() {
    }

    @Override
    public SoortPersoon mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        SoortPersoon model = soort(rs, "srt");
        return model;
    }

    private SoortPersoon soort(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column) != null ? (soortPersoonArray[rs.getInt(column)]) : null;
    }
}
