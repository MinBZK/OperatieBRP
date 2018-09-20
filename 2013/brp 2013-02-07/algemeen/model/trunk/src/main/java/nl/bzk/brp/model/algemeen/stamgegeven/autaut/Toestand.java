/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

/**
 * De Toestand waarin iets zich kan bevinden, in verhouding tot een fiatteringstraject.
 *
 * Er zijn een aantal plaatsen waarin de ene (beheerder) iets vastlegd, en de andere (beheerder) deze moet fiatteren,
 * dan wel teruglegt ter verbetering of definitief maakt. Het gaat hier om de onderkende mogelijke toestanden.
 *
 *
 *
 */
public enum Toestand {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * Initi�le versie, nog niet actief..
     */
    CONCEPT("Concept", "Initi�le versie, nog niet actief."),
    /**
     * Ingevoerd, maar nog niet door Stelselbeheerder (dan wel tweede paar ogen bij Stelselbeheerder) gefiatteerd..
     */
    TE_FIATTEREN("Te fiatteren",
            "Ingevoerd, maar nog niet door Stelselbeheerder (dan wel tweede paar ogen bij Stelselbeheerder) gefiatteerd."),
    /**
     * Bekeken, maar niet correct bevonden..
     */
    TE_VERBETEREN("Te verbeteren", "Bekeken, maar niet correct bevonden."),
    /**
     * Definitief, bruikbaar voor toegangsbewaking..
     */
    DEFINITIEF("Definitief", "Definitief, bruikbaar voor toegangsbewaking.");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor Toestand
     * @param omschrijving Omschrijving voor Toestand
     */
    private Toestand(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Toestand.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Toestand.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
