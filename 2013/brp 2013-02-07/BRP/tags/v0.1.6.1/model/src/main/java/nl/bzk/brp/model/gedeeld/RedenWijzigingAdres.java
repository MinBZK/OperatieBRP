/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.gedeeld;

/**
 * Reden wijziging adres.
 *
 */
public enum RedenWijzigingAdres {
    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY(null, null, null),
    /** Aangifter door persoon. */
    PERSOON("P", "Persoon", "Aangifter door persoon"),
    /** Ambtshalve. */
    AMBTSHALVE("A", "Ambtshalve", "Ambtshalve"),
    /** Ministrieel besluit. */
    MINISTERIE("M", "Ministrieel besluit", "Ministrieel besluit"),
    /** Technisch wijziging ivm. BAG. */
    BAG("B", "BAG", "Technisch wijziging ivm. BAG"),
    /** Infrastructurele wijziging. */
    INFRA("I", "Infrastructuur", "Infrastructurele wijziging");

    private final String code;
    private final String naam;
    private final String omschrijving;

    /**
     * Constructor.
     *
     * @param code soort wijzig code
     * @param naam soort wijzig naam
     * @param omschrijving de omschrijving
     */
    private RedenWijzigingAdres(final String code, final String naam, final String omschrijving) {
        this.code = code; this.naam = naam; this.omschrijving = omschrijving;
    }
    public String getCode() {
        return code;
    }

    public String getNaam() {
        return naam;
    }

    public String getOmschrijving() {
        return omschrijving;
    }
}
