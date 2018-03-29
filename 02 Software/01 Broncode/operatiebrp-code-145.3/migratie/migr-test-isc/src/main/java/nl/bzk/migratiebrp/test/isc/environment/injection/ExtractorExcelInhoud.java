/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.injection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.exception.TestException;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterImpl;
import nl.bzk.migratiebrp.util.excel.ExcelData;

/**
 * Extractor om de inhoud van een xls bestand te extraheren. Key is de bestandsnaam.
 *
 * Let op: de XLS wordt verwacht een geldig Lo3 bericht te zijn. De header word genegeerd.
 */
public class ExtractorExcelInhoud implements Extractor {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final ExcelAdapter excelAdapter = new ExcelAdapterImpl();

    @Override
    public final String extract(final Context context, final Bericht bericht, final String key) throws TestException {
        final File fileToRead = new File(bericht.getTestBericht().getInputFile().getParent(), key);
        if (!fileToRead.exists() || !fileToRead.canRead()) {
            throw new TestException("Bestand niet gevonden: " + fileToRead.toString());
        }

        try (final FileInputStream fis = new FileInputStream(fileToRead)) {
            final List<ExcelData> berichtDtos = excelAdapter.leesExcelBestand(fis);

            if (berichtDtos.isEmpty()) {
                throw new TestException("Excel bevat geen berichten: " + fileToRead.toString());
            }

            if (berichtDtos.size() > 1) {
                LOGGER.warn("Excel sheet bevatte meer dan 1 bericht. Enkel het eerste bericht wordt gebruikt.");
            }

            return Lo3Inhoud.formatInhoud(berichtDtos.get(0).getCategorieLijst());
        } catch (final
        IOException
                | ExcelAdapterException
                | Lo3SyntaxException e) {
            throw new TestException("Probleem bij inlezen Excel sheet", e);
        }
    }
}
