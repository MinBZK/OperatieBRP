/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.junit.Assert;
import org.junit.Test;

/** Unit test voor de methodes in de {@link AbstractAntwoordBericht} klasse. */
public class TestVoorAbstractAntwoordBericht {

    @Test
    public void testStandaardConstructor() {
        AbstractAntwoordBericht antwoordBericht = new AbstractAntwoordBericht() {
        };
        Assert.assertNull(antwoordBericht.getMeldingen());
    }

    @Test
    public void testConstructorMetNull() {
        AbstractAntwoordBericht antwoordBericht = new AbstractAntwoordBericht(null) {
        };
        Assert.assertNull(antwoordBericht.getMeldingen());
    }

    /**
     * Test dat de lijst van meldingen <code>null</code> is als de opgegeven lijst van meldingen tijdens constructie
     * leeg is.
     */
    @Test
    public void testConstructorMetLegeLijst() {
        AbstractAntwoordBericht antwoordBericht = new AbstractAntwoordBericht(Collections.<Melding>emptyList()) {
        };
        Assert.assertNull(antwoordBericht.getMeldingen());
    }

    @Test
    public void testConstructorMetGevuldeLijst() {
        AbstractAntwoordBericht antwoordBericht = new AbstractAntwoordBericht(Arrays
            .asList(new Melding(SoortMelding.INFO, MeldingCode.ALG0001),
                new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0002)))
        {
        };
        Assert.assertNotNull(antwoordBericht.getMeldingen());
        Assert.assertEquals(2, antwoordBericht.getMeldingen().size());
    }

    /** Test dat als we de initiele lijst aanpassen, dat de meldingen in het antwoordbericht niet zijn aangepast. */
    @Test
    public void testAanpassingenInitieleMeldingenLijst() {
        List<Melding> initMeldingen = new ArrayList<Melding>();
        initMeldingen.add(new Melding(SoortMelding.INFO, MeldingCode.ALG0001));
        initMeldingen.add(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0002));

        AbstractAntwoordBericht antwoordBericht = new AbstractAntwoordBericht(initMeldingen) {
        };
        Assert.assertEquals(2, antwoordBericht.getMeldingen().size());

        initMeldingen.add(new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.AUTH0001));
        Assert.assertEquals(3, initMeldingen.size());
        Assert.assertEquals(2, antwoordBericht.getMeldingen().size());
    }

    /** Test dat de meldingen lijst die je ophaalt uit het antwoordbericht niet aangepast kunnen worden. */
    @Test(expected = UnsupportedOperationException.class)
    public void testImmutabiliteitVanMeldingenLijst() {
        List<Melding> initMeldingen = new ArrayList<Melding>();
        initMeldingen.add(new Melding(SoortMelding.INFO, MeldingCode.ALG0001));
        initMeldingen.add(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0002));

        AbstractAntwoordBericht antwoordBericht = new AbstractAntwoordBericht(initMeldingen) {
        };
        Assert.assertEquals(2, antwoordBericht.getMeldingen().size());

        List<Melding> antwoordMeldingen = antwoordBericht.getMeldingen();
        antwoordMeldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.AUTH0001));
    }
}
