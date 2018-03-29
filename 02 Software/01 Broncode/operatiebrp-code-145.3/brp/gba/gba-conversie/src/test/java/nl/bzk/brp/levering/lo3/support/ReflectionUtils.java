/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.support;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Reflection utilities
 */
public final class ReflectionUtils {

    private ReflectionUtils() {
        // Niet instantieerbaar
    }

    /**
     * Instantieer een klasse; zoekt de juiste constructor (hoeft niet public te zijn) obv de gegevens argumenten.
     *
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
        // System.out.println("Constructors: " + constructors.size());
        removeConstructorWithInvalidArgumentLengths(constructors, arguments.length);
        // System.out.println("Constructors (na aantal controle): " + constructors.size());
        for (int i = 0; i < arguments.length; i++) {
            removeConstructorWithIncompatibleArgument(constructors, i, arguments[i]);
            // System.out.println("Constructors (na controle argument " + i + "): " + constructors.size());
        }

        if (constructors.isEmpty()) {
            throw new NoSuchMethodException("Geen constructor gevonden die matched met de gegeven argumenten voor class: " + clazz.getName());
        }
        if (constructors.size() > 1) {
            throw new NoSuchMethodException("Meer dan 1 constructor gevonden voor class die matched met de gegeven argumenten voor class: "
                + clazz.getName());
        }

        return (Constructor<T>) constructors.get(0);
    }

    private static void removeConstructorWithInvalidArgumentLengths(final List<Constructor<?>> constructors, final int length) {
        final Iterator<Constructor<?>> iterator = constructors.iterator();
        while (iterator.hasNext()) {
            final Constructor<?> constructor = iterator.next();
            if (constructor.getParameterTypes().length != length) {
                iterator.remove();
            }
        }
    }

    private static void removeConstructorWithIncompatibleArgument(final List<Constructor<?>> constructors, final int i, final Object argument) {
        if (argument == null) {
            return;
        }

        final Iterator<Constructor<?>> iterator = constructors.iterator();
        while (iterator.hasNext()) {
            final Constructor<?> constructor = iterator.next();
            final Class<?> argumentType = constructor.getParameterTypes()[i];

            if (!argumentType.isInstance(argument)) {
                // System.out.println("Constructor verwijderd. Argumenttype: " + argumentType.getName() +
                // ", gegeven argument: " + argument.getClass().getName());
                iterator.remove();
            }
        }
    }

}
