/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.arttestdatagenerator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * De Class TestdataGenerator.
 */
public class TestdataGenerator {

    /** De version. */
    private static String fullVersion;
    private static String releaseVersion;
    private static String timestamp;

    public static String getFullVersion() {
        return fullVersion;
    }

    public static String getReleaseVersion() {
        return releaseVersion;
    }

    public static String getTimestamp() {
        return timestamp;
    }

    /** De log. */

    private final Logger log = LoggerFactory.getLogger(getClass());

    /** De Constante DATEFORMAT. */
    public static final DateFormat DATEFORMAT = DateFormat.getDateTimeInstance(DateFormat.FULL,
        DateFormat.FULL, new Locale("nl", "NL"));


    /**
     * .
     * @throws IOException .
     */
    private void readVersionInfo() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Util.copy(this.getClass().getClassLoader().getResourceAsStream("version.txt"), outputStream);
        String fullVersionTxt = outputStream.toString();
        TestdataGenerator.fullVersion = "TestdataGenerator " + fullVersionTxt;
        if (fullVersionTxt.lastIndexOf("(") != -1) {
            timestamp = fullVersionTxt.substring(fullVersionTxt.lastIndexOf("("));
            // get rid of the parenthesis '(', ')'
            timestamp = timestamp.replaceAll("\\(", "").replaceAll("\\)", "");
        }
        releaseVersion = fullVersionTxt.substring(0, fullVersionTxt.indexOf("-r"));

    }

    /**
     * .
     * @param referentieDir MOET een directory zijn !!
     * @return .
     */
    private List<String> getReferentieExcelSheets(final File referentieDir) {
        final List<String> excelFileNames = new ArrayList<>();
        if (referentieDir.exists() && referentieDir.isDirectory()) {
            final File[] fileNames = referentieDir.listFiles();
            if(fileNames != null) {
                for (final File fileName : fileNames) {
                    if (fileName.getName().toLowerCase().endsWith(".xls")) {
                        excelFileNames.add(fileName.getPath());
                    }
                }
            }
        }
        Collections.sort(excelFileNames);
        return excelFileNames;
    }

    /**
     * .
     * @param excelDirectory .
     * @param sqlFileName .
     * @throws IOException .
     */
    public void run(final String excelDirectory, final String sqlFileName) throws IOException {
        System.out.println("Looking for excel: " + excelDirectory);
        List<String> excelFileNames = new ArrayList<>();
        File excelDir = new File(excelDirectory);
        if (excelDir.isDirectory()) {
            File[] fileNames = excelDir.listFiles();
            if(fileNames != null) {
                for (final File fileName : fileNames) {
                    if (fileName.getName().toLowerCase().endsWith(".xls")) {
                        excelFileNames.add(fileName.getPath());
                    }
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

        // zorg nu dat referentie excelsheets als eerste wordt opgepikt.
        // referentieDatasheets staan in de './referentie-data/*.xsl'
        final List<String> referentieFileNames;
        if (excelDir.isDirectory()) {
            referentieFileNames = getReferentieExcelSheets(new File(excelDir, "referentie-data"));
        } else {
            File fDir = excelDir.getParentFile();
            if (fDir == null) {
                fDir = new File("./");
            }
            referentieFileNames = getReferentieExcelSheets(new File(fDir, "referentie-data"));
        }
        // voeg referentiesheets in omgekeerd alfabetische volgorde toe
        Collections.reverse(referentieFileNames);
        excelFileNames.addAll(0, referentieFileNames);

        System.out.println("Gevonden files:" + excelFileNames);
        readVersionInfo();
        log.info("Start " + fullVersion);
        final Generator generator = new Generator(excelFileNames);
        File f = new File(sqlFileName);
        File parent = f.getParentFile();

        FileUtils.outputDir = parent.getPath();
        System.out.println("Working dir is: " + new File(".").getAbsolutePath());
        if (!parent.exists()) {
            parent.mkdirs();
        }
        File csvDir = new File(parent, "csv");
        if (csvDir.exists()) {
            org.apache.commons.io.FileUtils.deleteDirectory(csvDir);
        }

        generator.generate(sqlFileName);
        log.info("End");
    }


}
