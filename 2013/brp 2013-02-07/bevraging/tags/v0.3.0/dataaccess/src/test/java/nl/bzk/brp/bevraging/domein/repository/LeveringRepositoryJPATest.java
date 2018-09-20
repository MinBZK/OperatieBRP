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
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.autaut.Authenticatiemiddel;
import nl.bzk.brp.domein.lev.Abonnement;
import nl.bzk.brp.domein.lev.Levering;
import nl.bzk.brp.domein.lev.SoortAbonnement;
import nl.bzk.brp.domein.lev.SoortLevering;
import nl.bzk.brp.domein.lev.persistent.PersistentLevering;

import org.junit.Test;


/**
 * Testcases voor de {@link LeveringRepositoryJPA} class.
 */
public class LeveringRepositoryJPATest extends AbstractRepositoryTestCase {

    @Inject
    private LeveringRepository  leveringRepository;

    @Inject
    private DomeinObjectFactory domeinObjectFactory;

    /**
     * Test voor het opslaan van een levering.
     */
    @Test
    public void testOpslaanLevering() {
        Levering levering = getLevering();

        assertEquals(1, countRowsInTable("lev.lev"));
        assertNull(levering.getID());
        levering = leveringRepository.saveAndFlush((PersistentLevering) levering);
        assertNotNull(levering.getID());
        assertEquals(2, countRowsInTable("lev.lev"));
    }

    /**
     * Retourneert een geheel gevulde (nieuwe) levering.
     *
     * @return een levering.
     */
    private Levering getLevering() {
        Levering levering = domeinObjectFactory.createLevering();
        levering.setDatumTijdBeschouwing(Calendar.getInstance());
        levering.setDatumTijdKlaarzettenLevering(Calendar.getInstance());
        levering.setSoort(SoortLevering.BEVRAGING);
        levering.setAbonnement(getAbonnement());
        levering.setAuthenticatiemiddel(getAuthenticatieMiddel());
        return levering;
    }

    /**
     * Levert een reeds in de db bestaand abonnement.
     *
     * @return een abonnement.
     */
    private Abonnement getAbonnement() {
        Abonnement abonnement = domeinObjectFactory.createAbonnement();
        abonnement.setSoortAbonnement(SoortAbonnement.LEVERING);
        abonnement.setID(1);
        return abonnement;
    }

    /**
     * Levert een reeds in de db bestaand authenticatiemiddel.
     *
     * @return een authenticatiemiddel.
     */
    private Authenticatiemiddel getAuthenticatieMiddel() {
        Authenticatiemiddel authenticatiemiddel = domeinObjectFactory.createAuthenticatiemiddel();
        authenticatiemiddel.setID(1);
        return authenticatiemiddel;
    }

}
