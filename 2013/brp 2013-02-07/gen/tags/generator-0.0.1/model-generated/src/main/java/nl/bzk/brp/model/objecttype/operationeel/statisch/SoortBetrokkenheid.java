/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * De mogelijke wijzen om Betrokken te zijn in een Relatie..
 */
public enum SoortBetrokkenheid {

    /** DUMMY. */
    DUMMY("", ""),
    /** Kind. */
    KIND("K", "Kind"),
    /** Ouder. */
    OUDER("O", "Ouder"),
    /** Partner. */
    PARTNER("P", "Partner");

    /** code. */
    private String code;
    /** naam. */
    private String naam;

    /**
     * Constructor.
     *
     * @param code de code
     * @param naam de naam
     *
     */
    private SoortBetrokkenheid(final String code, final String naam) {
        this.code = code;
        this.naam = naam;
    }

    /**
     * De (functionele) code waarmee de Soort betrokkenheid kan worden aangeduid..
     * @return String
     */
    public String getCode() {
        return code;
    }

    /**
     * Omschrijving van de soort betrokkenheid..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

}
