/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.aut;

/**
 * De Toestand waarin iets zich kan bevinden, in verhouding tot een fiatteringstraject.
 */
public enum Toestand {

    /**
     * 0 niet gebruiken.
     */
    DUMMY(null, null),

    /**
     * Initiele versie, nog niet actief.
     */
    CONCEPT("Concept", "Initiele versie, nog niet actief."),

    /**
     * Ingevoerd, maar nog niet door Stelselbeheerder (dan wel tweede paar ogen bij Stelselbeheerder) gefiatteerd.
     */
    TE_FIATTEREN("Te fiatteren",
            "Ingevoerd, maar nog niet door Stelselbeheerder (dan wel tweede paar ogen bij Stelselbeheerder) "
                + "gefiatteerd."),

    /**
     * Bekeken, maar niet correct bevonden.
     */
    TE_VERBETEREN("Te verbeteren", "Bekeken, maar niet correct bevonden."),

    /**
     * Definitief, bruikbaar voor toegangsbewaking.
     */
    DEFINITIEF("Definitief", "Definitief, bruikbaar voor toegangsbewaking.");

    private String naam;
    private String omschrijving;

    /**
     * Constructor voor de enumeratie.
     *
     * @param naam Naam van de toestand.
     * @param omschrijving Omschrijving van de toestand.
     */
    Toestand(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    public String getNaam() {
        return naam;
    }

    public String getOmschrijving() {
        return omschrijving;
    }
}
