/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.dao;

import java.util.Collection;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Data access voor taak gerelateerde informatie.
 */
public interface TaakGerelateerdeInformatieDao {

    /**
     * Registratie van gerelateerde a-nummers aan een taak.
     * @param taskInstance taak instantie
     * @param administratienummers administratienummers
     */
    void registreerAdministratienummers(final TaskInstance taskInstance, String... administratienummers);

    /**
     * Zoeken van taken obv administratienummers.
     * @param administratienummers administratienummers
     * @return lijst van gerelateerde taak instanties (kan een lege lijst zijn)
     */
    Collection<TaskInstance> zoekOpAdministratienummers(String... administratienummers);
}
