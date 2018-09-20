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
import nl.moderniseringgba.isc.esb.message.brp.generated.ErkenningNotarieelAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class ErkenningNotarieelAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(ErkenningNotarieelAntwoordBerichtTest.class
                        .getResourceAsStream("erkenningNotarieelAntwoordBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final ErkenningNotarieelAntwoordBericht erkenningNotarieelAntwoordBericht =
                (ErkenningNotarieelAntwoordBericht) bericht;
        assertEquals("ErkenningNotarieelAntwoord", erkenningNotarieelAntwoordBericht.getBerichtType());
        assertEquals(StatusType.OK, erkenningNotarieelAntwoordBericht.getStatus());
        assertEquals("A-nummer wel gevonden, bedankt", erkenningNotarieelAntwoordBericht.getToelichting());
        assertEquals(null, erkenningNotarieelAntwoordBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final ErkenningNotarieelAntwoordType erkenningNotarieelAntwoordType = new ErkenningNotarieelAntwoordType();
        erkenningNotarieelAntwoordType.setStatus(StatusType.FOUT);
        final ErkenningNotarieelAntwoordBericht geboorteBericht =
                new ErkenningNotarieelAntwoordBericht(erkenningNotarieelAntwoordType);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("ErkenningNotarieelAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testFoutBericht() throws Exception {
        final ErkenningNotarieelAntwoordBericht erkenningNotarieelAntwoordBericht =
                new ErkenningNotarieelAntwoordBericht();
        erkenningNotarieelAntwoordBericht.setStatus(StatusType.FOUT);

        LOG.info("Geformat: {}", erkenningNotarieelAntwoordBericht.format());
        assertEquals("ErkenningNotarieelAntwoord", erkenningNotarieelAntwoordBericht.getBerichtType());
        assertEquals(StatusType.FOUT, erkenningNotarieelAntwoordBericht.getStatus());
    }

    @Test
    public void testEquals() throws Exception {
        final ErkenningNotarieelAntwoordType erkenningNotarieelAntwoordType = new ErkenningNotarieelAntwoordType();
        erkenningNotarieelAntwoordType.setStatus(StatusType.OK);
        erkenningNotarieelAntwoordType.setToelichting("Testtoelichting");
        final ErkenningNotarieelAntwoordBericht erkenningNotarieelAntwoordBerichtOrigineel =
                new ErkenningNotarieelAntwoordBericht(erkenningNotarieelAntwoordType);
        final ErkenningNotarieelAntwoordBericht erkenningNotarieelAntwoordBerichtKopie =
                new ErkenningNotarieelAntwoordBericht(erkenningNotarieelAntwoordType);
        final ErkenningNotarieelAntwoordBericht erkenningNotarieelAntwoordBerichtObjectKopie =
                erkenningNotarieelAntwoordBerichtOrigineel;
        final GeboorteVerzoekBericht geboorteVerzoekBericht = new GeboorteVerzoekBericht();

        erkenningNotarieelAntwoordBerichtKopie
                .setMessageId(erkenningNotarieelAntwoordBerichtOrigineel.getMessageId());
        erkenningNotarieelAntwoordBerichtKopie.setCorrelationId(erkenningNotarieelAntwoordBerichtOrigineel
                .getCorrelationId());

        assertEquals(true,
                erkenningNotarieelAntwoordBerichtObjectKopie.equals(erkenningNotarieelAntwoordBerichtOrigineel));
        assertEquals(false, erkenningNotarieelAntwoordBerichtOrigineel.equals(geboorteVerzoekBericht));
        assertEquals(true, erkenningNotarieelAntwoordBerichtKopie.equals(erkenningNotarieelAntwoordBerichtOrigineel));
        assertEquals(erkenningNotarieelAntwoordBerichtObjectKopie.hashCode(),
                erkenningNotarieelAntwoordBerichtOrigineel.hashCode());
        assertEquals(erkenningNotarieelAntwoordBerichtKopie.hashCode(),
                erkenningNotarieelAntwoordBerichtOrigineel.hashCode());
        assertEquals(erkenningNotarieelAntwoordBerichtKopie.toString(),
                erkenningNotarieelAntwoordBerichtOrigineel.toString());
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
                        ErkenningNotarieelAntwoordBerichtTest.class
                                .getResourceAsStream("erkenningNotarieelAntwoordBerichtSyntaxExceptionBericht.xml"));

        final ErkenningNotarieelAntwoordBericht erkenningNotarieelAntwoordBericht =
                new ErkenningNotarieelAntwoordBericht();

        try {
            erkenningNotarieelAntwoordBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final ErkenningNotarieelAntwoordBericht erkenningNotarieelAntwoordBericht =
                new ErkenningNotarieelAntwoordBericht();
        final String TOELICHTING = "Testtoelichting";
        erkenningNotarieelAntwoordBericht.setStatus(StatusType.FOUT);
        erkenningNotarieelAntwoordBericht.setToelichting(TOELICHTING);

        assertEquals(StatusType.FOUT, erkenningNotarieelAntwoordBericht.getStatus());
        assertEquals(TOELICHTING, erkenningNotarieelAntwoordBericht.getToelichting());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(ErkenningNotarieelAntwoordBerichtTest.class
                        .getResourceAsStream("erkenningNotarieelAntwoordBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }

}
