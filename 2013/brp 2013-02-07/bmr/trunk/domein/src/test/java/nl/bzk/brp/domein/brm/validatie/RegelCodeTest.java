/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.brm.validatie;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import junit.framework.Assert;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.brm.Regel;

import org.junit.Test;


public class RegelCodeTest {

    private static final Validator validator   = Validation.buildDefaultValidatorFactory().getValidator();

    private DomeinObjectFactory    factory = new PersistentDomeinObjectFactory();

    @Test
    public void testRegelCodeIsNull() {
        Regel regel = factory.createRegel();
        Set<ConstraintViolation<Regel>> violations = validator.validate(regel);
        Assert.assertEquals(4, violations.size());
    }

}
