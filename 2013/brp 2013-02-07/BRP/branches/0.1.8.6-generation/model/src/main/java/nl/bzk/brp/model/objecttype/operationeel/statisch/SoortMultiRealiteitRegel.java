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
 * De Soort multirealiteitsregel.
 * @version 1.0.18.
 */
public enum SoortMultiRealiteitRegel {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY("", ""),
    /** Persoon. */
    PERSOON("Persoon", "Multirealiteit op persoonsgegevens, zoals naam en geboortegegevens."),
    /** Relatie. */
    RELATIE("Relatie", "Multirealiteit op Relatie."),
    /** Betrokkenheid. */
    BETROKKENHEID("Betrokkenheid", "Multirealiteit op Betrokkenheid.");

    /** De naam van de Soort multirealiteitsregel. */
    private final String naam;
    /** De omschrijving van de soort multirealiteitsregel. */
    private final String omschrijving;

    /**
     * Constructor.
     *
     * @param naam De naam van de Soort multirealiteitsregel.
     * @param omschrijving De omschrijving van de soort multirealiteitsregel.
     *
     */
    private SoortMultiRealiteitRegel(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * @return De naam van de Soort multirealiteitsregel.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @return De omschrijving van de soort multirealiteitsregel.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
