/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.statisch;

/**
 * Functie adres.
 */
public enum FunctieAdres {

    /**
     * Dummy value. Echte values beginnen in de database bij 1 ipv 0
     */
    DUMMY(null, null),
    /**
     * Woonadres.
     */
    WOONADRES("W", "Woonadres"),
    /**
     * briefadres.
     */
    BRIEFADRES("B", "Briefadres");

    private final String code;

    private final String naam;

    /**
     * Constructor.
     *
     * @param code functie adres code
     * @param naam functie naam
     */
    private FunctieAdres(final String code, final String naam) {
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
