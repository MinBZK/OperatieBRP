/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.common.serialisatie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;

/**
 * Serializer die gebruik maakt van de <a href="http://jackson.codehaus.org/">Jackson</a> library en
 * het JSON formaat.
 */
public abstract class AbstractJacksonJsonSerializer implements JacksonJsonSerializer {

    private ObjectWriter writer = JsonMapper.writer();
    private ObjectReader reader = JsonMapper.reader();

    /**
     * Serialiseert een object van type T.
     * @param object Het te serialiseren object.
     * @return Het geserialiseerde object als byte array.
     */
    @Override
    public final byte[] serialiseer(final Object object) {
        try {
            return writer.writeValueAsBytes(object);
        } catch (final JsonProcessingException e) {
            throw new SerialisatieExceptie("Het serialiseren van het object is mislukt.", e);
        }
    }

    /**
     * Deserialiseert een object van type T.
     * @param bytes Het geserialiseerde object
     * @return Het gedeserialiseerde object
     */
    @Override
    public final <T> T deserialiseer(final byte[] bytes, final Class<?> clazz) {
        try {
            return reader.forType(clazz).readValue(bytes);
        } catch (final IOException e) {
            throw new SerialisatieExceptie("Het deserialiseren van het object is mislukt.", e);
        }
    }

    /**
     * Geeft de objectwriter voor deze klasse.
     * @return De objectwriter.
     */
    public final ObjectWriter getWriter() {
        return writer;
    }

    /**
     * Geeft de objectreader voor deze klasse.
     * @return De objectreader.
     */
    public final ObjectReader getReader() {
        return reader;
    }
}
