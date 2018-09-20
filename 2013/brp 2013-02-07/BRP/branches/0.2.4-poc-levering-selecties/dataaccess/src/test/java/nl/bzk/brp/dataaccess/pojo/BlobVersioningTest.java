/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.pojo;

import java.io.IOException;

import javax.inject.Inject;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.repository.blob.PlBlobOpslagplaats;
import nl.bzk.brp.dataaccess.repository.jpa.serialization.MappingOverrideModule;
import nl.bzk.brp.model.objecttype.pojo.PersoonHisModel;
import nl.bzk.brp.model.objecttype.pojo.VersionedHis;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: Add documentation
 */
public class BlobVersioningTest extends AbstractRepositoryTestCase {

    @Inject
    private PlBlobOpslagplaats blobOpslagplaats;

    private ObjectMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(BlobVersioningTest.class);

    @Before
    public void onSetUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new MappingOverrideModule());

        mapper.disable(MapperFeature.AUTO_DETECT_GETTERS, MapperFeature.AUTO_DETECT_CREATORS,
                       MapperFeature.AUTO_DETECT_SETTERS, MapperFeature.AUTO_DETECT_IS_GETTERS);

        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        mapper.enable(MapperFeature.AUTO_DETECT_FIELDS, MapperFeature.SORT_PROPERTIES_ALPHABETICALLY,
                      MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS);

        // serialization
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    @Test
    public void deserializeerMetVersieCheck() throws Exception {
        // given
        PersoonHisModel hisPersoon = getPersoonHisModel();
        byte[] bits = mapper.writeValueAsBytes(hisPersoon);

        // when
        ObjectReader versionReader =
                mapper.reader(VersionedHis.class).without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        VersionedHis versioned = versionReader.readValue(bits);

        PersoonHisModel deserializedHisPersoon = mapper.readValue(bits, PersoonHisModel.class);
        LOGGER.info("versionedhis: '{}'", versioned);

        // then
        Assert.assertEquals(hisPersoon.getVersion(), versioned.getVersion());
        Assert.assertEquals(hisPersoon.getVersion(), deserializedHisPersoon.getVersion());
        Assert.assertEquals(hisPersoon.getAdressen().size(), deserializedHisPersoon.getAdressen().size());
    }

    private PersoonHisModel getPersoonHisModel() throws IOException, ClassNotFoundException {
        return blobOpslagplaats.leesPlBlob(1001);
    }
}
