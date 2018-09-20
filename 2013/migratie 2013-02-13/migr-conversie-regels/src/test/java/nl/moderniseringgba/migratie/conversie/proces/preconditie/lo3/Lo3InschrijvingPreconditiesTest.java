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
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.conversie.tabel.GemeenteConversietabel;

import org.junit.Test;

/**
 * Preconditie tests voor categorie 07: inschrijving.
 */
@SuppressWarnings("unchecked")
public class Lo3InschrijvingPreconditiesTest extends AbstractPreconditieTest {

    private static final Lo3Herkomst LO3_HERKOMST_INSCHRIJVING = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0);
    @Inject
    private Lo3InschrijvingPrecondities precondities;

    private Lo3InschrijvingInhoud.Builder builder() {
        final Lo3InschrijvingInhoud.Builder builder = new Lo3InschrijvingInhoud.Builder();
        builder.setDatumIngangBlokkering(new Lo3Datum(20010101));

        builder.setDatumOpschortingBijhouding(new Lo3Datum(20020101));
        builder.setRedenOpschortingBijhoudingCode(new Lo3RedenOpschortingBijhoudingCode("M"));

        builder.setDatumEersteInschrijving(new Lo3Datum(19950405));

        builder.setGemeentePKCode(new Lo3GemeenteCode("0518"));

        builder.setIndicatieGeheimCode(new Lo3IndicatieGeheimCode(0));

        builder.setVersienummer(1);
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
        return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3Historie.NULL_HISTORIE, null,
                LO3_HERKOMST_INSCHRIJVING));

    }

    @Test
    public void testContr241() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setDatumEersteInschrijving(null);

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE037);
    }

    @Test
    public void testContr243() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setVersienummer(null);
        builder.setDatumtijdstempel(null);

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);
        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE037);
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
        assertPreconditie(Precondities.PRE037);
    }

    @Test
    public void testContr345() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setDatumtijdstempel(null);

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE038);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr40118() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setDatumIngangBlokkering(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
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
        assertPreconditie(Precondities.PRE054);
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
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr451() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setIndicatieGeheimCode(new Lo3IndicatieGeheimCode(9));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
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

        assertAantalErrors(1);
    }

    @Test
    public void testBijzondereSituatieGeheimindicatie1() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setIndicatieGeheimCode(new Lo3IndicatieGeheimCode(1));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);
        precondities.controleerStapel(stapel);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB009);
    }

    @Test
    public void testBijzondereSituatieGeheimindicatie3() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setIndicatieGeheimCode(new Lo3IndicatieGeheimCode(3));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);
        precondities.controleerStapel(stapel);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB009);
    }

    @Test
    public void testBijzondereSituatieGeheimindicatie6() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setIndicatieGeheimCode(new Lo3IndicatieGeheimCode(6));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);
        precondities.controleerStapel(stapel);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB009);
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
    public void testBijzondereSituatiePersoonOpgeschortRedenOnbekend() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setRedenOpschortingBijhoudingCode(new Lo3RedenOpschortingBijhoudingCode("."));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);
        precondities.controleerStapel(stapel);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB010);
    }

    @Test
    public void testBijzondereSituatiePersoonOpgeschortRedenFout() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setRedenOpschortingBijhoudingCode(new Lo3RedenOpschortingBijhoudingCode("F"));

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);
        precondities.controleerStapel(stapel);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB011);
    }

    @Test
    public void testBijzondereSituatiePersoonOpgeschortRedenNull() {
        final Lo3InschrijvingInhoud.Builder builder = builder();
        builder.setRedenOpschortingBijhoudingCode(null);

        final Lo3Stapel<Lo3InschrijvingInhoud> stapel = createLo3Stapel(builder);
        precondities.controleerStapel(stapel);
        assertAantalErrors(0);
    }
}
