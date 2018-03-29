/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling.populatiebepaling;

import static org.mockito.Matchers.eq;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.expressie.ExpressieService;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiehandeling;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class PopulatieBepalingServiceImplTest {

    @InjectMocks
    private PopulatieBepalingServiceImpl service;
    @Mock
    private PopulatieBepaler populatieTransitieBepaler;
    @Mock
    private PopulatieBepaler populatiePopulatieBinnenBuitenBepaler;
    @Mock
    private ExpressieService expressieService;

    @Test
    public void testPopulatieObvPopulatieBinnenBuitenBepaler() throws ExpressieException {
        final AdministratieveHandeling ah = TestVerantwoording.maakAdministratieveHandeling(1);
        final Persoonslijst persoonslijst = TestBuilders.maakBasisPersoon(1, ah.getActie(1));
        final HashMap<Long, Persoonslijst> persoonsLijstMap = Maps.newHashMap();
        persoonsLijstMap.put(persoonslijst.getId(), persoonslijst);
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.ATTENDERING);
        final Populatie populatie = Populatie.VERLAAT;
        Mockito.when(populatiePopulatieBinnenBuitenBepaler.bepaalInUitPopulatie(eq(persoonslijst), Mockito.any(),
                eq(autorisatiebundel.getLeveringsautorisatie()))).thenReturn(populatie);

        final Map<Persoonslijst, Populatie> persoonslijstPopulatieMap = service
                .bepaalPersoonPopulatieCorrelatie(new Mutatiehandeling(ah.getId(), persoonsLijstMap), autorisatiebundel);

        Assert.assertEquals(populatie, persoonslijstPopulatieMap.get(persoonslijst));

        Mockito.verify(populatiePopulatieBinnenBuitenBepaler)
                .bepaalInUitPopulatie(eq(persoonslijst), Mockito.any(), eq(autorisatiebundel.getLeveringsautorisatie()));
        Mockito.verifyZeroInteractions(populatieTransitieBepaler);

    }

    @Test
    public void testPopulatieObvPopulatieTransitiebepaler() throws ExpressieException {
        final AdministratieveHandeling ah = TestVerantwoording.maakAdministratieveHandeling(1);
        final Persoonslijst persoonslijst = TestBuilders.maakBasisPersoon(1, ah.getActie(1));
        final HashMap<Long, Persoonslijst> persoonsLijstMap = Maps.newHashMap();
        persoonsLijstMap.put(persoonslijst.getId(), persoonslijst);
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);

        final Populatie populatie = Populatie.VERLAAT;
        Mockito.when(populatieTransitieBepaler.bepaalInUitPopulatie(eq(persoonslijst), Mockito.any(),
                eq(autorisatiebundel.getLeveringsautorisatie()))).thenReturn(populatie);

        final Map<Persoonslijst, Populatie> persoonslijstPopulatieMap = service
                .bepaalPersoonPopulatieCorrelatie(new Mutatiehandeling(ah.getId(), persoonsLijstMap), autorisatiebundel);

        Assert.assertEquals(populatie, persoonslijstPopulatieMap.get(persoonslijst));

        Mockito.verify(populatieTransitieBepaler)
                .bepaalInUitPopulatie(eq(persoonslijst), Mockito.any(), eq(autorisatiebundel.getLeveringsautorisatie()));
        Mockito.verifyZeroInteractions(populatiePopulatieBinnenBuitenBepaler);

    }

    @Test
    public void testPopulatieObvPlaatsenAfnemerindicatie() throws ExpressieException {
        final AdministratieveHandeling ah = TestVerantwoording.maakAdministratieveHandeling(SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE);
        final Persoonslijst persoonslijst = TestBuilders.maakBasisPersoon(1, ah.getActie(1));
        final HashMap<Long, Persoonslijst> persoonsLijstMap = Maps.newHashMap();
        persoonsLijstMap.put(persoonslijst.getId(), persoonslijst);
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.PLAATSING_AFNEMERINDICATIE);
        final Map<Persoonslijst, Populatie> persoonslijstPopulatieMap = service
                .bepaalPersoonPopulatieCorrelatie(new Mutatiehandeling(ah.getId(), persoonsLijstMap), autorisatiebundel);
        Assert.assertEquals(Populatie.BETREEDT, persoonslijstPopulatieMap.get(persoonslijst.getNuNuBeeld()));
        Mockito.verifyZeroInteractions(populatieTransitieBepaler, populatiePopulatieBinnenBuitenBepaler);
    }

    @Test
    public void testPopulatieObvPlaatsenVerwijderenAfnemerindicatie() throws ExpressieException {
        final AdministratieveHandeling ah = TestVerantwoording.maakAdministratieveHandeling(SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE);
        final Persoonslijst persoonslijst = TestBuilders.maakBasisPersoon(1, ah.getActie(1));
        final Map<Long, Persoonslijst> persoonsLijstMap = Maps.newHashMap();
        persoonsLijstMap.put(persoonslijst.getId(), persoonslijst);
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.VERWIJDERING_AFNEMERINDICATIE);
        final Map<Persoonslijst, Populatie> persoonslijstPopulatieMap = service
                .bepaalPersoonPopulatieCorrelatie(new Mutatiehandeling(ah.getId(), persoonsLijstMap), autorisatiebundel);
        Assert.assertEquals(Populatie.BUITEN, persoonslijstPopulatieMap.get(persoonslijst.getNuNuBeeld()));
        Mockito.verifyZeroInteractions(populatieTransitieBepaler, populatiePopulatieBinnenBuitenBepaler);
    }

    private Autorisatiebundel maakAutorisatiebundel(final SoortDienst soortDienst) {
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(soortDienst);
        final Partij partij = TestPartijBuilder.maakBuilder().metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        return new Autorisatiebundel(tla, dienst);
    }
}
