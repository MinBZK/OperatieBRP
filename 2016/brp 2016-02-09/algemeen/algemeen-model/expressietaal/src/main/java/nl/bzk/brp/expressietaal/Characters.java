/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal;

/**
 * Definieert speciale characters en biedt methoden om characters te classificeren.
 */
public final class Characters {

    /**
     * Scheidingsteken voor elementen in een lijst.
     */
    public static final char LIJST_SCHEIDINGSTEKEN = ',';
    /**
     * Scheidingsteken voor onderdelen van een datum.
     */
    public static final char DATUM_SCHEIDINGSTEKEN = '/';
    /**
     * Startteken voor periode literal.
     */
    public static final char PERIODE_START         = '^';
    /**
     * Eindteken voor periode literal.
     */
    public static final char PERIODE_EIND          = '^';
    /**
     * Startteken voor lijst.
     */
    public static final char LIJST_START           = '{';
    /**
     * Eindteken voor lijst.
     */
    public static final char LIJST_EIND            = '}';
    /**
     * Teken voor attribuutreferentie.
     */
    public static final char REFERENCE             = '$';

    /**
     * Constructor. Private omdat het een utility class is.
     */
    private Characters() {
    }
}
