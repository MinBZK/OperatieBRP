/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.injection;

import java.util.Map;
import nl.bzk.migratiebrp.test.isc.exception.TestException;

/**
 * Calculator; berekent een variabele obv andere variabelen.
 */
public interface Calculator {

    /**
     * Bereken een waarde.
     * @param context context
     * @param variables variabelen
     * @param key sleutel (uit configuratie)
     * @return resulterende waarde uit berekening
     * @throws TestException bij fouten
     */
    String calculate(Context context, Map<String,String> variables, String key) throws TestException;
}
