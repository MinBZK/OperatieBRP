/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.serializatie;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import nl.bzk.brp.model.operationeel.kern.PersoonVolledig;
import org.springframework.stereotype.Component;

/**
 * Serializer die gebruik maakt van de <a href="http://jackson.codehaus.org/">Jackson</a> library en
 * het JSON formaat.
 */
@Component
public class JacksonJsonSerializer implements PersoonVolledigSerializer {

    private final ObjectMapper mapper;

    /**
     * Default constructor.
     */
    public JacksonJsonSerializer() {
        this(new JsonFactory());
    }

    /**
     *
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
    public byte[] serializeer(final PersoonVolledig object) throws IOException {
        return mapper.writeValueAsBytes(object);
    }

    @Override
    public PersoonVolledig deserializeer(final byte[] bytes) throws IOException {
        return mapper.readValue(bytes, PersoonVolledig.class);
    }
}
