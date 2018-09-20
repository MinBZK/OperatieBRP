/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.filter.ExcludeTableFilter;
import org.dbunit.dataset.filter.ITableFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;

/** Exporteert het BMR naar een DBUnit Dataset (XML bestand). */
public class ExportBmr {

    private static final String DB_BMR_DRIVER_PROP = "db.bmr.driver";
    private static final String DB_BMR_URL_PROP    = "db.bmr.url";
    private static final String DB_BMR_USER_PROP   = "db.bmr.user";
    private static final String DB_BMR_PASS_PROP   = "db.bmr.password";
    private static final String EXPORT_BESTAND     = "export.bestand";

    private final Properties properties;

    /**
     * Instantieert een nieuwe instantie en zet de properties op basis van een properties bestand.
     *
     * @throws java.io.IOException indien er problemen optreden bij het uitlezen van het properties bestand.
     */
    public ExportBmr() throws IOException {
        properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("bmr_import_export.properties"));
    }

    /**
     * Exporteert het BMR naar een DBUnit (XML) formaat.
     *
     * @param args command line argumenten; geen argumenten mogelijk
     * @throws IOException indien er problemen optreden bij het uitlezen van de properties of wegschrijven van het
     * DBUnit data bestand.
     * @throws ClassNotFoundException indien de driver voor de BMR database niet gevonden kan worden.
     * @throws SQLException indien er fouten optreden bij het uitvoeren van de benodigde SQL queries op de database.
     * @throws DatabaseUnitException indien er fouten optreden bij het vullen of wegschrijven van de DBUnit data file.
     */
    public static void main(final String[] args)
        throws IOException, ClassNotFoundException, SQLException, DatabaseUnitException
    {
        ExportBmr exportBmr = new ExportBmr();
        exportBmr.exporteer();
    }

    /**
     * Exporteert het BMR (middels een JDBC connectie naar de BMR Firebird database), naar een DBUnit dataset en
     * schrijft deze in een platte XML vorm weg naar een bestand.
     *
     * @throws ClassNotFoundException indien de driver voor de BMR database niet gevonden kan worden.
     * @throws SQLException indien er fouten optreden bij het uitvoeren van de benodigde SQL queries op de database.
     * @throws DatabaseUnitException indien er fouten optreden bij het vullen of schrijven van de DBUnit data file.
     * @throws IOException indien er problemen optreden bij het wegschrijven van het DBUnit data bestand.
     */
    public final void exporteer()
        throws ClassNotFoundException, SQLException, DatabaseUnitException, IOException
    {
        @SuppressWarnings("unused")
        final Class<?> driverClass = Class.forName(properties.getProperty(DB_BMR_DRIVER_PROP));

        // database connectie
        Connection jdbcConnectie = null;
        IDatabaseConnection connectie = null;
        try {
            jdbcConnectie = DriverManager.getConnection(properties.getProperty(DB_BMR_URL_PROP),
                properties.getProperty(DB_BMR_USER_PROP), properties.getProperty(DB_BMR_PASS_PROP));
            connectie = new DatabaseConnection(jdbcConnectie);

            // Tabel gefilterde database export
            final ITableFilter tabelFilter = bouwTabelFilter();
            final IDataSet dataSet = new FilteredDataSet(tabelFilter, connectie.createDataSet());
            FlatXmlDataSet.write(dataSet, new FileOutputStream(properties.getProperty(EXPORT_BESTAND)));
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
     * Bouwt een tabel filter op door enkele tabellen er uit te filteren.
     *
     * @return de tabel filter.
     */
    private ITableFilter bouwTabelFilter() {
        final ExcludeTableFilter tabelFilter = new ExcludeTableFilter();
        tabelFilter.excludeTable("MAT$*");
        tabelFilter.excludeTable("MDB$*");
        tabelFilter.excludeTable("MON$*");
        tabelFilter.excludeTable("RDB$*");

        return tabelFilter;
    }

}
