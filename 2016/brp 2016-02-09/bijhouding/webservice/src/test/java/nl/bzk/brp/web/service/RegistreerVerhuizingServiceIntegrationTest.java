/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.util.List;
import nl.bzk.brp.model.PersoonAdresDto;
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
public class RegistreerVerhuizingServiceIntegrationTest extends AbstractBijhoudingServiceIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final Integer VERHUIZENDE_PERSOON_ID = 1;

      /**
     * LET OP: Dit min bericht bevat het minimale XML bericht + de verplichte velden om het bericht door alle validatie
     * regels in de business laag te krijgen.
     */
    @Test
    public void testVerhuizingMinBericht() throws Exception {
        String bestandsNaam = "migratie_Verhuizing_Bijhouding_MIN.xml";
        zetActieVoorBericht("registreerVerhuizing");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_vbaRegistreerVerhuizing/brp:verhuizingBinnengemeentelijk/brp:acties/brp:registratieAdres/brp:persoon/@brp:objectSleutel",
                VERHUIZENDE_PERSOON_ID);
        Node antwoord = verzendBerichtNaarService(bestandsNaam);
        Assert.assertNotNull(antwoord);
        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
            XmlUtils.getNodeWaarde(NODE_VERWERKING, document));
    }

    @Test
    public void testVerhuizingBinnenGemeentelijk() throws Exception {
        String bestandsNaam = "migratie_Verhuizing_binnengemeentelijk_Bijhouding_MAX.xml";
        zetActieVoorBericht("registreerVerhuizing");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_vbaRegistreerVerhuizing/brp:verhuizingBinnengemeentelijk/brp:acties/brp:registratieAdres/brp:persoon/@brp:objectSleutel",
                VERHUIZENDE_PERSOON_ID);
        Node antwoord = verzendBerichtNaarService(bestandsNaam);
        Assert.assertNotNull(antwoord);
        String xml = XmlUtils.toXmlString(antwoord);

        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);

        Assert.assertTrue("bhg_vbaRegistreerVerhuizing_R root node bestaat niet",
            XmlUtils.isNodeAanwezig("//brp:bhg_vbaRegistreerVerhuizing_R", document));
        Assert.assertTrue("resultaat node bestaat niet", XmlUtils.isNodeAanwezig(NODE_RESULTAAT, document));
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
            XmlUtils.getNodeWaarde(NODE_VERWERKING, document));

        Document orgAanvraag = XmlUtils.bouwDocumentVanBestand(getInputStream(bestandsNaam));
        String bsn = XmlUtils.getNodeWaarde(
                "/brp:bhg_vbaRegistreerVerhuizing/brp:verhuizingBinnengemeentelijk/brp:acties/brp:registratieAdres/brp:persoon/@brp:objectSleutel",
                orgAanvraag);
        List<PersoonAdresDto> adressen = PersoonAdresDto.persoonAdresLevelA(jdbcTemplate, bsn);
        Assert.assertTrue("Kan geen persoon vinden op levelA met bsn " + bsn,
            ((adressen != null) && !adressen.isEmpty()));
        Assert.assertEquals("blokhuttendoosje", adressen.get(0).getAdreseerbaarObject());
        Assert.assertEquals("1066DG", adressen.get(0).getPostcode());

        // Sortering op id (Descending) zorgt er voor dat het meest recente (actuele) record als eerst in de lijst komt
        adressen = PersoonAdresDto.persoonAdresLevelC(jdbcTemplate, bsn);
        Assert.assertTrue("Kan geen persoon vinden op levelC met bsn " + bsn, adressen.size() > 0);
        Assert.assertEquals("blokhuttendoosje", adressen.get(0).getAdreseerbaarObject());
        Assert.assertEquals("1066DG", adressen.get(0).getPostcode());

        // verhuisde persoon
        Assert.assertEquals(bsn, XmlUtils.getNodeWaarde(
                "//brp:bijgehoudenPersonen/brp:persoon[1]/brp:identificatienummers/brp:burgerservicenummer", document));
    }

    @Test
    public void testVerhuizingIntergemeentelijk() throws Exception {
        String bestandsNaam = "migratie_Verhuizing_intergemeentelijk_Bijhouding_MAX.xml";
        zetActieVoorBericht("registreerVerhuizing");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_vbaRegistreerVerhuizing/brp:verhuizingIntergemeentelijk/brp:acties/brp:registratieAdres/brp:persoon/@brp:objectSleutel",
                VERHUIZENDE_PERSOON_ID);
        Node antwoord = verzendBerichtNaarService(bestandsNaam);
        Assert.assertNotNull(antwoord);
        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);

        Assert.assertTrue("bhg_vbaRegistreerVerhuizing_R root node bestaat niet",
            XmlUtils.isNodeAanwezig("//brp:bhg_vbaRegistreerVerhuizing_R", document));
        Assert.assertTrue("resultaat node bestaat niet", XmlUtils.isNodeAanwezig(NODE_RESULTAAT, document));
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
            XmlUtils.getNodeWaarde(NODE_VERWERKING, document));
    }

    @Test
    public void testVerhuizingNaarBuitenland() throws Exception {
        String bestandsNaam = "migratie_Verhuizing_naarBuitenland_Bijhouding_MAX.xml";
        zetActieVoorBericht("registreerVerhuizing");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_vbaRegistreerVerhuizing/brp:verhuizingNaarBuitenland/brp:acties/brp:registratieMigratie/brp:persoon/@brp:objectSleutel",
                VERHUIZENDE_PERSOON_ID);
        Node antwoord = verzendBerichtNaarService(bestandsNaam);
        Assert.assertNotNull(antwoord);
        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);

        Assert.assertTrue("bhg_vbaRegistreerVerhuizing_R root node bestaat niet",
                XmlUtils.isNodeAanwezig("//brp:bhg_vbaRegistreerVerhuizing_R", document));
        Assert.assertTrue("resultaat node bestaat niet", XmlUtils.isNodeAanwezig(NODE_RESULTAAT, document));
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
                XmlUtils.getNodeWaarde(NODE_VERWERKING, document));
    }


    @Test
    public void testVerhuizingVanafOnbekendAdres() throws Exception {
        String bestandsNaam = "migratie_Verhuizing_Bijhouding_vanaf_onbekend_adres.xml";
        zetActieVoorBericht("registreerVerhuizing");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_vbaRegistreerVerhuizing/brp:verhuizingIntergemeentelijk/brp:acties/brp:registratieAdres/brp:persoon/@brp:objectSleutel",
                VERHUIZENDE_PERSOON_ID);
        Node antwoord = verzendBerichtNaarService(bestandsNaam);
        Assert.assertNotNull(antwoord);
        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);

        Assert.assertTrue("bhg_vbaRegistreerVerhuizing_R root node bestaat niet",
            XmlUtils.isNodeAanwezig("//brp:bhg_vbaRegistreerVerhuizing_R", document));
        Assert.assertTrue("resultaat node bestaat niet", XmlUtils.isNodeAanwezig(NODE_RESULTAAT, document));
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
            XmlUtils.getNodeWaarde(NODE_VERWERKING, document));

        Document orgAanvraag = XmlUtils.bouwDocumentVanBestand(getInputStream(bestandsNaam));
        String bsn = XmlUtils.getNodeWaarde(
                "/brp:bhg_vbaRegistreerVerhuizing/brp:verhuizingIntergemeentelijk/brp:acties/brp:registratieAdres/brp:persoon/@brp:objectSleutel",
                orgAanvraag);
        List<PersoonAdresDto> adressen = PersoonAdresDto.persoonAdresLevelA(jdbcTemplate, bsn);
        Assert.assertTrue("Kan geen persoon vinden op levelA met bsn " + bsn,
            ((adressen != null) && !adressen.isEmpty()));
        Assert.assertEquals("blokhuttendoosje", adressen.get(0).getAdreseerbaarObject());
        // totdat Postcodevalidatie aan wordt gezet (dan foutmelding)
        Assert.assertEquals("1234AB", adressen.get(0).getPostcode());
    }

    @Override
    Logger getLogger() {
        return LOG;
    }

    @Override
    String getWsdlPortType() {
        return "bhgVerblijfAdres";
    }
}
