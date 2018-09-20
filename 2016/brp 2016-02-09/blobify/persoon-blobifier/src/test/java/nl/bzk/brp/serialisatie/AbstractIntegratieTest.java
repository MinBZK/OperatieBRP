/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.IOperationListener;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Abstracte superclass voor repository (persistence) testcases.
 */
@TransactionConfiguration(transactionManager = "lezenSchrijvenTransactionManager", defaultRollback = true)
public abstract class AbstractIntegratieTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String PUNT = ".";

    protected DataSource dataSrc;
    private IDatabaseTester databaseTester;
    private final IOperationListener operationListener = new MyOperationListener();

    @Override
    @Inject
    @Named("alpDataSource")
    public void setDataSource(final DataSource dataSource) {
        super.setDataSource(dataSource);

        this.dataSrc = dataSource;
    }

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
        final FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        IDataSet dataset;
        try {
            final Class<? extends AbstractIntegratieTest> clazz = getClass();
            final IDataSet[] dataSets = new IDataSet[getDataBestanden().size()];
            final Map<Integer, String> dataBestanden = getDataBestanden();
            for (Integer volgnummer : dataBestanden.keySet()) {
                dataSets[volgnummer] = builder.build(clazz.getResourceAsStream(dataBestanden.get(volgnummer)));
            }
            dataset = new CompositeDataSet(dataSets);
        } catch (DataSetException ex) {
            throw new RuntimeException(ex);
        }
        final ReplacementDataSet filteredDataSet = new ReplacementDataSet(dataset);
        filteredDataSet.addReplacementObject("[NULL]", null);

        databaseTester.setDataSet(filteredDataSet);
        databaseTester.onSetup();

        // Even uit gezet om build te fiksen.
//        updateSequences(databaseTester);
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

    /**
     * Retourneert de data bestanden die (middels DBUnit) voor elke test in deze class geladen moeten worden in de
     * database.
     *
     * @return een lijst van data bestanden die ingeladen moeten worden.
     */
    private Map<Integer, String> getDataBestanden() {
        final Map<Integer, String> dataBestanden = getInitieleDataBestanden();
        final Map<Integer, String> additioneleDataBestanden = getAdditioneleDataBestanden();
        if (additioneleDataBestanden != null && !additioneleDataBestanden.isEmpty()) {
            for (Integer volgnummer : additioneleDataBestanden.keySet()) {
                if (dataBestanden.get(volgnummer) != null) {
                    throw new IllegalStateException(
                            "Het volgnummer van een additionele databestand kan niet gelijk zijn"
                                    + "aan dat van een initieel databestand.");
                } else {
                    dataBestanden.put(volgnummer, additioneleDataBestanden.get(volgnummer));
                }
            }
        }
        return dataBestanden;
    }

    protected Map<Integer, String> getInitieleDataBestanden() {
        final Map<Integer, String> dataBestanden = new HashMap<Integer, String>();
        dataBestanden.put(0, "/data/dataset.xml");
        dataBestanden.put(1, "/data/aut-lev.xml");
        return dataBestanden;
    }

    /**
     * Lijst van eventueel nog additionele data bestanden die ingelezen moeten worden.
     *
     * @return een lijst van additionele data bestanden.
     * @see AbstractIntegratieTest#getDataBestanden()
     */
    protected Map<Integer, String> getAdditioneleDataBestanden() {
        return Collections.emptyMap();
    }

    @After
    public void tearDown() throws Exception {
        databaseTester.onTearDown();
    }

    /**
     * Private, lokale {@link org.dbunit.IOperationListener} implementatie die database connectie voor de unit tests configureerd.
     * Tevens kunnen hier overige features en properties voor dbunit gezet worden.
     */
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
