/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

/**
 * DTO objectje voor een enumeratie constructor waarde.
 */
public class EnumeratieConstructorParameter {

    private String waarde;
    private boolean isString;

    /**
     * Constructor.
     *
     * @param waarde de waarde
     * @param isString of het een string is
     */
    public EnumeratieConstructorParameter(final String waarde, final boolean isString) {
        this.waarde = waarde;
        this.isString = isString;
    }

    public String getWaarde() {
        return waarde;
    }

    public boolean isString() {
        return isString;
    }

}
