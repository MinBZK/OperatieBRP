/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.adapter.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

/**
 * De excel adapter transformeert een XLS bestand in een LO3 model.
 * 
 */
public final class ExcelAdapterImpl implements ExcelAdapter {

    private static final int HISTORIE_CATEGORIE_OFFSET = 50;

    private static final String AANDUIDING_VOOR_EEN_NIEUWE_CATEGORIE_ONTBREEKT_VERWACHT_OP_REGEL =
            "Aanduiding voor een nieuwe categorie ontbreekt, verwacht op regel: ";

    private static final String LEADING_ZERO = "0";

    /**
     * tweede kolom bevat element nummer (0-based).
     */
    private static final int ELEM_INDEX = 1;
    /**
     * derde kolom bevat element waarde (0-based).
     */
    private static final int VALUE_INDEX = 2;
    /**
     * De maximale lengte van het categorienummer.
     */
    private static final int CATEGORIE_LENGTE = 2;
    /**
     * De maximale lengte van het elementnummer.
     */
    private static final int ELEMENT_LENGTE = 4;

    @Override
    public List<ExcelData> leesExcelBestand(final InputStream excelBestand) throws IOException, ExcelAdapterException {
        final HSSFWorkbook workbook = new HSSFWorkbook(excelBestand);
        final List<ExcelData> result = new ArrayList<ExcelData>();
        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
            final HSSFSheet sheet = workbook.getSheetAt(sheetIndex);

            int column = VALUE_INDEX;
            while (true) {
                final ExcelData berichtDto = converteerColumnNaarExcelData(sheet, column);

                if (berichtDto.isEmpty()) {
                    break;
                }
                result.add(berichtDto);
                column++;
            }
        }

        return result;
    }

    private static ExcelData converteerColumnNaarExcelData(final HSSFSheet sheet, final int column)
            throws ExcelAdapterException {
        // lijst met stapels
        final List<String> headerLijst = new ArrayList<String>();
        final List<Lo3CategorieWaarde> categorieLijst = new ArrayList<Lo3CategorieWaarde>();

        int rowIndex = 0;

        // parse all headers into headerLijst; headers are the first rows of a sheet that have no value in the second
        // column
        rowIndex = parseHeaders(sheet, column, headerLijst, rowIndex);

        // parse the rest into categorieLijst
        rowIndex = parseContent(sheet, column, categorieLijst, rowIndex);

        // convert the headers into an array of Strings
        String[] headerArray = new String[headerLijst.size()];
        headerArray = headerLijst.toArray(headerArray);

        // instantiate and initialize BerichtDto
        final ExcelData berichtDto = new ExcelData();
        berichtDto.setHeaders(headerArray);
        berichtDto.setCategorieWaarden(categorieLijst);

        return berichtDto;
    }

    private static int parseHeaders(
            final HSSFSheet sheet,
            final int column,
            final List<String> headerLijst,
            final int startRowIndex) {
        int rowIndex = startRowIndex;
        HSSFRow row = sheet.getRow(rowIndex);

        // parse all headers into headerLijst; headers are the first rows of a sheet that have no value in the second
        // column
        while (row != null
                && (row.getCell(ELEM_INDEX) == null || row.getCell(ELEM_INDEX).getCellType() == Cell.CELL_TYPE_BLANK)) {

            final String plCellWaarde = parseCellWaarde(row.getCell(column));

            // the first header must have value "00000000" (fixed value for 'Random key'); if this value does not exist
            // in the first row, the sheet is assumed to contain no headers at all
            if (headerLijst.size() == 0 && (plCellWaarde == null || !"00000000".equals(plCellWaarde))) {
                // start at row 3, i.e. rowIndex 2
                rowIndex = 2;
                row = sheet.getRow(rowIndex);

                break;
            }

            headerLijst.add(plCellWaarde);
            row = sheet.getRow(++rowIndex);
        }

        return rowIndex;
    }

    // CHECKSTYLE:OFF - Cyclomatic complexity - komt door de loops om categorieen te controleren. Opsplitsen maakt het
    // onduidelijk
    private static int parseContent(
    // CHECKSTYLE:ON
            final HSSFSheet sheet,
            final int column,
            final List<Lo3CategorieWaarde> categorieLijst,
            final int startRowIndex) throws ExcelAdapterException {
        int rowIndex = startRowIndex;
        HSSFRow row = sheet.getRow(rowIndex);
        Lo3CategorieWaarde categorieWaarde = null;

        int stapel = 0;
        int voorkomen = 0;

        // parse the contents into categorieLijst
        while (row != null) {
            final HSSFCell cell = row.getCell(ELEM_INDEX);
            if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                row = sheet.getRow(++rowIndex);
                // sla lege rij over, ga door met volgende rij
                continue;
            }

            String elemNr = parseElementNummer(cell);

            if (elemNr.length() <= CATEGORIE_LENGTE) {
                while (elemNr.length() < CATEGORIE_LENGTE) {
                    elemNr = LEADING_ZERO + elemNr;
                }

                if (categorieWaarde != null && !categorieWaarde.isEmpty()) {
                    categorieLijst.add(categorieWaarde);

                    if (Integer.valueOf(elemNr) < HISTORIE_CATEGORIE_OFFSET) {
                        // CHECKSTYLE:OFF
                        if (Integer.valueOf(elemNr).equals(
                        // CHECKSTYLE:ON
                                Integer.valueOf(categorieWaarde.getCategorie().getCategorie())
                                        % HISTORIE_CATEGORIE_OFFSET)) {
                            stapel = stapel + 1;
                            voorkomen = 0;
                        } else {
                            stapel = 0;
                            voorkomen = 0;
                        }
                    } else {
                        voorkomen = voorkomen + 1;
                    }
                }

                categorieWaarde =
                        new Lo3CategorieWaarde(Lo3CategorieEnum.valueOfCategorie(elemNr), stapel, voorkomen);
            } else {
                // Element regel
                if (categorieWaarde == null) {
                    throw new ExcelAdapterException(getColumnLetter(column),
                            AANDUIDING_VOOR_EEN_NIEUWE_CATEGORIE_ONTBREEKT_VERWACHT_OP_REGEL + (rowIndex + 1));
                }

                parseCategorieRij(column, categorieWaarde, row, elemNr);
            }

            row = sheet.getRow(++rowIndex);
        }

        if (categorieWaarde != null && !categorieWaarde.isEmpty()) {
            categorieLijst.add(categorieWaarde);
        }

        return rowIndex;
    }

    private static void parseCategorieRij(
            final int column,
            final Lo3CategorieWaarde huidigeCategorie,
            final HSSFRow row,
            final String elemNrParam) {
        String elemNr = elemNrParam;
        while (elemNr.length() < ELEMENT_LENGTE) {
            elemNr = LEADING_ZERO + elemNr;
        }
        final Lo3ElementEnum element = Lo3ElementEnum.getLO3Element(elemNr);
        final String waarde = parseCellWaarde(row.getCell(column));

        if (waarde != null && waarde.length() > 0) {
            huidigeCategorie.addElement(element, waarde.trim());
        }
    }

    private static String parseElementNummer(final HSSFCell cell) {
        String elemNr;
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            elemNr = "" + (int) cell.getNumericCellValue();
        } else {
            elemNr = cell.toString();
        }
        return elemNr;
    }

    private static String parseCellWaarde(final HSSFCell waardeCell) {
        String waarde;
        if (waardeCell == null) {
            waarde = null;
        } else if (waardeCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            waarde = "" + (long) waardeCell.getNumericCellValue();
        } else {
            waarde = waardeCell.toString();
        }
        return waarde;
    }

    /**
     * Geef op basis van het kolom nummer de bijbehorende letter terug.
     * 
     * @param column
     *            de om te zetten kolom
     * @return de kolom letter
     */
    private static String getColumnLetter(final int column) {
        return Character.toString((char) ('A' + column));
    }

}
