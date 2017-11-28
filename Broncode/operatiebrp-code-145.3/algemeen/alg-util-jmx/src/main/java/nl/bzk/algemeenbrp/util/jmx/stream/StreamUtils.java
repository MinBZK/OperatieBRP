/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import nl.bzk.algemeenbrp.util.jmx.message.Message;
import nl.bzk.algemeenbrp.util.jmx.utils.IOUtils;

/**
 * Stream serialization and deserialization methods.
 */
final class StreamUtils {

    private StreamUtils() {
        throw new IllegalStateException("Do not instantiate");
    }

    /**
     * Serialize message.
     * @param message message
     * @return bytes
     * @throws IOException if an I/O error occurs when serializing the message
     */
    static byte[] serializeMessage(final Message message) throws IOException {
        final byte[] data;

        final ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
        try {
            objectOutput.writeObject(message);
            data = byteOutput.toByteArray();
        } finally {
            IOUtils.closeSilently(byteOutput);
            IOUtils.closeSilently(objectOutput);
        }
        return data;
    }

    /**
     * Deserialize message.
     * @param data bytes
     * @return message
     * @throws IOException if an I/O error occurs when deserializing the message
     */
    static Message deserializeMessage(final byte[] data) throws IOException {
        final ByteArrayInputStream byteInput = new ByteArrayInputStream(data);
        final ObjectInputStream objectInput = new ObjectInputStream(byteInput);

        try {
            return (Message) objectInput.readObject();
        } catch (final ClassCastException | ClassNotFoundException e) {
            final InvalidClassException ice = new InvalidClassException(
                    "Could not deserialize object from received data");
            ice.initCause(e);
            throw ice;
        } finally {
            IOUtils.closeSilently(byteInput);
            IOUtils.closeSilently(objectInput);
        }
    }

    /**
     * Serialize message length.
     * @param length length
     * @return bytes
     */
    static byte[] serializeLength(final int length) {
        final ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(length);
        return buffer.array();
    }

    /**
     * Deserialize message length.
     * @param data bytes
     * @return length
     * @throws IOException if an I/O error occurs when deserializing the length
     */
    static int deserializeLength(final byte[] data) throws IOException {
        if (data.length != 4) {
            throw new IOException("Invalid number of bytes for length");
        }

        final ByteBuffer buffer = ByteBuffer.wrap(data);
        return buffer.getInt();
    }

}
