/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.job;

import java.io.IOException;
import java.io.InputStream;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.apache.commons.io.IOUtils;

/**
 * Utilklasse voor het ophalen van resources.
 */
public final class ResourceUtil {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Niet instantieerbaar.
     */
    private ResourceUtil() {
    }

    /**
     * Haalt de string op die in het opgegeven resource pad zit.
     *
     * @param object
     *            Het resource object.
     * @param resourcePath
     *            Het pad van de resource.
     * @return String representatie van de resource.
     */
    protected static String getStringResourceData(final Object object, final String resourcePath) {
        final InputStream inputStream = object.getClass().getResourceAsStream(resourcePath);
        try {
            final String data = IOUtils.toString(inputStream);
            inputStream.close();
            return data;
        } catch (final IOException e) {
            LOG.error("Fout bij lezen van resource file: " + resourcePath, e);
            return null;
        }
    }
}
