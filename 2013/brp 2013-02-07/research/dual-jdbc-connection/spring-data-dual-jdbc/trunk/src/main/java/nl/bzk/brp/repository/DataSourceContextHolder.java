/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 *
 */
public class DataSourceContextHolder {
    private static Logger LOGGER = LoggerFactory.getLogger(DataSourceContextHolder.class);

    private static final ThreadLocal<nl.bzk.brp.repository.AvailableDataSourceTypes> contextHolder =
            new ThreadLocal<AvailableDataSourceTypes>();

    public static void setDataSourceType(AvailableDataSourceTypes dataSourceType) {
        Assert.notNull(dataSourceType, "dataSourceType cannot be null");
        LOGGER.info("Zet datasource naar : " + dataSourceType);
        contextHolder.set(dataSourceType);
    }

    public static AvailableDataSourceTypes getCurrentDataSourceType() {
        AvailableDataSourceTypes currentDataSource = contextHolder.get();
        if(null == currentDataSource) {
            setDataSourceType(AvailableDataSourceTypes.DEFAULT);
        }
        LOGGER.info("Huidige datasource is in context is: " + contextHolder.get());
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }

}
