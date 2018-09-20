/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.jms;

import nl.moderniseringgba.isc.voisc.entities.Bericht;

/**
 */
public interface QueueService {

    /**
     * Verstuurt een bericht naar de vospgOntvangst queue die weer door de ESB verwerkt kan worden.
     * 
     * @param bericht
     *            Het bericht dat door de ESB verwerkt moet worden.
     */
    void sendMessage(final Bericht bericht);

}
