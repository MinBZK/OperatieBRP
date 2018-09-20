/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * De Toestand waarin iets zich kan bevinden, in verhouding tot een fiatteringstraject..
 */
public enum Toestand {

    /** DUMMY. */
    DUMMY("", ""),
    /** Concept. */
    CONCEPT("Concept", "Initiele versie, nog niet actief."),
    /** Te fiatteren. */
    TE_FIATTEREN("Te fiatteren", "Ingevoerd, maar nog niet door Stelselbeheerder (dan wel tweede paar ogen bij Stelselbeheerder) gefiatteerd."),
    /** Te verbeteren. */
    TE_VERBETEREN("Te verbeteren", "Bekeken, maar niet correct bevonden."),
    /** Definitief. */
    DEFINITIEF("Definitief", "Definitief, bruikbaar voor toegangsbewaking.");

    /** naam. */
    private String naam;
    /** omschrijving. */
    private String omschrijving;

    /**
     * Constructor.
     *
     * @param naam de naam
     * @param omschrijving de omschrijving
     *
     */
    private Toestand(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * De naam..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

    /**
     * De omschrijving..
     * @return String
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
