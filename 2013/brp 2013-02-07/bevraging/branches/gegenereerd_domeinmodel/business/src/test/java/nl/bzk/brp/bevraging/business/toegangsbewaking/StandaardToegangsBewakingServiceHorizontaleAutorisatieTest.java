/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.toegangsbewaking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import nl.bzk.brp.bevraging.domein.aut.AutorisatieBesluit;
import nl.bzk.brp.bevraging.domein.aut.SoortAutorisatieBesluit;
import nl.bzk.brp.bevraging.domein.lev.Abonnement;
import nl.bzk.brp.bevraging.domein.lev.SoortAbonnement;
import nl.bzk.brp.bevraging.domein.repository.HorizontaleAutorisatieRepository;
import nl.bzk.brp.toegangsbewaking.parser.ParserException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class StandaardToegangsBewakingServiceHorizontaleAutorisatieTest {

    @Mock
    private HorizontaleAutorisatieRepository horizontaleAutorisatieRepository;

    private ToegangsBewakingService        service;

    @Test(expected = IllegalArgumentException.class)
    public void testInvalideCallMissendAbonnement() throws ParserException {
        service.controleerLijstVanPersonenVoorAbonnement(null, new ArrayList<Long>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalideCallMissendCommand() throws ParserException {
        Abonnement abonnement = getAbonnementOpSoortBerichten("", "");
        service.controleerLijstVanPersonenVoorAbonnement(abonnement, null);
    }

    @Test
    public void testNiemandToegangMetLegePersoonIds() throws ParserException {
        Abonnement abonnement = getAbonnementOpSoortBerichten("", "");

        Map<Long, Boolean> resultaat =
            service.controleerLijstVanPersonenVoorAbonnement(abonnement, new ArrayList<Long>());
        assertTrue(resultaat.isEmpty());
    }

    @Test
    public void testIedereenToegangMetLegeCriteria() throws ParserException {
        Abonnement abonnement = getAbonnementOpSoortBerichten("", "");

        Map<Long, Boolean> resultaat =
            service.controleerLijstVanPersonenVoorAbonnement(abonnement, Arrays.asList(1L, 2L, 3L));
        assertEquals(3, resultaat.size());
        for (Entry<Long, Boolean> entry : resultaat.entrySet()) {
            assertTrue(entry.getValue());
        }
    }

    @Test
    public void testIedereenToegangMetNullAlsCriteria() throws ParserException {
        Abonnement abonnement = getAbonnementOpSoortBerichten(null, null);

        Map<Long, Boolean> resultaat =
            service.controleerLijstVanPersonenVoorAbonnement(abonnement, Arrays.asList(1L, 2L, 3L));
        assertEquals(3, resultaat.size());
        for (Entry<Long, Boolean> entry : resultaat.entrySet()) {
            assertTrue(entry.getValue());
        }
    }

    @Test(expected = ParserException.class)
    public void testFoutVoorOngeldigCriteria() throws ParserException {
        Abonnement abonnement = getAbonnementOpSoortBerichten("xxxxx", null);
        Mockito.when(
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Matchers.anyListOf(Long.class),
                        Matchers.eq("xxxxx"), Matchers.isNull(String.class))).thenThrow(new ParserException(""));
        service.controleerLijstVanPersonenVoorAbonnement(abonnement, Arrays.asList(1L, 2L, 3L));
    }

    @Test
    public void testEnkelePersonenToegangVoorEenGeldigCriteria() throws ParserException {
        Abonnement abonnement = getAbonnementOpSoortBerichten("Leeftijd >= 2", "");

        Map<Long, Boolean> resultaat =
            service.controleerLijstVanPersonenVoorAbonnement(abonnement, Arrays.asList(1L, 2L, 3L));
        assertEquals(3, resultaat.size());
        assertFalse(resultaat.get(1L));
        assertTrue(resultaat.get(2L));
        assertTrue(resultaat.get(3L));
    }

    @Test
    public void testEnkelePersonenToegangVoorTweeCriteria() throws ParserException {
        Abonnement abonnement = getAbonnementOpSoortBerichten("Leeftijd >= 2", "Leeftijd < 3");

        Map<Long, Boolean> resultaat =
            service.controleerLijstVanPersonenVoorAbonnement(abonnement, Arrays.asList(1L, 2L, 3L));
        assertEquals(3, resultaat.size());
        assertFalse(resultaat.get(1L));
        assertTrue(resultaat.get(2L));
        assertFalse(resultaat.get(3L));
    }

    @Before
    public void init() throws ParserException {
        MockitoAnnotations.initMocks(this);
        service = new StandaardToegangsBewakingService();
        ReflectionTestUtils.setField(service, "horizontaleAutorisatieRepository", horizontaleAutorisatieRepository);

        Mockito.when(
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Matchers.anyListOf(Long.class),
                        Matchers.notNull(String.class), Matchers.notNull(String.class))).thenReturn(Arrays.asList(2L));
        Mockito.when(
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Matchers.anyListOf(Long.class),
                        Matchers.notNull(String.class), Matchers.eq(""))).thenReturn(Arrays.asList(2L, 3L));
        Mockito.when(
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Matchers.anyListOf(Long.class), Matchers.eq(""),
                        Matchers.notNull(String.class))).thenReturn(Arrays.asList(1L, 2L));
    }

    /**
     * Retourneert een abonnement, inclusief doelbinding, met de twee opgegeven populatie criteria.
     *
     * @param hoofdCriterium het hoofd populatie criterium op de doelbinding.
     * @param subCriterium het sub populatie criterium op het abonnement.
     * @return een abonnement.
     */
    private Abonnement getAbonnementOpSoortBerichten(final String hoofdCriterium, final String subCriterium) {
        AutorisatieBesluit besluit =
            new AutorisatieBesluit(SoortAutorisatieBesluit.LEVERINGSAUTORISATIE, "Het mag niet", new Partij(
                    SoortPartij.VERTEGENWOORDIGER_REGERING));
        Partij partij = new Partij(SoortPartij.GEMEENTE);
        Abonnement abonnement = partij.createDoelbinding(besluit).createAbonnement(SoortAbonnement.LEVERING);
        ReflectionTestUtils.setField(abonnement.getDoelBinding(), "populatieCriterium", hoofdCriterium);
        ReflectionTestUtils.setField(abonnement, "populatieCriterium", subCriterium);
        return abonnement;
    }

}
