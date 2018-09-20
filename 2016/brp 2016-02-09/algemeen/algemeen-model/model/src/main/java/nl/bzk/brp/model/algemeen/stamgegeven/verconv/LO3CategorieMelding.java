/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.verconv;

import javax.annotation.Generated;

/**
 * Categorisatie van soort LO3 melding.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum LO3CategorieMelding {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy"),
    /**
     * Verwerking.
     */
    VERWERKING("Verwerking"),
    /**
     * Preconditie.
     */
    PRECONDITIE("Preconditie"),
    /**
     * Bijzondere situatie.
     */
    BIJZONDERE_SITUATIE("Bijzondere situatie"),
    /**
     * Syntax.
     */
    SYNTAX("Syntax"),
    /**
     * Structuur.
     */
    STRUCTUUR("Structuur");

    private final String naam;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor LO3CategorieMelding
     */
    private LO3CategorieMelding(final String naam) {
        this.naam = naam;
    }

    /**
     * Retourneert Naam van LO3 Categorie melding.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

}
