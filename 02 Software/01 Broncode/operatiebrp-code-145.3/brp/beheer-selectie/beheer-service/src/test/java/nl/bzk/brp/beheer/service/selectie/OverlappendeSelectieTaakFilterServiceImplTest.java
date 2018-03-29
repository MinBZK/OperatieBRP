/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.selectie;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test voor {@link OverlappendeSelectieTaakFilterServiceImpl}.
 */
public class OverlappendeSelectieTaakFilterServiceImplTest {

    private OverlappendeSelectieTaakFilterServiceImpl service = new OverlappendeSelectieTaakFilterServiceImpl();
    private ToegangLeveringsAutorisatie toegangLeveringsAutorisatie;
    private Dienst dienst;
    private List<Dienst> diensten;

    @Before
    public void setUp() throws Exception {
        Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE);
        diensten = leveringsautorisatie.getDienstbundelSet().stream().map(Dienstbundel::getDienstSet).flatMap(Collection::stream).collect(Collectors.toList());
        dienst = Iterables.getOnlyElement(diensten);
        dienst.setEersteSelectieDatum(DatumUtil.vandaag());
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        toegangLeveringsAutorisatie = TestAutorisaties.maak(Rol.AFNEMER, dienst);
        leveringsautorisatie.getToegangLeveringsautorisatieSet().add(toegangLeveringsAutorisatie);
    }

    @Test
    public void verwerkZonderGepersisteerdeTaken() throws Exception {
        List<SelectieTaakDTO> berekendeTaakDtos = Lists.newArrayList();
        berekendeTaakDtos.add(new SelectieTaakDTO(dienst, toegangLeveringsAutorisatie, LocalDate.now(), LocalDate.now(), ZonedDateTime.now(), 0));

        Collection<SelectieTaakDTO> verwerkteTaken = service.filter(berekendeTaakDtos, Collections.emptyList());

        assertThat(verwerkteTaken, is(berekendeTaakDtos));
    }

    @Test
    public void verwerkMeerdereBerekendeEenGepersisteerdeTaken() throws Exception {
        List<SelectieTaakDTO> berekendeTaakDtos = Lists.newArrayList();
        // Taak binnen periode, maar dto2 heeft een volgnummer match met de gepersisteerde taak dus die wordt verwijderd.
        SelectieTaakDTO selectieTaakDto1 = new SelectieTaakDTO(dienst, toegangLeveringsAutorisatie, LocalDate.now(), LocalDate.now(), ZonedDateTime.now(), 0);
        SelectieTaakDTO
                selectieTaakDto2 =
                new SelectieTaakDTO(dienst, toegangLeveringsAutorisatie, LocalDate.now().plus(1, ChronoUnit.MONTHS), LocalDate.now(), ZonedDateTime.now(), 1);
        // Taak buiten periode, geen overlap.
        SelectieTaakDTO
                selectieTaakDto3 =
                new SelectieTaakDTO(dienst, toegangLeveringsAutorisatie, LocalDate.now().plus(3, ChronoUnit.MONTHS), LocalDate.now(), ZonedDateTime.now(), 0);
        berekendeTaakDtos.add(selectieTaakDto1);
        berekendeTaakDtos.add(selectieTaakDto2);
        berekendeTaakDtos.add(selectieTaakDto3);
        List<SelectieTaakDTO> gepersisteerdeTaakDtos = Lists.newArrayList();
        Selectietaak selectietaak = new Selectietaak(dienst, toegangLeveringsAutorisatie, 1);
        selectietaak.setId(1);
        selectietaak.setDatumPlanning(DatumUtil.vanDatumNaarInteger(LocalDate.now().plus(25, ChronoUnit.DAYS)));
        selectietaak.setStatus((short) SelectietaakStatus.PROTOCOLLERING_UITGEVOERD.getId());
        SelectieTaakDTO selectieTaakDto4 = new SelectieTaakDTO(selectietaak);
        gepersisteerdeTaakDtos.add(selectieTaakDto4);
        dienst.setEenheidSelectieInterval(EenheidSelectieInterval.MAAND.getId());
        dienst.setSelectieInterval(1);

        Leveringsautorisatie andereLeveringsautorisatie = TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE);
        andereLeveringsautorisatie.setId(100);
        List<Dienst>
                andereDiensten =
                andereLeveringsautorisatie.getDienstbundelSet().stream().map(Dienstbundel::getDienstSet).flatMap(Collection::stream)
                        .collect(Collectors.toList());
        Dienst andereDienst = andereDiensten.get(0);
        andereDienst.setId(100);
        andereDienst.setEersteSelectieDatum(DatumUtil.vandaag());
        andereDienst.setEenheidSelectieInterval(1);
        andereDienst.setEenheidSelectieInterval(EenheidSelectieInterval.MAAND.getId());
        andereDienst.setSoortSelectie(SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE.getId());
        diensten.addAll(andereDiensten);
        ToegangLeveringsAutorisatie andereToegang = TestAutorisaties.maak(Rol.AFNEMER, andereDienst);
        andereToegang.setId(100);
        // Taak met andere toegang en dienst. Geen overlap, want dienst matcht niet.
        SelectieTaakDTO
                selectieTaakDto5 =
                new SelectieTaakDTO(andereDiensten.get(0), andereToegang, LocalDate.now().plus(2, ChronoUnit.MONTHS),
                        LocalDate.now(), ZonedDateTime.now(), 0);
        berekendeTaakDtos.add(selectieTaakDto5);
        // Taak met zelfde dienst, maar andere toegang. Geen overlap, want toegang matcht niet.
        SelectieTaakDTO
                selectieTaakDto6 =
                new SelectieTaakDTO(dienst, andereToegang, LocalDate.now(),
                        LocalDate.now().plus(11, ChronoUnit.DAYS), ZonedDateTime.now(), 0);
        berekendeTaakDtos.add(selectieTaakDto6);

        Collection<SelectieTaakDTO> verwerkteTaken = service.filter(berekendeTaakDtos, gepersisteerdeTaakDtos);

        assertThat(verwerkteTaken, containsInAnyOrder(selectieTaakDto1, selectieTaakDto6, selectieTaakDto4, selectieTaakDto5, selectieTaakDto3));
    }

}