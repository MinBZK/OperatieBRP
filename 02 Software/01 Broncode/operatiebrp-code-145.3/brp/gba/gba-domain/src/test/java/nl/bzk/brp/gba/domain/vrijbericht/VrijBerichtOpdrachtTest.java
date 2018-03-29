/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.domain.vrijbericht;

import org.junit.Assert;
import org.junit.Test;

public class VrijBerichtOpdrachtTest {

    @Test
    public void test() {
        VrijBerichtOpdracht subject = new VrijBerichtOpdracht();
        Assert.assertNull(subject.getVerzendendePartijCode());
        Assert.assertNull(subject.getOntvangendePartijCode());
        Assert.assertNull(subject.getBericht());
        Assert.assertNull(subject.getReferentienummer());

        subject.setVerzendendePartijCode("062601");
        subject.setOntvangendePartijCode("170601");
        subject.setBericht("Hoi, hoe gaat het? Gefeliciteerd!");
        subject.setReferentienummer("REF-XXXX");

        Assert.assertEquals("062601", subject.getVerzendendePartijCode());
        Assert.assertEquals("170601", subject.getOntvangendePartijCode());
        Assert.assertEquals("Hoi, hoe gaat het? Gefeliciteerd!", subject.getBericht());
        Assert.assertEquals("REF-XXXX", subject.getReferentienummer());
    }
}
