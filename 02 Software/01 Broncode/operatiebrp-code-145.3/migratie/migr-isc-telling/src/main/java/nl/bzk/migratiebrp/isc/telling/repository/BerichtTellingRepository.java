/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.repository;

import java.sql.Timestamp;
import nl.bzk.migratiebrp.isc.telling.entiteit.BerichtTelling;

/**
 * De Bericht telling Repository.
 */
public interface BerichtTellingRepository {

    /**
     * Slaat de meegegeven tellingBericht op de database.
     * @param berichtTelling De berichtTelling entiteit die moet worden opgeslagen in de database
     * @return de berichtTelling entiteit die opgeslagen is in de database
     */
    BerichtTelling save(BerichtTelling berichtTelling);

    /**
     * Haalt de telling voor het meegegeven berichtType en datum op.
     * @param berichtType Het berichtType waarover de telling gaat.
     * @param kanaal Het kanaal waarover de telling gaat.
     * @param datum De datum waarover de telling gaat.
     * @return De telling voor het meegegeven berichtType en datum.
     */
    BerichtTelling haalBerichtTellingOp(String berichtType, String kanaal, Timestamp datum);

}
