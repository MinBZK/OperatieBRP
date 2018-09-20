/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.validatie;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;

import org.junit.Test;

public class ValidationUtilsTest {
    @Test(expected = NullPointerException.class)
    public void testControleerOpNullWaarden() {
        ValidationUtils.controleerOpNullWaarden("waarde mag niet null zijn", (String) null);
    }

    @Test
    public void testControleerOpLegeWaarden() {
        try {
            final String waarde = "";
            Validatie.controleerOpLegeWaarden("waarde mag niet leeg zijn", waarde);
            Assert.fail("IllegalArgumentException verwacht");
        } catch (final IllegalArgumentException npe) {
            Assert.assertEquals("waarde mag niet leeg zijn", npe.getMessage());
        }
    }

    @Test(expected = InvocationTargetException.class)
    public void testPrivateConstructor() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<ValidationUtils> c = ValidationUtils.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
    }
}
