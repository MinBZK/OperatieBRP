/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.sierratestdatagenerator;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * De Class WhiteBoxFiller.
 */
public class SierraTestdataGenerator {

    /** De version. */
    private static String version;

    /** De Constante SOURCE_XLS_FILE. */
    public final static String SOURCE_XLS_FILE         = "src/main/resources/SierraTestdataGenerator.xls";

    /** De Constante WHITEBOXFILENAME. */
    public final static String WHITEBOX_FILE_NAME      = "target/sierratestdatagenerator.sql";

    public static String getVersion() {
	return version;
    }

    private SierraTestdataGenerator() {
    }

    /** De log. */
    private final Logger log = LoggerFactory.getLogger(getClass());

    /** De Constante datFormat. */
    public final static DateFormat datFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, new Locale("nl", "NL"));

    public static void main(final String[] args) throws Exception {
	new SierraTestdataGenerator().run(args);
    }

    private void run(final String[] args) throws Exception {
	final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	Util.copy(this.getClass().getClassLoader().getResourceAsStream("version.txt"), outputStream);
	SierraTestdataGenerator.version = "SierraTestdataGenerator " + outputStream.toString();

	log.info("Start " + version);
	final Generator generator = new Generator(SOURCE_XLS_FILE);
	generator.generate(WHITEBOX_FILE_NAME);
	log.info("End");
    }


}
