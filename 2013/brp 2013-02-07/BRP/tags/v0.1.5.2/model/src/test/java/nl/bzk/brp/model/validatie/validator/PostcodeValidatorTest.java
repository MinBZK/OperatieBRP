/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor de {@link PostcodeValidator} class.
 */
public class PostcodeValidatorTest {

    private final PostcodeValidator validator = new PostcodeValidator();

    @Test
    public void testGeldigeDatumValidatie() {
        Assert.assertTrue(validator.isValid(null, null));
        Assert.assertTrue(validator.isValid("1234BB", null));
    }

    @Test
    public void testOnGeldigeDatumValidatie() {
        Assert.assertFalse(validator.isValid("", null));
        Assert.assertFalse(validator.isValid("aaaa", null));
        Assert.assertFalse(validator.isValid("123abc", null));
        Assert.assertFalse(validator.isValid("01abcd", null));
        Assert.assertFalse(validator.isValid("1234bb", null));
        Assert.assertFalse(validator.isValid("0234BB", null));
        Assert.assertFalse(validator.isValid("1234B1", null));
        Assert.assertFalse(validator.isValid("1234 B1", null));
    }
}
