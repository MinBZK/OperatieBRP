/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.lo3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Character;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Huisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.util.VerwerkerUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.util.DBUnit;
import nl.bzk.migratiebrp.synchronisatie.dal.util.DBUnit.InsertBefore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * Test de GgoLo3Builder klasse
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DBUnit.TestExecutionListener.class, TransactionalTestExecutionListener.class })
@ContextConfiguration(locations = {"classpath:test-viewer-beans.xml" })
public class GgoLo3GegevensgroepenBuilderTest {

    @Inject
    private GgoLo3GegevensgroepenBuilder builder;
    @Inject
    private GgoLo3ValueConvert lo3ValueConvert;
    
    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep01Success() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3Long aNummer = Lo3Long.wrap(1653648353L);
        final Lo3Integer burgerServiceNummer = Lo3Integer.wrap(427389033);
        builder.addGroep01(voorkomen, aNummer, burgerServiceNummer);

        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_0110, String.valueOf(Lo3Long.unwrap(aNummer)));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_0120, String.valueOf(Lo3Integer.unwrap(burgerServiceNummer)));
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep01EmptySuccess() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        builder.addGroep01(voorkomen, null, null);

        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_0110);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_0120);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep02Success() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final String voornamen = "Voornaam";
        final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode = new Lo3AdellijkeTitelPredikaatCode("B");
        final String voorvoegselGeslachtsnaam = "van";
        final String geslachtsnaam = "Achternaam";
        builder.addGroep02(
            voorkomen,
            Lo3String.wrap(voornamen),
            adellijkeTitelPredikaatCode,
            Lo3String.wrap(voorvoegselGeslachtsnaam),
            Lo3String.wrap(geslachtsnaam));

        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_0210, voornamen);
        assertContains(
            voorkomen,
            Lo3ElementEnum.ELEMENT_0220,
            lo3ValueConvert.convertToViewerValue(adellijkeTitelPredikaatCode, Lo3ElementEnum.ELEMENT_0220));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_0230, voorvoegselGeslachtsnaam);
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_0240, geslachtsnaam);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep02EmptySuccess() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        builder.addGroep02(voorkomen, null, null, null, null);

        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_0210);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_0220);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_0230);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_0240);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep03Success() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3Datum geboortedatum = new Lo3Datum(20000101);
        final Lo3GemeenteCode geboorteGemeenteCode = new Lo3GemeenteCode("1904");
        final Lo3LandCode geboorteLandCode = new Lo3LandCode("5002");
        builder.addGroep03(voorkomen, geboortedatum, geboorteGemeenteCode, geboorteLandCode);

        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_0310, lo3ValueConvert.convertToViewerValue(geboortedatum, Lo3ElementEnum.ELEMENT_0310));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_0320, lo3ValueConvert.convertToViewerValue(geboorteGemeenteCode, Lo3ElementEnum.ELEMENT_0320));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_0330, lo3ValueConvert.convertToViewerValue(geboorteLandCode, Lo3ElementEnum.ELEMENT_0330));
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep03EmptySuccess() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        builder.addGroep03(voorkomen, null, null, null);

        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_0310);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_0320);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_0330);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep04Success() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3Geslachtsaanduiding geslachtsaanduiding = new Lo3Geslachtsaanduiding("M");
        builder.addGroep04(voorkomen, geslachtsaanduiding);

        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_0410, lo3ValueConvert.convertToViewerValue(geslachtsaanduiding, Lo3ElementEnum.ELEMENT_0410));
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep04EmptySuccess() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        builder.addGroep04(voorkomen, null);

        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_0410);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep06Success() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3Datum datumSluitingHuwelijkOfAangaanGp = new Lo3Datum(20000102);
        final Lo3GemeenteCode lo3GemeenteCode = new Lo3GemeenteCode("1904");
        final Lo3LandCode lo3LandCode = new Lo3LandCode("5002");
        builder.addGroep06(voorkomen, datumSluitingHuwelijkOfAangaanGp, lo3GemeenteCode, lo3LandCode);

        assertContains(
            voorkomen,
            Lo3ElementEnum.ELEMENT_0610,
            lo3ValueConvert.convertToViewerValue(datumSluitingHuwelijkOfAangaanGp, Lo3ElementEnum.ELEMENT_0610));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_0620, lo3ValueConvert.convertToViewerValue(lo3GemeenteCode, Lo3ElementEnum.ELEMENT_0620));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_0630, lo3ValueConvert.convertToViewerValue(lo3LandCode, Lo3ElementEnum.ELEMENT_0630));
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep06EmptySuccess() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        builder.addGroep06(voorkomen, null, null, null);

        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_0610);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_0620);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_0630);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep07Success() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3Datum datumOntbindingHuwelijkOfGp = new Lo3Datum(20000101);
        final Lo3GemeenteCode gemeenteCodeOntbindingHuwelijkOfGp = new Lo3GemeenteCode("1904");
        final Lo3LandCode landCodeOntbindingHuwelijkOfGp = new Lo3LandCode("5002");
        final Lo3RedenOntbindingHuwelijkOfGpCode redenOntbindingHuwelijkOfGpCode = new Lo3RedenOntbindingHuwelijkOfGpCode("A");
        builder.addGroep07(
            voorkomen,
            datumOntbindingHuwelijkOfGp,
            gemeenteCodeOntbindingHuwelijkOfGp,
            landCodeOntbindingHuwelijkOfGp,
            redenOntbindingHuwelijkOfGpCode);

        assertContains(
            voorkomen,
            Lo3ElementEnum.ELEMENT_0710,
            lo3ValueConvert.convertToViewerValue(datumOntbindingHuwelijkOfGp, Lo3ElementEnum.ELEMENT_0710));
        assertContains(
            voorkomen,
            Lo3ElementEnum.ELEMENT_0720,
            lo3ValueConvert.convertToViewerValue(gemeenteCodeOntbindingHuwelijkOfGp, Lo3ElementEnum.ELEMENT_0720));
        assertContains(
            voorkomen,
            Lo3ElementEnum.ELEMENT_0730,
            lo3ValueConvert.convertToViewerValue(landCodeOntbindingHuwelijkOfGp, Lo3ElementEnum.ELEMENT_0730));
        assertContains(
            voorkomen,
            Lo3ElementEnum.ELEMENT_0740,
            lo3ValueConvert.convertToViewerValue(redenOntbindingHuwelijkOfGpCode, Lo3ElementEnum.ELEMENT_0740));
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep07EmptySuccess() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        builder.addGroep07(voorkomen, null, null, null, null);

        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_0710);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_0720);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_0730);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_0740);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep09Success() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3GemeenteCode gemeenteInschrijving = new Lo3GemeenteCode("1904");
        final Lo3Datum datumInschrijving = new Lo3Datum(20000101);
        builder.addGroep09(voorkomen, gemeenteInschrijving, datumInschrijving);

        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_0910, lo3ValueConvert.convertToViewerValue(gemeenteInschrijving, Lo3ElementEnum.ELEMENT_0910));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_0920, lo3ValueConvert.convertToViewerValue(datumInschrijving, Lo3ElementEnum.ELEMENT_0920));
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep09EmptySuccess() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        builder.addGroep09(voorkomen, null, null);

        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_0910);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_0920);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep10Success() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3FunctieAdres functieAdres = new Lo3FunctieAdres("W");
        final Lo3String gemeenteDeel = Lo3String.wrap("Berkel");
        final Lo3Datum aanvangAdreshouding = new Lo3Datum(20000101);
        builder.addGroep10(voorkomen, functieAdres, gemeenteDeel, aanvangAdreshouding);

        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_1010, lo3ValueConvert.convertToViewerValue(functieAdres, Lo3ElementEnum.ELEMENT_1010));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_1020, Lo3String.unwrap(gemeenteDeel));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_1030, lo3ValueConvert.convertToViewerValue(aanvangAdreshouding, Lo3ElementEnum.ELEMENT_1030));
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep10EmptySuccess() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        builder.addGroep10(voorkomen, null, null, null);

        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1010);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1020);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1030);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep11Success() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3String straatnaam = Lo3String.wrap("Straat");
        final Lo3String naamOpenbareRuimte = Lo3String.wrap("Ruimte");
        final Lo3Huisnummer huisnummer = new Lo3Huisnummer(1);
        final Lo3Character huisletter = Lo3Character.wrap('A');
        final Lo3String huisnummertoevoeging = Lo3String.wrap("d");
        final Lo3AanduidingHuisnummer aanduidingHuisnummer = new Lo3AanduidingHuisnummer("to");
        final Lo3String postcode = Lo3String.wrap("2343AA");
        final Lo3String woonplaatsnaam = Lo3String.wrap("Plaats");
        final Lo3String identificatiecodeVerblijfplaats = Lo3String.wrap("0000000000000000");
        final Lo3String identificatiecodeNummeraanduiding = Lo3String.wrap("0000000000000000");

        final Lo3VerblijfplaatsInhoud inhoud =
                new Lo3VerblijfplaatsInhoud(
                    null,
                    null,
                    null,
                    null,
                    null,
                    straatnaam,
                    naamOpenbareRuimte,
                    huisnummer,
                    huisletter,
                    huisnummertoevoeging,
                    aanduidingHuisnummer,
                    postcode,
                    woonplaatsnaam,
                    identificatiecodeVerblijfplaats,
                    identificatiecodeNummeraanduiding,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);

        builder.addGroep11(voorkomen, inhoud);

        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_1110, lo3ValueConvert.convertToViewerValue(straatnaam, Lo3ElementEnum.ELEMENT_1110));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_1115, lo3ValueConvert.convertToViewerValue(naamOpenbareRuimte, Lo3ElementEnum.ELEMENT_1115));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_1120, String.valueOf(huisnummer.getIntegerWaarde()));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_1130, lo3ValueConvert.convertToViewerValue(huisletter, Lo3ElementEnum.ELEMENT_1130));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_1140, lo3ValueConvert.convertToViewerValue(huisnummertoevoeging, Lo3ElementEnum.ELEMENT_1140));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_1150, lo3ValueConvert.convertToViewerValue(aanduidingHuisnummer, Lo3ElementEnum.ELEMENT_1150));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_1160, lo3ValueConvert.convertToViewerValue(postcode, Lo3ElementEnum.ELEMENT_1160));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_1170, lo3ValueConvert.convertToViewerValue(woonplaatsnaam, Lo3ElementEnum.ELEMENT_1170));
        assertContains(
            voorkomen,
            Lo3ElementEnum.ELEMENT_1180,
            lo3ValueConvert.convertToViewerValue(identificatiecodeVerblijfplaats, Lo3ElementEnum.ELEMENT_1180));
        assertContains(
            voorkomen,
            Lo3ElementEnum.ELEMENT_1190,
            lo3ValueConvert.convertToViewerValue(identificatiecodeNummeraanduiding, Lo3ElementEnum.ELEMENT_1190));
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep11EmptySuccess() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3VerblijfplaatsInhoud inhoud =
                new Lo3VerblijfplaatsInhoud(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);

        builder.addGroep11(voorkomen, inhoud);

        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1110);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1115);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1120);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1130);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1140);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1150);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1160);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1170);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1180);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1190);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep13Success() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3LandCode landAdresBuitenland = new Lo3LandCode("5002");
        final Lo3Datum datumVertrekUitNederland = new Lo3Datum(20000101);
        final Lo3String adresBuitenland1 = Lo3String.wrap("adres1");
        final Lo3String adresBuitenland2 = Lo3String.wrap("adres2");
        final Lo3String adresBuitenland3 = Lo3String.wrap("adres3");
        builder.addGroep13(voorkomen, landAdresBuitenland, datumVertrekUitNederland, adresBuitenland1, adresBuitenland2, adresBuitenland3);

        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_1310, lo3ValueConvert.convertToViewerValue(landAdresBuitenland, Lo3ElementEnum.ELEMENT_1310));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_1320, lo3ValueConvert.convertToViewerValue(datumVertrekUitNederland, Lo3ElementEnum.ELEMENT_1320));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_1330, lo3ValueConvert.convertToViewerValue(adresBuitenland1, Lo3ElementEnum.ELEMENT_1330));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_1340, lo3ValueConvert.convertToViewerValue(adresBuitenland2, Lo3ElementEnum.ELEMENT_1340));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_1350, lo3ValueConvert.convertToViewerValue(adresBuitenland3, Lo3ElementEnum.ELEMENT_1350));
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep13EmptySuccess() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        builder.addGroep13(voorkomen, null, null, null, null, null);

        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1310);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1320);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1330);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1340);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1350);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep14Success() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3LandCode landVanwaarIngeschreven = new Lo3LandCode("6030");
        final Lo3Datum vestigingInNederland = new Lo3Datum(20000101);
        builder.addGroep14(voorkomen, landVanwaarIngeschreven, vestigingInNederland);

        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_1410, lo3ValueConvert.convertToViewerValue(landVanwaarIngeschreven, Lo3ElementEnum.ELEMENT_1410));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_1420, lo3ValueConvert.convertToViewerValue(vestigingInNederland, Lo3ElementEnum.ELEMENT_1420));
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep14EmptySuccess() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        builder.addGroep14(voorkomen, null, null);

        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1410);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_1420);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep35Success() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3SoortNederlandsReisdocument soortNederlandsReisdocument = new Lo3SoortNederlandsReisdocument("PN");
        final Lo3String nummerNederlandsReisdocument = Lo3String.wrap("nummernlr");
        final Lo3Datum datumUitgifteNederlandsReisdocument = new Lo3Datum(20000101);
        final Lo3AutoriteitVanAfgifteNederlandsReisdocument autoriteitVanAfgifteNederlandsReisdocument =
                new Lo3AutoriteitVanAfgifteNederlandsReisdocument("B0518");
        final Lo3Datum datumEindeGeldigheidNederlandsReisdocument = new Lo3Datum(20000102);
        final Lo3Datum datumInhoudingVermissingNederlandsReisdocument = new Lo3Datum(20000103);
        final Lo3AanduidingInhoudingVermissingNederlandsReisdocument aanduidingInhoudingNederlandsReisdocument =
                new Lo3AanduidingInhoudingVermissingNederlandsReisdocument("V");
        final Lo3ReisdocumentInhoud inhoud =
                new Lo3ReisdocumentInhoud(
                    soortNederlandsReisdocument,
                    nummerNederlandsReisdocument,
                    datumUitgifteNederlandsReisdocument,
                    autoriteitVanAfgifteNederlandsReisdocument,
                    datumEindeGeldigheidNederlandsReisdocument,
                    datumInhoudingVermissingNederlandsReisdocument,
                    aanduidingInhoudingNederlandsReisdocument,
                    null);
        builder.addGroep35(voorkomen, inhoud);

        assertContains(
            voorkomen,
            Lo3ElementEnum.ELEMENT_3510,
            lo3ValueConvert.convertToViewerValue(soortNederlandsReisdocument, Lo3ElementEnum.ELEMENT_3510));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_3520, Lo3String.unwrap(nummerNederlandsReisdocument));
        assertContains(
            voorkomen,
            Lo3ElementEnum.ELEMENT_3530,
            lo3ValueConvert.convertToViewerValue(datumUitgifteNederlandsReisdocument, Lo3ElementEnum.ELEMENT_3530));
        assertContains(
            voorkomen,
            Lo3ElementEnum.ELEMENT_3540,
            lo3ValueConvert.convertToViewerValue(autoriteitVanAfgifteNederlandsReisdocument, Lo3ElementEnum.ELEMENT_3540));
        assertContains(
            voorkomen,
            Lo3ElementEnum.ELEMENT_3550,
            lo3ValueConvert.convertToViewerValue(datumEindeGeldigheidNederlandsReisdocument, Lo3ElementEnum.ELEMENT_3550));
        assertContains(
            voorkomen,
            Lo3ElementEnum.ELEMENT_3560,
            lo3ValueConvert.convertToViewerValue(datumInhoudingVermissingNederlandsReisdocument, Lo3ElementEnum.ELEMENT_3560));
        assertContains(
            voorkomen,
            Lo3ElementEnum.ELEMENT_3570,
            lo3ValueConvert.convertToViewerValue(aanduidingInhoudingNederlandsReisdocument, Lo3ElementEnum.ELEMENT_3570));
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep35EmptySuccess() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3ReisdocumentInhoud inhoud = new Lo3ReisdocumentInhoud();
        builder.addGroep35(voorkomen, inhoud);

        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_3510);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_3520);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_3530);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_3540);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_3550);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_3560);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_3570);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_3580);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep67Success() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3Datum datumOpschortingBijhouding = new Lo3Datum(20000101);
        final Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode = new Lo3RedenOpschortingBijhoudingCode("O");
        builder.addGroep67(voorkomen, datumOpschortingBijhouding, redenOpschortingBijhoudingCode);

        assertContains(
            voorkomen,
            Lo3ElementEnum.ELEMENT_6710,
            lo3ValueConvert.convertToViewerValue(datumOpschortingBijhouding, Lo3ElementEnum.ELEMENT_6710));
        assertContains(
            voorkomen,
            Lo3ElementEnum.ELEMENT_6720,
            lo3ValueConvert.convertToViewerValue(redenOpschortingBijhoudingCode, Lo3ElementEnum.ELEMENT_6720));
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep67EmptySuccess() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        builder.addGroep67(voorkomen, null, null);

        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_6710);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_6720);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep71Success() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3Datum datumVerificatie = new Lo3Datum(20000101);
        final Lo3String omschrijvingVerificatie = Lo3String.wrap("Verificatie");
        builder.addGroep71(voorkomen, datumVerificatie, omschrijvingVerificatie);

        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_7110, lo3ValueConvert.convertToViewerValue(datumVerificatie, Lo3ElementEnum.ELEMENT_7110));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_7120, Lo3String.unwrap(omschrijvingVerificatie));
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep71EmptySuccess() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        builder.addGroep71(voorkomen, null, null);

        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_7110);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_7120);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep80Success() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3Integer versienummer = Lo3Integer.wrap(4);
        final Lo3Datumtijdstempel datumtijdstempel = new Lo3Datumtijdstempel(20110504114350000L);
        builder.addGroep80(voorkomen, versienummer, datumtijdstempel);

        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_8010, lo3ValueConvert.convertToViewerValue(versienummer, Lo3ElementEnum.ELEMENT_8010));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_8020, lo3ValueConvert.convertToViewerValue(datumtijdstempel, Lo3ElementEnum.ELEMENT_8020));
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep80EmptySuccess() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        builder.addGroep80(voorkomen, null, null);

        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_8010);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_8020);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep8182Success() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3GemeenteCode gemeenteAkte = new Lo3GemeenteCode("1904");
        final Lo3String nummerAkte = Lo3String.wrap("2");
        final Lo3GemeenteCode gemeenteDocument = new Lo3GemeenteCode("1904");
        final Lo3Datum datumDocument = new Lo3Datum(20000101);
        final Lo3String beschrijvingDocument = Lo3String.wrap("Beschrijving");
        final Lo3RNIDeelnemerCode rniDeelnemerCode = new Lo3RNIDeelnemerCode("0000");
        final Lo3String omsVerdrag = Lo3String.wrap("Verdrag");
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(1L, gemeenteAkte, nummerAkte, gemeenteDocument, datumDocument, beschrijvingDocument, rniDeelnemerCode, omsVerdrag);

        builder.addDocumentatie(voorkomen, documentatie);

        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_8110, lo3ValueConvert.convertToViewerValue(gemeenteAkte, Lo3ElementEnum.ELEMENT_8110));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_8120, Lo3String.unwrap(nummerAkte));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_8210, lo3ValueConvert.convertToViewerValue(gemeenteDocument, Lo3ElementEnum.ELEMENT_8210));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_8220, lo3ValueConvert.convertToViewerValue(datumDocument, Lo3ElementEnum.ELEMENT_8220));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_8230, Lo3String.unwrap(beschrijvingDocument));
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep8182EmptySuccess() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3Documentatie documentatie = new Lo3Documentatie(1L, null, null, null, null, null, null, null);

        builder.addDocumentatie(voorkomen, documentatie);

        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_8110);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_8120);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_8210);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_8220);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_8230);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_8810);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_8820);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep83Success() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3Integer aanduidingGegevensInOnderzoek = Lo3Integer.wrap(10410);
        final Lo3Datum datumIngangOnderzoek = new Lo3Datum(20000101);
        final Lo3Datum datumEindeOnderzoek = new Lo3Datum(20000102);
        final Lo3Onderzoek onderzoek = new Lo3Onderzoek(aanduidingGegevensInOnderzoek, datumIngangOnderzoek, datumEindeOnderzoek);

        builder.addOnderzoek(voorkomen, onderzoek);

        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_8310, VerwerkerUtil.zeroPad(String.valueOf(Lo3Integer.unwrap(aanduidingGegevensInOnderzoek)), 6));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_8320, lo3ValueConvert.convertToViewerValue(datumIngangOnderzoek, Lo3ElementEnum.ELEMENT_8320));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_8330, lo3ValueConvert.convertToViewerValue(datumEindeOnderzoek, Lo3ElementEnum.ELEMENT_8330));
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep83EmptySuccess() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3Onderzoek onderzoek = new Lo3Onderzoek(null, null, null);

        builder.addOnderzoek(voorkomen, onderzoek);

        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_8310);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_8320);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_8330);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep848586Success() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();
        final Lo3IndicatieOnjuist indicatieOnjuist = new Lo3IndicatieOnjuist("O");
        final Lo3Datum ingangsdatumGeldigheid = new Lo3Datum(20000101);
        final Lo3Datum datumVanOpneming = new Lo3Datum(20000102);
        final Lo3Historie historie = new Lo3Historie(indicatieOnjuist, ingangsdatumGeldigheid, datumVanOpneming);

        builder.addHistorie(voorkomen, historie);

        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_8410, lo3ValueConvert.convertToViewerValue(indicatieOnjuist, Lo3ElementEnum.ELEMENT_8410));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_8510, lo3ValueConvert.convertToViewerValue(ingangsdatumGeldigheid, Lo3ElementEnum.ELEMENT_8510));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_8610, lo3ValueConvert.convertToViewerValue(datumVanOpneming, Lo3ElementEnum.ELEMENT_8610));
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep848586EmptySuccess() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();
        final Lo3Historie historie = new Lo3Historie(null, null, null);

        builder.addHistorie(voorkomen, historie);

        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_8410);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_8510);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_8610);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep88Success() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3RNIDeelnemerCode rniDeelnemerCode = new Lo3RNIDeelnemerCode("0000");
        final String omsVerdrag = "Verdrag";
        final Lo3Documentatie documentatie = new Lo3Documentatie(0L, null, null, null, null, null, rniDeelnemerCode, Lo3String.wrap(omsVerdrag));

        builder.addRni(voorkomen, documentatie);

        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_8810, lo3ValueConvert.convertToViewerValue(rniDeelnemerCode, Lo3ElementEnum.ELEMENT_8810));
        assertContains(voorkomen, Lo3ElementEnum.ELEMENT_8820, omsVerdrag);
    }

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testAddGroep88EmptySuccess() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final Lo3Documentatie documentatie = new Lo3Documentatie(1L, null, null, null, null, null, null, null);

        builder.addRni(voorkomen, documentatie);

        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_8810);
        assertNotContains(voorkomen, Lo3ElementEnum.ELEMENT_8820);
    }

    private void assertContains(final Map<String, String> voorkomen, final Lo3ElementEnum element, final String expected) {
        final String key = element.getElementNummer(true) + " " + element.getLabel();
        assertTrue("Key: " + key + " komt niet voor!", voorkomen.containsKey(key));
        assertNotNull("Geen waarde bij key: " + key, voorkomen.get(key));
        assertEquals("Waarde is niet verwacht.", expected, voorkomen.get(key));
    }

    private void assertNotContains(final Map<String, String> voorkomen, final Lo3ElementEnum element) {
        final String key = element.getElementNummer(true) + " " + element.getLabel();
        assertTrue("Key: " + key + " komt voor, zou niet moeten.", !voorkomen.containsKey(key));
    }
}
