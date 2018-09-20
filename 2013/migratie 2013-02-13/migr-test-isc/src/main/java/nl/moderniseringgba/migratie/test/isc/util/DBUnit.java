/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.isc.util;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.tools.ant.filters.StringInputStream;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CachedDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.xml.sax.InputSource;

/**
 * Primair toegangspunt voor DBUnit-functies vanuit een test. Is nodig bij gebruik van DBUnit in een Spring-context.
 * 
 */
public final class DBUnit {

    /**
     * Constante voor de tabelnamen.
     */
    private static final String QUALIFIED_TABLE_NAMES_FEATURE_ID =
            "http://www.dbunit.org/features/qualifiedTableNames";

    private final DataSource datasource;

    /**
     * Convenient constructor.
     * 
     * @param datasource
     *            De datasource.
     */
    public DBUnit(final DataSource datasource) {
        this.datasource = datasource;
    }

    /**
     * Insert een dataset vanuit een bestand in de database tabel.
     * 
     * @param inhoud
     *            De inhoud waarop de dataset wordt gebaseerd.
     * @throws DatabaseUnitException
     *             Database unit exceptie.
     * @throws SQLException
     *             SQL exceptie.
     */
    public void insert(final String inhoud) throws DatabaseUnitException, SQLException {
        final IDataSet dataSet = createDataset(inhoud);
        DatabaseOperation.CLOSE_CONNECTION(DatabaseOperation.REFRESH).execute(createConnection(), dataSet);
    }

    /**
     * Geeft een door DBUnit gewrapte connectie terug naar de testdatabase.
     * 
     * @return Een DBUnit {@link IDatabaseConnection} naar de testdatabase.
     * @throws SQLException
     *             SQL exceptie.
     * @throws DatabaseUnitException
     *             Database unit exceptie.
     */
    protected IDatabaseConnection createConnection() throws DatabaseUnitException, SQLException {
        final IDatabaseConnection connection = new DatabaseConnection(datasource.getConnection());
        connection.getConfig().setProperty(QUALIFIED_TABLE_NAMES_FEATURE_ID, true);
        return connection;
    }

    /**
     * Maakt een dataset aan op basis van de inhoud van een test case bericht.
     * 
     * @param inhoud
     *            De inhoud waarop de dataset wordt aangemaakt.
     * @return De aangemaakte dataset.
     */
    private IDataSet createDataset(final String inhoud) {
        try {
            if (inhoud == null) {
                throw new IllegalArgumentException("Argument \"inhoud\" mag geen null zijn.");
            }
            final FlatXmlProducer producer = new FlatXmlProducer(new InputSource(new StringInputStream(inhoud)));
            producer.setValidating(true);

            final IDataSet ds = new CachedDataSet(producer);
            return ds;
        } catch (final DataSetException dse) {
            throw new RuntimeException("Fout gevonden in testdata: " + dse.getMessage(), dse);
        }
    }

}
