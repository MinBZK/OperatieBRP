/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.xpath.XPathExpressionException;
import nl.bzk.brp.model.GeslachtsnaamcomponentDto;
import nl.bzk.brp.model.NationaliteitDto;
import nl.bzk.brp.model.PersoonDto;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
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
public class RegistreerGeboorteIntegratieTest extends AbstractBijhoudingServiceIntegrationTest {

    private static final Logger LOG = LoggerFactory
            .getLogger();

    @SuppressWarnings("deprecation")
    @Test
    public void testInschrijvingGeboorte() throws Exception {
        String xmlBericht = "afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001.xml";
        zetActieVoorBericht("registreerGeboorte");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsRegistreerGeboorte/brp:geboorteInNederland/brp:acties/brp:registratieGeboorte/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder/brp:persoon/@brp:objectSleutel",
                1001
        );
        Node antwoord = verzendBerichtNaarService(xmlBericht);
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);

        assertTrue(
            "InschrijvingAangifteGeboorte root node bestaat niet", XmlUtils
                .isNodeAanwezig("//brp:bhg_afsRegistreerGeboorte_R",
                    document));
        assertTrue("resultaat bestaat niet",
            XmlUtils.isNodeAanwezig("//brp:resultaat", document));
        assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
            XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerking",
                document));

        //Controleer de persoon in de database en haal gelijk de persoon op
        PersoonDto persoonDto = controleerEnHaalPersoonUitBericht(xmlBericht);

        Integer kindId = persoonDto.getId();
        Integer ouderId = 1001;

        //Controleer de geslachtsnaamcomponent in de database
        GeslachtsnaamcomponentDto geslnaamcompDto = new GeslachtsnaamcomponentDto();
        geslnaamcompDto.setPredikaatCode("K");
        geslnaamcompDto.setNaam("Vis");
        controllerGeslachtsnaamComponenten(kindId, 0, geslnaamcompDto);

        // Test Relatie en betrokkenheid
        List<Map<String, Object>> betrokkenheden = jdbcTemplate
                .queryForList("SELECT * FROM kern.betr WHERE pers = ?", kindId);
        assertFalse(betrokkenheden.isEmpty());
        assertEquals(1, betrokkenheden.size());
        Integer relatieId = (Integer) betrokkenheden.get(0).get("relatie");
        Assert.assertNotNull(relatieId);


        Map<String, Object> controleWaarden = new HashMap<>();
        controleWaarden.put("rol", SoortBetrokkenheid.OUDER.ordinal());
        controleWaarden.put("indOuder", Boolean.TRUE);
        controleWaarden.put("dataanv", null);
        controleerBetrokkenhedenInRelatie(relatieId, 2, ouderId, controleWaarden);

        controleWaarden = new HashMap<>();
        controleWaarden.put("rol", SoortBetrokkenheid.KIND.ordinal());
        controleWaarden.put("indOuder", null);
        controleWaarden.put("dataanv", null);
        controleerBetrokkenhedenInRelatie(relatieId, 2, kindId, controleWaarden);

        //Controleer bsn's in het antwoord bericht
        controleerBsnInAntwoordeBericht(document, "182101411", persoonDto.getBsn());


        List<Map<String, Object>> adressen = jdbcTemplate.queryForList(
                "SELECT * FROM kern.persadres WHERE pers = ?", kindId);
        assertFalse(adressen.isEmpty());
        assertEquals(1, adressen.size());
        // TODO Assert.assertEquals(x, adressen.get(0).get("..."));

        List<Map<String, Object>> voornamen = jdbcTemplate.queryForList(
                "SELECT * FROM kern.persadres WHERE pers = ?", kindId);
        assertFalse(adressen.isEmpty());
        assertEquals(1, adressen.size());
        // TODO Assert.assertEquals(x, adressen.get(0).get("..."));
    }

    @Test
    public void testInschrijvenGeboorteMetOverrulebareFout() throws Exception {
        zetActieVoorBericht("registreerGeboorte");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsRegistreerGeboorte/brp:geboorteInNederland/brp:acties/brp:registratieGeboorte/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder/brp:persoon/@brp:objectSleutel",
                1001
        );
        Node antwoord = verzendBerichtNaarService("afstamming_InschrijvingAangifteGeboorte_Fout001.xml");
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);
        // Wij verwachten dat nu een 'O' terugkomgt met de code (BRBY0106,
        // 'Geslachtsnaam van kind komt niet overeen met die van een van de
        // ouders of die
        // van andere gemeenschappelijke kinderen.')
        // omdat het kind geslachtnaam Kantjes' en de (enige) ouder 'Kant' heet.
        // En dit was de enige fout.
        assertEquals(Verwerkingsresultaat.FOUTIEF.getNaam(),
            XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerking",
                document));
        assertEquals("BRBY0106", XmlUtils.getNodeWaarde(
            "//brp:meldingen/brp:melding[1]/brp:regelCode", document));
        assertEquals("Deblokkeerbaar", XmlUtils.getNodeWaarde(
            "//brp:meldingen/brp:melding[1]/brp:soortNaam", document));
    }

    @Test
    public void testInschrijvenGeboorteMetOverrulebareFoutOverruled() throws Exception {
        zetActieVoorBericht("registreerGeboorte");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsRegistreerGeboorte/brp:geboorteInNederland/brp:acties/brp:registratieGeboorte/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder/brp:persoon/@brp:objectSleutel",
                1001
        );
        Node antwoord = verzendBerichtNaarService("afstamming_InschrijvingAangifteGeboorte_Fout001_Overruled.xml");
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);
        // Wij verwachten dat nu goed is verwerkt (G),
        // er is nog steeds een lijst van meldingen van type W met de code
        // (BRBY0106,
        // 'Geslachtsnaam van kind komt niet overeen met die van een van de
        // ouders of die
        // van andere gemeenschappelijke kinderen.')
        // En dit was de enige fout.
        assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
            XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerking",
                document));
        assertEquals("BRBY0106", XmlUtils.getNodeWaarde(
            "//brp:meldingen/brp:melding/brp:regelCode", document));
        assertEquals("Waarschuwing", XmlUtils.getNodeWaarde(
            "//brp:meldingen/brp:melding/brp:soortNaam", document));
        // Daarnaast is een lijst met de overruled die zijn gebruikt.
        assertEquals(
            "BRBY0106",
            XmlUtils.getNodeWaarde(
                "//brp:gedeblokkeerdeMeldingen/brp:gedeblokkeerdeMelding/brp:regelCode",
                document));
        // Assert.assertEquals("O",
        // XmlUtils.getNodeWaarde("//overruleMeldingen/overrule/soortCode",
        // document));
    }

    @Test
    public void testInschrijvingGeboorteFoutNaamNietGelijkAanBroer() throws Exception {
        zetActieVoorBericht("registreerGeboorte");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsRegistreerGeboorte/brp:geboorteInNederland/brp:acties/brp:registratieGeboorte/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder[1]/brp:persoon/@brp:objectSleutel",
                2
        );
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsRegistreerGeboorte/brp:geboorteInNederland/brp:acties/brp:registratieGeboorte/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder[2]/brp:persoon/@brp:objectSleutel",
                1
        );
        Node antwoord =
                verzendBerichtNaarService("afstamming_InschrijvingAangifteGeboorte_Fout002_AndereNaamDanBroer.xml");
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);

        assertEquals(Verwerkingsresultaat.FOUTIEF.getNaam(),
            XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerking",
                document));
        assertEquals("BRBY0107", XmlUtils.getNodeWaarde(
            "//brp:meldingen/brp:melding[1]/brp:regelCode", document));
        assertEquals("Deblokkeerbaar", XmlUtils.getNodeWaarde(
            "//brp:meldingen/brp:melding[1]/brp:soortNaam", document));
    }

    @Test
    public void testInschrijvingGeboorteFoutNaamWelGelijkAanBroer() throws Exception {
        zetActieVoorBericht("registreerGeboorte");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsRegistreerGeboorte/brp:geboorteInNederland/brp:acties/brp:registratieGeboorte/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder[1]/brp:persoon/@brp:objectSleutel",
                2
        );
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsRegistreerGeboorte/brp:geboorteInNederland/brp:acties/brp:registratieGeboorte/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder[2]/brp:persoon/@brp:objectSleutel",
                1
        );
        Node antwoord = verzendBerichtNaarService("afstamming_InschrijvingAangifteGeboorte_ZelfdeNaamAlsBroer.xml");
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);

        assertTrue("resultaat bestaat niet",
            XmlUtils.isNodeAanwezig("//brp:resultaat", document));
        assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
            XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerking",
                document));
    }


    @SuppressWarnings("deprecation")
    @Test
    public void testInschrijvingDoorGeboorteMetErkenningMetInschrevenOuders() throws Exception {
        String xmlBericht = "afstamming_inschrijvingDoorGeboorteMetErkenning_ingeschrevenOuders.xml";
        zetActieVoorBericht("registreerGeboorte");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsRegistreerGeboorte/brp:geboorteInNederlandMetErkenning/brp:acties/brp:registratieGeboorte/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder/brp:persoon/@brp:objectSleutel",
                1001
        );
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsRegistreerGeboorte/brp:geboorteInNederlandMetErkenning/brp:acties/brp:registratieOuder/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder/brp:persoon/@brp:objectSleutel",
                3
        );
        Node antwoord =
                verzendBerichtNaarService(xmlBericht);
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);

        assertTrue(
            "InschrijvingAangifteGeboorte root node bestaat niet", XmlUtils
                .isNodeAanwezig("//brp:bhg_afsRegistreerGeboorte_R",
                    document));
        assertTrue("resultaat bestaat niet",
            XmlUtils.isNodeAanwezig("//brp:resultaat", document));
        assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
            XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerking",
                document));

        //Controleer de persoon in de database en haal gelijk de persoon op
        PersoonDto persoon = controleerEnHaalPersoonUitBericht(xmlBericht);
        Integer kindId = persoon.getId();

        //Controleer de geslachtsnaamcomponent in de database
        GeslachtsnaamcomponentDto geslnaamcompDto = new GeslachtsnaamcomponentDto();
        geslnaamcompDto.setPredikaatCode("K");
        geslnaamcompDto.setNaam("Vis");
        controllerGeslachtsnaamComponenten(kindId, 0, geslnaamcompDto);

        //Controleer de relatie en de betrokkenheden
        List<Map<String, Object>> betrokkenheden = jdbcTemplate
                .queryForList("SELECT * FROM kern.betr WHERE pers = ?", kindId);
        assertFalse(betrokkenheden.isEmpty());
        assertEquals(1, betrokkenheden.size());
        Integer relatieId = (Integer) betrokkenheden.get(0).get("relatie");
        Assert.assertNotNull(relatieId);

        Map<String, Object> controleWaarden = new HashMap<>();
        controleWaarden.put("rol", SoortBetrokkenheid.OUDER.ordinal());
        controleWaarden.put("indOuder", Boolean.TRUE);
        controleWaarden.put("dataanv", null);
        controleerBetrokkenhedenInRelatie(relatieId, 3, 1001, controleWaarden);

        controleWaarden = new HashMap<>();
        controleWaarden.put("rol", SoortBetrokkenheid.OUDER.ordinal());
        controleWaarden.put("indOuder", Boolean.TRUE);
        controleWaarden.put("dataanv", null);
        controleerBetrokkenhedenInRelatie(relatieId, 3, 3, controleWaarden);

        controleWaarden = new HashMap<>();
        controleWaarden.put("rol", SoortBetrokkenheid.KIND.ordinal());
        controleWaarden.put("indOuder", null);
        controleWaarden.put("dataanv", null);
        controleerBetrokkenhedenInRelatie(relatieId, 3, kindId, controleWaarden);

        //Controleer nationaliteit
        controleerNationaliteiten(persoon.getId(), 2);

        //Controleer bsn's in het antwoord bericht
        controleerBsnInAntwoordeBericht(document, "089444917", "182101411", persoon.getBsn());
    }

    @Test
    public void testInschrijvingDoorGeboorteMetErkenningMetInschrevenOudersMeerderNationaliteitenInEenActie()
        throws Exception
    {
        String xmlBericht = "afstamming_inschrijvingDoorGeboorteMetErkenning_ingeschrevenOudersMet2Nation1.xml";
        zetActieVoorBericht("registreerGeboorte");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsRegistreerGeboorte/brp:geboorteInNederlandMetErkenning/brp:acties/brp:registratieGeboorte/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder/brp:persoon/@brp:objectSleutel",
                1001
        );
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsRegistreerGeboorte/brp:geboorteInNederlandMetErkenning/brp:acties/brp:registratieOuder/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder/brp:persoon/@brp:objectSleutel",
                3
        );
        Node antwoord =
                verzendBerichtNaarService(xmlBericht);
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);

        //Controleer de persoon in de database en haal gelijk de persoon op
        PersoonDto persoon = controleerEnHaalPersoonUitBericht(xmlBericht);

        //Controleer nationaliteit
        controleerNationaliteiten(persoon.getId(), 3, 2);
    }

    @Test
    public void testInschrijvingDoorGeboorteMetErkenningMetInschrevenOudersMeerderNationaliteitenInTweeActie()
        throws Exception
    {
        String xmlBericht = "afstamming_inschrijvingDoorGeboorteMetErkenning_ingeschrevenOudersMet2Nation2.xml";
        zetActieVoorBericht("registreerGeboorte");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsRegistreerGeboorte/brp:geboorteInNederlandMetErkenning/brp:acties/brp:registratieGeboorte/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder/brp:persoon/@brp:objectSleutel",
                1001
        );
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsRegistreerGeboorte/brp:geboorteInNederlandMetErkenning/brp:acties/brp:registratieOuder/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder/brp:persoon/@brp:objectSleutel",
                3
        );
        Node antwoord =
                verzendBerichtNaarService(xmlBericht);
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);

        //Controleer de persoon in de database en haal gelijk de persoon op
        PersoonDto persoon = controleerEnHaalPersoonUitBericht(xmlBericht);

        //Controleer nationaliteit
        controleerNationaliteiten(persoon.getId(), 2, 3);
    }


    @SuppressWarnings("deprecation")
    @Test
    public void testInschrijvingGeboorteMetStaatloos() throws Exception {
        String xmlBericht = "afstamming_AFS_RegistreerGeboorte_B_RegistratieStaatloos.xml";
        zetActieVoorBericht("registreerGeboorte");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsRegistreerGeboorte/brp:geboorteInNederland/brp:acties/brp:registratieGeboorte/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder/brp:persoon/@brp:objectSleutel",
                1001
        );
        Node antwoord = verzendBerichtNaarService(xmlBericht);
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);

        assertTrue(
            "InschrijvingAangifteGeboorte root node bestaat niet", XmlUtils
                .isNodeAanwezig("//brp:bhg_afsRegistreerGeboorte_R",
                    document));
        assertTrue("resultaat bestaat niet",
            XmlUtils.isNodeAanwezig("//brp:resultaat", document));
        assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
            XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerking",
                document));

        //Controleer de persoon in de database en haal gelijk de persoon op
        PersoonDto persoonDto = controleerEnHaalPersoonUitBericht(xmlBericht);

        Integer kindId = persoonDto.getId();

        List<Map<String, Object>> indicaties = jdbcTemplate.queryForList(
                "SELECT * FROM kern.persindicatie WHERE pers = ?", kindId);
        assertFalse(indicaties.isEmpty());
        assertEquals(1, indicaties.size());
        assertEquals(SoortIndicatie.INDICATIE_STAATLOOS.ordinal(), indicaties.get(0).get("srt"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testInschrijvingGeboorteMetBehandeldAlsNederlander() throws Exception {
        String xmlBericht = "afstamming_AFS_RegistreerGeboorte_B_RegistratieBehandeldAlsNederlander.xml";
        zetActieVoorBericht("registreerGeboorte");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsRegistreerGeboorte/brp:geboorteInNederland/brp:acties/brp:registratieGeboorte/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder/brp:persoon/@brp:objectSleutel",
                1001
        );
        Node antwoord = verzendBerichtNaarService(xmlBericht);
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);

        assertTrue(
            "InschrijvingAangifteGeboorte root node bestaat niet", XmlUtils
                .isNodeAanwezig("//brp:bhg_afsRegistreerGeboorte_R",
                    document));
        assertTrue("resultaat bestaat niet",
            XmlUtils.isNodeAanwezig("//brp:resultaat", document));
        assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
            XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerking",
                document));

        //Controleer de persoon in de database en haal gelijk de persoon op
        PersoonDto persoonDto = controleerEnHaalPersoonUitBericht(xmlBericht);

        Integer kindId = persoonDto.getId();

        List<Map<String, Object>> indicaties = jdbcTemplate.queryForList(
                "SELECT * FROM kern.persindicatie WHERE pers = ?", kindId);
        assertFalse(indicaties.isEmpty());
        assertEquals(1, indicaties.size());
        assertEquals(SoortIndicatie.INDICATIE_BEHANDELD_ALS_NEDERLANDER.ordinal(), indicaties.get(0).get("srt"));
    }

    private void controleerNationaliteiten(final Integer persoonId, final Integer... nationaliteiten) {
        //Controleer nationaliteit
        List<NationaliteitDto> nations = NationaliteitDto
                .getPersoonNationaliteitLevelA(jdbcTemplate, persoonId);
        assertTrue("Kan geen naamscomponenten vinden",
            !nations.isEmpty());

        assertEquals(nationaliteiten.length, nations.size());

        final List<Integer> verwachteIds = Arrays.asList(nationaliteiten);
        int aantalMatches = 0;
        for (int i = 0; i < nationaliteiten.length; i++) {
            if (verwachteIds.contains(nations.get(i).getNation())) {
                aantalMatches++;
            }
        }
        assertEquals(nationaliteiten.length, aantalMatches);
    }


    @SuppressWarnings("deprecation")
    private void controleerBetrokkenhedenInRelatie(final Integer relatieId, final int aantalBetrokkenheden,
                                                    final Integer persoonId, final Map<String, Object> values)
    {
        List<Map<String, Object>> betrokkenheden = jdbcTemplate
                .queryForList("SELECT * FROM kern.betr WHERE relatie = ?",
                        relatieId);
        assertFalse(betrokkenheden.isEmpty());
        assertEquals(aantalBetrokkenheden, betrokkenheden.size());

        Map<String, Object> betrokkenheid = getBetrokkenheid(betrokkenheden, persoonId);

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            LOG.info("Controleer Betrokkenheden, assert key: " + entry.getKey() + " value: " + entry.getValue());
            assertEquals(entry.getValue(),
                betrokkenheid.get(entry.getKey()));
        }
    }

    private void controleerBsnInAntwoordeBericht(final Document document, final String... burgerservicenummers)
        throws XPathExpressionException
    {
        for (int i = 0; i < burgerservicenummers.length; i++) {
            int xmlIndex = i + 1;
            assertEquals(
                burgerservicenummers[i],
                XmlUtils.getNodeWaarde(
                    "//brp:bijgehoudenPersonen/brp:persoon[" + xmlIndex
                        + "]/brp:identificatienummers/brp:burgerservicenummer",
                    document));
        }
    }

    private void controllerGeslachtsnaamComponenten(final int persoonId, final int geslcompIndex,
                                                    final GeslachtsnaamcomponentDto geslachtsnaamcomponent)
    {
        //Controleer Geslachtsnaamcomponenten
        List<GeslachtsnaamcomponentDto> geslachtscomponenten = GeslachtsnaamcomponentDto
                .getGeslachtsnaamcomponentA(jdbcTemplate, persoonId);
        assertTrue("Kan geen naamscomponenten vinden",
            !geslachtscomponenten.isEmpty());
        GeslachtsnaamcomponentDto gc = geslachtscomponenten.get(geslcompIndex);

        assertEquals(geslachtsnaamcomponent.getAdelijkeTitelCode(), gc.getAdelijkeTitelCode());
        assertEquals(geslachtsnaamcomponent.getPredicaatCode(), gc.getPredicaatCode());
        assertEquals(geslachtsnaamcomponent.getScheidingsteken(), gc.getScheidingsteken());
        assertEquals(geslachtsnaamcomponent.getVoorVoegsel(), gc.getVoorVoegsel());
        assertEquals(geslachtsnaamcomponent.getNaam(), gc.getNaam());
    }


    private PersoonDto controleerEnHaalPersoonUitBericht(final String bericht) throws Exception {
        //Controleer database
        Document orgAanvraag = XmlUtils
                .bouwDocumentVanBestand(
                        getInputStream(bericht));
        String bsn = XmlUtils
                .getNodeWaarde(
                        "//brp:registratieIdentificatienummers/brp:persoon/brp:identificatienummers/brp:burgerservicenummer",
                        orgAanvraag);
        String anr = XmlUtils
                .getNodeWaarde(
                        "//brp:registratieIdentificatienummers/brp:persoon/brp:identificatienummers/brp:administratienummer",
                        orgAanvraag);
        List<PersoonDto> personen = PersoonDto.persoonMetBsnBestaatInALaag(
            jdbcTemplate, bsn);

        assertTrue("Kan geen persoon vinden", personen.size() > 0);
        assertEquals(bsn, personen.get(0).getBsn());
        assertEquals(anr, personen.get(0).getAnr());

        return personen.get(0);
    }

    private Map<String, Object> getBetrokkenheid(final List<Map<String, Object>> betrokkenhedenRelatie,
                                                 final Integer persoonId)
    {
        for (Map<String, Object> betrokkenheid : betrokkenhedenRelatie) {
            Integer betrokkeneId = (Integer) betrokkenheid.get("pers");
            if (betrokkeneId.equals(persoonId)
                    || betrokkeneId.equals(persoonId))
            {
                return betrokkenheid;
            }
        }

        return null;
    }


    @Override
    Logger getLogger() {
        return LOG;
    }

    @Override
    String getWsdlPortType() {
        return "bhgAfstamming";
    }
}
