/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.converter;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.xml.util.ReflectionUtils;

/**
 * Factory voor {@link Converter}s.
 */
public final class ConverterFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private ConverterFactory() {
        // Niet instantieerbaar
    }

    /**
     * Geef de {@link Converter} voor de gegeven klasse.
     *
     * @param clazz klasse
     * @param <T> type van de klasse
     *
     * @return {@link Converter}
     */
    public static <T> Converter<T> getConverter(final Class<T> clazz) {
        final Converter<T> result;

        if (Enum.class.isAssignableFrom(clazz)) {
            final Class<? extends Enum> enumClazz = (Class<? extends Enum>) clazz;
            result = (Converter<T>) new EnumConverter<>(enumClazz);
        } else if (isShort(clazz)) {
            result = (Converter<T>) new ShortConverter();
        } else if (isInteger(clazz)) {
            result = (Converter<T>) new IntegerConverter();
        } else if (isLong(clazz)) {
            result = (Converter<T>) new LongConverter();
        } else if (isBoolean(clazz)) {
            result = (Converter<T>) new BooleanConverter();
        } else if (isCharacter(clazz)) {
            result = (Converter<T>) new CharacterConverter();
        } else if (String.class.equals(clazz)) {
            result = (Converter<T>) new StringConverter();
        } else if (ReflectionUtils.getStringConstructor(clazz) != null) {
            result = new ToStringConverter<>(clazz);
        } else {
            result = new DefaultConverter<>(clazz);
        }

        LOGGER.debug("getConverter(clazz={}) -> {}", clazz, result);

        return result;
    }

    private static <T> boolean isCharacter(final Class<T> clazz) {
        return Character.class.equals(clazz) || Character.TYPE.equals(clazz);
    }

    private static <T> boolean isBoolean(final Class<T> clazz) {
        return Boolean.class.equals(clazz) || Boolean.TYPE.equals(clazz);
    }

    private static <T> boolean isLong(final Class<T> clazz) {
        return Long.class.equals(clazz) || Long.TYPE.equals(clazz);
    }

    private static <T> boolean isInteger(final Class<T> clazz) {
        return Integer.class.equals(clazz) || Integer.TYPE.equals(clazz);
    }

    private static <T> boolean isShort(final Class<T> clazz) {
        return Short.class.equals(clazz) || Short.TYPE.equals(clazz);
    }
}
