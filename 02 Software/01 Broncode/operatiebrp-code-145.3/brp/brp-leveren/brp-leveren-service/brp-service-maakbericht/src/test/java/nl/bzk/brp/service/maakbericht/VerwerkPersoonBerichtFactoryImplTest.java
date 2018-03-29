/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;

import java.util.Collections;
import java.util.HashMap;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.VerstrekkingsbeperkingService;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevensBepalingService;
import nl.bzk.brp.service.maakbericht.filterstappen.Stappenlijst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class VerwerkPersoonBerichtFactoryImplTest {

    private static final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.maakLeegPersoon().build(), 1L);

    @InjectMocks
    private VerwerkPersoonBerichtFactoryImpl verwerkPersoonBerichtFactory;
    @Mock
    private BijgehoudenPersoonFactory bijgehoudenPersoonFactory;
    @Mock
    private StatischePersoongegevensBepalingService statischePersoongegevensBepalingService;
    @Mock
    private VerstrekkingsbeperkingService verstrekkingsbeperkingService;

    @Before
    public void voorTest() {
        ReflectionTestUtils.setField(verwerkPersoonBerichtFactory, "stappenlijst", new Stappenlijst());
        Mockito.when(verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(any(), any())).thenReturn(true);
        Mockito.when(bijgehoudenPersoonFactory.maakBijgehoudenPersonen(any())).thenReturn(Collections.emptyList());
        Mockito.when(statischePersoongegevensBepalingService.bepaal(persoonslijst, false)).thenReturn(new StatischePersoongegevens());
    }

    @Test
    public void maakBerichten() throws Exception {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.GEEF_DETAILS_PERSOON);
        final MaakBerichtParameters maakBerichtParameters = MaakBerichtParameters.getInstance(autorisatiebundel,
                bijgehoudenPersoonList -> Collections.emptyList(),
                20110101,
                persoonslijst,
                SoortSynchronisatie.VOLLEDIG_BERICHT,
                null);

        verwerkPersoonBerichtFactory.maakBerichten(maakBerichtParameters);

        Mockito.verify(statischePersoongegevensBepalingService, Mockito.times(1)).bepaal(persoonslijst, false);
        Mockito.verify(bijgehoudenPersoonFactory, Mockito.times(1)).maakBijgehoudenPersonen(anyListOf(Berichtgegevens.class));
        Mockito.verifyZeroInteractions(verstrekkingsbeperkingService);
    }


    @Test
    public void maakBerichten_GeenMaakBerichtPersonen() throws Exception {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        final MaakBerichtParameters maakBerichtParameters = MaakBerichtParameters.getInstance(autorisatiebundel,
                bijgehoudenPersoonList -> Collections.emptyList(),
                20110101,
                persoonslijst,
                SoortSynchronisatie.VOLLEDIG_BERICHT,
                null);
        maakBerichtParameters.setMaakBerichtPersoonMap(new HashMap<>());

        verwerkPersoonBerichtFactory.maakBerichten(maakBerichtParameters);

        Mockito.verify(statischePersoongegevensBepalingService, Mockito.times(1)).bepaal(persoonslijst, false);
        Mockito.verify(bijgehoudenPersoonFactory, Mockito.times(1)).maakBijgehoudenPersonen(anyListOf(Berichtgegevens.class));
        //beeld van vorige hnd is null dus 1 invocatie
        Mockito.verify(verstrekkingsbeperkingService, Mockito.times(0)).heeftGeldigeVerstrekkingsbeperking(any(), any());
    }

    @Test
    public void maakBerichten_MutatieberichtMetVerstrekkingsbeperking() throws Exception {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        final MaakBerichtParameters maakBerichtParameters = MaakBerichtParameters.getInstance(autorisatiebundel,
                bijgehoudenPersoonList -> Collections.emptyList(),
                20110101,
                persoonslijst,
                SoortSynchronisatie.VOLLEDIG_BERICHT,
                null);

        verwerkPersoonBerichtFactory.maakBerichten(maakBerichtParameters);

        Mockito.verify(statischePersoongegevensBepalingService, Mockito.times(1)).bepaal(persoonslijst, false);
        Mockito.verify(bijgehoudenPersoonFactory, Mockito.times(1)).maakBijgehoudenPersonen(anyListOf(Berichtgegevens.class));
        //beeld van vorige hnd is null dus 1 invocatie
        Mockito.verify(verstrekkingsbeperkingService, Mockito.times(1)).heeftGeldigeVerstrekkingsbeperking(any(), any());
    }

    private Autorisatiebundel maakAutorisatiebundel(SoortDienst soortDienst) {
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(new PartijRol(TestPartijBuilder.maakBuilder().metCode("000000").build(), Rol.AFNEMER),
                leveringsautorisatie);
        return new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
    }
}
