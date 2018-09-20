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
import nl.bzk.brp.business.dto.bijhouding.HuwelijkEnGeregistreerdPartnerschapBericht;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.dashboard.HuwelijkBerichtRequest;
import nl.bzk.brp.model.dashboard.VerwerkingStatus;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
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


public class HuwelijkNotificatorTest {

    private HuwelijkNotificator             notificator;

    @Mock
    private RestTemplate                    restTemplate;

    @Mock
    private DashboardNotificatieServiceImpl service;

    @Mock
    private PersoonRepository               persoonRepository;

    @Mock
    private ReferentieDataRepository        referentieDataRepository;

    @Before
    public void init() throws URISyntaxException {
        MockitoAnnotations.initMocks(this);
        notificator = new HuwelijkNotificator();
        notificator.setDashboardUri(new URI("http://localhost:8080/brp-dashboard/service/berichten/opslaan/huwelijk"));
        ReflectionTestUtils.setField(service, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(notificator, "dashboardNotificatieService", service);
        ReflectionTestUtils.setField(notificator, "persoonRepository", persoonRepository);
    }

    @Test
    public void testHuwelijkBericht() throws Exception {
        HuwelijkEnGeregistreerdPartnerschapBericht bericht = new HuwelijkEnGeregistreerdPartnerschapBericht();
        BerichtStuurgegevens berichtStuurgegevens = new BerichtStuurgegevens();
        berichtStuurgegevens.setApplicatie("Digimeente");
        berichtStuurgegevens.setPrevalidatieBericht(JaNee.Ja);
        bericht.setBerichtStuurgegevens(berichtStuurgegevens);

        List<Actie> brpActies = new ArrayList<Actie>();
        ActieBericht brpActie = new ActieBericht();
        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        RelatieBericht huwelijk = new RelatieBericht();
        List<BetrokkenheidBericht> betrokkenheden = new ArrayList<BetrokkenheidBericht>();

        bericht.setBrpActies(brpActies);
        brpActies.add(brpActie);
        brpActie.setRootObjecten(rootObjecten);
        rootObjecten.add(huwelijk);
        huwelijk.setBetrokkenheden(betrokkenheden);
        RelatieStandaardGroepBericht gegevens = new RelatieStandaardGroepBericht();
        gegevens.setDatumAanvang(new Datum(20120822));
        gegevens.setWoonPlaatsAanvangCode(new PlaatsCode((short) 123));
        huwelijk.setGegevens(gegevens);

        PersoonBericht partner1 =
            PersoonBuilder.bouwPersoon("321432543", Geslachtsaanduiding.MAN, 19660417,
                    StatischeObjecttypeBuilder.bouwGemeente((short) 0344, null), "Ab", "de", "Vis");

        BetrokkenheidBericht partnerschap1 = new BetrokkenheidBericht();
        partnerschap1.setRol(SoortBetrokkenheid.PARTNER);
        partnerschap1.setBetrokkene(partner1);
        betrokkenheden.add(partnerschap1);

        PersoonBericht partner2 =
            PersoonBuilder.bouwPersoon("987654321", Geslachtsaanduiding.MAN, 19660417,
                    StatischeObjecttypeBuilder.bouwGemeente((short) 344, null), "Ab", "de", "Vis");

        BetrokkenheidBericht partnerschap2 = new BetrokkenheidBericht();
        partnerschap2.setRol(SoortBetrokkenheid.PARTNER);
        partnerschap2.setBetrokkene(partner2);
        betrokkenheden.add(partnerschap2);

        BerichtenIds berichtenIds = new BerichtenIds(1L, 2L);
        Integer authenticatieMiddelId = 3;
        Partij partij = StatischeObjecttypeBuilder.bouwGemeente((short) 321, "Ridderkerk");
        BerichtContext context = new BerichtContext(berichtenIds, authenticatieMiddelId, partij, "ref");

        BerichtResultaat resultaat = new BerichtResultaat(null);

        Partij brpGemeenteGeboorte = StatischeObjecttypeBuilder.bouwGemeente((short) 344, "Utrecht");

        Mockito.when(referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 344))).thenReturn(
                brpGemeenteGeboorte);
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("321432543")))
                .thenReturn(bouwPersoonModel(partner1));
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("987654321")))
                .thenReturn(bouwPersoonModel(partner2));

        // test
        notificator.voorbereiden(bericht, context);
        notificator.notificeerDashboard(bericht, context, resultaat);

        ArgumentCaptor<URI> uriArgument = ArgumentCaptor.forClass(URI.class);
        ArgumentCaptor<HuwelijkBerichtRequest> berichtArgument = ArgumentCaptor.forClass(HuwelijkBerichtRequest.class);

        Mockito.verify(service).notificeerDashboard(uriArgument.capture(), berichtArgument.capture());

        Assert.assertEquals("http://localhost:8080/brp-dashboard/service/berichten/opslaan/huwelijk", uriArgument
                .getValue().toASCIIString());

        Assert.assertNotNull(berichtArgument.getValue().getKenmerken());
        Assert.assertEquals("0321", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getCode());
        Assert.assertEquals("Ridderkerk", berichtArgument.getValue().getKenmerken().getVerzendendePartij().getNaam());
        Assert.assertEquals("Digimeente", berichtArgument.getValue().getKenmerken().getBurgerZakenModuleNaam());
        Assert.assertTrue(berichtArgument.getValue().getKenmerken().isPrevalidatie());

        Assert.assertNotNull(berichtArgument.getValue().getVerwerking());
        Assert.assertNotNull(berichtArgument.getValue().getVerwerking().getVerwerkingsmoment());
        Assert.assertEquals(VerwerkingStatus.GESLAAGD, berichtArgument.getValue().getVerwerking().getStatus());

        List<Long> ouderBsns = new ArrayList<Long>();
        ouderBsns.add(321432543L);
        ouderBsns.add(987654321L);

        Assert.assertNotNull(berichtArgument.getValue().getPersoon1());
        Assert.assertNotNull(berichtArgument.getValue().getPersoon2());
        Assert.assertNotNull(berichtArgument.getValue().getPersoon1().getBsn());
        Assert.assertNotNull(berichtArgument.getValue().getPersoon2().getBsn());
        Long ouder1bsn = berichtArgument.getValue().getPersoon1().getBsn();
        Long ouder2bsn = berichtArgument.getValue().getPersoon2().getBsn();
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
