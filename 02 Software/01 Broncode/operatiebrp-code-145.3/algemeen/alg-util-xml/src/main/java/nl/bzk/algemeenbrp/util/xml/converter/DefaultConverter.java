/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.converter;

import java.lang.reflect.Constructor;
import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.exception.DecodeException;
import nl.bzk.algemeenbrp.util.xml.util.ReflectionUtils;

/**
 * Default converter die gebruik maakt van een lege String te encoderen en de default constructor
 * voor decoderen.
 *
 * @param <T> te converteren type
 */
public final class DefaultConverter<T extends Object> implements Converter<T> {

    private final Class<T> clazz;
    private final Constructor<T> constructor;

    /**
     * Constructor.
     *
     * @param clazz klasse
     */
    DefaultConverter(final Class<T> clazz) {
        this.clazz = clazz;
        constructor = ReflectionUtils.getDefaultConstructor(clazz);
        ReflectionUtils.setAccessible(constructor);
    }

    @Override
    public String encode(final Context context, final T value) {
        return "";
    }

    @Override
    public T decode(final Context context, final String value) throws DecodeException {
        if (constructor != null) {
            try {
                return constructor.newInstance();
            } catch (final ReflectiveOperationException e) {
                throw new DecodeException(context.getElementStack(), e);
            }
        } else {
            throw new DecodeException(context.getElementStack(), "Geen default constructor voor " + clazz);
        }
    }

}
