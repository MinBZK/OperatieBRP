/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

/**
 * De mogelijke reden voor opschorting van de bijhouding van een Persoon.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
public enum RedenOpschorting {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("-1", "Dummy"),
    /**
     * Overlijden.
     */
    OVERLIJDEN("O", "Overlijden"),
    /**
     * Ministerieel besluit.
     */
    MINISTERIEEL_BESLUIT("M", "Ministerieel besluit"),
    /**
     * Fout.
     */
    FOUT("F", "Fout"),
    /**
     * Onbekend.
     */
    ONBEKEND("?", "Onbekend");

    private final String code;
    private final String naam;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param code Code voor RedenOpschorting
     * @param naam Naam voor RedenOpschorting
     */
    private RedenOpschorting(final String code, final String naam) {
        this.code = code;
        this.naam = naam;
    }

    /**
     * Retourneert Code van Reden opschorting.
     *
     * @return Code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Reden opschorting.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

}
