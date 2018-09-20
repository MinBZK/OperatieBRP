/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Het 'element' is het kern begrip in het meta model.
Hier toegespitst op de elementen die we onderkennen in het operationele model..
 */
public enum Element {

    /** DUMMY. */
    DUMMY("", "", "");

    /** naam. */
    private String naam;
    /** soort. */
    private String soort;
    /** ouder. */
    private String ouder;

    /**
     * Constructor.
     *
     * @param naam de naam
     * @param soort de soort
     * @param ouder de ouder
     *
     */
    private Element(final String naam, final String soort, final String ouder) {
        this.naam = naam;
        this.soort = soort;
        this.ouder = ouder;
    }

    /**
     * Omschrijving van het element..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Nadere typering van het element / het soort element..
     * @return String
     */
    public String getSoort() {
        return soort;
    }

    /**
     * Het Element waarvan het onderhavige Element een onderdeel vormt..
     * @return String
     */
    public String getOuder() {
        return ouder;
    }

}
