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
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Default converter die gebruik maakt van de {@code toString()} method om te encoderen en voor
 * decoderen een constructor verwacht met 1 string parameter.
 * @param <T> te converteren type
 */
public final class ToStringConverter<T extends Object> implements Converter<T> {

    private final Class<T> clazz;
    private final Constructor<T> constructor;

    /**
     * Constructor.
     * @param clazz klasse
     */
    ToStringConverter(final Class<T> clazz) {
        this.clazz = clazz;
        constructor = ReflectionUtils.getStringConstructor(clazz);
        ReflectionUtils.setAccessible(constructor);
    }

    @Override
    public String encode(final Context context, final T value) {
        return StringEscapeUtils.escapeXml11(value.toString());
    }

    @Override
    public T decode(final Context context, final String value) throws DecodeException {
        if (constructor != null) {
            try {
                return constructor.newInstance(value);
            } catch (final ReflectiveOperationException e) {
                throw new DecodeException(context.getElementStack(), e);
            }
        } else {
            throw new DecodeException(context.getElementStack(), "Geen String constructor voor " + clazz);
        }
    }

}
