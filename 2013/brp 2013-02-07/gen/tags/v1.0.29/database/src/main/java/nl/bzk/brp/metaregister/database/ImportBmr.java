/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;

/**
 * Importeert de BMR data in de lokale BMR afslag, waarbij de data wordt uitgelezen uit een DB Unit bestand en de
 * settings voor de database worden uitgelezen uit een properties bestand.
 */
public class ImportBmr {

    private static final String DB_EXPORT_DRIVER_PROP = "db.lokaal.driver";
    private static final String DB_EXPORT_URL_PROP    = "db.lokaal.url";
    private static final String DB_EXPORT_USER_PROP   = "db.lokaal.user";
    private static final String DB_EXPORT_PASS_PROP   = "db.lokaal.password";
    private static final String EXPORT_BESTAND        = "export.bestand";

    private final Properties properties;

    /**
     * Instantieert een nieuwe instantie en zet de properties op basis van een properties bestand.
     *
     * @throws java.io.IOException indien er problemen optreden bij het uitlezen van het properties bestand.
     */
    public ImportBmr() throws IOException {
        properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("bmr_import_export.properties"));
    }

    /**
     * Main methode voor het aanroepen van de import.
     *
     * @param args command line argumenten; geen argumenten mogelijk
     */
    public static void main(final String[] args)
        throws IOException, ClassNotFoundException, SQLException, DatabaseUnitException
    {
        ImportBmr importBmr = new ImportBmr();
        importBmr.importeer();
    }

    /**
     * Voert de werkelijke import uit; leest de data uit het DBUnit XML bestand en insert deze in de lokale database
     * .
     */
    public final void importeer() throws ClassNotFoundException, SQLException, DatabaseUnitException, IOException {
        final Class driverClass = Class.forName(properties.getProperty(DB_EXPORT_DRIVER_PROP));

        // Database connectie
        Connection jdbcConnectie = null;
        IDatabaseConnection connectie = null;
        try {
            jdbcConnectie = DriverManager.getConnection(
                properties.getProperty(DB_EXPORT_URL_PROP), properties.getProperty(DB_EXPORT_USER_PROP),
                properties.getProperty(DB_EXPORT_PASS_PROP));
            connectie = new DatabaseConnection(jdbcConnectie);
            DatabaseConfig config = connectie.getConfig();
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());

            // Zet de referentiele integrity (tijdelijk) uit
            zetDatabaseReferentieleIntegriteit(connectie, false);

            // Tabel gefilterde database export
            final FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
            flatXmlDataSetBuilder.setColumnSensing(true);
            final IDataSet fullDataSet = flatXmlDataSetBuilder.build(new File(properties.getProperty(EXPORT_BESTAND)));
            DatabaseOperation.TRANSACTION(DatabaseOperation.CLEAN_INSERT).execute(connectie, fullDataSet);

            // Zet de referentiele integrity (tijdelijk) uit
            zetDatabaseReferentieleIntegriteit(connectie, true);
        } finally {
            if (connectie != null) {
                connectie.close();
            }
            if (jdbcConnectie != null) {
                jdbcConnectie.close();
            }
        }
    }

    /**
     * Zet de referentiele integriteit op de database op basis van de opgegeven waarde.
     *
     * @param connectie de connectie naar de database.
     * @param waarde de waarde (true of false) waartoe de referentiele integriteit gezet moet worden.
     */
    private void zetDatabaseReferentieleIntegriteit(final IDatabaseConnection connectie, final boolean waarde)
        throws SQLException
    {
        Statement statement = null;
        try {
            statement = connectie.getConnection().createStatement();
            statement.execute(String.format("SET DATABASE REFERENTIAL INTEGRITY %s;", waarde));
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }
}
