/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import nl.bzk.brp.model.hisvolledig.SerialisatieExceptie;

/**
 * Serialiseerder die serialiseert naar een JSON formaat.
 *
 * @param <T> Het type dat deze serialiseerder kan (de)serialiseren
 */
public class JsonStringSerializer<T> extends AbstractJacksonJsonSerializer<T> {

    private Class<T> typeParameterClass;

    /**
     * Constructor voor een serializer.
     *
     * @param typeParameterClass het type dat deze serializer kan (de)serializeren
     */
    public JsonStringSerializer(final Class<T> typeParameterClass) {
        super(new JsonFactory(), null, null);
        this.typeParameterClass = typeParameterClass;
    }

    /**
     * Serialiseert een object van type T.
     *
     * @param object Het te serialiseren object.
     * @return Het geserialiseerde object als String.
     */
    public String serialiseerNaarString(final T object) {
        try {
            return getMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new SerialisatieExceptie("Het serialiseren van het object is mislukt.", e);
        }
    }

    /**
     * Deserialiseert een object van type T.
     *
     * @param jsonString Het geserialiseerde object
     * @return Het gedeserialiseerde object
     */
    public T deserialiseerVanuitString(final String jsonString) {
        try {
            return getMapper().readValue(jsonString, this.typeParameterClass);
        } catch (IOException e) {
            throw new SerialisatieExceptie("Het deserialiseren van het object is mislukt.", e);
        }
    }

}
