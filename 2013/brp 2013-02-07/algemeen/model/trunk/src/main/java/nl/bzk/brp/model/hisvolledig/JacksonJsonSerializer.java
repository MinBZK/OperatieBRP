/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;

/**
 * Serializer die gebruik maakt van de <a href="http://jackson.codehaus.org/">Jackson</a> library en
 * het JSON formaat.
 */
public class JacksonJsonSerializer implements PersoonHisVolledigSerializer {

    private final ObjectMapper mapper;

    /**
     * Default constructor.
     */
    public JacksonJsonSerializer() {
        this(new JsonFactory());
    }

    /**
     * @param factory de jsonFactory die wordt gebruikt
     */
    protected JacksonJsonSerializer(final JsonFactory factory) {
        mapper = new ObjectMapper(factory);

        mapper.registerModule(new MappingConfiguratieModule());

        mapper.disable(MapperFeature.AUTO_DETECT_GETTERS);
        mapper.disable(MapperFeature.AUTO_DETECT_CREATORS);
        mapper.disable(MapperFeature.AUTO_DETECT_SETTERS);
        mapper.disable(MapperFeature.AUTO_DETECT_IS_GETTERS);
        mapper.disable(MapperFeature.AUTO_DETECT_FIELDS);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.NONE);

        mapper.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
        mapper.enable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS);

        // serialization
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        // deserialization
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Override
    public byte[] serializeer(final PersoonHisVolledig object) throws IOException {
        return mapper.writeValueAsBytes(object);
    }

    @Override
    public PersoonHisVolledig deserializeer(final byte[] bytes) throws IOException {
        return mapper.readValue(bytes, PersoonHisVolledig.class);
    }
}
