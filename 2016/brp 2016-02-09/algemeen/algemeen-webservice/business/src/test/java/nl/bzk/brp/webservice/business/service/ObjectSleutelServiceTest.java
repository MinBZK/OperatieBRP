/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;


import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ObjectSleutelServiceTest {

    private ObjectSleutelService objectSleutelService;

    @Before
    public void initialiseer() throws Exception {
        objectSleutelService = new ObjectSleutelServiceImpl();
    }

    @Test
    public void testRetourtjeDoorlooptijd()
        throws Exception
    {
        final int partijCode = 23423;

        long startTime = System.currentTimeMillis();
        final String objectSleutelString = objectSleutelService.genereerObjectSleutelString(343454, partijCode);
        objectSleutelService.bepaalPersoonId(objectSleutelString, partijCode);

        // Het zou binnen drie secondes toch wel gepiept moeten zijn.
        long endTime = System.currentTimeMillis();
        Assert.assertTrue(endTime - startTime < 3000);
    }

    @Test
    public void testRetourtjePersoonId()
        throws Exception
    {
        final Integer persoonId = 343454;
        final int partijCode = 23423;

        final String objectSleutelString = objectSleutelService.genereerObjectSleutelString(persoonId, partijCode);
        final Integer result =
                objectSleutelService.bepaalPersoonId(objectSleutelString, partijCode);

        Assert.assertEquals(persoonId, result);
    }

    @Test(expected = ObjectSleutelPartijInvalideExceptie.class)
    public void testObjectSleutelPartijInvalide()
        throws Exception
    {
        final int persoonId = 343454;
        final int partijCode = 23423;

        final String objectSleutelString = objectSleutelService.genereerObjectSleutelString(persoonId, partijCode);
        objectSleutelService.bepaalPersoonId(objectSleutelString, 548);
    }

    @Test(expected = ObjectSleutelVerlopenExceptie.class)
    public void testObjectSleutelVerlopenSleutel() throws Exception {

        final Long nu = new Date().getTime();
        final Long vierenTwintigUurEnEensecondeGeleden = nu - 86401000;
        final ObjectSleutel objectSleutel = new ObjectSleutel(1, vierenTwintigUurEnEensecondeGeleden, 1);

        final String objectSleutelString = genereerObjectSleutelString(objectSleutel);

        objectSleutelService.bepaalPersoonId(objectSleutelString, 1);

    }

    @Test
    public void testObjectSleutelNetNietVerlopenSleutel() throws Exception {
        final Long nu = new Date().getTime();
        final Long vierenTwintigUurMinusEenSecondeGeleden = nu - 86399000;
        final ObjectSleutel objectSleutel = new ObjectSleutel(1, vierenTwintigUurMinusEenSecondeGeleden, 1);

        final String objectSleutelString = genereerObjectSleutelString(objectSleutel);

        objectSleutelService.bepaalPersoonId(objectSleutelString, 1);

        Assert.assertNotNull(objectSleutel);
    }

    @Test
    public void testBepaalPersoonIdIndienObjectSleutelNetNietVerlopenSleutel() throws Exception {
        final Long nu = new Date().getTime();
        final Long vierenTwintigUurMinusEenSecondeGeleden = nu - 86399000;
        final ObjectSleutel objectSleutel = new ObjectSleutel(1, vierenTwintigUurMinusEenSecondeGeleden, 1);

        final String objectSleutelString = genereerObjectSleutelString(objectSleutel);

        final Integer persoonId =
            objectSleutelService.bepaalPersoonId(objectSleutelString, 1);

        Assert.assertNotNull(persoonId);
    }

    private String genereerObjectSleutelString(final ObjectSleutel objectSleutel) throws Exception {
        final byte[] serialize = objectSleutel.serialize();
        final Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec("BRPTango".getBytes("UTF-8"), "DES"));
        //Encrypt:
        byte[] rawBytes = cipher.doFinal(serialize);
        //Encode:
        return Base64.encodeBase64String(rawBytes);
    }

}
