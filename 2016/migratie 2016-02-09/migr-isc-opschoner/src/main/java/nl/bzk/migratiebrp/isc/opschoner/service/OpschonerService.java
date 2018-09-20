/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.service;

import java.sql.Timestamp;

/**
 * De algemene ingang in de services die Opschonen tellingen biedt.
 */
public interface OpschonerService {

    /**
     * Standaard wachttijd voor opschonen.
     */
    Integer STANDAARD_WACHT_TIJD_IN_UREN = 27;

    /**
     * Opschonen van tellingen die al verwerkt zijn en ouder zijn dan de opgegeven datum.
     * 
     * @param ouderDan
     *            Datum waarvoor de tellingen worden verwijderd.
     * @param wachtTijdInUren
     *            De gebruikte wachttijd in uren voordat een proces wordt opgeschoond.
     */
    void opschonenProcessen(Timestamp ouderDan, Integer wachtTijdInUren);
}
