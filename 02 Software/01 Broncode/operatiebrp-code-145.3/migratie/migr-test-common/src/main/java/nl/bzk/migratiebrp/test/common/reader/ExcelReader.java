/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3Lg01BerichtWaarde;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterImpl;
import nl.bzk.migratiebrp.util.excel.ExcelData;

/**
 * Excel reader.
 */
public final class ExcelReader implements Reader {

    private static final String FOUT_OPGETREDEN_BIJ_INLEZEN_EXCEL = "Fout opgetreden bij inlezen excel";

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final int ANUMMER = 3;
    private static final int OUD_ANUMMER = 4;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private final ExcelAdapter excelAdapter = new ExcelAdapterImpl();

    @Override
    public String readFile(final File file) {
        String result;

        try (final FileInputStream fis = new FileInputStream(file)) {
            final List<ExcelData> berichtDtos = excelAdapter.leesExcelBestand(fis);

            result = getAsString(berichtDtos.iterator().next());
        } catch (final Exception e /* Robuustheid testtooling */) {
            LOG.info(FOUT_OPGETREDEN_BIJ_INLEZEN_EXCEL, e);
            result = null;
        }

        return result;
    }

    @Override
    public List<Lo3Lg01BerichtWaarde> readFileAsLo3CategorieWaarde(final File file) throws Lo3SyntaxException {
        // Lees excel
        final List<Lo3Lg01BerichtWaarde> resultaat = new ArrayList<>();
        try (final FileInputStream fis = new FileInputStream(file)) {
            final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(fis);

            for (final ExcelData excelData : excelDatas) {
                final String[] headers = excelData.getHeaders();
                final List<Lo3CategorieWaarde> categorieWaardes = excelData.getCategorieLijst();
                final Lo3Lg01BerichtWaarde lo3Lg01BerichtWaarde;
                if (headers.length != 0) {
                    lo3Lg01BerichtWaarde = new Lo3Lg01BerichtWaarde(categorieWaardes, headers[ANUMMER], headers[OUD_ANUMMER]);
                } else {
                    lo3Lg01BerichtWaarde = new Lo3Lg01BerichtWaarde(categorieWaardes);
                }
                resultaat.add(lo3Lg01BerichtWaarde);
            }
        } catch (final Exception e /* Robustheid testtooling */) {
            LOG.info(FOUT_OPGETREDEN_BIJ_INLEZEN_EXCEL, e);
        }

        return resultaat;
    }

    private String getAsString(final ExcelData berichtDto) throws IOException {
        String header = getConcatenatedString(berichtDto.getHeaders());

        if (header.isEmpty()) {
            final Lo3Header lo3Header =
                    new Lo3Header(
                            Lo3HeaderVeld.RANDOM_KEY,
                            Lo3HeaderVeld.BERICHTNUMMER,
                            Lo3HeaderVeld.DATUM_TIJD,
                            Lo3HeaderVeld.A_NUMMER,
                            Lo3HeaderVeld.OUD_A_NUMMER);
            final String[] headers = new String[]{null, "Lg01", maakDatumTijd(), getAnummer(berichtDto.getCategorieLijst()), null};
            header = lo3Header.formatHeaders(headers);
        }

        // Compose LO3 message

        return header + Lo3Inhoud.formatInhoud(berichtDto.getCategorieLijst());
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
        return dateFormat.format(new Date()) + "000";
    }

    @Override
    public List<Map<String, Object>> readFileAsSqlOutput(final File file) throws IOException {
        throw new IOException("Operation not supported");
    }
}
