/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.algemeen.TestPartijRolBuilder;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAutorisatieBericht;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.MeldingBepalerService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.maakbericht.VerwerkPersoonBerichtFactory;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtHistorieFilterInformatie;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SelectieResultaatBerichtFactoryImplTest {

    static {
        BrpNu.set();
    }

    private static final ZonedDateTime zdtNu = BrpNu.get().getDatum();

    @InjectMocks
    private SelectieResultaatBerichtFactoryImpl selectieResultaatBerichtFactory;

    @Mock
    private VerwerkPersoonBerichtFactory verwerkPersoonBerichtFactory;

    @Mock
    private PartijService partijService;

    @Mock
    private MeldingBepalerService meldingBepalerService;

    @Captor
    private ArgumentCaptor<MaakBerichtParameters> maakBerichtParametersArgumentCaptor;


    @Test
    public void testMaakSelectieResultaatBericht_GeenHistorieFilter() {
        final SelectieAutorisatiebundel selectieAutorisatiebundel = maakSelectieAutorisatiebundel();
        final Dienst dienst = selectieAutorisatiebundel.getAutorisatiebundel().getDienst();
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.maakLeegPersoon(1).build(), 1L);

        selectieResultaatBerichtFactory.maakBerichten(Lists.newArrayList(selectieAutorisatiebundel), persoonslijst);

        //assert
        Mockito.verify(verwerkPersoonBerichtFactory).maakBerichten(maakBerichtParametersArgumentCaptor.capture());
        Assert.assertTrue(maakBerichtParametersArgumentCaptor.getValue().isVerantwoordingLeveren());
        assertEquals(selectieAutorisatiebundel.getAutorisatiebundel(), Iterables.getOnlyElement(maakBerichtParametersArgumentCaptor.getValue()
                .getAutorisatiebundels()));
        Assert.assertNull(maakBerichtParametersArgumentCaptor.getValue().getMaakBerichtPersoonMap().get(selectieAutorisatiebundel.getAutorisatiebundel())
                .get(persoonslijst)
                .getHistorieFilterInformatie());
    }


    @Test
    public void testMaakSelectieResultaatBericht_HistorieFilter_HistorievormInDienst_BeidePeilmomentenGevuld() {
        final SelectieAutorisatiebundel selectieAutorisatiebundel = maakSelectieAutorisatiebundel();
        final Dienst dienst = selectieAutorisatiebundel.getAutorisatiebundel().getDienst();
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        dienst.setHistorievormSelectie(HistorieVorm.MATERIEEL_FORMEEL.getId());

        selectieAutorisatiebundel.getSelectieAutorisatieBericht().setPeilmomentMaterieel(20140101);
        selectieAutorisatiebundel.getSelectieAutorisatieBericht().setPeilmomentFormeel(zdtNu);
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.maakLeegPersoon(1).build(), 1L);

        selectieResultaatBerichtFactory.maakBerichten(Lists.newArrayList(selectieAutorisatiebundel), persoonslijst);

        //assert
        Mockito.verify(verwerkPersoonBerichtFactory).maakBerichten(maakBerichtParametersArgumentCaptor.capture());
        Assert.assertTrue(maakBerichtParametersArgumentCaptor.getValue().isVerantwoordingLeveren());
        assertEquals(selectieAutorisatiebundel.getAutorisatiebundel(), Iterables.getOnlyElement(maakBerichtParametersArgumentCaptor.getValue()
                .getAutorisatiebundels()));
        MaakBerichtHistorieFilterInformatie historieFilterInformatie = maakBerichtParametersArgumentCaptor.getValue().getMaakBerichtPersoonMap().get
                (selectieAutorisatiebundel.getAutorisatiebundel()).get(persoonslijst).getHistorieFilterInformatie();
        assertEquals(HistorieVorm.MATERIEEL_FORMEEL, historieFilterInformatie.getHistorieVorm());
        assertEquals(zdtNu, historieFilterInformatie.getPeilmomentFormeel());
        assertEquals(20140101, historieFilterInformatie.getPeilmomentMaterieel().intValue());
    }


    @Test
    public void testMaakSelectieResultaatBericht_HistorieFilter_GeenHistorievormInDienst_BeidePeilmomentenGevuld() {
        final SelectieAutorisatiebundel selectieAutorisatiebundel = maakSelectieAutorisatiebundel();
        selectieAutorisatiebundel.getSelectieAutorisatieBericht().setPeilmomentMaterieel(20140101);
        selectieAutorisatiebundel.getSelectieAutorisatieBericht().setPeilmomentFormeel(zdtNu);
        final Dienst dienst = selectieAutorisatiebundel.getAutorisatiebundel().getDienst();
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.maakLeegPersoon(1).build(), 1L);

        selectieResultaatBerichtFactory.maakBerichten(Lists.newArrayList(selectieAutorisatiebundel), persoonslijst);

        //assert
        Mockito.verify(verwerkPersoonBerichtFactory).maakBerichten(maakBerichtParametersArgumentCaptor.capture());
        Assert.assertTrue(maakBerichtParametersArgumentCaptor.getValue().isVerantwoordingLeveren());
        assertEquals(selectieAutorisatiebundel.getAutorisatiebundel(), Iterables.getOnlyElement(maakBerichtParametersArgumentCaptor.getValue()
                .getAutorisatiebundels()));
        MaakBerichtHistorieFilterInformatie historieFilterInformatie = maakBerichtParametersArgumentCaptor.getValue().getMaakBerichtPersoonMap().get
                (selectieAutorisatiebundel.getAutorisatiebundel()).get(persoonslijst).getHistorieFilterInformatie();
        assertEquals(HistorieVorm.GEEN, historieFilterInformatie.getHistorieVorm());
        assertEquals(zdtNu, historieFilterInformatie.getPeilmomentFormeel());
        assertEquals(20140101, historieFilterInformatie.getPeilmomentMaterieel().intValue());
    }


    @Test
    public void maakBerichtenAfnemerIndicatie_Plaatsing() {
        final Autorisatiebundel autorisatiebundel = TestAutorisaties.maakAutorisatiebundel(SoortDienst.SELECTIE);
        autorisatiebundel.getDienst().setSoortSelectie(SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE.getId());
        final List<SelectieAutorisatiebundel>
                autorisatiebundels =
                Lists.newArrayList(new SelectieAutorisatiebundel(autorisatiebundel, new SelectieAutorisatieBericht()));
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.maakIngeschrevenPersoon().build(), 1L);

        Mockito.when(verwerkPersoonBerichtFactory.maakBerichten(any())).thenReturn(
                Lists.newArrayList(new VerwerkPersoonBericht(null, autorisatiebundel, null))
        );

        final List<VerwerkPersoonBericht> berichten =
                selectieResultaatBerichtFactory.maakBerichten(autorisatiebundels, persoonslijst);

        Mockito.verify(verwerkPersoonBerichtFactory).maakBerichten(maakBerichtParametersArgumentCaptor.capture());
        assertEquals(1, berichten.size());
    }

    @Test
    public void maakBerichtenAfnemerIndicatie_Verwijdering() {
        final Autorisatiebundel autorisatiebundel = TestAutorisaties.maakAutorisatiebundel(SoortDienst.SELECTIE);
        autorisatiebundel.getDienst().setSoortSelectie(SoortSelectie.SELECTIE_MET_VERWIJDERING_AFNEMERINDICATIE.getId());
        final List<SelectieAutorisatiebundel> autorisatiebundels = Lists.newArrayList(new SelectieAutorisatiebundel(autorisatiebundel, null));
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.maakLeegPersoon(1).build(), 1L);

        Mockito.when(verwerkPersoonBerichtFactory.maakBerichten(any())).thenReturn(
                Lists.newArrayList(new VerwerkPersoonBericht(null, autorisatiebundel, null))
        );

        final List<VerwerkPersoonBericht> berichten =
                selectieResultaatBerichtFactory.maakBerichten(autorisatiebundels, persoonslijst);

        Mockito.verify(verwerkPersoonBerichtFactory).maakBerichten(maakBerichtParametersArgumentCaptor.capture());
        assertEquals(1, berichten.size());
    }

    @Test
    public void testBijgehoudenPersoonBerichtDecorator() {
        final Autorisatiebundel autorisatiebundel = TestAutorisaties.maakAutorisatiebundel(SoortDienst.SELECTIE);
        final Dienst dienst = autorisatiebundel.getDienst();
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.maakLeegPersoon().build(), 1L);
        final BijgehoudenPersoon
                bijgehoudenPersoon =
                new BijgehoudenPersoon.Builder(persoonslijst, new BerichtElement("test")).build();
        final Map<Autorisatiebundel, List<BijgehoudenPersoon>> bijgehoudenPersonenPerAutorisatie
                = new HashMap<>();
        bijgehoudenPersonenPerAutorisatie.put(autorisatiebundel, Lists.newArrayList(bijgehoudenPersoon));

        Mockito.when(verwerkPersoonBerichtFactory.maakBerichten(any())).thenAnswer(invocationOnMock -> {
            MaakBerichtParameters maakBerichtParameters = (MaakBerichtParameters) invocationOnMock.getArguments()[0];
            return maakBerichtParameters.getBijgehoudenPersoonBerichtDecorator().build(bijgehoudenPersonenPerAutorisatie);
        });

        final List<VerwerkPersoonBericht> berichten =
                selectieResultaatBerichtFactory
                        .maakBerichten(Lists.newArrayList(new SelectieAutorisatiebundel(autorisatiebundel, new SelectieAutorisatieBericht())), persoonslijst);

        assertEquals(autorisatiebundel, Iterables.getOnlyElement(berichten).getAutorisatiebundel());
        assertEquals(bijgehoudenPersoon, Iterables.getOnlyElement(berichten).getBijgehoudenPersonen().get(0));
    }


    @Test
    public void testBijgehoudenPersoonBerichtDecorator_Afnemerindicatie() {
        final Autorisatiebundel autorisatiebundel = TestAutorisaties.maakAutorisatiebundel(SoortDienst.SELECTIE);
        final Dienst dienst = autorisatiebundel.getDienst();
        dienst.setSoortSelectie(SoortSelectie.SELECTIE_MET_VERWIJDERING_AFNEMERINDICATIE.getId());
        dienst.setIndVerzVolBerBijWijzAfniNaSelectie(true);
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.maakLeegPersoon().build(), 1L);
        final BijgehoudenPersoon
                bijgehoudenPersoon =
                new BijgehoudenPersoon.Builder(persoonslijst, new BerichtElement("test")).build();
        final Map<Autorisatiebundel, List<BijgehoudenPersoon>> bijgehoudenPersonenPerAutorisatie
                = new HashMap<>();
        bijgehoudenPersonenPerAutorisatie.put(autorisatiebundel, Lists.newArrayList(bijgehoudenPersoon));

        Mockito.when(verwerkPersoonBerichtFactory.maakBerichten(any())).thenAnswer(invocationOnMock -> {
            MaakBerichtParameters maakBerichtParameters = (MaakBerichtParameters) invocationOnMock.getArguments()[0];
            return maakBerichtParameters.getBijgehoudenPersoonBerichtDecorator().build(bijgehoudenPersonenPerAutorisatie);
        });

        Mockito.when(partijService.geefBrpPartij()).thenReturn(TestPartijBuilder.maakBuilder().metId(1).metCode("199903").build());

        final List<VerwerkPersoonBericht> berichten =
                selectieResultaatBerichtFactory
                        .maakBerichten(Lists.newArrayList(new SelectieAutorisatiebundel(autorisatiebundel, new SelectieAutorisatieBericht())), persoonslijst);

        assertEquals(autorisatiebundel, Iterables.getOnlyElement(berichten).getAutorisatiebundel());
        assertEquals(bijgehoudenPersoon, Iterables.getOnlyElement(berichten).getBijgehoudenPersonen().get(0));
    }


    private SelectieAutorisatiebundel maakSelectieAutorisatiebundel() {
        final Partij partij = TestPartijBuilder.maakBuilder().metCode("123456").build();
        final PartijRol partijRol = TestPartijRolBuilder.maker().metId(1).metRol(Rol.AFNEMER).metPartij(partij).maak();
        final Leveringsautorisatie levAut = TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, levAut);
        final Autorisatiebundel autorisatiebundel =
                new Autorisatiebundel(tla, levAut.getDienstbundelSet().iterator().next().getDienstSet().iterator().next());

        final SelectieAutorisatieBericht selectieAutorisatieBericht = new SelectieAutorisatieBericht();
        selectieAutorisatieBericht.setToegangLeveringsAutorisatieId(tla.getId());
        return new SelectieAutorisatiebundel(autorisatiebundel, selectieAutorisatieBericht);
    }
}
