/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.art.util.backup;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import nl.bzk.brp.art.util.sql.JdbcExtract;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Maakt een logische backup van een database.
 *
 */
public class PersonBackup {
    /** . */
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonBackup.class);

    private String dbUrl = "jdbc:postgresql://localhost/brp";
    private String dbUser = "brp";
    private String dbPassword = "brp";
    private String fileInputName = "personlist.txt";
    private String fileOutputName = "personbackup.sql";


    /**
     * .
     * @param dbUrl .
     * @param dbUser .
     * @param dbPassword .
     * @param fileInputName .
     * @param fileOutputName .
     */
    public PersonBackup(final String dbUrl, final String dbUser, final String dbPassword,
            final String fileInputName, final String fileOutputName)
    {
        this.dbUrl = dbUrl; this.dbUser = dbUser; this.dbPassword = dbPassword;
        this.fileInputName = fileInputName; this.fileOutputName = fileOutputName;
    }

    /**
     * .
     * @throws SQLException .
     * @throws ClassNotFoundException .
     * @throws IOException .
     */
    public void run() throws SQLException, ClassNotFoundException, IOException {
        JdbcExtract util = new JdbcExtract(dbUrl, dbUser, dbPassword);
        BufferedReader br = null;
        FileOutputStream fs = null;
        try {
            br = new BufferedReader(new FileReader(fileInputName));
            fs = new FileOutputStream(fileOutputName);
            String line = br.readLine();

            // haal nu alle personen die we kunen vinden uit dde database. Dit kan heel veel zijn.
            while (StringUtils.isNotBlank(line)) {
                String sqlInsert = util.dumpPersById(null, Arrays.asList(
                        new Integer(line.trim())));
                if (sqlInsert != null && sqlInsert.length() > 0) {
                    fs.write(sqlInsert.getBytes());
                    fs.write("\n".getBytes());
                }
                line = br.readLine();
            }
        } finally {
            if (fs != null) { fs.flush(); fs.close(); }
            if (br != null) { br.close(); }
        }
        LOGGER.debug("Einde");

    }
}
