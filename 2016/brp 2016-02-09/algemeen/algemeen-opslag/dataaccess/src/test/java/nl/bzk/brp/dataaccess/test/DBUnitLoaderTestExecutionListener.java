/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.test;

import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.DatabaseUnitException;
import org.dbunit.IDatabaseTester;
import org.dbunit.IOperationListener;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * TestExecutionListener die voor het uitvoeren van testmethodes eerst DBUnit data in een
 * database inlaadt. Dit gebeurt *BUITEN* een transactie, met een
 * {@link org.dbunit.operation.DatabaseOperation#CLEAN_INSERT}, dus de data blijft in de database.
 * De gebruikte {@link javax.sql.DataSource} wordt van de test instantie gehaald.
 */
@Component
public class DBUnitLoaderTestExecutionListener extends AbstractTestExecutionListener {
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String PUNT = ".";

    //GEEN EXTRA FIELDS PLAATSEN, MEM LEAK GEVOELIG

    private Class<?> currentClass;
    private IDatabaseTester databaseTester;

    @Override
    public void afterTestClass(final TestContext testContext) throws Exception {
        LOGGER.debug("teardown DBUnit databaseTester");
        if (databaseTester != null) {
            databaseTester.onTearDown();
        }
    }

    @Override
    public void prepareTestInstance(final TestContext testContext) {
        if (!testContext.getTestClass().equals(currentClass)) {
            final Object testInstance = testContext.getTestInstance();
            DataSource dataSource = null;
            if (testInstance instanceof DataSourceProvider) {
                dataSource = ((DataSourceProvider) testInstance).getDataSource();
            }
            final ResourceLoader resourceLoader = testContext.getApplicationContext();

            LOGGER.debug("setUp DBUnit databaseTester");
            try {
                final Data data = testContext.getTestClass().getAnnotation(Data.class);
                if (data != null && dataSource != null) {
                    databaseTester = new DataSourceDatabaseTester(dataSource);
                    databaseTester.setOperationListener(new MyOperationListener());
                    databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);

                    final FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
                    builder.setColumnSensing(true);

                    final String[] dataSetFiles = data.resources();
                    final List<IDataSet> dataSets = new ArrayList<>(dataSetFiles.length);
                    for (final String dataSetFile : dataSetFiles) {
                        final IDataSet ds;
                        if (dataSetFile.endsWith(".xml")) {
                            ds = builder.build(resourceLoader.getResource(dataSetFile).getInputStream());
                        } else if (dataSetFile.endsWith(".csv")) {
                            ds = new CsvDataSet(resourceLoader.getResource(dataSetFile).getFile());
                        } else {
                            throw new IllegalStateException(
                                "DbUnitRule only supports JSON, CSV or Flat XML data sets for the moment");
                        }
                        dataSets.add(ds);
                    }
                    final CompositeDataSet dataSet = new CompositeDataSet(dataSets.toArray(new IDataSet[dataSets.size()]));

                    final ReplacementDataSet filteredDataSet = new ReplacementDataSet(dataSet);
                    filteredDataSet.addReplacementObject("[NULL]", null);

                    databaseTester.setDataSet(filteredDataSet);
                    databaseTester.onSetup();

                    updateSequences(databaseTester);
                }
            }
            catch (final BatchUpdateException e) {
                LOGGER.error("Fout opgetreden bij initialisatie van DBUnit.", e.getNextException());
            }
            catch (final DatabaseUnitException e) {
                LOGGER.error("Fout opgetreden bij initialisatie van DBUnit.", e);
                if (e.getCause() instanceof BatchUpdateException) {
                    LOGGER.error("Fout opgetreden bij initialisatie van DBUnit.", ((BatchUpdateException) e.getCause()).getNextException());
                }
            }
            catch (final Exception e) {
                LOGGER.error("Fout opgetreden bij initialisatie van DBUnit.", e);
            } finally {
                currentClass = testContext.getTestClass();
            }
        }
    }

    /**
     * Update alle sequences naar de juiste waarde.
     *
     * @param iDatabaseTester de database tester
     * @throws DataSetException de data set exception
     */
    private void updateSequences(final IDatabaseTester iDatabaseTester) throws DataSetException {
        try {
            final String driverName = iDatabaseTester.getConnection().getConnection().getMetaData().getDriverName();
            final Statement statement = iDatabaseTester.getConnection().getConnection().createStatement();
            final String[] tables = iDatabaseTester.getDataSet().getTableNames();

            for (final String table : tables) {
                try {
                    final String schemaName = table.substring(0, table.indexOf(PUNT)).toLowerCase();
                    final String tableName = table.substring(table.indexOf(PUNT) + 1).toLowerCase();
                    final String schemaTable = schemaName + PUNT + tableName;
                    final String sequence = schemaName + ".seq_" + tableName;

                    if (driverName.contains("HSQL")) {
                        statement.execute("ALTER SEQUENCE " + sequence + " RESTART WITH 5000000;");
                    } else {
                        statement.execute("SELECT SETVAL('" + sequence + "', (select 1+coalesce(max(id),0) FROM " + schemaTable + "), true);");
                    }
                } catch (final SQLException ex) {
                    if (ex.getLocalizedMessage().contains("ERROR: relation") && ex.getLocalizedMessage().contains("does not exist")) {
                        // Sommige sequences bestaan niet in POSTGRES, logging hiervan is overbodig
//                        LOGGER.debug("Uitvoeren van update van de sequence is mislukt voor tabel {}", table, ex);
                    } else if (ex.getLocalizedMessage().contains("user lacks privilege")) {
                        // Sommige sequences bestaan niet in HSQLDB, logging hiervan is overbodig
//                        LOGGER.debug("Uitvoeren van update van de sequence is mislukt voor tabel {}", table, ex);
                    } else {
                        LOGGER.error("SQL fout opgetreden", ex);
                    }
                }
            }
        } catch (final Exception e) {
            LOGGER.error("Update van de sequences is mislukt", e);
        }
    }

    private static final class MyOperationListener implements IOperationListener {

        @Override
        public void connectionRetrieved(final IDatabaseConnection connection) {
            final DatabaseConfig config = connection.getConfig();
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
            config.setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
            config.setProperty(DatabaseConfig.PROPERTY_BATCH_SIZE, 300);
            config.setProperty(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, true);
        }

        @Override
        public void operationSetUpFinished(final IDatabaseConnection connection) {
        }

        @Override
        public void operationTearDownFinished(final IDatabaseConnection connection) {
        }

    }

}
