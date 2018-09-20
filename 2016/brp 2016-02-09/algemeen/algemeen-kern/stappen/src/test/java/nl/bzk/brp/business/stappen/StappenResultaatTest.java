/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.business.stappen.support.BrpStapResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de {@link nl.bzk.brp.business.stappen.support.BrpStapResultaat} class.
 */
public class StappenResultaatTest {

    private static final String TEST_1 = "Test1";
    private static final String TEST_2 = "Test2";
    private static final String TEST_3 = "Test3";
    private static final String TEST_4 = "Test4";
    private static final String TEST_5 = "Test5";
    private static final String TEST_6 = "Test6";
    private static final String TEST_7 = "Test7";

    /**
     * Standaard dient de lijst met meldingen nooit <code>null</code> te zijn en de resultaat code dient initieel op
     * "GOED" te staan.
     */
    @Test
    public void testConstructor() {
        final BrpStapResultaat resultaat = new BrpStapResultaat(null);
        Assert.assertTrue(resultaat.isSuccesvol());
        Assert.assertNotNull(resultaat.getMeldingen());
    }

    @Test
    public void testConstructorMetMeldingen() {
        final List<Melding> meldingen = new ArrayList<>();
        meldingen.add(new Melding(SoortMelding.INFORMATIE, Regel.ALG0001));
        meldingen.add(new Melding(SoortMelding.WAARSCHUWING, Regel.BRAL0001));

        final BrpStapResultaat resultaat = new BrpStapResultaat(meldingen);
        Assert.assertEquals(2, resultaat.getMeldingen().size());

        // Test dat meldingen lijst niet 'by reference' wordt overgenomen, maar dat de meldingen uit de lijst worden
        // overgenomen.
        meldingen.add(new Melding(SoortMelding.FOUT, Regel.AUTH0001));
        Assert.assertEquals(3, meldingen.size());
        Assert.assertEquals(2, resultaat.getMeldingen().size());
    }

    @Test
    public void testToevoegenEnkeleMelding() {
        final BrpStapResultaat resultaat = new BrpStapResultaat(null);
        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.BRAL0001));

        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals("BRAL0001", resultaat.getMeldingen().get(0).getRegel().getCode());
    }

    @Test
    public void testToevoegenMeerdereMeldingen() {
        final BrpStapResultaat resultaat = new BrpStapResultaat(null);
        resultaat.voegMeldingenToe(Arrays.asList(new Melding(SoortMelding.FOUT, Regel.BRAL0001),
                new Melding(SoortMelding.WAARSCHUWING, Regel.ACT0001)));

        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(2, resultaat.getMeldingen().size());

        resultaat.voegMeldingenToe(Arrays.asList(new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.BRAL0102),
                new Melding(SoortMelding.INFORMATIE, Regel.REF0001)));
        Assert.assertEquals(4, resultaat.getMeldingen().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testFoutIndienMeldingenLijstWordtAangepastVanBuiten() {
        final List<Melding> meldingen = new ArrayList<>();
        meldingen.add(new Melding(SoortMelding.INFORMATIE, Regel.ALG0001));
        meldingen.add(new Melding(SoortMelding.WAARSCHUWING, Regel.BRAL0001));

        final BrpStapResultaat resultaat = new BrpStapResultaat(meldingen);
        resultaat.getMeldingen().add(new Melding(SoortMelding.INFORMATIE, Regel.ACT0002));
    }

    @Test
    public void testBevatVerwerkingStoppendeFouten() {
        BrpStapResultaat resultaat;
        List<Melding> meldingen;

        resultaat = new BrpStapResultaat(null);
        Assert.assertFalse(resultaat.bevatStoppendeFouten());

        meldingen = new ArrayList<>();
        resultaat = new BrpStapResultaat(meldingen);
        Assert.assertFalse(resultaat.bevatStoppendeFouten());

        meldingen = Arrays.asList(new Melding(SoortMelding.INFORMATIE, Regel.ALG0001),
                new Melding(SoortMelding.WAARSCHUWING, Regel.ALG0001));
        resultaat = new BrpStapResultaat(meldingen);
        Assert.assertFalse(resultaat.bevatStoppendeFouten());

        meldingen = Arrays.asList(new Melding(SoortMelding.INFORMATIE, Regel.ALG0001),
                new Melding(SoortMelding.FOUT, Regel.ALG0001));
        resultaat = new BrpStapResultaat(meldingen);
        Assert.assertTrue(resultaat.bevatStoppendeFouten());

        meldingen = Arrays.asList(new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.ALG0001),
                new Melding(SoortMelding.WAARSCHUWING, Regel.ALG0001));
        resultaat = new BrpStapResultaat(meldingen);
        Assert.assertTrue(resultaat.bevatStoppendeFouten());
    }

    @Test
    public void testSorteringGetMeldingen() {
        final BrpStapResultaat resultaat = new BrpStapResultaat(null);
        resultaat.voegMeldingToe(new Melding(SoortMelding.INFORMATIE, Regel.ALG0001, TEST_1));
        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.BRAL0001, TEST_2));
        resultaat.voegMeldingToe(new Melding(SoortMelding.WAARSCHUWING, Regel.BRAL2102, TEST_3));
        resultaat.voegMeldingToe(new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.ALG0001, TEST_4));
        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001, TEST_5));
        resultaat.voegMeldingToe(new Melding(SoortMelding.WAARSCHUWING, Regel.BRAL1022, TEST_6));
        resultaat.voegMeldingToe(new Melding(SoortMelding.WAARSCHUWING, Regel.BRAL1021, TEST_7));

        final List<Melding> meldingen = resultaat.getMeldingen();

        Assert.assertEquals(TEST_5, meldingen.get(0).getMeldingTekst());
        Assert.assertEquals(TEST_2, meldingen.get(1).getMeldingTekst());
        Assert.assertEquals(TEST_4, meldingen.get(2).getMeldingTekst());
        Assert.assertEquals(TEST_7, meldingen.get(3).getMeldingTekst());
        Assert.assertEquals(TEST_6, meldingen.get(4).getMeldingTekst());
        Assert.assertEquals(TEST_3, meldingen.get(5).getMeldingTekst());
        Assert.assertEquals(TEST_1, meldingen.get(6).getMeldingTekst());
    }

    @Test
    public void zouIsFoutiefTrueEnIsSuccesvolIsFalseMoetenGevenAlsVerwerkingsFoutGezetIs() {
        final BrpStapResultaat resultaat = new BrpStapResultaat(null);

        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001, "Foutmelding"));
        Assert.assertFalse(resultaat.isSuccesvol());
        Assert.assertTrue(resultaat.isFoutief());
    }

    /**
     * Test dat de volgorde van meldingen veranderd na het overrulen van een melding.
     */
    @Test
    public void zouMeldingMoetenOverrulen() {
        final BrpStapResultaat resultaat = new BrpStapResultaat(null);

        final Melding overrulebaar = new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.ALG0001, "Deblokkeerbare melding");
        final Melding melding = new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.ALG0002, "Deblokkeerbare melding 2");
        resultaat.voegMeldingToe(overrulebaar);
        resultaat.voegMeldingToe(melding);

        Assert.assertTrue(resultaat.isFoutief());
        List<Melding> meldingen = resultaat.getMeldingen();
        int index = meldingen.indexOf(overrulebaar);
        Assert.assertSame(0, index);
        int index2 = meldingen.indexOf(melding);
        Assert.assertSame(1, index2);

        resultaat.deblokkeer(overrulebaar);

        meldingen = resultaat.getMeldingen();
        index = meldingen.indexOf(overrulebaar);
        final Melding overruled = meldingen.get(index);
        Assert.assertSame(SoortMelding.WAARSCHUWING, overruled.getSoort());
        Assert.assertSame(1, index);
        index2 = meldingen.indexOf(melding);
        Assert.assertSame(0, index2);
    }
}
