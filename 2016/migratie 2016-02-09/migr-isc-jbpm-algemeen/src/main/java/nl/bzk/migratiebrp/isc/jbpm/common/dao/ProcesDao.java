/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.dao;

/**
 * Data access voor proces relaties.
 */
public interface ProcesDao {
    /**
     * Leg de relatie vast tussen twee proces instanties.
     * 
     * @param processInstanceIdEen
     *            Eerste proces instantie id
     * @param processInstanceIdTwee
     *            Tweede proces instantie id
     */
    void registreerProcesRelatie(long processInstanceIdEen, long processInstanceIdTwee);

    /**
     * Toevoegen van gerelateerde informatie aan een proces.
     * 
     * @param processInstanceId
     *            proces instance id
     * @param soortGegeven
     *            soort gegevens
     * @param gegeven
     *            gegeven
     */
    void toevoegenGerelateerdGegeven(final long processInstanceId, String soortGegeven, String gegeven);
}
