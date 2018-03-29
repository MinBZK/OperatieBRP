/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.injection;


import java.util.Map;
import nl.bzk.migratiebrp.test.isc.exception.TestException;

public final class CalculatorGemeentecodeNaarPartijcode implements Calculator {

    @Override
    public String calculate(final Context context, final Map<String, String> variables, final String key) throws TestException {
        return variables.get(key) + "01";
    }
}
