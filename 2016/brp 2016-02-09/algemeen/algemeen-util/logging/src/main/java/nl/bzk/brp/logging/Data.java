/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.logging;

import java.util.HashMap;
import java.util.Map;


/**
 * Klasse voor het omzetten van sleutel/waarde-paren naar een map.
 */
public final class Data {

    private Data() {
    }

    /**
     * Zet sleutel/waarde-paren om naar een map.
     * 
     * @param keysAndValues De sleutel/waarde-paren.
     * @return De map met daarin de sleutels met hun bijbehorende waarden.
     */
    public static Map<String, String> asMap(final String... keysAndValues) {
        final Map<String, String> result = new HashMap<>();
        for (int i = 0; i + 1 < keysAndValues.length; i = i + 2) {
            result.put(keysAndValues[i], keysAndValues[i + 1]);
        }
        return result;
    }
}
