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
import nl.moderniseringgba.isc.esb.message.brp.generated.GeslachtsnaamwijzigingVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class GeslachtsnaamwijzingVerzoekBerichtTest {
    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testFormatEnParse() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(GeslachtsnaamwijzingVerzoekBerichtTest.class
                        .getResourceAsStream("geslachtsnaamwijzigingVerzoekBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final GeslachtsnaamwijzigingVerzoekBericht geslachtsnaamwijzigingVerzoekBericht =
                (GeslachtsnaamwijzigingVerzoekBericht) bericht;
        assertEquals("GeslachtsnaamwijzigingVerzoek", geslachtsnaamwijzigingVerzoekBericht.getBerichtType());
        assertEquals("uc308", geslachtsnaamwijzigingVerzoekBericht.getStartCyclus());
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
                        .getResourceAsStream("geslachtsnaamwijzigingVerzoekBericht.xml"));

        final BrpBericht geslachtsnaamwijzigingVerzoekBerichtOrigineel = factory.getBericht(berichtOrigineel);

        final GeslachtsnaamwijzigingVerzoekBericht geslachtsnaamwijzigingVerzoekBerichtKopie =
                (GeslachtsnaamwijzigingVerzoekBericht) factory.getBericht(berichtOrigineel);
        final GeslachtsnaamwijzigingVerzoekBericht geslachtsnaamwijzigingVerzoekBerichtObjectKopie =
                (GeslachtsnaamwijzigingVerzoekBericht) geslachtsnaamwijzigingVerzoekBerichtOrigineel;
        final ErkenningAntwoordBericht erkenningAntwoordBericht = new ErkenningAntwoordBericht();

        geslachtsnaamwijzigingVerzoekBerichtKopie.setMessageId(geslachtsnaamwijzigingVerzoekBerichtOrigineel
                .getMessageId());
        geslachtsnaamwijzigingVerzoekBerichtKopie.setCorrelationId(geslachtsnaamwijzigingVerzoekBerichtOrigineel
                .getCorrelationId());

        assertEquals(true,
                geslachtsnaamwijzigingVerzoekBerichtObjectKopie.equals(geslachtsnaamwijzigingVerzoekBerichtOrigineel));
        assertEquals(false, geslachtsnaamwijzigingVerzoekBerichtOrigineel.equals(erkenningAntwoordBericht));
        assertEquals(true,
                geslachtsnaamwijzigingVerzoekBerichtKopie.equals(geslachtsnaamwijzigingVerzoekBerichtOrigineel));
        assertEquals(geslachtsnaamwijzigingVerzoekBerichtObjectKopie.hashCode(),
                geslachtsnaamwijzigingVerzoekBerichtOrigineel.hashCode());
        assertEquals(geslachtsnaamwijzigingVerzoekBerichtKopie.hashCode(),
                geslachtsnaamwijzigingVerzoekBerichtOrigineel.hashCode());
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
                factory.newDocumentBuilder()
                        .parse(GeslachtsnaamwijzingVerzoekBerichtTest.class
                                .getResourceAsStream("geslachtsnaamwijzigingVerzoekBerichtSyntaxExceptionBericht.xml"));

        final GeslachtsnaamwijzigingVerzoekBericht geslachtsnaamwijzigingVerzoekBericht =
                new GeslachtsnaamwijzigingVerzoekBericht();

        try {
            geslachtsnaamwijzigingVerzoekBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("geslachtsnaamwijzigingVerzoekBericht.xml"));
        final GeslachtsnaamwijzigingVerzoekBericht geslachtsnaamwijzigingVerzoekBericht =
                (GeslachtsnaamwijzigingVerzoekBericht) factory.getBericht(berichtOrigineel);

        final String BRP_GEMEENTE_CODE = "1904";
        final String LO3_GEMEENTE_CODE = "1903";
        geslachtsnaamwijzigingVerzoekBericht.setBrpGemeente(new BrpGemeenteCode(new BigDecimal(BRP_GEMEENTE_CODE)));
        geslachtsnaamwijzigingVerzoekBericht.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal(LO3_GEMEENTE_CODE)));

        assertEquals(BRP_GEMEENTE_CODE, geslachtsnaamwijzigingVerzoekBericht.getBrpGemeente()
                .getFormattedStringCode());
        assertEquals(LO3_GEMEENTE_CODE, geslachtsnaamwijzigingVerzoekBericht.getLo3Gemeente()
                .getFormattedStringCode());
        Assert.assertNotNull(geslachtsnaamwijzigingVerzoekBericht.getBrpPersoonslijst());
        assertEquals("1234", geslachtsnaamwijzigingVerzoekBericht.getRegistratiegemeente());
        assertEquals("1234567", geslachtsnaamwijzigingVerzoekBericht.getAktenummer());
        assertEquals(new BigInteger("12345678"), geslachtsnaamwijzigingVerzoekBericht.getIngangsdatumGeldigheid());
    }

    @Test
    public void testMaakAntwoord() throws Exception {

        final String berichtOrigineel =
                IOUtils.toString(GeslachtsnaamwijzingVerzoekBerichtTest.class
                        .getResourceAsStream("geslachtsnaamwijzigingVerzoekBericht.xml"));
        final GeslachtsnaamwijzigingVerzoekBericht geslachtsnaamwijzigingVerzoekBericht =
                (GeslachtsnaamwijzigingVerzoekBericht) factory.getBericht(berichtOrigineel);

        final Object antwoordBericht = geslachtsnaamwijzigingVerzoekBericht.maakAntwoordBericht();

        Assert.assertNotNull(antwoordBericht);
        Assert.assertTrue(antwoordBericht instanceof GeslachtsnaamwijzigingAntwoordBericht);
        Assert.assertEquals(StatusType.OK, ((GeslachtsnaamwijzigingAntwoordBericht) antwoordBericht).getStatus());

    }

    @Test
    public void testBrpPersoonslijstGetter() throws Exception {

        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("geslachtsnaamwijzigingVerzoekBericht.xml"));
        final GeslachtsnaamwijzigingVerzoekBericht geslachtsnaamwijzigingVerzoekBericht =
                (GeslachtsnaamwijzigingVerzoekBericht) factory.getBericht(berichtOrigineel);

        Assert.assertNotNull(geslachtsnaamwijzigingVerzoekBericht.getBrpPersoonslijst());

    }

    @Test
    public void testBrpPersoonslijstGetterZonderInhoud() throws Exception {

        final GeslachtsnaamwijzigingVerzoekType geslachtsnaamwijzigingVerzoekType =
                new GeslachtsnaamwijzigingVerzoekType();
        final GeslachtsnaamwijzigingVerzoekBericht geslachtsnaamwijzigingVerzoekBericht =
                new GeslachtsnaamwijzigingVerzoekBericht(geslachtsnaamwijzigingVerzoekType);

        Assert.assertNull(geslachtsnaamwijzigingVerzoekBericht.getBrpPersoonslijst());

    }

    @Test
    public void testBrpPersoonslijstGetterZonderVerzoekType() throws Exception {

        final GeslachtsnaamwijzigingVerzoekBericht geslachtsnaamwijzigingVerzoekBericht =
                new GeslachtsnaamwijzigingVerzoekBericht(null);

        Assert.assertNull(geslachtsnaamwijzigingVerzoekBericht.getBrpPersoonslijst());

    }

    @Test
    public void tesBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(GeslachtsnaamwijzingVerzoekBerichtTest.class
                        .getResourceAsStream("geslachtsnaamwijzigingVerzoekBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }
}
