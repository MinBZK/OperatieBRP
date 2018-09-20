/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import junit.framework.AssertionFailedError;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.dbunit.DatabaseUnitException;
import org.dbunit.assertion.DbUnitAssert;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CachedDataSet;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.ColumnFilterTable;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.IRowValueProvider;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.RowFilterTable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.filter.IColumnFilter;
import org.dbunit.dataset.filter.IRowFilter;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class DBUnitUtil {
    private static final Logger LOG = LoggerFactory.getLogger();

    private static final DbUnitAssert ASSERT = new BrpDbUnitAssert();
    private static final TableFormatter FORMATTER = new TableFormatter();

    private boolean inMemory;

    /**
     * Geeft een SQL-connectie terug naar de testdatabase.
     *
     * @return Een {@link java.sql.Connection} naar de testdatabase.
     */
    protected abstract Connection createSqlConnection() throws SQLException;

    protected abstract void resetSequences(IDatabaseConnection connection) throws SQLException, DatabaseUnitException;

    /**
     * Zet de waarde van stamgegevens sequences.
     *
     * @param connection
     *            stamgegevens sequences
     * @throws SQLException
     *             the SQL exception
     * @throws DatabaseUnitException
     *             the database unit exception
     */
    protected abstract void setStamgegevensSequences(IDatabaseConnection connection) throws SQLException, DatabaseUnitException;

    protected abstract void truncateTables(IDatabaseConnection connection) throws SQLException, DatabaseUnitException;

    protected void setInMemory() {
        inMemory = true;
    }

    /**
     * Geeft een door DBUnit gewrapte connectie terug naar de testdatabase.
     *
     * @return Een DBUnit {@link IDatabaseConnection} naar de testdatabase.
     * @throws SQLException
     * @throws DatabaseUnitException
     */
    public IDatabaseConnection createConnection() throws DatabaseUnitException, SQLException {
        final IDatabaseConnection connection = new DatabaseConnection(createSqlConnection());
        final DatabaseConfig config = connection.getConfig();
        config.setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, Boolean.TRUE);
        config.setProperty(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, Boolean.TRUE);
        config.setProperty(DatabaseConfig.PROPERTY_BATCH_SIZE, 5000);
        if (inMemory) {
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
        } else {
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
        }
        return connection;
    }

    public void insert(final IDatabaseConnection connection, final Class<?> clazz, final String... datafiles) throws DatabaseUnitException, SQLException {
        insert(connection, createDataset(datafiles, clazz));
    }

    public void insert(final IDatabaseConnection connection, final IDataSet dataSet) throws DatabaseUnitException, SQLException {
        DatabaseOperation.REFRESH.execute(connection, dataSet);
    }

    protected void delete(final Class<?> clazz, final String... datafiles) throws DatabaseUnitException, SQLException {
        createDataset(datafiles, clazz);
    }

    protected void delete(final IDataSet dataSet) throws DatabaseUnitException, SQLException {
        DatabaseOperation.DELETE.execute(createConnection(), dataSet);
    }

    /**
     * Retourneert de actuele dataset uit de testdatabase. Merk op dat dit een relatief dure methode is, het resultaat
     * moet indien mogelijk gecached worden.
     *
     * @return De database vulling als dataset.
     * @throws DatabaseUnitException
     * @throws SQLException
     */
    protected IDataSet createActualSet(final IDatabaseConnection connection) throws SQLException, DatabaseUnitException {
        return connection.createDataSet();
    }

    protected void compareExpectedWithActual(final Class<?> clazz, final String... datafiles) throws DatabaseUnitException, SQLException {
        compareExpectedWithActual(createDataset(datafiles, clazz));
    }

    /**
     * Vergelijkt een actuele dataset met de verwachte dataset. Alle records in de verwachte dataset dienen ook voor te
     * komen in de actuele dataset. Tabellen die niet in de expected set voorkomen worden genegeerd.
     * <p>
     *
     * @param expected
     *            De expected dataset.
     * @throws DatabaseUnitException
     * @throws SQLException
     */
    protected void compareExpectedWithActual(final IDataSet expected) throws SQLException, DatabaseUnitException {
        if (expected == null) {
            throw new IllegalArgumentException("Argument 'expected' is null");
        }

        final IDatabaseConnection connection = createConnection();
        try {
            final IDataSet actual = createActualSet(connection);

            final String[] expTableNames = expected.getTableNames();
            for (final String currentTableName : expTableNames) {
                // prepareer verwachtte tabel
                final ITable expTable = expected.getTable(currentTableName);
                final ITable sortedExp = new SortedTable(expTable);
                final ITable currentTimestampColumnFilteredExp = new ColumnFilterTable(sortedExp, new CurrentTimestampColumnFilter());

                // prepareer actuele tabel
                final ITable actTable = actual.getTable(currentTableName);
                final ITable sortedActTable = new SortedTable(actTable);
                final ITable primaryKeyFilteredActTable = new RowFilterTable(sortedActTable, new PrimaryKeyRowFilter(expTable));
                final ITable currentTimestampRowFilteredActTable = new RowFilterTable(primaryKeyFilteredActTable, new CurrentTimestampRowFilter(expTable));
                final ITable currentTimestampColumnFilteredActTable =
                        new ColumnFilterTable(currentTimestampRowFilteredActTable, new CurrentTimestampColumnFilter());

                try {
                    ASSERT.assertEquals(currentTimestampColumnFilteredExp, currentTimestampColumnFilteredActTable);
                } catch (final AssertionFailedError assertionError) {
                    LOG.error("****** De volgende records zijn aangetroffen in de database:");
                    LOG.error(FORMATTER.format(sortedActTable));
                    LOG.error("****** Terwijl minimaal de volgende EXPECTED records verwacht worden:");
                    LOG.error(FORMATTER.format(sortedExp));
                    throw assertionError;
                }
            }
        } finally {
            connection.close();
        }
    }

    protected void compareNotExpectedWithActual(final Class<?> clazz, final String... datafiles) throws DatabaseUnitException, SQLException {
        compareNotExpectedWithActual(createDataset(datafiles, clazz));
    }

    /**
     * Vergelijkt een actuele dataset met een niet-verwachte dataset. Alle records in de niet-verwachte dataset dienen
     * niet voor te komen in de actuele dataset. Tabellen/records die niet in de not-expected set voorkomen worden
     * genegeerd.
     * <p>
     *
     * @param notExpected
     *            De not-expected dataset.
     * @throws DatabaseUnitException
     * @throws SQLException
     */
    protected void compareNotExpectedWithActual(final IDataSet notExpected) throws DatabaseUnitException, SQLException {
        if (notExpected == null) {
            throw new IllegalArgumentException("Argument 'notExpected' is null");
        }

        final IDatabaseConnection connection = createConnection();
        try {
            final IDataSet actual = createActualSet(connection);

            final String[] notExpTableNames = notExpected.getTableNames();
            for (final String currentTableName : notExpTableNames) {
                final ITable expTable = notExpected.getTable(currentTableName);
                final ITable actTable = actual.getTable(currentTableName);
                final ITable filteredActTable = new RowFilterTable(actTable, new PrimaryKeyRowFilter(expTable));
                final int actualRowCount = filteredActTable.getRowCount();

                if (0 != actualRowCount) {
                    final String foutmelding =
                            "Er zijn "
                                               + actualRowCount
                                               + " records gevonden in de tabel "
                                               + currentTableName.toUpperCase()
                                               + " die voorkomen in de NOT EXPECTED dataset";
                    LOG.error(foutmelding);
                    LOG.error("****** De volgende 'NotExpected' records zijn aangetroffen in de database:");
                    LOG.error(FORMATTER.format(filteredActTable));
                    Assert.fail(foutmelding + " (zie ERROR-log voor details)");
                }
            }
        } finally {
            connection.close();
        }
    }

    protected IDataSet createDataset(final String[] dataSetFiles, final Class<?> testClass) {
        final List<IDataSet> dataSets = new ArrayList<>();
        for (final String dataSetFile : dataSetFiles) {
            try {
                final IDataSet ds = getDataSet(testClass, dataSetFile);
                dataSets.add(ds);
            } catch (final DataSetException dse) {
                throw new RuntimeException("Fout gevonden in testdata " + dataSetFile + " : " + dse.getMessage(), dse);
            }
        }
        try {
            return new CompositeDataSet(dataSets.toArray(new IDataSet[dataSets.size()]));
        } catch (final DataSetException dse) {
            throw new RuntimeException("Fout gevonden in testdata: " + dse.getMessage(), dse);
        }
    }

    /**
     * Lees een resource uit het classpath.
     *
     * @param clazz
     *            De class (van bijvoorbeeld een unit test) dat wordt toegepast om resource lookups tegen uit te voeren.
     *
     * @param resourceName
     *            De naam van de resource. Deze moet bestaan, anders wordt er een {@link IllegalStateException} gegooid.
     *
     * @return De inputstream naar het resource.
     */
    public static InputStream getResource(final Class<?> clazz, final String resourceName) {
        if (resourceName == null) {
            throw new IllegalArgumentException("Argument 'resourceName' is null");
        }
        final InputStream result = clazz.getResourceAsStream(resourceName);
        if (result == null) {
            throw new IllegalStateException("De resource \"" + resourceName + "\" voor klasse \"" + clazz.getName() + "\" kon niet worden gevonden.");
        }
        return result;
    }

    /**
     * Retourneert een dataset op basis van een XML bestand dat als resource is opgenomen. Dit resource wordt relatief
     * tov de opgegeven klasse bepaald.
     *
     * @param resource
     *            De naam van de resource waartegen de resources geresolved worden.
     *
     * @return De ingelezen dataset.
     *
     * @throws DataSetException
     *             Als de dataset problemen bevat.
     */
    private IDataSet getDataSet(final Class<?> clazz, final String resource) throws DataSetException {
        if (resource == null) {
            throw new IllegalArgumentException("Argument \"resource\" mag geen null zijn.");
        }
        final FlatXmlProducer producer = new FlatXmlProducer(new InputSource(getResource(clazz, resource)), new EntityResolver() {

            @Override
            public InputSource resolveEntity(final String publicId, final String systemId) throws SAXException, IOException {
                InputSource result = null;
                if ("brp_kern".equals(publicId) || systemId != null && systemId.endsWith("brp_kern.dtd")) {
                    result = new InputSource(getResource(clazz, "/schema/brp_kern.dtd"));
                } else if ("blokkering".equals(publicId) || systemId != null && systemId.endsWith("blokkering.dtd")) {
                    result = new InputSource(getResource(clazz, "/schema/blokkering.dtd"));
                }
                return result;
            }
        });
        producer.setValidating(true);
        return new CachedDataSet(producer);
    }

    /**
     * Retourneert een dataset op basis van een XML bestand.
     *
     * @param file
     *            xml file
     *
     * @return De ingelezen dataset.
     * @throws FileNotFoundException
     *             als het opgegeven bestand niet gevonden kan worden
     *
     * @throws DataSetException
     *             Als de dataset problemen bevat.
     */
    public IDataSet readDataSet(final File file) throws FileNotFoundException, DataSetException {
        if (file == null) {
            throw new IllegalArgumentException("Argument \"resource\" mag geen null zijn.");
        }
        final FlatXmlProducer producer = new FlatXmlProducer(new InputSource(new FileInputStream(file)), new EntityResolver() {

            @Override
            public InputSource resolveEntity(final String publicId, final String systemId) throws SAXException, IOException {
                InputSource result = null;
                switch (publicId) {
                    case "brp_kern":
                        result = new InputSource(getResource(DBUnitUtil.class, "/schema/brp_kern.dtd"));
                        break;
                }
                return result;
            }
        });
        producer.setValidating(true);
        return new CachedDataSet(producer);
    }

    private static class PrimaryKeyRowFilter implements IRowFilter {
        private static final String PK_COLUMN_NAME = "ID";

        private final Set<String> allowedPks = new HashSet<>();

        /**
         * @param expTable
         *            expected table
         */
        public PrimaryKeyRowFilter(final ITable expTable) {
            try {
                for (int i = 0; i < expTable.getRowCount(); i++) {
                    allowedPks.add(expTable.getValue(i, PK_COLUMN_NAME).toString());
                }
            } catch (final DataSetException e) {
                throw new IllegalStateException(e);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean accept(final IRowValueProvider rowValueProvider) {
            try {
                final String columnValue = rowValueProvider.getColumnValue(PK_COLUMN_NAME).toString();
                return allowedPks.contains(columnValue);
            } catch (final DataSetException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static class CurrentTimestampRowFilter implements IRowFilter {
        private static final Map<String, List<String>> TS_TABLE_COLUMNS = new HashMap<>();

        private List<String> timestampColumns = new ArrayList<>();

        static {
            TS_TABLE_COLUMNS.put("KERN.ADMHND", Arrays.asList("TSREG"));
        }

        /**
         * @param expTable
         *            expected table
         */
        public CurrentTimestampRowFilter(final ITable expTable) {
            final String tableName = expTable.getTableMetaData().getTableName().toUpperCase();
            if (TS_TABLE_COLUMNS.containsKey(tableName)) {
                timestampColumns = TS_TABLE_COLUMNS.get(tableName);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean accept(final IRowValueProvider rowValueProvider) {
            try {
                for (final String timestampColumn : timestampColumns) {
                    final Object columnValue = rowValueProvider.getColumnValue(timestampColumn);
                    if (columnValue instanceof Timestamp) {
                        final Timestamp timestamp = (Timestamp) columnValue;
                        // Check that timestamp is within 1 minute of current time
                        if (Math.abs(timestamp.getTime() - System.currentTimeMillis()) > 60000) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            } catch (final DataSetException e) {
                throw new IllegalStateException(e);
            }
            return true;
        }
    }

    private static class CurrentTimestampColumnFilter implements IColumnFilter {
        private static final Map<String, List<String>> TS_TABLE_COLUMNS = new HashMap<>();

        static {
            TS_TABLE_COLUMNS.put("KERN.ADMHND", Arrays.asList("TSREG"));
            TS_TABLE_COLUMNS.put("KERN.PERS", Arrays.asList("TSLAATSTEWIJZ", "TSLAATSTEWIJZGBASYSTEMATIEK"));
        }

        @Override
        public boolean accept(final String tablename, final Column column) {
            final String tablenameUpper = tablename.toUpperCase();
            if (TS_TABLE_COLUMNS.containsKey(tablenameUpper)) {
                return !TS_TABLE_COLUMNS.get(tablenameUpper).contains(column.getColumnName().toUpperCase());
            }
            return true;
        }
    }

    private static class BrpDbUnitAssert extends DbUnitAssert {
        private static final String TODAY = "TODAY";

        @Override
        protected boolean skipCompare(final String columnName, final Object expectedValue, final Object actualValue) {
            final boolean result;
            if (actualValue instanceof Timestamp && TODAY.equals(expectedValue)) {
                final Calendar actualCalendar = Calendar.getInstance();
                final Calendar expectedCalendar = (Calendar) actualCalendar.clone();
                actualCalendar.setTimeInMillis(((Timestamp) actualValue).getTime());
                clearTime(actualCalendar);
                clearTime(expectedCalendar);
                result = actualCalendar.equals(expectedCalendar);
            } else {
                result = false;
            }
            return result;
        }

        private void clearTime(final Calendar calendar) {
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
        }
    }
}
