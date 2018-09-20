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
import nl.bzk.brp.business.dto.bijhouding.CorrectieAdresBericht;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.dashboard.AdresCorrectieBerichtRequest;
import nl.bzk.brp.model.dashboard.VerwerkingStatus;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonVoornaamBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonVoornaamModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
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


public class AdrescorrectieNotificatorTest {

    private AdrescorrectieNotificator       notificator;

    @Mock
    private RestTemplate                    restTemplate;

    @Mock
    private DashboardNotificatieServiceImpl service;

    @Mock
    private PersoonRepository               persoonRepository;

    @Before
    public void init() throws URISyntaxException {
        MockitoAnnotations.initMocks(this);
        notificator = new AdrescorrectieNotificator();
        notificator.setDashboardUri(new URI(
                "http://localhost:8080/brp-dashboard/service/berichten/opslaan/adrescorrectie"));
        ReflectionTestUtils.setField(service, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(notificator, "dashboardNotificatieService", service);
        ReflectionTestUtils.setField(notificator, "persoonRepository", persoonRepository);
    }

    @Test
    public void testSunnyDay() throws Exception {
        CorrectieAdresBericht bericht = new CorrectieAdresBericht();
        BerichtStuurgegevens berichtStuurgegevens = new BerichtStuurgegevens();
        berichtStuurgegevens.setApplicatie("Digimeente");
        bericht.setBerichtStuurgegevens(berichtStuurgegevens);
        List<Actie> brpActies = new ArrayList<Actie>();

        PersoonBericht persoon =
            PersoonBuilder.bouwPersoon("123234345", Geslachtsaanduiding.MAN, 19660417,
                    StatischeObjecttypeBuilder.GEMEENTE_UTRECHT, "Ab", "de", "Vis");
        PersoonAdresBericht adres =
            PersoonAdresBuilder.bouwWoonadres("Langstraat", 10, "1234AA",
                    StatischeObjecttypeBuilder.bouwWoonplaats((short) 747, "Vollenhoven"),
                    StatischeObjecttypeBuilder.bouwGemeente((short) 365, "Zeist"), 20120515);
        adres.getGegevens().setHuisnummertoevoeging(new Huisnummertoevoeging("B"));
        brpActies.add(PersoonAdresBuilder.bouwAdrescorrectieActie(new Datum(20120303), null, persoon, adres));

        bericht.setBrpActies(brpActies);

        BerichtenIds berichtenIds = new BerichtenIds(1L, 2L);
        Partij partij = StatischeObjecttypeBuilder.bouwGemeente((short) 321, "Hoorn");
        BerichtContext context = new BerichtContext(berichtenIds, 3, partij, "ref");

        BerichtResultaat resultaat = new BerichtResultaat(null);

        PersoonModel persoonModel = bouwPersoonModel(persoon);
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("123234345")))
                .thenReturn(persoonModel);

        // test
        notificator.voorbereiden(bericht, context);
        notificator.notificeerDashboard(bericht, context, resultaat);

        ArgumentCaptor<URI> uriArgument = ArgumentCaptor.forClass(URI.class);
        ArgumentCaptor<AdresCorrectieBerichtRequest> berichtArgument =
            ArgumentCaptor.forClass(AdresCorrectieBerichtRequest.class);

        Mockito.verify(service).notificeerDashboard(uriArgument.capture(), berichtArgument.capture());

        Assert.assertEquals("http://localhost:8080/brp-dashboard/service/berichten/opslaan/adrescorrectie", uriArgument
                .getValue().toASCIIString());

        Assert.assertNotNull(berichtArgument.getValue().getKenmerken());
        Assert.assertEquals("0321", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getCode());
        Assert.assertEquals("Hoorn", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getNaam());
        Assert.assertEquals("Digimeente", berichtArgument.getValue().getKenmerken().getBurgerZakenModuleNaam());
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

        Assert.assertNotNull(berichtArgument.getValue().getAdressen().get(0));
        Assert.assertEquals(20120303, berichtArgument.getValue().getAdressen().get(0).getDatumAanvang());
        Assert.assertNull(berichtArgument.getValue().getAdressen().get(0).getDatumEinde());
        Assert.assertNotNull(berichtArgument.getValue().getAdressen().get(0).getGemeente());
        Assert.assertEquals("0365", berichtArgument.getValue().getAdressen().get(0).getGemeente().getCode());
        Assert.assertEquals("Zeist", berichtArgument.getValue().getAdressen().get(0).getGemeente().getNaam());
        Assert.assertEquals("Langstr", berichtArgument.getValue().getAdressen().get(0).getStraat());
        Assert.assertEquals("10", berichtArgument.getValue().getAdressen().get(0).getHuisnummer());
        Assert.assertEquals("B", berichtArgument.getValue().getAdressen().get(0).getHuisnummertoevoeging());
        Assert.assertNotNull(berichtArgument.getValue().getAdressen().get(0).getPlaats());
        Assert.assertEquals("0747", berichtArgument.getValue().getAdressen().get(0).getPlaats().getCode());
        Assert.assertEquals("Vollenhoven", berichtArgument.getValue().getAdressen().get(0).getPlaats().getNaam());
        Assert.assertNull(berichtArgument.getValue().getAdressen().get(0).getDatumEinde());
    }

    @Test
    public void testMetMeerdereCorrecties() throws Exception {
        CorrectieAdresBericht bericht = new CorrectieAdresBericht();
        BerichtStuurgegevens berichtStuurgegevens = new BerichtStuurgegevens();
        berichtStuurgegevens.setApplicatie("Digimeente");
        bericht.setBerichtStuurgegevens(berichtStuurgegevens);
        List<Actie> brpActies = new ArrayList<Actie>();

        PersoonBericht persoon1 =
            PersoonBuilder.bouwPersoon("123234345", Geslachtsaanduiding.MAN, 19660417,
                    StatischeObjecttypeBuilder.GEMEENTE_UTRECHT, "Ab", "de", "Vis");
        PersoonBericht persoon2 =
            PersoonBuilder.bouwPersoon("123234345", Geslachtsaanduiding.MAN, 19660417,
                    StatischeObjecttypeBuilder.GEMEENTE_UTRECHT, "Ab", "de", "Vis");
        PersoonAdresBericht adres1 =
            PersoonAdresBuilder.bouwWoonadres("Langstraat", 10, "1234AA",
                    StatischeObjecttypeBuilder.bouwWoonplaats((short) 747, "Vollenhoven"),
                    StatischeObjecttypeBuilder.bouwGemeente((short) 365, "Zeist"), 20120515);
        PersoonAdresBericht adres2 =
            PersoonAdresBuilder.bouwWoonadres("Kortstraat", 10, "1234AA",
                    StatischeObjecttypeBuilder.bouwWoonplaats((short) 747, "Vollenhoven"),
                    StatischeObjecttypeBuilder.bouwGemeente((short) 365, "Zeist"), 20120515);

        brpActies.add(PersoonAdresBuilder.bouwAdrescorrectieActie(new Datum(20120303), new Datum(20130303), persoon1,
                adres1));
        brpActies.add(PersoonAdresBuilder.bouwAdrescorrectieActie(new Datum(20140303), null, persoon2, adres2));

        bericht.setBrpActies(brpActies);

        BerichtenIds berichtenIds = new BerichtenIds(1L, 2L);
        Partij partij = StatischeObjecttypeBuilder.bouwGemeente((short) 321, "Hoorn");
        BerichtContext context = new BerichtContext(berichtenIds, 3, partij, "ref");

        BerichtResultaat resultaat = new BerichtResultaat(null);

        PersoonModel persoonModel = bouwPersoonModel(persoon1);
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("123234345")))
                .thenReturn(persoonModel);

        // test
        notificator.voorbereiden(bericht, context);
        notificator.notificeerDashboard(bericht, context, resultaat);

        ArgumentCaptor<URI> uriArgument = ArgumentCaptor.forClass(URI.class);
        ArgumentCaptor<AdresCorrectieBerichtRequest> berichtArgument =
            ArgumentCaptor.forClass(AdresCorrectieBerichtRequest.class);

        Mockito.verify(service).notificeerDashboard(uriArgument.capture(), berichtArgument.capture());

        Assert.assertEquals("http://localhost:8080/brp-dashboard/service/berichten/opslaan/adrescorrectie", uriArgument
                .getValue().toASCIIString());

        Assert.assertNotNull(berichtArgument.getValue().getKenmerken());
        Assert.assertEquals("0321", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getCode());
        Assert.assertEquals("Hoorn", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getNaam());
        Assert.assertEquals("Digimeente", berichtArgument.getValue().getKenmerken().getBurgerZakenModuleNaam());
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

        Assert.assertNotNull(berichtArgument.getValue().getAdressen().get(0));
        Assert.assertEquals(20120303, berichtArgument.getValue().getAdressen().get(0).getDatumAanvang());
        Assert.assertEquals(20130303, berichtArgument.getValue().getAdressen().get(0).getDatumEinde().intValue());
        Assert.assertEquals("Langstr", berichtArgument.getValue().getAdressen().get(0).getStraat());

        Assert.assertNotNull(berichtArgument.getValue().getAdressen().get(1));
        Assert.assertEquals(20140303, berichtArgument.getValue().getAdressen().get(1).getDatumAanvang());
        Assert.assertNull(berichtArgument.getValue().getAdressen().get(1).getDatumEinde());
        Assert.assertEquals("Kortstr", berichtArgument.getValue().getAdressen().get(1).getStraat());
    }

    /**
     * Bouwt een {@link PersoonModel} instantie op basis van de opgegeven {@link PersoonBericht} instantie, waarbij
     * tevens de eventuele adressen, geslachtsnaamcomponenten en voornamen van de persoon worden omgezet.
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
        if (persoon.getPersoonVoornaam() != null && !persoon.getPersoonVoornaam().isEmpty()) {
            for (PersoonVoornaamBericht voornaamBericht : persoon.getPersoonVoornaam()) {
                persoonModel.getPersoonVoornaam().add(new PersoonVoornaamModel(voornaamBericht, persoonModel));
            }
        }
        if (persoon.getGeslachtsnaamcomponenten() != null && !persoon.getGeslachtsnaamcomponenten().isEmpty()) {
            for (PersoonGeslachtsnaamcomponentBericht geslachtsnaamcomponentBericht : persoon
                    .getGeslachtsnaamcomponenten())
            {
                persoonModel.getGeslachtsnaamcomponenten().add(
                        new PersoonGeslachtsnaamcomponentModel(geslachtsnaamcomponentBericht, persoonModel));
            }
        }
        return persoonModel;
    }
}
