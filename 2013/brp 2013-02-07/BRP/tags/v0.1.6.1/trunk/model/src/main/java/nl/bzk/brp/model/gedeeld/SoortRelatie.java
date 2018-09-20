/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.gedeeld;

/**
 * Enum voor stamgegeven soort relatie.
 */
public enum SoortRelatie {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0 */
    DUMMY(null, null, null),
    /** Huwelijk. **/
    HUWELIJK("H", "Huwelijk", ""),
    /** Geregistreerd partnerschap. **/
    GEREGISTREERD_PARTNERSCHAP("G",
            "Geregistreerd partnerschap", ""),
    /** Familierechtelijke betrekking. **/
    FAMILIERECHTELIJKE_BETREKKING("F", "Familierechtelijke betrekking",
            "Het betreft hier de familierechtelijke betrekking tussen Ouder(s) en Kind.");

    private final String code;
    private final String naam;
    private final String omschrijving;

    /**
     * Private contructor voor de enum waarden.
     *
     * @param code Code van de soort relatie.
     * @param naam Naam van de soort relatie.
     * @param omschrijving Omschrijving voor de soort relatie.
     */
    private SoortRelatie(final String code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    public String getCode() {
        return code;
    }

    public String getNaam() {
        return naam;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

}
