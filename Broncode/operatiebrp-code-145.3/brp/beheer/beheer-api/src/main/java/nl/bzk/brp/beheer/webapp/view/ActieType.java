/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view;

/**
 * Type van actie.
 */
public enum ActieType {

    /** actie op inhoud. */
    INHOUD("Toegevoegd"),
    /** actie aanpassing. */
    AANPASSING("Gewijzigd"),
    /** actie verval. */
    VERVAL("Vervallen"),
    /** actie verval tbv. muts. */
    VERVAL_MUTS("Vervallen tbv levering mutatie");

    private final String omschrijving;

    /**
     * Constructor van enum.
     * @param omschrijving omschrijving van de enum
     */
    ActieType(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    /**
     * Geeft de omschrijving van het actie type.
     *
     * @return De omschrijving van het actie type.
     */
    public String getOmschrijving() {
        return omschrijving;
    }
}
