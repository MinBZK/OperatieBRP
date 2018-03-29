/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import java.util.Collections;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import nl.bzk.migratiebrp.conversie.regels.tabel.GemeenteConversietabel;
import nl.bzk.migratiebrp.conversie.regels.tabel.LandConversietabel;
import nl.bzk.migratiebrp.conversie.regels.tabel.RedenEindeRelatieConversietabel;
import nl.bzk.migratiebrp.conversie.regels.tabel.VoorvoegselScheidingstekenConversietabel;
import org.junit.Test;

/**
 * Preconditie test voor categorie 05: huwelijk.
 */
public class Lo3HuwelijkPreconditiesTest extends AbstractPreconditieTest {

    private static final String DOC = "Doc";
    private static final String GEM_CODE2 = "0518";
    private static final String LAND_CODE = "6030";
    private static final String GEM_CODE = "0514";
    private static final Lo3Herkomst LO3_HERKOMST_HUWELIJK = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 0);
    private static final Lo3Herkomst LO3_HERKOMST_HUWELIJK_1 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 1);
    private static final Lo3Herkomst LO3_HERKOMST_HUWELIJK_2 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 2);
    private static final Lo3String PERSOON_ANUMMER = new Lo3String("1234567890");

    private Lo3HuwelijkPrecondities precondities = new Lo3HuwelijkPrecondities(new ConversietabelFactoryImpl());

    private Lo3HuwelijkOfGpInhoud.Builder sluitingBuilder() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder();
        builder.aNummer(Lo3String.wrap("1069532945"));
        builder.burgerservicenummer(Lo3String.wrap("179543489"));

        builder.voornamen(Lo3String.wrap("Jaap"));
        builder.adellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("P"));
        builder.voorvoegselGeslachtsnaam(Lo3String.wrap("van"));
        builder.geslachtsnaam(Lo3String.wrap("Joppen"));

        builder.geboortedatum(new Lo3Datum(19940104));
        builder.geboorteGemeenteCode(new Lo3GemeenteCode(GEM_CODE));
        builder.geboorteLandCode(new Lo3LandCode(LAND_CODE));

        builder.geslachtsaanduiding(new Lo3Geslachtsaanduiding("M"));

        builder.datumSluitingHuwelijkOfAangaanGp(new Lo3Datum(20000101));
        builder.gemeenteCodeSluitingHuwelijkOfAangaanGp(new Lo3GemeenteCode(GEM_CODE2));
        builder.landCodeSluitingHuwelijkOfAangaanGp(new Lo3LandCode(LAND_CODE));

        builder.datumOntbindingHuwelijkOfGp(null);
        builder.gemeenteCodeOntbindingHuwelijkOfGp(null);
        builder.landCodeOntbindingHuwelijkOfGp(null);
        builder.redenOntbindingHuwelijkOfGpCode(null);

        builder.soortVerbintenis(new Lo3SoortVerbintenis("H"));

        return builder;
    }

    private Lo3HuwelijkOfGpInhoud.Builder legeStapelBuilder() {
        return new Lo3HuwelijkOfGpInhoud.Builder();
    }

    private Lo3HuwelijkOfGpInhoud.Builder ontbindingBuilder() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder();
        builder.aNummer(Lo3String.wrap("1069532945"));
        builder.burgerservicenummer(Lo3String.wrap("179543489"));

        builder.voornamen(Lo3String.wrap("Jaap"));
        builder.adellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("P"));
        builder.voorvoegselGeslachtsnaam(Lo3String.wrap("van"));
        builder.geslachtsnaam(Lo3String.wrap("Joppen"));

        builder.geboortedatum(new Lo3Datum(19940104));
        builder.geboorteGemeenteCode(new Lo3GemeenteCode(GEM_CODE));
        builder.geboorteLandCode(new Lo3LandCode(LAND_CODE));

        builder.geslachtsaanduiding(new Lo3Geslachtsaanduiding("M"));

        builder.datumSluitingHuwelijkOfAangaanGp(null);
        builder.gemeenteCodeSluitingHuwelijkOfAangaanGp(null);
        builder.landCodeSluitingHuwelijkOfAangaanGp(null);

        builder.datumOntbindingHuwelijkOfGp(new Lo3Datum(20000101));
        builder.gemeenteCodeOntbindingHuwelijkOfGp(new Lo3GemeenteCode(GEM_CODE2));
        builder.landCodeOntbindingHuwelijkOfGp(new Lo3LandCode(LAND_CODE));
        builder.redenOntbindingHuwelijkOfGpCode(new Lo3RedenOntbindingHuwelijkOfGpCode("S"));

        builder.soortVerbintenis(new Lo3SoortVerbintenis("H"));

        return builder;
    }

    @Test
    public void testHappySluiting() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(sluitingBuilder().build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(0);
    }

    @Test
    public void testHappyOntbinding() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                ontbindingBuilder().build(),
                                Lo3StapelHelper.lo3Akt(1),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(0);
    }

    @Test
    public void testContr104() {
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> onjuist1 =
                Lo3StapelHelper.lo3Cat(
                        sluitingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        LO3_HERKOMST_HUWELIJK_2);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(
                        sluitingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(2),
                        Lo3StapelHelper.lo3His("O", 19990101, 20000101),
                        LO3_HERKOMST_HUWELIJK_1);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(
                        sluitingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(3),
                        Lo3StapelHelper.lo3His(null, 19990101, 20000101),
                        LO3_HERKOMST_HUWELIJK);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelOk = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2, juist3);

        precondities.controleerStapels(Collections.singletonList(stapelOk), PERSOON_ANUMMER);
        assertAantalErrors(0);

        onjuist1 =
                Lo3StapelHelper.lo3Cat(
                        sluitingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        LO3_HERKOMST_HUWELIJK);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelNok = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2);

        precondities.controleerStapels(Collections.singletonList(stapelNok), PERSOON_ANUMMER);
        assertSoortMeldingCode(SoortMeldingCode.PRE055, 1);
    }

    @Test
    public void testContr105() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelCorrectieOk =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                sluitingBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, "Doc A"),
                                Lo3StapelHelper.lo3His("O", 20000101, 20000101),
                                LO3_HERKOMST_HUWELIJK_1),
                        Lo3StapelHelper.lo3Cat(
                                new Lo3HuwelijkOfGpInhoud.Builder().build(),
                                Lo3StapelHelper.lo3Doc(2L, GEM_CODE, 20000101, "Doc B"),
                                Lo3StapelHelper.lo3His(null, 20000101, 20010101),
                                LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapelCorrectieOk), PERSOON_ANUMMER);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                new Lo3HuwelijkOfGpInhoud.Builder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapelNok), PERSOON_ANUMMER);

        // assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE056, 1);
    }

    @Test
    public void testContr108a() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder();
        builder.datumSluitingHuwelijkOfAangaanGp(new Lo3Datum(20000101));
        builder.gemeenteCodeSluitingHuwelijkOfAangaanGp(new Lo3GemeenteCode(GEM_CODE2));
        builder.landCodeSluitingHuwelijkOfAangaanGp(new Lo3LandCode(LAND_CODE));
        builder.soortVerbintenis(new Lo3SoortVerbintenis("H"));

        builder.aNummer(Lo3String.wrap("1069532945"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        // assertAantalErrors(2);
        assertSoortMeldingCode(SoortMeldingCode.PRE039, 1);
    }

    @Test
    public void testContr108b() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder();
        builder.datumSluitingHuwelijkOfAangaanGp(new Lo3Datum(20000101));
        builder.gemeenteCodeSluitingHuwelijkOfAangaanGp(new Lo3GemeenteCode(GEM_CODE2));
        builder.landCodeSluitingHuwelijkOfAangaanGp(new Lo3LandCode(LAND_CODE));
        builder.soortVerbintenis(new Lo3SoortVerbintenis("H"));

        builder.geboortedatum(new Lo3Datum(19940104));
        builder.geboorteGemeenteCode(new Lo3GemeenteCode(GEM_CODE));
        builder.geboorteLandCode(new Lo3LandCode(LAND_CODE));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        // assertAantalErrors(2);
        assertSoortMeldingCode(SoortMeldingCode.PRE039, 1);
    }

    @Test
    public void testContr108c() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder();
        builder.datumSluitingHuwelijkOfAangaanGp(new Lo3Datum(20000101));
        builder.gemeenteCodeSluitingHuwelijkOfAangaanGp(new Lo3GemeenteCode(GEM_CODE2));
        builder.landCodeSluitingHuwelijkOfAangaanGp(new Lo3LandCode(LAND_CODE));
        builder.soortVerbintenis(new Lo3SoortVerbintenis("H"));

        builder.geslachtsaanduiding(new Lo3Geslachtsaanduiding("O"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        // assertAantalErrors(2);
        assertSoortMeldingCode(SoortMeldingCode.PRE039, 1);
    }

    @Test
    public void testContr1091() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.geboorteGemeenteCode(new Lo3GemeenteCode("Brussel"));
        builder.geboorteLandCode(new Lo3LandCode("5010"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelBuitenlandsOk =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapelBuitenlandsOk), PERSOON_ANUMMER);

        assertAantalErrors(0);

        builder.geboorteLandCode(new Lo3LandCode(LAND_CODE));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapelNok), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE025, 1);
    }

    @Test
    public void testContr1092() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.gemeenteCodeSluitingHuwelijkOfAangaanGp(new Lo3GemeenteCode("Brussel"));
        builder.landCodeSluitingHuwelijkOfAangaanGp(new Lo3LandCode("5010"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelBuitenlandsOk =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapelBuitenlandsOk), PERSOON_ANUMMER);

        assertAantalErrors(0);

        builder.landCodeSluitingHuwelijkOfAangaanGp(new Lo3LandCode(LAND_CODE));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapelNok), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE027, 1);
    }

    @Test
    public void testContr1093() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = ontbindingBuilder();
        builder.gemeenteCodeOntbindingHuwelijkOfGp(new Lo3GemeenteCode("Brussel"));
        builder.landCodeOntbindingHuwelijkOfGp(new Lo3LandCode("5010"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelBuitenlandsOk =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapelBuitenlandsOk), PERSOON_ANUMMER);

        assertAantalErrors(0);

        builder.landCodeOntbindingHuwelijkOfGp(new Lo3LandCode(LAND_CODE));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapelNok), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE029, 1);
    }

    @Test
    public void testContr111() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.soortVerbintenis(new Lo3SoortVerbintenis("."));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE042, 1);
    }

    @Test
    public void testContr110() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                sluitingBuilder().build(),
                                Lo3StapelHelper.lo3Akt(1),
                                Lo3StapelHelper.lo3His(null, 20000101, 20000100),
                                LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE031, 1);
    }

    @Test
    public void testContr112() {
        final int standaardDatum = 0;
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel2 =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                sluitingBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, standaardDatum, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel2), PERSOON_ANUMMER);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                sluitingBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000100, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE067, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr230() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                sluitingBuilder().build(),
                                Lo3StapelHelper.lo3Akt(1),
                                Lo3StapelHelper.lo3His(null, null, 20000101),
                                LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE030, 1);
    }

    @Test
    public void testContr231() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                sluitingBuilder().build(),
                                Lo3StapelHelper.lo3Akt(1),
                                Lo3StapelHelper.lo3His(null, 20000101, null),
                                LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE031, 1);
    }

    @Test
    public void testContr232() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.datumOntbindingHuwelijkOfGp(new Lo3Datum(20010101));
        builder.gemeenteCodeOntbindingHuwelijkOfGp(new Lo3GemeenteCode("0517"));
        builder.landCodeOntbindingHuwelijkOfGp(new Lo3LandCode(LAND_CODE));
        builder.redenOntbindingHuwelijkOfGpCode(new Lo3RedenOntbindingHuwelijkOfGpCode("S"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE040, 1);
    }

    @Test
    public void testContr233() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();

        builder.voornamen(null);
        builder.adellijkeTitelPredikaatCode(null);
        builder.voorvoegselGeslachtsnaam(null);
        builder.geslachtsnaam(null);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        // assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE041, 1);
    }

    @Test
    public void testContr235() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();

        builder.soortVerbintenis(null);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE018, 1);
    }

    @Test
    public void testContr236() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                sluitingBuilder().build(),
                                Lo3StapelHelper.lo3Akt(1),
                                Lo3StapelHelper.lo3His("O", 20000101, 20000101),
                                LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        // assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE055, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr301() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.aNummer(null);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalInfos(1);
    }

    @Test
    public void testContr303() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.geslachtsnaam(null);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE064, 1);
    }

    @Test
    public void testContr304() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.geboortedatum(null);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE007, 1);
    }

    @Test
    public void testContr306() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.geboortedatum(null);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE007, 1);
    }

    @Test
    public void testContr309() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.landCodeSluitingHuwelijkOfAangaanGp(null);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE024, 1);
    }

    @Test
    public void testContr312() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = ontbindingBuilder();
        builder.landCodeOntbindingHuwelijkOfGp(null);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE028, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr4011() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.geboortedatum(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4012() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.datumSluitingHuwelijkOfAangaanGp(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4013() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = ontbindingBuilder();
        builder.datumOntbindingHuwelijkOfGp(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40121() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                sluitingBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20050155, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40124() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                sluitingBuilder().build(),
                                Lo3StapelHelper.lo3Akt(1),
                                Lo3StapelHelper.lo3His(null, 20040141, 20010101),
                                LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40125() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                sluitingBuilder().build(),
                                Lo3StapelHelper.lo3Akt(1),
                                Lo3StapelHelper.lo3His(null, 20010101, 20040141),
                                LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4021() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.geboorteGemeenteCode(GemeenteConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE025, 1);
    }

    @Test
    public void testContr4022() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.gemeenteCodeSluitingHuwelijkOfAangaanGp(GemeenteConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE027, 1);
    }

    @Test
    public void testContr4023() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = ontbindingBuilder();
        builder.gemeenteCodeOntbindingHuwelijkOfGp(GemeenteConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE029, 1);
    }

    @Test
    public void testContr4031() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.geboorteLandCode(LandConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE008, 1);
    }

    @Test
    public void testContr4032() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.landCodeSluitingHuwelijkOfAangaanGp(LandConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE024, 1);
    }

    @Test
    public void testContr4033() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = ontbindingBuilder();
        builder.landCodeOntbindingHuwelijkOfGp(LandConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE028, 1);
    }

    @Test
    public void testContr4063() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                sluitingBuilder().build(),
                                Lo3StapelHelper.lo3Documentatie(1L, LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getWaarde(), "1-x0001", null, null, null),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr4064() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                sluitingBuilder().build(),
                                Lo3StapelHelper.lo3Documentatie(1L, null, null, LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getWaarde(), 20010101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr411() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.adellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("QQ"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr412() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.voorvoegselGeslachtsnaam(Lo3String.wrap(VoorvoegselScheidingstekenConversietabel.LO3_NIET_VALIDE_UITZONDERING));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr414() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.geslachtsaanduiding(new Lo3Geslachtsaanduiding("Q"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr416() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = ontbindingBuilder();
        builder.redenOntbindingHuwelijkOfGpCode(RedenEindeRelatieConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr433() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.soortVerbintenis(new Lo3SoortVerbintenis("Q"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertSoortMeldingCode(SoortMeldingCode.PRE042, 1);
    }

    @Test
    public void testPre056LegeActueelGevuldHistorie() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueel =
                Lo3StapelHelper.lo3Cat(legeStapelBuilder().build(), Lo3StapelHelper.lo3Akt(2), Lo3StapelHelper.lo3His(20130103), LO3_HERKOMST_HUWELIJK);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> historie =
                Lo3StapelHelper.lo3Cat(sluitingBuilder().build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20120101), LO3_HERKOMST_HUWELIJK_1);
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel = Lo3StapelHelper.lo3Stapel(actueel, historie);

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalWarnings(0);
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE056, 1);
    }

    @Test
    public void testPre056GevuldeActueelLegeHistorie() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueel =
                Lo3StapelHelper.lo3Cat(sluitingBuilder().build(), Lo3StapelHelper.lo3Akt(2), Lo3StapelHelper.lo3His(20120103), LO3_HERKOMST_HUWELIJK_1);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> historie =
                Lo3StapelHelper.lo3Cat(legeStapelBuilder().build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20130101), LO3_HERKOMST_HUWELIJK);
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel = Lo3StapelHelper.lo3Stapel(actueel, historie);

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalWarnings(0);
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE056, 1);

    }

    @Test
    public void testPre056Ok() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueel =
                Lo3StapelHelper.lo3Cat(ontbindingBuilder().build(), Lo3StapelHelper.lo3Akt(2), Lo3StapelHelper.lo3His(20130103), LO3_HERKOMST_HUWELIJK);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> historie =
                Lo3StapelHelper.lo3Cat(sluitingBuilder().build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20120101), LO3_HERKOMST_HUWELIJK_1);
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel = Lo3StapelHelper.lo3Stapel(actueel, historie);

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(0);
        assertAantalWarnings(0);
    }

    @Test
    public void testPre056OkMetOnjuisteStapel() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueel =
                Lo3StapelHelper.lo3Cat(ontbindingBuilder().build(), Lo3StapelHelper.lo3Akt(3), Lo3StapelHelper.lo3His(20130101), LO3_HERKOMST_HUWELIJK);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> historieOnjuist =
                Lo3StapelHelper.lo3Cat(
                        ontbindingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His("O", 20120101, 20120102),
                        LO3_HERKOMST_HUWELIJK_2);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> historie =
                Lo3StapelHelper.lo3Cat(sluitingBuilder().build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20120101), LO3_HERKOMST_HUWELIJK_1);
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel = Lo3StapelHelper.lo3Stapel(actueel, historieOnjuist, historie);

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(0);
        assertAantalWarnings(0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB003)
    public void testBijzondereSituatieSluitingLandCodeStandaardwaarde() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.landCodeSluitingHuwelijkOfAangaanGp(new Lo3LandCode("0000"));
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB003, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB003)
    public void testBijzondereSituatieSluitingLandCodeInternationaalGebied() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.landCodeSluitingHuwelijkOfAangaanGp(new Lo3LandCode("9999"));
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB003, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB004)
    public void testBijzondereSituatieOntbindingLandCodeStandaardwaarde() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = ontbindingBuilder();
        builder.landCodeOntbindingHuwelijkOfGp(new Lo3LandCode("0000"));
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB004, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB004)
    public void testBijzondereSituatieOntbindingLandCodeInternationaalGebied() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = ontbindingBuilder();
        builder.landCodeOntbindingHuwelijkOfGp(new Lo3LandCode("9999"));
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB004, 1);
    }

    @Test
    public void testDocumentEnAkteAanwezig() {
        final Lo3Documentatie docs =
                Lo3StapelHelper.lo3Documentatie(1L, GEM_CODE2, "1-X" + String.format("%04d", 1L), GEM_CODE2, 20000101, "Testdocument");

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(sluitingBuilder().build(), docs, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));
        precondities.controleerDocumentOfAkte(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE020, 1);
    }

    @Test
    public void testDocumentEnAkteNietAanwezig() {

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(sluitingBuilder().build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_HUWELIJK));
        precondities.controleerDocumentOfAkte(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testPreconditie074Happy() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> juist1 =
                Lo3StapelHelper.lo3Cat(
                        sluitingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(null, 19990101, 19990101),
                        LO3_HERKOMST_HUWELIJK_2);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(
                        sluitingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(2),
                        Lo3StapelHelper.lo3His("O", 19990102, 20000101),
                        LO3_HERKOMST_HUWELIJK_1);

        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.datumSluitingHuwelijkOfAangaanGp(new Lo3Datum(20000101));

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(3),
                        Lo3StapelHelper.lo3His(null, 19990103, 20000102),
                        LO3_HERKOMST_HUWELIJK);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel = Lo3StapelHelper.lo3Stapel(juist1, onjuist2, juist3);

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);
        assertSoortMeldingCode(SoortMeldingCode.PRE074, 0);
    }

    @Test
    public void testPreconditie074Groep6Verschil() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> juist1 =
                Lo3StapelHelper.lo3Cat(
                        sluitingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(null, 19990101, 19990101),
                        LO3_HERKOMST_HUWELIJK_2);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(
                        sluitingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(2),
                        Lo3StapelHelper.lo3His("O", 19990102, 20000101),
                        LO3_HERKOMST_HUWELIJK_1);

        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.datumSluitingHuwelijkOfAangaanGp(new Lo3Datum(20000102));

        Lo3Categorie<Lo3HuwelijkOfGpInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(3),
                        Lo3StapelHelper.lo3His(null, 19990103, 20000102),
                        LO3_HERKOMST_HUWELIJK);

        Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel = Lo3StapelHelper.lo3Stapel(juist1, onjuist2, juist3);

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);
        assertSoortMeldingCode(SoortMeldingCode.PRE074, 1);

        builder.datumSluitingHuwelijkOfAangaanGp(new Lo3Datum(20000101));
        builder.gemeenteCodeSluitingHuwelijkOfAangaanGp(new Lo3GemeenteCode("0519"));

        juist3 =
                Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(3),
                        Lo3StapelHelper.lo3His(null, 19990103, 20000102),
                        LO3_HERKOMST_HUWELIJK);

        stapel = Lo3StapelHelper.lo3Stapel(juist1, onjuist2, juist3);

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);
        assertSoortMeldingCode(SoortMeldingCode.PRE074, 1);

        builder.datumSluitingHuwelijkOfAangaanGp(new Lo3Datum(20000101));
        builder.gemeenteCodeSluitingHuwelijkOfAangaanGp(new Lo3GemeenteCode(GEM_CODE2));
        builder.landCodeSluitingHuwelijkOfAangaanGp(new Lo3LandCode("6031"));

        juist3 =
                Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(3),
                        Lo3StapelHelper.lo3His(null, 19990103, 20000102),
                        LO3_HERKOMST_HUWELIJK);

        stapel = Lo3StapelHelper.lo3Stapel(juist1, onjuist2, juist3);

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);
        assertSoortMeldingCode(SoortMeldingCode.PRE074, 1);
    }

    @Test
    public void testPreconditie074VerschillendeCode() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> juist1 =
                Lo3StapelHelper.lo3Cat(
                        sluitingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(null, 19990101, 19990101),
                        LO3_HERKOMST_HUWELIJK_2);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(
                        sluitingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(2),
                        Lo3StapelHelper.lo3His("O", 19990102, 20000101),
                        LO3_HERKOMST_HUWELIJK_1);

        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.datumSluitingHuwelijkOfAangaanGp(new Lo3Datum(20000102));
        builder.soortVerbintenis(new Lo3SoortVerbintenis("P"));

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(3),
                        Lo3StapelHelper.lo3His(null, 19990103, 20000102),
                        LO3_HERKOMST_HUWELIJK);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel = Lo3StapelHelper.lo3Stapel(juist1, onjuist2, juist3);

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);
        assertSoortMeldingCode(SoortMeldingCode.PRE074, 0);
    }

    @Test
    public void testPreconditie074GeenGroep6() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> juist1 =
                Lo3StapelHelper.lo3Cat(
                        sluitingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(null, 19990101, 19990101),
                        LO3_HERKOMST_HUWELIJK_2);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(
                        sluitingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(2),
                        Lo3StapelHelper.lo3His("O", 19990102, 20000101),
                        LO3_HERKOMST_HUWELIJK_1);

        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.datumSluitingHuwelijkOfAangaanGp(null);
        builder.gemeenteCodeSluitingHuwelijkOfAangaanGp(null);
        builder.landCodeSluitingHuwelijkOfAangaanGp(null);

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(3),
                        Lo3StapelHelper.lo3His(null, 19990103, 20000102),
                        LO3_HERKOMST_HUWELIJK);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel = Lo3StapelHelper.lo3Stapel(juist1, onjuist2, juist3);

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);
        assertSoortMeldingCode(SoortMeldingCode.PRE074, 0);
    }

    @Test
    public void testPreconditie075Happy() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> juist1 =
                Lo3StapelHelper.lo3Cat(
                        ontbindingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(null, 19990101, 19990101),
                        LO3_HERKOMST_HUWELIJK_2);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(
                        ontbindingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(2),
                        Lo3StapelHelper.lo3His("O", 19990102, 20000101),
                        LO3_HERKOMST_HUWELIJK_1);

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(
                        ontbindingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(3),
                        Lo3StapelHelper.lo3His(null, 19990103, 20000102),
                        LO3_HERKOMST_HUWELIJK);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel = Lo3StapelHelper.lo3Stapel(juist1, onjuist2, juist3);

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);
        assertSoortMeldingCode(SoortMeldingCode.PRE075, 0);
    }

    @Test
    public void testPreconditie075HappyGeenGroep7WelGroep6() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = ontbindingBuilder();
        builder.datumOntbindingHuwelijkOfGp(null);
        builder.gemeenteCodeOntbindingHuwelijkOfGp(null);
        builder.landCodeOntbindingHuwelijkOfGp(null);
        builder.redenOntbindingHuwelijkOfGpCode(null);

        builder.datumSluitingHuwelijkOfAangaanGp(new Lo3Datum(20000101));
        builder.gemeenteCodeSluitingHuwelijkOfAangaanGp(new Lo3GemeenteCode(GEM_CODE2));
        builder.landCodeSluitingHuwelijkOfAangaanGp(new Lo3LandCode(LAND_CODE));

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> juist1 =
                Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(null, 19990101, 19990101),
                        LO3_HERKOMST_HUWELIJK_2);

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(
                        ontbindingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(2),
                        Lo3StapelHelper.lo3His("O", 19990102, 20000101),
                        LO3_HERKOMST_HUWELIJK_1);

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(
                        ontbindingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(3),
                        Lo3StapelHelper.lo3His(null, 19990103, 20000102),
                        LO3_HERKOMST_HUWELIJK);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel = Lo3StapelHelper.lo3Stapel(juist1, onjuist2, juist3);

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);
        assertSoortMeldingCode(SoortMeldingCode.PRE075, 0);
    }

    @Test
    public void testPreconditie075Groep7Verschil() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> juist1 =
                Lo3StapelHelper.lo3Cat(
                        ontbindingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(null, 19990101, 19990101),
                        LO3_HERKOMST_HUWELIJK_2);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(
                        ontbindingBuilder().build(),
                        Lo3StapelHelper.lo3Akt(2),
                        Lo3StapelHelper.lo3His("O", 19990102, 20000101),
                        LO3_HERKOMST_HUWELIJK_1);

        final Lo3HuwelijkOfGpInhoud.Builder builder = ontbindingBuilder();
        builder.datumOntbindingHuwelijkOfGp(new Lo3Datum(20000102));

        Lo3Categorie<Lo3HuwelijkOfGpInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(3),
                        Lo3StapelHelper.lo3His(null, 19990103, 20000102),
                        LO3_HERKOMST_HUWELIJK);

        Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel = Lo3StapelHelper.lo3Stapel(juist1, onjuist2, juist3);

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);
        assertSoortMeldingCode(SoortMeldingCode.PRE075, 1);

        builder.datumOntbindingHuwelijkOfGp(new Lo3Datum(20000101));
        builder.gemeenteCodeOntbindingHuwelijkOfGp(new Lo3GemeenteCode("0519"));

        juist3 =
                Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(3),
                        Lo3StapelHelper.lo3His(null, 19990103, 20000102),
                        LO3_HERKOMST_HUWELIJK);

        stapel = Lo3StapelHelper.lo3Stapel(juist1, onjuist2, juist3);

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);
        assertSoortMeldingCode(SoortMeldingCode.PRE075, 1);

        builder.gemeenteCodeOntbindingHuwelijkOfGp(new Lo3GemeenteCode(GEM_CODE2));
        builder.landCodeOntbindingHuwelijkOfGp(new Lo3LandCode("6031"));

        juist3 =
                Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(3),
                        Lo3StapelHelper.lo3His(null, 19990103, 20000102),
                        LO3_HERKOMST_HUWELIJK);

        stapel = Lo3StapelHelper.lo3Stapel(juist1, onjuist2, juist3);

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);
        assertSoortMeldingCode(SoortMeldingCode.PRE075, 1);

        builder.landCodeOntbindingHuwelijkOfGp(new Lo3LandCode(LAND_CODE));
        builder.redenOntbindingHuwelijkOfGpCode(new Lo3RedenOntbindingHuwelijkOfGpCode("Z"));

        juist3 =
                Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(3),
                        Lo3StapelHelper.lo3His(null, 19990103, 20000102),
                        LO3_HERKOMST_HUWELIJK);

        stapel = Lo3StapelHelper.lo3Stapel(juist1, onjuist2, juist3);

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);
        assertSoortMeldingCode(SoortMeldingCode.PRE075, 1);
    }

    @Test
    public void testPreconditie088Triggered() {
        Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                sluitingBuilder().datumSluitingHuwelijkOfAangaanGp(null).build(),
                                Lo3StapelHelper.lo3Akt(1),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_HUWELIJK));
        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);
        assertSoortMeldingCode(SoortMeldingCode.PRE088, 1);

        stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                sluitingBuilder().datumSluitingHuwelijkOfAangaanGp(new Lo3Datum(0)).build(),
                                Lo3StapelHelper.lo3Akt(1),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_HUWELIJK));
        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE088, 1);
    }

    @Test
    public void testControleerGroep83Procedure8310NietGevuld() {
        final Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(null, new Lo3Datum(20140101), null);
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                sluitingBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                lo3Onderzoek,
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE099, 1);
    }
}
