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
import nl.moderniseringgba.isc.esb.message.brp.generated.ErkenningVernietigingAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class ErkenningVernietigingAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(ErkenningVernietigingAntwoordBerichtTest.class
                        .getResourceAsStream("erkenningVernietigingAntwoordBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final ErkenningVernietigingAntwoordBericht erkenningVernietigingAntwoordBericht =
                (ErkenningVernietigingAntwoordBericht) bericht;
        assertEquals("ErkenningVernietigingAntwoord", erkenningVernietigingAntwoordBericht.getBerichtType());
        assertEquals(StatusType.OK, erkenningVernietigingAntwoordBericht.getStatus());
        assertEquals("A-nummer wel gevonden, bedankt", erkenningVernietigingAntwoordBericht.getToelichting());
        assertEquals(null, erkenningVernietigingAntwoordBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final ErkenningVernietigingAntwoordType erkenningVernietigingAntwoordType =
                new ErkenningVernietigingAntwoordType();
        erkenningVernietigingAntwoordType.setStatus(StatusType.FOUT);
        final ErkenningVernietigingAntwoordBericht geboorteBericht =
                new ErkenningVernietigingAntwoordBericht(erkenningVernietigingAntwoordType);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("ErkenningVernietigingAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testFoutBericht() throws Exception {
        final ErkenningVernietigingAntwoordBericht geboorteBericht = new ErkenningVernietigingAntwoordBericht();
        geboorteBericht.setStatus(StatusType.FOUT);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("ErkenningVernietigingAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testEquals() throws Exception {
        final ErkenningVernietigingAntwoordType erkenningVernietigingAntwoordType =
                new ErkenningVernietigingAntwoordType();
        erkenningVernietigingAntwoordType.setStatus(StatusType.OK);
        erkenningVernietigingAntwoordType.setToelichting("Testtoelichting");
        final ErkenningVernietigingAntwoordBericht erkenningVernietigingAntwoordBerichtOrigineel =
                new ErkenningVernietigingAntwoordBericht(erkenningVernietigingAntwoordType);
        final ErkenningVernietigingAntwoordBericht erkenningVernietigingAntwoordBerichtKopie =
                new ErkenningVernietigingAntwoordBericht(erkenningVernietigingAntwoordType);
        final ErkenningVernietigingAntwoordBericht erkenningVernietigingAntwoordBerichtObjectKopie =
                erkenningVernietigingAntwoordBerichtOrigineel;
        final GeboorteVerzoekBericht geboorteVerzoekBericht = new GeboorteVerzoekBericht();

        erkenningVernietigingAntwoordBerichtKopie.setMessageId(erkenningVernietigingAntwoordBerichtOrigineel
                .getMessageId());
        erkenningVernietigingAntwoordBerichtKopie.setCorrelationId(erkenningVernietigingAntwoordBerichtOrigineel
                .getCorrelationId());

        assertEquals(true,
                erkenningVernietigingAntwoordBerichtObjectKopie.equals(erkenningVernietigingAntwoordBerichtOrigineel));
        assertEquals(false, erkenningVernietigingAntwoordBerichtOrigineel.equals(geboorteVerzoekBericht));
        assertEquals(true,
                erkenningVernietigingAntwoordBerichtKopie.equals(erkenningVernietigingAntwoordBerichtOrigineel));
        assertEquals(erkenningVernietigingAntwoordBerichtObjectKopie.hashCode(),
                erkenningVernietigingAntwoordBerichtOrigineel.hashCode());
        assertEquals(erkenningVernietigingAntwoordBerichtKopie.hashCode(),
                erkenningVernietigingAntwoordBerichtOrigineel.hashCode());
        assertEquals(erkenningVernietigingAntwoordBerichtKopie.toString(),
                erkenningVernietigingAntwoordBerichtOrigineel.toString());
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
                factory.newDocumentBuilder()
                        .parse(ErkenningVernietigingAntwoordBerichtTest.class
                                .getResourceAsStream("erkenningVernietigingAntwoordBerichtSyntaxExceptionBericht.xml"));

        final ErkenningVernietigingAntwoordBericht erkenningVernietigingAntwoordBericht =
                new ErkenningVernietigingAntwoordBericht();

        try {
            erkenningVernietigingAntwoordBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final ErkenningVernietigingAntwoordBericht erkenningVernietigingAntwoordBericht =
                new ErkenningVernietigingAntwoordBericht();
        final String TOELICHTING = "Testtoelichting";
        erkenningVernietigingAntwoordBericht.setStatus(StatusType.FOUT);
        erkenningVernietigingAntwoordBericht.setToelichting(TOELICHTING);

        assertEquals(StatusType.FOUT, erkenningVernietigingAntwoordBericht.getStatus());
        assertEquals(TOELICHTING, erkenningVernietigingAntwoordBericht.getToelichting());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(ErkenningVernietigingAntwoordBerichtTest.class
                        .getResourceAsStream("erkenningVernietigingAntwoordBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }

}
