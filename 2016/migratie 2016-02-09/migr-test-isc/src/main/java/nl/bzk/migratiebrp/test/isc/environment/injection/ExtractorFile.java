/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.injection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.exception.TestException;
import org.apache.commons.io.IOUtils;

/**
 * Extractor om de inhoud van een bestand te extraheren. Key is de bestandsnaam.
 */
public class ExtractorFile implements Extractor {

    @Override
    public final String extract(final Context context, final Bericht bericht, final String key) throws TestException {
        final File fileToRead = new File(bericht.getTestBericht().getInputFile().getParent(), key);
        if (!fileToRead.exists() || !fileToRead.canRead()) {
            throw new TestException("Bestand niet gevonden: " + fileToRead.toString());
        }
        try (InputStream is = new FileInputStream(fileToRead)) {
            return IOUtils.toString(is);
        } catch (final IOException e) {
            throw new TestException("Bestand kan niet gelezen worden: ", e);
        }
    }
}
