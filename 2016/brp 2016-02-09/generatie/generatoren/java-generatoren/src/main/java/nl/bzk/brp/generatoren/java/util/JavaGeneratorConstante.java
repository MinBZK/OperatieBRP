/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.util;

/** Enum voor handige constanten die gebruikt kunnen worden voor het genereren van java code. */
public enum JavaGeneratorConstante {

    /** Een spatie. */
    SPACE(" "),
    /** Standaard Java indentie (vier spaties). */
    INDENT("    "),
    /**
     * OS/Systeem onafhankelijke lijn separator.
     * NB: Dit is bewust en is de enige newline die in het generatie proces
     * gebruikt dient te worden. Dit, omdat er anders telkens diffs ontstaan tussen gegenereerde
     * code op verschillende platformen.
     */
    NEWLINE_OS_ONAFHANKELIJK("\r\n"),
    /** OS/Systeem specifieke bestand separator. */
    FILE_DELIMITER(System.getProperty("file.separator")),
    /** Lijn separatoren. */
    LINE_DELIMITERS("\n\r\f"),
    /** Start van een javadoc. */
    START_JAVADOC("/**"),
    /** Prefix voor javadoc lijn (buiten start en eind). */
    MIDDLE_JAVADOC(" * "),
    /** Einde javadoc. */
    END_JAVADOC(" */"),
    /** Prefix voor een enkele commentaar regel. */
    SINGLE_COMMENT("// ");

    private final String waarde;

    /**
     * Constructor die de waarde van de constante zet.
     *
     * @param waarde de waarde van de constante.
     */
    private JavaGeneratorConstante(final String waarde) {
        this.waarde = waarde;
    }

    /**
     * Retourneert de waarde van de constante.
     *
     * @return de waarde van de constante.
     */
    public String getWaarde() {
        return waarde;
    }
}
