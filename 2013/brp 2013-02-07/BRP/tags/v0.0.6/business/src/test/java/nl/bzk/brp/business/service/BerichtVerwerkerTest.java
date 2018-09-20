/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.binding.BRPBericht;
import nl.bzk.brp.binding.BerichtResultaat;
import nl.bzk.brp.binding.Melding;
import nl.bzk.brp.binding.ResultaatCode;
import nl.bzk.brp.binding.SoortMelding;
import nl.bzk.brp.binding.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.logisch.BRPActie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;


/** Unit test voor de bericht verwerker. */
@RunWith(MockitoJUnitRunner.class)
public class BerichtVerwerkerTest {

    private BerichtVerwerker berichtVerwerker;

    @Mock
    private AbstractBerichtVerwerkingsStap stap1;

    @Mock
    private AbstractBerichtVerwerkingsStap stap2;

    @Mock
    private AbstractBerichtVerwerkingsStap stap3;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        berichtVerwerker = new BerichtVerwerkerImpl();
        List<AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat>> stappen =
            new ArrayList<AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat>>();

        Mockito.when(stap1
            .voerVerwerkingsStapUitVoorBericht(Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
                Mockito.any(BerichtResultaat.class))).thenReturn(true);
        stappen.add(stap1);

        Mockito.when(
            stap2.voerVerwerkingsStapUitVoorBericht(
                Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
                Mockito.any(BerichtResultaat.class))).thenReturn(true);
        stappen.add(stap2);

        Mockito.when(
            stap3.voerVerwerkingsStapUitVoorBericht(
                Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
                Mockito.any(BerichtResultaat.class))).thenReturn(true);
        stappen.add(stap3);

        ((BerichtVerwerkerImpl) berichtVerwerker).setStappen(stappen);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testBerichtVerwerkingSuccesvolleStappen() {
        BijhoudingsBericht bericht = maakNieuwBericht();
        BerichtResultaat resultaat = new BerichtResultaat(new ArrayList<Melding>());
        berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertEquals(ResultaatCode.GOED, resultaat.getResultaat());
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());

        InOrder verwerkingsVolgorde = Mockito.inOrder(stap1, stap2, stap3);

        verwerkingsVolgorde.verify(stap1).voerVerwerkingsStapUitVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class));

        verwerkingsVolgorde.verify(stap2).voerVerwerkingsStapUitVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class));

        verwerkingsVolgorde.verify(stap3).voerVerwerkingsStapUitVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class));

        //Test naverwerking in de juiste volgorde!
        InOrder naverwerkingVolgorde = Mockito.inOrder(stap1, stap2, stap3);

        naverwerkingVolgorde.verify(stap3).naVerwerkingsStapVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class)
        );

        naverwerkingVolgorde.verify(stap2).naVerwerkingsStapVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class)
        );

        naverwerkingVolgorde.verify(stap1).naVerwerkingsStapVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class)
        );
    }

    @Test
    public void testBerichtMetNullAlsActies() {
        BijhoudingsBericht bericht = maakNieuwBericht();
        bericht.setBrpActies(null);
        BerichtResultaat resultaat = new BerichtResultaat(new ArrayList<Melding>());
        berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertEquals(ResultaatCode.GOED, resultaat.getResultaat());
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testBerichtMetFalendeTweedeStap() {
        BijhoudingsBericht bericht = maakNieuwBericht();
        BerichtResultaat resultaat = new BerichtResultaat(new ArrayList<Melding>());
        //Stap 2 faalt:
        Mockito.when(
            stap2.voerVerwerkingsStapUitVoorBericht(
                Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
                Mockito.any(BerichtResultaat.class))).thenReturn(false);

        berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext(), resultaat);

        Mockito.verify(stap1).voerVerwerkingsStapUitVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class));

        Mockito.verify(stap2).voerVerwerkingsStapUitVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class));

        //Stap 3 nooit uitgevoerd.
        Mockito.verify(stap3, Mockito.never()).voerVerwerkingsStapUitVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class));

        //Test naverwerking in de juiste volgorde!
        InOrder naverwerkingVolgorde = Mockito.inOrder(stap1, stap2, stap3);

        //Naverwerking stap3 nooit uitgevoerd.
        naverwerkingVolgorde.verify(stap3, Mockito.never()).naVerwerkingsStapVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class)
        );

        naverwerkingVolgorde.verify(stap2).naVerwerkingsStapVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class)
        );

        naverwerkingVolgorde.verify(stap1).naVerwerkingsStapVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testBerichtMetExceptieInStap2() {
        BijhoudingsBericht bericht = maakNieuwBericht();
        Mockito.doThrow(new IllegalStateException("Test")).when(stap2)
               .voerVerwerkingsStapUitVoorBericht(Mockito.any(BijhoudingsBericht.class),
                   Mockito.any(BerichtContext.class),
                   Mockito.any(BerichtResultaat.class));
        BerichtResultaat resultaat = new BerichtResultaat(new ArrayList<Melding>());
        berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext(), resultaat);

        Mockito.verify(stap1).voerVerwerkingsStapUitVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class));

        Mockito.verify(stap2).voerVerwerkingsStapUitVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class));

        Mockito.verify(stap3, Mockito.never()).voerVerwerkingsStapUitVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class));

        Assert.assertFalse(resultaat.getMeldingen().isEmpty());

        //Test naverwerking in de juiste volgorde!
        InOrder naverwerkingVolgorde = Mockito.inOrder(stap1, stap2, stap3);

        //Naverwerking stap3 nooit uitgevoerd.
        naverwerkingVolgorde.verify(stap3, Mockito.never()).naVerwerkingsStapVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class)
        );

        naverwerkingVolgorde.verify(stap2).naVerwerkingsStapVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class)
        );

        naverwerkingVolgorde.verify(stap1).naVerwerkingsStapVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNaverwerkingsStapGooitExceptie() {
        BijhoudingsBericht bericht = maakNieuwBericht();
        Mockito.doThrow(new IllegalStateException("Test")).when(stap2)
               .naVerwerkingsStapVoorBericht(Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
                   Mockito.any(BerichtResultaat.class));
        BerichtResultaat resultaat = new BerichtResultaat(new ArrayList<Melding>());
        berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext(), resultaat);

        //Stappen worden gewoon uitgevoerd
        Mockito.verify(stap1).voerVerwerkingsStapUitVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class));

        Mockito.verify(stap2).voerVerwerkingsStapUitVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class));

        Mockito.verify(stap3).voerVerwerkingsStapUitVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class));

        //Test naverwerking in de juiste volgorde!
        InOrder naverwerkingVolgorde = Mockito.inOrder(stap1, stap2, stap3);

        naverwerkingVolgorde.verify(stap3).naVerwerkingsStapVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class)
        );

        naverwerkingVolgorde.verify(stap2).naVerwerkingsStapVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class)
        );

        //Naverwerking stap1 wordt WEL uitgevoerd.
        naverwerkingVolgorde.verify(stap1).naVerwerkingsStapVoorBericht(
            Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
            Mockito.any(BerichtResultaat.class)
        );

        Assert.assertFalse(resultaat.getMeldingen().isEmpty());
        Melding melding = resultaat.getMeldingen().iterator().next();
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, melding.getSoort());
    }

    @Test
    public void testStappenIsNull() {
        Assert.assertNotNull(((BerichtVerwerkerImpl) berichtVerwerker).getStappen());
        ((BerichtVerwerkerImpl) berichtVerwerker).setStappen(null);
        Assert.assertNull(((BerichtVerwerkerImpl) berichtVerwerker).getStappen());
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

    /**
     * Bouwt en retourneert een standaard {@link nl.bzk.brp.business.dto.BerichtContext} instantie, met ingevulde in-
     * en
     * uitgaande bericht ids, een authenticatiemiddel id en een partij.
     *
     * @return een geldig bericht context.
     */
    private BerichtContext bouwBerichtContext() {
        BerichtenIds ids = new BerichtenIds(2L, 3L);
        Partij partij = new Partij();
        partij.setId(5);
        return new BerichtContext(ids, 4, partij);
    }
}
