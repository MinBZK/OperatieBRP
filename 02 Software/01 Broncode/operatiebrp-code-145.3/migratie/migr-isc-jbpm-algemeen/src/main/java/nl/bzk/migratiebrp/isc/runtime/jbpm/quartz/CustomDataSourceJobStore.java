/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.quartz;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.quartz.SchedulerConfigException;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerSignaler;
import org.quartz.utils.ConnectionProvider;
import org.quartz.utils.DBConnectionManager;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Subclass of Spring's subclass of Quartz's JobStoreCMT class that delegates to a Spring-managed DataSource instead of
 * using a Quartz-managed connection pool. This JobStore will be used if SchedulerFactoryBean's "dataSource" property is
 * set.
 *
 * <p>
 * Overrides the initialize method so the transactional datasource will open a new connection each time.
 * <p>
 * Overrides the closeConnection method because the Spring DataSourceUtils do not work correctly with the Atomikos JTA
 * manager as they do not close the connection (only release it).
 * @see LocalDataSourceJobStore
 */
public final class CustomDataSourceJobStore extends LocalDataSourceJobStore {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public void initialize(final ClassLoadHelper loadHelper, final SchedulerSignaler signaler) throws SchedulerConfigException {
        super.initialize(loadHelper, signaler);

        // Absolutely needs thread-bound DataSource to initialize.
        final DataSource dataSource = SchedulerFactoryBean.getConfigTimeDataSource();
        if (dataSource == null) {
            throw new SchedulerConfigException("No local DataSource found for configuration - "
                    + "'dataSource' property must be set on SchedulerFactoryBean");
        }

        // Override transactional ConnectionProvider for Quartz.
        DBConnectionManager.getInstance().addConnectionProvider(TX_DATA_SOURCE_PREFIX + getInstanceName(), new ConnectionProvider() {
            @Override
            public Connection getConnection() throws SQLException {
                // Return a transactional Connection, if any.
                return dataSource.getConnection();
            }

            @Override
            public void shutdown() {
                // Do nothing
            }

            @Override
            public void initialize() {
                // Do nothing
            }
        });
    }

    @Override
    protected void closeConnection(final Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (final SQLException e) {
                LOGGER.debug("Could not close JDBC Connection", e);
            }
        }
    }

}
