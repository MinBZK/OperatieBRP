/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.whitebox.vulling;

import java.io.IOException;
import java.util.Properties;

/**
 * De Class Constants.
 */
public final class Constants {

    private Properties prop;
    private static Constants instance;

    private Constants() {
        try {
            prop = new Properties();
            prop.load(this.getClass().getResourceAsStream("/whitebox.properties"));
        } catch (Exception ioe) {
            System.out.println("kan whitebox.properties niet vinden op het classpath");
            System.out.println(ioe.getMessage());
        }
    }

    public static Constants getInstance() {
        if (instance == null) {
            instance = new Constants();
        }

        return instance;
    }

    /**
     * De Constante WHITEBOXFILENAME.
     */
    public final static String WHITEBOX_FILE_NAME = "whiteboxfiller.sql";

    public String getDatabaseName(){
        return prop.getProperty("database_name");
    }

    public String getDatabaseUser(){
        return prop.getProperty("datbase_user");
    }

    public String getPsqldir(){
        return prop.getProperty("psqldir");
    }

    public String getTempdir(){
        return prop.getProperty("tempdir");
    }

    public int getAmbtenaarCount(){
        String ambtenaarCountString = prop.getProperty("ambtenaar_count");
        return Integer.valueOf(ambtenaarCountString);
    }

    /**
     * Geef de locatie van de Excel file.
     * @return path naar xls file
     */
    public String getSourceXlsFile() {
        return prop.getProperty("xls_file");
    }

    /**
     * Geef de locatie van de target folder.
     * @return path naar target folder
     */
    public String getTargetDir() {
        return prop.getProperty("target_dir");
    }

    /**
     * Geef de locatie naar de folder met sql files.
     * @return path naar sql files
     */
    public String getSqlDir() {
        return prop.getProperty("sqldir");
    }

    //FIXME: Vieze hack, lelijk, maar ff quickfix.
    public void setAmbtenarenCount(final int ambtenaarCount) {
        prop.setProperty("ambtenaar_count", "" + ambtenaarCount);
    }
}
