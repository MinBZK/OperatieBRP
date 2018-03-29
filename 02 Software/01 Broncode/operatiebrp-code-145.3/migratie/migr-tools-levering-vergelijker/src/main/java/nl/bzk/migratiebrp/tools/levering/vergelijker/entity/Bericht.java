/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.entity;

/**
 * Interface voor berichten met de minimale methodes die implementaties dienen te bevatten.
 */
public interface Bericht {

    /**
     * Geeft het bericht als string terug.
     * @return Het bericht als string.
     */
    String getBerichtInhoud();

    /**
     * Geeft het soort bericht terug.
     * @return Het type bericht.
     */
    String getBerichtType();

    /**
     * Geeft het bericht als string terug.
     * @return Het bericht als string.
     */
    String getBericht();
}
