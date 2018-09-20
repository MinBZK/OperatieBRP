/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale.berichten;

import java.util.Date;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Richting;
import org.junit.Assert;
import org.junit.Test;

public class GbaArchiveringVerzoekTest {

    @Test
    public void test() {
        final GbaArchiveringVerzoek subject = new GbaArchiveringVerzoek();
        Assert.assertEquals(null, subject.getSoortBericht());
        Assert.assertEquals(null, subject.getRichting());
        Assert.assertEquals(null, subject.getZendendePartijCode());
        Assert.assertEquals(null, subject.getZendendeSysteem());
        Assert.assertEquals(null, subject.getOntvangendePartijCode());
        Assert.assertEquals(null, subject.getOntvangendeSysteem());
        Assert.assertEquals(null, subject.getReferentienummer());
        Assert.assertEquals(null, subject.getCrossReferentienummer());
        Assert.assertEquals(null, subject.getTijdstipVerzending());
        Assert.assertEquals(null, subject.getTijdstipOntvangst());
        Assert.assertEquals(null, subject.getData());

        subject.setSoortBericht("1");
        subject.setRichting(Richting.INGAAND);
        subject.setZendendePartijCode(2);
        subject.setZendendeSysteem("3");
        subject.setOntvangendePartijCode(4);
        subject.setOntvangendeSysteem("5");
        subject.setReferentienummer("6");
        subject.setCrossReferentienummer("7");
        final Date date1 = new Date();
        subject.setTijdstipVerzending(date1);
        final Date date2 = new Date();
        subject.setTijdstipOntvangst(date2);
        subject.setData("8");

        Assert.assertEquals("1", subject.getSoortBericht());
        Assert.assertEquals(Richting.INGAAND, subject.getRichting());
        Assert.assertEquals(Integer.valueOf(2), subject.getZendendePartijCode());
        Assert.assertEquals("3", subject.getZendendeSysteem());
        Assert.assertEquals(Integer.valueOf(4), subject.getOntvangendePartijCode());
        Assert.assertEquals("5", subject.getOntvangendeSysteem());
        Assert.assertEquals("6", subject.getReferentienummer());
        Assert.assertEquals("7", subject.getCrossReferentienummer());
        Assert.assertEquals(date1, subject.getTijdstipVerzending());
        Assert.assertNotSame(date1, subject.getTijdstipVerzending());
        Assert.assertEquals(date2, subject.getTijdstipOntvangst());
        Assert.assertNotSame(date2, subject.getTijdstipOntvangst());
        Assert.assertEquals("8", subject.getData());

        subject.setTijdstipVerzending(null);
        subject.setTijdstipOntvangst(null);
        Assert.assertEquals(null, subject.getTijdstipVerzending());
        Assert.assertEquals(null, subject.getTijdstipOntvangst());
    }
}
