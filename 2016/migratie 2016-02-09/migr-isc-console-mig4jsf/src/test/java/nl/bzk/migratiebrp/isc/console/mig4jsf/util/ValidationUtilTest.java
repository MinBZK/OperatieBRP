/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.util;

import org.junit.Assert;
import org.junit.Test;

public class ValidationUtilTest {

    @Test
    public void testValideerANummerOK() {
        Assert.assertTrue(ValidationUtil.valideerANummer("1231231234"));
        Assert.assertTrue(ValidationUtil.valideerANummer("1745105604"));
        Assert.assertTrue(ValidationUtil.valideerANummer("6139365949"));
        Assert.assertTrue(ValidationUtil.valideerANummer("7159878730"));
        Assert.assertTrue(ValidationUtil.valideerANummer("4368978532"));
    }

    @Test
    public void testValideerANummerFOUT() {
        Assert.assertFalse(ValidationUtil.valideerANummer(""));
        Assert.assertFalse(ValidationUtil.valideerANummer("-284289241"));
        Assert.assertFalse(ValidationUtil.valideerANummer("28428924141"));
        Assert.assertFalse(ValidationUtil.valideerANummer("363570648A"));
        Assert.assertFalse(ValidationUtil.valideerANummer("0465823130"));
        Assert.assertFalse(ValidationUtil.valideerANummer("5565431828"));
        Assert.assertFalse(ValidationUtil.valideerANummer("7159878731"));
    }
}
