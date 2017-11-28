/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.stream;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.bzk.algemeenbrp.util.jmx.message.Message;

/**
 * Stream to receive {@link Message Messages}.
 *
 * <p>
 * Note: the wrapped InputStream is not closed directly. It is
 * {@link java.net.Socket#close() closed} by the socket.
 * </p>
 */
public final class MessageInputStream {

    private static final Logger LOGGER = Logger.getLogger(MessageInputStream.class.getName());

    private final InputStream input;

    /**
     * Create a new message input stream.
     * @param base base input stream
     */
    public MessageInputStream(final InputStream base) {
        input = base;
    }

    /**
     * Read a message.
     * @return a message
     * @throws IOException on I/O errros
     */
    public Message read() throws IOException {
        synchronized (input) {
            LOGGER.log(Level.FINE, "Reading length (waiting for message) ...");
            final int length = StreamUtils.deserializeLength(readData(4));
            LOGGER.log(Level.FINE, "Reading data (length {0,number,######}) ...", length);
            final Message message;
            try {
                message = StreamUtils.deserializeMessage(readData(length));
            } catch (IOException e) {
                throw new MessageException(e);
            }
            LOGGER.log(Level.FINE, "Message received");
            return message;
        }
    }

    private byte[] readData(final int length) throws IOException {
        final byte[] data = new byte[length];
        int totalRead = 0;
        while (totalRead < length) {
            final int read = input.read(data, totalRead, length - totalRead);
            if (read == -1) {
                throw new EOFException();
            }
            totalRead += read;
            LOGGER.log(Level.FINE, "Read {0} bytes (total bytes read {1}) of {2} bytes expected", new Object[]{read, totalRead, length});
        }
        return data;
    }

}
