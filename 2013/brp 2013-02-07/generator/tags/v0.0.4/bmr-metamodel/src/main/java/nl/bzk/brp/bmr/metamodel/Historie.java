/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

/**
 * Soort inhoud.
 */
public enum Historie {
    /**
     * Geen historie.
     */
    G("Geen historie"),
    /**
     * Alleen formele historie.
     */
    F("Alleen formele historie"),
    /**
     * Alleen materiële historie.
     */
    M("Alleen materiële historie"),
    /**
     * Zowel materiële als formele historie.
     */
    B("Zowel materiële als formele historie"),
    /**
     * Bestaansperiode vastleggen
     */
    P("Bestaansperiode vastleggen");

    private String omschrijving;

    /**
     * Constructor voor eenmalige instantiatie in deze file.
     *
     * @param omschrijving de omschrijving.
     */
    private Historie(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

}
