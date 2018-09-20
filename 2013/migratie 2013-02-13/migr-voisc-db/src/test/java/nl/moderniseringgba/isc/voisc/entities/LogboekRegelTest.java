/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.entities;

import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Test;

public class LogboekRegelTest {

    private static final SimpleDateFormat DATE = new SimpleDateFormat("dd-MM-yyyy");

    @Test
    public void test() throws Exception {
        final Mailbox mailbox = new Mailbox();

        final LogboekRegel regel = new LogboekRegel();
        Assert.assertNull(regel.getId());
        Assert.assertNull(regel.getMailbox());
        Assert.assertNull(regel.getStartDatumTijd());
        Assert.assertNull(regel.getEindDatumTijd());
        Assert.assertEquals(0, regel.getAantalVerzonden());
        Assert.assertEquals(0, regel.getAantalOntvangen());
        Assert.assertEquals(0, regel.getAantalVerzondenOK());
        Assert.assertEquals(0, regel.getAantalVerzondenNOK());
        Assert.assertEquals(0, regel.getAantalOntvangenOK());
        Assert.assertEquals(0, regel.getAantalOntvangenNOK());
        Assert.assertNull(regel.getFoutmelding());
        Assert.assertFalse(regel.hasFoutmelding());
        regel.setFoutmelding("");
        Assert.assertFalse(regel.hasFoutmelding());

        regel.setId(12345L);
        regel.setMailbox(mailbox);
        regel.setStartDatumTijd(DATE.parse("01-01-2000"));
        regel.setEindDatumTijd(DATE.parse("02-05-2001"));
        regel.setAantalVerzonden(1234);
        regel.setAantalOntvangen(4321);
        regel.setAantalVerzondenOK(456);
        regel.setAantalVerzondenNOK(678);
        regel.setAantalOntvangenOK(654);
        regel.setAantalOntvangenNOK(876);
        regel.setFoutmelding("foutje");

        Assert.assertEquals(Long.valueOf(12345L), regel.getId());
        Assert.assertSame(mailbox, regel.getMailbox());
        Assert.assertEquals(DATE.parse("01-01-2000"), regel.getStartDatumTijd());
        Assert.assertEquals(DATE.parse("02-05-2001"), regel.getEindDatumTijd());
        Assert.assertEquals(1234, regel.getAantalVerzonden());
        Assert.assertEquals(4321, regel.getAantalOntvangen());
        Assert.assertEquals(456, regel.getAantalVerzondenOK());
        Assert.assertEquals(678, regel.getAantalVerzondenNOK());
        Assert.assertEquals(654, regel.getAantalOntvangenOK());
        Assert.assertEquals(876, regel.getAantalOntvangenNOK());
        Assert.assertEquals("foutje", regel.getFoutmelding());
        Assert.assertTrue(regel.hasFoutmelding());
    }
}
