/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.sierratestdatagenerator;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SheetHandlerImplSimpleInsert extends AbstractSheetHandlerImpl {

    private final static String QUOTE = "'";

    private final static Logger log = LoggerFactory.getLogger(SheetHandlerImplSimpleInsert.class);

    public SheetHandlerImplSimpleInsert(final int sheetNum) {
	super(sheetNum);
    }

    private final int START_ROW_NR = 1;
    private final int TABLE_NAME_COL_NR = 0;
    private final int AANTAL_KOLOMMEN_COL_NR = 1;

    @Override
    protected long printImpl(final Workbook workbook, final PrintWriter result) {

	long _count = 0;

	final Sheet sheet = workbook.getSheet(getSheetNum());

	int rowNum = 0;
	try {

	    // log.debug("Generating sheet '"+sheet.getName()+"' voor gemeente '"
	    // + gemeente.naam + "' , ambtenaar=" + ambtenaarCount + "/"
	    // + Generator.AMBTENAAR_COUNT);

	    result.println("--------------------------------------------------");
	    result.println("--- Sheet: " + getSheetNum());
	    result.println("--------------------------------------------------");

	    result.println();
	    result.println("begin;");
	    result.println();

	    for (rowNum = START_ROW_NR; rowNum < sheet.getRows(); rowNum++) {

		final StringWriter stringWriter = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(stringWriter);

		// log.debug("Sheet " + getSheetNum() +
		// " "+sheet.getName()+", row " + rowNum);

		final Cell[] row = sheet.getRow(rowNum);

		if (row.length > 0 && row[0].getContents().trim().length() > 0) {
		    final String tableName = row[TABLE_NAME_COL_NR].getContents();

		    final int colCount = Integer.valueOf(row[AANTAL_KOLOMMEN_COL_NR].getContents());

		    final List<String> stringRow = new ArrayList<String>(colCount);

		    for (int i = 0; i < row.length; i++) {
			stringRow.add(row[i].getContents());
		    }

		    handleRow(printWriter, stringRow, tableName, colCount, rowNum);

		}

		printWriter.flush();
		stringWriter.flush();

		final String line = stringWriter.toString();
		result.println(line);
		_count++;
	    }

	    result.println();
	    result.println("commit;");
	    result.println();

	} catch (final Exception e) {
	    log.error("Sheet=" + sheet.getName() + ", row=" + rowNum + ":" + e.getMessage());
	    throw new RuntimeException(e);
	}

	return _count;
    }

    private static String getColName(final List<String> row, final int colNum) {
	return row.get(colNum + 2);
    }

    private static String getColValue(final List<String> row, final int colNum, final int colCount) {
        return (row.size() <= colNum + 2 + colCount) ? null : row.get(colNum + 2 + colCount);
    }

    //TODO:  not used ???
    private static void setColValue(final List<String> row, final int colNum, final int colCount, final String value) {
	row.set(colNum + 2 + colCount, value);
    }

    private void handleRow(final PrintWriter printWriter, final List<String> row, final String tableName, final int colCount, final int rowNumber) {

	printWriter.print("INSERT INTO " + tableName + " (");

	boolean first = true;
	for (int colNum = 0; colNum < colCount; colNum++) {
	    if (first) {
		first = false;
	    } else {
		printWriter.print(',');
	    }
	    printWriter.print(getColName(row, colNum));
	}

	printWriter.print(") VALUES(");

	first = true;
	for (int colNum = 0; colNum < colCount; colNum++) {
	    if (first) {
		first = false;
	    } else {
		printWriter.print(',');
	    }
	    if (getColValue(row, colNum, colCount) != null && getColValue(row, colNum, colCount).trim().length() > 0) {
		printWriter.print(QUOTE + getColValue(row, colNum, colCount) + QUOTE);
	    } else {
		printWriter.print("null");
	    }
	}
	printWriter.print(");");
    }

}
