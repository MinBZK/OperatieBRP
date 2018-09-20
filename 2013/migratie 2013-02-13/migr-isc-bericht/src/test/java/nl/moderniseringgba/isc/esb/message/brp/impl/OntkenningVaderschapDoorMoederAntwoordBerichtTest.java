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
import nl.moderniseringgba.isc.esb.message.brp.generated.OntkenningVaderschapDoorMoederAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class OntkenningVaderschapDoorMoederAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(OntkenningVaderschapDoorMoederAntwoordBerichtTest.class
                        .getResourceAsStream("ontkenningVaderschapDoorMoederAntwoordBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final OntkenningVaderschapDoorMoederAntwoordBericht ontkenningVaderschapDoorMoederAntwoordBericht =
                (OntkenningVaderschapDoorMoederAntwoordBericht) bericht;
        assertEquals("OntkenningVaderschapDoorMoederAntwoord",
                ontkenningVaderschapDoorMoederAntwoordBericht.getBerichtType());
        assertEquals(StatusType.OK, ontkenningVaderschapDoorMoederAntwoordBericht.getStatus());
        assertEquals("A-nummer wel gevonden, bedankt", ontkenningVaderschapDoorMoederAntwoordBericht.getToelichting());
        assertEquals(null, ontkenningVaderschapDoorMoederAntwoordBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final OntkenningVaderschapDoorMoederAntwoordType ontkenningVaderschapDoorMoederAntwoordType =
                new OntkenningVaderschapDoorMoederAntwoordType();
        ontkenningVaderschapDoorMoederAntwoordType.setStatus(StatusType.FOUT);
        final OntkenningVaderschapDoorMoederAntwoordBericht geboorteBericht =
                new OntkenningVaderschapDoorMoederAntwoordBericht(ontkenningVaderschapDoorMoederAntwoordType);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("OntkenningVaderschapDoorMoederAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testFoutBericht() throws Exception {
        final OntkenningVaderschapDoorMoederAntwoordBericht geboorteBericht =
                new OntkenningVaderschapDoorMoederAntwoordBericht();
        geboorteBericht.setStatus(StatusType.FOUT);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("OntkenningVaderschapDoorMoederAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testEquals() throws Exception {
        final OntkenningVaderschapDoorMoederAntwoordType ontkenningVaderschapDoorMoederAntwoordType =
                new OntkenningVaderschapDoorMoederAntwoordType();
        ontkenningVaderschapDoorMoederAntwoordType.setStatus(StatusType.OK);
        ontkenningVaderschapDoorMoederAntwoordType.setToelichting("Testtoelichting");
        final OntkenningVaderschapDoorMoederAntwoordBericht ontkenningVaderschapDoorMoederAntwoordBerichtOrigineel =
                new OntkenningVaderschapDoorMoederAntwoordBericht(ontkenningVaderschapDoorMoederAntwoordType);
        final OntkenningVaderschapDoorMoederAntwoordBericht ontkenningVaderschapDoorMoederAntwoordBerichtKopie =
                new OntkenningVaderschapDoorMoederAntwoordBericht(ontkenningVaderschapDoorMoederAntwoordType);
        final OntkenningVaderschapDoorMoederAntwoordBericht ontkenningVaderschapDoorMoederAntwoordBerichtObjectKopie =
                ontkenningVaderschapDoorMoederAntwoordBerichtOrigineel;
        final GeboorteVerzoekBericht geboorteVerzoekBericht = new GeboorteVerzoekBericht();

        ontkenningVaderschapDoorMoederAntwoordBerichtKopie
                .setMessageId(ontkenningVaderschapDoorMoederAntwoordBerichtOrigineel.getMessageId());
        ontkenningVaderschapDoorMoederAntwoordBerichtKopie
                .setCorrelationId(ontkenningVaderschapDoorMoederAntwoordBerichtOrigineel.getCorrelationId());

        assertEquals(true,
                ontkenningVaderschapDoorMoederAntwoordBerichtObjectKopie
                        .equals(ontkenningVaderschapDoorMoederAntwoordBerichtOrigineel));
        assertEquals(false, ontkenningVaderschapDoorMoederAntwoordBerichtOrigineel.equals(geboorteVerzoekBericht));
        assertEquals(true,
                ontkenningVaderschapDoorMoederAntwoordBerichtKopie
                        .equals(ontkenningVaderschapDoorMoederAntwoordBerichtOrigineel));
        assertEquals(ontkenningVaderschapDoorMoederAntwoordBerichtObjectKopie.hashCode(),
                ontkenningVaderschapDoorMoederAntwoordBerichtOrigineel.hashCode());
        assertEquals(ontkenningVaderschapDoorMoederAntwoordBerichtKopie.hashCode(),
                ontkenningVaderschapDoorMoederAntwoordBerichtOrigineel.hashCode());
        assertEquals(ontkenningVaderschapDoorMoederAntwoordBerichtKopie.toString(),
                ontkenningVaderschapDoorMoederAntwoordBerichtOrigineel.toString());
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
                        .parse(OntkenningVaderschapDoorMoederAntwoordBerichtTest.class
                                .getResourceAsStream("ontkenningVaderschapDoorMoederAntwoordBerichtSyntaxExceptionBericht.xml"));

        final OntkenningVaderschapDoorMoederAntwoordBericht ontkenningVaderschapDoorMoederAntwoordBericht =
                new OntkenningVaderschapDoorMoederAntwoordBericht();

        try {
            ontkenningVaderschapDoorMoederAntwoordBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final OntkenningVaderschapDoorMoederAntwoordBericht ontkenningVaderschapDoorMoederAntwoordBericht =
                new OntkenningVaderschapDoorMoederAntwoordBericht();
        final String TOELICHTING = "Testtoelichting";
        ontkenningVaderschapDoorMoederAntwoordBericht.setStatus(StatusType.FOUT);
        ontkenningVaderschapDoorMoederAntwoordBericht.setToelichting(TOELICHTING);

        assertEquals(StatusType.FOUT, ontkenningVaderschapDoorMoederAntwoordBericht.getStatus());
        assertEquals(TOELICHTING, ontkenningVaderschapDoorMoederAntwoordBericht.getToelichting());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(OntkenningVaderschapDoorMoederAntwoordBerichtTest.class
                        .getResourceAsStream("ontkenningVaderschapDoorMoederAntwoordBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }

}
