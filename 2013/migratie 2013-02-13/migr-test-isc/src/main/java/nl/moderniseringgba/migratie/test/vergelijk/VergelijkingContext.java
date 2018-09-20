/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.vergelijk;

import java.util.HashMap;
import java.util.Map;

/**
 * Vergelijking context.
 */
public final class VergelijkingContext {

    private final Map<String, String> constantVariables = new HashMap<String, String>();

    /**
     * Controleer constante variabele.
     * 
     * @param name
     *            naam
     * @param value
     *            waarde
     * @return true als ok, anders false
     */
    boolean checkConstantVariable(final String name, final String value) {
        if (constantVariables.containsKey(name)) {
            return constantVariables.get(name).equals(value);
        } else {
            constantVariables.put(name, value);
            return true;
        }
    }

    /**
     * Geef de waarde voor een constante variabele.
     * 
     * @param name
     *            naam
     * @return waarde
     */
    public String getConstantVariable(final String name) {
        return constantVariables.get(name);
    }
}
