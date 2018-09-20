/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess.impl;

import java.util.List;

import nl.bzk.brp.preview.dataaccess.AdministratieveHandelingDao;
import nl.bzk.brp.preview.model.AdministratieveHandeling;
import nl.bzk.brp.preview.model.Melding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;


/**
 * Implementatie van de interface voor de DAO voor toegang tot administratieve handeling data (BRP).
 */
public class AdministratieveHandelingDaoImpl implements AdministratieveHandelingDao {

    /**
     * De brp jdbc template.
     */
    @Autowired
    private NamedParameterJdbcTemplate brpJdbcTemplate;

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.preview.dataaccess.AdministratieveHandelingDao#haalOp(java.lang.Long)
     */
    @Override
    public AdministratieveHandeling haalOp(final Long id) {
        String sql = "SELECT "
                + "         ka.id AS id, "
                + "         ks.code AS srtcode, "
                + "         ks.naam AS srt, "
                + "         kp.naam AS partij, "
                + "         ka.tsontlening AS tsontlening, "
                + "         ka.toelichtingontlening AS toelichtingontlening, "
                + "         ka.tsreg AS tsreg, "
                + "         bzm.naam AS bzmnaam, "
                + "         br.verwerkingswijze AS verwerkingswijze "
                + "     FROM kern.admhnd ka "
                + "     JOIN kern.partij kp ON (ka.partij = kp.id) "
                + "     JOIN kern.srtadmhnd ks ON (ka.srt = ks.id) "
                + "     JOIN ber.ber br ON (br.admhnd = ka.id) "
                + "     JOIN ber.srtber sbr ON (br.srt = sbr.id) "
                + "     JOIN ber.burgerzakenmodule bzm ON (sbr.module = bzm.id) "
                + "     WHERE ka.id = :id";

        final SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);

        return brpJdbcTemplate.queryForObject(sql, namedParameters, new AdministratieveHandelingRowMapper());
    }

    /**
     * Haal meldingen op.
     *
     * @param id de id
     * @return de list
     * @see AdministratieveHandelingDao#haalMeldingenOp(Long)
     */
    @Override
    public List<Melding> haalMeldingenOp(final Long id) {
        String sql = "SELECT "
                + "           mldsrt.code AS soortCode, "
                + "           mldsrt.naam AS soortNaam, "
                + "           mld.melding AS tekst"
                + "       FROM ber.melding mld "
                + "       JOIN ber.bermelding bmld ON (mld.id = bmld.melding) "
                + "       JOIN ber.ber br ON (br.id = bmld.ber) "
                + "       JOIN kern.admhnd ah ON (ah.id = br.admhnd) "
                + "       JOIN ber.srtmelding mldsrt ON (mld.srt = mldsrt.id) "
                + "       WHERE ah.id = :id";

        final SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);

        return brpJdbcTemplate.query(sql, namedParameters, new MeldingRowMapper());
    }

}
