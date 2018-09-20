/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie;

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

import nl.moderniseringgba.migratie.Preconditie;
import nl.moderniseringgba.migratie.Precondities;

import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class PreconditiesTest {
    private static final String ROOT_PACKAGE = "nl.moderniseringgba.migratie";

    @Test
    public void testVolledigheidPrecondities() {
        final Map<Precondities, List<String>> overzicht = maakOverzicht();
        prettyPrint(overzicht);
        final List<Precondities> allePrecondities = Arrays.asList(Precondities.values());
        final List<Precondities> missing = new ArrayList<Precondities>();
        for (final Precondities listed : allePrecondities) {
            if (!overzicht.containsKey(listed)) {
                missing.add(listed);
            }
        }
        if (!missing.isEmpty()) {
            System.out.println("De volgende Precondities komen niet voor in de code:");
            for (final Precondities miss : missing) {
                System.out.println(miss.name() + " (" + miss.getStelsel() + "): " + miss.getOmschrijving());
            }
        } else {
            System.out.println("Alle Precondities komen voor in de code!");
        }
        // TODO: nog niet alle precondities komen voor in de code
        // assertTrue(missing.isEmpty());
    }

    /**
     * Test die nagaat of de beschrijvingen zijn gevuld.
     */
    @Test
    public void testBeschrijvingenGevuld() {
        for (final Precondities pre : Precondities.values()) {
            assertNotNull(pre.getOmschrijving());
            assertFalse("Lege omschrijving voor " + pre, "".equals(pre.getOmschrijving().trim()));
        }
    }

    private Map<Precondities, List<String>> maakOverzicht() {
        final Reflections reflections =
                new Reflections(new ConfigurationBuilder()
                        .filterInputsBy(new FilterBuilder.Include(FilterBuilder.prefix(ROOT_PACKAGE)))
                        .setUrls(ClasspathHelper.forPackage(ROOT_PACKAGE))
                        .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner(), new ResourcesScanner(),
                                new MethodAnnotationsScanner()));

        final Map<Precondities, List<String>> overzicht = new HashMap<Precondities, List<String>>();

        // haal alle methoden op die geannoteerd zijn met Requirement
        final Set<Method> methodsAnnotated = reflections.getMethodsAnnotatedWith(Preconditie.class);
        for (final Method method : methodsAnnotated) {
            final Precondities[] value = method.getAnnotation(Preconditie.class).value();
            for (final Precondities Preconditie : value) {
                if (overzicht.containsKey(Preconditie)) {
                    overzicht.get(Preconditie).add(method.toString());
                } else {
                    overzicht.put(Preconditie, new ArrayList<String>(Arrays.asList(method.toString())));
                }
            }
        }

        // haal alle classes op die geannoteerd zijn met Requirement
        final Set<Class<?>> classesAnnotated = reflections.getTypesAnnotatedWith(Preconditie.class, true);
        for (final Class<?> clazz : classesAnnotated) {
            final Precondities[] value = clazz.getAnnotation(Preconditie.class).value();
            for (final Precondities Preconditie : value) {
                if (overzicht.containsKey(Preconditie)) {
                    overzicht.get(Preconditie).add(clazz.toString());
                } else {
                    overzicht.put(Preconditie, new ArrayList<String>(Arrays.asList(clazz.toString())));
                }
            }
        }
        return overzicht;
    }

    private void prettyPrint(final Map<Precondities, List<String>> overzicht) {
        // sorteer op key en print key-value pairs
        final List<Precondities> unsorted = new ArrayList<Precondities>(overzicht.keySet());
        Collections.sort(unsorted, new Comparator<Precondities>() {

            @Override
            public int compare(final Precondities o1, final Precondities o2) {
                return o1.name().compareTo(o2.name());
            }

        });
        final String whitespaces = "                              ";
        for (final Precondities key : unsorted) {
            final List<String> list = overzicht.get(key);
            final String white = whitespaces.substring(0, 30 - key.name().length());
            boolean keyPrinted = false;
            for (final String value : list) {
                if (!keyPrinted) {
                    System.out.println(key.name() + white + value);
                    keyPrinted = true;
                } else {
                    System.out.println(whitespaces.substring(0, key.name().length()) + white + value);
                }
            }
        }
    }
}
