/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.moderniseringgba.migratie.Definitie;
import nl.moderniseringgba.migratie.Definities;

import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class DefinitiesTest {
    private static final String ROOT_PACKAGE = "nl.moderniseringgba.migratie";

    @Test
    public void testVolledigheidDefinities() {
        final Map<Definities, List<String>> overzicht = maakOverzicht();
        prettyPrint(overzicht);
        final List<Definities> alleDefinities = Arrays.asList(Definities.values());
        final List<Definities> missing = new ArrayList<Definities>();
        for (final Definities listed : alleDefinities) {
            if (!overzicht.containsKey(listed)) {
                missing.add(listed);
            }
        }
        if (!missing.isEmpty()) {
            System.out.println("De volgende definities komen niet voor in de code:");
            for (final Definities miss : missing) {
                System.out.println(miss.getCode() + ": " + miss.getOmschrijving());
            }
        } else {
            System.out.println("Alle definities komen voor in de code!");
        }
        // TODO: nog niet alle definities komen voor in de code
        // assertTrue(missing.isEmpty());
    }

    /**
     * Test die nagaat of de codes overeenkomen met de enum naam. (Geen typefouten)
     */
    @Test
    public void testEnumNamen() {
        for (final Definities def : Definities.values()) {
            final String naamZonderKoppel = def.name().replaceAll("_", "").toUpperCase();
            final String codeZonderKoppel = def.getCode().replaceAll("-", "").toUpperCase();
            assertEquals(naamZonderKoppel, codeZonderKoppel);
        }
    }

    /**
     * Test die nagaat of de beschrijvingen zijn gevuld.
     */
    @Test
    public void testBeschrijvingenGevuld() {
        for (final Definities def : Definities.values()) {
            assertNotNull(def.getOmschrijving());
            assertFalse("Lege omschrijving voor " + def, "".equals(def.getOmschrijving().trim()));
        }
    }

    private Map<Definities, List<String>> maakOverzicht() {
        final Reflections reflections =
                new Reflections(new ConfigurationBuilder()
                        .filterInputsBy(new FilterBuilder.Include(FilterBuilder.prefix(ROOT_PACKAGE)))
                        .setUrls(ClasspathHelper.forPackage(ROOT_PACKAGE))
                        .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner(), new ResourcesScanner(),
                                new MethodAnnotationsScanner()));

        final Map<Definities, List<String>> overzicht = new HashMap<Definities, List<String>>();

        // haal alle methoden op die geannoteerd zijn met Requirement
        final Set<Method> methodsAnnotated = reflections.getMethodsAnnotatedWith(Definitie.class);
        for (final Method method : methodsAnnotated) {
            final Definities[] value = method.getAnnotation(Definitie.class).value();
            for (final Definities definitie : value) {
                if (overzicht.containsKey(definitie)) {
                    overzicht.get(definitie).add(method.toString());
                } else {
                    overzicht.put(definitie, new ArrayList<String>(Arrays.asList(method.toString())));
                }
            }
        }

        // haal alle classes op die geannoteerd zijn met Requirement
        final Set<Class<?>> classesAnnotated = reflections.getTypesAnnotatedWith(Definitie.class, true);
        for (final Class<?> clazz : classesAnnotated) {
            final Definities[] value = clazz.getAnnotation(Definitie.class).value();
            for (final Definities definitie : value) {
                if (overzicht.containsKey(definitie)) {
                    overzicht.get(definitie).add(clazz.toString());
                } else {
                    overzicht.put(definitie, new ArrayList<String>(Arrays.asList(clazz.toString())));
                }
            }
        }
        return overzicht;
    }

    private void prettyPrint(final Map<Definities, List<String>> overzicht) {
        // sorteer op key en print key-value pairs
        final List<Definities> unsorted = new ArrayList<Definities>(overzicht.keySet());
        Collections.sort(unsorted, new Comparator<Definities>() {

            @Override
            public int compare(final Definities o1, final Definities o2) {
                return o1.name().compareTo(o2.name());
            }

        });
        final String whitespaces = "                              ";
        for (final Definities key : unsorted) {
            final List<String> list = overzicht.get(key);
            final String white = whitespaces.substring(0, 30 - key.name().length());
            boolean keyPrinted = false;
            for (final String value : list) {
                if (!keyPrinted) {
                    System.out.println(key.getCode() + white + value);
                    keyPrinted = true;
                } else {
                    System.out.println(whitespaces.substring(0, key.getCode().length()) + white + value);
                }
            }
        }
    }
}
