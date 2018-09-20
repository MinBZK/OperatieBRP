/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

/**
 * Factory voor data object converters.
 */
@Component
public final class DataObjectConverterFactory {
    private final Map<String, DataObjectConverter> converters = new HashMap<>();

    /**
     * Constructor.
     * 
     * @param dataConverters
     *            lijst van data object converters.
     */
    @Inject
    public DataObjectConverterFactory(final List<DataObjectConverter> dataConverters) {
        for (final DataObjectConverter converter : dataConverters) {
            converters.put(converter.getType(), converter);
        }
    }

    /**
     * Geef de converter voor het gegevens data object. De converter wordt bepaalt aan de hand van de waarde van de
     * eerste header.
     * 
     * @param dataObject
     *            data object
     * @return converter
     * @throws IllegalArgumentException
     *             als er geen converter beschikbaar is
     */
    public DataObjectConverter getConverter(final DataObject dataObject) {
        final String discriminator = dataObject.getHeaders().get(0);

        if (converters.containsKey(discriminator)) {
            return converters.get(discriminator);
        } else {
            throw new IllegalArgumentException("Geen converter voor '" + discriminator + "'.");
        }

    }
}
