/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.ber.GedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Unit test voor de {@link nl.bzk.brp.business.stappen.BerichtVerwerkingsResultaat} class.
 */
public class BerichtResultaatTest {

    /**
     * Standaard dient de lijst met meldingen nooit <code>null</code> te zijn en de resultaat code dient initieel op
     * "GOED" te staan.
     */
    @Test
    public void testConstructor() {
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        assertTrue(resultaat.getVerwerkingsResultaat());
        assertNotNull(resultaat.getMeldingen());
    }

    @Test
    public void testConstructorMetMeldingen() {
        List<Melding> meldingen = new ArrayList<Melding>();
        meldingen.add(new Melding(SoortMelding.INFORMATIE, MeldingCode.ALG0001));
        meldingen.add(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.BRAL0001));

        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(meldingen);
        assertEquals(2, resultaat.getMeldingen().size());

        // Test dat meldingen lijst niet 'by reference' wordt overgenomen, maar dat de meldingen uit de lijst worden
        // overgenomen.
        meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.AUTH0001));
        assertEquals(3, meldingen.size());
        assertEquals(2, resultaat.getMeldingen().size());
    }

    @Test
    public void testToevoegenEnkeleMelding() {
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, MeldingCode.BRAL0001));

        assertNotNull(resultaat.getMeldingen());
        assertEquals(1, resultaat.getMeldingen().size());
        assertEquals(MeldingCode.BRAL0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testToevoegenMeerdereMeldingen() {
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        resultaat.voegMeldingenToe(Arrays.asList(new Melding(SoortMelding.FOUT, MeldingCode.BRAL0001),
                                                 new Melding(SoortMelding.WAARSCHUWING, MeldingCode.MR0502)));

        assertNotNull(resultaat.getMeldingen());
        assertEquals(2, resultaat.getMeldingen().size());

        resultaat.voegMeldingenToe(Arrays.asList(new Melding(SoortMelding.DEBLOKKEERBAAR, MeldingCode.BRAL0102),
                                                 new Melding(SoortMelding.INFORMATIE, MeldingCode.REF0001)));
        assertEquals(4, resultaat.getMeldingen().size());
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
        assertFalse(resultaat.bevatVerwerkingStoppendeFouten());

        meldingen = new ArrayList<Melding>();
        resultaat = new BerichtVerwerkingsResultaat(meldingen);
        assertFalse(resultaat.bevatVerwerkingStoppendeFouten());

        meldingen = Arrays.asList(new Melding(SoortMelding.INFORMATIE, MeldingCode.ALG0001),
                                  new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001));
        resultaat = new BerichtVerwerkingsResultaat(meldingen);
        assertFalse(resultaat.bevatVerwerkingStoppendeFouten());

        meldingen = Arrays.asList(new Melding(SoortMelding.INFORMATIE, MeldingCode.ALG0001),
                                  new Melding(SoortMelding.FOUT, MeldingCode.ALG0001));
        resultaat = new BerichtVerwerkingsResultaat(meldingen);
        assertTrue(resultaat.bevatVerwerkingStoppendeFouten());

        meldingen = Arrays.asList(new Melding(SoortMelding.DEBLOKKEERBAAR, MeldingCode.ALG0001),
                                  new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001));
        resultaat = new BerichtVerwerkingsResultaat(meldingen);
        assertTrue(resultaat.bevatVerwerkingStoppendeFouten());
    }

    @Test
    public void testSorteringGetMeldingen() {
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        resultaat.voegMeldingToe(new Melding(SoortMelding.INFORMATIE, MeldingCode.ALG0001, "Test1"));
        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, MeldingCode.BRAL0001, "Test2"));
        resultaat.voegMeldingToe(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.BRAL2102, "Test3"));
        resultaat.voegMeldingToe(new Melding(SoortMelding.DEBLOKKEERBAAR, MeldingCode.ALG0001, "Test4"));
        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, MeldingCode.ALG0001, "Test5"));
        resultaat.voegMeldingToe(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.BRAL1022, "Test6"));
        resultaat.voegMeldingToe(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.BRAL1021, "Test7"));

        List<Melding> meldingen = resultaat.getMeldingen();

        assertEquals("Test5", meldingen.get(0).getOmschrijving());
        assertEquals("Test2", meldingen.get(1).getOmschrijving());
        assertEquals("Test4", meldingen.get(2).getOmschrijving());
        assertEquals("Test7", meldingen.get(3).getOmschrijving());
        assertEquals("Test6", meldingen.get(4).getOmschrijving());
        assertEquals("Test3", meldingen.get(5).getOmschrijving());
        assertEquals("Test1", meldingen.get(6).getOmschrijving());
    }

    @Test
    public void testMarkeerAlsFoutief() {
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);

        assertTrue(resultaat.getVerwerkingsResultaat());

        resultaat.markeerVerwerkingAlsFoutief();

        assertFalse(resultaat.getVerwerkingsResultaat());
    }

    @Test
    public void testOverruleAlleOverrulebareFouten() {
        //TODO refactor test: overrule lijst zou in principe ook kunnen bestaan uit andere objecten dan resultaat
        Melding test1 = new Melding(SoortMelding.DEBLOKKEERBAAR, MeldingCode.ALG0001, "Test1");
        Melding test2 = new Melding(SoortMelding.DEBLOKKEERBAAR, MeldingCode.ALG0002, "Test2");

        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        resultaat.voegMeldingToe(test1);
        resultaat.voegMeldingToe(test2);

        List<Melding> overrule = new ArrayList<Melding>();
        overrule.add(test1);

        resultaat.overruleAlleOverrulebareFouten(overrule);

        Melding melding0 = resultaat.getMeldingen().get(0);
        assertEquals("Test2", melding0.getOmschrijving());
        assertEquals(SoortMelding.DEBLOKKEERBAAR, melding0.getSoort());

        Melding melding1 = resultaat.getMeldingen().get(1);
        assertEquals("Test1", melding1.getOmschrijving());
        assertEquals(SoortMelding.WAARSCHUWING, melding1.getSoort());
    }

    @Test
    public void testVoegtoeOverruleMeldingen() {
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);

        AdministratieveHandelingGedeblokkeerdeMeldingBericht bob1 =
                new AdministratieveHandelingGedeblokkeerdeMeldingBericht();
        bob1.setGedeblokkeerdeMelding(new GedeblokkeerdeMeldingBericht());
        bob1.getGedeblokkeerdeMelding().setRegel(Regel.ACT0001);

        AdministratieveHandelingGedeblokkeerdeMeldingBericht bob2 =
                new AdministratieveHandelingGedeblokkeerdeMeldingBericht();
        bob2.setGedeblokkeerdeMelding(new GedeblokkeerdeMeldingBericht());
        bob2.getGedeblokkeerdeMelding().setRegel(Regel.ACT0002);

        resultaat.voegtoeOverruleMeldingen(Arrays.asList(bob1, bob2));

        assertEquals(2, resultaat.getOverruleMeldingen().size());
        // verifieer dat ze gesorteerd zijn.
        assertEquals(MeldingCode.ACT0001.name(),
                     resultaat.getOverruleMeldingen().get(0).getGedeblokkeerdeMelding().getRegel().getCode());
        assertEquals(MeldingCode.ACT0002.name(),
                     resultaat.getOverruleMeldingen().get(1).getGedeblokkeerdeMelding().getRegel().getCode());

        AdministratieveHandelingGedeblokkeerdeMeldingBericht bob3 =
                new AdministratieveHandelingGedeblokkeerdeMeldingBericht();
        bob3.setGedeblokkeerdeMelding(new GedeblokkeerdeMeldingBericht());
        bob3.getGedeblokkeerdeMelding().setRegel(Regel.BRAL0001);

        resultaat.voegtoeOverruleMeldingen(Arrays.asList(bob3));
        assertEquals(3, resultaat.getOverruleMeldingen().size());
        assertEquals(MeldingCode.BRAL0001.name(),
                     resultaat.getOverruleMeldingen().get(2).getGedeblokkeerdeMelding().getRegel().getCode());
    }

    @Test
    public void zouIsFoutiefTrueEnIsSUccesvolIsFalseMoetenGevenAlsVerwerkingsFoutGezetIs() {
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        resultaat.markeerVerwerkingAlsFoutief();
        assertFalse(resultaat.isSuccesvol());
        assertTrue(resultaat.isFoutief());
    }
}
