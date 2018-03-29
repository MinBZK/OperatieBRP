/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Helper class voor het starten van processen.
 */
public final class ProcessHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final int DEFAULT_TIMEOUT_WAARDE = 30;
    public static final ProcessHelper DEFAULT = new ProcessHelper();

    private int timeout = DEFAULT_TIMEOUT_WAARDE;
    private TimeUnit timeoutTimeUnit = TimeUnit.SECONDS;

    /**
     * Maakt een {@link ProcessHelper} met specifieke timeout.
     * @param timeout waarde van de timeout
     * @param timeoutTimeUnit unit van de timeout
     */
    public ProcessHelper(final int timeout, final TimeUnit timeoutTimeUnit) {
        this.timeout = timeout;
        this.timeoutTimeUnit = timeoutTimeUnit;
    }

    public ProcessHelper() {
    }

    /**
     * Start een proces met de gegeven commandolist en wacht totdat deze uitgevoerd is.
     * @param commands lijst met commando's
     * @return een {@link ProcessOutputReader}
     */
    public ProcessOutputReader startProces(final List<String> commands) {
        LOGGER.info(String.format("Run command: %s", prettyPrintCommando(commands)));
        final ProcessBuilder pb = new ProcessBuilder(commands);
        try {
            return wacht(pb.start(), commands);
        } catch (IOException e) {
            String message = String.format("Uitvoeren process mislukt: %s", prettyPrintCommando(commands));
            LOGGER.error(message, e);
            throw new ProcessException(message, e);
        }  catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ProcessException("Interrupted: " + prettyPrintCommando(commands));
        }
    }

    private ProcessOutputReader wacht(final Process process, final List<String> commands) throws InterruptedException, IOException {
        final ProcessOutputReader processOutputReader = new ProcessOutputReader(process);
        processOutputReader.start();
        final boolean waitResult = process.waitFor(timeout, timeoutTimeUnit);
        if (!waitResult) {
            throw new IOException("Gestopt met wachten, duurt te lang");
        }
        if (process.exitValue() != 0) {
            throw new AbnormalProcessTerminationException(String.format("Process niet normaal geeindigd: output=%s errorOutput=%s",
                    processOutputReader.geefOutput(), processOutputReader.getErrorOutput()));
        }
        processOutputReader.join();
        if (processOutputReader.getErrorOutput() != null &&
                (processOutputReader.getErrorOutput().contains("Error response from daemon")
                        || processOutputReader.getErrorOutput().contains("Error: No such container"))) {
            final String message = String.format("Fout tijdens uitvoer van proces %s %n %s",
                    prettyPrintCommando(commands), processOutputReader.getErrorOutput());
            LOGGER.error(message);
            throw new IOException(message);
        }
        return processOutputReader;
    }

    private static String prettyPrintCommando(final List<String> commandList) {
        return "[" + String.join(" ", commandList) + "]";
    }


}
