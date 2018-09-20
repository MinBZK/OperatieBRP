/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.text.WordUtils;


public class GeneratorUtils {

    private static final List<String> stamGegevensZonderTuples = new ArrayList<String>();

    public static final String INVALID_JAVA_CHARACTERS = ",?/";

    static {
        stamGegevensZonderTuples.add("Database object");
        stamGegevensZonderTuples.add("Element");
        stamGegevensZonderTuples.add("Regelimplementatie");
        stamGegevensZonderTuples.add("Regel");
    }

    /**
     * Bekijk of dit stamgegeven een gegeven zonder tuples is.
     *
     * @param stamGegeven het stam gegeven
     * @return true, als het stamgegeven voorkomt in de lijst van stam gegevens zonder tuples
     */
    public static boolean isStamGegevensZonderTuples(String stamGegeven) {
        return stamGegevensZonderTuples.contains(stamGegeven);
    }

    /**
     * Hoofdletter maken van het eerste karakter van de input string.
     *
     * @param input the input
     * @return the string
     */
    public static String upperTheFirstCharacter(String input) {
        return StringUtils.capitalize(input);
    }

    /**
     * Kleine letter maken van het eerste karakter van de input string.
     *
     * @param input the input
     * @return the string
     */
    public static String lowerTheFirstCharacter(String input) {
        return StringUtils.uncapitalize(input);
    }

    /**
     * Convert the package name to a relative path name that can be used to
     * create a file. The System property <code>file.separator</code> is used.
     *
     * @param packageName The package name to convert.
     * @return The converted path name.
     */
    public static String convertPackageToPath(String packageName) {
        if (packageName == null || packageName.trim().equals("")) {
            return "";
        }
        String delimiter = JavaGeneratorConstants.FILE_DELIMITER.getValue();
        StringBuffer buffer = new StringBuffer(packageName.length());
        StringTokenizer tokenizer = new StringTokenizer(packageName, ".;");
        while (tokenizer.hasMoreTokens()) {
            buffer.append(delimiter);
            buffer.append(tokenizer.nextToken());
        }
        return buffer.toString();
    }

    /**
     * Normaliseer de input string, dwz vervang alle diacrieten met het teken zonder diacriet.
     *
     * @param input de invoer string
     * @return de string zonder diacrieten
     */
    public static String normalise(String input) {
        return  StringUtils.stripAccents(input);

    }

    /**
     * Converteer de input string naar een string die geen illegale tekens meer bevat om gebruikt te worden in een java
     * variable, methode, enum waarde enz.
     *
     * @param input de tekst waar een valide enum naam van moet worden gemaakt.
     * @return de gefilterde string
     */
    public static String toValidEnumName(String input) {
        return cleanUpInvalidJavaCharacters(input).replace(' ', '_');
    }

    /**
     * Converteer de input string naar een string die geen illegale tekens meer bevat om gebruikt te worden in een java
     * variable, methode, enum waarde enz.
     *
     * @param input de tekst die als java variabele naam gebruikt moet worden
     * @return de gefilterde string
     */
    public static String toValidJavaVariableName(String input) {
        return lowerTheFirstCharacter(stripSpaces(camelCase(cleanUpInvalidJavaCharacters(input))));
    }

    public static String stripSpaces(String input) {
        return StringUtils.replace(input," ","");
    }

    /**
     * Clean up invalide java karakters.
     *
     * @param input de tekst die valide java tekst moet worden
     * @return de gefilterde string
     */
    public static String cleanUpInvalidJavaCharacters(String input) {

        return StringUtils.replaceChars(input, INVALID_JAVA_CHARACTERS,"");
    }

    /**
     * Converteer de tekst naar CamelCase om aan de coding standaarden te voldoen. Vervangt spaties en maakt hoofdletters van nieuwe woorden
     *
     * @param input de tekst waar camelcase op moet worden toegepasts
     * @return de gefilterde string
     */
    public static String camelCase(String input) {
        return WordUtils.capitalize(input);
    }

    public static String toUpperCamel(final String input) {
        String upper = input;
        if (!StringUtils.isBlank(input)) {
            boolean isHoofd = false;
            boolean isVorigeHoofd = false;
            for (int i = 0; i < input.length(); i++) {
                //Stoppen als je een punt tegen komt!
                if (upper.charAt(i) == '.') {
                    break;
                }
                isHoofd = CharUtils.isAsciiAlphaUpper(upper.charAt(i)) || CharUtils.isAsciiNumeric(upper.charAt(i));
                if (i == upper.length() - 1) {
                    return StringUtils.capitalize(upper.toLowerCase());
                }
                if (i > 0 && !isVorigeHoofd && isHoofd) {
                    return StringUtils.capitalize(upper.substring(0, i).toLowerCase())
                        + toUpperCamel(upper.substring(i));
                }

                if (i > 1 && isVorigeHoofd && !isHoofd) {
                    return StringUtils.capitalize(upper.substring(0, i - 1).toLowerCase())
                        + toUpperCamel(upper.substring(i - 1));
                }


                isVorigeHoofd = isHoofd;
            }
        }
        return upper;
    }
}
