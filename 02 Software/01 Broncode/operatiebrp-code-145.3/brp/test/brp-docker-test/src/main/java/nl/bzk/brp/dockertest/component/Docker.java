/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.util.Map;

/**
 * Interface voor componenten
 */
public interface Docker {

    /**
     * @return de omgeving waarbinnen de Docker draait
     */
    Omgeving getOmgeving();

    /**
     * @return de logische naam van het component
     */
    default DockerNaam getLogischeNaam() {
        return getDockerInfo().logischeNaam();
    }

    /**
     * @return geeft de docker annotatie
     */
    default DockerInfo getDockerInfo() {
        return getClass().getAnnotation(DockerInfo.class);
    }

    /**
     * @return indicatie of het component is gestart.
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
     * @return een mapping van interne poorten naar externe poorten
     */
    Map<Integer, Integer> getPoortMap();

    /**
     * Hook die uitgevoerd wordt voor de start.
     */
    void preStart();

    /**
     * Start het component.
     */
    void start();

    /**
     * Stop het component.
     */
    void stop();

    String getDockerContainerId();

    /**
     * Enum voor statusindicatie component.
     */
    enum Status {
        INIT,
        GESTART,
        GESTOPT
    }

}
