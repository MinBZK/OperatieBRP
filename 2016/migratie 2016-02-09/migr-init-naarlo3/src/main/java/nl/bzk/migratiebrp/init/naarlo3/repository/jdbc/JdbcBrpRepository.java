/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarlo3.repository.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.sql.DataSource;

import nl.bzk.migratiebrp.init.naarlo3.repository.ANummerVerwerker;
import nl.bzk.migratiebrp.init.naarlo3.repository.BrpRepository;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
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
    private static final int FETCH_SIZE = 10000;

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
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @SuppressWarnings("checkstyle:illegalcatch")
    public void verwerkANummersVanIngeschrevenPersonen(final ANummerVerwerker verwerker, final int batchGrootte) {
        try (final InputStream inputStream = getClass().getResourceAsStream("/sql/select_anummers.sql")) {
            final String selectInitVullingSql = getStringData(inputStream);
            final AtomicInteger teller = new AtomicInteger(0);

            jdbcTemplate.query(selectInitVullingSql, new MapSqlParameterSource(), new RowCallbackHandler() {
                @Override
                public void processRow(final ResultSet rs) throws SQLException {
                    final long aNummer = rs.getLong("anr");

                    verwerker.addANummer(aNummer);
                    if (teller.addAndGet(1) >= batchGrootte) {
                        try {
                            verwerker.call();
                        } catch (final Exception e /* Catch Exception: Nodig om call() fouten correct af te handelen */) {
                            LOG.warn(FOUT_BIJ_VERWERKEN_VAN_A_NUMMERS, e);
                        }
                        teller.set(0);
                    }
                }
            });
            verwerker.call();
        } catch (final Exception e /* Nodig om call() fouten correct af te handelen */) {
            LOG.warn(FOUT_BIJ_VERWERKEN_VAN_A_NUMMERS, e);
        }
    }

    private String getStringData(final InputStream inputStream) {
        try {
            return IOUtils.toString(inputStream);
        } catch (final IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
