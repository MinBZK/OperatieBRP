/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.arttestdatagenerator;

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
public class SheetHandlerImplReferentieInsert extends AbstractSheetHandlerImpl {
    /** . */
    private static final Logger LOG = LoggerFactory.getLogger(SheetHandlerImplReferentieInsert.class);

    /** . */
    private static final int START_ROW_NR = 1;
    private Sheet sheet = null;

    private int count = 0;

    /**
     * .
     * @param parmSheet .
     */
    public SheetHandlerImplReferentieInsert(final Sheet parmSheet) {
        sheet = parmSheet;
    }


    @Override
    protected int printImpl(final PrintWriter result) {
        try {
            begin(result);
            List<String> insertStatements = new ArrayList<String>();
            List<String> deleteStatements = new ArrayList<String>();

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
                    final int colCount = getColCountInRow(row);
                    final List<String> columnNamesRow = new ArrayList<String>(colCount);
                    final List<String> valueRow = new ArrayList<String>(colCount);

                    for (int i = 0; i < colCount; i++) {
                        String colName = row[i + 2].getContents();
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
                    deleteStatements.add(0, deleteStatement(columnNamesRow, valueRow, tableName));
                    insertStatements.add(insertStatement(columnNamesRow, valueRow, tableName));
                }
                count++;
            }
            for (String st : deleteStatements) {
                result.println(st);
            }
            for (String st : insertStatements) {
                result.println(st);
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
    private String insertStatement(final List<String> colomNames, final List<String> values, final String tableName)
    {
        StringBuffer sb = new StringBuffer();
        return sb.append("INSERT INTO ")
            .append(tableName)
            .append(" (")
            .append(concatenateStrings(colomNames, KOMMA, ""))
            .append(") VALUES(")
            .append(concatenateStrings(values, KOMMA, QUOTE))
            .append(");")
            .toString();
    }

    /***
     * .
     * @param colomNames .
     * @param values .
     * @param tableName .
     * @return .
     */
    private String deleteStatement(final List<String> colomNames, final List<String> values, final String tableName)
    {
        StringBuffer sb = new StringBuffer();
        // zoek de primary key id en delete deze record eerst.
        for (int i = 0; i < colomNames.size(); i++) {
            if (colomNames.get(i).toLowerCase().trim().equals("id")) {
                sb.append("DELETE from ").append(tableName).append(" WHERE ID=").append(values.get(i)).append(";");
                break;
            }
        }
        return sb.toString();
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
