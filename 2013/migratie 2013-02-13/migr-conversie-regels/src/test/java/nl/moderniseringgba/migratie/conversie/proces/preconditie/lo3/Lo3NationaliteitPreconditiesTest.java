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
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud.Builder;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.conversie.tabel.LandConversietabel;
import nl.moderniseringgba.migratie.conversie.tabel.NationaliteitConversietabel;
import nl.moderniseringgba.migratie.conversie.tabel.RedenVerkrijgingVerliesNlNationaliteitConversietabel;

import org.junit.Test;

/**
 * Preconditie tests voor categorie 04: nationaliteit.
 */
@SuppressWarnings("unchecked")
public class Lo3NationaliteitPreconditiesTest extends AbstractPreconditieTest {

    private static final Lo3Herkomst LO3_HERKOMST_NATIONALITEIT =
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0);
    @Inject
    private Lo3NationaliteitPrecondities precondities;

    private Lo3NationaliteitInhoud.Builder buitenlandsBuilder() {
        final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder();
        builder.setNationaliteitCode(new Lo3NationaliteitCode("0028"));
        builder.setRedenVerkrijgingNederlandschapCode(null);
        builder.setRedenVerliesNederlandschapCode(null);
        builder.setAanduidingBijzonderNederlandschap(null);

        return builder;
    }

    private Lo3NationaliteitInhoud.Builder verkrijgingBuilder() {
        final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder();
        builder.setNationaliteitCode(new Lo3NationaliteitCode("0001"));
        builder.setRedenVerkrijgingNederlandschapCode(new Lo3RedenNederlandschapCode("001"));
        builder.setRedenVerliesNederlandschapCode(null);
        builder.setAanduidingBijzonderNederlandschap(null);

        return builder;
    }

    private Lo3NationaliteitInhoud.Builder verliesBuilder() {
        final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder();
        builder.setNationaliteitCode(null);
        builder.setRedenVerkrijgingNederlandschapCode(null);
        builder.setRedenVerliesNederlandschapCode(new Lo3RedenNederlandschapCode("072"));
        builder.setAanduidingBijzonderNederlandschap(null);

        return builder;
    }

    private Lo3NationaliteitInhoud.Builder bijzonderBuilder() {
        final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder();
        builder.setNationaliteitCode(null);
        builder.setRedenVerkrijgingNederlandschapCode(null);
        builder.setRedenVerliesNederlandschapCode(null);
        builder.setAanduidingBijzonderNederlandschap(new Lo3AanduidingBijzonderNederlandschap("B"));

        return builder;
    }

    @Test
    public void testHappyBuitenland() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                        LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testHappyVerkrijging() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(verkrijgingBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                        LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testHappyVerlies() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(verliesBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(2L, "0514", 20000101, "Doc"),
                        LO3_HERKOMST_NATIONALITEIT), Lo3StapelHelper.lo3Cat(verkrijgingBuilder().build(),
                        Lo3StapelHelper.lo3His(19990101), Lo3StapelHelper.lo3Doc(1L, "0514", 19990101, "Doc"),
                        LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testHappyBijzonder() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(bijzonderBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                        LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testVerkeerdeVolgordeOpneming() {
        final Builder builder = verkrijgingBuilder();
        builder.setNationaliteitCode(null);
        builder.setRedenVerkrijgingNederlandschapCode(null);
        final Lo3NationaliteitInhoud inhoud = builder.build();
        // Stapel met 2 voorkomens de oudste heeft de nieuwste opneming datum
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(verkrijgingBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20000101, 20131205), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_NATIONALITEIT),

                Lo3StapelHelper.lo3Cat(inhoud, Lo3StapelHelper.lo3His(null, 20010101, 20121205),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE050);
    }

    @Test
    public void testContr101() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapelBeeindigingOk =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                        LO3_HERKOMST_NATIONALITEIT), Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud.Builder().build(), Lo3StapelHelper.lo3His(20010101),
                        Lo3StapelHelper.lo3Doc(2L, "0514", 20010101, "Doc"), LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapelBeeindigingOk);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(new Lo3NationaliteitInhoud.Builder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                        LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapelNok);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE050);
    }

    @Test
    public void testContr102() {

        final Lo3Stapel<Lo3NationaliteitInhoud> enkeleNationaliteit =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                        LO3_HERKOMST_NATIONALITEIT), Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(),
                        Lo3StapelHelper.lo3His(20010101), Lo3StapelHelper.lo3Doc(2L, "0514", 20010101, "Doc"),
                        LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(enkeleNationaliteit);

        assertAantalErrors(0);

        final Lo3NationaliteitInhoud.Builder builder = buitenlandsBuilder();
        builder.setNationaliteitCode(new Lo3NationaliteitCode("0029"));

        final Lo3Stapel<Lo3NationaliteitInhoud> meedereNationaliteit =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_NATIONALITEIT),
                        Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(), Lo3StapelHelper.lo3His(20010101),
                                Lo3StapelHelper.lo3Doc(2L, "0514", 20010101, "Doc"), LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(meedereNationaliteit);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE051);
    }

    @Test
    public void testContr103() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(verliesBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                        LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);

        // assertAantalErrors(1);
        assertPreconditie(Precondities.PRE052);
    }

    @Test
    public void testContr104() {
        final Lo3Categorie<Lo3NationaliteitInhoud> onjuist1 =
                Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(), Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 19990101, "Doc"), LO3_HERKOMST_NATIONALITEIT);
        final Lo3Categorie<Lo3NationaliteitInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(), Lo3StapelHelper.lo3His("O", 19990101, 20000101),
                        Lo3StapelHelper.lo3Doc(2L, "0514", 19990101, "Doc"), LO3_HERKOMST_NATIONALITEIT);
        final Lo3Categorie<Lo3NationaliteitInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 19990101, 20000101),
                        Lo3StapelHelper.lo3Doc(3L, "0514", 19990101, "Doc"), LO3_HERKOMST_NATIONALITEIT);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapelOk = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2, juist3);

        precondities.controleerStapel(stapelOk);
        assertAantalErrors(0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapelNok = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2);

        precondities.controleerStapel(stapelNok);
        assertAantalErrors(2);
        assertPreconditie(Precondities.PRE055);
    }

    @Test
    public void testContr107a() {
        final Lo3NationaliteitInhoud.Builder builder = verliesBuilder();

        builder.setRedenVerkrijgingNederlandschapCode(new Lo3RedenNederlandschapCode("001"));
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);
        // assertAantalErrors(1);
        assertPreconditie(Precondities.PRE023);

    }

    @Test
    public void testContr107b() {
        final Lo3NationaliteitInhoud.Builder builder = verliesBuilder();
        builder.setNationaliteitCode(new Lo3NationaliteitCode("0027"));

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);
        // assertAantalErrors(1);
        assertPreconditie(Precondities.PRE023);
    }

    @Test
    public void testContr110() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20000101, 20000100),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE031);
    }

    @Test
    public void testContr112() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel2 =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 00000000, "Doc"),
                        LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel2);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000100, "Doc"),
                        LO3_HERKOMST_NATIONALITEIT));

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
    public void testContr222() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(),
                        Lo3StapelHelper.lo3His(null, null, 20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);

        assertPreconditie(Precondities.PRE030);
    }

    @Test
    public void testContr223() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20000101, null),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);

        assertPreconditie(Precondities.PRE031);
    }

    @Test
    public void testContr225() {
        final Lo3NationaliteitInhoud.Builder builder = buitenlandsBuilder();
        builder.setNationaliteitCode(new Lo3NationaliteitCode(Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE));
        builder.setRedenVerkrijgingNederlandschapCode(new Lo3RedenNederlandschapCode("001"));
        builder.setRedenVerliesNederlandschapCode(new Lo3RedenNederlandschapCode("072"));

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);

        // assertAantalErrors(1);
        assertPreconditie(Precondities.PRE023);
    }

    @Test
    public void testContr228() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(),
                        Lo3StapelHelper.lo3His("O", 20000101, 20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);

        assertPreconditie(Precondities.PRE055);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr40121() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20050155, "Doc"),
                        LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40124() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20040141, 20010101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20010101, "Doc"), LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40125() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20010101, 20040141),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20010101, "Doc"), LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4064() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(), Lo3StapelHelper
                        .lo3His(20000101), Lo3StapelHelper.lo3Documentatie(1L, null, null,
                        LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getCode(), 20010101, "Doc"),
                        LO3_HERKOMST_NATIONALITEIT));

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
    public void testContr415() {
        final Lo3NationaliteitInhoud.Builder builder = buitenlandsBuilder();
        builder.setNationaliteitCode(NationaliteitConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr447() {
        final Lo3NationaliteitInhoud.Builder builder = verkrijgingBuilder();
        builder.setRedenVerkrijgingNederlandschapCode(RedenVerkrijgingVerliesNlNationaliteitConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr448() {
        final Lo3NationaliteitInhoud.Builder builder = verliesBuilder();
        builder.setRedenVerliesNederlandschapCode(RedenVerkrijgingVerliesNlNationaliteitConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(2L, "0514", 20000101, "Doc"), LO3_HERKOMST_NATIONALITEIT),
                        Lo3StapelHelper.lo3Cat(verkrijgingBuilder().build(), Lo3StapelHelper.lo3His(19990101),
                                Lo3StapelHelper.lo3Doc(1L, "0514", 19990101, "Doc"), LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr449() {
        final Lo3NationaliteitInhoud.Builder builder = bijzonderBuilder();
        builder.setAanduidingBijzonderNederlandschap(new Lo3AanduidingBijzonderNederlandschap("Q"));

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr459() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(),
                        Lo3StapelHelper.lo3His("X", 20000101, 20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_NATIONALITEIT),
                        Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(), Lo3StapelHelper.lo3His(20010101),
                                Lo3StapelHelper.lo3Doc(2L, "0514", 20000101, "Doc"), LO3_HERKOMST_NATIONALITEIT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testBijzonderSituatiePersoonGeprivilegieerdHoofdletters() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(verkrijgingBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "PROBAS1234"),
                        LO3_HERKOMST_NATIONALITEIT));
        precondities.controleerStapel(stapel);

        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB012);
    }

    @Test
    public void testBijzonderSituatiePersoonGeprivilegieerdKleineletters() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(verkrijgingBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "probas1234"),
                        LO3_HERKOMST_NATIONALITEIT));
        precondities.controleerStapel(stapel);

        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB012);
    }

    @Test
    public void testBijzonderSituatiePersoonGeprivilegieerdMixedletters() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(verkrijgingBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "ProBas1234"),
                        LO3_HERKOMST_NATIONALITEIT));
        precondities.controleerStapel(stapel);

        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB012);
    }

    @Test
    public void testBijzonderSituatiePersoonNietGeprivilegieerd() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(verkrijgingBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "1234Probas"),
                        LO3_HERKOMST_NATIONALITEIT));
        precondities.controleerStapel(stapel);

        assertAantalInfos(0);
    }

    @Test
    public void testBijzonderSituatieDocumentBeschrijvingNull() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(verkrijgingBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, null),
                        LO3_HERKOMST_NATIONALITEIT));
        precondities.controleerStapel(stapel);

        assertAantalInfos(1);
    }
}
