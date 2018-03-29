/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Arrays;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.gbav.GBACharacterSet;
import nl.bzk.migratiebrp.voisc.spd.NoOperationConfirmation;
import nl.bzk.migratiebrp.voisc.spd.Operation;
import nl.bzk.migratiebrp.voisc.spd.OperationParser;
import nl.bzk.migratiebrp.voisc.spd.SpdConstants;
import nl.bzk.migratiebrp.voisc.spd.SpdElementToStringVisitor;
import nl.bzk.migratiebrp.voisc.spd.exception.ParseException;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;

/**
 * SPD Adapter.
 */
final class SpdAdapter {

    /**
     * Delivery report; identifier.
     */
    static final String DELIVERY_REPORT = "DelRep";
    /**
     * Delivery report; separator.
     */
    static final String DELIVERY_REPORT_SEPARATOR = ";";
    /**
     * Delivery report; code blocked.
     */
    static final String DELIVERY_REPORT_CODE_BLOCKED = "1035";
    /**
     * Delivery report; code unknown.
     */
    static final String DELIVERY_REPORT_CODE_UNKNOWN = "1055";

    private static final int ANDERE_DATA_LENGTE_VERWACHT = 1266;

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final InputStream in;

    private final BufferedOutputStream out;

    /**
     * Constructor.
     * @param socket socket
     * @throws IOException als de input- of outputstream niet geopend kan worden.
     */
    SpdAdapter(final Socket socket) throws IOException {
        in = socket.getInputStream();
        out = new BufferedOutputStream(socket.getOutputStream());
    }

    /**
     * Read data from the SSL connection into the buffer.
     * @return message object met daarin het bericht van de mailbox en de lengte.
     * @throws ParseException parse exception
     * @throws VoaException voa exception
     * @throws IOException io exception
     */
    Operation receiveRequest() throws VoaException, IOException {
        return OperationParser.parse(read());
    }

    /**
     * IMPORTANT NOTICE!<br>
     * To prevent dependencies on the platform's default characterset encoding, we will do a bitwise copy of the Java
     * string to a byte array. This byte array is sent to the SSL connection.
     * @param operation het bericht in sPd-formaat dat verstuurd moet worden naar de mailbox
     * @throws IOException io exception
     */
    void sendResponse(final Operation operation) throws IOException {
        write(GBACharacterSet.convertTeletexStringToByteArray(new SpdElementToStringVisitor().visit(operation).toString()));
    }

    /**
     * This method encapsulates the sPd-protocol and reads a given number of bytes into the buffer from the SSLSocket.
     * The following steps are taken:
     * <OL>
     * <LI>Try to read 5 bytes (this is the first length field)
     * <LI>Read as many bytes as specified in the length field
     * <UL>
     * <LI>This should be done in a loop, because the MailboxServer sends the data in blocks of 4kB.
     * </UL>
     * <LI>When the data is read, try to read again 5 bytes for a following length field.
     * <LI>When it is a new length field repeat the process from step 2.
     * <LI>But when the result is "00000", this is the Terminator and no more data should be read.
     * <LI>Exit the method and return the total buffer read.
     * </OL>
     * @return message containing the exact number of read bytes from the SSLSocket and the buffer it read into.
     */
    private byte[] read() throws IOException {
        int offset = 0;
        int length;
        final int nrBytesRead;

        final byte[] buffer = new byte[SpdConstants.MAX_SPD_LEN];

        while (true) {
            // read the expected length
            readLengthField(buffer, offset);

            try {
                // decode length field
                length = Integer.parseInt(
                        GBACharacterSet.convertTeletexByteArrayToString(Arrays.copyOfRange(buffer, offset, offset + SpdConstants.LENGTH_LENGTH)));
                offset += SpdConstants.LENGTH_LENGTH;
            } catch (final NumberFormatException e) {
                LOGGER.warn("Lengte is geen getal: " + e);
                length = 0;
                offset = 0;
            }

            // determine if terminator
            if (length == 0) {
                nrBytesRead = offset;
                break;
            }

            offset = readInput(buffer, offset, length);
        }

        return Arrays.copyOf(buffer, nrBytesRead);
    }

    private int readInput(final byte[] buf, final int offset, final int length) throws IOException {
        int verschuiving = offset;
        int lengte = length;
        while (lengte > 0) {
            // Read message
            final int read = in.read(buf, verschuiving, lengte);
            verschuiving += read;
            lengte -= read;
        }
        return verschuiving;
    }

    /**
     * This method reads 5 next byte into the buffer starting at 'offset' <br>
     * When it's not possible to read 5 bytes an exception is thrown.
     * @param buf buffer into which the data is read
     * @param offset the start offset in array 'buf' at which the data is written
     */
    private void readLengthField(final byte[] buf, final int offset) throws IOException {
        int read;
        // read the expected length
        read = in.read(buf, offset, SpdConstants.LENGTH_LENGTH);
        // when the number of bytes read is not 5 read again and again...
        while (read > 0 && read < SpdConstants.LENGTH_LENGTH) {
            read += in.read(buf, offset + read, SpdConstants.LENGTH_LENGTH - read);
        }

        // the following should never happen
        if (read != SpdConstants.LENGTH_LENGTH) {
            final String message;
            if (read < 0) {
                message = "Connection closed by Mailbox Server";
                LOGGER.warn(message);
            } else {
                message = "Expected 5 bytes length field, read: " + read;
                LOGGER.warn(message);
            }

            sendResponse(new Operation.Builder().add(new NoOperationConfirmation(ANDERE_DATA_LENGTE_VERWACHT)).build());
        }
    }

    /**
     * Write a message to the SSLSocket.
     * @param operation the byte array representing the message to be send.
     */
    private void write(final byte[] operation) throws IOException {
        out.write(operation);
        out.flush();
    }
}
