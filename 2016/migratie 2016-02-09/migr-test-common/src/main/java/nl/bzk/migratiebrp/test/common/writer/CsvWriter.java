/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.writer;

import au.com.bytecode.opencsv.CSVWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Writer implementatie voor CSV-bestanden.
 */
@Component
public final class CsvWriter implements Writer {

    @Override
    public void writeToFile(final File file, final List<String> contents) throws IOException {

        try (final CSVWriter writer = openWriter(file)) {
            final List<String[]> csvContent = new ArrayList<>();
            for (final String content : contents) {
                csvContent.add(new String[] {content });
            }
            writer.writeAll(csvContent);
        }
    }

    @Override
    public void writeSqlResultToFile(final File file, final List<Map<String, Object>> contents) throws IOException {
        try (final CSVWriter writer = openWriter(file)) {
            final List<String[]> csvContent = new ArrayList<>();

            Boolean headersProcessed = false;
            for (final Map<String, Object> resultMap : contents) {
                final String[] headers = new String[resultMap.size()];
                final String[] values = new String[resultMap.size()];
                int index = 0;

                for (final Map.Entry<String, Object> entry : resultMap.entrySet()) {
                    if (!headersProcessed) {
                        headers[index] = entry.getKey();
                    }
                    values[index] = "" + entry.getValue();
                    index++;
                }

                if (!headersProcessed) {
                    csvContent.add(headers);
                    headersProcessed = true;
                }
                csvContent.add(values);
            }

            writer.writeAll(csvContent);
        }
    }

    private CSVWriter openWriter(final File file) throws IOException {
        return new CSVWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
    }
}
