/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.mailbox.client.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.UnrecoverableKeyException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.gbav.GBACharacterSet;
import nl.bzk.migratiebrp.voisc.mailbox.client.Connection;
import nl.bzk.migratiebrp.voisc.mailbox.client.exception.ConnectionException;
import nl.bzk.migratiebrp.voisc.spd.SpdConstants;
import nl.bzk.migratiebrp.voisc.spd.exception.MessagesCodes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

/**
 * This class represents a SSL connection. This class is used to communicate with the MailboxServer.
 * <P>
 * NOTE: because this class is used by VOISC (and VOISC needs to setup a SSL connection with client and server
 * authentication), this class needs to know the filenames of the certificates (Java Keystores)
 */
public class SslConnectionImpl implements Connection {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final int DEFAULT_PORT_NUMBER = 24;

    /**
     * Default allowed protocollen obv 'GOEDE' versies uit ICT beveiligingsrichtlijnen.
     */
    private static final String DEFAULT_ALLOWED_PROTOCOLS = "TLSv1.2";

    /**
     * Default allowed ciphersuites obv 'GOEDE' ciphersuites uit ICT beveiligingsrichtlijnen.
     */
    private static final String DEFAULT_ALLOWED_CIPHERSUITES = "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384"
            + "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,"
            + "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,"
            + "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,"
            + "TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384,"
            + "TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256,"
            + "TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384,"
            + "TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256,"
            + "TLS_RSA_WITH_AES_256_GCM_SHA384,"
            + "TLS_RSA_WITH_AES_128_GCM_SHA256";

    @Value("classpath:keystores/keystore.jks")
    private Resource keystore;

    @Value("classpath:keystores/truststore.jks")
    private Resource truststore;

    @Value("${voisc.tls.allowed_protocols:" + DEFAULT_ALLOWED_PROTOCOLS + "}")
    private String[] tlsAllowedProtocols;

    @Value("${voisc.tls.allowed_cipher_suites:" + DEFAULT_ALLOWED_CIPHERSUITES + "}")
    private String[] tlsAllowedCipherSuites;

    private SSLSocket socket;

    private OutputStream out;

    private InputStream in;

    private int portNumber = DEFAULT_PORT_NUMBER;

    private String hostname = "localhost";

    private String keyPassword = "";

    private String keystorePassword = "";

    private String trustStorePassword = "";

    /**
     * Constructor.
     */
    public SslConnectionImpl() {
        //default constructor
    }

    /**
     * Geef de waarde van hostname.
     * @return hostname
     */
    final String getHostname() {
        return hostname;
    }

    /**
     * Zet de waarde van hostname.
     * @param hostname hostname
     */
    public final void setHostname(final String hostname) {
        this.hostname = hostname;
    }

    /**
     * Geef de waarde van port number.
     * @return port number
     */
    final int getPortNumber() {
        return portNumber;
    }

    /**
     * Zet de waarde van port number.
     * @param portNumber port number
     */
    public final void setPortNumber(final int portNumber) {
        this.portNumber = portNumber;
    }

    /**
     * Zet de waarde van password.
     * @param password password
     */
    public final void setKeyPassword(final String password) {
        keyPassword = password;
    }

    /**
     * Zet de waarde van keystorePassword.
     * @param keystorePassword password
     */
    public final void setKeystorePassword(final String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }

    /**
     * Zet de waarde van trustStorePassword.
     * @param trustStorePassword password
     */
    final void setTrustStorePassword(final String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    /**
     * Write a message to the SSLSocket.
     * @param operation the byte array representing the message to be send.
     */
    @Override
    public final void write(final byte[] operation) {
        reconnect();
        try {
            out.write(operation);
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
     * @return message containing the exact number of read bytes from the SSLSocket and the buffer it read into.
     */
    @Override
    public final byte[] read() {
        int offset = 0;
        int length;
        int nrBytesRead = 0;
        reconnect();

        final byte[] buffer = new byte[SpdConstants.MAX_SPD_LEN];

        try {
            while (true) {
                // read the expected length
                readLengthField(buffer, offset);

                // decode length field
                length =
                        Integer.parseInt(
                                GBACharacterSet.convertTeletexByteArrayToString(Arrays.copyOfRange(buffer, offset, offset + SpdConstants.LENGTH_LENGTH)));
                offset += SpdConstants.LENGTH_LENGTH;

                // determine if terminator
                if (length == 0) {
                    nrBytesRead = offset;
                    break;
                }
                offset = readInput(buffer, offset, length);
            }
        } catch (final IOException e) {
            throw new ConnectionException(MessagesCodes.ERRMSG_VOSPG_SSL_COMMUNICATION_ERROR, null, e);
        } catch (final NumberFormatException e) {
            LOGGER.warn("Lengte is geen getal: " + e);
            nrBytesRead = 0;
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
    private void readLengthField(final byte[] buf, final int offset) {
        int read;
        try {
            // read the expected length
            read = in.read(buf, offset, SpdConstants.LENGTH_LENGTH);
            // when the number of bytes read is not 5 read again and again...
            while (read > 0 && read < SpdConstants.LENGTH_LENGTH) {
                read += in.read(buf, offset + read, SpdConstants.LENGTH_LENGTH - read);
            }
        } catch (final IOException e) {
            throw new ConnectionException(MessagesCodes.ERRMSG_VOSPG_SSL_COMMUNICATION_ERROR, null, e);
        }

        // the following should never happen
        if (read != SpdConstants.LENGTH_LENGTH) {
            final String msg;
            if (read < 0) {
                msg = "Connection closed by Mailbox Server";
                LOGGER.warn(msg);
            } else {
                msg = "Expected 5 bytes length field, read: " + read;
                LOGGER.warn(msg);
            }
            throw new ConnectionException(MessagesCodes.ERRMSG_VOSPG_SSL_COMMUNICATION_ERROR, new Object[]{msg});
        }
    }

    /**
     * This method creates a SSLSocket on the given host and portnumber. It uses a Keystore with a client certificate to
     * setup the SSL connection.
     */
    @Override
    public final void connect() {
        // Key manager
        final KeyStore ks = KeystoreUtil.loadKeystore(keystore, keystorePassword.toCharArray());

        // Trust manager
        final KeyStore ts = KeystoreUtil.loadKeystore(truststore, trustStorePassword.toCharArray());
        final X509TrustManager voiscTM = new VoiscTrustManager(ts);
        final TrustManager[] myTMs = new TrustManager[]{voiscTM};

        try {
            // Controle dat het keypassword ok is
            ks.getKey(ks.aliases().nextElement(), keyPassword.toCharArray());
            final KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, keyPassword.toCharArray());

            // SSL Context
            final SSLContext sslcontext;
            sslcontext = SSLContext.getInstance(tlsAllowedProtocols[0]);
            sslcontext.init(kmf.getKeyManagers(), myTMs, null);
            final SSLSocketFactory f = sslcontext.getSocketFactory();
            socket = (SSLSocket) f.createSocket();

            socket.setEnabledProtocols(filter(tlsAllowedProtocols, socket.getSupportedProtocols()));
            socket.setEnabledCipherSuites(filter(tlsAllowedCipherSuites, socket.getSupportedCipherSuites()));

            LOGGER.debug("Verbinding openen naar " + hostname + ":" + portNumber);
            socket.connect(new InetSocketAddress(hostname, portNumber));
            LOGGER.debug("SSL initialiseren");
            socket.startHandshake();
            out = socket.getOutputStream();
            in = socket.getInputStream();
        } catch (final UnrecoverableKeyException e) {
            LOGGER.error("Password niet correct. Kan certificaat niet openen", e);
            throw new ConnectionException(MessagesCodes.ERRMSG_VOSPG_SSL_INCORRECT_CERT_PASSWORD, null, e);
        } catch (final GeneralSecurityException e) {
            LOGGER.error("Kan de SSL-keystore niet laden", e);
            throw new ConnectionException(MessagesCodes.ERRMSG_VOSPG_SSL_CONNECTION_ERROR, null, e);
        } catch (final IOException e) {
            LOGGER.error("Kan de verbinding niet opzetten (hostname=" + hostname + ", port=" + portNumber + ")", e);
            throw new ConnectionException(MessagesCodes.ERRMSG_VOSPG_SSL_CONNECTION_ERROR, null, e);
        }
    }

    /**
     * Close connection.
     */
    @Override
    public final void disconnect() {
        try {
            LOGGER.debug("Verbinding naar " + hostname + " wordt gesloten");
            socket.close();
        } catch (final IOException e) {
            throw new ConnectionException(MessagesCodes.ERRMSG_VOSPG_SSL_CONNECTION_ERROR, null, e);
        }
    }

    @Override
    public final void close() {
        disconnect();
    }

    private String[] filter(final String[] allAllowed, final String[] allSupported) {
        final List<String> supportedList = Arrays.asList(allSupported);
        return Stream.of(allAllowed).filter(supportedList::contains).collect(Collectors.toList()).toArray(new String[]{});
    }
}
