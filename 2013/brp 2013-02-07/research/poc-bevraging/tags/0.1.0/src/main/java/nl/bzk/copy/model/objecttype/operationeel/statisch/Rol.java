/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.statisch;

/**
 */
public enum Rol {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY(null),
    /**
     * Afnemer.
     */
    AFNEMER("Afnemer"),
    /**
     * Bevoegdheidstoedeler.
     */
    BEVOEGDHEIDSTOEDELER("Bevoegdheidstoedeler"),
    /**
     * Bijhoudingsorgaan College.
     */
    BIJHOUDINGSORGAAN_COLLEGE("Bijhoudingsorgaan College"),
    /**
     * Bijhoudingsorgaan Minister.
     */
    BIJHOUDINGSORGAAN_MINISTER("Bijhoudingsorgaan Minister"),
    /**
     * Stelselbeheerder.
     */
    STELSELBEHEERDER("Stelselbeheerder"),
    /**
     * Toezichthouder.
     */
    TOEZICHTHOUDER("Toezichthouder");

    private String naam;

    /**
     * Private constructor voor eenmalige instantiatie in deze file.
     *
     * @param naam De naam van de waarde.
     */
    private Rol(final String naam) {
        this.naam = naam;
    }

    public String getNaam() {
        return this.naam;
    }

}
