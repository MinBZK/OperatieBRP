/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.socket;

import javax.management.remote.JMXProviderException;

/**
 * Exception during SSL configuration.
 */
public final class SslConfigurationException extends JMXProviderException {

    private static final long serialVersionUID = 1L;

    /**
     * Create a new exception.
     * @param message message
     */
    SslConfigurationException(final String message) {
        super(message);
    }

    /**
     * Create a new exception.
     * @param message message
     * @param cause cause
     */
    SslConfigurationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
