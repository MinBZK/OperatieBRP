/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.arttestdatagenerator;

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

    private static final String SPATIE_CODE = "[spatie]";

    /** . */
    protected static final  String QUOTE = "'";
    protected static final  String KOMMA = ",";

    protected static final  String DOUBLE_QUOTE = "\"";
    protected static final  String SEMI_COLON = ";";
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
     * @param delimiter .
     * @return .
     */
    protected String concatenateStrings(final List<String> list, final String separator, final String delimiter) {
        return concatenateStrings(list, separator, delimiter, "null", false);
    }

    /**
     * .
     * @param list .
     * @param separator .
     * @param delimiter .
     * @param nullValue .
     * @return .
     */
    protected String concatenateStrings(final List<String> list, final String separator, final String delimiter,
            final String nullValue)
    {
        return concatenateStrings(list, separator, delimiter, nullValue, false);
    }

    /**
     * .
     * @param list .
     * @param separator .
     * @param delimiter .
     * @param nullValue .
     * @param delimiterOnlyIfNeeded .
     * @return .
     */
    protected String concatenateStrings(final List<String> list, final String separator, final String delimiter,
            final String nullValue, final boolean delimiterOnlyIfNeeded)
    {
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (String value : list) {
            if (first) {
                first = false;
            } else {
                sb.append(separator);
            }
            if (StringUtils.isNotBlank(value)) {
                String sValue = value;
                // vervang alle '[spatie]' codes
                if (value.equals(SPATIE_CODE)) {
                    sValue = " ";
                }
                if (delimiter.equals(DOUBLE_QUOTE)) {
                    sValue = StringUtils.replace(sValue, delimiter, delimiter+delimiter);
                }
                // als delimiterOnlyIfNeeded, zet de delimeter pas aan als de string een van deze characters bevat.
                // ''', '"', ',' ';'
                if (delimiterOnlyIfNeeded) {
                    if (StringUtils.indexOfAny(sValue, new char[] {',', ';', '\'',  '\"'}) >= 0) {
                        sb.append(delimiter).append(sValue).append(delimiter);
                        // TODO, escapen van \' en \" ?? of dubbele waarden?
                        //System.out.println("[" + delimiter+"]["+sValue+"]["+delimiter+"]");
                    } else {
                        sb.append(sValue);
                    }
                } else {
                    // delimeter altijd aan.
                    sb.append(delimiter).append(sValue).append(delimiter);
                }
            } else {
                sb.append(nullValue);
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
