/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository.jpa;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.IOperationListener;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;


/**
 * Abstracte superclass voor repository (persistence) testcases.
 */
@ContextConfiguration
public abstract class AbstractRepositoryTestCase extends AbstractTransactionalJUnit4SpringContextTests {

    private IDatabaseTester          databaseTester;
    private final IOperationListener operationListener = new MyOperationListener();

    @Inject
    private DataSource               dataSrc;

    /**
     * Deze Before method wordt gebruikt om de database te initialiseren met dbunit.
     *
     * @throws Exception als de database connectie niet geopend kan worden.
     */
    @Before
    public void setUp() throws Exception {
        databaseTester = new DataSourceDatabaseTester(dataSrc);
        databaseTester.setOperationListener(operationListener);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        IDataSet dataset = null;
        try {
            Class<? extends AbstractRepositoryTestCase> clazz = getClass();
            dataset =
                new CompositeDataSet(new IDataSet[] {
                    builder.build(clazz.getResourceAsStream("/data/stamgegevensStatisch.xml")),
                    builder.build(clazz.getResourceAsStream("/data/stamgegevensLand.xml")),
                    builder.build(clazz.getResourceAsStream("/data/testdata.xml")),
                    builder.build(clazz.getResourceAsStream("/data/abonnement.xml")) });
        } catch (DataSetException ex) {
            throw new RuntimeException(ex);
        }
        databaseTester.setDataSet(dataset);
        databaseTester.onSetup();
    }

    @After
    public void tearDown() throws Exception {
        databaseTester.onTearDown();
    }

    /**
     * Private, lokale {@link IOperationListener} implementatie die database connectie voor de unit tests configureerd.
     * Tevens kunnen hier overige features en properties voor dbunit gezet worden.
     */
    private final class MyOperationListener implements IOperationListener {

        @Override
        public void connectionRetrieved(final IDatabaseConnection connection) {
            DatabaseConfig config = connection.getConfig();
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
            config.setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
        }

        @Override
        public void operationSetUpFinished(final IDatabaseConnection connection) {
        }

        @Override
        public void operationTearDownFinished(final IDatabaseConnection connection) {
        }
    }
}
