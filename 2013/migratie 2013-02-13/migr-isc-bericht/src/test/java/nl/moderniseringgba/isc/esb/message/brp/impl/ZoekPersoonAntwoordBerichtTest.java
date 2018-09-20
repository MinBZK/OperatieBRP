/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.GevondenPersonenType;
import nl.moderniseringgba.isc.esb.message.brp.generated.GevondenPersoonType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ZoekPersoonAntwoordType;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class ZoekPersoonAntwoordBerichtTest {
    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testParse() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(ZoekPersoonVerzoekBerichtTest.class
                        .getResourceAsStream("zoekPersoonAntwoordBericht.xml"));
        final ZoekPersoonAntwoordBericht bericht = (ZoekPersoonAntwoordBericht) factory.getBericht(berichtOrigineel);

        assertEquals("ZoekPersoonAntwoord", bericht.getBerichtType());
        assertEquals(2, bericht.getAantalGevondenPersonen());
        assertEquals(null, bericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final ZoekPersoonAntwoordType zoekPersoonAntwoordType = new ZoekPersoonAntwoordType();
        final GevondenPersonenType gevondenPersonenType = new GevondenPersonenType();
        final List<GevondenPersoonType> gevondenPersoonTypeLijst = gevondenPersonenType.getGevondenPersoon();
        final GevondenPersoonType gevondenPersoon1 = new GevondenPersoonType();
        final GevondenPersoonType gevondenPersoon2 = new GevondenPersoonType();
        gevondenPersoon1.setANummer("1234567890");
        gevondenPersoon2.setANummer("9876543210");
        gevondenPersoon1.setBijhoudingsgemeente("0399");
        gevondenPersoon2.setBijhoudingsgemeente("0499");
        gevondenPersoonTypeLijst.add(gevondenPersoon1);
        gevondenPersoonTypeLijst.add(gevondenPersoon2);
        zoekPersoonAntwoordType.setStatus(StatusType.OK);
        zoekPersoonAntwoordType.setToelichting("blablabla");
        zoekPersoonAntwoordType.setGevondenPersonen(gevondenPersonenType);
        final ZoekPersoonAntwoordBericht antwoordBericht = new ZoekPersoonAntwoordBericht(zoekPersoonAntwoordType);
        final String geformat = antwoordBericht.format();
        // controleer of geformat bericht weer geparsed kan worden
        final ZoekPersoonAntwoordBericht format = (ZoekPersoonAntwoordBericht) factory.getBericht(geformat);
        assertEquals("ZoekPersoonAntwoord", format.getBerichtType());

        format.setMessageId(antwoordBericht.getMessageId());

        Assert.assertEquals(antwoordBericht, format);
    }

    @Test
    public void testGetSingleAnummer() {
        final ZoekPersoonAntwoordType zoekPersoonAntwoordType = new ZoekPersoonAntwoordType();
        final GevondenPersonenType gevondenPersonenType = new GevondenPersonenType();
        final List<GevondenPersoonType> gevondenPersoonTypeLijst = gevondenPersonenType.getGevondenPersoon();
        final GevondenPersoonType gevondenPersoon1 = new GevondenPersoonType();
        final GevondenPersoonType gevondenPersoon2 = new GevondenPersoonType();
        gevondenPersoon1.setANummer("1234567890");
        gevondenPersoon2.setANummer("9876543210");
        gevondenPersoon1.setBijhoudingsgemeente("0399");
        gevondenPersoon2.setBijhoudingsgemeente("0499");
        gevondenPersoonTypeLijst.add(gevondenPersoon1);
        gevondenPersoonTypeLijst.add(gevondenPersoon2);
        zoekPersoonAntwoordType.setStatus(StatusType.OK);
        zoekPersoonAntwoordType.setToelichting("blablabla");
        zoekPersoonAntwoordType.setGevondenPersonen(gevondenPersonenType);

        final ZoekPersoonAntwoordBericht antwoordBerichtDubbel =
                new ZoekPersoonAntwoordBericht(zoekPersoonAntwoordType);

        assertNull(antwoordBerichtDubbel.getSingleAnummer());

        gevondenPersoonTypeLijst.remove(1);

        final ZoekPersoonAntwoordBericht antwoordBerichtEnkel =
                new ZoekPersoonAntwoordBericht(zoekPersoonAntwoordType);

        assertEquals("1234567890", antwoordBerichtEnkel.getSingleAnummer());
    }

    @Test
    public void testFoutBericht() throws Exception {
        final ZoekPersoonAntwoordBericht zoekPersoonBericht = new ZoekPersoonAntwoordBericht();
        zoekPersoonBericht.setStatus(StatusType.FOUT);

        assertEquals("ZoekPersoonAntwoord", zoekPersoonBericht.getBerichtType());
        assertEquals(StatusType.FOUT, zoekPersoonBericht.getStatus());
    }

    @Test
    public void testEquals() throws Exception {
        final ZoekPersoonAntwoordType zoekPersoonAntwoordType = new ZoekPersoonAntwoordType();
        zoekPersoonAntwoordType.setStatus(StatusType.OK);
        zoekPersoonAntwoordType.setToelichting("Testtoelichting");
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBerichtOrigineel =
                new ZoekPersoonAntwoordBericht(zoekPersoonAntwoordType);
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBerichtKopie =
                new ZoekPersoonAntwoordBericht(zoekPersoonAntwoordType);
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBerichtObjectKopie = zoekPersoonAntwoordBerichtOrigineel;
        final ZoekPersoonVerzoekBericht zoekPersoonVerzoekBericht = new ZoekPersoonVerzoekBericht();

        zoekPersoonAntwoordBerichtKopie.setMessageId(zoekPersoonAntwoordBerichtOrigineel.getMessageId());
        zoekPersoonAntwoordBerichtKopie.setCorrelationId(zoekPersoonAntwoordBerichtOrigineel.getCorrelationId());

        assertEquals(true, zoekPersoonAntwoordBerichtObjectKopie.equals(zoekPersoonAntwoordBerichtOrigineel));
        assertEquals(false, zoekPersoonAntwoordBerichtOrigineel.equals(zoekPersoonVerzoekBericht));
        assertEquals(true, zoekPersoonAntwoordBerichtKopie.equals(zoekPersoonAntwoordBerichtOrigineel));
        assertEquals(zoekPersoonAntwoordBerichtObjectKopie.hashCode(), zoekPersoonAntwoordBerichtOrigineel.hashCode());
        assertEquals(zoekPersoonAntwoordBerichtKopie.hashCode(), zoekPersoonAntwoordBerichtOrigineel.hashCode());
        assertEquals(zoekPersoonAntwoordBerichtKopie.toString(), zoekPersoonAntwoordBerichtOrigineel.toString());
    }

    @Test(expected = BerichtInhoudException.class)
    public void testParseException() throws Exception {

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        final SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        // maak een resolver aan zodat alle gerefereerde xsd's ook gevonden kunnen worden.
        schemaFactory.setResourceResolver(new LSResourceResolver());

        factory.setSchema(schemaFactory.newSchema(new Source[] { new StreamSource(BrpBerichtFactory.class
                .getResourceAsStream("/xsd/BRP_Berichten.xsd")), }));

        final Document document =
                factory.newDocumentBuilder().parse(
                        ZoekPersoonAntwoordBerichtTest.class
                                .getResourceAsStream("zoekPersoonAntwoordBerichtSyntaxExceptionBericht.xml"));

        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBericht = new ZoekPersoonAntwoordBericht();

        try {
            zoekPersoonAntwoordBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBericht = new ZoekPersoonAntwoordBericht();

        final String TOELICHTING = "Testtoelichting";
        zoekPersoonAntwoordBericht.setStatus(StatusType.FOUT);
        zoekPersoonAntwoordBericht.setToelichting(TOELICHTING);

        assertEquals(StatusType.FOUT, zoekPersoonAntwoordBericht.getStatus());
        assertEquals(TOELICHTING, zoekPersoonAntwoordBericht.getToelichting());
    }

    @Test
    public void testGeenGevondenPersonen() throws Exception {
        final ZoekPersoonAntwoordType zoekPersoonAntwoordType = new ZoekPersoonAntwoordType();
        zoekPersoonAntwoordType.setGevondenPersonen(null);
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBericht =
                new ZoekPersoonAntwoordBericht(zoekPersoonAntwoordType);

        assertEquals(0, zoekPersoonAntwoordBericht.getAantalGevondenPersonen());

        final GevondenPersonenType gevondenPersonenType = new GevondenPersonenType();
        zoekPersoonAntwoordType.setGevondenPersonen(gevondenPersonenType);

        assertEquals(0, zoekPersoonAntwoordBericht.getAantalGevondenPersonen());

        final GevondenPersoonType gevondenPersoon1 = new GevondenPersoonType();
        final GevondenPersoonType gevondenPersoon2 = new GevondenPersoonType();
        gevondenPersonenType.getGevondenPersoon().add(gevondenPersoon1);
        gevondenPersonenType.getGevondenPersoon().add(gevondenPersoon2);

        assertEquals(2, zoekPersoonAntwoordBericht.getAantalGevondenPersonen());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(ZoekPersoonAntwoordBerichtTest.class
                        .getResourceAsStream("zoekPersoonAntwoordBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }

}
