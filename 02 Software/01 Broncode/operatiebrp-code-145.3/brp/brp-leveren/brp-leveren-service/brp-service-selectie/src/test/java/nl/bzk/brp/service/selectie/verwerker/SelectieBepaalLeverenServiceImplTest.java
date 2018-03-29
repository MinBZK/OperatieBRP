/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import com.google.common.collect.Sets;
import java.net.URISyntaxException;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.SelectieLijst;
import nl.bzk.brp.domain.expressie.functie.SelectieLijstFunctie;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.VerstrekkingsbeperkingService;
import nl.bzk.brp.service.algemeen.expressie.ExpressieService;
import nl.bzk.brp.service.selectie.verwerker.cache.VerwerkerCache;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * SelectieBepaalLeverenServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieBepaalLeverenServiceImplTest {

    private static final int DIENST_ID = 1;

    @Mock
    private ExpressieService expressieService;

    @Mock
    private VerstrekkingsbeperkingService verstrekkingsbeperkingService;

    @Mock
    private VerwerkerCache verwerkerCache;

    @Captor
    private ArgumentCaptor<SelectieLijst> selectieLijstArgumentCaptor;


    @InjectMocks
    private SelectieBepaalLeverenServiceImpl selectieBepaalLeverenService;

    @Before
    public void voorElkeTest() throws ExpressieException, URISyntaxException {
        Mockito.when(expressieService.geefPopulatiebeperking(Mockito.any())).thenReturn(BooleanLiteral.WAAR);
        Mockito.when(verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(Mockito.any(), Mockito.any())).thenReturn(false);
    }

    @Test
    public void testPersoonInSelectieHappyFlow() throws ExpressieException {
        Mockito.when(expressieService.evalueerMetSelectieDatumEnSelectielijst(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any())).thenReturn(true);
        Assert.assertTrue(bepaalInSelectie(SoortSelectie.STANDAARD_SELECTIE));
    }

    @Test
    public void testPersoonVerstrekkingsbeperking() throws ExpressieException {
        Mockito.when(verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(Mockito.any(), Mockito.any())).thenReturn(true);
        Assert.assertFalse(bepaalInSelectie(SoortSelectie.STANDAARD_SELECTIE));
        Mockito.verifyZeroInteractions(expressieService);
    }

    @Test
    public void testPersoonVerstrekkingsbeperkingSoortSelectieVerwijderen() throws ExpressieException {
        //voor verwijderen is een verstrekkingsbeperking niet relevant
        Mockito.when(verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(expressieService.evalueerMetSelectieDatumEnSelectielijst(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any())).thenReturn(true);
        Assert.assertTrue(bepaalInSelectie(SoortSelectie.SELECTIE_MET_VERWIJDERING_AFNEMERINDICATIE));
    }

    @Test
    public void testPersoonNietInDoelbinding() throws ExpressieException {
        Mockito.when(expressieService.geefPopulatiebeperking(Mockito.any())).thenReturn(BooleanLiteral.ONWAAR);
        Mockito.when(expressieService.evalueerMetSelectieDatumEnSelectielijst(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any())).thenReturn(false);
        Assert.assertFalse(bepaalInSelectie(SoortSelectie.STANDAARD_SELECTIE));
        Mockito.verify(expressieService, Mockito.times(1)).geefPopulatiebeperking(Mockito.any());
    }

    @Test
    public void testPersoonNietInSelectieLijst() throws ExpressieException {
        final Persoonslijst persoonslijst = TestBuilders.maakPersoonMetIdentificatieNrs("123456789", "123456879");

        Mockito.when(expressieService.geefPopulatiebeperking(Mockito.any())).thenReturn(ExpressieParser.parse(SelectieLijstFunctie.EXPRESSIE));
        SelectieLijst selectieLijst = new SelectieLijst(1, ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER),
                Sets.newHashSet());
        Mockito.when(verwerkerCache.getSelectieLijst(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(selectieLijst);
        bepaalInSelectie(SoortSelectie.STANDAARD_SELECTIE, persoonslijst, true);
        Mockito.verify(expressieService)
                .evalueerMetSelectieDatumEnSelectielijst(Mockito.any(), Mockito.any(), Mockito.anyInt(), selectieLijstArgumentCaptor.capture());
        Assert.assertEquals(selectieLijst, selectieLijstArgumentCaptor.getValue());
    }

    @Test
    public void testPersoonNietInSelectieLijstMaarIndicatieLijstFalse() throws ExpressieException {
        final Persoonslijst persoonslijst = TestBuilders.maakPersoonMetIdentificatieNrs("123456789", "123456879");

        Mockito.when(expressieService.geefPopulatiebeperking(Mockito.any())).thenReturn(ExpressieParser.parse(SelectieLijstFunctie.EXPRESSIE));
        SelectieLijst selectieLijst = new SelectieLijst(1, ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER),
                Sets.newHashSet());
        Mockito.when(verwerkerCache.getSelectieLijst(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(selectieLijst);
        bepaalInSelectie(SoortSelectie.STANDAARD_SELECTIE, persoonslijst, false);
        Mockito.verify(expressieService)
                .evalueerMetSelectieDatumEnSelectielijst(Mockito.any(), Mockito.any(), Mockito.anyInt(), selectieLijstArgumentCaptor.capture());

        Assert.assertTrue(SelectieLijst.GEEN_LIJST == selectieLijstArgumentCaptor.getValue());
    }


    @Test
    public void testPersoonInSelectieLijst() throws ExpressieException {
        Mockito.when(expressieService.geefPopulatiebeperking(Mockito.any())).thenReturn(ExpressieParser.parse(SelectieLijstFunctie.EXPRESSIE));
        Mockito.when(expressieService.evalueerMetSelectieDatumEnSelectielijst(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any())).thenReturn(true);

        final Persoonslijst persoonslijst = TestBuilders.maakPersoonMetIdentificatieNrs("123456789", "123456879");
        SelectieLijst selectieLijst = new SelectieLijst(1, ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER),
                Sets.newHashSet());
        Mockito.when(verwerkerCache.getSelectieLijst(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(selectieLijst);
        Assert.assertTrue(bepaalInSelectie(SoortSelectie.STANDAARD_SELECTIE, persoonslijst, true));
    }


    @Test
    public void testPersoonNietSelectieExceptieInExpressie() throws ExpressieException {
        final Expressie errorMock = Mockito.mock(Expressie.class);
        Mockito.doThrow(ExpressieException.class).when(errorMock).evalueer(Mockito.any());
        Mockito.when(expressieService.geefPopulatiebeperking(Mockito.any())).thenReturn(errorMock);
        Assert.assertFalse(bepaalInSelectie(SoortSelectie.STANDAARD_SELECTIE));
        Mockito.verify(expressieService, Mockito.times(1)).geefPopulatiebeperking(Mockito.any());
    }


    private boolean bepaalInSelectie(final SoortSelectie soortSelectie) {
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst.SELECTIE);
        dienst.setSoortSelectie(soortSelectie.getId());
        dienst.setId(DIENST_ID);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);
        final Persoonslijst persoonslijst = TestBuilders.maakPersoonMetIdentificatieNrs("123456789", "987654321");
        return selectieBepaalLeverenService.inSelectie(persoonslijst, autorisatiebundel, 20100101, 1, true, 1);
    }

    private boolean bepaalInSelectie(final SoortSelectie soortSelectie, final Persoonslijst persoonslijst, final boolean lijstGebruiken) {
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst.SELECTIE);
        dienst.setSoortSelectie(soortSelectie.getId());
        dienst.setId(DIENST_ID);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);
        return selectieBepaalLeverenService.inSelectie(persoonslijst, autorisatiebundel, 20100101, 1, lijstGebruiken, 1);
    }

}
