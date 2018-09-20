/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.whitebox.vulling;

import java.io.PrintWriter;

import jxl.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSheetHandlerImpl implements SheetHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final int sheetNum;

    public AbstractSheetHandlerImpl(final int sheetNum) {
	this.sheetNum = sheetNum;
    }

    public int getSheetNum() {
	return sheetNum;
    }

    @Override
    public void print(final Workbook workbook, final PrintWriter printWriter) {

	printWriter.println("--------------------------------------------------");
	printWriter.println("--- START");
	printWriter.println("--- Sheet: " + sheetNum + " '" + workbook.getSheet(sheetNum).getName() + "'");
	printWriter.println("--- Handler: " + this.getClass().getSimpleName());
	printWriter.println("--------------------------------------------------");

	printWriter.println();

	log.debug("Handling sheet " + sheetNum + " '" + workbook.getSheet(sheetNum).getName() + "'");

	final long count = printImpl(workbook, printWriter);

	printWriter.println();

	printWriter.println("--------------------------------------------------");
	printWriter.println("--- EIND");
	printWriter.println("--- Sheet: " + sheetNum + " '" + workbook.getSheet(sheetNum).getName() + "'");
	printWriter.println("--- Handler: " + this.getClass().getSimpleName());
	printWriter.println("--- Aantal: " + count);
	printWriter.println("--------------------------------------------------");

	//printWriter.flush();

	log.debug("Handled sheet " + sheetNum + " '" + workbook.getSheet(sheetNum).getName() + "', " + count + " records");
    }

    protected abstract long printImpl(Workbook workbook, PrintWriter printWriter);

}
