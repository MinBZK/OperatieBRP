/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
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
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonGeboorteGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonGeslachtsaanduidingGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonSamengesteldeNaamGroepModel;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.DatumUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 *
 */
public class BRBY0126Test {


    @Mock
    private PersoonRepository persoonRepository;

    @Mock
    private RelatieModel relatieModel;

    private RelatieBericht relatieBericht = new RelatieBericht();

    final List<BetrokkenheidBericht> betrokkenheden = new ArrayList<BetrokkenheidBericht>();

    final PersoonBericht kindBericht = new PersoonBericht();
    @InjectMocks
    private final BRBY0126 brby0126 = new BRBY0126();

    @Before
    public void setup() {
        initMocks(this);
        relatieBericht.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        BetrokkenheidBericht kindBetrokkenheidBericht = new BetrokkenheidBericht();
        kindBetrokkenheidBericht.setRol(SoortBetrokkenheid.KIND);
        kindBetrokkenheidBericht.setBetrokkene(kindBericht);
        betrokkenheden.add(kindBetrokkenheidBericht);

        relatieBericht.setBetrokkenheden(betrokkenheden);
    }


    @Test
    public void zouGeenMeldingMoetenGevenOmdatErGeenOuderBetrokkenhedenZijn() {
        List<Melding> meldingen = brby0126.executeer(relatieModel, relatieBericht, null);
        assertThat(meldingen.size(), is(0));
        verify(persoonRepository, never()).findByBurgerservicenummer(isA(Burgerservicenummer.class));
    }

    @Test
    public void zouGeenMeldingMoetenGevenOmdatOuderGeenKindHeeft() {
        betrokkenheden.add(createOuderBetrokkenheidBericht("123"));
        PersoonModel ouderModel = mock(PersoonModel.class);

        when(persoonRepository.findByBurgerservicenummer(isA(Burgerservicenummer.class))).thenReturn(ouderModel);
        when(ouderModel.getBetrokkenheden()).thenReturn(Collections.<BetrokkenheidModel>emptySet());

        List<Melding> meldingen = brby0126.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
        verify(persoonRepository, times(1)).findByBurgerservicenummer(isA(Burgerservicenummer.class));
        verify(ouderModel, times(2)).getBetrokkenheden();    // 2x vanwege null check
    }

    @Test
    public void zouGeenMeldingMoetenGevenOmdatGeboortedatumNaGeregistreerdKindIs() {
        betrokkenheden.add(createOuderBetrokkenheidBericht("123"));
        final PersoonModel eerderkindModel = mock(PersoonModel.class);
        createPathToEerderKindModel(eerderkindModel);

        // datum geboorte check
        final PersoonGeboorteGroepBericht persoonGeboorteGroepBericht = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroepBericht.setDatumGeboorte(DatumUtil.vandaag());
        kindBericht.setGeboorte(persoonGeboorteGroepBericht);
        final PersoonGeboorteGroepModel persoonGeboorteGroepModel = mock(PersoonGeboorteGroepModel.class);

        // deze worden aangepast in de verschillende testen
        when(eerderkindModel.getGeboorte()).thenReturn(persoonGeboorteGroepModel);
        when(persoonGeboorteGroepModel.getDatumGeboorte()).thenReturn(DatumUtil.morgen());

        List<Melding> meldingen = brby0126.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
        verify(eerderkindModel).getGeboorte();
    }


    @Test
    public void zouGeenMeldingMoetenGevenOmdatVoornamenAndersZijn() {
        betrokkenheden.add(createOuderBetrokkenheidBericht("123"));
        final PersoonModel eerderkindModel = mock(PersoonModel.class);
        createPathToEerderKindModel(eerderkindModel);

        // geboortedatum hetzelfde
        final PersoonGeboorteGroepBericht persoonGeboorteGroepBericht = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroepBericht.setDatumGeboorte(DatumUtil.vandaag());
        kindBericht.setGeboorte(persoonGeboorteGroepBericht);
        final PersoonGeboorteGroepModel persoonGeboorteGroepModel = mock(PersoonGeboorteGroepModel.class);

        // geslacht hetzelfde
        final PersoonGeslachtsaanduidingGroepBericht persoonGeslachtsaanduidingGroepBericht =
                new PersoonGeslachtsaanduidingGroepBericht();
        persoonGeslachtsaanduidingGroepBericht.setGeslachtsaanduiding(Geslachtsaanduiding.MAN);
        kindBericht.setGeslachtsaanduiding(persoonGeslachtsaanduidingGroepBericht);
        final PersoonGeslachtsaanduidingGroepModel persoonGeslachtsaanduidingGroepModel =
                mock(PersoonGeslachtsaanduidingGroepModel.class);

        final PersoonSamengesteldeNaamGroepBericht persoonSamengesteldeNaamGroepBericht =
                new PersoonSamengesteldeNaamGroepBericht();
        persoonSamengesteldeNaamGroepBericht.setGeslachtsnaam(new Geslachtsnaamcomponent("Janssen"));
        persoonSamengesteldeNaamGroepBericht.setScheidingsteken(new Scheidingsteken("/"));
        persoonSamengesteldeNaamGroepBericht.setVoornamen(new Voornaam("Piet"));
        persoonSamengesteldeNaamGroepBericht.setVoorvoegsel(new Voorvoegsel("den"));
        kindBericht.setSamengesteldeNaam(persoonSamengesteldeNaamGroepBericht);

        final PersoonSamengesteldeNaamGroepModel persoonSamengesteldeNaamGroepModel =
                mock(PersoonSamengesteldeNaamGroepModel.class);

        when(eerderkindModel.getGeboorte()).thenReturn(persoonGeboorteGroepModel);
        when(persoonGeboorteGroepModel.getDatumGeboorte()).thenReturn(DatumUtil.vandaag());
        when(eerderkindModel.getGeslachtsaanduiding()).thenReturn(persoonGeslachtsaanduidingGroepModel);
        when(persoonGeslachtsaanduidingGroepModel.getGeslachtsaanduiding()).thenReturn(Geslachtsaanduiding.MAN);
        when(eerderkindModel.getSamengesteldeNaam()).thenReturn(persoonSamengesteldeNaamGroepModel);
        when(persoonSamengesteldeNaamGroepModel.getGeslachtsnaam()).thenReturn(new Geslachtsnaamcomponent("Janssen"));
        when(persoonSamengesteldeNaamGroepModel.getScheidingsteken()).thenReturn(new Scheidingsteken("/"));
        when(persoonSamengesteldeNaamGroepModel.getVoornamen()).thenReturn(new Voornaam("Jan"));
        when(persoonSamengesteldeNaamGroepModel.getVoorvoegsel()).thenReturn(new Voorvoegsel("den"));

        List<Melding> meldingen = brby0126.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
    }

    @Test
    public void zouGeenMeldingMoetenGevenOmdatGeslachtsNamemAndersZijn() {
        betrokkenheden.add(createOuderBetrokkenheidBericht("123"));
        final PersoonModel eerderkindModel = mock(PersoonModel.class);
        createPathToEerderKindModel(eerderkindModel);

        // geboortedatum hetzelfde
        final PersoonGeboorteGroepBericht persoonGeboorteGroepBericht = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroepBericht.setDatumGeboorte(DatumUtil.vandaag());
        kindBericht.setGeboorte(persoonGeboorteGroepBericht);
        final PersoonGeboorteGroepModel persoonGeboorteGroepModel = mock(PersoonGeboorteGroepModel.class);

        // geslacht hetzelfde
        final PersoonGeslachtsaanduidingGroepBericht persoonGeslachtsaanduidingGroepBericht =
                new PersoonGeslachtsaanduidingGroepBericht();
        persoonGeslachtsaanduidingGroepBericht.setGeslachtsaanduiding(Geslachtsaanduiding.MAN);
        kindBericht.setGeslachtsaanduiding(persoonGeslachtsaanduidingGroepBericht);
        final PersoonGeslachtsaanduidingGroepModel persoonGeslachtsaanduidingGroepModel =
                mock(PersoonGeslachtsaanduidingGroepModel.class);

        final PersoonSamengesteldeNaamGroepBericht persoonSamengesteldeNaamGroepBericht =
                new PersoonSamengesteldeNaamGroepBericht();
        persoonSamengesteldeNaamGroepBericht.setGeslachtsnaam(new Geslachtsnaamcomponent("Janssen"));
        persoonSamengesteldeNaamGroepBericht.setScheidingsteken(new Scheidingsteken("/"));
        persoonSamengesteldeNaamGroepBericht.setVoornamen(new Voornaam("Jan"));
        persoonSamengesteldeNaamGroepBericht.setVoorvoegsel(new Voorvoegsel("den"));
        kindBericht.setSamengesteldeNaam(persoonSamengesteldeNaamGroepBericht);

        final PersoonSamengesteldeNaamGroepModel persoonSamengesteldeNaamGroepModel =
                mock(PersoonSamengesteldeNaamGroepModel.class);

        when(eerderkindModel.getGeboorte()).thenReturn(persoonGeboorteGroepModel);
        when(persoonGeboorteGroepModel.getDatumGeboorte()).thenReturn(DatumUtil.vandaag());
        when(eerderkindModel.getGeslachtsaanduiding()).thenReturn(persoonGeslachtsaanduidingGroepModel);
        when(persoonGeslachtsaanduidingGroepModel.getGeslachtsaanduiding()).thenReturn(Geslachtsaanduiding.MAN);
        when(eerderkindModel.getSamengesteldeNaam()).thenReturn(persoonSamengesteldeNaamGroepModel);
        when(persoonSamengesteldeNaamGroepModel.getGeslachtsnaam()).thenReturn(new Geslachtsnaamcomponent("Janssens"));
        when(persoonSamengesteldeNaamGroepModel.getScheidingsteken()).thenReturn(new Scheidingsteken("/"));
        when(persoonSamengesteldeNaamGroepModel.getVoornamen()).thenReturn(new Voornaam("Jan"));
        when(persoonSamengesteldeNaamGroepModel.getVoorvoegsel()).thenReturn(new Voorvoegsel("den"));

        List<Melding> meldingen = brby0126.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
    }


    @Test
    public void zouGeenMeldingMoetenGevenOmdatScheidingsTekensAndersZijn() {
        betrokkenheden.add(createOuderBetrokkenheidBericht("123"));
        final PersoonModel eerderkindModel = mock(PersoonModel.class);
        createPathToEerderKindModel(eerderkindModel);

        // geboortedatum hetzelfde
        final PersoonGeboorteGroepBericht persoonGeboorteGroepBericht = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroepBericht.setDatumGeboorte(DatumUtil.vandaag());
        kindBericht.setGeboorte(persoonGeboorteGroepBericht);
        final PersoonGeboorteGroepModel persoonGeboorteGroepModel = mock(PersoonGeboorteGroepModel.class);

        // geslacht hetzelfde
        final PersoonGeslachtsaanduidingGroepBericht persoonGeslachtsaanduidingGroepBericht =
                new PersoonGeslachtsaanduidingGroepBericht();
        persoonGeslachtsaanduidingGroepBericht.setGeslachtsaanduiding(Geslachtsaanduiding.MAN);
        kindBericht.setGeslachtsaanduiding(persoonGeslachtsaanduidingGroepBericht);
        final PersoonGeslachtsaanduidingGroepModel persoonGeslachtsaanduidingGroepModel =
                mock(PersoonGeslachtsaanduidingGroepModel.class);

        final PersoonSamengesteldeNaamGroepBericht persoonSamengesteldeNaamGroepBericht =
                new PersoonSamengesteldeNaamGroepBericht();
        persoonSamengesteldeNaamGroepBericht.setGeslachtsnaam(new Geslachtsnaamcomponent("Janssen"));
        persoonSamengesteldeNaamGroepBericht.setScheidingsteken(new Scheidingsteken("/"));
        persoonSamengesteldeNaamGroepBericht.setVoornamen(new Voornaam("Jan"));
        persoonSamengesteldeNaamGroepBericht.setVoorvoegsel(new Voorvoegsel("den"));
        kindBericht.setSamengesteldeNaam(persoonSamengesteldeNaamGroepBericht);

        final PersoonSamengesteldeNaamGroepModel persoonSamengesteldeNaamGroepModel =
                mock(PersoonSamengesteldeNaamGroepModel.class);

        when(eerderkindModel.getGeboorte()).thenReturn(persoonGeboorteGroepModel);
        when(persoonGeboorteGroepModel.getDatumGeboorte()).thenReturn(DatumUtil.vandaag());
        when(eerderkindModel.getGeslachtsaanduiding()).thenReturn(persoonGeslachtsaanduidingGroepModel);
        when(persoonGeslachtsaanduidingGroepModel.getGeslachtsaanduiding()).thenReturn(Geslachtsaanduiding.MAN);
        when(eerderkindModel.getSamengesteldeNaam()).thenReturn(persoonSamengesteldeNaamGroepModel);
        when(persoonSamengesteldeNaamGroepModel.getGeslachtsnaam()).thenReturn(new Geslachtsnaamcomponent("Janssen"));
        when(persoonSamengesteldeNaamGroepModel.getScheidingsteken()).thenReturn(new Scheidingsteken("-"));
        when(persoonSamengesteldeNaamGroepModel.getVoornamen()).thenReturn(new Voornaam("Jan"));
        when(persoonSamengesteldeNaamGroepModel.getVoorvoegsel()).thenReturn(new Voorvoegsel("den"));

        List<Melding> meldingen = brby0126.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
    }

    @Test
    public void zouGeenMeldingMoetenGevenOmdatVoorvoegselsAndersZijn() {
        betrokkenheden.add(createOuderBetrokkenheidBericht("123"));
        final PersoonModel eerderkindModel = mock(PersoonModel.class);
        createPathToEerderKindModel(eerderkindModel);

        // geboortedatum hetzelfde
        final PersoonGeboorteGroepBericht persoonGeboorteGroepBericht = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroepBericht.setDatumGeboorte(DatumUtil.vandaag());
        kindBericht.setGeboorte(persoonGeboorteGroepBericht);
        final PersoonGeboorteGroepModel persoonGeboorteGroepModel = mock(PersoonGeboorteGroepModel.class);

        // geslacht hetzelfde
        final PersoonGeslachtsaanduidingGroepBericht persoonGeslachtsaanduidingGroepBericht =
                new PersoonGeslachtsaanduidingGroepBericht();
        persoonGeslachtsaanduidingGroepBericht.setGeslachtsaanduiding(Geslachtsaanduiding.MAN);
        kindBericht.setGeslachtsaanduiding(persoonGeslachtsaanduidingGroepBericht);
        final PersoonGeslachtsaanduidingGroepModel persoonGeslachtsaanduidingGroepModel =
                mock(PersoonGeslachtsaanduidingGroepModel.class);

        final PersoonSamengesteldeNaamGroepBericht persoonSamengesteldeNaamGroepBericht =
                new PersoonSamengesteldeNaamGroepBericht();
        persoonSamengesteldeNaamGroepBericht.setGeslachtsnaam(new Geslachtsnaamcomponent("Janssen"));
        persoonSamengesteldeNaamGroepBericht.setScheidingsteken(new Scheidingsteken("/"));
        persoonSamengesteldeNaamGroepBericht.setVoornamen(new Voornaam("Jan"));
        persoonSamengesteldeNaamGroepBericht.setVoorvoegsel(new Voorvoegsel("den"));
        kindBericht.setSamengesteldeNaam(persoonSamengesteldeNaamGroepBericht);

        final PersoonSamengesteldeNaamGroepModel persoonSamengesteldeNaamGroepModel =
                mock(PersoonSamengesteldeNaamGroepModel.class);

        when(eerderkindModel.getGeboorte()).thenReturn(persoonGeboorteGroepModel);
        when(persoonGeboorteGroepModel.getDatumGeboorte()).thenReturn(DatumUtil.vandaag());
        when(eerderkindModel.getGeslachtsaanduiding()).thenReturn(persoonGeslachtsaanduidingGroepModel);
        when(persoonGeslachtsaanduidingGroepModel.getGeslachtsaanduiding()).thenReturn(Geslachtsaanduiding.MAN);
        when(eerderkindModel.getSamengesteldeNaam()).thenReturn(persoonSamengesteldeNaamGroepModel);
        when(persoonSamengesteldeNaamGroepModel.getGeslachtsnaam()).thenReturn(new Geslachtsnaamcomponent("Janssen"));
        when(persoonSamengesteldeNaamGroepModel.getScheidingsteken()).thenReturn(new Scheidingsteken("/"));
        when(persoonSamengesteldeNaamGroepModel.getVoornamen()).thenReturn(new Voornaam("Jan"));
        when(persoonSamengesteldeNaamGroepModel.getVoorvoegsel()).thenReturn(new Voorvoegsel("op den"));

        List<Melding> meldingen = brby0126.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
    }

    @Test
    public void zouGeenMeldingMoetenGevenOmdatGeslachtenAndersZijn() {
        betrokkenheden.add(createOuderBetrokkenheidBericht("123"));
        final PersoonModel eerderkindModel = mock(PersoonModel.class);
        createPathToEerderKindModel(eerderkindModel);

        // geboortedatum hetzelfde
        final PersoonGeboorteGroepBericht persoonGeboorteGroepBericht = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroepBericht.setDatumGeboorte(DatumUtil.vandaag());
        kindBericht.setGeboorte(persoonGeboorteGroepBericht);
        final PersoonGeboorteGroepModel persoonGeboorteGroepModel = mock(PersoonGeboorteGroepModel.class);

        // geslacht hetzelfde
        final PersoonGeslachtsaanduidingGroepBericht persoonGeslachtsaanduidingGroepBericht =
                new PersoonGeslachtsaanduidingGroepBericht();
        persoonGeslachtsaanduidingGroepBericht.setGeslachtsaanduiding(Geslachtsaanduiding.MAN);
        kindBericht.setGeslachtsaanduiding(persoonGeslachtsaanduidingGroepBericht);
        final PersoonGeslachtsaanduidingGroepModel persoonGeslachtsaanduidingGroepModel =
                mock(PersoonGeslachtsaanduidingGroepModel.class);

        final PersoonSamengesteldeNaamGroepBericht persoonSamengesteldeNaamGroepBericht =
                new PersoonSamengesteldeNaamGroepBericht();
        persoonSamengesteldeNaamGroepBericht.setGeslachtsnaam(new Geslachtsnaamcomponent("Janssen"));
        persoonSamengesteldeNaamGroepBericht.setScheidingsteken(new Scheidingsteken("/"));
        persoonSamengesteldeNaamGroepBericht.setVoornamen(new Voornaam("Jan"));
        persoonSamengesteldeNaamGroepBericht.setVoorvoegsel(new Voorvoegsel("den"));
        kindBericht.setSamengesteldeNaam(persoonSamengesteldeNaamGroepBericht);

        final PersoonSamengesteldeNaamGroepModel persoonSamengesteldeNaamGroepModel =
                mock(PersoonSamengesteldeNaamGroepModel.class);

        when(eerderkindModel.getGeboorte()).thenReturn(persoonGeboorteGroepModel);
        when(persoonGeboorteGroepModel.getDatumGeboorte()).thenReturn(DatumUtil.vandaag());
        when(eerderkindModel.getGeslachtsaanduiding()).thenReturn(persoonGeslachtsaanduidingGroepModel);
        when(persoonGeslachtsaanduidingGroepModel.getGeslachtsaanduiding()).thenReturn(Geslachtsaanduiding.VROUW);
        when(eerderkindModel.getSamengesteldeNaam()).thenReturn(persoonSamengesteldeNaamGroepModel);
        when(persoonSamengesteldeNaamGroepModel.getGeslachtsnaam()).thenReturn(new Geslachtsnaamcomponent("Janssen"));
        when(persoonSamengesteldeNaamGroepModel.getScheidingsteken()).thenReturn(new Scheidingsteken("/"));
        when(persoonSamengesteldeNaamGroepModel.getVoornamen()).thenReturn(new Voornaam("Jan"));
        when(persoonSamengesteldeNaamGroepModel.getVoorvoegsel()).thenReturn(new Voorvoegsel("den"));

        List<Melding> meldingen = brby0126.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
    }

    @Test
    public void zouMeldingMoetenGevenOmdatGeboortedatumEnSamengesteldeNaamZelfdeZijn() {
        betrokkenheden.add(createOuderBetrokkenheidBericht("123"));
        final PersoonModel eerderkindModel = mock(PersoonModel.class);
        createPathToEerderKindModel(eerderkindModel);

        // geboortedatum hetzelfde
        final PersoonGeboorteGroepBericht persoonGeboorteGroepBericht = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroepBericht.setDatumGeboorte(DatumUtil.vandaag());
        kindBericht.setGeboorte(persoonGeboorteGroepBericht);
        final PersoonGeboorteGroepModel persoonGeboorteGroepModel = mock(PersoonGeboorteGroepModel.class);

        // geslacht hetzelfde
        final PersoonGeslachtsaanduidingGroepBericht persoonGeslachtsaanduidingGroepBericht =
                new PersoonGeslachtsaanduidingGroepBericht();
        persoonGeslachtsaanduidingGroepBericht.setGeslachtsaanduiding(Geslachtsaanduiding.MAN);
        kindBericht.setGeslachtsaanduiding(persoonGeslachtsaanduidingGroepBericht);
        final PersoonGeslachtsaanduidingGroepModel persoonGeslachtsaanduidingGroepModel =
                mock(PersoonGeslachtsaanduidingGroepModel.class);

        final PersoonSamengesteldeNaamGroepBericht persoonSamengesteldeNaamGroepBericht =
                new PersoonSamengesteldeNaamGroepBericht();
        persoonSamengesteldeNaamGroepBericht.setGeslachtsnaam(new Geslachtsnaamcomponent("Janssen"));
        persoonSamengesteldeNaamGroepBericht.setScheidingsteken(new Scheidingsteken("/"));
        persoonSamengesteldeNaamGroepBericht.setVoornamen(new Voornaam("Jan"));
        persoonSamengesteldeNaamGroepBericht.setVoorvoegsel(new Voorvoegsel("den"));
        kindBericht.setSamengesteldeNaam(persoonSamengesteldeNaamGroepBericht);

        final PersoonSamengesteldeNaamGroepModel persoonSamengesteldeNaamGroepModel =
                mock(PersoonSamengesteldeNaamGroepModel.class);

        when(eerderkindModel.getGeboorte()).thenReturn(persoonGeboorteGroepModel);
        when(persoonGeboorteGroepModel.getDatumGeboorte()).thenReturn(DatumUtil.vandaag());
        when(eerderkindModel.getGeslachtsaanduiding()).thenReturn(persoonGeslachtsaanduidingGroepModel);
        when(persoonGeslachtsaanduidingGroepModel.getGeslachtsaanduiding()).thenReturn(Geslachtsaanduiding.MAN);
        when(eerderkindModel.getSamengesteldeNaam()).thenReturn(persoonSamengesteldeNaamGroepModel);
        when(persoonSamengesteldeNaamGroepModel.getGeslachtsnaam()).thenReturn(new Geslachtsnaamcomponent("Janssen"));
        when(persoonSamengesteldeNaamGroepModel.getScheidingsteken()).thenReturn(new Scheidingsteken("/"));
        when(persoonSamengesteldeNaamGroepModel.getVoornamen()).thenReturn(new Voornaam("Jan"));
        when(persoonSamengesteldeNaamGroepModel.getVoorvoegsel()).thenReturn(new Voorvoegsel("den"));

        List<Melding> meldingen = brby0126.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(1));
    }

    private void createPathToEerderKindModel(PersoonModel eerderkindModel) {
        final RelatieModel ouderEerderKindRelatieModel = mock(RelatieModel.class);
        final BetrokkenheidModel betrokkenheidModel = mock(BetrokkenheidModel.class);
        final PersoonModel ouderModel = mock(PersoonModel.class);
        final Set<BetrokkenheidModel> ouderBetrokkenhedenModellen = new HashSet<BetrokkenheidModel>();
        ouderBetrokkenhedenModellen.add(betrokkenheidModel);
        final Set<BetrokkenheidModel> ouderEerderKindRelatieBetrokkenhedenModellen = new HashSet<BetrokkenheidModel>();
        final BetrokkenheidModel eerderKindBetrokkenheid = mock(BetrokkenheidModel.class);
        ouderEerderKindRelatieBetrokkenhedenModellen.add(eerderKindBetrokkenheid);

        // RelatieUtils call
        when(persoonRepository.findByBurgerservicenummer(isA(Burgerservicenummer.class))).thenReturn(ouderModel);
        when(ouderModel.getBetrokkenheden()).thenReturn(ouderBetrokkenhedenModellen);
        when(betrokkenheidModel.getRelatie()).thenReturn(ouderEerderKindRelatieModel);
        when(ouderEerderKindRelatieModel.getSoort()).thenReturn(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        when(betrokkenheidModel.getRol()).thenReturn(SoortBetrokkenheid.OUDER);
        when(ouderEerderKindRelatieModel.getBetrokkenheden()).thenReturn(ouderEerderKindRelatieBetrokkenhedenModellen);
        when(eerderKindBetrokkenheid.getRol()).thenReturn(SoortBetrokkenheid.KIND);
        when(eerderKindBetrokkenheid.getBetrokkene()).thenReturn(eerderkindModel);
    }


    private BetrokkenheidBericht createOuderBetrokkenheidBericht(final String bsn) {
        final BetrokkenheidBericht ouderBetrokkenheidBericht = new BetrokkenheidBericht();
        ouderBetrokkenheidBericht.setRol(SoortBetrokkenheid.OUDER);

        final PersoonBericht ouder = new PersoonBericht();
        final PersoonIdentificatienummersGroepBericht identificatieNummers =
                new PersoonIdentificatienummersGroepBericht();
        final Burgerservicenummer burgerservicenummer = new Burgerservicenummer("bsn");
        identificatieNummers.setBurgerservicenummer(burgerservicenummer);
        ouder.setIdentificatienummers(identificatieNummers);
        ouderBetrokkenheidBericht.setBetrokkene(ouder);

        return ouderBetrokkenheidBericht;
    }


}
