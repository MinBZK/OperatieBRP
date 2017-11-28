/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.authenticator;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

/**
 * Base login modulle implementation.
 */
public abstract class AbstractLoginModule implements LoginModule {

    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map<String, ?> sharedState;
    private Map<String, ?> options;

    // Authentication status
    private boolean loggedIn;
    private boolean committed;
    private List<Principal> principals;

    /**
     * Create a new login module.
     */
    AbstractLoginModule() {
    }

    @Override
    public final void initialize(final Subject subject, final CallbackHandler callbackHandler,
                                 final Map<String, ?> sharedState, final Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;

        loggedIn = false;
        committed = false;
        principals = null;
    }

    @Override
    public final boolean login() throws LoginException {
        principals = login(subject, callbackHandler, sharedState, options);
        loggedIn = true;

        return true;
    }

    protected abstract List<Principal> login(final Subject subject, final CallbackHandler callbackHandler,
                                             final Map<String, ?> sharedState, final Map<String, ?> options) throws LoginException;

    @Override
    public final boolean commit() throws LoginException {
        if (!loggedIn) {
            return false;
        } else {
            // add Principals to the Subject
            if (subject.isReadOnly()) {
                throw new LoginException("Subject is read-only");
            }
            subject.getPrincipals().addAll(principals);
            committed = true;
        }
        return true;
    }

    @Override
    public final boolean abort() throws LoginException {
        if (!loggedIn) {
            return false;
        } else if (!committed) {
            principals = null;
            loggedIn = false;
        } else {
            logout();
        }
        return true;
    }

    @Override
    public final boolean logout() throws LoginException {
        if (subject.isReadOnly()) {
            throw new LoginException("Subject is read-only");
        }
        subject.getPrincipals().removeAll(principals);
        committed = false;

        principals = null;
        loggedIn = false;

        return true;
    }
}
