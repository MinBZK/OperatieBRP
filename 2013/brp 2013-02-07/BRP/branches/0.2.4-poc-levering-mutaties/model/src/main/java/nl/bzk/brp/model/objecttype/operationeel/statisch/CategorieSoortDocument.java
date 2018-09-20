/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Categorie soort document.
 */
public enum CategorieSoortDocument {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0 */
    DUMMY(null, null),
    /** . */
    NEDERLANDSE_AKTE("Nederlandse akte", "Nederlandse akte"),
    /** . */
    BUITENLANDSE_AKTE("Buitenlandse akte", "Buitenlandse akte");

    private final String naam;

    private final String omschrijving;

    /**
     * Constructor.
     * @param naam Naam van de soort actie.
     * @param omschrijving Omschrijving van de soort actie.
     */
    private CategorieSoortDocument(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert de naam.
     * @return De naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert de omschrijving.
     * @return De Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
