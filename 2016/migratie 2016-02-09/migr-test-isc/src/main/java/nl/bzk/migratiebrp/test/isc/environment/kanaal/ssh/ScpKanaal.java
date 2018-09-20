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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;

/**
 * SCP Kanaal.
 *
 * Bericht staat uit dubbele regels. Eerste regel is de remote path, tweede regel is het lokale te kopieren bestand.
 */
public final class ScpKanaal extends AbstractSchKanaal {

    private static final int BUFFER_SIZE = 1024;

    @Override
    protected void doInSession(final Session session, final Bericht bericht) throws IOException, JSchException, KanaalException {
        final String[] regels = bericht.getInhoud().replaceAll("\r", "").split("\n");
        if (regels.length % 2 != 0) {
            throw new KanaalException("Het input bericht moet bestaan uit dubbele regels. Eerste regel is de remote path, "
                                      + "tweede regel is het lokale te kopieren bestand.");
        }

        for (int i = 0; i < regels.length; i = i + 2) {
            verstuurBestand(session, regels[i], regels[i + 1]);
        }

    }

    private void verstuurBestand(final Session session, final String remotePath, final String localFileName) throws KanaalException, JSchException,
        IOException
    {
        final File localFile = new File(localFileName);
        if (!localFile.exists() || !localFile.isFile() || !localFile.canRead()) {
            throw new KanaalException("Lokaal bestand '" + localFileName + "' kan niet worden gelezen.");
        }

        // exec 'scp -t rfile' remotely
        String command = "scp " + " -t " + remotePath + (remotePath.endsWith("/") ? "" : "/") + localFile.getName();
        final Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);

        // get I/O streams for remote scp
        final OutputStream out = channel.getOutputStream();
        final InputStream in = channel.getInputStream();

        channel.connect();
        checkAck(in, "Kan de channel niet connecten");

        // send "C0644 filesize filename", where filename should not include '/'
        final long filesize = localFile.length();
        command = "C0644 " + filesize + " " + localFile.getName() + "\n";
        out.write(command.getBytes());
        out.flush();
        checkAck(in, "Het kopieren kon niet geinitieerd worden (bestaat de remote path?)");

        // send a content of lfile
        try (FileInputStream fis = new FileInputStream(localFile)) {
            final byte[] buf = new byte[BUFFER_SIZE];
            while (true) {
                final int len = fis.read(buf, 0, buf.length);
                if (len <= 0) {
                    break;
                }
                // out.flush();
                out.write(buf, 0, len);
            }

            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            checkAck(in, "Het kopieren  is niet gelukt");
        }

        out.close();
        channel.disconnect();
    }

    private void checkAck(final InputStream in, final String message) throws IOException, KanaalException {
        final int b = in.read();
        // b may be 0 for success,
        // 1 for error,
        // 2 for fatal error,
        // -1
        if (b == 0) {
            return;
        }
        if (b == -1) {
            throw new KanaalException(message + "\nonbekende fout");
        }

        if (b == 1 || b == 2) {
            final StringBuffer sb = new StringBuffer();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            } while (c != '\n');

            // error
            throw new KanaalException(message + "\n" + sb.toString());
        }
    }
}
