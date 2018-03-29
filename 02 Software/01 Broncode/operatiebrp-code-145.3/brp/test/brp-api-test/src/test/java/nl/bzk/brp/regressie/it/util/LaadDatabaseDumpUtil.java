/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.regressie.it.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * (1) Laad DB dump : De restore vd dump wordt uitgevoerd in 'art-brp' (=postgresdb).
 * De parameters zijn :
 * - BACKUP_FILE : naam van de .backup file, het bestand is in de src/test/resources directory geplaatst
 * - PG_BIN : bin dir waar postgres functies (pg_restore, dropdb etc.) te vinden zijn (Doorgaans '../Program Files/PostgreSQL/9.1/bin' (Windows) of 'usr/bin').
 *
 * - NB : pg_restore kan afhankelijk v lokale configuratie fout gaan omdat password is vereist. Wachtwoord kan in pgpass.conf gezet worden :
 *      The file .pgpass in a user's home directory or the file referenced by PGPASSFILE can contain passwords to be used if the connection requires a password
 *      (and no password has been specified otherwise). On Microsoft Windows the file is named %APPDATA%\postgresql\pgpass.conf
 *      (where %APPDATA% refers to the Application Data subdirectory in the user's profile).
 *      This file should contain lines of the following format: hostname:port:database:username:password
 */

public class LaadDatabaseDumpUtil {

    //de bin directory met postgres functies (pg_restore, dropdb, createdb)
    private static final String PG_BIN = "C:\\Program Files\\PostgreSQL\\9.2\\bin";
    // mac: meestal  op (afhankelijk van het versie nummer) /Library/PostgreSQL/9.5/
    //private static final String PG_BIN = "/Library/PostgreSQL/9.5/";

    //het bestand met de DB dump
    private static final String BACKUP_FILE = "brp/test/brp-api-test/src/test/resources/testcases/IENT/INTEST_2833/DBdump.backup";

    public static void main(String[] args) {
        laadDatabaseDump();
    }


    private static void laadDatabaseDump() {
        final String pad = new File(BACKUP_FILE).getAbsolutePath();
        final ProcessBuilder restoreDump = new ProcessBuilder(
                String.join(PG_BIN, "pg_restore"),
                "--host", "localhost",
                "--port", "5432",
                "--username", "postgres",
                "--dbname", "art-brp",
                "--role", "postgres",
                "--no-password",
                "--verbose",
                //comment de 'data-only' parameter uit om een volledige restore te doen van het schema en de data
                "--data-only",
                "--disable-triggers",
                pad);

        run(restoreDump);
    }

    private static void run(final ProcessBuilder... processBuilders) {
        Process process;
        for (ProcessBuilder pb : processBuilders) {
            try {
                pb.redirectErrorStream(true);
                process = pb.start();
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String ll;
                while ((ll = br.readLine()) != null) {
                    System.out.println(ll);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
