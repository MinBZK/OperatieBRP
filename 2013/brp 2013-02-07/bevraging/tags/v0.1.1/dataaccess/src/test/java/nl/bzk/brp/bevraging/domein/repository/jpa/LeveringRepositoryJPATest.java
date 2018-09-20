/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Calendar;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import nl.bzk.brp.bevraging.domein.lev.Levering;
import nl.bzk.brp.bevraging.domein.lev.LeveringSoort;
import nl.bzk.brp.bevraging.domein.repository.LeveringRepository;
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
        levering.setPartij(getPartij());
        return levering;
    }

    /**
     * Levert een reeds in de db bestaande partij.
     *
     * @return een partij.
     */
    private Partij getPartij() {
        Partij partij = new Partij(SoortPartij.GEMEENTE);
        ReflectionTestUtils.setField(partij, "id", 1L);
        return partij;
    }

}
