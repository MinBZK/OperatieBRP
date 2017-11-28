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

/**
 * Static login module.
 */
public final class StaticLoginModule extends AbstractLoginModule {

    /**
     * @return the principals configured in the {@link StaticConfiguration}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected List<Principal> login(final Subject subject, final CallbackHandler callbackHandler,
                                    final Map<String, ?> sharedState, final Map<String, ?> options) throws LoginException {
        return (List<Principal>) options.get(StaticConfiguration.PRINCIPALS);
    }

}
