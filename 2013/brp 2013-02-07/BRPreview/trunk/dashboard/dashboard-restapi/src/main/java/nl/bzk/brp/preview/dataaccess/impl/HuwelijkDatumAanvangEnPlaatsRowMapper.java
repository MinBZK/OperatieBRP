/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.brp.preview.model.Datum;
import nl.bzk.brp.preview.model.HuwelijkDatumAanvangEnPlaats;
import nl.bzk.brp.preview.model.Plaats;
import org.springframework.jdbc.core.RowMapper;


/**
 * De klasse HuwelijkDatumAanvangEnPlaatsRowMapper waarin de basisgegevens van een huwelijk vertaald worden naar
 * properties.
 */
public class HuwelijkDatumAanvangEnPlaatsRowMapper implements RowMapper<HuwelijkDatumAanvangEnPlaats> {

    @Override
    public HuwelijkDatumAanvangEnPlaats mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final HuwelijkDatumAanvangEnPlaats hdaven = new HuwelijkDatumAanvangEnPlaats();
        hdaven.setDatumAanvang(new Datum(rs.getInt("datumaanvang")));

        final Plaats plaats = new Plaats();
        plaats.setCode(rs.getString("plaatscode"));
        plaats.setNaam(rs.getString("plaatsnaam"));
        hdaven.setPlaats(plaats);

        return hdaven;
    }

}
