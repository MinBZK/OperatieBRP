/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.util;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.java.model.AbstractJavaType;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.BasisType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

/** Generatie utility class specifiek voor Java generatie. */
public final class JavaGeneratieUtil {

    /** Maximale lengte (aantal getallen) van een 'short'. */
    public static final int MAX_LENGTH_SHORT   = 4;
    /** Maximale lengte (aantal getallen) van een 'integer'. */
    public static final int MAX_LENGTH_INTEGER = 9;

    private static final List<String> STAMGEGEVENSZONDERTUPLES = new ArrayList<String>();

    /** Deze constante bevat literals die niet in java variabelen mogen voorkomen. */
    public static final String INVALID_JAVA_CHARACTERS = ".,?/_()";

    static {
        STAMGEGEVENSZONDERTUPLES.add("Database object");
        STAMGEGEVENSZONDERTUPLES.add("Element");
        STAMGEGEVENSZONDERTUPLES.add("Regelimplementatie");
        STAMGEGEVENSZONDERTUPLES.add("Regel");
    }

    /** Private constructor. */
    private JavaGeneratieUtil() {
    }

    /**
     * Bekijk of dit stamgegeven een gegeven zonder tuples is.
     *
     * @param stamGegeven het stam gegeven
     * @return true, als het stamgegeven voorkomt in de lijst van stam gegevens zonder tuples
     */
    public static boolean isStamGegevensZonderTuples(final String stamGegeven) {
        return STAMGEGEVENSZONDERTUPLES.contains(stamGegeven);
    }

    /**
     * Convert the package name to a relative path name that can be used to
     * create a file. The System property <code>file.separator</code> is used.
     *
     * @param packageName The package name to convert.
     * @return The converted path name.
     */
    public static String convertPackageToPath(final String packageName) {
        if (packageName == null || packageName.trim().equals("")) {
            return "";
        }
        String delimiter = JavaGeneratorConstante.FILE_DELIMITER.getWaarde();
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
    public static String normalise(final String input) {
        return StringUtils.stripAccents(input);

    }

    /**
     * Converteer de input string naar een string die geen illegale tekens meer bevat om gebruikt te worden in een java
     * variable, methode, enum waarde enz.
     *
     * @param input de tekst waar een valide enum naam van moet worden gemaakt.
     * @return de gefilterde string
     */
    public static String toValidEnumName(final String input) {
        return cleanUpInvalidJavaCharacters(input).replace(' ', '_').toUpperCase();
    }

    /**
     * Converteer de input string naar een string die geen illegale tekens meer bevat om gebruikt te worden in een java
     * variable, methode, enum waarde enz.
     *
     * @param input de tekst die als java variabele naam gebruikt moet worden
     * @return de gefilterde string
     */
    public static String toValidJavaVariableName(final String input) {
        return GeneratieUtil.lowerTheFirstCharacter(stripSpaces(camelCase(cleanUpInvalidJavaCharacters(input))));
    }

    /**
     * Verwijdert spaties uit de input.
     *
     * @param input De input.
     * @return input maar dan zonder spaties.
     */
    public static String stripSpaces(final String input) {
        return StringUtils.replace(input, " ", "");
    }

    /**
     * Clean up invalide java karakters.
     *
     * @param input de tekst die valide java tekst moet worden
     * @return de gefilterde string
     */
    public static String cleanUpInvalidJavaCharacters(final String input) {
        return StringUtils.replaceChars(input, INVALID_JAVA_CHARACTERS, "");
    }

    /**
     * Converteer de tekst naar CamelCase om aan de coding standaarden te voldoen. Vervangt spaties en maakt
     * hoofdletters van nieuwe woorden
     *
     * @param input de tekst waar camelcase op moet worden toegepasts
     * @return de gefilterde string
     */
    public static String camelCase(final String input) {
        return WordUtils.capitalize(input);
    }

    /**
     * Zet de 'javadoc' documentatie die de beschrijving geeft van het type (class of interface). Hierbij wordt de
     * opgegeven tekst gesplitst op whitespaces en wordt er een collectie van woorden van gemaakt. Dit daar deze
     * dan ten tijde van generatie correct gewrapped kan worden; bij generatie wordt elk woord dan afzonderlijk
     * weggeschreven tot de maximum lijn lengte, waarna dan het volgende woord op de volgende regel wordt
     * geschreven. Tevens wordt er, omwille van correcte indentie en javadoc prefix ('*') ook een splitsing gedaan
     * op het begin van elke lijn van de javadoc, waarbij dan de javadoc prefix wordt toegevoegd.
     *
     * @param javaDocVoorObject de javaDoc beschrijving van het object.
     * @return Lijst met stukjes javadoc als String.
     */
    public static List<String> genereerJavaDoc(final String javaDocVoorObject) {
        List<String> javaDoc = null;
        if (javaDocVoorObject != null) {
            // Opsplitsing op begin van een lijn (in multi-lijn mode) --> effectief op lijn separatoren.
            String[] javaDocDelen = javaDocVoorObject.split("(?m)^");

            // Vulling javadoc woorden lijst
            javaDoc = new ArrayList<String>();
            for (String javaDocDeel : javaDocDelen) {
                // Opsplitsing javadoc delen op whitespaces
                javaDoc.addAll(Arrays.asList(javaDocDeel.split("[ \\t\\x0B\\f]")));
            }
        }
        return javaDoc;
    }

    /**
     * Genereert een naam voor een enumeratie waarde. Dit gebeurt op basis van de ident_code van de tuple waar de
     * enumeratie waarde mee correspondeert.
     * Voor elk hoofdletter wordt een underscore '_' gezet, het resultaat wordt vervolgens volledig in hoofdletters
     * teruggegeven.
     *
     * @param tupleIdentCode Ident code van de tuple.
     * @return Naam voor de enum waarde.
     */
    public static String genereerNaamVoorEnumWaarde(final String tupleIdentCode) {
        final StringBuilder nameBuilder = new StringBuilder();
        char[] letters = tupleIdentCode.toCharArray();
        for (int i = 0; i < letters.length; i++) {
            if (i > 0) {
                if (Character.isUpperCase(letters[i])) {
                    nameBuilder.append("_").append(letters[i]);
                } else {
                    nameBuilder.append(letters[i]);
                }
            } else {
                nameBuilder.append(letters[i]);
            }
        }
        return nameBuilder.toString().toUpperCase();
    }

    /**
     * Maak van de collectie van imports een lijst die checkstyle compliant is.
     * De overbodige imports worden verwijderd, de lijst wordt gesplitst in java, javax en andere imports,
     * per groep gesorteerd en samengevoegd met een newline tussen de groepen.
     * NB: Er wordt hier geen 'import ' en ';' toegevoegd, dat gebeurt in het String template.
     *
     * @param type het type waar de imports voor zijn
     * @param imports alle imports voor het type
     * @return de lijst van import in checkstyle stijl
     */
    public static List<String> maakImportsCheckstyleStijl(final AbstractJavaType type, final Set<String> imports) {
        // Stap 1: Verwijder alle onnodige imports. Het is al een set, dus geen dubbelen.
        Iterator<String> iter = imports.iterator();
        while (iter.hasNext()) {
            String importClass = iter.next();
            // Verwijder imports uit hetzelfde package als het type.
            if (type.getPackagePad().equals(importClass.substring(0, importClass.lastIndexOf(".")))) {
                iter.remove();
            }
            // Verwijder imports java.lang. Deze zouden kunnen zijn toegevoegd door attribuut type imports.
            if (importClass.startsWith("java.lang.")) {
                iter.remove();
            }
        }

        // Stap 2: Splits de lijst in java imports, javax imports,
        // andere imports 'voor java' en andere imports 'na java' en sorteer deze.
        SortedSet<String> javaImports = new TreeSet<String>(
                Collections2.filter(imports, new JavaImportPredicate(true, false)));
        SortedSet<String> javaxImports = new TreeSet<String>(
                Collections2.filter(imports, new JavaImportPredicate(false, true)));
        SortedSet<String> andereImportsVoorJava = new TreeSet<String>(
                Collections2.filter(imports, new JavaImportPredicate(false, false, true)));
        SortedSet<String> andereImportsNaJava = new TreeSet<String>(
                Collections2.filter(imports, new JavaImportPredicate(false, false, false)));

        // Functie om import statement toe te voegen.
        Function<String, String> voegImportStatementToeFunctie = new Function<String, String>() {
            @Override
            public String apply(final String input) {
                return "import " + input + ";";
            }
        };

        // Stap 3: Voeg de lijsten weer samen, in de juiste volgorde, met een newline ertussen en een import statement.
        List<String> checkstyleImports = new ArrayList<String>();
        checkstyleImports.addAll(Collections2.transform(andereImportsVoorJava, voegImportStatementToeFunctie));
        checkstyleImports.add("");
        checkstyleImports.addAll(Collections2.transform(javaImports, voegImportStatementToeFunctie));
        checkstyleImports.add("");
        checkstyleImports.addAll(Collections2.transform(javaxImports, voegImportStatementToeFunctie));
        checkstyleImports.add("");
        checkstyleImports.addAll(Collections2.transform(andereImportsNaJava, voegImportStatementToeFunctie));

        return checkstyleImports;
    }

    /**
     * Bepaalt het java basis type voor een attribuut type.
     *
     * @param attribuutType het attribuut type uit het BMR.
     * @param defaultType het fallback default type. (HACK)
     * @return java type fully qualified class name.
     */
    public static JavaType bepaalJavaBasisTypeVoorAttribuutType(final AttribuutType attribuutType,
        final BasisType defaultType)
    {
        //TODO Smerige hack voor numerieke code, deze wordt altijd op een Integer gemapped in het BMR maar dit is niet
        //TODO goed. We zouden een aantal bytes filter moeten toepassen maar dit is voor numerieke codes tegen BMR
        //TODO principes in. (Roel & Oussama)
        JavaType javaType;
        if (attribuutType.getBasisType().getNaam().equalsIgnoreCase("Numerieke code")
            && attribuutType.getMaximumLengte() != null)
        {
            final int maxLengte = attribuutType.getMaximumLengte();
            if (maxLengte <= MAX_LENGTH_SHORT) {
                javaType = JavaType.SHORT;
            } else if (maxLengte <= MAX_LENGTH_INTEGER) {
                javaType = JavaType.INTEGER;
            } else {
                javaType = JavaType.LONG;
            }
        } else {
            javaType = JavaType.bouwVoorFullyQualifiedClassName(defaultType.getNaam());
        }
        return javaType;
    }

}
