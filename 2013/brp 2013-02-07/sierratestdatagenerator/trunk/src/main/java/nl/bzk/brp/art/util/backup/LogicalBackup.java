/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.art.util.backup;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.art.util.sql.JdbcExtract;
import nl.bzk.brp.art.util.sql.JdbcUpdateRef;
import nl.bzk.brp.art.util.sql.PersoonDto;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Maakt een logische backup van een database.
 *
 */
public class LogicalBackup {
    /** . */
    private static final Logger LOGGER = LoggerFactory.getLogger(LogicalBackup.class);

    private String dbUrl = "jdbc:postgresql://localhost/brp";
    private String dbUser = "brp";
    private String dbPassword = "brp";


    /**
     * .
     * @param dbUrl .
     * @param dbUser .
     * @param dbPassword .
     */
    public LogicalBackup(final String dbUrl, final String dbUser, final String dbPassword) {
        this.dbUrl = dbUrl; this.dbUser = dbUser; this.dbPassword = dbPassword;
    }

    /**
     * .
     * @throws SQLException .
     * @throws ClassNotFoundException .
     */
    public void run() throws SQLException, ClassNotFoundException {
        JdbcExtract util = new JdbcExtract(dbUrl, dbUser, dbPassword);
        JdbcUpdateRef updateRef = new JdbcUpdateRef(dbUrl, dbUser, dbPassword);
        // zorg eerst dat de test schema aangemaakt wordt in de database
        // we vertrouwen nu dat dit al aangemaakt is bij het vullen van de database met de testdata.

        // haal nu alle personen die we kunen vinden uit dde database. Dit kan heel veel zijn.
        List<PersoonDto> persIds = util.getAllPersIds();
        List<String> dbVersionTabel = util.getVersionNumber();
        String dbVersion = null;
        String excelTimestamp = null;
        if (CollectionUtils.isNotEmpty(dbVersionTabel)) {
            dbVersion = dbVersionTabel.get(0);
            excelTimestamp = dbVersionTabel.get(1);
            LOGGER.debug("Oude backup data verwijdered " + dbVersion);
            updateRef.reset(dbVersion);
        } else {
            LOGGER.debug("KAN GEEN oude backup data vinden, dbversion is leeg.");
        }
        LOGGER.debug("Er worden nu " + persIds.size() + " personen gebackuped");
        for (PersoonDto pers : persIds) {
//            LOGGER.debug ("p:["+pers.getId()+","+pers.getBsn()+","+pers.getAnr()+"]");
            String sqlInsert = util.dumpPersById(null, Arrays.asList(
                    pers.getId()));
            if (sqlInsert != null && sqlInsert.length() > 0) {
                updateRef.addSqlQuery(pers.getId(), pers.getBsn(), dbVersion, excelTimestamp, sqlInsert);
            }
        }
        LOGGER.debug("Einde");

    }
}
