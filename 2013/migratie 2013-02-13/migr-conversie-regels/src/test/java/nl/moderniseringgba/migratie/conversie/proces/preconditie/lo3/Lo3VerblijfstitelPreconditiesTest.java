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
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.conversie.tabel.VerblijfsrechtConversietabel;

import org.junit.Test;

/**
 * Preconditie tests voor categorie 10: verblijfstitel.
 */
@SuppressWarnings("unchecked")
public class Lo3VerblijfstitelPreconditiesTest extends AbstractPreconditieTest {

    private static final Lo3Herkomst LO3_HERKOMST_VERBLIJFSTITEL = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_10, 0,
            0);
    @Inject
    private Lo3VerblijfstitelPrecondities precondities;

    private Lo3VerblijfstitelInhoud.Builder builder() {
        final Lo3VerblijfstitelInhoud.Builder builder = new Lo3VerblijfstitelInhoud.Builder();
        builder.setAanduidingVerblijfstitelCode(new Lo3AanduidingVerblijfstitelCode("09"));
        builder.setDatumEindeVerblijfstitel(new Lo3Datum(20140506));
        builder.setIngangsdatumVerblijfstitel(new Lo3Datum(19940101));

        return builder;
    }

    @Test
    public void testHappy() {
        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSTITEL));

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
        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapelBeeindigingOk =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSTITEL), Lo3StapelHelper.lo3Cat(
                        new Lo3VerblijfstitelInhoud.Builder().build(), Lo3StapelHelper.lo3His(20010101), null,
                        LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapelBeeindigingOk);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(new Lo3VerblijfstitelInhoud.Builder().build(),
                        Lo3StapelHelper.lo3His(20000101), null, LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapelNok);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE050);
    }

    @Test
    public void testContr104() {
        final Lo3Categorie<Lo3VerblijfstitelInhoud> onjuist1 =
                Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His("O", 19990101, 19990101), null,
                        LO3_HERKOMST_VERBLIJFSTITEL);
        final Lo3Categorie<Lo3VerblijfstitelInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His("O", 19990101, 20000101), null,
                        LO3_HERKOMST_VERBLIJFSTITEL);
        final Lo3Categorie<Lo3VerblijfstitelInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(null, 19990101, 20000101), null,
                        LO3_HERKOMST_VERBLIJFSTITEL);

        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapelOk = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2, juist3);

        precondities.controleerStapel(stapelOk);
        assertAantalErrors(0);

        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapelNok = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2);

        precondities.controleerStapel(stapelNok);
        assertAantalErrors(2);
        assertPreconditie(Precondities.PRE055);
    }

    @Test
    public void testContr110() {
        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His(null, 20000101, 20000100), null, LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE031);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr258() {
        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His(null, null, 20000101), null, LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE030);
    }

    @Test
    public void testContr259() {
        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His(null, 20000101, null), null, LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE031);
    }

    @Test
    public void testContr260() {
        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His("O", 20000101, 20000101), null, LO3_HERKOMST_VERBLIJFSTITEL));

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
    public void testContr340() {
        final Lo3VerblijfstitelInhoud.Builder builder = builder();
        builder.setAanduidingVerblijfstitelCode(null);

        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE012);
    }

    @Test
    public void testContr341() {
        final Lo3VerblijfstitelInhoud.Builder builder = builder();
        builder.setIngangsdatumVerblijfstitel(null);

        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE012);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr40115() {
        final Lo3VerblijfstitelInhoud.Builder builder = builder();
        builder.setDatumEindeVerblijfstitel(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40116() {
        final Lo3VerblijfstitelInhoud.Builder builder = builder();
        builder.setIngangsdatumVerblijfstitel(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40124() {
        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His(null, 20040141, 20010101), null, LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40125() {
        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His(null, 20010101, 20040141), null, LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr446() {
        final Lo3VerblijfstitelInhoud.Builder builder = builder();
        builder.setAanduidingVerblijfstitelCode(VerblijfsrechtConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr459() {
        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(),
                        Lo3StapelHelper.lo3His("X", 20000101, 20000101), null, LO3_HERKOMST_VERBLIJFSTITEL),
                        Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20010101), null,
                                LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }
}
