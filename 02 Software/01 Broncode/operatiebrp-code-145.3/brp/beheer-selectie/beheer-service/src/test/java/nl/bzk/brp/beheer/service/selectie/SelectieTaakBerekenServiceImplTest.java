/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.selectie;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EenheidSelectieInterval;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.beheer.service.dal.DienstRepository;
import nl.bzk.brp.beheer.service.dal.SelectieTaakRepository;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link SelectieTaakBerekenServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieTaakBerekenServiceImplTest {

    @InjectMocks
    private SelectieTaakBerekenServiceImpl selectieTaakBerekenService;
    @Mock
    private DienstRepository dienstRepository;
    @Mock
    private SelectieTaakRepository selectieTaakRepository;
    @Mock
    private OverlappendeSelectieTaakFilterService overlappendeSelectieTaakFilterService;
    @Mock
    private SelectieTaakAutorisatieFilterService selectieTaakAutorisatieFilterService;
    private Dienst dienst;
    private ToegangLeveringsAutorisatie toegangLeveringsAutorisatie;
    private Leveringsautorisatie leveringsautorisatie;

    @Before
    public void before() {
        leveringsautorisatie = TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE);
        List<Dienst>
                diensten =
                leveringsautorisatie.getDienstbundelSet().stream().map(Dienstbundel::getDienstSet).flatMap(Collection::stream).collect(Collectors.toList());
        dienst = Iterables.getOnlyElement(diensten);
        dienst.setEersteSelectieDatum(DatumUtil.vandaag());
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        toegangLeveringsAutorisatie = TestAutorisaties.maak(Rol.AFNEMER, dienst);
        leveringsautorisatie.getToegangLeveringsautorisatieSet().add(toegangLeveringsAutorisatie);

        when(dienstRepository.getSelectieDienstenBinnenPeriode(any(), any())).thenReturn(diensten);
        doAnswer((p) -> p.getArguments()[0]).when(overlappendeSelectieTaakFilterService).filter(any(), any());
        doAnswer((p) -> p.getArguments()[0]).when(selectieTaakAutorisatieFilterService).filter(any());
    }

    @Test
    public void berekenSelectieTakenGeenHerhaling() {
        Collection<SelectieTaakDTO>
                taken =
                selectieTaakBerekenService.berekenSelectieTaken(LocalDate.now().minus(1, ChronoUnit.DAYS), LocalDate.now().plus(1, ChronoUnit.DAYS));

        SelectieTaakDTO
                expected =
                new SelectieTaakDTO(dienst, toegangLeveringsAutorisatie, DatumUtil.vanIntegerNaarLocalDate(dienst.getEersteSelectieDatum()), null, null, 1);
        assertThat(Iterables.getOnlyElement(taken), is(new ReflectionEquals(expected)));
    }

    @Test
    public void berekenSelectieTakenElkeTweeMaandenTotEenHalfJaarVooruit() {
        dienst.setSelectieInterval(2);
        dienst.setEenheidSelectieInterval(EenheidSelectieInterval.MAAND.getId());
        dienst.setSelectiePeilmomentMaterieelResultaat(20100101);
        dienst.setSelectiePeilmomentFormeelResultaat(DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(ZonedDateTime.now()));

        Collection<SelectieTaakDTO>
                taken =
                selectieTaakBerekenService.berekenSelectieTaken(LocalDate.now().minus(1, ChronoUnit.DAYS), LocalDate.now().plus(6, ChronoUnit.MONTHS));

        assertThat(taken.size(), is(3));
        assertThat(DatumUtil.vanIntegerNaarLocalDate(dienst.getEersteSelectieDatum()).plus(2, ChronoUnit.MONTHS),
                is(Iterables.get(taken, 1).getBerekendeSelectieDatum()));
        assertThat(DatumUtil.vanIntegerNaarLocalDate(dienst.getSelectiePeilmomentMaterieelResultaat()).plus(2, ChronoUnit.MONTHS),
                is(Iterables.get(taken, 1).getPeilmomentMaterieelResultaat()));
        assertThat(dienst.getSelectiePeilmomentFormeelResultaat().toLocalDateTime().toLocalDate().plus(2, ChronoUnit.MONTHS),
                is(Iterables.get(taken, 1).getPeilmomentFormeelResultaat().toLocalDate()));
    }

    @Test
    public void berekenSelectieTakenElke15DagenTotEenJaarVooruit() {
        dienst.setSelectieInterval(15);
        dienst.setEenheidSelectieInterval(EenheidSelectieInterval.DAG.getId());

        Collection<SelectieTaakDTO>
                taken =
                selectieTaakBerekenService.berekenSelectieTaken(LocalDate.now().minus(1, ChronoUnit.DAYS), LocalDate.now().plus(1, ChronoUnit.YEARS));

        assertThat(taken.size(), is(25));
    }

    @Test
    public void grensgevalEindeMaand() {
        dienst.setSelectieInterval(1);
        dienst.setEenheidSelectieInterval(EenheidSelectieInterval.MAAND.getId());
        dienst.setEersteSelectieDatum(20170130);
        dienst.setSelectiePeilmomentMaterieelResultaat(20170129);

        Collection<SelectieTaakDTO>
                taken =
                selectieTaakBerekenService.berekenSelectieTaken(LocalDate.of(2017, 1, 1), LocalDate.of(2017, 5, 1));

        assertThat(taken.size(), is(4));
        assertThat(Iterables.get(taken, 0).getBerekendeSelectieDatum(), is(LocalDate.of(2017, 1, 30)));
        assertThat(Iterables.get(taken, 1).getBerekendeSelectieDatum(), is(LocalDate.of(2017, 2, 28)));
        assertThat(Iterables.get(taken, 2).getBerekendeSelectieDatum(), is(LocalDate.of(2017, 3, 30)));
        assertThat(Iterables.get(taken, 3).getBerekendeSelectieDatum(), is(LocalDate.of(2017, 4, 30)));
        assertThat(Iterables.get(taken, 0).getPeilmomentMaterieelResultaat(), is(LocalDate.of(2017, 1, 29)));
        assertThat(Iterables.get(taken, 1).getPeilmomentMaterieelResultaat(), is(LocalDate.of(2017, 2, 28)));
        assertThat(Iterables.get(taken, 2).getPeilmomentMaterieelResultaat(), is(LocalDate.of(2017, 3, 29)));
        assertThat(Iterables.get(taken, 3).getPeilmomentMaterieelResultaat(), is(LocalDate.of(2017, 4, 29)));
    }

    @Test
    public void geenBerekendeTakenAlsEersteSelectieDatumNietGevuld() {
        dienst.setEersteSelectieDatum(null);

        Collection<SelectieTaakDTO>
                taken =
                selectieTaakBerekenService.berekenSelectieTaken(LocalDate.now().minus(1, ChronoUnit.DAYS), LocalDate.now().plus(1, ChronoUnit.YEARS));

        assertThat(taken.size(), is(0));
    }

    @Test
    public void geenBerekendeTakenAlsSoortSelectieNietGevuld() {
        dienst.setSoortSelectie(null);

        Collection<SelectieTaakDTO>
                taken =
                selectieTaakBerekenService.berekenSelectieTaken(LocalDate.now().minus(1, ChronoUnit.DAYS), LocalDate.now().plus(1, ChronoUnit.YEARS));

        assertThat(taken.size(), is(0));
    }

    @Test
    public void meerdereDiensten() {
        Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE);
        List<Dienst>
                diensten =
                leveringsautorisatie.getDienstbundelSet().stream().map(Dienstbundel::getDienstSet).flatMap(Collection::stream).collect(Collectors.toList());
        Dienst andereDienst = Iterables.getOnlyElement(diensten);
        andereDienst.setEersteSelectieDatum(DatumUtil.vandaag());
        andereDienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = TestAutorisaties.maak(Rol.AFNEMER, andereDienst);
        leveringsautorisatie.getToegangLeveringsautorisatieSet().add(toegangLeveringsAutorisatie);

        when(dienstRepository.getSelectieDienstenBinnenPeriode(any(), any())).thenReturn(Lists.newArrayList(dienst, andereDienst));
        dienst.setSelectieInterval(2);
        dienst.setEenheidSelectieInterval(EenheidSelectieInterval.MAAND.getId());
        andereDienst.setSelectieInterval(3);
        andereDienst.setEenheidSelectieInterval(EenheidSelectieInterval.MAAND.getId());

        Collection<SelectieTaakDTO>
                taken =
                selectieTaakBerekenService.berekenSelectieTaken(LocalDate.now().minus(1, ChronoUnit.DAYS), LocalDate.now().plus(6, ChronoUnit.MONTHS));

        assertThat(taken.size(), is(5));
    }

    @Test
    public void meerdereToegangen() {
        ToegangLeveringsAutorisatie andereToegang = TestAutorisaties.maak(Rol.AFNEMER, dienst);
        andereToegang.setId(100);
        leveringsautorisatie.getToegangLeveringsautorisatieSet().add(andereToegang);

        Collection<SelectieTaakDTO>
                taken =
                selectieTaakBerekenService.berekenSelectieTaken(LocalDate.now().minus(1, ChronoUnit.DAYS), LocalDate.now().plus(6, ChronoUnit.MONTHS));

        assertThat(taken.size(), is(2));
        assertThat(Iterables.get(taken, 0).getToegangLeveringsautorisatieId(), is(1));
        assertThat(Iterables.get(taken, 1).getToegangLeveringsautorisatieId(), is(100));
    }

    @Test
    public void eersteSelectieDatumVoorBeginDatumMetHerhaling() {
        dienst.setEersteSelectieDatum(DatumUtil.datumRondVandaag(15));
        dienst.setSelectieInterval(1);
        dienst.setEenheidSelectieInterval(EenheidSelectieInterval.MAAND.getId());

        Collection<SelectieTaakDTO>
                taken =
                selectieTaakBerekenService.berekenSelectieTaken(LocalDate.now().minus(1, ChronoUnit.DAYS), LocalDate.now().plus(1, ChronoUnit.MONTHS));

        assertThat(taken.size(), is(1));
    }

    @Test
    public void eersteSelectieDatumVoorBeginDatumZonderHerhaling() {
        dienst.setEersteSelectieDatum(DatumUtil.datumRondVandaag(15));

        Collection<SelectieTaakDTO>
                taken =
                selectieTaakBerekenService.berekenSelectieTaken(LocalDate.now().minus(1, ChronoUnit.DAYS), LocalDate.now().plus(1, ChronoUnit.MONTHS));

        assertThat(taken.size(), is(0));
    }

}