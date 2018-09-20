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
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortIndicatie;
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

public class BRBY0403Test {

    @Mock
    private PersoonRepository persoonRepository;

    private BRBY0403 brby0403;

    @Before
    public void init() {
        brby0403 = new BRBY0403();
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(brby0403, "persoonRepository", persoonRepository);
    }

    @Test
    public void testBeidePartnersNietOnderCuratele() {
        RelatieBericht huwelijk = new RelatieBuilder().bouwHuwlijkRelatie().
                voegPartnerToe(PersoonBuilder.bouwPersoon("man", null, 19840404, null, null, null, null)).
                voegPartnerToe(PersoonBuilder.bouwPersoon("vrouw", null, 19850404, null, null, null, null)).
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

        List<Melding> meldingen = brby0403.executeer(null, huwelijk, null);
        Assert.assertTrue(meldingen.isEmpty());
    }

    @Test
    public void testManOnderCuratele() {
        RelatieBericht huwelijk = new RelatieBuilder().bouwHuwlijkRelatie().
                voegPartnerToe(PersoonBuilder.bouwPersoon("man", null, 19840404, null, null, null, null)).
                voegPartnerToe(PersoonBuilder.bouwPersoon("vrouw", null, 19850404, null, null, null, null)).
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
        PersoonBuilder.voegPersoonIndicatieToe(SoortIndicatie.ONDER_CURATELE, manModel);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("man")))
                .thenReturn(manModel);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("vrouw")))
                .thenReturn(vrouwModel);

        List<Melding> meldingen = brby0403.executeer(null, huwelijk, null);
        Assert.assertTrue(meldingen.size() == 1);
        Melding melding = meldingen.get(0);
        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.BRBY0403, melding.getCode());
        Assert.assertEquals(Soortmelding.OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals("De partner is onder curatele, trouwen of geregistreerd partnerschap aangaan is niet toegestaan.", melding.getOmschrijving());
    }

    @Test
    public void testManEnVrouwOnderCuratele() {
        RelatieBericht huwelijk = new RelatieBuilder().bouwHuwlijkRelatie().
                voegPartnerToe(PersoonBuilder.bouwPersoon("man", null, 19840404, null, null, null, null)).
                voegPartnerToe(PersoonBuilder.bouwPersoon("vrouw", null, 19850404, null, null, null, null)).
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
        PersoonBuilder.voegPersoonIndicatieToe(SoortIndicatie.ONDER_CURATELE, manModel, vrouwModel);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("man")))
                .thenReturn(manModel);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("vrouw")))
                .thenReturn(vrouwModel);

        List<Melding> meldingen = brby0403.executeer(null, huwelijk, null);
        Assert.assertTrue(meldingen.size() == 2);
        Melding melding = meldingen.get(0);
        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.BRBY0403, melding.getCode());
        Assert.assertEquals(Soortmelding.OVERRULEBAAR, melding.getSoort());
        Assert.assertEquals("De partner is onder curatele, trouwen of geregistreerd partnerschap aangaan is niet toegestaan.", melding.getOmschrijving());

        Melding melding2 = meldingen.get(1);
        Assert.assertNotNull(melding2);
        Assert.assertEquals(MeldingCode.BRBY0403, melding2.getCode());
        Assert.assertEquals(Soortmelding.OVERRULEBAAR, melding2.getSoort());
        Assert.assertEquals("De partner is onder curatele, trouwen of geregistreerd partnerschap aangaan is niet toegestaan.", melding2.getOmschrijving());
    }

    @Test
    public void testManOnderCurateleMaarGeenNLNationaliteit() {
        RelatieBericht huwelijk = new RelatieBuilder().bouwHuwlijkRelatie().
                voegPartnerToe(PersoonBuilder.bouwPersoon("man", null, 19840404, null, null, null, null)).
                voegPartnerToe(PersoonBuilder.bouwPersoon("vrouw", null, 19850404, null, null, null, null)).
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

        PersoonBuilder.voegNederlandseNationaliteitToe(vrouwModel);
        PersoonBuilder.voegPersoonIndicatieToe(SoortIndicatie.ONDER_CURATELE, manModel);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("man")))
                .thenReturn(manModel);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("vrouw")))
                .thenReturn(vrouwModel);

        List<Melding> meldingen = brby0403.executeer(null, huwelijk, null);
        Assert.assertTrue(meldingen.isEmpty());
    }

    @Test
    public void testManEnVrouwOnderCurateleMaarGeenNLNationaliteit() {
        RelatieBericht huwelijk = new RelatieBuilder().bouwHuwlijkRelatie().
                voegPartnerToe(PersoonBuilder.bouwPersoon("man", null, 19840404, null, null, null, null)).
                voegPartnerToe(PersoonBuilder.bouwPersoon("vrouw", null, 19850404, null, null, null, null)).
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

        PersoonBuilder.voegPersoonIndicatieToe(SoortIndicatie.ONDER_CURATELE, manModel, vrouwModel);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("man")))
                .thenReturn(manModel);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("vrouw")))
                .thenReturn(vrouwModel);

        List<Melding> meldingen = brby0403.executeer(null, huwelijk, null);
        Assert.assertTrue(meldingen.isEmpty());
    }
}
