/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dashboard;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.BerichtStuurgegevens;
import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.dashboard.GeboorteBerichtRequest;
import nl.bzk.brp.model.dashboard.MeldingSoort;
import nl.bzk.brp.model.dashboard.VerwerkingStatus;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.PersoonVoornaam;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonGeslachtsAanduiding;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;


public class GeboorteNotificatorTest {

    private GeboorteNotificator notificator;

    @Mock
    private RestTemplate        restTemplate;

    @Mock
    private PersoonRepository   persoonRepository;

    @Before
    public void init() throws URISyntaxException {
        MockitoAnnotations.initMocks(this);
        notificator = new GeboorteNotificator();
        notificator
                .setDashboardUri(new URI("http://localhost:8080/brp-dashboard/service/berichten/opslaan/geboorte"));
        ReflectionTestUtils.setField(notificator, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(notificator, "persoonRepository", persoonRepository);
    }

    private Set<Betrokkenheid> creeerBetrokkenhedenVoorBericht(final BijhoudingsBericht bericht) {
        List<BRPActie> brpActies = new ArrayList<BRPActie>();
        BRPActie brpActie = new BRPActie();
        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        Relatie familie = new Relatie();
        Set<Betrokkenheid> betrokkenheden = new HashSet<Betrokkenheid>();

        bericht.setBrpActies(brpActies);
        brpActies.add(brpActie);
        brpActie.setRootObjecten(rootObjecten);
        rootObjecten.add(familie);
        familie.setBetrokkenheden(betrokkenheden);

        return betrokkenheden;
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGeboorteBericht() throws Exception {
        InschrijvingGeboorteBericht bericht = new InschrijvingGeboorteBericht();
        BerichtStuurgegevens berichtStuurgegevens = new BerichtStuurgegevens();
        berichtStuurgegevens.setApplicatie("Digimeente");
        berichtStuurgegevens.setPrevalidatieBericht(true);
        bericht.setBerichtStuurgegevens(berichtStuurgegevens);
        Set<Betrokkenheid> betrokkenheden = creeerBetrokkenhedenVoorBericht(bericht);

        Persoon nieuwgeborene = new Persoon();
        PersoonIdentificatienummers identificatienummers = new PersoonIdentificatienummers();
        identificatienummers.setBurgerservicenummer("123234345");
        nieuwgeborene.setIdentificatienummers(identificatienummers);
        PersoonGeboorte persoonGeboorte = new PersoonGeboorte();
        persoonGeboorte.setDatumGeboorte(19660417);
        Partij gemeenteGeboorte = new Partij();
        gemeenteGeboorte.setGemeentecode("030");
        gemeenteGeboorte.setNaam("Utrecht");
        persoonGeboorte.setGemeenteGeboorte(gemeenteGeboorte);
        nieuwgeborene.setGeboorte(persoonGeboorte);
        PersoonVoornaam persoonVoornaam = new PersoonVoornaam();
        persoonVoornaam.setNaam("Ab");
        nieuwgeborene.voegPersoonVoornaamToe(persoonVoornaam);
        PersoonGeslachtsnaamcomponent geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent();
        geslachtsnaamcomponent.setVoorvoegsel("de");
        geslachtsnaamcomponent.setNaam("Vis");
        nieuwgeborene.voegGeslachtsnaamcomponentToe(geslachtsnaamcomponent);
        Set<PersoonAdres> adressen = new HashSet<PersoonAdres>();
        PersoonAdres adres = new PersoonAdres();
        adres.setDatumAanvangAdreshouding(20120515);
        Partij gemeente = new Partij();
        gemeente.setGemeentecode("365");
        gemeente.setNaam("Zeist");
        adres.setGemeente(gemeente);
        adres.setAfgekorteNaamOpenbareRuimte("Brink");
        adres.setHuisnummer("11");
        adres.setHuisnummertoevoeging("B");
        Plaats woonplaats = new Plaats();
        woonplaats.setWoonplaatscode("747");
        woonplaats.setNaam("Vollenhoven");
        adres.setWoonplaats(woonplaats);
        adressen.add(adres);
        nieuwgeborene.setAdressen(adressen);
        Betrokkenheid kind = new Betrokkenheid();
        kind.setSoortBetrokkenheid(SoortBetrokkenheid.KIND);
        kind.setBetrokkene(nieuwgeborene);
        betrokkenheden.add(kind);

        Persoon ouder1 = new Persoon();
        PersoonIdentificatienummers identificatienummers1 = new PersoonIdentificatienummers();
        identificatienummers1.setBurgerservicenummer("321432543");
        ouder1.setIdentificatienummers(identificatienummers1);
        PersoonGeboorte persoonGeboorte1 = new PersoonGeboorte();
        persoonGeboorte1.setDatumGeboorte(19660417);
        Partij gemeenteGeboorte1 = new Partij();
        gemeenteGeboorte1.setGemeentecode("030");
        gemeenteGeboorte1.setNaam("Utrecht");
        persoonGeboorte1.setGemeenteGeboorte(gemeenteGeboorte1);
        ouder1.setGeboorte(persoonGeboorte1);
        PersoonVoornaam persoonVoornaam1 = new PersoonVoornaam();
        persoonVoornaam1.setNaam("Ab");
        ouder1.voegPersoonVoornaamToe(persoonVoornaam1);
        PersoonGeslachtsnaamcomponent geslachtsnaamcomponent1 = new PersoonGeslachtsnaamcomponent();
        geslachtsnaamcomponent1.setVoorvoegsel("de");
        geslachtsnaamcomponent1.setNaam("Vis");
        ouder1.voegGeslachtsnaamcomponentToe(geslachtsnaamcomponent1);
        Set<PersoonAdres> adressen1 = new HashSet<PersoonAdres>();
        PersoonAdres adres1 = new PersoonAdres();
        adres1.setDatumAanvangAdreshouding(20120515);
        Partij gemeente1 = new Partij();
        gemeente1.setGemeentecode("365");
        gemeente1.setNaam("Zeist");
        adres1.setGemeente(gemeente1);
        adres1.setAfgekorteNaamOpenbareRuimte("Brink");
        adres1.setHuisnummer("11");
        adres1.setHuisnummertoevoeging("B");
        Plaats woonplaats1 = new Plaats();
        woonplaats1.setWoonplaatscode("747");
        woonplaats1.setNaam("Vollenhoven");
        adres1.setWoonplaats(woonplaats1);
        adressen1.add(adres1);
        ouder1.setAdressen(adressen1);
        Betrokkenheid ouderschap1 = new Betrokkenheid();
        ouderschap1.setSoortBetrokkenheid(SoortBetrokkenheid.OUDER);
        ouderschap1.setBetrokkene(ouder1);
        betrokkenheden.add(ouderschap1);

        Persoon ouder2 = new Persoon();
        PersoonIdentificatienummers identificatienummers2 = new PersoonIdentificatienummers();
        identificatienummers2.setBurgerservicenummer("987654321");
        ouder2.setIdentificatienummers(identificatienummers2);
        PersoonGeboorte persoonGeboorte2 = new PersoonGeboorte();
        persoonGeboorte2.setDatumGeboorte(19660417);
        Partij gemeenteGeboorte2 = new Partij();
        gemeenteGeboorte2.setGemeentecode("030");
        gemeenteGeboorte2.setNaam("Utrecht");
        persoonGeboorte2.setGemeenteGeboorte(gemeenteGeboorte2);
        ouder2.setGeboorte(persoonGeboorte2);
        PersoonVoornaam persoonVoornaam2 = new PersoonVoornaam();
        persoonVoornaam2.setNaam("Ab");
        ouder2.voegPersoonVoornaamToe(persoonVoornaam2);
        PersoonGeslachtsnaamcomponent geslachtsnaamcomponent2 = new PersoonGeslachtsnaamcomponent();
        geslachtsnaamcomponent2.setVoorvoegsel("de");
        geslachtsnaamcomponent2.setNaam("Vis");
        ouder2.voegGeslachtsnaamcomponentToe(geslachtsnaamcomponent2);
        Set<PersoonAdres> adressen2 = new HashSet<PersoonAdres>();
        PersoonAdres adres2 = new PersoonAdres();
        adres2.setDatumAanvangAdreshouding(20120515);
        Partij gemeente2 = new Partij();
        gemeente2.setGemeentecode("365");
        gemeente2.setNaam("Zeist");
        adres2.setGemeente(gemeente2);
        adres2.setAfgekorteNaamOpenbareRuimte("Brink");
        adres2.setHuisnummer("11");
        adres2.setHuisnummertoevoeging("B");
        Plaats woonplaats2 = new Plaats();
        woonplaats2.setWoonplaatscode("747");
        woonplaats2.setNaam("Vollenhoven");
        adres2.setWoonplaats(woonplaats2);
        adressen2.add(adres2);
        ouder2.setAdressen(adressen2);
        Betrokkenheid ouderschap2 = new Betrokkenheid();
        ouderschap2.setSoortBetrokkenheid(SoortBetrokkenheid.OUDER);
        ouderschap2.setBetrokkene(ouder2);
        betrokkenheden.add(ouderschap2);

        BerichtenIds berichtenIds = new BerichtenIds(1L, 2L);
        Integer authenticatieMiddelId = 3;
        Partij partij = new Partij();
        partij.setGemeentecode("321");
        partij.setNaam("Ridderkerk");
        BerichtContext context = new BerichtContext(berichtenIds, authenticatieMiddelId, partij);

        BerichtResultaat resultaat = new BerichtResultaat(null);

        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer("123234345")).thenReturn(nieuwgeborene);
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer("321432543")).thenReturn(ouder1);
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer("987654321")).thenReturn(ouder2);

        // test
        notificator.notificeerDashboard(bericht, context, resultaat);

        ArgumentCaptor<URI> uriArgument = ArgumentCaptor.forClass(URI.class);
        ArgumentCaptor<GeboorteBerichtRequest> berichtArgument = ArgumentCaptor.forClass(GeboorteBerichtRequest.class);

        @SuppressWarnings("rawtypes")
        ArgumentCaptor<Class> typeArgument = ArgumentCaptor.forClass(Class.class);

        Mockito.verify(restTemplate).postForEntity(uriArgument.capture(), berichtArgument.capture(),
                typeArgument.capture());

        Assert.assertEquals("http://localhost:8080/brp-dashboard/service/berichten/opslaan/geboorte", uriArgument
                .getValue().toASCIIString());

        Assert.assertNotNull(berichtArgument.getValue().getKenmerken());
        Assert.assertEquals("321", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getCode());
        Assert.assertEquals("Ridderkerk", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getNaam());
        Assert.assertEquals("Digimeente", berichtArgument.getValue().getKenmerken().getBurgerZakenModuleNaam());
        Assert.assertTrue(berichtArgument.getValue().getKenmerken().isPrevalidatie());

        Assert.assertNotNull(berichtArgument.getValue().getVerwerking());
        Assert.assertNotNull(berichtArgument.getValue().getVerwerking().getVerwerkingsmoment());
        Assert.assertEquals(VerwerkingStatus.GESLAAGD, berichtArgument.getValue().getVerwerking().getStatus());

        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene());
        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene().getBsn());
        Assert.assertEquals(123234345L, berichtArgument.getValue().getNieuwgeborene().getBsn().longValue());
        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene().getGeslachtsnaamcomponenten());

        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene().getVoornamen());
        Assert.assertEquals("Ab", berichtArgument.getValue().getNieuwgeborene().getVoornamen().get(0));

        // TODO friso: test toevoegen voor persoon met meerdere voor- en/of achternamen

        Assert.assertEquals("de", berichtArgument.getValue().getNieuwgeborene().getGeslachtsnaamcomponenten().get(0)
                .getVoorvoegsel());
        Assert.assertEquals("Vis", berichtArgument.getValue().getNieuwgeborene().getGeslachtsnaamcomponenten().get(0)
                .getNaam());

        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene());
        Assert.assertEquals(20120515, berichtArgument.getValue().getAdresNieuwgeborene().getDatumAanvangAdreshouding());
        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene().getGemeente());
        Assert.assertEquals("365", berichtArgument.getValue().getAdresNieuwgeborene().getGemeente().getCode());
        Assert.assertEquals("Zeist", berichtArgument.getValue().getAdresNieuwgeborene().getGemeente().getNaam());
        Assert.assertEquals("Brink", berichtArgument.getValue().getAdresNieuwgeborene().getStraat());
        Assert.assertEquals("11", berichtArgument.getValue().getAdresNieuwgeborene().getHuisnummer());
        Assert.assertEquals("B", berichtArgument.getValue().getAdresNieuwgeborene().getHuisnummertoevoeging());
        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene().getPlaats());
        Assert.assertEquals("747", berichtArgument.getValue().getAdresNieuwgeborene().getPlaats().getCode());
        Assert.assertEquals("Vollenhoven", berichtArgument.getValue().getAdresNieuwgeborene().getPlaats().getNaam());

        Set<Long> ouderBsns = new HashSet<Long>();
        ouderBsns.add(321432543L);
        ouderBsns.add(987654321L);

        Assert.assertNotNull(berichtArgument.getValue().getOuder1());
        Assert.assertNotNull(berichtArgument.getValue().getOuder2());
        Assert.assertNotNull(berichtArgument.getValue().getOuder1().getBsn());
        Assert.assertNotNull(berichtArgument.getValue().getOuder2().getBsn());
        Long ouder1bsn = berichtArgument.getValue().getOuder1().getBsn();
        Long ouder2bsn = berichtArgument.getValue().getOuder2().getBsn();
        Assert.assertTrue(ouderBsns.contains(ouder1bsn));
        Assert.assertTrue(ouderBsns.contains(ouder1bsn));
        Assert.assertFalse(ouder1bsn.equals(ouder2bsn));

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGeboorteBerichtZonderOudersOfAdressen() throws Exception {
        InschrijvingGeboorteBericht bericht = new InschrijvingGeboorteBericht();
        BerichtStuurgegevens berichtStuurgegevens = new BerichtStuurgegevens();
        berichtStuurgegevens.setApplicatie("Digimeente");
        bericht.setBerichtStuurgegevens(berichtStuurgegevens);
        Set<Betrokkenheid> betrokkenheden = creeerBetrokkenhedenVoorBericht(bericht);

        Persoon nieuwgeborene = new Persoon();
        PersoonIdentificatienummers identificatienummers = new PersoonIdentificatienummers();
        identificatienummers.setBurgerservicenummer("123234345");
        nieuwgeborene.setIdentificatienummers(identificatienummers);
        PersoonGeboorte persoonGeboorte = new PersoonGeboorte();
        persoonGeboorte.setDatumGeboorte(19660417);
        Partij gemeenteGeboorte = new Partij();
        gemeenteGeboorte.setGemeentecode("Utrecht");
        persoonGeboorte.setGemeenteGeboorte(gemeenteGeboorte);
        nieuwgeborene.setGeboorte(persoonGeboorte);
        PersoonVoornaam persoonVoornaam = new PersoonVoornaam();
        persoonVoornaam.setNaam("Ab");
        nieuwgeborene.voegPersoonVoornaamToe(persoonVoornaam);
        PersoonGeslachtsnaamcomponent geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent();
        geslachtsnaamcomponent.setVoorvoegsel("de");
        geslachtsnaamcomponent.setNaam("Vis");
        nieuwgeborene.voegGeslachtsnaamcomponentToe(geslachtsnaamcomponent);
        Set<PersoonAdres> adressen = new HashSet<PersoonAdres>();
        nieuwgeborene.setAdressen(adressen);
        Betrokkenheid kind = new Betrokkenheid();
        kind.setSoortBetrokkenheid(SoortBetrokkenheid.KIND);
        kind.setBetrokkene(nieuwgeborene);
        betrokkenheden.add(kind);

        BerichtenIds berichtenIds = new BerichtenIds(1L, 2L);
        Integer authenticatieMiddelId = 3;
        Partij partij = new Partij();
        partij.setGemeentecode("321");
        partij.setNaam("Hoorn");
        BerichtContext context = new BerichtContext(berichtenIds, authenticatieMiddelId, partij);

        BerichtResultaat resultaat = new BerichtResultaat(null);

        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer("123234345")).thenReturn(nieuwgeborene);

        // test
        notificator.notificeerDashboard(bericht, context, resultaat);

        ArgumentCaptor<URI> uriArgument = ArgumentCaptor.forClass(URI.class);
        ArgumentCaptor<GeboorteBerichtRequest> berichtArgument = ArgumentCaptor.forClass(GeboorteBerichtRequest.class);

        @SuppressWarnings("rawtypes")
        ArgumentCaptor<Class> typeArgument = ArgumentCaptor.forClass(Class.class);

        Mockito.verify(restTemplate).postForEntity(uriArgument.capture(), berichtArgument.capture(),
                typeArgument.capture());

        Assert.assertEquals("http://localhost:8080/brp-dashboard/service/berichten/opslaan/geboorte", uriArgument
                .getValue().toASCIIString());

        Assert.assertNotNull(berichtArgument.getValue().getKenmerken());
        Assert.assertEquals("321", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getCode());
        Assert.assertEquals("Hoorn", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getNaam());
        Assert.assertEquals("Digimeente", berichtArgument.getValue().getKenmerken().getBurgerZakenModuleNaam());
        Assert.assertFalse(berichtArgument.getValue().getKenmerken().isPrevalidatie());

        Assert.assertNotNull(berichtArgument.getValue().getVerwerking());
        Assert.assertNotNull(berichtArgument.getValue().getVerwerking().getVerwerkingsmoment());
        Assert.assertEquals(VerwerkingStatus.GESLAAGD, berichtArgument.getValue().getVerwerking().getStatus());

        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene());
        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene().getBsn());
        Assert.assertEquals(123234345L, berichtArgument.getValue().getNieuwgeborene().getBsn().longValue());
        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene().getGeslachtsnaamcomponenten());

        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene().getVoornamen());
        Assert.assertEquals("Ab", berichtArgument.getValue().getNieuwgeborene().getVoornamen().get(0));

        // TODO friso: test toevoegen voor persoon met meerdere voor- en/of achternamen

        Assert.assertEquals("de", berichtArgument.getValue().getNieuwgeborene().getGeslachtsnaamcomponenten().get(0)
                .getVoorvoegsel());
        Assert.assertEquals("Vis", berichtArgument.getValue().getNieuwgeborene().getGeslachtsnaamcomponenten().get(0)
                .getNaam());

        Assert.assertNull(berichtArgument.getValue().getAdresNieuwgeborene());

        Assert.assertNull(berichtArgument.getValue().getOuder1());
        Assert.assertNull(berichtArgument.getValue().getOuder2());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGeboorteBerichtZonderKindAdres() throws Exception {
        InschrijvingGeboorteBericht bericht = new InschrijvingGeboorteBericht();
        BerichtStuurgegevens berichtStuurgegevens = new BerichtStuurgegevens();
        berichtStuurgegevens.setApplicatie("Digimeente");
        bericht.setBerichtStuurgegevens(berichtStuurgegevens);
        Set<Betrokkenheid> betrokkenheden = creeerBetrokkenhedenVoorBericht(bericht);

        Persoon nieuwgeborene = new Persoon();
        PersoonIdentificatienummers identificatienummers = new PersoonIdentificatienummers();
        identificatienummers.setBurgerservicenummer("123234345");
        nieuwgeborene.setIdentificatienummers(identificatienummers);
        PersoonGeboorte persoonGeboorte = new PersoonGeboorte();
        persoonGeboorte.setDatumGeboorte(19660417);
        Partij gemeenteGeboorte = new Partij();
        gemeenteGeboorte.setGemeentecode("030");
        gemeenteGeboorte.setNaam("Utrecht");
        persoonGeboorte.setGemeenteGeboorte(gemeenteGeboorte);
        nieuwgeborene.setGeboorte(persoonGeboorte);
        PersoonVoornaam persoonVoornaam = new PersoonVoornaam();
        persoonVoornaam.setNaam("Ab");
        nieuwgeborene.voegPersoonVoornaamToe(persoonVoornaam);
        PersoonGeslachtsnaamcomponent geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent();
        geslachtsnaamcomponent.setVoorvoegsel("de");
        geslachtsnaamcomponent.setNaam("Vis");
        nieuwgeborene.voegGeslachtsnaamcomponentToe(geslachtsnaamcomponent);
        Set<PersoonAdres> adressen = new HashSet<PersoonAdres>();
        nieuwgeborene.setAdressen(adressen);
        Betrokkenheid kind = new Betrokkenheid();
        kind.setSoortBetrokkenheid(SoortBetrokkenheid.KIND);
        kind.setBetrokkene(nieuwgeborene);
        betrokkenheden.add(kind);

        Persoon ouder1 = new Persoon();
        PersoonIdentificatienummers identificatienummers1 = new PersoonIdentificatienummers();
        identificatienummers1.setBurgerservicenummer("321432543");
        ouder1.setIdentificatienummers(identificatienummers1);
        PersoonGeboorte persoonGeboorte1 = new PersoonGeboorte();
        persoonGeboorte1.setDatumGeboorte(19660417);
        Partij gemeenteGeboorte1 = new Partij();
        gemeenteGeboorte1.setGemeentecode("030");
        gemeenteGeboorte1.setNaam("Utrecht");
        persoonGeboorte1.setGemeenteGeboorte(gemeenteGeboorte1);
        ouder1.setGeboorte(persoonGeboorte1);
        PersoonVoornaam persoonVoornaam1 = new PersoonVoornaam();
        persoonVoornaam1.setNaam("Ab");
        ouder1.voegPersoonVoornaamToe(persoonVoornaam1);
        PersoonGeslachtsnaamcomponent geslachtsnaamcomponent1 = new PersoonGeslachtsnaamcomponent();
        geslachtsnaamcomponent1.setVoorvoegsel("de");
        geslachtsnaamcomponent1.setNaam("Vis");
        ouder1.voegGeslachtsnaamcomponentToe(geslachtsnaamcomponent1);
        Set<PersoonAdres> adressen1 = new HashSet<PersoonAdres>();
        PersoonAdres adres1 = new PersoonAdres();
        adres1.setDatumAanvangAdreshouding(20120515);
        Partij gemeente1 = new Partij();
        gemeente1.setGemeentecode("127");
        gemeente1.setNaam("Sassenheim");
        adres1.setGemeente(gemeente1);
        adres1.setAfgekorteNaamOpenbareRuimte("Brink");
        adres1.setHuisnummer("11");
        adres1.setHuisnummertoevoeging("B");
        Plaats woonplaats1 = new Plaats();
        woonplaats1.setWoonplaatscode("747");
        woonplaats1.setNaam("Vollenhoven");
        adres1.setWoonplaats(woonplaats1);
        adressen1.add(adres1);
        ouder1.setAdressen(adressen1);
        PersoonGeslachtsAanduiding persoonGeslachtsAanduiding1 = new PersoonGeslachtsAanduiding();
        persoonGeslachtsAanduiding1.setGeslachtsAanduiding(GeslachtsAanduiding.MAN);
        ouder1.setPersoonGeslachtsAanduiding(persoonGeslachtsAanduiding1);
        Betrokkenheid ouderschap1 = new Betrokkenheid();
        ouderschap1.setSoortBetrokkenheid(SoortBetrokkenheid.OUDER);
        ouderschap1.setBetrokkene(ouder1);
        betrokkenheden.add(ouderschap1);

        Persoon ouder2 = new Persoon();
        PersoonIdentificatienummers identificatienummers2 = new PersoonIdentificatienummers();
        identificatienummers2.setBurgerservicenummer("987654321");
        ouder2.setIdentificatienummers(identificatienummers2);
        PersoonGeboorte persoonGeboorte2 = new PersoonGeboorte();
        persoonGeboorte2.setDatumGeboorte(19660417);
        Partij gemeenteGeboorte2 = new Partij();
        gemeenteGeboorte2.setGemeentecode("030");
        gemeenteGeboorte2.setNaam("Utrecht");
        persoonGeboorte2.setGemeenteGeboorte(gemeenteGeboorte2);
        ouder2.setGeboorte(persoonGeboorte2);
        PersoonVoornaam persoonVoornaam2 = new PersoonVoornaam();
        persoonVoornaam2.setNaam("Ab");
        ouder2.voegPersoonVoornaamToe(persoonVoornaam2);
        PersoonGeslachtsnaamcomponent geslachtsnaamcomponent2 = new PersoonGeslachtsnaamcomponent();
        geslachtsnaamcomponent2.setVoorvoegsel("de");
        geslachtsnaamcomponent2.setNaam("Vis");
        ouder2.voegGeslachtsnaamcomponentToe(geslachtsnaamcomponent2);
        Set<PersoonAdres> adressen2 = new HashSet<PersoonAdres>();
        PersoonAdres adres2 = new PersoonAdres();
        adres2.setDatumAanvangAdreshouding(20120515);
        Partij gemeente2 = new Partij();
        gemeente2.setGemeentecode("365");
        gemeente2.setNaam("Zeist");
        adres2.setGemeente(gemeente2);
        adres2.setAfgekorteNaamOpenbareRuimte("Brink");
        adres2.setHuisnummer("11");
        adres2.setHuisnummertoevoeging("B");
        Plaats woonplaats2 = new Plaats();
        woonplaats2.setWoonplaatscode("747");
        woonplaats2.setNaam("Vollenhoven");
        adres2.setWoonplaats(woonplaats2);
        adressen2.add(adres2);
        ouder2.setAdressen(adressen2);
        PersoonGeslachtsAanduiding persoonGeslachtsAanduiding2 = new PersoonGeslachtsAanduiding();
        persoonGeslachtsAanduiding2.setGeslachtsAanduiding(GeslachtsAanduiding.VROUW);
        ouder2.setPersoonGeslachtsAanduiding(persoonGeslachtsAanduiding2);
        Betrokkenheid ouderschap2 = new Betrokkenheid();
        ouderschap2.setSoortBetrokkenheid(SoortBetrokkenheid.OUDER);
        ouderschap2.setBetrokkene(ouder2);
        betrokkenheden.add(ouderschap2);

        BerichtenIds berichtenIds = new BerichtenIds(1L, 2L);
        Integer authenticatieMiddelId = 3;
        Partij partij = new Partij();
        partij.setGemeentecode("321");
        partij.setNaam("Ridderkerk");
        BerichtContext context = new BerichtContext(berichtenIds, authenticatieMiddelId, partij);

        BerichtResultaat resultaat = new BerichtResultaat(null);

        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer("123234345")).thenReturn(nieuwgeborene);
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer("321432543")).thenReturn(ouder1);
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer("987654321")).thenReturn(ouder2);

        // test
        notificator.notificeerDashboard(bericht, context, resultaat);

        ArgumentCaptor<URI> uriArgument = ArgumentCaptor.forClass(URI.class);
        ArgumentCaptor<GeboorteBerichtRequest> berichtArgument = ArgumentCaptor.forClass(GeboorteBerichtRequest.class);

        @SuppressWarnings("rawtypes")
        ArgumentCaptor<Class> typeArgument = ArgumentCaptor.forClass(Class.class);

        Mockito.verify(restTemplate).postForEntity(uriArgument.capture(), berichtArgument.capture(),
                typeArgument.capture());

        Assert.assertEquals("http://localhost:8080/brp-dashboard/service/berichten/opslaan/geboorte", uriArgument
                .getValue().toASCIIString());

        Assert.assertNotNull(berichtArgument.getValue().getKenmerken());
        Assert.assertEquals("321", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getCode());
        Assert.assertEquals("Ridderkerk", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getNaam());
        Assert.assertEquals("Digimeente", berichtArgument.getValue().getKenmerken().getBurgerZakenModuleNaam());
        Assert.assertFalse(berichtArgument.getValue().getKenmerken().isPrevalidatie());

        Assert.assertNotNull(berichtArgument.getValue().getVerwerking());
        Assert.assertNotNull(berichtArgument.getValue().getVerwerking().getVerwerkingsmoment());
        Assert.assertEquals(VerwerkingStatus.GESLAAGD, berichtArgument.getValue().getVerwerking().getStatus());

        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene());
        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene().getBsn());
        Assert.assertEquals(123234345L, berichtArgument.getValue().getNieuwgeborene().getBsn().longValue());
        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene().getGeslachtsnaamcomponenten());

        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene().getVoornamen());
        Assert.assertEquals("Ab", berichtArgument.getValue().getNieuwgeborene().getVoornamen().get(0));

        // TODO friso: test toevoegen voor persoon met meerdere voor- en/of achternamen

        Assert.assertEquals("de", berichtArgument.getValue().getNieuwgeborene().getGeslachtsnaamcomponenten().get(0)
                .getVoorvoegsel());
        Assert.assertEquals("Vis", berichtArgument.getValue().getNieuwgeborene().getGeslachtsnaamcomponenten().get(0)
                .getNaam());

        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene());
        Assert.assertEquals(20120515, berichtArgument.getValue().getAdresNieuwgeborene().getDatumAanvangAdreshouding());
        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene().getGemeente());
        Assert.assertEquals("365", berichtArgument.getValue().getAdresNieuwgeborene().getGemeente().getCode());
        Assert.assertEquals("Zeist", berichtArgument.getValue().getAdresNieuwgeborene().getGemeente().getNaam());
        Assert.assertEquals("Brink", berichtArgument.getValue().getAdresNieuwgeborene().getStraat());
        Assert.assertEquals("11", berichtArgument.getValue().getAdresNieuwgeborene().getHuisnummer());
        Assert.assertEquals("B", berichtArgument.getValue().getAdresNieuwgeborene().getHuisnummertoevoeging());
        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene().getPlaats());
        Assert.assertEquals("747", berichtArgument.getValue().getAdresNieuwgeborene().getPlaats().getCode());
        Assert.assertEquals("Vollenhoven", berichtArgument.getValue().getAdresNieuwgeborene().getPlaats().getNaam());

        Set<Long> ouderBsns = new HashSet<Long>();
        ouderBsns.add(321432543L);
        ouderBsns.add(987654321L);

        Assert.assertNotNull(berichtArgument.getValue().getOuder1());
        Assert.assertNotNull(berichtArgument.getValue().getOuder2());
        Assert.assertNotNull(berichtArgument.getValue().getOuder1().getBsn());
        Assert.assertNotNull(berichtArgument.getValue().getOuder2().getBsn());
        Long ouder1bsn = berichtArgument.getValue().getOuder1().getBsn();
        Long ouder2bsn = berichtArgument.getValue().getOuder2().getBsn();
        Assert.assertTrue(ouderBsns.contains(ouder1bsn));
        Assert.assertTrue(ouderBsns.contains(ouder1bsn));
        Assert.assertFalse(ouder1bsn.equals(ouder2bsn));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGeboorteBerichtZonderKindAdres1Ouder() throws Exception {
        InschrijvingGeboorteBericht bericht = new InschrijvingGeboorteBericht();
        BerichtStuurgegevens berichtStuurgegevens = new BerichtStuurgegevens();
        berichtStuurgegevens.setApplicatie("Digimeente");
        bericht.setBerichtStuurgegevens(berichtStuurgegevens);
        Set<Betrokkenheid> betrokkenheden = creeerBetrokkenhedenVoorBericht(bericht);

        Persoon nieuwgeborene = new Persoon();
        PersoonIdentificatienummers identificatienummers = new PersoonIdentificatienummers();
        identificatienummers.setBurgerservicenummer("123234345");
        nieuwgeborene.setIdentificatienummers(identificatienummers);
        PersoonGeboorte persoonGeboorte = new PersoonGeboorte();
        persoonGeboorte.setDatumGeboorte(19660417);
        Partij gemeenteGeboorte = new Partij();
        gemeenteGeboorte.setGemeentecode("030");
        gemeenteGeboorte.setNaam("Utrecht");
        persoonGeboorte.setGemeenteGeboorte(gemeenteGeboorte);
        nieuwgeborene.setGeboorte(persoonGeboorte);
        PersoonVoornaam persoonVoornaam = new PersoonVoornaam();
        persoonVoornaam.setNaam("Ab");
        nieuwgeborene.voegPersoonVoornaamToe(persoonVoornaam);
        PersoonGeslachtsnaamcomponent geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent();
        geslachtsnaamcomponent.setVoorvoegsel("de");
        geslachtsnaamcomponent.setNaam("Vis");
        nieuwgeborene.voegGeslachtsnaamcomponentToe(geslachtsnaamcomponent);
        Set<PersoonAdres> adressen = new HashSet<PersoonAdres>();
        nieuwgeborene.setAdressen(adressen);
        Betrokkenheid kind = new Betrokkenheid();
        kind.setSoortBetrokkenheid(SoortBetrokkenheid.KIND);
        kind.setBetrokkene(nieuwgeborene);
        betrokkenheden.add(kind);

        Persoon ouder1 = new Persoon();
        PersoonIdentificatienummers identificatienummers1 = new PersoonIdentificatienummers();
        identificatienummers1.setBurgerservicenummer("321432543");
        ouder1.setIdentificatienummers(identificatienummers1);
        PersoonGeboorte persoonGeboorte1 = new PersoonGeboorte();
        persoonGeboorte1.setDatumGeboorte(19660417);
        Partij gemeenteGeboorte1 = new Partij();
        gemeenteGeboorte1.setGemeentecode("030");
        gemeenteGeboorte1.setNaam("Utrecht");
        persoonGeboorte1.setGemeenteGeboorte(gemeenteGeboorte1);
        ouder1.setGeboorte(persoonGeboorte1);
        PersoonVoornaam persoonVoornaam1 = new PersoonVoornaam();
        persoonVoornaam1.setNaam("Ab");
        ouder1.voegPersoonVoornaamToe(persoonVoornaam1);
        PersoonGeslachtsnaamcomponent geslachtsnaamcomponent1 = new PersoonGeslachtsnaamcomponent();
        geslachtsnaamcomponent1.setVoorvoegsel("de");
        geslachtsnaamcomponent1.setNaam("Vis");
        ouder1.voegGeslachtsnaamcomponentToe(geslachtsnaamcomponent1);
        Set<PersoonAdres> adressen1 = new HashSet<PersoonAdres>();
        PersoonAdres adres1 = new PersoonAdres();
        adres1.setDatumAanvangAdreshouding(20120515);
        Partij gemeente1 = new Partij();
        gemeente1.setGemeentecode("127");
        gemeente1.setNaam("Sassenheim");
        adres1.setGemeente(gemeente1);
        adres1.setAfgekorteNaamOpenbareRuimte("Brink");
        adres1.setHuisnummer("11");
        adres1.setHuisnummertoevoeging("B");
        Plaats woonplaats1 = new Plaats();
        woonplaats1.setWoonplaatscode("747");
        woonplaats1.setNaam("Vollenhoven");
        adres1.setWoonplaats(woonplaats1);
        adressen1.add(adres1);
        ouder1.setAdressen(adressen1);
        PersoonGeslachtsAanduiding persoonGeslachtsAanduiding1 = new PersoonGeslachtsAanduiding();
        persoonGeslachtsAanduiding1.setGeslachtsAanduiding(GeslachtsAanduiding.MAN);
        ouder1.setPersoonGeslachtsAanduiding(persoonGeslachtsAanduiding1);
        Betrokkenheid ouderschap1 = new Betrokkenheid();
        ouderschap1.setSoortBetrokkenheid(SoortBetrokkenheid.OUDER);
        ouderschap1.setBetrokkene(ouder1);
        betrokkenheden.add(ouderschap1);

        BerichtenIds berichtenIds = new BerichtenIds(1L, 2L);
        Integer authenticatieMiddelId = 3;
        Partij partij = new Partij();
        partij.setGemeentecode("321");
        partij.setNaam("Ridderkerk");
        BerichtContext context = new BerichtContext(berichtenIds, authenticatieMiddelId, partij);

        BerichtResultaat resultaat = new BerichtResultaat(null);

        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer("123234345")).thenReturn(nieuwgeborene);
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer("321432543")).thenReturn(ouder1);

        // test
        notificator.notificeerDashboard(bericht, context, resultaat);

        ArgumentCaptor<URI> uriArgument = ArgumentCaptor.forClass(URI.class);
        ArgumentCaptor<GeboorteBerichtRequest> berichtArgument = ArgumentCaptor.forClass(GeboorteBerichtRequest.class);

        @SuppressWarnings("rawtypes")
        ArgumentCaptor<Class> typeArgument = ArgumentCaptor.forClass(Class.class);

        Mockito.verify(restTemplate).postForEntity(uriArgument.capture(), berichtArgument.capture(),
                typeArgument.capture());

        Assert.assertEquals("http://localhost:8080/brp-dashboard/service/berichten/opslaan/geboorte", uriArgument
                .getValue().toASCIIString());

        Assert.assertNotNull(berichtArgument.getValue().getKenmerken());
        Assert.assertEquals("321", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getCode());
        Assert.assertEquals("Ridderkerk", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getNaam());
        Assert.assertEquals("Digimeente", berichtArgument.getValue().getKenmerken().getBurgerZakenModuleNaam());
        Assert.assertFalse(berichtArgument.getValue().getKenmerken().isPrevalidatie());

        Assert.assertNotNull(berichtArgument.getValue().getVerwerking());
        Assert.assertNotNull(berichtArgument.getValue().getVerwerking().getVerwerkingsmoment());
        Assert.assertEquals(VerwerkingStatus.GESLAAGD, berichtArgument.getValue().getVerwerking().getStatus());

        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene());
        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene().getBsn());
        Assert.assertEquals(123234345L, berichtArgument.getValue().getNieuwgeborene().getBsn().longValue());
        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene().getGeslachtsnaamcomponenten());

        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene().getVoornamen());
        Assert.assertEquals("Ab", berichtArgument.getValue().getNieuwgeborene().getVoornamen().get(0));

        // TODO friso: test toevoegen voor persoon met meerdere voor- en/of achternamen

        Assert.assertEquals("de", berichtArgument.getValue().getNieuwgeborene().getGeslachtsnaamcomponenten().get(0)
                .getVoorvoegsel());
        Assert.assertEquals("Vis", berichtArgument.getValue().getNieuwgeborene().getGeslachtsnaamcomponenten().get(0)
                .getNaam());

        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene());
        Assert.assertEquals(20120515, berichtArgument.getValue().getAdresNieuwgeborene().getDatumAanvangAdreshouding());
        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene().getGemeente());
        Assert.assertEquals("127", berichtArgument.getValue().getAdresNieuwgeborene().getGemeente().getCode());
        Assert.assertEquals("Sassenheim", berichtArgument.getValue().getAdresNieuwgeborene().getGemeente().getNaam());
        Assert.assertEquals("Brink", berichtArgument.getValue().getAdresNieuwgeborene().getStraat());
        Assert.assertEquals("11", berichtArgument.getValue().getAdresNieuwgeborene().getHuisnummer());
        Assert.assertEquals("B", berichtArgument.getValue().getAdresNieuwgeborene().getHuisnummertoevoeging());
        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene().getPlaats());
        Assert.assertEquals("747", berichtArgument.getValue().getAdresNieuwgeborene().getPlaats().getCode());
        Assert.assertEquals("Vollenhoven", berichtArgument.getValue().getAdresNieuwgeborene().getPlaats().getNaam());

        Assert.assertNotNull(berichtArgument.getValue().getOuder1());
        Assert.assertNotNull(berichtArgument.getValue().getOuder1().getBsn());
        Assert.assertEquals(321432543L, berichtArgument.getValue().getOuder1().getBsn().longValue());

        Assert.assertNull(berichtArgument.getValue().getOuder2());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGeboorteBerichtNietSuccesvol() throws Exception {
        InschrijvingGeboorteBericht bericht = new InschrijvingGeboorteBericht();
        BerichtStuurgegevens berichtStuurgegevens = new BerichtStuurgegevens();
        berichtStuurgegevens.setApplicatie("Digimeente");
        bericht.setBerichtStuurgegevens(berichtStuurgegevens);
        Set<Betrokkenheid> betrokkenheden = creeerBetrokkenhedenVoorBericht(bericht);

        Persoon nieuwgeborene = new Persoon();
        PersoonIdentificatienummers identificatienummers = new PersoonIdentificatienummers();
        identificatienummers.setBurgerservicenummer("123234345");
        nieuwgeborene.setIdentificatienummers(identificatienummers);
        PersoonGeboorte persoonGeboorte = new PersoonGeboorte();
        persoonGeboorte.setDatumGeboorte(19660417);
        nieuwgeborene.setGeboorte(persoonGeboorte);
        PersoonVoornaam persoonVoornaam = new PersoonVoornaam();
        persoonVoornaam.setNaam("Ab");
        nieuwgeborene.voegPersoonVoornaamToe(persoonVoornaam);
        PersoonGeslachtsnaamcomponent geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent();
        geslachtsnaamcomponent.setVoorvoegsel("de");
        geslachtsnaamcomponent.setNaam("Vis");
        nieuwgeborene.voegGeslachtsnaamcomponentToe(geslachtsnaamcomponent);
        Set<PersoonAdres> adressen = new HashSet<PersoonAdres>();
        nieuwgeborene.setAdressen(adressen);
        Betrokkenheid kind = new Betrokkenheid();
        kind.setSoortBetrokkenheid(SoortBetrokkenheid.KIND);
        kind.setBetrokkene(nieuwgeborene);
        betrokkenheden.add(kind);

        Persoon ouder1 = new Persoon();
        PersoonIdentificatienummers identificatienummers1 = new PersoonIdentificatienummers();
        identificatienummers1.setBurgerservicenummer("321432543");
        ouder1.setIdentificatienummers(identificatienummers1);
        PersoonGeboorte persoonGeboorte1 = new PersoonGeboorte();
        persoonGeboorte1.setDatumGeboorte(19660417);
        Partij gemeenteGeboorte1 = new Partij();
        gemeenteGeboorte1.setGemeentecode("030");
        gemeenteGeboorte1.setNaam("Utrecht");
        persoonGeboorte1.setGemeenteGeboorte(gemeenteGeboorte1);
        ouder1.setGeboorte(persoonGeboorte1);
        PersoonVoornaam persoonVoornaam1 = new PersoonVoornaam();
        persoonVoornaam1.setNaam("Ab");
        ouder1.voegPersoonVoornaamToe(persoonVoornaam1);
        PersoonGeslachtsnaamcomponent geslachtsnaamcomponent1 = new PersoonGeslachtsnaamcomponent();
        geslachtsnaamcomponent1.setVoorvoegsel("de");
        geslachtsnaamcomponent1.setNaam("Vis");
        ouder1.voegGeslachtsnaamcomponentToe(geslachtsnaamcomponent1);
        Set<PersoonAdres> adressen1 = new HashSet<PersoonAdres>();
        PersoonAdres adres1 = new PersoonAdres();
        adres1.setDatumAanvangAdreshouding(20120515);
        Partij gemeente1 = new Partij();
        gemeente1.setGemeentecode("010");
        gemeente1.setNaam("Rotterdam");
        adres1.setGemeente(gemeente1);
        adres1.setAfgekorteNaamOpenbareRuimte("Brink");
        adres1.setHuisnummer("11");
        adres1.setHuisnummertoevoeging("B");
        Plaats woonplaats1 = new Plaats();
        woonplaats1.setWoonplaatscode("747");
        woonplaats1.setNaam("Vollenhoven");
        adres1.setWoonplaats(woonplaats1);
        adressen1.add(adres1);
        ouder1.setAdressen(adressen1);
        Betrokkenheid ouderschap1 = new Betrokkenheid();
        ouderschap1.setSoortBetrokkenheid(SoortBetrokkenheid.OUDER);
        ouderschap1.setBetrokkene(ouder1);
        betrokkenheden.add(ouderschap1);

        Persoon persoon2 = new Persoon();
        PersoonIdentificatienummers identificatienummers2 = new PersoonIdentificatienummers();
        identificatienummers2.setBurgerservicenummer("123234345");
        persoon2.setIdentificatienummers(identificatienummers2);
        PersoonGeboorte persoonGeboorte2 = new PersoonGeboorte();
        persoonGeboorte2.setDatumGeboorte(19660417);
        Partij gemeenteGeboorte2 = new Partij();
        gemeenteGeboorte2.setGemeentecode("030");
        gemeenteGeboorte2.setNaam("Utrecht");
        persoonGeboorte2.setGemeenteGeboorte(gemeenteGeboorte2);
        persoon2.setGeboorte(persoonGeboorte2);
        PersoonVoornaam persoonVoornaam2 = new PersoonVoornaam();
        persoonVoornaam2.setNaam("Ben");
        persoon2.voegPersoonVoornaamToe(persoonVoornaam2);
        PersoonGeslachtsnaamcomponent geslachtsnaamcomponent2 = new PersoonGeslachtsnaamcomponent();
        geslachtsnaamcomponent2.setVoorvoegsel("de");
        geslachtsnaamcomponent2.setNaam("Bie");
        persoon2.voegGeslachtsnaamcomponentToe(geslachtsnaamcomponent2);
        Set<PersoonAdres> adressen2 = new HashSet<PersoonAdres>();
        PersoonAdres adres2 = new PersoonAdres();
        adres2.setDatumAanvangAdreshouding(20120515);
        Partij gemeente2 = new Partij();
        gemeente2.setGemeentecode("365");
        gemeente2.setNaam("Zeist");
        adres2.setGemeente(gemeente2);
        adres2.setAfgekorteNaamOpenbareRuimte("Brink");
        adres2.setHuisnummer("11");
        adres2.setHuisnummertoevoeging("B");
        Plaats woonplaats2 = new Plaats();
        woonplaats2.setWoonplaatscode("747");
        woonplaats2.setNaam("Vollenhoven");
        adres2.setWoonplaats(woonplaats2);
        adressen2.add(adres2);
        persoon2.setAdressen(adressen2);

        BerichtenIds berichtenIds = new BerichtenIds(1L, 2L);
        Integer authenticatieMiddelId = 3;
        Partij partij = new Partij();
        partij.setGemeentecode("321");
        partij.setNaam("Hoorn");
        BerichtContext context = new BerichtContext(berichtenIds, authenticatieMiddelId, partij);

        List<Melding> meldingen = new ArrayList<Melding>();
        meldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.BRBER00121, "BSN bestaat al"));
        meldingen.add(new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0001, "zxc"));
        BerichtResultaat resultaat = new BerichtResultaat(meldingen);
        resultaat.markeerVerwerkingAlsFoutief();

        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer("321432543")).thenReturn(ouder1);
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer("123234345")).thenReturn(persoon2);

        // test
        notificator.notificeerDashboard(bericht, context, resultaat);

        ArgumentCaptor<URI> uriArgument = ArgumentCaptor.forClass(URI.class);
        ArgumentCaptor<GeboorteBerichtRequest> berichtArgument = ArgumentCaptor.forClass(GeboorteBerichtRequest.class);

        @SuppressWarnings("rawtypes")
        ArgumentCaptor<Class> typeArgument = ArgumentCaptor.forClass(Class.class);

        Mockito.verify(restTemplate).postForEntity(uriArgument.capture(), berichtArgument.capture(),
                typeArgument.capture());

        Assert.assertEquals("http://localhost:8080/brp-dashboard/service/berichten/opslaan/geboorte", uriArgument
                .getValue().toASCIIString());

        Assert.assertNotNull(berichtArgument.getValue().getKenmerken());
        Assert.assertEquals("321", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getCode());
        Assert.assertEquals("Hoorn", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getNaam());
        Assert.assertEquals("Digimeente", berichtArgument.getValue().getKenmerken().getBurgerZakenModuleNaam());
        Assert.assertFalse(berichtArgument.getValue().getKenmerken().isPrevalidatie());

        Assert.assertNotNull(berichtArgument.getValue().getVerwerking());
        Assert.assertNotNull(berichtArgument.getValue().getVerwerking().getVerwerkingsmoment());
        Assert.assertEquals(VerwerkingStatus.MISLUKT, berichtArgument.getValue().getVerwerking().getStatus());

        Assert.assertNotNull(berichtArgument.getValue().getMeldingen());
        Assert.assertEquals(2, berichtArgument.getValue().getMeldingen().size());
        Assert.assertNotNull(berichtArgument.getValue().getMeldingen().get(0));
        Assert.assertEquals(MeldingSoort.FOUT, berichtArgument.getValue().getMeldingen().get(0).getSoort());
        Assert.assertEquals("BSN bestaat al", berichtArgument.getValue().getMeldingen().get(0).getTekst());
        Assert.assertNotNull(berichtArgument.getValue().getMeldingen().get(1));
        Assert.assertEquals(MeldingSoort.OVERRULEBAAR, berichtArgument.getValue().getMeldingen().get(1).getSoort());
        Assert.assertEquals("zxc", berichtArgument.getValue().getMeldingen().get(1).getTekst());

        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene());
        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene().getBsn());
        Assert.assertEquals(123234345L, berichtArgument.getValue().getNieuwgeborene().getBsn().longValue());
        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene().getGeslachtsnaamcomponenten());

        Assert.assertNotNull(berichtArgument.getValue().getNieuwgeborene().getVoornamen());
        Assert.assertEquals("Ab", berichtArgument.getValue().getNieuwgeborene().getVoornamen().get(0));

        // TODO friso: test toevoegen voor persoon met meerdere voor- en/of achternamen

        Assert.assertEquals("de", berichtArgument.getValue().getNieuwgeborene().getGeslachtsnaamcomponenten().get(0)
                .getVoorvoegsel());
        Assert.assertEquals("Vis", berichtArgument.getValue().getNieuwgeborene().getGeslachtsnaamcomponenten().get(0)
                .getNaam());

        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene());
        Assert.assertEquals(20120515, berichtArgument.getValue().getAdresNieuwgeborene().getDatumAanvangAdreshouding());
        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene().getGemeente());
        Assert.assertEquals("010", berichtArgument.getValue().getAdresNieuwgeborene().getGemeente().getCode());
        Assert.assertEquals("Rotterdam", berichtArgument.getValue().getAdresNieuwgeborene().getGemeente().getNaam());
        Assert.assertEquals("Brink", berichtArgument.getValue().getAdresNieuwgeborene().getStraat());
        Assert.assertEquals("11", berichtArgument.getValue().getAdresNieuwgeborene().getHuisnummer());
        Assert.assertEquals("B", berichtArgument.getValue().getAdresNieuwgeborene().getHuisnummertoevoeging());
        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene().getPlaats());
        Assert.assertEquals("747", berichtArgument.getValue().getAdresNieuwgeborene().getPlaats().getCode());
        Assert.assertEquals("Vollenhoven", berichtArgument.getValue().getAdresNieuwgeborene().getPlaats().getNaam());

        Assert.assertNotNull(berichtArgument.getValue().getOuder1());
        Assert.assertNotNull(berichtArgument.getValue().getOuder1().getBsn());
        Assert.assertEquals(321432543L, berichtArgument.getValue().getOuder1().getBsn().longValue());

        Assert.assertNull(berichtArgument.getValue().getOuder2());
    }

}
