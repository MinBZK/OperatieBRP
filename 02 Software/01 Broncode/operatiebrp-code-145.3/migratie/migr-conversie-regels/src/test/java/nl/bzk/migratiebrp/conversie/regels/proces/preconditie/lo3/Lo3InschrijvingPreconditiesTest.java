/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import nl.bzk.migratiebrp.conversie.regels.tabel.GemeenteConversietabel;
import org.junit.Test;

/**
 * Preconditie tests voor categorie 07: inschrijving.
 */
public class Lo3InschrijvingPreconditiesTest extends AbstractPreconditieTest {

    private static final Lo3Herkomst LO3_HERKOMST_INSCHRIJVING = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0);
    private Lo3InschrijvingPrecondities precondities = new Lo3InschrijvingPrecondities(new ConversietabelFactoryImpl());

    private Lo3InschrijvingInhoud.Builder builder() {
        final Lo3InschrijvingInhoud.Builder builder = new Lo3InschrijvingInhoud.Builder();

        builder.setDatumOpschortingBijhouding(new Lo3Datum(20020101));
        builder.setRedenOpschortingBijhoudingCode(new Lo3RedenOpschortingBijhoudingCode("M"));

        builder.setDatumEersteInschrijving(new Lo3Datum(19950405));

        builder.setGemeentePKCode(new Lo3GemeenteCode("0518"));

        builder.setIndicatieGeheimCode(new Lo3IndicatieGeheimCode(0));

        builder.setVersienummer(new Lo3Integer(1));
        builder.setDatumtijdstempel(new Lo3Datumtijdstempel(20040404171515000L));

        builder.setIndicatiePKVolledigGeconverteerdCode(new Lo3IndicatiePKVolledigGeconverteerdCode("P"));

        return builder;
    }

    @Test
    public void testHappy() {
        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder());
        precondities.controleerStapel(stapel);
        assertAantalWarnings(0);
        assertAantalInfos(0);
        assertAantalErrors(0);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private Lo3Stapel<Lo3InschrijvingInhoud> createLo3Stapel(final Lo3InschrijvingInhoud.Builder builder) {
        return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, new Lo3Historie(null, null, null), LO3_HERKOMST_INSCHRIJVING));

    }

    @Test
    public void testContr241() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setDatumEersteInschrijving(null);

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE037, 1);
    }

    @Test
    public void testContr243() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setVersienummer(null);
        builder.setDatumtijdstempel(null);

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE037, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr344() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setVersienummer(null);

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE037, 1);
    }

    @Test
    public void testContr345() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setDatumtijdstempel(null);

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE038, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testOngeldigeDatumIngangBlokkering() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        // Ongeldige datum
        builder.setDatumIngangBlokkering(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testDatumIngangBlokkering() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setDatumIngangBlokkering(new Lo3Datum(20010101));

        precondities.controleerStapel(createLo3Stapel(builder));

        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB034, 1);
    }

    @Test
    public void testContr40119() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setDatumOpschortingBijhouding(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40120() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setDatumEersteInschrijving(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4062() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setGemeentePKCode(GemeenteConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);

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
    public void testContr450() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setRedenOpschortingBijhoudingCode(new Lo3RedenOpschortingBijhoudingCode("Q"));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr451() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setIndicatieGeheimCode(new Lo3IndicatieGeheimCode(9));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr455() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setDatumtijdstempel(new Lo3Datumtijdstempel(20041404171515000L));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr460() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setIndicatiePKVolledigGeconverteerdCode(new Lo3IndicatiePKVolledigGeconverteerdCode("Q"));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);

        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE077, 1);
        assertAantalErrors(1);
    }

    // ORANJE-857
    @Test
    public void testOvergangWintertijdZomertijd() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setDatumtijdstempel(new Lo3Datumtijdstempel(20120325020137190L));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB009)
    public void testBijzondereSituatieGeheimindicatie1() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setIndicatieGeheimCode(new Lo3IndicatieGeheimCode(1));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);
        precondities.controleerStapel(stapel);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB009, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB009)
    public void testBijzondereSituatieGeheimindicatie3() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setIndicatieGeheimCode(new Lo3IndicatieGeheimCode(3));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);
        precondities.controleerStapel(stapel);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB009, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB009)
    public void testBijzondereSituatieGeheimindicatie6() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setIndicatieGeheimCode(new Lo3IndicatieGeheimCode(6));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);
        precondities.controleerStapel(stapel);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB009, 1);
    }

    @Test
    public void testBijzondereSituatieGeheimindicatie7() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setIndicatieGeheimCode(new Lo3IndicatieGeheimCode(7));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);
        precondities.controleerStapel(stapel);
        assertAantalInfos(0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB011)
    public void testBijzondereSituatiePersoonOpgeschortRedenFout() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setRedenOpschortingBijhoudingCode(new Lo3RedenOpschortingBijhoudingCode("F"));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);
        precondities.controleerStapel(stapel);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB011, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE087)
    public void testBijzondereSituatiePersoonOpgeschortRedenNull() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setRedenOpschortingBijhoudingCode(null);

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);
        precondities.controleerStapel(stapel);
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE087, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB030)
    @Preconditie(SoortMeldingCode.PRE097)
    public void testVerificatiePre097omschrijving() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setDatumVerificatie(new Lo3Datum(19990101));
        builder.setOmschrijvingVerificatie(null);

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE097, 1);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB030, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB030)
    @Preconditie(SoortMeldingCode.PRE097)
    public void testVerificatiePre097datum() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setDatumVerificatie(null);
        builder.setOmschrijvingVerificatie(new Lo3String("verificatie"));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE097, 1);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB030, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB030)
    @Preconditie(SoortMeldingCode.PRE097)
    public void testVerificatieAfwezigPre097afwezig() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setDatumVerificatie(null);
        builder.setOmschrijvingVerificatie(null);

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
        assertAantalInfos(0);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB030, 0);
    }

    @Test
    public void testPreconditie098GroepNietAanwezig() {
        final Lo3Stapel<Lo3InschrijvingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                builder().build(),
                                Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_INSCHRIJVING));
        precondities.controleerStapel(stapel);
        assertAantalErrors(0);
        assertSoortMeldingCode(SoortMeldingCode.PRE098, 0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB032)
    @Preconditie(SoortMeldingCode.PRE098)
    public void testPreconditie098GroepAanwezig8810NietGevuld() {
        final Lo3Documentatie lo3Doc = Lo3StapelHelper.lo3Documentatie(1L, null, null, null, null, null, null, "omschrijvingVerdrag");
        final Lo3Stapel<Lo3InschrijvingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), lo3Doc, new Lo3Historie(null, null, null), LO3_HERKOMST_INSCHRIJVING));
        precondities.controleerStapel(stapel);
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE098, 1);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB032, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB031)
    @Preconditie(SoortMeldingCode.PRE098)
    public void testPreconditie098GroepAanwezig8810Gevuld() {
        final Lo3Documentatie lo3Doc = Lo3StapelHelper.lo3Documentatie(1L, null, null, null, null, null, "0000", null);
        final Lo3Stapel<Lo3InschrijvingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), lo3Doc, new Lo3Historie(null, null, null), LO3_HERKOMST_INSCHRIJVING));
        precondities.controleerStapel(stapel);
        assertAantalErrors(0);
        assertSoortMeldingCode(SoortMeldingCode.PRE098, 0);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB031, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE101)
    public void testBijzondereSituatiePersoonOpgeschortRedenOnbekend() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setRedenOpschortingBijhoudingCode(new Lo3RedenOpschortingBijhoudingCode("."));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);
        precondities.controleerStapel(stapel);
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE101, 1);
    }
}
