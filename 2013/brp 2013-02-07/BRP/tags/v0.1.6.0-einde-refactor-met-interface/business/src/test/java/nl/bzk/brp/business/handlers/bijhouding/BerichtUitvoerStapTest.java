/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import nl.bzk.brp.business.actie.AbstractActieUitvoerder;
import nl.bzk.brp.business.actie.ActieFactory;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractStapTest;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.gedeeld.SoortActie;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
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
    private AbstractActieUitvoerder actieUitvoerder;

    @Before
    public void init() {
        berichtUitvoerStap = new BerichtUitvoerStap();
        ReflectionTestUtils.setField(berichtUitvoerStap, "actieFactory", actieFactory);
    }

    @Test
    public void testBerichtZonderActies() {
        AbstractBijhoudingsBericht bericht = maakNieuwBericht();
        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBerichtMetNullAlsActies() {
        AbstractBijhoudingsBericht bericht = maakNieuwBericht();
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

        AbstractBijhoudingsBericht bericht = maakNieuwBericht(actie);

        Mockito.when(actieFactory.getActieUitvoerder(actie)).thenReturn(actieUitvoerder);
        Mockito.when(actieUitvoerder.voerUit(actie, bouwBerichtContext())).thenReturn(null);

        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
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
        AbstractBijhoudingsBericht bericht = maakNieuwBericht(actie1, actie2);

        BerichtContext berichtContext = bouwBerichtContext();
        Mockito.when(actieFactory.getActieUitvoerder(actie1)).thenReturn(actieUitvoerder);
        Mockito.when(actieFactory.getActieUitvoerder(actie2)).thenReturn(actieUitvoerder);
        Mockito.when(actieUitvoerder.voerUit(actie1, berichtContext)).thenReturn(
            Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.ALG0001)));
        Mockito.when(actieUitvoerder.voerUit(actie2, berichtContext)).thenReturn(
            Arrays.asList(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001), new Melding(
                SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001)));

        BerichtResultaat resultaat = new BerichtResultaat(new ArrayList<Melding>());
        boolean stapResultaat =
            berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, berichtContext, resultaat);

        Assert.assertTrue(stapResultaat);
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
        AbstractBijhoudingsBericht bericht = maakNieuwBericht(actie);

        Mockito.when(actieFactory.getActieUitvoerder(actie)).thenReturn(null);

        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            berichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
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
        AbstractBijhoudingsBericht bericht = maakNieuwBericht(actie);

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
    private AbstractBijhoudingsBericht maakNieuwBericht(final BRPActie... acties) {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() { };
        bericht.setBrpActies(Arrays.asList(acties));
        return bericht;
    }

}
