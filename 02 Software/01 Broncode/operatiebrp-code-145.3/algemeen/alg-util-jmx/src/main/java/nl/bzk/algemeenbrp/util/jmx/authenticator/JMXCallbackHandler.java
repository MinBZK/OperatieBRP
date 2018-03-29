/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.authenticator;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

/**
 * Callback handler for JMX based credentials.
 */
final class JMXCallbackHandler implements CallbackHandler {

    private final String username;
    private final String password;

    /**
     * Create a new callback handler.
     *
     * Assumes (and checks) the credentials object is an array of strings with fixed length of two.
     * The first entry contains the username, the second entry contains the password.
     */
    JMXCallbackHandler(final Object credentials) throws LoginException {
        if (credentials == null) {
            username = null;
            password = null;
        } else {
            if (!(credentials instanceof String[]) || ((String[]) credentials).length != 2) {
                throw new LoginException("JMX Credentials should be of type String[] and have a length of 2");
            }

            username = ((String[]) credentials)[0];
            password = ((String[]) credentials)[1];
        }
    }

    /**
     * Handles the callbacks; only supports {@link NameCallback} and {@link PasswordCallback}.
     * @throws UnsupportedCallbackException if an unsupported callback is encountered
     */
    @Override
    public void handle(final Callback[] callbacks)
            throws UnsupportedCallbackException {

        for (final Callback callback : callbacks) {
            if (callback instanceof NameCallback) {
                ((NameCallback) callback).setName(username);

            } else if (callback instanceof PasswordCallback) {
                ((PasswordCallback) callback)
                        .setPassword(password.toCharArray());

            } else {
                throw new UnsupportedCallbackException
                        (callback, "Unrecognized Callback");
            }
        }
    }
}
