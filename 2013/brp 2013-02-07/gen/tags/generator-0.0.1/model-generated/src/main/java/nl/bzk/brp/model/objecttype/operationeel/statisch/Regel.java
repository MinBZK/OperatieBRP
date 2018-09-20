/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Een Regel is een door de BRP te bewaken wetmatigheid..
 */
public enum Regel {

    /** DUMMY. */
    DUMMY("", "", "", "");

    /** soort. */
    private String soort;
    /** code. */
    private String code;
    /** omschrijving. */
    private String omschrijving;
    /** specificatie. */
    private String specificatie;

    /**
     * Constructor.
     *
     * @param soort de soort
     * @param code de code
     * @param omschrijving de omschrijving
     * @param specificatie de specificatie
     *
     */
    private Regel(final String soort, final String code, final String omschrijving, final String specificatie) {
        this.soort = soort;
        this.code = code;
        this.omschrijving = omschrijving;
        this.specificatie = specificatie;
    }

    /**
     * Classificatie van de regel..
     * @return String
     */
    public String getSoort() {
        return soort;
    }

    /**
     * Funtionele code die aan de regel is toegekend..
     * @return String
     */
    public String getCode() {
        return code;
    }

    /**
     * De omschrijving van de regel. Dit is een relatief korte(!) aanduiding van wat de regel poogt te bewerkstelligen..
     * @return String
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * De functionele specificatie van de regel..
     * @return String
     */
    public String getSpecificatie() {
        return specificatie;
    }

}
