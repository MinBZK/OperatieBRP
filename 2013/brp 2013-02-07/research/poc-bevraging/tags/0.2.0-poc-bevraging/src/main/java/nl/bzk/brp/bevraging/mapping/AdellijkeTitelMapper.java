/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.copy.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.copy.model.attribuuttype.Naam;
import nl.bzk.copy.model.objecttype.operationeel.statisch.AdellijkeTitel;


public class AdellijkeTitelMapper extends AbstractRowMapper<AdellijkeTitel> {

    public AdellijkeTitelMapper(String prefix) {
        super(prefix);
    }

    @Override
    public AdellijkeTitel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        AdellijkeTitel adellijkeTitel = null;

        if (shortValue(rs, "id") > -1) {
            adellijkeTitel = new AdellijkeTitel();
            adellijkeTitel.setAdellijkeTitelId(shortValue(rs, "id"));
            adellijkeTitel.setAdellijkeTitelCode(new AdellijkeTitelCode(stringValue(rs, "code")));
            adellijkeTitel.setNaamMannelijk(new Naam(stringValue(rs, "naamMannelijk")));
            adellijkeTitel.setNaamVrouwelijk(new Naam(stringValue(rs, "naamVrouwelijk")));
        }

        return adellijkeTitel;
    }
}
