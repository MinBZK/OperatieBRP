/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import nl.bzk.brp.model.basis.Attribuut;

/**
 * Abstract converter (uses unknown types).
 *
 * @param <O> OUT
 */
public abstract class AbstractAttribuutIdConverter<O extends Attribuut<?>> extends AbstractConverter<Integer, O> {

    private final Constructor<?> valueConstructor;
    private final Field valueIdField;
    private final Constructor<O> attribuutConstructor;

    /**
     * Constructor.
     *
     * @param attribuutClazz attribuut klasse
     * @param valueClazz waarde klasse
     */
    protected AbstractAttribuutIdConverter(final Class<O> attribuutClazz, final Class<?> valueClazz) {
        try {
            valueConstructor = valueClazz.getDeclaredConstructor();
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException("Kan value constructor niet vinden", e);
        }
        valueConstructor.setAccessible(true);
        valueIdField = findField(valueClazz, "iD");
        valueIdField.setAccessible(true);

        try {
            attribuutConstructor = attribuutClazz.getConstructor(valueClazz);
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException("Kan attribuut constructor niet vinden", e);
        }
    }

    @Override
    public final O convert(final Integer input) {
        final Object value;
        try {
            value = valueConstructor.newInstance();
            valueIdField.set(value, input.shortValue());
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException("Kan iD niet zetten", e);
        }

        try {
            return attribuutConstructor.newInstance(value);
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException("Kan attribuut niet instantieren", e);
        }
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
