/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor de {@link DatumValidator} class.
 */
public class DatumValidatorTest {

    private final DatumValidator validator = new DatumValidator();

    @Test
    public void testGeldigeDatumValidatie() {
        Assert.assertTrue("null zou WEL geldig moeten zijn.", validator.isValid(null, null));
        Assert.assertTrue("0 zou WEL geldig moeten zijn.", validator.isValid(0, null));
        Assert.assertTrue("2012 00 00 zou WEL geldig moeten zijn.", validator.isValid(20120000, null));
        Assert.assertTrue("2012 01 00 zou WEL geldig moeten zijn.", validator.isValid(20120100, null));
        Assert.assertTrue(validator.isValid(20120101, null));
        Assert.assertTrue(validator.isValid(20120229, null));
        Assert.assertTrue("0001 00 00 zou wel geldig moeten zijn.", validator.isValid(10000, null));
        Assert.assertTrue("0001 05 00 zou wel geldig moeten zijn.", validator.isValid(10500, null));
        Assert.assertTrue("0020 12 12 zou wel geldig moeten zijn.", validator.isValid(201212, null));

    }

    @Test
    public void testOnGeldigeDatumValidatie() {
        Assert.assertFalse("0000 00 01 zou NIET geldig moeten zijn.", validator.isValid(1, null));
        Assert.assertFalse("0000 20 12 zou NIET geldig moeten zijn.", validator.isValid(2012, null));
        Assert.assertFalse("2012 00 12 zou NIET geldig moeten zijn.", validator.isValid(20120012, null));
        Assert.assertFalse("2012 14 00 zou NIET geldig moeten zijn.", validator.isValid(20121400, null));
        Assert.assertFalse("2012 13 01 zou NIET geldig moeten zijn.", validator.isValid(20121300, null));
        Assert.assertFalse("2012 12 32 zou NIET geldig moeten zijn.", validator.isValid(20121232, null));
        Assert.assertFalse("2012 04 31 zou NIET geldig moeten zijn.", validator.isValid(20120431, null));
        Assert.assertFalse(validator.isValid(20110229, null));
        // date parser accepteert 4042/01/2  als 4042/01/02 !!
        Assert.assertFalse(validator.isValid(new Integer("04042012"), null));
        // leading zero is een octal getal!
        Assert.assertFalse("0404 20 12 zou NIET geldig moeten zijn.", validator.isValid(04042012, null));
        Assert.assertFalse("0001 13 00 zou NIET geldig moeten zijn.", validator.isValid(11300, null));
        Assert.assertFalse("0001 03 32 zou NIET geldig moeten zijn.", validator.isValid(10332, null));
    }
}
