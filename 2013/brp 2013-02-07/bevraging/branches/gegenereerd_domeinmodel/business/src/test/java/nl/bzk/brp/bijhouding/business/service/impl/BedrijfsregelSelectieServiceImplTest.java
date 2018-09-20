/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service.impl;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.bevraging.domein.RedenOpschorting;
import nl.bzk.brp.bevraging.domein.Verantwoordelijke;
import nl.bzk.brp.bevraging.domein.ber.SoortBericht;
import nl.bzk.brp.bijhouding.business.service.BedrijfsregelSelectieService;
import nl.bzk.brp.bijhouding.domein.brm.Regel;
import nl.bzk.brp.bijhouding.domein.brm.RegelGedrag;
import nl.bzk.brp.bijhouding.domein.brm.RegelImplementatie;
import nl.bzk.brp.bijhouding.domein.repository.RegelGedragRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class BedrijfsregelSelectieServiceImplTest {

    private static final Regel              REGEL_1           = BedrijfsregelTestUtils.creeerRegel("BRPE0001");
    private static final RegelImplementatie IMPLEMENTATIE_1   = creeerRegelImplementatie(REGEL_1,
                                                                      SoortBericht.OPVRAGEN_PERSOON_VRAAG);
    private static final Regel              REGEL_2           = BedrijfsregelTestUtils.creeerRegel("BRPE0002");
    private static final RegelImplementatie IMPLEMENTATIE_2   = creeerRegelImplementatie(REGEL_2,
                                                                      SoortBericht.OPVRAGEN_PERSOON_VRAAG);
    private static final SoortBericht       SOORT_BERICHT     = SoortBericht.OPVRAGEN_PERSOON_VRAAG;
    private static final Verantwoordelijke  VERANTWOORDELIJKE = Verantwoordelijke.COLLEGE;
    private static final Boolean            IS_OPSCHORTING    = true;
    private static final RedenOpschorting   REDEN_OPSCHORTING = RedenOpschorting.FOUT;

    @Mock
    private RegelGedragRepository           repository;

    private BedrijfsregelSelectieService    service;

    @Test
    public void testRegelGedragLijstIsNull() {
        mockZoekMethodeVanRepository(VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING, null);

        // test
        List<RegelGedrag> resultaat =
            service.zoekMeestSpecifiekBedrijfsregelGedrag(SOORT_BERICHT, VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING);

        Assert.assertSame(null, resultaat);
    }

    @Test
    public void testRegelGedragLijstIsLeeg() {
        RegelGedrag[] gedragingen = {};
        mockZoekMethodeVanRepository(VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING, Arrays.asList(gedragingen));

        // test
        List<RegelGedrag> resultaat =
            service.zoekMeestSpecifiekBedrijfsregelGedrag(SOORT_BERICHT, VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING);

        Assert.assertSame(null, resultaat);
    }

    @Test
    public void testRegelGedragLijstHeeft1Gedrag() {
        RegelGedrag[] gedragingen = {
            creeerGedrag(1L, IMPLEMENTATIE_1, VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING),
        };

        mockZoekMethodeVanRepository(VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING, Arrays.asList(gedragingen));

        // test
        List<RegelGedrag> resultaat =
            service.zoekMeestSpecifiekBedrijfsregelGedrag(SOORT_BERICHT, VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING);

        Set<Long> gedragIds = extractGedragIds(resultaat);
        Assert.assertTrue(gedragIds.contains(1L));
    }

    @Test
    public void test1RegelMeerGedrag() {
        RegelGedrag[] gedragingen = {
            creeerGedrag(1L, IMPLEMENTATIE_1, null, IS_OPSCHORTING, REDEN_OPSCHORTING),
            creeerGedrag(2L, IMPLEMENTATIE_1, VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING),
            creeerGedrag(3L, IMPLEMENTATIE_1, VERANTWOORDELIJKE, null, REDEN_OPSCHORTING),
            creeerGedrag(4L, IMPLEMENTATIE_1, VERANTWOORDELIJKE, IS_OPSCHORTING, null),
            creeerGedrag(5L, IMPLEMENTATIE_1, null, null, REDEN_OPSCHORTING),
        };

        mockZoekMethodeVanRepository(VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING, Arrays.asList(gedragingen));

        // test
        List<RegelGedrag> resultaat =
            service.zoekMeestSpecifiekBedrijfsregelGedrag(SOORT_BERICHT, VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING);

        Set<Long> gedragIds = extractGedragIds(resultaat);
        Assert.assertFalse(gedragIds.contains(1L));
        Assert.assertTrue(gedragIds.contains(2L));
        Assert.assertFalse(gedragIds.contains(3L));
        Assert.assertFalse(gedragIds.contains(4L));
        Assert.assertFalse(gedragIds.contains(5L));
    }

    @Test
    public void testMeerdereRegels() {
        RegelGedrag[] gedragingen = {
            creeerGedrag(1L, IMPLEMENTATIE_1, VERANTWOORDELIJKE, null, REDEN_OPSCHORTING),
            creeerGedrag(2L, IMPLEMENTATIE_2, VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING),
        };

        mockZoekMethodeVanRepository(VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING, Arrays.asList(gedragingen));

        // test
        List<RegelGedrag> resultaat =
            service.zoekMeestSpecifiekBedrijfsregelGedrag(SOORT_BERICHT, VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING);

        Set<Long> gedragIds = extractGedragIds(resultaat);
        Assert.assertTrue(gedragIds.contains(1L));
        Assert.assertTrue(gedragIds.contains(2L));
    }

    @Test
    public void testMeerderGelijkGedrag() {
        RegelGedrag[] gedragingen = {
            creeerGedrag(1L, IMPLEMENTATIE_1, VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING),
            creeerGedrag(2L, IMPLEMENTATIE_2, null, IS_OPSCHORTING, null),
            creeerGedrag(3L, IMPLEMENTATIE_2, null, IS_OPSCHORTING, null),
            creeerGedrag(4L, IMPLEMENTATIE_2, null, IS_OPSCHORTING, REDEN_OPSCHORTING),
            creeerGedrag(5L, IMPLEMENTATIE_2, null, IS_OPSCHORTING, REDEN_OPSCHORTING),
        };

        mockZoekMethodeVanRepository(null, IS_OPSCHORTING, REDEN_OPSCHORTING, Arrays.asList(gedragingen));

        // test
        List<RegelGedrag> resultaat =
                service.zoekMeestSpecifiekBedrijfsregelGedrag(SOORT_BERICHT, null, IS_OPSCHORTING, REDEN_OPSCHORTING);

        Set<Long> gedragIds = extractGedragIds(resultaat);
        Assert.assertTrue(gedragIds.contains(1L));
        Assert.assertFalse(gedragIds.contains(2L));
        Assert.assertFalse(gedragIds.contains(3L));
        Assert.assertTrue(gedragIds.contains(4L));
        Assert.assertTrue(gedragIds.contains(5L));
    }

    @Test
    public void testZoekAlgemeen() {
        RegelGedrag[] gedragingen = {
            creeerGedrag(1L, IMPLEMENTATIE_1, VERANTWOORDELIJKE, null, REDEN_OPSCHORTING),
            creeerGedrag(2L, IMPLEMENTATIE_1, VERANTWOORDELIJKE, null, null),
        };

        mockZoekMethodeVanRepository(VERANTWOORDELIJKE, null, REDEN_OPSCHORTING, Arrays.asList(gedragingen));

        // test
        List<RegelGedrag> resultaat =
            service.zoekMeestSpecifiekBedrijfsregelGedrag(SOORT_BERICHT, VERANTWOORDELIJKE, null, REDEN_OPSCHORTING);

        Set<Long> gedragIds = extractGedragIds(resultaat);
        Assert.assertTrue(gedragIds.contains(1L));
        Assert.assertFalse(gedragIds.contains(2L));
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        service = new BedrijfsregelSelectieServiceImpl();
        ReflectionTestUtils.setField(service, "repository", repository);
    }

    private RegelGedrag creeerGedrag(final Long id, final RegelImplementatie implementatie, final Verantwoordelijke verantwoordelijke,
            final Boolean opschorting, final RedenOpschorting redenOpschorting)
    {
        RegelGedrag resultaat;
        try {
            Constructor<RegelGedrag> constructor = RegelGedrag.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            resultaat = constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ReflectionTestUtils.setField(resultaat, "id", id);
        ReflectionTestUtils.setField(resultaat, "regelImplementatie", implementatie);
        ReflectionTestUtils.setField(resultaat, "verantwoordelijke", verantwoordelijke);
        ReflectionTestUtils.setField(resultaat, "opschorting", opschorting);
        ReflectionTestUtils.setField(resultaat, "redenOpschorting", redenOpschorting);
        return resultaat;
    }

    private static RegelImplementatie creeerRegelImplementatie(final Regel regel, final SoortBericht soortBericht) {
        RegelImplementatie resultaat;
        try {
            Constructor<RegelImplementatie> constructor = RegelImplementatie.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            resultaat = constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ReflectionTestUtils.setField(resultaat, "regel", regel);
        ReflectionTestUtils.setField(resultaat, "soortBericht", soortBericht);
        return resultaat;
    }

    private void mockZoekMethodeVanRepository(final Verantwoordelijke verantwoordelijke, final Boolean isOpschorting,
            final RedenOpschorting redenOpschorting, final List<RegelGedrag> returnValue)
    {
        Mockito.when(repository.zoekActiefBedrijfsregelGedrag(SoortBericht.OPVRAGEN_PERSOON_VRAAG, verantwoordelijke, isOpschorting, redenOpschorting))
                .thenReturn(returnValue);
    }

    private Set<Long> extractGedragIds(final List<RegelGedrag> gedragingen) {
        Set<Long> gedragIds = new HashSet<Long>();
        for (RegelGedrag gedrag : gedragingen) {
            gedragIds.add(gedrag.getId());
        }
        return gedragIds;
    }

}
