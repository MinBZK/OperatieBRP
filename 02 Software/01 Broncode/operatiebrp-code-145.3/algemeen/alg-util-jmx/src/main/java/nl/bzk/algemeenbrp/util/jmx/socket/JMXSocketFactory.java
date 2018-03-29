/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.management.remote.JMXServiceURL;

/**
 * Socket factory.
 */
public interface JMXSocketFactory {

    /**
     * Create a new client socket.
     * @param serviceUrl jmx service url
     * @return client socket
     * @throws IOException if an I/O error occurs during the creation of the socket
     */
    Socket createSocket(JMXServiceURL serviceUrl) throws IOException;

    /**
     * Create a new server socket.
     * @param serviceUrl jmx service url
     * @return server socket
     * @throws IOException if an I/O error occurs during the creation of the socket
     */
    ServerSocket createServerSocket(JMXServiceURL serviceUrl) throws IOException;

}
