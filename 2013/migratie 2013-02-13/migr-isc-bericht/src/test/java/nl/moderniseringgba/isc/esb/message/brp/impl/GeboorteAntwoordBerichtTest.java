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
import nl.moderniseringgba.isc.esb.message.brp.generated.GeboorteAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class GeboorteAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(GeboorteAntwoordBerichtTest.class.getResourceAsStream("geboorteAntwoordBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final GeboorteAntwoordBericht geboorteAntwoordBericht = (GeboorteAntwoordBericht) bericht;
        assertEquals("GeboorteAntwoord", geboorteAntwoordBericht.getBerichtType());
        assertEquals(StatusType.OK, geboorteAntwoordBericht.getStatus());
        assertEquals("A-nummer wel gevonden, bedankt", geboorteAntwoordBericht.getToelichting());
        assertEquals(null, geboorteAntwoordBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final GeboorteAntwoordType geboorteAntwoordType = new GeboorteAntwoordType();
        geboorteAntwoordType.setStatus(StatusType.FOUT);
        final GeboorteAntwoordBericht geboorteBericht = new GeboorteAntwoordBericht(geboorteAntwoordType);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("GeboorteAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testFoutBericht() throws Exception {
        final GeboorteAntwoordBericht geboorteBericht = new GeboorteAntwoordBericht();
        geboorteBericht.setStatus(StatusType.FOUT);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("GeboorteAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testEquals() throws Exception {
        final GeboorteAntwoordType geboorteAntwoordType = new GeboorteAntwoordType();
        geboorteAntwoordType.setStatus(StatusType.OK);
        geboorteAntwoordType.setToelichting("Testtoelichting");
        final GeboorteAntwoordBericht geboorteAntwoordBerichtOrigineel =
                new GeboorteAntwoordBericht(geboorteAntwoordType);
        final GeboorteAntwoordBericht geboorteAntwoordBerichtKopie =
                new GeboorteAntwoordBericht(geboorteAntwoordType);
        final GeboorteAntwoordBericht geboorteAntwoordBerichtObjectKopie = geboorteAntwoordBerichtOrigineel;
        final GeboorteVerzoekBericht geboorteVerzoekBericht = new GeboorteVerzoekBericht();

        geboorteAntwoordBerichtKopie.setMessageId(geboorteAntwoordBerichtOrigineel.getMessageId());
        geboorteAntwoordBerichtKopie.setCorrelationId(geboorteAntwoordBerichtOrigineel.getCorrelationId());

        assertEquals(true, geboorteAntwoordBerichtObjectKopie.equals(geboorteAntwoordBerichtOrigineel));
        assertEquals(false, geboorteAntwoordBerichtOrigineel.equals(geboorteVerzoekBericht));
        assertEquals(true, geboorteAntwoordBerichtKopie.equals(geboorteAntwoordBerichtOrigineel));
        assertEquals(geboorteAntwoordBerichtObjectKopie.hashCode(), geboorteAntwoordBerichtOrigineel.hashCode());
        assertEquals(geboorteAntwoordBerichtKopie.hashCode(), geboorteAntwoordBerichtOrigineel.hashCode());
        assertEquals(geboorteAntwoordBerichtKopie.toString(), geboorteAntwoordBerichtOrigineel.toString());
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
                        GeboorteAntwoordBerichtTest.class
                                .getResourceAsStream("geboorteAntwoordBerichtSyntaxExceptionBericht.xml"));

        final GeboorteAntwoordBericht geboorteAntwoordBericht = new GeboorteAntwoordBericht();

        try {
            geboorteAntwoordBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final GeboorteAntwoordBericht geboorteAntwoordBericht = new GeboorteAntwoordBericht();
        final String TOELICHTING = "Testtoelichting";
        geboorteAntwoordBericht.setStatus(StatusType.FOUT);
        geboorteAntwoordBericht.setToelichting(TOELICHTING);

        assertEquals(StatusType.FOUT, geboorteAntwoordBericht.getStatus());
        assertEquals(TOELICHTING, geboorteAntwoordBericht.getToelichting());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(GeboorteAntwoordBerichtTest.class
                        .getResourceAsStream("geboorteAntwoordBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }

}
