/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.gedeeld;

/**
 * Enumeratie voor de predikaten.
 *
 */
public enum Predikaat {
    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0 */
    DUMMY(null, null, null),
    /** Zijne Koninklijke Hoogheid. */
    KONINKLIJKE_HOOGHEID("K", "Zijne Koninklijke Hoogheid", "Zijne Koninklijke Hoogheid / Hare Koninklijke Hoogheid"),
    /** Zijne Hoogheid. */
    HOOGHEID("H", "Zijne Hoogheid", "Zijne Hoogheid / Hare Hoogheid"),
    /** Jonkheer. */
    JONKHEER("J", "Jonkheer", "Jonkheer / Jonkvrouw");

    private final String code;

    private final String naam;

    private final String omschrijving;

    /**
     * lokale constructor om de enums te bouwen.
     * @param code de code
     * @param naam de naam
     * @param omschrijving de omschrijving
     */
    private Predikaat(final String code, final String naam, final String omschrijving) {
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
