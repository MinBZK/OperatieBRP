/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.util.Set;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;

/**
 * Mailbox configuratie.
 */
public interface MailboxConfiguratie {

    /**
     * Bepaal de te bedienen mailboxen.
     * @return mailboxen
     */
    Set<Mailbox> bepaalMailboxen();

    /**
     * Toon de te bedienen mailboxen.
     * @return String met daarin de te bedienen mailboxen
     */
    String toonMailboxen();
}
