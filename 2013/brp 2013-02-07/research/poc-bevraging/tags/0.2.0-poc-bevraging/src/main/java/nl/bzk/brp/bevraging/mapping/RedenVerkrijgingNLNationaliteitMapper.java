/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.RedenVerkrijgingCode;
import nl.bzk.copy.model.attribuuttype.RedenVerkrijgingNaam;
import nl.bzk.copy.model.objecttype.operationeel.statisch.RedenVerkrijgingNLNationaliteit;

/**
 */
public class RedenVerkrijgingNLNationaliteitMapper extends AbstractRowMapper<RedenVerkrijgingNLNationaliteit> {

    public RedenVerkrijgingNLNationaliteitMapper(final String prefix) {
        super(prefix);
    }

    @Override
    public RedenVerkrijgingNLNationaliteit mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        RedenVerkrijgingNLNationaliteit model = new RedenVerkrijgingNLNationaliteit();

        model.setCode(new RedenVerkrijgingCode(shortValue(rs, "code")));
        model.setId(shortValue(rs, "id"));
        model.setOmschrijving(new RedenVerkrijgingNaam(stringValue(rs, "oms")));

        return model;
    }
}
