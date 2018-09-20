/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.testutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class EqualsAndHashcodeTester {

    /**
     * Explicit private constructor.
     */
    private EqualsAndHashcodeTester() {
    }

    /**
     * Test de equals, hashcode en toString methoden voor de opgegeven object instanties.
     * 
     * @param object
     *            Een object
     * @param equal
     *            Een object dat gelijk moet zijn aan object, maar niet dezelfde reference is (object!=equal)
     * @param notEqual
     *            Een object dat ongelijk is aan object, of null als er geen object instantie te maken is ongelijk aan
     *            object
     * 
     * @throws Exception
     */
    public static void testEqualsHashcodeAndToString(final Object object, final Object equal, final Object notEqual)
            throws Exception {
        testHashcodeEqualsEnToString(object, equal, notEqual, false);
    }

    /**
     * Test de equals, hashcode en toString methoden voor de opgegeven object instanties. De equals methode wordt iets
     * beperkt getest. Gebruik deze methode als de equals() afwijkend gedrag vertoont.
     * 
     * @param object
     *            Een object
     * @param equal
     *            Een object dat gelijk moet zijn aan object, maar niet dezelfde reference is (object!=equal)
     * @param notEqual
     *            Een object dat ongelijk is aan object, of null als er geen object instantie te maken is ongelijk aan
     *            object
     * 
     * @throws Exception
     */
    public static void testEqualsHashcodeAndToStringBeperkt(
            final Object object,
            final Object equal,
            final Object notEqual) throws Exception {
        testHashcodeEqualsEnToString(object, equal, notEqual, true);
    }

    /**
     * @param object
     * @param equal
     * @param notEqual
     * @param beperkt
     *            als de equals methode beperkt moet worden uitgevoerd omdat deze niet standaard is.
     * @throws Exception
     */
    private static void testHashcodeEqualsEnToString(
            final Object object,
            final Object equal,
            final Object notEqual,
            final boolean beperkt) throws Exception {
        assertEquals(object.hashCode(), object.hashCode());
        assertEquals(object.hashCode(), equal.hashCode());

        testString(object, equal, notEqual);

        assertTrue(object.equals(object));
        assertTrue(object.equals(equal));
        assertTrue(equal.equals(object));

        if (notEqual != null) {
            assertFalse(object.equals(notEqual));
        }
        assertFalse(object.equals(null));
        assertFalse(object.equals(new Object()));

        if (!beperkt) {
            while (maakVolgendFieldNull(object)) {
                assertFalse(object.equals(equal));
                assertFalse(equal.equals(object));
                maakVolgendFieldNull(equal);
                assertTrue(object.equals(equal));
                assertTrue(equal.equals(object));
            }
        } else if (maakAlleFieldsNull(object)) {
            assertFalse(object.equals(equal));
            assertFalse(equal.equals(object));
            maakAlleFieldsNull(equal);
            assertTrue(object.equals(equal));
            assertTrue(equal.equals(object));
        }

        assertEquals(object.hashCode(), object.hashCode());
        assertEquals(object.hashCode(), equal.hashCode());
    }

    /**
     * Test of gelijke objecten dezelfde String representatie hebben, of ongelijke objecten een afwijkende String
     * representatie hebben, en of de toString methode is overridden in de corresponderende klassen.
     * 
     * @param object
     * @param equal
     * @param notEqual
     *            of null
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    private static void testString(final Object object, final Object equal, final Object notEqual)
            throws NoSuchMethodException {
        assertEquals(object.toString(), equal.toString());
        if (notEqual != null) {
            assertNotSame(object.toString(), notEqual.toString());
        }
        // is toString overridden?
        assertEquals(object.getClass(), object.getClass().getMethod("toString").getDeclaringClass());
    }

    /**
     * Maak het volgende field null
     * 
     * @return true als er een field op null is gezet
     */
    private static boolean maakVolgendFieldNull(final Object object) throws Exception {
        final boolean modified = false;
        final Field[] objectFields = object.getClass().getDeclaredFields();
        for (final Field field : objectFields) {
            field.setAccessible(true);
            if (!field.isEnumConstant() && !Modifier.isStatic(field.getModifiers()) && !field.getType().isPrimitive()
                    && field.get(object) != null) {
                field.set(object, null);
                return true;
            }
        }
        return modified;
    }

    /**
     * Maak alle fields null.
     * 
     * @return true als er een field is omgezet naar null, false als alle velden al null waren of niet konden worden
     *         omgezet
     */
    private static boolean maakAlleFieldsNull(final Object object) throws Exception {
        boolean modified = false;
        final Field[] objectFields = object.getClass().getDeclaredFields();
        for (final Field field : objectFields) {
            field.setAccessible(true);
            if (!field.isEnumConstant() && !Modifier.isStatic(field.getModifiers()) && !field.getType().isPrimitive()) {
                field.set(object, null);
                modified = true;
            }
        }
        return modified;
    }
}
