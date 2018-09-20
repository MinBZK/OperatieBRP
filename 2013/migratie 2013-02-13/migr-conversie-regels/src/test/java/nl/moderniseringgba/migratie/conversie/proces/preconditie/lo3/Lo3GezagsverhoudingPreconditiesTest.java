/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.conversie.tabel.LandConversietabel;

import org.junit.Test;

/**
 * Preconditie tests voor categorie 11: gezagsverhouding.
 */
@SuppressWarnings("unchecked")
public class Lo3GezagsverhoudingPreconditiesTest extends AbstractPreconditieTest {

    private static final Lo3Herkomst LO3_HERKOMST_GEZAGSVERHOUDING = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_11,
            0, 0);
    @Inject
    private Lo3GezagsverhoudingPrecondities precondities;

    private Lo3GezagsverhoudingInhoud.Builder minderjarigBuilder() {
        final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder();
        builder.setIndicatieGezagMinderjarige(new Lo3IndicatieGezagMinderjarige("1D"));
        builder.setIndicatieCurateleregister(null);

        return builder;
    }

    private Lo3GezagsverhoudingInhoud.Builder curateleBuilder() {
        final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder();
        builder.setIndicatieGezagMinderjarige(null);
        builder.setIndicatieCurateleregister(new Lo3IndicatieCurateleregister(1));

        return builder;
    }

    @Test
    public void testHappyMinderjarige() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(minderjarigBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20040101, "Doc"),
                        LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testHappyCuratele() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(curateleBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20040101, "Doc"),
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
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(curateleBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                        LO3_HERKOMST_GEZAGSVERHOUDING), Lo3StapelHelper.lo3Cat(
                        new Lo3GezagsverhoudingInhoud.Builder().build(), Lo3StapelHelper.lo3His(20010101),
                        Lo3StapelHelper.lo3Doc(2L, "0514", 20010101, "Doc"), LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapelCorrectieOk);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(new Lo3GezagsverhoudingInhoud.Builder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                        LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapelNok);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE050);
    }

    @Test
    public void testContr104() {
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> onjuist1 =
                Lo3StapelHelper.lo3Cat(curateleBuilder().build(), Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 19990101, "Doc"), LO3_HERKOMST_GEZAGSVERHOUDING);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(minderjarigBuilder().build(), Lo3StapelHelper.lo3His("O", 19990101, 20000101),
                        Lo3StapelHelper.lo3Doc(2L, "0514", 19990101, "Doc"), LO3_HERKOMST_GEZAGSVERHOUDING);
        final Lo3Categorie<Lo3GezagsverhoudingInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(curateleBuilder().build(), Lo3StapelHelper.lo3His(null, 19990101, 20000101),
                        Lo3StapelHelper.lo3Doc(3L, "0514", 19990101, "Doc"), LO3_HERKOMST_GEZAGSVERHOUDING);

        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapelOk = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2, juist3);

        precondities.controleerStapel(stapelOk);
        assertAantalErrors(0);

        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapelNok = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2);

        precondities.controleerStapel(stapelNok);
        assertAantalErrors(2);
        assertPreconditie(Precondities.PRE055);
    }

    @Test
    public void testContr110() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(curateleBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20000101, 20000100),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE031);
    }

    @Test
    public void testContr112() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel2 =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(curateleBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 00000000, "Doc"),
                        LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel2);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(curateleBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000100, "Doc"),
                        LO3_HERKOMST_GEZAGSVERHOUDING));

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
    public void testContr261() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(minderjarigBuilder().build(),
                        Lo3StapelHelper.lo3His(null, null, 20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20040101, "Doc"), LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE030);
    }

    @Test
    public void testContr262() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(minderjarigBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20000101, null),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20040101, "Doc"), LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE031);
    }

    @Test
    public void testContr264() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(minderjarigBuilder().build(),
                        Lo3StapelHelper.lo3His("O", 20000101, 20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20040101, "Doc"), LO3_HERKOMST_GEZAGSVERHOUDING));

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
    public void testContr40121() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(minderjarigBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20050155, "Doc"),
                        LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40124() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(minderjarigBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20040141, 20010101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40125() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(minderjarigBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20010101, 20040141),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4064() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(minderjarigBuilder().build(), Lo3StapelHelper
                        .lo3His(20000101), Lo3StapelHelper.lo3Documentatie(1L, null, null,
                        LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getCode(), 20010101, "Doc"),
                        LO3_HERKOMST_GEZAGSVERHOUDING));

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
    public void testContr435() {
        final Lo3GezagsverhoudingInhoud.Builder builder = minderjarigBuilder();
        builder.setIndicatieGezagMinderjarige(new Lo3IndicatieGezagMinderjarige("QQ"));

        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr436() {
        final Lo3GezagsverhoudingInhoud.Builder builder = curateleBuilder();
        builder.setIndicatieCurateleregister(new Lo3IndicatieCurateleregister(9));

        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr459() {
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(curateleBuilder().build(),
                        Lo3StapelHelper.lo3His("X", 20000101, 20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_GEZAGSVERHOUDING),
                        Lo3StapelHelper.lo3Cat(curateleBuilder().build(), Lo3StapelHelper.lo3His(20010101),
                                Lo3StapelHelper.lo3Doc(2L, "0514", 20010101, "Doc"), LO3_HERKOMST_GEZAGSVERHOUDING));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }
}
