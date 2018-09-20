/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess.impl;

import java.util.List;

import nl.bzk.brp.preview.dataaccess.HuwelijkDao;
import nl.bzk.brp.preview.model.HuwelijkDatumAanvangEnPlaats;
import nl.bzk.brp.preview.model.Persoon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * Implementatie van de interface voor de DAO voor toegang tot huwelijk data (BRP).
 */
public class HuwelijkDaoImpl implements HuwelijkDao {

    /**
     * De brp jdbc template.
     */
    @Autowired
    private NamedParameterJdbcTemplate brpJdbcTemplate;

    @Override
    public List<Persoon> haalOpHuwelijkPersonen(final Long handelingId) {
        final String sql = "SELECT\n"
                + "    pers.datgeboorte geboortedatum, \n"
                + "    pers.bsn bsn, \n"
                + "    pers.voornamen voornamen, \n"
                + "    pers.voorvoegsel voorvoegsel, \n"
                + "    pers.geslnaam achternaam,\n"
                + "    gesl.naam geslacht,\n"
                + "    NULL geboortegemeentecode,\n"
                + "    NULL geboortegemeentenaam\n"
                + "  FROM kern.admhnd kad\n"
                + "    JOIN kern.actie kac ON (kad.id = kac.admhnd)\n"
                + "    JOIN kern.his_huwelijkgeregistreerdpar hgp ON (hgp.actieinh = kac.id)\n"
                + "    JOIN kern.relatie rel ON (hgp.relatie = rel.id)\n"
                + "    JOIN kern.betr betr ON (betr.relatie = rel.id)\n"
                + "    JOIN kern.pers pers ON (pers.id = betr.pers)\n"
                + "    JOIN kern.geslachtsaand gesl ON (gesl.id = pers.geslachtsaand)\n"
                + "  WHERE kad.id = :id";

        final SqlParameterSource namedParameters = new MapSqlParameterSource("id", handelingId);

        return brpJdbcTemplate.query(sql, namedParameters, new PersoonRowMapper());
    }

    @Override
    public HuwelijkDatumAanvangEnPlaats haalOpHuwelijkDatumAanvangEnPlaats(final Long handelingId) {
        final String sql = "SELECT\n"
                + "    hgp.dataanv datumaanvang, pl.code plaatscode, pl.naam plaatsnaam\n"
                + "    FROM kern.admhnd kad\n"
                + "    JOIN kern.actie kac ON (kad.id = kac.admhnd)\n"
                + "    JOIN kern.his_huwelijkgeregistreerdpar hgp ON (hgp.actieinh = kac.id)\n"
                + "    JOIN kern.plaats pl ON (hgp.wplaanv = pl.id)\n"
                + "    WHERE kad.id = :id";

        final SqlParameterSource namedParameters = new MapSqlParameterSource("id", handelingId);

        return brpJdbcTemplate.queryForObject(sql, namedParameters, new HuwelijkDatumAanvangEnPlaatsRowMapper());
    }
}
