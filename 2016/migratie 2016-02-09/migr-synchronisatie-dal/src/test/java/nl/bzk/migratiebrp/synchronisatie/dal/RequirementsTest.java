/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal;

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
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class RequirementsTest {
    private static final String ROOT_PACKAGE = "nl.bzk.migratiebrp";

    @Test
    public void testVolledigheidRequirements() {
        final Map<Requirements, List<String>> overzicht = maakOverzicht();
        prettyPrint(overzicht);
        final List<Requirements> allRequirements = Arrays.asList(Requirements.values());
        final List<Requirements> missing = new ArrayList<>();
        for (final Requirements listed : allRequirements) {
            if (!overzicht.containsKey(listed)) {
                missing.add(listed);
            }
        }
        if (!missing.isEmpty()) {
            System.out.println("The following requirements are not implemented (or annotated):");
            for (final Requirements miss : missing) {
                System.out.println(miss);
            }
        } else {
            System.out.println("All listed requirements are implemented");
        }
        // TODO: nog niet alle requirements komen voor in de code
        // assertTrue(missing.isEmpty());
    }

    /**
     * Test die nagaat of de codes overeenkomen met de enum naam. (Geen typefouten)
     */
    @Test
    public void testEnumNamen() {
        for (final Requirements req : Requirements.values()) {
            final String naamZonderKoppel = req.name().replaceAll("_", "").toUpperCase();
            final String codeZonderKoppel = req.getCode().replaceAll("-", "").toUpperCase();
            assertEquals(naamZonderKoppel, codeZonderKoppel);
        }
    }

    /**
     * Test die nagaat of de beschrijvingen zijn gevuld.
     */
    @Test
    public void testBeschrijvingenGevuld() {
        for (final Requirements req : Requirements.values()) {
            assertNotNull(req.getOmschrijving());
            assertFalse("Lege omschrijving voor " + req, "".equals(req.getOmschrijving().trim()));
        }
    }

    private Map<Requirements, List<String>> maakOverzicht() {
        final Reflections reflections =
                new Reflections(new ConfigurationBuilder().filterInputsBy(new FilterBuilder.Include(FilterBuilder.prefix(ROOT_PACKAGE)))
                                                          .setUrls(ClasspathHelper.forPackage(ROOT_PACKAGE))
                                                          .setScanners(
                                                              new SubTypesScanner(),
                                                              new TypeAnnotationsScanner(),
                                                              new ResourcesScanner(),
                                                              new MethodAnnotationsScanner()));

        final Map<Requirements, List<String>> overzicht = new HashMap<>();

        // haal alle methoden op die geannoteerd zijn met Requirement
        final Set<Method> methodsAnnotated = reflections.getMethodsAnnotatedWith(Requirement.class);
        for (final Method method : methodsAnnotated) {
            final Requirements[] value = method.getAnnotation(Requirement.class).value();
            for (final Requirements requirement : value) {
                if (overzicht.containsKey(requirement)) {
                    overzicht.get(requirement).add(method.toString());
                } else {
                    overzicht.put(requirement, new ArrayList<>(Arrays.asList(method.toString())));
                }
            }
        }

        // haal alle classes op die geannoteerd zijn met Requirement
        final Set<Class<?>> classesAnnotated = reflections.getTypesAnnotatedWith(Requirement.class, true);
        for (final Class<?> clazz : classesAnnotated) {
            final Requirements[] value = clazz.getAnnotation(Requirement.class).value();
            for (final Requirements requirement : value) {
                if (overzicht.containsKey(requirement)) {
                    overzicht.get(requirement).add(clazz.toString());
                } else {
                    overzicht.put(requirement, new ArrayList<>(Arrays.asList(clazz.toString())));
                }
            }
        }
        return overzicht;
    }

    private void prettyPrint(final Map<Requirements, List<String>> overzicht) {
        // sorteer op key en print key-value pairs
        final List<Requirements> unsorted = new ArrayList<>(overzicht.keySet());
        Collections.sort(unsorted, new Comparator<Requirements>() {

            @Override
            public int compare(final Requirements o1, final Requirements o2) {
                return o1.name().compareTo(o2.name());
            }

        });
        final String whitespaces = "                              ";
        for (final Requirements key : unsorted) {
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
