/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.huwelijkpartnerschap;

import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
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

    private BRBY0401 brby0401;

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
        RelatieBericht huwelijk = new RelatieBuilder().bouwHuwlijkRelatie().
                voegPartnerToe(PersoonBuilder.bouwPersoon("man", null, 19840404, null, null, null, null)).
                voegPartnerToe(PersoonBuilder.bouwPersoon("vrouw", null, 19850404, null, null, null, null)).
                setDatumAanvang(20010404).
                getRelatie();

        PersoonModel manModel = null;
        PersoonModel vrouwModel = null;
        for (BetrokkenheidBericht betrokkenheidBericht : huwelijk.getBetrokkenheden()) {
            if (betrokkenheidBericht.getBetrokkene().getIdentificatienummers().getBurgerservicenummer().equals(new Burgerservicenummer("man"))) {
                manModel = new PersoonModel(betrokkenheidBericht.getBetrokkene());
            } else {
                vrouwModel = new PersoonModel(betrokkenheidBericht.getBetrokkene());
            }
        }

        PersoonBuilder.voegNederlandseNationaliteitToe(manModel, vrouwModel);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("man")))
                .thenReturn(manModel);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("vrouw")))
                .thenReturn(vrouwModel);

        List<Melding> meldingen = brby0401.executeer(null, huwelijk, null);
        Assert.assertEquals(2, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0401, meldingen.get(0).getCode());
        Assert.assertEquals(Soortmelding.OVERRULEBAAR, meldingen.get(0).getSoort());
        Assert.assertEquals("De minimumleeftijd voor trouwen of geregistreerd partnerschap aangaan is 18 jaar voor partners met Nederlandse nationaliteit.",
                meldingen.get(0).getOmschrijving());
    }

    @Test
    public void testBeidePartnersMeerderjarig() {
        RelatieBericht huwelijk = new RelatieBuilder().bouwHuwlijkRelatie().
                voegPartnerToe(PersoonBuilder.bouwPersoon("man", null, 19800404, null, null, null, null)).
                voegPartnerToe(PersoonBuilder.bouwPersoon("vrouw", null, 19790404, null, null, null, null)).
                setDatumAanvang(20010404).
                getRelatie();

        PersoonModel manModel = null;
        PersoonModel vrouwModel = null;
        for (BetrokkenheidBericht betr : huwelijk.getBetrokkenheden()) {
            if (betr.getBetrokkene().getIdentificatienummers().getBurgerservicenummer().equals(new Burgerservicenummer("man"))) {
                manModel = new PersoonModel(betr.getBetrokkene());
            } else {
                vrouwModel = new PersoonModel(betr.getBetrokkene());
            }
        }

        PersoonBuilder.voegNederlandseNationaliteitToe(manModel, vrouwModel);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("man")))
                .thenReturn(manModel);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("vrouw")))
                .thenReturn(vrouwModel);

        List<Melding> meldingen = brby0401.executeer(null, huwelijk, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testBeidePartners18Jaar() {
        RelatieBericht huwelijk = new RelatieBuilder().bouwHuwlijkRelatie().
                voegPartnerToe(PersoonBuilder.bouwPersoon("man", null, 19830404, null, null, null, null)).
                voegPartnerToe(PersoonBuilder.bouwPersoon("vrouw", null, 19830404, null, null, null, null)).
                setDatumAanvang(20010404).
                getRelatie();

        PersoonModel manModel = null;
        PersoonModel vrouwModel = null;
        for (BetrokkenheidBericht betr : huwelijk.getBetrokkenheden()) {
            if (betr.getBetrokkene().getIdentificatienummers().getBurgerservicenummer().equals(new Burgerservicenummer("man"))) {
                manModel = new PersoonModel(betr.getBetrokkene());
            } else {
                vrouwModel = new PersoonModel(betr.getBetrokkene());
            }
        }

        PersoonBuilder.voegNederlandseNationaliteitToe(manModel, vrouwModel);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("man")))
                .thenReturn(manModel);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("vrouw")))
                .thenReturn(vrouwModel);

        List<Melding> meldingen = brby0401.executeer(null, huwelijk, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testVrouwMinderjarigVerschilMetHuwelijksDatum1Maand() {
        RelatieBericht huwelijk = new RelatieBuilder().bouwHuwlijkRelatie().
                voegPartnerToe(PersoonBuilder.bouwPersoon("man", null, 19830404, null, null, null, null)).
                voegPartnerToe(PersoonBuilder.bouwPersoon("vrouw", null, 19830504, null, null, null, null)).
                setDatumAanvang(20010404).
                getRelatie();

        PersoonModel manModel = null;
        PersoonModel vrouwModel = null;
        for (BetrokkenheidBericht betr : huwelijk.getBetrokkenheden()) {
            if (betr.getBetrokkene().getIdentificatienummers().getBurgerservicenummer().equals(new Burgerservicenummer("man"))) {
                manModel = new PersoonModel(betr.getBetrokkene());
            } else {
                vrouwModel = new PersoonModel(betr.getBetrokkene());
            }
        }

        PersoonBuilder.voegNederlandseNationaliteitToe(manModel, vrouwModel);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("man")))
                .thenReturn(manModel);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("vrouw")))
                .thenReturn(vrouwModel);

        List<Melding> meldingen = brby0401.executeer(null, huwelijk, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0401, meldingen.get(0).getCode());
        Assert.assertEquals(Soortmelding.OVERRULEBAAR, meldingen.get(0).getSoort());
        Assert.assertEquals("De minimumleeftijd voor trouwen of geregistreerd partnerschap aangaan is 18 jaar voor partners met Nederlandse nationaliteit.",
                meldingen.get(0).getOmschrijving());
    }

    @Test
    public void testVrouwMinderjarigVerschilMetHuwelijksDatum1Dag() {
        RelatieBericht huwelijk = new RelatieBuilder().bouwHuwlijkRelatie().
                voegPartnerToe(PersoonBuilder.bouwPersoon("man", null, 19830404, null, null, null, null)).
                voegPartnerToe(PersoonBuilder.bouwPersoon("vrouw", null, 19830405, null, null, null, null)).
                setDatumAanvang(20010404).
                getRelatie();

        PersoonModel manModel = null;
        PersoonModel vrouwModel = null;
        for (BetrokkenheidBericht betr : huwelijk.getBetrokkenheden()) {
            if (betr.getBetrokkene().getIdentificatienummers().getBurgerservicenummer().equals(new Burgerservicenummer("man"))) {
                manModel = new PersoonModel(betr.getBetrokkene());
            } else {
                vrouwModel = new PersoonModel(betr.getBetrokkene());
            }
        }

        PersoonBuilder.voegNederlandseNationaliteitToe(manModel, vrouwModel);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("man")))
                .thenReturn(manModel);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("vrouw")))
                .thenReturn(vrouwModel);

        List<Melding> meldingen = brby0401.executeer(null, huwelijk, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0401, meldingen.get(0).getCode());
        Assert.assertEquals(Soortmelding.OVERRULEBAAR, meldingen.get(0).getSoort());
        Assert.assertEquals("De minimumleeftijd voor trouwen of geregistreerd partnerschap aangaan is 18 jaar voor partners met Nederlandse nationaliteit.",
                meldingen.get(0).getOmschrijving());
    }

    @Test
    public void testManMinderJarigMaarGeenNederlandseNationaliteit() {
        RelatieBericht huwelijk = new RelatieBuilder().bouwHuwlijkRelatie().
                voegPartnerToe(PersoonBuilder.bouwPersoon("man", null, 19900404, null, null, null, null)).
                voegPartnerToe(PersoonBuilder.bouwPersoon("vrouw", null, 19830404, null, null, null, null)).
                setDatumAanvang(20010404).
                getRelatie();

        PersoonModel manModel = null;
        PersoonModel vrouwModel = null;
        for (BetrokkenheidBericht betrokkenheidBericht : huwelijk.getBetrokkenheden()) {
            if (betrokkenheidBericht.getBetrokkene().getIdentificatienummers().getBurgerservicenummer().equals(new Burgerservicenummer("man"))) {
                manModel = new PersoonModel(betrokkenheidBericht.getBetrokkene());
            } else {
                vrouwModel = new PersoonModel(betrokkenheidBericht.getBetrokkene());
            }
        }

        //Vrouw heeft de Nederlandse Nationaliteit. De man niet en is minderjarig.
        PersoonBuilder.voegNederlandseNationaliteitToe(vrouwModel);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("man")))
                .thenReturn(manModel);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("vrouw")))
                .thenReturn(vrouwModel);

        List<Melding> meldingen = brby0401.executeer(null, huwelijk, null);
        Assert.assertEquals(0, meldingen.size());
    }
}
