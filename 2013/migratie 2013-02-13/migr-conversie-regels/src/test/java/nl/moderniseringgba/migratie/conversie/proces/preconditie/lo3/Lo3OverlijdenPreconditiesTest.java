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
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.conversie.tabel.GemeenteConversietabel;
import nl.moderniseringgba.migratie.conversie.tabel.LandConversietabel;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Preconditie tests voor categorie 06: overlijden.
 */
@SuppressWarnings("unchecked")
public class Lo3OverlijdenPreconditiesTest extends AbstractPreconditieTest {

    private static final Lo3Herkomst LO3_HERKOMST_OVERLIJDEN = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_06, 0, 0);
    @Inject
    private Lo3OverlijdenPrecondities precondities;

    private Lo3OverlijdenInhoud.Builder builder() {
        final Lo3OverlijdenInhoud.Builder builder = new Lo3OverlijdenInhoud.Builder();

        builder.setDatum(new Lo3Datum(19940104));
        builder.setGemeenteCode(new Lo3GemeenteCode("0514"));
        builder.setLandCode(new Lo3LandCode("6030"));

        return builder;
    }

    @Test
    public void testHappy() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OVERLIJDEN));

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
                Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His("O", 20000601, 20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_OVERLIJDEN);
        final Lo3Categorie<Lo3OverlijdenInhoud> juistGevuld =
                Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(null, 20000901, 20000601),
                        Lo3StapelHelper.lo3Doc(2L, "0514", 20000601, "Doc"), LO3_HERKOMST_OVERLIJDEN);
        final Lo3Categorie<Lo3OverlijdenInhoud> correctie =
                Lo3StapelHelper.lo3Cat(new Lo3OverlijdenInhoud.Builder().build(),
                        Lo3StapelHelper.lo3His(null, 20000601, 20010101),
                        Lo3StapelHelper.lo3Doc(3L, "0514", 20010101, "Doc"), LO3_HERKOMST_OVERLIJDEN);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapelBeeindigingOk =
                Lo3StapelHelper.lo3Stapel(onjuistGevuld, juistGevuld, correctie);

        precondities.controleerStapel(stapelBeeindigingOk);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(new Lo3OverlijdenInhoud.Builder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                        LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapelNok);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE050);
    }

    @Test
    public void testContr105() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapelCorrectieOk =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His("O", 20000101, 20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc A"), LO3_HERKOMST_OVERLIJDEN),
                        Lo3StapelHelper.lo3Cat(new Lo3OverlijdenInhoud.Builder().build(),
                                Lo3StapelHelper.lo3His(null, 20000101, 20010101),
                                Lo3StapelHelper.lo3Doc(2L, "0514", 20000101, "Doc B"), LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapelCorrectieOk);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(new Lo3OverlijdenInhoud.Builder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                        LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapelNok);

        // assertAantalErrors(1);
        assertPreconditie(Precondities.PRE056);
    }

    @Test
    public void testContr104() {
        final Lo3Categorie<Lo3OverlijdenInhoud> onjuist1 =
                Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OVERLIJDEN);
        final Lo3Categorie<Lo3OverlijdenInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His("O", 19990101, 20000101),
                        Lo3StapelHelper.lo3Akt(2), LO3_HERKOMST_OVERLIJDEN);
        final Lo3Categorie<Lo3OverlijdenInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(null, 19990101, 20000101),
                        Lo3StapelHelper.lo3Akt(3), LO3_HERKOMST_OVERLIJDEN);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapelOk = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2, juist3);

        precondities.controleerStapel(stapelOk);
        assertAantalErrors(0);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapelNok = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2);

        precondities.controleerStapel(stapelNok);
        assertAantalErrors(2);
        assertPreconditie(Precondities.PRE055);
    }

    @Test
    public void testContr1091() {
        final Lo3OverlijdenInhoud.Builder builder = builder();
        builder.setGemeenteCode(new Lo3GemeenteCode("Brussel"));
        builder.setLandCode(new Lo3LandCode("5010"));

        final Lo3Stapel<Lo3OverlijdenInhoud> stapelBuitenlandsOk =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapelBuitenlandsOk);

        assertAantalErrors(0);

        builder.setLandCode(new Lo3LandCode("6030"));

        final Lo3Stapel<Lo3OverlijdenInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapelNok);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE026);
    }

    @Test
    public void testContr110() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His(null, 20000101, 20000100), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE031);
    }

    @Test
    public void testContr112() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel2 =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 00000000, "Doc"), LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel2);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000100, "Doc"), LO3_HERKOMST_OVERLIJDEN));

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
    public void testContr237() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Documentatie(1L, "0514", "9-x0001", "5014", 20010101, "Doc"),
                        LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertPreconditie(Precondities.PRE020);
    }

    @Test
    public void testContr238() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His(null, null, 20000101), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertPreconditie(Precondities.PRE030);
    }

    @Test
    public void testContr239() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His(null, 20000101, null), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertPreconditie(Precondities.PRE031);
    }

    @Test
    public void testContr240() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His("O", 20000101, 20000101), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertPreconditie(Precondities.PRE055);
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
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertPreconditie(Precondities.PRE009);
    }

    @Test
    public void testContr316() {
        final Lo3OverlijdenInhoud.Builder builder = builder();
        builder.setLandCode(null);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertPreconditie(Precondities.PRE009);
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
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40121() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20050155, "Doc"), LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40124() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His(null, 20040141, 20010101), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40125() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His(null, 20010101, 20040141), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4024() {
        final Lo3OverlijdenInhoud.Builder builder = builder();
        builder.setGemeenteCode(GemeenteConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE026);
    }

    @Test
    public void testContr4034() {
        final Lo3OverlijdenInhoud.Builder builder = builder();
        builder.setLandCode(LandConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE010);
    }

    @Test
    public void testContr4063() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Documentatie(1L,
                                LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getCode(), "9-x0001", null, null,
                                null), LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr4064() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Documentatie(1L, null, null,
                                LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getCode(), 20010101, "Doc"),
                        LO3_HERKOMST_OVERLIJDEN));

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
    public void testContr459() {
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His("X", 20000101, 20000101), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_OVERLIJDEN), Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His(20010101), Lo3StapelHelper.lo3Akt(2), LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testBijzondereSituatieLandcodeStandaardwaarde() {
        final Lo3OverlijdenInhoud.Builder builder = builder();
        builder.setLandCode(new Lo3LandCode("0000"));
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB002);
    }

    @Test
    public void testBijzondereSituatieLandcodeInternationaalGebied() {
        final Lo3OverlijdenInhoud.Builder builder = builder();
        builder.setLandCode(new Lo3LandCode("9999"));
        final Lo3Stapel<Lo3OverlijdenInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OVERLIJDEN));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB002);
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
                Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(null, 19990101, 20000101),
                        Lo3StapelHelper.lo3Akt(3), LO3_HERKOMST_OVERLIJDEN);

        final Lo3Stapel<Lo3OverlijdenInhoud> stapelOk = Lo3StapelHelper.lo3Stapel(juist);

        precondities.controleerStapel(stapelOk);
        assertAantalErrors(1);

        assertPreconditie(Precondities.PRE026);
    }
}
