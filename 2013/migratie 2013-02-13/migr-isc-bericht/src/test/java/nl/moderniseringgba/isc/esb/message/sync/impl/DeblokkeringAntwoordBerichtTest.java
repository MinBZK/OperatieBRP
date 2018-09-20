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
import nl.moderniseringgba.isc.esb.message.sync.generated.DeblokkeringAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class DeblokkeringAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(DeblokkeringAntwoordBerichtTest.class
                        .getResourceAsStream("deblokkeringAntwoordBericht.xml"));
        final SyncBericht bericht = factory.getBericht(berichtOrigineel);

        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht = (DeblokkeringAntwoordBericht) bericht;
        assertEquals("DeblokkeringAntwoord", deblokkeringAntwoordBericht.getBerichtType());
        assertEquals(StatusType.OK, deblokkeringAntwoordBericht.getStatus());
        assertEquals("PL gedeblokkeerd", deblokkeringAntwoordBericht.getToelichting());
        assertEquals(null, deblokkeringAntwoordBericht.getStartCyclus());

    }

    @Test
    public void testFormat() throws Exception {
        final DeblokkeringAntwoordType deblokkeringAntwoordType = new DeblokkeringAntwoordType();
        deblokkeringAntwoordType.setStatus(StatusType.FOUT);
        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht =
                new DeblokkeringAntwoordBericht(deblokkeringAntwoordType);

        LOG.info("Geformat: {}", deblokkeringAntwoordBericht.format());
        assertEquals("DeblokkeringAntwoord", deblokkeringAntwoordBericht.getBerichtType());
        assertEquals(StatusType.FOUT, deblokkeringAntwoordBericht.getStatus());
    }

    @Test
    public void testEquals() throws Exception {
        final DeblokkeringAntwoordType deblokkeringAntwoordType = new DeblokkeringAntwoordType();
        deblokkeringAntwoordType.setStatus(StatusType.OK);
        deblokkeringAntwoordType.setToelichting("Testtoelichting");
        final DeblokkeringAntwoordBericht deblokkeringAntwoordBerichtOrigineel =
                new DeblokkeringAntwoordBericht(deblokkeringAntwoordType);
        final DeblokkeringAntwoordBericht deblokkeringAntwoordBerichtKopie =
                new DeblokkeringAntwoordBericht(deblokkeringAntwoordType);
        final DeblokkeringAntwoordBericht deblokkeringAntwoordBerichtObjectKopie =
                deblokkeringAntwoordBerichtOrigineel;
        final BlokkeringAntwoordBericht blokkeringAntwoordBericht = new BlokkeringAntwoordBericht();

        deblokkeringAntwoordBerichtKopie.setMessageId(deblokkeringAntwoordBerichtOrigineel.getMessageId());
        deblokkeringAntwoordBerichtKopie.setCorrelationId(deblokkeringAntwoordBerichtOrigineel.getCorrelationId());

        assertEquals(true, deblokkeringAntwoordBerichtObjectKopie.equals(deblokkeringAntwoordBerichtOrigineel));
        assertEquals(false, deblokkeringAntwoordBerichtOrigineel.equals(blokkeringAntwoordBericht));
        assertEquals(true, deblokkeringAntwoordBerichtKopie.equals(deblokkeringAntwoordBerichtOrigineel));
        assertEquals(deblokkeringAntwoordBerichtObjectKopie.hashCode(),
                deblokkeringAntwoordBerichtOrigineel.hashCode());
        assertEquals(deblokkeringAntwoordBerichtKopie.hashCode(), deblokkeringAntwoordBerichtOrigineel.hashCode());
        assertEquals(deblokkeringAntwoordBerichtKopie.toString(), deblokkeringAntwoordBerichtOrigineel.toString());
    }

    @Test
    public void testFoutBericht() throws Exception {
        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht = new DeblokkeringAntwoordBericht();
        deblokkeringAntwoordBericht.setStatus(StatusType.FOUT);

        LOG.info("Geformat: {}", deblokkeringAntwoordBericht.format());
        assertEquals("DeblokkeringAntwoord", deblokkeringAntwoordBericht.getBerichtType());
        assertEquals(StatusType.FOUT, deblokkeringAntwoordBericht.getStatus());
    }

    @Test(expected = BerichtInhoudException.class)
    public void testParse() throws Exception {

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        final SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        // maak een resolver aan zodat alle gerefereerde xsd's ook gevonden kunnen worden.
        schemaFactory.setResourceResolver(new nl.moderniseringgba.isc.esb.message.brp.impl.LSResourceResolver());

        factory.setSchema(schemaFactory.newSchema(new Source[] { new StreamSource(SyncBerichtFactory.class
                .getResourceAsStream("/xsd/SYNC_Berichten.xsd")), }));

        final Document document =
                factory.newDocumentBuilder().parse(
                        DeblokkeringAntwoordBerichtTest.class
                                .getResourceAsStream("deblokkeringAntwoordBerichtSyntaxExceptionBericht.xml"));

        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht = new DeblokkeringAntwoordBericht();

        try {
            deblokkeringAntwoordBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht = new DeblokkeringAntwoordBericht();
        final String TOELICHTING = "Testtoelichting";
        deblokkeringAntwoordBericht.setStatus(StatusType.FOUT);
        deblokkeringAntwoordBericht.setToelichting(TOELICHTING);

        assertEquals(StatusType.FOUT, deblokkeringAntwoordBericht.getStatus());
        assertEquals(TOELICHTING, deblokkeringAntwoordBericht.getToelichting());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(DeblokkeringAntwoordBerichtTest.class
                        .getResourceAsStream("deblokkeringAntwoordBerichtSyntaxExceptionBericht.xml"));

        final SyncBericht syncBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(syncBericht instanceof OngeldigBericht);
    }

}
