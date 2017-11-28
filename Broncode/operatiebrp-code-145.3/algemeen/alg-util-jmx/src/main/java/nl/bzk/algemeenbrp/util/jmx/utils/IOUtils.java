/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.utils;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * IO utility methods.
 */
public final class IOUtils {

    private static final Logger LOGGER = Logger.getLogger(IOUtils.class.getName());

    private IOUtils() {
        throw new IllegalStateException("Do not instantiate");
    }

    /**
     * Close, ignoring IO exceptions.
     * @param closeable object to close
     */
    public static void closeSilently(final Closeable closeable) {
        ignoreIOException(closeable::close);
    }

    /**
     * Execute an IO function ignoring the IOException.
     * @param ioFunction IO function
     */
    public static void ignoreIOException(final IOFunction ioFunction) {
        try {
            ioFunction.execute();
        } catch (final IOException e) {
            // Ignore
            LOGGER.log(Level.FINE, "Ignoring exception", e);
        }
    }

    /**
     * IO Function.
     */
    @FunctionalInterface
    public interface IOFunction {

        /**
         * Execute function.
         * @throws IOException if an I/O error occurs when executing this function
         */
        void execute() throws IOException;
    }
}
