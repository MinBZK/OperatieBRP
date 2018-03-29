/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.repository.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nl.bzk.migratiebrp.bericht.model.sync.impl.Protocollering;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ProtocolleringBericht;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.repository.ProtocolleringRepository;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.BerichtVerwerker;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * De JDBC implementatie van de ProtocolleringRepository.
 */
@Repository
public final class JdbcProtocolleringRepository extends BasisJdbcRepository implements ProtocolleringRepository {

    private static final String ACTIVITEIT_ID_FIELD = "activiteit_id";
    private static final String PERSOON_ID_FIELD = "pers_id";
    private static final String BIJHOUDING_OPSCHORT_REDEN_FIELD = "bijhouding_opschort_reden";
    private static final String TOEGANGLEVERINGSAUTORISATIE_ID_FIELD = "toeganglevsautorisatie_id";
    private static final String TOEGANGLEVERINGSAUTORISATIE_COUNT_FIELD = "toeganglevsautorisatie_count";
    private static final String DIENST_ID_FIELD = "dienst_id";
    private static final String START_TIJDSTIP_FIELD = "start_dt";
    private static final String LAATSTE_ACTIE_TIJDSTIP_FIELD = "laatste_actie_dt";
    private static final String LAAD_PROTOCOLLERING_SQL = "/sql/laadProtocollering.sql";
    private static final String LAAD_PROTOCOLLERING_NAVULLING = "/* NAVULLING-BEPERKING-PLACEHOLDER */";

    private static final int PARAM_INDEX_1 = 1;
    private static final int PARAM_INDEX_2 = 2;

    @Override
    @Transactional(value = INIT_RUNTIME_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, timeout = Integer.MAX_VALUE)
    public void laadInitVullingTable() {
        final String sql = String.format(getStringResourceData(LAAD_PROTOCOLLERING_SQL), "");

        executeSql(sql);
    }

    @Override
    @Transactional(value = INIT_RUNTIME_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, timeout = Integer.MAX_VALUE)
    public void laadInitVullingTable(final LocalDateTime localDateTime) {
        final String sql = String.format(getStringResourceData(LAAD_PROTOCOLLERING_SQL), "");
        final String navullingSql =
                sql.replace(LAAD_PROTOCOLLERING_NAVULLING, String.format(" AND activiteit.laatste_actie_dt > '%s'", Timestamp.valueOf(localDateTime)));
        executeSql(navullingSql);
    }

    @Override
    @Transactional(value = INIT_RUNTIME_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    public boolean verwerk(final ConversieResultaat zoekConversieResultaat, final BerichtVerwerker<ProtocolleringBericht> verwerker, final int batchGrootte,
                           final int protocolleringSize) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("conversie_resultaat", zoekConversieResultaat.toString());
        parameters.put("limit", protocolleringSize);

        List<Long> opgehaaldeActiviteiten = new ArrayList<>();
        for (int x = 0; x < batchGrootte; x++) {
            final SqlRowSet rs = getJdbcTemplate().queryForRowSet(getStringResourceData("/sql/Verwerk_init_vulling_protocollering.sql"),
                    new MapSqlParameterSource(parameters));

            final ProtocolleringBericht bericht = new ProtocolleringBericht();

            while (rs.next()) {
                bericht.addProtocollering(convert(rs));
            }

            if (!bericht.getProtocollering().isEmpty()) {
                verwerker.voegBerichtToe(bericht);
            }

            opgehaaldeActiviteiten = bericht.getProtocollering().stream().mapToLong(Protocollering::getActiviteitId).boxed().collect(Collectors.toList());

            updateStatussen(opgehaaldeActiviteiten, ConversieResultaat.IN_VERZENDING);
            if (opgehaaldeActiviteiten.size() < protocolleringSize) {
                break;
            }
        }
        verwerker.verwerkBerichten();

        // Als het aantal te verwerken berichten kleiner is dan de batch grootte is alles verwerkt.
        // Dit is het aantal
        // van de laatste batch
        return protocolleringSize <= opgehaaldeActiviteiten.size();
    }

    @Override
    @Transactional(value = INIT_RUNTIME_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, timeout = Integer.MAX_VALUE)
    public void updateStatussen(final List<Long> activiteitIds, final ConversieResultaat conversieResultaat) {
        if (activiteitIds.isEmpty()) {
            return;
        }

        final String sql = getStringResourceData("/sql/update_init_vulling_protocollering.sql");

        getJdbcTemplate().getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                ps.setString(PARAM_INDEX_1, conversieResultaat.toString());
                ps.setLong(PARAM_INDEX_2, activiteitIds.get(i));
            }

            @Override
            public int getBatchSize() {
                return activiteitIds.size();
            }
        });
    }

    private Protocollering convert(final SqlRowSet rs) {
        final Protocollering protocollering = new Protocollering(rs.getLong(ACTIVITEIT_ID_FIELD));
        protocollering.setPersoonId(RowSetUtil.getLong(rs, PERSOON_ID_FIELD));
        protocollering.setNadereBijhoudingsaardCode(rs.getString(BIJHOUDING_OPSCHORT_REDEN_FIELD));
        protocollering.setToegangLeveringsautorisatieId(RowSetUtil.getInteger(rs, TOEGANGLEVERINGSAUTORISATIE_ID_FIELD));
        protocollering.setToegangLeveringsautorisatieCount(rs.getInt(TOEGANGLEVERINGSAUTORISATIE_COUNT_FIELD));
        protocollering.setDienstId(RowSetUtil.getInteger(rs, DIENST_ID_FIELD));
        protocollering.setStartTijdstip(RowSetUtil.getTimestampAsLocalDateTime(rs, START_TIJDSTIP_FIELD));
        protocollering.setLaatsteActieTijdstip(RowSetUtil.getTimestampAsLocalDateTime(rs, LAATSTE_ACTIE_TIJDSTIP_FIELD));

        return protocollering;
    }
}
