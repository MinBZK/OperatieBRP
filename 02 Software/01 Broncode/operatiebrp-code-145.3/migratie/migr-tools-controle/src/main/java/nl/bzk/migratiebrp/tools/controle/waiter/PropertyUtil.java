/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.controle.waiter;

/**
 * Utility class voor het uitlezen van properties.
 */
public final class PropertyUtil {

    /**
     * Default constructor.
     */
    private PropertyUtil() {

    }

    /**
     * Geef property uit environment of system properties.
     * @param key key
     * @return waarde
     */
    public static String getProperty(final String key) {
        String value = System.getenv(key);

        if (value == null || "".equals(value.trim())) {
            value = System.getProperty(key);
        }

        return value;
    }
}
