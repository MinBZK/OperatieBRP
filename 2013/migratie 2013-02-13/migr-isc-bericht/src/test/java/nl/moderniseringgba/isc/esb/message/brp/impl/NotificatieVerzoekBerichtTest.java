/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import static org.junit.Assert.assertEquals;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.NotificatieVerzoekType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class NotificatieVerzoekBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("notificatieVerzoekBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final NotificatieVerzoekBericht notificatieBericht = (NotificatieVerzoekBericht) bericht;
        assertEquals("NotificatieVerzoek", notificatieBericht.getBerichtType());
        assertEquals("Ok, er is wat misgegaan...", notificatieBericht.getNotificatie());
        assertEquals(null, notificatieBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final NotificatieVerzoekType notificatieVerzoekType = new NotificatieVerzoekType();
        notificatieVerzoekType.setNotificatie("Er is iets misgegaan.");

        final NotificatieVerzoekBericht notificatieBericht = new NotificatieVerzoekBericht(notificatieVerzoekType);
        final String geformat = notificatieBericht.format();

        LOG.info("Geformat: {}", geformat);
        // controleer of geformat bericht weer geparsed kan worden
        final NotificatieVerzoekBericht format = (NotificatieVerzoekBericht) factory.getBericht(geformat);
        assertEquals("NotificatieVerzoek", format.getBerichtType());
    }

    @Test
    public void testEquals() throws Exception {
        final NotificatieVerzoekType notificatieVerzoekType = new NotificatieVerzoekType();
        notificatieVerzoekType.setNotificatie("Er is iets misgegaan...");

        final NotificatieVerzoekBericht notificatieVerzoekBerichtOrigineel =
                new NotificatieVerzoekBericht(notificatieVerzoekType);
        final NotificatieVerzoekBericht notificatieVerzoekBerichtKopie =
                new NotificatieVerzoekBericht(notificatieVerzoekType);
        final NotificatieVerzoekBericht notificatieVerzoekBerichtObjectKopie = notificatieVerzoekBerichtOrigineel;
        final NotificatieAntwoordBericht notificatieAntwoordBericht = new NotificatieAntwoordBericht();

        notificatieVerzoekBerichtKopie.setMessageId(notificatieVerzoekBerichtOrigineel.getMessageId());
        notificatieVerzoekBerichtKopie.setCorrelationId(notificatieVerzoekBerichtOrigineel.getCorrelationId());

        assertEquals(true, notificatieVerzoekBerichtObjectKopie.equals(notificatieVerzoekBerichtOrigineel));
        assertEquals(false, notificatieVerzoekBerichtOrigineel.equals(notificatieAntwoordBericht));
        assertEquals(true, notificatieVerzoekBerichtKopie.equals(notificatieVerzoekBerichtOrigineel));
        assertEquals(notificatieVerzoekBerichtObjectKopie.hashCode(), notificatieVerzoekBerichtOrigineel.hashCode());
        assertEquals(notificatieVerzoekBerichtKopie.hashCode(), notificatieVerzoekBerichtOrigineel.hashCode());
        assertEquals(notificatieVerzoekBerichtKopie.toString(), notificatieVerzoekBerichtOrigineel.toString());
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
                        NotificatieVerzoekBerichtTest.class
                                .getResourceAsStream("notificatieVerzoekBerichtSyntaxExceptionBericht.xml"));

        final NotificatieVerzoekBericht notificatieVerzoekBericht = new NotificatieVerzoekBericht();

        try {
            notificatieVerzoekBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testConvenientConstructor() throws Exception {
        final String CORRELATIE_ID = "123456";
        final String NOTIFICATIE = "Er is iets misgegaan...";
        final NotificatieVerzoekBericht notificatieVerzoekBericht =
                new NotificatieVerzoekBericht(CORRELATIE_ID, NOTIFICATIE);

        assertEquals(CORRELATIE_ID, notificatieVerzoekBericht.getCorrelationId());
        assertEquals(NOTIFICATIE, notificatieVerzoekBericht.getNotificatie());
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final NotificatieVerzoekBericht notificatieVerzoekBericht = new NotificatieVerzoekBericht();
        final String NOTIFICATIE = "Er is iets misgegaan...";

        notificatieVerzoekBericht.setNotificatie(NOTIFICATIE);

        assertEquals(NOTIFICATIE, notificatieVerzoekBericht.getNotificatie());
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
