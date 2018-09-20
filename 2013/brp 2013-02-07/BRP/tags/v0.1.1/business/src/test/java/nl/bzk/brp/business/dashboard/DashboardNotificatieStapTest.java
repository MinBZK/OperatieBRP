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
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.dashboard.VerhuisBerichtRequest;
import nl.bzk.brp.model.dashboard.VerwerkingStatus;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.logisch.groep.PersoonSamengesteldeNaam;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;


public class DashboardNotificatieStapTest {

    private DashboardNotificatieStap stap;

    @Mock
    private RestTemplate             restTemplate;

    @Mock
    private PersoonRepository        persoonRepository;

    @Before
    public void init() throws URISyntaxException {
        MockitoAnnotations.initMocks(this);
        stap = new DashboardNotificatieStap();
        stap.setVerhuizingNotificatieActief(true);
        stap.setDashboardUri(new URI("http://localhost:8080/brp-dashboard/service/berichten/opslaan/verhuizing"));
        ReflectionTestUtils.setField(stap, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(stap, "persoonRepository", persoonRepository);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test() throws Exception {
        VerhuizingBericht bericht = new VerhuizingBericht();
        BerichtStuurgegevens berichtStuurgegevens = new BerichtStuurgegevens();
        berichtStuurgegevens.setApplicatie("Digimeente");
        bericht.setBerichtStuurgegevens(berichtStuurgegevens);
        List<BRPActie> brpActies = new ArrayList<BRPActie>();
        BRPActie brpActie = new BRPActie();
        List<RootObject> rootObjecten = new ArrayList<RootObject>();

        Persoon persoonIn = new Persoon();
        PersoonIdentificatienummers identificatienummersIn = new PersoonIdentificatienummers();
        identificatienummersIn.setBurgerservicenummer("123234345");
        persoonIn.setIdentificatienummers(identificatienummersIn);

        Persoon persoon = new Persoon();
        PersoonIdentificatienummers identificatienummers = new PersoonIdentificatienummers();
        identificatienummers.setBurgerservicenummer("123234345");
        persoon.setIdentificatienummers(identificatienummers);
        PersoonGeboorte persoonGeboorte = new PersoonGeboorte();
        persoonGeboorte.setDatumGeboorte(19660417);
        Partij gemeenteGeboorte = new Partij();
        gemeenteGeboorte.setGemeentecode("Utrecht");
        persoonGeboorte.setGemeenteGeboorte(gemeenteGeboorte);
        persoon.setGeboorte(persoonGeboorte);
        PersoonSamengesteldeNaam samengesteldenaam = new PersoonSamengesteldeNaam();
        samengesteldenaam.setVoornamen("Ab");
        samengesteldenaam.setGeslachtsnaam("Vis");
        samengesteldenaam.setVoorvoegsel("de");
        persoon.setSamengesteldenaam(samengesteldenaam);
        Set<PersoonAdres> adressen = new HashSet<PersoonAdres>();
        PersoonAdres adres = new PersoonAdres();
        adres.setDatumAanvangAdreshouding(20120515);
        Partij gemeente = new Partij();
        gemeente.setGemeentecode("Zeist");
        adres.setGemeente(gemeente);
        adres.setAfgekorteNaamOpenbareRuimte("Brink");
        adres.setHuisnummer("11");
        adres.setHuisnummertoevoeging("B");
        Plaats woonplaats = new Plaats();
        woonplaats.setWoonplaatscode("747");
        woonplaats.setNaam("Vollenhoven");
        adres.setWoonplaats(woonplaats);
        adressen.add(adres);
        persoon.setAdressen(adressen);
        rootObjecten.add(persoon);
        brpActie.setRootObjecten(rootObjecten);
        brpActies.add(brpActie);
        bericht.setBrpActies(brpActies);

        BerichtenIds berichtenIds = new BerichtenIds(1L, 2L);
        Integer authenticatieMiddelId = 3;
        Partij partij = new Partij();
        partij.setGemeentecode("321");
        BerichtContext context = new BerichtContext(berichtenIds, authenticatieMiddelId, partij);

        BerichtResultaat resultaat = new BerichtResultaat(null);

        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer("123234345")).thenReturn(persoon);

        // test
        stap.voerVerwerkingsStapUitVoorBericht(bericht, context, resultaat);

        ArgumentCaptor<URI> uriArgument = ArgumentCaptor.forClass(URI.class);
        ArgumentCaptor<VerhuisBerichtRequest> berichtArgument = ArgumentCaptor.forClass(VerhuisBerichtRequest.class);

        @SuppressWarnings("rawtypes")
        ArgumentCaptor<Class> typeArgument = ArgumentCaptor.forClass(Class.class);

        Mockito.verify(restTemplate).postForEntity(uriArgument.capture(), berichtArgument.capture(),
                typeArgument.capture());

        Assert.assertEquals("http://localhost:8080/brp-dashboard/service/berichten/opslaan/verhuizing", uriArgument
                .getValue().toASCIIString());

        Assert.assertNotNull(berichtArgument.getValue().getKenmerken());

        // TODO friso: omdat de Partij class nog geen naam property heeft geldt naam = code
        Assert.assertEquals("321", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getCode());
        Assert.assertEquals("321", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getNaam());

        Assert.assertEquals("Digimeente", berichtArgument.getValue().getKenmerken().getBurgerZakenModuleNaam());

        // TODO friso: hardcoded false omdat de BerichtStuurgegevens class nog geen prevalidatie vlag heeft
        Assert.assertFalse(berichtArgument.getValue().getKenmerken().isPrevalidatie());

        Assert.assertNotNull(berichtArgument.getValue().getVerwerking());
        Assert.assertNotNull(berichtArgument.getValue().getVerwerking().getVerwerkingsmoment());
        Assert.assertEquals(VerwerkingStatus.GESLAAGD, berichtArgument.getValue().getVerwerking().getStatus());

        Assert.assertNotNull(berichtArgument.getValue().getPersoon());
        Assert.assertNotNull(berichtArgument.getValue().getPersoon().getBsn());
        Assert.assertEquals(123234345L, berichtArgument.getValue().getPersoon().getBsn().longValue());
        Assert.assertNotNull(berichtArgument.getValue().getPersoon().getGeslachtsnaamcomponenten());

        Assert.assertNotNull(berichtArgument.getValue().getPersoon().getVoornamen());
        Assert.assertEquals("Ab", berichtArgument.getValue().getPersoon().getVoornamen().get(0));

        // TODO friso: test toevoegen voor persoon met meerdere voor- en/of achternamen

        Assert.assertEquals("de", berichtArgument.getValue().getPersoon().getGeslachtsnaamcomponenten().get(0)
                .getVoorvoegsel());
        Assert.assertEquals("Vis", berichtArgument.getValue().getPersoon().getGeslachtsnaamcomponenten().get(0)
                .getNaam());

        Assert.assertNotNull(berichtArgument.getValue().getNieuwAdres());
        Assert.assertEquals(20120515, berichtArgument.getValue().getNieuwAdres().getDatumAanvangAdreshouding());
        Assert.assertNotNull(berichtArgument.getValue().getNieuwAdres().getGemeente());

        // TODO friso: omdat de Partij class nog geen naam property heeft geldt naam = code
        Assert.assertEquals("Zeist", berichtArgument.getValue().getNieuwAdres().getGemeente().getCode());
        Assert.assertEquals("Zeist", berichtArgument.getValue().getNieuwAdres().getGemeente().getNaam());

        Assert.assertEquals("Brink", berichtArgument.getValue().getNieuwAdres().getStraat());
        Assert.assertEquals("11", berichtArgument.getValue().getNieuwAdres().getHuisnummer());
        Assert.assertEquals("B", berichtArgument.getValue().getNieuwAdres().getHuisnummertoevoeging());
        Assert.assertNotNull(berichtArgument.getValue().getNieuwAdres().getPlaats());
        Assert.assertEquals("747", berichtArgument.getValue().getNieuwAdres().getPlaats().getCode());
        Assert.assertEquals("Vollenhoven", berichtArgument.getValue().getNieuwAdres().getPlaats().getNaam());
    }

}
