/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.business.actie.ActieFactory;
import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.business.dto.ResultaatCode;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.SoortActie;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/** Unit test voor de bericht verwerker. */
public class BerichtVerwerkerTest {

    private BerichtVerwerker berichtVerwerker;

    @Mock
    private AbstractBerichtVerwerkingsStap stap1;

    @Mock
    private AbstractBerichtVerwerkingsStap stap2;

    @Mock
    private AbstractBerichtVerwerkingsStap stap3;

    @Mock
    private BerichtResultaatFactory berichtResultaatFactory;

    @Test
    @SuppressWarnings("unchecked")
    public void testBerichtVerwerkingSuccesvolleStappen() {
        BRPActie actie = new BRPActie();
        actie.setSoort(SoortActie.VERHUIZING);
        List<RootObject> personen = new ArrayList<RootObject>();
        personen.add(new Persoon());

        actie.setRootObjecten(personen);
        BijhoudingsBericht bericht = maakNieuwBericht(actie);
        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext());

        Assert.assertEquals(ResultaatCode.GOED, resultaat.getResultaatCode());
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
    public void testBijhoudingsBerichtMetNullAlsActies() {
        BijhoudingsBericht bericht = maakNieuwBericht();
        bericht.setBrpActies(null);
        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertFalse(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBijhoudingsBerichtMetMetLegeActies() {
        BijhoudingsBericht bericht = maakNieuwBericht();
        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertFalse(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBijhoudingsBerichtMetNullAlsRootObjectInActie() {
        BRPActie actie = new BRPActie();
        actie.setSoort(SoortActie.ERKENNING_GEBOORTE);

        BijhoudingsBericht bericht = maakNieuwBericht(actie);
        bericht.getBrpActies().get(0).setRootObjecten(null);
        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertFalse(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBijhoudingsBerichtMetMetRootObjectsInActie() {
        BRPActie actie = new BRPActie();
        actie.setSoort(SoortActie.ERKENNING_GEBOORTE);

        BijhoudingsBericht bericht = maakNieuwBericht(actie);
        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());
        Assert.assertFalse(resultaat.getMeldingen().isEmpty());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testBerichtMetFalendeTweedeStap() {
        BijhoudingsBericht bericht = maakNieuwBericht(new BRPActie());
        bericht.getBrpActies().get(0).voegRootObjectToe(new Persoon());

        //Stap 2 faalt:
        Mockito.when(
            stap2.voerVerwerkingsStapUitVoorBericht(
                Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
                Mockito.any(BerichtResultaat.class))).thenReturn(false);

        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext());

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
        BijhoudingsBericht bericht = maakNieuwBericht(new BRPActie());
        bericht.getBrpActies().get(0).voegRootObjectToe(new Persoon());

        Mockito.doThrow(new IllegalStateException("Test")).when(stap2)
               .voerVerwerkingsStapUitVoorBericht(Mockito.any(BijhoudingsBericht.class),
                   Mockito.any(BerichtContext.class),
                   Mockito.any(BerichtResultaat.class));
        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext());

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
        BijhoudingsBericht bericht = maakNieuwBericht(new BRPActie());
        bericht.getBrpActies().get(0).voegRootObjectToe(new Persoon());

        Mockito.doThrow(new IllegalStateException("Test")).when(stap2)
               .naVerwerkingsStapVoorBericht(Mockito.any(BijhoudingsBericht.class), Mockito.any(BerichtContext.class),
                   Mockito.any(BerichtResultaat.class));
        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext());

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

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        berichtVerwerker = new BerichtVerwerkerImpl();

        // Conditioneer de berichtverwerker
        ReflectionTestUtils.setField(berichtVerwerker, "berichtResultaatFactory", berichtResultaatFactory);
        ((BerichtVerwerkerImpl) berichtVerwerker).setStappen(bouwStappenLijst());

        Mockito.when(berichtResultaatFactory.creeerBerichtResultaat(Mockito.any(BRPBericht.class)))
               .thenReturn(new BerichtResultaat(null));
    }

    /**
     * Bouwt een lijst van stappen op, waarbij de stappen alle Mocks zijn.
     *
     * @return een lijst van stappen.
     */
    private List<AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat>> bouwStappenLijst() {
        List<AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat>> stappen =
            new ArrayList<AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat>>();

        Mockito.when(stap1.voerVerwerkingsStapUitVoorBericht(Mockito.any(BijhoudingsBericht.class),
            Mockito.any(BerichtContext.class), Mockito.any(BerichtResultaat.class))).thenReturn(true);
        stappen.add(stap1);

        Mockito.when(stap2.voerVerwerkingsStapUitVoorBericht(Mockito.any(BijhoudingsBericht.class),
            Mockito.any(BerichtContext.class), Mockito.any(BerichtResultaat.class))).thenReturn(true);
        stappen.add(stap2);

        Mockito.when(stap3.voerVerwerkingsStapUitVoorBericht(Mockito.any(BijhoudingsBericht.class),
            Mockito.any(BerichtContext.class), Mockito.any(BerichtResultaat.class))).thenReturn(true);
        stappen.add(stap3);
        return stappen;
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
