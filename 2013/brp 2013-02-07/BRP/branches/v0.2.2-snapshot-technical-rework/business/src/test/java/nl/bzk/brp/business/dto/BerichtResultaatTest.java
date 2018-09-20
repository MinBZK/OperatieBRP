/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortMelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.OverruleMelding;
import org.junit.Assert;
import org.junit.Test;

/** Unit test voor de {@link BerichtVerwerkingsResultaat} class. */
public class BerichtResultaatTest {

    /**
     * Standaard dient de lijst met meldingen nooit <code>null</code> te zijn en de resultaat code dient initieel op
     * "GOED" te staan.
     */
    @Test
    public void testConstructor() {
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        Assert.assertTrue(resultaat.getVerwerkingsResultaat());
        Assert.assertNotNull(resultaat.getMeldingen());
    }

    @Test
    public void testConstructorMetMeldingen() {
        List<Melding> meldingen = new ArrayList<Melding>();
        meldingen.add(new Melding(SoortMelding.INFORMATIE, MeldingCode.ALG0001));
        meldingen.add(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.BRAL0001));

        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(meldingen);
        Assert.assertEquals(2, resultaat.getMeldingen().size());

        // Test dat meldingen lijst niet 'by reference' wordt overgenomen, maar dat de meldingen uit de lijst worden
        // overgenomen.
        meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.AUTH0001));
        Assert.assertEquals(3, meldingen.size());
        Assert.assertEquals(2, resultaat.getMeldingen().size());
    }

    @Test
    public void testToevoegenEnkeleMelding() {
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, MeldingCode.BRAL0001));

        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.BRAL0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testToevoegenMeerdereMeldingen() {
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        resultaat.voegMeldingenToe(Arrays.asList(new Melding(SoortMelding.FOUT, MeldingCode.BRAL0001),
            new Melding(SoortMelding.WAARSCHUWING, MeldingCode.MR0502)));

        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(2, resultaat.getMeldingen().size());

        resultaat.voegMeldingenToe(Arrays.asList(new Melding(SoortMelding.OVERRULEBAAR, MeldingCode.BRAL0102),
            new Melding(SoortMelding.INFORMATIE, MeldingCode.REF0001)));
        Assert.assertEquals(4, resultaat.getMeldingen().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testFoutIndienMeldingenLijstWordtAangepastVanBuiten() {
        List<Melding> meldingen = new ArrayList<Melding>();
        meldingen.add(new Melding(SoortMelding.INFORMATIE, MeldingCode.ALG0001));
        meldingen.add(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.BRAL0001));

        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(meldingen);
        resultaat.getMeldingen().add(new Melding(SoortMelding.INFORMATIE, MeldingCode.MR0502));
    }

    @Test
    public void testBevatVerwerkingStoppendeFouten() {
        BerichtVerwerkingsResultaat resultaat;
        List<Melding> meldingen;

        meldingen = null;
        resultaat = new BerichtVerwerkingsResultaat(meldingen);
        Assert.assertFalse(resultaat.bevatVerwerkingStoppendeFouten());

        meldingen = new ArrayList<Melding>();
        resultaat = new BerichtVerwerkingsResultaat(meldingen);
        Assert.assertFalse(resultaat.bevatVerwerkingStoppendeFouten());

        meldingen = Arrays.asList(new Melding(SoortMelding.INFORMATIE, MeldingCode.ALG0001),
            new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001));
        resultaat = new BerichtVerwerkingsResultaat(meldingen);
        Assert.assertFalse(resultaat.bevatVerwerkingStoppendeFouten());

        meldingen = Arrays.asList(new Melding(SoortMelding.INFORMATIE, MeldingCode.ALG0001),
            new Melding(SoortMelding.FOUT, MeldingCode.ALG0001));
        resultaat = new BerichtVerwerkingsResultaat(meldingen);
        Assert.assertTrue(resultaat.bevatVerwerkingStoppendeFouten());

        meldingen = Arrays.asList(new Melding(SoortMelding.OVERRULEBAAR, MeldingCode.ALG0001),
            new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001));
        resultaat = new BerichtVerwerkingsResultaat(meldingen);
        Assert.assertTrue(resultaat.bevatVerwerkingStoppendeFouten());
    }

    @Test
    public void testSorteringGetMeldingen() {
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        resultaat.voegMeldingToe(new Melding(SoortMelding.INFORMATIE, MeldingCode.ALG0001, "Test1"));
        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, MeldingCode.BRAL0001, "Test2"));
        resultaat.voegMeldingToe(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.BRAL2102, "Test3"));
        resultaat.voegMeldingToe(new Melding(SoortMelding.OVERRULEBAAR, MeldingCode.ALG0001, "Test4"));
        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, MeldingCode.ALG0001, "Test5"));
        resultaat.voegMeldingToe(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.BRAL1022, "Test6"));
        resultaat.voegMeldingToe(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.BRAL1021, "Test7"));

        List<Melding> meldingen = resultaat.getMeldingen();

        Assert.assertEquals("Test5", meldingen.get(0).getOmschrijving());
        Assert.assertEquals("Test2", meldingen.get(1).getOmschrijving());
        Assert.assertEquals("Test4", meldingen.get(2).getOmschrijving());
        Assert.assertEquals("Test7", meldingen.get(3).getOmschrijving());
        Assert.assertEquals("Test6", meldingen.get(4).getOmschrijving());
        Assert.assertEquals("Test3", meldingen.get(5).getOmschrijving());
        Assert.assertEquals("Test1", meldingen.get(6).getOmschrijving());
    }

    @Test
    public void testMarkeerAlsFoutief() {
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);

        Assert.assertTrue(resultaat.getVerwerkingsResultaat());

        resultaat.markeerVerwerkingAlsFoutief();

        Assert.assertFalse(resultaat.getVerwerkingsResultaat());
    }

    @Test
    public void testOverruleAlleOverrulebareFouten() {
        //TODO refactor test: overrule lijst zou in principe ook kunnen bestaan uit andere objecten dan resultaat
        Melding test1 = new Melding(SoortMelding.OVERRULEBAAR, MeldingCode.ALG0001, "Test1");
        Melding test2 = new Melding(SoortMelding.OVERRULEBAAR, MeldingCode.ALG0002, "Test2");

        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        resultaat.voegMeldingToe(test1);
        resultaat.voegMeldingToe(test2);

        List<Melding> overrule = new ArrayList<Melding>();
        overrule.add(test1);

        resultaat.overruleAlleOverrulebareFouten(overrule);

        Melding melding0 = resultaat.getMeldingen().get(0);
        Assert.assertEquals("Test2", melding0.getOmschrijving());
        Assert.assertEquals(SoortMelding.OVERRULEBAAR, melding0.getSoort());

        Melding melding1 = resultaat.getMeldingen().get(1);
        Assert.assertEquals("Test1", melding1.getOmschrijving());
        Assert.assertEquals(SoortMelding.WAARSCHUWING, melding1.getSoort());
    }

    @Test
    public void testVoegtoeOverruleMeldingen() {
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);

        resultaat.voegtoeOverruleMeldingen(Arrays.asList(new OverruleMelding(MeldingCode.ACT0002)));
        resultaat.voegtoeOverruleMeldingen(Arrays.asList(new OverruleMelding(MeldingCode.ACT0001)));

        Assert.assertEquals(2, resultaat.getOverruleMeldingen().size());
        Assert.assertEquals(MeldingCode.ACT0001.name(), resultaat.getOverruleMeldingen().get(0).getCode());
        Assert.assertEquals(MeldingCode.ACT0002.name(), resultaat.getOverruleMeldingen().get(1).getCode());
    }
}
