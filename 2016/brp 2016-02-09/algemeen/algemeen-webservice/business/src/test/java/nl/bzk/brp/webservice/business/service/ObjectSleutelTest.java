/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

import org.junit.Assert;
import org.junit.Test;

public class ObjectSleutelTest {

    @Test
    public void testSerializatieRoundTrip() throws Exception {
        final ObjectSleutel objectSleutelUit = new ObjectSleutel(189325, 1546874L, 148321654);
        final byte[] serialize = objectSleutelUit.serialize();
        final ObjectSleutel objectSleutelIn = ObjectSleutel.deserialize(serialize);
        Assert.assertEquals(189325, objectSleutelIn.getPersoonId());
        Assert.assertEquals(1546874L, objectSleutelIn.getTijdstipVanUitgifte());
        Assert.assertEquals(148321654, objectSleutelIn.getPartijCode());
    }

}
