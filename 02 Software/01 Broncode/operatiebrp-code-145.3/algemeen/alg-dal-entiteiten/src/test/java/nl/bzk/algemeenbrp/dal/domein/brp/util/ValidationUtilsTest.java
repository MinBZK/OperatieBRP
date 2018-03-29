/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.Test;

/**
 * Unittests voor {@link ValidationUtils}.
 */
public class ValidationUtilsTest {

    @Test
    public void testControleerOpNullWaardenWaardeIsNull() {
        final String meldingTekst = "Waarde mag niet null zijn";
        try {
            ValidationUtils.controleerOpNullWaarden(meldingTekst, (Object) null);
            fail("Exception verwacht");
        } catch (NullPointerException e) {
            assertEquals(meldingTekst, e.getMessage());
        }
    }

    @Test
    public void testControleerOpNullWaardenWaardeIsNietNull() {
        try {
            ValidationUtils.controleerOpNullWaarden("Melding", "waarde");
            assertTrue(true);
        } catch (NullPointerException e) {
            fail("Exception niet verwacht");
        }
    }

    @Test
    public void controleerOpLegeWaardenWaardeisLeeg() {
        final String melding = "Waarde mag niet leeg zijn";
        try {
            ValidationUtils.controleerOpLegeWaarden(melding, "");
            fail("Exception verwacht");
        } catch (IllegalArgumentException e) {
            assertEquals(melding, e.getMessage());
        }
    }

    @Test
    public void controleerOpLegeWaardenWaardeisNull() {
        final String melding = "Waarde mag niet leeg zijn";
        try {
            ValidationUtils.controleerOpLegeWaarden(melding, (String) null);
            assertTrue(true);
        } catch (IllegalArgumentException e) {
            fail("Exception verwacht");
        }
    }

    @Test
    public void controleerOpLegeWaardenWaardeisGevuld() {
        final String melding = "Waarde mag niet leeg zijn";
        try {
            ValidationUtils.controleerOpLegeWaarden(melding, "Waarde");
            assertTrue(true);
        } catch (IllegalArgumentException e) {
            fail("Exception verwacht");
        }
    }

    @Test(expected = InvocationTargetException.class)
    public void testPrivateConstructor() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<ValidationUtils> c = ValidationUtils.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
    }

    @Test
    public void testZijnAlleParametersNull() {
        assertTrue(ValidationUtils.zijnParametersAllemaalNull(null, null, null));
        assertFalse(ValidationUtils.zijnParametersAllemaalNull(null, "niet null", null));
        assertFalse(ValidationUtils.zijnParametersAllemaalNull(null, "niet null"));
    }
}
