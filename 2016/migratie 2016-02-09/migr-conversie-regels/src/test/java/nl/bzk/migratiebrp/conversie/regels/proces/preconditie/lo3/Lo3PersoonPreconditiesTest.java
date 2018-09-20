/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.tabel.GemeenteConversietabel;
import nl.bzk.migratiebrp.conversie.regels.tabel.LandConversietabel;
import nl.bzk.migratiebrp.conversie.regels.tabel.VoorvoegselScheidingstekenConversietabel;
import org.junit.Test;

/**
 * Preconditie tests voor categorie 01: persoon.
 */
public class Lo3PersoonPreconditiesTest extends AbstractPreconditieTest {

    private static final String DOC = "Doc";
    private static final String GEMEENTE_CODE = "0514";
    private static final Lo3Herkomst LO3_HERKOMST_PERSOON = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
    private static final Lo3Herkomst LO3_HERKOMST_PERSOON_1 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 1);
    private static final Lo3Herkomst LO3_HERKOMST_PERSOON_2 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 2);
    private static final long ANUMMER = 1069532945L;

    @Inject
    private Lo3PersoonPrecondities precondities;

    private Lo3PersoonInhoud.Builder builder() {
        final Lo3PersoonInhoud.Builder builder = new Lo3PersoonInhoud.Builder();
        builder.setaNummer(Lo3Long.wrap(ANUMMER));
        builder.setBurgerservicenummer(Lo3Integer.wrap(179543489));
        builder.setVoornamen(Lo3String.wrap("Jaap"));
        builder.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("P"));
        builder.setVoorvoegselGeslachtsnaam(Lo3String.wrap("van"));
        builder.setGeslachtsnaam(Lo3String.wrap("Joppen"));
        builder.setGeboortedatum(new Lo3Datum(19940104));
        builder.setGeboorteGemeenteCode(new Lo3GemeenteCode(GEMEENTE_CODE));
        builder.setGeboorteLandCode(new Lo3LandCode("6030"));
        builder.setGeslachtsaanduiding(new Lo3Geslachtsaanduiding("M"));
        builder.setAanduidingNaamgebruikCode(new Lo3AanduidingNaamgebruikCode("E"));

        return builder;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testHappy() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel = createStapel(builder());

        precondities.controleerStapel(stapel);
        assertAantalInfos(0);
        assertAantalErrors(0);
    }

    private Lo3Stapel<Lo3PersoonInhoud> createStapel(final Lo3PersoonInhoud.Builder builder) {
        return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            builder.build(),
            Lo3StapelHelper.lo3Akt(1),
            Lo3StapelHelper.lo3His(20000101),
            LO3_HERKOMST_PERSOON));
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr104() {
        Lo3Categorie<Lo3PersoonInhoud> onjuist1 =
                Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                    LO3_HERKOMST_PERSOON_2);
        final Lo3Categorie<Lo3PersoonInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Akt(2),
                    Lo3StapelHelper.lo3His("O", 19990101, 20000101),
                    LO3_HERKOMST_PERSOON_1);
        final Lo3Categorie<Lo3PersoonInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Akt(3),
                    Lo3StapelHelper.lo3His(null, 19990101, 20000101),
                    LO3_HERKOMST_PERSOON);

        final Lo3Stapel<Lo3PersoonInhoud> stapelOk = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2, juist3);

        precondities.controleerStapel(stapelOk);
        assertAantalErrors(0);

        onjuist1 =
                Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His("O", 19990101, 19990101), LO3_HERKOMST_PERSOON);

        final Lo3Stapel<Lo3PersoonInhoud> stapelNok = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2);
        precondities.controleerStapel(stapelNok);
        assertSoortMeldingCode(SoortMeldingCode.PRE055, 1);
    }

    @Test
    public void testContr1091() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeboorteGemeenteCode(new Lo3GemeenteCode("Brussel"));
        builder.setGeboorteLandCode(new Lo3LandCode("5010"));

        final Lo3Stapel<Lo3PersoonInhoud> stapelBuitenlandsOk =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapelBuitenlandsOk);

        assertAantalErrors(0);

        builder.setGeboorteLandCode(new Lo3LandCode("6030"));

        final Lo3Stapel<Lo3PersoonInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapelNok);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE025, 1);
    }

    @Test
    public void testContr110() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(null, 20000101, 20000100),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE031, 1);
    }

    @Test
    public void testContr112() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel2 =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEMEENTE_CODE, 0, DOC),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel2);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEMEENTE_CODE, 20000100, DOC),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

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
    public void testContr201() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setaNummer(null);
        builder.setBurgerservicenummer(null);

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE005, 1);
    }

    @Test
    public void testContr202() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setVoornamen(null);
        builder.setAdellijkeTitelPredikaatCode(null);
        builder.setVoorvoegselGeslachtsnaam(null);
        builder.setGeslachtsnaam(null);

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE034, 1);
    }

    @Test
    public void testContr206() {
        final Lo3PersoonInhoud.Builder builder = builder();

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testContr207() {
        final Lo3PersoonInhoud.Builder builder = builder();

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(null, null, 20010101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE030, 1);
    }

    @Test
    public void testContr208() {
        final Lo3PersoonInhoud.Builder builder = builder();

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(null, null, 20010101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE030, 1);
    }

    @Test
    public void testContr209() {
        final Lo3PersoonInhoud.Builder builder = builder();

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His("O", 20010101, 20010101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE055, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr301() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setaNummer(null);

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE005, 1);
    }

    @Test
    public void testContr303() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeslachtsnaam(null);

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE034, 1);
    }

    @Test
    public void testContr304() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeboortedatum(null);

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE007, 1);
    }

    @Test
    public void testContr306() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeboortedatum(null);

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE007, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr4011() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeboortedatum(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40121() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEMEENTE_CODE, 20050155, DOC),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40124() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(null, 20040141, 20010101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40125() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(null, 20010101, 20040141),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4021BuitenlandOk() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeboorteGemeenteCode(new Lo3GemeenteCode("Brussel"));
        builder.setGeboorteLandCode(new Lo3LandCode("5010"));

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testContr4021() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeboorteGemeenteCode(GemeenteConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE025, 1);
    }

    @Test
    public void testContr4031() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeboorteLandCode(LandConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE008, 1);
    }

    @Test
    public void testContr4063() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Documentatie(1L, LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getWaarde(), "1-x0001", null, null, null),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr4064() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Documentatie(1L, null, null, LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getWaarde(), 20010101, DOC),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

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
    public void testContr411() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("QQ"));

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr412() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setVoorvoegselGeslachtsnaam(Lo3String.wrap(VoorvoegselScheidingstekenConversietabel.LO3_NIET_VALIDE_UITZONDERING));

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr414() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeslachtsaanduiding(new Lo3Geslachtsaanduiding("Q"));

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB001)
    public void testBijzondereSituatieGeboortelandStandaardWaarde() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeboorteLandCode(new Lo3LandCode("0000"));

        precondities.controleerStapel(createStapel(builder));
        assertAantalErrors(0);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB001, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB001)
    public void testBijzondereSituatieGeboortelandInternationaalGebied() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeboorteLandCode(new Lo3LandCode("9999"));

        precondities.controleerStapel(createStapel(builder));
        assertAantalErrors(0);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB001, 1);
    }

    /**
     * Test dat er geen sprake is van bijzondere situatie.
     */
    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB001)
    @Preconditie(SoortMeldingCode.PRE007)
    public void testBijzondereSituatieGeboortelandNull() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeboorteLandCode(null);

        precondities.controleerStapel(createStapel(builder));
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE007, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB005)
    public void testBijzondereSituatieVorigAnummerOngelijkAnummer() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setVorigANummer(Lo3Long.wrap(4254732321L));
        final Lo3Stapel<Lo3PersoonInhoud> stapel = createStapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB005, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB006)
    public void testBijzondereSituatieVorigAnummerGelijkAnummer() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setVorigANummer(Lo3Long.wrap(ANUMMER));
        final Lo3Stapel<Lo3PersoonInhoud> stapel = createStapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB006, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB007)
    public void testBijzondereSituatieVolgendAnummerGelijkAnummer() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setVolgendANummer(Lo3Long.wrap(ANUMMER));
        final Lo3Stapel<Lo3PersoonInhoud> stapel = createStapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB007, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB008)
    public void testBijzondereSituatieVolgendAnummerOngelijkAnummer() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setVolgendANummer(Lo3Long.wrap(2801489057L));
        final Lo3Stapel<Lo3PersoonInhoud> stapel = createStapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB008, 1);
    }

    @Test
    public void testPreconditie092Ok() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setAanduidingNaamgebruikCode(new Lo3AanduidingNaamgebruikCode("E"));
        final Lo3Stapel<Lo3PersoonInhoud> stapel = createStapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testPreconditie092FoutWeggelaten() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setAanduidingNaamgebruikCode(null);
        final Lo3Stapel<Lo3PersoonInhoud> stapel = createStapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB040, 1);
    }

    @Test
    public void testPreconditie092FoutLegeString() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setAanduidingNaamgebruikCode(new Lo3AanduidingNaamgebruikCode(""));
        final Lo3Stapel<Lo3PersoonInhoud> stapel = createStapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB040, 1);
    }

    @Test
    public void testPreconditie098GroepNietAanwezig() {
        final Lo3Documentatie lo3Doc = Lo3StapelHelper.lo3Akt(1);
        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), lo3Doc, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_PERSOON));
        precondities.controleerStapel(stapel);
        assertAantalErrors(0);
        assertSoortMeldingCode(SoortMeldingCode.PRE098, 0);
    }

    @Test
    public void testPreconditie098GroepAanwezig8810NietGevuld() {
        final Lo3Documentatie lo3Doc =
                Lo3StapelHelper.lo3Documentatie(1L, "0518", "1-X" + String.format("%04d", 1), null, null, null, null, "omschrijvingVerdrag");
        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), lo3Doc, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_PERSOON));
        precondities.controleerStapel(stapel);
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE098, 1);
    }

    @Test
    public void testPreconditie098GroepAanwezig8810Gevuld() {
        final Lo3Documentatie lo3Doc = Lo3StapelHelper.lo3Documentatie(1L, "0518", "1-X" + String.format("%04d", 1), null, null, null, "0000", null);
        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), lo3Doc, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_PERSOON));
        precondities.controleerStapel(stapel);
        assertAantalErrors(0);
        assertSoortMeldingCode(SoortMeldingCode.PRE098, 0);
    }

    @Test
    public void testControleerGroep83Procedure8320NietGevuld() {
        final Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10110), null, null);
        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEMEENTE_CODE, 20000101, DOC),
                    lo3Onderzoek,
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE099, 1);
    }

    @Test
    public void testBijzondereSituatieLb035Predicaat() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("JH"));
        builder.setGeslachtsaanduiding(new Lo3Geslachtsaanduiding("V"));
        final Lo3Stapel<Lo3PersoonInhoud> stapel = createStapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB035, 1);
    }

    @Test
    public void testBijzondereSituatieLb035PredicaatCorrectGeslacht() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("JH"));
        builder.setGeslachtsaanduiding(new Lo3Geslachtsaanduiding("M"));
        final Lo3Stapel<Lo3PersoonInhoud> stapel = createStapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(0);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB035, 0);
    }

    @Test
    public void testBijzondereSituatieLb035AdellijkeTitel() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("B"));
        builder.setGeslachtsaanduiding(new Lo3Geslachtsaanduiding("V"));
        final Lo3Stapel<Lo3PersoonInhoud> stapel = createStapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB035, 1);
    }

    @Test
    public void testBijzondereSituatieLb035AdellijkeTitelCorrectGeslacht() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("BS"));
        builder.setGeslachtsaanduiding(new Lo3Geslachtsaanduiding("V"));
        final Lo3Stapel<Lo3PersoonInhoud> stapel = createStapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(0);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB035, 0);
    }

    @Test
    public void testBijzondereSituatieLb035RidderVrouw() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("R"));
        builder.setGeslachtsaanduiding(new Lo3Geslachtsaanduiding("V"));
        final Lo3Stapel<Lo3PersoonInhoud> stapel = createStapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(0);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB035, 0);
    }

    @Test
    public void testBijzondereSituatieLb035RidderMan() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("R"));
        builder.setGeslachtsaanduiding(new Lo3Geslachtsaanduiding("M"));
        final Lo3Stapel<Lo3PersoonInhoud> stapel = createStapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(0);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB035, 0);
    }

    @Test
    public void testBijzondereSituatieLb035RidderOnbekend() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("R"));
        builder.setGeslachtsaanduiding(new Lo3Geslachtsaanduiding("O"));
        final Lo3Stapel<Lo3PersoonInhoud> stapel = createStapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB035, 1);
    }
}
