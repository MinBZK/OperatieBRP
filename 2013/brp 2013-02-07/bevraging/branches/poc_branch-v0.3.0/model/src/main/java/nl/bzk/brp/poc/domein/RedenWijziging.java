/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.domein;

/**
 * Enumeratie voor reden wijziging adres.
 */
public enum RedenWijziging {

    /**
     * Deze enumeratie correspondeert met een statische tabel waarvan de id's bij 1 beginnen te tellen. De ordinal van
     * een enum begint echter bij 0.
     */
    DUMMY(null, null),
    /**
     * Reden wijziging adres is vanwege aangifte door persoon.
     */
    AANGIFTE_PERSOON("P", "Aangifte door persoon"),
    /**
     * Reden wijziging adres is vanwege ambtshalve (besluit).
     */
    AMBTSHALVE("A", "Ambtshalve"),
    /**
     * BReden wijziging adres is vanwege ministerieel besluit.
     */
    MINISTERIEEL_BESLUIT("M", "Ministerieel besluit"),
    /**
     * Reden wijziging adres is vanwege een (technische) wijziging in de BAG.
     */
    TECHNISCHE_WIJZIGING_IVM_BAG("B", "Technische wijzigingen i.v.m. BAG"),
    /**
     * Reden wijziging adres is vanwege een infrastructurele wijziging, bijvoorbeeld door gemeentelijke herindeling.
     */
    INFRASTRUCTURELE_WIJZIGING("I", "Infrastructurele wijziging");

    private String code;
    private String naam;

    /**
     * Constructor voor de initialisatie van de enumeratie.
     *
     * @param code code.
     * @param naam naam.
     */
    private RedenWijziging(final String code, final String naam) {
        this.code = code;
        this.naam = naam;
    }

    public String getCode() {
        return code;
    }

    public String getNaam() {
        return naam;
    }

}
