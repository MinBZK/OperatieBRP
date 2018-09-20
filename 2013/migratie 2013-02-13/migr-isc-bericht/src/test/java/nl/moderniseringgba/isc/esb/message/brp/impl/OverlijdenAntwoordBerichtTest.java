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
import nl.moderniseringgba.isc.esb.message.brp.generated.OverlijdenAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class OverlijdenAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(OverlijdenAntwoordBerichtTest.class
                        .getResourceAsStream("overlijdenAntwoordBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final OverlijdenAntwoordBericht overlijdenAntwoordBericht = (OverlijdenAntwoordBericht) bericht;
        assertEquals("OverlijdenAntwoord", overlijdenAntwoordBericht.getBerichtType());
        assertEquals(StatusType.OK, overlijdenAntwoordBericht.getStatus());
        assertEquals("A-nummer wel gevonden, bedankt", overlijdenAntwoordBericht.getToelichting());
        assertEquals(null, overlijdenAntwoordBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final OverlijdenAntwoordType overlijdenAntwoordType = new OverlijdenAntwoordType();
        overlijdenAntwoordType.setStatus(StatusType.FOUT);
        final OverlijdenAntwoordBericht geboorteBericht = new OverlijdenAntwoordBericht(overlijdenAntwoordType);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("OverlijdenAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testFoutBericht() throws Exception {
        final OverlijdenAntwoordBericht geboorteBericht = new OverlijdenAntwoordBericht();
        geboorteBericht.setStatus(StatusType.FOUT);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("OverlijdenAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testEquals() throws Exception {
        final OverlijdenAntwoordType overlijdenAntwoordType = new OverlijdenAntwoordType();
        overlijdenAntwoordType.setStatus(StatusType.OK);
        overlijdenAntwoordType.setToelichting("Testtoelichting");
        final OverlijdenAntwoordBericht overlijdenAntwoordBerichtOrigineel =
                new OverlijdenAntwoordBericht(overlijdenAntwoordType);
        final OverlijdenAntwoordBericht overlijdenAntwoordBerichtKopie =
                new OverlijdenAntwoordBericht(overlijdenAntwoordType);
        final OverlijdenAntwoordBericht overlijdenAntwoordBerichtObjectKopie = overlijdenAntwoordBerichtOrigineel;
        final GeboorteVerzoekBericht geboorteVerzoekBericht = new GeboorteVerzoekBericht();

        overlijdenAntwoordBerichtKopie.setMessageId(overlijdenAntwoordBerichtOrigineel.getMessageId());
        overlijdenAntwoordBerichtKopie.setCorrelationId(overlijdenAntwoordBerichtOrigineel.getCorrelationId());

        assertEquals(true, overlijdenAntwoordBerichtObjectKopie.equals(overlijdenAntwoordBerichtOrigineel));
        assertEquals(false, overlijdenAntwoordBerichtOrigineel.equals(geboorteVerzoekBericht));
        assertEquals(true, overlijdenAntwoordBerichtKopie.equals(overlijdenAntwoordBerichtOrigineel));
        assertEquals(overlijdenAntwoordBerichtObjectKopie.hashCode(), overlijdenAntwoordBerichtOrigineel.hashCode());
        assertEquals(overlijdenAntwoordBerichtKopie.hashCode(), overlijdenAntwoordBerichtOrigineel.hashCode());
        assertEquals(overlijdenAntwoordBerichtKopie.toString(), overlijdenAntwoordBerichtOrigineel.toString());
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
                        OverlijdenAntwoordBerichtTest.class
                                .getResourceAsStream("overlijdenAntwoordBerichtSyntaxExceptionBericht.xml"));

        final OverlijdenAntwoordBericht overlijdenAntwoordBericht = new OverlijdenAntwoordBericht();

        try {
            overlijdenAntwoordBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final OverlijdenAntwoordBericht overlijdenAntwoordBericht = new OverlijdenAntwoordBericht();
        final String TOELICHTING = "Testtoelichting";
        overlijdenAntwoordBericht.setStatus(StatusType.FOUT);
        overlijdenAntwoordBericht.setToelichting(TOELICHTING);

        assertEquals(StatusType.FOUT, overlijdenAntwoordBericht.getStatus());
        assertEquals(TOELICHTING, overlijdenAntwoordBericht.getToelichting());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(OverlijdenAntwoordBerichtTest.class
                        .getResourceAsStream("overlijdenAntwoordBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }

}
