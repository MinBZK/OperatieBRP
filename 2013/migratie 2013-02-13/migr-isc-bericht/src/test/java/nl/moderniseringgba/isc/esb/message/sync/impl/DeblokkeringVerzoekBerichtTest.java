/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.sync.impl;

import static org.junit.Assert.assertEquals;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.sync.SyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBerichtFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.DeblokkeringVerzoekType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;

public class DeblokkeringVerzoekBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    @Ignore
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(DeblokkeringVerzoekBerichtTest.class
                        .getResourceAsStream("deblokkeringVerzoekBericht.xml"));
        final SyncBericht bericht = factory.getBericht(berichtOrigineel);

        final DeblokkeringVerzoekBericht deblokkeringVerzoekBericht = (DeblokkeringVerzoekBericht) bericht;
        assertEquals("1234567890", deblokkeringVerzoekBericht.getANummer());
        assertEquals(null, deblokkeringVerzoekBericht.getStartCyclus());

    }

    @Test
    @Ignore
    public void testFormat() throws Exception {
        final DeblokkeringVerzoekType deblokkeringVerzoekType = new DeblokkeringVerzoekType();
        deblokkeringVerzoekType.setANummer("1234567890");
        deblokkeringVerzoekType.setProcessId("1232");

        final DeblokkeringVerzoekBericht deblokkeringVerzoekBericht =
                new DeblokkeringVerzoekBericht(deblokkeringVerzoekType);
        final String geformat = deblokkeringVerzoekBericht.format();

        LOG.info("Geformat: {}", geformat);
        // controleer of geformat bericht weer geparsed kan worden
        final DeblokkeringVerzoekBericht format = (DeblokkeringVerzoekBericht) factory.getBericht(geformat);
        assertEquals("DeblokkeringVerzoek", format.getBerichtType());
    }

    @Test
    @Ignore
    public void testEquals() throws Exception {
        final DeblokkeringVerzoekType deblokkeringVerzoekType = new DeblokkeringVerzoekType();
        deblokkeringVerzoekType.setANummer("1234567890");
        deblokkeringVerzoekType.setProcessId("1232");

        final DeblokkeringVerzoekBericht deblokkeringVerzoekBerichtOrigineel =
                new DeblokkeringVerzoekBericht(deblokkeringVerzoekType);
        final DeblokkeringVerzoekBericht deblokkeringVerzoekBerichtKopie =
                new DeblokkeringVerzoekBericht(deblokkeringVerzoekType);
        final DeblokkeringVerzoekBericht deblokkeringVerzoekBerichtObjectKopie = deblokkeringVerzoekBerichtOrigineel;
        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht = new DeblokkeringAntwoordBericht();

        deblokkeringVerzoekBerichtKopie.setMessageId(deblokkeringVerzoekBerichtOrigineel.getMessageId());
        deblokkeringVerzoekBerichtKopie.setCorrelationId(deblokkeringVerzoekBerichtOrigineel.getCorrelationId());

        assertEquals(true, deblokkeringVerzoekBerichtObjectKopie.equals(deblokkeringVerzoekBerichtOrigineel));
        assertEquals(false, deblokkeringVerzoekBerichtOrigineel.equals(deblokkeringAntwoordBericht));
        assertEquals(true, deblokkeringVerzoekBerichtKopie.equals(deblokkeringVerzoekBerichtOrigineel));
        assertEquals(deblokkeringVerzoekBerichtObjectKopie.hashCode(), deblokkeringVerzoekBerichtOrigineel.hashCode());
        assertEquals(deblokkeringVerzoekBerichtKopie.hashCode(), deblokkeringVerzoekBerichtOrigineel.hashCode());
        assertEquals(deblokkeringVerzoekBerichtKopie.toString(), deblokkeringVerzoekBerichtOrigineel.toString());
    }

    @Test(expected = BerichtInhoudException.class)
    @Ignore
    public void testParse() throws Exception {

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        final SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        // maak een resolver aan zodat alle gerefereerde xsd's ook gevonden kunnen worden.
        schemaFactory.setResourceResolver(new LSResourceResolver());

        factory.setSchema(schemaFactory.newSchema(new Source[] { new StreamSource(SyncBerichtFactory.class
                .getResourceAsStream("/xsd/SYNC_Berichten.xsd")), }));

        final Document document =
                factory.newDocumentBuilder().parse(
                        DeblokkeringVerzoekBerichtTest.class
                                .getResourceAsStream("deblokkeringVerzoekBerichtSyntaxExceptionBericht.xml"));

        final DeblokkeringVerzoekBericht deblokkeringVerzoekBericht = new DeblokkeringVerzoekBericht();

        try {
            deblokkeringVerzoekBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    @Ignore
    public void testSettersEnGetters() throws Exception {
        final DeblokkeringVerzoekBericht deblokkeringVerzoekBericht = new DeblokkeringVerzoekBericht();
        final String ANUMMER = "1234567890";
        final String PROCESS_ID = "123456";
        deblokkeringVerzoekBericht.setANummer(ANUMMER);
        deblokkeringVerzoekBericht.setProcessId(PROCESS_ID);

        assertEquals(ANUMMER, deblokkeringVerzoekBericht.getANummer());
        assertEquals(PROCESS_ID, deblokkeringVerzoekBericht.getProcessId());
    }

    @Test
    @Ignore
    public void tesBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(BlokkeringVerzoekBerichtTest.class
                        .getResourceAsStream("blokkeringVerzoekBerichtSyntaxExceptionBericht.xml"));

        final SyncBericht syncBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(syncBericht instanceof OngeldigBericht);
    }

}
