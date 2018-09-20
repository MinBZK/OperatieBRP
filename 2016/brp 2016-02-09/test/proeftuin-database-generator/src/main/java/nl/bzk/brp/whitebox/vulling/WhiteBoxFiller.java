/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.whitebox.vulling;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * De Class WhiteBoxFiller.
 *
 * Known issue: gegenereerde whiteboxfiller.sql bevat geen spaties als scheidingsteken -> issue:DELTA-23
 */
public class WhiteBoxFiller {

    /**
     * De Constante DATEFORMAT.
     */
    public static final DateFormat DATEFORMAT =
        DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, new Locale("nl", "NL"));
    /**
     * De Constante NO_DB_RESET.
     */
    private static final Object NO_DB_RESET = "no-db-reset";
    /**
     * De version.
     */
    private static String version;
    /**
     * De white box file name.
     */
    private static String whiteBoxFileName = Constants.WHITEBOX_FILE_NAME;
    /**
     * De db reset.
     */
    private static Boolean dbReset = true;

    /**
     * De constanten.
     */
    final Constants constants = Constants.getInstance();
    /**
     * De log.
     */
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * De main methode.
     *
     * @param args de argumenten
     * @throws Exception de exception
     */
    public static void main(final String[] args) throws Exception {
        new WhiteBoxFiller().run(args);
    }

    /**
     * Run.
     *
     * @param args de args
     * @throws Exception de exception
     */
    private void run(final String[] args) throws Exception {
        int ambtenaarCount = constants.getAmbtenaarCount();

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Util.copy(this.getClass().getClassLoader().getResourceAsStream("version.txt"), outputStream);
        WhiteBoxFiller.version = "WhiteBoxFiller " + outputStream.toString();
        try {
            if (args.length > 0) {
                ambtenaarCount = Integer.valueOf(args[0]);
            }
            if (args.length > 1) {
                whiteBoxFileName = args[1];
            }
            if (args.length > 2) {
                dbReset = !args[2].equals(NO_DB_RESET);
            }
        } catch (final Exception e) {
            log.error("Fout opgetreden bij verwerken van opstartparameters: ", e);
        }
        //FIXME: Vieze hack, lelijk, maar ff quickfix.
        constants.setAmbtenarenCount(ambtenaarCount);

        log.info("Start " + version);
        runFromCommandLine(whiteBoxFileName, dbReset);
        log.info("End");
    }

    /**
     * Run from command line.
     *
     * @param sqlFileName    de sql file name
     * @param dbReset        de db reset
     * @throws Exception de exception
     */
    private void runFromCommandLine(final String sqlFileName, final Boolean dbReset) throws Exception {
        final Constants constants = Constants.getInstance();
        final Generator generator = new Generator(constants.getSourceXlsFile());
        generator.generate(constants.getTargetDir() + sqlFileName);

        // Bij optie NO_DB_RESET wordt de database reset niet uitgevoerd
        if (dbReset) {
            new DatabaseResetter().resetDatabase(constants.getTargetDir() + sqlFileName);
        }
    }

    public static String getVersion() {
        return version;
    }

}
