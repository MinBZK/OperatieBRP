/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.selectie;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.google.common.collect.Iterables;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EenheidSelectieInterval;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.beheer.service.dal.ReferentieRepository;
import nl.bzk.brp.beheer.service.dal.SelectieTaakRepository;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link SelectieTaakServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieTaakServiceImplTest {

    @InjectMocks
    private SelectieTaakServiceImpl service;
    @Mock
    private SelectieTaakBerekenService selectieTaakBerekenService;
    @Mock
    private SelectieTaakRepository selectieTaakRepository;
    @Mock
    private ReferentieRepository referentieRepository;
    @Captor
    private ArgumentCaptor<Selectietaak> selectietaakArgumentCaptor;
    private SelectieTaakDTO selectieTaakDTO;
    private Selectietaak selectietaak;

    @Before
    public void before() {
        Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE);
        List<Dienst>
                diensten =
                leveringsautorisatie.getDienstbundelSet().stream().map(Dienstbundel::getDienstSet).flatMap(Collection::stream).collect(Collectors.toList());
        Dienst dienst = Iterables.getOnlyElement(diensten);
        dienst.setEersteSelectieDatum(20100101);
        dienst.setSelectieInterval(1);
        dienst.setEenheidSelectieInterval(EenheidSelectieInterval.MAAND.getId());
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = TestAutorisaties.maak(Rol.AFNEMER, dienst);
        selectietaak = new Selectietaak(dienst, toegangLeveringsAutorisatie, 0);
        selectietaak.setDatumPlanning(20170101);
        selectieTaakDTO = new SelectieTaakDTO(selectietaak);
        selectieTaakDTO.setDienstId(1);
        selectieTaakDTO.setVolgnummer(1);
        selectieTaakDTO.setStatus((short) 1);
        selectieTaakDTO.setStatusToelichting("Toelichting");
        selectieTaakDTO.setPeilmomentMaterieelResultaat(LocalDate.now());
        selectieTaakDTO.setPeilmomentFormeelResultaat(ZonedDateTime.now());
        when(referentieRepository.getReferentie(Dienst.class, selectieTaakDTO.getDienstId())).thenReturn(dienst);
        when(referentieRepository.getReferentie(ToegangLeveringsAutorisatie.class, selectieTaakDTO.getToegangLeveringsautorisatieId()))
                .thenReturn(toegangLeveringsAutorisatie);
        doAnswer(a -> {
            Selectietaak argumentAt = a.getArgumentAt(0, Selectietaak.class);
            argumentAt.setId(1);
            return argumentAt;
        }).when(selectieTaakRepository).slaOp(any());
    }

    @Test
    public void getSelectieTaken() {
        SelectieTaakDTO taak = new SelectieTaakDTO();
        taak.setId(1);
        taak.setBerekendeSelectieDatum(DatumUtil.vanIntegerNaarLocalDate(20170101));
        taak.setAfnemerCode("000001");
        when(selectieTaakBerekenService.berekenSelectieTaken(any(), any())).thenReturn(asList(selectieTaakDTO, taak));
        SelectiePeriodeDTO selectiePeriode = new SelectiePeriodeDTO(LocalDate.now().minus(1, ChronoUnit.MONTHS), LocalDate.now().plus(3, ChronoUnit.MONTHS));
        Collection<SelectieTaakDTO> selectieTaken = service.getSelectieTaken(selectiePeriode);

        assertThat(selectieTaken.size(), is(2));
        verify(selectieTaakBerekenService).berekenSelectieTaken(selectiePeriode.getBeginDatum(), selectiePeriode.getEindDatum());
        verify(selectieTaakRepository).slaOp(any());
    }

    @Test
    public void getSelectieTaken_TaakIdNull() {
        selectieTaakDTO.setId(null);
        when(selectieTaakBerekenService.berekenSelectieTaken(any(), any())).thenReturn(Collections.singleton(selectieTaakDTO));
        SelectiePeriodeDTO selectiePeriode = new SelectiePeriodeDTO(LocalDate.now().minus(1, ChronoUnit.MONTHS), LocalDate.now().plus(3, ChronoUnit.MONTHS));
        Collection<SelectieTaakDTO> selectieTaken = service.getSelectieTaken(selectiePeriode);

        assertThat(selectieTaken.size(), is(1));
        verify(selectieTaakBerekenService).berekenSelectieTaken(selectiePeriode.getBeginDatum(), selectiePeriode.getEindDatum());
        verify(selectieTaakRepository).slaOp(any());
    }

    @Test
    public void slaNieuweSelectieTaakOp() throws Exception {
        SelectieTaakDTO opgeslagenTaakDto = service.slaSelectieTaakOp(selectieTaakDTO);

        assertThat(opgeslagenTaakDto.getId(), is(1));
        verify(selectieTaakRepository).slaOp(selectietaakArgumentCaptor.capture());
        Selectietaak opgeslagenTaak = selectietaakArgumentCaptor.getValue();
        assertThat(opgeslagenTaak.getDatumPlanning(), is(DatumUtil.vanDatumNaarInteger(selectieTaakDTO.getDatumPlanning())));
        assertThat(opgeslagenTaak.isActueelEnGeldig(), is(true));
        assertThat(opgeslagenTaak.isActueelEnGeldigStatus(), is(true));
        assertThat(opgeslagenTaak.getStatusToelichting(), is(selectieTaakDTO.getStatusToelichting()));
        assertThat(opgeslagenTaak.getStatus(), is(selectieTaakDTO.getStatus()));
        assertThat(opgeslagenTaak.getPeilmomentMaterieelResultaat(), is(DatumUtil.vanDatumNaarInteger(selectieTaakDTO.getPeilmomentMaterieelResultaat())));
        assertThat(opgeslagenTaak.getPeilmomentFormeelResultaat(),
                is(DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(selectieTaakDTO.getPeilmomentFormeelResultaat())));
    }

    @Test
    public void updateBestaandeSelectieTaak() {
        selectietaak.setId(1);
        selectieTaakDTO.setId(1);
        when(selectieTaakRepository.vindSelectietaak(any())).thenReturn(selectietaak);

        SelectieTaakDTO opgeslagenTaakDto = service.slaSelectieTaakOp(selectieTaakDTO);

        assertThat(opgeslagenTaakDto.getId(), is(1));
        verify(selectieTaakRepository).vindSelectietaak(1);
        verifyNoMoreInteractions(selectieTaakRepository);
        assertThat(selectietaak.getDatumPlanning(), is(DatumUtil.vanDatumNaarInteger(selectieTaakDTO.getDatumPlanning())));
        assertThat(selectietaak.isActueelEnGeldigStatus(), is(true));
        assertThat(selectietaak.getStatusToelichting(), is(selectieTaakDTO.getStatusToelichting()));
        assertThat(selectietaak.getStatus(), is(selectieTaakDTO.getStatus()));
        assertThat(selectietaak.getPeilmomentMaterieelResultaat(), is(DatumUtil.vanDatumNaarInteger(selectieTaakDTO.getPeilmomentMaterieelResultaat())));
        assertThat(selectietaak.getPeilmomentFormeelResultaat(),
                is(DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(selectieTaakDTO.getPeilmomentFormeelResultaat())));
    }

}