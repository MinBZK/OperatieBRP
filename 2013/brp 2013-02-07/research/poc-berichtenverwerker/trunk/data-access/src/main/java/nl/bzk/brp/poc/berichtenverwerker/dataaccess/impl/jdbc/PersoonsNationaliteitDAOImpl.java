/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.dataaccess.impl.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import nl.bzk.brp.poc.berichtenverwerker.dataaccess.PersoonsNationaliteitDAO;
import nl.bzk.brp.poc.berichtenverwerker.model.Actie;
import nl.bzk.brp.poc.berichtenverwerker.model.Nation;
import nl.bzk.brp.poc.berichtenverwerker.model.Pers;
import nl.bzk.brp.poc.berichtenverwerker.model.Persnation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;


/**
 * Direct SQL (JDBC) specifieke implementatie van de {@link PersoonsNationaliteitDAO} interface.
 */
public class PersoonsNationaliteitDAOImpl implements PersoonsNationaliteitDAO {

    private static final String        SQL_PERSOONSNATIONALITEIT_OP_BASIS_VAN_ID  =
            "select pn.*, n.naam n_naam, p.geslnaam p_naam from KERN.PERSNATION pn, KERN.NATION n, KERN.PERS p "
            + "where pn.id = :id and pn.pers = p.id and pn.nation = n.id";
    private static final String        SQL_INSERT_PERSOONSNATIONALITEIT           =
            "insert into KERN.PERSNATION (id, pers, nation, dataanvgel, actiebegin, dattijdreg) values "
            + "(:id, :pers, :nation, :dataanvgel, :actiebegin, :dattijdreg)";
    private static final String        SQL_INSERT_HISTORISCHPERSOONSNATIONALITEIT =
            "insert into KERN.HISPERSNATION (id, persnation, dataanvgel, actiebegin, dattijdreg) values "
            + "(:id, :persnation, :dataanvgel, :actiebegin, :dattijdreg)";
    private static final String        SQL_HISTORISCHPERSOONSNATIONALITEIT_ID_VOOR_VERWIJDERING  =
            "select id from KERN.HISPERSNATION where persnation = :persnation and dataanvgel = :dataanvgel and "
            + "dattijdverval is null";
    private static final String        SQL_UPDATE_HISTORISCHPERSOONSNATIONALITEIT =
            "update KERN.HISPERSNATION set persnation = null, actieeinde = :actieeinde, "
            + "dattijdverval = :dattijdverval where id = :id";
    private static final String        SQL_DELETE_PERSOONSNATIONALITEIT           =
            "delete from KERN.PERSNATION where id = :id";

    @Autowired
    private String sequenceTemplate;
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public final void setDataSource(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public final Persnation vindPersoonsNationaliteitOpBasisVanId(final long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(SQL_PERSOONSNATIONALITEIT_OP_BASIS_VAN_ID, namedParameters,
                new PersoonsNationaliteitMapper());
    }

    @Override
    public final void voegToePersoonsNationaliteit(final Persnation persoonsNationaliteit) {
        long newId = jdbcTemplate.queryForLong(String.format(sequenceTemplate, "SEQ_PERSNATION"),
                Collections.<String, Object>emptyMap());
        long newHisId = jdbcTemplate.queryForLong(String.format(sequenceTemplate, "SEQ_HISPERSNATION"),
                Collections.<String, Object>emptyMap());
        persoonsNationaliteit.setId(newId);

        Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("id", persoonsNationaliteit.getId());
        namedParameters.put("pers", persoonsNationaliteit.getPers().getId());
        namedParameters.put("nation", persoonsNationaliteit.getNation().getId());
        namedParameters.put("dataanvgel", persoonsNationaliteit.getDataanvgel());
        namedParameters.put("actiebegin", persoonsNationaliteit.getActie().getId());
        namedParameters.put("dattijdreg", persoonsNationaliteit.getDattijdreg());

        this.jdbcTemplate.update(SQL_INSERT_PERSOONSNATIONALITEIT, namedParameters);

        namedParameters.put("id", newHisId);
        namedParameters.put("persnation", persoonsNationaliteit.getId());
        this.jdbcTemplate.update(SQL_INSERT_HISTORISCHPERSOONSNATIONALITEIT, namedParameters);
    }

    @Override
    public final void verwijderPersoonsNationaliteit(final Actie actie, final Persnation persoonsNationaliteit) {
        Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("persnation", persoonsNationaliteit.getId());
        namedParameters.put("dataanvgel", persoonsNationaliteit.getDataanvgel());
        long hisId = this.jdbcTemplate.queryForLong(
                SQL_HISTORISCHPERSOONSNATIONALITEIT_ID_VOOR_VERWIJDERING, namedParameters);

        namedParameters = new HashMap<String, Object>();
        namedParameters.put("id", hisId);
        namedParameters.put("actieeinde", actie.getId());
        namedParameters.put("dattijdverval", new Date());
        this.jdbcTemplate.update(SQL_UPDATE_HISTORISCHPERSOONSNATIONALITEIT, namedParameters);

        namedParameters = new HashMap<String, Object>();
        namedParameters.put("id", persoonsNationaliteit.getId());
        this.jdbcTemplate.update(SQL_DELETE_PERSOONSNATIONALITEIT, namedParameters);
    }

    /**
     * Interne mapping class die op basis van een rij resultaat uit een SQL call de PersoonsNationaliteit bouwt.
     */
    private static final class PersoonsNationaliteitMapper implements RowMapper<Persnation> {

        @Override
        public Persnation mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            Pers persoon = new Pers();
            persoon.setId(rs.getLong("pers"));
            persoon.setGeslnaam(rs.getString("p_naam"));
            Nation nationaliteit = new Nation();
            nationaliteit.setId(rs.getInt("nation"));
            nationaliteit.setNaam(rs.getString("n_naam"));

            Persnation persoonsNationaliteit = new Persnation();
            persoonsNationaliteit.setId(rs.getLong("id"));
            persoonsNationaliteit.setPers(persoon);
            persoonsNationaliteit.setNation(nationaliteit);
            persoonsNationaliteit.setDattijdreg(rs.getDate("dattijdreg"));
            persoonsNationaliteit.setDataanvgel(rs.getBigDecimal("dataanvgel"));

            return persoonsNationaliteit;
        }
    }
}
