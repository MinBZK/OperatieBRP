/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.ssh;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.IOException;
import java.io.InputStream;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * SSH Kanaal.
 *
 * SSH Verbindings properties
 */
public final class SshExecKanaal extends AbstractSchKanaal implements Kanaal {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int SLEEP_TIMEOUT = 1000;
    private static final int BUFFER_SIZE = 1024;

    @Override
    public void doInSession(final Session session, final Bericht bericht) throws JSchException, IOException, KanaalException {
        LOG.info("Opening 'exec' channel");
        final Channel channel = session.openChannel("exec");

        LOG.info("Setting command: {}", bericht.getInhoud());
        ((ChannelExec) channel).setCommand(bericht.getInhoud());

        LOG.info("Setting streams.");
        channel.setInputStream(null);
        channel.setOutputStream(System.out);
        ((ChannelExec) channel).setErrStream(System.err);

        final InputStream in = channel.getInputStream();

        LOG.info("Connecting...");
        channel.connect();

        LOG.info("Reading...");
        final byte[] tmp = new byte[BUFFER_SIZE];
        while (true) {
            while (in.available() > 0) {
                final int i = in.read(tmp, 0, BUFFER_SIZE);
                if (i < 0) {
                    break;
                }
                LOG.info(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                if (in.available() > 0) {
                    continue;
                }
                LOG.info("exit-status: " + channel.getExitStatus());
                break;
            }
            try {
                Thread.sleep(SLEEP_TIMEOUT);
            } catch (final InterruptedException e) {
                LOG.info("Ignoring interrupt ...");
                // Ignore
            }
        }

        LOG.info("Disconnecting...");
        channel.disconnect();
        LOG.info("Done...");

        if (channel.getExitStatus() != 0) {
            throw new KanaalException("Probleem bij uitvoeren commando via ssh; exitcode=" + channel.getExitStatus());
        }
    }
}
