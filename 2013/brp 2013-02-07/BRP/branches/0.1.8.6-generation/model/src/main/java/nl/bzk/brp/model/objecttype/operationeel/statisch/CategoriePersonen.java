/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Deze code is gegenereerd vanuit het metaregister van het BRP, versie 1.0.18.
 *
 */
package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Categoriesatie van personen.
 * @version 1.0.18.
 */
public enum CategoriePersonen {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY("", ""),
    /** College verstrekkende partij. */
    COLLEGE_VERSTREKKENDE_PARTIJ("College verstrekkende partij", "Personen vallend onder het College van B&W van de verstrekkende partij"),
    /** College ontvangende partij. */
    COLLEGE_ONTVANGENDE_PARTIJ("College ontvangende partij", "Personen vallend onder het College van B&W van de ontvangende partij"),
    /** Willekeurig College. */
    WILLEKEURIG_COLLEGE("Willekeurig College", "Personen vallend onder EEN College van B&W"),
    /** Minister. */
    MINISTER("Minister", "Personen vallend onder de Minister");

    /** De naam van de categorie personen. */
    private final String naam;
    /** De omschrijving van de categorie personen. */
    private final String omschrijving;

    /**
     * Constructor.
     *
     * @param naam De naam van de categorie personen.
     * @param omschrijving De omschrijving van de categorie personen.
     *
     */
    private CategoriePersonen(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * @return De naam van de categorie personen.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @return De omschrijving van de categorie personen.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
