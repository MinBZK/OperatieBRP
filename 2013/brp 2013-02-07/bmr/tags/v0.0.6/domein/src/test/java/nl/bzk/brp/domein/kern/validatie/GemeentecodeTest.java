/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.kern.validatie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.kern.Partij;

import org.junit.Assert;
import org.junit.Test;


public class GemeentecodeTest {

    private DomeinObjectFactory kernFactory = new PersistentDomeinObjectFactory();

    private Validator           validator   = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testGemeenteCodeIsNull() {
        Partij gemeente = kernFactory.createPartij();
        Set<ConstraintViolation<Partij>> violations = validator.validate(gemeente);
        Assert.assertEquals(1, violations.size());
        ConstraintViolation<Partij> violation = violations.iterator().next();
        Assert.assertEquals("may not be null", violation.getMessage());
    }

    @Test
    public void testGemeenteCodeIsTeKort() {
        Partij gemeente = kernFactory.createPartij();
        gemeente.setGemeentecode("");
        Set<ConstraintViolation<Partij>> violations = validator.validate(gemeente);
        Assert.assertEquals(2, violations.size());
        List<ConstraintViolation<Partij>> violationList = new ArrayList<ConstraintViolation<Partij>>(violations);
        Collections.sort(violationList, new Comparator<ConstraintViolation<Partij>>() {

            @Override
            public int compare(final ConstraintViolation<Partij> o1, final ConstraintViolation<Partij> o2) {
                return o1.getPropertyPath().toString().compareTo(o2.getPropertyPath().toString());
            }
        });
        Assert.assertEquals("size must be between 4 and 4", violationList.get(0).getMessage());
        Assert.assertEquals("gemeentecode", violationList.get(0).getPropertyPath().toString());
        Assert.assertEquals("may not be null", violationList.get(1).getMessage());
        Assert.assertEquals("naam", violationList.get(1).getPropertyPath().toString());
    }

    @Test
    public void testGemeenteCodeIsGoed() {
        Partij gemeente = kernFactory.createPartij();
        gemeente.setGemeentecode("1234");
        Set<ConstraintViolation<Partij>> violations = validator.validate(gemeente);
        Assert.assertEquals(1, violations.size());
        Iterator<ConstraintViolation<Partij>> iterator = violations.iterator();
        ConstraintViolation<Partij> violation = iterator.next();
        Assert.assertEquals("may not be null", violation.getMessage());
    }
}
