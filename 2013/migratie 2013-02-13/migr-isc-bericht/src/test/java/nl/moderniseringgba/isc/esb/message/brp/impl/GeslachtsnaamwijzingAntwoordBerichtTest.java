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
import nl.moderniseringgba.isc.esb.message.brp.generated.GeslachtsnaamwijzigingAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class GeslachtsnaamwijzingAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(GeslachtsnaamwijzingAntwoordBerichtTest.class
                        .getResourceAsStream("geslachtsnaamwijzigingAntwoordBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final GeslachtsnaamwijzigingAntwoordBericht geslachtsnaamwijzigingAntwoordBericht =
                (GeslachtsnaamwijzigingAntwoordBericht) bericht;
        assertEquals("GeslachtsnaamwijzigingAntwoord", geslachtsnaamwijzigingAntwoordBericht.getBerichtType());
        assertEquals(StatusType.OK, geslachtsnaamwijzigingAntwoordBericht.getStatus());
        assertEquals("A-nummer wel gevonden, bedankt", geslachtsnaamwijzigingAntwoordBericht.getToelichting());
        assertEquals(null, geslachtsnaamwijzigingAntwoordBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final GeslachtsnaamwijzigingAntwoordType geslachtsnaamwijzigingAntwoordType =
                new GeslachtsnaamwijzigingAntwoordType();
        geslachtsnaamwijzigingAntwoordType.setStatus(StatusType.FOUT);
        final GeslachtsnaamwijzigingAntwoordBericht geboorteBericht =
                new GeslachtsnaamwijzigingAntwoordBericht(geslachtsnaamwijzigingAntwoordType);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("GeslachtsnaamwijzigingAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testFoutBericht() throws Exception {
        final GeslachtsnaamwijzigingAntwoordBericht geboorteBericht = new GeslachtsnaamwijzigingAntwoordBericht();
        geboorteBericht.setStatus(StatusType.FOUT);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("GeslachtsnaamwijzigingAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testEquals() throws Exception {
        final GeslachtsnaamwijzigingAntwoordType geslachtsnaamwijzigingAntwoordType =
                new GeslachtsnaamwijzigingAntwoordType();
        geslachtsnaamwijzigingAntwoordType.setStatus(StatusType.OK);
        geslachtsnaamwijzigingAntwoordType.setToelichting("Testtoelichting");
        final GeslachtsnaamwijzigingAntwoordBericht geslachtsnaamwijzigingAntwoordBerichtOrigineel =
                new GeslachtsnaamwijzigingAntwoordBericht(geslachtsnaamwijzigingAntwoordType);
        final GeslachtsnaamwijzigingAntwoordBericht geslachtsnaamwijzigingAntwoordBerichtKopie =
                new GeslachtsnaamwijzigingAntwoordBericht(geslachtsnaamwijzigingAntwoordType);
        final GeslachtsnaamwijzigingAntwoordBericht geslachtsnaamwijzigingAntwoordBerichtObjectKopie =
                geslachtsnaamwijzigingAntwoordBerichtOrigineel;
        final GeboorteVerzoekBericht geboorteVerzoekBericht = new GeboorteVerzoekBericht();

        geslachtsnaamwijzigingAntwoordBerichtKopie.setMessageId(geslachtsnaamwijzigingAntwoordBerichtOrigineel
                .getMessageId());
        geslachtsnaamwijzigingAntwoordBerichtKopie.setCorrelationId(geslachtsnaamwijzigingAntwoordBerichtOrigineel
                .getCorrelationId());

        assertEquals(true,
                geslachtsnaamwijzigingAntwoordBerichtObjectKopie
                        .equals(geslachtsnaamwijzigingAntwoordBerichtOrigineel));
        assertEquals(false, geslachtsnaamwijzigingAntwoordBerichtOrigineel.equals(geboorteVerzoekBericht));
        assertEquals(true,
                geslachtsnaamwijzigingAntwoordBerichtKopie.equals(geslachtsnaamwijzigingAntwoordBerichtOrigineel));
        assertEquals(geslachtsnaamwijzigingAntwoordBerichtObjectKopie.hashCode(),
                geslachtsnaamwijzigingAntwoordBerichtOrigineel.hashCode());
        assertEquals(geslachtsnaamwijzigingAntwoordBerichtKopie.hashCode(),
                geslachtsnaamwijzigingAntwoordBerichtOrigineel.hashCode());
        assertEquals(geslachtsnaamwijzigingAntwoordBerichtKopie.toString(),
                geslachtsnaamwijzigingAntwoordBerichtOrigineel.toString());
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
                        .parse(GeslachtsnaamwijzingAntwoordBerichtTest.class
                                .getResourceAsStream("geslachtsnaamwijzigingAntwoordBerichtSyntaxExceptionBericht.xml"));

        final GeslachtsnaamwijzigingAntwoordBericht geslachtsnaamwijzigingAntwoordBericht =
                new GeslachtsnaamwijzigingAntwoordBericht();

        try {
            geslachtsnaamwijzigingAntwoordBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final GeslachtsnaamwijzigingAntwoordBericht geslachtsnaamwijzigingAntwoordBericht =
                new GeslachtsnaamwijzigingAntwoordBericht();
        final String TOELICHTING = "Testtoelichting";
        geslachtsnaamwijzigingAntwoordBericht.setStatus(StatusType.FOUT);
        geslachtsnaamwijzigingAntwoordBericht.setToelichting(TOELICHTING);

        assertEquals(StatusType.FOUT, geslachtsnaamwijzigingAntwoordBericht.getStatus());
        assertEquals(TOELICHTING, geslachtsnaamwijzigingAntwoordBericht.getToelichting());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(GeslachtsnaamwijzingAntwoordBerichtTest.class
                        .getResourceAsStream("geslachtsnaamwijzigingAntwoordBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }

}
