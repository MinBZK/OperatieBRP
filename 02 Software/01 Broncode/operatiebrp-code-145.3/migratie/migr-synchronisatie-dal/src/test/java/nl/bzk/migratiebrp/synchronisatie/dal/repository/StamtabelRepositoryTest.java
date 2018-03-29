/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import java.util.Collection;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.test.dal.DBUnit.InsertBefore;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test.
 */
public class StamtabelRepositoryTest extends AbstractDatabaseTest {

    @Inject
    private StamtabelRepository reposistory;

    @InsertBefore("StamtabelPartijTestData.xml")
    @Test
    public void haalAllePartijenOp() {
        final Collection<Partij> partijen = reposistory.findAllPartijen();
        Assert.assertNotNull(partijen);
        Assert.assertFalse(partijen.isEmpty());
        Assert.assertEquals(3, partijen.size());
        Assert.assertTrue(partijen.stream().anyMatch(partij -> !partij.getGemeenten().isEmpty()));
    }

    @InsertBefore("StamtabelPartijTestData.xml")
    @Test
    public void haalAlleGemeentenOp() {
        final Collection<Gemeente> gemeenten = reposistory.findAllGemeentes();
        Assert.assertNotNull(gemeenten);
        Assert.assertFalse(gemeenten.isEmpty());
        Assert.assertEquals(2, gemeenten.size());
    }
}