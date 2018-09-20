/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import nl.bzk.brp.preview.dataaccess.BerichtenDao;
import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.model.OndersteundeBijhoudingsTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.incrementer.AbstractSequenceMaxValueIncrementer;


public class BerichtDaoImpl implements BerichtenDao {

    private static Logger                       logger = LoggerFactory.getLogger(BerichtDaoImpl.class);

    @Autowired
    private NamedParameterJdbcTemplate          jdbcTemplate;

    private AbstractSequenceMaxValueIncrementer sequence;

    public void setSequence(final AbstractSequenceMaxValueIncrementer sequence) {
        this.sequence = sequence;
    }

    /**
     * Haalt alle berichten op.
     *
     * @return een lijst van berichten
     */
    @Override
    public List<Bericht> getAlleBerichten() {

        // Prepare our SQL statement
        String sql = "select * from dashboard.berichten order by tsverzonden desc nulls last";

        // Maps a SQL result to a Java object
        RowMapper<Bericht> mapper = new RowMapper<Bericht>() {

            @Override
            public Bericht mapRow(final ResultSet rs, final int rowNum) throws SQLException {
                Bericht bericht = new Bericht();
                bericht.setPartij(rs.getString("partij"));
                bericht.setBericht(rs.getString("bericht"));
                bericht.setBerichtDetails(rs.getString("berichtdetails"));
                bericht.setAantalMeldingen(rs.getInt("aantalmeldingen"));
                Calendar verzondenOp = Calendar.getInstance();
                Timestamp timestamp = rs.getTimestamp("tsverzonden", verzondenOp);
                if (timestamp != null) {
                    verzondenOp.setTimeInMillis(timestamp.getTime());
                }
                bericht.setVerzondenOp(verzondenOp);
                bericht.setBurgerZakenModule(rs.getString("bzm"));
                bericht.setSoortBijhouding(OndersteundeBijhoudingsTypes.valueOf(rs.getString("soortactie")));
                bericht.setPrevalidatie(rs.getBoolean("indprevalidatie"));
                return bericht;
            }
        };

        List<Bericht> berichten = jdbcTemplate.query(sql, new HashMap<String, Object>(), mapper);
        logger.debug("Aantal berichten gevonden in de database:", berichten.size());

        return berichten;
    }

    @Override
    public void opslaan(final Bericht bericht) {
        long id = sequence.nextLongValue();

        MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        namedParameters.addValue("partij", bericht.getPartij());
        namedParameters.addValue("bericht", bericht.getBericht());
        namedParameters.addValue("berichtdetails", bericht.getBerichtDetails());
        namedParameters.addValue("aantalmeldingen", bericht.getAantalMeldingen());
        namedParameters.addValue("tsverzonden", bericht.getVerzondenOp());
        namedParameters.addValue("bzm", bericht.getBurgerZakenModule());
        namedParameters.addValue("soortactie", bericht.getSoortBijhouding().name());
        namedParameters.addValue("indprevalidatie", bericht.isPrevalidatie());

        String sql =
            "insert into dashboard.berichten ("
                + "id, partij, bericht, berichtdetails, aantalmeldingen, tsverzonden, bzm, soortactie, indprevalidatie"
                + ") values ("
                + ":id, :partij, :bericht, :berichtdetails, :aantalmeldingen, :tsverzonden, :bzm, :soortactie, :indprevalidatie"
                + ");";

        jdbcTemplate.update(sql, namedParameters);
    }

}
