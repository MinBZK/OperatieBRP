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
 * De mogelijke soort actie.
 * @version 1.0.18.
 */
public enum SoortActie {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY("", ""),
    /** Conversie GBA. */
    CONVERSIE_GBA("Conversie GBA", "1"),
    /** Inschrijving Geboorte. */
    INSCHRIJVING_GEBOORTE("Inschrijving Geboorte", "2"),
    /** Verhuizing. */
    VERHUIZING("Verhuizing", "3"),
    /** Registratie Erkenning. */
    REGISTRATIE_ERKENNING("Registratie Erkenning", "4"),
    /** Registratie Huwelijk. */
    REGISTRATIE_HUWELIJK("Registratie Huwelijk", "4"),
    /** Wijziging Geslachtsnaamcomponent. */
    WIJZIGING_GESLACHTSNAAMCOMPONENT("Wijziging Geslachtsnaamcomponent", "2"),
    /** Wijziging Naamgebruik. */
    WIJZIGING_NAAMGEBRUIK("Wijziging Naamgebruik", "4"),
    /** Correctie Adres Binnen NL. */
    CORRECTIE_ADRES_BINNEN_NL("Correctie Adres Binnen NL", "3"),
    //MANUALY ADDED
    /** Registratie Nationaliteit. */
    REGISTRATIE_NATIONALITEIT("Registratie Nationaliteit", "5");

    /** Naam van de soort actie. */
    private final String naam;
    /** De categorisatie van soort actie. */
    private final String categorieSoortActie;

    /**
     * Constructor.
     *
     * @param naam Naam van de soort actie.
     * @param categorieSoortActie De categorisatie van soort actie.
     *
     */
    private SoortActie(final String naam, final String categorieSoortActie) {
        this.naam = naam;
        this.categorieSoortActie = categorieSoortActie;
    }

    /**
     * @return Naam van de soort actie.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @return De categorisatie van soort actie.
     */
    public String getCategorieSoortActie() {
        return categorieSoortActie;
    }

}
