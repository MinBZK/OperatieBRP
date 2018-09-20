/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.interceptor.helper;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;


/**
 * Callback class die wordt gebruikt in de {@link org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor
 * WSS4JOutInterceptor}, welke benodigd is voor de signing van uitgaande berichten. Deze callback class
 * zorgt voor het benodigde wachtwoord.<br/<br/>
 * Voorlopig levert deze class een hardcoded wachtwoord op, daar functioneel nog onduidelijk is hoe het wachtwoord
 * geconfigureerd dient te worden en wat voor wachtwoord het zou moeten zijn. In de
 */
public class ServerPasswordCallback implements CallbackHandler {

    private static final Map<String, String> MAP;

    // TODO: Als eisen/specs duidelijk zijn, dan hardcoded wachtwoord vervangen door configureerbare, encrypted variant
    static {
        Map<String, String> initMap = new HashMap<String, String>();
        initMap.put("serverkey", "serverkeypassword");
        MAP = Collections.unmodifiableMap(initMap);
    }

    @Override
    public void handle(final Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        if (callbacks == null || callbacks.length == 0 || callbacks[0] == null) {
            throw new IllegalArgumentException("Kan antwoordbericht niet ondertekenen daar er geen wachtwoord callback "
                    + "beschikbaar is.");
        }

        if (callbacks[0] instanceof WSPasswordCallback) {
            WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
            pc.setPassword(MAP.get(pc.getIdentifier()));
        } else {
            throw new UnsupportedCallbackException(callbacks[0]);
        }
    }

}
