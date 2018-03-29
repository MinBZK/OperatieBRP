/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.io.IOUtils;

/**
 * Helper class voor het lezen van de output van een proces.
 */
public final class ProcessOutputReader {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final String naam = Thread.currentThread().getName();
    private final Process process;
    private String output;
    private StringBuilder errorOutput = new StringBuilder();

    private final Thread errorReaderThread = new Thread() {

        @Override
        public void run() {
            Thread.currentThread().setName(naam);
            try (final InputStream errorStream = process.getErrorStream()) {
                while (process.isAlive()) {
                    final List<String> errorRegels = IOUtils.readLines(errorStream, StandardCharsets.UTF_8);
                    if (!errorRegels.isEmpty()) {
                        final String error = String.join(System.lineSeparator(), errorRegels);
                        LOGGER.warn(error);
                        errorOutput.append(error);
                    }
                }
            } catch (IOException e) {
                throw new ComponentException("Kan errorstream niet lezen", e);
            }
        }
    };

    private final Thread inputReaderThread = new Thread() {

        @Override
        public void run() {
            final StringBuilder sb = new StringBuilder();
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (sb.length() != 0) {
                        sb.append("\n");
                    }
                    sb.append(line);
                }
            } catch (IOException e) {
                throw new ComponentException("Kan inputstream niet lezen", e);
            }
            output = sb.toString();
        }
    };

    /**
     * Constructor.
     *
     * @param process een proces
     */
    public ProcessOutputReader(final Process process) {
        this.process = process;
    }

    /**
     * Start het lezen van output van het proces.
     */
    public void start() {
        errorReaderThread.start();
        inputReaderThread.start();
    }

    /**
     * @return de output van het {@link Process}
     */
    public String geefOutput() {
        return output;
    }

    /**
     * @return de error output van het {@link Process}, of null indien geen fouten
     */
    public String getErrorOutput() {
        return errorOutput.length() == 0 ? null : errorOutput.toString();
    }

    public void join() throws InterruptedException {
        errorReaderThread.join();
        inputReaderThread.join();
    }
}
