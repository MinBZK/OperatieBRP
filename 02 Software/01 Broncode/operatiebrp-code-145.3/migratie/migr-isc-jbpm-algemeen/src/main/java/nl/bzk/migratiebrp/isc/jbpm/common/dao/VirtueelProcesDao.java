/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.dao;

import nl.bzk.migratiebrp.isc.runtime.jbpm.model.VirtueelProces;

/**
 * Virtueel proces data access.
 */
public interface VirtueelProcesDao {

    /**
     * Maak een virtueel proces.
     * @return id
     */
    long maak();

    /**
     * Toevoegen van gerelateerde informatie aan een virtueel proces.
     * @param processInstanceId proces instance id
     * @param soortGegeven soort gegeven
     * @param gegeven gegeven
     */
    void toevoegenGerelateerdeGegevens(final long processInstanceId, String soortGegeven, String gegeven);

    /**
     * Lees het virtuele proces.
     * @param id id
     * @return het virtuele proces
     */
    VirtueelProces lees(long id);

    /**
     * Verwijder een virtueel proces.
     * @param id id
     */
    void verwijder(long id);

}
