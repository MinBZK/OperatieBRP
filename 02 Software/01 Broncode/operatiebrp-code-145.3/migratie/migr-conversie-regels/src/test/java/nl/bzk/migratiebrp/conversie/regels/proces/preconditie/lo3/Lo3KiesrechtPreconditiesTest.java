/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import nl.bzk.migratiebrp.conversie.regels.tabel.LandConversietabel;
import org.junit.Test;

/**
 * Preconditie tests voor categorie 13: kiesrecht.
 */
public class Lo3KiesrechtPreconditiesTest extends AbstractPreconditieTest {

    private static final String DOC = "Doc";
    private static final String GEM_CODE = "0514";
    private static final Lo3Herkomst LO3_HERKOMST_KIESRECHT = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_13, 0, 0);

    private Lo3KiesrechtPrecondities precondities = new Lo3KiesrechtPrecondities(new ConversietabelFactoryImpl());

    private Lo3KiesrechtInhoud.Builder builder() {
        final Lo3KiesrechtInhoud.Builder builder = new Lo3KiesrechtInhoud.Builder();
        builder.setAanduidingEuropeesKiesrecht(new Lo3AanduidingEuropeesKiesrecht(1));
        builder.setDatumEuropeesKiesrecht(new Lo3Datum(20000101));
        builder.setEinddatumUitsluitingEuropeesKiesrecht(new Lo3Datum(20200101));

        builder.setAanduidingUitgeslotenKiesrecht(new Lo3AanduidingUitgeslotenKiesrecht("A"));
        builder.setEinddatumUitsluitingKiesrecht(new Lo3Datum(20200101));

        return builder;
    }

    @Test
    public void testHappy() {

        final Lo3Stapel<Lo3KiesrechtInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                new Lo3Historie(null, null, null),
                                LO3_HERKOMST_KIESRECHT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr112() {
        final Lo3Stapel<Lo3KiesrechtInhoud> stapel2 =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 00000000, DOC),
                                new Lo3Historie(null, null, null),
                                LO3_HERKOMST_KIESRECHT));

        precondities.controleerStapel(stapel2);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3KiesrechtInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000100, DOC),
                                new Lo3Historie(null, null, null),
                                LO3_HERKOMST_KIESRECHT));

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
    public void testContr4019() {
        final Lo3KiesrechtInhoud.Builder builder = builder();
        builder.setDatumEuropeesKiesrecht(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3KiesrechtInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                new Lo3Historie(null, null, null),
                                LO3_HERKOMST_KIESRECHT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40110() {
        final Lo3KiesrechtInhoud.Builder builder = builder();
        builder.setEinddatumUitsluitingEuropeesKiesrecht(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3KiesrechtInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                new Lo3Historie(null, null, null),
                                LO3_HERKOMST_KIESRECHT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40114() {
        final Lo3KiesrechtInhoud.Builder builder = builder();
        builder.setEinddatumUitsluitingKiesrecht(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3KiesrechtInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                new Lo3Historie(null, null, null),
                                LO3_HERKOMST_KIESRECHT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40121() {
        final Lo3Stapel<Lo3KiesrechtInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder().build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20050155, DOC),
                                new Lo3Historie(null, null, null),
                                LO3_HERKOMST_KIESRECHT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4064() {
        final Lo3Stapel<Lo3KiesrechtInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder().build(),
                                Lo3StapelHelper.lo3Documentatie(1L, null, null, LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getWaarde(), 20010101, DOC),
                                new Lo3Historie(null, null, null),
                                LO3_HERKOMST_KIESRECHT));

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
    public void testContr434() {
        final Lo3KiesrechtInhoud.Builder builder = builder();
        builder.setAanduidingEuropeesKiesrecht(new Lo3AanduidingEuropeesKiesrecht(9));

        final Lo3Stapel<Lo3KiesrechtInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                new Lo3Historie(null, null, null),
                                LO3_HERKOMST_KIESRECHT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr444() {
        final Lo3KiesrechtInhoud.Builder builder = builder();
        builder.setAanduidingUitgeslotenKiesrecht(new Lo3AanduidingUitgeslotenKiesrecht("Q"));

        final Lo3Stapel<Lo3KiesrechtInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                new Lo3Historie(null, null, null),
                                LO3_HERKOMST_KIESRECHT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testPreconditie89() {
        final Lo3KiesrechtInhoud.Builder builder = builder();
        builder.setAanduidingUitgeslotenKiesrecht(null);

        final Lo3Stapel<Lo3KiesrechtInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                new Lo3Historie(null, null, null),
                                LO3_HERKOMST_KIESRECHT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE089, 1);
    }

    @Test
    public void testPreconditie90() {
        final Lo3KiesrechtInhoud.Builder builder = builder();
        builder.setAanduidingEuropeesKiesrecht(null);

        final Lo3Stapel<Lo3KiesrechtInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder.build(),
                                Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                                new Lo3Historie(null, null, null),
                                LO3_HERKOMST_KIESRECHT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE090, 1);
    }
}
