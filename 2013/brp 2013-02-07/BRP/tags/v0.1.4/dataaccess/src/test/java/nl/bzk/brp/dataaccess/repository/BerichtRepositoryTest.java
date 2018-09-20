/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.Calendar;

import javax.inject.Inject;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.operationeel.ber.PersistentBericht;
import nl.bzk.brp.model.operationeel.ber.Richting;
import org.junit.Assert;
import org.junit.Test;


public class BerichtRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private BerichtRepository repository;

    @Test
    public void testFindOneIngaand() {
        PersistentBericht bericht = repository.findOne(2001L);
        Assert.assertNotNull(bericht);
        Assert.assertEquals(2001L, bericht.getId().longValue());
        Assert.assertEquals(Richting.INGAAND, bericht.getRichting());
        Assert.assertEquals("in", bericht.getData());
        Assert.assertEquals(1, bericht.getDatumTijdOntvangst().get(Calendar.MINUTE));
        Assert.assertNull(bericht.getDatumTijdVerzenden());
        Assert.assertNull(bericht.getAntwoordOp());
    }

    @Test
    public void testFindOneUitgaand() {
        PersistentBericht bericht = repository.findOne(2002L);
        Assert.assertNotNull(bericht);
        Assert.assertEquals(2002L, bericht.getId().longValue());
        Assert.assertEquals(Richting.UITGAAND, bericht.getRichting());
        Assert.assertEquals("uit", bericht.getData());
        Assert.assertNull(bericht.getDatumTijdOntvangst());
        Assert.assertEquals(2, bericht.getDatumTijdVerzenden().get(Calendar.MINUTE));
        Assert.assertNotNull(bericht.getAntwoordOp());
        Assert.assertEquals(2001L, bericht.getAntwoordOp().getId().longValue());
    }

    @Test
    public void testSave() {
        PersistentBericht bericht = new PersistentBericht();
        bericht.setRichting(Richting.INGAAND);
        bericht.setData("test");
        repository.save(bericht);
        Assert.assertNotNull(bericht.getId());
    }

}
