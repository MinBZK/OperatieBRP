/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.schrijver;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyZeroInteractions;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import nl.bzk.brp.service.selectie.publicatie.SelectieTaakResultaatPublicatieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * PersoonFragmenterVerwerkerServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class MaakSelectieResultaatTaakVerwerkerServiceImplTest {

    @Mock
    private SelectieResultaatWriterFactory selectieResultaatWriterFactory;

    @Mock
    private ConfiguratieService configuratieService;

    @Mock
    private SelectieTaakResultaatPublicatieService selectieTaakResultaatPublicatieService;

    @Mock
    private SelectieFileService selectieFileService;

    @Mock
    private PartijService partijService;

    @Mock
    private LeveringsautorisatieService leveringsautorisatieService;

    @Mock
    private SteekproefService steekproefService;

    @InjectMocks
    private MaakSelectieResultaatTaakVerwerkerServiceImpl persoonFragmenterVerwerkerService;

    @Before
    public void setUp() {
        BrpNu.set();
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst
                .SELECTIE);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(
                TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);
        final ToegangLeveringsAutorisatie tla = autorisatiebundel.getToegangLeveringsautorisatie();
        Mockito.when(leveringsautorisatieService.geefToegangLeveringsAutorisatie(Mockito.anyInt())).thenReturn(tla);
    }

    @Test
    public void testHappyFlow() throws IOException, SelectieResultaatVerwerkException {

        Mockito.when(configuratieService.getSchrijverPoolSize()).thenReturn(1);
        Mockito.when(configuratieService.getMaximaleWachttijdFragmentVerwerkerMin()).thenReturn(10L);
        List<Path> paths = new ArrayList<>();
        paths.add(new DummyPath());
        Mockito.when(selectieFileService.geefFragmentFiles(Mockito.any())).thenReturn(paths);

        final String persoonFragment = "regel1";
        final List<String> regels = Lists.newArrayList(new String(Base64.getEncoder().encode(persoonFragment.getBytes())));
        final Stream<String> stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                regels.iterator(), Spliterator.ORDERED | Spliterator.NONNULL), false);
        Mockito.when(selectieFileService.leesFragmentRegels(Mockito.any())).thenReturn(stream);

        final MaakSelectieResultaatTaak maakSelectieResultaatTaak = new MaakSelectieResultaatTaak();
        maakSelectieResultaatTaak.setSelectieRunId(1);
        maakSelectieResultaatTaak.setDienstId(1);
        maakSelectieResultaatTaak.setToegangLeveringsAutorisatieId(1);

        final SelectieResultaatWriterFactory.PersoonBestandWriter
                persoonBestandWriter =
                Mockito.mock(SelectieResultaatWriterFactory.PersoonBestandWriter.class);
        Mockito.when(selectieResultaatWriterFactory.persoonWriterBrp(Mockito.any(), Mockito.any())).thenReturn(persoonBestandWriter);
        final SelectieResultaatWriterFactory.TotalenBestandWriter
                totalenBestandWriter =
                Mockito.mock(SelectieResultaatWriterFactory.TotalenBestandWriter.class);
        Mockito.when(selectieResultaatWriterFactory.totalenWriterBrp(Mockito.any(), Mockito.any())).thenReturn(totalenBestandWriter);

        persoonFragmenterVerwerkerService.verwerk(maakSelectieResultaatTaak);

        Mockito.verify(persoonBestandWriter).voegPersoonToe(persoonFragment);
        Mockito.verify(totalenBestandWriter).schrijfTotalen(1, 2);
        Mockito.verify(steekproefService).maakSteekproefBestand(maakSelectieResultaatTaak);

        Mockito.verify(selectieTaakResultaatPublicatieService, times(1)).publiceerSelectieTaakResultaat(Mockito.any());
    }

    @Test
    public void testFlowSelectietaakOngeldig() throws IOException, SelectieResultaatVerwerkException {

        Mockito.when(configuratieService.getSchrijverPoolSize()).thenReturn(1);
        Mockito.when(configuratieService.getMaximaleWachttijdFragmentVerwerkerMin()).thenReturn(10L);
        List<Path> paths = new ArrayList<>();
        paths.add(new DummyPath());
        Mockito.when(selectieFileService.geefFragmentFiles(Mockito.any())).thenReturn(paths);

        final String persoonFragment = "regel1";
        final List<String> regels = Lists.newArrayList(new String(Base64.getEncoder().encode(persoonFragment.getBytes())));
        final Stream<String> stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                regels.iterator(), Spliterator.ORDERED | Spliterator.NONNULL), false);
        Mockito.when(selectieFileService.leesFragmentRegels(Mockito.any())).thenReturn(stream);

        final MaakSelectieResultaatTaak maakSelectieResultaatTaak = new MaakSelectieResultaatTaak();
        maakSelectieResultaatTaak.setSelectieRunId(1);
        maakSelectieResultaatTaak.setDienstId(1);
        maakSelectieResultaatTaak.setToegangLeveringsAutorisatieId(1);
        maakSelectieResultaatTaak.setOngeldig(true);

        final SelectieResultaatWriterFactory.PersoonBestandWriter
                persoonBestandWriter =
                Mockito.mock(SelectieResultaatWriterFactory.PersoonBestandWriter.class);
        Mockito.when(selectieResultaatWriterFactory.persoonWriterBrp(Mockito.any(), Mockito.any())).thenReturn(persoonBestandWriter);
        final SelectieResultaatWriterFactory.TotalenBestandWriter
                totalenBestandWriter =
                Mockito.mock(SelectieResultaatWriterFactory.TotalenBestandWriter.class);
        Mockito.when(selectieResultaatWriterFactory.totalenWriterBrp(Mockito.any(), Mockito.any())).thenReturn(totalenBestandWriter);

        persoonFragmenterVerwerkerService.verwerk(maakSelectieResultaatTaak);

        verifyZeroInteractions(totalenBestandWriter);
        verifyZeroInteractions(persoonBestandWriter);
        verifyZeroInteractions(steekproefService);

        Mockito.verify(selectieTaakResultaatPublicatieService, times(1)).publiceerSelectieTaakResultaat(Mockito.any());
    }


    @Test
    public void testFoutFlow() throws IOException {
        Mockito.when(configuratieService.getSchrijverPoolSize()).thenReturn(1);
        Mockito.when(configuratieService.getMaximaleWachttijdFragmentVerwerkerMin()).thenReturn(10L);
        List<Path> paths = new ArrayList<>();
        paths.add(new DummyPath());
        Mockito.when(selectieFileService.geefFragmentFiles(Mockito.any())).thenReturn(paths);
        Mockito.when(selectieFileService.leesFragmentRegels(Mockito.any())).thenThrow(new IOException());

        final MaakSelectieResultaatTaak selectiePersoonFragmentSchrijfTaak = new MaakSelectieResultaatTaak();
        selectiePersoonFragmentSchrijfTaak.setSelectieRunId(1);
        selectiePersoonFragmentSchrijfTaak.setDienstId(1);
        selectiePersoonFragmentSchrijfTaak.setToegangLeveringsAutorisatieId(1);

        persoonFragmenterVerwerkerService.verwerk(selectiePersoonFragmentSchrijfTaak);

        Mockito.verify(selectieTaakResultaatPublicatieService, times(0)).publiceerSelectieTaakResultaat(Mockito.any());
        Mockito.verify(selectieTaakResultaatPublicatieService, times(1)).publiceerFout();
    }

    @Test
    public void testTimeoutFlow() throws IOException {

        Mockito.when(configuratieService.getSchrijverPoolSize()).thenReturn(1);
        Mockito.when(configuratieService.getMaximaleWachttijdFragmentVerwerkerMin()).thenReturn(0L);
        final String persoonFragment = "regel1";
        final List<String> regels = Lists.newArrayList(new String(Base64.getEncoder().encode(persoonFragment.getBytes())));
        final Stream<String> stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                regels.iterator(), Spliterator.ORDERED | Spliterator.NONNULL), false);

        List<Path> paths = new ArrayList<>();
        paths.add(new DummyPath());
        Mockito.when(selectieFileService.geefFragmentFiles(Mockito.any())).thenReturn(paths);
        Mockito.when(selectieFileService.leesFragmentRegels(Mockito.any())).thenReturn(stream);

        final MaakSelectieResultaatTaak selectiePersoonFragmentSchrijfTaak = new MaakSelectieResultaatTaak();
        selectiePersoonFragmentSchrijfTaak.setSelectieRunId(1);
        selectiePersoonFragmentSchrijfTaak.setDienstId(1);
        selectiePersoonFragmentSchrijfTaak.setToegangLeveringsAutorisatieId(1);

        persoonFragmenterVerwerkerService.verwerk(selectiePersoonFragmentSchrijfTaak);

        Mockito.verify(selectieTaakResultaatPublicatieService, times(0)).publiceerSelectieTaakResultaat(Mockito.any());
        Mockito.verify(selectieTaakResultaatPublicatieService, times(1)).publiceerFout();
    }


}
