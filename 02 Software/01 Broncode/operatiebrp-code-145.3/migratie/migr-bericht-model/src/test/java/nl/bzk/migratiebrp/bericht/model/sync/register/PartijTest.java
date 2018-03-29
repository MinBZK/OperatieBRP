/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.register;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Test;

public class PartijTest {

    @Test
    public void testGBAPartij() {
        Partij partij = new Partij("012301", "0123", null, Collections.singletonList(Rol.AFNEMER));
        Assert.assertTrue(partij.isAfnemer());
        Assert.assertFalse(partij.isBijhouder());
        Assert.assertEquals(Stelsel.GBA, partij.getStelsel());
    }

    @Test
    public void testBRPPartij() {
        Partij partij = new Partij("012301", null, Date.valueOf("2017-01-01"), Collections.singletonList(Rol.AFNEMER));
        Assert.assertTrue(partij.isAfnemer());
        Assert.assertFalse(partij.isBijhouder());
        Assert.assertEquals(Stelsel.BRP, partij.getStelsel());
        Assert.assertEquals(null, partij.getGemeenteCode());
    }

    @Test
    public void testGBAPartijBijhouder() {
        Partij partij = new Partij("012301", "0123", null, Arrays.asList(Rol.AFNEMER, Rol.BIJHOUDINGSORGAAN_COLLEGE));
        Assert.assertTrue(partij.isAfnemer());
        Assert.assertTrue(partij.isBijhouder());
        Assert.assertEquals(Stelsel.GBA, partij.getStelsel());
        Assert.assertEquals("0123", partij.getGemeenteCode());
    }

    @Test
    public void testBRPPartijBijhouder() {
        Partij partij = new Partij("012301", "0123", Date.valueOf("2017-01-01"), Arrays.asList(Rol.AFNEMER, Rol.BIJHOUDINGSORGAAN_MINISTER));
        Assert.assertTrue(partij.isAfnemer());
        Assert.assertTrue(partij.isBijhouder());
        Assert.assertEquals(Stelsel.BRP, partij.getStelsel());
    }

    @Test
    public void testBRPPartijBijhoudingsvoorstelOrgaan() {
        Partij partij = new Partij("012301", "0123", Date.valueOf("2017-01-01"), Collections.singletonList(Rol.BIJHOUDINGSVOORSTELORGAAN));
        Assert.assertFalse(partij.isAfnemer());
        Assert.assertFalse(partij.isBijhouder());
        Assert.assertEquals(Stelsel.BRP, partij.getStelsel());
    }

    @Test
    public void testRNI() {
        Partij partij = new Partij("199901", null, null, Collections.singletonList(Rol.BIJHOUDINGSORGAAN_COLLEGE));
        Assert.assertFalse(partij.isAfnemer());
        Assert.assertTrue(partij.isBijhouder());
        Assert.assertTrue(partij.isRNI());
        Assert.assertEquals(Stelsel.GBA, partij.getStelsel());
        Assert.assertEquals("1999", partij.getGemeenteCode());
    }
}
