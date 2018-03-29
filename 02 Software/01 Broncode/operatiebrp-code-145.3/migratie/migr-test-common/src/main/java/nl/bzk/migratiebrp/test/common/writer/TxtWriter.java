/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Writer implementatie voor TXT-bestanden.
 */
@Component
public final class TxtWriter implements Writer {
    /**
     * Schrijft de inhoud van de meegegeven lijst per regel weg.
     * @param file het betsand waar toe weg geschreven moet worden
     * @param contents de content die weg geschreven moet worden
     * @throws IOException als de onderliggende writer een exceptie geeft.
     */
    @Override
    public void writeToFile(final File file, final List<String> contents) throws IOException {
        try (final BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            for (final String content : contents) {
                output.write(content);
                output.write("\n");
                output.flush();
            }
        }
    }

    @Override
    public void writeSqlResultToFile(final File file, final List<Map<String, Object>> contents) throws IOException {
        throw new IOException("Operation not supported");
    }
}
