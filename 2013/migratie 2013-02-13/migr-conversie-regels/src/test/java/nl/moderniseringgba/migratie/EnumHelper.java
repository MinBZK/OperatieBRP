/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple class to make an enum of a CSV file.
 */
public final class EnumHelper {

    // private static final Pattern LINE_PATTERN = Pattern.compile("\\\"([0-9A-Za-z\\.]+)\\\",\\\"([^\"]*)\\\".*");
    private static final Pattern LINE_PATTERN = Pattern.compile("\\\"([^\"]*)\\\".*");

    private EnumHelper() {
        throw new AssertionError("Niet instantieerbaar");
    }

    public static void main(final String[] args) throws Exception {
        final String directorName = "P:\\doc\\csv\\";
        final File directory = new File(directorName);
        for (final File file : directory.listFiles(new FileFilter() {

            @Override
            public boolean accept(final File file) {
                return file.getName().endsWith(".csv");
            }
        })) {
            makeEnum(file);
        }
        System.out.println("Done");

        // makeEnum("Tabel34 Landen (gesorteerd op code).csv");
    }

    private static void makeEnum(final File file) {
        System.out.println("Converting: " + file.getParent() + '\\' + file.getName());
        BufferedReader reader = null;
        final File writeFile = new File(file.getParent(), file.getName() + ".enum");
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            writer = new BufferedWriter(new FileWriter(writeFile));

            System.out.println(writeFile.getName());

            // writer.write("    private static enum GBA_TABEL {");
            // writer.newLine();

            String line = reader.readLine(); // Skip first line
            while ((line = reader.readLine()) != null) {
                final Matcher matcher = LINE_PATTERN.matcher(line);
                if (matcher.matches()) {
                    // CODE + OMSCHRIJVING
                    // writer.write("/** " + matcher.group(2) + ". */");
                    // writer.write(toEnumName(matcher.group(2)) + "(\"" + matcher.group(1) + "\", \""
                    // + matcher.group(2) + "\"),");

                    // CODE
                    writer.write("/** " + matcher.group(1) + ". */");
                    writer.write(toEnumName(matcher.group(1)) + "(\"" + matcher.group(1) + "\"),");
                    writer.newLine();
                } else {
                    System.out.println("// DOES NOT MATCH: " + line);
                }
            }
            //
            // writer.write("        private static final Map<String, GBA_TABEL> VALUES_BY_CODE = Collections"
            // + "                .unmodifiableMap(new HashMap<String, GBA_TABEL>() {" + "                    {"
            // + "                        for (final GBA_TABEL value : GBA_TABEL.values()) {"
            // + "                            put(value.code, value);" + "                        }"
            // + "                    }" + "                });" +
            //
            // "        private final String code;" + "        private final String omschrijving;" +
            //
            // "        GBA_TABEL(final String code, final String omschrijving) {"
            // + "            this.code = code;" + "            this.omschrijving = omschrijving;" + "        }"
            // +
            //
            // "        public String getCode() {" + "            return code;" + "        }" +
            //
            // "        public String getOmschrijving() {" + "            return omschrijving;" + "        }" +
            //
            // "        public static GBA_TABEL valueOfCode(final String code) throws IllegalArgumentException {"
            // + "            if (VALUES_BY_CODE.containsKey(code)) {"
            // + "                return VALUES_BY_CODE.get(code);" + "            } else {"
            // + "                throw new IllegalArgumentException(\"Onbekend code: \" + code);"
            // + "            }" + "        }" + "    }");

        } catch (final IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (final IOException ioe) {
            }
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (final IOException ioe) {
            }
        }

    }

    private static String toEnumName(final String name) {
        final StringBuilder sb = new StringBuilder();

        final String normalized =
                Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                        .toUpperCase();

        for (int i = 0; i < normalized.length(); i++) {
            final char c = normalized.charAt(i);

            switch (c) {
                case ' ':
                case '-':
                    sb.append("_");
                    break;
                default:
                    if (Character.isJavaIdentifierPart(c)) {
                        sb.append(c);
                    }
            }
        }

        return sb.toString();
    }
}
