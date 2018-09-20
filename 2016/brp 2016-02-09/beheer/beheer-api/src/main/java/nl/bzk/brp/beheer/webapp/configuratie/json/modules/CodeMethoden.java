/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Code methoden utilities.
 */
public final class CodeMethoden {

    private static final Map<Class<?>, Method> CODE_METHODES = new HashMap<>();

    private CodeMethoden() {
        // Niet instantieerbaar
    }

    private static Method geefCodeMethode(final Class<?> clazz) {
        if (CODE_METHODES.containsKey(clazz)) {
            return CODE_METHODES.get(clazz);
        }

        final Method method = bepaalCodeMethode(clazz);
        CODE_METHODES.put(clazz, method);
        return method;
    }

    private static Method bepaalCodeMethode(final Class<?> clazz) {
        for (final Method method : clazz.getMethods()) {
            if ("getCode".equals(method.getName()) && method.getParameterTypes().length == 0) {
                return method;
            }
        }
        return null;
    }

    /**
     * Heeft het gegeven object een Object getCode() methode?
     *
     * @param value object
     * @return true, als het object een naam methode heeft.
     */
    public static boolean heeftCodeMethode(final Object value) {
        return geefCodeMethode(value.getClass()) != null;
    }

    /**
     * Geef de naam van het object via de code methode.
     *
     * @param value object
     * @return code
     * @throws NullPointerException als er geen code methode is
     * @throws IllegalArgumentException als iets fout gaat bij het uitvoeren van de code methode
     */
    public static Object invokeCodeMethode(final Object value) {
        try {
            return geefCodeMethode(value.getClass()).invoke(value);
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
