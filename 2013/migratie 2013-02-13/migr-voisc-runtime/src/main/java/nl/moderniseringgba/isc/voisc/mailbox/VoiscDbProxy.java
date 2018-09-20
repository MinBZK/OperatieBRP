/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.mailbox;

import java.util.List;

import nl.moderniseringgba.isc.voisc.entities.Bericht;
import nl.moderniseringgba.isc.voisc.entities.LogboekRegel;
import nl.moderniseringgba.isc.voisc.entities.Mailbox;
import nl.moderniseringgba.isc.voisc.exceptions.VoaException;

/**
 *
 */
public interface VoiscDbProxy {

    /**
     * Sla het bericht op in de database.
     * 
     * @param bericht
     *            Het bericht dat opgeslagen moet worden.
     * @param doReconnectLoop
     *            Geeft aan of er een rconnect gedaan moet worden.
     * @param inkomend
     *            Geeft aan of het gaat om een inkomend bericht.
     * @throws VoaException
     *             Als het opslaan mislukt is.
     */
    void saveBericht(final Bericht bericht, final boolean doReconnectLoop, final boolean inkomend)
            throws VoaException;

    /**
     * Vult de eindtijd in van de meegegeven regel en slaat deze op in de database.
     * 
     * @param regel
     *            de logregel die afgerond en opgeslagen moet worden
     */
    void saveLogboekRegel(LogboekRegel regel);

    /**
     * Slaat de mailbox op in de DB.
     * 
     * @param mailbox
     *            de mailbox die opgeslagen moet worden
     */
    void saveMailbox(Mailbox mailbox);

    /**
     * Haalt een bericht op a.d.h.v. het dispatchSequenceNumber. Wordt gebruikt om een bericht dat binnen gekomen is via
     * de MailboxServer op te zoeken
     * 
     * @param dispatchSequenceNumber
     *            het nummer waarmee het bericht gezocht wordt
     * @return het bericht behorende bij het opgegeven numer
     */
    Bericht getBerichtByDispatchSequenceNumber(int dispatchSequenceNumber);

    /**
     * Zoekt een bericht bij het opgegeven eref. Wordt gebruikt om een bericht te zoeken dat eerder is binnen gekomen
     * via ESB, waarbij het bericht vanaf de MailboxServer gekoppeld moet worden.
     * 
     * @param eref
     *            de identificatie waarmee het bericht gezocht wordt.
     * @return het bericht dat bij het eref hoort.
     */
    Bericht getBerichtByEref(String eref);

    /**
     * Zoekt een mailbox bij de opgegeven gemeentecode.
     * 
     * @param gemeentecode
     *            de gemeentecode waarmee de mailbox gezocht wordt.
     * @return de mailbox die hoort bij de gemeentecode
     */
    Mailbox getMailboxByGemeentecode(String gemeentecode);

    /**
     * Geeft alle berichten terug die verstuurd moeten worden naar de MailboxServer.
     * 
     * @param mailboxNr
     *            het nummer van de mailbox waarvan de berichten verstuurd moet worden
     * @return alle berichten voor de desbetreffende mailbox
     */
    List<Bericht> getBerichtToSendMBS(String mailboxNr);

    /**
     * Geetf een opgegeven aantal berichten terug dat naar de ESB gestuurd gaat worden.
     * 
     * @param limit
     *            hoeveel berichten er maximaal worden terug gegeven
     * @return Lijst van berichten dat naar de ESB worden gestuurd
     */
    List<Bericht> getBerichtToSendQueue(int limit);

    /**
     * Stuurt een foutbericht naar de ESB.
     * 
     * @param bericht
     *            het bericht wat de fout heeft veroorzaakt
     * @param error
     *            de foutreden
     * @throws VoaException
     *             Wordt gegooid als er een onverwachte fout optreedt.
     */
    void sendEsbErrorBericht(Bericht bericht, String error) throws VoaException;

    /**
     * Zoekt een bericht bij het opgegeven correlatie ID. Wordt gebruikt om een bericht te zoeken dat eerder is binnen
     * gekomen via de MailboxServer, waarbij het (antwoord)bericht vanaf de ESB gekoppeld moet worden.
     * 
     * @param correlationId
     *            het correlatie ID waarmee het bericht gezocht wordt.
     * @return Het bericht dat hoort bij het opgegeven correlatie ID.
     */
    Bericht getBerichtByEsbMessageId(String correlationId);

    /**
     * Zoekt de mailbox met het mailbox nummer.
     * 
     * @param nummer
     *            Nummer van de mailbox.
     * @return Mailbox
     */
    Mailbox getMailboxByNummer(String nummer);

    /**
     * Haal bericht op.
     * 
     * @param bref
     *            bref
     * @param originator
     *            originator
     * @return bericht
     */
    Bericht getBerichtByBrefAndOrginator(final String bref, final String originator);

}
