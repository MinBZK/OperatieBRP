/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.sierratestdatagenerator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Locale;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * De Class WhiteBoxFiller.
 */
public class SierraTestdataGenerator {

    /** De version. */
    private static String fullVersion;
    private static String releaseVersion;
    private static String timestamp;

    /** De Constante SOURCE_XLS_FILE. */
    public final static String SOURCE_XLS_FILE         = "src/main/resources/SierraTestdataGenerator.xls";

    /** De Constante WHITEBOXFILENAME. */
    public final static String WHITEBOX_FILE_NAME      = "target/sierratestdatagenerator.sql";

    public static String getFullVersion() {
        return fullVersion;
    }

    public static String getReleaseVersion() {
        return releaseVersion;
    }

    public static String getTimestamp() {
        return timestamp;
    }

    private SierraTestdataGenerator() {
    }

    /** De log. */

    private final Logger log = LoggerFactory.getLogger(getClass());

    /** De Constante datFormat. */
    public final static DateFormat datFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, new Locale("nl", "NL"));


    public static void main(final String[] args) throws Exception {
        Options options = CommandLineUtil.createOptions();
        CommandLine line = CommandLineUtil.parse(options, args);

        new SierraTestdataGenerator().run(line.getOptionValue(CommandLineUtil.OPTION_FILENAME), // SOURCE_XLS_FILE
                line.getOptionValue(CommandLineUtil.OPTION_SQL)     // WHITEBOX_FILE_NAME
                );
        }

    private void readVersionInfo() throws IOException {
		ArrayList<String> mySourceFiles;
		mySourceFiles = new ArrayList<String>();
		mySourceFiles.add("SierraTestdataGenerator");
		mySourceFiles.add("SierraTestdataODPER");

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Util.copy(this.getClass().getClassLoader().getResourceAsStream("version.txt"), outputStream);
        String fullVersionTxt = outputStream.toString();
        SierraTestdataGenerator.fullVersion = "SierraTestdataGenerator " + fullVersionTxt;
        if (fullVersionTxt.lastIndexOf("(") != -1) {
            timestamp = fullVersionTxt.substring(fullVersionTxt.lastIndexOf("("));
            // get rid of the parenthesis '(', ')'
            timestamp = timestamp.replaceAll("\\(", "").replaceAll("\\)", "");
        }
        releaseVersion = fullVersionTxt.substring(0, fullVersionTxt.indexOf("-r"));

    }

    private void run(final String excelFileName, final String sqlFileName) throws Exception {
        readVersionInfo();
        log.info("Start " + fullVersion);
        final Generator generator = new Generator(excelFileName);
        File f = new File(sqlFileName);
        File parent = f.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        generator.generate(sqlFileName);
		log.info("End");
    }


}
