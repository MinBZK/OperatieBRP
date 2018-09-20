/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Abstract converter (uses unknown types).
 *
 * @param <O> OUT
 */
public abstract class AbstractIdConverter<O> extends AbstractConverter<Integer, O> {

    private final Constructor<O> valueConstructor;
    private final Field valueIdField;

    /**
     * Constructor.
     *
     * @param valueClazz waarde klasse
     */
    protected AbstractIdConverter(final Class<O> valueClazz) {
        try {
            valueConstructor = valueClazz.getDeclaredConstructor();
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException("Kan value constructor niet vinden", e);
        }
        valueConstructor.setAccessible(true);
        valueIdField = findField(valueClazz, "iD");
        valueIdField.setAccessible(true);

    }

    @Override
    public final O convert(final Integer input) {
        final O value;
        try {
            value = valueConstructor.newInstance();
            valueIdField.set(value, input.shortValue());
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException("Kan iD niet zetten", e);
        }
        return value;
    }

    private static Field findField(final Class<?> clazz, final String field) {
        try {
            return clazz.getDeclaredField(field);
        } catch (final NoSuchFieldException nsfe) {
            if (clazz.getSuperclass() != null) {
                return findField(clazz.getSuperclass(), field);
            } else {
                throw new IllegalArgumentException("Kan " + field + " niet vinden.", nsfe);
            }
        }

    }
}
