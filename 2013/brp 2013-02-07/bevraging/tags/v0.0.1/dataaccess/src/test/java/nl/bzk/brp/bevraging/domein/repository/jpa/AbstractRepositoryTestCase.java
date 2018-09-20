/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository.jpa;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.IOperationListener;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Abstracte superclass voor repository (persistence) testcases.
 */
@ContextConfiguration
public abstract class AbstractRepositoryTestCase extends AbstractTransactionalJUnit4SpringContextTests {

    private IDatabaseTester databaseTester;
    /**
     * Ingewikkelde manier om features en properties voor dbunit te zetten.
     */
    private IOperationListener operationListener = new IOperationListener() {

        @Override
        public void connectionRetrieved(IDatabaseConnection connection) {
            DatabaseConfig config = connection.getConfig();
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
            config.setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
        }

        @Override
        public void operationSetUpFinished(IDatabaseConnection connection) {
        }

        @Override
        public void operationTearDownFinished(IDatabaseConnection connection) {
        }
    };

    /**
     * {@inheritDoc}
     * 
     * We gebruiken de setter voor de datasource, die door Spring wordt aangeroepen,
     * om meteen even de database tester te creëren en initialiseren.
     */
    @Inject
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
        databaseTester = new DataSourceDatabaseTester(dataSource);
        databaseTester.setOperationListener(operationListener);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        IDataSet dataSet = null;
        try {
            dataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream("/data/basic.xml"));
        } catch (DataSetException ex) {
            throw new RuntimeException(ex);
        }
        databaseTester.setDataSet(dataSet);
    }

    @Before
    public void setUp() throws Exception {
        databaseTester.onSetup();
    }
}
