/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.brp.preview.model.AdministratieveHandeling;
import org.springframework.jdbc.core.RowMapper;


/**
 * De klasse AdministratieveHandelingRowMapper waarin de kolommen van een Administratieve Handeling vertaald worden naar
 * properties.
 */
public class AdministratieveHandelingRowMapper implements RowMapper<AdministratieveHandeling> {

    @Override
    public AdministratieveHandeling mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling();
        administratieveHandeling.setAdministratieveHandelingId(rs.getLong("id"));
        administratieveHandeling.setPartij(rs.getString("partij"));
        administratieveHandeling.setSoortAdministratieveHandelingCode(rs.getString("srtcode"));
        administratieveHandeling.setSoortAdministratieveHandeling(rs.getString("srt"));
        administratieveHandeling.setTijdstipOntlening(rs.getDate("tsontlening"));
        administratieveHandeling.setTijdstipRegistratie(rs.getDate("tsreg"));
        administratieveHandeling.setToelichtingOntlening(rs.getString("toelichtingontlening"));
        administratieveHandeling.setBzm(rs.getString("bzmnaam"));
        administratieveHandeling.setVerwerkingswijze(rs.getString("verwerkingswijze"));
        return administratieveHandeling;
    }

}
