/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.junit.Assert;
import org.junit.Test;

/** Unit test voor de {@link BerichtResultaat} class. */
public class BerichtResultaatTest {

    /**
     * Standaard dient de lijst met meldingen nooit <code>null</code> te zijn en de resultaat code dient initieel op
     * "GOED" te staan.
     */
    @Test
    public void testConstructor() {
        BerichtResultaat resultaat = new BerichtResultaat(null);
        Assert.assertEquals(ResultaatCode.GOED, resultaat.getResultaatCode());
        Assert.assertNotNull(resultaat.getMeldingen());
    }

    @Test
    public void testConstructorMetMeldingen() {
        List<Melding> meldingen = new ArrayList<Melding>();
        meldingen.add(new Melding(SoortMelding.INFO, MeldingCode.ALG0001));
        meldingen.add(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.BRAL0001));

        BerichtResultaat resultaat = new BerichtResultaat(meldingen);
        Assert.assertEquals(2, resultaat.getMeldingen().size());

        // Test dat meldingen lijst niet 'by reference' wordt overgenomen, maar dat de meldingen uit de lijst worden
        // overgenomen.
        meldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.AUTH0001));
        Assert.assertEquals(3, meldingen.size());
        Assert.assertEquals(2, resultaat.getMeldingen().size());
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

    @Test(expected = UnsupportedOperationException.class)
    public void testFoutIndienMeldingenLijstWordtAangepastVanBuiten() {
        List<Melding> meldingen = new ArrayList<Melding>();
        meldingen.add(new Melding(SoortMelding.INFO, MeldingCode.ALG0001));
        meldingen.add(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.BRAL0001));

        BerichtResultaat resultaat = new BerichtResultaat(meldingen);
        resultaat.getMeldingen().add(new Melding(SoortMelding.INFO, MeldingCode.MR0502));
    }
}
