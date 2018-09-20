/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * De mogelijke soort actie..
 */
public enum SoortActie {

    /** DUMMY. */
    DUMMY("", ""),
    /** Conversie GBA. */
    CONVERSIE_GBA("Conversie GBA", "1"),
    /** Inschrijving Geboorte. */
    INSCHRIJVING_GEBOORTE("Inschrijving Geboorte", "2"),
    /** Verhuizing. */
    VERHUIZING("Verhuizing", "3"),
    /** Registratie Erkenning. */
    REGISTRATIE_ERKENNING("Registratie Erkenning", ""),
    /** Registratie Huwelijk. */
    REGISTRATIE_HUWELIJK("Registratie Huwelijk", ""),
    /** Wijziging Geslachtsnaamcomponent. */
    WIJZIGING_GESLACHTSNAAMCOMPONENT("Wijziging Geslachtsnaamcomponent", ""),
    /** Wijziging Naamgebruik. */
    WIJZIGING_NAAMGEBRUIK("Wijziging Naamgebruik", ""),
    /** Correctie Adres Binnen NL. */
    CORRECTIE_ADRES_BINNEN_NL("Correctie Adres Binnen NL", "");

    /** naam. */
    private String naam;
    /** categorieSoortActie. */
    private String categorieSoortActie;

    /**
     * Constructor.
     *
     * @param naam de naam
     * @param categorieSoortActie de categorieSoortActie
     *
     */
    private SoortActie(final String naam, final String categorieSoortActie) {
        this.naam = naam;
        this.categorieSoortActie = categorieSoortActie;
    }

    /**
     * Naam van de soort actie..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

    /**
     * De categorisatie van soort actie..
     * @return String
     */
    public String getCategorieSoortActie() {
        return categorieSoortActie;
    }

}
