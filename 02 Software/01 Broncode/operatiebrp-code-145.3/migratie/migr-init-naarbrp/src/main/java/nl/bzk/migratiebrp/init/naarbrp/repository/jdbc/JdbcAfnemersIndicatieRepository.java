/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.repository.jdbc;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AfnemersindicatieRecordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AfnemersindicatiesType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesBericht;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.repository.AfnemersIndicatieRepository;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.BerichtVerwerker;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * De JDBC implementatie van de AutorisatieRepository.
 */
@Repository
public final class JdbcAfnemersIndicatieRepository extends BasisJdbcRepository implements AfnemersIndicatieRepository {

    private static final String PL_ID = "pl_id";
    private static final String VOLG_NR = "volg_nr";
    private static final String STAPEL_NR = "stapel_nr";
    private static final String GELDIGHEID_START_DATUM = "geldigheid_start_datum";
    private static final String A_NUMMER = "a_nr";
    private static final String AFNEMER_CODE = "afnemer_code";
    private static final String BERICHT_RESULTAAT = "bericht_resultaat";
    private static final String CONVERSIE_RESULTAAT = "conversie_resultaat";
    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * {@inheritDoc}
     *
     * Let op: transactie timeout is verhoogd naar Integer.MAX_VALUE omdat dit statement zeer lang
     * kan duren.
     */
    @Override
    @Transactional(value = INIT_RUNTIME_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, timeout = Integer.MAX_VALUE)
    public void laadInitVullingAfnIndTable() {
        final String sqlInitVulling = getStringResourceData("/sql/laadAfnemerindicatie.sql");

        getJdbcTemplate().getJdbcOperations().execute(sqlInitVulling);
    }

    @Override
    @Transactional(value = INIT_RUNTIME_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public void updateAfnemersIndicatiesBerichtStatus(final List<String> aNummers, final ConversieResultaat conversieResultaat) {
        final String updateInitVullingSqlAfnInd = getStringResourceData("/sql/update_init_vulling_afnind.sql");

        @SuppressWarnings("unchecked") final Map<String, ?>[] batchParameters = new Map[aNummers.size()];
        int batchIndex = 0;
        for (final String aNummer : aNummers) {
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put(A_NUMMER, aNummer);
            parameters.put(BERICHT_RESULTAAT, conversieResultaat.toString());
            batchParameters[batchIndex] = parameters;
            batchIndex += 1;
        }

        getJdbcTemplate().batchUpdate(updateInitVullingSqlAfnInd, batchParameters);
    }

    @Override
    @Transactional(value = INIT_RUNTIME_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    public boolean verwerkAfnemerindicaties(final ConversieResultaat zoekConversieResultaat, final BerichtVerwerker<AfnemersindicatiesBericht> verwerker,
                                            final int batchGrootte) {
        if (haalAfnemerindicatiesBatchOp(zoekConversieResultaat, verwerker, batchGrootte)) {
            LOG.debug("Berichten verwerken.");
            verwerker.verwerkBerichten();
        }
        return verwerker.aantalBerichten() >= batchGrootte;
    }

    private boolean haalAfnemerindicatiesBatchOp(final ConversieResultaat zoekConversieResultaat, final BerichtVerwerker<AfnemersindicatiesBericht> verwerker,
                                                 final int batchGrootte) {

        final String selectInitVullingSqlAfnInd = getStringResourceData("/sql/Verwerk_init_vulling_afnind.sql");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(CONVERSIE_RESULTAAT, zoekConversieResultaat.toString());
        parameters.put("batch_grootte", batchGrootte);
        final SqlParameterSource namedParameters = new MapSqlParameterSource(parameters);

        final AfnemersindicatieStapelRowHandler rowHandler = new AfnemersindicatieStapelRowHandler(getJdbcTemplate(), verwerker);

        getJdbcTemplate().query(selectInitVullingSqlAfnInd, namedParameters, rowHandler);

        return verwerker.aantalBerichten() > 0;
    }

    /**
     * Row handler om berichten te verwerken.
     */
    private static final class AfnemersindicatieStapelRowHandler implements RowCallbackHandler {
        private final NamedParameterJdbcTemplate jdbcTemplate;
        private final BerichtVerwerker<AfnemersindicatiesBericht> verwerker;

        /**
         * Constructor.
         * @param verwerker Lo3BerichtVerwerker
         */
        AfnemersindicatieStapelRowHandler(final NamedParameterJdbcTemplate jdbcTemplate, final BerichtVerwerker<AfnemersindicatiesBericht> verwerker) {
            this.jdbcTemplate = jdbcTemplate;
            this.verwerker = verwerker;
        }

        @Override
        public void processRow(final ResultSet rs) throws SQLException {
            final AfnemersindicatiesBericht bericht = mapAfnemersindicatiesBericht(rs);
            verwerker.voegBerichtToe(bericht);
        }

        private AfnemersindicatiesBericht mapAfnemersindicatiesBericht(final ResultSet rs) throws SQLException {
            final AfnemersindicatiesType afnemersindicaties = new AfnemersindicatiesType();
            afnemersindicaties.setANummer(rs.getString(A_NUMMER));

            final Map<String, Object> parameters = new HashMap<>();
            parameters.put(PL_ID, rs.getInt(PL_ID));
            final SqlParameterSource namedParameters = new MapSqlParameterSource(parameters);

            final List<AfnemersindicatieRecordType> indicaties = jdbcTemplate.query("select * from initvul.initvullingresult_afnind_regel where pl_id = :pl_id",
                    namedParameters, new AfnemersindicatieRegelMapper());

            if (indicaties.contains(null)) {
                LOG.error("Er zijn onverwerkte regels voor Anummer {}, controleer de voorgaande logregels voor de betreffende afnemers!",
                        afnemersindicaties.getANummer());
            }

            afnemersindicaties.getAfnemersindicatie().addAll(indicaties);

            final AfnemersindicatiesBericht result = new AfnemersindicatiesBericht(afnemersindicaties);
            result.setMessageId(rs.getString(PL_ID));

            return result;
        }
    }

    /**
     * Mapper voor afnemersindicaties.
     */
    private static final class AfnemersindicatieRegelMapper implements RowMapper<AfnemersindicatieRecordType> {
        @Override
        public AfnemersindicatieRecordType mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final AfnemersindicatieRecordType record = new AfnemersindicatieRecordType();
            record.setAfnemerCode(rs.getString(AFNEMER_CODE));
            record.setGeldigheidStartDatum(BigInteger.valueOf(rs.getInt(GELDIGHEID_START_DATUM)));
            record.setStapelNummer(rs.getInt(STAPEL_NR));
            record.setVolgNummer(rs.getInt(VOLG_NR));
            return record;
        }
    }
}
