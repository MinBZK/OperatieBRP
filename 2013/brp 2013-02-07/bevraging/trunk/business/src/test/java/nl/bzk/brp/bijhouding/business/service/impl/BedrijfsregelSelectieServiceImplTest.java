/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.bijhouding.business.service.BedrijfsregelSelectieService;
import nl.bzk.brp.bijhouding.domein.repository.RegelGedragRepository;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.ber.SoortBericht;
import nl.bzk.brp.domein.brm.Regel;
import nl.bzk.brp.domein.brm.Regelimplementatie;
import nl.bzk.brp.domein.brm.Regelimplementatiesituatie;
import nl.bzk.brp.domein.kern.RedenOpschorting;
import nl.bzk.brp.domein.kern.Verantwoordelijke;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class BedrijfsregelSelectieServiceImplTest {

    private static final DomeinObjectFactory FACTORY           = new PersistentDomeinObjectFactory();

    private static final Regel               REGEL_1           = BedrijfsregelTestUtils.creeerRegel("BRPE0001");
    private static final Regelimplementatie  IMPLEMENTATIE_1   = creeerRegelImplementatie(REGEL_1,
                                                                       SoortBericht.OPVRAGENPERSOONVRAAG);
    private static final Regel               REGEL_2           = BedrijfsregelTestUtils.creeerRegel("BRPE0002");
    private static final Regelimplementatie  IMPLEMENTATIE_2   = creeerRegelImplementatie(REGEL_2,
                                                                       SoortBericht.OPVRAGENPERSOONVRAAG);
    private static final SoortBericht        SOORT_BERICHT     = SoortBericht.OPVRAGENPERSOONVRAAG;
    private static final Verantwoordelijke   VERANTWOORDELIJKE =
                                                                   Verantwoordelijke.COLLEGE_VAN_BURGEMEESTER_EN_WETHOUDERS;
    private static final Boolean             IS_OPSCHORTING    = true;
    private static final RedenOpschorting    REDEN_OPSCHORTING = RedenOpschorting.FOUT;

    @Mock
    private RegelGedragRepository            repository;

    private BedrijfsregelSelectieService     service;

    @Test
    public void testRegelGedragLijstIsNull() {
        mockZoekMethodeVanRepository(VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING, null);

        // test
        List<Regelimplementatiesituatie> resultaat =
            service.zoekMeestSpecifiekBedrijfsregelGedrag(SOORT_BERICHT, VERANTWOORDELIJKE, IS_OPSCHORTING,
                    REDEN_OPSCHORTING);

        Assert.assertSame(null, resultaat);
    }

    @Test
    public void testRegelGedragLijstIsLeeg() {
        Regelimplementatiesituatie[] gedragingen = {};
        mockZoekMethodeVanRepository(VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING, Arrays.asList(gedragingen));

        // test
        List<Regelimplementatiesituatie> resultaat =
            service.zoekMeestSpecifiekBedrijfsregelGedrag(SOORT_BERICHT, VERANTWOORDELIJKE, IS_OPSCHORTING,
                    REDEN_OPSCHORTING);

        Assert.assertSame(null, resultaat);
    }

    @Test
    public void testRegelGedragLijstHeeft1Gedrag() {
        Regelimplementatiesituatie[] gedragingen = {
                creeerGedrag(1, IMPLEMENTATIE_1, VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING), };

        mockZoekMethodeVanRepository(VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING, Arrays.asList(gedragingen));

        // test
        List<Regelimplementatiesituatie> resultaat =
            service.zoekMeestSpecifiekBedrijfsregelGedrag(SOORT_BERICHT, VERANTWOORDELIJKE, IS_OPSCHORTING,
                    REDEN_OPSCHORTING);

        Set<Integer> gedragIds = extractGedragIds(resultaat);
        Assert.assertTrue(gedragIds.contains(1));
    }

    @Test
    public void test1RegelMeerGedrag() {
        Regelimplementatiesituatie[] gedragingen = {
                creeerGedrag(1, IMPLEMENTATIE_1, null, IS_OPSCHORTING, REDEN_OPSCHORTING),
                creeerGedrag(2, IMPLEMENTATIE_1, VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING),
                creeerGedrag(3, IMPLEMENTATIE_1, VERANTWOORDELIJKE, null, REDEN_OPSCHORTING),
                creeerGedrag(4, IMPLEMENTATIE_1, VERANTWOORDELIJKE, IS_OPSCHORTING, null),
                creeerGedrag(5, IMPLEMENTATIE_1, null, null, REDEN_OPSCHORTING), };

        mockZoekMethodeVanRepository(VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING, Arrays.asList(gedragingen));

        // test
        List<Regelimplementatiesituatie> resultaat =
            service.zoekMeestSpecifiekBedrijfsregelGedrag(SOORT_BERICHT, VERANTWOORDELIJKE, IS_OPSCHORTING,
                    REDEN_OPSCHORTING);

        Set<Integer> gedragIds = extractGedragIds(resultaat);
        Assert.assertFalse(gedragIds.contains(1));
        Assert.assertTrue(gedragIds.contains(2));
        Assert.assertFalse(gedragIds.contains(3));
        Assert.assertFalse(gedragIds.contains(4));
        Assert.assertFalse(gedragIds.contains(5));
    }

    @Test
    public void testMeerdereRegels() {
        Regelimplementatiesituatie[] gedragingen = {
                creeerGedrag(1, IMPLEMENTATIE_1, VERANTWOORDELIJKE, null, REDEN_OPSCHORTING),
                creeerGedrag(2, IMPLEMENTATIE_2, VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING), };

        mockZoekMethodeVanRepository(VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING, Arrays.asList(gedragingen));

        // test
        List<Regelimplementatiesituatie> resultaat =
            service.zoekMeestSpecifiekBedrijfsregelGedrag(SOORT_BERICHT, VERANTWOORDELIJKE, IS_OPSCHORTING,
                    REDEN_OPSCHORTING);

        Set<Integer> gedragIds = extractGedragIds(resultaat);
        Assert.assertTrue(gedragIds.contains(1));
        Assert.assertTrue(gedragIds.contains(2));
    }

    @Test
    public void testMeerderGelijkGedrag() {
        Regelimplementatiesituatie[] gedragingen = {
                creeerGedrag(1, IMPLEMENTATIE_1, VERANTWOORDELIJKE, IS_OPSCHORTING, REDEN_OPSCHORTING),
                creeerGedrag(2, IMPLEMENTATIE_2, null, IS_OPSCHORTING, null),
                creeerGedrag(3, IMPLEMENTATIE_2, null, IS_OPSCHORTING, null),
                creeerGedrag(4, IMPLEMENTATIE_2, null, IS_OPSCHORTING, REDEN_OPSCHORTING),
                creeerGedrag(5, IMPLEMENTATIE_2, null, IS_OPSCHORTING, REDEN_OPSCHORTING), };

        mockZoekMethodeVanRepository(null, IS_OPSCHORTING, REDEN_OPSCHORTING, Arrays.asList(gedragingen));

        // test
        List<Regelimplementatiesituatie> resultaat =
            service.zoekMeestSpecifiekBedrijfsregelGedrag(SOORT_BERICHT, null, IS_OPSCHORTING, REDEN_OPSCHORTING);

        Set<Integer> gedragIds = extractGedragIds(resultaat);
        Assert.assertTrue(gedragIds.contains(1));
        Assert.assertFalse(gedragIds.contains(2));
        Assert.assertFalse(gedragIds.contains(3));
        Assert.assertTrue(gedragIds.contains(4));
        Assert.assertTrue(gedragIds.contains(5));
    }

    @Test
    public void testZoekAlgemeen() {
        Regelimplementatiesituatie[] gedragingen = {
                creeerGedrag(1, IMPLEMENTATIE_1, VERANTWOORDELIJKE, null, REDEN_OPSCHORTING),
                creeerGedrag(2, IMPLEMENTATIE_1, VERANTWOORDELIJKE, null, null), };

        mockZoekMethodeVanRepository(VERANTWOORDELIJKE, null, REDEN_OPSCHORTING, Arrays.asList(gedragingen));

        // test
        List<Regelimplementatiesituatie> resultaat =
            service.zoekMeestSpecifiekBedrijfsregelGedrag(SOORT_BERICHT, VERANTWOORDELIJKE, null, REDEN_OPSCHORTING);

        Set<Integer> gedragIds = extractGedragIds(resultaat);
        Assert.assertTrue(gedragIds.contains(1));
        Assert.assertFalse(gedragIds.contains(2));
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        service = new BedrijfsregelSelectieServiceImpl();
        ReflectionTestUtils.setField(service, "repository", repository);
    }

    private Regelimplementatiesituatie creeerGedrag(final Integer id, final Regelimplementatie implementatie,
            final Verantwoordelijke verantwoordelijke, final Boolean opschorting,
            final RedenOpschorting redenOpschorting)
    {
        Regelimplementatiesituatie resultaat = FACTORY.createRegelimplementatiesituatie();
        resultaat.setID(id);
        resultaat.setRegelimplementatie(implementatie);
        resultaat.setBijhoudingsverantwoordelijkheid(verantwoordelijke);
        resultaat.setIndicatieOpgeschort(opschorting);
        resultaat.setRedenOpschorting(redenOpschorting);
        return resultaat;
    }

    private static Regelimplementatie creeerRegelImplementatie(final Regel regel, final SoortBericht soortBericht) {
        Regelimplementatie resultaat = FACTORY.createRegelimplementatie();
        resultaat.setRegel(regel);
        resultaat.setSoortBericht(soortBericht);
        return resultaat;
    }

    private void mockZoekMethodeVanRepository(final Verantwoordelijke verantwoordelijke, final Boolean isOpschorting,
            final RedenOpschorting redenOpschorting, final List<Regelimplementatiesituatie> returnValue)
    {
        Mockito.when(
                repository.zoekActiefBedrijfsregelGedrag(SoortBericht.OPVRAGENPERSOONVRAAG, verantwoordelijke,
                        isOpschorting, redenOpschorting)).thenReturn(returnValue);
    }

    private Set<Integer> extractGedragIds(final List<Regelimplementatiesituatie> gedragingen) {
        Set<Integer> gedragIds = new HashSet<Integer>();
        for (Regelimplementatiesituatie gedrag : gedragingen) {
            gedragIds.add(gedrag.getID());
        }
        return gedragIds;
    }

}
