/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/** Unit test voor de {@link }BerichtResultaat} class. */
public class BerichtResultaatTest {

    @Test
    public void testConstructorEnResultaatCodeVoorNull() {
        BerichtResultaat resultaat = new BerichtResultaat(null);
        Assert.assertEquals(ResultaatCode.GOED, resultaat.getResultaat());
        Assert.assertNull(resultaat.getMeldingen());
    }

    @Test
    public void testConstructorEnResultaatCodeVoorEnkeleMelding() {
        testConstructorEnResultaatVoorMeldingen(ResultaatCode.GOED, SoortMelding.INFO);
        testConstructorEnResultaatVoorMeldingen(ResultaatCode.GOED, SoortMelding.WAARSCHUWING);
        testConstructorEnResultaatVoorMeldingen(ResultaatCode.FOUT, SoortMelding.FOUT_ONOVERRULEBAAR);
        testConstructorEnResultaatVoorMeldingen(ResultaatCode.FOUT, SoortMelding.FOUT_OVERRULEBAAR);
    }

    @Test
    public void testConstructorEnResultaatCodeVoorMeerdereMeldingen() {
        testConstructorEnResultaatVoorMeldingen(ResultaatCode.GOED, SoortMelding.INFO, SoortMelding.INFO);
        testConstructorEnResultaatVoorMeldingen(ResultaatCode.GOED, SoortMelding.WAARSCHUWING, SoortMelding.INFO);
        testConstructorEnResultaatVoorMeldingen(ResultaatCode.FOUT, SoortMelding.FOUT_ONOVERRULEBAAR,
            SoortMelding.INFO);
        testConstructorEnResultaatVoorMeldingen(ResultaatCode.FOUT, SoortMelding.INFO, SoortMelding.FOUT_OVERRULEBAAR);
        testConstructorEnResultaatVoorMeldingen(ResultaatCode.FOUT, SoortMelding.FOUT_ONOVERRULEBAAR,
            SoortMelding.FOUT_OVERRULEBAAR);
    }

    @Test
    public void testToevoegenEnkeleMelding() {
        BerichtResultaat resultaat = new BerichtResultaat(null);
        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.BRAL0001));

        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.BRAL0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testToevoegenMeerdereMeldingen() {
        BerichtResultaat resultaat = new BerichtResultaat(null);
        resultaat.voegMeldingenToe(Arrays.asList(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.BRAL0001),
            new Melding(SoortMelding.WAARSCHUWING, MeldingCode.MR0502)));

        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(2, resultaat.getMeldingen().size());

        resultaat.voegMeldingenToe(Arrays.asList(new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRAL0102),
            new Melding(SoortMelding.INFO, MeldingCode.REF0001)));
        Assert.assertEquals(4, resultaat.getMeldingen().size());
    }

    private void testConstructorEnResultaatVoorMeldingen(final ResultaatCode verwachtResultaat,
        final SoortMelding... soortMeldingen)
    {
        List<Melding> meldingen = new ArrayList<Melding>();
        for (SoortMelding soortMelding : soortMeldingen) {
            meldingen.add(new Melding(soortMelding, MeldingCode.ALG0001));
        }
        BerichtResultaat resultaat = new BerichtResultaat(meldingen);

        Assert.assertEquals(verwachtResultaat, resultaat.getResultaat());
        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(soortMeldingen.length, resultaat.getMeldingen().size());
    }

}
