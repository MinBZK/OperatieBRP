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

import nl.bzk.brp.bevraging.domein.repository.HorizontaleAutorisatieRepository;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.autaut.Autorisatiebesluit;
import nl.bzk.brp.domein.autaut.SoortAutorisatiebesluit;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.SoortPartij;
import nl.bzk.brp.domein.lev.Abonnement;
import nl.bzk.brp.domein.lev.SoortAbonnement;
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

    private ToegangsBewakingService          service;

    private DomeinObjectFactory              factory = new PersistentDomeinObjectFactory();

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
                horizontaleAutorisatieRepository.filterPersonenBijFilter(Matchers.anyListOf(Long.class),
                        Matchers.eq(""), Matchers.notNull(String.class))).thenReturn(Arrays.asList(1L, 2L));
    }

    /**
     * Retourneert een abonnement, inclusief doelbinding, met de twee opgegeven populatie criteria.
     *
     * @param hoofdCriterium het hoofd populatie criterium op de doelbinding.
     * @param subCriterium het sub populatie criterium op het abonnement.
     * @return een abonnement.
     */
    private Abonnement getAbonnementOpSoortBerichten(final String hoofdCriterium, final String subCriterium) {
        Partij partij = factory.createPartij();
        partij.setSoort(SoortPartij.VERTEGENWOORDIGER_REGERING);
        Autorisatiebesluit besluit = factory.createAutorisatiebesluit();
        besluit.setSoort(SoortAutorisatiebesluit.LEVERINGSAUTORISATIE);
        besluit.setBesluittekst("Het mag niet");
        besluit.setAutoriseerder(partij);
        partij = factory.createPartij();
        partij.setSoort(SoortPartij.GEMEENTE);
        Abonnement abonnement = partij.createDoelbinding(besluit).createAbonnement(SoortAbonnement.LEVERING);
        abonnement.getDoelbinding().setPopulatiecriterium(hoofdCriterium);
        abonnement.setPopulatiecriterium(subCriterium);
        return abonnement;
    }

}
