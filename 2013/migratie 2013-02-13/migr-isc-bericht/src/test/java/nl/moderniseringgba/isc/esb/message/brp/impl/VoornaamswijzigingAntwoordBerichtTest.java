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
import nl.moderniseringgba.isc.esb.message.brp.generated.VoornaamswijzigingAntwoordType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class VoornaamswijzigingAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(VoornaamswijzigingAntwoordBerichtTest.class
                        .getResourceAsStream("voornaamswijzigingAntwoordBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final VoornaamswijzigingAntwoordBericht voornaamswijzigingAntwoordBericht =
                (VoornaamswijzigingAntwoordBericht) bericht;
        assertEquals("VoornaamswijzigingAntwoord", voornaamswijzigingAntwoordBericht.getBerichtType());
        assertEquals(StatusType.OK, voornaamswijzigingAntwoordBericht.getStatus());
        assertEquals("A-nummer wel gevonden, bedankt", voornaamswijzigingAntwoordBericht.getToelichting());
        assertEquals(null, voornaamswijzigingAntwoordBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final VoornaamswijzigingAntwoordType voornaamswijzigingAntwoordType = new VoornaamswijzigingAntwoordType();
        voornaamswijzigingAntwoordType.setStatus(StatusType.FOUT);
        final VoornaamswijzigingAntwoordBericht geboorteBericht =
                new VoornaamswijzigingAntwoordBericht(voornaamswijzigingAntwoordType);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("VoornaamswijzigingAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testFoutBericht() throws Exception {
        final VoornaamswijzigingAntwoordBericht geboorteBericht = new VoornaamswijzigingAntwoordBericht();
        geboorteBericht.setStatus(StatusType.FOUT);

        LOG.info("Geformat: {}", geboorteBericht.format());
        assertEquals("VoornaamswijzigingAntwoord", geboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, geboorteBericht.getStatus());
    }

    @Test
    public void testEquals() throws Exception {
        final VoornaamswijzigingAntwoordType voornaamswijzigingAntwoordType = new VoornaamswijzigingAntwoordType();
        voornaamswijzigingAntwoordType.setStatus(StatusType.OK);
        voornaamswijzigingAntwoordType.setToelichting("Testtoelichting");
        final VoornaamswijzigingAntwoordBericht voornaamswijzigingAntwoordBerichtOrigineel =
                new VoornaamswijzigingAntwoordBericht(voornaamswijzigingAntwoordType);
        final VoornaamswijzigingAntwoordBericht voornaamswijzigingAntwoordBerichtKopie =
                new VoornaamswijzigingAntwoordBericht(voornaamswijzigingAntwoordType);
        final VoornaamswijzigingAntwoordBericht voornaamswijzigingAntwoordBerichtObjectKopie =
                voornaamswijzigingAntwoordBerichtOrigineel;
        final GeboorteVerzoekBericht geboorteVerzoekBericht = new GeboorteVerzoekBericht();

        voornaamswijzigingAntwoordBerichtKopie
                .setMessageId(voornaamswijzigingAntwoordBerichtOrigineel.getMessageId());
        voornaamswijzigingAntwoordBerichtKopie.setCorrelationId(voornaamswijzigingAntwoordBerichtOrigineel
                .getCorrelationId());

        assertEquals(true,
                voornaamswijzigingAntwoordBerichtObjectKopie.equals(voornaamswijzigingAntwoordBerichtOrigineel));
        assertEquals(false, voornaamswijzigingAntwoordBerichtOrigineel.equals(geboorteVerzoekBericht));
        assertEquals(true, voornaamswijzigingAntwoordBerichtKopie.equals(voornaamswijzigingAntwoordBerichtOrigineel));
        assertEquals(voornaamswijzigingAntwoordBerichtObjectKopie.hashCode(),
                voornaamswijzigingAntwoordBerichtOrigineel.hashCode());
        assertEquals(voornaamswijzigingAntwoordBerichtKopie.hashCode(),
                voornaamswijzigingAntwoordBerichtOrigineel.hashCode());
        assertEquals(voornaamswijzigingAntwoordBerichtKopie.toString(),
                voornaamswijzigingAntwoordBerichtOrigineel.toString());
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
                        VoornaamswijzigingAntwoordBerichtTest.class
                                .getResourceAsStream("voornaamswijzigingAntwoordBerichtSyntaxExceptionBericht.xml"));

        final VoornaamswijzigingAntwoordBericht voornaamswijzigingAntwoordBericht =
                new VoornaamswijzigingAntwoordBericht();

        try {
            voornaamswijzigingAntwoordBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final VoornaamswijzigingAntwoordBericht voornaamswijzigingAntwoordBericht =
                new VoornaamswijzigingAntwoordBericht();
        final String TOELICHTING = "Testtoelichting";
        voornaamswijzigingAntwoordBericht.setStatus(StatusType.FOUT);
        voornaamswijzigingAntwoordBericht.setToelichting(TOELICHTING);

        assertEquals(StatusType.FOUT, voornaamswijzigingAntwoordBericht.getStatus());
        assertEquals(TOELICHTING, voornaamswijzigingAntwoordBericht.getToelichting());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(VoornaamswijzigingAntwoordBerichtTest.class
                        .getResourceAsStream("voornaamswijzigingAntwoordBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }

}
