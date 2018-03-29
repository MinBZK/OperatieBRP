/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import java.util.Arrays;
import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SignaleringEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Signalering;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import nl.bzk.migratiebrp.conversie.regels.tabel.LandConversietabel;
import org.junit.Test;

/**
 * Preconditie tests voor categorie 12: reisdocument.
 */
public class Lo3ReisdocumentPreconditiesTest extends AbstractPreconditieTest {

    private static final String DOC = "Doc";
    private static final String GEM_CODE = "0514";
    private static final Lo3Herkomst LO3_HERKOMST_REISDOCUMENT = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_12, 0, 0);
    private Lo3ReisdocumentPrecondities precondities = new Lo3ReisdocumentPrecondities(new ConversietabelFactoryImpl());

    private Lo3ReisdocumentInhoud.Builder reisdocumentBuilder() {
        final Lo3ReisdocumentInhoud.Builder builder = new Lo3ReisdocumentInhoud.Builder();
        builder.soortNederlandsReisdocument(new Lo3SoortNederlandsReisdocument("PN"));
        builder.nummerNederlandsReisdocument(Lo3String.wrap("123456789"));
        builder.datumUitgifteNederlandsReisdocument(new Lo3Datum(20050101));
        builder.autoriteitVanAfgifteNederlandsReisdocument(new Lo3AutoriteitVanAfgifteNederlandsReisdocument("BI0518"));
        builder.datumEindeGeldigheidNederlandsReisdocument(new Lo3Datum(20150101));
        builder.datumInhoudingVermissingNederlandsReisdocument(new Lo3Datum(20080505));
        builder.aanduidingInhoudingNederlandsReisdocument(new Lo3AanduidingInhoudingVermissingNederlandsReisdocument("V"));
        return builder;
    }

    private Lo3ReisdocumentInhoud.Builder signaleringBuilder() {
        final Lo3ReisdocumentInhoud.Builder builder = new Lo3ReisdocumentInhoud.Builder();
        builder.signalering(new Lo3Signalering(1));
        return builder;
    }

    @Test
    public void testHappyReisdocument() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                reisdocumentBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testHappySignalering() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                signaleringBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr110() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                reisdocumentBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(null, 20000101, 20000100),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE031, 1);
    }

    @Test
    public void testContr112() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel2 =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                reisdocumentBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 00000000, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel2);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                reisdocumentBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000100, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

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
    @Preconditie(SoortMeldingCode.PRE035)
    public void testPre035Groep35En36Aanwezig() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.signalering(Lo3SignaleringEnum.SIGNALERING.asElement());

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE035, 1);
    }

    @Test
    public void testContr267() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                reisdocumentBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(null, null, 20000101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE030, 1);
    }

    @Test
    public void testContr268() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                reisdocumentBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(null, 20000101, null),
                                LO3_HERKOMST_REISDOCUMENT));

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
    @Definitie(Definities.DEF011)
    public void testPre11SoortNLReisdocumentNull() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.soortNederlandsReisdocument(null);

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE011, 1);
    }

    @Test
    @Definitie(Definities.DEF011)
    public void testPre11NummerNLReisdocumentNull() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.nummerNederlandsReisdocument(null);

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE011, 1);
    }

    @Test
    @Definitie(Definities.DEF011)
    public void testPre11DatumUitgifteNLReisdocumentNull() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.datumUitgifteNederlandsReisdocument(null);

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE011, 1);
    }

    @Test
    @Definitie(Definities.DEF011)
    public void testPre11AutoriteitVanAfgifteNull() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.autoriteitVanAfgifteNederlandsReisdocument(null);

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE011, 1);
    }

    @Test
    @Definitie(Definities.DEF011)
    public void testPre11DatumEindeGeldigheidNull() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.datumEindeGeldigheidNederlandsReisdocument(null);

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE011, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr40111() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.datumUitgifteNederlandsReisdocument(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40112() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.datumEindeGeldigheidNederlandsReisdocument(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40113() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.datumInhoudingVermissingNederlandsReisdocument(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40121() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                reisdocumentBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20050155, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40124() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                reisdocumentBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(null, 20040141, 20010101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40125() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                reisdocumentBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(null, 20010101, 20040141),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4064() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                reisdocumentBuilder().build(),
                                Lo3StapelHelper.lo3Documentatie(1L, null, null, LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getWaarde(), 20010101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

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
    public void testContr437() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.soortNederlandsReisdocument(new Lo3SoortNederlandsReisdocument("QQ"));

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr440() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.aanduidingInhoudingNederlandsReisdocument(new Lo3AanduidingInhoudingVermissingNederlandsReisdocument("Q"));

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr442() {
        final Lo3ReisdocumentInhoud.Builder builder = signaleringBuilder();
        builder.signalering(new Lo3Signalering(9));

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testControleerGroep83Procedure8320NietGevuld() {
        final Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10110), null, null);
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                signaleringBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                lo3Onderzoek,
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE099, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE107)
    public void testPreconditie107() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel1 =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(signaleringBuilder().build(), LO3_HERKOMST_REISDOCUMENT));
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel2 =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(signaleringBuilder().build(), LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapels(Arrays.asList(stapel1, stapel2));

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE107, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE107)
    public void testPreconditie107NietGetriggerd() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel1 =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(signaleringBuilder().build(), LO3_HERKOMST_REISDOCUMENT));
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel2 =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                reisdocumentBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapels(Arrays.asList(stapel1, stapel2));

        assertAantalErrors(0);
        assertSoortMeldingCode(SoortMeldingCode.PRE107, 0);
    }
}
