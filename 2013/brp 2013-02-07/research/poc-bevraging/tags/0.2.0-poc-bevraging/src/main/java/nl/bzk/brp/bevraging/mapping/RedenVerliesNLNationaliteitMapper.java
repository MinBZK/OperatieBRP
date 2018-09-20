/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.RedenVerliesNaam;
import nl.bzk.copy.model.objecttype.operationeel.statisch.RedenVerliesNLNationaliteit;

/**
 */
public class RedenVerliesNLNationaliteitMapper extends AbstractRowMapper<RedenVerliesNLNationaliteit> {

    public RedenVerliesNLNationaliteitMapper(final String prefix) {
        super(prefix);
    }

    @Override
    public RedenVerliesNLNationaliteit mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        RedenVerliesNLNationaliteit model = new RedenVerliesNLNationaliteit();

        model.setId(shortValue(rs, "id"));
        model.setNaam(new RedenVerliesNaam(stringValue(rs, "oms")));

        return model;
    }
}
