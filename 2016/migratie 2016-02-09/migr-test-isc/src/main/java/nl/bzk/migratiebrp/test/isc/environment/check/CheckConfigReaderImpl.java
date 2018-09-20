/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.check;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Configuratie lezer.
 */
public class CheckConfigReaderImpl implements CheckConfigReader {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public final List<CheckConfig> readConfig(final File file) throws IOException {
        final List<CheckConfig> result = new ArrayList<>();
        if (file.exists() && file.isFile()) {

            try (final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        // skip empty line
                        continue;
                    }

                    final int firstIndex = line.indexOf(',');

                    if (firstIndex == -1) {
                        LOG.warn("Ongeldige configuratieregel: {}", line);
                        continue;
                    }

                    final String type = line.substring(0, firstIndex);
                    final String config = line.substring(firstIndex + 1);
                    result.add(new CheckConfig(type, config));
                }
            }
        }
        return result;
    }
}
