/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.test.utils.bijhouding.BijhoudingAlgoritme;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Deze coversie tool kan worden gebruikt voor het converteren van ART testdata. Doordat de structuur van de ART testdata xls bestanden gelijk is,
 * kunnen d.m.v. deze tool structuur aanpassingen geautomatiseerd worden doorgevoerd. Zo worden er:
 * - kolomnamen die een nieuwe naam hebben gekregen aangepast
 * - kolomnamen die zijn vervallen verwijderd
 * - nieuwe kolomnamen toegevoegd
 *
 * De basis die voor deze conversie wordt gebruikt is de Conversie data.xls sheet
 * ...
 * ...
 *
 */
public class TestdataSheetOmzetten {

    private static final String FILENAME_CONVERSION_EXCELSHEET = "Conversie data.xls";

    private static final List<String> DOORTREKKEN_KOLOM_HEADERS = Arrays.asList("tabelnaam", "aantal kolommen", "kolomnaam");

    private final Map<String, Map<String, String>> columnsToAdd;
    private final Map<String, Map<String, String>> columnsToConvert;
    private final Map<String, List<String>> columnsToDelete;
    private final Map<String, Sheet> sheetsToAdd;

    public TestdataSheetOmzetten() {
        columnsToAdd = new HashMap<>();
        columnsToConvert = new HashMap<>();
        columnsToDelete = new HashMap<>();
        sheetsToAdd = new HashMap<>();
    }

    public void zetTestdataSheetOm(final Map<String, Workbook> artTestdataWorkbooks, final String testFileDirectory) {
        readConvertionWorkBookAndSetData();
        convertArtTestdataFromFiles(artTestdataWorkbooks);
    }

    private void readConvertionWorkBookAndSetData() {
    	Workbook converterWorkbook;
		try {
			converterWorkbook = WorkbookFactory.create(new File("src\\main\\resources\\" + FILENAME_CONVERSION_EXCELSHEET));

	        for (int i = 0; i < converterWorkbook.getNumberOfSheets(); i++) {
	            Sheet dataSheet = converterWorkbook.getSheetAt(i);
	            String dataSheetName = dataSheet.getSheetName();

	            if (sheetContainsConvertionData(dataSheet)) {
	            	Iterator<Row> rowIterator = dataSheet.iterator();
	                while(rowIterator.hasNext()) {
	                    Row row = rowIterator.next();

	                    String oldColumnName = null;
	                    String newColumnName = null;
	                    String newColumnData = null;

	                    if (row.getCell(0) != null
	                            && row.getCell(0).getStringCellValue() != null
	                            && !row.getCell(0).getStringCellValue().trim().isEmpty()) {
	                        oldColumnName = row.getCell(0).getStringCellValue().trim();
	                    }

	                    if (row.getCell(1) != null
	                            && row.getCell(1) != null
	                            && row.getCell(1).getStringCellValue() != null
	                            && !row.getCell(1).getStringCellValue().trim().isEmpty()) {
	                        newColumnName = row.getCell(1).getStringCellValue().trim();
	                    }

	                    if (row.getCell(2) != null
	                            && row.getCell(2) != null
	                            && row.getCell(2).getStringCellValue() != null
	                            && !row.getCell(2).getStringCellValue().trim().isEmpty()) {
	                        newColumnData = row.getCell(2).getStringCellValue().trim();
	                    }

	                    if (oldColumnName == null && newColumnName != null) {
	                        if (columnsToAdd.get(dataSheetName) == null) {
	                            columnsToAdd.put(dataSheetName, new HashMap<String, String>());
	                        }
	                        columnsToAdd.get(dataSheetName).put(newColumnName, newColumnData);
	                    } else if (oldColumnName != null && newColumnName == null) {
	                        if (columnsToDelete.get(dataSheetName) == null) {
	                            columnsToDelete.put(dataSheetName, new ArrayList<String>());
	                        }
	                        columnsToDelete.get(dataSheetName).add(oldColumnName);
	                    } else if (oldColumnName != null && newColumnName != null) {
	                        if (columnsToConvert.get(dataSheetName) == null) {
	                            columnsToConvert.put(dataSheetName, new HashMap<String, String>());
	                        }
	                        columnsToConvert.get(dataSheetName).put(oldColumnName, newColumnName);
	                    } else {
	                        // empty cells, don't care...
	                    }
	                }
	            } else {
	                // it's a new sheet with template data which must be copied
	                sheetsToAdd.put(dataSheetName, dataSheet);
	            }
	        }
		} catch (InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * check if this sheet contains convertion data (first row starts with: [oude waarde],[nieuwe waarde],[nieuwe default waarde])
     * @param dataSheet the Sheet to check
     * @return true is sheet contains convertion data, false otherwise
     */
    private boolean sheetContainsConvertionData(final Sheet dataSheet) {
    	boolean isConvertionDataSheet = false;
    	if (dataSheet.getRow(0) != null) {
    		Row firstRow = dataSheet.getRow(0);

    		if (firstRow.getCell(0) != null && firstRow.getCell(0).getStringCellValue().equals("oude waarde")
    				&& firstRow.getCell(1) != null && firstRow.getCell(1).getStringCellValue().equals("nieuwe waarde")
    				&& firstRow.getCell(2) != null && firstRow.getCell(2).getStringCellValue().equals("nieuwe default waarde")) {
    			isConvertionDataSheet = true;
    		}
    	}

    	return isConvertionDataSheet;
    }

    private void convertArtTestdataFromFiles(final Map<String, Workbook> artTestdataWorkbooks) {
        for (Map.Entry<String, Workbook> testdataWorkbooks : artTestdataWorkbooks.entrySet()) {
            String filename = testdataWorkbooks.getKey();
            System.out.println("Now converting art testdata sheet: " + filename);
            Workbook testdataWorkbook = testdataWorkbooks.getValue();

/* NB: 2014-02-10: Alle functionaliteit behalve 'kolommen doortrekken' uitgezet ivm laatste stap conversie.

            convertColumnName(testdataWorkbook);

            // Arghhh, Apache POI does not support adding columns!! :-(
            //addNewColumnsWithDefaultData(testdataWorkbook);

            addNewtemplateSheets(testdataWorkbook);
//NB: 2014-02-11: Bijhouding afleiding opnieuw geactiveerd vanwege kleine fixes in het algoritme.
 */

            if (!filename.contains("LevAbo") && !filename.contains("LeveringenZonderBijhouding")) {
                new BijhoudingAlgoritme(testdataWorkbook).voegBijhoudingGroepToe();
            }

            trekKolomNaamFormulesDoor(testdataWorkbook);
        }
    }

    private void trekKolomNaamFormulesDoor(final Workbook workbook) {
        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            int kolom = 0;
            while (volgendeKolomDoortrekken(sheet, kolom)) {
                String kolomLetter = zetOmNaarKolomLetter(kolom);
                int row = 2;
                while (doorgaanMetDoortrekken(sheet, row, kolom)) {
                    sheet.getRow(row).getCell(kolom).setCellFormula(kolomLetter + row);
                    row++;
                }
                kolom++;
            }
        }
    }

    private boolean volgendeKolomDoortrekken(final Sheet sheet, final int kolom) {
        boolean doorgaan = false;
        for (String doortrekkenKolomHeader : DOORTREKKEN_KOLOM_HEADERS) {
            doorgaan = doorgaan || (sheet.getRow(0).getCell(kolom) != null
                    && sheet.getRow(0).getCell(kolom).getCellType() == Cell.CELL_TYPE_STRING
                    && sheet.getRow(0).getCell(kolom).getStringCellValue().trim().toLowerCase().contains(doortrekkenKolomHeader));
        }
        return doorgaan;
    }

    private boolean doorgaanMetDoortrekken(final Sheet sheet, final int rowNummer, final int kolom) {
        boolean doorgaan = false;
        Row row = sheet.getRow(rowNummer);
        if (row != null) {
            Cell cell = row.getCell(kolom);
            if (cell != null) {
                if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                    doorgaan = cell.getCellFormula().length() > 0;
                } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    doorgaan = cell.getNumericCellValue() != 0;
                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    doorgaan = cell.getStringCellValue().length() > 0;
                }
            }
        }
        return doorgaan;
    }

    // Alleen voor gebruik binnen trekKolomNaamFormulesDoor, niet algemeen!
    private String zetOmNaarKolomLetter(final int kolom) {
        String letterKolom;
        if (kolom <= 25) {
            letterKolom = "" + (char) ('A' + kolom);
        } else if (kolom <= 25 + 26) {
            letterKolom = "A" + (char) ('A' + (kolom - 26));
        } else if (kolom <= 25 + 26 + 26) {
            letterKolom = "B" + (char) ('A' + (kolom - 26 - 26));
        } else {
            throw new IllegalArgumentException("Kolom nummer out of range");
        }
        return letterKolom;
    }

    private void addNewtemplateSheets(final Workbook testdataWorkbook) {
        for (String sheetNaam : sheetsToAdd.keySet()) {
            // first check if this sheet doesn't exist (from a previous convertion run...)
            if (testdataWorkbook.getSheet(sheetNaam) == null) {
                addNewSheet(testdataWorkbook, sheetNaam, sheetsToAdd.get(sheetNaam));
            }
        }
    }

    private void convertColumnName(final Workbook testdataWorkbook) {
    	for (int i = 0; i < testdataWorkbook.getNumberOfSheets(); i++) {
    		Sheet testdataSheet = testdataWorkbook.getSheetAt(i);

            // first check if the sheet needs to be changed
            if (columnsToConvert.containsKey(testdataSheet.getSheetName())) {
                Map<String, String> columnsToBeConverted = columnsToConvert.get(testdataSheet.getSheetName());

                // check the first two rows of the testdataSheet to see is the current content should be replaced
                // the other rows in the testdataSheet don't contain columns names, so the can be skipped
                int rowNumber = 0;
                for (Iterator<Row> rowIterator = testdataSheet.iterator(); rowIterator.hasNext() && rowNumber < 2; rowNumber++) {
                    Row row = rowIterator.next();

                    for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext();) {
                        convertTestdataCell(columnsToBeConverted, cellIterator.next());
                    }
                }
            }
        }
    }

    private void addNewSheet(final Workbook testdataWorkbook, final String sheetName, final Sheet templateSheet) {
    	// create new (empty) sheet
    	HSSFSheet newSheet = (HSSFSheet)testdataWorkbook.createSheet(sheetName);
    	// copy all cell of the template sheet to the new sheet
    	WorkbookUtil.copySheets(newSheet, (HSSFSheet)templateSheet);

    }

    private void convertTestdataCell(final Map<String, String> columnsToBeConverted, final Cell testdataCell) {
        if (testdataCell != null && testdataCell.getCellType() == Cell.CELL_TYPE_STRING) {
            if (columnsToBeConverted.containsKey(testdataCell.getStringCellValue().trim())) {
            	testdataCell.setCellValue(columnsToBeConverted.get(testdataCell.getStringCellValue().trim()));
            }
        }
    }

	public void zetWoonplaatsIdsOm(final Map<String, Workbook> artTestdataWorkbooks) {
        for (Workbook testdataWorkbook : artTestdataWorkbooks.values()) {
	        for (int i = 0; i < testdataWorkbook.getNumberOfSheets(); i++) {
	            Sheet testdataSheet = testdataWorkbook.getSheetAt(i);
	            convertWoonplaatsIds(testdataSheet);
	        }
        }
	}

	private void convertWoonplaatsIds(final Sheet testdataSheet) {
		List<String> columnsToconvert = Arrays.asList("wplnaamgeboorte", "id wplgeboorte", "wplnaamoverlijden", "wplnaamaanv", "wplnaameinde", "wplnaam");
		for (String columnToConvert : columnsToconvert) {
			Integer columnNumber = POIUtil.getColumnIndex(testdataSheet, columnToConvert);
			if (columnNumber != null) {
				for (Iterator<Row> rowIterator = testdataSheet.iterator(); rowIterator.hasNext();) {
					Row row = rowIterator.next();
					convertWoonplaatsCode(row.getCell(columnNumber));
				}
			}
        }
	}

	private void convertWoonplaatsCode(final Cell cell) {
		if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			String woonplaatsnaam = WoonplaatsUtil.getNaam(String.valueOf(Math.round(cell.getNumericCellValue())));
			if (woonplaatsnaam != null) {
				cell.setCellValue(woonplaatsnaam);
			} else {
				System.out.print("geen woonplaatsnaam gevonden voor id:" + cell.getNumericCellValue());
			}
		}
	}

}