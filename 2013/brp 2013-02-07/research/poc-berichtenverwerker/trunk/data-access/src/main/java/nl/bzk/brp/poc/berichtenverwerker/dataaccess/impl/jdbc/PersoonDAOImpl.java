/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.dataaccess.impl.jdbc;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import nl.bzk.brp.poc.berichtenverwerker.dataaccess.PersoonDAO;
import nl.bzk.brp.poc.berichtenverwerker.model.Pers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;


/**
 * Direct SQL (JDBC) specifieke implementatie van de {@link PersoonDAO} interface.
 */
public class PersoonDAOImpl implements PersoonDAO {

    private static final String        SQL_PERSOON_OP_BASIS_VAN_ID  = "select p.* from KERN.PERS p where p.id = :id";
    private static final String        SQL_PERSOON_OP_BASIS_VAN_BSN = "select p.* from KERN.PERS p where p.bsn = :bsn";

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public final void setDataSource(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public final Pers vindPersoonOpBasisVanId(final long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(SQL_PERSOON_OP_BASIS_VAN_ID, namedParameters, new PersoonMapper());
    }

    @Override
    public final Pers vindPersoonOpBasisVanBsn(final BigDecimal bsn) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("bsn", bsn);
        return jdbcTemplate.queryForObject(SQL_PERSOON_OP_BASIS_VAN_BSN, namedParameters, new PersoonMapper());
    }

    /**
     * Interne mapping class die op basis van een rij resultaat uit een SQL call de Persoon bouwt.
     */
    private static final class PersoonMapper implements RowMapper<Pers> {

        @Override
        public Pers mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            Pers persoon = new Pers();
            persoon.setId(rs.getLong("id"));
            persoon.setBsn(rs.getBigDecimal("bsn"));
            persoon.setVoornamen(rs.getString("voornamen"));
            persoon.setGeslnaam(rs.getString("geslnaam"));

            return persoon;
        }
    }
}
