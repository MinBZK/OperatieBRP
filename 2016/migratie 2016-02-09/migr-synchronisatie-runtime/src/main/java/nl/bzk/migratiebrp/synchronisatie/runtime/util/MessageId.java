/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.util;

import java.util.UUID;

/**
 * Message Id helper.
 */
public final class MessageId {

    private MessageId() {
        // Niet instantieerbaar
    }

    /**
     * Genereer een message id voor een sync antwooord bericht.
     *
     * @return message id
     */
    public static String generateSyncMessageId() {
        return UUID.randomUUID().toString();
    }
}
