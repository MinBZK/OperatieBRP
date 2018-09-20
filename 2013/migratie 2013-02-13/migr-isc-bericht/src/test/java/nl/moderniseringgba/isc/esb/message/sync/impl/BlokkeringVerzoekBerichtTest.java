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
import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.PersoonsaanduidingType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class BlokkeringVerzoekBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(BlokkeringVerzoekBerichtTest.class
                        .getResourceAsStream("blokkeringVerzoekBericht.xml"));
        final SyncBericht bericht = factory.getBericht(berichtOrigineel);

        final BlokkeringVerzoekBericht blokkeringBericht = (BlokkeringVerzoekBericht) bericht;
        assertEquals("1234567890", blokkeringBericht.getANummer());
        assertEquals(null, blokkeringBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final BlokkeringVerzoekType blokkeringVerzoekType = new BlokkeringVerzoekType();
        blokkeringVerzoekType.setANummer("1234567890");
        blokkeringVerzoekType.setProcessId("1232");
        blokkeringVerzoekType.setGemeenteNaar("1905");
        blokkeringVerzoekType.setGemeenteRegistratie("1904");
        blokkeringVerzoekType.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA);

        final BlokkeringVerzoekBericht blokkeringBericht = new BlokkeringVerzoekBericht(blokkeringVerzoekType);
        final String geformat = blokkeringBericht.format();

        LOG.info("Geformat: {}", geformat);
        // controleer of geformat bericht weer geparsed kan worden
        final BlokkeringVerzoekBericht format = (BlokkeringVerzoekBericht) factory.getBericht(geformat);
        assertEquals("BlokkeringVerzoek", format.getBerichtType());
    }

    @Test
    public void testEquals() throws Exception {
        final BlokkeringVerzoekType blokkeringVerzoekType = new BlokkeringVerzoekType();
        blokkeringVerzoekType.setANummer("1234567890");
        blokkeringVerzoekType.setProcessId("1232");
        blokkeringVerzoekType.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA);

        final BlokkeringVerzoekBericht blokkeringVerzoekBerichtOrigineel =
                new BlokkeringVerzoekBericht(blokkeringVerzoekType);
        final BlokkeringVerzoekBericht blokkeringVerzoekBerichtKopie =
                new BlokkeringVerzoekBericht(blokkeringVerzoekType);
        final BlokkeringVerzoekBericht blokkeringVerzoekBerichtObjectKopie = blokkeringVerzoekBerichtOrigineel;
        final BlokkeringAntwoordBericht blokkeringAntwoordBericht = new BlokkeringAntwoordBericht();

        blokkeringVerzoekBerichtKopie.setMessageId(blokkeringVerzoekBerichtOrigineel.getMessageId());
        blokkeringVerzoekBerichtKopie.setCorrelationId(blokkeringVerzoekBerichtOrigineel.getCorrelationId());

        assertEquals(true, blokkeringVerzoekBerichtObjectKopie.equals(blokkeringVerzoekBerichtOrigineel));
        assertEquals(false, blokkeringVerzoekBerichtOrigineel.equals(blokkeringAntwoordBericht));
        assertEquals(true, blokkeringVerzoekBerichtKopie.equals(blokkeringVerzoekBerichtOrigineel));
        assertEquals(blokkeringVerzoekBerichtObjectKopie.hashCode(), blokkeringVerzoekBerichtOrigineel.hashCode());
        assertEquals(blokkeringVerzoekBerichtKopie.hashCode(), blokkeringVerzoekBerichtOrigineel.hashCode());
        assertEquals(blokkeringVerzoekBerichtKopie.toString(), blokkeringVerzoekBerichtOrigineel.toString());
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
                        BlokkeringVerzoekBerichtTest.class
                                .getResourceAsStream("blokkeringVerzoekBerichtSyntaxExceptionBericht.xml"));

        final BlokkeringVerzoekBericht blokkeringVerzoekBericht = new BlokkeringVerzoekBericht();

        try {
            blokkeringVerzoekBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final BlokkeringVerzoekBericht blokkeringVerzoekBericht = new BlokkeringVerzoekBericht();
        final String ANUMMER = "1234567890";
        final String PROCESS_ID = "123456";
        blokkeringVerzoekBericht.setANummer(ANUMMER);
        blokkeringVerzoekBericht.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA);
        blokkeringVerzoekBericht.setProcessId(PROCESS_ID);

        assertEquals(ANUMMER, blokkeringVerzoekBericht.getANummer());
        assertEquals(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA,
                blokkeringVerzoekBericht.getPersoonsaanduiding());
        assertEquals(PROCESS_ID, blokkeringVerzoekBericht.getProcessId());
    }

    @Test
    public void tesBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(BlokkeringVerzoekBerichtTest.class
                        .getResourceAsStream("blokkeringVerzoekBerichtSyntaxExceptionBericht.xml"));

        final SyncBericht syncBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(syncBericht instanceof OngeldigBericht);
    }

}
