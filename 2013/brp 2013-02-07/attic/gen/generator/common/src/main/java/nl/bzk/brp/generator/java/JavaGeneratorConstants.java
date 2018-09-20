/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java;

/**
 * Enum voor handige constanten die gebruikt kunnen worden voor het genereren van java code.
 */
public enum JavaGeneratorConstants {
    SPACE(" "), INDENT("    "), NEWLINE(System.getProperty("line.separator")), FILE_DELIMITER(System
            .getProperty("file.separator")), LINE_DELIMITERS("\n\r\f"), START_JAVADOC("/**"), MIDDLE_JAVADOC(" * "),
    END_JAVADOC(" */"), SINGLE_COMMENT("// ");

    private String value;

    JavaGeneratorConstants(String definition) {
        value = definition;
    }

    public String getValue() {
        return value;
    }
}
