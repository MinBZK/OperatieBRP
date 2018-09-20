/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.controle.check;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Check if a host can be reached using a ping.
 */
public final class CheckPing implements Check {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int TIMEOUT = 5000;

    private final String host;

    /**
     * Constructor.
     *
     * @param host
     *            host
     */

    public CheckPing(final String host) {
        this.host = host;
    }

    @Override
    public void check() {
        try {
            LOG.info("PING: Controleren bereikbaarheid: " + host);
            final InetAddress inet = InetAddress.getByName(host);
            LOG.info("PING: Host address: " + inet.getHostAddress());

            if (inet.isReachable(TIMEOUT)) {
                LOG.info("PING: Server is bereikbaar");
            } else {
                LOG.error("PING: Server is NIET bereikbaar");
            }

        } catch (final UnknownHostException e) {
            LOG.error("PING: Onbekende server");
            LOG.error(ExceptionUtils.getStackTrace(e));
        } catch (final IOException e) {
            LOG.error("PING: Fout in verbinding maken met server");
            LOG.error(ExceptionUtils.getStackTrace(e));
        }
    }

}
