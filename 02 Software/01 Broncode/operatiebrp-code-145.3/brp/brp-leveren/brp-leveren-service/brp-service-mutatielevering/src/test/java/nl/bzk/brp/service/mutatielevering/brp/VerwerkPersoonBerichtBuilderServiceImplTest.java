/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.brp;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.MeldingBepalerService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * VerwerkPersoonBerichtBuilderServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class VerwerkPersoonBerichtBuilderServiceImplTest {

    @InjectMocks
    private VerwerkPersoonBerichtBuilderServiceImpl service;

    @Mock
    private PartijService partijService;
    @Mock
    private MeldingBepalerService meldingBepalerService;

    @Before
    public void voorTest() {
        Mockito.when(meldingBepalerService.geefWaarschuwingen(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Collections.EMPTY_LIST);
    }

    @Test
    public void testMaakVolledigBericht() {
        final SoortDienst soortDienst = SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
        final Map<Autorisatiebundel, List<BijgehoudenPersoon>> bijgehoudenPersonenMap = new HashMap<>();
        final Persoonslijst persoonslijst = TestBuilders.maakPersoonMetHandelingen(1);
        final BijgehoudenPersoon bijgehoudenpersoon = new BijgehoudenPersoon.Builder(persoonslijst, BerichtElement
                .builder().build())
                .metCommunicatieId(1)
                .metVolledigBericht(true)
                .build();
        bijgehoudenPersonenMap.put(autorisatiebundel, Lists.newArrayList(bijgehoudenpersoon));

        final Map<Autorisatiebundel, Map<Persoonslijst, Populatie>> teLeverenPersonenMap = new HashMap<>();
        Map<Persoonslijst, Populatie> populatieMap = new HashMap<>();
        teLeverenPersonenMap.put(autorisatiebundel, populatieMap);
        populatieMap.put(persoonslijst, Populatie.VERLAAT);
        teLeverenPersonenMap.put(autorisatiebundel, populatieMap);

        final List<VerwerkPersoonBericht>
                verwerkPersoonBerichten =
                service.maakBerichten(persoonslijst.getAdministratieveHandeling().getId(), bijgehoudenPersonenMap,
                        teLeverenPersonenMap);
        Assert.assertFalse(verwerkPersoonBerichten.isEmpty());
    }

    @Test
    public void testMaakMutatieBericht() {
        final Persoonslijst persoonslijst = TestBuilders.maakPersoonMetHandelingen(1);

        final SoortDienst soortDienst = SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
        final Map<Autorisatiebundel, List<BijgehoudenPersoon>> bijgehoudenPersonenMap = new HashMap<>();
        final BijgehoudenPersoon bijgehoudenpersoon = new BijgehoudenPersoon.Builder(persoonslijst, BerichtElement
                .builder().build())
                .metCommunicatieId(1)
                .metVolledigBericht(false)
                .build();
        bijgehoudenPersonenMap.put(autorisatiebundel, Lists.newArrayList(bijgehoudenpersoon));

        final Map<Autorisatiebundel, Map<Persoonslijst, Populatie>> teLeverenPersonenMap = new HashMap<>();
        Map<Persoonslijst, Populatie> populatieMap = new HashMap<>();
        teLeverenPersonenMap.put(autorisatiebundel, populatieMap);
        populatieMap.put(persoonslijst, Populatie.VERLAAT);
        teLeverenPersonenMap.put(autorisatiebundel, populatieMap);

        final List<VerwerkPersoonBericht> verwerkPersoonBerichten =
                service.maakBerichten(persoonslijst.getAdministratieveHandeling().getId(), bijgehoudenPersonenMap, teLeverenPersonenMap);
        Assert.assertFalse(verwerkPersoonBerichten.isEmpty());
    }
}
