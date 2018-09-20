/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Calendar;

import javax.inject.Inject;

import nl.bzk.brp.AbstractRepositoryTestCase;
import nl.bzk.brp.bevraging.domein.aut.AuthenticatieMiddel;
import nl.bzk.brp.bevraging.domein.lev.Abonnement;
import nl.bzk.brp.bevraging.domein.lev.Levering;
import nl.bzk.brp.bevraging.domein.lev.LeveringSoort;
import nl.bzk.brp.bevraging.domein.lev.SoortAbonnement;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;



/**
 * Testcases voor de {@link LeveringRepositoryJPA} class.
 */
public class LeveringRepositoryJPATest extends AbstractRepositoryTestCase {

    @Inject
    private LeveringRepository leveringRepository;

    /**
     * Test voor het opslaan van een levering.
     */
    @Test
    public void testOpslaanLevering() {
        Levering levering = getLevering();

        assertEquals(1, countRowsInTable("lev.lev"));
        assertNull(levering.getId());
        levering = leveringRepository.saveAndFlush(levering);
        assertNotNull(levering.getId());
        assertEquals(2, countRowsInTable("lev.lev"));
    }

    /**
     * Retourneert een geheel gevulde (nieuwe) levering.
     *
     * @return een levering.
     */
    private Levering getLevering() {
        Levering levering = new Levering();
        levering.setBeschouwing(Calendar.getInstance());
        levering.setLevering(Calendar.getInstance());
        levering.setSoort(LeveringSoort.BRP_BEVRAGING);
        levering.setAbonnement(getAbonnement());
        levering.setAuthenticatieMiddel(getAuthenticatieMiddel());
        return levering;
    }

    /**
     * Levert een reeds in de db bestaand abonnement.
     *
     * @return een abonnement.
     */
    private Abonnement getAbonnement() {
        Abonnement abonnement = new Abonnement(null, SoortAbonnement.LEVERING);
        ReflectionTestUtils.setField(abonnement, "id", 1L);
        return abonnement;
    }

    /**
     * Levert een reeds in de db bestaand authenticatiemiddel.
     *
     * @return een authenticatiemiddel.
     */
    private AuthenticatieMiddel getAuthenticatieMiddel() {
        AuthenticatieMiddel authenticatiemiddel = new AuthenticatieMiddel() { };
        ReflectionTestUtils.setField(authenticatiemiddel, "id", 1L);
        return authenticatiemiddel;
    }

}
