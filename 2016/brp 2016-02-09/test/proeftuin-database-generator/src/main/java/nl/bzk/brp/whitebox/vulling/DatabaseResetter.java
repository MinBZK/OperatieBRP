/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.whitebox.vulling;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class DatabaseResetter.
 */
public class DatabaseResetter {

    /** De log. */
    private final Logger        log         = LoggerFactory.getLogger(getClass());

    /** De Constante DRIVER_NAME. */
    private static final String DRIVER_NAME = "org.postgresql.Driver";

    final Constants constants = Constants.getInstance();

    /**
     * Instantieert een nieuwe database resetter.
     */
    public DatabaseResetter() {
        try {
            Class.forName(DRIVER_NAME).newInstance();
        } catch (final Exception e) {
            log.error("Error loading JDBC driver" + e.toString(), e);
        }
    }

    /**
     * Reset database.
     *
     * @param filename de filename
     */
    public void resetDatabase(final String filename) {

        log.info("Running file " + filename);

        try {
            // For using a default password (-w), you need to configure a PgPass File:
            // http://www.postgresql.org/docs/current/static/libpq-pgpass.html
            final Process p =
                Runtime.getRuntime().exec(
                        constants.getPsqldir() + File.separatorChar + "psql -e -w -U " + constants.getDatabaseUser()
                                + " -d " + constants.getDatabaseName() + " -o " + constants.getTempdir()
                                + File.separatorChar + "output.txt -f " + filename);

            new Thread() {
                @Override
                public void run() {
                    try {
                        final BufferedReader input = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                        String line;
                        while ((line = input.readLine()) != null) {
                            if (!line.contains("NOTICE:")) {
                                if (line.contains("ERROR:")) {
                                    log.error(line);
                                } else {
                                    log.info(line);
                                }
                            }
                        }
                        input.close();
                    } catch (final Exception e) {
                        log.error("", e);
                    }
                }
            }.start();

            new Thread() {

                @Override
                public void run() {
                    try {
                        final BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String line;
                        while ((line = input.readLine()) != null) {
                            log.info(line);
                        }
                        input.close();
                    } catch (final Exception e) {
                        log.error("", e);
                    }
                }
            }.start();

            p.waitFor();

        } catch (final Exception err) {
            log.error("", err);
        }
    }
}
