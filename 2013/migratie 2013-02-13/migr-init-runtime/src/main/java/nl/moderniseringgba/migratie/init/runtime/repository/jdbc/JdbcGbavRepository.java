/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.init.runtime.repository.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import nl.moderniseringgba.migratie.init.runtime.domein.ConversieResultaat;
import nl.moderniseringgba.migratie.init.runtime.repository.GbavRepository;
import nl.moderniseringgba.migratie.init.runtime.repository.Lo3BerichtVerwerker;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * De JDBC implementatie van de GbavRepository.
 */
@Repository
public final class JdbcGbavRepository implements GbavRepository {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String CONVERSIE_RESULTAAT = "conversie_resultaat";
    private static final int FETCH_SIZE = 1000;
    private static final String FOUT_BIJ_VERWERKEN_VAN_LO3_BERICHT = "Fout bij verwerken van LO3 Berichten";
    private static final String UPDATE_INITVULLINGRESULT_SET_CONVERSIE_RESULTAAT_SQL =
            "UPDATE initvullingresult SET conversie_resultaat = :conversie_resultaat WHERE anummer = :anummer";

    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Set the datasource to use.
     * 
     * @param dataSource
     *            the datasource to use
     */
    @Inject
    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        ((JdbcTemplate) jdbcTemplate.getJdbcOperations()).setFetchSize(FETCH_SIZE);
    }

    /**
     * {@inheritDoc}
     *
     * @throws ParseException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void verwerkLo3Berichten(
            final ConversieResultaat zoekConversieResultaat,
            final Properties config,
            final Lo3BerichtVerwerker verwerker,
            final int batchGrootte) throws ParseException {
        final String selectInitVullingSql = getStringResourceData("/sql/Verwerk_init_vulling.sql");

        final Map<String, Object> parameters = maakFindLo3BerichtenParameters(null, zoekConversieResultaat, config);

        final SqlParameterSource namedParameters = new MapSqlParameterSource(parameters);
        final Lo3BerichtVerwerkerRowHandler rowHandler =
                new Lo3BerichtVerwerkerRowHandler(verwerker, batchGrootte);
        
        jdbcTemplate.query(selectInitVullingSql, namedParameters, rowHandler);

        try {
            verwerker.call();
            // CHECKSTYLE:OFF - Catch Exception: Nodig om call() fouten correct af te handelen
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            LOG.warn(FOUT_BIJ_VERWERKEN_VAN_LO3_BERICHT, e);
        }
    }

    private Map<String, Object> maakFindLo3BerichtenParameters(final Long plIdOffset,
                                                               final ConversieResultaat conversieResultaat,
                                                               final Properties config) throws ParseException {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        final String gemeenteString = config.getProperty("gemeente.code");
        final Date startDatum = dateFormat.parse(config.getProperty("datum.start"));
        final Date eindDatum = dateFormat.parse(config.getProperty("datum.eind"));
        final int gemeenteCode = Integer.parseInt(gemeenteString != null ? gemeenteString : "-1");
        final boolean anummerLimit = config.getProperty("anummer.limit") == null;

        valideerStartDatumEnEindDatum(startDatum, eindDatum);

        parameters.put("pl_id", plIdOffset);
        parameters.put("start_datum", startDatum);
        parameters.put("eind_datum", eindDatum);
        parameters.put("geen_gemeente", gemeenteCode == -1);
        parameters.put("gemeente_code", gemeenteCode);
        parameters.put("geen_anummer_beperking", anummerLimit);
        parameters.put(CONVERSIE_RESULTAAT, conversieResultaat.toString());
        return parameters;
    }

    private void valideerStartDatumEnEindDatum(final Date startDatum, final Date eindDatum) {
        if (startDatum.compareTo(eindDatum) >= 0) {
            throw new IllegalArgumentException("De datum.start moet kleiner zijn dan de datum.eind.");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createAndFillInitVullingTable() {
        final String sqlInitVulling = getStringResourceData("/sql/Initiele_vulling.sql");

        jdbcTemplate.getJdbcOperations().execute(sqlInitVulling);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createInitVullingTable() {
        final String sql = getStringResourceData("/sql/create_table_init_vulling.sql");

        jdbcTemplate.getJdbcOperations().execute(sql);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveLg01(final String lg01,
                         final Long aNummer,
                         final Integer gemeenteVanInschrijving,
                         final ConversieResultaat conversieResultaat) {
        final String sql =
                "INSERT INTO initvullingresult(gbav_pl_id, anummer, berichttype, bericht_inhoud, "
                + "datumtijd_opname_in_gbav, gemeente_van_inschrijving, conversie_resultaat) "
                + "VALUES ((SELECT (COALESCE(MAX(gbav_pl_id),0) +1) FROM initvullingresult), "
                + ":anummer, :berichttype, :bericht_inhoud, :datumtijd, :gemeente_inschrijving, "
                + ":conversie_resultaat);";
        final Map<String, Object> parameters = new HashMap<String, Object>();
        // CHECKSTYLE:OFF: magic number en dubbele string
        parameters.put("anummer", aNummer);
        parameters.put("berichttype", 1111); // Dit is het type voor Lg01
        parameters.put("bericht_inhoud", lg01);
        parameters.put("datumtijd", new Date());
        parameters.put("gemeente_inschrijving", gemeenteVanInschrijving);
        parameters.put(CONVERSIE_RESULTAAT, conversieResultaat.toString());
        // CHECKSTYLE:ON

        jdbcTemplate.update(sql, new MapSqlParameterSource(parameters));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateLo3BerichtStatus(final List<Long> aNummers, final ConversieResultaat conversieResultaat) {
        final String conversieResultaatString = conversieResultaat.toString();
        final String sql = UPDATE_INITVULLINGRESULT_SET_CONVERSIE_RESULTAAT_SQL;

        final List<Map<String, Object>> batchParameters = new ArrayList<Map<String, Object>>(aNummers.size());
        for (final Long aNummer : aNummers) {
            final Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("anummer", aNummer);
            parameters.put(CONVERSIE_RESULTAAT, conversieResultaatString);
            batchParameters.add(parameters);
        }

        jdbcTemplate.batchUpdate(sql, (Map<String, ?>[]) batchParameters.toArray());
    }

    /**
     * Haalt de string op die in het opgegeven resource pad zit.
     *
     * @param resourcePath Het pad van de resource.
     * @return String representatie van de resource.
     */
    private String getStringResourceData(final String resourcePath) {
        final InputStream inputStream = getClass().getResourceAsStream(resourcePath);
        try {
            final String data = IOUtils.toString(inputStream);
            inputStream.close();
            return data;
        } catch (final IOException e) {
            LOG.error("Fout bij lezen van resource file: " + resourcePath, e);
            return null;
        }
    }

    /**
     * Row handler om berichten te verwerken.
     */
    private final class Lo3BerichtVerwerkerRowHandler implements RowCallbackHandler {
        private final Lo3BerichtVerwerker verwerker;
        private int batchGrootte;
        private int teller;

        public Lo3BerichtVerwerkerRowHandler(
                final Lo3BerichtVerwerker verwerker,
                final int batchGrootte) {
            this.verwerker = verwerker;
            this.batchGrootte = batchGrootte;
        }

        @Override
        public void processRow(final ResultSet rs) throws SQLException {
            final String berichtInhoud = rs.getString("bericht_inhoud");
            final long aNummer = rs.getLong("anummer");

            verwerker.addLo3Bericht(berichtInhoud, aNummer);
            teller += 1;
            
            if (teller >= batchGrootte) {
                try {
                    verwerker.call();
                    // CHECKSTYLE:OFF - Catch Exception: Nodig om call() fouten correct af te handelen
                } catch (final Exception e) {
                    // CHECKSTYLE:ON
                    LOG.warn(FOUT_BIJ_VERWERKEN_VAN_LO3_BERICHT, e);
                }
            }
        }
    }
}
