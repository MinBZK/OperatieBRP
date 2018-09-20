/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.sierratestdatagenerator;

import java.io.PrintWriter;
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
public abstract class AbstractSheetHandlerImpl implements SheetHandler {
    /** . */
    private static final Logger LOG = LoggerFactory.getLogger(AbstractSheetHandlerImpl.class);
    /** . */
    private static final int TABLE_NAME_COL_NR = 0;
    /** . */
    private static final int AANTAL_KOLOMMEN_COL_NR = 1;

    /**
     * .
     * @param row .
     * @return .
     */
    protected boolean rowIsNotEmpty(final Cell[] row) {
        return (row.length > 0 && row[0].getContents().trim().length() > 0);
    }

    /**
     * .
     * @param row .
     * @return .
     */
    protected String getTableNameInRow(final Cell[] row) {
        return (row.length > TABLE_NAME_COL_NR && row[TABLE_NAME_COL_NR].getContents().trim().length() > 0)
            ? row[TABLE_NAME_COL_NR].getContents().trim() : null;
    }

    /**
     * .
     * @param row .
     * @return .
     */
    protected Integer getColCountInRow(final Cell[] row) {
        if (row.length > AANTAL_KOLOMMEN_COL_NR && row[AANTAL_KOLOMMEN_COL_NR].getContents().trim().length() > 0) {
            try {
                Integer colCount = Integer.valueOf(row[AANTAL_KOLOMMEN_COL_NR].getContents().trim());
                return colCount;
            } catch (NumberFormatException e) {
                LOG.error(String.format("Ongeldige number [%s]", row[AANTAL_KOLOMMEN_COL_NR].getContents()), e);
            }
        }
        return null;
    }
    /**
     * .
     * @param list .
     * @param separator .
     * @return .
     */
    protected String concatenateStrings(final List<String> list, final String separator) {
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (String value : list) {
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }
            if (StringUtils.isNotBlank(value)) {
                sb.append(separator).append(value).append(separator);
            } else {
                sb.append("null");
            }
        }
        return sb.toString();
    }

    /**
     * .
     * @return .
     */
    protected abstract Sheet getSheet();

    /**
     * .
     * @return .
     */
    protected abstract String getHandlerName();

    /**
     * .
     * @return .
     */
    protected abstract int getCount();

    /**
     * .
     * @param printWriter .
     * @return .
     */
    protected abstract int printImpl(final PrintWriter printWriter);

    /**
     * .
     * @param printWriter .
     */
    protected void begin(final PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("begin;");
        printWriter.println();
    }

    /**
     * .
     * @param printWriter .
     */
    protected void commit(final PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("commit;");
        printWriter.println();
    }

    /**
     * .
     * @param printWriter .
     */
    protected void printHeader(final PrintWriter printWriter) {
        printWriter.println("--------------------------------------------------");
        printWriter.println("--- START");
        printWriter.println(String.format("--- Sheet: (%s)", getSheet().getName()));
        printWriter.println("--- Handler: " + this.getClass().getSimpleName());
        printWriter.println("--------------------------------------------------");
    }


    /**
     * .
     * @param printWriter .
     */
    protected void printFooter(final PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("--------------------------------------------------");
        printWriter.println("--- EIND");
        printWriter.println(String.format("--- Sheet: (%s)", getSheet().getName()));
        printWriter.println("--- Handler: " + this.getClass().getSimpleName());
        printWriter.println(String.format("--- Aantal: (%d)", getCount()));
        printWriter.println("--------------------------------------------------");
    }

    @Override
    public int print(final PrintWriter printWriter) {
        int numRecords = 0;
        printHeader(printWriter);
        numRecords = printImpl(printWriter);
        printFooter(printWriter);
        return numRecords;
    }

}
