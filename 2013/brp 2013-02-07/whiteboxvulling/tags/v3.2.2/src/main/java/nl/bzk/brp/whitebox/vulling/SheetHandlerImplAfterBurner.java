/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.whitebox.vulling;

import java.io.PrintWriter;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class SheetHandlerImplAfterBurner extends AbstractSheetHandlerImpl {

    public SheetHandlerImplAfterBurner(final int sheetNum) {
	super(sheetNum);
    }

    @Override
    protected long printImpl(final Workbook workbook, final PrintWriter printWriter) {
	int count = 0;
	final Sheet sheet = workbook.getSheet(getSheetNum());

	for (count = 0; count < sheet.getRows(); count++) {
	    final Cell[] row = sheet.getRow(count);

	    if (row.length >= 2) {
		if ("commentaar".equalsIgnoreCase(row[0].getContents())) {
		    printWriter.println("--- " + row[1].getContents().replaceAll("\n", "\n-- \t") + "");
		}
		if ("SQL".equalsIgnoreCase(row[0].getContents())) {
		    printWriter.println(row[1].getContents() + ";");
		}
	    }
	}

	return count;
    }

}
