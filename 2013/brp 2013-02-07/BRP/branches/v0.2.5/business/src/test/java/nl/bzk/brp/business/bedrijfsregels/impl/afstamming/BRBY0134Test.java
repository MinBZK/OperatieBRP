/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * To change this template use File | Settings | File Templates.
 */
public class BRBY0134Test {

    @Mock
    PersoonRepository persoonRepository;

    @Mock
    RelatieRepository relatieRepository;

    @InjectMocks
    private BRBY0134 brby0134;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test @SuppressWarnings("unchecked")
    public void zouGeenMeldingMoetenGevenWantGeenOudersInBericht() {
        Relatie relatieBericht = mock(RelatieBericht.class);
        Relatie relatieModel = mock(RelatieModel.class);

        when(relatieBericht.getOuderBetrokkenheden()).thenReturn(Collections.EMPTY_SET);

        List<Melding> meldingen = brby0134.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
        verify(persoonRepository, never()).findByBurgerservicenummer(isA(Burgerservicenummer.class));
        verify(relatieRepository, never()).isVerwant(anyInt(), anyInt());
    }

    @Test
    public void zouGeenMeldingMoetenGevenWantEenOuderInBericht() {
        Relatie relatieBericht = mock(RelatieBericht.class);
        Relatie relatieModel = mock(RelatieModel.class);
        Set<BetrokkenheidBericht> betrokkenheidBerichten = new HashSet<BetrokkenheidBericht>();
        betrokkenheidBerichten.add(new BetrokkenheidBericht());

        doReturn(betrokkenheidBerichten).when(relatieBericht).getOuderBetrokkenheden();
        List<Melding> meldingen = brby0134.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
        verify(persoonRepository, never()).findByBurgerservicenummer(isA(Burgerservicenummer.class));
        verify(relatieRepository, never()).isVerwant(anyInt(), anyInt());
    }

    @Test
    public void zouGeenMeldingMoetenGevenWantMeerDanTweeOudersInBericht() {
        Relatie relatieBericht = mock(RelatieBericht.class);
        Relatie relatieModel = mock(RelatieModel.class);

        Set<BetrokkenheidBericht> betrokkenheidBerichten = new HashSet<BetrokkenheidBericht>();
        betrokkenheidBerichten.add(new BetrokkenheidBericht());
        betrokkenheidBerichten.add(new BetrokkenheidBericht());
        betrokkenheidBerichten.add(new BetrokkenheidBericht());

        assertThat(betrokkenheidBerichten.size(), is(3));
        doReturn(betrokkenheidBerichten).when(relatieBericht).getOuderBetrokkenheden();

        List<Melding> meldingen = brby0134.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
        verify(persoonRepository, never()).findByBurgerservicenummer(isA(Burgerservicenummer.class));
        verify(relatieRepository, never()).isVerwant(anyInt(), anyInt());
    }

    @Test
    public void zouGeenMeldingMoetenGevenOmdatBurgerservicenummerGeenPersoonModelHeeft() {
        Relatie relatieBericht = mock(RelatieBericht.class);
        Relatie relatieModel = mock(RelatieModel.class);
        PersoonModel eersteOuderModel = mock(PersoonModel.class);

        Set<BetrokkenheidBericht> betrokkenheidBerichten = new HashSet<BetrokkenheidBericht>();
        final BetrokkenheidBericht eersteOuderBericht = new BetrokkenheidBericht();
        final BetrokkenheidBericht tweedeOuderBericht = new BetrokkenheidBericht();
        final PersoonBericht eersteOuder = new PersoonBericht();
        final PersoonBericht tweedeOuder = new PersoonBericht();
        final PersoonIdentificatienummersGroepBericht eersteOuderIdentificatienummers =
                new PersoonIdentificatienummersGroepBericht();
        final PersoonIdentificatienummersGroepBericht tweedeOuderIdentificatienummers =
                new PersoonIdentificatienummersGroepBericht();
        final Burgerservicenummer eersteOuderBurgerservicenummer = new Burgerservicenummer("123");
        final Burgerservicenummer tweedeOuderBurgerservicenummer = new Burgerservicenummer("456");

        eersteOuderIdentificatienummers.setBurgerservicenummer(eersteOuderBurgerservicenummer);
        eersteOuder.setIdentificatienummers(eersteOuderIdentificatienummers);
        eersteOuderBericht.setBetrokkene(eersteOuder);

        tweedeOuderIdentificatienummers.setBurgerservicenummer(tweedeOuderBurgerservicenummer);
        tweedeOuder.setIdentificatienummers(tweedeOuderIdentificatienummers);
        tweedeOuderBericht.setBetrokkene(tweedeOuder);

        betrokkenheidBerichten.add(eersteOuderBericht);
        betrokkenheidBerichten.add(tweedeOuderBericht);

        doReturn(betrokkenheidBerichten).when(relatieBericht).getOuderBetrokkenheden();
        when(persoonRepository.findByBurgerservicenummer(eq(eersteOuderBurgerservicenummer))).thenReturn(eersteOuderModel);
        when(persoonRepository.findByBurgerservicenummer(eq(tweedeOuderBurgerservicenummer))).thenReturn(null);

        List<Melding> meldingen = brby0134.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
        verify(relatieRepository, never()).isVerwant(anyInt(), anyInt());
        verify(persoonRepository, times(2)).findByBurgerservicenummer(isA(Burgerservicenummer.class));
    }

    @Test
    public void zouGeenMeldingMoetenGevenOmdatOudersNietVerwantZijn() {
        Relatie relatieBericht = mock(RelatieBericht.class);
        Relatie relatieModel = mock(RelatieModel.class);
        PersoonModel eersteOuderModel = mock(PersoonModel.class);
        PersoonModel tweedeOuderModel = mock(PersoonModel.class);

        Set<BetrokkenheidBericht> betrokkenheidBerichten = new HashSet<BetrokkenheidBericht>();
        final BetrokkenheidBericht eersteOuderBericht = new BetrokkenheidBericht();
        final BetrokkenheidBericht tweedeOuderBericht = new BetrokkenheidBericht();
        final PersoonBericht eersteOuder = new PersoonBericht();
        final PersoonBericht tweedeOuder = new PersoonBericht();
        final PersoonIdentificatienummersGroepBericht eersteOuderIdentificatienummers =
                new PersoonIdentificatienummersGroepBericht();
        final PersoonIdentificatienummersGroepBericht tweedeOuderIdentificatienummers =
                new PersoonIdentificatienummersGroepBericht();
        final Burgerservicenummer eersteOuderBurgerservicenummer = new Burgerservicenummer("123");
        final Burgerservicenummer tweedeOuderBurgerservicenummer = new Burgerservicenummer("456");

        eersteOuderIdentificatienummers.setBurgerservicenummer(eersteOuderBurgerservicenummer);
        eersteOuder.setIdentificatienummers(eersteOuderIdentificatienummers);
        eersteOuderBericht.setBetrokkene(eersteOuder);

        tweedeOuderIdentificatienummers.setBurgerservicenummer(tweedeOuderBurgerservicenummer);
        tweedeOuder.setIdentificatienummers(tweedeOuderIdentificatienummers);
        tweedeOuderBericht.setBetrokkene(tweedeOuder);

        betrokkenheidBerichten.add(eersteOuderBericht);
        betrokkenheidBerichten.add(tweedeOuderBericht);

        doReturn(betrokkenheidBerichten).when(relatieBericht).getOuderBetrokkenheden();
        when(persoonRepository.findByBurgerservicenummer(eq(eersteOuderBurgerservicenummer))).thenReturn(eersteOuderModel);
        when(persoonRepository.findByBurgerservicenummer(eq(tweedeOuderBurgerservicenummer))).thenReturn(tweedeOuderModel);

        // zou moeten checken op 123 en 456, maar volgorde is niet bepaald
        when(relatieRepository.isVerwant(anyInt(), anyInt())).thenReturn(false);

        List<Melding> meldingen = brby0134.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
        verify(persoonRepository, times(2)).findByBurgerservicenummer(isA(Burgerservicenummer.class));
    }

    @Test
    public void zouMeldingMoetenGevenOmdatOudersVerwantZijn() {
        Relatie relatieBericht = mock(RelatieBericht.class);
        Relatie relatieModel = mock(RelatieModel.class);
        PersoonModel eersteOuderModel = mock(PersoonModel.class);
        PersoonModel tweedeOuderModel = mock(PersoonModel.class);

        Set<BetrokkenheidBericht> betrokkenheidBerichten = new HashSet<BetrokkenheidBericht>();
        final BetrokkenheidBericht eersteOuderBericht = new BetrokkenheidBericht();
        final BetrokkenheidBericht tweedeOuderBericht = new BetrokkenheidBericht();
        final PersoonBericht eersteOuder = new PersoonBericht();
        final PersoonBericht tweedeOuder = new PersoonBericht();
        final PersoonIdentificatienummersGroepBericht eersteOuderIdentificatienummers =
                new PersoonIdentificatienummersGroepBericht();
        final PersoonIdentificatienummersGroepBericht tweedeOuderIdentificatienummers =
                new PersoonIdentificatienummersGroepBericht();
        final Burgerservicenummer eersteOuderBurgerservicenummer = new Burgerservicenummer("456");
        final Burgerservicenummer tweedeOuderBurgerservicenummer = new Burgerservicenummer("123");

        eersteOuderIdentificatienummers.setBurgerservicenummer(eersteOuderBurgerservicenummer);
        eersteOuderIdentificatienummers.setVerzendendId(eersteOuderBurgerservicenummer.getWaarde());
        eersteOuder.setIdentificatienummers(eersteOuderIdentificatienummers);
        eersteOuderBericht.setBetrokkene(eersteOuder);
        eersteOuderBericht.setVerzendendId("ouder1." + eersteOuderIdentificatienummers.getVerzendendId());

        tweedeOuderIdentificatienummers.setBurgerservicenummer(tweedeOuderBurgerservicenummer);
        tweedeOuderIdentificatienummers.setVerzendendId(tweedeOuderBurgerservicenummer.getWaarde());
        tweedeOuder.setIdentificatienummers(tweedeOuderIdentificatienummers);
        tweedeOuderBericht.setBetrokkene(tweedeOuder);
        tweedeOuderBericht.setVerzendendId("ouder2." + tweedeOuderIdentificatienummers.getVerzendendId());

        betrokkenheidBerichten.add(eersteOuderBericht);
        betrokkenheidBerichten.add(tweedeOuderBericht);

        doReturn(betrokkenheidBerichten).when(relatieBericht).getOuderBetrokkenheden();
        when(persoonRepository.findByBurgerservicenummer(eq(eersteOuderBurgerservicenummer))).thenReturn(eersteOuderModel);
        when(persoonRepository.findByBurgerservicenummer(eq(tweedeOuderBurgerservicenummer))).thenReturn(tweedeOuderModel);

        // zou moeten checken op 123 en 456, maar volgorde is niet bepaald
        when(relatieRepository.isVerwant(anyInt(), anyInt())).thenReturn(true);

        List<Melding> meldingen = brby0134.executeer(relatieModel, relatieBericht, null);

        verify(persoonRepository, times(2)).findByBurgerservicenummer(isA(Burgerservicenummer.class));
        assertThat(meldingen.size(), is(1));
        // de ouders zijn gesorteerd op bsn nr., de fout zit o de 1e ouder dus ouder2.123
        // LET op: in het neiuwe model, is de ouderbetrokkenheden een list ipv. een set => de lijst is wat de
        // gebruiker stuurt (en is dus VASTE volgorde voor de tester, dus is de venzendendID istijd dezelfde en is
        // SORTERING niet meer nodig en verwachten we ALTIJD de 1 ouder ==> ouder1.456  ipv. ouder2.123
        assertThat("Ongesorteerd, vandaar in verkeerde volgorde", meldingen.get(0).getVerzendendId(), is("ouder2.123"));

    }
}
