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
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SheetHandlerImplSimpleInsert extends AbstractSheetHandlerImpl {

    private final static String QUOTE = "'";

    private final static Logger log = LoggerFactory
            .getLogger(SheetHandlerImplSimpleInsert.class);

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
            result.println("--- Sheet: " + sheet.getName());
            result.println("--------------------------------------------------");

            result.println();
            result.println("begin;");
            result.println();

            for (rowNum = START_ROW_NR; rowNum < sheet.getRows(); rowNum++) {

                final StringWriter stringWriter = new StringWriter();
                final PrintWriter printWriter = new PrintWriter(stringWriter);

                // log.debug("Sheet " + getSheetNum() +
                // " "+sheet.getName()+", row " + rowNum);

                // get all cells for the whole row. ASSUME: row[0] = tableName, row[1] = # columns.
                // row [2..n+1] = column names, row [n+2..2*n+1] = values
                final Cell[] row = sheet.getRow(rowNum);
                if (row.length > 0 && row[0].getContents().trim().length() > 0) {
                    final String tableName = row[TABLE_NAME_COL_NR].getContents();
                    if (StringUtils.isBlank(tableName) || "0".equals(tableName)) {
                        log.error("Tabelnaam in sheet " + sheet.getName() + " is incorrect [" + (rowNum + 1) + ",1]=" +tableName);
                    }
                    final int colCount = Integer.valueOf(row[AANTAL_KOLOMMEN_COL_NR].getContents());

                    final List<String> columnNamesRow = new ArrayList<String>(colCount);
                    final List<String> valueRow = new ArrayList<String>(colCount);

                    for (int i = 0; i < colCount; i++) {
                        String colName = row[i + 2].getContents();
                        if (StringUtils.isBlank(colName) || "0".equals(colName)) {
                            log.error("Kolomnaam in sheet " + sheet.getName() + " is incorrect ["
                                   + (rowNum + 1) + "," + (i + 2) + "]=" + colName);
                        }
                        columnNamesRow.add(colName);
                    }
                    for (int i = 0; i < colCount; i++) {
                        // hou rekening mee dat excel de laatste cellen in de row, als deze leeg, is niet mee neemt.
                        if (i + colCount + 2 < row.length) {
                            valueRow.add(row[i + colCount + 2].getContents());
                        } else {
                            valueRow.add(null);
                        }
                    }
                    if (columnNamesRow.size() != valueRow.size()) {
                        log.error("Aantal kolommen (" + columnNamesRow.size() + ") is anders dan aantal waarden ("
                            + valueRow.size() + ") in rij " + (rowNum + 1));
                    }
                    handleRow(printWriter, columnNamesRow, valueRow, tableName);
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
            log.error("Sheet=" + sheet.getName() + ", row=" + rowNum + ":"
                    + e.getMessage());
            throw new RuntimeException(e);
        }

        return _count;
    }

//    private static String getColName(final List<String> row, final int colNum) {
//        return row.get(colNum + 2);
//    }
//
//    private static String getColValue(final List<String> row, final int colNum,
//            final int colCount) {
//        return (row.size() <= colNum + 2 + colCount) ? null : row.get(colNum
//                + 2 + colCount);
//    }
//
//    // TODO: not used ???
//    private static void setColValue(final List<String> row, final int colNum,
//            final int colCount, final String value) {
//        row.set(colNum + 2 + colCount, value);
//    }

    private void handleRow(final PrintWriter printWriter, final List<String> row, final List<String> values,
        final String tableName)
    {

        printWriter.print("INSERT INTO " + tableName + " (");

        boolean first = true;
        for (String kolomName : row) {
            if (first) {
                first = false;
            } else {
                printWriter.print(',');
            }
            printWriter.print(kolomName);
        }

        printWriter.print(") VALUES(");

        first = true;
        for (String value : values) {
            if (first) {
                first = false;
            } else {
                printWriter.print(',');
            }
            if (StringUtils.isNotBlank(value)) {
                printWriter.print(QUOTE + value + QUOTE);
            } else {
                printWriter.print("null");
            }
        }
        printWriter.print(");");
    }

}
