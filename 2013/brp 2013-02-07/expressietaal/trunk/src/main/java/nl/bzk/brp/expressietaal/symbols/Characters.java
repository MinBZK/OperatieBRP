/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

/**
 * Definieert speciale characters en biedt methoden om characters te classificeren.
 */
public final class Characters {

    /**
     * Tekens die als witruimte gelden.
     */
    public static final String WHITESPACE_CHARS = " \t\n";
    /**
     * Tekens die herkend worden als 'haakje'.
     */
    public static final String BRACKET_CHARS = "()[]{}#";
    /**
     * Tekens die een operator zijn of aan het begin van een operator kunnen staan.
     */
    public static final String FIRSTOPERATOR_CHARS = "<>=+-*/";
    /**
     * Scheidingsteken voor string.
     */
    public static final char STRING_DELIMITER = '"';
    /**
     * Scheidingsteken voor elementen in een lijst.
     */
    public static final char ELEMENT_SCHEIDINGSTEKEN = ',';
    /**
     * Scheidingsteken voor attributen.
     */
    public static final char OBJECT_QUALIFIER = '.';
    /**
     * Beginhaakje voor expressies.
     */
    public static final char HAAKJE_START = '(';
    /**
     * Eindhaakje voor expressies.
     */
    public static final char HAAKJE_EIND = ')';
    /**
     * Startteken voor datum literal.
     */
    public static final char DATUM_START = '#';
    /**
     * Eindteken voor datum literal.
     */
    public static final char DATUM_EIND = '#';
    /**
     * Startteken voor lijst.
     */
    public static final char LIJST_START = '{';
    /**
     * Eindteken voor lijst.
     */
    public static final char LIJST_EIND = '}';
    /**
     * Startteken voor index.
     */
    public static final char INDEX_START = '[';
    /**
     * Eindteken voor index.
     */
    public static final char INDEX_EIND = ']';

    /**
     * Constructor. Private omdat het een utility class is.
     */
    private Characters() {
    }

    /**
     * Bepaal of het karakter een witruimte-karakter is.
     *
     * @param ch Te testen karakter.
     * @return True als het een witruimte-karakter is.
     */
    public static boolean isWitruimte(final char ch) {
        return WHITESPACE_CHARS.contains(String.valueOf(ch));
    }

    /**
     * Bepaal of het karakter een haakje is.
     *
     * @param ch Te testen karakter.
     * @return True als het een haakje is.
     */
    public static boolean isHaakje(final char ch) {
        return BRACKET_CHARS.contains(String.valueOf(ch));
    }

    /**
     * Bepaal of het karakter een cijfer is.
     *
     * @param ch Te testen karakter.
     * @return True als het een cijfer is.
     */
    public static boolean isCijfer(final char ch) {
        return (ch >= '0' && ch <= '9');
    }

    /**
     * Bepaal of het karakter een letter is.
     *
     * @param ch Te testen karakter.
     * @return True als het een letter is.
     */
    public static boolean isLetter(final char ch) {
        return ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'));
    }

    /**
     * Bepaal of het karakter een startteken van een identifier kan zijn.
     *
     * @param ch Te testen karakter.
     * @return True als het teken aan het begin van een identifier kan staan.
     */
    public static boolean isStarttekenIdentifier(final char ch) {
        return isLetter(ch);
    }

    /**
     * Bepaal of het karakter onderdeel van een identifier kan zijn.
     *
     * @param ch Te testen karakter.
     * @return True als het teken onderdeel van een identifier kan zijn.
     */
    public static boolean isIdentifierTeken(final char ch) {
        return isLetter(ch) || isCijfer(ch) || (ch == '_');
    }

    /**
     * Bepaal of het karakter een startteken van een operator kan zijn.
     *
     * @param ch Te testen karakter.
     * @return True als het teken aan het begin van een operator kan staan.
     */
    public static boolean isStarttekenOperator(final char ch) {
        return FIRSTOPERATOR_CHARS.contains(String.valueOf(ch));
    }

    /**
     * Bepaal of het karakter een scheidingsteken voor lijstelementen en argumenten is.
     *
     * @param ch Te testen karakter.
     * @return True als het een scheidingsteken is.
     */
    public static boolean isScheidingsteken(final char ch) {
        return ch == ELEMENT_SCHEIDINGSTEKEN;
    }

    /**
     * Bepaal of het karakter een scheidingsteken voor attributen is.
     *
     * @param ch Te testen karakter.
     * @return True als het een scheidingsteken is.
     */
    public static boolean isAttribuutScheidingsteken(final char ch) {
        return ch == OBJECT_QUALIFIER;
    }
}
