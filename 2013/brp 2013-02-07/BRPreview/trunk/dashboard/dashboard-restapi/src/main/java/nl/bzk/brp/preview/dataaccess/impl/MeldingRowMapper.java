/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.bzk.brp.preview.model.Melding;
import nl.bzk.brp.preview.model.MeldingSoort;
import org.springframework.jdbc.core.RowMapper;


/**
 * De klasse MeldingRowMapper waarin de kolommen van een Melding (behorend bij een bericht) vertaald worden naar
 * properties.
 */
public class MeldingRowMapper implements RowMapper<Melding> {

    @Override
    public Melding mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final MeldingSoort meldingSoort = new MeldingSoort(rs.getString("soortCode"), rs.getString("soortNaam"));

        final Melding melding = new Melding();
        melding.setSoort(meldingSoort);
        melding.setTekst(rs.getString("tekst"));

        return melding;
    }

}
