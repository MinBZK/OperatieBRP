/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.authenticator;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.management.remote.JMXPrincipal;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import nl.bzk.algemeenbrp.util.jmx.utils.PropertiesLoader;

/**
 * Properties login module.
 */
public final class PropertiesLoginModule extends AbstractLoginModule {

    @Override
    protected List<Principal> login(final Subject subject, final CallbackHandler callbackHandler,
                                    final Map<String, ?> sharedState, final Map<String, ?> options) throws LoginException {
        // Retrieve username and password
        if (callbackHandler == null) {
            throw new LoginException("Could not retrieve credentials: no callback handler");
        }

        final Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("username");
        callbacks[1] = new PasswordCallback("password", false);

        final String username;
        final char[] password;
        try {
            callbackHandler.handle(callbacks);
            username = ((NameCallback) callbacks[0]).getName();
            final char[] tmpPassword = ((PasswordCallback) callbacks[1]).getPassword();
            password = new char[tmpPassword.length];
            System.arraycopy(tmpPassword, 0,
                    password, 0, tmpPassword.length);
            ((PasswordCallback) callbacks[1]).clearPassword();

        } catch (final UnsupportedCallbackException | IOException e) {
            final LoginException le = new LoginException("Could not retrieve credentials");
            le.initCause(e);
            throw le;
        }

        // Validate username and password
        final Properties properties = loadProperties(options);
        if (validateCredentials(properties, username, password)) {
            final Principal userPrincipal = new JMXPrincipal(username);
            return Collections.singletonList(userPrincipal);
        } else {
            throw new FailedLoginException("Invalid credentials");
        }
    }

    private Properties loadProperties(final Map<String, ?> options) throws LoginException {
        final PropertiesLoader loader = (PropertiesLoader) options.get(PropertiesConfiguration.PROPERTIES_LOADER);
        if (loader == null) {
            throw new LoginException("No properties loader configured");
        }
        try {
            return loader.loadProperties();
        } catch (final IOException e) {
            final LoginException le = new LoginException("Could not load properties");
            le.initCause(e);
            throw le;
        }
    }

    private boolean validateCredentials(final Properties properties, final String username, final char[] password) {
        final String expectedPassword = properties.getProperty(username);
        return expectedPassword != null && Arrays.equals(expectedPassword.toCharArray(), password);
    }

}
