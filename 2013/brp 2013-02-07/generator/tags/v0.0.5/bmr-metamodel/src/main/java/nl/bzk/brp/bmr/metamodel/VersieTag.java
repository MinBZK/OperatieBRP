/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

/**
 * Versietag.
 */
public enum VersieTag {

    /**
     * Werkversie: de huidige versie.
     */
    W("Werkversie"),
    /**
     * Vastgesteld.
     */
    V("Vastgesteld"),
    /**
     * Concept.
     */
    C("Concept"),
    /**
     * Bevroren.
     */
    B("Bevroren"),
    /**
     * Vastgesteld (Eerdere versies).
     */
    O("Vastgesteld (Eerdere versies)");

    private String omschrijving;

    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Met deze private constructor worden alle enumeratiewaarden éénmalig geïnitialiseerd.
     *
     * @param omschrijving de omschrijving van de enumeratiewaarde.
     */
    private VersieTag(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

}
