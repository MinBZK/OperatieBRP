/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import nl.bzk.brp.domain.internbericht.bijhoudingsnotificatie.BijhoudingsplanNotificatieBericht;

/**
 * Interface waarmee (JMS) notificaties gedaan.
 */
public interface MutatieNotificatieService {

    /**
     * Verstuurt een notificatie bericht voor de systeemhandeling VerwerkBijhoudingsplan.
     *
     * @param bijhoudingsplanNotificatieBericht bijhoudingsplan notificatiebericht
     */
    void verstuurBijhoudingsNotificatie(final BijhoudingsplanNotificatieBericht bijhoudingsplanNotificatieBericht);

}
