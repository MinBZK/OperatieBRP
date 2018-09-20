/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.leveringsautorisatie;

import java.util.Collections;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.cache.LeveringAutorisatieCache;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;


@RunWith(MockitoJUnitRunner.class)
public class ToegangLeveringsautorisatieServiceIntegratieTest {

    private static final int LEVERING_AUTORISATIE_ID = 12345;
    private static final int PARTIJ_CODE_ATTRIBUUT   = 123;

    @Mock
    private LeveringAutorisatieCache leveringAutorisatieCache;

    @InjectMocks
    private ToegangLeveringsautorisatieService toegangLeveringsautorisatieService = new ToegangLeveringsautorisatieServiceImpl();


    @Test
    public final void geefGeldigeLeveringsutorisaties() {
        Mockito.when(leveringAutorisatieCache.geefGeldigeToegangleveringsautorisaties()).thenReturn(
            Collections.singletonList(TestToegangLeveringautorisatieBuilder.maker().maak()));
        toegangLeveringsautorisatieService.geefGeldigeLeveringsautorisaties();
        Mockito.verify(leveringAutorisatieCache, Mockito.times(1)).geefGeldigeToegangleveringsautorisaties();
    }

    @Test
    public final void geefLeveringautorisatie() {
        Mockito.when(leveringAutorisatieCache.geefLeveringsautorisatie(LEVERING_AUTORISATIE_ID)).thenReturn(
            TestLeveringsautorisatieBuilder.maker().maak());
        toegangLeveringsautorisatieService.geefLeveringautorisatie(LEVERING_AUTORISATIE_ID);
        Mockito.verify(leveringAutorisatieCache, Mockito.times(1)).geefLeveringsautorisatie(LEVERING_AUTORISATIE_ID);
    }

    @Test
    public final void geefLeveringautorisatieZonderControle() {
        Mockito.when(leveringAutorisatieCache.geefLeveringsautorisatieZonderControle(LEVERING_AUTORISATIE_ID)).thenReturn(
            TestLeveringsautorisatieBuilder.maker().maak());
        toegangLeveringsautorisatieService.geefLeveringautorisatieZonderControle(LEVERING_AUTORISATIE_ID);
        Mockito.verify(leveringAutorisatieCache, Mockito.times(1)).geefLeveringsautorisatieZonderControle(LEVERING_AUTORISATIE_ID);
    }

    @Test
    public final void geefToegangLeveringautorisatieZonderControle() {
        Mockito.when(leveringAutorisatieCache.geefToegangleveringautorisatieZonderControle(LEVERING_AUTORISATIE_ID, PARTIJ_CODE_ATTRIBUUT)).thenReturn(
            TestToegangLeveringautorisatieBuilder.maker().maak());
        toegangLeveringsautorisatieService.geefToegangLeveringsautorisatieOpZonderControle(LEVERING_AUTORISATIE_ID, PARTIJ_CODE_ATTRIBUUT);
        Mockito.verify(leveringAutorisatieCache, Mockito.times(1)).geefToegangleveringautorisatieZonderControle(LEVERING_AUTORISATIE_ID, PARTIJ_CODE_ATTRIBUUT);
    }


    @Test(expected = EmptyResultDataAccessException.class)
    public final void geefGeenLeveringautorisatieWantNietGevonden() {
        Mockito.when(leveringAutorisatieCache.geefLeveringsautorisatie(LEVERING_AUTORISATIE_ID)).thenReturn(null);
        toegangLeveringsautorisatieService.geefLeveringautorisatie(LEVERING_AUTORISATIE_ID);
        Mockito.verify(leveringAutorisatieCache, Mockito.times(1)).geefLeveringsautorisatie(LEVERING_AUTORISATIE_ID);
    }

    @Test
    public final void bestaatLeveringautorisatie() {
        Mockito.when(leveringAutorisatieCache.geefLeveringsautorisatieZonderControle(LEVERING_AUTORISATIE_ID)).thenReturn(
            TestLeveringsautorisatieBuilder.maker().metId(LEVERING_AUTORISATIE_ID).maak());
        Assert.assertTrue(toegangLeveringsautorisatieService.bestaatLeveringautorisatie(LEVERING_AUTORISATIE_ID));
        Mockito.verify(leveringAutorisatieCache, Mockito.times(1)).geefLeveringsautorisatieZonderControle(LEVERING_AUTORISATIE_ID);
    }

    @Test
    public final void bestaatNietLeveringautorisatie() {
        Mockito.when(leveringAutorisatieCache.geefLeveringsautorisatieZonderControle(LEVERING_AUTORISATIE_ID)).thenReturn(
            null);
        Assert.assertFalse(toegangLeveringsautorisatieService.bestaatLeveringautorisatie(LEVERING_AUTORISATIE_ID));
        Mockito.verify(leveringAutorisatieCache, Mockito.times(1)).geefLeveringsautorisatieZonderControle(LEVERING_AUTORISATIE_ID);
    }

//    @Test
//    public final void geefAbonnementExpressies() throws ExpressieExceptie {
//        final Dienst dienst = TestDienstBuilder.dummy();
//        Leveringsautorisatie leveringsautorisatie = leveringautorisatieService.geefLeveringautorisatie(ABONNEMENT_UTRECHT);
//        List<String> expressies = dienst.geefAttributenFilterExpressieLijst(Rol.AFNEMER);
//        Assert.assertNotNull(expressies);
//    }

//    @Test
//    public final void kanDienstLezen() {
//        final CategorieDienst[] soortenDienst = { CategorieDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING };
//        final List<DienstModel> diensten =
//            leveringautorisatieService.geefDienstenVoorAbonnementEnSoort(
//                new NaamEnumeratiewaardeAttribuut(ABONNEMENT_UTRECHT),
//                soortenDienst);
//
//        Assert.assertFalse(diensten.isEmpty());
//    }
//
//    @Test
//    public final void geenDienstGevonden() {
//        final CategorieDienst[] soortenDienst = { CategorieDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING };
//        final List<DienstModel> diensten = leveringautorisatieService.geefDienstenVoorAbonnementEnSoort(
//            new NaamEnumeratiewaardeAttribuut("Niet bestaand leveringsautorisatie"),
//            soortenDienst);
//
//        Assert.assertTrue(diensten.isEmpty());
//    }
//
//    @Test
//    public final void geenActieveDienstGevonden() {
//        final CategorieDienst[] soortenDienst = {
//            CategorieDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING,
//            CategorieDienst.GEEF_DETAILS_PERSOON, };
//        final List<DienstModel> diensten = leveringautorisatieService.geefDienstenVoorAbonnementEnSoort(
//            new NaamEnumeratiewaardeAttribuut("Abonnement Rotterdam (lokaal)"),
//            soortenDienst);
//
//        Assert.assertTrue(diensten.isEmpty());
//    }
}
