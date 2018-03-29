/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.writer;

import java.io.File;
import java.util.regex.Matcher;
import org.springframework.stereotype.Component;

/**
 * Writer factory.
 */
@Component
public final class WriterFactory extends AbstractIOFactory {

    /**
     * Geef writer voor het bestand. De writer wordt bepaald aan de hand van de extensie.
     * @param file bestand
     * @return reader
     */
    public Writer getWriter(final File file) {
        Writer result = null;
        final Matcher matcher = PATTERN_EXTENSIE.matcher(file.getName().toLowerCase());
        if (matcher.find()) {
            switch (matcher.group(1)) {
                case INPUT_EXTENSIE_CSV:
                    result = new CsvWriter();
                    break;
                case INPUT_EXTENSIE_TXT:
                    result = new TxtWriter();
                    break;
                default:
                    // Zou hier niet moeten komen als er eerst de accept-methode wordt gebruikt
                    result = null;
            }
        }
        return result;
    }

    @Override
    public boolean accept(final File file) {
        boolean result = false;
        final Matcher matcher = PATTERN_EXTENSIE.matcher(file.getName().toLowerCase());
        if (matcher.find()) {
            final String extensie = matcher.group(1);
            switch (extensie) {
                case INPUT_EXTENSIE_CSV:
                case INPUT_EXTENSIE_TXT:
                    result = true;
                    break;
                default:
                    LOG.error(String.format("Bestand heeft een niet ondersteunde extensie (%s).", extensie));
                    result = false;
            }
        }
        return result;
    }
}
