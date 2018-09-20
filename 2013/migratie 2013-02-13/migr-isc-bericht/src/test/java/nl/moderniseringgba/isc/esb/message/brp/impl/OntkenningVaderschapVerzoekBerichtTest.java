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
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class OntkenningVaderschapVerzoekBerichtTest {
    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testFormatEnParse() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(OntkenningVaderschapVerzoekBerichtTest.class
                        .getResourceAsStream("ontkenningVaderschapVerzoekBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final OntkenningVaderschapVerzoekBericht ontkenningVaderschapVerzoekBericht =
                (OntkenningVaderschapVerzoekBericht) bericht;
        assertEquals("OntkenningVaderschapVerzoek", ontkenningVaderschapVerzoekBericht.getBerichtType());
        assertEquals("uc308", ontkenningVaderschapVerzoekBericht.getStartCyclus());
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
                        .getResourceAsStream("ontkenningVaderschapVerzoekBericht.xml"));

        final BrpBericht ontkenningVaderschapVerzoekBerichtOrigineel = factory.getBericht(berichtOrigineel);

        final OntkenningVaderschapVerzoekBericht ontkenningVaderschapVerzoekBerichtKopie =
                (OntkenningVaderschapVerzoekBericht) factory.getBericht(berichtOrigineel);
        final OntkenningVaderschapVerzoekBericht ontkenningVaderschapVerzoekBerichtObjectKopie =
                (OntkenningVaderschapVerzoekBericht) ontkenningVaderschapVerzoekBerichtOrigineel;
        final ErkenningAntwoordBericht erkenningAntwoordBericht = new ErkenningAntwoordBericht();

        ontkenningVaderschapVerzoekBerichtKopie.setMessageId(ontkenningVaderschapVerzoekBerichtOrigineel
                .getMessageId());
        ontkenningVaderschapVerzoekBerichtKopie.setCorrelationId(ontkenningVaderschapVerzoekBerichtOrigineel
                .getCorrelationId());

        assertEquals(true,
                ontkenningVaderschapVerzoekBerichtObjectKopie.equals(ontkenningVaderschapVerzoekBerichtOrigineel));
        assertEquals(false, ontkenningVaderschapVerzoekBerichtOrigineel.equals(erkenningAntwoordBericht));
        assertEquals(true,
                ontkenningVaderschapVerzoekBerichtKopie.equals(ontkenningVaderschapVerzoekBerichtOrigineel));
        assertEquals(ontkenningVaderschapVerzoekBerichtObjectKopie.hashCode(),
                ontkenningVaderschapVerzoekBerichtOrigineel.hashCode());
        assertEquals(ontkenningVaderschapVerzoekBerichtKopie.hashCode(),
                ontkenningVaderschapVerzoekBerichtOrigineel.hashCode());
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
                        OntkenningVaderschapVerzoekBerichtTest.class
                                .getResourceAsStream("ontkenningVaderschapVerzoekBerichtSyntaxExceptionBericht.xml"));

        final OntkenningVaderschapVerzoekBericht ontkenningVaderschapVerzoekBericht =
                new OntkenningVaderschapVerzoekBericht();

        try {
            ontkenningVaderschapVerzoekBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("ontkenningVaderschapVerzoekBericht.xml"));
        final OntkenningVaderschapVerzoekBericht ontkenningVaderschapVerzoekBericht =
                (OntkenningVaderschapVerzoekBericht) factory.getBericht(berichtOrigineel);

        final String BRP_GEMEENTE_CODE = "1904";
        final String LO3_GEMEENTE_CODE = "1903";
        ontkenningVaderschapVerzoekBericht.setBrpGemeente(new BrpGemeenteCode(new BigDecimal(BRP_GEMEENTE_CODE)));
        ontkenningVaderschapVerzoekBericht.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal(LO3_GEMEENTE_CODE)));

        assertEquals(BRP_GEMEENTE_CODE, ontkenningVaderschapVerzoekBericht.getBrpGemeente().getFormattedStringCode());
        assertEquals(LO3_GEMEENTE_CODE, ontkenningVaderschapVerzoekBericht.getLo3Gemeente().getFormattedStringCode());
        assertEquals("1234", ontkenningVaderschapVerzoekBericht.getRegistratiegemeente());
        assertEquals("1234567", ontkenningVaderschapVerzoekBericht.getAktenummer());
        assertEquals(new BigInteger("12345678"), ontkenningVaderschapVerzoekBericht.getIngangsdatumGeldigheid());
        Assert.assertNotNull(ontkenningVaderschapVerzoekBericht.getBrpPersoonslijst());
    }

    @Test
    public void testBrpPersoonslijstGetter() throws Exception {

        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("ontkenningVaderschapVerzoekBericht.xml"));
        final OntkenningVaderschapVerzoekBericht ontkenningVaderschapVerzoekBericht =
                (OntkenningVaderschapVerzoekBericht) factory.getBericht(berichtOrigineel);

        Assert.assertNotNull(ontkenningVaderschapVerzoekBericht.getBrpPersoonslijst());

    }

    @Test
    public void testMaakAntwoord() throws Exception {

        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("ontkenningVaderschapVerzoekBericht.xml"));
        final OntkenningVaderschapVerzoekBericht ontkenningVaderschapVerzoekBericht =
                (OntkenningVaderschapVerzoekBericht) factory.getBericht(berichtOrigineel);

        final Object antwoordBericht = ontkenningVaderschapVerzoekBericht.maakAntwoordBericht();

        Assert.assertNotNull(antwoordBericht);
        Assert.assertTrue(antwoordBericht instanceof OntkenningVaderschapAntwoordBericht);
        Assert.assertEquals(StatusType.OK, ((OntkenningVaderschapAntwoordBericht) antwoordBericht).getStatus());

    }

    @Test
    public void tesBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(OntkenningVaderschapVerzoekBerichtTest.class
                        .getResourceAsStream("ontkenningVaderschapVerzoekBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }
}
