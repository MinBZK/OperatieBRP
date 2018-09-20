/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Categorisatie van de voor de bijhouding verantwoordelijke Partij..
 */
public enum Verantwoordelijke {

    /** DUMMY. */
    DUMMY("", ""),
    /** College van burgemeester en wethouders. */
    COLLEGE_VAN_BURGEMEESTER_EN_WETHOUDERS("C", "College van burgemeester en wethouders"),
    /** Minister. */
    MINISTER("M", "Minister");

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
    private Verantwoordelijke(final String code, final String naam) {
        this.code = code;
        this.naam = naam;
    }

    /**
     * De (functionele) code voor de aanduiding van de voor de bijhouding verantwoordelijke Partij..
     * @return String
     */
    public String getCode() {
        return code;
    }

    /**
     * De naam van de Verantwoordelijke..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

}
