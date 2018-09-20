/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

/**
 * Factory voor property converters.
 */
@Component
public final class PropertyConverterFactory {
    private final Map<Class<?>, PropertyConverter<?>> converters = new HashMap<Class<?>, PropertyConverter<?>>();

    /**
     * Constructor.
     * 
     * @param propertyConverters
     *            alle property converters
     */
    @Inject
    public PropertyConverterFactory(final List<PropertyConverter<?>> propertyConverters) {
        for (final PropertyConverter<?> converter : propertyConverters) {
            converters.put(converter.getType(), converter);
        }
    }

    /**
     * Shortcut om de juiste property converter te zoeken en de gegeven waarde te converteren.
     * 
     * @param <T>
     *            type
     * @param clazz
     *            verwachte class
     * @param value
     *            string waarde
     * @return 'typed' waardes
     * 
     */
    @SuppressWarnings("unchecked")
    public <T> T convert(final Class<T> clazz, final String value) {
        final PropertyConverter<?> converter = converters.get(clazz);
        if (converter == null) {
            throw new AssertionError("Geen property converter voor '" + clazz.getName() + "'.");

        }

        final Object result = converter.convert(value);

        return (T) result;
    }
}
