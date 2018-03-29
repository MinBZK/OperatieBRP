/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.injection;

import java.util.Map;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelServiceImpl;
import nl.bzk.migratiebrp.test.isc.exception.TestException;

/**
 * Object sleutel calculator.
 */
public class CalculatorObjectsleutel implements Calculator {

    private static final ObjectSleutelService objectSleutelService = new ObjectSleutelServiceImpl();

    @Override
    public String calculate(final Context context, final Map<String, String> variables, final String key) throws TestException {
        final String[] variableNames = key.split(",");
        if (variableNames.length != 2) {
            throw new TestException("Variabele namen voor persoon id en partij code verwacht in configuratie van objectsleutel calculator");
        }

        final String persoonIdVariabele = variableNames[0];
        final String lockVersieVariabele = variableNames[1];

        final String persoonIdValue = variables.get(persoonIdVariabele);
        final String lockVersieValue = variables.get(lockVersieVariabele);

        final int persoonId = Integer.parseInt(persoonIdValue);
        final int lockVersie = Integer.parseInt(lockVersieValue);

        return objectSleutelService.maakPersoonObjectSleutel(persoonId, lockVersie).maskeren();
    }
}
