/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein;

/**
 * Aanduiding van het soort adres.
 *
 * Adressen zijn óf een woonadres, of een briefadres. Deze twee begrippen zijn gedefinieerd in de Wet BRP (artikel 1.1.)
 *
 */
public enum FunctieAdres {

    /**
     * Deze enumeratie correspondeert met een statische tabel waarvan de id's bij 1 beginnen te tellen. De ordinal van
     * een enum begint echter bij 0. Deze waarde is dan ook puur een dummy waarde en dient dan ook niet gebruikt te
     * worden.
     */
    DUMMY(null, null),
    /**
     * Woonadres als gedefinieerd in de Wet BRP (artikel 1.1.).
     */
    WOONADRES("W", "Woonadres"),
    /**
     * Briefadres als gedefinieerd in de Wet BRP (artikel 1.1.).
     */
    BRIEFADRES("B", "Briefadres");

    private String code;
    private String naam;

    /**
     * Private setter om deze enumeratie te initialiseren.
     *
     * @param code code.
     * @param naam naam.
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
