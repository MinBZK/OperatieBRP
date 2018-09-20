/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.huwelijkpartnerschap;

import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class BRBY0409Test {

    private BRBY0409 brby0409;

    @Mock
    private PersoonRepository persoonRepository;

    @Mock
    private RelatieRepository relatieRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        brby0409 = new BRBY0409();

        ReflectionTestUtils.setField(brby0409, "persoonRepository", persoonRepository);
        ReflectionTestUtils.setField(brby0409, "relatieRepository", relatieRepository);
    }

    @Test
    public void testIsVerwant() {
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

        ReflectionTestUtils.setField(manModel, "id", 1);
        ReflectionTestUtils.setField(vrouwModel, "id", 2);

        PersoonBuilder.voegNederlandseNationaliteitToe(manModel, vrouwModel);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("man")))
                .thenReturn(manModel);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("vrouw")))
                .thenReturn(vrouwModel);

        //Doordat betrokkenheden in een Set zit kan persoon1 en persoon2 id omgedraaid zijn
        Mockito.when(relatieRepository.isVerwant(1, 2)).thenReturn(true);
        Mockito.when(relatieRepository.isVerwant(2, 1)).thenReturn(true);

        List<Melding> meldingen = brby0409.executeer(null, huwelijk, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0409, meldingen.get(0).getCode());
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, meldingen.get(0).getSoort());

        Mockito.verify(relatieRepository, Mockito.times(1)).isVerwant(Matchers.anyInt(), Matchers.anyInt());
    }

    @Test
    public void testNietVerwant() {
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

        ReflectionTestUtils.setField(manModel, "id", 1);
        ReflectionTestUtils.setField(vrouwModel, "id", 2);

        PersoonBuilder.voegNederlandseNationaliteitToe(manModel, vrouwModel);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("man")))
                .thenReturn(manModel);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("vrouw")))
                .thenReturn(vrouwModel);

        //Doordat betrokkenheden in een Set zit kan persoon1 en persoon2 id omgedraaid zijn
        Mockito.when(relatieRepository.isVerwant(1, 2)).thenReturn(false);
        Mockito.when(relatieRepository.isVerwant(2, 1)).thenReturn(false);

        List<Melding> meldingen = brby0409.executeer(null, huwelijk, null);
        Assert.assertEquals(0, meldingen.size());

        Mockito.verify(relatieRepository, Mockito.times(1)).isVerwant(Matchers.anyInt(), Matchers.anyInt());
    }

    @Test
    public void testMagTrouwenGeenNederlandseNationaliteit() {
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

        ReflectionTestUtils.setField(manModel, "id", 1);
        ReflectionTestUtils.setField(vrouwModel, "id", 2);

        PersoonBuilder.voegNederlandseNationaliteitToe(manModel);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("man")))
                .thenReturn(manModel);
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("vrouw")))
                .thenReturn(vrouwModel);

        //Doordat betrokkenheden in een Set zit kan persoon1 en persoon2 id omgedraaid zijn
        Mockito.when(relatieRepository.isVerwant(1, 2)).thenReturn(true);
        Mockito.when(relatieRepository.isVerwant(2, 1)).thenReturn(true);

        List<Melding> meldingen = brby0409.executeer(null, huwelijk, null);
        Assert.assertEquals(0, meldingen.size());

        Mockito.verify(relatieRepository, Mockito.times(0)).isVerwant(Matchers.anyInt(), Matchers.anyInt());
    }
}
