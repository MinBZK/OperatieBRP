/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import nl.bzk.migratiebrp.conversie.regels.tabel.VerblijfsrechtsConversietabel;
import org.junit.Test;

/**
 * Preconditie tests voor categorie 10: verblijfstitel.
 */
public class Lo3VerblijfstitelPreconditiesTest extends AbstractPreconditieTest {

    private static final Lo3Herkomst LO3_HERKOMST_VERBLIJFSTITEL = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_10, 0, 0);
    private static final Lo3Herkomst LO3_HERKOMST_VERBLIJFSTITEL_1 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_10, 0, 1);
    private static final Lo3Herkomst LO3_HERKOMST_VERBLIJFSTITEL_2 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_10, 0, 2);

    private Lo3VerblijfstitelPrecondities precondities = new Lo3VerblijfstitelPrecondities(new ConversietabelFactoryImpl());

    private Lo3VerblijfstitelInhoud.Builder builder() {
        final Lo3VerblijfstitelInhoud.Builder builder = new Lo3VerblijfstitelInhoud.Builder();
        builder.setAanduidingVerblijfstitelCode(new Lo3AanduidingVerblijfstitelCode("09"));
        builder.setDatumEindeVerblijfstitel(new Lo3Datum(20140506));
        builder.setDatumAanvangVerblijfstitel(new Lo3Datum(19940101));

        return builder;
    }

    @Test
    public void testHappy() {
        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFSTITEL));

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
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(builder().build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFSTITEL_1),
                        Lo3StapelHelper.lo3Cat(
                                new Lo3VerblijfstitelInhoud.Builder().build(),
                                null,
                                Lo3StapelHelper.lo3His(20010101),
                                LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapelBeeindigingOk);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        new Lo3VerblijfstitelInhoud.Builder().build(),
                        null,
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapelNok);

        assertAantalWarnings(0);
        assertAantalErrors(0);
        assertSoortMeldingCode(SoortMeldingCode.PRE050, 0);
    }

    @Test
    public void testContr104() {
        Lo3Categorie<Lo3VerblijfstitelInhoud> onjuist1 =
                Lo3StapelHelper.lo3Cat(builder().build(), null, Lo3StapelHelper.lo3His("O", 19990101, 19990101), LO3_HERKOMST_VERBLIJFSTITEL_2);
        final Lo3Categorie<Lo3VerblijfstitelInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(builder().build(), null, Lo3StapelHelper.lo3His("O", 19990101, 20000101), LO3_HERKOMST_VERBLIJFSTITEL_1);
        final Lo3Categorie<Lo3VerblijfstitelInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(builder().build(), null, Lo3StapelHelper.lo3His(null, 19990101, 20000101), LO3_HERKOMST_VERBLIJFSTITEL);

        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapelOk = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2, juist3);

        precondities.controleerStapel(stapelOk);
        assertAantalErrors(0);

        onjuist1 = Lo3StapelHelper.lo3Cat(builder().build(), null, Lo3StapelHelper.lo3His("O", 19990101, 19990101), LO3_HERKOMST_VERBLIJFSTITEL);

        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapelNok = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2);

        precondities.controleerStapel(stapelNok);
        assertSoortMeldingCode(SoortMeldingCode.PRE055, 1);
    }

    @Test
    public void testContr110() {
        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        null,
                        Lo3StapelHelper.lo3His(null, 20000101, 20000100),
                        LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE031, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr258() {
        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        null,
                        Lo3StapelHelper.lo3His(null, null, 20000101),
                        LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE030, 1);
    }

    @Test
    public void testContr259() {
        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        null,
                        Lo3StapelHelper.lo3His(null, 20000101, null),
                        LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE031, 1);
    }

    @Test
    public void testContr260() {
        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        null,
                        Lo3StapelHelper.lo3His("O", 20000101, 20000101),
                        LO3_HERKOMST_VERBLIJFSTITEL));

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
    public void testContr340() {
        final Lo3VerblijfstitelInhoud.Builder builder = builder();
        builder.setAanduidingVerblijfstitelCode(null);

        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE012, 1);
    }

    @Test
    public void testContr341() {
        final Lo3VerblijfstitelInhoud.Builder builder = builder();
        builder.setDatumAanvangVerblijfstitel(null);

        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE012, 1);
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
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40116() {
        final Lo3VerblijfstitelInhoud.Builder builder = builder();
        builder.setDatumAanvangVerblijfstitel(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40124() {
        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        null,
                        Lo3StapelHelper.lo3His(null, 20040141, 20010101),
                        LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40125() {
        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        null,
                        Lo3StapelHelper.lo3His(null, 20010101, 20040141),
                        LO3_HERKOMST_VERBLIJFSTITEL));

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
        builder.setAanduidingVerblijfstitelCode(VerblijfsrechtsConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testControleerGroep83Procedure8310NietGevuld() {
        final Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(null, new Lo3Datum(20140101), null);
        final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                        lo3Onderzoek,
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_VERBLIJFSTITEL));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE099, 1);
    }

}
