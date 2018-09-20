/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.isc.reader;

import java.io.File;
import java.io.FileInputStream;
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

/**
 * Excel reader.
 */
public final class ExcelReader implements Reader {

    private static final SimpleDateFormat DATUMTIJD = new SimpleDateFormat("yyyyMMddHHmmss");
    private final ExcelAdapter excelAdapter = new ExcelAdapterImpl();

    @Override
    public String readFile(final File file) {
        String result;

        try {
            final List<ExcelData> berichtDtos = excelAdapter.leesExcelBestand(new FileInputStream(file));

            result = getAsString(berichtDtos.iterator().next());
            // CHECKSTYLE:OFF - Robustheid testtooling
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            e.printStackTrace();

            result = null;
        }

        return result;
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
    private String getAsString(final ExcelData berichtDto) throws IOException {
        String header = getConcatenatedString(berichtDto.getHeaders());

        if (header.isEmpty()) {
            final Lo3Header lo3Header =
                    new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER, Lo3HeaderVeld.DATUM_TIJD,
                            Lo3HeaderVeld.A_NUMMER, Lo3HeaderVeld.OUD_A_NUMMER);
            final String[] headers =
                    new String[] { null, "Lg01", maakDatumTijd(), getAnummer(berichtDto.getCategorieLijst()), null };
            header = lo3Header.formatHeaders(headers);
        }

        // Compose LO3 message
        final String bericht = header + Lo3Inhoud.formatInhoud(berichtDto.getCategorieLijst());

        return bericht;
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

}
