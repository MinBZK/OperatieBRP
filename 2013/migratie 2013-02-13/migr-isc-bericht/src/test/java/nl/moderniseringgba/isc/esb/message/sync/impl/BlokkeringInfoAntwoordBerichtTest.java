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
import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringInfoAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.PersoonsaanduidingType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class BlokkeringInfoAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(BlokkeringInfoAntwoordBerichtTest.class
                        .getResourceAsStream("blokkeringInfoAntwoordBericht.xml"));
        final SyncBericht bericht = factory.getBericht(berichtOrigineel);

        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBericht = (BlokkeringInfoAntwoordBericht) bericht;
        assertEquals(PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP,
                blokkeringInfoAntwoordBericht.getPersoonsaanduiding());
        assertEquals("1234567890", blokkeringInfoAntwoordBericht.getProcessId());
        assertEquals(StatusType.GEBLOKKEERD, blokkeringInfoAntwoordBericht.getStatus());
        assertEquals("PL geblokkeerd", blokkeringInfoAntwoordBericht.getToelichting());
        assertEquals(null, blokkeringInfoAntwoordBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final BlokkeringInfoAntwoordType blokkeringInfoAntwoordType = new BlokkeringInfoAntwoordType();
        blokkeringInfoAntwoordType.setStatus(StatusType.FOUT);
        final BlokkeringInfoAntwoordBericht blokkeringBericht =
                new BlokkeringInfoAntwoordBericht(blokkeringInfoAntwoordType);

        LOG.info("Geformat: {}", blokkeringBericht.format());
        assertEquals("BlokkeringInfoAntwoord", blokkeringBericht.getBerichtType());
        assertEquals(StatusType.FOUT, blokkeringBericht.getStatus());
    }

    @Test
    public void testFoutBericht() throws Exception {
        final BlokkeringInfoAntwoordBericht blokkeringBericht = new BlokkeringInfoAntwoordBericht();
        blokkeringBericht.setStatus(StatusType.FOUT);

        LOG.info("Geformat: {}", blokkeringBericht.format());
        assertEquals("BlokkeringInfoAntwoord", blokkeringBericht.getBerichtType());
        assertEquals(StatusType.FOUT, blokkeringBericht.getStatus());
    }

    @Test
    public void testEquals() throws Exception {
        final BlokkeringInfoAntwoordType blokkeringInfoAntwoordType = new BlokkeringInfoAntwoordType();
        blokkeringInfoAntwoordType.setStatus(StatusType.OK);
        blokkeringInfoAntwoordType.setToelichting("Testtoelichting");
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBerichtOrigineel =
                new BlokkeringInfoAntwoordBericht(blokkeringInfoAntwoordType);
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBerichtKopie =
                new BlokkeringInfoAntwoordBericht(blokkeringInfoAntwoordType);
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBerichtObjectKopie =
                blokkeringInfoAntwoordBerichtOrigineel;
        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht = new DeblokkeringAntwoordBericht();

        blokkeringInfoAntwoordBerichtKopie.setMessageId(blokkeringInfoAntwoordBerichtOrigineel.getMessageId());
        blokkeringInfoAntwoordBerichtKopie
                .setCorrelationId(blokkeringInfoAntwoordBerichtOrigineel.getCorrelationId());

        assertEquals(true, blokkeringInfoAntwoordBerichtObjectKopie.equals(blokkeringInfoAntwoordBerichtOrigineel));
        assertEquals(false, blokkeringInfoAntwoordBerichtOrigineel.equals(deblokkeringAntwoordBericht));
        assertEquals(true, blokkeringInfoAntwoordBerichtKopie.equals(blokkeringInfoAntwoordBerichtOrigineel));
        assertEquals(blokkeringInfoAntwoordBerichtObjectKopie.hashCode(),
                blokkeringInfoAntwoordBerichtOrigineel.hashCode());
        assertEquals(blokkeringInfoAntwoordBerichtKopie.hashCode(), blokkeringInfoAntwoordBerichtOrigineel.hashCode());
        assertEquals(blokkeringInfoAntwoordBerichtKopie.toString(), blokkeringInfoAntwoordBerichtOrigineel.toString());
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
                        BlokkeringInfoAntwoordBerichtTest.class
                                .getResourceAsStream("blokkeringInfoAntwoordBerichtSyntaxExceptionBericht.xml"));

        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBericht = new BlokkeringInfoAntwoordBericht();

        try {
            blokkeringInfoAntwoordBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBericht = new BlokkeringInfoAntwoordBericht();
        final String TOELICHTING = "Testtoelichting";
        final String PROCESS_ID = "123456";
        blokkeringInfoAntwoordBericht.setStatus(StatusType.FOUT);
        blokkeringInfoAntwoordBericht.setToelichting(TOELICHTING);
        blokkeringInfoAntwoordBericht.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA);
        blokkeringInfoAntwoordBericht.setProcessId(PROCESS_ID);

        assertEquals(StatusType.FOUT, blokkeringInfoAntwoordBericht.getStatus());
        assertEquals(TOELICHTING, blokkeringInfoAntwoordBericht.getToelichting());
        assertEquals(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA,
                blokkeringInfoAntwoordBericht.getPersoonsaanduiding());
        assertEquals(PROCESS_ID, blokkeringInfoAntwoordBericht.getProcessId());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(BlokkeringInfoAntwoordBerichtTest.class
                        .getResourceAsStream("blokkeringInfoAntwoordBerichtSyntaxExceptionBericht.xml"));

        final SyncBericht syncBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(syncBericht instanceof OngeldigBericht);
    }

}
