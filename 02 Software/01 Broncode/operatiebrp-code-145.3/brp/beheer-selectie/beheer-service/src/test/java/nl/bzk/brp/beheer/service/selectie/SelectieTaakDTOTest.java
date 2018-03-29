/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.selectie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import org.junit.Test;

public class SelectieTaakDTOTest {
    private static Integer TAAK_ID = 1;
    private static Integer DIENST_ID = 2;
    private static Integer SELECTIE_INTERVAL = 3;
    private static Integer SOORT_SELECTIE = 1;
    private static Integer EENHEID_SELECTIE_INTERVAL = 1;
    private static String EENHEID_SELECTIE_INTERVAL_STR = "Dag";
    private static int PEILMOM_MAT = 20100101;
    private static int EERSTE_SEL_DATUM = 20110101;
    private static int DATUM_PLANNING = 20120101;
    private static ZonedDateTime ZDT_NU = DatumUtil.nuAlsZonedDateTime();
    private static Timestamp PEILMOM_FORM = DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(ZDT_NU);
    private static Integer TLA_ID = 4;
    private static Short STATUS = (short) 1;
    private static Integer VOLG_NR = 5;
    private static String STATUS_TOELICHTING = "Status toelichting";

    @Test
    public void testMaak() {
        Autorisatiebundel autorisatiebundel = TestAutorisaties.maakAutorisatiebundel(SoortDienst.SELECTIE);
        autorisatiebundel.getToegangLeveringsautorisatie().setId(TLA_ID);
        Dienst dienst = autorisatiebundel.getDienst();
        dienst.setId(DIENST_ID);
        dienst.setSelectieInterval(SELECTIE_INTERVAL);
        dienst.setEersteSelectieDatum(EERSTE_SEL_DATUM);
        dienst.setSoortSelectie(SOORT_SELECTIE);
        dienst.setEenheidSelectieInterval(EENHEID_SELECTIE_INTERVAL);
        Selectietaak selectietaak = new Selectietaak(autorisatiebundel.getDienst(), autorisatiebundel.getToegangLeveringsautorisatie(), 1);
        selectietaak.setId(TAAK_ID);
        selectietaak.setDatumPlanning(DATUM_PLANNING);
        selectietaak.setPeilmomentMaterieelResultaat(PEILMOM_MAT);
        selectietaak.setPeilmomentFormeelResultaat(PEILMOM_FORM);
        selectietaak.setStatus(STATUS);
        selectietaak.setVolgnummer(VOLG_NR);
        selectietaak.setStatusToelichting(STATUS_TOELICHTING);

        SelectieTaakDTO dto = new SelectieTaakDTO(selectietaak);
        assertEquals(TAAK_ID, dto.getId());
        assertEquals(DIENST_ID, dto.getDienstId());
        assertEquals(SELECTIE_INTERVAL, dto.getSelectieInterval());
        assertEquals(EENHEID_SELECTIE_INTERVAL_STR, dto.getEenheidSelectieInterval());
        assertEquals("BRP", dto.getStelsel());
        assertEquals("000000", dto.getAfnemerCode());
        assertEquals("dummy", dto.getAfnemerNaam());
        assertEquals(LocalDate.of(2011, 1, 1), dto.getEersteSelectieDatum());
        assertEquals("Standaard selectie", dto.getSelectieSoort());
        assertEquals(TLA_ID, dto.getToegangLeveringsautorisatieId());
        assertEquals(LocalDate.of(2010, 1, 1), dto.getPeilmomentMaterieelResultaat());
        assertEquals(DatumUtil.vanTimestampNaarZonedDateTime(PEILMOM_FORM), dto.getPeilmomentFormeelResultaat());
        assertEquals(STATUS, dto.getStatus());
        assertEquals(LocalDate.of(2012, 1, 1), dto.getDatumPlanning());
        assertEquals(VOLG_NR, dto.getVolgnummer());
        assertEquals(STATUS_TOELICHTING, dto.getStatusToelichting());
        assertEquals(dienst, dto.getDienst());
        assertEquals(autorisatiebundel.getToegangLeveringsautorisatie(), dto.getToegangLeveringsautorisatie());
        assertEquals(LocalDate.of(2011, 1, 13), dto.getBerekendeSelectieDatum());
    }

    @Test
    public void testMaak_SelectieIntervalNull() {
        Autorisatiebundel autorisatiebundel = TestAutorisaties.maakAutorisatiebundel(SoortDienst.SELECTIE);
        autorisatiebundel.getToegangLeveringsautorisatie().setId(TLA_ID);
        Dienst dienst = autorisatiebundel.getDienst();
        dienst.setId(DIENST_ID);
        dienst.setSelectieInterval(null);
        dienst.setEersteSelectieDatum(EERSTE_SEL_DATUM);
        dienst.setSoortSelectie(SOORT_SELECTIE);
        dienst.setEenheidSelectieInterval(EENHEID_SELECTIE_INTERVAL);
        Selectietaak selectietaak = new Selectietaak(autorisatiebundel.getDienst(), autorisatiebundel.getToegangLeveringsautorisatie(), 1);
        selectietaak.setId(TAAK_ID);
        selectietaak.setDatumPlanning(DATUM_PLANNING);
        selectietaak.setPeilmomentMaterieelResultaat(PEILMOM_MAT);
        selectietaak.setPeilmomentFormeelResultaat(PEILMOM_FORM);
        selectietaak.setStatus(STATUS);
        selectietaak.setVolgnummer(VOLG_NR);
        selectietaak.setStatusToelichting(STATUS_TOELICHTING);

        SelectieTaakDTO dto = new SelectieTaakDTO(selectietaak);

        assertNull(dto.getSelectieInterval());
        assertEquals(EENHEID_SELECTIE_INTERVAL_STR, dto.getEenheidSelectieInterval());
        assertEquals(LocalDate.of(2011, 1, 1), dto.getEersteSelectieDatum());
        assertEquals(LocalDate.of(2011, 1, 1), dto.getBerekendeSelectieDatum());
    }

    @Test
    public void testMaak_PeilmomentenNull() {
        Autorisatiebundel autorisatiebundel = TestAutorisaties.maakAutorisatiebundel(SoortDienst.SELECTIE);
        autorisatiebundel.getToegangLeveringsautorisatie().setId(TLA_ID);
        Dienst dienst = autorisatiebundel.getDienst();
        dienst.setId(DIENST_ID);
        dienst.setSelectieInterval(null);
        dienst.setEersteSelectieDatum(EERSTE_SEL_DATUM);
        dienst.setSoortSelectie(SOORT_SELECTIE);
        dienst.setEenheidSelectieInterval(EENHEID_SELECTIE_INTERVAL);
        Selectietaak selectietaak = new Selectietaak(autorisatiebundel.getDienst(), autorisatiebundel.getToegangLeveringsautorisatie(), 1);
        selectietaak.setId(TAAK_ID);
        selectietaak.setDatumPlanning(DATUM_PLANNING);
        selectietaak.setPeilmomentMaterieelResultaat(null);
        selectietaak.setPeilmomentFormeelResultaat(null);
        selectietaak.setStatus(STATUS);
        selectietaak.setVolgnummer(VOLG_NR);
        selectietaak.setStatusToelichting(STATUS_TOELICHTING);

        SelectieTaakDTO dto = new SelectieTaakDTO(selectietaak);

        assertNull(dto.getPeilmomentMaterieelResultaat());
        assertNull(dto.getPeilmomentFormeelResultaat());
    }
}