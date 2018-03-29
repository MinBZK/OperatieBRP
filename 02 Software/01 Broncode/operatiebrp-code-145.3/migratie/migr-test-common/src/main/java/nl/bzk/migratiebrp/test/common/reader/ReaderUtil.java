/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.reader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.mozilla.universalchardet.UniversalDetector;

/**
 * Util class om een Reader te kunnen krijgen voor een bepaald bestand.
 */
public final class ReaderUtil {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int DETECTION_BUFFER = 4096;

    private ReaderUtil() {
    }

    /**
     * Geeft een {@link java.io.BufferedReader} terug voor het meegegeven bestand. Als de encodering van het bestand
     * achterhaalt kan worden, dan wordt deze gebruikt. Anders wordt er standaard UTF-8 gebruikt.
     * @param file bestand dat gelezen moet worden met deze Reader.
     * @return een {@link java.io.BufferedReader} met de juiste encodering voor het bestand
     * @throws IOException als het bestand niet gelezen kan worden voor het bepalen van de encodering
     */
    public static java.io.Reader getReader(final File file) throws IOException {
        final BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
        if (!input.markSupported()) {
            throw new IllegalArgumentException("BufferedInputStream does not support mark?!");
        }
        input.mark(DETECTION_BUFFER);

        // Detect encoding
        final byte[] detectionBuffer = new byte[DETECTION_BUFFER];
        final int length = input.read(detectionBuffer, 0, DETECTION_BUFFER);
        final String encoding = detectEncoding(detectionBuffer, length);
        input.reset();

        LOG.info("File encoding detected: " + encoding);

        // Get reader
        return new BufferedReader(new InputStreamReader(input, Charset.forName(encoding)));
    }

    private static String detectEncoding(final byte[] data, final int length) {
        final UniversalDetector detector = new UniversalDetector(null);
        detector.handleData(data, 0, length);
        detector.dataEnd();
        final String encoding = detector.getDetectedCharset();
        detector.reset();

        return encoding == null ? "UTF-8" : encoding;
    }
}
