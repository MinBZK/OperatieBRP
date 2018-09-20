/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.populatie;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.levering.business.filters.LeverenPersoonFilter;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class FilterNietTeLeverenPersonenServiceTest {

    private final FilterNietTeLeverenPersonenServiceImpl nietTeLeverenPersonenService =
            new FilterNietTeLeverenPersonenServiceImpl();

    @Mock
    private LeverenPersoonFilter populatieBepalingFilter;
    @Mock
    private LeverenPersoonFilter afnemerIndicatieFilter;

    @Mock
    private Leveringinformatie leveringAutorisatie;
    @Mock
    private AdministratieveHandelingModel administratieveHandelingModel;
    @Mock
    private PersoonHisVolledig            persoon;

    @Before
    public final void setup() {
        MockitoAnnotations.initMocks(this);

        nietTeLeverenPersonenService.setFilters(Arrays.asList(populatieBepalingFilter, afnemerIndicatieFilter));
        nietTeLeverenPersonenService.init();
    }

    @Test
    public final void alleFiltersFalseDanResultaatLegeMap() throws ExpressieExceptie {
        // arrange
        final Map<Integer, Populatie> populatieMap = new HashMap<>();
        populatieMap.put(1, Populatie.BETREEDT);
        populatieMap.put(2, Populatie.BINNEN);

        final PersoonHisVolledig persoon1 = mock(PersoonHisVolledig.class);
        when(persoon1.getID()).thenReturn(1);
        final PersoonHisVolledig persoon2 = mock(PersoonHisVolledig.class);
        when(persoon2.getID()).thenReturn(2);

        final List<PersoonHisVolledig> personen = Arrays.asList(persoon1, persoon2);

        // act
        nietTeLeverenPersonenService.filterNietTeLeverenPersonen(personen, populatieMap, leveringAutorisatie, administratieveHandelingModel);

        // assert
        assertNotNull(persoon);
        assertThat(populatieMap.size(), is(0));
    }

    @Test
    public final void alleFiltersTrueDanResultaatOrigineleMap() throws ExpressieExceptie {
        // arrange
        final Map<Integer, Populatie> populatieMap = new HashMap<>();
        populatieMap.put(1, Populatie.BETREEDT);
        populatieMap.put(2, Populatie.BINNEN);

        final PersoonHisVolledig persoon1 = mock(PersoonHisVolledig.class);
        when(persoon1.getID()).thenReturn(1);
        final PersoonHisVolledig persoon2 = mock(PersoonHisVolledig.class);
        when(persoon2.getID()).thenReturn(2);

        final List<PersoonHisVolledig> personen = Arrays.asList(persoon1, persoon2);

        when(populatieBepalingFilter.magLeverenDoorgaan(any(PersoonHisVolledig.class), any(Populatie.class),
                eq(leveringAutorisatie), eq(administratieveHandelingModel)))
                .thenReturn(true);
        when(afnemerIndicatieFilter.magLeverenDoorgaan(any(PersoonHisVolledig.class), any(Populatie.class),
                eq(leveringAutorisatie), eq(administratieveHandelingModel)))
                .thenReturn(true);

        // act
        nietTeLeverenPersonenService.filterNietTeLeverenPersonen(personen, populatieMap, leveringAutorisatie,
                administratieveHandelingModel);

        // assert
        assertThat(populatieMap.size(), is(2));
    }

    @Test
    public void filtersTrueVoorPersoon1DanResultaatMapMet1Persoon() throws ExpressieExceptie {
        // arrange
        final Map<Integer, Populatie> populatieMap = new HashMap<>();
        populatieMap.put(1, Populatie.BETREEDT);
        populatieMap.put(2, Populatie.BINNEN);

        final PersoonHisVolledig persoon1 = mock(PersoonHisVolledig.class);
        when(persoon1.getID()).thenReturn(1);
        final PersoonHisVolledig persoon2 = mock(PersoonHisVolledig.class);
        when(persoon2.getID()).thenReturn(2);

        final List<PersoonHisVolledig> personen = Arrays.asList(persoon1, persoon2);

        when(populatieBepalingFilter.magLeverenDoorgaan(any(PersoonHisVolledig.class), any(Populatie.class),
                eq(leveringAutorisatie), eq(administratieveHandelingModel)))
                .thenReturn(true).thenReturn(false);
        when(afnemerIndicatieFilter.magLeverenDoorgaan(any(PersoonHisVolledig.class), any(Populatie.class),
                eq(leveringAutorisatie), eq(administratieveHandelingModel)))
                .thenReturn(true).thenReturn(false);

        // act
        nietTeLeverenPersonenService.filterNietTeLeverenPersonen(personen, populatieMap, leveringAutorisatie,
                administratieveHandelingModel);

        // assert
        assertThat(populatieMap.size(), is(1));
    }

    @Test
    public final void zonderFiltersBlijftOrigineelOver() throws ExpressieExceptie {
        // arrange
        final Map<Integer, Populatie> populatieMap = new HashMap<>();
        populatieMap.put(1, Populatie.BETREEDT);
        populatieMap.put(2, Populatie.BINNEN);

        final PersoonHisVolledig persoon1 = mock(PersoonHisVolledig.class);
        when(persoon1.getID()).thenReturn(1);
        final PersoonHisVolledig persoon2 = mock(PersoonHisVolledig.class);
        when(persoon2.getID()).thenReturn(2);

        final List<PersoonHisVolledig> personen = Arrays.asList(persoon1, persoon2);

        nietTeLeverenPersonenService.setFilters(Collections.<LeverenPersoonFilter>emptyList());

        // act
        nietTeLeverenPersonenService.filterNietTeLeverenPersonen(personen, populatieMap, leveringAutorisatie, administratieveHandelingModel);

        // assert
        assertThat(populatieMap.size(), is(2));
    }

}
