/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.kern.validatie;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import junit.framework.Assert;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.kern.Land;

import org.junit.Before;
import org.junit.Test;


public class LandcodeTest {

    private static Validator    validator;

    private DomeinObjectFactory kernFactory = new PersistentDomeinObjectFactory();

    @Before
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testLandNaamIsNull() {
        Land land = kernFactory.createLand();
        land.setLandcode("1234");
        Set<ConstraintViolation<Land>> violations = validator.validate(land);
        Assert.assertEquals(1, violations.size());
        ConstraintViolation<Land> violation = violations.iterator().next();
        Assert.assertEquals("may not be null", violation.getMessage());
        Assert.assertNull(violation.getInvalidValue());
    }

    @Test
    public void testLandcodeTeLang() {
        Land land = kernFactory.createLand();
        land.setLandcode("12345");
        land.setNaam("Nederland");
        Set<ConstraintViolation<Land>> violations = validator.validate(land);
        Assert.assertEquals(1, violations.size());
        ConstraintViolation<Land> violation = violations.iterator().next();
        Assert.assertEquals("size must be between 0 and 4", violation.getMessage());
        Assert.assertEquals("12345", violation.getInvalidValue());
    }
}
