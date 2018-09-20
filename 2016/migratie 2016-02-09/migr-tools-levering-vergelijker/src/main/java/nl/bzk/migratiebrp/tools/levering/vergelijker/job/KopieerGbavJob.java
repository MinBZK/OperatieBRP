/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import javax.sql.DataSource;
import nl.bzk.migratiebrp.util.common.JdbcConstants;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Job voor het kopiëren van GBAV informatie naar de leveringvergelijker datasource.
 */
public final class KopieerGbavJob {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int FETCH_SIZE = 1000;

    /**
     * Main execute methode voor het uitvoeren van de job.
     *
     * @param context
     *            De context van de job.
     */
    public void execute(final ConfigurableApplicationContext context) {

        final DataSource leveringDataSource = context.getBean("leveringVergelijkerDataSource", DataSource.class);
        final NamedParameterJdbcTemplate leveringJdbcTemplate = new NamedParameterJdbcTemplate(leveringDataSource);
        ((JdbcTemplate) leveringJdbcTemplate.getJdbcOperations()).setFetchSize(FETCH_SIZE);

        final String sqlCreate = ResourceUtil.getStringResourceData(this, "/sql/leveringsvergelijking-create-gbav.sql");
        leveringJdbcTemplate.getJdbcOperations().execute(sqlCreate);

        try (Connection leveringConnection = leveringDataSource.getConnection()) {
            final PreparedStatement insertStatement =
                    leveringConnection.prepareStatement("insert into mig_leveringsvergelijking_berichtcorrelatie_gbav(id, bijhouding_ber_id, afnemer_code, "
                                                        + "levering_ber_id, berichtnummer) values (?, ?, ?, ?, ?)");

            final DataSource gbavDataSource = context.getBean("gbaDataSource", DataSource.class);
            final NamedParameterJdbcTemplate gbavJdbcTemplate = new NamedParameterJdbcTemplate(gbavDataSource);
            ((JdbcTemplate) gbavJdbcTemplate.getJdbcOperations()).setFetchSize(FETCH_SIZE);

            final String sqlSelect = ResourceUtil.getStringResourceData(this, "/sql/leveringsvergelijking-laad-gbav.sql");
            final AtomicLong counter = new AtomicLong(0L);

            gbavJdbcTemplate.query(sqlSelect, new HashMap<String, Object>(), new RowCallbackHandler() {

                @Override
                public void processRow(final ResultSet arg0) throws SQLException {
                    insertStatement.setLong(JdbcConstants.COLUMN_1, arg0.getLong(JdbcConstants.COLUMN_1));
                    insertStatement.setLong(JdbcConstants.COLUMN_2, arg0.getLong(JdbcConstants.COLUMN_2));
                    insertStatement.setString(JdbcConstants.COLUMN_3, arg0.getString(JdbcConstants.COLUMN_3));
                    insertStatement.setLong(JdbcConstants.COLUMN_4, arg0.getLong(JdbcConstants.COLUMN_4));
                    insertStatement.setString(JdbcConstants.COLUMN_5, arg0.getString(JdbcConstants.COLUMN_5));
                    insertStatement.executeUpdate();
                    counter.incrementAndGet();
                }
            });

            LOG.info(counter.get() + " berichten gekopieerd.");
        } catch (final SQLException e) {
            throw new RuntimeException("Probleem tijdens kopieren gbav berichten naar soa database.", e);
        }
    }
}
