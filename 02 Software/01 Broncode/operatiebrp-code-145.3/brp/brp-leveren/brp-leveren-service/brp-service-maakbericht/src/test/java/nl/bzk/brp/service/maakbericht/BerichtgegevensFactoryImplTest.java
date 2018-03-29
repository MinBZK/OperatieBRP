/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht;

import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevensBepalingService;
import nl.bzk.brp.service.maakbericht.filterstappen.MaakBerichtStap;
import nl.bzk.brp.service.maakbericht.filterstappen.Stappenlijst;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class BerichtgegevensFactoryImplTest {

    @InjectMocks
    private VerwerkPersoonBerichtFactoryImpl berichtgegevensFactory;

    @Mock
    private BijgehoudenPersoonFactory maakPersoonBerichtService;
    @Mock
    private StatischePersoongegevensBepalingService statischePersoonGegevensBepalingService;

    @Test
    public void voorTest() {
        final MaakBerichtStap stap1 = Mockito.mock(MaakBerichtStap.class);

        ReflectionTestUtils.setField(berichtgegevensFactory, "stappenlijst", new Stappenlijst(stap1));

        final Berichtgegevens berichtGegevens = new Berichtgegevens(null, null, null, null, null);

        Mockito.doAnswer(invocationOnMock -> {
            ((Berichtgegevens) invocationOnMock.getArguments()[0]).setLeegBericht(false);
            return null;
        }).when(stap1).execute(berichtGegevens);

        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.maakIngeschrevenPersoon().build(), 0L);
        maakBerichtParameters.setBijgehoudenPersonen(Lists.newArrayList(persoonslijst));
        maakBerichtParameters.setAdministratieveHandelingId(persoonslijst.getAdministratieveHandeling().getId());
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));

        final MaakBerichtPersoonInformatie maakBerichtPersoon = new MaakBerichtPersoonInformatie(null);
        final Map<Persoonslijst, MaakBerichtPersoonInformatie> maakBerichtPersoonMap = new HashMap<>();
        maakBerichtPersoonMap.put(persoonslijst, maakBerichtPersoon);
        final Map<Autorisatiebundel, Map<Persoonslijst, MaakBerichtPersoonInformatie>> autorisatiebundelMapMap = new HashMap<>();
        autorisatiebundelMapMap.put(autorisatiebundel, maakBerichtPersoonMap);
        maakBerichtParameters.setMaakBerichtPersoonMap(autorisatiebundelMapMap);

        maakBerichtParameters.setBijgehoudenPersoonBerichtDecorator(bijgehoudenPersoonList -> null);
        berichtgegevensFactory.maakBerichten(maakBerichtParameters);

        Mockito.verify(stap1, Mockito.times(1)).execute(Mockito.any());
    }

    private Autorisatiebundel maakAutorisatiebundel(final SoortDienst soortDienst) {
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(new PartijRol(TestPartijBuilder.maakBuilder().metCode("000000").build(), Rol.AFNEMER),
                leveringsautorisatie);
        return new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
    }
}
