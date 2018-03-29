/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.database.repository;

import java.util.Collection;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;

/**
 * Mailbox repository.
 */
public interface MailboxRepository {

    /**
     * Haalt een mailbox op bij de opgegeven partijcode.
     * @param partijcode de partijcode wiens gegevens we willen ophalen (bv 190401 of 051801)
     * @throws CentraleMailboxException indien gezocht wordt op de partijcode van de centrale voorziening
     * @return De mailbox gegevens van de gevraagde gemeentecode
     *
     */
    Mailbox getMailboxByPartijcode(final String partijcode);

    /**
     * Haalt een mailbox op bij de opgegeven mailboxnr.
     * @param mailboxnr de mailboxnr wiens gegevens we willen ophalen (bv 1904010 of 0518010)
     * @return De gevraagde mailbox gegevens, of null als niet gevonden
     */
    Mailbox getMailboxByNummer(final String mailboxnr);

    /**
     * Slaat de mailbox op.
     * @param mailbox mailbox
     * @return de opgeslagen mailbox
     */
    Mailbox save(Mailbox mailbox);

    /**
     * Haal alle mailboxen op.
     * @return alle mailboxen
     */
    Collection<Mailbox> getAllMailboxen();
}
