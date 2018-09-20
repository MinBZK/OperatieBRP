/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.Omschrijving;
import nl.bzk.copy.model.attribuuttype.RedenBeeindigingRelatieCode;
import nl.bzk.copy.model.objecttype.operationeel.statisch.RedenBeeindigingRelatie;


public class RedenBeeindigingRelatieMapper extends AbstractRowMapper<RedenBeeindigingRelatie> {

    public RedenBeeindigingRelatieMapper(final String prefix) {
        super(prefix);
    }

    @Override
    public RedenBeeindigingRelatie mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        RedenBeeindigingRelatie model = new RedenBeeindigingRelatie();

        //TODO: id?
        model.setCode(new RedenBeeindigingRelatieCode(stringValue(rs, "code")));
        model.setOmschrijving(new Omschrijving(stringValue(rs, "oms")));

        return model;
    }

}
