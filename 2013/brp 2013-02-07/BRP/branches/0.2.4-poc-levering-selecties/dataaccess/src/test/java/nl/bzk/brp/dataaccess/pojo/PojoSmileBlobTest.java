/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.pojo;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.blob.PlBlobOpslagplaats;
import nl.bzk.brp.dataaccess.repository.jpa.serialization.BlobSerializer;
import nl.bzk.brp.model.blob.PlBlob;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.pojo.PersoonHisModel;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PojoSmileBlobTest extends AbstractRepositoryTestCase {
    private final Logger LOGGER = LoggerFactory.getLogger(PojoSmileBlobTest.class);

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private PlBlobOpslagplaats blobOpslagplaats;

    @Inject
    @Named("jacksonSmileBlobSerializer")
    private BlobSerializer blobSerializer;

    @Test
    public void getPersoonBlobLeesGenormaliseerdModel() throws Exception {
        //given
        PersoonModel persoon = persoonRepository.haalPersoonMetAdres(1001);
        PersoonHisModel hisModel = blobOpslagplaats.leesPlBlob(1001);

        Assert.assertEquals("Gelezen adressen", 2, hisModel.getAdressen().size());
        Assert.assertEquals("Gelezen geslachtsaanduiding", 0, hisModel.getGeslachtsaanduiding().size());

        // when
        byte[] json = blobSerializer.serializeObject(hisModel);
        LOGGER.info(String.format("HisModel: %s \nSize: %s", new String(json), json.length));

        PlBlob blob = new PlBlob();
        blob.setPersoon(persoon);
        blob.setPl(json);

        PersoonHisModel copy = blobSerializer.deserializeObject(blob);

        // then
        Assert.assertNotNull("gedeserializeerde adressen", copy.getAdressen());
        Assert.assertEquals("gelezen adressen is gelijk aan gedeserializeerde adressen", hisModel.getAdressen().size(), copy.getAdressen().size());
    }

}
