/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Typering van de (logische) Elementen..
 */
public enum SoortElement {

    /** DUMMY. */
    DUMMY(""),
    /** Objecttype. */
    OBJECTTYPE("Objecttype"),
    /** Attribuut. */
    ATTRIBUUT("Attribuut");

    /** naam. */
    private String naam;

    /**
     * Constructor.
     *
     * @param naam de naam
     *
     */
    private SoortElement(final String naam) {
        this.naam = naam;
    }

    /**
     * Omschrijving van het soort element..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

}
