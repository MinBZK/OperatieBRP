/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.util.Date;

/**
 * De algemene ingang in de services die de Voisc biedt.
 */
public interface VoiscService {

    /**
     * Verstuur de van de mailbox ontvangen berichten naar de ISC.
     */
    void berichtenVerzendenNaarIsc();

    /**
     * Verstuur naar en ontvang van berichten de mailbox.
     */
    void berichtenVerzendenNaarEnOntvangenVanMailbox();

    /**
     * Opschonen van Bericht Entities die al verwerkt zijn en ouder zijn dan de opgegeven datum.
     *
     * @param ouderDan
     *            Date
     * @return int het aantal opgeschoonde berichten
     */
    int opschonenVoiscBerichten(Date ouderDan);

    /**
     * Herstellen bericht status.
     *
     * @param ouderDan
     *            datum waarvoor de status in verwerking gezet moet zijn
     * @return int het aantal herstelde berichten
     */
    int herstellenVoiscBerichten(Date ouderDan);
}
