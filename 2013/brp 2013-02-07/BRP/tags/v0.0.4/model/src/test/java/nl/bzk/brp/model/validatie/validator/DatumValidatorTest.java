/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import org.junit.Assert;
import org.junit.Test;


public class DatumValidatorTest {

    private final DatumValidator validator = new DatumValidator();

    @Test
    public void testGeldigeDatumValidatie() {
        Assert.assertTrue(validator.isValid(null, null));
        Assert.assertTrue(validator.isValid(0, null));
        Assert.assertTrue(validator.isValid(20120000, null));
        Assert.assertTrue(validator.isValid(20120100, null));
        Assert.assertTrue(validator.isValid(20120101, null));

    }

    @Test
    public void testOnGeldigeDatumValidatie() {
        Assert.assertFalse(validator.isValid(1, null));
        Assert.assertFalse(validator.isValid(2012, null));
        Assert.assertFalse(validator.isValid(201212, null));
        Assert.assertFalse(validator.isValid(20120012, null));
        Assert.assertFalse(validator.isValid(20121300, null));
        Assert.assertFalse(validator.isValid(20121232, null));
    }
}
