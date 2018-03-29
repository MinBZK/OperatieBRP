/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.socket;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.remote.JMXServiceURL;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;

/**
 * Anonymous SSL Socket factory.
 */
public final class AnonymousSslSocketFactory implements JMXSocketFactory {

    private static final int BACKLOG = 50;
    private static final Logger LOGGER = Logger.getLogger(AnonymousSslSocketFactory.class.getName());

    private static final String[] TLS_ALLOWED_PROTOCOLS = {"TLSv1.2",};

    private static final String[] ANONYMOUS_CIPHERSUITES = {
            "TLS_DH_anon_WITH_AES_128_GCM_SHA256",
            "TLS_DH_anon_WITH_AES_128_CBC_SHA256",
            "TLS_DH_anon_WITH_AES_128_CBC_SHA",};

    private final SSLContext sslContext;
    private final String[] enabledProtocols;
    private final String[] enabledCiphersuites;

    /**
     * Create a new ssl socket factory.
     * @throws SslConfigurationException if an SSL error occurs when creating the socket factory
     */
    public AnonymousSslSocketFactory() throws SslConfigurationException {
        try {
            sslContext = SSLContext.getInstance(TLS_ALLOWED_PROTOCOLS[0]);
        } catch (final NoSuchAlgorithmException e) {
            throw new SslConfigurationException("Algorithm '" + TLS_ALLOWED_PROTOCOLS[0] + "' not found", e);
        }

        try {
            sslContext.init(new KeyManager[]{}, new TrustManager[]{}, null);
        } catch (final KeyManagementException e) {
            throw new SslConfigurationException("Unexpected exception initializing SSL context", e);
        }

        // Only allow TLS v1.2
        enabledProtocols = combine(new String[]{}, sslContext.getSupportedSSLParameters().getProtocols(), TLS_ALLOWED_PROTOCOLS);

        // Add anonymous ciphersuites to default enabled ciphersuites
        // (also allow the 'default' ciphersuites so we can default connect safely to a server with a certificate)
        enabledCiphersuites =
                combine(sslContext.getDefaultSSLParameters().getCipherSuites(), sslContext.getSupportedSSLParameters().getCipherSuites(),
                        ANONYMOUS_CIPHERSUITES);
    }

    private static String[] combine(final String[] defaultValues, final String[] supportedValues, final String[] wantedValues)
            throws SslConfigurationException {
        final List<String> supported = Arrays.asList(supportedValues);
        final List<String> result = new ArrayList<>(supportedValues.length);
        result.addAll(Arrays.asList(defaultValues));

        boolean valueAdded = false;
        for (final String wantedValue : wantedValues) {
            if (result.contains(wantedValue)) {
                valueAdded = true;
            } else if (supported.contains(wantedValue)) {
                result.add(wantedValue);
                valueAdded = true;
            }
        }

        if (!valueAdded) {
            throw new SslConfigurationException("None of the specified values could be added.\n   Wanted: " + Arrays.asList(wantedValues)
                    + "\n   Supported: " + supported);
        }
        return result.toArray(new String[result.size()]);
    }

    /**
     * Create a client socket.
     * @param serviceUrl jmx service url
     * @return client socket
     * @throws IOException if an I/O error occurs when creating the socket
     */
    @Override
    public Socket createSocket(final JMXServiceURL serviceUrl) throws IOException {
        final SSLSocket baseSslSocket = (SSLSocket) sslContext.getSocketFactory().createSocket(serviceUrl.getHost(),
                serviceUrl.getPort());
        baseSslSocket.setEnabledProtocols(enabledProtocols);
        baseSslSocket.setEnabledCipherSuites(enabledCiphersuites);
        baseSslSocket.setKeepAlive(true);

        LOGGER.log(Level.FINE, "Created client socket");
        return baseSslSocket;
    }

    /**
     * Create a server socket.
     * @param serviceUrl jmx service url
     * @return server socket
     * @throws IOException if an I/O error occurs when creating the socket
     */
    @Override
    public ServerSocket createServerSocket(final JMXServiceURL serviceUrl) throws IOException {
        final InetAddress host = InetAddress.getByName(serviceUrl.getHost());
        final SSLServerSocket baseSslServerSocket = (SSLServerSocket) sslContext.getServerSocketFactory()
                .createServerSocket(serviceUrl.getPort(), BACKLOG, host);
        baseSslServerSocket.setEnabledProtocols(enabledProtocols);
        baseSslServerSocket.setEnabledCipherSuites(enabledCiphersuites);

        LOGGER.log(Level.FINE, "Created server socket");
        return baseSslServerSocket;
    }
}
