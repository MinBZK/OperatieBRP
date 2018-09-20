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
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.service.BrpStamtabelService;
import nl.bzk.migratiebrp.ggo.viewer.util.ViewerDateUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Betrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BetrokkenheidOuderHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BetrokkenheidOuderlijkGezagHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Document;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DocumentHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.LandOfGebied;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.MaterieleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVoornaam;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVoornaamHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenBeeindigingRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RelatieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortBetrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortDocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortRelatie;
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
@ContextConfiguration(locations = {"classpath:test-viewer-beans.xml" })
public class GgoBrpGegevensgroepenBuilderTest {

    @Inject
    private GgoBrpGegevensgroepenBuilder builder;

    @Inject
    private BrpStamtabelService brpStamtabelService;

    @Inject
    private GgoBrpValueConvert brpValueConvert;

    private Betrokkenheid betrokkenheid;

    @Before
    public void setUp() {
        betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, new Relatie(SoortRelatie.ERKENNING_ONGEBOREN_VRUCHT));
    }

    @Test
    public void testAddGroepRelatieFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final Integer datumAanvang = 20131220;
        final Gemeente gemeenteAanvang = new Gemeente((short) 518, "Gemeente", (short) 14, new Partij("Partij", (short) 12));
        final String woonplaatsnaamAanvang = "woonplaatsnaam2";
        final String buitenlandsePlaatsAanvang = "Maask";
        final String buitenlandseRegioAanvang = "Vlaam";
        final LandOfGebied landOfGebiedCodeAanvang = new LandOfGebied((short) 6040, "Land");
        final String omschrijvingLocatieAanvang = "Daaro";

        final RedenBeeindigingRelatie redenBeeindigingRelatie = new RedenBeeindigingRelatie('Z', "Zomaar");
        final Integer datumEinde = 20131221;
        final Gemeente gemeenteEinde = gemeenteAanvang;
        final String woonplaatsnaamEinde = "woonplaatsnaam3";
        final String buitenlandsePlaatsEinde = "Mask";
        final String buitenlandseRegioEinde = "Vlam";
        final LandOfGebied landOfGebiedCodeEinde = new LandOfGebied((short) 6041, "Gebied");
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
        inhoud.setGemeenteEinde(gemeenteEinde);
        inhoud.setWoonplaatsnaamEinde(woonplaatsnaamEinde);
        inhoud.setBuitenlandsePlaatsEinde(buitenlandsePlaatsEinde);
        inhoud.setBuitenlandseRegioEinde(buitenlandseRegioEinde);
        inhoud.setLandOfGebiedEinde(landOfGebiedCodeEinde);
        inhoud.setOmschrijvingLocatieEinde(omschrijvingLocatieEinde);

        builder.addGroepRelatie(voorkomen, inhoud, null);
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

        builder.addGroepRelatie(voorkomen, null, null);
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
        final String identificatie = "hj45jhrekw34";
        final String aktenummer = "A21341234";
        final String omschrijving = "Omschrijving";

        final DocumentHistorie inhoud = new DocumentHistorie(new Document(soortDocumentCode), new Partij("tegen de Anderen", 11));
        inhoud.setIdentificatie(identificatie);
        inhoud.setAktenummer(aktenummer);
        inhoud.setOmschrijving(omschrijving);

        builder.addGroepDocument(voorkomen.getInhoud(), inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.SOORT_DOCUMENT, soortDocumentCode.getNaam());
        assertContains(ggoInhoud, GgoBrpElementEnum.IDENTIFICATIE, identificatie);
        assertContains(ggoInhoud, GgoBrpElementEnum.AKTENUMMER, aktenummer);
        assertContains(ggoInhoud, GgoBrpElementEnum.OMSCHRIJVING, omschrijving);
        assertContains(ggoInhoud, GgoBrpElementEnum.PARTIJ, "0011 (tegen de Anderen)");
    }

    @Test
    public void testAddGroepDocumentEmpty() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        builder.addGroepDocument(voorkomen.getInhoud(), null, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.SOORT_DOCUMENT);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.IDENTIFICATIE);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.AKTENUMMER);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.OMSCHRIJVING);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.PARTIJ);
    }

    @Test
    public void testAddGroepOuderlijkGezagFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final BetrokkenheidOuderlijkGezagHistorie inhoud = new BetrokkenheidOuderlijkGezagHistorie(betrokkenheid, false);

        builder.addGroepOuderlijkGezag(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.OUDER_HEEFT_GEZAG, "Nee");
    }

    @Test
    public void testAddGroepOuderlijkGezagEmpty() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        builder.addGroepOuderlijkGezag(voorkomen, null, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.OUDER_HEEFT_GEZAG);
    }

    @Test
    public void testAddGroepOuderFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final BetrokkenheidOuderHistorie inhoud = new BetrokkenheidOuderHistorie(betrokkenheid, true);
        inhoud.setIndicatieOuderUitWieKindIsGeboren(false);

        builder.addGroepOuder(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.INDICATIE_OUDER, "Ja");
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

        builder.addHistorie(voorkomen, brpHistorie, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(
            ggoInhoud,
            GgoBrpElementEnum.DATUM_GELDIGHEID,
            ViewerDateUtil.formatDatum(datumAanvangGeldigheid) + " - " + ViewerDateUtil.formatDatum(datumEindeGeldigheid));
        assertContains(ggoInhoud, GgoBrpElementEnum.DATUM_TIJD_REGISTRATIE_VERVAL, ViewerDateUtil.formatDatumTijdUtc(datumTijdRegistratie)
                                                                                   + " - "
                                                                                   + ViewerDateUtil.formatDatumTijdUtc(datumTijdVerval));
    }

    @Test
    public void testAddHistorieEmptyAll() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        builder.addHistorie(voorkomen, null, null);
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

        builder.addHistorie(voorkomen, brpHistorie, null);
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
