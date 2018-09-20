/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.util.Date;
import java.util.List;
import java.util.Set;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.entities.StatusEnum;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscDatabaseException;

/**
 * Wrapper voor bewerkingen op VOISC database. Hierin worden de locks en reconnects geregeld.
 */
public interface VoiscDatabase {

    /**
     * Geeft een opgegeven aantal berichten terug dat naar ISC gestuurd gaat worden.
     *
     * Let op: de berichten worden gelocked met een pessimistic lock!
     *
     * @param limit
     *            hoeveel berichten er maximaal worden terug gegeven
     * @return Lijst van berichten dat naar de ESB worden gestuurd
     */
    List<Bericht> leesEnLockNaarIscTeVerzendenBericht(int limit);

    /**
     * Geeft alle berichten terug die verstuurd moeten worden naar de MailboxServer.
     *
     * Let op: de berichten worden gelocked met een pessimistic lock!
     *
     * @param mailboxNr
     *            het nummer van de mailbox waarvan de berichten verstuurd moet worden
     * @return alle berichten voor de desbetreffende mailbox
     */
    List<Bericht> leesEnLockNaarMailboxTeVerzendenBericht(String mailboxNr);

    /**
     * Sla het bericht op in de database.
     *
     * @param bericht
     *            Het bericht dat opgeslagen moet worden.
     * @return het opgeslagen bericht
     */
    Bericht saveBericht(final Bericht bericht);

    /**
     * Zoekt een mailbox bij de opgegeven instantiecode.
     *
     * @param instantiecode
     *            de instantiecode waarmee de mailbox gezocht wordt.
     * @return de mailbox die hoort bij de instantiecode
     */
    Mailbox getMailboxByInstantiecode(Integer instantiecode);

    /**
     * Slaat de mailbox op in de DB.
     *
     * @param mailbox
     *            de mailbox die opgeslagen moet worden
     */
    void saveMailbox(Mailbox mailbox);

    /**
     * Verwijder die berichten die (een van) de opgegeven statussen hebben en verwerkt zijn voor de opgegeven timestamp.
     *
     * @param ouderDan
     *            timestamp
     * @param statussen
     *            te verwijderen statussen
     * @return aantal verwijderde berichten
     * @throws VoiscDatabaseException
     *             als een van de paramaters niet gevuld is
     */
    int verwijderVerwerkteBerichtenOuderDan(Date ouderDan, Set<StatusEnum> statussen) throws VoiscDatabaseException;

    /**
     * Herstel status berichten.
     *
     * @param ouderDan
     *            timestamp
     * @param statusVan
     *            status van
     * @param statusNaar
     *            status naar
     * @return aantal herstelde berichten
     */
    int herstelBerichten(Date ouderDan, StatusEnum statusVan, StatusEnum statusNaar);
}
