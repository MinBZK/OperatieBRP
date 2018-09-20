/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import java.util.Date;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Unit test voor de niet standaard methodes in de {@link HisBetrokkenheidOuder} class.
 */
public class HisBetrokkenheidOuderTest {

    @Test
    public void testClone() throws CloneNotSupportedException {
        HisBetrokkenheidOuder betrokkenheidOuder = new HisBetrokkenheidOuder();
        betrokkenheidOuder.setId(3L);
        betrokkenheidOuder.setBetrokkenheid(new PersistentBetrokkenheid());
        betrokkenheidOuder.setIndicatieOuder(true);
        betrokkenheidOuder.setDatumAanvangGeldigheid(Integer.valueOf(20120603));
        betrokkenheidOuder.setDatumEindeGeldigheid(Integer.valueOf(20120607));
        betrokkenheidOuder.setDatumTijdRegistratie(new Date());

        HisBetrokkenheidOuder clone = betrokkenheidOuder.clone();
        Assert.assertNull(clone.getId());
        Assert.assertNotNull(clone.getBetrokkenheid());
        Assert.assertTrue(clone.getIndicatieOuder());
        Assert.assertEquals(Integer.valueOf(20120603), clone.getDatumAanvangGeldigheid());
        Assert.assertEquals(Integer.valueOf(20120607), clone.getDatumEindeGeldigheid());
        Assert.assertNull(clone.getDatumTijdRegistratie());
    }
}
