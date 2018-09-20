/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import org.junit.Assert;
import org.junit.Test;


public class ObjectUtilTest {

    @Test
    public void isAllEmptyTest() {
        Assert.assertFalse("geen van lle zijn null", ObjectUtil.isAllEmpty("Haab", "hhh", "tetet"));
        Assert.assertFalse("Minimaal een met een null", ObjectUtil.isAllEmpty("Haab", null, "tetet"));
        Assert.assertTrue("Allemaal nullen", ObjectUtil.isAllEmpty(null, null, null));
        Assert.assertTrue("Allemaal nullen en 1 lege string", ObjectUtil.isAllEmpty(null, "", null, null));
    }

    @Test
    public void testConverteerNaarEmpty() {
        Assert.assertEquals("", ObjectUtil.converteerNaarEmpty(null));
        Assert.assertEquals("abc", ObjectUtil.converteerNaarEmpty("abc"));
    }

    @Test
    public void testConverteerNaarFalse() {
        Assert.assertEquals(false, ObjectUtil.converteerNaarFalse(null));
        Assert.assertEquals(true, ObjectUtil.converteerNaarFalse(true));
        Assert.assertEquals(false, ObjectUtil.converteerNaarFalse(false));
    }

}
