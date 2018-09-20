/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

/**
 * Helper klasse om poorten te managen voor één of meerdere
 * omgevingen.
 */
public class PoortManager {

    private static final PoortManager POORT_MANAGER = new PoortManager();

    private final Set<Integer> vergevenPoorten = new HashSet<>();

    public static PoortManager get() {
        return POORT_MANAGER;
    }

    /**
     * Singleton
     */
    private PoortManager() {}

    /**
     * @return een poort die gebruikt kan worden. Garandeert dat een
     * een vergeven poort niet nogmaals wordt uitgegeven.
     */
    public int geefVrijePoort() {

        int poort = vindVrijePoort();
        if (vergevenPoorten.contains(poort)) {
            return geefVrijePoort();
        }
        vergevenPoorten.add(poort);
        return poort;
    }

    /**
     * Returns a free port number on localhost.
     * <p/>
     * Heavily inspired from org.eclipse.jdt.launching.SocketUtil (to avoid a dependency to JDT just because of this). Slightly improved with close()
     * missing in JDT. And throws exception instead of returning -1.
     *
     * @return a free port number on localhost
     * @throws IllegalStateException if unable to find a free port
     */
    private int vindVrijePoort() {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(0);
            socket.setReuseAddress(true);
            int port = socket.getLocalPort();
            try {
                socket.close();
            } catch (IOException e) {
                // Ignore IOException on close()
            }
            return port;
        } catch (IOException e) {
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
        throw new IllegalStateException("Kon geen vrije port vinden");
    }
}
