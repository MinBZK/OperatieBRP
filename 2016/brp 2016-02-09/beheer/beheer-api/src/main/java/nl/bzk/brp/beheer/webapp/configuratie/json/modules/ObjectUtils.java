/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Object utilities.
 */
public final class ObjectUtils {

    private ObjectUtils() {
        // Niet instantieerbaar
    }

    /**
     * Haal waarde op.
     *
     * @param waardeObject node waarde
     * @param path path naar waarde
     * @param <T> verwachte resultaat type
     * @return gevonden waarde
     */
    public static <T> T getWaarde(final Object waardeObject, final String path) {
        final String[] pathElementen = path.split("\\.");
        Object testObject = waardeObject;
        String methodName;
        for (final String pathElement : pathElementen) {
            if (testObject != null) {
                methodName = pathElement;
                if (!"ordinal".equals(methodName)) {
                    methodName = "get" + pathElement.substring(0, 1).toUpperCase() + pathElement.substring(1);
                }
                testObject = getMethodeWaarde(testObject, methodName);
            }
        }
        return (T) testObject;
    }

    /**
     * Haal waarde op.
     *
     * @param waardeObject node waarde
     * @param path path naar waarde
     * @param sdf date formatter
     * @return gevonden waarde
     */
    public static String getGeformateerdeWaarde(final Object waardeObject, final String path, final SimpleDateFormat sdf) {
        final Date result = getWaarde(waardeObject, path);
        if (result != null) {
            return sdf.format(result);
        }
        return null;
    }

    private static Object getMethodeWaarde(final Object obj, final String methodName) {
        try {
            final Method method = bepaalMethode(obj.getClass(), methodName);
            method.setAccessible(true);
            return method.invoke(obj);
        } catch (final
            IllegalAccessException
            | IllegalArgumentException
            | InvocationTargetException ex)
        {
            throw new IllegalArgumentException("Kan methode '" + methodName + "' niet uitvoeren.", ex);
        }
    }

    private static Method bepaalMethode(final Class<?> clazz, final String methodName) {
        try {
            return clazz.getDeclaredMethod(methodName);
        } catch (final NoSuchMethodException nsme) {
            if (clazz.getSuperclass() != null) {
                return bepaalMethode(clazz.getSuperclass(), methodName);
            } else {
                throw new IllegalArgumentException("Kan de methode '" + methodName + "' niet vinden.", nsme);
            }
        }
    }
}
