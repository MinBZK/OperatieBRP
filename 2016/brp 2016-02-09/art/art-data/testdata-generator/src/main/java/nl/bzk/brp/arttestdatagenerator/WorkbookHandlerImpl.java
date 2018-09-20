/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.arttestdatagenerator;

import java.io.PrintWriter;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * .
 *
 */
public class WorkbookHandlerImpl implements WorkbookHandler {
    /** . */
    private static final Logger LOG = LoggerFactory.getLogger(WorkbookHandlerImpl.class);

    private final Workbook workbook;

    /**
     * .
     * @param workbook .
     */
    public WorkbookHandlerImpl(final Workbook workbook) {
        this.workbook = workbook;
    }
    /**
     * .
     * @param sheet .
     * @return .
     */
    private boolean isSheetLeeg(final Sheet sheet) {
        boolean retval = true;
        int numRows = sheet.getRows();
        if (numRows != 0) {
            Cell cell = null;
            for (int i = 0; i < numRows; i++) {
                cell = sheet.getCell(i, 0);
                if (cell != null && StringUtils.isNotBlank(cell.getContents())) {
                    retval = false;
                    break;
                }
            }
        }
        return retval;
    }

    /**
     * .
     * @param sheet .
     * @return .
     */
    private String getTypeSheet(final Sheet sheet) {
        return sheet.getCell(0, 0).getContents().trim().toLowerCase();
    }

    @Override
    public void print(final PrintWriter printWriter) {
        int sheetNum =  workbook.getNumberOfSheets();
        for (int i = 0; i < sheetNum; i++) {
            int countRowsProcess = 0;
            Sheet sheet = workbook.getSheet(i);
            String sheetName = sheet.getName();

            // test welk type sheet deze afgehandeld moet worden.
            // ondersteund zijn de volgende type ['-end-', '-referentiedata-', '-skip-', '-tabel-']
            if (isSheetLeeg(sheet)) {
                printWriter.println("--------------------------------------------------");
                printWriter.println(String.format("--- Sheet: (%d) (%s) is leeg", i, sheetName));
                printWriter.println("--------------------------------------------------");
            } else {
                String sheetType = getTypeSheet(sheet);
                SheetHandler handler = null;
                if (sheetType.equals("-end-")) {
                    handler = new SheetHandlerImplTimestamp(sheet);
                    countRowsProcess = handler.print(printWriter);
                } else if (sheetType.equals("-skip-")) {
                    printWriter.println("--------------------------------------------------");
                    printWriter.println(String.format("--- Sheet: (%d) (%s) wordt overgeslagen", i, sheetName));
                    printWriter.println("--------------------------------------------------");
                } else if (sheetType.equals("-referentiedata-")) {
                    handler = new SheetHandlerImplReferentieInsert(sheet);
                    countRowsProcess = handler.print(printWriter);
                } else if (sheetType.equals("tabelnaam")) {
                    handler = new SheetHandlerImplSimpleInsert(sheet);
//                    handler = new SheetHandlerImplSimpleCsv(sheet);
                    countRowsProcess = handler.print(printWriter);
                } else if (sheetType.equals("-tabel-")) {
                    handler = new SheetHandlerImplSimpleInsert(sheet);
//                    handler = new SheetHandlerImplSimpleCsv(sheet);
                    countRowsProcess = handler.print(printWriter);
                } else {
                    handler = new SheetHandlerImplSimpleInsert(sheet);
//                    handler = new SheetHandlerImplSimpleCsv(sheet);
                    countRowsProcess = handler.print(printWriter);
                }
                // run the handler.
            }
            LOG.debug(String.format("Handled sheet (%d) '%s' (%d) records", i, sheetName, countRowsProcess));
        }

    }

}
