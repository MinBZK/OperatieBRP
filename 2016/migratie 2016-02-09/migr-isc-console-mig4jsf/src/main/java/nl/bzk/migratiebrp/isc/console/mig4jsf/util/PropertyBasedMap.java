/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * Map die de inhoud uit een property file haalt.
 */
public final class PropertyBasedMap extends HashMap<String, String> {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * 
     * @param propertyResource
     *            Resource voor de properties.
     */
    public PropertyBasedMap(final String propertyResource) {
        final Properties content = new Properties();
        try (InputStream is = getClass().getResourceAsStream(propertyResource)) {
            content.load(is);

            for (final String propertyName : content.stringPropertyNames()) {
                super.put(propertyName, content.getProperty(propertyName));
            }
        } catch (final IOException e) {
            throw new IllegalArgumentException("Kan " + propertyResource + " niet laden.", e);
        }
    }
}
