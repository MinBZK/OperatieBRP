/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 */
public class DataSourceRouter extends AbstractRoutingDataSource{
    private static Logger LOGGER = LoggerFactory.getLogger(DataSourceRouter.class);

    @Override
    public Connection getConnection() throws SQLException {
        DataSource ds = super.determineTargetDataSource();
        LOGGER.info("Huidige datasource is: " + ds.toString());
        return ds.getConnection();
    }

    /**
     * Determine the current lookup key. This will typically be
     * implemented to check a thread-bound transaction context.
     * <p>Allows for arbitrary keys. The returned key needs
     * to match the stored lookup key type, as resolved by the
     * {@link #resolveSpecifiedLookupKey} method.
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getCurrentDataSourceType();
    }
}
