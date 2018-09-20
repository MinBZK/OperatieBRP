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
 * Hoedanigheid van een Partij binnen het BRP-stelsel die bepaalt welke BRP-Functies en/of BRP-Acties die Partij mag verrichten.
 * @version 1.0.18.
 */
public enum Rol {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
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

    /** Naam van de rol. */
    private final String naam;

    /**
     * Constructor.
     *
     * @param naam Naam van de rol.
     *
     */
    private Rol(final String naam) {
        this.naam = naam;
    }

    /**
     * @return Naam van de rol.
     */
    public String getNaam() {
        return naam;
    }

}
