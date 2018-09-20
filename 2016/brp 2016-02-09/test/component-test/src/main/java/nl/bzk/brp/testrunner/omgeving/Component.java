/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.omgeving;

import java.util.Map;

/**
 * Interface voor componenten
 */
public interface Component {

    /**
     * @return de logische naam van het component
     */
    String getLogischeNaam();

    /**
     * @return indicatie of het component is gestart. Let op!
     * Dit biedt geen garantie dat het functioneel gestart is.
     */
    boolean isGestart();

    /**
     * @return indicatie of het component functioneel is gestart.
     */
    boolean isFunctioneelBeschikbaar();

    /**
     * @return indicatie of het component is gestopt
     */
    boolean isGestopt();

    /**
     * @return de omgeving waar het component toe behoort
     */
    Omgeving getOmgeving();

    /**
     * @return een mapping van interne poorten naar externe poorten
     */
    Map<Integer, Integer> getPoortMap();

    /**
     * Herstart het component
     */
    void herstart();

    void preStart();

    void start();

    void stop();
}
