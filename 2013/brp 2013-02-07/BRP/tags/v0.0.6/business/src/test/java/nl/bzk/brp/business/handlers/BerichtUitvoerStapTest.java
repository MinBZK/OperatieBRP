/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import nl.bzk.brp.binding.BerichtResultaat;
import nl.bzk.brp.binding.Melding;
import nl.bzk.brp.binding.MeldingCode;
import nl.bzk.brp.binding.SoortMelding;
import nl.bzk.brp.binding.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.business.actie.ActieFactory;
import nl.bzk.brp.business.actie.ActieUitvoerder;
import nl.bzk.brp.business.handlers.bijhouding.BerichtUitvoerStap;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.gedeeld.SoortActie;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class BerichtUitvoerStapTest extends AbstractStapTest {

    private static final String VALIDE_BSN = "123456782";

    private BerichtUitvoerStap berichtUitvoerStap;

    @Mock
    private ActieFactory actieFactory;

    @Mock
    private ActieUitvoerder actieUitvoerder;

    @Before
    public void init() {
        berichtUitvoerStap = new BerichtUitvoerStap();
        ReflectionTestUtils.setField(berichtUitvoerStap, "actieFactory", actieFactory);
    }

    @Test
    public void testBerichtZonderActies() {
        BijhoudingsBericht bericht = maakNieuwBericht();
        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertNull(resultaat.getMeldingen());
    }

    @Test
    public void testBerichtMetNullAlsActies() {
        BijhoudingsBericht bericht = maakNieuwBericht();
        bericht.setBrpActies(null);
        BerichtResultaat resultaat = new BerichtResultaat(new ArrayList<Melding>());
        boolean stapResultaat =
            berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBerichtMetActieZonderMeldingen() {
        BRPActie actie = new BRPActie();
        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer(VALIDE_BSN);
        PersoonAdres nieuwAdres = new PersoonAdres();
        persoon.setAdressen(new HashSet<PersoonAdres>());
        persoon.getAdressen().add(nieuwAdres);
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        actie.setSoort(SoortActie.VERHUIZING);

        BijhoudingsBericht bericht = maakNieuwBericht(actie);

        Mockito.when(actieFactory.getActieUitvoerder(actie)).thenReturn(actieUitvoerder);
        Mockito.when(actieUitvoerder.voerUit(actie)).thenReturn(null);

        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertNull(resultaat.getMeldingen());
    }

    @Test
    public void testBerichtMetMeerdereActiesEnMeldingen() {
        Persoon persoon = new Persoon();
        PersoonAdres nieuwAdres = new PersoonAdres();
        persoon.setAdressen(new HashSet<PersoonAdres>());
        persoon.getAdressen().add(nieuwAdres);
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer(VALIDE_BSN);
        BRPActie actie1 = new BRPActie();
        actie1.setRootObjecten(Arrays.asList((RootObject) persoon));
        actie1.setSoort(SoortActie.VERHUIZING);
        BRPActie actie2 = new BRPActie();
        actie2.setRootObjecten(Arrays.asList((RootObject) persoon));
        actie2.setSoort(SoortActie.VERHUIZING);
        BijhoudingsBericht bericht = maakNieuwBericht(actie1, actie2);

        Mockito.when(actieFactory.getActieUitvoerder(actie1)).thenReturn(actieUitvoerder);
        Mockito.when(actieFactory.getActieUitvoerder(actie2)).thenReturn(actieUitvoerder);
        Mockito.when(actieUitvoerder.voerUit(actie1)).thenReturn(
            Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.ALG0001)));
        Mockito.when(actieUitvoerder.voerUit(actie2)).thenReturn(
            Arrays.asList(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001), new Melding(
                SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001)));

        BerichtResultaat resultaat = new BerichtResultaat(new ArrayList<Melding>());
        boolean stapResultaat =
            berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertFalse(stapResultaat);
        Assert.assertEquals(3, resultaat.getMeldingen().size());
    }

    @Test
    public void testBerichtZonderBekendeActieUitvoerder() {
        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer(VALIDE_BSN);
        BRPActie actie = new BRPActie();
        actie.setSoort(SoortActie.VERHUIZING);
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        BijhoudingsBericht bericht = maakNieuwBericht(actie);

        Mockito.when(actieFactory.getActieUitvoerder(actie)).thenReturn(null);

        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertFalse(stapResultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
    }

    @Test(expected = IllegalStateException.class)
    public void testBerichtMetExceptieInUitvoering() {
        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer(VALIDE_BSN);
        BRPActie actie = new BRPActie();
        actie.setSoort(SoortActie.VERHUIZING);
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        BijhoudingsBericht bericht = maakNieuwBericht(actie);

        Mockito.when(actieFactory.getActieUitvoerder(actie)).thenThrow(new IllegalStateException("Test"));

        BerichtResultaat resultaat = new BerichtResultaat(null);
        berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);
    }

    /**
     * Instantieert een bericht met de opgegeven acties.
     *
     * @param acties de acties waaruit het bericht dient te bestaan.
     * @return een nieuw bericht met opgegeven acties.
     */
    private BijhoudingsBericht maakNieuwBericht(final BRPActie... acties) {
        BijhoudingsBericht bericht = new BijhoudingsBericht();
        bericht.setBrpActies(Arrays.asList(acties));
        return bericht;
    }

}
