/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.whitebox.vulling;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WhiteBoxFiller {

    private static String version;

    public static int     ambtenarenCount = Constants.DEFAULT_AMBTENAAR_COUNT;

    public static String getVersion() {
        return version;
    }

    private final Logger           log         = LoggerFactory.getLogger(getClass());

    public final static DateFormat datFormat   = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL,
                                                       new Locale("nl", "NL"));

    private static final Object    NO_DB_RESET = "no-db-reset";

    public static void main(final String[] args) throws Exception {
        new WhiteBoxFiller().run(args);
    }

    private void run(final String[] args) throws Exception {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Util.copy(this.getClass().getClassLoader().getResourceAsStream("version.txt"), outputStream);
        WhiteBoxFiller.version = "WhiteBoxFiller " + outputStream.toString();

        log.info("Start " + version);
        if (args.length == 0) {
            runFromCommandLine("" + Constants.DEFAULT_AMBTENAAR_COUNT, Constants.WHITEBOX_FILE_NAME, null);
        } else if (args.length == 1) {
            runFromCommandLine(args[0], Constants.WHITEBOX_FILE_NAME, null);
        } else if (args.length == 2) {
            runFromCommandLine(args[0], args[1], null);
        } else {
            runFromCommandLine(args[0], args[1], args[2]);
        }
        log.info("End");
    }

    private void runFromCommandLine(final String size, final String sqlFileName, final String option) throws Exception {
        log.info("Size ambtenaren: " + size);
        ambtenarenCount = Integer.parseInt(size);

        final Generator generator = new Generator(Constants.SOURCE_XLS_FILE);
        generator.generate(Constants.TARGET_DIR + sqlFileName);

        // Bij optie NO_DB_RESET wordt de database reset niet uitgevoerd
        if (option == null || !option.equals(NO_DB_RESET)) {
            resetDatabase();
            new DatabaseResetter().resetDatabase(Constants.TARGET_DIR + sqlFileName);
        }
    }

    private void resetDatabase() throws Exception {

        final List<String> sqlFiles =
            Arrays.asList(new String[] { "brp.sql", "stamgegevensLand.sql", "stamgegevensNationaliteit.sql",
                "stamgegevensPlaats.sql", "stamgegevensStatisch.sql", "stamgegevensPartijGemeente.sql" });
        for (final String sqlFile : sqlFiles) {
            new DatabaseResetter().resetDatabase(Constants.BASE + File.separatorChar + sqlFile);
        }
    }

}
