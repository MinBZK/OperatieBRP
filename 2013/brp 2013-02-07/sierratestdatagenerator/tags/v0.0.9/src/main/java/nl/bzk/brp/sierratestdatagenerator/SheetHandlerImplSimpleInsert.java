/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.sierratestdatagenerator;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * .
 *
 */
public class SheetHandlerImplSimpleInsert extends AbstractSheetHandlerImpl {
    /** . */
    private static final Logger LOG = LoggerFactory.getLogger(SheetHandlerImplSimpleInsert.class);

    /** . */
    private static final  String QUOTE = "'";
    /** . */
    private static final int START_ROW_NR = 1;
    private Sheet sheet = null;

    private int count = 0;

    /**
     * .
     * @param parmSheet .
     */
    public SheetHandlerImplSimpleInsert(final Sheet parmSheet) {
        sheet = parmSheet;
    }


    @Override
    protected int printImpl(final PrintWriter result) {
        try {
            begin(result);

            for (int rowNum = START_ROW_NR; rowNum < sheet.getRows(); rowNum++) {

                // get all cells for the whole row. ASSUME: row[0] = tableName, row[1] = # columns.
                // row [2..n+1] = column names, row [n+2..2*n+1] = values
                final Cell[] row = sheet.getRow(rowNum);
                if (rowIsNotEmpty(row)) {
                    final String tableName = getTableNameInRow(row);
                    if (StringUtils.isBlank(tableName) || "0".equals(tableName)) {
                        LOG.error("Tabelnaam in sheet " + sheet.getName() + " is incorrect [" + (rowNum + 1) + ",1]="
                            + tableName);
                    }
                    final Integer colCount = getColCountInRow(row);
                    if (colCount == null || colCount == 0) {
                        LOG.error("Sheet " + sheet.getName() + " heeft geen colCount in [" + (rowNum + 1) + ",2]");
                    } else {
                        final List<String> columnNamesRow = new ArrayList<String>(colCount);
                        final List<String> valueRow = new ArrayList<String>(colCount);

                        for (int i = 0; i < colCount; i++) {
                            String colName = (i + 2 >= row.length) ? null : row[i + 2].getContents();
                            if (StringUtils.isBlank(colName) || "0".equals(colName)) {
                                LOG.error("Kolomnaam in sheet " + sheet.getName() + " is incorrect ["
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
                            LOG.error("Aantal kolommen (" + columnNamesRow.size() + ") is anders dan aantal waarden ("
                                + valueRow.size() + ") in rij " + (rowNum + 1));
                        }
                        for (String str : valueRow) {
                            if (StringUtils.isNotBlank(str) && str.startsWith("Eleonora")) {
                                byte[] bytes = str.getBytes();
                                for (int i=0; i < bytes.length; i++) {
                                    System.out.print((int)bytes[i] + " ");
                                }
                                System.out.println("|<---");
                            }
                        }
                        result.println(handleRow(columnNamesRow, valueRow, tableName));
                    }
                }
                count++;
            }
            commit(result);
        } catch (final Exception e) {
            LOG.error("error occured in sheet=" + sheet.getName() + ", rows =" + count + ":" + e.getMessage());
            throw new RuntimeException(e);
        }

        return count;
    }

    /***
     * .
     * @param colomNames .
     * @param values .
     * @param tableName .
     * @return .
     */
    private String handleRow(final List<String> colomNames, final List<String> values, final String tableName)
    {
        StringBuffer sb = new StringBuffer();
        return sb.append("INSERT INTO ")
            .append(tableName)
            .append(" (")
            .append(concatenateStrings(colomNames, ""))
            .append(") VALUES(")
            .append(concatenateStrings(values, QUOTE))
            .append(");")
            .toString();
    }

    @Override
    protected Sheet getSheet() {
        return sheet;
    }

    @Override
    protected String getHandlerName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected int getCount() {
        return count;
    }

}
