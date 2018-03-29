/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import nl.bzk.migratiebrp.bericht.model.sync.generated.RichtingType;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscQueueException;

/**
 * Wrapper voor bewerking op de queues.
 */
public interface VoiscQueue {

    /**
     * Verstuurt een bericht naar de voiscOntvangst queue die weer door de ESB verwerkt kan worden.
     * @param bericht Het bericht dat door de ESB verwerkt moet worden.
     * @throws VoiscQueueException bij fouten
     */
    void verstuurBerichtNaarIsc(final Bericht bericht) throws VoiscQueueException;

    /**
     * Verstuurt het bericht voor archivering.
     * @param bericht bericht
     * @param richting richting (ingaand of uitgaand)
     * @throws VoiscQueueException bij fouten
     */
    void archiveerBericht(final Bericht bericht, final RichtingType richting) throws VoiscQueueException;
}
