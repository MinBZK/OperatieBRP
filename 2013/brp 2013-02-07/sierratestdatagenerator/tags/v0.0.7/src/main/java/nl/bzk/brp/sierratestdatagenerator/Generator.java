/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.sierratestdatagenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.text.DateFormatter;

import jxl.Workbook;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Generator {

    private final Logger log = LoggerFactory.getLogger(getClass());

    Workbook workbook;

    private final String xlsFilename;

    public Generator(final String xlsFilename) {
        this.xlsFilename = xlsFilename;
    }

    private static List<SheetHandler> sheetHandlers = new ArrayList<SheetHandler>();

    static {
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(0)); // persoon
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(2)); // brpactie
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(3)); // persoon-ident
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(4)); // his_persgeslachtsaand
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(5)); // persgeslnaamcomp
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(6)); // his_persgeslnaamcomp
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(7)); // his_persaanschr
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(8)); // his_perssamengesteldenaam
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(9)); // his_geboorte
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(10)); // his_overleiden
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(12)); // persadres
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(13)); // his_persadres
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(16)); // his_bijhgem
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(18)); // relatie
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(19)); // his_relatie
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(20)); // betrokkenheid
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(21)); // his_betrouderlijkgezag
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(22)); // his_ouderschap
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(24)); // persindicatie
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(25)); // his_persindicatie
        sheetHandlers.add(new SheetHandlerImplSimpleInsert(26)); // his_persopschorting
        sheetHandlers.add(new SheetHandlerImplTimestamp(27));    // -end- speciale versie.
    }

    public boolean generate(final String outputName) {

	log.info("Reading '" + xlsFilename + "'");
	log.info("Writing '" + outputName + "'");

	// Writer writer = null;
	Writer writer = null;
	PrintWriter printWriter = null;

	try {

	    // writer = new FileOutputStream(file, false);
	    // writer = new FileWriter(outputName);

	    writer = new OutputStreamWriter(new FileOutputStream(outputName), "UTF-8");

	    // bufferedWriter = new BufferedWriter(writer, 1024*1024*4);
	    printWriter = new PrintWriter(writer, true);

	    printWriter.println("--- Start " + " " + SierraTestdataGenerator.getFullVersion() + " @ " + new DateFormatter(SierraTestdataGenerator.datFormat).valueToString(new Date()));

	    printWriter.println();
	    printWriter.println("---  #####   ###  #######  ######   ######      #");
	    printWriter.println("--- #     #   #   #        #     #  #     #    # #");
	    printWriter.println("--- #         #   #        #     #  #     #   #   #");
	    printWriter.println("---  #####    #   #####    ######   ######   #     #");
	    printWriter.println("---       #   #   #        #   #    #   #    #######");
	    printWriter.println("--- #     #   #   #        #    #   #    #   #     #");
	    printWriter.println("---  #####   ###  #######  #     #  #     #  #     #");
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
	    printWriter.println("--- Version:    " + SierraTestdataGenerator.getFullVersion());
	    printWriter.println("--- Timestamp:  " + new DateFormatter(SierraTestdataGenerator.datFormat).valueToString(new Date()));
	    printWriter.println("--- Created by: " + System.getProperty("user.name"));
	    printWriter.println("--- XLS file:   " + xlsFilename);
	    printWriter.println("--------------------------------------------------");
	    printWriter.println();

	    final List<String> sqlFiles = Arrays.asList(new String[] {
// BOLIE: NOOIT, maar dan NOOIT je database aanmaken (!), je moet de versie gebruiken die bij de server hoort.
//		"db/brp.sql",
//		"db/stamgegevensLand.sql",
//		"db/stamgegevensNationaliteit.sql",
//		"db/stamgegevensPlaats.sql",
//		"db/stamgegevensStatisch.sql",
//		"db/stamgegevensPartijGemeente.sql",
//		"db/stamgegevensVerkrijgingVerliesNLNationaliteit.sql",
//		"db/stamgegevensUpdateSoortBericht.sql",
//      "db/brp-cascade-delete.sql",
        "db/scenario_data_verwijderaar.sql",
	    "testtables.sql",
        "foreburner.sql"
		});

	    for (final String sqlFile : sqlFiles) {
	        addClassPathFile(sqlFile, printWriter);
	    }

	    workbook = Workbook.getWorkbook(new File(xlsFilename));

	    printWriter.println("--------------------------------------------------");
	    printWriter.println("--- START Generated from: " + xlsFilename);
	    printWriter.println("--------------------------------------------------");
	    printWriter.println();

	    for (final SheetHandler sheetHandler : sheetHandlers) {
            sheetHandler.print(workbook, printWriter);
            printWriter.flush();
            writer.flush();
	    }

	    printWriter.println("--------------------------------------------------");
	    printWriter.println("--- END Generated from: " + xlsFilename);
	    printWriter.println("--------------------------------------------------");

	    addClassPathFile("afterburner.sql", printWriter);

	    // bolie: ook de certificaten worden NIET meer geimporteerd, het is een onderdeel van de 'statische' data.
	    // addClassPathFile("db/autmiddel_en_certificaten.sql", printWriter);

	    // bolie: ook indexen moeten we niet meer aanmaken (tenzij we ze eerst diablen en na de insert enablen).
	    //addClassPathFile("db/brp-indexen.sql", printWriter);

	    printWriter.println();
	    printWriter.println("--- END " + " " + SierraTestdataGenerator.getFullVersion() + " @ " + new DateFormatter(SierraTestdataGenerator.datFormat).valueToString(new Date()));

	    return true;

	} catch (final Exception e) {
	    log.error("", e);
	    if (printWriter != null) {
		printWriter.println("An error occurred, dumping exception:");
	    }
	    e.printStackTrace(printWriter);
	} finally {
	    try {
		if (printWriter != null && writer != null /*
							   * && bufferedWriter
							   * != null
							   */) {

		    // bufferedWriter.flush();
		    printWriter.flush();
		    writer.flush();

		    // bufferedWriter.close();
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

    private void addClassPathFile(final String string, final PrintWriter printWriter) throws IOException, ParseException {
	log.info("Adding '" + string + "'");
	printWriter.println("--------------------------------------------------");
	printWriter.println("--- Included: " + string);
	printWriter.println("--------------------------------------------------");
	printWriter.println();
	printWriter.flush();
	IOUtils.copy(this.getClass().getClassLoader().getResourceAsStream(string), printWriter, "UTF-8");
    }

}
