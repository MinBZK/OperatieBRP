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

import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.BerichtStuurgegevens;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.web.bijhouding.BijhoudingAntwoordBericht;
import nl.bzk.brp.web.bijhouding.VerwerkingsResultaat;
import org.junit.Assert;
import org.junit.Test;


/** Unit test voor de methodes in de {@link AbstractAntwoordBericht} klasse. */
public class TestVoorAbstractAntwoordBericht {

    @Test
    public void testStandaardConstructor() {
        AbstractAntwoordBericht antwoordBericht = new AbstractAntwoordBericht() {
        };

        BerichtStuurgegevens berichtStuurgegevens = antwoordBericht.getBerichtStuurgegevens();
        Assert.assertNotNull(berichtStuurgegevens);
        Assert.assertNull(berichtStuurgegevens.getApplicatie());
        //TODO Hosing: op een of andere manier is referentie nummer onbekend via eclipse, maar via maven clean install is de referentie 1
        Assert.assertNull(berichtStuurgegevens.getReferentienummer());
        Assert.assertNull(berichtStuurgegevens.getOrganisatie());
        Assert.assertNull(berichtStuurgegevens.getCrossReferentienummer());

        Assert.assertNull(antwoordBericht.getVerwerkingsResultaat());
        Assert.assertNull(antwoordBericht.getMeldingen());
        //Assert.assertNull(antwoordBericht.getHoogsteMeldingNiveau());
    }

    @Test
    public void testConstructorMetNull() {
        AbstractAntwoordBericht antwoordBericht = new AbstractAntwoordBericht(null) {
        };

        BerichtStuurgegevens berichtStuurgegevens = antwoordBericht.getBerichtStuurgegevens();
        Assert.assertNotNull(berichtStuurgegevens);
        Assert.assertNull(berichtStuurgegevens.getApplicatie());
        //TODO Hosing: op een of andere manier is referentie nummer onbekend via eclipse, maar via maven clean install is de referentie 1
        Assert.assertNull(berichtStuurgegevens.getReferentienummer());
        Assert.assertNull(berichtStuurgegevens.getOrganisatie());
        Assert.assertNull(berichtStuurgegevens.getCrossReferentienummer());

        Assert.assertNull(antwoordBericht.getVerwerkingsResultaat());
        Assert.assertNull(antwoordBericht.getMeldingen());
        //Assert.assertNull(antwoordBericht.getHoogsteMeldingNiveau());
    }

    /**
     * Test dat de lijst van meldingen <code>null</code> is als de opgegeven lijst van meldingen tijdens constructie
     * leeg is.
     */
    @Test
    public void testConstructorMetLegeLijst() {

        AbstractAntwoordBericht antwoordBericht =
            new AbstractAntwoordBericht(new BerichtResultaat(Collections.<Melding>emptyList())) {
            };
        Assert.assertNull(antwoordBericht.getMeldingen());
    }

    @Test
    public void testConstructorMetGevuldeLijst() {
        AbstractAntwoordBericht antwoordBericht =
            new AbstractAntwoordBericht(new BerichtResultaat(Arrays.asList(new Melding(SoortMelding.INFO,
                    MeldingCode.ALG0001), new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0002))))
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

        AbstractAntwoordBericht antwoordBericht = new AbstractAntwoordBericht(new BerichtResultaat(initMeldingen)) {
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

        AbstractAntwoordBericht antwoordBericht = new AbstractAntwoordBericht(new BerichtResultaat(initMeldingen)) {
        };
        Assert.assertEquals(2, antwoordBericht.getMeldingen().size());

        List<Melding> antwoordMeldingen = antwoordBericht.getMeldingen();
        antwoordMeldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.AUTH0001));
    }

    @Test
    public void testBepaalHoogsteNiveau() {
        AbstractAntwoordBericht antwoordBericht;

        // Test met "Geen" meldingen
        antwoordBericht = new AbstractAntwoordBericht(new BerichtResultaat(null)) {
        };
        // TODO Hosing: Wat is het correcte gedrag? Moet bij een resultaat zonder meldingen de hoogste niveau een "G"
        // zijn?
        // Assert.assertEquals("G", antwoordBericht.getHoogsteMeldingNiveau());
        //Assert.assertNull(antwoordBericht.getHoogsteMeldingNiveau());

        // Test met enkele melding van niveau info
        antwoordBericht = new AbstractAntwoordBericht(new BerichtResultaat(bouwLijstVanMeldingen(SoortMelding.INFO))) {
        };
        //Assert.assertEquals("I", antwoordBericht.getHoogsteMeldingNiveau());

        // Test met enkele melding van niveau waarschuwing
        antwoordBericht =
            new AbstractAntwoordBericht(new BerichtResultaat(bouwLijstVanMeldingen(SoortMelding.WAARSCHUWING))) {
            };
        //Assert.assertEquals("W", antwoordBericht.getHoogsteMeldingNiveau());

        // Test met enkele melding van niveau overrulebare fout
        antwoordBericht =
            new AbstractAntwoordBericht(new BerichtResultaat(bouwLijstVanMeldingen(SoortMelding.FOUT_OVERRULEBAAR))) {
            };
        //Assert.assertEquals("O", antwoordBericht.getHoogsteMeldingNiveau());

        // Test met enkele melding van niveau fout/onoverrulebare fout
        antwoordBericht =
            new AbstractAntwoordBericht(new BerichtResultaat(bouwLijstVanMeldingen(SoortMelding.FOUT_ONOVERRULEBAAR))) {
            };
        //Assert.assertEquals("F", antwoordBericht.getHoogsteMeldingNiveau());

        // Test met meerdere meldingen van zelfde niveau
        antwoordBericht =
            new AbstractAntwoordBericht(new BerichtResultaat(bouwLijstVanMeldingen(SoortMelding.INFO,
                    SoortMelding.INFO, SoortMelding.INFO)))
            {
            };
        //Assert.assertEquals("I", antwoordBericht.getHoogsteMeldingNiveau());

        // Test met meldingen van alle niveau's
        antwoordBericht =
            new AbstractAntwoordBericht(new BerichtResultaat(bouwLijstVanMeldingen(SoortMelding.INFO,
                    SoortMelding.WAARSCHUWING, SoortMelding.FOUT_OVERRULEBAAR, SoortMelding.FOUT_ONOVERRULEBAAR)))
            {
            };
        //Assert.assertEquals("F", antwoordBericht.getHoogsteMeldingNiveau());

        // Test met meldingen van alle niveau's, maar in andere volgorde
        antwoordBericht =
            new BijhoudingAntwoordBericht(new BijhoudingResultaat(bouwLijstVanMeldingen(SoortMelding.FOUT_ONOVERRULEBAAR,
                    SoortMelding.FOUT_OVERRULEBAAR, SoortMelding.WAARSCHUWING, SoortMelding.INFO)));
        //Assert.assertEquals("F", antwoordBericht.getHoogsteMeldingNiveau());

        // Test met meldingen van enkele niveau's
        antwoordBericht =
            new AbstractAntwoordBericht(new BerichtResultaat(bouwLijstVanMeldingen(SoortMelding.INFO,
                    SoortMelding.WAARSCHUWING, SoortMelding.FOUT_OVERRULEBAAR)))
            {
            };
        //Assert.assertEquals("O", antwoordBericht.getHoogsteMeldingNiveau());

        // Test met meldingen van enkele niveau's
        antwoordBericht =
            new AbstractAntwoordBericht(new BerichtResultaat(bouwLijstVanMeldingen(SoortMelding.INFO,
                    SoortMelding.WAARSCHUWING)))
            {
            };
        //Assert.assertEquals("W", antwoordBericht.getHoogsteMeldingNiveau());
    }

    @Test
    public void testBepaalVerwerkingsResultaat() {
        BerichtResultaat berichtResultaat;
        AbstractAntwoordBericht antwoordBericht;

        berichtResultaat = new BerichtResultaat(null);
        antwoordBericht = new AbstractAntwoordBericht(berichtResultaat) {
        };
        Assert.assertEquals(VerwerkingsResultaat.GOED, antwoordBericht.getVerwerkingsResultaat());

        berichtResultaat = new BerichtResultaat(null);
        berichtResultaat.markeerVerwerkingAlsFoutief();
        antwoordBericht = new AbstractAntwoordBericht(berichtResultaat) {
        };
        Assert.assertEquals(VerwerkingsResultaat.FOUT, antwoordBericht.getVerwerkingsResultaat());
    }

    /**
     * Bouwt een lijst van meldingen op basis van de opgegeven soorten. Voor elke opgegeven soort wordt er een melding
     * toegevoegd aan de lijst die daarna geretourneerd wordt.
     *
     * @param soorten de soorten van de meldingen die in de lijst moeten worden toegevoegd.
     * @return een lijst van meldingen met de opgegeven soorten.
     */
    private List<Melding> bouwLijstVanMeldingen(final SoortMelding... soorten) {
        List<Melding> meldingen = new ArrayList<Melding>();
        int i = 0;
        for (SoortMelding soort : soorten) {
            meldingen.add(new Melding(soort, MeldingCode.values()[i % MeldingCode.values().length]));
        }
        return meldingen;
    }
}
