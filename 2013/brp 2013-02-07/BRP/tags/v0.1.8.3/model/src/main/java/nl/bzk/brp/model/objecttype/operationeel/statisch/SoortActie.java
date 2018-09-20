/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Soort actie.
 */
public enum SoortActie {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY(null, null),
    /** Conversie GBA. **/
    CONVERSIE_GBA("Conversie GBA", "Een conversie"),
    /** Aangifte geboorte. */
    AANGIFTE_GEBOORTE("Inschrijving door Aangifte geboorte", "Inschrijving door Aangifte geboorte"),
    /** Verhuizing. */
    VERHUIZING("Verhuizing", "Een verhuizing"),
    /** Huwelijk. */
    HUWELIJK("Huwelijk", "Een huwelijk"),
    /** Wijziging naam gebruik. **/
    WIJZIGING_NAAMGEBRUIK("Wijziging NaamGebruik", "Wijzigen van naam gebruik"),
    /** Erkenning geboort. */
    ERKENNING_GEBOORTE("Erkenning geboorte", "Erkenning geboorte kind"),
    /** Registratie nationaliteit. */
    REGISTRATIE_NATIONALITEIT("Registratie nationaliteit",
            "Registratie van een of meerdere nationaliteit(en) voor een persoon");


    private final String naam;

    private final String categorieSoortActie;

    /**
     * Constructor.
     *
     * @param naam de naam
     * @param categorieSoortActie de categorie
     */
    private SoortActie(final String naam, final String categorieSoortActie) {
        this.naam = naam;
        this.categorieSoortActie = categorieSoortActie;
    }

    public String getNaam() {
        return naam;
    }

    public String getCategorieSoortActie() {
        return categorieSoortActie;
    }

}
