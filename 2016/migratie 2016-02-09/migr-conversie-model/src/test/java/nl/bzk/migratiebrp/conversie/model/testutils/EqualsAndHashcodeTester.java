/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.testutils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.Assert;

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
     * @throws NoSuchMethodException,IllegalAccessException
     */
    public static void testEqualsHashcodeAndToString(final Object object, final Object equal, final Object notEqual) throws NoSuchMethodException,
        IllegalAccessException
    {
        EqualsAndHashcodeTester.testHashcodeEqualsEnToString(object, equal, notEqual, false);
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
     * @throws NoSuchMethodException, IllegalAccessException
     */
    public static void testEqualsHashcodeAndToStringBeperkt(final Object object, final Object equal, final Object notEqual) throws NoSuchMethodException,
        IllegalAccessException
    {
        EqualsAndHashcodeTester.testHashcodeEqualsEnToString(object, equal, notEqual, true);
    }

    /**
     * @param object object
     * @param equal Een object dat gelijk moet zijn aan object, maar niet dezelfde reference is (object!=equal)
     * @param notEqual Een object dat ongelijk is aan object, of null als er geen object instantie te maken is ongelijk aan            object
     * @param beperkt boolean
     *            als de equals methode beperkt moet worden uitgevoerd omdat deze niet standaard is.
     * @throws NoSuchMethodException, IllegalAccessException
     */
    private static void testHashcodeEqualsEnToString(final Object object, final Object equal, final Object notEqual, final boolean beperkt)
        throws NoSuchMethodException, IllegalAccessException
    {

        Assert.assertEquals(object.hashCode(), object.hashCode());
        Assert.assertEquals(object.hashCode(), equal.hashCode());

        EqualsAndHashcodeTester.testString(object, equal, notEqual);

        Assert.assertTrue(object.equals(object));
        Assert.assertTrue(object.equals(equal));
        Assert.assertTrue(equal.equals(object));

        if (notEqual != null) {
            Assert.assertFalse(object.equals(notEqual));
        }
        Assert.assertNotNull(object);
        Assert.assertFalse(object.equals(new Object()));

        if (!beperkt) {
            while (EqualsAndHashcodeTester.maakVolgendFieldNull(object)) {
                Assert.assertFalse(object.equals(equal));
                Assert.assertFalse(equal.equals(object));
                EqualsAndHashcodeTester.maakVolgendFieldNull(equal);
                Assert.assertTrue(object.equals(equal));
                Assert.assertTrue(equal.equals(object));
            }
        } else if (EqualsAndHashcodeTester.maakAlleFieldsNull(object)) {
            Assert.assertFalse(object.equals(equal));
            Assert.assertFalse(equal.equals(object));
            EqualsAndHashcodeTester.maakAlleFieldsNull(equal);
            Assert.assertTrue(object.equals(equal));
            Assert.assertTrue(equal.equals(object));
        }

        Assert.assertEquals(object.hashCode(), object.hashCode());
        Assert.assertEquals(object.hashCode(), equal.hashCode());
    }

    /**
     * Test of gelijke objecten dezelfde String representatie hebben, of ongelijke objecten een afwijkende String
     * representatie hebben, en of de toString methode is overridden in de corresponderende klassen.
     * 
     * @param object Een object
     * @param equal Een object dat gelijk moet zijn aan object, maar niet dezelfde reference is (object!=equal)
     * @param notEqual Een object dat ongelijk is aan object, of null als er geen object instantie te maken is ongelijk aan            object
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    private static void testString(final Object object, final Object equal, final Object notEqual) throws NoSuchMethodException {
        Assert.assertEquals(object.toString(), equal.toString());
        if (notEqual != null) {
            Assert.assertNotSame(object.toString(), notEqual.toString());
        }
    }

    /**
     * Maak het volgende field null
     * 
     * @return true als er een field op null is gezet
     */
    private static boolean maakVolgendFieldNull(final Object object) throws IllegalAccessException {
        final boolean modified = false;
        final Field[] objectFields = object.getClass().getDeclaredFields();
        for (final Field field : objectFields) {
            field.setAccessible(true);
            if (!field.isEnumConstant() && !Modifier.isStatic(field.getModifiers()) && !field.getType().isPrimitive() && field.get(object) != null) {
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
    private static boolean maakAlleFieldsNull(final Object object) throws IllegalAccessException {
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
