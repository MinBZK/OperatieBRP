/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAutorisatieBericht;
import nl.bzk.brp.domain.internbericht.selectie.SelectiePersoonBericht;
import nl.bzk.brp.domain.internbericht.selectie.SelectieVerwerkTaakBericht;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import nl.bzk.brp.service.selectie.algemeen.SelectieException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * PersoonBerichtFragmentServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class PersoonXMLServiceImplTest {

    @Mock
    private ConfiguratieService configuratieService;

    @Mock
    private VerwerkPersoonService verwerkPersoonService;

    @InjectMocks
    private VerwerkPersoonExecutorServiceImpl persoonBerichtFragmentService;


    @Test
    public void testHappyFlow() throws SelectieException {
        Mockito.when(configuratieService.getVerwerkerPoolSize()).thenReturn(1);
        Mockito.when(configuratieService.getMaximaleWachttijdPersoonslijstFragmentMin()).thenReturn(10L);

        final SelectieVerwerkTaakBericht selectieTaak = new SelectieVerwerkTaakBericht();
        selectieTaak.setSelectieRunId(1);
        final List<SelectiePersoonBericht> selectiePersonen = new ArrayList<>();
        List<SelectieAutorisatieBericht> selectieAutorisatieBerichten = new ArrayList<>();
        //
        final SelectiePersoonBericht selectiePersoonBericht = new SelectiePersoonBericht();
        final String dummy = "dummy";
        selectiePersoonBericht.setPersoonHistorieVolledigGegevens(new String(Base64.getEncoder().encode(dummy.getBytes())));
        selectiePersonen.add(selectiePersoonBericht);

        final SelectieAutorisatieBericht selectieAutorisatieBericht = new SelectieAutorisatieBericht();
        selectieAutorisatieBericht.setDienstId(1);
        selectieAutorisatieBericht.setToegangLeveringsAutorisatieId(1);
        selectieAutorisatieBerichten.add(selectieAutorisatieBericht);

        selectieTaak.setPersonen(selectiePersonen);
        selectieTaak.setSelectieAutorisaties(selectieAutorisatieBerichten);

        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst
                .SELECTIE);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(
                TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);

        final List<SelectieAutorisatiebundel> autorisatiebundels = new ArrayList<>();
        autorisatiebundels.add(new SelectieAutorisatiebundel(autorisatiebundel, selectieAutorisatieBericht));
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);

        //maak
        persoonBerichtFragmentService.verwerkPersonen(selectieTaak, Lists.newArrayList(persoonslijst), autorisatiebundels);
    }

    @Test(expected = SelectieException.class)
    public void testFoutFlow() throws SelectieException, StapException {
        Mockito.when(configuratieService.getVerwerkerPoolSize()).thenReturn(1);
        Mockito.when(configuratieService.getMaximaleWachttijdPersoonslijstFragmentMin()).thenReturn(1L);

        final SelectieVerwerkTaakBericht selectieTaak = new SelectieVerwerkTaakBericht();
        selectieTaak.setSelectieRunId(1);
        final List<SelectiePersoonBericht> selectiePersonen = new ArrayList<>();
        List<SelectieAutorisatieBericht> selectieAutorisatyBerichts = new ArrayList<>();
        //
        final SelectiePersoonBericht selectiePersoonBericht = new SelectiePersoonBericht();
        final String dummy = "dummy";
        selectiePersoonBericht.setPersoonHistorieVolledigGegevens(new String(Base64.getEncoder().encode(dummy.getBytes())));
        selectiePersonen.add(selectiePersoonBericht);

        final SelectieAutorisatieBericht selectieAutorisatieBericht = new SelectieAutorisatieBericht();
        selectieAutorisatieBericht.setDienstId(1);
        selectieAutorisatieBericht.setToegangLeveringsAutorisatieId(1);
        selectieAutorisatyBerichts.add(selectieAutorisatieBericht);

        selectieTaak.setPersonen(selectiePersonen);
        selectieTaak.setSelectieAutorisaties(selectieAutorisatyBerichts);

        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst
                .SELECTIE);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(
                TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);

        final List<SelectieAutorisatiebundel> autorisatiebundels = new ArrayList<>();
        autorisatiebundels.add(new SelectieAutorisatiebundel(autorisatiebundel, selectieAutorisatieBericht));

        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);

        //error
        Mockito.when(verwerkPersoonService.verwerk(Mockito.any())).thenThrow((new RuntimeException()));
        //maak
        persoonBerichtFragmentService.verwerkPersonen(selectieTaak, Lists.newArrayList(persoonslijst), autorisatiebundels);
    }
}
