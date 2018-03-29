/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package support;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * Reflection utilities
 */
public final class ReflectionUtils {

    private static final String DIE_MATCHED_MET_ARGUMENTEN = " die matched met de gegeven argumenten.";
    private static final String FIELD = "Field '";
    private static final String NIET_GEVONDEN = "' niet gevonden.";

    private ReflectionUtils() {
        // Niet instantieerbaar
    }

    /**
     * Instantieer een klasse; zoekt de juiste constructor (hoeft niet public te zijn) obv de gegevens argumenten.
     * @param clazz klasse
     * @param arguments argumenten
     * @param <T> klasse
     * @return instantie
     * @throws ReflectiveOperationException bij fouten
     */
    public static <T> T instantiate(final Class<T> clazz, final Object... arguments) throws ReflectiveOperationException {
        final Constructor<T> constructor = findConstructor(clazz, arguments);
        constructor.setAccessible(true);
        return constructor.newInstance(arguments);
    }

    @SuppressWarnings("unchecked")
    private static <T> Constructor<T> findConstructor(final Class<T> clazz, final Object[] arguments) throws ReflectiveOperationException {
        final List<Constructor<?>> constructors = new ArrayList<>(Arrays.asList(clazz.getDeclaredConstructors()));
        removeConstructorWithInvalidArgumentLengths(constructors, arguments.length);
        for (int i = 0; i < arguments.length; i++) {
            removeConstructorWithIncompatibleArgument(constructors, i, arguments[i]);
        }

        if (constructors.isEmpty()) {
            throw new NoSuchMethodException("Geen constructor gevonden voor class " + clazz.getName()
                    + DIE_MATCHED_MET_ARGUMENTEN);
        }
        if (constructors.size() > 1) {
            throw new NoSuchMethodException("Meer dan 1 constructor gevonden voor class " + clazz.getName()
                    + DIE_MATCHED_MET_ARGUMENTEN);
        }

        return (Constructor<T>) constructors.get(0);
    }

    private static void removeConstructorWithInvalidArgumentLengths(final List<Constructor<?>> constructors,
                                                                    final int length) {
        final Iterator<Constructor<?>> iterator = constructors.iterator();
        while (iterator.hasNext()) {
            final Constructor<?> constructor = iterator.next();
            if (constructor.getParameterTypes().length != length) {
                iterator.remove();
            }
        }
    }

    private static void removeConstructorWithIncompatibleArgument(final List<Constructor<?>> constructors, final int i,
                                                                  final Object argument) {
        if (argument == null) {
            return;
        }

        final Iterator<Constructor<?>> iterator = constructors.iterator();
        while (iterator.hasNext()) {
            final Constructor<?> constructor = iterator.next();
            final Class<?> argumentType = constructor.getParameterTypes()[i];

            if (!argumentType.isInstance(argument)) {
                iterator.remove();
            }
        }
    }

    /**
     * Find a field in a (super)class.
     * @param object object
     * @param field field name
     * @return field, or null if not found
     */
    public static Field findField(final Object object, final String field) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(field);
            } catch (final NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }

    /**
     * Set a field value.
     * @param object object
     * @param fieldName field name
     * @param value value
     * @throws ReflectiveOperationException on errors.
     */
    public static void setField(final Object object, final String fieldName, final Object value) throws ReflectiveOperationException {
        final Field field = findField(object, fieldName);
        if (field != null) {
            field.setAccessible(true);
            field.set(object, value);
        } else {
            throw new ReflectiveOperationException(FIELD + fieldName + NIET_GEVONDEN);
        }
    }

    /**
     * Get a field value.
     * @param object object
     * @param fieldName field name
     * @return value
     * @throws ReflectiveOperationException on errors.
     */
    public static Object getField(final Object object, final String fieldName) throws ReflectiveOperationException {
        final Field field = findField(object, fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        } else {
            throw new ReflectiveOperationException(FIELD + fieldName + NIET_GEVONDEN);
        }
    }

}
