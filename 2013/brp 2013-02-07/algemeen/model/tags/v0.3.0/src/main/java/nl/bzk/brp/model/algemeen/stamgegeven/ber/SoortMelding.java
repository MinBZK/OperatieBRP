/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.ber;

/**
 * Categorisatie van de ernst van meldingen.
 *
 *
 *
 */
public enum SoortMelding {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("-1", "Dummy"),
    /**
     * Geen.
     */
    GEEN("G", "Geen"),
    /**
     * Informatie.
     */
    INFORMATIE("I", "Informatie"),
    /**
     * Waarschuwing.
     */
    WAARSCHUWING("W", "Waarschuwing"),
    /**
     * Deblokkeerbaar.
     */
    DEBLOKKEERBAAR("D", "Deblokkeerbaar"),
    /**
     * Fout.
     */
    FOUT("F", "Fout");

    private final String code;
    private final String naam;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param code Code voor SoortMelding
     * @param naam Naam voor SoortMelding
     */
    private SoortMelding(final String code, final String naam) {
        this.code = code;
        this.naam = naam;
    }

    /**
     * Retourneert Code van Soort melding.
     *
     * @return Code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Soort melding.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

}
