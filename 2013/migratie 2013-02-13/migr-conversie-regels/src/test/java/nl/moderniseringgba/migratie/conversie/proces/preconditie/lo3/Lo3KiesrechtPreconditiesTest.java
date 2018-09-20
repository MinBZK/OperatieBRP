/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.conversie.tabel.LandConversietabel;

import org.junit.Test;

/**
 * Preconditie tests voor categorie 13: kiesrecht.
 */
@SuppressWarnings("unchecked")
public class Lo3KiesrechtPreconditiesTest extends AbstractPreconditieTest {

    private static final Lo3Herkomst LO3_HERKOMST_KIESRECHT = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_13, 0, 0);
    @Inject
    private Lo3KiesrechtPrecondities precondities;

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
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3Historie.NULL_HISTORIE,
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_KIESRECHT));

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
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3Historie.NULL_HISTORIE,
                        Lo3StapelHelper.lo3Doc(1L, "0514", 00000000, "Doc"), LO3_HERKOMST_KIESRECHT));

        precondities.controleerStapel(stapel2);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3KiesrechtInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3Historie.NULL_HISTORIE,
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000100, "Doc"), LO3_HERKOMST_KIESRECHT));

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
    public void testContr4019() {
        final Lo3KiesrechtInhoud.Builder builder = builder();
        builder.setDatumEuropeesKiesrecht(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3KiesrechtInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3Historie.NULL_HISTORIE,
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_KIESRECHT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40110() {
        final Lo3KiesrechtInhoud.Builder builder = builder();
        builder.setEinddatumUitsluitingEuropeesKiesrecht(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3KiesrechtInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3Historie.NULL_HISTORIE,
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_KIESRECHT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40114() {
        final Lo3KiesrechtInhoud.Builder builder = builder();
        builder.setEinddatumUitsluitingKiesrecht(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3KiesrechtInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3Historie.NULL_HISTORIE,
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_KIESRECHT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40121() {
        final Lo3Stapel<Lo3KiesrechtInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3Historie.NULL_HISTORIE,
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20050155, "Doc"), LO3_HERKOMST_KIESRECHT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4064() {
        final Lo3Stapel<Lo3KiesrechtInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3Historie.NULL_HISTORIE,
                        Lo3StapelHelper.lo3Documentatie(1L, null, null,
                                LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getCode(), 20010101, "Doc"),
                        LO3_HERKOMST_KIESRECHT));

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
    public void testContr434() {
        final Lo3KiesrechtInhoud.Builder builder = builder();
        builder.setAanduidingEuropeesKiesrecht(new Lo3AanduidingEuropeesKiesrecht(9));

        final Lo3Stapel<Lo3KiesrechtInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3Historie.NULL_HISTORIE,
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_KIESRECHT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr444() {
        final Lo3KiesrechtInhoud.Builder builder = builder();
        builder.setAanduidingUitgeslotenKiesrecht(new Lo3AanduidingUitgeslotenKiesrecht("Q"));

        final Lo3Stapel<Lo3KiesrechtInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3Historie.NULL_HISTORIE,
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_KIESRECHT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }
}
