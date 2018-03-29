/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import com.google.common.collect.Lists;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.algemeen.TestPartijRolBuilder;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.VerstrekkingsbeperkingService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.algemeen.expressie.ExpressieService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GevondenZoekPersoonFilterServiceImplTest {

    @InjectMocks
    private GevondenZoekPersoonFilterServiceImpl gevondenZoekPersoonFilterService;

    @Mock
    private ExpressieService expressieService;

    @Mock
    private VerstrekkingsbeperkingService verstrekkingsbeperkingService;

    @Mock
    private PartijService partijService;


    @Test
    public void filterPersoonslijstPersoonValtBuitenPopulatie() throws ExpressieException {
        final Autorisatiebundel autorisatiebundel = maakAutorisatieBundel();
        final List<Persoonslijst> persoonslijstList = maakPersoonslijst();
        Mockito.when(expressieService.geefPopulatiebeperking(Mockito.any())).thenReturn(null);
        Mockito.when(expressieService.evalueer(Mockito.any(Expressie.class), Mockito.any(Persoonslijst.class))).thenReturn(false);
        List<Persoonslijst> gefilterdePersoonslijstList = gevondenZoekPersoonFilterService.filterPersoonslijst(autorisatiebundel, persoonslijstList);

        Assert.assertTrue(gefilterdePersoonslijstList.isEmpty());
    }

    @Test
    public void filterPersoonslijstPersoonVerstrekkingsbeperking() throws ExpressieException {
        final Autorisatiebundel autorisatiebundel = maakAutorisatieBundel();
        final List<Persoonslijst> persoonslijstList = maakPersoonslijst();
        Mockito.when(expressieService.geefPopulatiebeperking(Mockito.any())).thenReturn(null);
        Mockito.when(expressieService.evalueer(Mockito.any(Expressie.class), Mockito.any(Persoonslijst.class))).thenReturn(true);
        Mockito.when(verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(Mockito.any(Persoonslijst.class), Mockito.any())).thenReturn
                (true);
        List<Persoonslijst> gefilterdePersoonslijstList = gevondenZoekPersoonFilterService.filterPersoonslijst(autorisatiebundel, persoonslijstList);

        Assert.assertTrue(gefilterdePersoonslijstList.isEmpty());
    }

    @Test
    public void filterPersoonslijstPersoonValtBinnenPopulatie() throws ExpressieException {
        final Autorisatiebundel autorisatiebundel = maakAutorisatieBundel();
        final List<Persoonslijst> persoonslijstList = maakPersoonslijst();
        Mockito.when(expressieService.geefPopulatiebeperking(Mockito.any())).thenReturn(null);
        Mockito.when(expressieService.evalueer(Mockito.any(Expressie.class), Mockito.any(Persoonslijst.class))).thenReturn(true);
        List<Persoonslijst> gefilterdePersoonslijstList = gevondenZoekPersoonFilterService.filterPersoonslijst(autorisatiebundel, persoonslijstList);

        Assert.assertFalse(gefilterdePersoonslijstList.isEmpty());
    }

    @Test
    public void filterPersoonslijstExpressieExceptie() throws ExpressieException {
        final Autorisatiebundel autorisatiebundel = maakAutorisatieBundel();
        final List<Persoonslijst> persoonslijstList = maakPersoonslijst();
        Mockito.doThrow(ExpressieException.class)
                .when(expressieService).geefPopulatiebeperking(Mockito.any());

        List<Persoonslijst> gefilterdePersoonslijsten = gevondenZoekPersoonFilterService.filterPersoonslijst(autorisatiebundel, persoonslijstList);

        Assert.assertTrue(gefilterdePersoonslijsten.isEmpty());
    }

    private Autorisatiebundel maakAutorisatieBundel() {
        final Leveringsautorisatie levAut = TestAutorisaties.metSoortDienst(SoortDienst.ZOEK_PERSOON);
        levAut.setPopulatiebeperking("WAAR");
        final Dienstbundel dienstbundel = new Dienstbundel(levAut);
        dienstbundel.setNaderePopulatiebeperking("WAAR");
        final Dienst dienst = new Dienst(dienstbundel, SoortDienst.ZOEK_PERSOON);

        //@formatter:off
        final PartijRol partijRol =
            TestPartijRolBuilder.maker()
                .metId(1)
                .metPartij(TestPartijBuilder.maakBuilder().metCode("000000").build())
                .metRol(Rol.AFNEMER)
                .maak();
        //@formatter:on

        final ToegangLeveringsAutorisatie toegangLevAut = new ToegangLeveringsAutorisatie(partijRol, levAut);
        toegangLevAut.setNaderePopulatiebeperking("WAAR");
        return new Autorisatiebundel(toegangLevAut, dienst);
    }

    private List<Persoonslijst> maakPersoonslijst() {
        final MetaObject persoon = TestBuilders.maakLeegPersoon(1).build();
        return Lists.newArrayList(new Persoonslijst(persoon, 0L));
    }
}
