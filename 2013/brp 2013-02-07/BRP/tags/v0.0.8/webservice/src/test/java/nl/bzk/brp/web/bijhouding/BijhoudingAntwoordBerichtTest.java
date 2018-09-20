/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bijhouding;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.junit.Assert;
import org.junit.Test;

/** Unit test klasse voor de methodes in de {@link BijhoudingAntwoordBericht} klasse. */
public class BijhoudingAntwoordBerichtTest {

    @Test
    public void testConstructor() {
        BijhoudingAntwoordBericht antwoordBericht =
            new BijhoudingAntwoordBericht(new BerichtResultaat(bouwLijstVanMeldingen(SoortMelding.INFO)));

        Assert.assertNotNull(antwoordBericht.getMeldingen());
        Assert.assertEquals(1, antwoordBericht.getMeldingen().size());

        Assert.assertNull(antwoordBericht.getBijgehoudenPersonen());
        Assert.assertNotNull(antwoordBericht.getTijdstipRegistratie());
        Assert.assertNotNull(antwoordBericht.getVerwerkingsResultaat());
    }

    @Test
    public void testBepaalHoogsteNiveau() {
        BijhoudingAntwoordBericht antwoordBericht;

        // Test met "Geen" meldingen
        antwoordBericht = new BijhoudingAntwoordBericht(new BerichtResultaat(null));
        Assert.assertEquals("G", antwoordBericht.getHoogsteMeldingNiveau());

        // Test met enkele melding van niveau info
        antwoordBericht = new BijhoudingAntwoordBericht(new BerichtResultaat(bouwLijstVanMeldingen(SoortMelding.INFO)));
        Assert.assertEquals("I", antwoordBericht.getHoogsteMeldingNiveau());

        // Test met enkele melding van niveau waarschuwing
        antwoordBericht =
            new BijhoudingAntwoordBericht(new BerichtResultaat(bouwLijstVanMeldingen(SoortMelding.WAARSCHUWING)));
        Assert.assertEquals("W", antwoordBericht.getHoogsteMeldingNiveau());

        // Test met enkele melding van niveau overrulebare fout
        antwoordBericht =
            new BijhoudingAntwoordBericht(new BerichtResultaat(bouwLijstVanMeldingen(SoortMelding.FOUT_OVERRULEBAAR)));
        Assert.assertEquals("O", antwoordBericht.getHoogsteMeldingNiveau());

        // Test met enkele melding van niveau fout/onoverrulebare fout
        antwoordBericht = new BijhoudingAntwoordBericht(
            new BerichtResultaat(bouwLijstVanMeldingen(SoortMelding.FOUT_ONOVERRULEBAAR)));
        Assert.assertEquals("F", antwoordBericht.getHoogsteMeldingNiveau());

        // Test met meerdere meldingen van zelfde niveau
        antwoordBericht = new BijhoudingAntwoordBericht(
            new BerichtResultaat(bouwLijstVanMeldingen(SoortMelding.INFO, SoortMelding.INFO, SoortMelding.INFO)));
        Assert.assertEquals("I", antwoordBericht.getHoogsteMeldingNiveau());

        // Test met meldingen van alle niveau's
        antwoordBericht = new BijhoudingAntwoordBericht(new BerichtResultaat(
            bouwLijstVanMeldingen(SoortMelding.INFO, SoortMelding.WAARSCHUWING, SoortMelding.FOUT_OVERRULEBAAR,
                SoortMelding.FOUT_ONOVERRULEBAAR)));
        Assert.assertEquals("F", antwoordBericht.getHoogsteMeldingNiveau());

        // Test met meldingen van alle niveau's, maar in andere volgorde
        antwoordBericht = new BijhoudingAntwoordBericht(new BerichtResultaat(
            bouwLijstVanMeldingen(SoortMelding.FOUT_ONOVERRULEBAAR, SoortMelding.FOUT_OVERRULEBAAR,
                SoortMelding.WAARSCHUWING, SoortMelding.INFO)));
        Assert.assertEquals("F", antwoordBericht.getHoogsteMeldingNiveau());

        // Test met meldingen van enkele niveau's
        antwoordBericht = new BijhoudingAntwoordBericht(new BerichtResultaat(
            bouwLijstVanMeldingen(SoortMelding.INFO, SoortMelding.WAARSCHUWING, SoortMelding.FOUT_OVERRULEBAAR)));
        Assert.assertEquals("O", antwoordBericht.getHoogsteMeldingNiveau());

        // Test met meldingen van enkele niveau's
        antwoordBericht = new BijhoudingAntwoordBericht(new BerichtResultaat(
            bouwLijstVanMeldingen(SoortMelding.INFO, SoortMelding.WAARSCHUWING)));
        Assert.assertEquals("W", antwoordBericht.getHoogsteMeldingNiveau());
    }

    @Test
    public void testBepaalVerwerkingsResultaat() {
        BerichtResultaat berichtResultaat;
        BijhoudingAntwoordBericht antwoordBericht;

        berichtResultaat = new BerichtResultaat(null);
        antwoordBericht = new BijhoudingAntwoordBericht(berichtResultaat);
        Assert.assertEquals(VerwerkingsResultaat.GOED, antwoordBericht.getVerwerkingsResultaat());

        berichtResultaat = new BerichtResultaat(null);
        berichtResultaat.markeerVerwerkingAlsFoutief();
        antwoordBericht = new BijhoudingAntwoordBericht(berichtResultaat);
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
