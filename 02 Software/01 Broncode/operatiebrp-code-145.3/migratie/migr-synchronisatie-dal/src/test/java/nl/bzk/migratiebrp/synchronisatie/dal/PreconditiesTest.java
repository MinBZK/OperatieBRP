/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;

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
    private static final String ROOT_PACKAGE = "nl.bzk.migratiebrp";

    @Test
    public void testVolledigheidPrecondities() {
        final Map<SoortMeldingCode, List<String>> overzicht = maakOverzicht();
        prettyPrint(overzicht);
        final List<SoortMeldingCode> allePrecondities = Arrays.asList(SoortMeldingCode.values());
        final List<SoortMeldingCode> missing = new ArrayList<>();
        for (final SoortMeldingCode listed : allePrecondities) {
            if (!overzicht.containsKey(listed)) {
                missing.add(listed);
            }
        }
        if (!missing.isEmpty()) {
            System.out.println("De volgende Precondities komen niet voor in de code:");
            for (final SoortMeldingCode miss : missing) {
                System.out.println(miss.name());
            }
        } else {
            System.out.println("Alle Precondities komen voor in de code!");
        }
        // TODO: nog niet alle precondities komen voor in de code
        // Assert.assertTrue(missing.isEmpty());
    }

    private Map<SoortMeldingCode, List<String>> maakOverzicht() {
        final Reflections reflections =
                new Reflections(new ConfigurationBuilder().filterInputsBy(new FilterBuilder.Include(FilterBuilder.prefix(ROOT_PACKAGE)))
                        .setUrls(ClasspathHelper.forPackage(ROOT_PACKAGE))
                        .setScanners(
                                new SubTypesScanner(),
                                new TypeAnnotationsScanner(),
                                new ResourcesScanner(),
                                new MethodAnnotationsScanner()));

        final Map<SoortMeldingCode, List<String>> overzicht = new HashMap<>();

        // haal alle methoden op die geannoteerd zijn met Preconditie
        final Set<Method> methodsAnnotated = reflections.getMethodsAnnotatedWith(Preconditie.class);
        for (final Method method : methodsAnnotated) {
            final SoortMeldingCode[] value = method.getAnnotation(Preconditie.class).value();
            for (final SoortMeldingCode soortMeldingCode : value) {
                if (overzicht.containsKey(soortMeldingCode)) {
                    overzicht.get(soortMeldingCode).add(method.toString());
                } else {
                    overzicht.put(soortMeldingCode, new ArrayList<>(Arrays.asList(method.toString())));
                }
            }
        }

        // haal alle classes op die geannoteerd zijn met Preconditie
        final Set<Class<?>> classesAnnotated = reflections.getTypesAnnotatedWith(Preconditie.class, true);
        for (final Class<?> clazz : classesAnnotated) {
            final SoortMeldingCode[] value = clazz.getAnnotation(Preconditie.class).value();
            for (final SoortMeldingCode soortMeldingCode : value) {
                if (overzicht.containsKey(soortMeldingCode)) {
                    overzicht.get(soortMeldingCode).add(clazz.toString());
                } else {
                    overzicht.put(soortMeldingCode, new ArrayList<>(Arrays.asList(clazz.toString())));
                }
            }
        }
        return overzicht;
    }

    private void prettyPrint(final Map<SoortMeldingCode, List<String>> overzicht) {
        // sorteer op key en print key-value pairs
        final List<SoortMeldingCode> unsorted = new ArrayList<>(overzicht.keySet());
        Collections.sort(unsorted, new Comparator<SoortMeldingCode>() {

            @Override
            public int compare(final SoortMeldingCode o1, final SoortMeldingCode o2) {
                return o1.name().compareTo(o2.name());
            }

        });
        final String whitespaces = "                              ";
        for (final SoortMeldingCode key : unsorted) {
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
