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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    /** . */
    public SierraTestdataGenerator() {
    }

    /** De log. */

    private final Logger log = LoggerFactory.getLogger(getClass());

    /** De Constante DATEFORMAT. */
    public static final DateFormat DATEFORMAT = DateFormat.getDateTimeInstance(DateFormat.FULL,
        DateFormat.FULL, new Locale("nl", "NL"));


//    /**
//     * .
//     * @param args .
//     * @throws Exception .
//     */
//    public static void main(final String[] args) throws Exception {
//        Options options = CommandLineUtil.createOptions();
//        CommandLine line = CommandLineUtil.parse(options, args);
//        if (line.hasOption(CommandLineUtil.COMMAND_CONVERT)) {
//
//            new SierraTestdataGenerator().run(
//                   line.getOptionValue(CommandLineUtil.OPTION_EXCEL_DIR),
//                    line.getOptionValue(CommandLineUtil.OPTION_SQL)
//                );
//        }
//    }

    private void readVersionInfo() throws IOException {

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

    /**
     * .
     * @param excelDirectory .
     * @param sqlFileName .
     * @throws IOException .
     */
    public void run(final String excelDirectory, final String sqlFileName) throws IOException {
        System.out.println("Looking for excel: " + excelDirectory);
        List<String> excelFileNames = new ArrayList<String>();
        File excelDir = new File(excelDirectory);
        if (excelDir.isDirectory()) {
            File[] fileNames = excelDir.listFiles();
            for (int i = 0; i < fileNames.length; i++) {
                if (fileNames[i].getName().toLowerCase().endsWith(".xls")) {
                    excelFileNames.add(fileNames[i].getPath());
                }
            }
            if (excelFileNames.size() == 0) {
                throw new IOException("Kan geen bestanden vinden in " + excelDirectory);
            }
        } else if (excelDir.isFile() && excelDir.exists() && excelDir.getPath().toLowerCase().endsWith(".xls")) {
            excelFileNames.add(excelDirectory);
        } else {
            throw new IOException("Kan geen bestanden vinden in " + excelDirectory);
        }
        System.out.println("Gevonden files:" + excelFileNames);
        readVersionInfo();
        log.info("Start " + fullVersion);
        final Generator generator = new Generator(excelFileNames);
        File f = new File(sqlFileName);
        File parent = f.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        generator.generate(sqlFileName);
        log.info("End");
    }


}
