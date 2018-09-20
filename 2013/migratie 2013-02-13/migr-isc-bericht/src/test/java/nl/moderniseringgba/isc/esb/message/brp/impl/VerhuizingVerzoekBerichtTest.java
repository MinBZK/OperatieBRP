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
import nl.moderniseringgba.isc.esb.message.brp.generated.IscGemeenten;
import nl.moderniseringgba.isc.esb.message.brp.generated.VerhuizingVerzoekType;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class VerhuizingVerzoekBerichtTest {

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(VerhuizingVerzoekBerichtTest.class
                        .getResourceAsStream("verhuizingVerzoekBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final VerhuizingVerzoekBericht verhuisBericht = (VerhuizingVerzoekBericht) bericht;
        assertInhoud(verhuisBericht);
        assertEquals("uc302", verhuisBericht.getStartCyclus());
    }

    private void assertInhoud(final VerhuizingVerzoekBericht verhuisBericht) {
        assertEquals("6543210987654321", verhuisBericht.getAdresseerbaarObject());
        assertEquals("NOR", verhuisBericht.getAfgekorteNaamOpenbareRuimte());
        assertEquals("afzender", verhuisBericht.getAfzender());
        assertEquals("1234567890", verhuisBericht.getANummer());
        assertEquals("VerhuizingVerzoek", verhuisBericht.getBerichtType());
        assertEquals("123456789", verhuisBericht.getBsn());
        assertEquals("20120921", verhuisBericht.getDatumInschrijving());
        assertEquals("0399", verhuisBericht.getHuidigeGemeente());
        assertEquals("A", verhuisBericht.getHuisletter());
        assertEquals("42", verhuisBericht.getHuisnummer());
        assertEquals("2", verhuisBericht.getHuisnummerToevoeging());
        assertEquals("1234567890123456", verhuisBericht.getIdNummerAanduiding());
        assertEquals("bij het bruggetje links", verhuisBericht.getLocatieOmschrijving());
        assertEquals("TO", verhuisBericht.getLocatieTovAdres());
        assertEquals("naamOpenbareRuimte", verhuisBericht.getNaamOpenbareRuimte());
        assertEquals("0499", verhuisBericht.getNieuweGemeente());
        assertEquals("1234AB", verhuisBericht.getPostcode());
        assertEquals("Modernodam", verhuisBericht.getWoonplaats());
    }

    @Test
    public void testFormat() throws Exception {
        final VerhuizingVerzoekBericht verhuisBericht = new VerhuizingVerzoekBericht();
        vulVerhuisberichtWaarden(verhuisBericht);
        final String geformat = verhuisBericht.format();
        // controleer of geformat bericht weer geparsed kan worden
        final VerhuizingVerzoekBericht format = (VerhuizingVerzoekBericht) factory.getBericht(geformat);
        assertEquals("VerhuizingVerzoek", format.getBerichtType());
        assertInhoud(format);
    }

    private void vulVerhuisberichtWaarden(final VerhuizingVerzoekBericht verhuisBericht) {
        verhuisBericht.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal("1904")));
        verhuisBericht.setBrpGemeente(new BrpGemeenteCode(new BigDecimal("1905")));
        verhuisBericht.setAdresseerbaarObject("6543210987654321");
        verhuisBericht.setAfgekorteNaamOpenbareRuimte("NOR");
        verhuisBericht.setAfzender("afzender");
        verhuisBericht.setANummer("1234567890");
        verhuisBericht.setBsn("123456789");
        verhuisBericht.setDatumInschrijving("20120921");
        verhuisBericht.setHuidigeGemeente("0399");
        verhuisBericht.setHuisletter("A");
        verhuisBericht.setHuisnummer("42");
        verhuisBericht.setHuisnummerToevoeging("2");
        verhuisBericht.setIdNummerAanduiding("1234567890123456");
        verhuisBericht.setLocatieOmschrijving("bij het bruggetje links");
        verhuisBericht.setLocatieTovAdres("TO");
        verhuisBericht.setNaamOpenbareRuimte("naamOpenbareRuimte");
        verhuisBericht.setNieuweGemeente("0499");
        verhuisBericht.setPostcode("1234AB");
        verhuisBericht.setWoonplaats("Modernodam");

    }

    @Test
    public void testFormatType() throws Exception {
        final VerhuizingVerzoekType verhuizingVerzoekType = new VerhuizingVerzoekType();
        vulVerzoekTypeMetWaarden(verhuizingVerzoekType);

        final VerhuizingVerzoekBericht verhuisBericht = new VerhuizingVerzoekBericht(verhuizingVerzoekType);
        final String geformat = verhuisBericht.format();
        // controleer of geformat bericht weer geparsed kan worden
        final VerhuizingVerzoekBericht format = (VerhuizingVerzoekBericht) factory.getBericht(geformat);
        assertEquals("VerhuizingVerzoek", format.getBerichtType());
        assertInhoud(format);
    }

    private void vulVerzoekTypeMetWaarden(final VerhuizingVerzoekType verhuizingVerzoekType) {
        final IscGemeenten iscGemeenten = new IscGemeenten();
        iscGemeenten.setLo3Gemeente("1904");
        iscGemeenten.setBrpGemeente("1905");
        verhuizingVerzoekType.setIscGemeenten(iscGemeenten);
        verhuizingVerzoekType.setAdresseerbaarObject("6543210987654321");
        verhuizingVerzoekType.setAfgekorteNaamOpenbareRuimte("NOR");
        verhuizingVerzoekType.setAfzender("afzender");
        verhuizingVerzoekType.setANummer("1234567890");
        verhuizingVerzoekType.setBurgerservicenummer("123456789");
        verhuizingVerzoekType.setDatumInschrijving(new BigInteger("20120921"));
        verhuizingVerzoekType.setHuidigeGemeente("0399");
        verhuizingVerzoekType.setHuisletter("A");
        verhuizingVerzoekType.setHuisnummer("42");
        verhuizingVerzoekType.setHuisnummertoevoeging("2");
        verhuizingVerzoekType.setIdNummeraanduiding("1234567890123456");
        verhuizingVerzoekType.setLocatieOmschrijving("bij het bruggetje links");
        verhuizingVerzoekType.setLocatieTovAdres("TO");
        verhuizingVerzoekType.setNaamOpenbareRuimte("naamOpenbareRuimte");
        verhuizingVerzoekType.setNieuweGemeente("0499");
        verhuizingVerzoekType.setPostcode("1234AB");
        verhuizingVerzoekType.setWoonplaats("Modernodam");
    }

    @Test
    public void testEquals() throws Exception {
        final VerhuizingVerzoekType verhuizingVerzoekType = new VerhuizingVerzoekType();
        vulVerzoekTypeMetWaarden(verhuizingVerzoekType);

        final VerhuizingVerzoekBericht verhuizingVerzoekBerichtOrigineel =
                new VerhuizingVerzoekBericht(verhuizingVerzoekType);
        final VerhuizingVerzoekBericht verhuizingVerzoekBerichtKopie =
                new VerhuizingVerzoekBericht(verhuizingVerzoekType);
        final VerhuizingVerzoekBericht verhuizingVerzoekBerichtObjectKopie = verhuizingVerzoekBerichtOrigineel;
        final VerhuizingAntwoordBericht verhuizingAntwoordBericht = new VerhuizingAntwoordBericht();

        verhuizingVerzoekBerichtKopie.setMessageId(verhuizingVerzoekBerichtOrigineel.getMessageId());
        verhuizingVerzoekBerichtKopie.setCorrelationId(verhuizingVerzoekBerichtOrigineel.getCorrelationId());

        assertEquals(true, verhuizingVerzoekBerichtObjectKopie.equals(verhuizingVerzoekBerichtOrigineel));
        assertEquals(false, verhuizingVerzoekBerichtOrigineel.equals(verhuizingAntwoordBericht));
        assertEquals(true, verhuizingVerzoekBerichtKopie.equals(verhuizingVerzoekBerichtOrigineel));
        assertEquals(verhuizingVerzoekBerichtObjectKopie.hashCode(), verhuizingVerzoekBerichtOrigineel.hashCode());
        assertEquals(verhuizingVerzoekBerichtKopie.hashCode(), verhuizingVerzoekBerichtOrigineel.hashCode());
        assertEquals(verhuizingVerzoekBerichtKopie.toString(), verhuizingVerzoekBerichtOrigineel.toString());
    }

    @Test(expected = BerichtInhoudException.class)
    public void testParse() throws Exception {

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        final SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        // maak een resolver aan zodat alle gerefereerde xsd's ook gevonden kunnen worden.
        schemaFactory.setResourceResolver(new LSResourceResolver());

        factory.setSchema(schemaFactory.newSchema(new Source[] { new StreamSource(BrpBerichtFactory.class
                .getResourceAsStream("/xsd/BRP_Berichten.xsd")), }));

        final Document document =
                factory.newDocumentBuilder().parse(
                        VerhuizingVerzoekBerichtTest.class
                                .getResourceAsStream("verhuizingVerzoekBerichtSyntaxExceptionBericht.xml"));

        final VerhuizingVerzoekBericht verhuizingVerzoekBericht = new VerhuizingVerzoekBericht();

        try {
            verhuizingVerzoekBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final VerhuizingVerzoekBericht verhuizingVerzoekBericht = new VerhuizingVerzoekBericht();
        verhuizingVerzoekBericht.setDatumInschrijving(null);
        assertEquals(null, verhuizingVerzoekBericht.getDatumInschrijving());

        vulVerhuisberichtWaarden(verhuizingVerzoekBericht);

        assertEquals(new BrpGemeenteCode(new BigDecimal("1904")), verhuizingVerzoekBericht.getLo3Gemeente());
        assertEquals(new BrpGemeenteCode(new BigDecimal("1905")), verhuizingVerzoekBericht.getBrpGemeente());
    }

    @Test
    public void tesBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(VerhuizingVerzoekBerichtTest.class
                        .getResourceAsStream("verhuizingVerzoekBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }

}
