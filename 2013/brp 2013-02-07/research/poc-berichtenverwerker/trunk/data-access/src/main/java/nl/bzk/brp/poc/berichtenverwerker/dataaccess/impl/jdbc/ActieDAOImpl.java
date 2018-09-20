/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.dataaccess.impl.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import nl.bzk.brp.poc.berichtenverwerker.dataaccess.ActieDAO;
import nl.bzk.brp.poc.berichtenverwerker.model.Actie;
import nl.bzk.brp.poc.berichtenverwerker.model.Gem;
import nl.bzk.brp.poc.berichtenverwerker.model.Srtactie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;


/**
 * Direct SQL (JDBC) specifieke implementatie van de {@link ActieDAO} interface.
 */
public class ActieDAOImpl implements ActieDAO {

    private static final String        SQL_ACTIE_OP_BASIS_VAN_ID =
            "select a.*, sa.naam as sa_naam, g.naam as g_naam from KERN.ACTIE a, KERN.SRTACTIE sa, KERN.GEM g "
            + "where a.id = :id and a.srt = sa.id and a.gem = g.id";
    private static final String        SQL_INSERT_ACTIE          =
            "insert into KERN.ACTIE (id, srt, gem, tijdstipreg) values "
            + "(:id, :srt, :gem, :reg)";

    @Autowired
    private String sequenceTemplate;
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public final void setDataSource(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public final Actie vindActieOpBasisVanId(final long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(SQL_ACTIE_OP_BASIS_VAN_ID, namedParameters, new ActieMapper());
    }

    @Override
    public final void voegToeActie(final Actie actie) {
        long newId = jdbcTemplate.queryForLong(String.format(sequenceTemplate, "SEQ_ACTIE"),
                Collections.<String, Object>emptyMap());
        actie.setId(newId);

        Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("id", actie.getId());
        namedParameters.put("gem", actie.getGem().getId());
        namedParameters.put("srt", actie.getSrtactie().getId());
        namedParameters.put("reg", actie.getTijdstipreg());

        this.jdbcTemplate.update(SQL_INSERT_ACTIE, namedParameters);
    }

    /**
     * Interne mapping class die op basis van een rij resultaat uit een SQL call de Actie bouwt.
     */
    private static final class ActieMapper implements RowMapper<Actie> {

        @Override
        public Actie mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            Srtactie soortActie = new Srtactie();
            soortActie.setId(rs.getInt("srt"));
            soortActie.setNaam(rs.getString("sa_naam"));

            Gem gemeente = new Gem();
            gemeente.setId(rs.getInt("gem"));
            gemeente.setNaam(rs.getString("g_naam"));

            Actie actie = new Actie();
            actie.setId(rs.getLong("id"));
            actie.setSrtactie(soortActie);
            actie.setGem(gemeente);
            actie.setTijdstipreg(rs.getDate("tijdstipreg"));
            return actie;
        }
    }
}
