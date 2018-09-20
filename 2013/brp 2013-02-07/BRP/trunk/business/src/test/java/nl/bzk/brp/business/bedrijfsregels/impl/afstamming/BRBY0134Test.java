/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.operationeel.kern.FamilierechtelijkeBetrekkingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/** To change this template use File | Settings | File Templates. */
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

    @Test
    @SuppressWarnings("unchecked")
    public void zouGeenMeldingMoetenGevenWantGeenOudersInBericht() {
        FamilierechtelijkeBetrekkingBericht relatieBericht = mock(FamilierechtelijkeBetrekkingBericht.class);
        FamilierechtelijkeBetrekkingModel relatieModel = mock(FamilierechtelijkeBetrekkingModel.class);

        when(relatieBericht.getOuderBetrokkenheden()).thenReturn(Collections.EMPTY_SET);

        List<Melding> meldingen = brby0134.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
        verify(persoonRepository, never()).findByBurgerservicenummer(isA(Burgerservicenummer.class));
        verify(relatieRepository, never()).isVerwant(anyInt(), anyInt());
    }

    @Test
    public void zouGeenMeldingMoetenGevenWantEenOuderInBericht() {
        FamilierechtelijkeBetrekkingBericht relatieBericht = mock(FamilierechtelijkeBetrekkingBericht.class);
        FamilierechtelijkeBetrekkingModel relatieModel = mock(FamilierechtelijkeBetrekkingModel.class);
        Set<BetrokkenheidBericht> betrokkenheidBerichten = new HashSet<BetrokkenheidBericht>();
        betrokkenheidBerichten.add(new OuderBericht());

        doReturn(betrokkenheidBerichten).when(relatieBericht).getOuderBetrokkenheden();
        List<Melding> meldingen = brby0134.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
        verify(persoonRepository, never()).findByBurgerservicenummer(isA(Burgerservicenummer.class));
        verify(relatieRepository, never()).isVerwant(anyInt(), anyInt());
    }

    @Test
    public void zouGeenMeldingMoetenGevenWantMeerDanTweeOudersInBericht() {
        FamilierechtelijkeBetrekkingBericht relatieBericht = mock(FamilierechtelijkeBetrekkingBericht.class);
        FamilierechtelijkeBetrekkingModel relatieModel = mock(FamilierechtelijkeBetrekkingModel.class);

        Set<BetrokkenheidBericht> betrokkenheidBerichten = new HashSet<BetrokkenheidBericht>();
        betrokkenheidBerichten.add(new OuderBericht());
        betrokkenheidBerichten.add(new OuderBericht());
        betrokkenheidBerichten.add(new OuderBericht());

        assertThat(betrokkenheidBerichten.size(), is(3));
        doReturn(betrokkenheidBerichten).when(relatieBericht).getOuderBetrokkenheden();

        List<Melding> meldingen = brby0134.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
        verify(persoonRepository, never()).findByBurgerservicenummer(isA(Burgerservicenummer.class));
        verify(relatieRepository, never()).isVerwant(anyInt(), anyInt());
    }

    @Test
    public void zouGeenMeldingMoetenGevenOmdatBurgerservicenummerGeenPersoonModelHeeft() {
        FamilierechtelijkeBetrekkingBericht relatieBericht = mock(FamilierechtelijkeBetrekkingBericht.class);
        FamilierechtelijkeBetrekkingModel relatieModel = mock(FamilierechtelijkeBetrekkingModel.class);
        PersoonModel eersteOuderModel = mock(PersoonModel.class);

        Set<BetrokkenheidBericht> betrokkenheidBerichten = new HashSet<BetrokkenheidBericht>();
        final OuderBericht eersteOuderBericht = new OuderBericht();
        final OuderBericht tweedeOuderBericht = new OuderBericht();
        final PersoonBericht eersteOuder = new PersoonBericht();
        final PersoonBericht tweedeOuder = new PersoonBericht();
        final PersoonIdentificatienummersGroepBericht eersteOuderIdentificatienummers =
            new PersoonIdentificatienummersGroepBericht();
        final PersoonIdentificatienummersGroepBericht tweedeOuderIdentificatienummers =
            new PersoonIdentificatienummersGroepBericht();
        final Burgerservicenummer eersteOuderBurgerservicenummer = new Burgerservicenummer("456");
        final Burgerservicenummer tweedeOuderBurgerservicenummer = new Burgerservicenummer("124");

        eersteOuderIdentificatienummers.setBurgerservicenummer(eersteOuderBurgerservicenummer);
        eersteOuder.setIdentificatienummers(eersteOuderIdentificatienummers);
        eersteOuderBericht.setPersoon(eersteOuder);

        tweedeOuderIdentificatienummers.setBurgerservicenummer(tweedeOuderBurgerservicenummer);
        tweedeOuder.setIdentificatienummers(tweedeOuderIdentificatienummers);
        tweedeOuderBericht.setPersoon(tweedeOuder);

        betrokkenheidBerichten.add(eersteOuderBericht);
        betrokkenheidBerichten.add(tweedeOuderBericht);

        doReturn(betrokkenheidBerichten).when(relatieBericht).getOuderBetrokkenheden();
        when(persoonRepository.findByBurgerservicenummer(eq(eersteOuderBurgerservicenummer)))
            .thenReturn(eersteOuderModel);
        when(persoonRepository.findByBurgerservicenummer(eq(tweedeOuderBurgerservicenummer))).thenReturn(null);

        List<Melding> meldingen = brby0134.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
        verify(relatieRepository, never()).isVerwant(anyInt(), anyInt());
        verify(persoonRepository, times(2)).findByBurgerservicenummer(isA(Burgerservicenummer.class));
    }

    @Test
    public void zouGeenMeldingMoetenGevenOmdatOudersNietVerwantZijn() {
        FamilierechtelijkeBetrekkingBericht relatieBericht = mock(FamilierechtelijkeBetrekkingBericht.class);
        FamilierechtelijkeBetrekkingModel relatieModel = mock(FamilierechtelijkeBetrekkingModel.class);
        PersoonModel eersteOuderModel = mock(PersoonModel.class);
        PersoonModel tweedeOuderModel = mock(PersoonModel.class);

        Set<BetrokkenheidBericht> betrokkenheidBerichten = new HashSet<BetrokkenheidBericht>();
        final OuderBericht eersteOuderBericht = new OuderBericht();
        final OuderBericht tweedeOuderBericht = new OuderBericht();
        final PersoonBericht eersteOuder = new PersoonBericht();
        final PersoonBericht tweedeOuder = new PersoonBericht();
        final PersoonIdentificatienummersGroepBericht eersteOuderIdentificatienummers =
            new PersoonIdentificatienummersGroepBericht();
        final PersoonIdentificatienummersGroepBericht tweedeOuderIdentificatienummers =
            new PersoonIdentificatienummersGroepBericht();
        final Burgerservicenummer eersteOuderBurgerservicenummer = new Burgerservicenummer("456");
        final Burgerservicenummer tweedeOuderBurgerservicenummer = new Burgerservicenummer("123");

        eersteOuderIdentificatienummers.setBurgerservicenummer(eersteOuderBurgerservicenummer);
        eersteOuder.setIdentificatienummers(eersteOuderIdentificatienummers);
        eersteOuderBericht.setPersoon(eersteOuder);

        tweedeOuderIdentificatienummers.setBurgerservicenummer(tweedeOuderBurgerservicenummer);
        tweedeOuder.setIdentificatienummers(tweedeOuderIdentificatienummers);
        tweedeOuderBericht.setPersoon(tweedeOuder);

        betrokkenheidBerichten.add(eersteOuderBericht);
        betrokkenheidBerichten.add(tweedeOuderBericht);

        doReturn(betrokkenheidBerichten).when(relatieBericht).getOuderBetrokkenheden();
        when(persoonRepository.findByBurgerservicenummer(eq(eersteOuderBurgerservicenummer)))
            .thenReturn(eersteOuderModel);
        when(persoonRepository.findByBurgerservicenummer(eq(tweedeOuderBurgerservicenummer)))
            .thenReturn(tweedeOuderModel);

        // zou moeten checken op 123 en 456, maar volgorde is niet bepaald
        when(relatieRepository.isVerwant(anyInt(), anyInt())).thenReturn(false);

        List<Melding> meldingen = brby0134.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
        verify(persoonRepository, times(2)).findByBurgerservicenummer(isA(Burgerservicenummer.class));
    }

    @Test
    public void zouMeldingMoetenGevenOmdatOudersVerwantZijn() {
        FamilierechtelijkeBetrekkingBericht relatieBericht = mock(FamilierechtelijkeBetrekkingBericht.class);
        FamilierechtelijkeBetrekkingModel relatieModel = mock(FamilierechtelijkeBetrekkingModel.class);
        PersoonModel eersteOuderModel = mock(PersoonModel.class);
        PersoonModel tweedeOuderModel = mock(PersoonModel.class);

        Set<BetrokkenheidBericht> betrokkenheidBerichten = new HashSet<BetrokkenheidBericht>();
        final OuderBericht eersteOuderBericht = new OuderBericht();
        final OuderBericht tweedeOuderBericht = new OuderBericht();
        final PersoonBericht eersteOuder = new PersoonBericht();
        final PersoonBericht tweedeOuder = new PersoonBericht();
        final PersoonIdentificatienummersGroepBericht eersteOuderIdentificatienummers =
            new PersoonIdentificatienummersGroepBericht();
        final PersoonIdentificatienummersGroepBericht tweedeOuderIdentificatienummers =
            new PersoonIdentificatienummersGroepBericht();
        // expliciet omgekeerde volgorde van bsn, om de sortering te testen.
        final Burgerservicenummer eersteOuderBurgerservicenummer = new Burgerservicenummer("456");
        final Burgerservicenummer tweedeOuderBurgerservicenummer = new Burgerservicenummer("123");

        eersteOuderIdentificatienummers.setBurgerservicenummer(eersteOuderBurgerservicenummer);
        eersteOuderIdentificatienummers.setCommunicatieID(eersteOuderBurgerservicenummer.toString());
        eersteOuder.setIdentificatienummers(eersteOuderIdentificatienummers);
        eersteOuder.setCommunicatieID("ouder1.pers." + eersteOuderIdentificatienummers.getCommunicatieID());
        eersteOuderBericht.setPersoon(eersteOuder);
        eersteOuderBericht.setCommunicatieID("ouder1." + eersteOuderIdentificatienummers.getCommunicatieID());

        tweedeOuderIdentificatienummers.setBurgerservicenummer(tweedeOuderBurgerservicenummer);
        tweedeOuderIdentificatienummers.setCommunicatieID(tweedeOuderBurgerservicenummer.toString());
        tweedeOuder.setIdentificatienummers(tweedeOuderIdentificatienummers);
        tweedeOuder.setCommunicatieID("ouder2.pers." + tweedeOuderIdentificatienummers.getCommunicatieID());
        tweedeOuderBericht.setPersoon(tweedeOuder);
        tweedeOuderBericht.setCommunicatieID("ouder2." + tweedeOuderIdentificatienummers.getCommunicatieID());

        betrokkenheidBerichten.add(eersteOuderBericht);
        betrokkenheidBerichten.add(tweedeOuderBericht);

        doReturn(betrokkenheidBerichten).when(relatieBericht).getOuderBetrokkenheden();
        when(persoonRepository.findByBurgerservicenummer(eq(eersteOuderBurgerservicenummer)))
            .thenReturn(eersteOuderModel);
        when(persoonRepository.findByBurgerservicenummer(eq(tweedeOuderBurgerservicenummer)))
            .thenReturn(tweedeOuderModel);

        // zou moeten checken op 123 en 456, maar volgorde is niet bepaald
        when(relatieRepository.isVerwant(anyInt(), anyInt())).thenReturn(true);

        List<Melding> meldingen = brby0134.executeer(relatieModel, relatieBericht, null);

        verify(persoonRepository, times(2)).findByBurgerservicenummer(isA(Burgerservicenummer.class));
        assertThat(meldingen.size(), is(1));
        // de ouders zijn gesorteerd op bsn nr., de fout zit o de 1e ouder dus ouder2.123
        // LET op: in het neiuwe model, is de ouderbetrokkenheden een list ipv. een set => de lijst is wat de
        // gebruiker stuurt (en is dus VASTE volgorde voor de tester, dus is de venzendendID istijd dezelfde en is
        // SORTERING niet meer nodig en verwachten we ALTIJD de 1 ouder ==> ouder1.456  ipv. ouder2.123
        assertThat("Ongesorteerd, vandaar in verkeerde volgorde", meldingen.get(0).getCommunicatieID(), is("ouder2.000000123"));
    }
}
