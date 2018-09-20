/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.Naam;
import nl.bzk.copy.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.copy.model.objecttype.operationeel.statisch.RedenWijzigingAdres;

/**
 */
public class RedenWijzigingAdresMapper extends AbstractRowMapper<RedenWijzigingAdres> {

    public RedenWijzigingAdresMapper(final String prefix) {
        super(prefix);
    }

    @Override
    public RedenWijzigingAdres mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        RedenWijzigingAdres model = new RedenWijzigingAdres();

        model.setNaam(new Naam(stringValue(rs, "naam")));
        model.setRedenWijzigingAdresCode(new RedenWijzigingAdresCode(stringValue(rs, "code")));
        model.setRedenWijzigingAdresID(shortValue(rs, "id"));

        return model;
    }
}
