/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.art.util.cleanupexpecteds;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class CleanUpExpectedResults {

    private static final boolean DELETE_ON_DISK = false;

    public static void main(final String[] args) throws Exception {
        String pad = "D:/Werk/QSD/mGBA/Eclipse Workspace/art-bijhouding-trunk/";
        File artPadFile = new File(pad);
        Collection<File> artFolders = FileUtils.listFilesAndDirs(artPadFile, FalseFileFilter.INSTANCE, new IOFileFilter() {
            @Override
            public boolean accept(final File dir, final String name) {
                return false;
            }
            @Override
            public boolean accept(final File file) {
                String name = file.getName();
                return name.startsWith("ART-") || name.startsWith("KUC") || name.startsWith("PUC");
            }
        });
        // WTF zit deze er uberhaupt by default in?
        artFolders.remove(artPadFile);
        for (File artFolder : artFolders) {
            System.out.println("Doorlopen van: " + artFolder.getName());

            // Zoek alle bestaande testgevallen in Excel sheet.
            File inputSheet = FileUtils.listFiles(new File(artFolder.getAbsolutePath() + "/ART"), new String[] {"xls"}, true).iterator().next();
            System.out.println("  Gevonden Excel input sheet: " + inputSheet.getName());
            List<String> testgevalBestanden = bepaalTestgevallen(inputSheet);

            Collection<File> expectedFiles = FileUtils.listFiles(new File(artFolder.getAbsolutePath() + "/ART/ExpectedResults/response/"), new String[] {"xml"}, true);
            for (File inputFile : expectedFiles) {
                if (!testgevalBestanden.contains(inputFile.getName())) {
                    System.out.println("    Niet meer gebruikte expected: " + inputFile.getName());
                    if (DELETE_ON_DISK) {
                        inputFile.delete();
                    }
                }
            }
        }
    }

    private static List<String> bepaalTestgevallen(final File inputSheet) throws Exception {
        List<String> testgevalBestanden = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(new FileInputStream(inputSheet));
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        // Skip header
        rowIterator.next();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell naamCell = row.getCell(0);
            Cell volgnummerCell = row.getCell(1);
            if (naamCell != null && naamCell.getCellType() != Cell.CELL_TYPE_BLANK) {
                String volgnummer;
                if (volgnummerCell == null || volgnummerCell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    volgnummer = "";
                } else if (volgnummerCell.getCellType() == Cell.CELL_TYPE_STRING) {
                    volgnummer = volgnummerCell.getStringCellValue();
                } else if (volgnummerCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    volgnummer = "" + ((int) volgnummerCell.getNumericCellValue());
                } else {
                    throw new IllegalStateException("Ongeldig cell type voor volgnummer: " + volgnummerCell.getCellType());
                }
                String testgevalBestand = naamCell.getStringCellValue() + "-" + volgnummer + "-soapresponse.xml";
                testgevalBestanden.add(testgevalBestand);
            }
        }
        return testgevalBestanden;
    }

}
