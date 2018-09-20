/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.testutils;

import java.lang.reflect.Field;

/**
 * Test utilities.
 */
public final class ReflectionUtil {

    private ReflectionUtil() {
        throw new AssertionError("Niet instantieerbaar");
    }

    public static Object getField(final Object object, final String fieldName) {
        try {
            final Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
            // CHECKSTYLE:OFF
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            throw new RuntimeException(e);
        }
    }
}
