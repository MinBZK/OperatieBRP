/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.IOperationListener;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * TestExecutionListener die voor het uitvoeren van testmethodes eerst DBUnit data in een database inlaadt. Dit gebeurt
 * *BUITEN* een transactie, met een {@link org.dbunit.operation.DatabaseOperation.CLEAN_INSERT}, dus de data blijft in
 * de database. De gebruikte {@link javax.sql.DataSource} wordt van de test instantie gehaald.
 */
@Component
public class DBUnitLoaderTestExecutionListener extends AbstractTestExecutionListener {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private DataSource dataSource;
    private ResourceLoader resourceLoader;
    private Class<?> currentClass;

    private IDatabaseTester databaseTester;
    private final IOperationListener operationListener = new MyOperationListener();

    @Override
    public void afterTestClass(final TestContext testContext) throws Exception {
        LOGGER.debug("teardown DBUnit databaseTester");
        if (databaseTester != null) {
            databaseTester.onTearDown();
        }
    }

    @Override
    public void prepareTestInstance(final TestContext testContext) throws Exception {
        if (!testContext.getTestClass().equals(currentClass)) {
            final Object testInstance = testContext.getTestInstance();

            if (testInstance instanceof DataSourceProvider) {
                dataSource = ((DataSourceProvider) testInstance).getDataSource();
            } else {
                throw new IllegalArgumentException(
                    "Test klasse moet de interface DataSourceProvider implementeren om gebruik te kunnen maken van de DBUnitLoaderTestExecutionListener.");
            }
            resourceLoader = testContext.getApplicationContext();

            LOGGER.debug("setUp DBUnit databaseTester");
            try {
                final Data data = testContext.getTestClass().getAnnotation(Data.class);
                if (data != null) {
                    databaseTester = new DataSourceDatabaseTester(dataSource);
                    databaseTester.setOperationListener(operationListener);
                    databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);

                    final FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
                    builder.setColumnSensing(true);

                    final String[] dataSetFiles = data.resources();
                    final List<IDataSet> dataSets = new ArrayList<IDataSet>(dataSetFiles.length);
                    for (String dataSetFile : dataSetFiles) {
                        IDataSet ds;
                        if (dataSetFile.endsWith(".xml")) {
                            ds = builder.build(resourceLoader.getResource(dataSetFile).getInputStream());
                        } else if (dataSetFile.endsWith(".csv")) {
                            ds = new CsvDataSet(resourceLoader.getResource(dataSetFile).getFile());
                        } else {
                            throw new IllegalStateException("DbUnitRule only supports JSON, CSV or Flat XML data sets for the moment");
                        }
                        dataSets.add(ds);
                    }
                    final CompositeDataSet dataSet = new CompositeDataSet(dataSets.toArray(new IDataSet[dataSets.size()]));

                    final ReplacementDataSet filteredDataSet = new ReplacementDataSet(dataSet);
                    filteredDataSet.addReplacementObject("[NULL]", null);

                    databaseTester.setDataSet(filteredDataSet);
                    databaseTester.onSetup();
                }
            } finally {
                currentClass = testContext.getTestClass();
            }
        }
    }

    private final class MyOperationListener implements IOperationListener {

        @Override
        public void connectionRetrieved(final IDatabaseConnection connection) {
            final DatabaseConfig config = connection.getConfig();
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
