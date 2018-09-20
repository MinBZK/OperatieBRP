/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.huwelijkpartnerschap;

import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class BRBY0401Test {

    private BRBY0401          brby0401;

    @Mock
    private PersoonRepository persoonRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        brby0401 = new BRBY0401();

        ReflectionTestUtils.setField(brby0401, "persoonRepository", persoonRepository);
    }

    @Test
    public void testBeidePartnersMinderjarig() {
        HuwelijkGeregistreerdPartnerschapBericht huwelijk =
            new RelatieBuilder<HuwelijkGeregistreerdPartnerschapBericht>().bouwHuwlijkRelatie()
                    .voegPartnerToe(PersoonBuilder.bouwPersoon(111, null, 19840404, null, null, null, null))
                    .voegPartnerToe(PersoonBuilder.bouwPersoon(22222, null, 19850404, null, null, null, null))
                    .setDatumAanvang(20010404).getRelatie();

        PersoonModel manModel = null;
        PersoonModel vrouwModel = null;
        for (BetrokkenheidBericht betrokkenheidBericht : huwelijk.getBetrokkenheden()) {
            if (betrokkenheidBericht.getPersoon().getIdentificatienummers().getBurgerservicenummer()
                    .equals(new Burgerservicenummer("111")))
            {
                manModel = new PersoonModel(betrokkenheidBericht.getPersoon());
            } else {
                vrouwModel = new PersoonModel(betrokkenheidBericht.getPersoon());
            }
        }

        PersoonBuilder.voegNederlandseNationaliteitToe(manModel, vrouwModel);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("111"))).thenReturn(manModel);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("22222"))).thenReturn(
                vrouwModel);

        List<Melding> meldingen = brby0401.executeer(null, huwelijk, null);
        Assert.assertEquals(2, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0401, meldingen.get(0).getCode());
        Assert.assertEquals(SoortMelding.DEBLOKKEERBAAR, meldingen.get(0).getSoort());
        Assert.assertEquals(
                "De minimumleeftijd voor trouwen of geregistreerd partnerschap aangaan is 18 jaar voor partners met "
                    + "Nederlandse nationaliteit.", meldingen.get(0).getOmschrijving());
    }

    @Test
    public void testBeidePartnersMeerderjarig() {
        HuwelijkGeregistreerdPartnerschapBericht huwelijk =
            new RelatieBuilder<HuwelijkGeregistreerdPartnerschapBericht>().bouwHuwlijkRelatie()
                    .voegPartnerToe(PersoonBuilder.bouwPersoon(111, null, 19800404, null, null, null, null))
                    .voegPartnerToe(PersoonBuilder.bouwPersoon(22222, null, 19790404, null, null, null, null))
                    .setDatumAanvang(20010404).getRelatie();

        PersoonModel manModel = null;
        PersoonModel vrouwModel = null;
        for (BetrokkenheidBericht betr : huwelijk.getBetrokkenheden()) {
            if (betr.getPersoon().getIdentificatienummers().getBurgerservicenummer()
                    .equals(new Burgerservicenummer("111")))
            {
                manModel = new PersoonModel(betr.getPersoon());
            } else {
                vrouwModel = new PersoonModel(betr.getPersoon());
            }
        }

        PersoonBuilder.voegNederlandseNationaliteitToe(manModel, vrouwModel);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("111"))).thenReturn(manModel);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("22222"))).thenReturn(
                vrouwModel);

        List<Melding> meldingen = brby0401.executeer(null, huwelijk, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testBeidePartners18Jaar() {
        HuwelijkGeregistreerdPartnerschapBericht huwelijk =
            new RelatieBuilder<HuwelijkGeregistreerdPartnerschapBericht>().bouwHuwlijkRelatie()
                    .voegPartnerToe(PersoonBuilder.bouwPersoon(111, null, 19830404, null, null, null, null))
                    .voegPartnerToe(PersoonBuilder.bouwPersoon(22222, null, 19830404, null, null, null, null))
                    .setDatumAanvang(20010404).getRelatie();

        PersoonModel manModel = null;
        PersoonModel vrouwModel = null;
        for (BetrokkenheidBericht betr : huwelijk.getBetrokkenheden()) {
            if (betr.getPersoon().getIdentificatienummers().getBurgerservicenummer()
                    .equals(new Burgerservicenummer("111")))
            {
                manModel = new PersoonModel(betr.getPersoon());
            } else {
                vrouwModel = new PersoonModel(betr.getPersoon());
            }
        }

        PersoonBuilder.voegNederlandseNationaliteitToe(manModel, vrouwModel);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("111"))).thenReturn(manModel);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("22222"))).thenReturn(
                vrouwModel);

        List<Melding> meldingen = brby0401.executeer(null, huwelijk, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testVrouwMinderjarigVerschilMetHuwelijksDatum1Maand() {
        HuwelijkGeregistreerdPartnerschapBericht huwelijk =
            new RelatieBuilder<HuwelijkGeregistreerdPartnerschapBericht>().bouwHuwlijkRelatie()
                    .voegPartnerToe(PersoonBuilder.bouwPersoon(111, null, 19830404, null, null, null, null))
                    .voegPartnerToe(PersoonBuilder.bouwPersoon(22222, null, 19830504, null, null, null, null))
                    .setDatumAanvang(20010404).getRelatie();

        PersoonModel manModel = null;
        PersoonModel vrouwModel = null;
        for (BetrokkenheidBericht betr : huwelijk.getBetrokkenheden()) {
            if (betr.getPersoon().getIdentificatienummers().getBurgerservicenummer()
                    .equals(new Burgerservicenummer("111")))
            {
                manModel = new PersoonModel(betr.getPersoon());
            } else {
                vrouwModel = new PersoonModel(betr.getPersoon());
            }
        }

        PersoonBuilder.voegNederlandseNationaliteitToe(manModel, vrouwModel);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("111"))).thenReturn(manModel);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("22222"))).thenReturn(
                vrouwModel);

        List<Melding> meldingen = brby0401.executeer(null, huwelijk, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0401, meldingen.get(0).getCode());
        Assert.assertEquals(SoortMelding.DEBLOKKEERBAAR, meldingen.get(0).getSoort());
        Assert.assertEquals(
                "De minimumleeftijd voor trouwen of geregistreerd partnerschap aangaan is 18 jaar voor partners met "
                    + "Nederlandse nationaliteit.", meldingen.get(0).getOmschrijving());
    }

    @Test
    public void testVrouwMinderjarigVerschilMetHuwelijksDatum1Dag() {
        HuwelijkGeregistreerdPartnerschapBericht huwelijk =
            new RelatieBuilder<HuwelijkGeregistreerdPartnerschapBericht>().bouwHuwlijkRelatie()
                    .voegPartnerToe(PersoonBuilder.bouwPersoon(111, null, 19830404, null, null, null, null))
                    .voegPartnerToe(PersoonBuilder.bouwPersoon(22222, null, 19830405, null, null, null, null))
                    .setDatumAanvang(20010404).getRelatie();

        PersoonModel manModel = null;
        PersoonModel vrouwModel = null;
        for (BetrokkenheidBericht betr : huwelijk.getBetrokkenheden()) {
            if (betr.getPersoon().getIdentificatienummers().getBurgerservicenummer()
                    .equals(new Burgerservicenummer("111")))
            {
                manModel = new PersoonModel(betr.getPersoon());
            } else {
                vrouwModel = new PersoonModel(betr.getPersoon());
            }
        }

        PersoonBuilder.voegNederlandseNationaliteitToe(manModel, vrouwModel);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("111"))).thenReturn(manModel);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("22222"))).thenReturn(
                vrouwModel);

        List<Melding> meldingen = brby0401.executeer(null, huwelijk, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0401, meldingen.get(0).getCode());
        Assert.assertEquals(SoortMelding.DEBLOKKEERBAAR, meldingen.get(0).getSoort());
        Assert.assertEquals(
                "De minimumleeftijd voor trouwen of geregistreerd partnerschap aangaan is 18 jaar voor partners met "
                    + "Nederlandse nationaliteit.", meldingen.get(0).getOmschrijving());
    }

    @Test
    public void testManMinderJarigMaarGeenNederlandseNationaliteit() {
        HuwelijkGeregistreerdPartnerschapBericht huwelijk =
            new RelatieBuilder<HuwelijkGeregistreerdPartnerschapBericht>().bouwHuwlijkRelatie()
                    .voegPartnerToe(PersoonBuilder.bouwPersoon(111, null, 19900404, null, null, null, null))
                    .voegPartnerToe(PersoonBuilder.bouwPersoon(22222, null, 19830404, null, null, null, null))
                    .setDatumAanvang(20010404).getRelatie();

        PersoonModel manModel = null;
        PersoonModel vrouwModel = null;
        for (BetrokkenheidBericht betrokkenheidBericht : huwelijk.getBetrokkenheden()) {
            if (betrokkenheidBericht.getPersoon().getIdentificatienummers().getBurgerservicenummer()
                    .equals(new Burgerservicenummer("111")))
            {
                manModel = new PersoonModel(betrokkenheidBericht.getPersoon());
            } else {
                vrouwModel = new PersoonModel(betrokkenheidBericht.getPersoon());
            }
        }

        // Vrouw heeft de Nederlandse Nationaliteit. De man niet en is minderjarig.
        PersoonBuilder.voegNederlandseNationaliteitToe(vrouwModel);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("111"))).thenReturn(manModel);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("22222"))).thenReturn(
                vrouwModel);

        List<Melding> meldingen = brby0401.executeer(null, huwelijk, null);
        Assert.assertEquals(0, meldingen.size());
    }
}
