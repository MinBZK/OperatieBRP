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
import nl.moderniseringgba.isc.esb.message.brp.generated.GerechtelijkeVaststellingVaderschapAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class GerechtelijkeVaststellingVaderschapAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(GerechtelijkeVaststellingVaderschapAntwoordBerichtTest.class
                        .getResourceAsStream("gerechtelijkeVaststellingVaderschapAntwoordBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final GerechtelijkeVaststellingVaderschapAntwoordBericht gerechtelijkeVaststellingVaderschapAntwoordBericht =
                (GerechtelijkeVaststellingVaderschapAntwoordBericht) bericht;
        assertEquals("GerechtelijkeVaststellingVaderschapAntwoord",
                gerechtelijkeVaststellingVaderschapAntwoordBericht.getBerichtType());
        assertEquals(StatusType.OK, gerechtelijkeVaststellingVaderschapAntwoordBericht.getStatus());
        assertEquals("A-nummer wel gevonden, bedankt",
                gerechtelijkeVaststellingVaderschapAntwoordBericht.getToelichting());
        assertEquals(null, gerechtelijkeVaststellingVaderschapAntwoordBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final GerechtelijkeVaststellingVaderschapAntwoordType gerechtelijkeVaststellingVaderschapAntwoordType =
                new GerechtelijkeVaststellingVaderschapAntwoordType();
        gerechtelijkeVaststellingVaderschapAntwoordType.setStatus(StatusType.FOUT);
        final GerechtelijkeVaststellingVaderschapAntwoordBericht geboorteBericht =
                new GerechtelijkeVaststellingVaderschapAntwoordBericht(
                        gerechtelijkeVaststellingVaderschapAntwoordType);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("GerechtelijkeVaststellingVaderschapAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testFoutBericht() throws Exception {
        final GerechtelijkeVaststellingVaderschapAntwoordBericht geboorteBericht =
                new GerechtelijkeVaststellingVaderschapAntwoordBericht();
        geboorteBericht.setStatus(StatusType.FOUT);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("GerechtelijkeVaststellingVaderschapAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testEquals() throws Exception {
        final GerechtelijkeVaststellingVaderschapAntwoordType gerechtelijkeVaststellingVaderschapAntwoordType =
                new GerechtelijkeVaststellingVaderschapAntwoordType();
        gerechtelijkeVaststellingVaderschapAntwoordType.setStatus(StatusType.OK);
        gerechtelijkeVaststellingVaderschapAntwoordType.setToelichting("Testtoelichting");
        final GerechtelijkeVaststellingVaderschapAntwoordBericht gerechtelijkeVaststellingVaderschapAntwoordBerichtOrigineel =
                new GerechtelijkeVaststellingVaderschapAntwoordBericht(
                        gerechtelijkeVaststellingVaderschapAntwoordType);
        final GerechtelijkeVaststellingVaderschapAntwoordBericht gerechtelijkeVaststellingVaderschapAntwoordBerichtKopie =
                new GerechtelijkeVaststellingVaderschapAntwoordBericht(
                        gerechtelijkeVaststellingVaderschapAntwoordType);
        final GerechtelijkeVaststellingVaderschapAntwoordBericht gerechtelijkeVaststellingVaderschapAntwoordBerichtObjectKopie =
                gerechtelijkeVaststellingVaderschapAntwoordBerichtOrigineel;
        final GeboorteVerzoekBericht geboorteVerzoekBericht = new GeboorteVerzoekBericht();

        gerechtelijkeVaststellingVaderschapAntwoordBerichtKopie
                .setMessageId(gerechtelijkeVaststellingVaderschapAntwoordBerichtOrigineel.getMessageId());
        gerechtelijkeVaststellingVaderschapAntwoordBerichtKopie
                .setCorrelationId(gerechtelijkeVaststellingVaderschapAntwoordBerichtOrigineel.getCorrelationId());

        assertEquals(true,
                gerechtelijkeVaststellingVaderschapAntwoordBerichtObjectKopie
                        .equals(gerechtelijkeVaststellingVaderschapAntwoordBerichtOrigineel));
        assertEquals(false,
                gerechtelijkeVaststellingVaderschapAntwoordBerichtOrigineel.equals(geboorteVerzoekBericht));
        assertEquals(true,
                gerechtelijkeVaststellingVaderschapAntwoordBerichtKopie
                        .equals(gerechtelijkeVaststellingVaderschapAntwoordBerichtOrigineel));
        assertEquals(gerechtelijkeVaststellingVaderschapAntwoordBerichtObjectKopie.hashCode(),
                gerechtelijkeVaststellingVaderschapAntwoordBerichtOrigineel.hashCode());
        assertEquals(gerechtelijkeVaststellingVaderschapAntwoordBerichtKopie.hashCode(),
                gerechtelijkeVaststellingVaderschapAntwoordBerichtOrigineel.hashCode());
        assertEquals(gerechtelijkeVaststellingVaderschapAntwoordBerichtKopie.toString(),
                gerechtelijkeVaststellingVaderschapAntwoordBerichtOrigineel.toString());
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
                        .parse(GerechtelijkeVaststellingVaderschapAntwoordBerichtTest.class
                                .getResourceAsStream("gerechtelijkeVaststellingVaderschapAntwoordBerichtSyntaxExceptionBericht.xml"));

        final GerechtelijkeVaststellingVaderschapAntwoordBericht gerechtelijkeVaststellingVaderschapAntwoordBericht =
                new GerechtelijkeVaststellingVaderschapAntwoordBericht();

        try {
            gerechtelijkeVaststellingVaderschapAntwoordBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final GerechtelijkeVaststellingVaderschapAntwoordBericht gerechtelijkeVaststellingVaderschapAntwoordBericht =
                new GerechtelijkeVaststellingVaderschapAntwoordBericht();
        final String TOELICHTING = "Testtoelichting";
        gerechtelijkeVaststellingVaderschapAntwoordBericht.setStatus(StatusType.FOUT);
        gerechtelijkeVaststellingVaderschapAntwoordBericht.setToelichting(TOELICHTING);

        assertEquals(StatusType.FOUT, gerechtelijkeVaststellingVaderschapAntwoordBericht.getStatus());
        assertEquals(TOELICHTING, gerechtelijkeVaststellingVaderschapAntwoordBericht.getToelichting());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(GerechtelijkeVaststellingVaderschapAntwoordBerichtTest.class
                        .getResourceAsStream("gerechtelijkeVaststellingVaderschapAntwoordBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }

}
