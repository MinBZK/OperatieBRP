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
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.conversie.tabel.GemeenteConversietabel;
import nl.moderniseringgba.migratie.conversie.tabel.LandConversietabel;
import nl.moderniseringgba.migratie.conversie.tabel.VoorvoegselScheidingstekenConversietabel;

import org.junit.Test;

/**
 * Preconditie tests voor categorie 01: persoon.
 */
@SuppressWarnings("unchecked")
public class Lo3PersoonsPreconditiesTest extends AbstractPreconditieTest {

    private static final Lo3Herkomst LO3_HERKOMST_PERSOON = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
    private final long anummer = 1069532945L;

    @Inject
    private Lo3PersoonPrecondities precondities;

    private Lo3PersoonInhoud.Builder builder() {
        final Lo3PersoonInhoud.Builder builder = new Lo3PersoonInhoud.Builder();
        builder.setaNummer(anummer);
        builder.setBurgerservicenummer(179543489L);
        builder.setVoornamen("Jaap");
        builder.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("P"));
        builder.setVoorvoegselGeslachtsnaam("van");
        builder.setGeslachtsnaam("Joppen");
        builder.setGeboortedatum(new Lo3Datum(19940104));
        builder.setGeboorteGemeenteCode(new Lo3GemeenteCode("0514"));
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
        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_PERSOON));
        return stapel;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr104() {
        final Lo3Categorie<Lo3PersoonInhoud> onjuist1 =
                Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_PERSOON);
        final Lo3Categorie<Lo3PersoonInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His("O", 19990101, 20000101),
                        Lo3StapelHelper.lo3Akt(2), LO3_HERKOMST_PERSOON);
        final Lo3Categorie<Lo3PersoonInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(null, 19990101, 20000101),
                        Lo3StapelHelper.lo3Akt(3), LO3_HERKOMST_PERSOON);

        final Lo3Stapel<Lo3PersoonInhoud> stapelOk = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2, juist3);

        precondities.controleerStapel(stapelOk);
        assertAantalErrors(0);

        final Lo3Stapel<Lo3PersoonInhoud> stapelNok = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2);
        precondities.controleerStapel(stapelNok);
        assertAantalErrors(2);
        assertPreconditie(Precondities.PRE055);
    }

    @Test
    public void testContr1091() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeboorteGemeenteCode(new Lo3GemeenteCode("Brussel"));
        builder.setGeboorteLandCode(new Lo3LandCode("5010"));

        final Lo3Stapel<Lo3PersoonInhoud> stapelBuitenlandsOk =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapelBuitenlandsOk);

        assertAantalErrors(0);

        builder.setGeboorteLandCode(new Lo3LandCode("6030"));

        final Lo3Stapel<Lo3PersoonInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapelNok);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE025);
    }

    @Test
    public void testContr110() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His(null, 20000101, 20000100), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE031);
    }

    @Test
    public void testContr112() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel2 =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 00000000, "Doc"), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel2);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000100, "Doc"), LO3_HERKOMST_PERSOON));

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
    public void testContr201() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setaNummer(null);
        builder.setBurgerservicenummer(null);

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE005);
    }

    @Test
    public void testContr202() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setVoornamen(null);
        builder.setAdellijkeTitelPredikaatCode(null);
        builder.setVoorvoegselGeslachtsnaam(null);
        builder.setGeslachtsnaam(null);

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE034);
    }

    @Test
    public void testContr206() {
        final Lo3PersoonInhoud.Builder builder = builder();

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testContr207() {
        final Lo3PersoonInhoud.Builder builder = builder();

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper
                        .lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(),
                                Lo3StapelHelper.lo3His(null, null, 20010101), Lo3StapelHelper.lo3Akt(1),
                                LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE030);
    }

    @Test
    public void testContr208() {
        final Lo3PersoonInhoud.Builder builder = builder();

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper
                        .lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(),
                                Lo3StapelHelper.lo3His(null, null, 20010101), Lo3StapelHelper.lo3Akt(1),
                                LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE030);
    }

    @Test
    public void testContr209() {
        final Lo3PersoonInhoud.Builder builder = builder();

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(),
                        Lo3StapelHelper.lo3His("O", 20010101, 20010101), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(2);
        assertPreconditie(Precondities.PRE055);
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
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE005);
    }

    @Test
    public void testContr303() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeslachtsnaam(null);

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE034);
    }

    @Test
    public void testContr304() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeboortedatum(null);

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE007);
    }

    @Test
    public void testContr306() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeboortedatum(null);

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE007);
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
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40121() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20050155, "Doc"), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40124() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His(null, 20040141, 20010101), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40125() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His(null, 20010101, 20040141), Lo3StapelHelper.lo3Akt(1),
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
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testContr4021() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeboorteGemeenteCode(GemeenteConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE025);
    }

    @Test
    public void testContr4031() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeboorteLandCode(LandConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE008);
    }

    @Test
    public void testContr4063() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Documentatie(1L,
                                LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getCode(), "9-x0001", null, null,
                                null), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr4064() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Documentatie(1L, null, null,
                                LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getCode(), 20010101, "Doc"),
                        LO3_HERKOMST_PERSOON));

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
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("QQ"));

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr412() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setVoorvoegselGeslachtsnaam(VoorvoegselScheidingstekenConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr414() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeslachtsaanduiding(new Lo3Geslachtsaanduiding("Q"));

        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr459() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His("X", 20000101, 20000101), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_PERSOON), Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His(20010101), Lo3StapelHelper.lo3Akt(2), LO3_HERKOMST_PERSOON));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testBijzondereSituatieGeboortelandStandaardWaarde() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeboorteLandCode(new Lo3LandCode("0000"));

        precondities.controleerStapel(createStapel(builder));
        assertAantalErrors(0);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB001);
    }

    @Test
    public void testBijzondereSituatieGeboortelandInternationaalGebied() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeboorteLandCode(new Lo3LandCode("9999"));

        precondities.controleerStapel(createStapel(builder));
        assertAantalErrors(0);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB001);
    }

    /**
     * Test dat er geen sprake is van bijzondere situatie.
     */
    @Test
    public void testBijzondereSituatieGeboortelandNull() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setGeboorteLandCode(null);

        precondities.controleerStapel(createStapel(builder));
        assertAantalErrors(1);
    }

    @Test
    public void testBijzondereSituatieVorigAnummerOngelijkAnummer() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setVorigANummer(4254732321L);
        final Lo3Stapel<Lo3PersoonInhoud> stapel = createStapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB005);
    }

    @Test
    public void testBijzondereSituatieVorigAnummerGelijkAnummer() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setVorigANummer(anummer);
        final Lo3Stapel<Lo3PersoonInhoud> stapel = createStapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB006);
    }

    @Test
    public void testBijzondereSituatieVolgendAnummerGelijkAnummer() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setVolgendANummer(anummer);
        final Lo3Stapel<Lo3PersoonInhoud> stapel = createStapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB007);
    }

    @Test
    public void testBijzondereSituatieVolgendAnummerOngelijkAnummer() {
        final Lo3PersoonInhoud.Builder builder = builder();
        builder.setVolgendANummer(2801489057L);
        final Lo3Stapel<Lo3PersoonInhoud> stapel = createStapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB008);
    }
}
