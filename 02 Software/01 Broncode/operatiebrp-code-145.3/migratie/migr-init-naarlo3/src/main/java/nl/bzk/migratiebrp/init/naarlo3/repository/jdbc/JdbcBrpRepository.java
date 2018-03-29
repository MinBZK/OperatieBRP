/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarlo3.repository.jdbc;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.init.naarlo3.repository.ANummerVerwerker;
import nl.bzk.migratiebrp.init.naarlo3.repository.BrpRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * De JDBC implementatie van de BrpRepository.
 */
@Repository
public final class JdbcBrpRepository implements BrpRepository {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String FOUT_BIJ_VERWERKEN_VAN_A_NUMMERS = "Fout bij verwerken van A-nummers";
    private static final int FETCH_SIZE = 10_000;

    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Set the datasource to use.
     * @param dataSource the datasource to use
     */
    @Inject
    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        ((JdbcTemplate) jdbcTemplate.getJdbcOperations()).setFetchSize(FETCH_SIZE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)

    public void verwerkANummersVanIngeschrevenPersonen(final ANummerVerwerker verwerker, final int batchGrootte) {
        try (final InputStream inputStream = getClass().getResourceAsStream("/sql/select_anummers.sql")) {
            final String selectInitVullingSql = IOUtils.toString(inputStream, Charset.defaultCharset());
            final AtomicInteger teller = new AtomicInteger(0);

            jdbcTemplate.query(selectInitVullingSql, new MapSqlParameterSource(), rs -> {
                final String aNummer = rs.getString("anr");

                verwerker.addANummer(aNummer);
                if (teller.addAndGet(1) >= batchGrootte) {
                    call(verwerker);
                    teller.set(0);
                }
            });
            verwerker.call();
        } catch (final Exception e /* Nodig om call() fouten correct af te handelen */) {
            LOG.warn(FOUT_BIJ_VERWERKEN_VAN_A_NUMMERS, e);
        }
    }

    private void call(ANummerVerwerker verwerker) {
        try {
            verwerker.call();
        } catch (final Exception e /* Catch Exception: Nodig om call() fouten correct af te handelen */) {
            LOG.warn(FOUT_BIJ_VERWERKEN_VAN_A_NUMMERS, e);
        }
    }
}
