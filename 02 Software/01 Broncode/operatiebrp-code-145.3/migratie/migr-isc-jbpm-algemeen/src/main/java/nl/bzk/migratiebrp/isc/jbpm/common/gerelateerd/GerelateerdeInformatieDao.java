/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.gerelateerd;

import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;

/**
 * Data access voor gerelateerde informatie.
 */
public interface GerelateerdeInformatieDao {

    /**
     * Toevoegen van gerelateerde informatie aan een proces.
     * @param processInstanceId proces instance id
     * @param gerelateerdeInformatie gerelateerd informatie
     */
    void toevoegenGerelateerdeGegevens(final long processInstanceId, GerelateerdeInformatie gerelateerdeInformatie);

}
