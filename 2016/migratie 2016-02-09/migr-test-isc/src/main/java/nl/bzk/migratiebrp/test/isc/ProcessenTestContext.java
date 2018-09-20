/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc;

import java.util.HashMap;
import java.util.Map;

/**
 * Test context.
 */
public final class ProcessenTestContext {

    private final Map<String, String> values = new HashMap<>();

    /**
     * Get a property.
     *
     * @param key
     *            key
     * @return value (null, if not found)
     */
    public String get(final String key) {
        return values.get(key);
    }

    /**
     * Set a property.
     * 
     * @param key
     *            key
     * @param value
     *            value
     */
    public void set(final String key, final String value) {
        values.put(key, value);
    }
}
