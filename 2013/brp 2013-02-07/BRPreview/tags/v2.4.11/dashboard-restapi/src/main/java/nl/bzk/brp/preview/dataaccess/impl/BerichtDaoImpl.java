/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.bzk.brp.preview.dataaccess.BerichtenDao;
import nl.bzk.brp.preview.model.Bericht;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.incrementer.AbstractSequenceMaxValueIncrementer;


/**
 * De Class BerichtDaoImpl.
 */
public class BerichtDaoImpl implements BerichtenDao {

    private static final String WHERE_VANAF = " tsverzonden > :vanaf ";

    private static final String ORDERBY_TIJDSTEMPEL = " order by tsverzonden desc nulls last";

    /** De constante SELECT_BERICHTEN. */
    private static final String SELECT_BERICHTEN = "select * from dashboard.berichten";

    /** De constante WHERE_PARTIJ. */
    private static final String WHERE_PARTIJ = " partij= :partij";

    private static final String WHERE = " where ";

    /** De logger. */
    private static Logger                       logger = LoggerFactory.getLogger(BerichtDaoImpl.class);

    /** De jdbc template. */
    @Autowired
    private NamedParameterJdbcTemplate          jdbcTemplate;

    /** De gebruikte sequence. */
    private AbstractSequenceMaxValueIncrementer sequence;

    /**
     * Zet de sequence.
     *
     * @param sequence the new sequence
     */
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
        String sql = SELECT_BERICHTEN + ORDERBY_TIJDSTEMPEL;

        return getBerichten(sql, null);
    }

    /**
     * Haal de berichten op van de bsn.
     *
     * @param bsn de bsn
     * @return the berichten op bsn
     * @see nl.bzk.brp.preview.dataaccess.BerichtenDao#getBerichtenOpBsn(java.lang.Integer)
     */
    @Override
    public final List<Bericht> getBerichtenOpBsn(final Integer bsn) {

        // Prepare our SQL statement
        String sql =
            "select ber.* from dashboard.berichten ber, dashboard.bericht_bsn bsn"
                + " where ber.id = bsn.bericht and bsn.bsn = %d order by tsverzonden desc nulls last";

        sql = String.format(sql, bsn);

        // Maps a SQL result to a Java object
        RowMapper<Bericht> mapper = new BerichtRowMapper();

        List<Bericht> berichten = jdbcTemplate.query(sql, new HashMap<String, Object>(), mapper);
        logger.debug("Aantal berichten gevonden:" + berichten.size());

        return berichten;
    }



    /**
     * Haal de berichten op vanaf het gespecificeerde tijdstip.
     *
     * @param vanaf de vanaf
     * @return the berichten vanaf
     * @see nl.bzk.brp.preview.dataaccess.BerichtenDao#getBerichtenVanaf(java.util.Calendar)
     */
    @Override
    public final List<Bericht> getBerichtenVanaf(final Calendar vanaf) {

        // Prepare our SQL statement
        String sql =
            SELECT_BERICHTEN + WHERE
                + WHERE_VANAF
                + ORDERBY_TIJDSTEMPEL;

        //sql = String.format(sql, vanaf);
        SqlParameterSource namedParameters = new MapSqlParameterSource("vanaf", vanaf);

        return getBerichten(sql, namedParameters);
    }


    @Override
    public List<Bericht> getBerichtenVoorPartij(final String partij) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("partij", partij);

        // Prepare our SQL statement
        String sql = SELECT_BERICHTEN + WHERE + WHERE_PARTIJ + ORDERBY_TIJDSTEMPEL;

        return getBerichten(sql,namedParameters);
    }

    @Override
    public List<Bericht> getBerichtenVoorPartijVanaf(final String partij, final Calendar vanaf) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("partij", partij).addValue("vanaf", vanaf);


        // Prepare our SQL statement
        String sql = SELECT_BERICHTEN + WHERE + WHERE_VANAF + " AND " + WHERE_PARTIJ + ORDERBY_TIJDSTEMPEL;

        return getBerichten(sql,namedParameters);

    }


    private List<Bericht> getBerichten(final String sql, final SqlParameterSource parameters) {
        // Maps a SQL result to a Java object
        RowMapper<Bericht> mapper = new BerichtRowMapper();

        List<Bericht> berichten = jdbcTemplate.query(sql, parameters, mapper);

        logger.debug("Aantal berichten gevonden:" + berichten.size());
        if(berichten.size() > 0) {
            logger.debug("Laatste bericht:" + berichten.get(0).toString());
        }

        return berichten;

    }
    /**
     * Opslaan van het binnengekomen bericht van de BRP.
     *
     * @param bericht de bericht
     * @see nl.bzk.brp.preview.dataaccess.BerichtenDao#opslaan(nl.bzk.brp.preview.model.Bericht)
     */
    @Override
    public final void opslaan(final Bericht bericht) {
        long berichtId = sequence.nextLongValue();
        opslaanBericht(berichtId, bericht);
        if (bericht.getBurgerservicenummers() != null) {
            for (Integer bsn : bericht.getBurgerservicenummers()) {
                opslaanBsn(berichtId, bsn);
                logger.debug("Bericht opgeslagen voor BSN:" + bsn);
            }
        }
    }

    /**
     * Opslaan bericht.
     *
     * @param id de id
     * @param bericht de bericht
     */
    private void opslaanBericht(final long id, final Bericht bericht) {

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
                + "id, partij, bericht, berichtdetails, aantalmeldingen, tsverzonden, bzm, "
                + "soortactie, indprevalidatie" + ") values ("
                + ":id, :partij, :bericht, :berichtdetails, :aantalmeldingen, :tsverzonden,"
                + ":bzm, :soortactie, :indprevalidatie" + ");";

        int affected = jdbcTemplate.update(sql, namedParameters);
        if (affected > 0) {
            logger.debug("Bericht " + id + " opgeslagen voor partij " + bericht.getPartij());
        }
    }

    /**
     * Opslaan van de bsn bij het bericht zodat berichten getoond kunnen worden bij het opvragen van persoonsgegevens op
     * basis van BSN.
     *
     * @param berichtId de bericht id
     * @param bsn de bsn
     */
    private void opslaanBsn(final long berichtId, final Integer bsn) {
        long id = sequence.nextLongValue();

        MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        namedParameters.addValue("berichtId", berichtId);
        namedParameters.addValue("bsn", bsn);

        String sql = "insert into dashboard.bericht_bsn (id, bericht, bsn) values (:id, :berichtId, :bsn);";

        int affected = jdbcTemplate.update(sql, namedParameters);
        if (affected > 0) {
            logger.debug("BSN " + bsn + " opgeslagen bij bericht " + berichtId);
        }
    }

    @Override
    public Set<Integer> getBsns(final int aantal) {
        String sql = "select bsn from dashboard.bericht_bsn order by id desc limit " + aantal;
        List<Integer> bsnList = jdbcTemplate.queryForList(sql, (Map<String, ?>) null, Integer.class);
        Set<Integer> bsnSet = new HashSet<Integer>(bsnList);
        return bsnSet;
    }

}
