/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * De implementatie van een Regel voor een Soort bericht..
 */
public enum RegelSoortBericht {

    /** DUMMY. */
    DUMMY("", "");

    /** regel. */
    private String regel;
    /** soortBericht. */
    private String soortBericht;

    /**
     * Constructor.
     *
     * @param regel de regel
     * @param soortBericht de soortBericht
     *
     */
    private RegelSoortBericht(final String regel, final String soortBericht) {
        this.regel = regel;
        this.soortBericht = soortBericht;
    }

    /**
     * De Regel die wordt geïmplementeerd..
     * @return String
     */
    public String getRegel() {
        return regel;
    }

    /**
     * Het Soort bericht, waarvoor de Regel is geïmplementeerd..
     * @return String
     */
    public String getSoortBericht() {
        return soortBericht;
    }

}
