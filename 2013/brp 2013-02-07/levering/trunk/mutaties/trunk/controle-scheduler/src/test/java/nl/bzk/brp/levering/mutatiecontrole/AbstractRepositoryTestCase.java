/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatiecontrole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import nl.bzk.brp.levering.mutatiecontrole.integratie.AbstractIntegratieTest;
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

/** Abstracte superclass voor repository (persistence) testcases. */
public abstract class AbstractRepositoryTestCase extends AbstractIntegratieTest {

    private static final String DB_STAMGEGEVENS = "/data/stamgegevensStatisch.xml";
    private static final String DB_STATISCH = "/data/statisch.xml";
    private static final String DB_DATASET = "/data/dataset.xml";

    private IDatabaseTester databaseTester;
    private final IOperationListener operationListener = new MyOperationListener();

    @Inject
    @Named("brpDataSource")
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
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        IDataSet dataset;
        try {
            Class<? extends AbstractRepositoryTestCase> clazz = getClass();
            IDataSet[] dataSets = new IDataSet[getDataBestanden().size()];
            int index = 0;
            for (String dataBestand : getDataBestanden()) {
                dataSets[index++] = builder.build(clazz.getResourceAsStream(dataBestand));
            }
            dataset = new CompositeDataSet(dataSets);
        } catch (DataSetException ex) {
            throw new RuntimeException(ex);
        }
        ReplacementDataSet filteredDataSet = new ReplacementDataSet(dataset);
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
        List<String> dataBestanden = getInitieleDataBestanden();
        List<String> additioneleDataBestanden = getAdditioneleDataBestanden();
        if (additioneleDataBestanden != null && !additioneleDataBestanden.isEmpty()) {
            dataBestanden.addAll(additioneleDataBestanden);
        }
        return dataBestanden;
    }

    protected List<String> getInitieleDataBestanden() {
        List<String> dataBestanden = new ArrayList<String>();
        dataBestanden.addAll(Arrays.asList(
                DB_STAMGEGEVENS,
                DB_STATISCH,
                DB_DATASET));

        return dataBestanden;
    }

    /**
     * Lijst van eventueel nog additionele data bestanden die ingelezen moeten worden.
     *
     * @return een lijst van additionele data bestanden.
     * @see AbstractRepositoryTestCase#getDataBestanden()
     */
    protected List<String> getAdditioneleDataBestanden() {
        return Collections.emptyList();
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
