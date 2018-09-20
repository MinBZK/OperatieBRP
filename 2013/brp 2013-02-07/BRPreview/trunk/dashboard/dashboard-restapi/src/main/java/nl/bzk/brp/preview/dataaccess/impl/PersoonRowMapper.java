/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import nl.bzk.brp.preview.model.Gemeente;
import nl.bzk.brp.preview.model.Geslachtsnaamcomponent;
import nl.bzk.brp.preview.model.Persoon;
import org.springframework.jdbc.core.RowMapper;


/**
 * De klasse PersoonRowMapper waarin de kolommen van een Persoon vertaald worden naar
 * properties.
 */
public class PersoonRowMapper implements RowMapper<Persoon> {

    @Override
    public Persoon mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Persoon persoon = new Persoon();
        persoon.setBsn(rs.getInt("bsn"));
        persoon.setDatumGeboorte(rs.getInt("geboortedatum"));
        persoon.setGeslacht(rs.getString("geslacht"));

        final Gemeente gemeenteGeboorte = new Gemeente();
        gemeenteGeboorte.setCode(rs.getString("geboortegemeentecode"));
        gemeenteGeboorte.setNaam(rs.getString("geboortegemeentenaam"));
        persoon.setGemeenteGeboorte(gemeenteGeboorte);

        final String voorNamen = rs.getString("voornamen");
        persoon.setVoornamen(Arrays.asList(voorNamen.split(" ")));

        final Geslachtsnaamcomponent geslachtsnaamcomponent = new Geslachtsnaamcomponent();
        geslachtsnaamcomponent.setVoorvoegsel(rs.getString("voorvoegsel"));
        geslachtsnaamcomponent.setNaam(rs.getString("achternaam"));
        persoon.setGeslachtsnaamcomponenten(Arrays.asList(new Geslachtsnaamcomponent[]{geslachtsnaamcomponent}));

        return persoon;
    }

}
