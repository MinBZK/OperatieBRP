/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.stappen;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.kern.GedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Assert;
import org.junit.Test;


public class BerichtVerwerkingsResultaatImplTest {

    /**
     * Standaard dient de lijst met meldingen nooit <code>null</code> te zijn en de resultaat code dient initieel op
     * "GOED" te staan.
     */
    @Test
    public void testConstructor() {
        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(null);
        Assert.assertFalse(resultaat.bevatStoppendeFouten());
        Assert.assertNotNull(resultaat.getMeldingen());
    }

    @Test
    public void testPrivateDefaultConstructor() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException
    {
        final Constructor<BerichtVerwerkingsResultaatImpl> c =
                BerichtVerwerkingsResultaatImpl.class.getDeclaredConstructor();
        c.setAccessible(true);
        final BerichtVerwerkingsResultaatImpl resultaat = c.newInstance();

        Assert.assertFalse(resultaat.bevatStoppendeFouten());
        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(0, resultaat.getMeldingen().size());
    }

    @Test
    public void testConstructorMetMeldingen() {
        final List<Melding> meldingen = new ArrayList<>();
        meldingen.add(new Melding(SoortMelding.INFORMATIE, Regel.ALG0001));
        meldingen.add(new Melding(SoortMelding.WAARSCHUWING, Regel.BRAL0001));

        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(meldingen);
        Assert.assertEquals(2, resultaat.getMeldingen().size());

        // Test dat meldingen lijst niet 'by reference' wordt overgenomen, maar dat de meldingen uit de lijst worden
        // overgenomen.
        meldingen.add(new Melding(SoortMelding.FOUT, Regel.AUTH0001));
        Assert.assertEquals(3, meldingen.size());
        Assert.assertEquals(2, resultaat.getMeldingen().size());
    }

    @Test
    public void testToevoegenEnkeleMelding() {
        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(null);
        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.BRAL0001));

        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(Regel.BRAL0001, resultaat.getMeldingen().get(0).getRegel());
    }

    @Test
    public void testToevoegenMeerdereMeldingen() {
        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(null);
        resultaat.voegMeldingenToe(Arrays.asList(new Melding(SoortMelding.FOUT, Regel.BRAL0001), new Melding(
                SoortMelding.WAARSCHUWING, Regel.BRBY0502)));

        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(2, resultaat.getMeldingen().size());

        resultaat.voegMeldingenToe(Arrays.asList(new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.BRAL0102), new Melding(
                SoortMelding.INFORMATIE, Regel.REF0001)));
        Assert.assertEquals(4, resultaat.getMeldingen().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testFoutIndienMeldingenLijstWordtAangepastVanBuiten() {
        final List<Melding> meldingen = new ArrayList<>();
        meldingen.add(new Melding(SoortMelding.INFORMATIE, Regel.ALG0001));
        meldingen.add(new Melding(SoortMelding.WAARSCHUWING, Regel.BRAL0001));

        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(meldingen);
        resultaat.getMeldingen().add(new Melding(SoortMelding.INFORMATIE, Regel.BRBY0502));
    }

    @Test
    public void testBevatVerwerkingStoppendeFouten() {
        BerichtVerwerkingsResultaat resultaat;
        List<Melding> meldingen;

        meldingen = null;
        resultaat = new BerichtVerwerkingsResultaatImpl(meldingen);
        Assert.assertFalse(resultaat.bevatVerwerkingStoppendeFouten());

        meldingen = new ArrayList<>();
        resultaat = new BerichtVerwerkingsResultaatImpl(meldingen);
        Assert.assertFalse(resultaat.bevatVerwerkingStoppendeFouten());

        meldingen =
                Arrays.asList(new Melding(SoortMelding.INFORMATIE, Regel.ALG0001),
                        new Melding(SoortMelding.WAARSCHUWING,
                                Regel.ALG0001));
        resultaat = new BerichtVerwerkingsResultaatImpl(meldingen);
        Assert.assertFalse(resultaat.bevatVerwerkingStoppendeFouten());

        meldingen =
                Arrays.asList(new Melding(SoortMelding.INFORMATIE, Regel.ALG0001), new Melding(SoortMelding.FOUT,
                        Regel.ALG0001));
        resultaat = new BerichtVerwerkingsResultaatImpl(meldingen);
        Assert.assertTrue(resultaat.bevatVerwerkingStoppendeFouten());

        meldingen =
                Arrays.asList(new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.ALG0001), new Melding(
                        SoortMelding.WAARSCHUWING, Regel.ALG0001));
        resultaat = new BerichtVerwerkingsResultaatImpl(meldingen);
        Assert.assertTrue(resultaat.bevatVerwerkingStoppendeFouten());

    }

    @Test
    public void testSorteringGetMeldingen() {
        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(null);
        resultaat.voegMeldingToe(new Melding(SoortMelding.INFORMATIE, Regel.ALG0001, "Test1"));
        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.BRAL0001, "Test2"));
        resultaat.voegMeldingToe(new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.ALG0001, "Test4"));
        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001, "Test5"));
        resultaat.voegMeldingToe(new Melding(SoortMelding.WAARSCHUWING, Regel.BRAL1022, "Test6"));
        resultaat.voegMeldingToe(new Melding(SoortMelding.WAARSCHUWING, Regel.BRAL1021, "Test7"));

        final List<Melding> meldingen = resultaat.getMeldingen();

        Assert.assertEquals("Test5", meldingen.get(0).getMeldingTekst());
        Assert.assertEquals("Test2", meldingen.get(1).getMeldingTekst());
        Assert.assertEquals("Test4", meldingen.get(2).getMeldingTekst());
        Assert.assertEquals("Test7", meldingen.get(3).getMeldingTekst());
        Assert.assertEquals("Test6", meldingen.get(4).getMeldingTekst());
        Assert.assertEquals("Test1", meldingen.get(5).getMeldingTekst());
    }

    @Test
    public void testOverruleAlleOverrulebareFouten() {
        // TODO refactor test: overrule lijst zou in principe ook kunnen bestaan uit andere objecten dan resultaat
        final Melding test1 = new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.ALG0001, "Test1");
        final Melding test2 = new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.ALG0002, "Test2");

        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(null);
        resultaat.voegMeldingToe(test1);
        resultaat.voegMeldingToe(test2);

        final List<Melding> overrule = new ArrayList<>();
        overrule.add(test1);

        resultaat.overruleAlleOverrulebareFouten(overrule);

        final Melding melding0 = resultaat.getMeldingen().get(0);
        Assert.assertEquals("Test2", melding0.getMeldingTekst());
        Assert.assertEquals(SoortMelding.DEBLOKKEERBAAR, melding0.getSoort());

        final Melding melding1 = resultaat.getMeldingen().get(1);
        Assert.assertEquals("Test1", melding1.getMeldingTekst());
        Assert.assertEquals(SoortMelding.WAARSCHUWING, melding1.getSoort());
    }

    @Test
    public void testOverruleAlleOverrulebareFoutenMetNietDeblokkerbareMelding() {
        // TODO refactor test: overrule lijst zou in principe ook kunnen bestaan uit andere objecten dan resultaat
        final Melding test1 = new Melding(SoortMelding.FOUT, Regel.ALG0001, "Test1");

        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(null);
        resultaat.voegMeldingToe(test1);

        final List<Melding> overrule = new ArrayList<>();
        overrule.add(test1);

        resultaat.overruleAlleOverrulebareFouten(overrule);

        final Melding melding0 = resultaat.getMeldingen().get(0);
        Assert.assertEquals("Test1", melding0.getMeldingTekst());
        Assert.assertEquals(SoortMelding.FOUT, melding0.getSoort());
    }

    @Test
    public void testOverruleAlleOverrulebareFoutenMetMeldingNietToegevoegdAanResultaat() {
        // TODO refactor test: overrule lijst zou in principe ook kunnen bestaan uit andere objecten dan resultaat
        final Melding test1 = new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.ALG0001, "test1");

        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(null);
        // melding test1 wordt niet toegevoegd aan het BerichtVerwerkingsResultaatImpl

        final List<Melding> overrule = new ArrayList<>();
        overrule.add(test1);

        resultaat.overruleAlleOverrulebareFouten(overrule);

        Assert.assertFalse(resultaat.bevatStoppendeFouten());
        Assert.assertNotNull(resultaat.getMeldingen());
    }

    @Test
    public void testSetAdministratieveHandeling() {
        final AdministratieveHandelingModel administratieveHandeling =
                new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                        SoortAdministratieveHandeling.ADOPTIE_INGEZETENE), null, null, null);
        final BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaatImpl(null);
        resultaat.setAdministratieveHandeling(administratieveHandeling);

        Assert.assertEquals(administratieveHandeling, resultaat.getAdministratieveHandeling());
    }

    @Test
    public void testVoegtoeOverruleMeldingen() {
        final BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaatImpl(null);

        final AdministratieveHandelingGedeblokkeerdeMeldingBericht bob1 =
                new AdministratieveHandelingGedeblokkeerdeMeldingBericht();
        bob1.setGedeblokkeerdeMelding(new GedeblokkeerdeMeldingBericht());
        bob1.getGedeblokkeerdeMelding().setRegel(new RegelAttribuut(Regel.ACT0001));

        final AdministratieveHandelingGedeblokkeerdeMeldingBericht bob2 =
                new AdministratieveHandelingGedeblokkeerdeMeldingBericht();
        bob2.setGedeblokkeerdeMelding(new GedeblokkeerdeMeldingBericht());
        bob2.getGedeblokkeerdeMelding().setRegel(new RegelAttribuut(Regel.ACT0002));

        resultaat.voegtoeOverruleMeldingen(Arrays.asList(bob1, bob2));

        Assert.assertEquals(2, resultaat.getOverruleMeldingen().size());
        // verifieer dat ze gesorteerd zijn.
        Assert.assertEquals(Regel.ACT0001, resultaat.getOverruleMeldingen().get(0).getGedeblokkeerdeMelding()
                .getRegel().getWaarde());
        Assert.assertEquals(Regel.ACT0002, resultaat.getOverruleMeldingen().get(1).getGedeblokkeerdeMelding()
                .getRegel().getWaarde());

        final AdministratieveHandelingGedeblokkeerdeMeldingBericht bob3 =
                new AdministratieveHandelingGedeblokkeerdeMeldingBericht();
        bob3.setGedeblokkeerdeMelding(new GedeblokkeerdeMeldingBericht());
        bob3.getGedeblokkeerdeMelding().setRegel(new RegelAttribuut(Regel.BRAL0001));

        resultaat.voegtoeOverruleMeldingen(Arrays.asList(bob3));
        Assert.assertEquals(3, resultaat.getOverruleMeldingen().size());
        Assert.assertEquals(Regel.BRAL0001, resultaat.getOverruleMeldingen().get(2).getGedeblokkeerdeMelding()
                .getRegel().getWaarde());
    }

}
