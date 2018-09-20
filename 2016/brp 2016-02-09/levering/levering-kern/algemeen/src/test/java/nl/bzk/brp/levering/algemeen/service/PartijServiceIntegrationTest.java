/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service;

import javax.inject.Inject;
import nl.bzk.brp.levering.dataaccess.AbstractIntegratieTest;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;


public class PartijServiceIntegrationTest extends AbstractIntegratieTest {

    @Inject
    private PartijService partijService;

    @Test
    public final void testVindPartijOpCode() {
        final Partij partij = partijService.vindPartijOpCode(96801);
        Assert.assertNotNull(partij);
        Assert.assertEquals("Gemeente Sittard", partij.getNaam().getWaarde());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public final void testVindNietBestaandePartijOpCode() {
        partijService.vindPartijOpCode(99999);
    }

    @Test
    public void bestaatPartij() {
        Assert.assertTrue(partijService.bestaatPartij(96801));
        Assert.assertFalse(partijService.bestaatPartij(99999));
    }

}
