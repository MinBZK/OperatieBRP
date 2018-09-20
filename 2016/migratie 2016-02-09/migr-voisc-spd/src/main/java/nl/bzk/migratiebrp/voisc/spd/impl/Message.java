/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd.impl;

import nl.bzk.migratiebrp.util.gbav.GBACharacterSet;
import nl.bzk.migratiebrp.voisc.spd.constants.SpdConstants;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Wrapper object voor de berichten van en naar de mailbox. Hiermee wordt de implementatie van de onderliggende laag(bv
 * SSLSocket of Inputstream) gemaskeerd
 *
 */
public class Message {

    private byte[] messageBytes;
    private int length;
    private String message;

    /**
     * Geef de waarde van message from mailbox.
     *
     * @return message from mailbox
     */
    public final byte[] getMessageFromMailbox() {
        return ArrayUtils.clone(messageBytes);
    }

    /**
     * Zet het bericht verkregen van de mailbox en zet de lengte op bericht.lengte - de lengte van de terminator (@see
     * {@link SpdConstants.TERMINATOR}.
     *
     * @param mesgBytes
     *            het bericht dat van de mailbox wordt verkregen.
     */
    public final void setMessageFromMailbox(final byte[] mesgBytes) {
        messageBytes = mesgBytes.clone();
        message = new String(messageBytes);
        // TODO: moet dit niet het volgende zijn:
        // message = GBACharacterSet.convertTeletexByteArrayToString(messageBytes);
        length = messageBytes.length - SpdConstants.TERMINATOR.length();
    }

    /**
     * Geef de waarde van length.
     *
     * @return length
     */
    public final int getLength() {
        return length;
    }

    /**
     * Voeg eerst het bericht van de mailbox en daarna de lengte. Anders wordt deze bij het zetten van het bericht
     * overschreven.
     *
     * @param length
     *            de lengte van het bericht
     */
    public final void setLength(final int length) {
        this.length = length;
    }

    /**
     * Geef de waarde van message.
     *
     * @return message
     */
    public final String getMessage() {
        return message;
    }

    /**
     * Zet het SpD bericht. Verder wordt de SpD-Terminator (@see SpdConstants.TERMINATOR) toegevoegd en wordt het
     * bericht omgezet naar een byte-array
     *
     * @param mesg
     *            Het SpD bericht dat vestuurd moet worden.
     */
    public final void setMessageToMailbox(final String mesg) {
        message = mesg + SpdConstants.TERMINATOR;
        messageBytes = GBACharacterSet.convertTeletexStringToByteArray(message);
        length = messageBytes.length - SpdConstants.LENGTH_LENGTH;
    }
}
