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
 * Unit test voor de niet standaard methodes in de {@link HisRelatie} class.
 */
public class HisRelatieTest {

    @Test
    public void testClone() throws CloneNotSupportedException {
        HisRelatie relatie = new HisRelatie();
        relatie.setId(3L);
        relatie.setRelatie(new PersistentRelatie());
        relatie.setDatumAanvang(Integer.valueOf(20120603));
        relatie.setDatumEinde(Integer.valueOf(20120607));
        relatie.setDatumTijdRegistratie(new Date());

        HisRelatie clone = relatie.clone();
        Assert.assertNull(clone.getId());
        Assert.assertNotNull(clone.getRelatie());
        Assert.assertEquals(Integer.valueOf(20120603), clone.getDatumAanvang());
        Assert.assertEquals(Integer.valueOf(20120607), clone.getDatumEinde());
        Assert.assertNull(clone.getDatumTijdRegistratie());
    }
}
