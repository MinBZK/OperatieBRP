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
import nl.moderniseringgba.isc.esb.message.brp.generated.MvGeboorteAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class MvGeboorteAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(MvGeboorteAntwoordBerichtTest.class
                        .getResourceAsStream("mvGeboorteAntwoordBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final MvGeboorteAntwoordBericht mvGeboorteAntwoordBericht = (MvGeboorteAntwoordBericht) bericht;
        assertEquals("MvGeboorteAntwoord", mvGeboorteAntwoordBericht.getBerichtType());
        assertEquals(StatusType.OK, mvGeboorteAntwoordBericht.getStatus());
        assertEquals("A-nummer wel gevonden, bedankt", mvGeboorteAntwoordBericht.getToelichting());
        assertEquals(null, mvGeboorteAntwoordBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final MvGeboorteAntwoordType mvGeboorteAntwoordType = new MvGeboorteAntwoordType();
        mvGeboorteAntwoordType.setStatus(StatusType.FOUT);
        final MvGeboorteAntwoordBericht mvGeboorteBericht = new MvGeboorteAntwoordBericht(mvGeboorteAntwoordType);

        LOG.info("Geformat: {}", mvGeboorteBericht.format());
        assertEquals("MvGeboorteAntwoord", mvGeboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, mvGeboorteBericht.getStatus());
    }

    @Test
    public void testFoutBericht() throws Exception {
        final MvGeboorteAntwoordBericht mvGeboorteBericht = new MvGeboorteAntwoordBericht();
        mvGeboorteBericht.setStatus(StatusType.FOUT);

        LOG.info("Geformat: {}", mvGeboorteBericht.format());
        assertEquals("MvGeboorteAntwoord", mvGeboorteBericht.getBerichtType());
        assertEquals(StatusType.FOUT, mvGeboorteBericht.getStatus());
    }

    @Test
    public void testEquals() throws Exception {
        final MvGeboorteAntwoordType mvGeboorteAntwoordType = new MvGeboorteAntwoordType();
        mvGeboorteAntwoordType.setStatus(StatusType.OK);
        mvGeboorteAntwoordType.setToelichting("Testtoelichting");
        final MvGeboorteAntwoordBericht mvGeboorteAntwoordBerichtOrigineel =
                new MvGeboorteAntwoordBericht(mvGeboorteAntwoordType);
        final MvGeboorteAntwoordBericht mvGeboorteAntwoordBerichtKopie =
                new MvGeboorteAntwoordBericht(mvGeboorteAntwoordType);
        final MvGeboorteAntwoordBericht mvGeboorteAntwoordBerichtObjectKopie = mvGeboorteAntwoordBerichtOrigineel;
        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = new MvGeboorteVerzoekBericht();

        mvGeboorteAntwoordBerichtKopie.setMessageId(mvGeboorteAntwoordBerichtOrigineel.getMessageId());
        mvGeboorteAntwoordBerichtKopie.setCorrelationId(mvGeboorteAntwoordBerichtOrigineel.getCorrelationId());

        assertEquals(true, mvGeboorteAntwoordBerichtObjectKopie.equals(mvGeboorteAntwoordBerichtOrigineel));
        assertEquals(false, mvGeboorteAntwoordBerichtOrigineel.equals(mvGeboorteVerzoekBericht));
        assertEquals(true, mvGeboorteAntwoordBerichtKopie.equals(mvGeboorteAntwoordBerichtOrigineel));
        assertEquals(mvGeboorteAntwoordBerichtObjectKopie.hashCode(), mvGeboorteAntwoordBerichtOrigineel.hashCode());
        assertEquals(mvGeboorteAntwoordBerichtKopie.hashCode(), mvGeboorteAntwoordBerichtOrigineel.hashCode());
        assertEquals(mvGeboorteAntwoordBerichtKopie.toString(), mvGeboorteAntwoordBerichtOrigineel.toString());
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
                        MvGeboorteAntwoordBerichtTest.class
                                .getResourceAsStream("mvGeboorteAntwoordBerichtSyntaxExceptionBericht.xml"));

        final MvGeboorteAntwoordBericht mvGeboorteAntwoordBericht = new MvGeboorteAntwoordBericht();

        try {
            mvGeboorteAntwoordBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final MvGeboorteAntwoordBericht mvGeboorteAntwoordBericht = new MvGeboorteAntwoordBericht();
        final String TOELICHTING = "Testtoelichting";
        final String ANUMMER = "123456";
        mvGeboorteAntwoordBericht.setStatus(StatusType.FOUT);
        mvGeboorteAntwoordBericht.setToelichting(TOELICHTING);
        mvGeboorteAntwoordBericht.setANummer(ANUMMER);

        assertEquals(StatusType.FOUT, mvGeboorteAntwoordBericht.getStatus());
        assertEquals(TOELICHTING, mvGeboorteAntwoordBericht.getToelichting());
        assertEquals(ANUMMER, mvGeboorteAntwoordBericht.getANummer());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(MvGeboorteAntwoordBerichtTest.class
                        .getResourceAsStream("mvGeboorteAntwoordBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }

}
