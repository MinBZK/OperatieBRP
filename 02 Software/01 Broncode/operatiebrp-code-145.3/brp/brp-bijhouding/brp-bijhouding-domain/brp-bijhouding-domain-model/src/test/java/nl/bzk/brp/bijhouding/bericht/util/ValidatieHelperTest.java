/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.Test;

/**
 * Unittest voor {@link ValidatieHelper}.
 */
public class ValidatieHelperTest {

    @Test(expected = InvocationTargetException.class)
    public void testPrivateConstructor() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<ValidatieHelper> c = ValidatieHelper.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
    }

    @Test
    public void testControleerOpNullWaarde() {
        ValidatieHelper.controleerOpNullWaarde("Waarde", "naam");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testControleerOpNullWaardeNullCheck() {
        ValidatieHelper.controleerOpNullWaarde(null, "naam");
    }

    @Test
    public void testVergelijkStringMetShort() {
        assertTrue(ValidatieHelper.vergelijkStringMetShort("2", Short.valueOf("2")));
        assertFalse(ValidatieHelper.vergelijkStringMetShort("1", Short.valueOf("2")));
        assertFalse(ValidatieHelper.vergelijkStringMetShort("a", Short.valueOf("2")));
    }
}
