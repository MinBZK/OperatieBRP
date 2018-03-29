/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAutorisatieBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.MeldingBepalerService;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.SynchronisatieBerichtGegevensFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * VerwerkPersoonServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class VerwerkPersoonServiceImplTest {

    @Mock
    private SelectieResultaatBerichtFactory selectieBerichtFactory;
    @Mock
    private SelectieMaakFragmentBerichtService maakSelectiePersoonBerichtService;
    @Mock
    private SelectieBepaalLeverenService selectieBepaalLeverenService;
    @Mock
    private SynchronisatieBerichtGegevensFactory synchronisatieBerichtGegevensFactory;
    @Mock
    private MeldingBepalerService meldingBepalerService;

    @InjectMocks
    private VerwerkPersoonServiceImpl verwerkPersoonService;

    @Before
    public void voorTest() {
        Mockito.when(meldingBepalerService.geefWaarschuwingen(Mockito.any(BijgehoudenPersoon.class))).thenReturn(Collections.EMPTY_LIST);
    }

    @Test
    public void testStandaardSelectie() throws StapException {
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst.SELECTIE);
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);
        autorisatiebundel.getLeveringsautorisatie().setProtocolleringsniveau(Protocolleringsniveau.GEHEIM);
        SelectieAutorisatieBericht selectieAutorisatiebundel = new SelectieAutorisatieBericht();
        selectieAutorisatiebundel.setDienstId(autorisatiebundel.getDienst().getId());
        selectieAutorisatiebundel.setToegangLeveringsAutorisatieId(autorisatiebundel.getToegangLeveringsautorisatie().getId());

        final MaakSelectieResultaatOpdracht maakSelectieOpdracht = new MaakSelectieResultaatOpdracht();

        maakSelectieOpdracht.setAutorisatiebundels(Lists.newArrayList(new SelectieAutorisatiebundel(autorisatiebundel, selectieAutorisatiebundel)));
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 1L);
        maakSelectieOpdracht.setPersoonslijst(persoonslijst);

        BijgehoudenPersoon bijgehoudenPersoon = new BijgehoudenPersoon.Builder(persoonslijst, null).build();
        VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(null, autorisatiebundel, Lists.newArrayList(bijgehoudenPersoon));
        final List<VerwerkPersoonBericht> resultatenBericht = Lists.newArrayList(bericht);

        Mockito.when(selectieBerichtFactory.maakBerichten(Mockito.any(), Mockito.any())).thenReturn(Lists.newArrayList(resultatenBericht));
        Mockito.when(maakSelectiePersoonBerichtService.maakPersoonFragment(Mockito.any(), Mockito.any())).thenReturn("xml");
        Mockito.when(selectieBepaalLeverenService
                .inSelectie(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean(), Mockito.anyInt()))
                .thenReturn(true);

        final List<VerwerkPersoonResultaat> resultaten = verwerkPersoonService.verwerk(maakSelectieOpdracht);

        Assert.assertTrue(resultaten.size() == 1);

        final VerwerkPersoonResultaat verwerkPersoonResultaat = resultaten.stream().findFirst().orElse(null);

        Assert.assertNotNull(verwerkPersoonResultaat.getPersoonFragment());
    }

    @Test
    public void testStandaardSelectieNiksInSelectie() throws StapException {
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst.SELECTIE);
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);
        autorisatiebundel.getLeveringsautorisatie().setProtocolleringsniveau(Protocolleringsniveau.GEHEIM);
        SelectieAutorisatieBericht selectieAutorisatiebundel = new SelectieAutorisatieBericht();
        selectieAutorisatiebundel.setSelectietaakId(1);
        selectieAutorisatiebundel.setDienstId(autorisatiebundel.getDienst().getId());
        selectieAutorisatiebundel.setToegangLeveringsAutorisatieId(autorisatiebundel.getToegangLeveringsautorisatie().getId());

        final MaakSelectieResultaatOpdracht maakSelectieOpdracht = new MaakSelectieResultaatOpdracht();

        maakSelectieOpdracht.setAutorisatiebundels(Lists.newArrayList(new SelectieAutorisatiebundel(autorisatiebundel, selectieAutorisatiebundel)));
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 1L);
        maakSelectieOpdracht.setPersoonslijst(persoonslijst);

        BijgehoudenPersoon bijgehoudenPersoon = new BijgehoudenPersoon.Builder(persoonslijst, null).build();
        VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(null, autorisatiebundel, Lists.newArrayList(bijgehoudenPersoon));
        final List<VerwerkPersoonBericht> resultatenBericht = Lists.newArrayList(bericht);

        Mockito.when(selectieBerichtFactory.maakBerichten(Mockito.any(), Mockito.any())).thenReturn(Lists.newArrayList(resultatenBericht));
        Mockito.when(maakSelectiePersoonBerichtService.maakPersoonFragment(Mockito.any(), Mockito.any())).thenReturn("xml");
        Mockito.when(selectieBepaalLeverenService
                .inSelectie(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean(), Mockito.anyInt()))
                .thenReturn(false);

        final List<VerwerkPersoonResultaat> resultaten = verwerkPersoonService.verwerk(maakSelectieOpdracht);

        Assert.assertTrue(resultaten.isEmpty());

    }


    @Test
    public void testAfnemerindicatie() throws StapException {
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst.SELECTIE);
        dienst.setSoortSelectie(SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE.getId());
        dienst.setIndVerzVolBerBijWijzAfniNaSelectie(true);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);
        autorisatiebundel.getLeveringsautorisatie().setProtocolleringsniveau(Protocolleringsniveau.GEHEIM);
        SelectieAutorisatieBericht selectieAutorisatiebundel = new SelectieAutorisatieBericht();
        selectieAutorisatiebundel.setDienstId(autorisatiebundel.getDienst().getId());
        selectieAutorisatiebundel.setToegangLeveringsAutorisatieId(autorisatiebundel.getToegangLeveringsautorisatie().getId());

        final MaakSelectieResultaatOpdracht maakSelectieOpdracht = new MaakSelectieResultaatOpdracht();

        maakSelectieOpdracht.setAutorisatiebundels(Lists.newArrayList(new SelectieAutorisatiebundel(autorisatiebundel, selectieAutorisatiebundel)));
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 1L);
        maakSelectieOpdracht.setPersoonslijst(persoonslijst);

        BijgehoudenPersoon bijgehoudenPersoon = new BijgehoudenPersoon.Builder(persoonslijst, null).build();
        VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(null, autorisatiebundel, Lists.newArrayList(bijgehoudenPersoon));
        final List<VerwerkPersoonBericht> resultatenBericht = Lists.newArrayList(bericht);

        SynchronisatieBerichtGegevens synchronisatieBerichtgegevens = SynchronisatieBerichtGegevens.builder().build();
        Mockito.when(synchronisatieBerichtGegevensFactory.maak(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(synchronisatieBerichtgegevens);

        Mockito.when(maakSelectiePersoonBerichtService.maakPersoonFragment(Mockito.any(), Mockito.any())).thenReturn("xml");
        Mockito.when(selectieBepaalLeverenService
                .inSelectie(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean(), Mockito.anyInt()))
                .thenReturn(true);
        Mockito.when(selectieBerichtFactory.maakBerichten(Mockito.any(), Mockito.any())).thenReturn(resultatenBericht);

        final List<VerwerkPersoonResultaat> resultaten = verwerkPersoonService.verwerk(maakSelectieOpdracht);

        Assert.assertTrue(resultaten.size() == 1);
        final VerwerkPersoonResultaat verwerkPersoonResultaat = resultaten.stream().findFirst().orElse(null);

        Assert.assertNotNull(verwerkPersoonResultaat.getSynchronisatieBerichtGegevens());
    }

}
