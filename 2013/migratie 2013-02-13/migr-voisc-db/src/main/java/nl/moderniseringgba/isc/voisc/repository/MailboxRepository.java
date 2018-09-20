/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.repository;

import java.util.List;

import nl.moderniseringgba.isc.voisc.entities.LogboekRegel;
import nl.moderniseringgba.isc.voisc.entities.Mailbox;

/**
 * Mailbox repository.
 */
public interface MailboxRepository {

    /**
     * Haalt een mailbox op bij de opgegeven gemeentecode.
     * 
     * @param gemeentecode
     *            de gemeentecode wiens gegevens we willen ophalen (bv 1904 of 0518)
     * @return De mailbox gegevens van de gevraagde gemeentecode als er teveel mailboxen bij een gemeentecode wordt
     *         gevonden
     */
    Mailbox getMailboxByGemeentecode(String gemeentecode);

    /**
     * Haalt een mailbox op bij de opgegeven mailboxnummer.
     * 
     * @param mailboxnummer
     *            de mailboxnummer wiens gegevens we willen ophalen (bv 1904010 of 0518010).
     * @return de mailbox gegevens van de gevraagde mailboxnummer
     */
    Mailbox getMailboxByNummer(String mailboxnummer);

    /**
     * Haalt alle mailboxes op.
     * 
     * @return een lijst met alle beschikbare mailboxes uit de database
     */
    List<Mailbox> getMailboxes();

    /**
     * Slaat de logboekregel op die gemaakt is bij een verzend/ontvang cyclus van een mailbox.
     * 
     * @param entry
     *            de logboekregel die opgeslagen moet worden.
     * @return de opgeslagen logboekregel
     */
    LogboekRegel saveLogboekEntry(LogboekRegel entry);

    /**
     * Slaat de mailbox op.
     * 
     * @param mailbox
     *            mailbox
     */
    void save(Mailbox mailbox);

}
