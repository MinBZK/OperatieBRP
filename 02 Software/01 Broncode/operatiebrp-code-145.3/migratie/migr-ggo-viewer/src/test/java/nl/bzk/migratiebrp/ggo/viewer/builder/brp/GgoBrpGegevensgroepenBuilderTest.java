/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderlijkGezagHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Document;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.util.PortInitializer;
import nl.bzk.migratiebrp.ggo.viewer.util.ViewerDateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test de GgoBrp3Builder klasse
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = {"classpath:test-viewer-beans.xml"}, initializers = {PortInitializer.class})
public class GgoBrpGegevensgroepenBuilderTest {

    @Inject
    private GgoBrpGegevensgroepenBuilder builder;

    private Betrokkenheid betrokkenheid;

    @Before
    public void setUp() {
        betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING));
    }

    @Test
    public void testAddGroepRelatieFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final Integer datumAanvang = 20131220;
        final Gemeente gemeenteAanvang = new Gemeente((short) 518, "Gemeente", "0014", new Partij("Partij", "001201"));
        final String woonplaatsnaamAanvang = "woonplaatsnaam2";
        final String buitenlandsePlaatsAanvang = "Maask";
        final String buitenlandseRegioAanvang = "Vlaam";
        final LandOfGebied landOfGebiedCodeAanvang = new LandOfGebied("6040", "Land");
        final String omschrijvingLocatieAanvang = "Daaro";

        final RedenBeeindigingRelatie redenBeeindigingRelatie = new RedenBeeindigingRelatie('Z', "Zomaar");
        final Integer datumEinde = 20131221;
        final String woonplaatsnaamEinde = "woonplaatsnaam3";
        final String buitenlandsePlaatsEinde = "Mask";
        final String buitenlandseRegioEinde = "Vlam";
        final LandOfGebied landOfGebiedCodeEinde = new LandOfGebied("6041", "Gebied");
        final String omschrijvingLocatieEinde = "Daro";

        final RelatieHistorie inhoud = new RelatieHistorie(new Relatie(SoortRelatie.HUWELIJK));
        inhoud.setDatumAanvang(datumAanvang);
        inhoud.setLandOfGebiedAanvang(landOfGebiedCodeAanvang);
        inhoud.setGemeenteAanvang(gemeenteAanvang);
        inhoud.setWoonplaatsnaamAanvang(woonplaatsnaamAanvang);
        inhoud.setBuitenlandsePlaatsAanvang(buitenlandsePlaatsAanvang);
        inhoud.setBuitenlandseRegioAanvang(buitenlandseRegioAanvang);
        inhoud.setOmschrijvingLocatieAanvang(omschrijvingLocatieAanvang);
        inhoud.setRedenBeeindigingRelatie(redenBeeindigingRelatie);
        inhoud.setDatumEinde(datumEinde);
        inhoud.setGemeenteEinde(gemeenteAanvang);
        inhoud.setWoonplaatsnaamEinde(woonplaatsnaamEinde);
        inhoud.setBuitenlandsePlaatsEinde(buitenlandsePlaatsEinde);
        inhoud.setBuitenlandseRegioEinde(buitenlandseRegioEinde);
        inhoud.setLandOfGebiedEinde(landOfGebiedCodeEinde);
        inhoud.setOmschrijvingLocatieEinde(omschrijvingLocatieEinde);

        builder.addGroepRelatie(voorkomen, inhoud);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.DATUM_AANVANG, ViewerDateUtil.formatDatum(datumAanvang));
        assertContains(ggoInhoud, GgoBrpElementEnum.GEMEENTE_AANVANG, "0014 (Gemeente)");
        assertContains(ggoInhoud, GgoBrpElementEnum.WOONPLAATSNAAM_AANVANG, woonplaatsnaamAanvang);
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDSE_PLAATS_AANVANG, buitenlandsePlaatsAanvang);
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDSE_REGIO_AANVANG, buitenlandseRegioAanvang);
        assertContains(ggoInhoud, GgoBrpElementEnum.LAND_OF_GEBIED_AANVANG, "6040 (Land)");
        assertContains(ggoInhoud, GgoBrpElementEnum.OMSCHRIJVING_LOCATIE_AANVANG, omschrijvingLocatieAanvang);
        assertContains(ggoInhoud, GgoBrpElementEnum.DATUM_EINDE, ViewerDateUtil.formatDatum(datumEinde));
        assertContains(ggoInhoud, GgoBrpElementEnum.GEMEENTE_EINDE, "0014 (Gemeente)");
        assertContains(ggoInhoud, GgoBrpElementEnum.WOONPLAATSNAAM_EINDE, woonplaatsnaamEinde);
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDSE_PLAATS_EINDE, buitenlandsePlaatsEinde);
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDSE_REGIO_EINDE, buitenlandseRegioEinde);
        assertContains(ggoInhoud, GgoBrpElementEnum.LAND_OF_GEBIED_EINDE, "6041 (Gebied)");
        assertContains(ggoInhoud, GgoBrpElementEnum.OMSCHRIJVING_LOCATIE_EINDE, omschrijvingLocatieEinde);
    }

    @Test
    public void testAddGroepRelatieEmpty() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        builder.addGroepRelatie(voorkomen, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.DATUM_AANVANG);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.GEMEENTE_AANVANG);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.WOONPLAATSNAAM_AANVANG);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDSE_PLAATS_AANVANG);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDSE_REGIO_AANVANG);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.LAND_OF_GEBIED_AANVANG);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.OMSCHRIJVING_LOCATIE_AANVANG);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.REDEN_EINDE_RELATIE);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.DATUM_EINDE);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.GEMEENTE_EINDE);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.WOONPLAATSNAAM_EINDE);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDSE_PLAATS_EINDE);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDSE_REGIO_EINDE);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.LAND_OF_GEBIED_EINDE);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.OMSCHRIJVING_LOCATIE_EINDE);
    }

    @Test
    public void testAddGroepDocumentFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final SoortDocument soortDocumentCode = new SoortDocument("naam", "oms");
        final String aktenummer = "A21341234";
        final String omschrijving = "Omschrijving";

        final Document inhoud = new Document(soortDocumentCode, new Partij("tegen de Anderen", "001101"));
        inhoud.setAktenummer(aktenummer);
        inhoud.setOmschrijving(omschrijving);

        builder.addGroepDocument(voorkomen.getInhoud(), inhoud);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.SOORT_DOCUMENT, soortDocumentCode.getNaam());
        assertContains(ggoInhoud, GgoBrpElementEnum.AKTENUMMER, aktenummer);
        assertContains(ggoInhoud, GgoBrpElementEnum.OMSCHRIJVING, omschrijving);
        assertContains(ggoInhoud, GgoBrpElementEnum.PARTIJ, "001101 (tegen de Anderen)");
    }

    @Test
    public void testAddGroepDocumentEmpty() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        builder.addGroepDocument(voorkomen.getInhoud(), null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.SOORT_DOCUMENT);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.AKTENUMMER);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.OMSCHRIJVING);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.PARTIJ);
    }

    @Test
    public void testAddGroepOuderlijkGezagFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final BetrokkenheidOuderlijkGezagHistorie inhoud = new BetrokkenheidOuderlijkGezagHistorie(betrokkenheid, false);

        builder.addGroepOuderlijkGezag(voorkomen, inhoud);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.OUDER_HEEFT_GEZAG, "Nee");
    }

    @Test
    public void testAddGroepOuderlijkGezagEmpty() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        builder.addGroepOuderlijkGezag(voorkomen, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.OUDER_HEEFT_GEZAG);
    }

    @Test
    public void testAddGroepOuderFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final BetrokkenheidOuderHistorie inhoud = new BetrokkenheidOuderHistorie(betrokkenheid);
        inhoud.setIndicatieOuderUitWieKindIsGeboren(false);

        builder.addGroepOuder(voorkomen, inhoud);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.INDICATIE_OUDERUITWIEKINDISGEBOREN, "Nee");
    }

    @Test
    public void testAddHistorieFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final Integer datumAanvangGeldigheid = 20130101;
        final Integer datumEindeGeldigheid = 20130101;
        final Timestamp datumTijdRegistratie = new Timestamp(new Date().getTime());
        final Timestamp datumTijdVerval = new Timestamp(new Date().getTime());

        final MaterieleHistorie brpHistorie = new PersoonVoornaamHistorie(new PersoonVoornaam(new Persoon(SoortPersoon.INGESCHREVENE), 1), "Henk");
        brpHistorie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        brpHistorie.setDatumEindeGeldigheid(datumEindeGeldigheid);
        brpHistorie.setDatumTijdRegistratie(datumTijdRegistratie);
        brpHistorie.setDatumTijdVerval(datumTijdVerval);

        builder.addHistorie(voorkomen, brpHistorie);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(
                ggoInhoud,
                GgoBrpElementEnum.DATUM_GELDIGHEID,
                ViewerDateUtil.formatDatum(datumAanvangGeldigheid) + " - " + ViewerDateUtil.formatDatum(datumEindeGeldigheid));
        assertContains(
                ggoInhoud,
                GgoBrpElementEnum.DATUM_TIJD_REGISTRATIE_VERVAL,
                ViewerDateUtil.formatDatumTijdUtc(datumTijdRegistratie) + " - " + ViewerDateUtil.formatDatumTijdUtc(datumTijdVerval));
    }

    @Test
    public void testAddHistorieEmptyAll() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        builder.addHistorie(voorkomen, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.DATUM_GELDIGHEID);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.DATUM_TIJD_REGISTRATIE_VERVAL);
    }

    @Test
    public void testAddHistorieEmptyParts() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final Integer datumEindeGeldigheid = 20130101;
        final Timestamp datumTijdRegistratie = new Timestamp(new Date().getTime());

        final MaterieleHistorie brpHistorie = new PersoonVoornaamHistorie(new PersoonVoornaam(new Persoon(SoortPersoon.INGESCHREVENE), 1), "Henk");
        brpHistorie.setDatumEindeGeldigheid(datumEindeGeldigheid);
        brpHistorie.setDatumTijdRegistratie(datumTijdRegistratie);

        builder.addHistorie(voorkomen, brpHistorie);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.DATUM_GELDIGHEID, " - " + ViewerDateUtil.formatDatum(datumEindeGeldigheid));
        assertContains(ggoInhoud, GgoBrpElementEnum.DATUM_TIJD_REGISTRATIE_VERVAL, ViewerDateUtil.formatDatumTijdUtc(datumTijdRegistratie) + " - ");
    }

    private void assertContains(final Map<String, String> voorkomen, final GgoBrpElementEnum element, final String expected) {
        final String key = element.getLabel();
        assertTrue("Key: " + key + " komt niet voor!", voorkomen.containsKey(key));
        assertNotNull("Geen waarde bij key: " + key, voorkomen.get(key));
        assertEquals("Waarde is niet verwacht.", expected, voorkomen.get(key));
    }

    private void assertNotContains(final Map<String, String> voorkomen, final GgoBrpElementEnum element) {
        final String key = element.getLabel();
        assertTrue("Key: " + key + " komt voor, zou niet moeten.", !voorkomen.containsKey(key));
    }
}
