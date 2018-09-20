/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.bericht.ber.AbstractBerichtBericht;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNaamgebruikBericht;
import nl.bzk.brp.model.bericht.kern.HandelingOverlijdenInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaatImpl;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import nl.bzk.brp.webservice.business.stappen.TestBerichtContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de bericht verwerker.
 */
public class AbstractBerichtVerwerkerTest {

    private AbstractBerichtVerwerker berichtVerwerker;

    @Mock
    private AbstractBerichtVerwerkingStap stap1;

    @Mock
    private AbstractBerichtVerwerkingStap stap2;

    @Mock
    private AbstractBerichtVerwerkingStap stap3;

    @Mock
    private BerichtResultaatFactory berichtResultaatFactory;

    @Test
    @SuppressWarnings("unchecked")
    public void testVerwerkBerichtSuccesvolleStappen() {
        final ActieBericht actie = new ActieRegistratieAdresBericht();

        actie.setRootObject(new PersoonBericht());
        final BerichtBericht bericht = maakNieuwBericht(actie);
        final BerichtContext context = bouwBerichtContext();
        final BerichtVerwerkingsResultaatImpl resultaat = berichtVerwerker.verwerkBericht(bericht, context);

        Assert.assertTrue(resultaat.getMeldingen().isEmpty());

        final InOrder verwerkingsVolgorde = Mockito.inOrder(stap1, stap2, stap3);

        verwerkingsVolgorde.verify(stap1).voerStapUit(
            Matchers.any(AbstractBerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class));

        verwerkingsVolgorde.verify(stap2).voerStapUit(
            Matchers.any(AbstractBerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class));

        verwerkingsVolgorde.verify(stap3).voerStapUit(
            Matchers.any(AbstractBerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class));

        //Test naverwerking in de juiste volgorde!
        final InOrder naverwerkingVolgorde = Mockito.inOrder(stap1, stap2, stap3);

        naverwerkingVolgorde.verify(stap3).voerNabewerkingStapUit(
            Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class)
        );

        naverwerkingVolgorde.verify(stap2).voerNabewerkingStapUit(
            Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class)
        );

        naverwerkingVolgorde.verify(stap1).voerNabewerkingStapUit(
            Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testVerwerkSuccesvolleStappen() {
        final ActieBericht actie = new ActieRegistratieAdresBericht();

        actie.setRootObject(new PersoonBericht());
        final BerichtBericht bericht = maakNieuwBericht(actie);
        final BerichtContext context = bouwBerichtContext();
        final BerichtVerwerkingsResultaatImpl resultaat = berichtVerwerker.verwerk(bericht, context);

        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }


    @Test
    @SuppressWarnings("unchecked")
    public void testBerichtMetFalendeTweedeStap() {
        final BerichtBericht bericht = maakNieuwBericht(new ActieRegistratieNaamgebruikBericht());
        final ActieBericht actieBericht = bericht.getAdministratieveHandeling().getActies().get(0);
        actieBericht.setRootObject(new PersoonBericht());

        //Stap 2 faalt:
        Mockito.when(
            stap2.voerStapUit(Matchers.any(AbstractBerichtBericht.class), Matchers.any(BerichtContext.class),
                Matchers.any(BerichtVerwerkingsResultaatImpl.class))).thenReturn(false);

        berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext());

        Mockito.verify(stap1).voerStapUit(
            Matchers.any(AbstractBerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class));

        Mockito.verify(stap2).voerStapUit(
            Matchers.any(AbstractBerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class));

        //Stap 3 nooit uitgevoerd.
        Mockito.verify(stap3, Mockito.never()).voerStapUit(
            Matchers.any(AbstractBerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class));

        //Test naverwerking in de juiste volgorde!
        final InOrder naverwerkingVolgorde = Mockito.inOrder(stap1, stap2, stap3);

        //Naverwerking stap3 nooit uitgevoerd.
        naverwerkingVolgorde.verify(stap3, Mockito.never()).voerNabewerkingStapUit(
            Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class)
        );

        naverwerkingVolgorde.verify(stap2).voerNabewerkingStapUit(
            Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class)
        );

        naverwerkingVolgorde.verify(stap1).voerNabewerkingStapUit(
            Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testBerichtMetExceptieInStap2() {
        final BerichtBericht bericht = maakNieuwBericht(new ActieRegistratieNaamgebruikBericht());
        final ActieBericht actieBericht = bericht.getAdministratieveHandeling().getActies().get(0);
        actieBericht.setRootObject(new PersoonBericht());

        Mockito.doThrow(new IllegalStateException("Test")).when(stap2)
            .voerStapUit(Matchers.any(AbstractBerichtBericht.class),
                Matchers.any(BerichtContext.class),
                Matchers.any(BerichtVerwerkingsResultaatImpl.class));
        final BerichtVerwerkingsResultaatImpl resultaat =
            berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext());

        Mockito.verify(stap1).voerStapUit(
            Matchers.any(AbstractBerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class));

        Mockito.verify(stap2).voerStapUit(
            Matchers.any(AbstractBerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class));

        Mockito.verify(stap3, Mockito.never()).voerStapUit(
            Matchers.any(AbstractBerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class));

        Assert.assertFalse(resultaat.getMeldingen().isEmpty());

        //Test naverwerking in de juiste volgorde!
        final InOrder naverwerkingVolgorde = Mockito.inOrder(stap1, stap2, stap3);

        //Naverwerking stap3 nooit uitgevoerd.
        naverwerkingVolgorde.verify(stap3, Mockito.never()).voerNabewerkingStapUit(
            Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class)
        );

        naverwerkingVolgorde.verify(stap2).voerNabewerkingStapUit(
            Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class)
        );

        naverwerkingVolgorde.verify(stap1).voerNabewerkingStapUit(
            Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNaverwerkingsStapGooitExceptie() {
        final BerichtBericht bericht = maakNieuwBericht(new ActieRegistratieNaamgebruikBericht());
        final ActieBericht actieBericht = bericht.getAdministratieveHandeling().getActies().get(0);
        actieBericht.setRootObject(new PersoonBericht());

        Mockito.doThrow(new IllegalStateException("Test")).when(stap2)
            .voerNabewerkingStapUit(Matchers.any(BerichtBericht.class),
                Matchers.any(BerichtContext.class),
                Matchers.any(BerichtVerwerkingsResultaatImpl.class));
        final BerichtVerwerkingsResultaatImpl resultaat =
            berichtVerwerker.verwerkBericht(bericht, bouwBerichtContext());

        //Stappen worden gewoon uitgevoerd
        Mockito.verify(stap1).voerStapUit(
            Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class));

        Mockito.verify(stap2).voerStapUit(
            Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class));

        Mockito.verify(stap3).voerStapUit(
            Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class));

        //Test naverwerking in de juiste volgorde!
        final InOrder naverwerkingVolgorde = Mockito.inOrder(stap1, stap2, stap3);

        naverwerkingVolgorde.verify(stap3).voerNabewerkingStapUit(
            Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class)
        );

        naverwerkingVolgorde.verify(stap2).voerNabewerkingStapUit(
            Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class)
        );

        //Naverwerking stap1 wordt WEL uitgevoerd.
        naverwerkingVolgorde.verify(stap1).voerNabewerkingStapUit(
            Matchers.any(BerichtBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class)
        );

        Assert.assertFalse(resultaat.getMeldingen().isEmpty());
        final Melding melding = resultaat.getMeldingen().iterator().next();
        Assert.assertEquals(SoortMelding.FOUT, melding.getSoort());
    }

    @Test
    public void testStappenIsNull() {
        Assert.assertNotNull(berichtVerwerker.getStappen());
        berichtVerwerker.setStappen(null);
        Assert.assertNull(berichtVerwerker.getStappen());
    }

    @Test
    public void testVoerStapUitMetStappenIsNull() {
        berichtVerwerker.setStappen(null);

        final ActieBericht actie = new ActieRegistratieAdresBericht();

        actie.setRootObject(new PersoonBericht());
        final BerichtBericht bericht = maakNieuwBericht(actie);
        final BerichtContext context = bouwBerichtContext();
        final BerichtVerwerkingsResultaatImpl resultaat = berichtVerwerker.verwerk(bericht, context);

        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testVerwerkBerichtMetStoppendeFouten() {
        maakBerichtVerwerkerMetValidatie();

        final ActieBericht actie = new ActieRegistratieAdresBericht();
        actie.setRootObject(new PersoonBericht());
        final BerichtBericht bericht = maakNieuwBericht(actie);
        final BerichtContext context = bouwBerichtContext();
        final BerichtVerwerkingsResultaatImpl resultaat = berichtVerwerker.verwerk(bericht, context);

        Assert.assertFalse(resultaat.getMeldingen().isEmpty());
        final Melding melding = resultaat.getMeldingen().iterator().next();
        Assert.assertEquals(SoortMelding.FOUT, melding.getSoort());
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        berichtVerwerker = new AbstractBerichtVerwerker() {
            @Override
            protected void valideerBerichtOpVerplichteObjecten(final BerichtBericht bericht,
                final BerichtVerwerkingsResultaatImpl berichtResultaat)
            {

            }
        };

        // Conditioneer de berichtverwerker
        ReflectionTestUtils.setField(berichtVerwerker, "berichtResultaatFactory", berichtResultaatFactory);
        berichtVerwerker.setStappen(bouwStappenLijst());

        Mockito.when(berichtResultaatFactory.creeerBerichtResultaat(Matchers.any(BerichtBericht.class),
            Matchers.any(BerichtContext.class)))
            .thenReturn(new BerichtVerwerkingsResultaatImpl(null));
    }

    public void maakBerichtVerwerkerMetValidatie() {
        berichtVerwerker = new AbstractBerichtVerwerker() {
            @Override
            protected void valideerBerichtOpVerplichteObjecten(final BerichtBericht bericht,
                final BerichtVerwerkingsResultaatImpl berichtResultaat)
            {
                berichtResultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ACT0001, null));
            }
        };

        // Conditioneer de berichtverwerker
        ReflectionTestUtils.setField(berichtVerwerker, "berichtResultaatFactory", berichtResultaatFactory);
        berichtVerwerker.setStappen(bouwStappenLijst());
    }

    /**
     * Bouwt een lijst van stappen op, waarbij de stappen alle Mocks zijn.
     *
     * @return een lijst van stappen.
     */
    @SuppressWarnings("unchecked")
    private List<AbstractStap<BerichtBericht, BerichtContext, BerichtVerwerkingsResultaat>> bouwStappenLijst() {
        final List<AbstractStap<BerichtBericht, BerichtContext, BerichtVerwerkingsResultaat>> stappen =
            new ArrayList<>();

        Mockito.when(stap1.voerStapUit(Matchers.any(AbstractBerichtBericht.class),
            Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class))).thenReturn(true);
        stappen.add(stap1);

        Mockito.when(stap2.voerStapUit(Matchers.any(AbstractBerichtBericht.class),
            Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class))).thenReturn(true);
        stappen.add(stap2);

        Mockito.when(stap3.voerStapUit(Matchers.any(AbstractBerichtBericht.class),
            Matchers.any(BerichtContext.class),
            Matchers.any(BerichtVerwerkingsResultaatImpl.class))).thenReturn(true);
        stappen.add(stap3);
        return stappen;
    }


    /**
     * Instantieert een bericht met de opgegeven acties.
     *
     * @param acties de acties waaruit het bericht dient te bestaan.
     * @return een nieuw bericht met opgegeven acties.
     */
    private BerichtBericht maakNieuwBericht(final ActieBericht... acties) {
        final BerichtBericht bericht = new BerichtBericht(new SoortBerichtAttribuut(SoortBericht.DUMMY)) {
        };

        if (acties != null) {
            if (bericht.getStandaard() == null) {
                bericht.setStandaard(new BerichtStandaardGroepBericht());
            }
            bericht.getStandaard().setAdministratieveHandeling(new HandelingOverlijdenInNederlandBericht());
            bericht.getAdministratieveHandeling().setActies(Arrays.asList(acties));
        }
        return bericht;
    }

    /**
     * Bouwt en retourneert een standaard {@link nl.bzk.brp.business.stappen.BerichtContext} instantie, met ingevulde in- en uitgaande bericht ids, een
     * authenticatiemiddel id en een partij.
     *
     * @return een geldig bericht context.
     */
    private BerichtContext bouwBerichtContext() {
        final BerichtenIds ids = new BerichtenIds(2L, 3L);
        return new TestBerichtContext(ids, TestPartijBuilder.maker().maak(), "ref", null);
    }

}
