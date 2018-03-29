/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers;

import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;

/**
 * Antwoord handler.
 *
 * @param <T> antwoord bericht type
 */
public interface AntwoordHandler<T extends SyncBericht> {

    /**
     * Verwerk het antwoord.
     * @param antwoordBericht antwoord bericht
     * @param messageId message id
     * @param correlationId correlatie id
     */
    void verwerk(final T antwoordBericht, final String messageId, final String correlationId);
}
