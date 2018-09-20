/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.arttestdatagenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    /** . */
    private static final int START_ROW_NR = 1;

    private Sheet sheet = null;

    private int count = 0;
    private final boolean createCsvBestanden = true;


    /**
     * .
     * @param parmSheet .
     */
    public SheetHandlerImplSimpleInsert(final Sheet parmSheet) {
        sheet = parmSheet;
    }


    @Override
    protected int printImpl(final PrintWriter result) {
        FileOutputStream fos = null;
        try {
            begin(result);

            for (int rowNum = START_ROW_NR; rowNum < sheet.getRows(); rowNum++) {

                // get all cells for the whole row. ASSUME: row[0] = tableName, row[1] = # columns.
                // row [2..n+1] = column names, row [n+2..2*n+1] = values
                final Cell[] row = sheet.getRow(rowNum);
                if (rowIsNotEmpty(row)) {
                    final String tableName = getTableNameInRow(row).trim().toLowerCase();
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
                            columnNamesRow.add(colName.toLowerCase().trim());
                        }
                        if (createCsvBestanden) {
                            fos = initialHeader(fos, tableName, columnNamesRow);
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
                        String sqlInsertStatement = handleRow(columnNamesRow, valueRow, tableName);
                        if (createCsvBestanden) {
                            handleRowCsv(columnNamesRow, valueRow, fos);
                        }
                        result.println(sqlInsertStatement);
                    }
                }
                count++;
            }
            commit(result);
        } catch (final Exception e) {
            LOG.error("error occured in sheet=" + sheet.getName() + ", rows =" + count + ":" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (null != fos) {
                try {
                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                    ;
                }
            }

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
     * @param fos .
     * @return .
     */

    private String handleRowCsv(final List<String> colomNames, final List<String> values, final FileOutputStream fos)
    {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("")
                .append(concatenateStrings(values, KOMMA, DOUBLE_QUOTE, "", true))
                .append("\n")
                .toString();
            fos.write(sb.toString().getBytes());
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * .
     * @param fos .
     * @param tableName .
     * @param columnNamesRow .
     * @return .
     * @throws IOException .
     */
    private FileOutputStream initialHeader(final FileOutputStream fos, final String tableName,
            final List<String> columnNamesRow) throws IOException
    {
        if (null != fos) {
            return fos;
        } else {
            File outputDir = new File(FileUtils.outputDir, "csv");
            outputDir.mkdirs();
            File f = new File(outputDir.getPath(), tableName.trim().toLowerCase() + ".csv");
            boolean printHeader = true;

            // start a new file (truncated) ==> hou rekening met je excel sheet.
            if (f.exists() && f.isFile() && f.length() > 0) {
                printHeader = false;
            }
            FileOutputStream fnew = new FileOutputStream(f, true);
            if (printHeader) {
                fnew.write(new StringBuffer()
                    .append(concatenateStrings(columnNamesRow, KOMMA, ""))
                    .append("\n")
                    .toString().getBytes());
            }
            return fnew;
        }
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
