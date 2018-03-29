/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Reflection utilities.
 */
public final class ReflectionUtils {

    private static final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS = new HashMap<>();

    static {
        PRIMITIVES_TO_WRAPPERS.put(boolean.class, Boolean.class);
        PRIMITIVES_TO_WRAPPERS.put(byte.class, Byte.class);
        PRIMITIVES_TO_WRAPPERS.put(char.class, Character.class);
        PRIMITIVES_TO_WRAPPERS.put(double.class, Double.class);
        PRIMITIVES_TO_WRAPPERS.put(float.class, Float.class);
        PRIMITIVES_TO_WRAPPERS.put(int.class, Integer.class);
        PRIMITIVES_TO_WRAPPERS.put(long.class, Long.class);
        PRIMITIVES_TO_WRAPPERS.put(short.class, Short.class);
    }

    private ReflectionUtils() {
        // Niet instantieerbaar
    }

    /**
     * Zet het gegeven object (field, method, constructor) als toegankelijk.
     * @param accessibleObject object
     */
    public static void setAccessible(final AccessibleObject accessibleObject) {
        if (accessibleObject != null) {
            accessibleObject.setAccessible(true);
        }
    }

    /**
     * Geef alle velden van een klasse (inclusief velden uit super klassen, exclusief
     * java.lang.Object).
     * @param clazz klasse
     * @return lijst van alle velden
     */
    public static Field[] getAllFields(final Class<?> clazz) {
        final java.util.List<Field> result = new ArrayList<>();

        Class<?> theClazz = clazz;
        while (theClazz != null && !Object.class.equals(theClazz)) {
            for (final Field field : theClazz.getDeclaredFields()) {
                if (!field.isSynthetic()) {
                    result.add(field);
                }
            }
            theClazz = theClazz.getSuperclass();
        }

        return result.toArray(new Field[]{});
    }

    /**
     * Geef alle methoden uit een klasse (inclusief methoden uit super klassen, exclusief
     * java.lang.Object) die voldoen aan de volgende voorwaarden:
     * <ol>
     * <li>Geen parameters</li>
     * <li>Return type die ongelijk is aan {@code void}.</li>
     * <li>Een 'echte' methode ({@link Method#isSynthetic()} is false)</li>
     * </ol>
     * @param clazz klasse
     * @return lijst van methoden
     */
    public static Method[] getAllGetters(final Class<?> clazz) {
        final Map<String, Method> result = new LinkedHashMap<>();

        Class<?> theClazz = clazz;
        while (theClazz != null && !Object.class.equals(theClazz)) {
            for (final Method method : theClazz.getDeclaredMethods()) {
                if (!method.isSynthetic() && method.getParameterTypes().length == 0 && method.getReturnType() != Void.class
                        && !result.containsKey(method.getName())) {
                    result.put(method.getName(), method);
                }
            }
            theClazz = theClazz.getSuperclass();
        }

        return result.values().toArray(new Method[]{});

    }

    /**
     * Geef alle methoden uit een klasse (inclusief methoden uit super klassen, exclusief
     * java.lang.Object) die voldoen aan de volgende voorwaarden:
     * <ol>
     * <li>Exact 1 parameter</li>
     * <li>Return type die gelijk is aan {@code void}.</li>
     * <li>Een 'echte' methode ({@link Method#isSynthetic()} is false)</li>
     * </ol>
     * @param clazz klasse
     * @return lijst van methoden
     */
    public static Method[] getAllSetters(final Class<?> clazz) {
        final Map<String, Method> result = new LinkedHashMap<>();

        Class<?> theClazz = clazz;
        while (theClazz != null && !Object.class.equals(theClazz)) {
            for (final Method method : theClazz.getDeclaredMethods()) {
                if (!method.isSynthetic() && method.getParameterTypes().length == 1 && Void.TYPE.equals(method.getReturnType())
                        && !result.containsKey(method.getName())) {
                    result.put(method.getName(), method);
                }
            }
            theClazz = theClazz.getSuperclass();
        }

        return result.values().toArray(new Method[]{});
    }

    /**
     * Bepaal of twee klassen gelijk zijn aan elkaar waarbij geen onderscheid wordt gemaakt tussen
     * primitives en hun wrapper objecten.
     * @param clazz1 klasse
     * @param clazz2 klasse
     * @return true, indien de klassen gelijk zijn
     */
    public static boolean isSameClass(final Class<? extends Object> clazz1, final Class<?> clazz2) {
        final Class<?> nonPrimitiveClass1 = clazz1.isPrimitive() ? PRIMITIVES_TO_WRAPPERS.get(clazz1) : clazz1;
        final Class<?> nonPrimitiveClass2 = clazz2.isPrimitive() ? PRIMITIVES_TO_WRAPPERS.get(clazz2) : clazz2;
        return nonPrimitiveClass1.equals(nonPrimitiveClass2);
    }

    /**
     * Geef de default constructor van een klasse.
     * @param clazz klasse
     * @param <T> type
     * @return default constructor
     */
    public static <T> Constructor<T> getDefaultConstructor(final Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor();
        } catch (final ReflectiveOperationException e) {
            return null;
        }
    }

    /**
     * Geef de string constructor van een klasse.
     * @param clazz klasse
     * @param <T> type
     * @return string constructor
     */
    public static <T> Constructor<T> getStringConstructor(final Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor(String.class);
        } catch (final ReflectiveOperationException e) {
            return null;
        }
    }
}
