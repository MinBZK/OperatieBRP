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
import nl.moderniseringgba.isc.esb.message.brp.generated.TransseksualiteitAntwoordType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class TransseksualiteitAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(TransseksualiteitAntwoordBerichtTest.class
                        .getResourceAsStream("transseksualiteitAntwoordBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final TransseksualiteitAntwoordBericht transseksualiteitAntwoordBericht =
                (TransseksualiteitAntwoordBericht) bericht;
        assertEquals("TransseksualiteitAntwoord", transseksualiteitAntwoordBericht.getBerichtType());
        assertEquals(StatusType.OK, transseksualiteitAntwoordBericht.getStatus());
        assertEquals("A-nummer wel gevonden, bedankt", transseksualiteitAntwoordBericht.getToelichting());
        assertEquals(null, transseksualiteitAntwoordBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final TransseksualiteitAntwoordType transseksualiteitAntwoordType = new TransseksualiteitAntwoordType();
        transseksualiteitAntwoordType.setStatus(StatusType.FOUT);
        final TransseksualiteitAntwoordBericht geboorteBericht =
                new TransseksualiteitAntwoordBericht(transseksualiteitAntwoordType);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("TransseksualiteitAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testFoutBericht() throws Exception {
        final TransseksualiteitAntwoordBericht geboorteBericht = new TransseksualiteitAntwoordBericht();
        geboorteBericht.setStatus(StatusType.FOUT);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("TransseksualiteitAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testEquals() throws Exception {
        final TransseksualiteitAntwoordType transseksualiteitAntwoordType = new TransseksualiteitAntwoordType();
        transseksualiteitAntwoordType.setStatus(StatusType.OK);
        transseksualiteitAntwoordType.setToelichting("Testtoelichting");
        final TransseksualiteitAntwoordBericht transseksualiteitAntwoordBerichtOrigineel =
                new TransseksualiteitAntwoordBericht(transseksualiteitAntwoordType);
        final TransseksualiteitAntwoordBericht transseksualiteitAntwoordBerichtKopie =
                new TransseksualiteitAntwoordBericht(transseksualiteitAntwoordType);
        final TransseksualiteitAntwoordBericht transseksualiteitAntwoordBerichtObjectKopie =
                transseksualiteitAntwoordBerichtOrigineel;
        final GeboorteVerzoekBericht geboorteVerzoekBericht = new GeboorteVerzoekBericht();

        transseksualiteitAntwoordBerichtKopie.setMessageId(transseksualiteitAntwoordBerichtOrigineel.getMessageId());
        transseksualiteitAntwoordBerichtKopie.setCorrelationId(transseksualiteitAntwoordBerichtOrigineel
                .getCorrelationId());

        assertEquals(true,
                transseksualiteitAntwoordBerichtObjectKopie.equals(transseksualiteitAntwoordBerichtOrigineel));
        assertEquals(false, transseksualiteitAntwoordBerichtOrigineel.equals(geboorteVerzoekBericht));
        assertEquals(true, transseksualiteitAntwoordBerichtKopie.equals(transseksualiteitAntwoordBerichtOrigineel));
        assertEquals(transseksualiteitAntwoordBerichtObjectKopie.hashCode(),
                transseksualiteitAntwoordBerichtOrigineel.hashCode());
        assertEquals(transseksualiteitAntwoordBerichtKopie.hashCode(),
                transseksualiteitAntwoordBerichtOrigineel.hashCode());
        assertEquals(transseksualiteitAntwoordBerichtKopie.toString(),
                transseksualiteitAntwoordBerichtOrigineel.toString());
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
                        TransseksualiteitAntwoordBerichtTest.class
                                .getResourceAsStream("transseksualiteitAntwoordBerichtSyntaxExceptionBericht.xml"));

        final TransseksualiteitAntwoordBericht transseksualiteitAntwoordBericht =
                new TransseksualiteitAntwoordBericht();

        try {
            transseksualiteitAntwoordBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final TransseksualiteitAntwoordBericht transseksualiteitAntwoordBericht =
                new TransseksualiteitAntwoordBericht();
        final String TOELICHTING = "Testtoelichting";
        transseksualiteitAntwoordBericht.setStatus(StatusType.FOUT);
        transseksualiteitAntwoordBericht.setToelichting(TOELICHTING);

        assertEquals(StatusType.FOUT, transseksualiteitAntwoordBericht.getStatus());
        assertEquals(TOELICHTING, transseksualiteitAntwoordBericht.getToelichting());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(TransseksualiteitAntwoordBerichtTest.class
                        .getResourceAsStream("transseksualiteitAntwoordBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }

}
