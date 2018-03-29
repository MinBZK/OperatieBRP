/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud.Builder;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3PersoonslijstOpschoner;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import nl.bzk.migratiebrp.conversie.regels.tabel.LandConversietabel;
import nl.bzk.migratiebrp.conversie.regels.tabel.NationaliteitConversietabel;
import nl.bzk.migratiebrp.conversie.regels.tabel.RedenVerkrijgingNLNationaliteitConversietabel;
import nl.bzk.migratiebrp.conversie.regels.tabel.RedenVerliesNLNationaliteitConversietabel;

import org.junit.Test;

/**
 * Preconditie tests voor categorie 04: nationaliteit.
 */
public class Lo3NationaliteitPreconditiesTest extends AbstractPreconditieTest {

    private static final String GEM_CODE = "0514";
    private static final String DOC = "Doc";
    private static final Lo3Documentatie DOCUMENTATIE = Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20010101, DOC);
    private static final Lo3RedenNederlandschapCode REDEN_OPNAME_NATIONALITEIT_CODE = new Lo3RedenNederlandschapCode("001");
    private static final Lo3RedenNederlandschapCode REDEN_BEEINDIGING_NATIONALITEIT_CODE = new Lo3RedenNederlandschapCode("072");
    private static final Lo3AanduidingBijzonderNederlandschap AANDUIDING_BIJZONDER_NEDERLANDSCHAP = new Lo3AanduidingBijzonderNederlandschap("B");
    private static final String NAT_CODE2 = "0321";
    private static final String NAT_CODE = "0123";
    private static final Lo3Herkomst LO3_HERKOMST_S0_V0 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0);
    private static final Lo3Herkomst LO3_HERKOMST_S0_V1 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1);
    private static final Lo3Herkomst LO3_HERKOMST_S0_V2 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 2);
    private static final Lo3Herkomst LO3_HERKOMST_S1_V0 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 1, 0);
    private static final Lo3Herkomst LO3_HERKOMST_S1_V1 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 1, 1);


    private Lo3NationaliteitPrecondities precondities = new Lo3NationaliteitPrecondities(new ConversietabelFactoryImpl());

    private final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();

    private Lo3NationaliteitInhoud.Builder nederlandsBuilder() {
        final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder();
        builder.nationaliteitCode(new Lo3NationaliteitCode("0001"));
        builder.redenVerkrijgingNederlandschapCode(REDEN_OPNAME_NATIONALITEIT_CODE);

        return builder;
    }

    private Lo3NationaliteitInhoud.Builder buitenlandsBuilder() {
        final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder();
        builder.nationaliteitCode(new Lo3NationaliteitCode("0028"));

        return builder;
    }

    private Lo3NationaliteitInhoud.Builder verkrijgingBuilder() {
        final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder();
        builder.nationaliteitCode(Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE);
        builder.redenVerkrijgingNederlandschapCode(REDEN_OPNAME_NATIONALITEIT_CODE);

        return builder;
    }

    private Lo3NationaliteitInhoud.Builder verliesBuilder() {
        final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder();
        builder.redenVerliesNederlandschapCode(REDEN_BEEINDIGING_NATIONALITEIT_CODE);

        return builder;
    }

    private Lo3NationaliteitInhoud.Builder bijzonderBuilder() {
        final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder();
        builder.aanduidingBijzonderNederlandschap(AANDUIDING_BIJZONDER_NEDERLANDSCHAP);

        return builder;
    }

    private Lo3NationaliteitInhoud.Builder staatloosBuilder() {
        final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder();
        builder.nationaliteitCode(new Lo3NationaliteitCode(Lo3NationaliteitCode.NATIONALITEIT_CODE_STAATLOOS));

        return builder;
    }

    @Test
    public void testHappyBuitenland() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testHappyVerkrijging() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(verkrijgingBuilder().build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testHappyVerlies() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                verliesBuilder().build(),
                                Lo3StapelHelper.lo3Doc(2L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_S0_V0),
                        Lo3StapelHelper.lo3Cat(
                                verkrijgingBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 19990101, DOC),
                                Lo3StapelHelper.lo3His(19990101),
                                LO3_HERKOMST_S0_V1));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testHappyBijzonder() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(bijzonderBuilder().build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0));

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
        builder.nationaliteitCode(null);
        builder.redenVerkrijgingNederlandschapCode(null);
        final Lo3NationaliteitInhoud inhoud = builder.build();
        // Stapel met 2 voorkomens de oudste heeft de nieuwste opneming datum
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                verkrijgingBuilder().build(),
                                Lo3StapelHelper.lo3Akt(1),
                                Lo3StapelHelper.lo3His(null, 20000101, 20131205),
                                LO3_HERKOMST_S0_V1),
                        Lo3StapelHelper.lo3Cat(inhoud, Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(null, 20010101, 20121205), LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapel);

        assertAantalWarnings(0);
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE050, 1);
    }

    @Test
    public void testContr101() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapelBeeindigingOk =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V1),
                        Lo3StapelHelper.lo3Cat(
                                new Lo3NationaliteitInhoud.Builder().build(),
                                Lo3StapelHelper.lo3Doc(2L, GEM_CODE, 20010101, DOC),
                                Lo3StapelHelper.lo3His(20010101),
                                LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapelBeeindigingOk);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                new Lo3NationaliteitInhoud.Builder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapelNok);

        assertAantalWarnings(0);
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE050, 1);
    }

    @Test
    public void testContr102() {

        final Lo3Stapel<Lo3NationaliteitInhoud> enkeleNationaliteit =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V1),
                        Lo3StapelHelper.lo3Cat(
                                buitenlandsBuilder().build(),
                                Lo3StapelHelper.lo3Doc(2L, GEM_CODE, 20010101, DOC),
                                Lo3StapelHelper.lo3His(20010101),
                                LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(enkeleNationaliteit);

        assertAantalErrors(0);

        final Lo3NationaliteitInhoud.Builder builder = buitenlandsBuilder();
        builder.nationaliteitCode(new Lo3NationaliteitCode("0029"));

        final Lo3Stapel<Lo3NationaliteitInhoud> meedereNationaliteit =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_S0_V1),
                        Lo3StapelHelper.lo3Cat(
                                buitenlandsBuilder().build(),
                                Lo3StapelHelper.lo3Doc(2L, GEM_CODE, 20010101, DOC),
                                Lo3StapelHelper.lo3His(20010101),
                                LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(meedereNationaliteit);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE051, 1);
    }

    @Test
    public void testPre052VerkrijgingOnjuist() {
        final Lo3Categorie<Lo3NationaliteitInhoud> cat54 =
                Lo3StapelHelper.lo3Cat(
                        verkrijgingBuilder().build(),
                        Lo3StapelHelper.lo3Doc(2L, GEM_CODE, 19990101, "Doc1"),
                        Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1));

        final Lo3Categorie<Lo3NationaliteitInhoud> cat04 =
                Lo3StapelHelper.lo3Cat(
                        verliesBuilder().build(),
                        Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_S0_V0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(cat54, cat04);

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE112, 1);
    }

    @Test
    public void testContr104() {
        Lo3Categorie<Lo3NationaliteitInhoud> onjuist1 =
                Lo3StapelHelper.lo3Cat(
                        buitenlandsBuilder().build(),
                        Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 19990101, DOC),
                        Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        LO3_HERKOMST_S0_V2);
        final Lo3Categorie<Lo3NationaliteitInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(
                        buitenlandsBuilder().build(),
                        Lo3StapelHelper.lo3Doc(2L, GEM_CODE, 19990101, DOC),
                        Lo3StapelHelper.lo3His("O", 19990101, 20000101),
                        LO3_HERKOMST_S0_V1);
        final Lo3Categorie<Lo3NationaliteitInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(
                        buitenlandsBuilder().build(),
                        Lo3StapelHelper.lo3Doc(3L, GEM_CODE, 19990101, DOC),
                        Lo3StapelHelper.lo3His(null, 19990101, 20000101),
                        LO3_HERKOMST_S0_V0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapelOk = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2, juist3);

        precondities.controleerStapel(stapelOk);
        assertAantalErrors(0);

        onjuist1 =
                Lo3StapelHelper.lo3Cat(
                        buitenlandsBuilder().build(),
                        Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 19990101, DOC),
                        Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        LO3_HERKOMST_S0_V0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapelNok = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2);

        precondities.controleerStapel(stapelNok);
        assertSoortMeldingCode(SoortMeldingCode.PRE055, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE023)
    public void testPreconditie023RedenVerkrijgingIngevuld() {
        final Lo3NationaliteitInhoud.Builder builder = verliesBuilder();
        builder.redenVerkrijgingNederlandschapCode(REDEN_OPNAME_NATIONALITEIT_CODE);
        builder.nationaliteitCode(Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE);

        final Lo3Categorie<Lo3NationaliteitInhoud> categorie =
                Lo3StapelHelper.lo3Cat(builder.build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(categorie);

        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE023, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE023)
    public void testPreconditie023NationaliteitIngevuld() {
        final Lo3NationaliteitInhoud.Builder builder = verliesBuilder();
        builder.nationaliteitCode(new Lo3NationaliteitCode("0027"));

        final Lo3Categorie<Lo3NationaliteitInhoud> categorie =
                Lo3StapelHelper.lo3Cat(builder.build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(categorie);

        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE023, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE023)
    public void testPreconditie023AanduidingBijzonderNederlanderSchapIngevuld() {
        final Lo3NationaliteitInhoud.Builder builder = verliesBuilder();
        builder.aanduidingBijzonderNederlandschap(AANDUIDING_BIJZONDER_NEDERLANDSCHAP);

        final Lo3Categorie<Lo3NationaliteitInhoud> categorie =
                Lo3StapelHelper.lo3Cat(builder.build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(categorie);

        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE023, 1);
    }

    @Test
    public void testContr110() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                buitenlandsBuilder().build(),
                                DOCUMENTATIE,
                                Lo3StapelHelper.lo3His(null, 20000101, 20000100),
                                LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE031, 1);
    }

    @Test
    public void testContr112() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel2 =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                buitenlandsBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 0, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapel2);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                buitenlandsBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000100, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE067, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr222() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(null, null, 20000101), LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE030, 1);
    }

    @Test
    public void testContr223() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(buitenlandsBuilder().build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(null, 20000101, null), LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE031, 1);
    }

    @Test
    public void testContr225() {
        final Lo3NationaliteitInhoud.Builder builder = buitenlandsBuilder();
        builder.nationaliteitCode(Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE);
        builder.redenVerkrijgingNederlandschapCode(REDEN_OPNAME_NATIONALITEIT_CODE);
        builder.redenVerliesNederlandschapCode(new Lo3RedenNederlandschapCode("072"));

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE023, 1);
    }

    @Test
    public void testContr228() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                buitenlandsBuilder().build(),
                                DOCUMENTATIE,
                                Lo3StapelHelper.lo3His("O", 20000101, 20000101),
                                LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE055, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr40121() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                buitenlandsBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20050155, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40124() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                buitenlandsBuilder().build(),
                                DOCUMENTATIE,
                                Lo3StapelHelper.lo3His(null, 20040141, 20010101),
                                LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40125() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                buitenlandsBuilder().build(),
                                DOCUMENTATIE,
                                Lo3StapelHelper.lo3His(null, 20010101, 20040141),
                                LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4064() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                buitenlandsBuilder().build(),
                                Lo3StapelHelper.lo3Documentatie(1L, null, null, LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getWaarde(), 20010101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr415() {
        final Lo3NationaliteitInhoud.Builder builder = buitenlandsBuilder();
        builder.nationaliteitCode(NationaliteitConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr447() {
        final Lo3NationaliteitInhoud.Builder builder = verkrijgingBuilder();
        builder.redenVerkrijgingNederlandschapCode(RedenVerkrijgingNLNationaliteitConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr448() {
        final Lo3NationaliteitInhoud.Builder builder = verliesBuilder();
        builder.redenVerliesNederlandschapCode(RedenVerliesNLNationaliteitConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(2L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_S0_V0),
                        Lo3StapelHelper.lo3Cat(
                                verkrijgingBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 19990101, DOC),
                                Lo3StapelHelper.lo3His(19990101),
                                LO3_HERKOMST_S0_V1));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr449() {
        final Lo3NationaliteitInhoud.Builder builder = bijzonderBuilder();
        builder.aanduidingBijzonderNederlandschap(new Lo3AanduidingBijzonderNederlandschap("Q"));

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB012)
    public void testBijzonderSituatiePersoonGeprivilegieerdHoofdletters() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                verkrijgingBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, "PROBAS1234"),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_S0_V0));
        precondities.controleerStapel(stapel);

        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB012, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB012)
    public void testBijzonderSituatiePersoonGeprivilegieerdKleineletters() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                verkrijgingBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, "probas1234"),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_S0_V0));
        precondities.controleerStapel(stapel);

        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB012, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB012)
    public void testBijzonderSituatiePersoonGeprivilegieerdMixedletters() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                verkrijgingBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, "ProBas1234"),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_S0_V0));
        precondities.controleerStapel(stapel);

        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB012, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB012)
    public void testBijzonderSituatiePersoonNietGeprivilegieerd() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                verkrijgingBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, "1234Probas"),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_S0_V0));
        precondities.controleerStapel(stapel);
        assertAantalInfos(0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB012)
    public void testBijzonderSituatieDocumentBeschrijvingNull() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                verkrijgingBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, null),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_S0_V0));
        precondities.controleerStapel(stapel);
        assertAantalInfos(0);
    }

    @Test
    public void testGeenPre051OnjuisteHistorie() throws OngeldigePersoonslijstException {
        final Lo3Herkomst herkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat04 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null),
                        null,
                        Lo3StapelHelper.lo3His(20120101),
                        LO3_HERKOMST_S0_V0);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat54 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE2), null, null, null, null),
                        null,
                        Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        herkomst);
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(cat54, cat04);
        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE051, 1);

        final Lo3Persoonslijst pl = new Lo3PersoonslijstBuilder().nationaliteitStapel(stapel).build();
        opschoner.opschonen(pl);
        assertAantalErrors(0);
        assertSoortMeldingCode(SoortMeldingCode.PRE051, 1);

        final LogRegel regel = Logging.getLogging().getRegels().iterator().next();
        final Lo3Herkomst foutHerkomst = regel.getLo3Herkomst();
        Assert.assertEquals(herkomst, foutHerkomst);
    }

    @Test
    public void testWelPre051JuisteHistorie() {
        final Lo3Herkomst herkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat04 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null),
                        null,
                        Lo3StapelHelper.lo3His(20120101),
                        LO3_HERKOMST_S0_V0);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat54 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE2), null, null, null, null),
                        null,
                        Lo3StapelHelper.lo3His(19990101),
                        herkomst);
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(cat54, cat04);
        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE051, 1);
        final LogRegel regel = Logging.getLogging().getRegels().iterator().next();
        final Lo3Herkomst foutHerkomst = regel.getLo3Herkomst();
        Assert.assertEquals(herkomst, foutHerkomst);
    }

    @Test
    public void testPreconditie082Happy() {
        final Lo3Categorie<Lo3NationaliteitInhoud> cat04Stapel1 =
                Lo3StapelHelper.lo3Cat(new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null), Lo3CategorieEnum.CATEGORIE_04);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat54Stapel1 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1));

        final Lo3Categorie<Lo3NationaliteitInhoud> cat04Stapel2 =
                Lo3StapelHelper.lo3Cat(new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE2), null, null, null, null), Lo3CategorieEnum.CATEGORIE_04);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat54Stapel2 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null),
                        Lo3StapelHelper.lo3Akt(2),
                        Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1));

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel1 = Lo3StapelHelper.lo3Stapel(cat54Stapel1, cat04Stapel1);
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel2 = Lo3StapelHelper.lo3Stapel(cat54Stapel2, cat04Stapel2);
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels = new ArrayList<>();
        stapels.add(stapel1);
        stapels.add(stapel2);
        precondities.controleerStapels(stapels);

        assertSoortMeldingCode(SoortMeldingCode.PRE082, 0);
    }

    @Test
    public void testPreconditie082LegeRijen() {
        final Lo3Categorie<Lo3NationaliteitInhoud> stapel1Voorkomen0 =
                Lo3StapelHelper.lo3Cat(new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null), Lo3CategorieEnum.CATEGORIE_04);
        final Lo3Categorie<Lo3NationaliteitInhoud> stapel1Voorkomen1 =
                Lo3StapelHelper.lo3Cat(new Lo3NationaliteitInhoud(null, null, null, null, null), Lo3CategorieEnum.CATEGORIE_04);
        final Lo3Categorie<Lo3NationaliteitInhoud> stapel1Voorkomen2 =
                Lo3StapelHelper.lo3Cat(new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null), Lo3CategorieEnum.CATEGORIE_04);

        final Lo3Categorie<Lo3NationaliteitInhoud> stapel2Voorkomen0 =
                Lo3StapelHelper.lo3Cat(new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE2), null, null, null, null), Lo3CategorieEnum.CATEGORIE_04);
        final Lo3Categorie<Lo3NationaliteitInhoud> stapel2Voorkomen1 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0));
        final Lo3Categorie<Lo3NationaliteitInhoud> stapel2Voorkomen2 =
                Lo3StapelHelper.lo3Cat(new Lo3NationaliteitInhoud(null, null, null, null, null), Lo3CategorieEnum.CATEGORIE_04);
        final Lo3Categorie<Lo3NationaliteitInhoud> stapel2Voorkomen3 =
                Lo3StapelHelper.lo3Cat(new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE2), null, null, null, null), Lo3CategorieEnum.CATEGORIE_04);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel1 = Lo3StapelHelper.lo3Stapel(stapel1Voorkomen2, stapel1Voorkomen1, stapel1Voorkomen0);
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel2 =
                Lo3StapelHelper.lo3Stapel(stapel2Voorkomen3, stapel2Voorkomen2, stapel2Voorkomen1, stapel2Voorkomen0);
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels = new ArrayList<>();
        stapels.add(stapel1);
        stapels.add(stapel2);
        precondities.controleerStapels(stapels);

        assertSoortMeldingCode(SoortMeldingCode.PRE082, 0);
    }

    @Test
    public void testPreconditie082ZelfdeNatInStapels() {
        final Lo3Herkomst herkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat04 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null),
                        Lo3StapelHelper.lo3Akt(2),
                        Lo3StapelHelper.lo3His(20120101),
                        LO3_HERKOMST_S0_V0);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat54 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(19990101),
                        herkomst);
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel1 = Lo3StapelHelper.lo3Stapel(cat54, cat04);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel2 = Lo3StapelHelper.lo3Stapel(cat54, cat04);
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels = new ArrayList<>();
        stapels.add(stapel1);
        stapels.add(stapel2);
        precondities.controleerStapels(stapels);

        assertAantalErrors(2);
        assertSoortMeldingCode(SoortMeldingCode.PRE082, 2);
    }

    @Test
    public void testPreconditie093ZelfdeNatInStapels1() {
        final Lo3Herkomst herkomst0000 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0);
        final Lo3Herkomst herkomst0001 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat0400 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null),
                        Lo3StapelHelper.lo3Akt(2),
                        Lo3StapelHelper.lo3His(20120101),
                        herkomst0000);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat5400 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        herkomst0001);
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel1 = Lo3StapelHelper.lo3Stapel(cat5400, cat0400);

        final Lo3Herkomst herkomst0100 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 1, 0);
        final Lo3Herkomst herkomst0101 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 1, 1);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat0401 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null),
                        Lo3StapelHelper.lo3Akt(2),
                        Lo3StapelHelper.lo3His(20120101),
                        herkomst0100);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat5401 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        herkomst0101);
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel2 = Lo3StapelHelper.lo3Stapel(cat5401, cat0401);

        final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels = new ArrayList<>();
        stapels.add(stapel1);
        stapels.add(stapel2);
        precondities.controleerStapels(stapels);

        assertAantalErrors(3);
        assertSoortMeldingCode(SoortMeldingCode.PRE082, 1);
        assertSoortMeldingCode(SoortMeldingCode.PRE093, 2);
    }

    @Test
    public void testPreconditie093ZelfdeNatInStapels2() {
        final Lo3Herkomst herkomst0000 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0);
        final Lo3Herkomst herkomst0001 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat0400 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null),
                        Lo3StapelHelper.lo3Akt(2),
                        Lo3StapelHelper.lo3His("O", 20120101, 20120101),
                        herkomst0000);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat5400 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        herkomst0001);
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel1 = Lo3StapelHelper.lo3Stapel(cat5400, cat0400);

        final Lo3Herkomst herkomst0100 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 1, 0);
        final Lo3Herkomst herkomst0101 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 1, 1);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat0401 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null),
                        Lo3StapelHelper.lo3Akt(2),
                        Lo3StapelHelper.lo3His("O", 20120101, 20120101),
                        herkomst0100);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat5401 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        herkomst0101);
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel2 = Lo3StapelHelper.lo3Stapel(cat5401, cat0401);

        final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels = new ArrayList<>();
        stapels.add(stapel1);
        stapels.add(stapel2);
        precondities.controleerStapels(stapels);

        assertAantalErrors(8);
        assertSoortMeldingCode(SoortMeldingCode.PRE093, 4);
        assertSoortMeldingCode(SoortMeldingCode.PRE055, 2);
        assertSoortMeldingCode(SoortMeldingCode.PRE112, 2);
    }

    @Test
    public void testPreconditie082HappyLeeg() {
        final Lo3Categorie<Lo3NationaliteitInhoud> cat04Stapel1 =
                Lo3StapelHelper.lo3Cat(new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null), Lo3CategorieEnum.CATEGORIE_04);

        final Lo3Categorie<Lo3NationaliteitInhoud> cat04Stapel2 = Lo3StapelHelper.lo3Cat(new Lo3NationaliteitInhoud(), Lo3CategorieEnum.CATEGORIE_04);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat54Stapel2 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(NAT_CODE), null, null, null, null),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1));

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel1 = Lo3StapelHelper.lo3Stapel(cat04Stapel1);
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel2 = Lo3StapelHelper.lo3Stapel(cat54Stapel2, cat04Stapel2);
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels = new ArrayList<>();
        stapels.add(stapel1);
        stapels.add(stapel2);
        precondities.controleerStapels(stapels);

        assertSoortMeldingCode(SoortMeldingCode.PRE082, 0);
    }

    @Test
    public void testPre083() {
        final Lo3NationaliteitInhoud.Builder builder = verkrijgingBuilder();
        builder.nationaliteitCode(null);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_04));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE083, 1);
    }

    @Test
    public void testPre083MetBijzonderNederlanderschap() {
        final Lo3NationaliteitInhoud.Builder builder = verkrijgingBuilder();
        builder.nationaliteitCode(null);
        builder.aanduidingBijzonderNederlandschap(AANDUIDING_BIJZONDER_NEDERLANDSCHAP);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_04));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE083, 0);
    }

    @Test
    public void testPre083ZonderBijzonderNederlanderschap() {
        final Lo3NationaliteitInhoud.Builder builder = verkrijgingBuilder();
        builder.nationaliteitCode(null);
        builder.aanduidingBijzonderNederlandschap(null);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_04));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE083, 1);
    }

    @Test
    public void testStaatloos() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(staatloosBuilder().build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testPreconditie098GroepNietAanwezig() {
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(verkrijgingBuilder().build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0));
        precondities.controleerStapel(stapel);
        assertAantalErrors(0);
        assertSoortMeldingCode(SoortMeldingCode.PRE098, 0);
    }

    @Test
    public void testPreconditie098GroepAanwezig8810NietGevuld() {
        final Lo3Documentatie lo3Doc =
                Lo3StapelHelper.lo3Documentatie(1L, "0518", "1-X" + String.format("%04d", 1), null, null, null, null, "omschrijvingVerdrag");
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(verkrijgingBuilder().build(), lo3Doc, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0));
        precondities.controleerStapel(stapel);
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE098, 1);
    }

    @Test
    public void testPreconditie098GroepAanwezig8810Gevuld() {
        final Lo3Documentatie lo3Doc = Lo3StapelHelper.lo3Documentatie(1L, "0518", "1-X" + String.format("%04d", 1), null, null, null, "0000", null);
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(verkrijgingBuilder().build(), lo3Doc, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0));
        precondities.controleerStapel(stapel);
        assertAantalErrors(0);
        assertSoortMeldingCode(SoortMeldingCode.PRE098, 0);
    }

    @Test
    public void testControleerGroep83Procedure8320NietGevuld() {
        final Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10110), null, null);
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                verkrijgingBuilder().build(),
                                DOCUMENTATIE,
                                lo3Onderzoek,
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_S0_V0));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE099, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE103)
    public void testPreconditie103() {
        final Lo3Categorie<Lo3NationaliteitInhoud> cat04Stapel1 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE, null, null, null, null),
                        Lo3CategorieEnum.CATEGORIE_04);

        final Lo3Categorie<Lo3NationaliteitInhoud> cat04Stapel2 =
                Lo3StapelHelper.lo3Cat(new Lo3NationaliteitInhoud(null, null, null, AANDUIDING_BIJZONDER_NEDERLANDSCHAP, null), Lo3CategorieEnum.CATEGORIE_04);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel1 = Lo3StapelHelper.lo3Stapel(cat04Stapel1);
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel2 = Lo3StapelHelper.lo3Stapel(cat04Stapel2);
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels = new ArrayList<>();
        stapels.add(stapel1);
        stapels.add(stapel2);
        precondities.controleerStapels(stapels);

        assertSoortMeldingCode(SoortMeldingCode.PRE103, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE103)
    public void testPreconditie103TreedNietOpDubbeleNLStapel() {
        final Lo3Categorie<Lo3NationaliteitInhoud> cat04Stapel1 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE, null, null, null, null),
                        Lo3CategorieEnum.CATEGORIE_04);

        final Lo3Categorie<Lo3NationaliteitInhoud> cat04Stapel2 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE, null, null, null, null),
                        Lo3CategorieEnum.CATEGORIE_04);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel1 = Lo3StapelHelper.lo3Stapel(cat04Stapel1);
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel2 = Lo3StapelHelper.lo3Stapel(cat04Stapel2);
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels = new ArrayList<>();
        stapels.add(stapel1);
        stapels.add(stapel2);
        precondities.controleerStapels(stapels);

        assertSoortMeldingCode(SoortMeldingCode.PRE103, 0);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE103)
    public void testPreconditie103TreedNietOpAllesOpEenStapel() {
        final Lo3Categorie<Lo3NationaliteitInhoud> cat04Stapel1 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE, null, null, null, null),
                        LO3_HERKOMST_S0_V0);

        final Lo3Categorie<Lo3NationaliteitInhoud> cat54Stapel1 =
                Lo3StapelHelper.lo3Cat(new Lo3NationaliteitInhoud(null, null, null, AANDUIDING_BIJZONDER_NEDERLANDSCHAP, null), LO3_HERKOMST_S0_V1);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel1 = Lo3StapelHelper.lo3Stapel(cat54Stapel1, cat04Stapel1);
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels = new ArrayList<>();
        stapels.add(stapel1);
        precondities.controleerStapels(stapels);

        assertSoortMeldingCode(SoortMeldingCode.PRE103, 0);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE103)
    public void testPreconditie103TreedNietOpDubbeleBijzonderNLStapel() {
        final Lo3Categorie<Lo3NationaliteitInhoud> cat04Stapel1 =
                Lo3StapelHelper.lo3Cat(new Lo3NationaliteitInhoud(null, null, null, AANDUIDING_BIJZONDER_NEDERLANDSCHAP, null), LO3_HERKOMST_S0_V0);

        final Lo3Categorie<Lo3NationaliteitInhoud> cat04Stapel2 =
                Lo3StapelHelper.lo3Cat(new Lo3NationaliteitInhoud(null, null, null, AANDUIDING_BIJZONDER_NEDERLANDSCHAP, null), LO3_HERKOMST_S1_V0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel1 = Lo3StapelHelper.lo3Stapel(cat04Stapel1);
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel2 = Lo3StapelHelper.lo3Stapel(cat04Stapel2);
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels = new ArrayList<>();
        stapels.add(stapel1);
        stapels.add(stapel2);
        precondities.controleerStapels(stapels);

        assertSoortMeldingCode(SoortMeldingCode.PRE103, 0);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE103)
    public void testPreconditie103OnjuisteBijzonderNL() {
        final Lo3Categorie<Lo3NationaliteitInhoud> cat04Stapel1 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE, null, null, null, null),
                        LO3_HERKOMST_S0_V0);

        final Lo3Categorie<Lo3NationaliteitInhoud> cat04Stapel2 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE, null, null, null, null),
                        LO3_HERKOMST_S1_V0);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat54Stapel2 =
                Lo3StapelHelper.lo3Cat(
                        new Lo3NationaliteitInhoud(null, null, null, AANDUIDING_BIJZONDER_NEDERLANDSCHAP, null),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        LO3_HERKOMST_S1_V1);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel1 = Lo3StapelHelper.lo3Stapel(cat04Stapel1);
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel2 = Lo3StapelHelper.lo3Stapel(cat54Stapel2, cat04Stapel2);
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels = new ArrayList<>();
        stapels.add(stapel1);
        stapels.add(stapel2);
        precondities.controleerStapels(stapels);

        assertSoortMeldingCode(SoortMeldingCode.PRE103, 0);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE104)
    public void testPreconditie104NationaliteitOnjuistBijzonderNederlanderschapJuist() {
        final Lo3NationaliteitInhoud.Builder builder = new Builder();
        final Lo3Categorie<Lo3NationaliteitInhoud> cat04Stapel1 = Lo3StapelHelper.lo3Cat(builder.build(), LO3_HERKOMST_S0_V0);

        builder.nationaliteitCode(Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat54Stapel1 = Lo3StapelHelper.lo3OnjuistCat(builder.build(), LO3_HERKOMST_S0_V1);

        builder.nationaliteitCode(null).aanduidingBijzonderNederlandschap(AANDUIDING_BIJZONDER_NEDERLANDSCHAP);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat04Stapel2 = Lo3StapelHelper.lo3Cat(builder.build(), LO3_HERKOMST_S1_V0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel1 = Lo3StapelHelper.lo3Stapel(cat54Stapel1, cat04Stapel1);
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel2 = Lo3StapelHelper.lo3Stapel(cat04Stapel2);

        final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels = new ArrayList<>();
        stapels.add(stapel1);
        stapels.add(stapel2);

        precondities.controleerStapels(stapels);
        assertSoortMeldingCode(SoortMeldingCode.PRE104, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE104)
    public void testPreconditie104BijzonderNederlanderschapOnjuistNationaliteitJuist() {
        final Lo3NationaliteitInhoud.Builder builder = new Builder();
        final Lo3Categorie<Lo3NationaliteitInhoud> cat04Stapel1 = Lo3StapelHelper.lo3Cat(builder.build(), LO3_HERKOMST_S0_V0);

        builder.nationaliteitCode(Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat54Stapel1 = Lo3StapelHelper.lo3OnjuistCat(builder.build(), LO3_HERKOMST_S0_V1);

        final Lo3Categorie<Lo3NationaliteitInhoud> cat04Stapel2 = Lo3StapelHelper.lo3Cat(builder.build(), LO3_HERKOMST_S1_V0);

        builder.aanduidingBijzonderNederlandschap(AANDUIDING_BIJZONDER_NEDERLANDSCHAP);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat54Stapel2 = Lo3StapelHelper.lo3OnjuistCat(builder.build(), LO3_HERKOMST_S1_V1);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel1 = Lo3StapelHelper.lo3Stapel(cat54Stapel1, cat04Stapel1);
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel2 = Lo3StapelHelper.lo3Stapel(cat54Stapel2, cat04Stapel2);

        final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels = new ArrayList<>();
        stapels.add(stapel1);
        stapels.add(stapel2);

        precondities.controleerStapels(stapels);
        assertSoortMeldingCode(SoortMeldingCode.PRE104, 2);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE104)
    public void testPreconditie104BijzonderNederlanderschapOnjuistNationaliteitOnJuist() {
        final Lo3NationaliteitInhoud.Builder builder = new Builder();
        final Lo3Categorie<Lo3NationaliteitInhoud> cat04Stapel1 = Lo3StapelHelper.lo3Cat(builder.build(), LO3_HERKOMST_S0_V0);

        builder.aanduidingBijzonderNederlandschap(AANDUIDING_BIJZONDER_NEDERLANDSCHAP);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat54Stapel1 = Lo3StapelHelper.lo3OnjuistCat(builder.build(), LO3_HERKOMST_S0_V1);

        builder.aanduidingBijzonderNederlandschap(null).nationaliteitCode(Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE);
        final Lo3Categorie<Lo3NationaliteitInhoud> cat04Stapel2 = Lo3StapelHelper.lo3Cat(builder.build(), LO3_HERKOMST_S1_V0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel1 = Lo3StapelHelper.lo3Stapel(cat54Stapel1, cat04Stapel1);
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel2 = Lo3StapelHelper.lo3Stapel(cat04Stapel2);

        final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels = new ArrayList<>();
        stapels.add(stapel1);
        stapels.add(stapel2);

        precondities.controleerStapels(stapels);
        assertSoortMeldingCode(SoortMeldingCode.PRE104, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE105)
    public void testPreconditie105NationaliteitIngevuld() {
        final Lo3NationaliteitInhoud.Builder builder = bijzonderBuilder();
        builder.nationaliteitCode(Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE);

        final Lo3Categorie<Lo3NationaliteitInhoud> categorie =
                Lo3StapelHelper.lo3Cat(builder.build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(categorie);

        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE105, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE105)
    public void testPreconditie105RedenVerkrijgingIngevuld() {
        final Lo3NationaliteitInhoud.Builder builder = bijzonderBuilder();
        builder.redenVerkrijgingNederlandschapCode(REDEN_OPNAME_NATIONALITEIT_CODE);

        final Lo3Categorie<Lo3NationaliteitInhoud> categorie =
                Lo3StapelHelper.lo3Cat(builder.build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(categorie);

        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE105, 0);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE105)
    public void testPreconditie105RedenVerliesIngevuld() {
        final Lo3NationaliteitInhoud.Builder builder = bijzonderBuilder();
        builder.redenVerliesNederlandschapCode(REDEN_BEEINDIGING_NATIONALITEIT_CODE);

        final Lo3Categorie<Lo3NationaliteitInhoud> categorie =
                Lo3StapelHelper.lo3Cat(builder.build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(categorie);

        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE105, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE106)
    public void testPreconditie106RedenVerliesIngevuld() {
        final Lo3NationaliteitInhoud.Builder builder = verkrijgingBuilder();
        builder.redenVerliesNederlandschapCode(REDEN_BEEINDIGING_NATIONALITEIT_CODE);

        final Lo3Categorie<Lo3NationaliteitInhoud> categorie =
                Lo3StapelHelper.lo3Cat(builder.build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(categorie);

        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE106, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE106)
    public void testPreconditie106AanduidingBijzonderNederlanderschapIngevuld() {
        final Lo3NationaliteitInhoud.Builder builder = verkrijgingBuilder();
        builder.aanduidingBijzonderNederlandschap(AANDUIDING_BIJZONDER_NEDERLANDSCHAP);

        final Lo3Categorie<Lo3NationaliteitInhoud> categorie =
                Lo3StapelHelper.lo3Cat(builder.build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(categorie);

        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE106, 1);
    }

    @Test
    @Preconditie({SoortMeldingCode.PRE109, SoortMeldingCode.PRE110})
    public void testPreconditie109() {
        final Lo3NationaliteitInhoud.Builder builder1 = new Lo3NationaliteitInhoud.Builder();
        builder1.nationaliteitCode(new Lo3NationaliteitCode("1234"));

        final Lo3Categorie<Lo3NationaliteitInhoud> categorie1 =
                Lo3StapelHelper.lo3Cat(builder1.build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0);

        final Lo3NationaliteitInhoud.Builder builder2 = new Lo3NationaliteitInhoud.Builder();
        builder2.aanduidingBijzonderNederlandschap(AANDUIDING_BIJZONDER_NEDERLANDSCHAP);

        final Lo3Categorie<Lo3NationaliteitInhoud> categorie2 =
                Lo3StapelHelper.lo3Cat(builder2.build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(categorie1, categorie2);

        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE109, 1);
        assertSoortMeldingCode(SoortMeldingCode.PRE110, 0);
    }

    @Test
    @Preconditie({SoortMeldingCode.PRE109, SoortMeldingCode.PRE110})
    public void testPreconditie110BijzonderNederlanderschapJuist() {
        final Lo3NationaliteitInhoud.Builder builder1 = new Lo3NationaliteitInhoud.Builder();
        builder1.nationaliteitCode(new Lo3NationaliteitCode("1234"));

        final Lo3Categorie<Lo3NationaliteitInhoud> categorie1 =
                Lo3StapelHelper.lo3Cat(builder1.build(), DOCUMENTATIE, Lo3StapelHelper.lo3His("O", 20000101, 20000101), LO3_HERKOMST_S0_V0);

        final Lo3NationaliteitInhoud.Builder builder2 = new Lo3NationaliteitInhoud.Builder();
        builder2.aanduidingBijzonderNederlandschap(AANDUIDING_BIJZONDER_NEDERLANDSCHAP);

        final Lo3Categorie<Lo3NationaliteitInhoud> categorie2 =
                Lo3StapelHelper.lo3Cat(builder2.build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(categorie1, categorie2);

        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE109, 0);
        assertSoortMeldingCode(SoortMeldingCode.PRE110, 1);
    }

    @Test
    @Preconditie({SoortMeldingCode.PRE109, SoortMeldingCode.PRE110})
    public void testPreconditie110BijzonderNederlanderschapOnjuist() {
        final Lo3NationaliteitInhoud.Builder builder1 = new Lo3NationaliteitInhoud.Builder();
        builder1.nationaliteitCode(new Lo3NationaliteitCode("1234"));

        final Lo3Categorie<Lo3NationaliteitInhoud> categorie1 =
                Lo3StapelHelper.lo3Cat(builder1.build(), DOCUMENTATIE, Lo3StapelHelper.lo3His("O", 20000101, 20000101), LO3_HERKOMST_S0_V0);

        final Lo3NationaliteitInhoud.Builder builder2 = new Lo3NationaliteitInhoud.Builder();
        builder2.aanduidingBijzonderNederlandschap(AANDUIDING_BIJZONDER_NEDERLANDSCHAP);

        final Lo3Categorie<Lo3NationaliteitInhoud> categorie2 =
                Lo3StapelHelper.lo3Cat(builder2.build(), DOCUMENTATIE, Lo3StapelHelper.lo3His("O", 20000101, 20000101), LO3_HERKOMST_S0_V0);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(categorie1, categorie2);

        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE109, 0);
        assertSoortMeldingCode(SoortMeldingCode.PRE110, 2);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE112)
    public void testPreconditie112GeenOnjuistVoorkomens() {
        final Lo3Categorie<Lo3NationaliteitInhoud> categorieLeeg =
                Lo3StapelHelper.lo3Cat(verliesBuilder().build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000102), LO3_HERKOMST_S0_V0);
        final Lo3Categorie<Lo3NationaliteitInhoud> categorieGevuld =
                Lo3StapelHelper.lo3Cat(nederlandsBuilder().build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V1);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(categorieLeeg, categorieGevuld);
        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE112, 0);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE112)
    public void testPreconditie112OnjuistVoorkomensLegeRij() {
        final Lo3Categorie<Lo3NationaliteitInhoud> categorieLeeg =
                Lo3StapelHelper.lo3Cat(verliesBuilder().build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_S0_V0);
        final Lo3Categorie<Lo3NationaliteitInhoud> categorieGevuld =
                Lo3StapelHelper.lo3Cat(nederlandsBuilder().build(), DOCUMENTATIE, Lo3StapelHelper.lo3His("O", 20000101, 20000102), LO3_HERKOMST_S0_V1);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(categorieLeeg, categorieGevuld);
        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE112, 0);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE112)
    public void testPreconditie112OnjuistVoorkomensLegeRijAndereDatum() {
        final Lo3Categorie<Lo3NationaliteitInhoud> categorieLeeg =
                Lo3StapelHelper.lo3Cat(verliesBuilder().build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000102), LO3_HERKOMST_S0_V0);
        final Lo3Categorie<Lo3NationaliteitInhoud> categorieGevuld =
                Lo3StapelHelper.lo3Cat(nederlandsBuilder().build(), DOCUMENTATIE, Lo3StapelHelper.lo3His("O", 20000101, 20000102), LO3_HERKOMST_S0_V1);

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = Lo3StapelHelper.lo3Stapel(categorieLeeg, categorieGevuld);
        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE112, 1);
    }
}
