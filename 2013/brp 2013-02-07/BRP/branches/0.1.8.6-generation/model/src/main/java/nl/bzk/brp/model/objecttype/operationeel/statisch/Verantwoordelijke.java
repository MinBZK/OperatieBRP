/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Deze code is gegenereerd vanuit het metaregister van het BRP, versie 1.0.18.
 *
 */
package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Categorisatie van de voor de bijhouding verantwoordelijke Partij.
 * @version 1.0.18.
 */
public enum Verantwoordelijke {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY("", ""),
    /** College van burgemeester en wethouders. */
    COLLEGE_VAN_BURGEMEESTER_EN_WETHOUDERS("C", "College van burgemeester en wethouders"),
    /** Minister. */
    MINISTER("M", "Minister");

    /** De (functionele) code voor de aanduiding van de voor de bijhouding verantwoordelijke Partij. */
    private final String code;
    /** De naam van de Verantwoordelijke. */
    private final String naam;

    /**
     * Constructor.
     *
     * @param code De (functionele) code voor de aanduiding van de voor de bijhouding verantwoordelijke Partij.
     * @param naam De naam van de Verantwoordelijke.
     *
     */
    private Verantwoordelijke(final String code, final String naam) {
        this.code = code;
        this.naam = naam;
    }

    /**
     * @return De (functionele) code voor de aanduiding van de voor de bijhouding verantwoordelijke Partij.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return De naam van de Verantwoordelijke.
     */
    public String getNaam() {
        return naam;
    }

}
