/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.generated.TransseksualiteitVerzoekType;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class TransseksualiteitVerzoekBerichtTest {
    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testFormatEnParse() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(TransseksualiteitVerzoekBerichtTest.class
                        .getResourceAsStream("transseksualiteitVerzoekBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final TransseksualiteitVerzoekBericht transseksualiteitVerzoekBericht =
                (TransseksualiteitVerzoekBericht) bericht;
        assertEquals("TransseksualiteitVerzoek", transseksualiteitVerzoekBericht.getBerichtType());
        assertEquals("uc308", transseksualiteitVerzoekBericht.getStartCyclus());
        System.out.println("Bericht: " + bericht);

        final String formatted = bericht.format();
        System.out.println("Bericht.formatted: " + formatted);
        Assert.assertNotNull(formatted);

        final BrpBericht parsed = factory.getBericht(formatted);
        System.out.println("Bericht.parsed: " + parsed);
        controleerFormatEnParse(bericht, parsed);
    }

    private void controleerFormatEnParse(final BrpBericht bericht, final BrpBericht parsed) throws IOException,
            ClassNotFoundException {
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

    @Test
    public void testEquals() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("transseksualiteitVerzoekBericht.xml"));

        final BrpBericht transseksualiteitVerzoekBerichtOrigineel = factory.getBericht(berichtOrigineel);

        final TransseksualiteitVerzoekBericht transseksualiteitVerzoekBerichtKopie =
                (TransseksualiteitVerzoekBericht) factory.getBericht(berichtOrigineel);
        final TransseksualiteitVerzoekBericht transseksualiteitVerzoekBerichtObjectKopie =
                (TransseksualiteitVerzoekBericht) transseksualiteitVerzoekBerichtOrigineel;
        final ErkenningAntwoordBericht erkenningAntwoordBericht = new ErkenningAntwoordBericht();

        transseksualiteitVerzoekBerichtKopie.setMessageId(transseksualiteitVerzoekBerichtOrigineel.getMessageId());
        transseksualiteitVerzoekBerichtKopie.setCorrelationId(transseksualiteitVerzoekBerichtOrigineel
                .getCorrelationId());

        assertEquals(true,
                transseksualiteitVerzoekBerichtObjectKopie.equals(transseksualiteitVerzoekBerichtOrigineel));
        assertEquals(false, transseksualiteitVerzoekBerichtOrigineel.equals(erkenningAntwoordBericht));
        assertEquals(true, transseksualiteitVerzoekBerichtKopie.equals(transseksualiteitVerzoekBerichtOrigineel));
        assertEquals(transseksualiteitVerzoekBerichtObjectKopie.hashCode(),
                transseksualiteitVerzoekBerichtOrigineel.hashCode());
        assertEquals(transseksualiteitVerzoekBerichtKopie.hashCode(),
                transseksualiteitVerzoekBerichtOrigineel.hashCode());
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
                        TransseksualiteitVerzoekBerichtTest.class
                                .getResourceAsStream("transseksualiteitVerzoekBerichtSyntaxExceptionBericht.xml"));

        final TransseksualiteitVerzoekBericht transseksualiteitVerzoekBericht = new TransseksualiteitVerzoekBericht();

        try {
            transseksualiteitVerzoekBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("transseksualiteitVerzoekBericht.xml"));
        final TransseksualiteitVerzoekBericht transseksualiteitVerzoekBericht =
                (TransseksualiteitVerzoekBericht) factory.getBericht(berichtOrigineel);

        final String BRP_GEMEENTE_CODE = "1904";
        final String LO3_GEMEENTE_CODE = "1903";
        transseksualiteitVerzoekBericht.setBrpGemeente(new BrpGemeenteCode(new BigDecimal(BRP_GEMEENTE_CODE)));
        transseksualiteitVerzoekBericht.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal(LO3_GEMEENTE_CODE)));

        assertEquals(BRP_GEMEENTE_CODE, transseksualiteitVerzoekBericht.getBrpGemeente().getFormattedStringCode());
        assertEquals(LO3_GEMEENTE_CODE, transseksualiteitVerzoekBericht.getLo3Gemeente().getFormattedStringCode());
        Assert.assertNotNull(transseksualiteitVerzoekBericht.getBrpPersoonslijst());
        assertEquals("1234", transseksualiteitVerzoekBericht.getRegistratiegemeente());
        assertEquals("1234567", transseksualiteitVerzoekBericht.getAktenummer());
        assertEquals(new BigInteger("12345678"), transseksualiteitVerzoekBericht.getIngangsdatumGeldigheid());
    }

    @Test
    public void testMaakAntwoord() throws Exception {

        final String berichtOrigineel =
                IOUtils.toString(TransseksualiteitVerzoekBerichtTest.class
                        .getResourceAsStream("transseksualiteitVerzoekBericht.xml"));
        final TransseksualiteitVerzoekBericht transseksualiteitVerzoekBericht =
                (TransseksualiteitVerzoekBericht) factory.getBericht(berichtOrigineel);

        final Object antwoordBericht = transseksualiteitVerzoekBericht.maakAntwoordBericht();

        Assert.assertNotNull(antwoordBericht);
        Assert.assertTrue(antwoordBericht instanceof TransseksualiteitAntwoordBericht);
        Assert.assertEquals(StatusType.OK, ((TransseksualiteitAntwoordBericht) antwoordBericht).getStatus());

    }

    @Test
    public void testBrpPersoonslijstGetter() throws Exception {

        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("transseksualiteitVerzoekBericht.xml"));
        final TransseksualiteitVerzoekBericht transseksualiteitVerzoekBericht =
                (TransseksualiteitVerzoekBericht) factory.getBericht(berichtOrigineel);

        Assert.assertNotNull(transseksualiteitVerzoekBericht.getBrpPersoonslijst());

    }

    @Test
    public void testBrpPersoonslijstGetterZonderInhoud() throws Exception {

        final TransseksualiteitVerzoekType transseksualiteitVerzoekType = new TransseksualiteitVerzoekType();
        final TransseksualiteitVerzoekBericht transseksualiteitVerzoekBericht =
                new TransseksualiteitVerzoekBericht(transseksualiteitVerzoekType);

        Assert.assertNull(transseksualiteitVerzoekBericht.getBrpPersoonslijst());

    }

    @Test
    public void testBrpPersoonslijstGetterZonderVerzoekType() throws Exception {

        final TransseksualiteitVerzoekBericht transseksualiteitVerzoekBericht =
                new TransseksualiteitVerzoekBericht(null);

        Assert.assertNull(transseksualiteitVerzoekBericht.getBrpPersoonslijst());

    }

    @Test
    public void tesBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(TransseksualiteitVerzoekBerichtTest.class
                        .getResourceAsStream("transseksualiteitVerzoekBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }
}
