/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.mailbox;


/**
 * This interface should be implemented to establish a (SSL) connection to the MailboxServer. To use this interface
 * correctly the following steps should executed
 * <OL>
 * <LI>init()
 * <LI>connect()
 * <LI>read() and/or write()
 * <LI>disconnect()
 * </OL>
 */
public interface Connection {

    /**
     * Write a byte array with a composed sPd-message to the socket.
     * 
     * @param sPdMessage
     *            the byte array representation of a sPd-message
     */
    void write(Message sPdMessage);

    /**
     * Read a byte array from the socket.
     * 
     * @return Message object with the byte array and the number of bytes read
     */
    Message read();

    /**
     * Open a connection.
     * 
     */
    void connect();

    /**
     * Close the existing connection.
     */
    void disconnect();

}
