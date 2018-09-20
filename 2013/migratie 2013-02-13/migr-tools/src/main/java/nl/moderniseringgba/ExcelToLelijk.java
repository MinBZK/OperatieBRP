/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Lg01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapter;
import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapterImpl;
import nl.moderniseringgba.migratie.adapter.excel.ExcelData;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;

import org.apache.commons.io.FileUtils;

/**
 * Main voor het omzetten van excel bestanden naar gbav lelijk formaat bestanden.
 */
public final class ExcelToLelijk {

    /**
     * Constructor.
     */
    private ExcelToLelijk() {
        super();
    }

    /**
     * Main voor het omzetten van excel bestanden naar bestanden in gbav lelijk formaat.
     * 
     * @param args
     *            De argumenten.
     * @throws IOException
     *             Als er een fout optreedt.
     */
    public static void main(final String[] args) throws IOException {
        String excelFolder = null;
        if (args != null && args.length > 0) {
            excelFolder = args[0];
        }
        if (excelFolder == null || excelFolder.isEmpty()) {
            System.out.println("Directory met de excel bestanden is LEEG! script wordt gestopt");
            System.exit(-1);
        }
        System.out.println("Directory met de excel bestanden: " + excelFolder);
        final List<File> files = bepaalExcelFiles(excelFolder);
        verwerkExcelBestanden(files, excelFolder);

    }

    private static void verwerkExcelBestanden(final List<File> files, final String excelFolder) {
        final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();
        final ExcelAdapter excelAdapter = new ExcelAdapterImpl();

        for (final File file : files) {
            try {

                // Lees excel
                final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(new FileInputStream(file));

                // Parsen input *ZONDER* syntax en precondite controles
                final List<Lo3Persoonslijst> lo3Persoonslijsten = new ArrayList<Lo3Persoonslijst>();
                for (final ExcelData excelData : excelDatas) {
                    lo3Persoonslijsten.add(parser.parse(excelData.getCategorieLijst()));
                }

                for (final Lo3Persoonslijst pl : lo3Persoonslijsten) {
                    final String lg01 = formateerAlsLg01(pl);
                    final File lelijkFile = new File(excelFolder + file.getName() + "_lelijk");
                    if (!lelijkFile.exists()) {
                        lelijkFile.createNewFile();
                    }
                    final OutputStream outStream = new FileOutputStream(lelijkFile);
                    final BufferedOutputStream buffOut = new BufferedOutputStream(outStream);
                    buffOut.write(lg01.getBytes());
                    buffOut.close();
                }
                // CHECKSTYLE:OFF
            } catch (final Exception e) {
                // CHECKSTYLE:ON
                e.printStackTrace();
                System.out.println("Probleem bij het inlezen van van de persoonslijst in file: " + file
                        + ". Inlezen wordt voortgezet.");
            }
        }
    }

    private static List<File> bepaalExcelFiles(final String excelFolder) {
        final File inputFolder = new File(excelFolder);
        System.out.println(String.format("Input folder %s %s gevonden", inputFolder, inputFolder.exists() ? "is"
                : "niet"));
        final List<File> files = new ArrayList<File>(FileUtils.listFiles(inputFolder, new String[] { "xls" }, true));
        Collections.sort(files);
        return files;
    }

    private static String formateerAlsLg01(final Lo3Persoonslijst lo3) {
        final Lg01Bericht lg01 = new Lg01Bericht();
        lg01.setLo3Persoonslijst(lo3);
        return Lo3Inhoud.formatInhoud(lg01.formatInhoud());
    }
}
