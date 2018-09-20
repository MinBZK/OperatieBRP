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
 * Het 'element' is het kern begrip in het meta model.
Hier toegespitst op de elementen die we onderkennen in het operationele model.
 * @version 1.0.18.
 */
public enum Element {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY("", "", "");

    /** Omschrijving van het element. */
    private final String naam;
    /** Nadere typering van het element / het soort element. */
    private final String soort;
    /** Het Element waarvan het onderhavige Element een onderdeel vormt. */
    private final String ouder;

    /**
     * Constructor.
     *
     * @param naam Omschrijving van het element.
     * @param soort Nadere typering van het element / het soort element.
     * @param ouder Het Element waarvan het onderhavige Element een onderdeel vormt.
     *
     */
    private Element(final String naam, final String soort, final String ouder) {
        this.naam = naam;
        this.soort = soort;
        this.ouder = ouder;
    }

    /**
     * @return Omschrijving van het element.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @return Nadere typering van het element / het soort element.
     */
    public String getSoort() {
        return soort;
    }

    /**
     * @return Het Element waarvan het onderhavige Element een onderdeel vormt.
     */
    public String getOuder() {
        return ouder;
    }

}
