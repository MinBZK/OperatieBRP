/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.stream;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.bzk.algemeenbrp.util.jmx.message.Message;

/**
 * Stream to send {@link Message Messages}.
 *
 * <p>
 * Note: the wrapped OutputStream is not closed directly. It is
 * {@link java.net.Socket#close() closed} by the socket.
 * </p>
 */
public final class MessageOutputStream {

    private static final Logger LOGGER = Logger.getLogger(MessageOutputStream.class.getName());

    private final BufferedOutputStream output;

    /**
     * Create a new message output stream.
     * @param base base output stream
     * @throws IOException on I/O errors
     */
    public MessageOutputStream(final OutputStream base) throws IOException {
        output = new BufferedOutputStream(base);
        output.flush();
    }

    /**
     * Write a message.
     * @param message message
     * @throws IOException on I/O errors
     */
    public void write(final Message message) throws IOException {
        LOGGER.log(Level.FINE, "Writing message");
        final byte[] data;
        final byte[] length;
        try {
            data = StreamUtils.serializeMessage(message);
            length = StreamUtils.serializeLength(data.length);
        } catch(IOException e) {
            throw new MessageException(e);
        }

        synchronized (output) {
            LOGGER.log(Level.FINE, "Sending data (length {0,number,######}) ...", data.length);
            output.write(length);
            output.write(data);

            LOGGER.log(Level.FINE, "Flushing data");
            output.flush();
        }
    }
}
