/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.PersoonDto;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.utils.XmlUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/** Testen tegen live webservices, gedeployed in een embedded Jetty Server. */

@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public class HuwelijkEnPartnerschapIntegrationTest extends AbstractBijhoudingServiceIntegrationTest {

    private static final Logger LOG = LoggerFactory
        .getLogger();

    @SuppressWarnings("deprecation")
    @Test
    public void testRegistreerHuwelijkEnPartnerschap() throws Exception {
        zetActieVoorBericht("registreerHuwelijkPartnerschap");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap/brp:voltrekkingHuwelijkInNederland/brp:acties/brp:registratieAanvangHuwelijkGeregistreerdPartnerschap/brp:huwelijk/brp:betrokkenheden/brp:partner[1]/brp:persoon/@brp:objectSleutel",
                1002);
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap/brp:voltrekkingHuwelijkInNederland/brp:acties/brp:registratieAanvangHuwelijkGeregistreerdPartnerschap/brp:huwelijk/brp:betrokkenheden/brp:partner[2]/brp:persoon/@brp:objectSleutel",
                1003);
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap/brp:voltrekkingHuwelijkInNederland/brp:acties/brp:registratieNaamgebruik[1]/brp:persoon/@brp:objectSleutel",
                1002
        );
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap/brp:voltrekkingHuwelijkInNederland/brp:acties/brp:registratieNaamgebruik[2]/brp:persoon/@brp:objectSleutel",
                1003
        );

        Node antwoord = verzendBerichtNaarService("huwelijkPartnerschap_RegistratieHuwelijk_Bijhouding_v1.0.xml");
        assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);

        assertTrue("Huwelijk root node bestaat niet", XmlUtils
            .isNodeAanwezig("//brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R",
                document));
        assertTrue("resultaat bestaat niet",
            XmlUtils.isNodeAanwezig("//brp:resultaat", document));
        assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
            XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerking",
                document));
        // test de rest

        // now check in the database what has been written.
        Document orgAanvraag = XmlUtils
            .bouwDocumentVanBestand(getInputStream("huwelijkPartnerschap_RegistratieHuwelijk_Bijhouding_v1.0.xml"));
        String bsnMan = XmlUtils.getAttribuutWaarde(
                "//brp:partner[1]/brp:persoon", "brp:objectSleutel",
                orgAanvraag);
        String bsnVrouw = XmlUtils.getAttribuutWaarde(
                "//brp:partner[2]/brp:persoon", "brp:objectSleutel",
                orgAanvraag);
        List<String> bsnNrs = Arrays.asList(bsnMan, bsnVrouw);

        PersoonDto persoonMan = PersoonDto.persoonMetBsnBestaatInALaag(
            jdbcTemplate, bsnMan).get(0);
        assertNotNull("Kan geen persoon vinden", persoonMan);
        assertEquals(bsnMan, persoonMan.getBsn());

        PersoonDto persoonVrouw = PersoonDto.persoonMetBsnBestaatInALaag(
            jdbcTemplate, bsnVrouw).get(0);
        assertNotNull("Kan geen persoon vinden", persoonVrouw);
        assertEquals(bsnVrouw, persoonVrouw.getBsn());

        // Test Relatie en betrokkenheid
        List<Map<String, Object>> betrokkenheden1 = jdbcTemplate
            .queryForList("SELECT * FROM kern.betr WHERE pers = ?",
                persoonMan.getId());
        assertEquals(1, betrokkenheden1.size());
        Integer relatieId1 = (Integer) betrokkenheden1.get(0).get("relatie");
        assertNotNull(relatieId1);
        assertEquals(SoortBetrokkenheid.PARTNER.ordinal(),
            betrokkenheden1.get(0).get("rol"));

        List<Map<String, Object>> betrokkenheden2 = jdbcTemplate
            .queryForList("SELECT * FROM kern.betr WHERE pers = ?",
                persoonVrouw.getId());
        assertEquals(1, betrokkenheden2.size());
        Integer relatieId2 = (Integer) betrokkenheden2.get(0).get("relatie");
        assertNotNull(relatieId2);
        assertEquals(SoortBetrokkenheid.PARTNER.ordinal(),
            betrokkenheden2.get(0).get("rol"));

        assertTrue(bsnNrs.contains(XmlUtils
            .getNodeWaarde(
                "//brp:persoon[1]/brp:identificatienummers/brp:burgerservicenummer",
                document)));
        assertTrue(bsnNrs.contains(XmlUtils
            .getNodeWaarde(
                "//brp:persoon[2]/brp:identificatienummers/brp:burgerservicenummer",
                document)));

        assertEquals("betrokkenheid moet dezelfde relatie id hebben",
            relatieId1, relatieId2);

        // Test relatie
        List<Map<String, Object>> relatie = jdbcTemplate.queryForList(
            "SELECT * FROM kern.relatie WHERE id = ?", relatieId1);
        assertEquals("relatie moet landaanv Nederland hebben", 2,
            relatie.get(0).get("landgebiedaanv"));

        assertTrue(bsnNrs.contains(XmlUtils
            .getNodeWaarde(
                "//brp:persoon[1]/brp:identificatienummers/brp:burgerservicenummer",
                document)));
        assertTrue(bsnNrs.contains(XmlUtils
            .getNodeWaarde(
                "//brp:persoon[2]/brp:identificatienummers/brp:burgerservicenummer",
                document)));

        String bsnMan2 = XmlUtils.getAttribuutWaarde(
                "//brp:registratieNaamgebruik/brp:persoon",
                "brp:objectSleutel", orgAanvraag);
        assertEquals(bsnMan, bsnMan2);

        // test op naamgebruik wijzigingen A Laag van Man
        List<Map<String, Object>> persoonenNaamWijziging = jdbcTemplate
            .queryForList("SELECT * FROM kern.pers WHERE id = ?",
                persoonMan.getId());
        assertEquals(1, persoonenNaamWijziging.size());
        assertEquals("Geest",
            persoonenNaamWijziging.get(0).get("geslnaamstamnaamgebruik"));
        assertEquals("van de",
            persoonenNaamWijziging.get(0).get("voorvoegselnaamgebruik"));
        assertEquals(" ",
            persoonenNaamWijziging.get(0).get("scheidingstekennaamgebruik"));
        assertEquals("transfixo pectore flammas", persoonenNaamWijziging
            .get(0).get("voornamennaamgebruik"));
        assertEquals(
            false,
            persoonenNaamWijziging.get(0).get(
                "indnaamgebruikafgeleid"));

        // test op naamgebruik wijzigingen C/D Laag van Man
        List<Map<String, Object>> persoonenNaamWijzigingHis = jdbcTemplate
            .queryForList(
                "SELECT * FROM kern.his_persnaamgebruik WHERE pers = ?",
                persoonMan.getId());
        assertEquals(1, persoonenNaamWijzigingHis.size());
        // "gebrgeslnaamegp"
        assertEquals("Geest",
            persoonenNaamWijzigingHis.get(0).get("geslnaamstamnaamgebruik"));
        assertEquals("van de",
            persoonenNaamWijzigingHis.get(0).get("voorvoegselnaamgebruik"));
        assertEquals(" ",
            persoonenNaamWijzigingHis.get(0).get("scheidingstekennaamgebruik"));
        assertEquals("transfixo pectore flammas",
            persoonenNaamWijzigingHis.get(0).get("voornamennaamgebruik"));
        assertEquals(
            false,
            persoonenNaamWijzigingHis.get(0).get(
                "indnaamgebruikafgeleid"));
        assertNotNull(persoonenNaamWijzigingHis.get(0).get("actieinh"));
        // Er is geen dataanvgel meer
        // Assert.assertEquals(Integer.valueOf(20120613),
        // persoonenNaamWijzigingHis.get(0).get("dataanvgel"));

        assertEquals(null,
            persoonenNaamWijzigingHis.get(0).get("dateindegel"));
        assertEquals(null,
            persoonenNaamWijzigingHis.get(0).get("actieverval"));
        assertEquals(null,
            persoonenNaamWijzigingHis.get(0).get("tsverval"));

        // TODO naamgebruik met indicaties
        // test op naamgebruik wijzigingen A Laag van Vrouw
        // TODO wee actievere (een van deze dingen, zodra we meerdere
        // naamgebruik kunnen opvoeren.

    }

    @Test
    public void testRegistreerHuwelijkEnPartnerschapMetAanschrijvingVerkeerdePersoon() throws Exception {
        zetActieVoorBericht("registreerHuwelijkPartnerschap");
        Node antwoord = verzendBerichtNaarService(
            "huwelijkPartnerschap_RegistratieHuwelijk_Bijhouding_Met_Niet_Bestaand_BSN_v1.0.xml");
        assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);

        printResponse(document);
        assertTrue("Huwelijk root node bestaat niet", XmlUtils
            .isNodeAanwezig("//brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R",
                document));
        assertTrue("resultaat bestaat niet",
            XmlUtils.isNodeAanwezig("//brp:resultaat", document));
        assertEquals(Verwerkingsresultaat.FOUTIEF.getNaam(),
            XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerking",
                document));

        // test de rest (inconsistentie check)
//        Assert.assertEquals("BRAL1001", XmlUtils.getNodeWaarde(
//            "//brp:meldingen/brp:melding/brp:regelCode", document));
        assertEquals("Fout", XmlUtils.getNodeWaarde(
            "//brp:meldingen/brp:melding/brp:soortNaam", document));
//        Assert.assertEquals("id.aanschr1.pers", XmlUtils.getAttribuutWaarde(
//            "//brp:meldingen/brp:melding", "brp:referentieID", document));
    }

    @Override
    Logger getLogger() {
        return LOG;
    }

    @Override
    String getWsdlPortType() {
        return "bhgHuwelijkGeregistreerdPartnerschap";
    }
}
