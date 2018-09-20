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
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.generated.VerhuizingAntwoordType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class VerhuizingAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(VerhuizingAntwoordBerichtTest.class
                        .getResourceAsStream("verhuizingAntwoordBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final VerhuizingAntwoordBericht verhuizingAntwoordBericht = (VerhuizingAntwoordBericht) bericht;
        assertEquals("VerhuizingAntwoord", verhuizingAntwoordBericht.getBerichtType());
        assertEquals(StatusType.OK, verhuizingAntwoordBericht.getStatus());
        assertEquals("A-nummer wel gevonden, bedankt", verhuizingAntwoordBericht.getToelichting());
        assertEquals(null, verhuizingAntwoordBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final VerhuizingAntwoordType verhuizingAntwoordType = new VerhuizingAntwoordType();
        verhuizingAntwoordType.setStatus(StatusType.FOUT);
        final VerhuizingAntwoordBericht verhuizingBericht = new VerhuizingAntwoordBericht(verhuizingAntwoordType);

        LOG.info("Geformat: {}", verhuizingBericht.format());
        assertEquals("VerhuizingAntwoord", verhuizingBericht.getBerichtType());
        assertEquals(StatusType.FOUT, verhuizingBericht.getStatus());
    }

    @Test
    public void testFoutBericht() throws Exception {
        final VerhuizingAntwoordBericht verhuizingBericht = new VerhuizingAntwoordBericht();
        verhuizingBericht.setStatus(StatusType.FOUT);

        LOG.info("Geformat: {}", verhuizingBericht.format());
        assertEquals("VerhuizingAntwoord", verhuizingBericht.getBerichtType());
        assertEquals(StatusType.FOUT, verhuizingBericht.getStatus());
    }

    @Test
    public void testEquals() throws Exception {
        final VerhuizingAntwoordType verhuizingAntwoordType = new VerhuizingAntwoordType();
        verhuizingAntwoordType.setStatus(StatusType.OK);
        verhuizingAntwoordType.setToelichting("Testtoelichting");
        final VerhuizingAntwoordBericht verhuizingAntwoordBerichtOrigineel =
                new VerhuizingAntwoordBericht(verhuizingAntwoordType);
        final VerhuizingAntwoordBericht verhuizingAntwoordBerichtKopie =
                new VerhuizingAntwoordBericht(verhuizingAntwoordType);
        final VerhuizingAntwoordBericht verhuizingAntwoordBerichtObjectKopie = verhuizingAntwoordBerichtOrigineel;
        final VerhuizingVerzoekBericht verhuizingVerzoekBericht = new VerhuizingVerzoekBericht();

        verhuizingAntwoordBerichtKopie.setMessageId(verhuizingAntwoordBerichtOrigineel.getMessageId());
        verhuizingAntwoordBerichtKopie.setCorrelationId(verhuizingAntwoordBerichtOrigineel.getCorrelationId());

        assertEquals(true, verhuizingAntwoordBerichtObjectKopie.equals(verhuizingAntwoordBerichtOrigineel));
        assertEquals(false, verhuizingAntwoordBerichtOrigineel.equals(verhuizingVerzoekBericht));
        assertEquals(true, verhuizingAntwoordBerichtKopie.equals(verhuizingAntwoordBerichtOrigineel));
        assertEquals(verhuizingAntwoordBerichtObjectKopie.hashCode(), verhuizingAntwoordBerichtOrigineel.hashCode());
        assertEquals(verhuizingAntwoordBerichtKopie.hashCode(), verhuizingAntwoordBerichtOrigineel.hashCode());
        assertEquals(verhuizingAntwoordBerichtKopie.toString(), verhuizingAntwoordBerichtOrigineel.toString());
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
                        VerhuizingAntwoordBerichtTest.class
                                .getResourceAsStream("verhuizingAntwoordBerichtSyntaxExceptionBericht.xml"));

        final VerhuizingAntwoordBericht verhuizingAntwoordBericht = new VerhuizingAntwoordBericht();

        try {
            verhuizingAntwoordBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final VerhuizingAntwoordBericht verhuizingAntwoordBericht = new VerhuizingAntwoordBericht();
        final String TOELICHTING = "Testtoelichting";
        verhuizingAntwoordBericht.setStatus(StatusType.FOUT);
        verhuizingAntwoordBericht.setToelichting(TOELICHTING);

        assertEquals(StatusType.FOUT, verhuizingAntwoordBericht.getStatus());
        assertEquals(TOELICHTING, verhuizingAntwoordBericht.getToelichting());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(VerhuizingAntwoordBerichtTest.class
                        .getResourceAsStream("verhuizingAntwoordBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }

}
