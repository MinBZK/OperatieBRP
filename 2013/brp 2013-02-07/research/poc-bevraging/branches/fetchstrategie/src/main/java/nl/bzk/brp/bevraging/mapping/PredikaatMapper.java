/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.Naam;
import nl.bzk.copy.model.attribuuttype.PredikaatCode;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Predikaat;


public class PredikaatMapper extends AbstractRowMapper<Predikaat> {

    public PredikaatMapper(String prefix) {
        super(prefix);
    }

    @Override
    public Predikaat mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        Predikaat predikaat = null;

        if (shortValue(rs, "id") > -1) {
            predikaat = new Predikaat();
            predikaat.setId(shortValue(rs, "id"));
            predikaat.setCode(new PredikaatCode(stringValue(rs, "code")));
            predikaat.setNaamMannelijk(new Naam(stringValue(rs, "naamMannelijk")));
            predikaat.setNaamVrouwelijk(new Naam(stringValue(rs, "naamVrouwelijk")));
        }
        return predikaat;
    }

}
