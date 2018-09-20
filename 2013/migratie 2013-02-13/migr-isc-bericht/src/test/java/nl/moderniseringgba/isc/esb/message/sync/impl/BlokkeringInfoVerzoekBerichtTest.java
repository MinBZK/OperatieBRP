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
import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringInfoVerzoekType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class BlokkeringInfoVerzoekBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(BlokkeringInfoVerzoekBerichtTest.class
                        .getResourceAsStream("blokkeringInfoVerzoekBericht.xml"));
        final SyncBericht bericht = factory.getBericht(berichtOrigineel);

        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoekBericht = (BlokkeringInfoVerzoekBericht) bericht;
        assertEquals("1234567890", blokkeringInfoVerzoekBericht.getANummer());
        assertEquals("BlokkeringInfoVerzoek", blokkeringInfoVerzoekBericht.getBerichtType());
        assertEquals(null, blokkeringInfoVerzoekBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final BlokkeringInfoVerzoekType blokkeringInfoVerzoekType = new BlokkeringInfoVerzoekType();
        blokkeringInfoVerzoekType.setANummer("1234567890");

        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoekBericht =
                new BlokkeringInfoVerzoekBericht(blokkeringInfoVerzoekType);
        final String geformat = blokkeringInfoVerzoekBericht.format();

        LOG.info("Geformat: {}", geformat);
        // controleer of geformat bericht weer geparsed kan worden
        final BlokkeringInfoVerzoekBericht format = (BlokkeringInfoVerzoekBericht) factory.getBericht(geformat);
        assertEquals("BlokkeringInfoVerzoek", format.getBerichtType());
    }

    @Test
    public void testEquals() throws Exception {
        final BlokkeringInfoVerzoekType blokkeringInfoVerzoekType = new BlokkeringInfoVerzoekType();
        blokkeringInfoVerzoekType.setANummer("1234567890");
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoekBerichtOrigineel =
                new BlokkeringInfoVerzoekBericht(blokkeringInfoVerzoekType);
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoekBerichtKopie =
                new BlokkeringInfoVerzoekBericht(blokkeringInfoVerzoekType);
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoekBerichtObjectKopie =
                blokkeringInfoVerzoekBerichtOrigineel;
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBericht = new BlokkeringInfoAntwoordBericht();

        blokkeringInfoVerzoekBerichtKopie.setMessageId(blokkeringInfoVerzoekBerichtOrigineel.getMessageId());
        blokkeringInfoVerzoekBerichtKopie.setCorrelationId(blokkeringInfoVerzoekBerichtOrigineel.getCorrelationId());

        assertEquals(true, blokkeringInfoVerzoekBerichtObjectKopie.equals(blokkeringInfoVerzoekBerichtOrigineel));
        assertEquals(false, blokkeringInfoVerzoekBerichtOrigineel.equals(blokkeringInfoAntwoordBericht));
        assertEquals(true, blokkeringInfoVerzoekBerichtKopie.equals(blokkeringInfoVerzoekBerichtOrigineel));
        assertEquals(blokkeringInfoVerzoekBerichtObjectKopie.hashCode(),
                blokkeringInfoVerzoekBerichtOrigineel.hashCode());
        assertEquals(blokkeringInfoVerzoekBerichtKopie.hashCode(), blokkeringInfoVerzoekBerichtOrigineel.hashCode());
        assertEquals(blokkeringInfoVerzoekBerichtKopie.toString(), blokkeringInfoVerzoekBerichtOrigineel.toString());
    }

    @Test(expected = BerichtInhoudException.class)
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
                        BlokkeringInfoVerzoekBerichtTest.class
                                .getResourceAsStream("blokkeringInfoVerzoekBerichtSyntaxExceptionBericht.xml"));

        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoekBericht = new BlokkeringInfoVerzoekBericht();

        try {
            blokkeringInfoVerzoekBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoekBericht = new BlokkeringInfoVerzoekBericht();
        final String ANUMMER = "1234567890";
        blokkeringInfoVerzoekBericht.setANummer(ANUMMER);

        assertEquals(ANUMMER, blokkeringInfoVerzoekBericht.getANummer());
    }

    @Test
    public void tesBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(BlokkeringInfoVerzoekBerichtTest.class
                        .getResourceAsStream("blokkeringInfoVerzoekBerichtSyntaxExceptionBericht.xml"));

        final SyncBericht syncBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(syncBericht instanceof OngeldigBericht);
    }

}
