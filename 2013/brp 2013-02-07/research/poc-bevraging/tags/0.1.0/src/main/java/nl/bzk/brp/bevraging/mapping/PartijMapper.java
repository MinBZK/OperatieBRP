/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.Gemeentecode;
import nl.bzk.copy.model.attribuuttype.Naam;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Partij;

/**
 */
public class PartijMapper extends AbstractRowMapper<Partij> {

    public PartijMapper(final String prefix) {
        super(prefix);
    }

    @Override
    public Partij mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        Partij model = null;

        if (shortValue(rs, "id") > -1) {
            model = new Partij();
            model.setId(shortValue(rs, "id"));
            model.setDatumAanvang(datumValue(rs, "dataanv"));
            model.setDatumEinde(datumValue(rs, "dateinde"));
            model.setGemeentecode(new Gemeentecode(shortValue(rs, "code")));
            model.setNaam(new Naam(stringValue(rs, "naam")));
        }
        return model;
    }

}
