/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.generateplugin.mojo;

import java.text.Normalizer;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Utiliteits methoden.
 */
public final class Utils {

    static final String INDENTATION = "    ";
    private static final String LITERAL = "literal:";

    private Utils() {
        // Niet instantieerbaar
    }

    /**
     * Converteer een naam naar een java enumeratie naam.
     * @param javaNameBase naam
     * @return enumeratie naam
     */
    public static String convertToJavaEnumName(final String javaNameBase) {
        if (javaNameBase.startsWith(LITERAL)) {
            return StringEscapeUtils.unescapeJava(javaNameBase.replaceAll(String.format("^%s", LITERAL), ""));
        } else {
            String result = javaNameBase;

            // Unaccent
            result = Normalizer.normalize(result, Normalizer.Form.NFD);
            // Replace whitespace with underscore
            result = result.replaceAll("(\\s|-)", "_");
            // Uppercase
            result = result.toUpperCase();
            // Remove unsupported characters
            result = result.replaceAll("[^A-Z0-9_]", "");
            // Remove duplicate seperators
            result = result.replaceAll("_{2,}", "_");

            return result;
        }
    }
}
