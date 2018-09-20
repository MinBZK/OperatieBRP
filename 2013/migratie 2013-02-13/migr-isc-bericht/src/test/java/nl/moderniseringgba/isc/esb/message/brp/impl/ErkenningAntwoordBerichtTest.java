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
import nl.moderniseringgba.isc.esb.message.brp.generated.ErkenningAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class ErkenningAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(ErkenningAntwoordBerichtTest.class
                        .getResourceAsStream("erkenningAntwoordBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final ErkenningAntwoordBericht erkenningAntwoordBericht = (ErkenningAntwoordBericht) bericht;
        assertEquals("ErkenningAntwoord", erkenningAntwoordBericht.getBerichtType());
        assertEquals(StatusType.OK, erkenningAntwoordBericht.getStatus());
        assertEquals("A-nummer wel gevonden, bedankt", erkenningAntwoordBericht.getToelichting());
        assertEquals(null, erkenningAntwoordBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final ErkenningAntwoordType erkenningAntwoordType = new ErkenningAntwoordType();
        erkenningAntwoordType.setStatus(StatusType.FOUT);
        final ErkenningAntwoordBericht geboorteBericht = new ErkenningAntwoordBericht(erkenningAntwoordType);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("ErkenningAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testFoutBericht() throws Exception {
        final ErkenningAntwoordBericht geboorteBericht = new ErkenningAntwoordBericht();
        geboorteBericht.setStatus(StatusType.FOUT);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("ErkenningAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testEquals() throws Exception {
        final ErkenningAntwoordType erkenningAntwoordType = new ErkenningAntwoordType();
        erkenningAntwoordType.setStatus(StatusType.OK);
        erkenningAntwoordType.setToelichting("Testtoelichting");
        final ErkenningAntwoordBericht erkenningAntwoordBerichtOrigineel =
                new ErkenningAntwoordBericht(erkenningAntwoordType);
        final ErkenningAntwoordBericht erkenningAntwoordBerichtKopie =
                new ErkenningAntwoordBericht(erkenningAntwoordType);
        final ErkenningAntwoordBericht erkenningAntwoordBerichtObjectKopie = erkenningAntwoordBerichtOrigineel;
        final GeboorteVerzoekBericht geboorteVerzoekBericht = new GeboorteVerzoekBericht();

        erkenningAntwoordBerichtKopie.setMessageId(erkenningAntwoordBerichtOrigineel.getMessageId());
        erkenningAntwoordBerichtKopie.setCorrelationId(erkenningAntwoordBerichtOrigineel.getCorrelationId());

        assertEquals(true, erkenningAntwoordBerichtObjectKopie.equals(erkenningAntwoordBerichtOrigineel));
        assertEquals(false, erkenningAntwoordBerichtOrigineel.equals(geboorteVerzoekBericht));
        assertEquals(true, erkenningAntwoordBerichtKopie.equals(erkenningAntwoordBerichtOrigineel));
        assertEquals(erkenningAntwoordBerichtObjectKopie.hashCode(), erkenningAntwoordBerichtOrigineel.hashCode());
        assertEquals(erkenningAntwoordBerichtKopie.hashCode(), erkenningAntwoordBerichtOrigineel.hashCode());
        assertEquals(erkenningAntwoordBerichtKopie.toString(), erkenningAntwoordBerichtOrigineel.toString());
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
                        ErkenningAntwoordBerichtTest.class
                                .getResourceAsStream("erkenningAntwoordBerichtSyntaxExceptionBericht.xml"));

        final ErkenningAntwoordBericht erkenningAntwoordBericht = new ErkenningAntwoordBericht();

        try {
            erkenningAntwoordBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final ErkenningAntwoordBericht erkenningAntwoordBericht = new ErkenningAntwoordBericht();
        final String TOELICHTING = "Testtoelichting";
        erkenningAntwoordBericht.setStatus(StatusType.FOUT);
        erkenningAntwoordBericht.setToelichting(TOELICHTING);

        assertEquals(StatusType.FOUT, erkenningAntwoordBericht.getStatus());
        assertEquals(TOELICHTING, erkenningAntwoordBericht.getToelichting());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(ErkenningAntwoordBerichtTest.class
                        .getResourceAsStream("erkenningAntwoordBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }

}
