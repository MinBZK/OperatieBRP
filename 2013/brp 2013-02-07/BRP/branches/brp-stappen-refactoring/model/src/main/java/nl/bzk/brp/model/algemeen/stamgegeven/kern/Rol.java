/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

/**
 * Hoedanigheid van een Partij binnen het BRP-stelsel die bepaalt welke BRP-Functies en/of BRP-Acties die Partij mag
 * verrichten.
 *
 * Als mogelijke, toekomstige rollen worden voorzien: Burger en Terugmelder. De eerste (Burger) is nog niet in beeld, en
 * de tweede (Terugmelder) komt momenteel altijd in samenhang met Afnemer voor. De twee rollen worden daarom nu niet
 * opgenomen.
 * RvdP 20 september 2011.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
public enum Rol {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy"),
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

    private final String naam;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor Rol
     */
    private Rol(final String naam) {
        this.naam = naam;
    }

    /**
     * Retourneert Naam van Rol.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

}
