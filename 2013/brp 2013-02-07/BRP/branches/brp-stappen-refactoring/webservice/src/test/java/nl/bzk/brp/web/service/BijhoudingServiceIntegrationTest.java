/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import nl.bzk.brp.model.GeslachtsnaamcomponentDto;
import nl.bzk.brp.model.PersoonAdresDto;
import nl.bzk.brp.model.PersoonDto;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.utils.XmlUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


/** Testen tegen live webservices, gedeployed in een embedded Jetty Server. */

@RunWith(SpringJUnit4ClassRunner.class)
public class BijhoudingServiceIntegrationTest extends AbstractIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(BijhoudingServiceIntegrationTest.class);

    /**
     * LET OP: Dit min bericht bevat het minimale XML bericht + de verplichte velden om het bericht door alle validatie
     * regels in de business laag te krijgen.
     */
    @Test @Ignore("nog niet geïmplementeerd, see SIERRA-494")
    public void testVerhuizingMinBericht() throws Exception {
        String bestandsNaam = "migratie_Verhuizing_Bijhouding_MIN.xml";
        zetActieVoorBericht("verhuizing");
        Node antwoord = verzendBerichtNaarService(bestandsNaam);
        Assert.assertNotNull(antwoord);
        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printAlsResponseHeeftFoutCode(document);
        Assert.assertEquals(Verwerkingsresultaat.VERWERKING_GESLAAGD.getCode(),
            XmlUtils.getNodeWaarde("//resultaat/verwerkingCode", document));
    }

    @Test @Ignore("nog niet geïmplementeerd, see SIERRA-494")
    public void testVerhuizing() throws Exception {
        String bestandsNaam = "migratie_Verhuizing_Bijhouding_v0001_MAX.xml";
        zetActieVoorBericht("verhuizing");
        Node antwoord = verzendBerichtNaarService(bestandsNaam);
        Assert.assertNotNull(antwoord);
        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);

        Assert.assertTrue("migratie_Verhuizing_BijhoudingResponse root node bestaat niet",
            XmlUtils.isNodeAanwezig("//migratie_Verhuizing_BijhoudingResponse", document));
        Assert.assertTrue("resultaat node bestaat niet", XmlUtils.isNodeAanwezig("//resultaat", document));
        Assert.assertEquals(Verwerkingsresultaat.VERWERKING_GESLAAGD.getCode(),
            XmlUtils.getNodeWaarde("//resultaat/verwerkingCode", document));

        Document orgAanvraag = XmlUtils.bouwDocumentVanBestand(getInputStream(bestandsNaam));
        String bsn = XmlUtils.getNodeWaarde("//persoon/identificatienummers/burgerservicenummer", orgAanvraag);
        String anr = XmlUtils.getNodeWaarde("//persoon/identificatienummers/administratienummer", orgAanvraag);
        List<PersoonAdresDto> adressen = PersoonAdresDto.persoonAdresLevelA(simpleJdbcTemplate, bsn);
        Assert.assertTrue("Kan geen persoon vinden op levelA met bsn " + bsn,
            ((adressen != null) && !adressen.isEmpty()));
        Assert.assertEquals("Blokhut", adressen.get(0).getAdreseerbaarObject());
        Assert.assertEquals("1066DG", adressen.get(0).getPostcode());

        // deze persoon had nog geen historie in het adressen bestand, Dus bij het verhuizen,
        // is er slechts 1 hist. record.
        adressen = PersoonAdresDto.persoonAdresLevelC(simpleJdbcTemplate, bsn);
        Assert.assertTrue("Kan geen persoon vinden op levelC met bsn " + bsn, adressen.size() > 0);
        Assert.assertEquals("Blokhut", adressen.get(0).getAdreseerbaarObject());
        Assert.assertEquals("1066DG", adressen.get(0).getPostcode());

        // verhuisde persoon
        Assert.assertEquals(bsn, XmlUtils.getNodeWaarde(
            "//bijgehoudenPersonen/persoon[1]/identificatienummers/burgerservicenummer", document));
    }

    @Test @Ignore("nog niet geïmplementeerd, see SIERRA-494")
    public void testVerhuizing2() throws Exception {
        String bestandsNaam = "migratie_Verhuizing_Bijhouding_v0002_MAX.xml";
        zetActieVoorBericht("verhuizing");
        Node antwoord = verzendBerichtNaarService(bestandsNaam);
        Assert.assertNotNull(antwoord);
        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printAlsResponseHeeftFoutCode(document);

        Assert.assertTrue("migratie_Verhuizing_BijhoudingResponse root node bestaat niet",
            XmlUtils.isNodeAanwezig("//migratie_Verhuizing_BijhoudingResponse", document));
        Assert.assertTrue("resultaat node bestaat niet", XmlUtils.isNodeAanwezig("//resultaat", document));
        Assert.assertEquals(Verwerkingsresultaat.VERWERKING_GESLAAGD.getCode(),
            XmlUtils.getNodeWaarde("//resultaat/verwerkingCode", document));
    }

    @Test @Ignore("nog niet geïmplementeerd, see SIERRA-494")
    public void testVerhuizingVanafOnbekendAdres() throws Exception {
        String bestandsNaam = "migratie_Verhuizing_Bijhouding_vanaf_onbekend_adres.xml";
        zetActieVoorBericht("verhuizing");
        Node antwoord = verzendBerichtNaarService(bestandsNaam);
        Assert.assertNotNull(antwoord);
        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printAlsResponseHeeftFoutCode(document);

        Assert.assertTrue("migratie_Verhuizing_BijhoudingResponse root node bestaat niet",
            XmlUtils.isNodeAanwezig("//migratie_Verhuizing_BijhoudingResponse", document));
        Assert.assertTrue("resultaat node bestaat niet", XmlUtils.isNodeAanwezig("//resultaat", document));
        Assert.assertEquals(Verwerkingsresultaat.VERWERKING_GESLAAGD.getCode(),
            XmlUtils.getNodeWaarde("//resultaat/verwerkingCode", document));

        Document orgAanvraag = XmlUtils.bouwDocumentVanBestand(getInputStream(bestandsNaam));
        String bsn = XmlUtils.getNodeWaarde("//persoon/identificatienummers/burgerservicenummer", orgAanvraag);
        String anr = XmlUtils.getNodeWaarde("//persoon/identificatienummers/administratienummer", orgAanvraag);
        List<PersoonAdresDto> adressen = PersoonAdresDto.persoonAdresLevelA(simpleJdbcTemplate, bsn);
        Assert.assertTrue("Kan geen persoon vinden op levelA met bsn " + bsn,
            ((adressen != null) && !adressen.isEmpty()));
        Assert.assertEquals("Blokhut15", adressen.get(0).getAdreseerbaarObject());
        // totdat Postcodevalidatie aan wordt gezet (dan foutmelding)
        Assert.assertEquals("1066DG", adressen.get(0).getPostcode());
    }

    @Test @Ignore("nog niet geïmplementeerd, see SIERRA-454")
    public void testInschrijvingGeboorte() throws Exception {
        zetActieVoorBericht("inschrijvingGeboorte");
        Node antwoord = verzendBerichtNaarService("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001.xml");
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);

        Assert.assertTrue("InschrijvingAangifteGeboorte root node bestaat niet",
            XmlUtils.isNodeAanwezig("//afstamming_InschrijvingAangifteGeboorte_BijhoudingResponse", document));
        Assert.assertTrue("resultaat bestaat niet", XmlUtils.isNodeAanwezig("//resultaat", document));
        Assert.assertEquals(Verwerkingsresultaat.VERWERKING_GESLAAGD.getCode(),
            XmlUtils.getNodeWaarde("//resultaat/verwerkingCode", document));
        // test de rest

        // now check in the database what has been written.
        Document orgAanvraag =
            XmlUtils
                .bouwDocumentVanBestand(getInputStream("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001.xml"));
        String bsn = XmlUtils.getNodeWaarde("//kind/persoon/identificatienummers/burgerservicenummer", orgAanvraag);
        String anr = XmlUtils.getNodeWaarde("//kind/persoon/identificatienummers/administratienummer", orgAanvraag);
        List<PersoonDto> personen = PersoonDto.persoonMetBsnNumerBestaatLevelA(simpleJdbcTemplate, bsn);
        Assert.assertTrue("Kan geen persoon vinden", personen.size() > 0);
        Assert.assertEquals(bsn, personen.get(0).getBsn());
        Assert.assertEquals(anr, personen.get(0).getAnr());
        Integer kindId = personen.get(0).getId();
        Integer ouderId = 1001;
        List<GeslachtsnaamcomponentDto> geslachtscomponenten =
            GeslachtsnaamcomponentDto.getGeslachtsnaamcomponentA(simpleJdbcTemplate, kindId);
        Assert.assertTrue("Kan geen naamscomponenten vinden", !geslachtscomponenten.isEmpty());
        GeslachtsnaamcomponentDto gc = geslachtscomponenten.get(0);

        Assert.assertEquals("H", gc.getAdelijkeTitelCode());
        Assert.assertEquals("K", gc.getPredikaatCode());
        Assert.assertEquals("/", gc.getScheidingsteken());
        Assert.assertEquals("van", gc.getVoorVoegsel());
        Assert.assertEquals("Kant", gc.getNaam());

        // Test Relatie en betrokkenheid
        List<Map<String, Object>> betrokkenheden =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.betr WHERE pers = ?", kindId);
        Assert.assertFalse(betrokkenheden.isEmpty());
        Assert.assertEquals(1, betrokkenheden.size());
        Integer relatieId = (Integer) betrokkenheden.get(0).get("relatie");
        Assert.assertNotNull(relatieId);
        Assert.assertEquals(SoortBetrokkenheid.KIND.ordinal(), betrokkenheden.get(0).get("rol"));
        Assert.assertNull(betrokkenheden.get(0).get("indOuder"));

        List<Map<String, Object>> betrokkenhedenRelatie =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.betr WHERE relatie = ?", relatieId);
        Assert.assertFalse(betrokkenhedenRelatie.isEmpty());
        Assert.assertEquals(2, betrokkenhedenRelatie.size());
        for (Map<String, Object> betrokkenheid : betrokkenhedenRelatie) {
            Integer betrokkeneId = (Integer) betrokkenheid.get("pers");
            Assert.assertTrue(betrokkeneId.equals(ouderId) || betrokkeneId.equals(kindId));
            if (betrokkeneId.equals(ouderId)) {
                Assert.assertEquals(SoortBetrokkenheid.OUDER.ordinal(), betrokkenheid.get("rol"));
                Assert.assertEquals(Boolean.TRUE, betrokkenheid.get("indOuder"));
                // transient
                Assert.assertEquals(null, betrokkenheid.get("dataanv"));
            } else {
                Assert.assertEquals(SoortBetrokkenheid.KIND.ordinal(), betrokkenheid.get("rol"));
                Assert.assertEquals(null, betrokkenheid.get("indOuder"));
                // transient
                Assert.assertEquals(null, betrokkenheid.get("dataanv"));
            }
        }
        // ouder
        Assert.assertEquals("182101411", XmlUtils.getNodeWaarde(
            "//bijgehoudenPersonen/persoon[1]/identificatienummers/burgerservicenummer", document));
        // kind
        Assert.assertEquals(bsn, XmlUtils.getNodeWaarde(
            "//bijgehoudenPersonen/persoon[2]/identificatienummers/burgerservicenummer", document));

        List<Map<String, Object>> adressen =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.persadres WHERE pers = ?", kindId);
        Assert.assertFalse(adressen.isEmpty());
        Assert.assertEquals(1, adressen.size());
        // TODO Assert.assertEquals(x, adressen.get(0).get("..."));

        List<Map<String, Object>> voornamen =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.persadres WHERE pers = ?", kindId);
        Assert.assertFalse(adressen.isEmpty());
        Assert.assertEquals(1, adressen.size());
        // TODO Assert.assertEquals(x, adressen.get(0).get("..."));
    }


    @Test @Ignore("nog niet geïmplementeerd, see SIERRA-454")
    public void testInschrijvenGeboorteMetOverrulebareFout() throws Exception {
        zetActieVoorBericht("inschrijvingGeboorte");
        Node antwoord = verzendBerichtNaarService("afstamming_InschrijvingAangifteGeboorte_Fout001.xml");
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printAlsResponseHeeftFoutCode(document);
        // Wij verwachten dat nu een 'O' terugkomgt met de code (BRBY0106,
        // 'Geslachtsnaam van kind komt niet overeen met die van een van de ouders of die
        // van andere gemeenschappelijke kinderen.')
        // omdat het kind geslachtnaam Kantjes' en de (enige) ouder 'Kant' heet.
        // En dit was de enige fout.
        Assert.assertEquals(Verwerkingsresultaat.VERWERKING_FOUTIEF.getCode(),
            XmlUtils.getNodeWaarde("//resultaat/verwerkingCode", document));
        Assert.assertEquals("BRBY0165", XmlUtils.getNodeWaarde("//meldingen/melding[1]/regelCode", document));
        Assert.assertEquals("F", XmlUtils.getNodeWaarde("//meldingen/melding[1]/soortCode", document));
        Assert.assertEquals("BRBY0106", XmlUtils.getNodeWaarde("//meldingen/melding[2]/regelCode", document));
        Assert.assertEquals("O", XmlUtils.getNodeWaarde("//meldingen/melding[2]/soortCode", document));
    }

    @Test @Ignore("nog niet geïmplementeerd, see SIERRA-454")
    public void testInschrijvenGeboorteMetOverrulebareFoutOverruled() throws Exception {
        zetActieVoorBericht("inschrijvingGeboorte");
        Node antwoord = verzendBerichtNaarService("afstamming_InschrijvingAangifteGeboorte_Fout001_Overruled.xml");
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printAlsResponseHeeftFoutCode(document);
        // Wij verwachten dat nu goed is verwerkt (G),
        // er is nog steeds een lijst van meldingen van type W met de code (BRBY0106,
        // 'Geslachtsnaam van kind komt niet overeen met die van een van de ouders of die
        // van andere gemeenschappelijke kinderen.')
        // En dit was de enige fout.
        Assert.assertEquals(Verwerkingsresultaat.VERWERKING_GESLAAGD.getCode(),
            XmlUtils.getNodeWaarde("//resultaat/verwerkingCode", document));
        Assert.assertEquals("BRBY0106", XmlUtils.getNodeWaarde("//meldingen/melding/regelCode", document));
        Assert.assertEquals("W", XmlUtils.getNodeWaarde("//meldingen/melding/soortCode", document));
        // Daarnaast is een lijst met de overruled die zijn gebruikt.
        Assert.assertEquals("BRBY0106", XmlUtils.getNodeWaarde("//overruleMeldingen/overrule/regelCode", document));
        // Assert.assertEquals("O", XmlUtils.getNodeWaarde("//overruleMeldingen/overrule/soortCode", document));
    }

    @Test @Ignore("nog niet geïmplementeerd, see SIERRA-454")
    public void testRegistreerHuwelijkEnPartnerschap() throws Exception {
        zetActieVoorBericht("registreerHuwelijkEnPartnerschap");
        Node antwoord = verzendBerichtNaarService("huwelijkPartnerschap_RegistratieHuwelijk_Bijhouding_v1.0.xml");
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);

        Assert.assertTrue("Huwelijk root node bestaat niet",
            XmlUtils.isNodeAanwezig("//huwelijkPartnerschap_RegistratieHuwelijk_BijhoudingResponse", document));
        Assert.assertTrue("resultaat bestaat niet", XmlUtils.isNodeAanwezig("//resultaat", document));
        Assert.assertEquals(Verwerkingsresultaat.VERWERKING_GESLAAGD.getCode(),
            XmlUtils.getNodeWaarde("//resultaat/verwerkingCode", document));
        // test de rest

        // now check in the database what has been written.
        Document orgAanvraag =
            XmlUtils
                .bouwDocumentVanBestand(getInputStream("huwelijkPartnerschap_RegistratieHuwelijk_Bijhouding_v1.0.xml"));
        String bsnMan =
            XmlUtils.getNodeWaarde("//partner[1]/persoon/identificatienummers/burgerservicenummer", orgAanvraag);
        String bsnVrouw =
            XmlUtils.getNodeWaarde("//partner[2]/persoon/identificatienummers/burgerservicenummer", orgAanvraag);
        List<String> bsnNrs = Arrays.asList(bsnMan, bsnVrouw);

        PersoonDto persoonMan = PersoonDto.persoonMetBsnNumerBestaatLevelA(simpleJdbcTemplate, bsnMan).get(0);
        Assert.assertNotNull("Kan geen persoon vinden", persoonMan);
        Assert.assertEquals(bsnMan, persoonMan.getBsn());

        PersoonDto persoonVrouw = PersoonDto.persoonMetBsnNumerBestaatLevelA(simpleJdbcTemplate, bsnVrouw).get(0);
        Assert.assertNotNull("Kan geen persoon vinden", persoonVrouw);
        Assert.assertEquals(bsnVrouw, persoonVrouw.getBsn());

        // Test Relatie en betrokkenheid
        List<Map<String, Object>> betrokkenheden1 =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.betr WHERE pers = ?", persoonMan.getId());
        Assert.assertEquals(1, betrokkenheden1.size());
        Integer relatieId1 = (Integer) betrokkenheden1.get(0).get("relatie");
        Assert.assertNotNull(relatieId1);
        Assert.assertEquals(SoortBetrokkenheid.PARTNER.ordinal(), betrokkenheden1.get(0).get("rol"));

        List<Map<String, Object>> betrokkenheden2 =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.betr WHERE pers = ?", persoonVrouw.getId());
        Assert.assertEquals(1, betrokkenheden2.size());
        Integer relatieId2 = (Integer) betrokkenheden2.get(0).get("relatie");
        Assert.assertNotNull(relatieId2);
        Assert.assertEquals(SoortBetrokkenheid.PARTNER.ordinal(), betrokkenheden2.get(0).get("rol"));

        Assert.assertTrue(bsnNrs.contains(
            XmlUtils.getNodeWaarde("//persoon[1]/identificatienummers/burgerservicenummer", document)));
        Assert.assertTrue(bsnNrs.contains(
            XmlUtils.getNodeWaarde("//persoon[2]/identificatienummers/burgerservicenummer", document)));

        Assert.assertEquals("betrokkenheid moet dezelfde relatie id hebben", relatieId1, relatieId2);

        // Test relatie
        List<Map<String, Object>> relatie =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.relatie WHERE id = ?", relatieId1);
        Assert.assertEquals("relatie moet landaanv Nederland hebben", 2, relatie.get(0).get("landaanv"));

        Assert.assertTrue(bsnNrs.contains(
            XmlUtils.getNodeWaarde("//persoon[1]/identificatienummers/burgerservicenummer", document)));
        Assert.assertTrue(bsnNrs.contains(
            XmlUtils.getNodeWaarde("//persoon[2]/identificatienummers/burgerservicenummer", document)));

        String bsnMan2 = XmlUtils.getNodeWaarde(
            "//wijzigingNaamgebruik/persoon/identificatienummers/burgerservicenummer", orgAanvraag);
        Assert.assertEquals(bsnMan, bsnMan2);

        // test op aanschrijving wijzigingen A Laag van Man
        List<Map<String, Object>> persoonenNaamWijziging =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.pers WHERE id = ?", persoonMan.getId());
        Assert.assertEquals(1, persoonenNaamWijziging.size());
        Assert.assertEquals("Geest", persoonenNaamWijziging.get(0).get("geslnaamaanschr"));
        Assert.assertEquals("van der", persoonenNaamWijziging.get(0).get("voorvoegselaanschr"));
        Assert.assertEquals("-", persoonenNaamWijziging.get(0).get("scheidingstekenaanschr"));
        Assert.assertEquals(null, persoonenNaamWijziging.get(0).get("voornamenaanschr"));
        Assert.assertEquals(false, persoonenNaamWijziging.get(0).get("indaanschralgoritmischafgele"));
        Assert.assertEquals(null, persoonenNaamWijziging.get(0).get("indaanschrmetadellijketitels"));
        Assert.assertEquals("A", persoonenNaamWijziging.get(0).get("aanschrstatushis"));

        // test op aanschrijving wijzigingen C/D Laag van Man
        List<Map<String, Object>> persoonenNaamWijzigingHis =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.his_persaanschr WHERE pers = ?", persoonMan.getId());
        Assert.assertEquals(1, persoonenNaamWijzigingHis.size());
        // "gebrgeslnaamegp"
        Assert.assertEquals("Geest", persoonenNaamWijzigingHis.get(0).get("geslnaamaanschr"));
        Assert.assertEquals("van der", persoonenNaamWijzigingHis.get(0).get("voorvoegselaanschr"));
        Assert.assertEquals("-", persoonenNaamWijzigingHis.get(0).get("scheidingstekenaanschr"));
        Assert.assertEquals(null, persoonenNaamWijzigingHis.get(0).get("voornamenaanschr"));
        Assert.assertEquals(false, persoonenNaamWijzigingHis.get(0).get("indaanschralgoritmischafgele"));
        Assert.assertEquals(null, persoonenNaamWijzigingHis.get(0).get("indaanschrmetadellijketitels"));
        Assert.assertEquals(Integer.valueOf(20120613), persoonenNaamWijzigingHis.get(0).get("dataanvgel"));
        Assert.assertNotNull(persoonenNaamWijzigingHis.get(0).get("actieinh"));

        Assert.assertEquals(null, persoonenNaamWijzigingHis.get(0).get("dateindegel"));
        Assert.assertEquals(null, persoonenNaamWijzigingHis.get(0).get("actieverval"));
        Assert.assertEquals(null, persoonenNaamWijzigingHis.get(0).get("tsverval"));

        // TODO aanschrijving met indicaties
        // test op aanschrijving wijzigingen A Laag van Vrouw
        List<Map<String, Object>> persoonenNaamWijzigingVrouw =
                 simpleJdbcTemplate.queryForList("SELECT * FROM kern.pers WHERE id = ?", persoonVrouw.getId());
        Assert.assertEquals(1, persoonenNaamWijzigingVrouw.size());
//        System.out.println("geslnaamaanschr=" + persoonenNaamWijzigingVrouw.get(0).get("geslnaamaanschr"));
//        System.out.println("voorvoegselaanschr=" + persoonenNaamWijzigingVrouw.get(0).get("voorvoegselaanschr"));
//        System.out.println("scheidingstekenaanschr=" + persoonenNaamWijzigingVrouw.get(0).get("scheidingstekenaanschr"));
//        System.out.println("voornamenaanschr=" + persoonenNaamWijzigingVrouw.get(0).get("voornamenaanschr"));
//        System.out.println("aanschrstatushis=" + persoonenNaamWijzigingVrouw.get(0).get("aanschrstatushis"));


        // in geval van "E"
        // gebrgeslnaamegp=1, voorvoegselaanschr=null, scheidingstekenaanschr=',',  voornamenaanschr='Single two',
        // aanschrstatushis='A'
//        Assert.assertEquals("Boer", persoonenNaamWijzigingVrouw.get(0).get("geslnaamaanschr"));
//        Assert.assertEquals(null, persoonenNaamWijzigingVrouw.get(0).get("voorvoegselaanschr"));
//        Assert.assertEquals(",", persoonenNaamWijzigingVrouw.get(0).get("scheidingstekenaanschr"));
//        Assert.assertEquals("Single two", persoonenNaamWijzigingVrouw.get(0).get("voornamenaanschr"));
//        Assert.assertEquals(true, persoonenNaamWijzigingVrouw.get(0).get("indaanschralgoritmischafgele"));
//        Assert.assertEquals(2, persoonenNaamWijzigingVrouw.get(0).get("gebrgeslnaamegp"));
//        Assert.assertEquals(null, persoonenNaamWijzigingVrouw.get(0).get("indaanschrmetadellijketitels"));
//        Assert.assertEquals("A", persoonenNaamWijzigingVrouw.get(0).get("aanschrstatushis"));

        // in geval van "V"
        Assert.assertEquals("Vries-,Boer", persoonenNaamWijzigingVrouw.get(0).get("geslnaamaanschr"));
        Assert.assertEquals(null, persoonenNaamWijzigingVrouw.get(0).get("voorvoegselaanschr"));
        Assert.assertEquals(",", persoonenNaamWijzigingVrouw.get(0).get("scheidingstekenaanschr"));
        Assert.assertEquals("Single two", persoonenNaamWijzigingVrouw.get(0).get("voornamenaanschr"));
        Assert.assertEquals(true, persoonenNaamWijzigingVrouw.get(0).get("indaanschralgoritmischafgele"));
        Assert.assertEquals(3, persoonenNaamWijzigingVrouw.get(0).get("gebrgeslnaamegp"));
        Assert.assertEquals(null, persoonenNaamWijzigingVrouw.get(0).get("indaanschrmetadellijketitels"));
        Assert.assertEquals("A", persoonenNaamWijzigingVrouw.get(0).get("aanschrstatushis"));

        List<Map<String, Object>> persoonenNaamWijzigingVrouwHis =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.his_persaanschr WHERE pers = ?", persoonVrouw.getId());
        Assert.assertEquals("Vries-,Boer", persoonenNaamWijzigingVrouwHis.get(0).get("geslnaamaanschr"));
        Assert.assertEquals(20120613, persoonenNaamWijzigingVrouwHis.get(0).get("dataanvgel"));
    }

    @Test @Ignore("nog niet geïmplementeerd, see SIERRA-496")
    public void testRegistreerHuwelijkEnPartnerschapMetEnkeleNietBestaandBsn() throws Exception {
        zetActieVoorBericht("registreerHuwelijkEnPartnerschap");
        Node antwoord = verzendBerichtNaarService("huwelijkPartnerschap_RegistratieHuwelijk_Bijhouding_Met_Niet_Bestaand_BSN_v1.0.xml");
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);

        printAlsResponseHeeftFoutCode(document);
        Assert.assertTrue("Huwelijk root node bestaat niet",
            XmlUtils.isNodeAanwezig("//huwelijkPartnerschap_RegistratieHuwelijk_BijhoudingResponse", document));
        Assert.assertTrue("resultaat bestaat niet", XmlUtils.isNodeAanwezig("//resultaat", document));
        Assert.assertEquals(Verwerkingsresultaat.VERWERKING_FOUTIEF.getCode(),
            XmlUtils.getNodeWaarde("//resultaat/verwerkingCode", document));

        // test de rest (incosistentie check)
        Assert.assertEquals("BRBY04011", XmlUtils.getNodeWaarde("//meldingen/melding/regelCode", document));
        Assert.assertEquals("F", XmlUtils.getNodeWaarde("//meldingen/melding/soortCode", document));
        Assert.assertEquals("id.naamgeb.pers.idnrs", XmlUtils.getAttribuutWaarde("//meldingen/melding", "brp:cIDVerzendend",
            document));
    }

    @Test
    public void testRegistreerOverlijden() throws Exception {
        zetActieVoorBericht("registreerOverlijden");
        Node antwoord = verzendBerichtNaarService("registratieOverlijden.xml");
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        System.out.println(xml);
        Document document = XmlUtils.bouwDocument(xml);

        printAlsResponseHeeftFoutCode(document);
        Assert.assertTrue("Overlijden root node bestaat niet",XmlUtils.isNodeAanwezig("//brp:OVL_RegistreerOverlijden_BR", document));
        Assert.assertTrue("resultaat bestaat niet", XmlUtils.isNodeAanwezig("//brp:resultaat", document));
        Assert.assertEquals(Verwerkingsresultaat.VERWERKING_GESLAAGD.getCode(),
            XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerkingCode", document));


        // now check in the database what has been written.
        Document orgAanvraag = XmlUtils.bouwDocumentVanBestand(getInputStream("registratieOverlijden.xml"));
        String bsnMan = XmlUtils.getAttribuutWaarde("//ns:persoon", "ns:technischeSleutel", orgAanvraag);

        PersoonDto persoonMan = PersoonDto.persoonMetBsnNumerBestaatLevelA(simpleJdbcTemplate, bsnMan).get(0);
        Assert.assertNotNull("Kan geen persoon vinden", persoonMan);
        Assert.assertEquals(bsnMan, persoonMan.getBsn());

        // Test Relatie en betrokkenheid
        List<Map<String, Object>> resultaten =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.pers WHERE pers.id = ?", persoonMan.getId());
        Assert.assertEquals(1, resultaten.size());
        Map<String, Object> res = resultaten.get(0);
        Assert.assertEquals(20130101, res.get("datoverlijden"));
        Assert.assertEquals(4, res.get("gemoverlijden"));
        Assert.assertEquals(1301, res.get("wploverlijden"));
        Assert.assertEquals(null, res.get("blplaatsoverlijden"));
        Assert.assertEquals(null, res.get("blregiooverlijden"));
        Assert.assertEquals(2, res.get("landoverlijden"));
        Assert.assertEquals(null, res.get("omslocoverlijden"));

        resultaten =
                simpleJdbcTemplate.queryForList("SELECT * FROM kern.his_persopschorting WHERE pers = ?", persoonMan.getId());
        Assert.assertEquals(1, resultaten.size());
        res = resultaten.get(0);
        Assert.assertEquals(1, res.get("rdnopschortingbijhouding"));
        Assert.assertEquals(20130101, res.get("dataanvgel"));

        resultaten =
                simpleJdbcTemplate.queryForList("SELECT * FROM kern.his_persoverlijden WHERE pers = ?", persoonMan.getId());
        Assert.assertEquals(1, resultaten.size());
        res = resultaten.get(0);
        Assert.assertEquals(20130101, res.get("datoverlijden"));
        Assert.assertEquals(4, res.get("gemoverlijden"));
        Assert.assertEquals(1301, res.get("wploverlijden"));
        Assert.assertEquals(null, res.get("blplaatsoverlijden"));
        Assert.assertEquals(null, res.get("blregiooverlijden"));
        Assert.assertEquals(2, res.get("landoverlijden"));
        Assert.assertEquals(null, res.get("omslocoverlijden"));
    }

    @Override
    URL getWsdlUrl() throws MalformedURLException {
        return new URL("http://localhost:8181/brp/services/bijhouding?wsdl");
    }

    @Override
    QName getServiceQName() {
        return new QName("http://www.bprbzk.nl/BRP/bijhouding/service", "BijhoudingService");
    }

    @Override
    QName getPortName() {
        return new QName("http://www.bprbzk.nl/BRP/bijhouding/service", "BijhoudingPort");
    }

    private void printAlsResponseHeeftFoutCode(final Document document) throws Exception {
        if (!Verwerkingsresultaat.VERWERKING_GESLAAGD.getCode()
                                 .equals(XmlUtils.getNodeWaarde("//resultaat/verwerkingCode", document)))
        {
            String xml = XmlUtils.toXmlString(document.getDocumentElement());
            LOG.error(xml);

        }
    }

}
