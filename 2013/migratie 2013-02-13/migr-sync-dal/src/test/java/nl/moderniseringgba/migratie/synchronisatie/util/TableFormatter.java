/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.util;

import java.util.Arrays;
import java.util.Comparator;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;

/*
 * CHECKSTYLE:OFF aangepaste kopie van DBUnit-class
 */
public class TableFormatter {

    public TableFormatter() {

    }

    /**
     * Formats a table with all data in a beautiful way. Can be useful to print out the table data on a console.
     * 
     * @param table
     *            The table to be formatted in a beautiful way
     * @return The table data as a formatted String
     * @throws DataSetException
     */
    public String format(final ITable table) throws DataSetException {
        final StringBuffer sb = new StringBuffer();
        final ITableMetaData tableMetaData = table.getTableMetaData();
        // Title line
        sb.append("******");
        sb.append(" table: ").append(tableMetaData.getTableName()).append(" ");
        sb.append("**");
        sb.append(" row count: ").append(table.getRowCount()).append(" ");
        sb.append("******");
        sb.append("\n");

        // Column headers
        final int width = 28;
        final Column[] cols = tableMetaData.getColumns();
        sortCols(cols);

        for (int i = 0; i < cols.length; i++) {
            sb.append(padRight(cols[i].getColumnName().toUpperCase(), width, ' '));
            sb.append("|");
        }
        sb.append("\n");

        // Separator
        for (int i = 0; i < cols.length; i++) {
            sb.append(padRight("", width, '='));
            sb.append("|");
        }
        sb.append("\n");

        // Values
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < cols.length; j++) {
                final Object value = table.getValue(i, cols[j].getColumnName());
                final String stringValue = String.valueOf(value);
                sb.append(padRight(stringValue, width, ' '));
                sb.append("|");
            }
            // New row
            sb.append("\n");
        }

        return sb.toString();
    }

    private Column[] sortCols(final Column[] cols) {
        Arrays.sort(cols, new Comparator<Column>() {
            @Override
            public int compare(final Column columnA, final Column columnB) {
                if (columnA.getColumnName().toLowerCase().equals("id")) {
                    return -1;
                } else if (columnB.getColumnName().toLowerCase().equals("id")) {
                    return 1;
                } else {
                    return columnA.getColumnName().compareTo(columnB.getColumnName());
                }
            }
        });
        return cols;
    }

    /**
     * Pads the given String with the given <code>padChar</code> up to the given <code>length</code>.
     * 
     * @param s
     * @param length
     * @param padChar
     * @return The padded string
     */
    public static final String padLeft(final String s, final int length, final char padChar) {
        String result = s;

        final char[] padCharArray = getPadCharArray(s, length, padChar);
        if (padCharArray != null) {
            result = pad(s, padCharArray, true);
        }

        return result;
    }

    /**
     * Pads the given String with the given <code>padChar</code> up to the given <code>length</code>.
     * 
     * @param s
     * @param length
     * @param padChar
     * @return The padded string
     */
    public static final String padRight(final String s, final int length, final char padChar) {
        String result = s;

        final char[] padCharArray = getPadCharArray(s, length, padChar);
        if (padCharArray != null) {
            result = pad(s, padCharArray, false);
        }

        return result;
    }

    private static final char[] getPadCharArray(final String s, final int length, final char padChar) {
        if (length > 0 && length > s.length()) {
            final int padCount = length - s.length();
            final char[] padArray = new char[padCount];
            for (int i = 0; i < padArray.length; i++) {
                padArray[i] = padChar;
            }
            return padArray;
        } else {
            return null;
        }
    }

    private static final String pad(final String s, final char[] padArray, final boolean padLeft) {
        final StringBuffer sb = new StringBuffer(s);
        if (padLeft) {
            sb.insert(0, padArray);
        } else {
            sb.append(padArray);
        }
        return sb.toString();
    }

}
