/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Classificatie van de Relatie..
 */
public enum SoortRelatie {

    /** DUMMY. */
    DUMMY("", "", ""),
    /** Huwelijk. */
    HUWELIJK("H", "Huwelijk", ""),
    /** Geregistreerd partnerschap. */
    GEREGISTREERD_PARTNERSCHAP("G", "Geregistreerd partnerschap", ""),
    /** Familierechtelijke betrekking. */
    FAMILIERECHTELIJKE_BETREKKING("F", "Familierechtelijke betrekking", "Het betreft hier de familierechtelijke betrekking tussen Ouder(s) en Kind.");

    /** code. */
    private String code;
    /** naam. */
    private String naam;
    /** omschrijving. */
    private String omschrijving;

    /**
     * Constructor.
     *
     * @param code de code
     * @param naam de naam
     * @param omschrijving de omschrijving
     *
     */
    private SoortRelatie(final String code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * De (functionele) code waarmee de Soort relatie kan worden aangeduid..
     * @return String
     */
    public String getCode() {
        return code;
    }

    /**
     * Omschrijving van de soort relatie..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

    /**
     * De omschrijving van de Soort relatie..
     * @return String
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
