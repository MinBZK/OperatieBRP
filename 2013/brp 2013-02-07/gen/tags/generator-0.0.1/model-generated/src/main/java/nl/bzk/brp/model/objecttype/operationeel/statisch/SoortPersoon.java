/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Classificatie van Personen..
 */
public enum SoortPersoon {

    /** DUMMY. */
    DUMMY("", "", ""),
    /** Ingeschrevene. */
    INGESCHREVENE("I", "Ingeschrevene", ""),
    /** Niet ingeschrevene. */
    NIET_INGESCHREVENE("N", "Niet ingeschrevene", ""),
    /** Alternatieve realiteit. */
    ALTERNATIEVE_REALITEIT("A", "Alternatieve realiteit", "");

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
    private SoortPersoon(final String code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * De (functionele) code voor het Soort persoon..
     * @return String
     */
    public String getCode() {
        return code;
    }

    /**
     * De naam van het Soort persoon..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

    /**
     * De omschrijving van de Soort persoon..
     * @return String
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
