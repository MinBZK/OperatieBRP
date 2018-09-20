/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.AbstractRepositoryTestCase;
import nl.bzk.brp.domein.lev.Abonnement;

import org.junit.Assert;
import org.junit.Test;


/**
 * Testcases voor de {@link AbonnementRepository} class.
 */
public class AbonnementRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private AbonnementRepository abonnementRepository;

    /**
     * Test het ophalen van een abonnement op basis van de id en partij id.
     */
    @Test
    public void testZoekAbonnementOpId() {
        List<Abonnement> abonnementen = abonnementRepository.findById(1);
        Assert.assertNotNull(abonnementen);
        Assert.assertEquals(1, abonnementen.size());

        abonnementen = abonnementRepository.findById(2);
        Assert.assertNotNull(abonnementen);
        Assert.assertEquals(1, abonnementen.size());
    }
}
