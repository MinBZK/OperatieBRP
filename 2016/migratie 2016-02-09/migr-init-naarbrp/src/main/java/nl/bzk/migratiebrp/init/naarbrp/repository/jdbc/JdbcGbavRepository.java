/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.repository.GbavRepository;
import nl.bzk.migratiebrp.init.naarbrp.service.bericht.SyncNaarBrpBericht;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.SynchronisatieBerichtVerwerker;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * De JDBC implementatie van de GbavRepository.
 */
@Repository
public final class JdbcGbavRepository extends AbstractJdbcRepository implements GbavRepository {

    private static final String START_DATUM_LEEG = "start_datum_leeg";
    private static final String EIND_DATUM_LEEG = "eind_datum_leeg";
    private static final String GEMEENTE_CODE_LEEG = "gemeente_code_leeg";
    private static final String PL_ID = "pl_id";
    private static final String START_DATUM = "start_datum";
    private static final String EIND_DATUM = "eind_datum";
    private static final String GEEN_ANUMMER_BEPERKING = "geen_anummer_beperking";
    private static final String GEMEENTE_CODE = "gemeente_code";
    private static final String ANUMMER = "anummer";
    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String CONVERSIE_RESULTAAT = "conversie_resultaat";

    private static final String UPDATE_INITVULLINGRESULT_SET_CONVERSIE_RESULTAAT_SQL =
            "UPDATE initvul.initvullingresult " + "SET conversie_resultaat = :conversie_resultaat " + "WHERE anummer= :anummer";
    private static final int TYPE_LG01 = 1111;
    private static final String BERICHTTYPE = "berichttype";
    private static final String BERICHT_INHOUD = "bericht_inhoud";
    private static final String DATUMTIJD = "datumtijd";
    private static final String GEMEENTE_INSCHRIJVING = "gemeente_inschrijving";

    private String gemeenteString;
    private String startDatumString;
    private String eindDatumString;
    private String anummerLimitString;

    @Override
    @Value("${gemeente.code:}")
    public void setGemeenteString(final String gemeenteString) {
        this.gemeenteString = gemeenteString;
    }

    @Override
    @Value("${datum.start:}")
    public void setStartDatumString(final String startDatumString) {
        this.startDatumString = startDatumString;
    }

    @Override
    @Value("${datum.eind:}")
    public void setEindDatumString(final String eindDatumString) {
        this.eindDatumString = eindDatumString;
    }

    @Override
    @Value("${anummer.limit:}")
    public void setAnummerLimitString(final String anummerLimitString) {
        this.anummerLimitString = anummerLimitString;
    }

    /**
     * {@inheritDoc}
     *
     * @throws ParseException
     */
    @Override
    @Transactional(value = INIT_RUNTIME_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    public boolean verwerkLo3Berichten(
        final ConversieResultaat zoekConversieResultaat,
        final SynchronisatieBerichtVerwerker verwerker,
        final int batchGrootte) throws ParseException
    {
        if (haalLo3BerichtenBatchOp(zoekConversieResultaat, verwerker, batchGrootte)) {
            LOG.debug("Berichten verwerken.");
            verwerker.verwerkBerichten();
        }
        return verwerker.aantalBerichten() >= batchGrootte;
    }

    @SuppressWarnings("checkstyle:illegalcatch")
    private boolean haalLo3BerichtenBatchOp(
        final ConversieResultaat zoekConversieResultaat,
        final SynchronisatieBerichtVerwerker verwerker,
        final int batchGrootte) throws ParseException
    {
        final String selectInitVullingSql = getStringResourceData("/sql/Verwerk_init_vulling.sql") + " LIMIT " + batchGrootte;
        LOG.debug("SQL voor ophalen initiele vulling berichten: {}", selectInitVullingSql);

        final Map<String, Object> parameters = maakFindLo3BerichtenParameters(null, zoekConversieResultaat);
        LOG.debug("Gebruik de volgende parameters:{}", parameters);

        final SqlParameterSource namedParameters = new MapSqlParameterSource(parameters);
        final SynchronisatieBerichtVerwerkerRowHandler rowHandler = new SynchronisatieBerichtVerwerkerRowHandler(verwerker);

        getJdbcTemplate().query(selectInitVullingSql, namedParameters, rowHandler);

        return verwerker.aantalBerichten() > 0;
    }

    private Map<String, Object> maakFindLo3BerichtenParameters(final Long plIdOffset, final ConversieResultaat conversieResultaat) throws ParseException {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);

        final Map<String, Object> parameters = new HashMap<>();
        final Date startDatum = StringUtils.isEmpty(startDatumString) ? null : dateFormat.parse(startDatumString);
        final Date eindDatum = StringUtils.isEmpty(eindDatumString) ? null : dateFormat.parse(eindDatumString);
        final Integer gemeenteCode = StringUtils.isEmpty(gemeenteString) ? null : Integer.parseInt(gemeenteString);
        final boolean anummerLimit = StringUtils.isEmpty(anummerLimitString);

        valideerStartDatumEnEindDatum(startDatum, eindDatum);
        LOG.debug(" startDatum={},\n eindDatum={},\n gemeenteCode={},\n anummerLimit={}", startDatum, eindDatum, gemeenteCode, anummerLimit);

        // Geef aparte 'leeg' parameters op omdat controleren op null
        // parameters in sql niet lekker werkt.
        parameters.put(START_DATUM_LEEG, startDatum == null);
        parameters.put(EIND_DATUM_LEEG, eindDatum == null);
        parameters.put(GEMEENTE_CODE_LEEG, gemeenteCode == null);

        parameters.put(PL_ID, plIdOffset);
        parameters.put(START_DATUM, startDatum);
        parameters.put(EIND_DATUM, eindDatum);
        parameters.put(GEMEENTE_CODE, gemeenteCode);
        parameters.put(GEEN_ANUMMER_BEPERKING, anummerLimit);
        parameters.put(CONVERSIE_RESULTAAT, conversieResultaat.toString());
        return parameters;
    }

    private void valideerStartDatumEnEindDatum(final Date startDatum, final Date eindDatum) {
        if (startDatum != null && eindDatum != null && startDatum.compareTo(eindDatum) >= 0) {
            throw new IllegalArgumentException("De datum.start moet kleiner zijn dan de datum.eind.");
        }
    }

    @Override
    @Transactional(value = INIT_RUNTIME_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public void laadInitVullingTable() {
        final String sqlInitVulling = getStringResourceData("/sql/laadInitvullingLog.sql");
        getJdbcTemplate().getJdbcOperations().execute(sqlInitVulling);
    }

    @Override
    @Transactional(value = INIT_RUNTIME_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public void createInitVullingTable() {
        final String sql = getStringResourceData("/sql/createInitVullingTables.sql");
        getJdbcTemplate().getJdbcOperations().execute(sql);
    }

    @Override
    @Transactional(value = INIT_RUNTIME_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public void saveLg01(final String lg01, final Long aNummer, final Integer gemeenteVanInschrijving, final ConversieResultaat conversieResultaat) {
        final String sql =
                "INSERT INTO initvul.initvullingresult(gbav_pl_id, anummer, berichttype, bericht_inhoud, "
                           + "datumtijd_opname_in_gbav, gemeente_van_inschrijving, conversie_resultaat) "
                           + "VALUES ((SELECT (COALESCE(MAX(gbav_pl_id),0) +1) FROM initvul.initvullingresult), "
                           + ":anummer, :berichttype, :bericht_inhoud, :datumtijd, :gemeente_inschrijving, "
                           + ":conversie_resultaat);";
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(ANUMMER, aNummer);
        parameters.put(BERICHTTYPE, TYPE_LG01);
        parameters.put(BERICHT_INHOUD, lg01);
        parameters.put(DATUMTIJD, new Date());
        parameters.put(GEMEENTE_INSCHRIJVING, gemeenteVanInschrijving);
        parameters.put(CONVERSIE_RESULTAAT, conversieResultaat.toString());

        getJdbcTemplate().update(sql, new MapSqlParameterSource(parameters));
    }

    @Override
    @Transactional(value = INIT_RUNTIME_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public void updateLo3BerichtStatus(final List<Long> aNummers, final ConversieResultaat conversieResultaat) {
        final String conversieResultaatString = conversieResultaat.toString();
        final String sql = UPDATE_INITVULLINGRESULT_SET_CONVERSIE_RESULTAAT_SQL;

        @SuppressWarnings("unchecked")
        final Map<String, ?>[] batchParameters = new Map[aNummers.size()];
        int batchIndex = 0;
        for (final Long aNummer : aNummers) {
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put(ANUMMER, aNummer);
            parameters.put(CONVERSIE_RESULTAAT, conversieResultaatString);
            batchParameters[batchIndex] = parameters;
            batchIndex += 1;
        }

        getJdbcTemplate().batchUpdate(sql, batchParameters);
    }

    /**
     * Row handler om berichten te verwerken.
     */
    private static final class SynchronisatieBerichtVerwerkerRowHandler implements RowCallbackHandler {
        private final SynchronisatieBerichtVerwerker verwerker;

        /**
         * Constructor.
         *
         * @param verwerker
         *            Lo3BerichtVerwerker
         */
        protected SynchronisatieBerichtVerwerkerRowHandler(final SynchronisatieBerichtVerwerker verwerker) {
            this.verwerker = verwerker;
        }

        @Override
        @SuppressWarnings("checkstyle:illegalcatch")
        public void processRow(final ResultSet rs) throws SQLException {
            final String berichtInhoud = rs.getString(BERICHT_INHOUD);
            final long aNummer = rs.getLong(ANUMMER);
            LOG.debug("Voeg resultaat toe: {}", aNummer);
            verwerker.voegBerichtToe(new SyncNaarBrpBericht(aNummer, berichtInhoud));
        }
    }
}
