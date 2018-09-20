/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.BijzondereSituaties;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.conversie.tabel.GemeenteConversietabel;
import nl.moderniseringgba.migratie.conversie.tabel.LandConversietabel;
import nl.moderniseringgba.migratie.conversie.tabel.RedenEindeRelatieConversietabel;
import nl.moderniseringgba.migratie.conversie.tabel.VoorvoegselScheidingstekenConversietabel;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Preconditie test voor categorie 05: huwelijk.
 */
@SuppressWarnings("unchecked")
public class Lo3HuwelijkPreconditiesTest extends AbstractPreconditieTest {

    private static final Lo3Herkomst LO3_HERKOMST_HUWELIJK = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 0);
    @Inject
    private Lo3HuwelijkPrecondities precondities;

    private Lo3HuwelijkOfGpInhoud.Builder sluitingBuilder() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder();
        builder.setaNummer(1069532945L);
        builder.setBurgerservicenummer(179543489L);

        builder.setVoornamen("Jaap");
        builder.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("P"));
        builder.setVoorvoegselGeslachtsnaam("van");
        builder.setGeslachtsnaam("Joppen");

        builder.setGeboortedatum(new Lo3Datum(19940104));
        builder.setGeboorteGemeenteCode(new Lo3GemeenteCode("0514"));
        builder.setGeboorteLandCode(new Lo3LandCode("6030"));

        builder.setGeslachtsaanduiding(new Lo3Geslachtsaanduiding("M"));

        builder.setDatumSluitingHuwelijkOfAangaanGp(new Lo3Datum(20000101));
        builder.setGemeenteCodeSluitingHuwelijkOfAangaanGp(new Lo3GemeenteCode("0518"));
        builder.setLandCodeSluitingHuwelijkOfAangaanGp(new Lo3LandCode("6030"));

        builder.setDatumOntbindingHuwelijkOfGp(null);
        builder.setGemeenteCodeOntbindingHuwelijkOfGp(null);
        builder.setLandCodeOntbindingHuwelijkOfGp(null);
        builder.setRedenOntbindingHuwelijkOfGpCode(null);

        builder.setSoortVerbintenis(new Lo3SoortVerbintenis("H"));

        return builder;
    }

    private Lo3HuwelijkOfGpInhoud.Builder legeStapelBuilder() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder();
        return builder;
    }

    private Lo3HuwelijkOfGpInhoud.Builder ontbindingBuilder() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder();
        builder.setaNummer(1069532945L);
        builder.setBurgerservicenummer(179543489L);

        builder.setVoornamen("Jaap");
        builder.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("P"));
        builder.setVoorvoegselGeslachtsnaam("van");
        builder.setGeslachtsnaam("Joppen");

        builder.setGeboortedatum(new Lo3Datum(19940104));
        builder.setGeboorteGemeenteCode(new Lo3GemeenteCode("0514"));
        builder.setGeboorteLandCode(new Lo3LandCode("6030"));

        builder.setGeslachtsaanduiding(new Lo3Geslachtsaanduiding("M"));

        builder.setDatumSluitingHuwelijkOfAangaanGp(null);
        builder.setGemeenteCodeSluitingHuwelijkOfAangaanGp(null);
        builder.setLandCodeSluitingHuwelijkOfAangaanGp(null);

        builder.setDatumOntbindingHuwelijkOfGp(new Lo3Datum(20000101));
        builder.setGemeenteCodeOntbindingHuwelijkOfGp(new Lo3GemeenteCode("0518"));
        builder.setLandCodeOntbindingHuwelijkOfGp(new Lo3LandCode("6030"));
        builder.setRedenOntbindingHuwelijkOfGpCode(new Lo3RedenOntbindingHuwelijkOfGpCode("S"));

        builder.setSoortVerbintenis(new Lo3SoortVerbintenis("H"));

        return builder;
    }

    @Test
    public void testHappySluiting() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(sluitingBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testHappyOntbinding() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(ontbindingBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Ignore("Volgens mij zou je niet puur een huwelijk moeten kunnen beeidingen")
    @Test
    public void testContr101() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelCorrectieOk =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(sluitingBuilder().build(),
                                Lo3StapelHelper.lo3His("O", 20000101, 20000101),
                                Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_HUWELIJK),
                        Lo3StapelHelper.lo3Cat(new Lo3HuwelijkOfGpInhoud.Builder().build(),
                                Lo3StapelHelper.lo3His(null, 20000101, 20000101),
                                Lo3StapelHelper.lo3Doc(2L, "0514", 20000101, "Doc"), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapelCorrectieOk);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(new Lo3HuwelijkOfGpInhoud.Builder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                        LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapelNok);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE050);
    }

    @Test
    public void testContr104() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> onjuist1 =
                Lo3StapelHelper.lo3Cat(sluitingBuilder().build(), Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(sluitingBuilder().build(), Lo3StapelHelper.lo3His("O", 19990101, 20000101),
                        Lo3StapelHelper.lo3Akt(2), LO3_HERKOMST_HUWELIJK);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(sluitingBuilder().build(), Lo3StapelHelper.lo3His(null, 19990101, 20000101),
                        Lo3StapelHelper.lo3Akt(3), LO3_HERKOMST_HUWELIJK);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelOk = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2, juist3);

        precondities.controleerStapel(stapelOk);
        assertAantalErrors(0);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelNok = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2);

        precondities.controleerStapel(stapelNok);
        assertAantalErrors(2);
        assertPreconditie(Precondities.PRE055);
    }

    @Test
    public void testContr105() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelCorrectieOk =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(sluitingBuilder().build(),
                                Lo3StapelHelper.lo3His("O", 20000101, 20000101),
                                Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc A"), LO3_HERKOMST_HUWELIJK),
                        Lo3StapelHelper.lo3Cat(new Lo3HuwelijkOfGpInhoud.Builder().build(),
                                Lo3StapelHelper.lo3His(null, 20000101, 20010101),
                                Lo3StapelHelper.lo3Doc(2L, "0514", 20000101, "Doc B"), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapelCorrectieOk);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(new Lo3HuwelijkOfGpInhoud.Builder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                        LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapelNok);

        // assertAantalErrors(1);
        assertPreconditie(Precondities.PRE056);
    }

    @Test
    public void testContr108a() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder();
        builder.setDatumSluitingHuwelijkOfAangaanGp(new Lo3Datum(20000101));
        builder.setGemeenteCodeSluitingHuwelijkOfAangaanGp(new Lo3GemeenteCode("0518"));
        builder.setLandCodeSluitingHuwelijkOfAangaanGp(new Lo3LandCode("6030"));
        builder.setSoortVerbintenis(new Lo3SoortVerbintenis("H"));

        builder.setaNummer(1069532945L);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        // assertAantalErrors(2);
        assertPreconditie(Precondities.PRE039);
    }

    @Test
    public void testContr108b() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder();
        builder.setDatumSluitingHuwelijkOfAangaanGp(new Lo3Datum(20000101));
        builder.setGemeenteCodeSluitingHuwelijkOfAangaanGp(new Lo3GemeenteCode("0518"));
        builder.setLandCodeSluitingHuwelijkOfAangaanGp(new Lo3LandCode("6030"));
        builder.setSoortVerbintenis(new Lo3SoortVerbintenis("H"));

        builder.setGeboortedatum(new Lo3Datum(19940104));
        builder.setGeboorteGemeenteCode(new Lo3GemeenteCode("0514"));
        builder.setGeboorteLandCode(new Lo3LandCode("6030"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        // assertAantalErrors(2);
        assertPreconditie(Precondities.PRE039);
    }

    @Test
    public void testContr108c() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder();
        builder.setDatumSluitingHuwelijkOfAangaanGp(new Lo3Datum(20000101));
        builder.setGemeenteCodeSluitingHuwelijkOfAangaanGp(new Lo3GemeenteCode("0518"));
        builder.setLandCodeSluitingHuwelijkOfAangaanGp(new Lo3LandCode("6030"));
        builder.setSoortVerbintenis(new Lo3SoortVerbintenis("H"));

        builder.setGeslachtsaanduiding(new Lo3Geslachtsaanduiding("O"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        // assertAantalErrors(2);
        assertPreconditie(Precondities.PRE039);
    }

    @Test
    public void testContr1091() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setGeboorteGemeenteCode(new Lo3GemeenteCode("Brussel"));
        builder.setGeboorteLandCode(new Lo3LandCode("5010"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelBuitenlandsOk =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapelBuitenlandsOk);

        assertAantalErrors(0);

        builder.setGeboorteLandCode(new Lo3LandCode("6030"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapelNok);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE025);
    }

    @Test
    public void testContr1092() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setGemeenteCodeSluitingHuwelijkOfAangaanGp(new Lo3GemeenteCode("Brussel"));
        builder.setLandCodeSluitingHuwelijkOfAangaanGp(new Lo3LandCode("5010"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelBuitenlandsOk =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapelBuitenlandsOk);

        assertAantalErrors(0);

        builder.setLandCodeSluitingHuwelijkOfAangaanGp(new Lo3LandCode("6030"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapelNok);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE027);
    }

    @Test
    public void testContr1093() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = ontbindingBuilder();
        builder.setGemeenteCodeOntbindingHuwelijkOfGp(new Lo3GemeenteCode("Brussel"));
        builder.setLandCodeOntbindingHuwelijkOfGp(new Lo3LandCode("5010"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelBuitenlandsOk =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapelBuitenlandsOk);

        assertAantalErrors(0);

        builder.setLandCodeOntbindingHuwelijkOfGp(new Lo3LandCode("6030"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapelNok);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE029);
    }

    @Test
    public void testContr111() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setSoortVerbintenis(new Lo3SoortVerbintenis("."));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE042);
    }

    @Test
    public void testContr110() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(sluitingBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20000101, 20000100), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE031);
    }

    @Test
    public void testContr112() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel2 =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(sluitingBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 000000000, "Doc"),
                        LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel2);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(sluitingBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000100, "Doc"),
                        LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE067);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr230() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(sluitingBuilder().build(),
                        Lo3StapelHelper.lo3His(null, null, 20000101), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE030);
    }

    @Test
    public void testContr231() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(sluitingBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20000101, null), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE031);
    }

    @Test
    public void testContr232() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setDatumOntbindingHuwelijkOfGp(new Lo3Datum(20010101));
        builder.setGemeenteCodeOntbindingHuwelijkOfGp(new Lo3GemeenteCode("0517"));
        builder.setLandCodeOntbindingHuwelijkOfGp(new Lo3LandCode("6030"));
        builder.setRedenOntbindingHuwelijkOfGpCode(new Lo3RedenOntbindingHuwelijkOfGpCode("S"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE040);
    }

    @Test
    public void testContr233() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();

        builder.setVoornamen(null);
        builder.setAdellijkeTitelPredikaatCode(null);
        builder.setVoorvoegselGeslachtsnaam(null);
        builder.setGeslachtsnaam(null);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        // assertAantalErrors(1);
        assertPreconditie(Precondities.PRE041);
    }

    @Test
    public void testContr235() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();

        builder.setSoortVerbintenis(null);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE018);
    }

    @Test
    public void testContr236() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(sluitingBuilder().build(),
                        Lo3StapelHelper.lo3His("O", 20000101, 20000101), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        // assertAantalErrors(1);
        assertPreconditie(Precondities.PRE055);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr301() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setaNummer(null);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalInfos(1);
    }

    @Test
    public void testContr303() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setGeslachtsnaam(null);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE064);
    }

    @Test
    public void testContr304() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setGeboortedatum(null);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE007);
    }

    @Test
    public void testContr306() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setGeboortedatum(null);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE007);
    }

    @Test
    public void testContr309() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setLandCodeSluitingHuwelijkOfAangaanGp(null);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE024);
    }

    @Test
    public void testContr312() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = ontbindingBuilder();
        builder.setLandCodeOntbindingHuwelijkOfGp(null);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE028);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr4011() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setGeboortedatum(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4012() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setDatumSluitingHuwelijkOfAangaanGp(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4013() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = ontbindingBuilder();
        builder.setDatumOntbindingHuwelijkOfGp(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40121() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(sluitingBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20050155, "Doc"),
                        LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40124() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(sluitingBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20040141, 20010101), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40125() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(sluitingBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20010101, 20040141), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4021() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setGeboorteGemeenteCode(GemeenteConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE025);
    }

    @Test
    public void testContr4022() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setGemeenteCodeSluitingHuwelijkOfAangaanGp(GemeenteConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE027);
    }

    @Test
    public void testContr4023() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = ontbindingBuilder();
        builder.setGemeenteCodeOntbindingHuwelijkOfGp(GemeenteConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE029);
    }

    @Test
    public void testContr4031() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setGeboorteLandCode(LandConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE008);
    }

    @Test
    public void testContr4032() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setLandCodeSluitingHuwelijkOfAangaanGp(LandConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE024);
    }

    @Test
    public void testContr4033() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = ontbindingBuilder();
        builder.setLandCodeOntbindingHuwelijkOfGp(LandConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE028);
    }

    @Test
    public void testContr4063() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(sluitingBuilder().build(), Lo3StapelHelper
                        .lo3His(20000101), Lo3StapelHelper.lo3Documentatie(1L,
                        LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getCode(), "9-x0001", null, null, null),
                        LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr4064() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(sluitingBuilder().build(), Lo3StapelHelper
                        .lo3His(20000101), Lo3StapelHelper.lo3Documentatie(1L, null, null,
                        LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getCode(), 20010101, "Doc"),
                        LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr411() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("QQ"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr412() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setVoorvoegselGeslachtsnaam(VoorvoegselScheidingstekenConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr414() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setGeslachtsaanduiding(new Lo3Geslachtsaanduiding("Q"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr416() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = ontbindingBuilder();
        builder.setRedenOntbindingHuwelijkOfGpCode(RedenEindeRelatieConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr433() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setSoortVerbintenis(new Lo3SoortVerbintenis("Q"));

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(2);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr459() {
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(sluitingBuilder().build(),
                        Lo3StapelHelper.lo3His("X", 20000101, 20000101), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_HUWELIJK), Lo3StapelHelper.lo3Cat(sluitingBuilder().build(),
                        Lo3StapelHelper.lo3His(20010101), Lo3StapelHelper.lo3Akt(2), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testPre068LegeActueelGevuldHistorie() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueel =
                Lo3StapelHelper.lo3Cat(legeStapelBuilder().build(), Lo3StapelHelper.lo3His(20130103),
                        Lo3StapelHelper.lo3Akt(2), LO3_HERKOMST_HUWELIJK);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> historie =
                Lo3StapelHelper.lo3Cat(sluitingBuilder().build(), Lo3StapelHelper.lo3His(20120101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK);
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel = Lo3StapelHelper.lo3Stapel(actueel, historie);

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testPre068GevuldeActueelLegeHistorie() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueel =
                Lo3StapelHelper.lo3Cat(sluitingBuilder().build(), Lo3StapelHelper.lo3His(20120103),
                        Lo3StapelHelper.lo3Akt(2), LO3_HERKOMST_HUWELIJK);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> historie =
                Lo3StapelHelper.lo3Cat(legeStapelBuilder().build(), Lo3StapelHelper.lo3His(20130101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK);
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel = Lo3StapelHelper.lo3Stapel(actueel, historie);

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testPre068Ok() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueel =
                Lo3StapelHelper.lo3Cat(ontbindingBuilder().build(), Lo3StapelHelper.lo3His(20130103),
                        Lo3StapelHelper.lo3Akt(2), LO3_HERKOMST_HUWELIJK);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> historie =
                Lo3StapelHelper.lo3Cat(sluitingBuilder().build(), Lo3StapelHelper.lo3His(20120101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK);
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel = Lo3StapelHelper.lo3Stapel(actueel, historie);

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testPre068OkMetOnjuisteStapel() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueel =
                Lo3StapelHelper.lo3Cat(ontbindingBuilder().build(), Lo3StapelHelper.lo3His(20130101),
                        Lo3StapelHelper.lo3Akt(3), LO3_HERKOMST_HUWELIJK);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> historieOnjuist =
                Lo3StapelHelper.lo3Cat(ontbindingBuilder().build(), Lo3StapelHelper.lo3His("O", 20120101, 20120102),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> historie =
                Lo3StapelHelper.lo3Cat(sluitingBuilder().build(), Lo3StapelHelper.lo3His(20120101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK);
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel = Lo3StapelHelper.lo3Stapel(actueel, historieOnjuist, historie);

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testBijzondereSituatieSluitingLandCodeStandaardwaarde() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setLandCodeSluitingHuwelijkOfAangaanGp(new Lo3LandCode("0000"));
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB003);
    }

    @Test
    public void testBijzondereSituatieSluitingLandCodeInternationaalGebied() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = sluitingBuilder();
        builder.setLandCodeSluitingHuwelijkOfAangaanGp(new Lo3LandCode("9999"));
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB003);
    }

    @Test
    public void testBijzondereSituatieOntbindingLandCodeStandaardwaarde() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = ontbindingBuilder();
        builder.setLandCodeOntbindingHuwelijkOfGp(new Lo3LandCode("0000"));
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB004);
    }

    @Test
    public void testBijzondereSituatieOntbindingLandCodeInternationaalGebied() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = ontbindingBuilder();
        builder.setLandCodeOntbindingHuwelijkOfGp(new Lo3LandCode("9999"));
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_HUWELIJK));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB004);
    }

    @Test
    public void testDocumentEnAkteAanwezig() {
        final Lo3Documentatie docs =
                Lo3StapelHelper.lo3Documentatie(1L, "0518", "9-X" + String.format("%04d", 1L), "0518", 20000101,
                        "Testdocument");

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(sluitingBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), docs, LO3_HERKOMST_HUWELIJK));
        precondities.controleerDocumentOfAkte(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE020);
    }

    @Test
    public void testDocumentEnAkteNietAanwezig() {

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(sluitingBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), null, LO3_HERKOMST_HUWELIJK));
        precondities.controleerDocumentOfAkte(stapel);

        assertAantalErrors(0);
    }
}
