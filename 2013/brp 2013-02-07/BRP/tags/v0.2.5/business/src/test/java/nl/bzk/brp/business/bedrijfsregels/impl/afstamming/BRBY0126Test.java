/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.brp.model.attribuuttype.Scheidingsteken;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.groep.bericht.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonGeboorteGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonSamengesteldeNaamGroepModel;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.DatumUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 *
 */
public class BRBY0126Test {


    @Mock
    private PersoonRepository persoonRepository;

    @Mock
    private RelatieModel relatieModel;

    @Mock
    private RelatieBericht relatieBericht;

    @Mock
    private PersoonBericht kindBericht;

    private final BetrokkenheidBericht betrokkenheidBericht = new BetrokkenheidBericht();

    @InjectMocks
    private final BRBY0126 brby0126 = new BRBY0126();

    @Before
    public void setup() {
        initMocks(this);
        betrokkenheidBericht.setRol(SoortBetrokkenheid.KIND);
        betrokkenheidBericht.setBetrokkene(kindBericht);

        // RelatieUtils calls
        when(relatieBericht.getSoort()).thenReturn(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        when(relatieBericht.getBetrokkenheden()).thenReturn(Arrays.<BetrokkenheidBericht>asList(betrokkenheidBericht));
    }




    @Test
    public void zouGeenMeldingMoetenGevenOmdatErGeenOuderBetrokkenhedenZijn() {
        when(relatieBericht.getOuderBetrokkenheden()).thenReturn(Collections.<BetrokkenheidBericht>emptySet());

        List<Melding> meldingen = brby0126.executeer(relatieModel, relatieBericht, null);
        assertThat(meldingen.size(), is(0));
        verify(persoonRepository, never()).findByBurgerservicenummer(isA(Burgerservicenummer.class));
    }

    @Test  @Ignore
    public void zouGeenMeldingMoetenGevenOmdatOuderGeenKindHeeft() {
        PersoonBericht ouder = mock(PersoonBericht.class);
        BetrokkenheidBericht ouderBetrokkenheidBericht = new BetrokkenheidBericht();
        ouderBetrokkenheidBericht.setBetrokkene(ouder);
        final PersoonIdentificatienummersGroepBericht identificatieNummers = mock(PersoonIdentificatienummersGroepBericht.class);
        final Burgerservicenummer burgerservicenummer = new Burgerservicenummer("123");
        final PersoonModel ouderModel = mock(PersoonModel.class);

        Set<BetrokkenheidBericht> ouders = new HashSet<BetrokkenheidBericht>();
        ouders.add(ouderBetrokkenheidBericht);

        when(relatieBericht.getOuderBetrokkenheden()).thenReturn(ouders);
        when(ouder.getIdentificatienummers()).thenReturn(identificatieNummers);
        when(identificatieNummers.getBurgerservicenummer()).thenReturn(burgerservicenummer);
        when(persoonRepository.findByBurgerservicenummer(burgerservicenummer)).thenReturn(ouderModel);
        when(ouderModel.getKindBetrokkenheden()).thenReturn(Collections.<BetrokkenheidModel>emptySet());


        List<Melding> meldingen = brby0126.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
        verify(ouder).getIdentificatienummers();
        verify(kindBericht, never()).getGeboorte();

    }

    @Ignore //TODO Frank, kan je deze test even corrigeren?
    @Test
    public void zouGeenMeldingMoetenGevenOmdatGeboortedatumNaGeregistreerdKindIs() {
        PersoonBericht ouder = mock(PersoonBericht.class);
        final PersoonModel kindModel= mock(PersoonModel.class);
        final PersoonModel ouderModel = mock(PersoonModel.class);
        final BetrokkenheidModel betrokkenheidModel = mock(BetrokkenheidModel.class);

        final PersoonGeboorteGroepBericht persoonGeboorteGroepBericht = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroepBericht.setDatumGeboorte(DatumUtil.vandaag());
        final PersoonGeboorteGroepModel persoonGeboorteGroepModel = mock(PersoonGeboorteGroepModel.class);

        BetrokkenheidBericht ouderBetrokkenheidBericht = new BetrokkenheidBericht();
        ouderBetrokkenheidBericht.setBetrokkene(ouder);
        final PersoonIdentificatienummersGroepBericht identificatieNummers = mock(PersoonIdentificatienummersGroepBericht.class);
        final Burgerservicenummer burgerservicenummer = new Burgerservicenummer("123");
        Set<BetrokkenheidBericht> ouders = new HashSet<BetrokkenheidBericht>();
        ouders.add(ouderBetrokkenheidBericht);
        final Set<BetrokkenheidModel> kinderBetrokkenheden = new HashSet<BetrokkenheidModel>();
        kinderBetrokkenheden.add(betrokkenheidModel);



        when(relatieBericht.getOuderBetrokkenheden()).thenReturn(ouders);
        when(ouder.getIdentificatienummers()).thenReturn(identificatieNummers);
        when(identificatieNummers.getBurgerservicenummer()).thenReturn(burgerservicenummer);
        when(persoonRepository.findByBurgerservicenummer(burgerservicenummer)).thenReturn(ouderModel);
        when(ouderModel.getKindBetrokkenheden()).thenReturn(kinderBetrokkenheden);
        when(betrokkenheidModel.getBetrokkene()).thenReturn(kindModel);

        when(kindBericht.getGeboorte()).thenReturn(persoonGeboorteGroepBericht);
        when(kindModel.getGeboorte()).thenReturn(persoonGeboorteGroepModel);
        when(persoonGeboorteGroepModel.getDatumGeboorte()).thenReturn(DatumUtil.morgen());

        List<Melding> meldingen = brby0126.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
        verify(kindModel).getGeboorte();


    }

    @Test
    public void zouGeenMeldingMoetenGevenOmdatVoornamenAndersZijn() {
        PersoonBericht ouder = mock(PersoonBericht.class);
        final PersoonModel kindModel= mock(PersoonModel.class);
        final PersoonModel ouderModel = mock(PersoonModel.class);
        final BetrokkenheidModel betrokkenheidModel = mock(BetrokkenheidModel.class);

        final PersoonGeboorteGroepBericht persoonGeboorteGroepBericht = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroepBericht.setDatumGeboorte(DatumUtil.vandaag());
        final PersoonGeboorteGroepModel persoonGeboorteGroepModel = mock(PersoonGeboorteGroepModel.class);

        final PersoonSamengesteldeNaamGroepBericht persoonSamengesteldeNaamGroepBericht = new PersoonSamengesteldeNaamGroepBericht();
        persoonSamengesteldeNaamGroepBericht.setGeslachtsnaam(new Geslachtsnaamcomponent("Janssen"));
        persoonSamengesteldeNaamGroepBericht.setScheidingsteken(new Scheidingsteken("/"));
        persoonSamengesteldeNaamGroepBericht.setVoornamen(new Voornaam("Piet"));
        persoonSamengesteldeNaamGroepBericht.setVoorvoegsel(new Voorvoegsel("den"));

        final PersoonSamengesteldeNaamGroepModel persoonSamengesteldeNaamGroepModel = mock(PersoonSamengesteldeNaamGroepModel.class);

        BetrokkenheidBericht ouderBetrokkenheidBericht = new BetrokkenheidBericht();
        ouderBetrokkenheidBericht.setBetrokkene(ouder);
        final PersoonIdentificatienummersGroepBericht identificatieNummers = mock(PersoonIdentificatienummersGroepBericht.class);
        final Burgerservicenummer burgerservicenummer = new Burgerservicenummer("123");
        Set<BetrokkenheidBericht> ouders = new HashSet<BetrokkenheidBericht>();
        ouders.add(ouderBetrokkenheidBericht);
        final Set<BetrokkenheidModel> kinderBetrokkenheden = new HashSet<BetrokkenheidModel>();
        kinderBetrokkenheden.add(betrokkenheidModel);



        when(relatieBericht.getOuderBetrokkenheden()).thenReturn(ouders);
        when(ouder.getIdentificatienummers()).thenReturn(identificatieNummers);
        when(identificatieNummers.getBurgerservicenummer()).thenReturn(burgerservicenummer);
        when(persoonRepository.findByBurgerservicenummer(burgerservicenummer)).thenReturn(ouderModel);
        when(ouderModel.getKindBetrokkenheden()).thenReturn(kinderBetrokkenheden);
        when(betrokkenheidModel.getBetrokkene()).thenReturn(kindModel);

        when(kindBericht.getGeboorte()).thenReturn(persoonGeboorteGroepBericht);
        when(kindBericht.getSamengesteldeNaam()).thenReturn(persoonSamengesteldeNaamGroepBericht);
        when(kindModel.getGeboorte()).thenReturn(persoonGeboorteGroepModel);
        when(persoonGeboorteGroepModel.getDatumGeboorte()).thenReturn(DatumUtil.vandaag());
        when(kindModel.getSamengesteldeNaam()).thenReturn(persoonSamengesteldeNaamGroepModel);
        when(persoonSamengesteldeNaamGroepModel.getGeslachtsnaam()).thenReturn(new Geslachtsnaamcomponent("Janssen"));
        when(persoonSamengesteldeNaamGroepModel.getScheidingsteken()).thenReturn(new Scheidingsteken("/"));
        when(persoonSamengesteldeNaamGroepModel.getVoornamen()).thenReturn(new Voornaam("Jan"));
        when(persoonSamengesteldeNaamGroepModel.getVoorvoegsel()).thenReturn(new Voorvoegsel("den"));

        List<Melding> meldingen = brby0126.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
    }

    @Test
    public void zouGeenMeldingMoetenGevenOmdatGeslachtsNamemAndersZijn() {
        PersoonBericht ouder = mock(PersoonBericht.class);
        final PersoonModel kindModel= mock(PersoonModel.class);
        final PersoonModel ouderModel = mock(PersoonModel.class);
        final BetrokkenheidModel betrokkenheidModel = mock(BetrokkenheidModel.class);

        final PersoonGeboorteGroepBericht persoonGeboorteGroepBericht = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroepBericht.setDatumGeboorte(DatumUtil.vandaag());
        final PersoonGeboorteGroepModel persoonGeboorteGroepModel = mock(PersoonGeboorteGroepModel.class);

        final PersoonSamengesteldeNaamGroepBericht persoonSamengesteldeNaamGroepBericht = new PersoonSamengesteldeNaamGroepBericht();
        persoonSamengesteldeNaamGroepBericht.setGeslachtsnaam(new Geslachtsnaamcomponent("Janssens"));
        persoonSamengesteldeNaamGroepBericht.setScheidingsteken(new Scheidingsteken("/"));
        persoonSamengesteldeNaamGroepBericht.setVoornamen(new Voornaam("Jan"));
        persoonSamengesteldeNaamGroepBericht.setVoorvoegsel(new Voorvoegsel("den"));

        final PersoonSamengesteldeNaamGroepModel persoonSamengesteldeNaamGroepModel = mock(PersoonSamengesteldeNaamGroepModel.class);

        BetrokkenheidBericht ouderBetrokkenheidBericht = new BetrokkenheidBericht();
        ouderBetrokkenheidBericht.setBetrokkene(ouder);
        final PersoonIdentificatienummersGroepBericht identificatieNummers = mock(PersoonIdentificatienummersGroepBericht.class);
        final Burgerservicenummer burgerservicenummer = new Burgerservicenummer("123");
        Set<BetrokkenheidBericht> ouders = new HashSet<BetrokkenheidBericht>();
        ouders.add(ouderBetrokkenheidBericht);
        final Set<BetrokkenheidModel> kinderBetrokkenheden = new HashSet<BetrokkenheidModel>();
        kinderBetrokkenheden.add(betrokkenheidModel);



        when(relatieBericht.getOuderBetrokkenheden()).thenReturn(ouders);
        when(ouder.getIdentificatienummers()).thenReturn(identificatieNummers);
        when(identificatieNummers.getBurgerservicenummer()).thenReturn(burgerservicenummer);
        when(persoonRepository.findByBurgerservicenummer(burgerservicenummer)).thenReturn(ouderModel);
        when(ouderModel.getKindBetrokkenheden()).thenReturn(kinderBetrokkenheden);
        when(betrokkenheidModel.getBetrokkene()).thenReturn(kindModel);

        when(kindBericht.getGeboorte()).thenReturn(persoonGeboorteGroepBericht);
        when(kindBericht.getSamengesteldeNaam()).thenReturn(persoonSamengesteldeNaamGroepBericht);
        when(kindModel.getGeboorte()).thenReturn(persoonGeboorteGroepModel);
        when(persoonGeboorteGroepModel.getDatumGeboorte()).thenReturn(DatumUtil.vandaag());
        when(kindModel.getSamengesteldeNaam()).thenReturn(persoonSamengesteldeNaamGroepModel);
        when(persoonSamengesteldeNaamGroepModel.getGeslachtsnaam()).thenReturn(new Geslachtsnaamcomponent("Janssen"));
        when(persoonSamengesteldeNaamGroepModel.getScheidingsteken()).thenReturn(new Scheidingsteken("/"));
        when(persoonSamengesteldeNaamGroepModel.getVoornamen()).thenReturn(new Voornaam("Jan"));
        when(persoonSamengesteldeNaamGroepModel.getVoorvoegsel()).thenReturn(new Voorvoegsel("den"));

        List<Melding> meldingen = brby0126.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
    }

    @Test
    public void zouGeenMeldingMoetenGevenOmdatScheidingsTekensAndersZijn() {
        PersoonBericht ouder = mock(PersoonBericht.class);
        final PersoonModel kindModel= mock(PersoonModel.class);
        final PersoonModel ouderModel = mock(PersoonModel.class);
        final BetrokkenheidModel betrokkenheidModel = mock(BetrokkenheidModel.class);

        final PersoonGeboorteGroepBericht persoonGeboorteGroepBericht = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroepBericht.setDatumGeboorte(DatumUtil.vandaag());
        final PersoonGeboorteGroepModel persoonGeboorteGroepModel = mock(PersoonGeboorteGroepModel.class);

        final PersoonSamengesteldeNaamGroepBericht persoonSamengesteldeNaamGroepBericht = new PersoonSamengesteldeNaamGroepBericht();
        persoonSamengesteldeNaamGroepBericht.setGeslachtsnaam(new Geslachtsnaamcomponent("Janssen"));
        persoonSamengesteldeNaamGroepBericht.setScheidingsteken(new Scheidingsteken("-"));
        persoonSamengesteldeNaamGroepBericht.setVoornamen(new Voornaam("Jan"));
        persoonSamengesteldeNaamGroepBericht.setVoorvoegsel(new Voorvoegsel("den"));

        final PersoonSamengesteldeNaamGroepModel persoonSamengesteldeNaamGroepModel = mock(PersoonSamengesteldeNaamGroepModel.class);

        BetrokkenheidBericht ouderBetrokkenheidBericht = new BetrokkenheidBericht();
        ouderBetrokkenheidBericht.setBetrokkene(ouder);
        final PersoonIdentificatienummersGroepBericht identificatieNummers = mock(PersoonIdentificatienummersGroepBericht.class);
        final Burgerservicenummer burgerservicenummer = new Burgerservicenummer("123");
        Set<BetrokkenheidBericht> ouders = new HashSet<BetrokkenheidBericht>();
        ouders.add(ouderBetrokkenheidBericht);
        final Set<BetrokkenheidModel> kinderBetrokkenheden = new HashSet<BetrokkenheidModel>();
        kinderBetrokkenheden.add(betrokkenheidModel);



        when(relatieBericht.getOuderBetrokkenheden()).thenReturn(ouders);
        when(ouder.getIdentificatienummers()).thenReturn(identificatieNummers);
        when(identificatieNummers.getBurgerservicenummer()).thenReturn(burgerservicenummer);
        when(persoonRepository.findByBurgerservicenummer(burgerservicenummer)).thenReturn(ouderModel);
        when(ouderModel.getKindBetrokkenheden()).thenReturn(kinderBetrokkenheden);
        when(betrokkenheidModel.getBetrokkene()).thenReturn(kindModel);

        when(kindBericht.getGeboorte()).thenReturn(persoonGeboorteGroepBericht);
        when(kindBericht.getSamengesteldeNaam()).thenReturn(persoonSamengesteldeNaamGroepBericht);
        when(kindModel.getGeboorte()).thenReturn(persoonGeboorteGroepModel);
        when(persoonGeboorteGroepModel.getDatumGeboorte()).thenReturn(DatumUtil.vandaag());
        when(kindModel.getSamengesteldeNaam()).thenReturn(persoonSamengesteldeNaamGroepModel);
        when(persoonSamengesteldeNaamGroepModel.getGeslachtsnaam()).thenReturn(new Geslachtsnaamcomponent("Janssen"));
        when(persoonSamengesteldeNaamGroepModel.getScheidingsteken()).thenReturn(new Scheidingsteken("/"));
        when(persoonSamengesteldeNaamGroepModel.getVoornamen()).thenReturn(new Voornaam("Jan"));
        when(persoonSamengesteldeNaamGroepModel.getVoorvoegsel()).thenReturn(new Voorvoegsel("den"));

        List<Melding> meldingen = brby0126.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
    }

    @Test
    public void zouGeenMeldingMoetenGevenOmdatVoorvoegselsAndersZijn() {
        PersoonBericht ouder = mock(PersoonBericht.class);
        final PersoonModel kindModel= mock(PersoonModel.class);
        final PersoonModel ouderModel = mock(PersoonModel.class);
        final BetrokkenheidModel betrokkenheidModel = mock(BetrokkenheidModel.class);

        final PersoonGeboorteGroepBericht persoonGeboorteGroepBericht = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroepBericht.setDatumGeboorte(DatumUtil.vandaag());
        final PersoonGeboorteGroepModel persoonGeboorteGroepModel = mock(PersoonGeboorteGroepModel.class);

        final PersoonSamengesteldeNaamGroepBericht persoonSamengesteldeNaamGroepBericht = new PersoonSamengesteldeNaamGroepBericht();
        persoonSamengesteldeNaamGroepBericht.setGeslachtsnaam(new Geslachtsnaamcomponent("Janssen"));
        persoonSamengesteldeNaamGroepBericht.setScheidingsteken(new Scheidingsteken("/"));
        persoonSamengesteldeNaamGroepBericht.setVoornamen(new Voornaam("Jan"));
        persoonSamengesteldeNaamGroepBericht.setVoorvoegsel(new Voorvoegsel("op den"));

        final PersoonSamengesteldeNaamGroepModel persoonSamengesteldeNaamGroepModel = mock(PersoonSamengesteldeNaamGroepModel.class);

        BetrokkenheidBericht ouderBetrokkenheidBericht = new BetrokkenheidBericht();
        ouderBetrokkenheidBericht.setBetrokkene(ouder);
        final PersoonIdentificatienummersGroepBericht identificatieNummers = mock(PersoonIdentificatienummersGroepBericht.class);
        final Burgerservicenummer burgerservicenummer = new Burgerservicenummer("123");
        Set<BetrokkenheidBericht> ouders = new HashSet<BetrokkenheidBericht>();
        ouders.add(ouderBetrokkenheidBericht);
        final Set<BetrokkenheidModel> kinderBetrokkenheden = new HashSet<BetrokkenheidModel>();
        kinderBetrokkenheden.add(betrokkenheidModel);



        when(relatieBericht.getOuderBetrokkenheden()).thenReturn(ouders);
        when(ouder.getIdentificatienummers()).thenReturn(identificatieNummers);
        when(identificatieNummers.getBurgerservicenummer()).thenReturn(burgerservicenummer);
        when(persoonRepository.findByBurgerservicenummer(burgerservicenummer)).thenReturn(ouderModel);
        when(ouderModel.getKindBetrokkenheden()).thenReturn(kinderBetrokkenheden);
        when(betrokkenheidModel.getBetrokkene()).thenReturn(kindModel);

        when(kindBericht.getGeboorte()).thenReturn(persoonGeboorteGroepBericht);
        when(kindBericht.getSamengesteldeNaam()).thenReturn(persoonSamengesteldeNaamGroepBericht);
        when(kindModel.getGeboorte()).thenReturn(persoonGeboorteGroepModel);
        when(persoonGeboorteGroepModel.getDatumGeboorte()).thenReturn(DatumUtil.vandaag());
        when(kindModel.getSamengesteldeNaam()).thenReturn(persoonSamengesteldeNaamGroepModel);
        when(persoonSamengesteldeNaamGroepModel.getGeslachtsnaam()).thenReturn(new Geslachtsnaamcomponent("Janssen"));
        when(persoonSamengesteldeNaamGroepModel.getScheidingsteken()).thenReturn(new Scheidingsteken("/"));
        when(persoonSamengesteldeNaamGroepModel.getVoornamen()).thenReturn(new Voornaam("Jan"));
        when(persoonSamengesteldeNaamGroepModel.getVoorvoegsel()).thenReturn(new Voorvoegsel("den"));

        List<Melding> meldingen = brby0126.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
    }

    @Ignore //TODO Frank, kan je deze test even corrigeren?
    @Test
    public void zouMeldingMoetenGevenOmdatGeboortedatumEnSamengesteldeNaamZelfdeZijn() {
        PersoonBericht ouder = mock(PersoonBericht.class);
        final PersoonModel kindModel= mock(PersoonModel.class);
        final PersoonModel ouderModel = mock(PersoonModel.class);
        final BetrokkenheidModel betrokkenheidModel = mock(BetrokkenheidModel.class);

        final PersoonGeboorteGroepBericht persoonGeboorteGroepBericht = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroepBericht.setDatumGeboorte(DatumUtil.vandaag());
        final PersoonGeboorteGroepModel persoonGeboorteGroepModel = mock(PersoonGeboorteGroepModel.class);

        final PersoonSamengesteldeNaamGroepBericht persoonSamengesteldeNaamGroepBericht = new PersoonSamengesteldeNaamGroepBericht();
        persoonSamengesteldeNaamGroepBericht.setGeslachtsnaam(new Geslachtsnaamcomponent("Janssen"));
        persoonSamengesteldeNaamGroepBericht.setScheidingsteken(new Scheidingsteken("/"));
        persoonSamengesteldeNaamGroepBericht.setVoornamen(new Voornaam("Jan"));
        persoonSamengesteldeNaamGroepBericht.setVoorvoegsel(new Voorvoegsel("den"));

        final PersoonSamengesteldeNaamGroepModel persoonSamengesteldeNaamGroepModel = mock(PersoonSamengesteldeNaamGroepModel.class);

        BetrokkenheidBericht ouderBetrokkenheidBericht = new BetrokkenheidBericht();
        ouderBetrokkenheidBericht.setBetrokkene(ouder);
        final PersoonIdentificatienummersGroepBericht identificatieNummers = mock(PersoonIdentificatienummersGroepBericht.class);
        final Burgerservicenummer burgerservicenummer = new Burgerservicenummer("123");
        Set<BetrokkenheidBericht> ouders = new HashSet<BetrokkenheidBericht>();
        ouders.add(ouderBetrokkenheidBericht);
        final Set<BetrokkenheidModel> kinderBetrokkenheden = new HashSet<BetrokkenheidModel>();
        kinderBetrokkenheden.add(betrokkenheidModel);



        when(relatieBericht.getOuderBetrokkenheden()).thenReturn(ouders);
        when(ouder.getIdentificatienummers()).thenReturn(identificatieNummers);
        when(identificatieNummers.getBurgerservicenummer()).thenReturn(burgerservicenummer);
        when(persoonRepository.findByBurgerservicenummer(burgerservicenummer)).thenReturn(ouderModel);
        when(ouderModel.getKindBetrokkenheden()).thenReturn(kinderBetrokkenheden);
        when(betrokkenheidModel.getBetrokkene()).thenReturn(kindModel);

        when(kindBericht.getGeboorte()).thenReturn(persoonGeboorteGroepBericht);
        when(kindBericht.getSamengesteldeNaam()).thenReturn(persoonSamengesteldeNaamGroepBericht);
        when(kindModel.getGeboorte()).thenReturn(persoonGeboorteGroepModel);
        when(persoonGeboorteGroepModel.getDatumGeboorte()).thenReturn(DatumUtil.vandaag());
        when(kindModel.getSamengesteldeNaam()).thenReturn(persoonSamengesteldeNaamGroepModel);
        when(persoonSamengesteldeNaamGroepModel.getGeslachtsnaam()).thenReturn(new Geslachtsnaamcomponent("Janssen"));
        when(persoonSamengesteldeNaamGroepModel.getScheidingsteken()).thenReturn(new Scheidingsteken("/"));
        when(persoonSamengesteldeNaamGroepModel.getVoornamen()).thenReturn(new Voornaam("Jan"));
        when(persoonSamengesteldeNaamGroepModel.getVoorvoegsel()).thenReturn(new Voorvoegsel("den"));

        List<Melding> meldingen = brby0126.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(1));

    }
}
