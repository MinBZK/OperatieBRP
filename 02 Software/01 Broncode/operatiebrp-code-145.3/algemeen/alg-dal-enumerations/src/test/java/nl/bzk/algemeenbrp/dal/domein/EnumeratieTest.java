/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Enumeratie;
import org.junit.Test;
import org.reflections.Reflections;

/**
 * Unittest voor alle enumeraties die de {@link Enumeratie} interface implementeren.
 */
public class EnumeratieTest {

    @Test
    public void testEnumeraties() throws InvocationTargetException, IllegalAccessException {
        enumeratieTester("nl.bzk.algemeenbrp.dal.domein.brp.enums");
    }

    @Test(expected = IllegalStateException.class)
    public void testEnumeratiesVerkeerdPackage() throws InvocationTargetException, IllegalAccessException {
        enumeratieTester("nl.bzk.algemeen.dal.domein.brp.kern.enums");
    }

    private void enumeratieTester(final String packageName) throws InvocationTargetException, IllegalAccessException {
        final Reflections reflections = new Reflections(packageName);
        final Set<Class<? extends Enum>> subTypesOf = reflections.getSubTypesOf(Enum.class);

        if (subTypesOf.isEmpty()) {
            throw new IllegalStateException("Geen enumeraties gevonden");
        }

        for (final Class<? extends Enum> enumClass : subTypesOf) {
            final Enum[] enumConstants = enumClass.getEnumConstants();
            for (final Enum enumConstant : enumConstants) {
                if (enumConstant instanceof Enumeratie) {
                    final Enumeratie enumeratie = (Enumeratie) enumConstant;
                    if (enumeratie.heeftCode()) {
                        assertNotNull(enumeratie.getCode());
                    } else {
                        try {
                            assertNull(enumeratie.getCode());
                        } catch (final UnsupportedOperationException e) {
                            assertFalse(e.getMessage().isEmpty());
                        }
                    }

                    // Enumeratie heeft of naam, of deze gooit een exceptie
                    try {
                        assertNotNull(
                            "Waarde " + enumConstant + " van enumeratie " + enumClass.getSimpleName() + " heeft geen naam", enumeratie.getNaam());
                    } catch (final UnsupportedOperationException e) {
                        assertFalse(e.getMessage().isEmpty());
                    }

                    assertTrue(
                        String.format("Enumeratie %s uit class %s heeft id gelijk aan 0", enumConstant.name(), enumClass.getCanonicalName()),
                        enumeratie.getId() != 0);

                    // Test de constructie van de Enum via de static mehodes
                    for (final Method staticMethod : verzamelParseMethods(enumeratie)) {
                        final Class<?>[] parameterTypes = staticMethod.getParameterTypes();
                        if (parameterTypes.length == 1) {
                            if (parameterTypes[0].isAssignableFrom(Short.class)) {
                                assertEquals(enumeratie, staticMethod.invoke(enumeratie, enumeratie.getId()));
                            } else if (parameterTypes[0].isAssignableFrom(String.class)) {
                                if (staticMethod.getName().contains("Code")) {
                                    assertEquals(enumeratie, staticMethod.invoke(enumeratie, enumeratie.getCode()));
                                } else if (staticMethod.getName().contains("Naam")) {
                                    assertEquals(enumeratie, staticMethod.invoke(enumeratie, enumeratie.getNaam()));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private List<Method> verzamelParseMethods(final Enumeratie enumeratie) {
        final List<Method> parseMethods = new ArrayList<>();
        for (final Method method : enumeratie.getClass().getDeclaredMethods()) {
            if (Modifier.isStatic(method.getModifiers()) && method.getName().startsWith("parse")) {
                parseMethods.add(method);
            }
        }
        return parseMethods;
    }
}
