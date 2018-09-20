/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.ISO31661Alpha2;
import nl.bzk.copy.model.attribuuttype.Landcode;
import nl.bzk.copy.model.attribuuttype.Naam;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Land;

/**
 */
public class LandMapper extends AbstractRowMapper<Land> {

    public LandMapper(String prefix) {
        super(prefix);
    }

    @Override
    public Land mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        Land model = null;

        if (integerValue(rs, "id") > -1) {
            model = new Land();
            model.setId(integerValue(rs, "id"));
            model.setDatumAanvang(datumValue(rs, "dataanvgel"));
            model.setDatumEinde(datumValue(rs, "dateindegel"));
            model.setCode(new Landcode(shortValue(rs, "code")));
            model.setNaam(new Naam(stringValue(rs, "naam")));
            model.setIso31661Alpha2(new ISO31661Alpha2(stringValue(rs, "iso31661alpha2")));
        }
        return model;
    }
}
