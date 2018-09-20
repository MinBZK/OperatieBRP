/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.arttestdatagenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.text.DateFormatter;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Generator {

    private final Logger log = LoggerFactory.getLogger(getClass());


    private final List<String> xlsFilenames;

    /**
     * Converteer 1 excel sheet en schrijft alle SQL insert statements naar de printWriter.
     * @param xlsFilenames lijst van excel files.
     */
    public Generator(final List<String> xlsFilenames) {
        this.xlsFilenames = xlsFilenames;
    }

    /** Dit moet gerefactored worden, de sql bestanden MOETEN niet niet in dit project staan. */
    private static final List<String> EXTERNAL_SQL_FILES_BEFORE = Arrays.asList(
            "db/art-helper/scenario_data_verwijderaar.sql",
            "db/art-helper/testtables.sql",
            "db/art-helper/foreburner.sql");

    private static final List<String> EXTERNAL_SQL_FILES_AFTER = Arrays.asList("db/art-helper/afterburner.sql");
    /**
     * .
     * @param printWriter .
     * @throws ParseException .
     */
    private void printInto(final PrintWriter printWriter) throws ParseException {

        printWriter.println("--- Start " + " " + TestdataGenerator.getFullVersion() + " @ "
            + new DateFormatter(TestdataGenerator.DATEFORMAT).valueToString(new Date()));

        printWriter.println();
        printWriter.println("--- ######   ######  ###### ");
        printWriter.println("--- #     #  #     # #     #");
        printWriter.println("--- #     #  #     # #     #");
        printWriter.println("--- ######   ######  ###### ");
        printWriter.println("--- #     #  #   #   #      ");
        printWriter.println("--- #     #  #    #  #      ");
        printWriter.println("--- ######   #     # #      ");
        printWriter.println();
        printWriter.println("--- #######  #######   #####   #######  ######      #     #######     #");
        printWriter.println("---    #     #        #     #     #     #     #    # #       #       # #");
        printWriter.println("---    #     #        #           #     #     #   #   #      #      #   #");
        printWriter.println("---    #     #####     #####      #     #     #  #     #     #     #     #");
        printWriter.println("---    #     #              #     #     #     #  #######     #     #######  ");
        printWriter.println("---    #     #        #     #     #     #     #  #     #     #     #     #");
        printWriter.println("---    #     #######   #####      #     ######   #     #     #     #     #");
        printWriter.println();
        printWriter.println("---  #####   #######  #     #  #######  ######      #     #######  #######  ######");
        printWriter.println("--- #     #  #        ##    #  #        #     #    # #       #     #     #  #     #");
        printWriter.println("--- #        #        # #   #  #        #     #   #   #      #     #     #  #     #");
        printWriter.println("--- #  ####  #####    #  #  #  #####    ######   #     #     #     #     #  ######");
        printWriter.println("--- #     #  #        #   # #  #        #   #    #######     #     #     #  #   #");
        printWriter.println("--- #     #  #        #    ##  #        #    #   #     #     #     #     #  #    #");
        printWriter.println("---  #####   #######  #     #  #######  #     #  #     #     #     #######  #     #");
        printWriter.println();
        printWriter.println("--------------------------------------------------");
        printWriter.println("--- Version:    " + TestdataGenerator.getFullVersion());
        printWriter.println("--- Timestamp:  "
            + new DateFormatter(TestdataGenerator.DATEFORMAT).valueToString(new Date()));
        printWriter.println("--- Created by: " + System.getProperty("user.name"));
        printWriter.println("--- XLS file:   " + xlsFilenames);
        printWriter.println("--------------------------------------------------");
        printWriter.println();
    }

    /**
     * .
     * @param xlsFilename .
     * @param printWriter .
     * @throws BiffException .
     * @throws IOException .
     */
    private void processSingleExcelFile(final String xlsFilename, final PrintWriter printWriter)
        throws BiffException, IOException
    {
        // http://bhuwan-javaj2eeproblemssolution.blogspot.nl/2010/03/junk-characters-while-reading-excel.html
        Workbook workbook;
        System.out.println("Parsing: " + xlsFilename);
        WorkbookSettings ws = new WorkbookSettings();
        ws.setEncoding("Cp1252");
        //dit versnelt de boel nogal!
        ws.setGCDisabled(true);
//        ws.setLocale(new Locale("nl", "NL"));
        workbook = Workbook.getWorkbook(new File(xlsFilename), ws);

        printWriter.println("--------------------------------------------------");
        printWriter.println("--- START Generated from: " + xlsFilename);
        printWriter.println("--------------------------------------------------");
        printWriter.println();
        WorkbookHandler handler = new WorkbookHandlerImpl(workbook);
        handler.print(printWriter);

        printWriter.println("--------------------------------------------------");
        printWriter.println("--- END Generated from: " + xlsFilename);
        printWriter.println("--------------------------------------------------");
        workbook.close();
    }

    /**
     * Genereer de complete sql in een bestand; wordt gebouwd uit meerdere excel sheet en enkele externe sql bestanden.
     * @param outputName de sql bestand
     * @return true als goed afgelopen, false anders.
     */
    public boolean generate(final String outputName) {
        log.info("Reading '" + xlsFilenames + "'");
        log.info("Writing '" + outputName + "'");
        Writer writer = null;
        PrintWriter printWriter = null;

        try {
            writer = new OutputStreamWriter(new FileOutputStream(outputName), "UTF-8");

            // bufferedWriter = new BufferedWriter(writer, 1024*1024*4);
            printWriter = new PrintWriter(writer, true);
            printInto(printWriter);

            for (final String sqlFile : EXTERNAL_SQL_FILES_BEFORE) {
                addClassPathFile(sqlFile, printWriter);
            }

            System.out.println("excel files: " + xlsFilenames);
            for (String xlsFilename : xlsFilenames) {
                processSingleExcelFile(xlsFilename, printWriter);
            }

            for (final String sqlFile : EXTERNAL_SQL_FILES_AFTER) {
                addClassPathFile(sqlFile, printWriter);
            }

            // bolie: de certificaten worden NIET meer geimporteerd,
            // het is een onderdeel van de 'statische' data geworden.

            // bolie: indexen moeten we niet meer aanmaken (tenzij we ze eerst diablen en na de insert enablen).

            printWriter.println();
            printWriter.println("--- END "
                    + " "
                    + TestdataGenerator.getFullVersion()
                    + " @ "
                    + new DateFormatter(TestdataGenerator.DATEFORMAT)
                            .valueToString(new Date()));

            return true;

        } catch (final Exception e) {
            log.error("", e);
            if (printWriter != null) {
                printWriter.println("An error occurred, dumping exception:");
            }
            e.printStackTrace(printWriter);
        } finally {
            try {
                if (printWriter != null && writer != null) {
                    printWriter.flush();
                    writer.flush();
                    printWriter.close();
                    writer.close();
                }
            } catch (final Exception f) {
                log.error("", f);
            }

            log.info("Done");

        }
        return false;
    }

    /**
     * .
     * @param string .
     * @param printWriter .
     * @throws IOException .
     * @throws ParseException .
     */
    private void addClassPathFile(final String string, final PrintWriter printWriter)
        throws IOException, ParseException
    {
        log.info("Adding '" + string + "'");
        printWriter.println("--------------------------------------------------");
        printWriter.println("--- Included: " + string);
        printWriter.println("--------------------------------------------------");
        printWriter.println();
        printWriter.flush();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(string);
        if (null == is) {
            throw new IOException(String.format("Cannot load inputstream: [%s]. Program terminated.", string));
        }
        IOUtils.copy(is, printWriter, "UTF-8");
    }

}
