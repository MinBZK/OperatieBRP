/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.stappen.BerichtenIds;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.stappen.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAanschrijvingBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieErkenningBericht;
import nl.bzk.brp.model.bericht.kern.HandelingErkenningOngeborenVruchtBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;
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
        ActieBericht actie = new ActieRegistratieAdresBericht();
        List<RootObject> personen = new ArrayList<RootObject>();
        personen.add(new PersoonBericht());

        actie.setRootObjecten(personen);
        AbstractBijhoudingsBericht bericht = maakNieuwBericht(actie);
        BerichtContext context = bouwBerichtContext();
        BerichtVerwerkingsResultaat resultaat = berichtVerwerker.verwerkBericht(bericht, context);

        Assert.assertTrue(resultaat.getVerwerkingsResultaat());
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());

        InOrder verwerkingsVolgorde = Mockito.inOrder(stap1, stap2, stap3);

        verwerkingsVolgorde.verify(stap1).voerVerwerkingsStapUitVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class));

        verwerkingsVolgorde.verify(stap2).voerVerwerkingsStapUitVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class));

        verwerkingsVolgorde.verify(stap3).voerVerwerkingsStapUitVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class));

        //Test naverwerking in de juiste volgorde!
        InOrder naverwerkingVolgorde = Mockito.inOrder(stap1, stap2, stap3);

        naverwerkingVolgorde.verify(stap3).naVerwerkingsStapVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class)
        );

        naverwerkingVolgorde.verify(stap2).naVerwerkingsStapVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class)
        );

        naverwerkingVolgorde.verify(stap1).naVerwerkingsStapVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class)
        );
    }

    @Test
    public void testBijhoudingsBerichtMetNullAlsActies() {
        AbstractBijhoudingsBericht bericht = maakNieuwBericht();
        BerichtVerwerkingsResultaat resultaat = berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext());

        Assert.assertFalse(resultaat.getVerwerkingsResultaat());
        Assert.assertFalse(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBijhoudingsBerichtMetMetLegeActies() {
        AbstractBijhoudingsBericht bericht = maakNieuwBericht();
        BerichtVerwerkingsResultaat resultaat = berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext());

        Assert.assertFalse(resultaat.getVerwerkingsResultaat());
        Assert.assertFalse(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testAbstractBijhoudingsBerichtMetNullAlsRootObjectInActie() {
        ActieBericht actie = new ActieRegistratieErkenningBericht();

        AbstractBijhoudingsBericht bericht = maakNieuwBericht(actie);

        ActieBericht actieBericht = bericht.getAdministratieveHandeling().getActies().get(0);
        actieBericht.setRootObjecten(null);
        BerichtVerwerkingsResultaat resultaat = berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext());

        Assert.assertFalse(resultaat.getVerwerkingsResultaat());
        Assert.assertFalse(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testAbstractBijhoudingsBerichtMetRootObjectsInActie() {
        ActieBericht actie = new ActieRegistratieErkenningBericht();

        AbstractBijhoudingsBericht bericht = maakNieuwBericht(actie);
        BerichtVerwerkingsResultaat resultaat = berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext());

        Assert.assertFalse(resultaat.getVerwerkingsResultaat());
        Assert.assertFalse(resultaat.getMeldingen().isEmpty());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testBerichtMetFalendeTweedeStap() {
        AbstractBijhoudingsBericht bericht = maakNieuwBericht(new ActieRegistratieAanschrijvingBericht());
        ActieBericht actieBericht = bericht.getAdministratieveHandeling().getActies().get(0);
        actieBericht.setRootObjecten(Arrays.asList((RootObject) new PersoonBericht()));

        //Stap 2 faalt:
        Mockito.when(
            stap2.voerVerwerkingsStapUitVoorBericht(
                Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
                Matchers.any(BerichtVerwerkingsResultaat.class))).thenReturn(false);

        berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext());

        Mockito.verify(stap1).voerVerwerkingsStapUitVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class));

        Mockito.verify(stap2).voerVerwerkingsStapUitVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class));

        //Stap 3 nooit uitgevoerd.
        Mockito.verify(stap3, Mockito.never()).voerVerwerkingsStapUitVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class));

        //Test naverwerking in de juiste volgorde!
        InOrder naverwerkingVolgorde = Mockito.inOrder(stap1, stap2, stap3);

        //Naverwerking stap3 nooit uitgevoerd.
        naverwerkingVolgorde.verify(stap3, Mockito.never()).naVerwerkingsStapVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class)
        );

        naverwerkingVolgorde.verify(stap2).naVerwerkingsStapVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class)
        );

        naverwerkingVolgorde.verify(stap1).naVerwerkingsStapVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testBerichtMetExceptieInStap2() {
        AbstractBijhoudingsBericht bericht = maakNieuwBericht(new ActieRegistratieAanschrijvingBericht());
        ActieBericht actieBericht = bericht.getAdministratieveHandeling().getActies().get(0);
        actieBericht.setRootObjecten(Arrays.asList((RootObject) new PersoonBericht()));

        Mockito.doThrow(new IllegalStateException("Test")).when(stap2)
               .voerVerwerkingsStapUitVoorBericht(Matchers.any(AbstractBijhoudingsBericht.class),
                   Matchers.any(BerichtContext.class),
                   Matchers.any(BerichtVerwerkingsResultaat.class));
        BerichtVerwerkingsResultaat resultaat = berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext());

        Mockito.verify(stap1).voerVerwerkingsStapUitVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class));

        Mockito.verify(stap2).voerVerwerkingsStapUitVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class));

        Mockito.verify(stap3, Mockito.never()).voerVerwerkingsStapUitVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class));

        Assert.assertFalse(resultaat.getMeldingen().isEmpty());

        //Test naverwerking in de juiste volgorde!
        InOrder naverwerkingVolgorde = Mockito.inOrder(stap1, stap2, stap3);

        //Naverwerking stap3 nooit uitgevoerd.
        naverwerkingVolgorde.verify(stap3, Mockito.never()).naVerwerkingsStapVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class)
        );

        naverwerkingVolgorde.verify(stap2).naVerwerkingsStapVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class)
        );

        naverwerkingVolgorde.verify(stap1).naVerwerkingsStapVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNaverwerkingsStapGooitExceptie() {
        AbstractBijhoudingsBericht bericht = maakNieuwBericht(new ActieRegistratieAanschrijvingBericht());
        ActieBericht actieBericht = bericht.getAdministratieveHandeling().getActies().get(0);
        actieBericht.setRootObjecten(Arrays.asList((RootObject) new PersoonBericht()));

        Mockito.doThrow(new IllegalStateException("Test")).when(stap2)
               .naVerwerkingsStapVoorBericht(Matchers.any(AbstractBijhoudingsBericht.class),
                   Matchers.any(BerichtContext.class),
                   Matchers.any(BerichtVerwerkingsResultaat.class));
        BerichtVerwerkingsResultaat resultaat = berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext());

        //Stappen worden gewoon uitgevoerd
        Mockito.verify(stap1).voerVerwerkingsStapUitVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class));

        Mockito.verify(stap2).voerVerwerkingsStapUitVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class));

        Mockito.verify(stap3).voerVerwerkingsStapUitVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class));

        //Test naverwerking in de juiste volgorde!
        InOrder naverwerkingVolgorde = Mockito.inOrder(stap1, stap2, stap3);

        naverwerkingVolgorde.verify(stap3).naVerwerkingsStapVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class)
        );

        naverwerkingVolgorde.verify(stap2).naVerwerkingsStapVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class)
        );

        //Naverwerking stap1 wordt WEL uitgevoerd.
        naverwerkingVolgorde.verify(stap1).naVerwerkingsStapVoorBericht(
            Matchers.any(AbstractBijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaat.class)
        );

        Assert.assertFalse(resultaat.getMeldingen().isEmpty());
        Melding melding = resultaat.getMeldingen().iterator().next();
        Assert.assertEquals(SoortMelding.FOUT, melding.getSoort());
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

        Mockito.when(berichtResultaatFactory.creeerBerichtResultaat(Matchers.any(BerichtBericht.class),
            Matchers.any(BerichtContext.class))).thenReturn(new BerichtVerwerkingsResultaat(null));
    }

    /**
     * Bouwt een lijst van stappen op, waarbij de stappen alle Mocks zijn.
     *
     * @return een lijst van stappen.
     */
    @SuppressWarnings("unchecked")
    private List<AbstractBerichtVerwerkingsStap<BerichtBericht, BerichtVerwerkingsResultaat>> bouwStappenLijst() {
        List<AbstractBerichtVerwerkingsStap<BerichtBericht, BerichtVerwerkingsResultaat>> stappen =
            new ArrayList<AbstractBerichtVerwerkingsStap<BerichtBericht, BerichtVerwerkingsResultaat>>();

        Mockito.when(stap1.voerVerwerkingsStapUitVoorBericht(Matchers.any(AbstractBijhoudingsBericht.class),
            Matchers.any(BerichtContext.class), Matchers.any(BerichtVerwerkingsResultaat.class))).thenReturn(true);
        stappen.add(stap1);

        Mockito.when(stap2.voerVerwerkingsStapUitVoorBericht(Matchers.any(AbstractBijhoudingsBericht.class),
            Matchers.any(BerichtContext.class), Matchers.any(BerichtVerwerkingsResultaat.class))).thenReturn(true);
        stappen.add(stap2);

        Mockito.when(stap3.voerVerwerkingsStapUitVoorBericht(Matchers.any(AbstractBijhoudingsBericht.class),
            Matchers.any(BerichtContext.class), Matchers.any(BerichtVerwerkingsResultaat.class))).thenReturn(true);
        stappen.add(stap3);
        return stappen;
    }


    /**
     * Instantieert een bericht met de opgegeven acties.
     *
     * @param acties de acties waaruit het bericht dient te bestaan.
     * @return een nieuw bericht met opgegeven acties.
     */
    private AbstractBijhoudingsBericht maakNieuwBericht(final ActieBericht... acties) {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht(null) {
        };
        if (acties == null) {
            //           bericht.setBrpActies(null);
        } else {
            bericht
                .setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
            bericht.getAdministratieveHandeling().setActies(Arrays.asList(acties));
        }
        return bericht;
    }

    /**
     * Bouwt en retourneert een standaard {@link nl.bzk.brp.business.stappen.BerichtContext} instantie, met ingevulde in-
     * en
     * uitgaande bericht ids, een authenticatiemiddel id en een partij.
     *
     * @return een geldig bericht context.
     */
    private BerichtContext bouwBerichtContext() {
        BerichtenIds ids = new BerichtenIds(2L, 3L);
        return new BerichtContext(ids, Mockito.mock(Partij.class), "ref");
    }
}
