/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

/**
 * De Class Plaats.
 */
public class Plaats {

    /** De code. */
    private String code;

    /** De naam. */
    private String naam;

    /**
     * Instantieert een nieuwe plaats.
     */
    public Plaats() {
    }

    /**
     * Instantieert een nieuwe plaats.
     *
     * @param naam de naam
     */
    public Plaats(final String naam) {
        this.naam = naam;
    }

    /**
     * Haalt een code op.
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Instellen van code.
     *
     * @param code de nieuwe code
     */
    public void setCode(final String code) {
        this.code = code;
    }

    /**
     * Haalt een naam op.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Instellen van naam.
     *
     * @param naam de nieuwe naam
     */
    public void setNaam(final String naam) {
        this.naam = naam;
    }

}
