/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.tabel.GemeenteConversietabel;
import nl.bzk.migratiebrp.conversie.regels.tabel.LandConversietabel;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Preconditie tests voor categorie 06: overlijden.
 */
public class Lo3OverlijdenPreconditiesTest extends AbstractPreconditieTest {

    private static final String DOC = "Doc";
    private static final String GEM_CODE = "0514";
    private static final Lo3Herkomst LO3_HERKOMST_OVERLIJDEN = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_06, 0, 0);
    private static final Lo3Herkomst LO3_HERKOMST_OVERLIJDEN_1 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_06, 0, 1);
    private static final Lo3Herkomst LO3_HERKOMST_OVERLIJDEN_2 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_06, 0, 2);
    @Inject
    private Lo3OverlijdenPrecondities precondities;

    private Lo3OverlijdenInhoud.Builder builder() {
        final Lo3OverlijdenInhoud.Builder builder = new Lo3OverlijdenInhoud.Builder();

        builder.setDatum(new Lo3Datum(19940104));
        builder.setGemeenteCode(new Lo3GemeenteCode(GEM_CODE));
        builder.setLandCode(new Lo3LandCode("6030"));

        return builder;
    }

    @Test
    public void testHappy() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Ignore("Volgens mij zou je niet puur een overlijden moeten kunnen beeidingen")
    @Test
    public void testContr101() {
        final Lo3Categorie<Lo3OverlijdenInhoud> onjuistGevuld =
                Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                    Lo3StapelHelper.lo3His("O", 20000601, 20000101),
                    LO3_HERKOMST_OVERLIJDEN);
        final Lo3Categorie<Lo3OverlijdenInhoud> juistGevuld =
                Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Doc(2L, GEM_CODE, 20000601, DOC),
                    Lo3StapelHelper.lo3His(null, 20000901, 20000601),
                    LO3_HERKOMST_OVERLIJDEN);
        final Lo3Categorie<Lo3OverlijdenInhoud> correctie =
                Lo3StapelHelper.lo3Cat(
                    new Lo3OverlijdenInhoud.Builder().build(),
                    Lo3StapelHelper.lo3Doc(3L, GEM_CODE, 20010101, DOC),
                    Lo3StapelHelper.lo3His(null, 20000601, 20010101),
                    LO3_HERKOMST_OVERLIJDEN);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapelBeeindigingOk = Lo3StapelHelper.lo3Stapel(onjuistGevuld, juistGevuld, correctie);

        precondities.controleerStapel(stapelBeeindigingOk);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    new Lo3OverlijdenInhoud.Builder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapelNok);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE050, 1);
    }

    @Test
    public void testContr105() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapelCorrectieOk =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, "Doc A"),
                    Lo3StapelHelper.lo3His("O", 20000101, 20000101),
                    LO3_HERKOMST_OVERLIJDEN), Lo3StapelHelper.lo3Cat(
                    new Lo3OverlijdenInhoud.Builder().build(),
                    Lo3StapelHelper.lo3Doc(2L, GEM_CODE, 20000101, "Doc B"),
                    Lo3StapelHelper.lo3His(null, 20000101, 20010101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapelCorrectieOk);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    new Lo3OverlijdenInhoud.Builder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapelNok);

        // assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE056, 1);
    }

    @Test
    public void testContr104() {
        Lo3Categorie<Lo3OverlijdenInhoud> onjuist1 =
                Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                    LO3_HERKOMST_OVERLIJDEN_2);
        final Lo3Categorie<Lo3OverlijdenInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Akt(2),
                    Lo3StapelHelper.lo3His("O", 19990101, 20000101),
                    LO3_HERKOMST_OVERLIJDEN_1);
        final Lo3Categorie<Lo3OverlijdenInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Akt(3),
                    Lo3StapelHelper.lo3His(null, 19990101, 20000101),
                    LO3_HERKOMST_OVERLIJDEN);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapelOk = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2, juist3);

        precondities.controleerStapel(stapelOk);
        assertAantalErrors(0);

        onjuist1 =
                Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                    LO3_HERKOMST_OVERLIJDEN);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapelNok = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2);

        precondities.controleerStapel(stapelNok);
        assertSoortMeldingCode(SoortMeldingCode.PRE055, 1);
    }

    @Test
    public void testContr1091() {
        final Lo3OverlijdenInhoud.Builder builder = builder();
        builder.setGemeenteCode(new Lo3GemeenteCode("Brussel"));
        builder.setLandCode(new Lo3LandCode("5010"));

        final Lo3Stapel<Lo3OverlijdenInhoud> stapelBuitenlandsOk =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapelBuitenlandsOk);

        assertAantalErrors(0);

        builder.setLandCode(new Lo3LandCode("6030"));

        final Lo3Stapel<Lo3OverlijdenInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapelNok);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE026, 1);
    }

    @Test
    public void testContr110() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(null, 20000101, 20000100),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE031, 1);
    }

    @Test
    public void testContr112() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel2 =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 0, DOC),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel2);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000100, DOC),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));

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
    public void testContr237() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Documentatie(1L, GEM_CODE, "9-x0001", "5014", 20010101, DOC),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE020, 1);
    }

    @Test
    public void testContr238() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(null, null, 20000101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE030, 1);
    }

    @Test
    public void testContr239() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(null, 20000101, null),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE031, 1);
    }

    @Test
    public void testContr240() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His("O", 20000101, 20000101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE055, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr314() {
        final Lo3OverlijdenInhoud.Builder builder = builder();
        builder.setDatum(null);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE009, 1);
    }

    @Test
    public void testContr316() {
        final Lo3OverlijdenInhoud.Builder builder = builder();
        builder.setLandCode(null);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE009, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr4014() {
        final Lo3OverlijdenInhoud.Builder builder = builder();
        builder.setDatum(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40121() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20050155, DOC),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40124() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(null, 20040141, 20010101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40125() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(null, 20010101, 20040141),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4024() {
        final Lo3OverlijdenInhoud.Builder builder = builder();
        builder.setGemeenteCode(GemeenteConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE026, 1);
    }

    @Test
    public void testContr4034() {
        final Lo3OverlijdenInhoud.Builder builder = builder();
        builder.setLandCode(LandConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE010, 1);
    }

    @Test
    public void testContr4063() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Documentatie(1L, LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getWaarde(), "1-x0001", null, null, null),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr4064() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Documentatie(1L, null, null, LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getWaarde(), 20010101, DOC),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));

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
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB002)
    public void testBijzondereSituatieLandcodeStandaardwaarde() {
        final Lo3OverlijdenInhoud.Builder builder = builder();
        builder.setLandCode(new Lo3LandCode("0000"));
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB002, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB002)
    public void testBijzondereSituatieLandcodeInternationaalGebied() {
        final Lo3OverlijdenInhoud.Builder builder = builder();
        builder.setLandCode(new Lo3LandCode("9999"));
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB002, 1);
    }

    /**
     * Test toegevoegd die test hoe omgegaan wordt met een lege string als gemeentecode. Voorheen werd een
     * IllegalArgumentException gegooid. Dit wordt niet meer gegooid maar de situatie moet nu wel door een preconditie
     * worden afgevangen.
     */
    @Test
    public void testOngeldigeGemeenteCode() {
        final Lo3OverlijdenInhoud.Builder builder = new Lo3OverlijdenInhoud.Builder();

        builder.setDatum(new Lo3Datum(19940104));
        builder.setGemeenteCode(new Lo3GemeenteCode(""));
        builder.setLandCode(new Lo3LandCode("6030"));

        final Lo3Categorie<Lo3OverlijdenInhoud> juist =
                Lo3StapelHelper.lo3Cat(
                    builder.build(),
                    Lo3StapelHelper.lo3Akt(3),
                    Lo3StapelHelper.lo3His(null, 19990101, 20000101),
                    LO3_HERKOMST_OVERLIJDEN);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapelOk = Lo3StapelHelper.lo3Stapel(juist);

        precondities.controleerStapel(stapelOk);
        assertAantalErrors(1);

        assertSoortMeldingCode(SoortMeldingCode.PRE026, 1);
    }

    @Test
    public void testPreconditie098GroepNietAanwezig() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));
        precondities.controleerStapel(stapel);
        assertAantalErrors(0);
        assertSoortMeldingCode(SoortMeldingCode.PRE098, 0);
    }

    @Test
    public void testPreconditie098GroepAanwezig8810NietGevuld() {
        final Lo3Documentatie lo3Doc =
                Lo3StapelHelper.lo3Documentatie(1L, "0518", "1-X" + String.format("%04d", 1), null, null, null, null, "omschrijvingVerdrag");
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), lo3Doc, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_OVERLIJDEN));
        precondities.controleerStapel(stapel);
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE098, 1);
    }

    @Test
    public void testPreconditie098GroepAanwezig8810Gevuld() {
        final Lo3Documentatie lo3Doc = Lo3StapelHelper.lo3Documentatie(1L, "0518", "1-X" + String.format("%04d", 1), null, null, null, "0000", null);
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), lo3Doc, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_OVERLIJDEN));
        precondities.controleerStapel(stapel);
        assertAantalErrors(0);
        assertSoortMeldingCode(SoortMeldingCode.PRE098, 0);
    }

    @Test
    public void testControleerGroep83Procedure8320NietGevuld() {
        final Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10110), null, null);
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                    lo3Onderzoek,
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE099, 1);
    }
}
