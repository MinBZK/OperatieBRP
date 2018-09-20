/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.GeslachtsaanduidingCodeS;
import nl.moderniseringgba.isc.esb.message.brp.generated.ZoekPersoonVerzoekType;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class ZoekPersoonVerzoekBerichtTest {

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testParse() throws Exception {
        final ZoekPersoonVerzoekBericht bericht =
                (ZoekPersoonVerzoekBericht) factory.getBericht(IOUtils.toString(ZoekPersoonVerzoekBerichtTest.class
                        .getResourceAsStream("zoekPersoonVerzoekBericht.xml")));
        assertEquals("ZoekPersoonVerzoek", bericht.getBerichtType());
        assertEquals("123456789", bericht.getBsn());
        assertEquals(null, bericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final ZoekPersoonVerzoekType zoekPersoonVerzoekType = new ZoekPersoonVerzoekType();
        zoekPersoonVerzoekType.setANummer("123456780");
        zoekPersoonVerzoekType.setBurgerservicenummer("123456789");
        zoekPersoonVerzoekType.setGeslachtsnaam("Jansen");
        zoekPersoonVerzoekType.setGeboortedatum(new BigInteger("19540304"));
        zoekPersoonVerzoekType.setGeslachtsaanduiding(GeslachtsaanduidingCodeS.M);
        zoekPersoonVerzoekType.setVoornamen("Pieter klaas");
        zoekPersoonVerzoekType.setPostcode("1234AB");
        zoekPersoonVerzoekType.setBijhoudingsgemeente("0123");
        final ZoekPersoonVerzoekBericht zoekBericht = new ZoekPersoonVerzoekBericht(zoekPersoonVerzoekType);

        final String geformat = zoekBericht.format();
        System.out.println(geformat);
        // controleer of geformat bericht weer geparsed kan worden
        final ZoekPersoonVerzoekBericht format = (ZoekPersoonVerzoekBericht) factory.getBericht(geformat);
        Assert.assertEquals("ZoekPersoonVerzoek", format.getBerichtType());

        format.setMessageId(zoekBericht.getMessageId());

        Assert.assertEquals(zoekBericht, format);
    }

    @Test
    public void testZoekBinnenGemeente() throws Exception {
        final ZoekPersoonVerzoekBericht zoekBericht = new ZoekPersoonVerzoekBericht();
        zoekBericht.setBsn("123456789");
        zoekBericht.setBijhoudingsGemeente(new BrpGemeenteCode(new BigDecimal("123")));

        final String geformat = zoekBericht.format();
        System.out.println(geformat);
        // controleer of geformat bericht weer geparsed kan worden
        final ZoekPersoonVerzoekBericht format = (ZoekPersoonVerzoekBericht) factory.getBericht(geformat);
        Assert.assertEquals("ZoekPersoonVerzoek", format.getBerichtType());

        format.setMessageId(zoekBericht.getMessageId());

        Assert.assertEquals(zoekBericht, format);
    }

    @Test
    public void testEquals() throws Exception {
        final ZoekPersoonVerzoekType zoekPersoonVerzoekType = new ZoekPersoonVerzoekType();
        zoekPersoonVerzoekType.setANummer("1234567890");
        zoekPersoonVerzoekType.setBijhoudingsgemeente("1904");
        zoekPersoonVerzoekType.setBurgerservicenummer("134548512");
        zoekPersoonVerzoekType.setGeboortedatum(new BigInteger("19950506"));
        zoekPersoonVerzoekType.setGeslachtsaanduiding(GeslachtsaanduidingCodeS.M);
        zoekPersoonVerzoekType.setGeslachtsnaam("Janssen");
        zoekPersoonVerzoekType.setPostcode("1341AX");
        zoekPersoonVerzoekType.setVoornamen("Karel");

        final ZoekPersoonVerzoekBericht zoekPersoonVerzoekBerichtOrigineel =
                new ZoekPersoonVerzoekBericht(zoekPersoonVerzoekType);
        final ZoekPersoonVerzoekBericht zoekPersoonVerzoekBerichtKopie =
                new ZoekPersoonVerzoekBericht(zoekPersoonVerzoekType);
        final ZoekPersoonVerzoekBericht zoekPersoonVerzoekBerichtObjectKopie = zoekPersoonVerzoekBerichtOrigineel;
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBericht = new ZoekPersoonAntwoordBericht();

        zoekPersoonVerzoekBerichtKopie.setMessageId(zoekPersoonVerzoekBerichtOrigineel.getMessageId());
        zoekPersoonVerzoekBerichtKopie.setCorrelationId(zoekPersoonVerzoekBerichtOrigineel.getCorrelationId());

        assertEquals(true, zoekPersoonVerzoekBerichtObjectKopie.equals(zoekPersoonVerzoekBerichtOrigineel));
        assertEquals(false, zoekPersoonVerzoekBerichtOrigineel.equals(zoekPersoonAntwoordBericht));
        assertEquals(true, zoekPersoonVerzoekBerichtKopie.equals(zoekPersoonVerzoekBerichtOrigineel));
        assertEquals(zoekPersoonVerzoekBerichtObjectKopie.hashCode(), zoekPersoonVerzoekBerichtOrigineel.hashCode());
        assertEquals(zoekPersoonVerzoekBerichtKopie.hashCode(), zoekPersoonVerzoekBerichtOrigineel.hashCode());
        assertEquals(zoekPersoonVerzoekBerichtKopie.toString(), zoekPersoonVerzoekBerichtOrigineel.toString());
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
                        NotificatieVerzoekBerichtTest.class
                                .getResourceAsStream("zoekPersoonVerzoekBerichtSyntaxExceptionBericht.xml"));

        final ZoekPersoonVerzoekBericht zoekPersoonVerzoekBericht = new ZoekPersoonVerzoekBericht();

        try {
            zoekPersoonVerzoekBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final ZoekPersoonVerzoekBericht zoekPersoonVerzoekBericht = new ZoekPersoonVerzoekBericht();

        zoekPersoonVerzoekBericht.setANummer(null);
        zoekPersoonVerzoekBericht.setBijhoudingsGemeente(null);
        zoekPersoonVerzoekBericht.setBsn(null);
        zoekPersoonVerzoekBericht.setGeboorteDatum(null);
        zoekPersoonVerzoekBericht.setGeslachtsaanduiding(null);
        zoekPersoonVerzoekBericht.setGeslachtsnaam(null);
        zoekPersoonVerzoekBericht.setPostcode(null);
        zoekPersoonVerzoekBericht.setVoornamen(null);

        assertEquals(null, zoekPersoonVerzoekBericht.getANummer());
        assertEquals(null, zoekPersoonVerzoekBericht.getBijhoudingsGemeente());
        assertEquals(null, zoekPersoonVerzoekBericht.getBsn());
        assertEquals(null, zoekPersoonVerzoekBericht.getGeboorteDatum());
        assertEquals(null, zoekPersoonVerzoekBericht.getGeslachtsaanduiding());
        assertEquals(null, zoekPersoonVerzoekBericht.getGeslachtsnaam());
        assertEquals(null, zoekPersoonVerzoekBericht.getPostcode());
        assertEquals(null, zoekPersoonVerzoekBericht.getVoornamen());

        final String ANUMMER = "1234567890";
        final BrpGemeenteCode bijhoudingsGemeente = new BrpGemeenteCode(new BigDecimal("1904"));
        final String BSN = "134548512";
        final BrpDatum geboorteDatum = new BrpDatum(19950506);
        final String GESLACHTSNAAM = "Janssen";
        final String POSTCODE = "1341AX";
        final String VOORNAMEN = "Karel";
        zoekPersoonVerzoekBericht.setANummer(ANUMMER);
        zoekPersoonVerzoekBericht.setBijhoudingsGemeente(bijhoudingsGemeente);
        zoekPersoonVerzoekBericht.setBsn(BSN);
        zoekPersoonVerzoekBericht.setGeboorteDatum(geboorteDatum);
        zoekPersoonVerzoekBericht.setGeslachtsaanduiding(GeslachtsaanduidingCodeS.M);
        zoekPersoonVerzoekBericht.setGeslachtsnaam(GESLACHTSNAAM);
        zoekPersoonVerzoekBericht.setPostcode(POSTCODE);
        zoekPersoonVerzoekBericht.setVoornamen(VOORNAMEN);

        assertEquals(ANUMMER, zoekPersoonVerzoekBericht.getANummer());
        assertEquals(bijhoudingsGemeente.getFormattedStringCode(), zoekPersoonVerzoekBericht.getBijhoudingsGemeente()
                .getFormattedStringCode());
        assertEquals(BSN, zoekPersoonVerzoekBericht.getBsn());
        assertEquals(geboorteDatum.getDatum(), zoekPersoonVerzoekBericht.getGeboorteDatum().getDatum());
        assertEquals(GeslachtsaanduidingCodeS.M, zoekPersoonVerzoekBericht.getGeslachtsaanduiding());
        assertEquals(GESLACHTSNAAM, zoekPersoonVerzoekBericht.getGeslachtsnaam());
        assertEquals(POSTCODE, zoekPersoonVerzoekBericht.getPostcode());
        assertEquals(VOORNAMEN, zoekPersoonVerzoekBericht.getVoornamen());
    }

    @Test
    public void tesBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("notificatieVerzoekBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }

}
