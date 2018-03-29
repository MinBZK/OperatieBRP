/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static org.junit.Assert.*;

public class EncodingConstantsTest {

    @Test
    public void testSettings() {
        assertEquals(StandardCharsets.UTF_8, EncodingConstants.CHARSET);
        assertEquals(StandardCharsets.UTF_8.name(), EncodingConstants.CHARSET_NAAM);
        assertEquals(Locale.forLanguageTag("nl-NL"), EncodingConstants.LOCALE);

    }

    @Test(expected = AssertionError.class)
    public void testPrivateConstructor() throws Throwable {
        try {
            final Constructor<?>[] constructors = EncodingConstants.class.getDeclaredConstructors();
            constructors[0].setAccessible(true);
            constructors[0].newInstance((Object[]) null);
            Assert.fail();
        } catch (InvocationTargetException e) {
            Assert.assertTrue(e.getCause() instanceof AssertionError);
            throw e.getCause();
        }
    }
}
