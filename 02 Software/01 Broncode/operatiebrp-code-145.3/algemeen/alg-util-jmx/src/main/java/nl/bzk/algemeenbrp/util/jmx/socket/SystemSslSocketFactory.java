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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.remote.JMXServiceURL;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

/**
 * System SSL Socket factory.
 */
public final class SystemSslSocketFactory implements JMXSocketFactory {

    private static final Logger LOGGER = Logger.getLogger(SystemSslSocketFactory.class.getName());

    private final SSLContext sslContext;

    /**
     * Create a new system socket factory.
     * @throws SslConfigurationException if an SSL error occurs when creating the socket factory
     */
    public SystemSslSocketFactory() throws SslConfigurationException {
        try {
            sslContext = SSLContext.getDefault();
        } catch (final NoSuchAlgorithmException e) {
            throw new SslConfigurationException("No SSL Context found", e);
        }

        try {
            sslContext.init(null, null, null);
        } catch (final KeyManagementException e) {
            throw new SslConfigurationException("Unexpected exception initializing SSL context", e);
        }

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
                .createServerSocket(serviceUrl.getPort(), 0, host);

        LOGGER.log(Level.FINE, "Created server socket");
        return baseSslServerSocket;
    }
}
