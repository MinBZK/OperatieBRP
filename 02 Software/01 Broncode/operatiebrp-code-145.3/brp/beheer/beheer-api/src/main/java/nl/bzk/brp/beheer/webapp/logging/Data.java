/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.logging;

import java.util.HashMap;
import java.util.Map;


/**
 * Klasse voor het omzetten van sleutel/waarde-paren naar een map.
 */
public interface Data {

    /**
     * Zet sleutel/waarde-paren om naar een map.
     * @param keysAndValues De sleutel/waarde-paren.
     * @return De map met daarin de sleutels met hun bijbehorende waarden.
     */
    static Map<String, String> asMap(final String... keysAndValues) {
        final Map<String, String> result = new HashMap<>();
        final int increment = 2;
        for (int i = 0; i + 1 < keysAndValues.length; i = i + increment) {
            result.put(keysAndValues[i], keysAndValues[i + 1]);
        }
        return result;
    }
}
