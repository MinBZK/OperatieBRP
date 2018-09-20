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

/**
 * De Class WhiteBoxFiller.
 */
// Known issue: gegenereerde whiteboxfiller.sql bevat geen spaties als scheidingsteken -> issue:DELTA-23
public class WhiteBoxFiller {

    /** De version. */
    private static String version;

    /** De ambtenaren count. */
    public static int ambtenarenCount = Constants.DEFAULT_AMBTENAAR_COUNT;

    /** De white box file name. */
    private static String whiteBoxFileName = Constants.WHITEBOX_FILE_NAME;

    /** De db reset. */
    private static Boolean dbReset = true;

    public static String getVersion() {
	return version;
    }

    /** De log. */
    private final Logger log = LoggerFactory.getLogger(getClass());

    /** De Constante datFormat. */
    public final static DateFormat datFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, new Locale("nl", "NL"));

    /** De Constante NO_DB_RESET. */
    private static final Object NO_DB_RESET = "no-db-reset";

    /**
     * De main methode.
     *
     * @param args
     *            de argumenten
     * @throws Exception
     *             de exception
     */
    public static void main(final String[] args) throws Exception {
	new WhiteBoxFiller().run(args);
    }

    /**
     * Run.
     *
     * @param args
     *            de args
     * @throws Exception
     *             de exception
     */
    private void run(final String[] args) throws Exception {
	final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	Util.copy(this.getClass().getClassLoader().getResourceAsStream("version.txt"), outputStream);
	WhiteBoxFiller.version = "WhiteBoxFiller " + outputStream.toString();
	try {
	    if (args.length > 0) {
		ambtenarenCount = Integer.valueOf(args[0]);
	    }
	    if (args.length > 1) {
		whiteBoxFileName = args[1];
	    }
	    if (args.length > 2) {
		dbReset = Boolean.valueOf(!args[2].equals(NO_DB_RESET));
	    }
	} catch (Exception e) {
	    log.error("Fout opgetreden bij verwerken van opstartparameters: ", e);
	}

	log.info("Start " + version);
	runFromCommandLine(ambtenarenCount, whiteBoxFileName, dbReset);
	log.info("End");
    }

    /**
     * Run from command line.
     *
     * @param ambtenarenSize
     *            de ambtenaren size
     * @param sqlFileName
     *            de sql file name
     * @param dbReset
     *            de db reset
     * @throws Exception
     *             de exception
     */
    private void runFromCommandLine(final Integer ambtenarenSize, final String sqlFileName, final Boolean dbReset) throws Exception {
	final Generator generator = new Generator(Constants.SOURCE_XLS_FILE);
	generator.generate(Constants.TARGET_DIR + sqlFileName);

	// Bij optie NO_DB_RESET wordt de database reset niet uitgevoerd
	if (dbReset) {
	    preResetDatabase();
	    new DatabaseResetter().resetDatabase(Constants.TARGET_DIR + sqlFileName);
	    postResetDatabase();
	}
    }

    /**
     * Reset database.
     *
     * @throws Exception
     *             de exception
     */
    private void preResetDatabase() throws Exception {

	final List<String> sqlFiles = Arrays.asList(new String[] { "brp.sql", "stamgegevensLand.sql", "stamgegevensNationaliteit.sql", "stamgegevensPlaats.sql", "stamgegevensStatisch.sql", "stamgegevensPartijGemeente.sql", "stamgegevensVerkrijgingVerliesNLNationaliteit.sql", "stamgegevensUpdateSoortBericht.sql" });
	for (final String sqlFile : sqlFiles) {
	    new DatabaseResetter().resetDatabase(Constants.BASE + File.separatorChar + sqlFile);
	}
    }

    private void postResetDatabase() throws Exception {

	final List<String> sqlFiles = Arrays.asList(new String[] { "autmiddel_en_certificaten.sql", "brp-indexen.sql" });
	for (final String sqlFile : sqlFiles) {
	    new DatabaseResetter().resetDatabase(Constants.BASE + File.separatorChar + sqlFile);
	}
    }

}
