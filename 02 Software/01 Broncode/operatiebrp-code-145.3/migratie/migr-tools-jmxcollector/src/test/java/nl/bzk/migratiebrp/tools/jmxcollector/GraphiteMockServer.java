/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.jmxcollector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Mock server voor Graphite.
 */
public class GraphiteMockServer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private volatile boolean doorgaan = true;
    private int port = -1;
    private List<String> ontvangenMetingen = new ArrayList<>();

    @Override
    public void run() {
        try {
            start();
        } catch (IOException e) {

        }
    }

    public void start() throws IOException {
        LOGGER.info("Start Mock Graphite Server");
        final ServerSocket serverSocket = new ServerSocket(0);
        port = serverSocket.getLocalPort();
        while (doorgaan) {
            final Socket socket = serverSocket.accept();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String regel;
            while ((regel = reader.readLine()) != null){
                LOGGER.info("Ontvangen meting " + regel);
                ontvangenMetingen.add(regel);
            }
        }
        LOGGER.info("Mock Graphite Server gestopt");
    }

    public void stop() {
        LOGGER.info("Stop Mock Graphite Server");
        doorgaan = false;
    }

    public int getPort() {
        return port;
    }

    public List<String> getOntvangenMetingen() {
        LOGGER.info("Ophalen ontvangenMetingen");
        return ontvangenMetingen;
    }
}
