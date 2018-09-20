/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

/**
 * Typering van Partij.
 *
 * De soorten Partij zijn niet volledig disjunct. Zo is een willekeurige gemeente zowel een "Gemeente" als ook een
 * "Overheidsorgaan". De gebruikte typering is dan de meest beschrijvende typering: zo is de "Soort partij" voor een
 * gemeente gelijk aan "Gemeente", en niet aan "Overheidsorgaan".
 *
 * In de naamgeving van de Soorten partij is gekozen voor naamgeving die overlap suggereert: zo is een willekeurige
 * gemeente zowel een "Gemeente" als een "Overheidsorgaan". Een alternatieve naamgeving zou zijn geweest om dat
 * bijvoorbeeld in plaats van Overheidsorgaan het te hebben over "Overige overheidsorganen".
 * Hiervoor is echter niet gekozen. Het typeren van Partijen door middel van "Soort partij" is iets dat plaats vindt
 * binnen een afdeling van ��n (beheer)organisatie, waardoor verschillend gebruik van de typering niet heel
 * waarschijnlijk is.
 * RvdP 17 oktober 2011.
 *
 *
 *
 */
public enum SoortPartij {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy"),
    /**
     * Wetgever.
     */
    WETGEVER("Wetgever"),
    /**
     * Vertegenwoordiger Regering.
     */
    VERTEGENWOORDIGER_REGERING("Vertegenwoordiger Regering"),
    /**
     * Gemeente.
     */
    GEMEENTE("Gemeente"),
    /**
     * Overheidsorgaan.
     */
    OVERHEIDSORGAAN("Overheidsorgaan"),
    /**
     * Derde.
     */
    DERDE("Derde"),
    /**
     * Samenwerkingsverband.
     */
    SAMENWERKINGSVERBAND("Samenwerkingsverband"),
    /**
     * BRP voorziening.
     */
    B_R_P_VOORZIENING("BRP voorziening");

    private final String naam;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortPartij
     */
    private SoortPartij(final String naam) {
        this.naam = naam;
    }

    /**
     * Retourneert Naam van Soort partij.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

}
