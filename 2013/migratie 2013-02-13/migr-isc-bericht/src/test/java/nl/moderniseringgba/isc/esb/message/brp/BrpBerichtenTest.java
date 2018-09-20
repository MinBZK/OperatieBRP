/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;

import nl.moderniseringgba.isc.esb.message.brp.generated.IscGemeenten;
import nl.moderniseringgba.isc.esb.message.brp.generated.NotificatieAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.NotificatieVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.generated.SynchronisatieSignaalType;
import nl.moderniseringgba.isc.esb.message.brp.generated.VerhuizingAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.VerhuizingVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ZoekPersoonAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.impl.NotificatieAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.NotificatieVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.SynchronisatieSignaalBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.ZoekPersoonAntwoordBericht;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class BrpBerichtenTest {

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testGeboorteVerzoekBericht() throws Exception {
        final String origineel =
                IOUtils.toString(BrpBerichtenTest.class.getResourceAsStream("impl/geboorteVerzoekBericht.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);
        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testNotificatie() throws Exception {
        final NotificatieVerzoekType notificatieVerzoekType = new NotificatieVerzoekType();
        notificatieVerzoekType.setNotificatie("Notificatie bericht zegt ok.");
        final NotificatieVerzoekBericht input = new NotificatieVerzoekBericht(notificatieVerzoekType);
        testFormatAndParseBericht(input);
    }

    @Test
    public void testNotificatieAntwoord() throws Exception {
        final NotificatieAntwoordType notificatieAntwoordType = new NotificatieAntwoordType();
        notificatieAntwoordType.setStatus(StatusType.OK);
        notificatieAntwoordType.setToelichting("Super!");
        final NotificatieAntwoordBericht input = new NotificatieAntwoordBericht(notificatieAntwoordType);
        testFormatAndParseBericht(input);
    }

    @Test
    public void testSynchronisatie() throws Exception {
        final SynchronisatieSignaalType synchronisatieSignaalType = new SynchronisatieSignaalType();
        synchronisatieSignaalType.setANummer("1237298734");
        final SynchronisatieSignaalBericht input = new SynchronisatieSignaalBericht(synchronisatieSignaalType);
        testFormatAndParseBericht(input);
    }

    @Test
    public void testVerhuis() throws Exception {
        final VerhuizingVerzoekType verhuizingVerzoekType = new VerhuizingVerzoekType();
        final IscGemeenten iscGemeenten = new IscGemeenten();

        iscGemeenten.setLo3Gemeente("1904");
        iscGemeenten.setBrpGemeente("1905");
        verhuizingVerzoekType.setAfzender("Jaap");
        verhuizingVerzoekType.setANummer("1234567892");
        verhuizingVerzoekType.setBurgerservicenummer("567545445");
        verhuizingVerzoekType.setDatumInschrijving(new BigInteger("20120921"));
        verhuizingVerzoekType.setHuidigeGemeente("1904");
        verhuizingVerzoekType.setNieuweGemeente("1905");
        verhuizingVerzoekType.setIscGemeenten(iscGemeenten);
        final VerhuizingVerzoekBericht minimaal = new VerhuizingVerzoekBericht(verhuizingVerzoekType);
        testFormatAndParseBericht(minimaal);
    }

    @Test
    public void testVerhuisAntwoord() throws Exception {
        final VerhuizingAntwoordType verhuizingAntwoordType = new VerhuizingAntwoordType();
        verhuizingAntwoordType.setStatus(StatusType.OK);
        verhuizingAntwoordType.setToelichting("Super!");
        final VerhuizingAntwoordBericht input = new VerhuizingAntwoordBericht(verhuizingAntwoordType);
        testFormatAndParseBericht(input);
    }

    @Test
    public void testZoekPersoonAntwoord() throws Exception {
        final ZoekPersoonAntwoordType zoekPersoonAntwoordType = new ZoekPersoonAntwoordType();
        zoekPersoonAntwoordType.setStatus(StatusType.OK);
        zoekPersoonAntwoordType.setToelichting("Super!");
        final ZoekPersoonAntwoordBericht input = new ZoekPersoonAntwoordBericht(zoekPersoonAntwoordType);
        testFormatAndParseBericht(input);
    }

    @Test(expected = RuntimeException.class)
    public void testElementToStringExceptie() throws Exception {
        try {
            factory.elementToString(null);
        } catch (final RuntimeException exceptie) {
            throw exceptie;
        }
    }

    private void testFormatAndParseBericht(final BrpBericht bericht) throws Exception {
        System.out.println("Bericht: " + bericht);

        final String formatted = bericht.format();
        System.out.println("Bericht.formatted: " + formatted);
        Assert.assertNotNull(formatted);

        final BrpBericht parsed = factory.getBericht(formatted);
        System.out.println("Bericht.parsed: " + parsed);
        Assert.assertNotNull(parsed);

        parsed.setMessageId(bericht.getMessageId());
        parsed.setCorrelationId(bericht.getCorrelationId());

        Assert.assertEquals(bericht, parsed);

        // Serialize
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(parsed);

        final byte[] data = baos.toByteArray();

        final ByteArrayInputStream bais = new ByteArrayInputStream(data);
        final ObjectInputStream ois = new ObjectInputStream(bais);

        final Object deserialized = ois.readObject();
        Assert.assertEquals(parsed, deserialized);

        Assert.assertEquals(parsed.getMessageId(), ((BrpBericht) deserialized).getMessageId());
    }

}
