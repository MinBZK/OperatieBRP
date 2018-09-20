/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.kern.interceptor.helper;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.wss4j.common.ext.WSPasswordCallback;


/**
 * Callback class die wordt gebruikt in de {@link org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor
 * WSS4JOutInterceptor}, welke benodigd is voor de signing van uitgaande berichten. Deze callback class
 * zorgt voor het benodigde wachtwoord.
 */
public class ServerPasswordCallback implements CallbackHandler {

    private String password;

    @Override
    public void handle(final Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        if (callbacks == null || callbacks.length == 0 || callbacks[0] == null) {
            throw new IllegalArgumentException(
                    "Kan antwoordbericht niet ondertekenen daar er geen wachtwoord callback beschikbaar is.");
        }

        if (callbacks[0] instanceof WSPasswordCallback) {
            WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
            pc.setPassword(password);
        } else {
            throw new UnsupportedCallbackException(callbacks[0]);
        }
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
