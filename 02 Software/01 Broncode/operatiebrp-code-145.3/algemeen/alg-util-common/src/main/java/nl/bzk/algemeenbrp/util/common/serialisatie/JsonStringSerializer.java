/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.common.serialisatie;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;

/**
 * Serialiseerder die serialiseert naar een JSON formaat.
 */
public class JsonStringSerializer extends AbstractJacksonJsonSerializer {

    /**
     * Serialiseert een object van type T.
     * @param object Het te serialiseren object.
     * @return Het geserialiseerde object als String.
     */
    public final String serialiseerNaarString(final Object object) {
        try {
            return getWriter().writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            throw new SerialisatieExceptie("Het serialiseren van het object is mislukt.", e);
        }
    }

    /**
     * Deserialiseert een object van type T.
     * @param jsonString Het geserialiseerde object
     * @param clazz class van het geserialiseerde object
     * @param <T> type van het geserialiseerde object
     * @return Het gedeserialiseerde object
     */
    public final <T> T deserialiseerVanuitString(final String jsonString, final Class<T> clazz) {
        try {
            return getReader().forType(clazz).readValue(jsonString);
        } catch (final IOException e) {
            throw new SerialisatieExceptie("Het deserialiseren van het object is mislukt.", e);
        }
    }
}
