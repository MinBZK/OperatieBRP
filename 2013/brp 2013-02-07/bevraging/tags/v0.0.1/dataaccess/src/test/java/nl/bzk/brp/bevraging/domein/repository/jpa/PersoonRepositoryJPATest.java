/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository.jpa;

import javax.inject.Inject;
import nl.bzk.brp.bevraging.domein.repository.PersoonRepository;
import nl.bzk.brp.bevraging.domein.Persoon;
import nl.bzk.brp.bevraging.domein.SoortPersoon;
import org.junit.Assert;
import org.junit.Test;

/**
 * Testcases voor de {@link PersoonRepositoryJPA} class.
 */
public class PersoonRepositoryJPATest extends AbstractRepositoryTestCase {

    @Inject
    private PersoonRepository persoonRepository;

    /**
     * Test of zoekOpBSN method, of class PersoonRepositoryJPA.
     */
    @Test
    public void testZoekOpBSN() {
        Persoon persoon = persoonRepository.zoekOpBSN(123456789);
        Assert.assertNotNull(persoon);
        Assert.assertEquals(123456789, persoon.getBsn().intValue());
        Assert.assertEquals("Wittgenstein", persoon.getGeslachtsnaam());
        Assert.assertSame(SoortPersoon.INGESCHREVENE, persoon.getSoortPersoon());
        
        Assert.assertNotNull(persoonRepository.zoekOpBSN(234567891));
        Assert.assertNotNull(persoonRepository.zoekOpBSN(345678912));
        Assert.assertNull(persoonRepository.zoekOpBSN(456789123));
    }
}
