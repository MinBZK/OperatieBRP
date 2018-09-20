/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.whitebox.vulling;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.text.DateFormatter;
import jxl.Workbook;
import jxl.WorkbookSettings;
import nl.bzk.brp.whitebox.vulling.model.Gemeente;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Generator {

    private static final List<SheetHandler> sheetHandlers = new ArrayList<SheetHandler>();
    public static List<Gemeente> gemeenten = new ArrayList<Gemeente>();

    static {
        sheetHandlers.add(new GemeenteMetaHandler(12, gemeenten));
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(0)); // persoon
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(24)); // admhnd
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(2)); // brpactie
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(3)); // persoon-ident
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(4)); // his_geslacht
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(5)); // his_perssamengesteldenaam
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(7)); // his_geboorte
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(8)); // his_overlijden
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(10)); // persadres
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(11)); // his_persadres
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(14)); // his_persbijhouding
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(16)); // relatie
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(17)); // his_relatie
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(18)); // betrokkenheid relatie
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(19)); // his_ouderschap
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(21)); // pers indicatie
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(22)); // his pers indicatie
        //Opschorting zit tegenwoordig in his_persbijhouding
//	sheetHandlers.add(new SheetHandlerImplSimpleInsert(23)); // his pers opschorting
        // sheetHandlers.add(new SheetHandlerImplAfterBurner(20));
    }

    final Constants constants = Constants.getInstance();
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String xlsFilename;
    Workbook workbook;

    public Generator(final String xlsFilename) {
        this.xlsFilename = xlsFilename;
    }

    public boolean generate(final String outputName) {

        log.info("Reading '" + xlsFilename + "'");
        log.info("Writing '" + outputName + "'");

        Writer writer = null;
        PrintWriter printWriter = null;

        try {
            writer = new OutputStreamWriter(new FileOutputStream(outputName), "UTF-8");
            printWriter = new PrintWriter(writer, true);

            printWriter.println(
                "--- Start " + " " + WhiteBoxFiller.getVersion() + " @ " + new DateFormatter(WhiteBoxFiller.DATEFORMAT).valueToString(new Date()));

            printWriter.println();
            printWriter.println("--- #     #  #     #  ###  #######  #######  ######   #######  #     #  #######  ###  #        #        #######  ######");
            printWriter
                .println("--- #  #  #  #     #   #      #     #        #     #  #     #   #   #   #         #   #        #        #        #     #");
            printWriter
                .println("--- #  #  #  #     #   #      #     #        #     #  #     #    # #    #         #   #        #        #        #     #");
            printWriter.println("--- #  #  #  #######   #      #     #####    ######   #     #     #     #####     #   #        #        #####    ######");
            printWriter.println("--- #  #  #  #     #   #      #     #        #     #  #     #    # #    #         #   #        #        #        #   #");
            printWriter.println("--- #  #  #  #     #   #      #     #        #     #  #     #   #   #   #         #   #        #        #        #    #");
            printWriter
                .println("---  ## ##   #     #  ###     #     #######  ######   #######  #     #  #        ###  #######  #######  #######  #     #");

            printWriter.println();
            printWriter.println("--------------------------------------------------");
            printWriter.println("--- WhiteBoxFiller");
            printWriter.println("--- Version:    " + WhiteBoxFiller.getVersion());
            printWriter.println("--- Timestamp:  " + new DateFormatter(WhiteBoxFiller.DATEFORMAT).valueToString(new Date()));
            printWriter.println("--- Created by: " + System.getProperty("user.name"));
            printWriter.println("--- XLS file:   " + xlsFilename);
            printWriter.println("--------------------------------------------------");
            printWriter.println();

            addClassPathFile("/foreburner.sql", printWriter);

            // Nodig voor juist uitlezen van diakrieten (cp1252 is Latin 1 tekenset, anders geeft JXL plugin problemen)
            final WorkbookSettings ws = new WorkbookSettings();
            ws.setEncoding("cp1252");

            workbook = Workbook.getWorkbook(new File(xlsFilename), ws);

            AdresLookup.initialize(workbook.getSheet(12));

            printWriter.println("--------------------------------------------------");
            printWriter.println("--- START Generated from: " + xlsFilename);
            printWriter.println("--------------------------------------------------");
            printWriter.println();

            for (final SheetHandler sheetHandler : sheetHandlers) {
                sheetHandler.print(workbook, printWriter);
                printWriter.flush();
                //bufferedWriter.flush();
                writer.flush();
            }

            printWriter.println("--------------------------------------------------");
            printWriter.println("--- END Generated from: " + xlsFilename);
            printWriter.println("--------------------------------------------------");

            addClassPathFile("/afterburner.sql", printWriter);

            extraBsnNummers(printWriter);
            extraANummers(printWriter);

//		-- VACUUM -- garbage-collect and optionally analyze a database,
//		-- ANALYZE collects statistics about the contents of tables in the database, and stores the results in the pg_statistic
//		-- system catalog. Subsequently, the query planner uses these statistics to help determine the most efficient execution
//		-- plans for queries.
            printWriter.println("VACUUM ANALYZE;");

            printWriter.println();
            printWriter
                .println("--- END " + " " + WhiteBoxFiller.getVersion() + " @ " + new DateFormatter(WhiteBoxFiller.DATEFORMAT).valueToString(new Date()));

            return true;

        } catch (final Exception e) {
            log.error("", e);
            if (printWriter != null) {
                printWriter.println("An error occurred, dumping exception:");
            }
            e.printStackTrace(printWriter);
        } finally {
            try {
                if (printWriter != null && writer != null /*&& bufferedWriter != null*/) {

                    //bufferedWriter.flush();
                    printWriter.flush();
                    writer.flush();

                    //bufferedWriter.close();
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

    private void extraBsnNummers(final PrintWriter result) throws ParseException {

        log.info("Extra BSN's");

        result.println("BEGIN;");
        result.println("--- BEGIN BSN's");
        result.println();
        result.println("DROP TABLE IF EXISTS Kern.bsnvooraad CASCADE;");
        result.println();
        result.println("CREATE TABLE Kern.bsnvooraad (");
        result.println("	bsn int CONSTRAINT bsnvooraad_pk PRIMARY KEY");
        result.println("	,gemeentecode int NOT NULL");
        result.println("	,gemeentenaam varchar(255) NOT NULL");
        result.println("	,ambtenaar int NOT NULL");
        result.println("	);");
        result.println("COMMIT;");
        result.println();

        for (final Gemeente gemeente : Generator.gemeenten) {

            log.debug("BSN's for " + gemeente.naam);

            for (int ambtenaarCount = 0; ambtenaarCount < constants.getAmbtenaarCount(); ambtenaarCount++) {

                result.println("--------------------------------------------------");
                result.println("--- Xtra BSN's");
                result.println("--- Gemeente: " + gemeente.naam);
                result.println("--- Ambtenaar: " + ambtenaarCount);
                result.println("--- Generated at: " + new DateFormatter(WhiteBoxFiller.DATEFORMAT).valueToString(new Date()));
                result.println("--------------------------------------------------");

                result.println();
                result.println("BEGIN;");
                result.println();

                result.println("COPY kern.bsnvooraad (bsn, gemeentecode, gemeentenaam, ambtenaar) FROM stdin;");

                for (int i = 0; i < 100; i++) {
                    result.println(BSNGenerator.nextBSN() + "\t" + gemeente.code + "\t" + gemeente.naam + "\t" + ambtenaarCount);
                }
                result.println("\\.");

                result.println();
                result.println("COMMIT;");
                result.println();
            }
        }

        result.println("--- END BSN's");
    }

    private void extraANummers(final PrintWriter result) throws ParseException {

        result.println("--- BEGIN ANR's");
        result.println();
        result.println("BEGIN;");

        result.println("DROP TABLE IF EXISTS Kern.anrvooraad CASCADE;");
        result.println();
        result.println("CREATE TABLE Kern.anrvooraad (");
        result.println("	anr bigint CONSTRAINT anrvooraad_pk PRIMARY KEY");
        result.println("	,gemeentecode int NOT NULL");
        result.println("	,gemeentenaam varchar(255) NOT NULL");
        result.println("	,ambtenaar int NOT NULL");
        result.println("	);");
        result.println("COMMIT;");
        result.println();

        log.info("Extra Anr's");

        for (final Gemeente gemeente : Generator.gemeenten) {

            log.debug("Anr's for " + gemeente.naam);

            for (int ambtenaarCount = 0; ambtenaarCount < constants.getAmbtenaarCount(); ambtenaarCount++) {

                result.println("--------------------------------------------------");
                result.println("--- ANR's");
                result.println("--- Gemeente: " + gemeente.naam);
                result.println("--- Ambtenaar: " + ambtenaarCount);
                result.println("--- Generated at: " + new DateFormatter(WhiteBoxFiller.DATEFORMAT).valueToString(new Date()));
                result.println("--------------------------------------------------");

                result.println();
                result.println("BEGIN;");
                result.println();

                result.println("COPY kern.anrvooraad (anr, gemeentecode, gemeentenaam, ambtenaar) FROM stdin;");

                for (int i = 0; i < 100; i++) {
                    result.println(AnrGenerator.getNextAnr() + "\t" + gemeente.code + "\t" + gemeente.naam + "\t" + ambtenaarCount);
                }
                result.println("\\.");

                result.println();
                result.println("COMMIT;");
                result.println();

            }
        }

        result.println("--- END ANR's");
    }

    private void addClassPathFile(final String string, final PrintWriter printWriter) throws IOException, ParseException {
        log.info("Adding '" + string + "'");
        printWriter.println("--------------------------------------------------");
        printWriter.println("--- Included: " + string);
        printWriter.println("--------------------------------------------------");
        printWriter.println();
        printWriter.flush();
        IOUtils.copy(this.getClass().getResourceAsStream(string), printWriter, "UTF-8");
    }

}
