/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.util;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Utilities.
 */
public final class Utils {

    private static final int GET_SET_PREFIX_LENGTH = 3;

    private Utils() {
        // Niet instantieerbaar
    }

    /**
     * Bepaal naam (gebruik gegeven naam en geef anders default obv gegeven klasse).
     * @param name naam, kan null (of leeg) zijn
     * @param clazz klasse
     * @return naam
     */
    public static String name(final String name, final Class<?> clazz) {
        if (name != null && !"".equals(name)) {
            return name;
        } else {
            return lowerFirstCharacter(clazz.getSimpleName());
        }
    }

    /**
     * Bepaal naam (gebruik gegeven naam en geef anders default obv gegeven element).
     * @param name naam, kan null (of leeg) zijn
     * @param element element (Field, Method of Parameter)
     * @return naam
     */
    public static String name(final String name, final AnnotatedElement element) {
        final String result;
        if (name != null && !"".equals(name)) {
            result = name;
        } else {
            if (element instanceof Field) {
                result = ((Field) element).getName();
            } else if (element instanceof Method) {
                result = lowerFirstCharacter(removeGetSet(((Method) element).getName()));
            } else if (element instanceof Parameter) {
                result = ((Parameter) element).getName();
            } else {
                throw new IllegalArgumentException("Onbekend soort element " + element);
            }
        }
        return result;
    }

    private static String removeGetSet(final String name) {
        if (name.startsWith("get") || name.startsWith("set")) {
            return name.substring(GET_SET_PREFIX_LENGTH);
        } else {
            return name;
        }
    }

    private static String lowerFirstCharacter(final String value) {
        return Character.toLowerCase(value.charAt(0)) + value.substring(1);
    }

}
