/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.database.entities.testutils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Entity;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.junit.Assert;
import org.reflections.Reflections;

/**
 * Tester voor getter en setter methoden.
 */
public class GetterSetterTester {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Map<Class<?>, Object> VALUES = new HashMap<>();
    static {
        VALUES.put(Character.class, 'c');
        VALUES.put(Character.TYPE, 'c');
        VALUES.put(String.class, "42");
        VALUES.put(Number.class, 42);
        VALUES.put(Long.class, 1234L);
        VALUES.put(Long.TYPE, 1234L);
        VALUES.put(Integer.class, 42);
        VALUES.put(Integer.TYPE, 42);
        VALUES.put(Short.class, (short) 2);
        VALUES.put(Short.TYPE, (short) 2);
        VALUES.put(Boolean.class, true);
        VALUES.put(Boolean.TYPE, true);
        VALUES.put(Timestamp.class, new Timestamp(System.currentTimeMillis()));
        VALUES.put(Set.class, Collections.emptySet());
        final byte[] byteArray = new byte[] {};
        VALUES.put(byteArray.getClass(), byteArray);
    }

    /**
     * Test entities in package.
     *
     * @param packageName
     *            package
     */
    public void testEntities(final String packageName) throws ReflectiveOperationException {
        final Reflections reflections = new Reflections(packageName);
        final Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Entity.class);
        LOG.info("Test " + classes.size() + " entiteiten in package " + packageName);
        for (final Class<?> clazz : classes) {
            final Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            for (final Constructor<?> constructor : constructors) {
                constructor.setAccessible(true);

                final Object[] arguments = new Object[constructor.getParameterTypes().length];
                for (int i = 0; i < constructor.getParameterTypes().length; i++) {
                    arguments[i] = generateValue(constructor.getParameterTypes()[i]);
                }

                LOG.info("Maak object van klasse " + clazz.getName() + " met " + constructor.getParameterTypes().length + " constructor argumenten");
                final Object object = constructor.newInstance(arguments);
                test(object);
            }

        }
    }

    /**
     * Test het object (op alle mogelijke manieren {@link #bepaalPropertiesObvFields(Object)}).
     *
     * @param objectToTest
     *            object om te testen
     * @throws ReflectiveOperationException
     *             bij fouten
     */
    public void test(final Object objectToTest) throws ReflectiveOperationException {
        LOG.info("Test object van klasse: " + objectToTest.getClass().getName());
        final Set<String> properties = new TreeSet<>();
        properties.addAll(bepaalPropertiesObvFields(objectToTest));

        for (final String property : properties) {
            test(property, objectToTest);
        }
    }

    /**
     * Bepaal properties obv fields in klassen.
     *
     * @param objectToTest
     *            objcet om te testen
     * @return lisjt van properties
     */
    public Set<String> bepaalPropertiesObvFields(final Object objectToTest) {
        final Set<String> result = new TreeSet<>();

        Class<?> clazz = objectToTest.getClass();
        while (clazz != null) {
            for (final Field field : clazz.getDeclaredFields()) {
                result.add(field.getName());
            }

            clazz = clazz.getSuperclass();
        }

        return result;
    }

    /**
     * Test gebaseerd op property naam (setProperty, getProperty/isProperty, property).
     *
     * @param property
     *            property naam
     * @param objectToTest
     *            object om te testen
     * @throws ReflectiveOperationException
     *             bij fouten
     */
    public void test(final String property, final Object objectToTest) throws ReflectiveOperationException {
        LOG.info("Test property " + property + " object van klasse: " + objectToTest.getClass().getName());
        final Field field = findField(objectToTest.getClass(), property);
        final Method setter = findMethod(objectToTest.getClass(), "set" + Character.toUpperCase(property.charAt(0)) + property.substring(1), 1);
        Method getter = findMethod(objectToTest.getClass(), "get" + Character.toUpperCase(property.charAt(0)) + property.substring(1), 0);
        if (getter == null) {
            getter = findMethod(objectToTest.getClass(), "is" + Character.toUpperCase(property.charAt(0)) + property.substring(1), 0);
        }

        if (field != null) {
            field.setAccessible(true);
            if (setter != null && field.getType().isAssignableFrom(setter.getParameterTypes()[0])) {
                setter.setAccessible(true);

                final Object value = generateValue(field.getType());
                try {
                    setter.invoke(objectToTest, value);
                } catch (final Exception e) {
                    LOG.warn("Kan property "
                             + property
                             + " ("
                             + field.getType().getName()
                             + ") op object ("
                             + objectToTest.getClass().getName()
                             + ") niet setten met "
                             + value
                             + " ("
                             + value.getClass().getName()
                             + ")", e);
                    throw e;
                }

                final Object result = field.get(objectToTest);
                checkEquals(value, result);
            }

            if (getter != null && field.getType().isAssignableFrom(getter.getReturnType())) {
                getter.setAccessible(true);
                final Object value = field.get(objectToTest);

                final Object result = getter.invoke(objectToTest);

                checkEquals(value, result);
            }
        }

        if (setter != null && getter != null && getter.getReturnType().isAssignableFrom(setter.getParameterTypes()[0])) {
            setter.setAccessible(true);
            final Object value = generateValue(setter.getParameterTypes()[0]);
            setter.invoke(objectToTest, value);

            getter.setAccessible(true);
            final Object result = getter.invoke(objectToTest);
            checkEquals(value, result);
        }
    }

    private void checkEquals(final Object value, final Object result) {
        if (value == null) {
            Assert.assertNull(result);
        } else if (value.getClass().isArray()) {
            if (value.getClass().getComponentType().equals(Byte.TYPE)) {
                Assert.assertArrayEquals((byte[]) value, (byte[]) result);
            } else {
                Assert.assertArrayEquals((Object[]) value, (Object[]) result);
            }
        } else {
            Assert.assertEquals(value, result);
        }
    }

    private Field findField(final Class<?> clazz, final String property) {
        try {
            return clazz.getDeclaredField(property);
        } catch (final NoSuchFieldException e) {
            final Class<?> superClazz = clazz.getSuperclass();
            return superClazz == null ? null : findField(superClazz, property);
        }
    }

    private Method findMethod(final Class<?> clazz, final String name, final int numberOfArguments) {
        for (final Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(name) && method.getParameterTypes().length == numberOfArguments) {
                return method;
            }
        }

        final Class<?> superClazz = clazz.getSuperclass();
        return superClazz == null ? null : findMethod(superClazz, name, numberOfArguments);
    }

    private Object generateValue(final Class<?> type) {
        if (VALUES.containsKey(type)) {
            return VALUES.get(type);
        } else if (type.isEnum()) {
            final Object[] values = type.getEnumConstants();
            return values[0];
        } else {
            try {
                final Constructor<?> constructor = type.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            } catch (final ReflectiveOperationException e) {
            }

            throw new IllegalArgumentException("Kan geen waarde genereren voor type " + type.getName());
        }
    }
}
