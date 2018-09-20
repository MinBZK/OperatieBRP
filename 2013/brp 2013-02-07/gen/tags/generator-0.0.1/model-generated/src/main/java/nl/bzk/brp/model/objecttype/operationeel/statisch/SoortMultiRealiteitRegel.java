/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * De Soort multirealiteitsregel..
 */
public enum SoortMultiRealiteitRegel {

    /** DUMMY. */
    DUMMY("", ""),
    /** Persoon. */
    PERSOON("Persoon", "Multirealiteit op persoonsgegevens, zoals naam en geboortegegevens."),
    /** Relatie. */
    RELATIE("Relatie", "Multirealiteit op Relatie."),
    /** Betrokkenheid. */
    BETROKKENHEID("Betrokkenheid", "Multirealiteit op Betrokkenheid.");

    /** naam. */
    private String naam;
    /** omschrijving. */
    private String omschrijving;

    /**
     * Constructor.
     *
     * @param naam de naam
     * @param omschrijving de omschrijving
     *
     */
    private SoortMultiRealiteitRegel(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * De naam van de Soort multirealiteitsregel..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

    /**
     * De omschrijving van de soort multirealiteitsregel..
     * @return String
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
