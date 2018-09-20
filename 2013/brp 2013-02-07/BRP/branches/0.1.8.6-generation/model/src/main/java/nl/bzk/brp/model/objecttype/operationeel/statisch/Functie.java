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
 * Functie is een door een Partij uitvoerbare activiteit die ten dienste staat aan het vervullen van één of meer rollen van die Partij.
 * @version 1.0.18.
 */
public enum Functie {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY(""),
    /** Dummy vulling. */
    DUMMY_VULLING("Dummy vulling");

    /** Naam van de functie. */
    private final String naam;

    /**
     * Constructor.
     *
     * @param naam Naam van de functie.
     *
     */
    private Functie(final String naam) {
        this.naam = naam;
    }

    /**
     * @return Naam van de functie.
     */
    public String getNaam() {
        return naam;
    }

}
