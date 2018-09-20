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
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
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
 * Preconditie tests voor categorie 03: Ouder 2.
 */
@SuppressWarnings("unchecked")
public class Lo3Ouder2PrecondtiesTest extends AbstractLo3OuderPreconditiesTest {

    private static final Lo3Herkomst LO3_HERKOMST_OUDER2 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_03, 0, 0);

    @Inject
    private Lo3Ouder2Precondities preconditiesOuder2;

    @Override
    public Lo3OuderPrecondities getPrecondities() {
        return preconditiesOuder2;
    }

    @Override
    public Lo3Herkomst getHerkomst() {
        return LO3_HERKOMST_OUDER2;
    }

    @Override
    public Lo3Herkomst getHerkomst(final int voorkomen) {
        return new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_03, 0, voorkomen);
    }

    @Test
    public void testHappy() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(0);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr104() {
        final Lo3Categorie<Lo3OuderInhoud> onjuist1 =
                Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OUDER2);
        final Lo3Categorie<Lo3OuderInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His("O", 19990101, 20000101),
                        Lo3StapelHelper.lo3Akt(2), LO3_HERKOMST_OUDER2);
        final Lo3Categorie<Lo3OuderInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(null, 19990101, 20000101),
                        Lo3StapelHelper.lo3Akt(3), LO3_HERKOMST_OUDER2);

        final Lo3Stapel<Lo3OuderInhoud> stapelOk = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2, juist3);

        getPrecondities().controleerStapel(stapelOk);
        assertAantalErrors(0);

        final Lo3Stapel<Lo3OuderInhoud> stapelNok = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2);

        getPrecondities().controleerStapel(stapelNok);
        assertAantalErrors(2);
        assertPreconditie(Precondities.PRE055);
    }

    @Test
    public void testContr1091() {
        final Lo3OuderInhoud.Builder builder = builder();
        builder.setGeboorteGemeenteCode(new Lo3GemeenteCode("Brussel"));
        builder.setGeboorteLandCode(new Lo3LandCode("5010"));

        final Lo3Stapel<Lo3OuderInhoud> stapelBuitenlandsOk =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapelBuitenlandsOk);

        assertAantalErrors(0);

        builder.setGeboorteLandCode(new Lo3LandCode("6030"));

        final Lo3Stapel<Lo3OuderInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapelNok);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE025);
    }

    @Test
    public void testContr110() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His(null, 20000101, 20000100), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE031);
    }

    @Test
    public void testContr112() {
        final Lo3Stapel<Lo3OuderInhoud> stapel2 =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 00000000, "Doc"), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel2);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000100, "Doc"), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE067);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr216() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testContr217() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper
                        .lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                                Lo3StapelHelper.lo3His(null, null, 20000101), Lo3StapelHelper.lo3Akt(1),
                                LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE030);
    }

    @Test
    public void testContr218() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper
                        .lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                                Lo3StapelHelper.lo3His(null, 20000101, null), Lo3StapelHelper.lo3Akt(1),
                                LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE031);
    }

    @Test
    public void testContr220() {
        final Lo3OuderInhoud.Builder builder = builder();
        builder.setVoornamen(null);
        builder.setAdellijkeTitelPredikaatCode(null);
        builder.setVoorvoegselGeslachtsnaam(null);
        builder.setGeslachtsnaam(null);

        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        // assertAantalErrors(1);
        assertPreconditie(Precondities.PRE049);
    }

    @Test
    public void testContr221() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His("O", 20010101, 20000101), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

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
        final Lo3OuderInhoud.Builder builder = builder();
        builder.setaNummer(null);

        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalInfos(1);
    }

    @Test
    public void testContr303() {
        final Lo3OuderInhoud.Builder builder = builder();
        builder.setGeslachtsnaam(null);

        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE064);
    }

    @Test
    public void testContr304() {
        final Lo3OuderInhoud.Builder builder = builder();
        builder.setGeboortedatum(null);

        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE007);
    }

    @Test
    public void testContr306() {
        final Lo3OuderInhoud.Builder builder = builder();
        builder.setGeboortedatum(null);

        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

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
        final Lo3OuderInhoud.Builder builder = builder();
        builder.setGeboortedatum(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40117() {
        final Lo3OuderInhoud.Builder builder = builder();
        builder.setFamilierechtelijkeBetrekking(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40121() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20050155, "Doc"), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40124() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His(null, 20040141, 20010101), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40125() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His(null, 20010101, 20040141), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4021() {
        final Lo3OuderInhoud.Builder builder = builder();
        builder.setGeboorteGemeenteCode(GemeenteConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE025);
    }

    @Test
    public void testContr4031() {
        final Lo3OuderInhoud.Builder builder = builder();
        builder.setGeboorteLandCode(LandConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE008);
    }

    @Test
    public void testContr4063() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Documentatie(1L,
                                LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getCode(), "9-x0001", null, null,
                                null), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr4064() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Documentatie(1L, null, null,
                                LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getCode(), 20010101, "Doc"),
                        LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

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
        final Lo3OuderInhoud.Builder builder = builder();
        builder.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("QQ"));

        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr412() {
        final Lo3OuderInhoud.Builder builder = builder();
        builder.setVoorvoegselGeslachtsnaam(VoorvoegselScheidingstekenConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr414() {
        final Lo3OuderInhoud.Builder builder = builder();
        builder.setGeslachtsaanduiding(new Lo3Geslachtsaanduiding("Q"));

        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr459() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His("X", 20000101, 20000101), Lo3StapelHelper.lo3Akt(1),
                        LO3_HERKOMST_OUDER2), Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His(20010101), Lo3StapelHelper.lo3Akt(2), LO3_HERKOMST_OUDER2));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }
}
