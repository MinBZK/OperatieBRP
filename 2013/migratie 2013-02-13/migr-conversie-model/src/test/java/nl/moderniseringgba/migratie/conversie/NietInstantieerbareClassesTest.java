/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import nl.moderniseringgba.migratie.conversie.serialize.PersoonslijstDecoder;
import nl.moderniseringgba.migratie.conversie.serialize.PersoonslijstEncoder;

import org.junit.Test;

/**
 * Deze class test dat alles classes in de lijst niet via reflectie kunnen worden geinstantieerd.
 * 
 */
public class NietInstantieerbareClassesTest {

    private static final Class<?>[] NIET_INSTANTIEERBARE_CLASSES = new Class[] { PersoonslijstEncoder.class,
            PersoonslijstDecoder.class, };

    @Test
    public void testClasses() throws InstantiationException, IllegalAccessException, NoSuchMethodException {
        for (final Class<?> clazz : NIET_INSTANTIEERBARE_CLASSES) {
            final Constructor<?> defaultConstructor = clazz.getDeclaredConstructor();
            defaultConstructor.setAccessible(true);
            try {
                defaultConstructor.newInstance();
                fail(String.format("Expected InvocationTargetException on calling constructor of class: %s", clazz));
            } catch (final InvocationTargetException ite) {
                assertTrue(ite.getCause() instanceof AssertionError);
            }
        }
    }

}
