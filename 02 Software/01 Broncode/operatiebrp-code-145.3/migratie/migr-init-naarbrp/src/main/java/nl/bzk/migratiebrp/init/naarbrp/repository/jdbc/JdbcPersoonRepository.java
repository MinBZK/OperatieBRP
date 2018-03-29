/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.repository.PersoonRepository;
import nl.bzk.migratiebrp.init.naarbrp.service.bericht.SyncNaarBrpBericht;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.BerichtVerwerker;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * De JDBC implementatie van de PersoonRepository.
 */
@Repository
public final class JdbcPersoonRepository extends BasisJdbcRepository implements PersoonRepository {

    private static final String ANUMMER = "anummer";
    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String CONVERSIE_RESULTAAT = "conversie_resultaat";

    private static final String UPDATE_INITVULLINGRESULT_SET_CONVERSIE_RESULTAAT_SQL =
            "UPDATE initvul.initvullingresult " + "SET conversie_resultaat = :conversie_resultaat " + "WHERE anummer= :anummer";
    private static final String TYPE_LG01 = "Lg01";
    private static final String BERICHTTYPE = "berichttype";
    private static final String BERICHT_INHOUD = "bericht_inhoud";
    private static final String DATUMTIJD = "datumtijd";
    private static final String GEMEENTE_INSCHRIJVING = "gemeente_inschrijving";

    @Override
    @Transactional(value = INIT_RUNTIME_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    public boolean verwerkLo3Berichten(final ConversieResultaat zoekConversieResultaat, final BerichtVerwerker<SyncNaarBrpBericht> verwerker,
                                       final int batchGrootte) {
        if (haalLo3BerichtenBatchOp(zoekConversieResultaat, verwerker, batchGrootte)) {
            LOG.debug("Berichten verwerken.");
            final Instant t1 = Instant.now();
            verwerker.verwerkBerichten();
            LOG.info("Klaar met bericht verwerken. totale tijd (sec):" + Duration.between(t1, Instant.now()).getSeconds());
        }
        return verwerker.aantalBerichten() >= batchGrootte;
    }

    private boolean haalLo3BerichtenBatchOp(final ConversieResultaat zoekConversieResultaat, final BerichtVerwerker<SyncNaarBrpBericht> verwerker,
                                            final int batchGrootte) {
        final Instant t1 = Instant.now();
        final String selectInitVullingSql = getStringResourceData("/sql/Verwerk_init_vulling.sql") + " LIMIT " + batchGrootte;
        LOG.debug("SQL voor ophalen initiele vulling berichten: {}", selectInitVullingSql);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(CONVERSIE_RESULTAAT, zoekConversieResultaat.toString());
        LOG.debug("Gebruik de volgende parameters:{}", parameters);

        final SqlParameterSource namedParameters = new MapSqlParameterSource(parameters);
        final SynchronisatieBerichtVerwerkerRowHandler rowHandler = new SynchronisatieBerichtVerwerkerRowHandler(verwerker);

        getJdbcTemplate().query(selectInitVullingSql, namedParameters, rowHandler);
        LOG.info("Ophalen berichten duurde " + Duration.between(t1, Instant.now()).getSeconds());
        return verwerker.aantalBerichten() > 0;
    }

    /**
     * {@inheritDoc}
     *
     * Let op: transactie timeout is verhoogd naar Integer.MAX_VALUE omdat dit statement zeer lang
     * kan duren.
     */
    @Override
    @Transactional(value = INIT_RUNTIME_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, timeout = Integer.MAX_VALUE)
    public void laadInitVullingTable() {
        final String sqlInitVulling = getStringResourceData("/sql/laadPersoon.sql");
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
    public void saveLg01(final String lg01, final String aNummer, final String gemeenteVanInschrijving, final ConversieResultaat conversieResultaat) {
        final String sql = "INSERT INTO initvul.initvullingresult(gbav_pl_id, anummer, berichttype, bericht_inhoud, "
                + "datumtijd_opname_in_gbav, gemeente_van_inschrijving, conversie_resultaat) "
                + "VALUES ((SELECT (COALESCE(MAX(gbav_pl_id),0) +1) FROM initvul.initvullingresult), "
                + ":anummer, :berichttype, :bericht_inhoud, :datumtijd, :gemeente_inschrijving, " + ":conversie_resultaat);";
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
    public void updateLo3BerichtStatus(final List<String> aNummers, final ConversieResultaat conversieResultaat) {
        final String conversieResultaatString = conversieResultaat.toString();

        @SuppressWarnings("unchecked")
        final Map<String, ?>[] batchParameters = new Map[aNummers.size()];
        int batchIndex = 0;
        for (final String aNummer : aNummers) {
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put(ANUMMER, aNummer);
            parameters.put(CONVERSIE_RESULTAAT, conversieResultaatString);
            batchParameters[batchIndex] = parameters;
            batchIndex += 1;
        }

        getJdbcTemplate().batchUpdate(UPDATE_INITVULLINGRESULT_SET_CONVERSIE_RESULTAAT_SQL, batchParameters);
    }

    /**
     * Row handler om berichten te verwerken.
     */
    private static final class SynchronisatieBerichtVerwerkerRowHandler implements RowCallbackHandler {
        private final BerichtVerwerker<SyncNaarBrpBericht> verwerker;

        /**
         * Constructor.
         * @param verwerker Lo3BerichtVerwerker
         */
        protected SynchronisatieBerichtVerwerkerRowHandler(final BerichtVerwerker<SyncNaarBrpBericht> verwerker) {
            this.verwerker = verwerker;
        }

        @Override
        public void processRow(final ResultSet rs) throws SQLException {
            final String berichttype = rs.getString(BERICHTTYPE);
            final String berichtInhoud = rs.getString(BERICHT_INHOUD);
            final String aNummer = rs.getString(ANUMMER);
            LOG.debug("Voeg resultaat toe: {}", aNummer);
            verwerker.voegBerichtToe(new SyncNaarBrpBericht(aNummer, berichtInhoud, berichttype));
        }
    }
}
