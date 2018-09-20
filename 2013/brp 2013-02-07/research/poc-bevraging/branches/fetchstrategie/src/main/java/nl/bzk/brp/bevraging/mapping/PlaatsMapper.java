/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.Naam;
import nl.bzk.copy.model.attribuuttype.PlaatsCode;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Plaats;

/**
 */
public class PlaatsMapper extends AbstractRowMapper<Plaats> {

    public PlaatsMapper(final String prefix) {
        super(prefix);
    }

    @Override
    public Plaats mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        Plaats model = null;

        if(integerValue(rs, "id") > -1) {
            model = new Plaats();
            model.setId(integerValue(rs, "id"));
            model.setDatumAanvang(datumValue(rs, "dataanvgel"));
            model.setDatumEinde(datumValue(rs, "dateindegel"));
            model.setCode(new PlaatsCode(shortValue(rs, "code")));
            model.setNaam(new Naam(stringValue(rs, "naam")));
        }

        return model;
    }

}
