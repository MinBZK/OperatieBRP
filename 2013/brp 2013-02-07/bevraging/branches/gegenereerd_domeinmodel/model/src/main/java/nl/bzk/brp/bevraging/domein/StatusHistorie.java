/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein;

/**
 * Status Historie.
 */
public enum StatusHistorie {

    /**
     * Deze enumeratie correspondeert met een statische tabel waarvan de id's bij 1 beginnen te tellen. De ordinal van
     * een enum begint echter bij 0. Deze waarde is dan ook puur een dummy waarde en dient dan ook niet gebruikt te
     * worden.
     */
    DUMMY(null, null),
    /**
     * Actueel voorkomend.
     */
    A("A", "Actueel vookomend"),
    /**
     * Toekomstig voorkomend, maar niet actueel.
     */
    T("T", "Toekomstig voorkomend, maar niet Actueel"),
    /**
     * Materieel voorkomend, maar niet Actueel (en niet Toekomstig).
     */
    M("M", "Materieel voorkomend, maar niet Actueel (en niet Toekomstig)"),
    /**
     * (Alleen) Formeel historisch voorkomend.
     */
    F("F", "(Alleen) Formeel historisch voorkomend");

    private String code;

    private String naam;

    /**
     * Private constructor voor eenmalige initalisatie in deze enumeratie.
     *
     * @param code de code zoals die in de database wordt gepersisteerd.
     * @param naam de naam voor weergave.
     */
    private StatusHistorie(final String code, final String naam) {
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
