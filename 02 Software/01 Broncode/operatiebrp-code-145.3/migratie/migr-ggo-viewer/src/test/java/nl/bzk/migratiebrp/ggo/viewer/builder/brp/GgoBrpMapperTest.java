/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderlijkGezagHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonDeelnameEuVerkiezingenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonInschrijvingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonMigratieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNaamgebruikHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNummerverwijzingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonOverlijdenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonPersoonskaartHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocumentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonUitsluitingKiesrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerblijfsrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.service.BrpStamtabelService;
import nl.bzk.migratiebrp.ggo.viewer.util.PortInitializer;
import nl.bzk.migratiebrp.ggo.viewer.util.ViewerDateUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Map;

import javax.inject.Inject;

/**
 * Test de GgoBrp3Builder klasse
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = {"classpath:test-viewer-beans.xml"}, initializers = {PortInitializer.class})
public class GgoBrpMapperTest {

    @Inject
    private GgoBrpGegevensgroepenBuilder gegevensgroepenBuilder;
    @Inject
    private GgoBrpActieBuilder actieBuilder;
    @Inject
    private GgoBrpOnderzoekBuilder onderzoekBuilder;
    @Inject
    private GgoBrpValueConvert valueConvert;
    @Inject
    private BrpStamtabelService brpStamtabelService;

    private GgoBrpIdentificatienummersMapper identificatienummersMapper;
    private GgoBrpVoornaamMapper voornaamMapper;
    private GgoBrpGeslachtsnaamcomponentMapper geslachtsnaamcomponentMapper;
    private GgoBrpSamengesteldeNaamMapper samengesteldeNaamMapper;
    private GgoBrpGeboorteMapper geboorteMapper;
    private GgoBrpGeslachtsaanduidingMapper geslachtsaanduidingMapper;
    private GgoBrpNaamgebruikMapper naamgebruikMapper;
    private GgoBrpNummerverwijzingMapper nummerverwijzingMapper;
    private GgoBrpNationaliteitMapper nationaliteitMapper;
    private GgoBrpOverlijdenMapper overlijdenMapper;
    private GgoBrpInschrijvingMapper inschrijvingMapper;
    private GgoBrpPersoonskaartMapper persoonskaartMapper;
    private GgoBrpBijhoudingMapper bijhoudingMapper;
    private GgoBrpAdresMapper adresMapper;
    private GgoBrpMigratieMapper migratieMapper;
    private GgoBrpVerblijfsrechtMapper verblijfsrechtMapper;
    private GgoBrpReisdocumentMapper reisdocumentMapper;
    private GgoBrpDeelnameEuVerkiezingenMapper deelnameEuVerkiezingenMapper;
    private GgoBrpUitsluitingKiesrechtMapper uitsluitingKiesrechtMapper;

    private Persoon persoon;
    private Partij partij;
    private Gemeente gemeente;

    @Before
    public void setUp() {
        persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        partij = new Partij("Gemeente Onderijssel", "000013");
        gemeente = new Gemeente((short) 11, "Onderijssel", "0012", partij);

        identificatienummersMapper = new GgoBrpIdentificatienummersMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert);
        voornaamMapper = new GgoBrpVoornaamMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert);
        geslachtsnaamcomponentMapper = new GgoBrpGeslachtsnaamcomponentMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert);
        samengesteldeNaamMapper = new GgoBrpSamengesteldeNaamMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert);
        geboorteMapper = new GgoBrpGeboorteMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert);
        geslachtsaanduidingMapper = new GgoBrpGeslachtsaanduidingMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert);
        naamgebruikMapper = new GgoBrpNaamgebruikMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert);
        nummerverwijzingMapper = new GgoBrpNummerverwijzingMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert);
        nationaliteitMapper = new GgoBrpNationaliteitMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert);
        overlijdenMapper = new GgoBrpOverlijdenMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert);
        inschrijvingMapper = new GgoBrpInschrijvingMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert);
        persoonskaartMapper = new GgoBrpPersoonskaartMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert);
        bijhoudingMapper = new GgoBrpBijhoudingMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert);
        adresMapper = new GgoBrpAdresMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert);
        migratieMapper = new GgoBrpMigratieMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert);
        verblijfsrechtMapper = new GgoBrpVerblijfsrechtMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert);
        reisdocumentMapper = new GgoBrpReisdocumentMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert);
        deelnameEuVerkiezingenMapper = new GgoBrpDeelnameEuVerkiezingenMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert);
        uitsluitingKiesrechtMapper = new GgoBrpUitsluitingKiesrechtMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert);
    }

    @Test
    public void testAddGroepNaamgebruikNaamFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final String geslachtsnaamstam = "Balkie Balkie";
        final String voornamen = "Jan Peter";
        final String voorvoegsel = "van der";
        final Character scheidingsteken = ' ';

        final PersoonNaamgebruikHistorie inhoud = new PersoonNaamgebruikHistorie(persoon, geslachtsnaamstam, false, Naamgebruik.PARTNER_NA_EIGEN);
        inhoud.setVoornamenNaamgebruik(voornamen);
        inhoud.setVoorvoegselNaamgebruik(voorvoegsel);
        inhoud.setScheidingstekenNaamgebruik(scheidingsteken);
        inhoud.setPredicaat(Predicaat.J);
        inhoud.setAdellijkeTitel(AdellijkeTitel.B);
        inhoud.setNaamgebruik(Naamgebruik.PARTNER_NA_EIGEN);
        inhoud.setIndicatieNaamgebruikAfgeleid(true);

        naamgebruikMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.VOORNAMEN, voornamen);
        assertContains(ggoInhoud, GgoBrpElementEnum.VOORVOEGSEL, voorvoegsel);
        assertContains(ggoInhoud, GgoBrpElementEnum.SCHEIDINGSTEKEN, scheidingsteken.toString());
        assertContains(ggoInhoud, GgoBrpElementEnum.GESLACHTSNAAMSTAM, geslachtsnaamstam);
        assertContains(ggoInhoud, GgoBrpElementEnum.NAAMGEBRUIK, "N (Eigen, partner)");
        assertContains(ggoInhoud, GgoBrpElementEnum.INDICATIE_AFGELEID, "Ja");
        assertContains(ggoInhoud, GgoBrpElementEnum.PREDICAAT, "J (jonkheer / jonkvrouw)");
        assertContains(ggoInhoud, GgoBrpElementEnum.ADELLIJKE_TITEL, "B (baron / barones)");
    }

    @Test
    public void testAddGroepNaamgebruikNaamEmpty() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final String geslachtsnaamstam = "Balkie Balkie";

        final PersoonNaamgebruikHistorie inhoud = new PersoonNaamgebruikHistorie(persoon, geslachtsnaamstam, false, Naamgebruik.PARTNER_NA_EIGEN);

        naamgebruikMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.VOORNAMEN);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.VOORVOEGSEL);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.SCHEIDINGSTEKEN);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.PREDICAAT);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.ADELLIJKE_TITEL);
    }

    @Test
    public void testAddGroepAdresHoudingFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final SoortAdres functieAdresCode = SoortAdres.BRIEFADRES;
        final RedenWijzigingVerblijf redenWijzigingAdresCode = new RedenWijzigingVerblijf('Z', "Zomaar");
        final Aangever aangeverAdreshoudingCode = new Aangever('H', "Hij daar", "Ja hij");
        final Integer datumAanvangAdreshouding = 20010101;
        final String adresseerbaarObject = "3432432";
        final String identificatiecodeNummeraanduiding = "423423";
        final String naamOpenbareRuimte = "Openbare ruimte";
        final String afgekorteNaamOpenbareRuimte = "Openb. ruimte";
        final String gemeentedeel = "Gemeente deel";
        final Integer huisnummer = 12;
        final Character huisletter = 'A';
        final String huisnummertoevoeging = "b";
        final String postcode = "2286GH";
        final String woonplaatsnaam = "Woonplaatsnaam";
        final String locatieTovAdres = "TO";
        final String locatieOmschrijving = "Locatie omschrijving";
        final String buitenlandsAdresRegel1 = "regel1";
        final String buitenlandsAdresRegel2 = "regel2";
        final String buitenlandsAdresRegel3 = "regel3";
        final String buitenlandsAdresRegel4 = "regel4";
        final String buitenlandsAdresRegel5 = "regel5";
        final String buitenlandsAdresRegel6 = "regel6";

        final LandOfGebied landOfGebied = new LandOfGebied("0085", "Hawaiistan");

        final PersoonAdresHistorie inhoud = new PersoonAdresHistorie(new PersoonAdres(persoon), functieAdresCode, landOfGebied, redenWijzigingAdresCode);
        inhoud.setAangeverAdreshouding(aangeverAdreshoudingCode);
        inhoud.setDatumAanvangAdreshouding(datumAanvangAdreshouding);
        inhoud.setIdentificatiecodeNummeraanduiding(identificatiecodeNummeraanduiding);
        inhoud.setIdentificatiecodeAdresseerbaarObject(adresseerbaarObject);
        inhoud.setNaamOpenbareRuimte(naamOpenbareRuimte);
        inhoud.setAfgekorteNaamOpenbareRuimte(afgekorteNaamOpenbareRuimte);
        inhoud.setGemeente(gemeente);
        inhoud.setGemeentedeel(gemeentedeel);
        inhoud.setHuisnummer(huisnummer);
        inhoud.setHuisnummertoevoeging(huisnummertoevoeging);
        inhoud.setHuisletter(huisletter);
        inhoud.setPostcode(postcode);
        inhoud.setWoonplaatsnaam(woonplaatsnaam);
        inhoud.setLocatietovAdres(locatieTovAdres);
        inhoud.setLocatieOmschrijving(locatieOmschrijving);
        inhoud.setBuitenlandsAdresRegel1(buitenlandsAdresRegel1);
        inhoud.setBuitenlandsAdresRegel2(buitenlandsAdresRegel2);
        inhoud.setBuitenlandsAdresRegel3(buitenlandsAdresRegel3);
        inhoud.setBuitenlandsAdresRegel4(buitenlandsAdresRegel4);
        inhoud.setBuitenlandsAdresRegel5(buitenlandsAdresRegel5);
        inhoud.setBuitenlandsAdresRegel6(buitenlandsAdresRegel6);
        inhoud.setLandOfGebied(landOfGebied);

        adresMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.SOORT_ADRES, "B (Briefadres)");
        assertContains(ggoInhoud, GgoBrpElementEnum.REDEN_WIJZIGING_ADRES, "Z (Zomaar)");
        assertContains(ggoInhoud, GgoBrpElementEnum.AANGEVER_ADRESHOUDING, "H (Hij daar)");
        assertContains(ggoInhoud, GgoBrpElementEnum.DATUM_AANVANG_ADRESHOUDING, ViewerDateUtil.formatDatum(datumAanvangAdreshouding));
        assertContains(ggoInhoud, GgoBrpElementEnum.IDENTIFICATIECODE_ADRESSEERBAAR_OBJECT, adresseerbaarObject);
        assertContains(ggoInhoud, GgoBrpElementEnum.IDENTIFICATIECODE_NUMMERAANDUIDING, identificatiecodeNummeraanduiding);
        assertContains(ggoInhoud, GgoBrpElementEnum.GEMEENTE, "0012 (Onderijssel)");
        assertContains(ggoInhoud, GgoBrpElementEnum.NAAM_OPENBARE_RUIMTE, naamOpenbareRuimte);
        assertContains(ggoInhoud, GgoBrpElementEnum.AFGEKORTE_NAAM_OPENBARE_RUIMTE, afgekorteNaamOpenbareRuimte);
        assertContains(ggoInhoud, GgoBrpElementEnum.GEMEENTEDEEL, gemeentedeel);
        assertContains(ggoInhoud, GgoBrpElementEnum.HUISNUMMER, String.valueOf(huisnummer));
        assertContains(ggoInhoud, GgoBrpElementEnum.HUISLETTER, String.valueOf(huisletter));
        assertContains(ggoInhoud, GgoBrpElementEnum.HUISNUMMERTOEVOEGING, huisnummertoevoeging);
        assertContains(ggoInhoud, GgoBrpElementEnum.POSTCODE, postcode);
        assertContains(ggoInhoud, GgoBrpElementEnum.WOONPLAATS, woonplaatsnaam);
        assertContains(ggoInhoud, GgoBrpElementEnum.LOCATIE_TOV_ADRES, locatieTovAdres);
        assertContains(ggoInhoud, GgoBrpElementEnum.LOCATIE_OMSCHRIJVING, locatieOmschrijving);
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_1, buitenlandsAdresRegel1);
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_2, buitenlandsAdresRegel2);
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_3, buitenlandsAdresRegel3);
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_4, buitenlandsAdresRegel4);
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_5, buitenlandsAdresRegel5);
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_6, buitenlandsAdresRegel6);
        assertContains(ggoInhoud, GgoBrpElementEnum.LAND_OF_GEBIED, "0085 (Hawaiistan)");
    }

    @Test
    public void testAddGroepAdresHoudingEmpty() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final LandOfGebied landOfGebied = new LandOfGebied("0085", "Hawaiistan");

        final PersoonAdresHistorie inhoud =
                new PersoonAdresHistorie(new PersoonAdres(persoon), SoortAdres.BRIEFADRES, landOfGebied, new RedenWijzigingVerblijf('W', "Wijziging"));

        adresMapper.verwerkInhoud(voorkomen, inhoud, null);

        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.AANGEVER_ADRESHOUDING);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.DATUM_AANVANG_ADRESHOUDING);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.IDENTIFICATIECODE_ADRESSEERBAAR_OBJECT);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.IDENTIFICATIECODE_NUMMERAANDUIDING);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.GEMEENTE);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.NAAM_OPENBARE_RUIMTE);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.AFGEKORTE_NAAM_OPENBARE_RUIMTE);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.GEMEENTEDEEL);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.HUISNUMMER);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.HUISLETTER);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.HUISNUMMERTOEVOEGING);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.POSTCODE);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.WOONPLAATS);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.LOCATIE_TOV_ADRES);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.LOCATIE_OMSCHRIJVING);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_1);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_2);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_3);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_4);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_5);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_6);
    }

    @Test
    public void testAddGroepBijhoudingFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final Bijhoudingsaard bijhoudingsaard = Bijhoudingsaard.NIET_INGEZETENE;
        final NadereBijhoudingsaard nadereBijhoudingsaard = NadereBijhoudingsaard.EMIGRATIE;

        final PersoonBijhoudingHistorie inhoud = new PersoonBijhoudingHistorie(persoon, partij, bijhoudingsaard, nadereBijhoudingsaard);
        bijhoudingMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.BIJHOUDINGSPARTIJ, "000013 (Gemeente Onderijssel)");
        assertContains(ggoInhoud, GgoBrpElementEnum.BIJHOUDINGSAARD, "N (Niet-ingezetene)");
        assertContains(ggoInhoud, GgoBrpElementEnum.NADERE_BIJHOUDINGSAARD, "E (Emigratie)");
    }

    @Test
    public void testAddGroepDeelnameEuVerkiezingenFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final Integer datumAanleidingAanpassingDeelnameEuVerkiezingen = 20101212;
        final Integer datumVoorzienEindeUitsluitingEuVerkiezingen = 20110101;

        final PersoonDeelnameEuVerkiezingenHistorie inhoud = new PersoonDeelnameEuVerkiezingenHistorie(persoon, false);
        inhoud.setDatumAanleidingAanpassingDeelnameEuVerkiezingen(20101212);
        inhoud.setDatumVoorzienEindeUitsluitingEuVerkiezingen(20110101);

        deelnameEuVerkiezingenMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.INDICATIE_DEELNAME_EU_VERKIEZINGEN, "Nee");
        assertContains(
                ggoInhoud,
                GgoBrpElementEnum.DATUM_AANL_AANP_DEELNAME_EU_VERKIEZINGEN,
                ViewerDateUtil.formatDatum(datumAanleidingAanpassingDeelnameEuVerkiezingen));
        assertContains(
                ggoInhoud,
                GgoBrpElementEnum.DATUM_VOORZIEN_EINDE_UITSLUITING_EU_VERKIEZINGEN,
                ViewerDateUtil.formatDatum(datumVoorzienEindeUitsluitingEuVerkiezingen));
    }

    @Test
    public void testAddGroepDeelnameEuVerkiezingenEmpty() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final PersoonDeelnameEuVerkiezingenHistorie inhoud = new PersoonDeelnameEuVerkiezingenHistorie(persoon, false);

        deelnameEuVerkiezingenMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.DATUM_AANL_AANP_DEELNAME_EU_VERKIEZINGEN);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.DATUM_VOORZIEN_EINDE_UITSLUITING_EU_VERKIEZINGEN);
    }

    @Test
    public void testAddGroepGeboorteFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final Integer geboortedatum = 19800101;
        final String woonplaatsnaam = "woonplaatsnaam";
        final String buitenlandsePlaatsGeboorte = "buitenlandse plaats";
        final String buitenlandseRegioGeboorte = "buitenlandse regio";
        final LandOfGebied landOfGebied = new LandOfGebied("6030", "Japal");
        final String omschrijvingGeboortelocatie = "omschrijving locatie";

        final PersoonGeboorteHistorie inhoud = new PersoonGeboorteHistorie(persoon, geboortedatum, landOfGebied);
        inhoud.setGemeente(gemeente);
        inhoud.setWoonplaatsnaamGeboorte(woonplaatsnaam);
        inhoud.setBuitenlandsePlaatsGeboorte(buitenlandsePlaatsGeboorte);
        inhoud.setBuitenlandseRegioGeboorte(buitenlandseRegioGeboorte);
        inhoud.setOmschrijvingGeboortelocatie(omschrijvingGeboortelocatie);

        geboorteMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.DATUM_GEBOORTE, ViewerDateUtil.formatDatum(geboortedatum));
        assertContains(ggoInhoud, GgoBrpElementEnum.GEMEENTE_GEBOORTE, "0012 (Onderijssel)");
        assertContains(ggoInhoud, GgoBrpElementEnum.WOONPLAATSNAAM_GEBOORTE, woonplaatsnaam);
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDSE_PLAATS_GEBOORTE, buitenlandsePlaatsGeboorte);
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDSE_REGIO_GEBOORTE, buitenlandseRegioGeboorte);
        assertContains(ggoInhoud, GgoBrpElementEnum.LAND_OF_GEBIED_GEBOORTE, "6030 (Japal)");
        assertContains(ggoInhoud, GgoBrpElementEnum.OMSCHRIJVING_GEBOORTELOCATIE, omschrijvingGeboortelocatie);
    }

    @Test
    public void testAddGroepGeboorteEmpty() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final Integer geboortedatum = 19800101;
        final LandOfGebied landOfGebied = new LandOfGebied("6030", "Japal");

        final PersoonGeboorteHistorie inhoud = new PersoonGeboorteHistorie(persoon, geboortedatum, landOfGebied);

        geboorteMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.GEMEENTE_GEBOORTE);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.WOONPLAATSNAAM_GEBOORTE);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDSE_PLAATS_GEBOORTE);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDSE_REGIO_GEBOORTE);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.OMSCHRIJVING_GEBOORTELOCATIE);
    }

    @Test
    public void testAddGroepGeslachtsaanduidingFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final PersoonGeslachtsaanduidingHistorie inhoud = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.VROUW);
        geslachtsaanduidingMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.GESLACHTSAANDUIDING, "V (Vrouw)");
    }

    @Test
    public void testAddGroepGeslachtsnaamFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final String voorvoegsel = "van der";
        final Character scheidingsteken = ' ';
        final String stam = "Test";
        final BrpPredicaatCode predicaatCode = new BrpPredicaatCode("K");
        final BrpAdellijkeTitelCode adellijkeTitelCode = new BrpAdellijkeTitelCode("P");
        final int volgnummer = 0;

        final PersoonGeslachtsnaamcomponent component = new PersoonGeslachtsnaamcomponent(persoon, volgnummer);
        final PersoonGeslachtsnaamcomponentHistorie inhoud = new PersoonGeslachtsnaamcomponentHistorie(component, stam);
        inhoud.setScheidingsteken(scheidingsteken);
        inhoud.setVoorvoegsel(voorvoegsel);
        inhoud.setPredicaat(Predicaat.K);
        inhoud.setAdellijkeTitel(AdellijkeTitel.P);

        geslachtsnaamcomponentMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.VOORVOEGSEL, voorvoegsel);
        assertContains(ggoInhoud, GgoBrpElementEnum.SCHEIDINGSTEKEN, String.valueOf(scheidingsteken));
        assertContains(ggoInhoud, GgoBrpElementEnum.STAM, stam);
        assertContains(ggoInhoud, GgoBrpElementEnum.PREDICAAT, brpStamtabelService.getPredicaat(predicaatCode.getWaarde()));
        assertContains(ggoInhoud, GgoBrpElementEnum.ADELLIJKE_TITEL, brpStamtabelService.getAdellijkeTitel(adellijkeTitelCode.getWaarde()));
        assertContains(ggoInhoud, GgoBrpElementEnum.VOLGNUMMER, String.valueOf(volgnummer));
    }

    @Test
    public void testAddGroepGeslachtsnaamEmpty() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final String stam = "Stam mag niet leeg zijn";

        final PersoonGeslachtsnaamcomponent component = new PersoonGeslachtsnaamcomponent(persoon, 0);
        final PersoonGeslachtsnaamcomponentHistorie inhoud = new PersoonGeslachtsnaamcomponentHistorie(component, stam);

        geslachtsnaamcomponentMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.VOORVOEGSEL);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.SCHEIDINGSTEKEN);
        assertContains(ggoInhoud, GgoBrpElementEnum.STAM, stam);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.PREDICAAT);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.ADELLIJKE_TITEL);
    }

    @Test
    public void testAddGroepIdentificatienummersFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final String administratienummer = "1234356546";
        final String burgerservicenummer = "124432534";

        final PersoonIDHistorie inhoud = new PersoonIDHistorie(persoon);
        inhoud.setAdministratienummer(administratienummer);
        inhoud.setBurgerservicenummer(burgerservicenummer);

        identificatienummersMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.ADMINISTRATIENUMMER, String.valueOf(administratienummer));
        assertContains(ggoInhoud, GgoBrpElementEnum.BURGERSERVICENUMMER, String.valueOf(burgerservicenummer));
    }

    @Test
    public void testAddGroepIdentificatienummersEmpty() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final PersoonIDHistorie inhoud = new PersoonIDHistorie(persoon);

        identificatienummersMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.ADMINISTRATIENUMMER);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BURGERSERVICENUMMER);
    }

    @Test
    public void testAddGroepMigratieFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final LandOfGebied landVanwaarIngeschreven = new LandOfGebied("5005", "Nepan");
        final SoortMigratie soortMigratie = SoortMigratie.IMMIGRATIE;
        final RedenWijzigingVerblijf redenWijzigingAdresCode = new RedenWijzigingVerblijf('X', "Iks");
        final Aangever aangeverAdreshoudingCode = new Aangever('P', "Piet", "Een dik, klein mannetje van middelbare leeftijd, licht kalend");
        final String buitenlandsAdresRegel1 = "regel1";
        final String buitenlandsAdresRegel2 = "regel2";
        final String buitenlandsAdresRegel3 = "regel3";
        final String buitenlandsAdresRegel4 = "regel4";
        final String buitenlandsAdresRegel5 = "regel5";
        final String buitenlandsAdresRegel6 = "regel6";

        final PersoonMigratieHistorie inhoud = new PersoonMigratieHistorie(persoon, soortMigratie);
        inhoud.setLandOfGebied(landVanwaarIngeschreven);
        inhoud.setRedenWijzigingMigratie(redenWijzigingAdresCode);
        inhoud.setAangeverMigratie(aangeverAdreshoudingCode);
        inhoud.setBuitenlandsAdresRegel1(buitenlandsAdresRegel1);
        inhoud.setBuitenlandsAdresRegel2(buitenlandsAdresRegel2);
        inhoud.setBuitenlandsAdresRegel3(buitenlandsAdresRegel3);
        inhoud.setBuitenlandsAdresRegel4(buitenlandsAdresRegel4);
        inhoud.setBuitenlandsAdresRegel5(buitenlandsAdresRegel5);
        inhoud.setBuitenlandsAdresRegel6(buitenlandsAdresRegel6);

        migratieMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.LAND_OF_GEBIED_MIGRATIE, "5005 (Nepan)");
        assertContains(ggoInhoud, GgoBrpElementEnum.SOORT_MIGRATIE, "I (Immigratie)");
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_1, buitenlandsAdresRegel1);
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_2, buitenlandsAdresRegel2);
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_3, buitenlandsAdresRegel3);
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_4, buitenlandsAdresRegel4);
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_5, buitenlandsAdresRegel5);
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_6, buitenlandsAdresRegel6);
    }

    @Test
    public void testAddGroepMigratieEmpty() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final PersoonMigratieHistorie inhoud = new PersoonMigratieHistorie(persoon, SoortMigratie.IMMIGRATIE);

        migratieMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.LAND_OF_GEBIED);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_1);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_2);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_3);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_4);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_5);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_6);
    }

    @Test
    public void testAddGroepInschrijvingFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final Integer datumInschrijving = 19080101;
        final Timestamp datumtijdstempel = new Timestamp(19080101);
        final Long versienummer = 4L;

        final PersoonInschrijvingHistorie inhoud = new PersoonInschrijvingHistorie(persoon, datumInschrijving, versienummer, datumtijdstempel);
        inschrijvingMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.DATUM_INSCHRIJVING, ViewerDateUtil.formatDatum(datumInschrijving));
        assertContains(ggoInhoud, GgoBrpElementEnum.VERSIENUMMER, "0004");
    }

    @Test
    public void testAddGroepNationaliteitFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final Nationaliteit nationaliteitCode = new Nationaliteit("Martiaanse", "0014");
        final RedenVerkrijgingNLNationaliteit redenVerkrijgingNederlandschapCode = new RedenVerkrijgingNLNationaliteit("002", "zomaar");
        final RedenVerliesNLNationaliteit redenVerliesNederlandschapCode = new RedenVerliesNLNationaliteit("004", "tsja");

        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoon, nationaliteitCode);
        final PersoonNationaliteitHistorie inhoud = new PersoonNationaliteitHistorie(nationaliteit);
        inhoud.setRedenVerkrijgingNLNationaliteit(redenVerkrijgingNederlandschapCode);
        inhoud.setRedenVerliesNLNationaliteit(redenVerliesNederlandschapCode);

        nationaliteitMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.NATIONALITEIT, "0014 (Martiaanse)");
        assertContains(ggoInhoud, GgoBrpElementEnum.REDEN_VERKRIJGING_NEDERLANDSCHAP, "002 (zomaar)");
        assertContains(ggoInhoud, GgoBrpElementEnum.REDEN_VERLIES_NEDERLANDSCHAP, "004 (tsja)");
    }

    @Test
    public void testAddGroepNationaliteitEmpty() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoon, new Nationaliteit("Kazachstaanse", "0013"));
        final PersoonNationaliteitHistorie inhoud = new PersoonNationaliteitHistorie(nationaliteit);

        nationaliteitMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.REDEN_VERKRIJGING_NEDERLANDSCHAP);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.REDEN_VERLIES_NEDERLANDSCHAP);
    }

    @Test
    public void testAddGroepNummerverwijzingFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final String vorigeAdministratienummer = "3213324325";
        final String volgendeAdministratienummer = "3243253543";
        final String vorigeBurgerservicenummer = "123456789";
        final String volgendeBurgerservicenummer = "987654321";

        final PersoonNummerverwijzingHistorie inhoud = new PersoonNummerverwijzingHistorie(persoon);
        inhoud.setVorigeAdministratienummer(vorigeAdministratienummer);
        inhoud.setVolgendeAdministratienummer(volgendeAdministratienummer);
        inhoud.setVorigeBurgerservicenummer(vorigeBurgerservicenummer);
        inhoud.setVolgendeBurgerservicenummer(volgendeBurgerservicenummer);

        nummerverwijzingMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.VORIG_ADMINISTRATIENUMMER, String.valueOf(vorigeAdministratienummer));
        assertContains(ggoInhoud, GgoBrpElementEnum.VOLGEND_ADMINISTRATIENUMMER, String.valueOf(volgendeAdministratienummer));
        assertContains(ggoInhoud, GgoBrpElementEnum.VORIG_BURGERSERVICENUMMER, String.valueOf(vorigeBurgerservicenummer));
        assertContains(ggoInhoud, GgoBrpElementEnum.VOLGEND_BURGERSERVICENUMMER, String.valueOf(volgendeBurgerservicenummer));
    }

    @Test
    public void testAddGroepNummerverwijzingEmpty() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final PersoonNummerverwijzingHistorie inhoud = new PersoonNummerverwijzingHistorie(persoon);

        nummerverwijzingMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.VORIG_ADMINISTRATIENUMMER);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.VOLGEND_ADMINISTRATIENUMMER);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.VORIG_BURGERSERVICENUMMER);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.VOLGEND_BURGERSERVICENUMMER);
    }

    @Test
    public void testAddGroepOverlijdenFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final Integer datum = 20201212;
        final String woonplaatsnaam = "woonplaatsnaam2";
        final String buitenlandsePlaats = "ergens in het buitenland";
        final String buitenlandseRegio = "daaro";
        final LandOfGebied landOfGebiedCode = new LandOfGebied("0012", "Dameenjeniestan");
        final String omschrijvingLocatie = "locatie";

        final PersoonOverlijdenHistorie inhoud = new PersoonOverlijdenHistorie(persoon, datum, landOfGebiedCode);
        inhoud.setGemeente(gemeente);
        inhoud.setWoonplaatsnaamOverlijden(woonplaatsnaam);
        inhoud.setBuitenlandsePlaatsOverlijden(buitenlandsePlaats);
        inhoud.setBuitenlandseRegioOverlijden(buitenlandseRegio);
        inhoud.setOmschrijvingLocatieOverlijden(omschrijvingLocatie);

        overlijdenMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.DATUM_OVERLIJDEN, ViewerDateUtil.formatDatum(datum));
        assertContains(ggoInhoud, GgoBrpElementEnum.GEMEENTE, "0012 (Onderijssel)");
        assertContains(ggoInhoud, GgoBrpElementEnum.WOONPLAATSNAAM_OVERLIJDEN, woonplaatsnaam);
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDSE_PLAATS, buitenlandsePlaats);
        assertContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDSE_REGIO, buitenlandseRegio);
        assertContains(ggoInhoud, GgoBrpElementEnum.LAND_OF_GEBIED, "0012 (Dameenjeniestan)");
        assertContains(ggoInhoud, GgoBrpElementEnum.OMSCHRIJVING_LOCATIE, omschrijvingLocatie);
    }

    @Test
    public void testAddGroepOverlijdenEmpty() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final LandOfGebied landOfGebiedCode = new LandOfGebied("0012", "Dameenjeniestan");
        final PersoonOverlijdenHistorie inhoud = new PersoonOverlijdenHistorie(persoon, 20201212, landOfGebiedCode);

        overlijdenMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.GEMEENTE);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.WOONPLAATSNAAM_OVERLIJDEN);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDSE_PLAATS);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.BUITENLANDSE_REGIO);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.OMSCHRIJVING_LOCATIE);
    }

    @Test
    public void testAddGroepPersoonskaartFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final PersoonPersoonskaartHistorie inhoud = new PersoonPersoonskaartHistorie(persoon, true);
        inhoud.setPartij(partij);

        persoonskaartMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.GEMEENTE_PK, "000013 (Gemeente Onderijssel)");
        assertContains(ggoInhoud, GgoBrpElementEnum.INDICATIE_PK_VOLLEDIG_GECONVERTEERD, "Ja");
    }

    @Test
    public void testAddGroepPersoonskaartEmpty() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final PersoonPersoonskaartHistorie inhoud = new PersoonPersoonskaartHistorie(persoon, true);

        persoonskaartMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.GEMEENTE_PK);
    }

    @Test
    public void testAddGroepReisdocumentFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final SoortNederlandsReisdocument soort = new SoortNederlandsReisdocument("X", "Iks");
        final String nummer = "213123";
        final Integer datumIngangDocument = 12012211;
        final Integer datumUitgifte = 19801011;
        final String autoriteitVanAfgifte = "Q";
        final Integer datumEindeDocument = 20120101;
        final Integer datumInhoudingOfVermissing = 19991212;
        final AanduidingInhoudingOfVermissingReisdocument aanduidingInhoudingOfVermissing =
                new AanduidingInhoudingOfVermissingReisdocument('V', "Vermist");

        final PersoonReisdocument reisdocument = new PersoonReisdocument(persoon, soort);
        final PersoonReisdocumentHistorie inhoud =
                new PersoonReisdocumentHistorie(12012211, 19801011, 20120101, nummer, autoriteitVanAfgifte, reisdocument);
        inhoud.setDatumInhoudingOfVermissing(datumInhoudingOfVermissing);
        inhoud.setAanduidingInhoudingOfVermissingReisdocument(aanduidingInhoudingOfVermissing);

        reisdocumentMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.SOORT, "X (Iks)");
        assertContains(ggoInhoud, GgoBrpElementEnum.NUMMER, nummer);
        assertContains(ggoInhoud, GgoBrpElementEnum.DATUM_INGANG_DOCUMENT, ViewerDateUtil.formatDatum(datumIngangDocument));
        assertContains(ggoInhoud, GgoBrpElementEnum.DATUM_UITGIFTE, ViewerDateUtil.formatDatum(datumUitgifte));
        assertContains(ggoInhoud, GgoBrpElementEnum.AUTORITEIT_VAN_AFGIFTE, autoriteitVanAfgifte);
        assertContains(ggoInhoud, GgoBrpElementEnum.DATUM_EINDE_DOCUMENT, ViewerDateUtil.formatDatum(datumEindeDocument));
        assertContains(ggoInhoud, GgoBrpElementEnum.DATUM_INHOUDING_OF_VERMISSING, ViewerDateUtil.formatDatum(datumInhoudingOfVermissing));
        assertContains(ggoInhoud, GgoBrpElementEnum.AANDUIDING_INHOUDING_OF_VERMISSING, "V (Vermist)");
    }

    @Test
    public void testAddGroepSamengesteldeNaamFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final Predicaat predicaatCode = Predicaat.K;
        final String voornamen = "voornaamA voornaamB";
        final String voorvoegsel = "voeg";
        final char scheidingsteken = '-';
        final AdellijkeTitel adellijkeTitelCode = AdellijkeTitel.R;
        final String geslachtsnaamstam = "achternaam";
        final boolean indicatieNamenreeks = false;
        final boolean indicatieAfgeleid = true;

        final PersoonSamengesteldeNaamHistorie inhoud =
                new PersoonSamengesteldeNaamHistorie(persoon, geslachtsnaamstam, indicatieAfgeleid, indicatieNamenreeks);
        inhoud.setPredicaat(predicaatCode);
        inhoud.setVoornamen(voornamen);
        inhoud.setAdellijkeTitel(adellijkeTitelCode);
        inhoud.setVoorvoegsel(voorvoegsel);
        inhoud.setScheidingsteken(scheidingsteken);

        samengesteldeNaamMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.PREDICAAT, "K (Zijne Koninklijke Hoogheid / Hare Koninklijke Hoogheid)");
        assertContains(ggoInhoud, GgoBrpElementEnum.VOORNAMEN, voornamen);
        assertContains(ggoInhoud, GgoBrpElementEnum.VOORVOEGSEL, voorvoegsel);
        assertContains(ggoInhoud, GgoBrpElementEnum.SCHEIDINGSTEKEN, "" + scheidingsteken);
        assertContains(ggoInhoud, GgoBrpElementEnum.ADELLIJKE_TITEL, "R (ridder / ridder)");
        assertContains(ggoInhoud, GgoBrpElementEnum.GESLACHTSNAAMSTAM, geslachtsnaamstam);
        assertContains(ggoInhoud, GgoBrpElementEnum.INDICATIE_NAMENREEKS, "Nee");
        assertContains(ggoInhoud, GgoBrpElementEnum.INDICATIE_AFGELEID, "Ja");
    }

    @Test
    public void testAddGroepSamengesteldeNaamEmpty() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final String geslachtsnaamstam = "achternaam";
        final boolean indicatieNamenreeks = false;
        final boolean indicatieAfgeleid = true;

        final PersoonSamengesteldeNaamHistorie inhoud =
                new PersoonSamengesteldeNaamHistorie(persoon, geslachtsnaamstam, indicatieAfgeleid, indicatieNamenreeks);

        samengesteldeNaamMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.PREDICAAT);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.VOORNAMEN);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.VOORVOEGSEL);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.SCHEIDINGSTEKEN);
        assertNotContains(ggoInhoud, GgoBrpElementEnum.ADELLIJKE_TITEL);
    }

    @Test
    public void testAddGroepUitsluitingKiesrechtFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final PersoonUitsluitingKiesrechtHistorie inhoud = new PersoonUitsluitingKiesrechtHistorie(persoon, true);
        inhoud.setDatumVoorzienEindeUitsluitingKiesrecht(0);

        uitsluitingKiesrechtMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.INDICATIE_UITSLUITING_KIESRECHT, "Ja");
        assertContains(ggoInhoud, GgoBrpElementEnum.DATUM_EINDE_UITSLUITING_KIESRECHT, ViewerDateUtil.formatDatum(0));
    }

    @Test
    public void testAddGroepUitsluitingKiesrechtEmpty() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final PersoonUitsluitingKiesrechtHistorie inhoud = new PersoonUitsluitingKiesrechtHistorie(persoon, true);

        uitsluitingKiesrechtMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.DATUM_EINDE_UITSLUITING_KIESRECHT);
    }

    @Test
    public void testAddGroepVerblijfsrechtFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final Verblijfsrecht verblijfsrechtCode = new Verblijfsrecht("01", "Verblijfsrecht 1");
        final Integer aanvangVerblijfsrecht = 20122010;
        final Integer mededelingVerblijfsrecht = 20122010;
        final Integer voorzienEindeVerblijfsrecht = 20130101;

        final PersoonVerblijfsrechtHistorie inhoud =
                new PersoonVerblijfsrechtHistorie(persoon, verblijfsrechtCode, aanvangVerblijfsrecht, mededelingVerblijfsrecht);
        inhoud.setDatumVoorzienEindeVerblijfsrecht(voorzienEindeVerblijfsrecht);

        verblijfsrechtMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.AANDUIDING_VERBLIJFSRECHT, verblijfsrechtCode.getOmschrijving());
        assertContains(ggoInhoud, GgoBrpElementEnum.DATUM_MEDEDELING_VERBLIJFSRECHT, ViewerDateUtil.formatDatum(aanvangVerblijfsrecht));
        assertContains(ggoInhoud, GgoBrpElementEnum.DATUM_VOORZIEN_EINDE_VERBLIJFSRECHT, ViewerDateUtil.formatDatum(voorzienEindeVerblijfsrecht));
    }

    @Test
    public void testAddGroepVerblijfsrechtEmpty() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final Verblijfsrecht verblijfsrechtCode = new Verblijfsrecht("01", "Verblijfsrecht 1");
        final PersoonVerblijfsrechtHistorie inhoud = new PersoonVerblijfsrechtHistorie(persoon, verblijfsrechtCode, 20122010, 20122010);

        verblijfsrechtMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertNotContains(ggoInhoud, GgoBrpElementEnum.DATUM_VOORZIEN_EINDE_VERBLIJFSRECHT);
    }

    @Test
    public void testAddGroepVoornaamFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final String voornaam = "pietje";
        final int volgnummer = 1;

        final PersoonVoornaam vnaam = new PersoonVoornaam(persoon, volgnummer);
        final PersoonVoornaamHistorie inhoud = new PersoonVoornaamHistorie(vnaam, voornaam);

        voornaamMapper.verwerkInhoud(voorkomen, inhoud, null);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.VOORNAAM, voornaam);
        assertContains(ggoInhoud, GgoBrpElementEnum.VOLGNUMMER, String.valueOf(volgnummer));
    }

    @Test
    public void testAddGroepOuderlijkGezagFilled() {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();

        final BetrokkenheidOuderlijkGezagHistorie inhoud =
                new BetrokkenheidOuderlijkGezagHistorie(
                        new Betrokkenheid(SoortBetrokkenheid.OUDER, new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING)),
                        false);

        gegevensgroepenBuilder.addGroepOuderlijkGezag(voorkomen, inhoud);
        final Map<String, String> ggoInhoud = voorkomen.getInhoud();

        assertContains(ggoInhoud, GgoBrpElementEnum.OUDER_HEEFT_GEZAG, "Nee");
    }

    private void assertContains(final Map<String, String> voorkomen, final GgoBrpElementEnum element, final String expected) {
        final String key = element.getLabel();
        Assert.assertTrue("Key: " + key + " komt niet voor!", voorkomen.containsKey(key));
        Assert.assertNotNull("Geen waarde bij key: " + key, voorkomen.get(key));
        Assert.assertEquals("Waarde is niet verwacht.", expected, voorkomen.get(key));
    }

    private void assertNotContains(final Map<String, String> voorkomen, final GgoBrpElementEnum element) {
        final String key = element.getLabel();
        Assert.assertTrue("Key: " + key + " komt voor, zou niet moeten.", !voorkomen.containsKey(key));
    }
}
