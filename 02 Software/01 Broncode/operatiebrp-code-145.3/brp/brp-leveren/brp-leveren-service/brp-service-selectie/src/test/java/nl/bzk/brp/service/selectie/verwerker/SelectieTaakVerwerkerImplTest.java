/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import static org.mockito.Mockito.times;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAutorisatieBericht;
import nl.bzk.brp.domain.internbericht.selectie.SelectiePersoonBericht;
import nl.bzk.brp.domain.internbericht.selectie.SelectieTaakResultaat;
import nl.bzk.brp.domain.internbericht.selectie.SelectieVerwerkTaakBericht;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.helper.TestDatumUtil;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.PlaatsAfnemerBerichtService;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import nl.bzk.brp.service.selectie.algemeen.SelectieException;
import nl.bzk.brp.service.selectie.publicatie.SelectieTaakResultaatPublicatieService;
import nl.bzk.brp.service.selectie.verwerker.cache.VerwerkerCache;
import nl.bzk.brp.service.selectie.verwerker.persoonsbeelden.PersoonsBeeldenService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * SelectieTaakVerwerkerImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieTaakVerwerkerImplTest {

    @Mock
    private LeveringsautorisatieService leveringsautorisatieService;

    @Mock
    private SelectieTaakResultaatPublicatieService selectieTaakResultaatPublicatieService;

    @Mock
    private PersoonsBeeldenService persoonsBeeldenService;

    @Mock
    private VerwerkPersoonExecutorService persoonXMLService;

    @Mock
    private ConfiguratieService configuratieService;

    @Mock
    private VerwerkerCache verwerkerAutorisatieCache;

    @Mock
    private AfnemerindicatieVerzoekPublicatieService afnemerindicatieVerzoekPublicatieService;

    @Mock
    private VerwerkerPublicatieService verwerkerPublicatieService;

    @Mock
    private PlaatsAfnemerBerichtService plaatsAfnemerBerichtService;

    @Captor
    private ArgumentCaptor<SelectieTaakResultaat> selectieTaakResultaatArgumentCaptor;

    @InjectMocks
    private SelectieTaakVerwerkerImpl selectieTaakVerwerker;

    @Test
    public void testHappyFlowStandaard() throws SelectieException {
        final SelectieVerwerkTaakBericht selectieTaak = new SelectieVerwerkTaakBericht();
        final int selectieRunId = 1;
        selectieTaak.setSelectieRunId(selectieRunId);
        final List<SelectiePersoonBericht> selectiePersonen = new ArrayList<>();
        List<SelectieAutorisatieBericht> selectieAutorisatieBerichten = new ArrayList<>();
        //
        final SelectiePersoonBericht selectiePersoonBericht = new SelectiePersoonBericht();
        selectiePersoonBericht.setPersoonHistorieVolledigGegevens("dummy");
        selectiePersonen.add(selectiePersoonBericht);

        final SelectieAutorisatieBericht selectieAutorisatieBericht = new SelectieAutorisatieBericht();
        selectieAutorisatieBericht.setDienstId(1);
        selectieAutorisatieBericht.setToegangLeveringsAutorisatieId(1);
        selectieAutorisatieBericht.setPeilmomentFormeel(DatumUtil.nuAlsZonedDateTime());
        selectieAutorisatieBerichten.add(selectieAutorisatieBericht);

        selectieTaak.setPersonen(selectiePersonen);
        selectieTaak.setSelectieAutorisaties(selectieAutorisatieBerichten);

        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst
                .SELECTIE);
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(
                TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);
        SelectieAutorisatieBericht selectieAutorisatiebundel = new SelectieAutorisatieBericht();
        selectieAutorisatiebundel.setDienstId(autorisatiebundel.getDienst().getId());
        selectieAutorisatiebundel.setToegangLeveringsAutorisatieId(autorisatiebundel.getToegangLeveringsautorisatie().getId());

        Mockito.when(verwerkerAutorisatieCache.getAutorisatiebundel(selectieAutorisatieBericht, selectieRunId)).thenReturn(autorisatiebundel);

        final VerwerkPersoonResultaat verwerkPersoonResultaat = maakVerwerkPersoonResultaat(autorisatiebundel, selectieAutorisatiebundel);

        Collection<VerwerkPersoonResultaat> lijst = Lists.newArrayList(verwerkPersoonResultaat);
        Mockito.when(persoonXMLService.verwerkPersonen(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(lijst);
        Mockito.when(persoonsBeeldenService.maakPersoonsBeelden(Mockito.any()))
                .thenReturn(lijst.stream().map(p -> p.getPersoonslijst()).collect(Collectors.toList()));
        //verwerk
        selectieTaakVerwerker.verwerkSelectieTaak(selectieTaak);
        //verify
        Mockito.verify(selectieTaakResultaatPublicatieService, times(1)).publiceerSelectieTaakResultaat(selectieTaakResultaatArgumentCaptor.capture());
        Mockito.verify(selectieTaakResultaatPublicatieService, times(0)).publiceerFout();
        //0 gefilterd
        final SelectieTaakResultaat selectieTaakResultaat = selectieTaakResultaatArgumentCaptor.getValue();
        Assert.assertTrue(selectieTaakResultaat.getOngeldigeTaken().isEmpty());
    }


    @Test
    public void testFilterGbaSystematiekStandaard() throws SelectieException {
        final SelectieVerwerkTaakBericht selectieTaak = new SelectieVerwerkTaakBericht();
        final int selectieRunId = 1;
        selectieTaak.setSelectieRunId(selectieRunId);
        final List<SelectiePersoonBericht> selectiePersonen = new ArrayList<>();
        List<SelectieAutorisatieBericht> selectieAutorisatieBerichten = new ArrayList<>();
        //
        final SelectiePersoonBericht selectiePersoonBericht = new SelectiePersoonBericht();
        selectiePersoonBericht.setPersoonHistorieVolledigGegevens("dummy");
        selectiePersonen.add(selectiePersoonBericht);

        final SelectieAutorisatieBericht selectieAutorisatieBericht = new SelectieAutorisatieBericht();
        selectieAutorisatieBericht.setDienstId(1);
        selectieAutorisatieBericht.setToegangLeveringsAutorisatieId(1);
        //gba systematiek is 2 dagen terug
        selectieAutorisatieBericht.setPeilmomentFormeel(DatumUtil.nuAlsZonedDateTime().minusDays(3));
        selectieAutorisatieBerichten.add(selectieAutorisatieBericht);

        selectieTaak.setPersonen(selectiePersonen);
        selectieTaak.setSelectieAutorisaties(selectieAutorisatieBerichten);

        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst
                .SELECTIE);
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(
                TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);
        SelectieAutorisatieBericht selectieAutorisatiebundel = new SelectieAutorisatieBericht();
        selectieAutorisatiebundel.setDienstId(autorisatiebundel.getDienst().getId());
        selectieAutorisatiebundel.setToegangLeveringsAutorisatieId(autorisatiebundel.getToegangLeveringsautorisatie().getId());

        Mockito.when(verwerkerAutorisatieCache.getAutorisatiebundel(selectieAutorisatieBericht, selectieRunId)).thenReturn(autorisatiebundel);

        final VerwerkPersoonResultaat verwerkPersoonResultaat = maakVerwerkPersoonResultaat(autorisatiebundel, selectieAutorisatiebundel);

        Collection<VerwerkPersoonResultaat> lijst = Lists.newArrayList(verwerkPersoonResultaat);
        Mockito.when(persoonXMLService.verwerkPersonen(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(lijst);
        Mockito.when(persoonsBeeldenService.maakPersoonsBeelden(Mockito.any()))
                .thenReturn(lijst.stream().map(p -> p.getPersoonslijst()).collect(Collectors.toList()));
        //verwerk
        selectieTaakVerwerker.verwerkSelectieTaak(selectieTaak);
        //verify
        Mockito.verify(selectieTaakResultaatPublicatieService, times(1)).publiceerSelectieTaakResultaat(selectieTaakResultaatArgumentCaptor.capture());
        Mockito.verify(selectieTaakResultaatPublicatieService, times(0)).publiceerFout();
        //1 gefilterd
        final SelectieTaakResultaat selectieTaakResultaat = selectieTaakResultaatArgumentCaptor.getValue();
        Assert.assertTrue(selectieTaakResultaat.getOngeldigeTaken().size() == 1);
    }

    @Test
    public void testHappyFlowAfnemerindicatie() throws SelectieException {
        final SelectieVerwerkTaakBericht selectieTaak = new SelectieVerwerkTaakBericht();
        final int selectieRunId = 1;
        selectieTaak.setSelectieRunId(selectieRunId);
        final List<SelectiePersoonBericht> selectiePersonen = new ArrayList<>();
        List<SelectieAutorisatieBericht> selectieAutorisatieBerichten = new ArrayList<>();
        //
        final SelectiePersoonBericht selectiePersoonBericht = new SelectiePersoonBericht();
        selectiePersoonBericht.setPersoonHistorieVolledigGegevens("dummy");
        selectiePersonen.add(selectiePersoonBericht);

        final SelectieAutorisatieBericht selectieAutorisatieBericht = new SelectieAutorisatieBericht();
        selectieAutorisatieBericht.setDienstId(1);
        selectieAutorisatieBericht.setToegangLeveringsAutorisatieId(1);
        //gba systematiek is 2 dagen terug maar niet relevant voor afnemerindicatie soort
        selectieAutorisatieBericht.setPeilmomentFormeel(DatumUtil.nuAlsZonedDateTime().minusDays(3));
        selectieAutorisatieBerichten.add(selectieAutorisatieBericht);

        selectieTaak.setPersonen(selectiePersonen);
        selectieTaak.setSelectieAutorisaties(selectieAutorisatieBerichten);

        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst
                .SELECTIE);
        dienst.setSoortSelectie(SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE.getId());
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(
                TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);

        SelectieAutorisatieBericht selectieAutorisatiebundel = new SelectieAutorisatieBericht();
        selectieAutorisatiebundel.setDienstId(autorisatiebundel.getDienst().getId());
        selectieAutorisatiebundel.setToegangLeveringsAutorisatieId(autorisatiebundel.getToegangLeveringsautorisatie().getId());

        Mockito.when(verwerkerAutorisatieCache.getAutorisatiebundel(selectieAutorisatieBericht, selectieRunId)).thenReturn(autorisatiebundel);

        final VerwerkPersoonResultaat verwerkPersoonResultaat = maakVerwerkPersoonResultaat(autorisatiebundel, selectieAutorisatiebundel);

        Collection<VerwerkPersoonResultaat> lijst = Lists.newArrayList(verwerkPersoonResultaat);
        Mockito.when(persoonXMLService.verwerkPersonen(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(lijst);
        Mockito.when(persoonsBeeldenService.maakPersoonsBeelden(Mockito.any()))
                .thenReturn(lijst.stream().map(p -> p.getPersoonslijst()).collect(Collectors.toList()));

        Mockito.when(verwerkerAutorisatieCache.getAutorisatiebundel(selectieAutorisatieBericht, selectieRunId)).thenReturn(autorisatiebundel);

        //verwerk
        selectieTaakVerwerker.verwerkSelectieTaak(selectieTaak);
        //verify
        Mockito.verify(selectieTaakResultaatPublicatieService, times(1)).publiceerSelectieTaakResultaat(selectieTaakResultaatArgumentCaptor.capture());
        Mockito.verify(selectieTaakResultaatPublicatieService, times(0)).publiceerFout();
        //voor afnemerindicatie geen filtering op gba systematiek
        final SelectieTaakResultaat selectieTaakResultaat = selectieTaakResultaatArgumentCaptor.getValue();
        Assert.assertTrue(selectieTaakResultaat.getOngeldigeTaken().isEmpty());
    }

    private VerwerkPersoonResultaat maakVerwerkPersoonResultaat(Autorisatiebundel autorisatiebundel, SelectieAutorisatieBericht selectieAutorisatiebundel) {
        final VerwerkPersoonResultaat verwerkPersoonResultaat = new VerwerkPersoonResultaat();
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.maakLeegPersoon().metGroep()
                .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                .metRecord()
                .metId(1)
                .metActieInhoud(TestVerantwoording.maakActie(1, TestDatumUtil.gisteren()))
                .metAttribuut(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGING.getId(), DatumUtil.nuAlsZonedDateTime())
                .metAttribuut(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK.getId(),
                        DatumUtil.nuAlsZonedDateTime().minusDays(2))
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(Element.PERSOON_VERSIE.getId())
                .metRecord().metAttribuut(Element.PERSOON_VERSIE_LOCK.getId(), 123L).eindeRecord()
                .eindeGroep().eindeObject().build(), 1L);
        verwerkPersoonResultaat.setPersoonslijst(persoonslijst);
        verwerkPersoonResultaat.setSelectieTaakId(1);
        verwerkPersoonResultaat.setAutorisatiebundel(autorisatiebundel);
        verwerkPersoonResultaat.setPersoonFragment("persoon");
        return verwerkPersoonResultaat;
    }
}
