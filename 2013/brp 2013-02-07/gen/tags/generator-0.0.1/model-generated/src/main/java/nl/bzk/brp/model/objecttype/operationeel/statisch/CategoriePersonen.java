/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Categoriesatie van personen..
 */
public enum CategoriePersonen {

    /** DUMMY. */
    DUMMY("", ""),
    /** College verstrekkende partij. */
    COLLEGE_VERSTREKKENDE_PARTIJ("College verstrekkende partij", "Personen vallend onder het College van B&W van de verstrekkende partij"),
    /** College ontvangende partij. */
    COLLEGE_ONTVANGENDE_PARTIJ("College ontvangende partij", "Personen vallend onder het College van B&W van de ontvangende partij"),
    /** Willekeurig College. */
    WILLEKEURIG_COLLEGE("Willekeurig College", "Personen vallend onder EEN College van B&W"),
    /** Minister. */
    MINISTER("Minister", "Personen vallend onder de Minister");

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
    private CategoriePersonen(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * De naam van de categorie personen.
     * @return String
     */
    public String getNaam() {
        return naam;
    }

    /**
     * De omschrijving van de categorie personen.
     * @return String
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
