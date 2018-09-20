/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.database.repository;

import java.util.List;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;

/**
 * Mailbox repository.
 */
public interface MailboxRepository {

    /**
     * Haalt een mailbox op bij de opgegeven instantiecode.
     *
     * @param instantiecode
     *            de instantiecode wiens gegevens we willen ophalen (bv 1904 of 0518)
     * @return De mailbox gegevens van de gevraagde gemeentecode
     */
    Mailbox getMailboxByInstantiecode(final int instantiecode);

    /**
     * Haalt een mailbox op bij de opgegeven mailboxnr.
     *
     * @param mailboxnr
     *            de mailboxnr wiens gegevens we willen ophalen (bv 1904010 of 0518010)
     * @return De gevraagde mailbox gegevens, of null als niet gevonden
     */
    Mailbox getMailboxByNummer(final String mailboxnr);

    /**
     * Haalt alle gemeente mailboxes op.
     *
     * @return een lijst met alle gemeente mailboxes uit de database
     */
    List<Mailbox> getGemeenteMailboxes();

    /**
     * Haalt alle centrale mailboxes op.
     *
     * @return een lijst met alle centrale mailboxes uit de database
     */
    List<Mailbox> getCentraleMailboxes();

    /**
     * Haalt alle afnemer mailboxes op.
     *
     * @return een lijst met alle afnemer mailboxes uit de database
     */
    List<Mailbox> getAfnemerMailboxes();

    /**
     * Slaat de mailbox op.
     *
     * @param mailbox
     *            mailbox
     * @return de opgeslagen mailbox
     */
    Mailbox save(Mailbox mailbox);

}
