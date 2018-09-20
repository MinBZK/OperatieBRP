/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * De categorisatie van de soorten BRP acties..
 */
public enum CategorieSoortActie {

    /** DUMMY. */
    DUMMY("", ""),
    /** Conversie. */
    CONVERSIE("Conversie", "Alle soorten acties voortvloeiend uit conversie"),
    /** Familierechtelijke betrekking. */
    FAMILIERECHTELIJKE_BETREKKING("Familierechtelijke betrekking", "Alle soorten acties met betrekking tot leggen van familierechtelijke betrekking tussen ouder(s) en kind"),
    /** Verhuizing. */
    VERHUIZING("Verhuizing", "Alle soorten acties met betrekking tot verhuizingen");

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
    private CategorieSoortActie(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * De naam waarmee de categorie soort actie wordt omschreven..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

    /**
     * De omschrijving voor de categorie soort actie..
     * @return String
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
