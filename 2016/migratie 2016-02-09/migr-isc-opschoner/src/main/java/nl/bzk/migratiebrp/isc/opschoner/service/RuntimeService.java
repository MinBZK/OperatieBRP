/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.service;

/**
 * Interface voor de lock service.
 */
public interface RuntimeService {

    /**
     * Voeg een lock op de meegegeven runtime toe.
     * 
     * @param runtimeNaam
     *            De naam van de te locken runtime.
     */
    void lockRuntime(String runtimeNaam);

    /**
     * Verwijdert de lock op de meegeven runtime.
     * 
     * @param runtimeNaam
     *            De naam van de te unlocken runtime.
     */
    void unlockRuntime(String runtimeNaam);

}
