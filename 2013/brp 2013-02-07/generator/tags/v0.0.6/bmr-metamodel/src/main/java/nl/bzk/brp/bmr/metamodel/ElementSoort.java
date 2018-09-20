/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

/**
 * Classificatie van elementen, die bepaalt van welk subtype een element is.
 */
public enum ElementSoort {
    /**
     * Attribuut.
     */
    A("Attribuut"),
    /**
     * Attribuuttype.
     */
    AT("Attribuuttype"),
    /**
     * Bron.
     */
    B("Bron"),
    /**
     * Bedrijfsregel.
     */
    BR("Bedrijfsregel"),
    /**
     * Berichtsjabloon.
     */
    BS("Berichtsjabloon"),
    /**
     * Basistype.
     */
    BT("Basistype"),
    /**
     * Domein.
     */
    D("Domeinmodel"),
    /**
     * Groep.
     */
    G("Groep"),
    /**
     * Laag.
     */
    L("Laag"),
    /**
     * Objecttype.
     */
    OT("Objecttype"),
    /**
     * Schema.
     */
    S("Schema"),
    /**
     * Versie.
     */
    V("Versie"),
    /**
     * Waarderegel waarde.
     */
    W("Waarderegel waarde");

    private String omschrijving;

    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Met deze private constructor worden alle enumeratiewaarden éénmalig geïnitialiseerd.
     *
     * @param omschrijving de omschrijving van de enumeratiewaarde.
     */
    private ElementSoort(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

}
