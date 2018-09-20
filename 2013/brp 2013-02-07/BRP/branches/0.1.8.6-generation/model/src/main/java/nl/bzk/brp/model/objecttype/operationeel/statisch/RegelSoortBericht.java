/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Deze code is gegenereerd vanuit het metaregister van het BRP, versie 1.0.18.
 *
 */
package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * De implementatie van een Regel voor een Soort bericht.
 * @version 1.0.18.
 */
public enum RegelSoortBericht {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY("", "");

    /** De Regel die wordt geïmplementeerd. */
    private final String regel;
    /** Het Soort bericht, waarvoor de Regel is geïmplementeerd. */
    private final String soortBericht;

    /**
     * Constructor.
     *
     * @param regel De Regel die wordt geïmplementeerd.
     * @param soortBericht Het Soort bericht, waarvoor de Regel is geïmplementeerd.
     *
     */
    private RegelSoortBericht(final String regel, final String soortBericht) {
        this.regel = regel;
        this.soortBericht = soortBericht;
    }

    /**
     * @return De Regel die wordt geïmplementeerd.
     */
    public String getRegel() {
        return regel;
    }

    /**
     * @return Het Soort bericht, waarvoor de Regel is geïmplementeerd.
     */
    public String getSoortBericht() {
        return soortBericht;
    }

}
