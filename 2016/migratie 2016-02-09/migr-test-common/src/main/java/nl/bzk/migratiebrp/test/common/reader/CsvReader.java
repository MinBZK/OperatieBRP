/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.reader;

import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3Lg01BerichtWaarde;

/**
 * Reader implementatie voor CSV-bestanden.
 */
public final class CsvReader implements Reader {

    /**
     * Leest de alle regels van een CSV-bestand en voegt deze regels samen tot 1 regel, gescheiden door een '\n'.
     * 
     * @param file
     *            Bestand dat ingelezen moet worden
     * @throws java.io.IOException
     *             Als in de onderliggende CSVReader een Exception optreedt
     * @return String met daarin de inhoud van het bestand
     */
    @Override
    public String readFile(final File file) throws IOException {
        final StringBuilder sb = new StringBuilder();
        try (CSVReader reader = new CSVReader(ReaderUtil.getReader(file))) {
            sb.append(Arrays.toString(reader.readNext())).append("\n");
        }
        return sb.toString();
    }

    /**
     * Niet geimplementeerd. Geeft null terug.
     * 
     * @param file
     *            Bestand dat ingelezen moet worden
     * @return null, deze methode is niet geimplementeerd.
     */
    @Override
    public List<Lo3Lg01BerichtWaarde> readFileAsLo3CategorieWaarde(final File file) {
        return null;
    }

    /**
     * Leest het CSV-bestand in en retourneert een lijst met daarin een map van header/value.
     * 
     * @param file
     *            het csv-bestand dat ingelezen moet worden
     * @return Een lijst met daarin header/value combinatie. Elke entry in de lijst is een regel in het bestand. Elke
     *         map-entry is een combinatie van header en value.
     * @throws IOException
     *             als er iets mis is gegaan bij het inlezen van het bestand
     */
    @Override
    public List<Map<String, Object>> readFileAsSqlOutput(final File file) throws IOException {
        if (!file.exists()) {
            return null;
        }

        final List<Map<String, Object>> results = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            final List<String[]> content = reader.readAll();
            if (!content.isEmpty()) {
                final String[] headers = content.get(0);
                for (int contentIndex = 1; contentIndex < content.size(); contentIndex++) {
                    final Map<String, Object> map = new LinkedHashMap<>();
                    final String[] values = content.get(contentIndex);
                    for (int valueIndex = 0; valueIndex < values.length; valueIndex++) {
                        map.put(headers[valueIndex], values[valueIndex]);
                    }
                    results.add(map);
                }
            }
        }
        return results;
    }
}
