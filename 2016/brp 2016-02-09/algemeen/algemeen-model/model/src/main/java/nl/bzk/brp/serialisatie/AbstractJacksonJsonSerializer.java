/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeBase;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import nl.bzk.brp.model.hisvolledig.SerialisatieExceptie;


/**
 * Serializer die gebruik maakt van de <a href="http://jackson.codehaus.org/">Jackson</a> library en het JSON formaat.
 *
 * @param <T> Het type voor de serialisatie.
 */
public abstract class AbstractJacksonJsonSerializer<T> implements JacksonJsonSerializer<T> {

    private final ObjectMapper mapper;

    /**
     * @param factory             de jsonFactory die wordt gebruikt
     * @param mappingConfiguratie de mapping configuratie
     * @param filterProvider      de filterProvider
     */
    public AbstractJacksonJsonSerializer(final JsonFactory factory, final SimpleModule mappingConfiguratie,
        final FilterProvider filterProvider) {
        mapper = new ObjectMapper(factory);

        if (mappingConfiguratie != null) {
            mapper.registerModule(mappingConfiguratie);
        }
        if (filterProvider != null) {
            mapper.setFilters(filterProvider);
        }

        // DELTA-1109: Hibernate4Module, ter voorkoming van serialisatie van niet geladen HibernateProxy instanties
        final Hibernate4Module hibernate4Module = new Hibernate4Module();
        hibernate4Module.disable(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION);
        hibernate4Module.enable(Hibernate4Module.Feature.FORCE_LAZY_LOADING);
        hibernate4Module.disable(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION);
        mapper.registerModule(hibernate4Module);

        mapper.disable(MapperFeature.AUTO_DETECT_GETTERS);
        mapper.disable(MapperFeature.AUTO_DETECT_CREATORS);
        mapper.disable(MapperFeature.AUTO_DETECT_SETTERS);
        mapper.disable(MapperFeature.AUTO_DETECT_IS_GETTERS);
        mapper.disable(MapperFeature.AUTO_DETECT_FIELDS);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.NONE);

        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        mapper.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
        mapper.enable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS);

        // serialization
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.enable(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        // deserialization
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    }

    /**
     * Serialiseert een object van type T.
     *
     * @param object Het te serialiseren object.
     * @return Het geserialiseerde object als byte array.
     */
    @Override
    public byte[] serialiseer(final T object) {
        try {
            return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new SerialisatieExceptie("Het serialiseren van het object is mislukt.", e);
        }
    }

    /**
     * Deserialiseert een object van type T.
     *
     * @param bytes Het geserialiseerde object
     * @return Het gedeserialiseerde object
     */
    @Override
    public T deserialiseer(final byte[] bytes) {
        try {
            return mapper.readValue(bytes, getType());
        } catch (IOException e) {
            throw new SerialisatieExceptie("Het deserialiseren van het object is mislukt.", e);
        }
    }

    /**
     * Deserialiseert een object van type <code>T</code>. Hierbij is <code>type</code> een sub-klasse van <code>T</code> .
     *
     * @param bytes het geserialiseerde object
     * @param type  het specifieke type van T
     * @return Het gedeserialiseerde object
     * @throws IOException Als het deserialiseren mislukt
     */
    public final T deserialiseer(final byte[] bytes, final TypeBase type) throws IOException {
        return mapper.readValue(bytes, type);
    }

    /**
     * Deze methode geeft het type terug van de klasse die ge-deserialiseerd moet worden. Dit kan zowel een normale klasse zijn, als een generic
     * collectie.
     *
     * @return Het type van de klasse.
     */
    private TypeBase getType() {
        final ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();

        final Type type = superclass.getActualTypeArguments()[0];

        final Type classType;
        if (type instanceof ParameterizedType) {
            classType = ((ParameterizedType) type).getRawType();
            final Type genericSubType = ((ParameterizedType) type).getActualTypeArguments()[0];
            return mapper.getTypeFactory().constructCollectionType((Class) classType, (Class) genericSubType);
        } else {
            classType = superclass.getActualTypeArguments()[0];
            return SimpleType.construct((Class) classType);
        }
    }

    /**
     * Geeft de objectmapper voor deze klasse.
     *
     * @return De objectmapper.
     */
    public final ObjectMapper getMapper() {
        return mapper;
    }

}
