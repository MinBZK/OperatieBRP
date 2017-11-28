/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.common.serialisatie;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Arrays;

/**
 * Utility class for return ObjectWriter and ObjectReader objects for writing object to and reading object from json respectively.
 */
public final class JsonMapper {
    private static final ObjectMapper mapper = initializeMapper();

    private JsonMapper() {
        // private constructor.
    }

    /**
     * Returns an ObjectReader with the project default settings.
     * @param features the list of DeserializationFeatures to be enabled, overwriting the project defaults
     * @return ObjectReader
     */
    public static ObjectReader reader(DeserializationFeature... features) {
        if (features.length == 0) {
            return mapper.reader();
        } else if (features.length == 1) {
            return mapper.reader(features[0]);
        } else {
            return mapper.reader(features[0], Arrays.copyOfRange(features, 1, features.length));
        }
    }

    /**
     * Returns an ObjectWriter with the project default settings.
     * @param features the SerializationFeatures to be enabled, overwriting the project defaults
     * @return ObjectWriter
     */
    public static ObjectWriter writer(SerializationFeature... features) {
        if (features.length == 0) {
            return mapper.writer();
        } else if (features.length == 1) {
            return mapper.writer(features[0]);
        } else {
            return mapper.writer(features[0], Arrays.copyOfRange(features, 1, features.length));
        }
    }

    private static ObjectMapper initializeMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())

                .disable(MapperFeature.AUTO_DETECT_GETTERS)
                .disable(MapperFeature.AUTO_DETECT_CREATORS)
                .disable(MapperFeature.AUTO_DETECT_SETTERS)
                .disable(MapperFeature.AUTO_DETECT_IS_GETTERS)
                .disable(MapperFeature.AUTO_DETECT_FIELDS)
                .disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.NONE)
                .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .enable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)

                // serialization
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .enable(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID)
                .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

                // deserialization
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
    }
}
