/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.mailbox.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import nl.moderniseringgba.isc.voisc.constants.MessagesCodes;
import nl.moderniseringgba.isc.voisc.constants.SpdConstants;
import nl.moderniseringgba.isc.voisc.exceptions.ConnectionException;
import nl.moderniseringgba.isc.voisc.exceptions.SslPasswordException;
import nl.moderniseringgba.isc.voisc.mailbox.Connection;
import nl.moderniseringgba.isc.voisc.mailbox.Message;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

/**
 * This class represents a SSL connection. This class is used to communicate with the MailboxServer.
 * <P>
 * NOTE: because this class is used by VOISC (and VOISC needs to setup a SSL connection with client and server
 * authentication), this class needs to know the filenames of the certificates (Java Keystores)
 * 
 */
// CHECKSTYLE:OFF Enige fout nu is Fan-out
public class SslConnectionImpl implements Connection {
    // CHECKSTYLE:ON
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final int SPD_LENGTH_FIELD = 5;

    private static final int DEFAULT_PORT_NUMBER = 24;

    private static final String PREFFERED_CIPHER_SUITE = "SSL_RSA_WITH_3DES_EDE_CBC_SHA";

    @Value("classpath:keystores/keystore.jks")
    private Resource keystore; // NOSONAR kan geen static zijn, dan vult Spring hem niet

    @Value("classpath:keystores/truststore.jks")
    private Resource truststore; // NOSONAR kan geen static zijn, dan vult Spring hem niet

    private SSLSocket socket;

    private OutputStream out;

    private InputStream in;

    private int portNumber = DEFAULT_PORT_NUMBER;

    private String hostname = "localhost";

    private String sslPwd = "";

    /**
     * Constructor.
     * 
     */
    public SslConnectionImpl() {
    }

    public final void setHostname(final String hostname) {
        this.hostname = hostname;
    }

    public final String getHostname() {
        return hostname;
    }

    public final void setPortNumber(final int portNumber) {
        this.portNumber = portNumber;
    }

    public final int getPortNumber() {
        return portNumber;
    }

    public final void setPassword(final String password) {
        sslPwd = password;
    }

    /**
     * Write a message to the SSLSocket.
     * 
     * @param sPdMessage
     *            the byte array representing the message to be send.
     */
    @Override
    public final void write(final Message sPdMessage) {
        reconnect();
        try {
            out.write(sPdMessage.getMessageFromMailbox());
            out.flush();
        } catch (final IOException e) {
            LOGGER.error("Kan niet schrijven naar " + hostname, e);
            throw new ConnectionException(MessagesCodes.ERRMSG_VOSPG_SSL_COMMUNICATION_ERROR, null, e);
        }
    }

    private void reconnect() {
        if (socket.isClosed()) {
            // re-open socket
            connect();
        }
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
     * 
     * @return message containing the exact number of read bytes from the SSLSocket and the buffer it read into.
     */
    @Override
    public final Message read() {
        int offset = 0;
        int length = 0;
        int nrBytesRead = 0;
        reconnect();

        final byte[] buf = new byte[SpdConstants.MAX_SPD_LEN];

        try {
            while (true) {
                // read the expected length
                readLengthField(buf, offset);

                try {
                    // decode length field
                    length = Integer.parseInt(new String(buf, offset, SPD_LENGTH_FIELD));
                    offset += SPD_LENGTH_FIELD;
                } catch (final NumberFormatException e) {
                    LOGGER.warn("Lengte is geen getal: " + e);
                    nrBytesRead = -1;
                    break;
                }

                // determine if terminator
                if (length == 0) {
                    // don't count the terminator length in the total length
                    nrBytesRead = offset - SpdConstants.TERMINATOR.length();
                    break;
                }
                offset = readInput(buf, offset, length);
            }
        } catch (final IOException e) {
            throw new ConnectionException(MessagesCodes.ERRMSG_VOSPG_SSL_COMMUNICATION_ERROR, null, e);
        }

        final Message message = new Message();
        message.setMessageFromMailbox(buf);
        message.setLength(nrBytesRead);
        return message;
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
     * 
     * @param buf
     *            buffer into which the data is read
     * @param offset
     *            the start offset in array 'buf' at which the data is written
     */
    private void readLengthField(final byte[] buf, final int offset) {
        int read = 0;
        try {
            // read the expected length
            read = in.read(buf, offset, SPD_LENGTH_FIELD);
            // when the number of bytes read is not 5 read again and again...
            while (read > 0 && read < SPD_LENGTH_FIELD) {
                read += in.read(buf, offset + read, SPD_LENGTH_FIELD - read);
            }
        } catch (final IOException e) {
            throw new ConnectionException(MessagesCodes.ERRMSG_VOSPG_SSL_COMMUNICATION_ERROR, null, e);
        }

        // the following should never happen
        if (read != SPD_LENGTH_FIELD) {
            String msg = "";
            if (read < 0) {
                msg = "Connection closed by Mailbox Server";
                LOGGER.warn(msg);
            } else {
                msg = "Expected 5 bytes length field, read: " + read;
                LOGGER.warn(msg);
            }
            throw new ConnectionException(MessagesCodes.ERRMSG_VOSPG_SSL_COMMUNICATION_ERROR, new Object[] { msg });
        }
    }

    /**
     * This method creates a SSLSocket on the given host and portnumber. It uses a Keystore with a client certificate to
     * setup the SSL connection.
     */
    @Override
    public final void connect() {
        final String sslVersion = "SSLv3";
        final char[] keypassword = sslPwd.toCharArray();
        KeyStore ks;
        try {
            ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(keystore.getFile()), null);
            ks.getKey("1", keypassword);
            final KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, keypassword);
            SSLContext sslcontext = SSLContext.getInstance(sslVersion);
            sslcontext = SSLContext.getInstance(sslVersion);
            final X509TrustManager vospgTM = new VoiscTrustManager(truststore.getFile());
            final TrustManager[] myTMs = new TrustManager[] { vospgTM };
            sslcontext.init(kmf.getKeyManagers(), myTMs, null);
            final SSLSocketFactory f = sslcontext.getSocketFactory();
            socket = (SSLSocket) f.createSocket();

            final String[] enabled = { PREFFERED_CIPHER_SUITE };
            socket.setEnabledCipherSuites(enabled);
            LOGGER.info("Verbinding openen naar " + hostname + ":" + portNumber);
            socket.connect(new InetSocketAddress(hostname, portNumber));
            out = socket.getOutputStream();
            in = socket.getInputStream();
        } catch (final UnrecoverableKeyException e) {
            LOGGER.error("Password niet correct. Kan certificaat niet openen", e);
            throw new SslPasswordException(MessagesCodes.ERRMSG_VOSPG_SSL_INCORRECT_CERT_PASSWORD, null, e);
        } catch (final GeneralSecurityException e) {
            LOGGER.error("Kan de SSL-keystore niet laden", e);
            throw new ConnectionException(MessagesCodes.ERRMSG_VOSPG_SSL_CONNECTION_ERROR, null, e);
        } catch (final IOException e) {
            LOGGER.error("Kan de verbinding niet opzetten", e);
            throw new ConnectionException(MessagesCodes.ERRMSG_VOSPG_SSL_CONNECTION_ERROR, null, e);
        }
    }

    /**
     * Close connection.
     */
    @Override
    public final void disconnect() {
        try {
            LOGGER.info("Verbinding naar " + hostname + " wordt gesloten");
            socket.close();
        } catch (final IOException e) {
            throw new ConnectionException(MessagesCodes.ERRMSG_VOSPG_SSL_CONNECTION_ERROR, null, e);
        }
    }
}
