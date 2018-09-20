/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class RenameMain {

//    private static final String TEST_FILE_DIRECTORY = "M:\\workspace\\eclipse\\trunk\\art\\art-testdata\\src\\main\\resources\\brp-testdata";
//    private static final String TEST_FILE_DIRECTORY = "D:/Werk/QSD/mGBA/Eclipse Workspace/testdata-trunk/src/main/resources/brp-testdata";
    private static final String TEST_FILE_DIRECTORY = "D:/Werk/QSD/mGBA/Eclipse Workspace/testdata-trunk/src/main/resources/tmp";
//    private static final String TEST_FILE_DIRECTORY = "M:\\art testdata conversie\\excel_merge_test";
    private static final String TEST_FILENAME_FILTER = "SierraTestdata";

    public static void main(final String[] args) {
        Map<String, Workbook> artTestdataWorkbooks = getArtTestdataWorkbooks(TEST_FILE_DIRECTORY);

        // voer dataconversie uit
        TestdataSheetOmzetten xlsDataConverter = new TestdataSheetOmzetten();
        xlsDataConverter.zetTestdataSheetOm(artTestdataWorkbooks, TEST_FILE_DIRECTORY);

/* NB: Uitgezet, is al gedaan!
        // voer woonplaats conversie uit
        xlsDataConverter.zetWoonplaatsIdsOm(artTestdataWorkbooks);
*/

        writeArtTestdataWorkbooksToFile(artTestdataWorkbooks);
    }

    private static Map<String, Workbook> getArtTestdataWorkbooks(final String path) {
    	Map<String, Workbook> artTestdataWorkbooks = new HashMap<>();
        for (File file : getArtTestdataFiles(path)) {
        	try {
        		FileInputStream fis = new FileInputStream(file);
        		// Skip lev abo, is al handmatig gedaan.
        		if (!file.getName().contains("LevAbo")) {
        		    artTestdataWorkbooks.put(file.getPath(), WorkbookFactory.create(fis));
        		}
        		fis.close();
			} catch (InvalidFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return artTestdataWorkbooks;
    }

    private static void writeArtTestdataWorkbooksToFile(final Map<String, Workbook> artTestdataWorkbooks) {
    	for (String filename : artTestdataWorkbooks.keySet()) {
    		FileOutputStream fileOut;
			try {
	    		Workbook workbook = artTestdataWorkbooks.get(filename);
	    		// Uitgezet, snapt niet alle slimme formules van ons.
	    		//HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
				fileOut = new FileOutputStream(filename);
				workbook.write(fileOut);
				fileOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

    private static List<File> getArtTestdataFiles(final String path) {
        List<File> testdataFiles = new ArrayList<>();
        File testdataDirectory = new File(path);
        if (testdataDirectory.isDirectory()) {
            for (File testdataFile : testdataDirectory.listFiles(new FileFilter() {

                @Override
                public boolean accept(final File pathname) {
                    return (pathname.getName().contains(TEST_FILENAME_FILTER) && !pathname.getName().contains("lock"));
                }
            })) {
                if (testdataFile.isFile()) {
                    testdataFiles.add(testdataFile);
                } else {
                    testdataFiles.addAll(getArtTestdataFiles(testdataFile.getPath()));
                }
            }
        }

        return testdataFiles;
    }
}
