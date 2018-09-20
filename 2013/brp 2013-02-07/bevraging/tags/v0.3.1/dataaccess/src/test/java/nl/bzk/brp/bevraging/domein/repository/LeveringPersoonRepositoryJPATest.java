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
import nl.bzk.brp.domein.lev.LeveringPersoon;
import nl.bzk.brp.domein.lev.SoortAbonnement;
import nl.bzk.brp.domein.lev.SoortLevering;
import org.junit.Test;


/**
 * Testcases voor de {@link LeveringPersoonRepositoryJPA} class.
 */
public class LeveringPersoonRepositoryJPATest extends AbstractRepositoryTestCase {

    @Inject
    private LeveringPersoonRepository leveringPersoonRepository;

    @Inject
    private PersoonRepository         persoonRepository;

    @Inject
    private DomeinObjectFactory       domeinObjectFactory;

    /**
     * Test voor het opslaan van een levering.
     */
    @Test
    public void testOpslaanLeveringPersoon() {
        LeveringPersoon leveringPersoon = domeinObjectFactory.createLeveringPersoon();
        leveringPersoon.setLevering(getLevering());
        leveringPersoon.setPersoon(persoonRepository.findOne(1L));

        int aantalLeveringPersonen = countRowsInTable("lev.levpers");
        assertNull(leveringPersoon.getID());
        leveringPersoonRepository.save(leveringPersoon);
        leveringPersoon = leveringPersoonRepository.findAll().get(0);
        assertNotNull(leveringPersoon.getID());
        assertEquals(aantalLeveringPersonen + 1, countRowsInTable("lev.levpers"));
    }

    /**
     * Retourneert een geheel gevulde (nieuwe) levering.
     *
     * @return een levering.
     */
    private Levering getLevering() {
        Levering levering = domeinObjectFactory.createLevering();
        levering.setID(1L);
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
