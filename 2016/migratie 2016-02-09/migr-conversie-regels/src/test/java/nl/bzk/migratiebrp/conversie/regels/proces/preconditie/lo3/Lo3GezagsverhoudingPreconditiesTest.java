/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.tabel.PartijConversietabel;

import org.junit.Test;

/**
 * Preconditie tests voor categorie 11: gezagsverhouding.
 */
public class Lo3GezagsverhoudingPreconditiesTest extends AbstractPreconditieTest {

    private static final String DOC = "Doc";
    private static final String GEM_CODE = "0514";
    private static final Lo3Documentatie DOCUMENTATIE = Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC);
    private static final Lo3Herkomst LO3_HERKOMST_GEZAGSVERHOUDING = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_11, 0, 0);
    private static final Lo3Herkomst LO3_HERKOMST_GEZAGSVERHOUDING_1 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_61, 0, 1);
    private static final Lo3Herkomst LO3_HERKOMST_GEZAGSVERHOUDING_2 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_61, 0, 2);

    @Inject
    private Lo3GezagsverhoudingPrecondities precondities;

    private Lo3GezagsverhoudingInhoud.Builder minderjarigBuilder() {
        final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder();
        builder.indicatieGezagMinderjarige(new Lo3IndicatieGezagMinderjarige("1D"));
        builder.indicatieCurateleregister(null);

        return builder;
    }

    private Lo3GezagsverhoudingInhoud.Builder curateleBuilder() {
        final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder();
        builder.indicatieGezagMinderjarige(null);
        builder.indicatieCurateleregister(new Lo3IndicatieCurateleregister(1));

        return builder;
    }

    @Test
    public void testHappyMinderjarige() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    minderjarigBuilder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20040101, DOC),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testHappyCuratele() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    curateleBuilder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20040101, DOC),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr101() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapelCorrectieOk =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    curateleBuilder().build(),
 DOCUMENTATIE,
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_GEZAGSVERHOUDING_1), Lo3StapelHelper.lo3Cat(
                    new Lo3GezagsverhoudingInhoud.Builder().build(),
                    Lo3StapelHelper.lo3Doc(2L, GEM_CODE, 20010101, DOC),
                    Lo3StapelHelper.lo3His(20010101),
                    LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapelCorrectieOk);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    new Lo3GezagsverhoudingInhoud.Builder().build(),
                        DOCUMENTATIE,
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapelNok);

        assertAantalWarnings(0);
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE050, 1);
    }

    @Test
    public void testContr104() {
        Lo3Categorie<Lo3GezagsverhoudingInhoud> onjuist1 =
                Lo3StapelHelper.lo3Cat(
                    curateleBuilder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 19990101, DOC),
                    Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                    LO3_HERKOMST_GEZAGSVERHOUDING_2);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(
                    minderjarigBuilder().build(),
                    Lo3StapelHelper.lo3Doc(2L, GEM_CODE, 19990101, DOC),
                    Lo3StapelHelper.lo3His("O", 19990101, 20000101),
                    LO3_HERKOMST_GEZAGSVERHOUDING_1);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(
                    curateleBuilder().build(),
                    Lo3StapelHelper.lo3Doc(3L, GEM_CODE, 19990101, DOC),
                    Lo3StapelHelper.lo3His(null, 19990101, 20000101),
                    LO3_HERKOMST_GEZAGSVERHOUDING);

        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapelOk = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2, juist3);

        precondities.controleerStapel(stapelOk);
        assertAantalErrors(0);

        onjuist1 =
                Lo3StapelHelper.lo3Cat(
                    curateleBuilder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 19990101, DOC),
                    Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                    LO3_HERKOMST_GEZAGSVERHOUDING);

        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapelNok = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2);

        precondities.controleerStapel(stapelNok);
        assertSoortMeldingCode(SoortMeldingCode.PRE055, 1);
    }

    @Test
    public void testContr110() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    curateleBuilder().build(),
                        DOCUMENTATIE,
                    Lo3StapelHelper.lo3His(null, 20000101, 20000100),
                    LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE031, 1);
    }

    @Test
    public void testContr112() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel2 =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    curateleBuilder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 00000000, DOC),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel2);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    curateleBuilder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000100, DOC),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_GEZAGSVERHOUDING));

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
    public void testContr261() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    minderjarigBuilder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20040101, DOC),
                    Lo3StapelHelper.lo3His(null, null, 20000101),
                    LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE030, 1);
    }

    @Test
    public void testContr262() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    minderjarigBuilder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20040101, DOC),
                    Lo3StapelHelper.lo3His(null, 20000101, null),
                    LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE031, 1);
    }

    @Test
    public void testContr264() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    minderjarigBuilder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20040101, DOC),
                    Lo3StapelHelper.lo3His("O", 20000101, 20000101),
                    LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        // assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE055, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr40121() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    minderjarigBuilder().build(),
                    Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20050155, DOC),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40124() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    minderjarigBuilder().build(),
                        DOCUMENTATIE,
                    Lo3StapelHelper.lo3His(null, 20040141, 20010101),
                    LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40125() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    minderjarigBuilder().build(),
                        DOCUMENTATIE,
                    Lo3StapelHelper.lo3His(null, 20010101, 20040141),
                    LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4064() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    minderjarigBuilder().build(),
                    Lo3StapelHelper.lo3Documentatie(1L, null, null, PartijConversietabel.LO3_NIET_VALIDE_UITZONDERING.getWaarde(), 20010101, DOC),
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_GEZAGSVERHOUDING));

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
    public void testContr435() {
        final Lo3GezagsverhoudingInhoud.Builder builder = minderjarigBuilder();
        builder.indicatieGezagMinderjarige(new Lo3IndicatieGezagMinderjarige("QQ"));

        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
 DOCUMENTATIE,
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE078, 1);
    }

    @Test
    public void testContr436() {
        final Lo3GezagsverhoudingInhoud.Builder builder = curateleBuilder();
        builder.indicatieCurateleregister(new Lo3IndicatieCurateleregister(9));

        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    builder.build(),
 DOCUMENTATIE,
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testControleerGroep83Procedure8310NietGevuld() {
        final Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(null, new Lo3Datum(20140101), null);
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        curateleBuilder().build(),
                        DOCUMENTATIE,
                        lo3Onderzoek,
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE099, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE112)
    public void testPreconditie112GeenOnjuistVoorkomens() {
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> categorieLeeg =
                Lo3StapelHelper.lo3Cat(
                    new Lo3GezagsverhoudingInhoud.Builder().build(),
                    DOCUMENTATIE,
                    Lo3StapelHelper.lo3His(20000102),
                    LO3_HERKOMST_GEZAGSVERHOUDING);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> categorieGevuld =
                Lo3StapelHelper.lo3Cat(curateleBuilder().build(), DOCUMENTATIE, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_GEZAGSVERHOUDING_1);

        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel = Lo3StapelHelper.lo3Stapel(categorieLeeg, categorieGevuld);
        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE112, 0);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE112)
    public void testPreconditie112OnjuistVoorkomensLegeRij() {
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> categorieLeeg =
                Lo3StapelHelper.lo3Cat(
                    new Lo3GezagsverhoudingInhoud.Builder().build(),
                    DOCUMENTATIE,
                    Lo3StapelHelper.lo3His(20000101),
                    LO3_HERKOMST_GEZAGSVERHOUDING);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> categorieGevuld =
                Lo3StapelHelper.lo3Cat(
                    curateleBuilder().build(),
                    DOCUMENTATIE,
                    Lo3StapelHelper.lo3His("O", 20000101, 20000102),
                    LO3_HERKOMST_GEZAGSVERHOUDING_1);

        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel = Lo3StapelHelper.lo3Stapel(categorieLeeg, categorieGevuld);
        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE112, 0);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE112)
    public void testPreconditie112OnjuistVoorkomensLegeRijAndereDatum() {
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> categorieLeeg =
                Lo3StapelHelper.lo3Cat(
                    new Lo3GezagsverhoudingInhoud.Builder().build(),
                    DOCUMENTATIE,
                    Lo3StapelHelper.lo3His(20000102),
                    LO3_HERKOMST_GEZAGSVERHOUDING);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> categorieGevuld =
                Lo3StapelHelper.lo3Cat(
                    curateleBuilder().build(),
                    DOCUMENTATIE,
                    Lo3StapelHelper.lo3His("O", 20000101, 20000102),
                    LO3_HERKOMST_GEZAGSVERHOUDING_1);

        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel = Lo3StapelHelper.lo3Stapel(categorieLeeg, categorieGevuld);
        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE112, 1);
    }
}
