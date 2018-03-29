/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.util;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DBUnitUtil {

    private final ApplicationContext applicationContext;
    private DataSource dataSource;

    @Inject
    public DBUnitUtil(DataSource datasource, ApplicationContext applicationContext) {
        this.dataSource = datasource;
        this.applicationContext = applicationContext;
    }

    public IDatabaseConnection connection() throws SQLException, DatabaseUnitException {
        final IDatabaseConnection connection = new DatabaseConnection(dataSource.getConnection());
        final DatabaseConfig config = connection.getConfig();
        config.setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, Boolean.TRUE);
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());

        return connection;
    }

    public IDataSet createDataset(String dataSet) throws DataSetException, IOException {
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        builder.setDtdMetadata(true);
        return builder.build(applicationContext.getResource(dataSet).getURL());
    }

    public IDataSet createDataset(Path dataSet) throws DataSetException, IOException {
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        builder.setDtdMetadata(true);
        return builder.build(dataSet.toFile());
    }
}
