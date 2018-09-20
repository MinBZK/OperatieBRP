/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.brp.preview.model.Adres;
import nl.bzk.brp.preview.model.Gemeente;
import nl.bzk.brp.preview.model.Plaats;
import org.springframework.jdbc.core.RowMapper;


/**
 * De klasse AdresRowMapper waarin de kolommen van een Adres vertaald worden naar
 * properties.
 */
public class AdresRowMapper implements RowMapper<Adres> {

    @Override
    public Adres mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Adres adres = new Adres();
        adres.setDatumAanvang(rs.getInt("datumaanvang"));

        final Gemeente gemeente = new Gemeente();
        gemeente.setCode(rs.getString("gemeentecode"));
        gemeente.setNaam(rs.getString("gemeentenaam"));
        adres.setGemeente(gemeente);

        final Plaats plaats = new Plaats();
        plaats.setCode(rs.getString("plaatscode"));
        plaats.setNaam(rs.getString("plaatsnaam"));
        adres.setPlaats(plaats);

        adres.setStraat(rs.getString("straatnaam"));
        adres.setHuisnummer(rs.getString("huisnr"));
        adres.setHuisnummertoevoeging(rs.getString("huisnrtoevoeging"));

        return adres;
    }

}
