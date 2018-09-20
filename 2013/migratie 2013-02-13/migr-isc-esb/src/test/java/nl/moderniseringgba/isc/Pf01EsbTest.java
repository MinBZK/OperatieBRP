/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc;

import java.io.IOException;

import nl.moderniseringgba.isc.esb.message.BerichtId;
import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtOnbekendException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3BerichtFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class Pf01EsbTest extends AbstractEsbTest {

    private final Lo3BerichtFactory lo3BerichtFactory = new Lo3BerichtFactory();

    @Test
    public void testPf01OnbekendTypeVospg() throws BerichtSyntaxException, BerichtInhoudException,
            BerichtOnbekendException {
        final Bericht bericht = new Bericht();
        bericht.setMessageId(BerichtId.generateMessageId());
        bericht.setInhoud("00000000Xx0100000");
        bericht.setOriginator("0518");
        bericht.setRecipient("0599");

        verstuurVospgBericht(bericht);

        final Bericht resultaat = ontvangVospgBericht(bericht.getMessageId());

        Assert.assertNotNull(resultaat);
        final Lo3Bericht pf01 = lo3BerichtFactory.getBericht(resultaat.getInhoud());
        Assert.assertEquals("Pf01", pf01.getBerichtType());
    }

    @Test
    public void testPf01CorrelatieFoutVospg() throws BerichtSyntaxException, BerichtInhoudException,
            BerichtOnbekendException {
        final Bericht bericht = new Bericht();
        bericht.setMessageId(BerichtId.generateMessageId());
        bericht.setCorrelatieId("7f8b1470-c9aa-11e1-9b21-0800200c9a66-Totaal-Onbekend-Correlatie-ID-en-anders-wel-heel-toevallig-7640b140-c9aa-11e1-9b21-0800200c9a66");
        bericht.setInhoud("00000000If01G1234123456789000000");

        verstuurVospgBericht(bericht);

        final Bericht resultaat = ontvangVospgBericht(bericht.getMessageId());

        Assert.assertNotNull(resultaat);
        final Lo3Bericht pf01 = lo3BerichtFactory.getBericht(resultaat.getInhoud());
        Assert.assertEquals("Pf01", pf01.getBerichtType());
    }

    @Test
    public void testPf01CyclusStartFoutVospg() throws BerichtSyntaxException, BerichtInhoudException,
            BerichtOnbekendException {
        final Bericht bericht = new Bericht();
        bericht.setMessageId(BerichtId.generateMessageId());
        bericht.setInhoud("00000000If01G1234123456789000000");
        bericht.setOriginator("0518");
        bericht.setRecipient("0599");

        verstuurVospgBericht(bericht);

        final Bericht resultaat = ontvangVospgBericht(bericht.getMessageId());

        Assert.assertNotNull(resultaat);
        final Lo3Bericht pf01 = lo3BerichtFactory.getBericht(resultaat.getInhoud());
        Assert.assertEquals("Pf01", pf01.getBerichtType());
    }

    @Test
    public void testPf01Lg01FoutVospg() throws BerichtSyntaxException, BerichtInhoudException,
            BerichtOnbekendException, IOException {
        final Bericht bericht = new Bericht();
        bericht.setMessageId(BerichtId.generateMessageId());
        bericht.setInhoud(IOUtils.toString(this.getClass().getResourceAsStream("lg01.txt")));

        verstuurVospgBericht(bericht);

        final Bericht resultaat = ontvangVospgBericht(bericht.getMessageId());

        Assert.assertNotNull(resultaat);
        final Lo3Bericht pf01 = lo3BerichtFactory.getBericht(resultaat.getInhoud());
        Assert.assertEquals("Pf01", pf01.getBerichtType());
    }

    @Test
    public void testPf01OnbekendTypeMvi() throws BerichtSyntaxException, BerichtInhoudException,
            BerichtOnbekendException {
        final Bericht bericht = new Bericht();
        bericht.setMessageId(BerichtId.generateMessageId());
        bericht.setInhoud("00000000Xx0100000");

        verstuurMviBericht(bericht);

        final Bericht resultaat = ontvangMviBericht(bericht.getMessageId());

        Assert.assertNotNull(resultaat);
        final Lo3Bericht pf01 = lo3BerichtFactory.getBericht(resultaat.getInhoud());
        Assert.assertEquals("Pf01", pf01.getBerichtType());
    }

    @Test
    public void testPf01Lg01FoutMvi() throws BerichtSyntaxException, BerichtInhoudException,
            BerichtOnbekendException, IOException {
        final Bericht bericht = new Bericht();
        bericht.setMessageId(BerichtId.generateMessageId());
        bericht.setInhoud("00000000Pf0100000");

        verstuurMviBericht(bericht);

        final Bericht resultaat = ontvangMviBericht(bericht.getMessageId());

        Assert.assertNotNull(resultaat);
        final Lo3Bericht pf01 = lo3BerichtFactory.getBericht(resultaat.getInhoud());
        Assert.assertEquals("Pf01", pf01.getBerichtType());
    }

}
