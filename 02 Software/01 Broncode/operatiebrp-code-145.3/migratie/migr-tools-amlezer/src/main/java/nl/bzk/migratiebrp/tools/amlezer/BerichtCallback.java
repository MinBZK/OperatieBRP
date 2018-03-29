/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.amlezer;

import nl.bzk.migratiebrp.voisc.database.entities.Bericht;

/**
 * Bericht callback.
 */
public interface BerichtCallback {
    /**
     * Start berichten verwerking.
     * @throws BerichtCallbackException bij fouten
     */
    void start() throws BerichtCallbackException;

    /**
     * Verwerk een bericht.
     * @param bericht bericht
     * @throws BerichtCallbackException bij fouten
     */
    void onBericht(Bericht bericht) throws BerichtCallbackException;

    /**
     * Einde berichten verwerking.
     * @throws BerichtCallbackException bij fouten
     */
    void end() throws BerichtCallbackException;
}
