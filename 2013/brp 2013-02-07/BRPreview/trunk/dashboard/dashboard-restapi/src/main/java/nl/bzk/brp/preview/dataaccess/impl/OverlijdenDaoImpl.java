/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess.impl;

import nl.bzk.brp.preview.dataaccess.OverlijdenDao;
import nl.bzk.brp.preview.model.Overlijden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * Implementatie van de interface voor de DAO voor toegang tot overlijden data (BRP).
 */
public class OverlijdenDaoImpl implements OverlijdenDao {

    /**
     * De brp jdbc template.
     */
    @Autowired
    private NamedParameterJdbcTemplate brpJdbcTemplate;

    @Override
    public Overlijden haalOpOverlijden(final Long handelingId) {
        final String sql = "SELECT\n"
                + "  kpe.datgeboorte geboortedatum,\n"
                + "  kpe.bsn bsn,\n"
                + "  kpe.voornamen voornamen,\n"
                + "  kpe.voorvoegsel voorvoegsel,\n"
                + "  kpe.geslnaam achternaam,\n"
                + "  gesl.naam geslacht,\n"
                + "  gemoverl.code overlijdengemeentecode,\n"
                + "  gemoverl.naam overlijdengemeentenaam,\n"
                + "  overl.datoverlijden overlijdendatum,\n"
                + "  NULL geboortegemeentecode,\n"
                + "  NULL geboortegemeentenaam\n"
                + "FROM kern.admhnd kad\n"
                + "JOIN kern.actie kac ON (kad.id = kac.admhnd)\n"
                + "JOIN kern.his_persoverlijden overl ON (overl.actieinh = kac.id)\n"
                + "JOIN kern.pers kpe ON (kpe.id = overl.pers)\n"
                + "LEFT JOIN kern.partij gemoverl ON (gemoverl.id = overl.gemoverlijden)\n"
                + "JOIN kern.geslachtsaand gesl ON (gesl.id = kpe.geslachtsaand)\n"
                + "WHERE kad.id = :id";

        final SqlParameterSource namedParameters = new MapSqlParameterSource("id", handelingId);

        return brpJdbcTemplate.queryForObject(sql, namedParameters, new OverlijdenRowMapper());
    }
}
