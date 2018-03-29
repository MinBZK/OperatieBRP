/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.environment;

import java.io.IOException;
import java.util.Map;
import javax.management.remote.JMXAuthenticator;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorServer;
import nl.bzk.algemeenbrp.util.jmx.access.DefaultAccessController;
import nl.bzk.algemeenbrp.util.jmx.access.JMXAccessController;
import nl.bzk.algemeenbrp.util.jmx.authenticator.StaticAuthenticator;
import nl.bzk.algemeenbrp.util.jmx.socket.AnonymousSslSocketFactory;
import nl.bzk.algemeenbrp.util.jmx.socket.JMXSocketFactory;
import nl.bzk.algemeenbrp.util.jmx.socket.SslConfigurationException;

/**
 * Environment constants.
 */
public final class Environment {

    public static final String KEY_SOCKETFACTORY = "jmx.remote.socketfactory";

    public static final String KEY_AUTHENTICATOR = JMXConnectorServer.AUTHENTICATOR;

    public static final String KEY_ACCESSCONTROLLER = "jmx.remote.accesscontroller";

    public static final String KEY_REQUESTTIMEOUT = "jmx.remote.requesttimeout";

    public static final String KEY_CREDENTIALS = JMXConnector.CREDENTIALS;

    public static final String KEY_THREADPRIORITY = "jmx.remote.threadpriority";
    private static final int DEAFULT_TIME_OUT = 15;

    private Environment() {
        throw new IllegalStateException("Do not instantiate");
    }

    /**
     * Bepaalt de socket factory op basis van de properties.
     * @param environment De verzameling properties uit de environment
     * @return de socket factory
     * @throws IOException in het geval de socket factory niet aangemaakt kan worden
     */
    public static JMXSocketFactory determineSocketFactory(final Map<String, ?> environment) throws IOException {
        // Custom socket factory via the environment
        final JMXSocketFactory custom = (JMXSocketFactory) environment.get(KEY_SOCKETFACTORY);
        if (custom != null) {
            return custom;
        }

        // Default: no authentication
        try {
            return new AnonymousSslSocketFactory();
        } catch (final SslConfigurationException e) {
            throw new IOException("Could not create default socket factory", e);
        }
    }

    /**
     * Bepaalt de authenticator op basis van de properties.
     * @param environment De verzameling properties uit de environment
     * @return de authenticator
     */
    public static JMXAuthenticator determineAuthenticator(final Map<String, ?> environment) {
        // Custom authenticator via the environment
        final JMXAuthenticator custom = (JMXAuthenticator) environment.get(KEY_AUTHENTICATOR);
        if (custom != null) {
            return custom;
        }

        // Default: no authentication
        return new StaticAuthenticator();
    }

    /**
     * Bepaalt de access controller op basis van de properties.
     * @param environment De verzameling properties uit de environment
     * @return de access controller
     */
    public static JMXAccessController determineAccessController(final Map<String, ?> environment) {
        // Custom access control via the environment
        final JMXAccessController custom = (JMXAccessController) environment.get(KEY_ACCESSCONTROLLER);
        if (custom != null) {
            return custom;
        }

        // Default: readonly access
        return new DefaultAccessController();
    }


    /**
     * Bepaalt de credentials op basis van de properties.
     * @param environment De verzameling properties uit de environment
     * @return de credentials
     */
    public static Object determineCredentials(final Map<String, ?> environment) {
        return environment.get(KEY_CREDENTIALS);
    }

    /**
     * Bepaalt de request timeout op basis van de properties.
     * @param environment De verzameling properties uit de environment
     * @return de request time out
     */
    public static int determineRequestTimeout(final Map<String, ?> environment) {
        final Object timeout = environment.get(KEY_REQUESTTIMEOUT);
        if (timeout instanceof Number) {
            // Custom timeout
            return ((Number) timeout).intValue();
        } else if (timeout instanceof String) {
            // Custom timeout
            return Integer.parseInt((String) timeout);
        } else if (timeout == null) {
            // Default: 15 seconds
            return DEAFULT_TIME_OUT;
        } else {
            // Invalid object
            throw new IllegalArgumentException("Environment key " + KEY_REQUESTTIMEOUT + " should contain a Number (or a String)");
        }
    }

    /**
     * Bepaalt de prioriteit van de thread op basis van de properties.
     * @param environment De verzameling properties uit de environment
     * @return de prioriteit van de thread
     */
    public static int determineThreadPriority(final Map<String, ?> environment) {
        final Object timeout = environment.get(KEY_THREADPRIORITY);
        if (timeout instanceof Number) {
            // Custom timeout
            return ((Number) timeout).intValue();
        } else if (timeout instanceof String) {
            // Custom timeout
            return Integer.parseInt((String) timeout);
        } else if (timeout == null) {
            // Default
            return Thread.NORM_PRIORITY;
        } else {
            // Invalid object
            throw new IllegalArgumentException("Environment key " + KEY_THREADPRIORITY + " should contain a Number (or a String)");
        }
    }

}
