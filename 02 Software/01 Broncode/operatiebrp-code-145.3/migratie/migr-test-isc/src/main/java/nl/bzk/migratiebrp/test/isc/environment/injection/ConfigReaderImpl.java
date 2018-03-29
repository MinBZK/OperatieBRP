/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.injection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Configuratie lezer.
 */
public class ConfigReaderImpl implements ConfigReader {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public final List<Config> readConfig(final File file) throws IOException {
        final List<Config> result = new ArrayList<>();
        if (file.exists() && file.isFile()) {
            try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        // skip empty line
                        continue;
                    }

                    final int firstIndex = line.indexOf(',');
                    final int secondIndex = line.indexOf(',', firstIndex + 1);

                    if (firstIndex == -1 || secondIndex == -1) {
                        LOG.warn("Ongeldige configuratieregel: {}", line);
                        continue;
                    }

                    final String name = line.substring(0, firstIndex);
                    final String type = line.substring(firstIndex + 1, secondIndex);
                    final String key = line.substring(secondIndex + 1);
                    result.add(new Config(name, type, key));
                }
            }
        }
        return result;
    }
}
