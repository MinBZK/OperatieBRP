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
 * Naam methoden utilities.
 */
public final class NaamMethoden {

    private static final Map<Class<?>, Method> NAAM_METHODES = new HashMap<>();

    private NaamMethoden() {
        // Niet instantieerbaar
    }

    private static Method geefNaamMethode(final Class<?> clazz) {
        if (NAAM_METHODES.containsKey(clazz)) {
            return NAAM_METHODES.get(clazz);
        }

        final Method method = bepaalNaamMethode(clazz);
        NAAM_METHODES.put(clazz, method);
        return method;
    }

    private static Method bepaalNaamMethode(final Class<?> clazz) {
        for (final Method method : clazz.getMethods()) {
            if ("getNaam".equals(method.getName()) && method.getParameterTypes().length == 0) {
                return method;
            }
        }
        return null;
    }

    /**
     * Heeft het gegeven object een Object getNaam() methode?
     *
     * @param value object
     * @return true, als het object een naam methode heeft.
     */
    public static boolean heeftNaamMethode(final Object value) {
        return geefNaamMethode(value.getClass()) != null;
    }

    /**
     * Geef de naam van het object vai de naam methode.
     *
     * @param value object
     * @return naam
     * @throws NullPointerException als er geen naam methode is
     * @throws IllegalArgumentException als iets fout gaat bij het uitvoeren van de naam methode
     */
    public static Object invokeNaamMethode(final Object value) {
        try {
            return geefNaamMethode(value.getClass()).invoke(value);
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
