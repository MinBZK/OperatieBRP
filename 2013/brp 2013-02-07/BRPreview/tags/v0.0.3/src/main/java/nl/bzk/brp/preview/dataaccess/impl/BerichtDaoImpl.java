/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public class BerichtDaoImpl implements BerichtenDao {

    private static Logger              logger = LoggerFactory.getLogger(BerichtDaoImpl.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Haalt alle berichten op.
     *
     * @return een lijst van berichten
     */
    public List<Bericht> getAlleBerichten() {

        // Prepare our SQL statement
        String sql = "select * from dashboard.berichten";

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
                verzondenOp.setTimeInMillis(rs.getTimestamp("tsverzonden", verzondenOp).getTime());
                bericht.setVerzondenOp(verzondenOp);
                bericht.setBurgerZakenModule(rs.getString("bzm"));
                bericht.setSoortBijhouding(OndersteundeBijhoudingsTypes.valueOf(rs.getString("soortactie")));
                bericht.setPrevalidatie(rs.getBoolean("indprevalidatie"));
                // TODO: vul hier de rest in...
                return bericht;
            }
        };

        List<Bericht> berichten = jdbcTemplate.query(sql, new HashMap<String, Object>(), mapper);
        logger.debug("Aantal berichten gevonden in de database:", berichten.size());

        return berichten;
    }

}
