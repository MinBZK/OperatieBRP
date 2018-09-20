/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Hoedanigheid van een Partij binnen het BRP-stelsel die bepaalt welke BRP-Functies en/of BRP-Acties die Partij mag verrichten..
 */
public enum Rol {

    /** DUMMY. */
    DUMMY(""),
    /** Afnemer. */
    AFNEMER("Afnemer"),
    /** Bevoegdheidstoedeler. */
    BEVOEGDHEIDSTOEDELER("Bevoegdheidstoedeler"),
    /** Bijhoudingsorgaan College. */
    BIJHOUDINGSORGAAN_COLLEGE("Bijhoudingsorgaan College"),
    /** Bijhoudingsorgaan Minister. */
    BIJHOUDINGSORGAAN_MINISTER("Bijhoudingsorgaan Minister"),
    /** Stelselbeheerder. */
    STELSELBEHEERDER("Stelselbeheerder"),
    /** Toezichthouder. */
    TOEZICHTHOUDER("Toezichthouder");

    /** naam. */
    private String naam;

    /**
     * Constructor.
     *
     * @param naam de naam
     *
     */
    private Rol(final String naam) {
        this.naam = naam;
    }

    /**
     * Naam van de rol.
     * @return String
     */
    public String getNaam() {
        return naam;
    }

}
