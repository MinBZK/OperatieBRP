/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class POIUtil {

    public static Integer getColumnIndex(final Sheet testdataSheet, final String columnName) {
        return getColumnIndex(testdataSheet, columnName, false);
    }

    /**
     * Bepaal de kolom index van de gegeven kolom naam.
     *
     * @param testdataSheet
     * @param columnName
     * @param looseMatch is gelijk, begint met of eindigt met
     * @return
     */
    public static Integer getColumnIndex(final Sheet testdataSheet, final String columnName, final boolean looseMatch) {
        Integer columnIndex = null;
        Row firstRow = testdataSheet.getRow(0);
        for (Iterator<Cell> cellIterator = firstRow.cellIterator(); cellIterator.hasNext();) {
            Cell cell = cellIterator.next();
            if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
                if (looseMatch &&
                        (cell.getStringCellValue().trim().toLowerCase().endsWith(columnName.toLowerCase())
                                || cell.getStringCellValue().trim().toLowerCase().startsWith(columnName.toLowerCase()))) {
                    columnIndex = cell.getColumnIndex();
                    break;
                } else if (!looseMatch && cell.getStringCellValue().trim().toLowerCase().equals(columnName.toLowerCase())) {
                    columnIndex = cell.getColumnIndex();
                    break;
                }
            }
        }
        return columnIndex;
    }

}
