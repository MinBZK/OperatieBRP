/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.utils.XmlUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public class NationaliteitIntegrationTest extends AbstractBijhoudingServiceIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * LET OP: Dit min bericht bevat het minimale XML bericht + de verplichte velden om het bericht door alle validatie
     * regels in de business laag te krijgen.
     */
    @Test
    public void testVerkrijgenNederlandseNationaliteitMinBericht() throws Exception {
        String bestandsNaam = "nationaliteit_Verkrijgen_Nederlandse_Nationaliteit_MIN.xml";
        zetActieVoorBericht("registreerNationaliteit");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_natRegistreerNationaliteit/brp:verkrijgingNederlandseNationaliteit/brp:acties/brp:registratieNationaliteitNaam/brp:persoon/@brp:objectSleutel",
                1
        );
        Node antwoord = verzendBerichtNaarService(bestandsNaam);
        Assert.assertNotNull(antwoord);
        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
            XmlUtils.getNodeWaarde(NODE_VERWERKING, document));
    }

    @Test
    public void testVerkrijgingVreemdeNationaliteitMinBericht() throws Exception {
        String bestandsNaam = "nationaliteit_Verkrijgen_Vreemde_Nationaliteit_MIN.xml";
        zetActieVoorBericht("registreerNationaliteit");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_natRegistreerNationaliteit/brp:verkrijgingVreemdeNationaliteit/brp:acties/brp:registratieNationaliteitNaam/brp:persoon/@brp:objectSleutel",
                1
        );
        Node antwoord = verzendBerichtNaarService(bestandsNaam);
        Assert.assertNotNull(antwoord);
        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
                            XmlUtils.getNodeWaarde(NODE_VERWERKING, document));
    }

    @Test
    public void testRegistratieIndicatieNationaliteitRegistratieBehandeldAlsNederlanderMinBericht() throws Exception {
        String bestandsNaam = "nationaliteit_Registratie_Indicatie_Nationaliteit_Registratie_Behandeld_Als_Nederlander_MIN.xml";
        zetActieVoorBericht("registreerNationaliteit");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_natRegistreerNationaliteit/brp:wijzigingIndicatieNationaliteit/brp:acties/brp:registratieBehandeldAlsNederlander/brp:persoon/@brp:objectSleutel",
                1
        );
        Node antwoord = verzendBerichtNaarService(bestandsNaam);
        Assert.assertNotNull(antwoord);
        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
                            XmlUtils.getNodeWaarde(NODE_VERWERKING, document));
    }

    @Test
    public void testRegistratieIndicatieNationaliteitRegistratieStaatsloosMinBericht() throws Exception {
        String bestandsNaam = "nationaliteit_Registratie_Indicatie_Nationaliteit_Registratie_Staatsloos_MIN.xml";
        zetActieVoorBericht("registreerNationaliteit");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_natRegistreerNationaliteit/brp:wijzigingIndicatieNationaliteit/brp:acties/brp:registratieStaatloos/brp:persoon/@brp:objectSleutel",
                1
        );
        Node antwoord = verzendBerichtNaarService(bestandsNaam);
        Assert.assertNotNull(antwoord);
        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
                            XmlUtils.getNodeWaarde(NODE_VERWERKING, document));
    }

    @Test
    public void testRegistratieIndicatieNationaliteitRegistratieVastgesteldNietNederlander() throws Exception {
        String bestandsNaam = "nationaliteit_Registratie_Indicatie_Nationaliteit_Registratie_Vastgesteld_Niet_Nederlander_MIN.xml";
        zetActieVoorBericht("registreerNationaliteit");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_natRegistreerNationaliteit/brp:wijzigingIndicatieNationaliteit/brp:acties/brp:registratieVastgesteldNietNederlander/brp:persoon/@brp:objectSleutel",
                1
        );
        Node antwoord = verzendBerichtNaarService(bestandsNaam);
        Assert.assertNotNull(antwoord);
        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
                            XmlUtils.getNodeWaarde(NODE_VERWERKING, document));
    }

    @Override
    Logger getLogger() {
        return LOG;
    }

    @Override
    String getWsdlPortType() {
        return "bhgNationaliteit";
    }
}
