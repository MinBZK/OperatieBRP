/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.lg01;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.Lo3Header;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapter;
import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapterImpl;
import nl.moderniseringgba.migratie.adapter.excel.ExcelData;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.apache.commons.io.FileUtils;

public abstract class AbstractConverteerTest {

    private static final SimpleDateFormat DATUMTIJD = new SimpleDateFormat("yyyyMMddHHmmss");

    private final ExcelAdapter excelAdapter = new ExcelAdapterImpl();

    protected void testMain(final File directory) throws Exception {
        // Convert XLS sheets that could start with LO3 headers
        // XLS sheets that start with LO3 headers start with the default random key value '00000000' in the third
        // column; all the subsequent rows with no value in the second column are treated as header rows (only the value
        // in the third column will be used as-is as header).
        // XLS sheets that do not start with LO3 headers, do not start with the value '00000000' in the third column and
        // the first two lines of the XLS sheet will be skipped; a dummy Lg01 messageheader will be used.

        // Verify that the input directory exists
        // final File directory = new File("./converteer");
        if (!(directory.exists() && directory.isDirectory())) {
            return;
        }
        final File outputDirectory = new File(directory.getParent(), directory.getName() + "-output");

        // Empty output directory by deleting and creating it
        if (outputDirectory.exists()) {
            try {
                FileUtils.deleteDirectory(outputDirectory);
            } catch (final IOException e) {
            }
        }
        outputDirectory.mkdirs();

        // Process files in the input directory
        for (final File file : directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(final File candidate) {
                return candidate.isFile(); // any file is processed
            }
        })) {
            try {
                // Read LO3 messages from an XLS file as lists of Lo3CategorieWaarde's
                final List<ExcelData> berichtDtos = excelAdapter.leesExcelBestand(new FileInputStream(file));

                // Output each list of Lo3CategorieWaarde's as a LO3 message to a separate file
                for (int i = 0; i < berichtDtos.size(); i++) {
                    final ExcelData berichtDto = berichtDtos.get(i);
                    String berichtNummer = null;

                    if (berichtDto.getHeaders().length >= 2) {
                        berichtNummer = berichtDto.getHeaders()[1];
                    }

                    if (berichtNummer == null) {
                        berichtNummer = "Lg01";
                    }

                    outputBerichtDto(new File(outputDirectory, file.getName() + "." + i + "." + berichtNummer
                            + ".txt"), berichtDto);
                }

                // CHECKSTYLE:OFF - Catch throwable voor robustheid proces
            } catch (final Throwable t) {
                // CHECKSTYLE:ON
                System.err.println("Probleem met inlezen: " + file.getName());
                t.printStackTrace();
            }
        }

    }

    /**
     * Output a berichtDto as a LO3 message to a file
     * 
     * @param file
     *            File to write a LO3 message (Lg01) to
     * @param categorieLijst
     *            List of values within a category that needs to be converted to a LO3 message
     * @throws IOException
     *             if writing to a file fails
     */
    private void outputBerichtDto(final File file, final ExcelData berichtDto) throws IOException {
        // final String[] headerValues = berichtDto.getHeaders();
        String header = getConcatenatedString(berichtDto.getHeaders());

        if (header.isEmpty()) {
            final Lo3Header HEADER =
                    new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER, Lo3HeaderVeld.DATUM_TIJD,
                            Lo3HeaderVeld.A_NUMMER, Lo3HeaderVeld.OUD_A_NUMMER);
            final String[] headers =
                    new String[] { null, "Lg01", maakDatumTijd(), getAnummer(berichtDto.getCategorieLijst()), null };
            header = HEADER.formatHeaders(headers);
        }

        // Compose LO3 message
        final String bericht = header + Lo3Inhoud.formatInhoud(berichtDto.getCategorieLijst());

        // Output LO3 message to a file
        final FileOutputStream fos = new FileOutputStream(file);
        fos.write(bericht.getBytes());
        fos.close(); // need flushing first?
    }

    private String getAnummer(final List<Lo3CategorieWaarde> categorieLijst) {
        for (final Lo3CategorieWaarde cat : categorieLijst) {
            if (cat.getCategorie() == Lo3CategorieEnum.CATEGORIE_01) {
                for (final Map.Entry<Lo3ElementEnum, String> entry : cat.getElementen().entrySet()) {
                    if (entry.getKey() == Lo3ElementEnum.ELEMENT_0110) {
                        return entry.getValue();
                    }
                }
            }
        }

        return null;
    }

    private String maakDatumTijd() {
        return DATUMTIJD.format(new Date()) + "000";
    }

    private String getConcatenatedString(final String[] array) {
        final StringBuilder sb = new StringBuilder();

        for (final String s : array) {
            if (s != null) {
                sb.append(s);
            }
        }

        return sb.toString();
    }

}
