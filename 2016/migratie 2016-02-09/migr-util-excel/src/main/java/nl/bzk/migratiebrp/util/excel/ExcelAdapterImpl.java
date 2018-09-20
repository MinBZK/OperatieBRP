/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
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
    public List<ExcelData> leesExcelBestand(final InputStream excelBestand) throws ExcelAdapterException, Lo3SyntaxException {
        final HSSFWorkbook workbook;
        try {
            workbook = new HSSFWorkbook(excelBestand);
        } catch (final IOException e) {
            throw new ExcelAdapterException("Kan Excelbestand niet openen", e);
        }
        final List<ExcelData> result = new ArrayList<>();
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

    private static ExcelData converteerColumnNaarExcelData(final HSSFSheet sheet, final int column) throws ExcelAdapterException, Lo3SyntaxException {
        // lijst met stapels
        final List<String> headerLijst = new ArrayList<>();
        final List<Lo3CategorieWaarde> categorieLijst = new ArrayList<>();

        int rowIndex = 0;

        // parse all headers into headerLijst; headers are the first rows of a sheet that have no value in the second
        // column
        rowIndex = parseHeaders(sheet, column, headerLijst, rowIndex);

        // parse the rest into categorieLijst
        parseContent(sheet, column, categorieLijst, rowIndex);

        // convert the headers into an array of Strings
        String[] headerArray = new String[headerLijst.size()];
        headerArray = headerLijst.toArray(headerArray);

        // instantiate and initialize BerichtDto
        final ExcelData berichtDto = new ExcelData();
        berichtDto.setHeaders(headerArray);
        berichtDto.setCategorieWaarden(categorieLijst);

        return berichtDto;
    }

    private static int parseHeaders(final HSSFSheet sheet, final int column, final List<String> headerLijst, final int startRowIndex) {
        int rowIndex = startRowIndex;
        HSSFRow row = sheet.getRow(rowIndex);

        // parse all headers into headerLijst; headers are the first rows of a sheet that have no value in the second
        // column
        while (row != null && (row.getCell(ELEM_INDEX) == null || row.getCell(ELEM_INDEX).getCellType() == Cell.CELL_TYPE_BLANK)) {

            final String plCellWaarde = parseCellWaarde(row.getCell(column));

            // the first header must have value "00000000" (fixed value for 'Random key'); if this value does not exist
            // in the first row, the sheet is assumed to contain no headers at all
            if (headerLijst.size() == 0 && (plCellWaarde == null || !"00000000".equals(plCellWaarde))) {
                // start at row 3, i.e. rowIndex 2
                rowIndex = 2;
                sheet.getRow(rowIndex);

                break;
            }

            headerLijst.add(plCellWaarde);
            row = sheet.getRow(++rowIndex);
        }

        return rowIndex;
    }

    private static int parseContent(final HSSFSheet sheet, final int column, final List<Lo3CategorieWaarde> categorieLijst, final int startRowIndex)
        throws Lo3SyntaxException, ExcelAdapterException
    {
        int rowIndex = startRowIndex;
        HSSFRow row = sheet.getRow(rowIndex);
        Lo3CategorieWaarde categorieWaarde = null;

        int huidigeCat = 0;
        int stapel = 0;
        int voorkomen = 0;

        int vorigeCat = 0;
        int vorigeStapel = 0;
        int vorigeVoorkomen = 0;

        // parse the contents into categorieLijst
        while (row != null) {
            final HSSFCell cell = row.getCell(ELEM_INDEX);
            if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                row = sheet.getRow(++rowIndex);
                // sla lege rij over, ga door met volgende rij
                continue;
            }

            String elemNr = parseElementNummer(cell);

            // Als elemNr groters is dan 2, dan is het een waarde veld.
            if (elemNr.length() <= CATEGORIE_LENGTE) {
                // elemNr is een categorie, nu aanvullen met voorloopnullen als deze nog niet op lengte is.
                while (elemNr.length() < CATEGORIE_LENGTE) {
                    elemNr = LEADING_ZERO + elemNr;
                }

                // CategorieWaarde opslaan als deze gevuld is.
                final boolean isCategorieWaardeGevuld = categorieWaarde != null && !categorieWaarde.isEmpty();
                if (isCategorieWaardeGevuld) {
                    categorieLijst.add(categorieWaarde);
                } else {
                    huidigeCat = vorigeCat;
                    stapel = vorigeStapel;
                    voorkomen = vorigeVoorkomen;
                }

                vorigeCat = huidigeCat;
                vorigeStapel = stapel;
                vorigeVoorkomen = voorkomen;

                // Als Cat hetzelfde is, dan volgende stapel, tenzij het cat historisch is, dan volgend voorkomen.
                final int verwerkendeCat = Integer.parseInt(elemNr);
                if (verwerkendeCat < HISTORIE_CATEGORIE_OFFSET
                    && (verwerkendeCat == huidigeCat || verwerkendeCat == huidigeCat - HISTORIE_CATEGORIE_OFFSET))
                {
                    stapel = stapel + 1;
                    voorkomen = 0;
                } else if (verwerkendeCat < HISTORIE_CATEGORIE_OFFSET) {
                    huidigeCat = verwerkendeCat;
                    stapel = 0;
                    voorkomen = 0;
                } else {
                    voorkomen = voorkomen + 1;
                }

                categorieWaarde = new Lo3CategorieWaarde(Lo3CategorieEnum.getLO3Categorie(elemNr), stapel, voorkomen);
            } else {
                // Element regel
                if (categorieWaarde == null) {
                    throw new ExcelAdapterException(getColumnLetter(column), AANDUIDING_VOOR_EEN_NIEUWE_CATEGORIE_ONTBREEKT_VERWACHT_OP_REGEL
                                                                             + (rowIndex + 1));
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

    private static void parseCategorieRij(final int column, final Lo3CategorieWaarde huidigeCategorie, final HSSFRow row, final String elemNrParam)
        throws Lo3SyntaxException
    {
        String elemNr = elemNrParam;
        while (elemNr.length() < ELEMENT_LENGTE) {
            elemNr = LEADING_ZERO + elemNr;
        }
        final Lo3ElementEnum element = Lo3ElementEnum.getLO3Element(elemNr);
        final String waarde = parseCellWaarde(row.getCell(column));

        if (waarde != null && waarde.length() > 0) {
            huidigeCategorie.addElement(element, waarde);
        }
    }

    private static String parseElementNummer(final HSSFCell cell) {
        final String elemNr;
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            elemNr = "" + (int) cell.getNumericCellValue();
        } else {
            elemNr = cell.toString();
        }
        return elemNr;
    }

    private static String parseCellWaarde(final HSSFCell waardeCell) {
        final String waarde;
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
