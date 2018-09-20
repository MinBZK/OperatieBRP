/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.dataaccess;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;


/**
 * Abstracte/generieke unit test voor het testen van DAO classes. Deze generieke parent unit test bevat logica voor het
 * correct opbouwen en afbreken van de connectie naar de test database, alsmede logica voor het met behulp van DBUnit
 * initialiseren van de data in de database.
 */
@ContextConfiguration
public abstract class AbstractDAOTest extends AbstractTransactionalJUnit4SpringContextTests implements
        InitializingBean {

    private static final Logger LOGGER                  = LoggerFactory.getLogger(AbstractDAOTest.class);
    private static final String XML_DATASET_FILE_PATH   = "./src/test/resources/dbunit-test-data";
    private static final String JDBC_PROPERTY_FILE_PATH = "/jdbc.properties";
    private static final String DB_SCHEMA_NAME          = "Kern";
    private static final String DB_SCHEMA_TABLE_NAME    = "TABLE_SCHEM";

    @Autowired
    private DataSource          dataSource;
    @Autowired
    private IDatabaseConnection connection;

    private IDataSet dataSet = null;

    /**
     * Methode voor het met behulp van DBUnit toevoegen van een voor de test class specifieke data set. Deze methode
     * wordt vanwege de {@link Before} annotatie voor het uitvoeren van elke test methode aangeroepen.
     *
     * @throws SQLException fout die optreedt als er iets mis gaat met de call naar de database.
     * @throws DatabaseUnitException fout die optreedt als er iets mis gaat binnen DBUnit.
     */
    @Before
    public final void initDataSet() throws SQLException, DatabaseUnitException {
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
    }

    /**
     * Methode voor het met behulp van DBUnit weer verwijderen van de voor de test class toegevoegde specifieke data
     * set. Deze methode wordt vanwege de {@link After} annotatie na het uitvoeren van elke test methode aangeroepen.
     *
     * @throws SQLException fout die optreedt als er iets mis gaat met de call naar de database.
     * @throws DatabaseUnitException fout die optreedt als er iets mis gaat binnen DBUnit.
     */
    @After
    public final void cleanUpDataSet() throws DatabaseUnitException, SQLException {
        DatabaseOperation.DELETE_ALL.execute(connection, dataSet);
    }

    /**
     * Retourneert de naam van het DBUnit XML bestand met daarin de initiele data waarmee de database gevuld dient
     * te worden alvorens de test wordt gedraaid. Deze methode dient door subclasses overschreven te worden met dan
     * een verwijzing naar de naam van het XML bestand (in de {@code src/test/resources/dbunit-test-data} folder)
     * waarin de te gebruiken testdata is opgenomen.
     *
     * @return naam van een DBUnit XML bestand.
     */
    protected abstract String getXmlDataSetFileName();

    /**
     * Deze methode initialiseert de test database en de voor de test specifieke dataset. Deze methode wordt vanuit
     * Spring automatisch aangeroepen nadat Spring de bean heeft gecreeerd. In de methode wordt eerst gecontroleerd
     * of de database reeds bestaat (en het schema aanwezig is) en zo niet wordt de database alsnog geinitialiseerd.
     * Tevens wordt de DBUnit connectie configuratie aangepast om de voor de database driver specifieke "datatype
     * factory" te gebruiken, en wordt de voor de test specifieke dataset uitgelezen en geinitialiseerd.
     *
     * @throws DataSetException indien er een fout optreedt bij het uitlezen en opbouwen van de test specifieke
     *         dataset.
     * @throws IOException indien er een fout optreedt bij het initialiseren van de database.
     */
    @Override
    public final void afterPropertiesSet() throws IOException, DataSetException {
        // Initialiseer database indien dit nog niet is gedaan
        boolean initialized = isDatabaseReedsIsGeinitialiseerd();
        if (!initialized) {
            initialiseerDatabase();
        }

        // Zet de DBUnit database configuratie
        DatabaseConfig config = connection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());

        // Lees de dataset uit het betreffende bestand
        dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream(
                String.format("%s/%s", XML_DATASET_FILE_PATH, getXmlDataSetFileName())));
    }

    /**
     * Initialiseert de database door het uitvoeren van twee SQL scripts; een voor het creeeren van de tabellen,
     * schema, sequences etc. en een voor het invoegen van een standaard set met data (de stamtabellen).
     *
     * @throws IOException indien er een fout optreedt bij het lezen/laden van het properties bestand.
     */
    private void initialiseerDatabase() throws IOException {
        Properties properties = new Properties();
        properties.load(AbstractDAOTest.class.getResourceAsStream(JDBC_PROPERTY_FILE_PATH));

        Resource resource;
        SimpleJdbcTemplate template = new SimpleJdbcTemplate(dataSource);

        resource = new ClassPathResource(properties.getProperty("jdbc.initLocation"));
        SimpleJdbcTestUtils.executeSqlScript(template, resource, true);
        resource = new ClassPathResource(properties.getProperty("jdbc.dataLocation"));
        SimpleJdbcTestUtils.executeSqlScript(template, resource, true);
    }

    /**
     * Controleert of de test dataset reeds is geinitialiseerd of niet. Hiervoor wordt gekeken of het 'Kern' schema
     * reeds aanwezig is in de database of niet.
     *
     * @return een boolean die aangeeft of de dataset reeds is geinitialiseerd of niet.
     */
    private boolean isDatabaseReedsIsGeinitialiseerd() {
        boolean initialized = false;
        ResultSet result = null;
        try {
            result = dataSource.getConnection().getMetaData().getSchemas();
            while (result.next()) {
                if (result.getString(DB_SCHEMA_TABLE_NAME).equalsIgnoreCase(DB_SCHEMA_NAME)) {
                    initialized = true;
                    break;
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("Fout tijdens het controleren of het 'Kern' schema reeds aanwezig is.", ex);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException ex) {
                    LOGGER.error("Fout tijdens het afsluiten van de resultset.", ex);
                }
            }
        }
        return initialized;
    }

}
