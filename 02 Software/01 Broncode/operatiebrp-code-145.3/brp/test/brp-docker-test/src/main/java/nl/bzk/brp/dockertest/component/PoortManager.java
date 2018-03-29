/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.BiFunction;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.test.common.TestclientExceptie;
import org.springframework.util.SocketUtils;

/**
 * Helper klasse om poorten te managen voor één of meerdere
 * omgevingen.
 */
final class PoortManager {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final BiFunction<Integer, Docker, Integer> POORT_MANAGER =
            Environment.isJenkinsRun() ? new TelnetPoortManager() : new LocalPoortManager();

    private static final Set<Integer> VERGEVEN_POORTEN = new HashSet<>();

    /**
     * Singleton
     */
    private PoortManager() {
    }

    /**
     * Geeft een beschikbare vrije poort.
     * @return een poortnummer
     */
    static synchronized int geefVrijePoort(Integer externeVoorkeurspoort, final Docker docker) {
        final Integer externePoort = POORT_MANAGER.apply(externeVoorkeurspoort, docker);
        if (!VERGEVEN_POORTEN.contains(externePoort)) {
            VERGEVEN_POORTEN.add(externePoort);
            return externePoort;
        }
        return geefVrijePoort(null, docker);
    }

    private static class LocalPoortManager implements BiFunction<Integer, Docker, Integer> {

        LocalPoortManager() {
            LOGGER.info("LocalPoortManager");
        }

        @Override
        public Integer apply(Integer voorkeurspoort, Docker docker) {
            return vindVrijePoort(voorkeurspoort);
        }

        /**
         * Returns a free port number on localhost.
         * <p/>
         * Heavily inspired from org.eclipse.jdt.launching.SocketUtil (to avoid a dependency to JDT just because of this). Slightly improved with close()
         * missing in JDT. And throws exception instead of returning -1.
         * @param voorkeurspoort een voorkeurspoort
         * @return a free port number on localhost
         * @throws IllegalStateException if unable to find a free port
         */
        private int vindVrijePoort(final Integer voorkeurspoort) {
            if (voorkeurspoort != null && isVoorkeurspoortBeschikbaar(voorkeurspoort)) {
                return voorkeurspoort;
            }
            return SocketUtils.findAvailableTcpPort();
        }

        private boolean isVoorkeurspoortBeschikbaar(final Integer voorkeurspoort) {
            ServerSocket socket = null;
            try {
                socket = new ServerSocket(voorkeurspoort);
                socket.setReuseAddress(true);
                socket.getLocalPort();
                try {
                    socket.close();
                } catch (IOException e) {
                    // Ignore IOException on close()
                }
                return true;
            } catch (IOException e) {
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                    }
                }
            }
            return false;
        }
    }

    private static final class TelnetPoortManager implements BiFunction<Integer, Docker, Integer> {

        private static final int MAX_RETRIES = 20;
        private static final int LOWER_BOUND = 1024;
        private static final int UPPER_BOUND = (int) (Math.pow(2, 16) - 1);
        private final Random random = new Random();

        TelnetPoortManager() {
            LOGGER.info("TelnetPoortManager");
        }

        @Override
        public Integer apply(Integer voorkeurspoort, Docker docker) {
            return applyMetMaxRetries(voorkeurspoort, MAX_RETRIES, docker);
        }

        private Integer applyMetMaxRetries(final Integer voorkeurspoort, final int retries, final Docker docker) {

            if (retries == 0) {
                throw new TestclientExceptie("Kan geen vrije poort vinden!");
            }
            final int mogelijkePoort = voorkeurspoort == null ? nextRandom() : voorkeurspoort;
            final ProcessBuilder processBuilder = new ProcessBuilder("telnet",
                    docker.getOmgeving().getDockerHostname(), String.valueOf(mogelijkePoort));
            processBuilder.redirectErrorStream(true);
            try {
                final ProcessOutputReader processOutputReader = new ProcessOutputReader(processBuilder.start());
                processOutputReader.start();
                processOutputReader.join();

                final String output = processOutputReader.geefOutput();
                if (output.contains("Connection refused")) {
                    LOGGER.debug("Poort {} beschikbaar!", mogelijkePoort);
                    return mogelijkePoort;
                }
            } catch (Exception e) {
                LOGGER.error("Kan poort niet benaderen; poging [%d/%d]", MAX_RETRIES - retries, MAX_RETRIES, e);
                return applyMetMaxRetries(null, retries - 1, docker);
            }
            LOGGER.debug("Poort {} bezet; poging [%d/%d]", MAX_RETRIES - retries, MAX_RETRIES, mogelijkePoort);
            return applyMetMaxRetries(null, retries - 1, docker);
        }

        private Integer nextRandom() {
            int randomPoort = random.nextInt(UPPER_BOUND);
            if (randomPoort <= LOWER_BOUND || VERGEVEN_POORTEN.contains(randomPoort)) {
                return nextRandom();
            }
            return randomPoort;
        }
    }
}
