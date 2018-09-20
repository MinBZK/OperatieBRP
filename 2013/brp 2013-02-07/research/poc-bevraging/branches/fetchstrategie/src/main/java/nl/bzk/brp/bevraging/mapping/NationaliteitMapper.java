/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.Naam;
import nl.bzk.copy.model.attribuuttype.Nationaliteitcode;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Nationaliteit;

/**
 */
public class NationaliteitMapper extends AbstractRowMapper<Nationaliteit> {

    public NationaliteitMapper(final String prefix) {
        super(prefix);
    }

    @Override
    public Nationaliteit mapRow(final ResultSet rs, final int rowNum) throws SQLException {

        Nationaliteit model = new Nationaliteit();

        model.setNationaliteitId(integerValue(rs, "id"));
        model.setNaam(new Naam(stringValue(rs, "naam")));
        model.setNationaliteitcode(new Nationaliteitcode(shortValue(rs, "nationcode")));

        return model;
    }
}
