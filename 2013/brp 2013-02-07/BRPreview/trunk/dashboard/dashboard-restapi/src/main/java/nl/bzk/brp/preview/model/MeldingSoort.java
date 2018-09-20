/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

/**
 * De Class MeldingSoort.
 */
public class MeldingSoort {

    /** De code. */
    private final String code;

    /** De naam. */
    private final String naam;

    /**
     * Instantieert een nieuwe melding soort.
     *
     * @param code de code
     * @param naam de naam
     */
    public MeldingSoort(final String code, final String naam) {
        this.code = code;
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
     * Haalt een naam op.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

}
