/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.model.OndersteundeBijhoudingsTypes;
import org.springframework.jdbc.core.RowMapper;


/**
 * De Class BerichtRowMapper, deze wordt gebruikt om rijen te mappen naar Bericht-objecten.
 */
public class BerichtRowMapper implements RowMapper<Bericht> {

    @Override
    public Bericht mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        Bericht bericht = new Bericht();
        bericht.setBerichtId(rs.getLong("id"));
        bericht.setPartij(rs.getString("partij"));
        bericht.setBericht(rs.getString("bericht"));
        bericht.setBerichtDetails(rs.getString("berichtdetails"));
        bericht.setAantalMeldingen(rs.getInt("aantalmeldingen"));
        Calendar verzondenOp = Calendar.getInstance();
        Timestamp timestamp = rs.getTimestamp("tsverzonden", verzondenOp);
        if (timestamp != null) {
            verzondenOp.setTimeInMillis(timestamp.getTime());
        }
        bericht.setVerzondenOp(verzondenOp);
        bericht.setBurgerZakenModule(rs.getString("bzm"));
        bericht.setSoortBijhouding(OndersteundeBijhoudingsTypes.valueOf(rs.getString("soortactie")));
        bericht.setPrevalidatie(rs.getBoolean("indprevalidatie"));
        return bericht;
    }

}
