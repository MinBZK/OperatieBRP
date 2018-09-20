/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

/** Abstracte superclass voor repository (persistence) testcases. */
@ContextConfiguration(locations = { "/integratieTestDatabase-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public abstract class AbstractIntegratieTestMetDatabase extends AbstractTransactionalJUnit4SpringContextTests {

    private IDatabaseTester databaseTester;
    private final IOperationListener operationListener = new MyOperationListener();

    @Inject
    @Named("protocolleringDataSource")
    private DataSource dataSrc;

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
        final IDataSet dataset;
        try {
            final Class<? extends AbstractIntegratieTestMetDatabase> clazz = getClass();
            final IDataSet[] dataSets = new IDataSet[getDataBestanden().size()];
            int index = 0;
            for (final String dataBestand : getDataBestanden()) {
                dataSets[index++] = builder.build(clazz.getResourceAsStream(dataBestand));
            }
            dataset = new CompositeDataSet(dataSets);
        } catch (final DataSetException ex) {
            throw new IllegalArgumentException("DBUnit test data is niet correct", ex);
        }
        final ReplacementDataSet filteredDataSet = new ReplacementDataSet(dataset);
        filteredDataSet.addReplacementObject("[NULL]", null);

        databaseTester.setDataSet(filteredDataSet);
        databaseTester.onSetup();
    }

    /**
     * Retourneert de data bestanden die (middels DBUnit) voor elke test in deze class geladen moeten worden in de
     * database.
     *
     * @return een lijst van data bestanden die ingeladen moeten worden.
     */
    private List<String> getDataBestanden() {
        final List<String> dataBestanden = getInitieleDataBestanden();
        final List<String> additioneleDataBestanden = getAdditioneleDataBestanden();
        if (additioneleDataBestanden != null && !additioneleDataBestanden.isEmpty()) {
            dataBestanden.addAll(additioneleDataBestanden);
        }
        return dataBestanden;
    }

    /**
     * Geeft de intitiele data bestanden.
     *
     * @return lijst met initiele data bestanden
     */
    protected List<String> getInitieleDataBestanden() {
        final List<String> dataBestanden = new ArrayList<>();
        dataBestanden.addAll(Arrays.asList(
                "/data/dataset.xml"));
        return dataBestanden;
    }

    /**
     * Lijst van eventueel nog additionele data bestanden die ingelezen moeten worden.
     *
     * @return een lijst van additionele data bestanden.
     * @see AbstractIntegratieTestMetDatabase#getDataBestanden()
     */
    protected List<String> getAdditioneleDataBestanden() {
        return Collections.emptyList();
    }

    /**
     * Methode die bij afsluiten wordt uitgevoerd.
     *
     * @throws Exception exceptie
     */
    @After
    public void tearDown() throws Exception {
        databaseTester.onTearDown();
    }

    /**
     * Private, lokale {@link org.dbunit.IOperationListener} implementatie die database connectie voor de unit tests
     * configureerd.
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
