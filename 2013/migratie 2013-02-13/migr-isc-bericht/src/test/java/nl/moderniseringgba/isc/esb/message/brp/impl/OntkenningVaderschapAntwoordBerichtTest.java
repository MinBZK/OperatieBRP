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
import nl.moderniseringgba.isc.esb.message.brp.generated.OntkenningVaderschapAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class OntkenningVaderschapAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(OntkenningVaderschapAntwoordBerichtTest.class
                        .getResourceAsStream("ontkenningVaderschapAntwoordBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final OntkenningVaderschapAntwoordBericht ontkenningVaderschapAntwoordBericht =
                (OntkenningVaderschapAntwoordBericht) bericht;
        assertEquals("OntkenningVaderschapAntwoord", ontkenningVaderschapAntwoordBericht.getBerichtType());
        assertEquals(StatusType.OK, ontkenningVaderschapAntwoordBericht.getStatus());
        assertEquals("A-nummer wel gevonden, bedankt", ontkenningVaderschapAntwoordBericht.getToelichting());
        assertEquals(null, ontkenningVaderschapAntwoordBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final OntkenningVaderschapAntwoordType ontkenningVaderschapAntwoordType =
                new OntkenningVaderschapAntwoordType();
        ontkenningVaderschapAntwoordType.setStatus(StatusType.FOUT);
        final OntkenningVaderschapAntwoordBericht geboorteBericht =
                new OntkenningVaderschapAntwoordBericht(ontkenningVaderschapAntwoordType);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("OntkenningVaderschapAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testFoutBericht() throws Exception {
        final OntkenningVaderschapAntwoordBericht geboorteBericht = new OntkenningVaderschapAntwoordBericht();
        geboorteBericht.setStatus(StatusType.FOUT);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("OntkenningVaderschapAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testEquals() throws Exception {
        final OntkenningVaderschapAntwoordType ontkenningVaderschapAntwoordType =
                new OntkenningVaderschapAntwoordType();
        ontkenningVaderschapAntwoordType.setStatus(StatusType.OK);
        ontkenningVaderschapAntwoordType.setToelichting("Testtoelichting");
        final OntkenningVaderschapAntwoordBericht ontkenningVaderschapAntwoordBerichtOrigineel =
                new OntkenningVaderschapAntwoordBericht(ontkenningVaderschapAntwoordType);
        final OntkenningVaderschapAntwoordBericht ontkenningVaderschapAntwoordBerichtKopie =
                new OntkenningVaderschapAntwoordBericht(ontkenningVaderschapAntwoordType);
        final OntkenningVaderschapAntwoordBericht ontkenningVaderschapAntwoordBerichtObjectKopie =
                ontkenningVaderschapAntwoordBerichtOrigineel;
        final GeboorteVerzoekBericht geboorteVerzoekBericht = new GeboorteVerzoekBericht();

        ontkenningVaderschapAntwoordBerichtKopie.setMessageId(ontkenningVaderschapAntwoordBerichtOrigineel
                .getMessageId());
        ontkenningVaderschapAntwoordBerichtKopie.setCorrelationId(ontkenningVaderschapAntwoordBerichtOrigineel
                .getCorrelationId());

        assertEquals(true,
                ontkenningVaderschapAntwoordBerichtObjectKopie.equals(ontkenningVaderschapAntwoordBerichtOrigineel));
        assertEquals(false, ontkenningVaderschapAntwoordBerichtOrigineel.equals(geboorteVerzoekBericht));
        assertEquals(true,
                ontkenningVaderschapAntwoordBerichtKopie.equals(ontkenningVaderschapAntwoordBerichtOrigineel));
        assertEquals(ontkenningVaderschapAntwoordBerichtObjectKopie.hashCode(),
                ontkenningVaderschapAntwoordBerichtOrigineel.hashCode());
        assertEquals(ontkenningVaderschapAntwoordBerichtKopie.hashCode(),
                ontkenningVaderschapAntwoordBerichtOrigineel.hashCode());
        assertEquals(ontkenningVaderschapAntwoordBerichtKopie.toString(),
                ontkenningVaderschapAntwoordBerichtOrigineel.toString());
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
                        .parse(OntkenningVaderschapAntwoordBerichtTest.class
                                .getResourceAsStream("ontkenningVaderschapAntwoordBerichtSyntaxExceptionBericht.xml"));

        final OntkenningVaderschapAntwoordBericht ontkenningVaderschapAntwoordBericht =
                new OntkenningVaderschapAntwoordBericht();

        try {
            ontkenningVaderschapAntwoordBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final OntkenningVaderschapAntwoordBericht ontkenningVaderschapAntwoordBericht =
                new OntkenningVaderschapAntwoordBericht();
        final String TOELICHTING = "Testtoelichting";
        ontkenningVaderschapAntwoordBericht.setStatus(StatusType.FOUT);
        ontkenningVaderschapAntwoordBericht.setToelichting(TOELICHTING);

        assertEquals(StatusType.FOUT, ontkenningVaderschapAntwoordBericht.getStatus());
        assertEquals(TOELICHTING, ontkenningVaderschapAntwoordBericht.getToelichting());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(OntkenningVaderschapAntwoordBerichtTest.class
                        .getResourceAsStream("ontkenningVaderschapAntwoordBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }

}
