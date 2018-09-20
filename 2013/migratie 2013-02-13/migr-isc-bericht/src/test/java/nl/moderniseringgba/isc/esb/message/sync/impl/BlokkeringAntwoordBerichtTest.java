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
import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.PersoonsaanduidingType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class BlokkeringAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(BlokkeringAntwoordBerichtTest.class
                        .getResourceAsStream("blokkeringAntwoordBericht.xml"));
        final SyncBericht bericht = factory.getBericht(berichtOrigineel);

        final BlokkeringAntwoordBericht blokkeringAntwoordBericht = (BlokkeringAntwoordBericht) bericht;
        assertEquals("BlokkeringAntwoord", blokkeringAntwoordBericht.getBerichtType());
        assertEquals(StatusType.OK, blokkeringAntwoordBericht.getStatus());
        assertEquals("PL geblokkeerd", blokkeringAntwoordBericht.getToelichting());
        assertEquals(PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP,
                blokkeringAntwoordBericht.getPersoonsaanduiding());
        assertEquals(null, blokkeringAntwoordBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final BlokkeringAntwoordType blokkeringAntwoordType = new BlokkeringAntwoordType();
        blokkeringAntwoordType.setStatus(StatusType.FOUT);
        final BlokkeringAntwoordBericht blokkeringBericht = new BlokkeringAntwoordBericht(blokkeringAntwoordType);

        LOG.info("Geformat: {}", blokkeringBericht.format());
        assertEquals("BlokkeringAntwoord", blokkeringBericht.getBerichtType());
        assertEquals(StatusType.FOUT, blokkeringBericht.getStatus());
    }

    @Test
    public void testFoutBericht() throws Exception {
        final BlokkeringAntwoordBericht blokkeringBericht = new BlokkeringAntwoordBericht();
        blokkeringBericht.setStatus(StatusType.FOUT);

        LOG.info("Geformat: {}", blokkeringBericht.format());
        assertEquals("BlokkeringAntwoord", blokkeringBericht.getBerichtType());
        assertEquals(StatusType.FOUT, blokkeringBericht.getStatus());
    }

    @Test
    public void testEquals() throws Exception {
        final BlokkeringAntwoordType blokkeringAntwoordType = new BlokkeringAntwoordType();
        blokkeringAntwoordType.setStatus(StatusType.OK);
        blokkeringAntwoordType.setToelichting("Testtoelichting");
        final BlokkeringAntwoordBericht blokkeringAntwoordBerichtOrigineel =
                new BlokkeringAntwoordBericht(blokkeringAntwoordType);
        final BlokkeringAntwoordBericht blokkeringAntwoordBerichtKopie =
                new BlokkeringAntwoordBericht(blokkeringAntwoordType);
        final BlokkeringAntwoordBericht blokkeringAntwoordBerichtObjectKopie = blokkeringAntwoordBerichtOrigineel;
        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht = new DeblokkeringAntwoordBericht();

        blokkeringAntwoordBerichtKopie.setMessageId(blokkeringAntwoordBerichtOrigineel.getMessageId());
        blokkeringAntwoordBerichtKopie.setCorrelationId(blokkeringAntwoordBerichtOrigineel.getCorrelationId());

        assertEquals(true, blokkeringAntwoordBerichtObjectKopie.equals(blokkeringAntwoordBerichtOrigineel));
        assertEquals(false, blokkeringAntwoordBerichtOrigineel.equals(deblokkeringAntwoordBericht));
        assertEquals(true, blokkeringAntwoordBerichtKopie.equals(blokkeringAntwoordBerichtOrigineel));
        assertEquals(blokkeringAntwoordBerichtObjectKopie.hashCode(), blokkeringAntwoordBerichtOrigineel.hashCode());
        assertEquals(blokkeringAntwoordBerichtKopie.hashCode(), blokkeringAntwoordBerichtOrigineel.hashCode());
        assertEquals(blokkeringAntwoordBerichtKopie.toString(), blokkeringAntwoordBerichtOrigineel.toString());
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
                        BlokkeringAntwoordBerichtTest.class
                                .getResourceAsStream("blokkeringAntwoordBerichtSyntaxExceptionBericht.xml"));

        final BlokkeringAntwoordBericht blokkeringAntwoordBericht = new BlokkeringAntwoordBericht();

        try {
            blokkeringAntwoordBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final BlokkeringAntwoordBericht blokkeringAntwoordBericht = new BlokkeringAntwoordBericht();
        final String TOELICHTING = "Testtoelichting";
        blokkeringAntwoordBericht.setStatus(StatusType.FOUT);
        blokkeringAntwoordBericht.setToelichting(TOELICHTING);

        assertEquals(StatusType.FOUT, blokkeringAntwoordBericht.getStatus());
        assertEquals(TOELICHTING, blokkeringAntwoordBericht.getToelichting());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(BlokkeringAntwoordBerichtTest.class
                        .getResourceAsStream("blokkeringAntwoordBerichtSyntaxExceptionBericht.xml"));

        final SyncBericht syncBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(syncBericht instanceof OngeldigBericht);
    }

}
