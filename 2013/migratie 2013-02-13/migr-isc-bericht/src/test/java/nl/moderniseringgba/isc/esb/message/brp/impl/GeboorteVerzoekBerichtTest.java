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

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.GeboorteVerzoekType;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class GeboorteVerzoekBerichtTest {
    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testFormatEnParse() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(GeboorteVerzoekBerichtTest.class.getResourceAsStream("geboorteVerzoekBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);
        System.out.println(bericht);

        final GeboorteVerzoekBericht geboorteVerzoekBericht = (GeboorteVerzoekBericht) bericht;
        assertEquals("GeboorteVerzoek", geboorteVerzoekBericht.getBerichtType());
        assertEquals("uc306", geboorteVerzoekBericht.getStartCyclus());
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
                        .getResourceAsStream("geboorteVerzoekBericht.xml"));

        final BrpBericht geboorteVerzoekBerichtOrigineel = factory.getBericht(berichtOrigineel);

        final GeboorteVerzoekBericht geboorteVerzoekBerichtKopie =
                (GeboorteVerzoekBericht) factory.getBericht(berichtOrigineel);
        final GeboorteVerzoekBericht geboorteVerzoekBerichtObjectKopie =
                (GeboorteVerzoekBericht) geboorteVerzoekBerichtOrigineel;
        final GeboorteAntwoordBericht geboorteAntwoordBericht = new GeboorteAntwoordBericht();

        geboorteVerzoekBerichtKopie.setMessageId(geboorteVerzoekBerichtOrigineel.getMessageId());
        geboorteVerzoekBerichtKopie.setCorrelationId(geboorteVerzoekBerichtOrigineel.getCorrelationId());

        assertEquals(true, geboorteVerzoekBerichtObjectKopie.equals(geboorteVerzoekBerichtOrigineel));
        assertEquals(false, geboorteVerzoekBerichtOrigineel.equals(geboorteAntwoordBericht));
        assertEquals(true, geboorteVerzoekBerichtKopie.equals(geboorteVerzoekBerichtOrigineel));
        assertEquals(geboorteVerzoekBerichtObjectKopie.hashCode(), geboorteVerzoekBerichtOrigineel.hashCode());
        assertEquals(geboorteVerzoekBerichtKopie.hashCode(), geboorteVerzoekBerichtOrigineel.hashCode());
        assertEquals(geboorteVerzoekBerichtKopie.toString(), geboorteVerzoekBerichtOrigineel.toString());
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
                        GeboorteVerzoekBerichtTest.class
                                .getResourceAsStream("geboorteVerzoekBerichtSyntaxExceptionBericht.xml"));

        final GeboorteVerzoekBericht geboorteVerzoekBericht = new GeboorteVerzoekBericht();

        try {
            geboorteVerzoekBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final GeboorteVerzoekBericht geboorteVerzoekBericht = new GeboorteVerzoekBericht();
        final String BRP_GEMEENTE_CODE = "1904";
        final BrpPersoonslijst brpPL = new BrpPersoonslijstBuilder().build();
        geboorteVerzoekBericht.setBrpGemeente(new BrpGemeenteCode(new BigDecimal(BRP_GEMEENTE_CODE)));
        geboorteVerzoekBericht.setBrpPersoonslijst(brpPL);

        assertEquals(BRP_GEMEENTE_CODE, geboorteVerzoekBericht.getBrpGemeente().getFormattedStringCode());
        assertEquals(brpPL, geboorteVerzoekBericht.getBrpPersoonslijst());
    }

    @Test
    public void testBrpPersoonslijstGetter() throws Exception {

        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("geboorteVerzoekBericht.xml"));
        final GeboorteVerzoekBericht geboorteVerzoekBericht =
                (GeboorteVerzoekBericht) factory.getBericht(berichtOrigineel);

        Assert.assertNotNull(geboorteVerzoekBericht.getBrpPersoonslijst());

    }

    @Test
    public void testBrpPersoonslijstGetterZonderInhoud() throws Exception {

        final GeboorteVerzoekType geboorteVerzoekType = new GeboorteVerzoekType();
        final GeboorteVerzoekBericht geboorteVerzoekBericht = new GeboorteVerzoekBericht(geboorteVerzoekType);

        Assert.assertNull(geboorteVerzoekBericht.getBrpPersoonslijst());

    }

    @Test
    public void testBrpPersoonslijstGetterZonderVerzoekType() throws Exception {

        final GeboorteVerzoekBericht geboorteVerzoekBericht = new GeboorteVerzoekBericht(null);

        Assert.assertNull(geboorteVerzoekBericht.getBrpPersoonslijst());

    }

    @Test
    public void tesBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(GeboorteVerzoekBerichtTest.class
                        .getResourceAsStream("geboorteVerzoekBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }
}
