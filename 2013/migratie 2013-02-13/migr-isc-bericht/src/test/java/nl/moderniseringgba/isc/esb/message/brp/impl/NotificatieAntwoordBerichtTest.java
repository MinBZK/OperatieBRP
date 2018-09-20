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
import nl.moderniseringgba.isc.esb.message.brp.generated.NotificatieAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class NotificatieAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(NotificatieAntwoordBerichtTest.class
                        .getResourceAsStream("notificatieAntwoordBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final NotificatieAntwoordBericht notificatieAntwoordBericht = (NotificatieAntwoordBericht) bericht;
        assertEquals("NotificatieAntwoord", notificatieAntwoordBericht.getBerichtType());
        assertEquals(StatusType.OK, notificatieAntwoordBericht.getStatus());
        assertEquals("A-nummer wel gevonden, bedankt", notificatieAntwoordBericht.getToelichting());
        assertEquals(null, notificatieAntwoordBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final NotificatieAntwoordType noitificatieAntwoordType = new NotificatieAntwoordType();
        noitificatieAntwoordType.setStatus(StatusType.FOUT);
        final NotificatieAntwoordBericht notificatieAntwoordBericht =
                new NotificatieAntwoordBericht(noitificatieAntwoordType);

        LOG.info("Geformat: {}", notificatieAntwoordBericht.format());
        assertEquals("NotificatieAntwoord", notificatieAntwoordBericht.getBerichtType());
        assertEquals(StatusType.FOUT, notificatieAntwoordBericht.getStatus());
    }

    @Test
    public void testFoutBericht() throws Exception {
        final NotificatieAntwoordBericht notificatieBericht = new NotificatieAntwoordBericht();
        notificatieBericht.setStatus(StatusType.FOUT);

        LOG.info("Geformat: {}", notificatieBericht.format());
        assertEquals("NotificatieAntwoord", notificatieBericht.getBerichtType());
        assertEquals(StatusType.FOUT, notificatieBericht.getStatus());
    }

    @Test
    public void testEquals() throws Exception {
        final NotificatieAntwoordType notificatieAntwoordType = new NotificatieAntwoordType();
        notificatieAntwoordType.setStatus(StatusType.OK);
        notificatieAntwoordType.setToelichting("Testtoelichting");
        final NotificatieAntwoordBericht notificatieAntwoordBerichtOrigineel =
                new NotificatieAntwoordBericht(notificatieAntwoordType);
        final NotificatieAntwoordBericht notificatieAntwoordBerichtKopie =
                new NotificatieAntwoordBericht(notificatieAntwoordType);
        final NotificatieAntwoordBericht notificatieAntwoordBerichtObjectKopie = notificatieAntwoordBerichtOrigineel;
        final NotificatieVerzoekBericht notificatieVerzoekBericht = new NotificatieVerzoekBericht();

        notificatieAntwoordBerichtKopie.setMessageId(notificatieAntwoordBerichtOrigineel.getMessageId());
        notificatieAntwoordBerichtKopie.setCorrelationId(notificatieAntwoordBerichtOrigineel.getCorrelationId());

        assertEquals(true, notificatieAntwoordBerichtObjectKopie.equals(notificatieAntwoordBerichtOrigineel));
        assertEquals(false, notificatieAntwoordBerichtOrigineel.equals(notificatieVerzoekBericht));
        assertEquals(true, notificatieAntwoordBerichtKopie.equals(notificatieAntwoordBerichtOrigineel));
        assertEquals(notificatieAntwoordBerichtObjectKopie.hashCode(), notificatieAntwoordBerichtOrigineel.hashCode());
        assertEquals(notificatieAntwoordBerichtKopie.hashCode(), notificatieAntwoordBerichtOrigineel.hashCode());
        assertEquals(notificatieAntwoordBerichtKopie.toString(), notificatieAntwoordBerichtOrigineel.toString());
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
                        NotificatieAntwoordBerichtTest.class
                                .getResourceAsStream("notificatieAntwoordBerichtSyntaxExceptionBericht.xml"));

        final NotificatieAntwoordBericht notificatieAntwoordBericht = new NotificatieAntwoordBericht();

        try {
            notificatieAntwoordBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final NotificatieAntwoordBericht notificatieAntwoordBericht = new NotificatieAntwoordBericht();
        final String TOELICHTING = "Testtoelichting";
        notificatieAntwoordBericht.setStatus(StatusType.FOUT);
        notificatieAntwoordBericht.setToelichting(TOELICHTING);

        assertEquals(StatusType.FOUT, notificatieAntwoordBericht.getStatus());
        assertEquals(TOELICHTING, notificatieAntwoordBericht.getToelichting());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(NotificatieAntwoordBerichtTest.class
                        .getResourceAsStream("verhuizingAntwoordBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }

}
