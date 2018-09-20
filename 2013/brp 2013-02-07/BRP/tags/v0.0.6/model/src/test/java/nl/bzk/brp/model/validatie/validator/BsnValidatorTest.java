/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor de {@link BsnValidator} class.
 */
public class BsnValidatorTest {

    private final BsnValidator validator = new BsnValidator();

    @Test
    public void testGeldigeBsnValidatie() {
        Assert.assertTrue(validator.isValid(null, null));
        Assert.assertTrue(validator.isValid("", null));
        Assert.assertTrue(validator.isValid("111222333", null));
        Assert.assertTrue(validator.isValid("123456782", null));
        Assert.assertTrue(validator.isValid("882134061", null));
        Assert.assertTrue(validator.isValid("089444917", null));

    }

    @Test
    public void testOnGeldigeBsnValidatie() {
        Assert.assertFalse(validator.isValid("A", null));
        Assert.assertFalse(validator.isValid("1", null));
        Assert.assertFalse(validator.isValid("11122233", null));
        Assert.assertFalse(validator.isValid("89444917", null));
    }
}
