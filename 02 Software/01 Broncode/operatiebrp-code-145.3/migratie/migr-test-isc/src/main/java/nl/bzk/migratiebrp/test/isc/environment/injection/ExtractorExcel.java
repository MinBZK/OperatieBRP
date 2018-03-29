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
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;
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
public class ExtractorExcel implements Extractor {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final ExcelAdapter excelAdapter = new ExcelAdapterImpl();

    @Override
    public final String extract(final Context context, final Bericht bericht, final String key) throws TestException {
        LOGGER.info("Argumenten: {}", key);
        final String[] arguments = key.split(",");

        if (arguments.length < 2) {
            throw new TestException("Niet genoeg argumenten voor Excel extractor");
        }

        final ExcelData excelData = readExcel(bericht, arguments[arguments.length - 1]);

        String resultaat;
        switch (arguments[0].toLowerCase()) {
            case "bericht":
                resultaat = verwerkBericht(excelData, arguments);
                break;
            case "header":
                resultaat = verwerkHeader(excelData, arguments);
                break;
            case "inhoud":
                resultaat = verwerkInhoud(excelData, arguments);
                break;
            case "element":
                resultaat = verwerkElement(excelData, arguments);
                break;
            default:
                throw new TestException("Onverwacht argument '" + arguments[0] + "'. 'bericht', 'header', 'inhoud' of 'element' verwacht.");
        }
        return resultaat;

    }

    private String verwerkBericht(final ExcelData excelData, final String[] arguments) throws TestException {
        if (arguments.length != 2) {
            throw new TestException("Enkel de argumenten 'bericht' en 'bestandsnaam' verwacht.");
        }

        final String header = getConcatenatedString(excelData.getHeaders());
        return header + Lo3Inhoud.formatInhoud(excelData.getCategorieLijst());
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

    private String verwerkHeader(final ExcelData excelData, final String[] arguments) throws TestException {
        if (arguments.length != 3) {
            throw new TestException("Enkel de argumenten 'header', 'nummer (startend vanaf 0)' en 'bestandsnaam' verwacht.");
        }

        return excelData.getHeaders()[Integer.parseInt(arguments[1]) - 1];
    }

    private String verwerkInhoud(final ExcelData excelData, final String[] arguments) throws TestException {
        if (arguments.length != 2) {
            throw new TestException("Enkel de argumenten 'inhoud' en 'bestandsnaam' verwacht.");
        }

        return Lo3Inhoud.formatInhoud(excelData.getCategorieLijst());
    }

    private String verwerkElement(final ExcelData excelData, final String[] arguments) throws TestException {
        if (arguments.length != 6) {
            throw new TestException(
                    "Enkel de argumenten 'header', 'categorienummer', 'stapelnummer (startend vanaf 0)',"
                            + " 'volgnummer (startend vanaf 0)', 'elementnummer (bv 0110 voor a-nummer)' en 'bestandsnaam' verwacht.");
        }

        try {
            return Lo3CategorieWaardeUtil.getElementWaarde(
                    excelData.getCategorieLijst(),
                    Lo3CategorieEnum.getLO3Categorie(Integer.parseInt(arguments[1])),
                    Integer.parseInt(arguments[2]),
                    Integer.parseInt(arguments[3]),
                    Lo3ElementEnum.getLO3Element(arguments[4]));
        } catch (
                NumberFormatException
                        | Lo3SyntaxException e) {
            throw new TestException("Een van de argumenten voor 'header' was ongeldig.", e);
        }
    }

    private ExcelData readExcel(final Bericht bericht, final String excelBestand) throws TestException {
        final File fileToRead = new File(bericht.getTestBericht().getInputFile().getParent(), excelBestand);
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

            return berichtDtos.get(0);
        } catch (final
        IOException
                | ExcelAdapterException
                | Lo3SyntaxException e) {
            throw new TestException("Probleem bij inlezen Excel sheet", e);
        }
    }
}
