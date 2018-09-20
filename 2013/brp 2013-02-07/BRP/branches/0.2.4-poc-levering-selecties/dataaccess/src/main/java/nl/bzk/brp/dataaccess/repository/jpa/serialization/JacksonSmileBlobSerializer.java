/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.serialization;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.dataformat.smile.SmileGenerator;
import nl.bzk.brp.model.blob.PlBlob;
import nl.bzk.brp.model.objecttype.pojo.PersoonHisModel;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class JacksonSmileBlobSerializer implements BlobSerializer {
    private ObjectMapper mapper;

    public JacksonSmileBlobSerializer() {
        SmileFactory f = new SmileFactory();
        f.enable(SmileGenerator.Feature.CHECK_SHARED_NAMES);
        f.enable(SmileGenerator.Feature.CHECK_SHARED_STRING_VALUES);
        f.enable(SmileGenerator.Feature.WRITE_HEADER);

        mapper = new ObjectMapper(f);
        mapper.registerModule(new MappingOverrideModule());

        mapper.disable(MapperFeature.AUTO_DETECT_GETTERS);
        mapper.disable(MapperFeature.AUTO_DETECT_CREATORS);
        mapper.disable(MapperFeature.AUTO_DETECT_SETTERS);
        mapper.disable(MapperFeature.AUTO_DETECT_IS_GETTERS);

        //mapper.enable(MapperFeature.AUTO_DETECT_FIELDS);
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

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
    public byte[] serializeObject(final PersoonHisModel model) throws IOException {
        return mapper.writeValueAsBytes(model);
    }

    @Override
    public PersoonHisModel deserializeObject(final PlBlob blob) throws IOException, ClassNotFoundException {
        return mapper.readValue(blob.getPl(), PersoonHisModel.class);
    }
}
