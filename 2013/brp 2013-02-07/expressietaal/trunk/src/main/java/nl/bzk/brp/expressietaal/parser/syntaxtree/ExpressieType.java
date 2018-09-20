/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

/**
 * Bevat alle mogelijke typen van expressies.
 */
public enum ExpressieType {
    /**
     * Onbekend type.
     */
    UNKNOWN("UnknownType"),
    /**
     * Numeriek type (integer).
     */
    NUMBER("Number"),
    /**
     * Stringtype.
     */
    STRING("String"),
    /**
     * Boolean type.
     */
    BOOLEAN("Boolean"),
    /**
     * Datumtype.
     */
    DATE("Date"),
    /**
     * Datum-tijdtype.
     */
    DATETIME("DateTime"),
    /**
     * Lijsttype.
     */
    LIST("List"),
    /**
     * Indexed attribuut type.
     */
    INDEXED("Indexed"),
    /**
     * Persoontype.
     */
    PERSOON("Persoon"),
    /**
     * Huwelijktype.
     */
    HUWELIJK("Huwelijk"),
    /**
     * Geregistreerd-partnerschaptype.
     */
    GEREGISTREERD_PARTNERSCHAP("Geregistreerd partnerschap");

    private final String expressieTypeNaam;

    /**
     * Constructor.
     *
     * @param expressieTypeNaam Naam van het type.
     */
    private ExpressieType(final String expressieTypeNaam) {
        this.expressieTypeNaam = expressieTypeNaam;
    }

    String getNaam() {
        return expressieTypeNaam;
    }

    @Override
    public String toString() {
        return getNaam();
    }
}
