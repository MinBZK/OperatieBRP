/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.brp.preview.model.Datum;
import nl.bzk.brp.preview.model.Gemeente;
import nl.bzk.brp.preview.model.Overlijden;
import nl.bzk.brp.preview.model.Persoon;
import org.springframework.jdbc.core.RowMapper;


/**
 * De klasse PersoonRowMapper waarin de kolommen van een Persoon vertaald worden naar
 * properties.
 */
public class OverlijdenRowMapper implements RowMapper<Overlijden> {

    @Override
    public Overlijden mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final PersoonRowMapper persoonRowMapper = new PersoonRowMapper();
        final Persoon persoon = persoonRowMapper.mapRow(rs, rowNum);

        final Overlijden overlijden = new Overlijden();
        overlijden.setPersoon(persoon);

        overlijden.setDatumOverlijden(new Datum(rs.getInt("overlijdendatum")));

        final Gemeente gemeente = new Gemeente();
        gemeente.setCode(rs.getString("overlijdengemeentecode"));
        gemeente.setNaam(rs.getString("overlijdengemeentenaam"));
        overlijden.setGemeenteOverlijden(gemeente);

        return overlijden;
    }

}
