/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.Test;

public class Lo3ValidatieTest {

    @Test(expected = AssertionError.class)
    public void testPrivateConstructor() throws Throwable {
        try {
            Constructor<Lo3Validatie> c = Lo3Validatie.class.getDeclaredConstructor();
            c.setAccessible(true);
            c.newInstance();
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @Test
    public void testIsEenParameterNietNull() throws Exception {
        assertTrue(Lo3Validatie.isEenParameterNietNull("1", "2"));
        assertTrue(Lo3Validatie.isEenParameterNietNull(null, "2", "3", "4"));
        assertTrue(Lo3Validatie.isEenParameterNietNull(null, null, null, "4"));
        assertFalse(Lo3Validatie.isEenParameterNietNull(null, null, null));
    }

    @Test
    public void testIsElementGevuld() throws Exception {
        Lo3String lo3a = new Lo3String("iets");
        Lo3String lo3b = new Lo3String(null);
        assertTrue(Lo3Validatie.isElementGevuld(lo3a));
        assertFalse(Lo3Validatie.isElementGevuld(lo3b));
    }

    @Test
    public void testIsEenParameterGevuld() throws Exception {
        Lo3String lo3a = new Lo3String("iets");
        Lo3String lo3c = new Lo3String(null);
        Lo3String lo3d = new Lo3String(null);
        assertTrue(Lo3Validatie.isEenParameterGevuld(lo3a, lo3c, lo3d));
        assertFalse(Lo3Validatie.isEenParameterGevuld(lo3c, lo3d));
    }
}
