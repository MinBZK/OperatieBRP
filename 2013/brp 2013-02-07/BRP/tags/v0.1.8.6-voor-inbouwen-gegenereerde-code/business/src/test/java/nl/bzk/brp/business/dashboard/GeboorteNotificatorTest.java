/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dashboard;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.BerichtStuurgegevens;
import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.dashboard.GeboorteBerichtRequest;
import nl.bzk.brp.model.dashboard.MeldingSoort;
import nl.bzk.brp.model.dashboard.VerwerkingStatus;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.OverruleMelding;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.util.PersoonAdresBuilder;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
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
    private RestTemplate restTemplate;

    @Mock
    private PersoonRepository persoonRepository;

    @Mock
    private ReferentieDataRepository referentieDataRepository;

    @Before
    public void init() throws URISyntaxException {
        MockitoAnnotations.initMocks(this);
        notificator = new GeboorteNotificator();
        notificator
            .setDashboardUri(new URI("http://localhost:8080/brp-dashboard/service/berichten/opslaan/geboorte"));
        ReflectionTestUtils.setField(notificator, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(notificator, "persoonRepository", persoonRepository);
        ReflectionTestUtils.setField(notificator, "referentieDataRepository", referentieDataRepository);
    }

    private List<BetrokkenheidBericht> creeerBetrokkenhedenVoorBericht(final AbstractBijhoudingsBericht bericht) {
        List<Actie> brpActies = new ArrayList<Actie>();
        ActieBericht brpActie = new ActieBericht();
        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        RelatieBericht familie = new RelatieBericht();
        List<BetrokkenheidBericht> betrokkenheden = new ArrayList<BetrokkenheidBericht>();

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
        berichtStuurgegevens.setPrevalidatieBericht(JaNee.Ja);
        bericht.setBerichtStuurgegevens(berichtStuurgegevens);
        List<BetrokkenheidBericht> betrokkenheden = creeerBetrokkenhedenVoorBericht(bericht);

        PersoonBericht nieuwgeborene = PersoonBuilder.bouwPersoon("123234345", Geslachtsaanduiding.MAN, 19660417,
            StatischeObjecttypeBuilder.bouwGemeente((short) 344, null), "Ab", "de", "Vis");
        PersoonAdresBericht adres = PersoonAdresBuilder.bouwWoonadres("Brink", 11, "1234AA",
            StatischeObjecttypeBuilder.bouwWoonplaats((short) 747, "Vollenhoven"),
            StatischeObjecttypeBuilder.bouwGemeente((short) 355, "Zeist"), 20120515);
        adres.getGegevens().setHuisnummertoevoeging(new Huisnummertoevoeging("B"));
        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();
        adressen.add(adres);
        nieuwgeborene.setAdressen(adressen);

        BetrokkenheidBericht kind = new BetrokkenheidBericht();
        kind.setRol(SoortBetrokkenheid.KIND);
        kind.setBetrokkene(nieuwgeborene);
        betrokkenheden.add(kind);

        PersoonBericht ouder1 = PersoonBuilder.bouwPersoon("321432543", Geslachtsaanduiding.MAN, 19660417,
            StatischeObjecttypeBuilder.bouwGemeente((short) 0344, null), "Ab", "de", "Vis");
        PersoonAdresBericht adresOuder1 = PersoonAdresBuilder.bouwWoonadres("Brink", 11, "1234AA",
            StatischeObjecttypeBuilder.bouwWoonplaats((short) 747, "Vollenhoven"),
            StatischeObjecttypeBuilder.bouwGemeente((short) 355, "Zeist"), 20120515);
        adresOuder1.getGegevens().setHuisnummertoevoeging(new Huisnummertoevoeging("B"));
        List<PersoonAdresBericht> adressenOuder1 = new ArrayList<PersoonAdresBericht>();
        adressenOuder1.add(adresOuder1);
        ouder1.setAdressen(adressenOuder1);

        BetrokkenheidBericht ouderschap1 = new BetrokkenheidBericht();
        ouderschap1.setRol(SoortBetrokkenheid.OUDER);
        ouderschap1.setBetrokkene(ouder1);
        betrokkenheden.add(ouderschap1);

        PersoonBericht ouder2 = PersoonBuilder.bouwPersoon("987654321", Geslachtsaanduiding.MAN, 19660417,
            StatischeObjecttypeBuilder.bouwGemeente((short) 344, null), "Ab", "de", "Vis");
        PersoonAdresBericht adresOuder2 = PersoonAdresBuilder.bouwWoonadres("Brink", 11, "1234AA",
            StatischeObjecttypeBuilder.bouwWoonplaats((short) 747, "Vollenhoven"),
            StatischeObjecttypeBuilder.bouwGemeente((short) 355, "Zeist"), 20120515);
        adresOuder2.getGegevens().setHuisnummertoevoeging(new Huisnummertoevoeging("B"));
        List<PersoonAdresBericht> adressenOuder2 = new ArrayList<PersoonAdresBericht>();
        adressenOuder2.add(adresOuder2);
        ouder2.setAdressen(adressenOuder2);

        BetrokkenheidBericht ouderschap2 = new BetrokkenheidBericht();
        ouderschap2.setRol(SoortBetrokkenheid.OUDER);
        ouderschap2.setBetrokkene(ouder2);
        betrokkenheden.add(ouderschap2);

        BerichtenIds berichtenIds = new BerichtenIds(1L, 2L);
        Integer authenticatieMiddelId = 3;
        Partij partij = StatischeObjecttypeBuilder.bouwGemeente((short) 321, "Ridderkerk");
        BerichtContext context = new BerichtContext(berichtenIds, authenticatieMiddelId, partij, "ref");

        BerichtResultaat resultaat = new BerichtResultaat(null);

        Partij brpGemeenteGeboorte = StatischeObjecttypeBuilder.bouwGemeente((short) 344, "Utrecht");

        Mockito.when(referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 344)))
               .thenReturn(brpGemeenteGeboorte);
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("123234345")))
               .thenReturn(bouwPersoonModel(nieuwgeborene));
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("321432543")))
               .thenReturn(bouwPersoonModel(ouder1));
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("987654321")))
               .thenReturn(bouwPersoonModel(ouder2));

        // test
        notificator.voorbereiden(bericht, context);
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
        Assert.assertEquals("0321", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getCode());
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
        Assert.assertEquals("0344", berichtArgument.getValue().getNieuwgeborene().getGemeenteGeboorte().getCode());
        Assert.assertEquals("Utrecht", berichtArgument.getValue().getNieuwgeborene().getGemeenteGeboorte().getNaam());

        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene());
        Assert.assertEquals(20120515, berichtArgument.getValue().getAdresNieuwgeborene().getDatumAanvangAdreshouding());
        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene().getGemeente());
        Assert.assertEquals("0355", berichtArgument.getValue().getAdresNieuwgeborene().getGemeente().getCode());
        Assert.assertEquals("Zeist", berichtArgument.getValue().getAdresNieuwgeborene().getGemeente().getNaam());

        Assert.assertEquals("Brink", berichtArgument.getValue().getAdresNieuwgeborene().getStraat());
        Assert.assertEquals("11", berichtArgument.getValue().getAdresNieuwgeborene().getHuisnummer());
        Assert.assertEquals("B", berichtArgument.getValue().getAdresNieuwgeborene().getHuisnummertoevoeging());
        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene().getPlaats());
        Assert.assertEquals("0747", berichtArgument.getValue().getAdresNieuwgeborene().getPlaats().getCode());
        Assert.assertEquals("Vollenhoven", berichtArgument.getValue().getAdresNieuwgeborene().getPlaats().getNaam());

        List<Long> ouderBsns = new ArrayList<Long>();
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
        List<BetrokkenheidBericht> betrokkenheden = creeerBetrokkenhedenVoorBericht(bericht);

        PersoonBericht nieuwgeborene = PersoonBuilder.bouwPersoon("123234345", Geslachtsaanduiding.MAN, 19660417,
            StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM, "Ab", "de", "Vis");
        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();
        nieuwgeborene.setAdressen(adressen);

        BetrokkenheidBericht kind = new BetrokkenheidBericht();
        kind.setRol(SoortBetrokkenheid.KIND);
        kind.setBetrokkene(nieuwgeborene);
        betrokkenheden.add(kind);

        BerichtenIds berichtenIds = new BerichtenIds(1L, 2L);
        Integer authenticatieMiddelId = 3;
        Partij partij = StatischeObjecttypeBuilder.bouwGemeente((short) 321, "Hoorn");
        BerichtContext context = new BerichtContext(berichtenIds, authenticatieMiddelId, partij, "ref");

        BerichtResultaat resultaat = new BerichtResultaat(null);

        Partij brpGemeenteGeboorte = StatischeObjecttypeBuilder.bouwGemeente((short) 030, "Utrecht");

        Mockito.when(referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 030)))
               .thenReturn(brpGemeenteGeboorte);
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("123234345")))
               .thenReturn(bouwPersoonModel(nieuwgeborene));

        // test
        notificator.voorbereiden(bericht, context);
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
        Assert.assertEquals("0321", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getCode());
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
        List<BetrokkenheidBericht> betrokkenheden = creeerBetrokkenhedenVoorBericht(bericht);

        PersoonBericht nieuwgeborene = PersoonBuilder.bouwPersoon("123234345", Geslachtsaanduiding.MAN, 19660417,
            StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM, "Ab", "de", "Vis");
        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();
        nieuwgeborene.setAdressen(adressen);

        BetrokkenheidBericht kind = new BetrokkenheidBericht();
        kind.setRol(SoortBetrokkenheid.KIND);
        kind.setBetrokkene(nieuwgeborene);
        betrokkenheden.add(kind);

        PersoonBericht ouder1 = PersoonBuilder.bouwPersoon("321432543", Geslachtsaanduiding.MAN, 19660417,
            StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Ab", "de", "Vis");
        PersoonAdresBericht adresOuder1 = PersoonAdresBuilder.bouwWoonadres("Brink", 11, "1234AA",
            StatischeObjecttypeBuilder.bouwWoonplaats((short) 747, "Vollenhoven"),
            StatischeObjecttypeBuilder.bouwGemeente((short) 127, "Sassenheim"), 20120515);
        adresOuder1.getGegevens().setHuisnummertoevoeging(new Huisnummertoevoeging("B"));
        List<PersoonAdresBericht> adressenOuder1 = new ArrayList<PersoonAdresBericht>();
        adressenOuder1.add(adresOuder1);
        ouder1.setAdressen(adressenOuder1);

        BetrokkenheidBericht ouderschap1 = new BetrokkenheidBericht();
        ouderschap1.setRol(SoortBetrokkenheid.OUDER);
        ouderschap1.setBetrokkene(ouder1);
        betrokkenheden.add(ouderschap1);

        PersoonBericht ouder2 = PersoonBuilder.bouwPersoon("987654321", Geslachtsaanduiding.MAN, 19660417,
            StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM, "Ab", "de", "Vis");
        ouder2.getGeslachtsaanduiding().setGeslachtsaanduiding(Geslachtsaanduiding.VROUW);
        PersoonAdresBericht adresOuder2 = PersoonAdresBuilder.bouwWoonadres("Brink", 11, "1234AA",
            StatischeObjecttypeBuilder.bouwWoonplaats((short) 747, "Vollenhoven"),
            StatischeObjecttypeBuilder.bouwGemeente((short) 365, "Zeist"), 20120515);
        adresOuder2.getGegevens().setHuisnummertoevoeging(new Huisnummertoevoeging("B"));
        List<PersoonAdresBericht> adressenOuder2 = new ArrayList<PersoonAdresBericht>();
        adressenOuder2.add(adresOuder2);
        ouder2.setAdressen(adressenOuder2);

        BetrokkenheidBericht ouderschap2 = new BetrokkenheidBericht();
        ouderschap2.setRol(SoortBetrokkenheid.OUDER);
        ouderschap2.setBetrokkene(ouder2);
        betrokkenheden.add(ouderschap2);

        BerichtenIds berichtenIds = new BerichtenIds(1L, 2L);
        Integer authenticatieMiddelId = 3;
        Partij partij = StatischeObjecttypeBuilder.bouwGemeente((short) 321, "Ridderkerk");
        BerichtContext context = new BerichtContext(berichtenIds, authenticatieMiddelId, partij, "ref");

        BerichtResultaat resultaat = new BerichtResultaat(null);

        Partij brpGemeenteGeboorte = StatischeObjecttypeBuilder.bouwGemeente((short) 30, "Utrecht");

        Mockito.when(referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 30)))
               .thenReturn(brpGemeenteGeboorte);
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("123234345")))
               .thenReturn(bouwPersoonModel(nieuwgeborene));
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("321432543")))
               .thenReturn(bouwPersoonModel(ouder1));
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("987654321")))
               .thenReturn(bouwPersoonModel(ouder2));

        // test
        notificator.voorbereiden(bericht, context);
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
        Assert.assertEquals("0321", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getCode());
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
        Assert.assertEquals("0365", berichtArgument.getValue().getAdresNieuwgeborene().getGemeente().getCode());
        Assert.assertEquals("Zeist", berichtArgument.getValue().getAdresNieuwgeborene().getGemeente().getNaam());
        Assert.assertEquals("Brink", berichtArgument.getValue().getAdresNieuwgeborene().getStraat());
        Assert.assertEquals("11", berichtArgument.getValue().getAdresNieuwgeborene().getHuisnummer());
        Assert.assertEquals("B", berichtArgument.getValue().getAdresNieuwgeborene().getHuisnummertoevoeging());
        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene().getPlaats());
        Assert.assertEquals("0747", berichtArgument.getValue().getAdresNieuwgeborene().getPlaats().getCode());
        Assert.assertEquals("Vollenhoven", berichtArgument.getValue().getAdresNieuwgeborene().getPlaats().getNaam());

        List<Long> ouderBsns = new ArrayList<Long>();
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
        List<BetrokkenheidBericht> betrokkenheden = creeerBetrokkenhedenVoorBericht(bericht);

        PersoonBericht nieuwgeborene = PersoonBuilder.bouwPersoon("123234345", Geslachtsaanduiding.MAN, 19660417,
            StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM, "Ab", "de", "Vis");
        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();
        nieuwgeborene.setAdressen(adressen);

        BetrokkenheidBericht kind = new BetrokkenheidBericht();
        kind.setRol(SoortBetrokkenheid.KIND);
        kind.setBetrokkene(nieuwgeborene);
        betrokkenheden.add(kind);

        PersoonBericht ouder1 = PersoonBuilder.bouwPersoon("321432543", Geslachtsaanduiding.MAN, 19660417,
            StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM, "Ab", "de", "Vis");
        PersoonAdresBericht adresOuder1 = PersoonAdresBuilder.bouwWoonadres("Brink", 11, "1234AA",
            StatischeObjecttypeBuilder.bouwWoonplaats((short) 747, "Vollenhoven"),
            StatischeObjecttypeBuilder.bouwGemeente((short) 127, "Sassenheim"), 20120515);
        adresOuder1.getGegevens().setHuisnummertoevoeging(new Huisnummertoevoeging("B"));
        List<PersoonAdresBericht> adressenOuder1 = new ArrayList<PersoonAdresBericht>();
        adressenOuder1.add(adresOuder1);
        ouder1.setAdressen(adressenOuder1);

        BetrokkenheidBericht ouderschap1 = new BetrokkenheidBericht();
        ouderschap1.setRol(SoortBetrokkenheid.OUDER);
        ouderschap1.setBetrokkene(ouder1);
        betrokkenheden.add(ouderschap1);

        BerichtenIds berichtenIds = new BerichtenIds(1L, 2L);
        Integer authenticatieMiddelId = 3;
        Partij partij = StatischeObjecttypeBuilder.bouwGemeente((short) 321, "Ridderkerk");
        BerichtContext context = new BerichtContext(berichtenIds, authenticatieMiddelId, partij, "ref");

        BerichtResultaat resultaat = new BerichtResultaat(null);

        Partij brpGemeenteGeboorte = StatischeObjecttypeBuilder.bouwGemeente((short) 030, "Utrecht");

        Mockito.when(referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 030)))
               .thenReturn(brpGemeenteGeboorte);
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("123234345")))
               .thenReturn(bouwPersoonModel(nieuwgeborene));
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("321432543")))
               .thenReturn(bouwPersoonModel(ouder1));

        // test
        notificator.voorbereiden(bericht, context);
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
        Assert.assertEquals("0321", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getCode());
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
        Assert.assertEquals("0127", berichtArgument.getValue().getAdresNieuwgeborene().getGemeente().getCode());
        Assert.assertEquals("Sassenheim", berichtArgument.getValue().getAdresNieuwgeborene().getGemeente().getNaam());
        Assert.assertEquals("Brink", berichtArgument.getValue().getAdresNieuwgeborene().getStraat());
        Assert.assertEquals("11", berichtArgument.getValue().getAdresNieuwgeborene().getHuisnummer());
        Assert.assertEquals("B", berichtArgument.getValue().getAdresNieuwgeborene().getHuisnummertoevoeging());
        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene().getPlaats());
        Assert.assertEquals("0747", berichtArgument.getValue().getAdresNieuwgeborene().getPlaats().getCode());
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
        List<BetrokkenheidBericht> betrokkenheden = creeerBetrokkenhedenVoorBericht(bericht);

        PersoonBericht nieuwgeborene = PersoonBuilder.bouwPersoon("123234345", Geslachtsaanduiding.MAN, 19660417,
            StatischeObjecttypeBuilder.bouwGemeente((short) 70, null), "Ab", "de", "Vis");
        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();
        nieuwgeborene.setAdressen(adressen);

        BetrokkenheidBericht kind = new BetrokkenheidBericht();
        kind.setRol(SoortBetrokkenheid.KIND);
        kind.setBetrokkene(nieuwgeborene);
        betrokkenheden.add(kind);

        PersoonBericht ouder1 = PersoonBuilder.bouwPersoon("321432543", Geslachtsaanduiding.MAN, 19660417,
            StatischeObjecttypeBuilder.GEMEENTE_UTRECHT, "Ab", "de", "Vis");
        PersoonAdresBericht adresOuder1 = PersoonAdresBuilder.bouwWoonadres("Brink", 11, "1234AA",
            StatischeObjecttypeBuilder.bouwWoonplaats((short) 747, "Vollenhoven"),
            StatischeObjecttypeBuilder.bouwGemeente((short) 10, "Rotterdam"), 20120515);
        adresOuder1.getGegevens().setHuisnummertoevoeging(new Huisnummertoevoeging("B"));
        List<PersoonAdresBericht> adressenOuder1 = new ArrayList<PersoonAdresBericht>();
        adressenOuder1.add(adresOuder1);
        ouder1.setAdressen(adressenOuder1);

        BetrokkenheidBericht ouderschap1 = new BetrokkenheidBericht();
        ouderschap1.setRol(SoortBetrokkenheid.OUDER);
        ouderschap1.setBetrokkene(ouder1);
        betrokkenheden.add(ouderschap1);

        PersoonBericht ouder2 = PersoonBuilder.bouwPersoon("123234345", Geslachtsaanduiding.MAN, 19660417,
            StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM, "Ben", "de", "Bie");
        PersoonAdresBericht adresOuder2 = PersoonAdresBuilder.bouwWoonadres("Brink", 11, "1234AA",
            StatischeObjecttypeBuilder.bouwWoonplaats((short) 747, "Vollenhoven"),
            StatischeObjecttypeBuilder.bouwGemeente((short) 365, "Zeist"), 20120515);
        adresOuder2.getGegevens().setHuisnummertoevoeging(new Huisnummertoevoeging("B"));
        List<PersoonAdresBericht> adressenOuder2 = new ArrayList<PersoonAdresBericht>();
        adressenOuder2.add(adresOuder2);
        ouder2.setAdressen(adressenOuder2);

//        BetrokkenheidBericht ouderschap2 = new BetrokkenheidBericht();
//        ouderschap2.setRol(SoortBetrokkenheid.OUDER);
//        ouderschap2.setBetrokkene(ouder2);
//        betrokkenheden.add(ouderschap2);

        BerichtenIds berichtenIds = new BerichtenIds(1L, 2L);
        Integer authenticatieMiddelId = 3;
        Partij partij = StatischeObjecttypeBuilder.bouwGemeente((short) 321, "Hoorn");
        BerichtContext context = new BerichtContext(berichtenIds, authenticatieMiddelId, partij, "ref");

        List<Melding> meldingen = new ArrayList<Melding>();
        meldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.BRBER00121, "BSN bestaat al"));
        meldingen.add(new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0001, "zxc"));
        BerichtResultaat resultaat = new BerichtResultaat(meldingen);
        resultaat.markeerVerwerkingAlsFoutief();

        Partij brpGemeenteGeboorte = StatischeObjecttypeBuilder.bouwGemeente((short) 30, "Utrecht");

        Mockito.when(referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 30)))
               .thenReturn(brpGemeenteGeboorte);
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("321432543")))
               .thenReturn(bouwPersoonModel(ouder1));
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("123234345")))
               .thenReturn(bouwPersoonModel(ouder2));

        // test
        notificator.voorbereiden(bericht, context);
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
        Assert.assertEquals("0321", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getCode());
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
        Assert.assertEquals("0070", berichtArgument.getValue().getNieuwgeborene().getGemeenteGeboorte().getCode().toString());
        Assert.assertNull(berichtArgument.getValue().getNieuwgeborene().getGemeenteGeboorte().getNaam());

        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene());
        Assert.assertEquals(20120515, berichtArgument.getValue().getAdresNieuwgeborene().getDatumAanvangAdreshouding());
        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene().getGemeente());
        Assert.assertEquals("0010", berichtArgument.getValue().getAdresNieuwgeborene().getGemeente().getCode());
        Assert.assertEquals("Rotterdam", berichtArgument.getValue().getAdresNieuwgeborene().getGemeente().getNaam());
        Assert.assertEquals("Brink", berichtArgument.getValue().getAdresNieuwgeborene().getStraat());
        Assert.assertEquals("11", berichtArgument.getValue().getAdresNieuwgeborene().getHuisnummer());
        Assert.assertEquals("B", berichtArgument.getValue().getAdresNieuwgeborene().getHuisnummertoevoeging());
        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene().getPlaats());
        Assert.assertEquals("0747", berichtArgument.getValue().getAdresNieuwgeborene().getPlaats().getCode());
        Assert.assertEquals("Vollenhoven", berichtArgument.getValue().getAdresNieuwgeborene().getPlaats().getNaam());

        Assert.assertNotNull(berichtArgument.getValue().getOuder1());
        Assert.assertNotNull(berichtArgument.getValue().getOuder1().getBsn());
        Assert.assertEquals(321432543L, berichtArgument.getValue().getOuder1().getBsn().longValue());

        Assert.assertNull(berichtArgument.getValue().getOuder2());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGeboorteBerichtMetOverrule() throws Exception {
        InschrijvingGeboorteBericht bericht = new InschrijvingGeboorteBericht();
        BerichtStuurgegevens berichtStuurgegevens = new BerichtStuurgegevens();
        berichtStuurgegevens.setApplicatie("Digimeente");
        berichtStuurgegevens.setPrevalidatieBericht(JaNee.Ja);
        bericht.setBerichtStuurgegevens(berichtStuurgegevens);
        List<BetrokkenheidBericht> betrokkenheden = creeerBetrokkenhedenVoorBericht(bericht);

        PersoonBericht nieuwgeborene = PersoonBuilder.bouwPersoon("123234345", Geslachtsaanduiding.MAN, 19660417,
            StatischeObjecttypeBuilder.bouwGemeente((short) 30, null), "Ab", "de", "Vis");
        PersoonAdresBericht adres = PersoonAdresBuilder.bouwWoonadres("Brink", 11, "1234AA",
            StatischeObjecttypeBuilder.bouwWoonplaats((short) 747, "Vollenhoven"),
            StatischeObjecttypeBuilder.bouwGemeente((short) 365, "Zeist"), 20120515);
        adres.getGegevens().setHuisnummertoevoeging(new Huisnummertoevoeging("B"));
        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();
        adressen.add(adres);
        nieuwgeborene.setAdressen(adressen);

        BetrokkenheidBericht kind = new BetrokkenheidBericht();
        kind.setRol(SoortBetrokkenheid.KIND);
        kind.setBetrokkene(nieuwgeborene);
        betrokkenheden.add(kind);

        PersoonBericht ouder1 = PersoonBuilder.bouwPersoon("321432543", Geslachtsaanduiding.MAN, 19660417,
            StatischeObjecttypeBuilder.bouwGemeente((short) 30, "Utrecht"), "Ab", "de", "Vis");
        PersoonAdresBericht adresOuder1 = PersoonAdresBuilder.bouwWoonadres("Brink", 11, "1234AA",
            StatischeObjecttypeBuilder.bouwWoonplaats((short) 747, "Vollenhoven"),
            StatischeObjecttypeBuilder.bouwGemeente((short) 365, "Zeist"), 20120515);
        adresOuder1.getGegevens().setHuisnummertoevoeging(new Huisnummertoevoeging("B"));
        List<PersoonAdresBericht> adressenOuder1 = new ArrayList<PersoonAdresBericht>();
        adressenOuder1.add(adresOuder1);
        ouder1.setAdressen(adressenOuder1);

        BetrokkenheidBericht ouderschap1 = new BetrokkenheidBericht();
        ouderschap1.setRol(SoortBetrokkenheid.OUDER);
        ouderschap1.setBetrokkene(ouder1);
        betrokkenheden.add(ouderschap1);

        PersoonBericht ouder2 = PersoonBuilder.bouwPersoon("987654321", Geslachtsaanduiding.MAN, 19660417,
            StatischeObjecttypeBuilder.bouwGemeente((short) 30, "Utrecht"), "Ab", "de", "Vis");
        PersoonAdresBericht adresOuder2 = PersoonAdresBuilder.bouwWoonadres("Brink", 11, "1234AA",
            StatischeObjecttypeBuilder.bouwWoonplaats((short) 747, "Vollenhoven"),
            StatischeObjecttypeBuilder.bouwGemeente((short) 365, "Zeist"), 20120515);
        adresOuder2.getGegevens().setHuisnummertoevoeging(new Huisnummertoevoeging("B"));
        List<PersoonAdresBericht> adressenOuder2 = new ArrayList<PersoonAdresBericht>();
        adressenOuder2.add(adresOuder2);
        ouder2.setAdressen(adressenOuder2);

        BetrokkenheidBericht ouderschap2 = new BetrokkenheidBericht();
        ouderschap2.setRol(SoortBetrokkenheid.OUDER);
        ouderschap2.setBetrokkene(ouder2);
        betrokkenheden.add(ouderschap2);

        BerichtenIds berichtenIds = new BerichtenIds(1L, 2L);
        Integer authenticatieMiddelId = 3;
        Partij partij = StatischeObjecttypeBuilder.bouwGemeente((short) 321, "Ridderkerk");
        BerichtContext context = new BerichtContext(berichtenIds, authenticatieMiddelId, partij, "ref");

        List<Melding> meldingen = new ArrayList<Melding>();
        meldingen.add(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.BRBER00121, "BSN bestaat al"));
        meldingen.add(new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0001, "zxc"));
        meldingen.add(new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0002, "asd"));
        meldingen.add(new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0001, "qwe"));
        meldingen.add(new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.AUTH0001, "qaz"));

        BerichtResultaat resultaat = new BerichtResultaat(meldingen);

        List<OverruleMelding> overruleMeldingen = new ArrayList<OverruleMelding>();
        overruleMeldingen.add(new OverruleMelding(MeldingCode.ALG0001));
        overruleMeldingen.add(new OverruleMelding(MeldingCode.AUTH0001));
        resultaat.setOverruleMeldingen(overruleMeldingen);

        Partij brpGemeenteGeboorte = StatischeObjecttypeBuilder.bouwGemeente((short) 30, "Utrecht");

        Mockito.when(referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 30)))
               .thenReturn(brpGemeenteGeboorte);
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("123234345")))
               .thenReturn(bouwPersoonModel(nieuwgeborene));
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("321432543")))
               .thenReturn(bouwPersoonModel(ouder1));
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("987654321")))
               .thenReturn(bouwPersoonModel(ouder2));

        // test
        notificator.voorbereiden(bericht, context);
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
        Assert.assertEquals("0321", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getCode());
        Assert.assertEquals("Ridderkerk", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getNaam());
        Assert.assertEquals("Digimeente", berichtArgument.getValue().getKenmerken().getBurgerZakenModuleNaam());
        Assert.assertTrue(berichtArgument.getValue().getKenmerken().isPrevalidatie());

        Assert.assertNotNull(berichtArgument.getValue().getVerwerking());
        Assert.assertNotNull(berichtArgument.getValue().getVerwerking().getVerwerkingsmoment());
        Assert.assertEquals(VerwerkingStatus.GESLAAGD, berichtArgument.getValue().getVerwerking().getStatus());

        Assert.assertNotNull(berichtArgument.getValue().getMeldingen());
        Assert.assertEquals(5, berichtArgument.getValue().getMeldingen().size());
        Assert.assertNotNull(berichtArgument.getValue().getMeldingen().get(0));
        Assert.assertEquals(MeldingSoort.WAARSCHUWING, berichtArgument.getValue().getMeldingen().get(0).getSoort());
        Assert.assertEquals("BSN bestaat al", berichtArgument.getValue().getMeldingen().get(0).getTekst());
        Assert.assertNotNull(berichtArgument.getValue().getMeldingen().get(1));
        Assert.assertEquals(MeldingSoort.GENEGEERDE_FOUT, berichtArgument.getValue().getMeldingen().get(1).getSoort());
        Assert.assertEquals("zxc", berichtArgument.getValue().getMeldingen().get(1).getTekst());
        Assert.assertNotNull(berichtArgument.getValue().getMeldingen().get(2));
        Assert.assertEquals(MeldingSoort.OVERRULEBAAR, berichtArgument.getValue().getMeldingen().get(2).getSoort());
        Assert.assertEquals("asd", berichtArgument.getValue().getMeldingen().get(2).getTekst());
        Assert.assertNotNull(berichtArgument.getValue().getMeldingen().get(3));
        Assert.assertEquals(MeldingSoort.GENEGEERDE_FOUT, berichtArgument.getValue().getMeldingen().get(3).getSoort());
        Assert.assertEquals("qwe", berichtArgument.getValue().getMeldingen().get(3).getTekst());
        Assert.assertNotNull(berichtArgument.getValue().getMeldingen().get(4));
        Assert.assertEquals(MeldingSoort.GENEGEERDE_FOUT, berichtArgument.getValue().getMeldingen().get(4).getSoort());
        Assert.assertEquals("qaz", berichtArgument.getValue().getMeldingen().get(4).getTekst());

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
        Assert.assertEquals("0365", berichtArgument.getValue().getAdresNieuwgeborene().getGemeente().getCode());
        Assert.assertEquals("Zeist", berichtArgument.getValue().getAdresNieuwgeborene().getGemeente().getNaam());
        Assert.assertEquals("Brink", berichtArgument.getValue().getAdresNieuwgeborene().getStraat());
        Assert.assertEquals("11", berichtArgument.getValue().getAdresNieuwgeborene().getHuisnummer());
        Assert.assertEquals("B", berichtArgument.getValue().getAdresNieuwgeborene().getHuisnummertoevoeging());
        Assert.assertNotNull(berichtArgument.getValue().getAdresNieuwgeborene().getPlaats());
        Assert.assertEquals("0747", berichtArgument.getValue().getAdresNieuwgeborene().getPlaats().getCode());
        Assert.assertEquals("Vollenhoven", berichtArgument.getValue().getAdresNieuwgeborene().getPlaats().getNaam());

        List<Long> ouderBsns = new ArrayList<Long>();
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

    /**
     * Bouwt een {@link PersoonModel} instantie op basis van de opgegeven {@link PersoonBericht} instantie, waarbij
     * tevens de eventuele adressen van de persoon worden omgezet.
     *
     * @param persoon de persoon die moet worden omgezet
     * @return het persoon model object
     */
    private PersoonModel bouwPersoonModel(final PersoonBericht persoon) {
        final PersoonModel persoonModel = new PersoonModel(persoon);
        if (persoon.getAdressen() != null && !persoon.getAdressen().isEmpty()) {
            for (PersoonAdresBericht adresBericht : persoon.getAdressen()) {
                persoonModel.getAdressen().add(new PersoonAdresModel(adresBericht, persoonModel));
            }
        }
        return persoonModel;
    }
}
