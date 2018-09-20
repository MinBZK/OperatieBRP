/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

/**
 * Categoriesatie van personen.
 *
 * Bij het toedelen van bijhoudingsautorisatie is het van belang onderscheid te maken tussen verschillende categorie�n
 * personen, zoals personen waarvoor de bijhouding onder de verantwoordelijkheid vallen van de minister, en personen die
 * vallen onder de verantwoordelijkheid van de verstrekkende partij.
 *
 * De naamgeving 'categorie personen' is wat algemeen van aard, en drukt niet uit dat het specifiek gaat om een
 * categorisatie van personen naar (o.a.) verantwoordelijkheid. Omdat er geen conflicterende categorisaties zijn
 * onderkend, wordt dit nadeel voor lief genomen: de huidige naam is namelijk compact. RvdP 16 april 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
public enum CategoriePersonen {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * Personen vallend onder het College van B&W van de verstrekkende partij.
     */
    COLLEGE_VERSTREKKENDE_PARTIJ("College verstrekkende partij",
            "Personen vallend onder het College van B&W van de verstrekkende partij"),
    /**
     * Personen vallend onder het College van B&W van de ontvangende partij.
     */
    COLLEGE_ONTVANGENDE_PARTIJ("College ontvangende partij",
            "Personen vallend onder het College van B&W van de ontvangende partij"),
    /**
     * Personen vallend onder EEN College van B&W.
     */
    WILLEKEURIG_COLLEGE("Willekeurig College", "Personen vallend onder EEN College van B&W"),
    /**
     * Personen vallend onder de Minister.
     */
    MINISTER("Minister", "Personen vallend onder de Minister");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor CategoriePersonen
     * @param omschrijving Omschrijving voor CategoriePersonen
     */
    private CategoriePersonen(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Categorie personen.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Categorie personen.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
