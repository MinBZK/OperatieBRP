/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import nl.bzk.brp.testclient.misc.Eigenschappen;
import org.apache.ws.security.WSPasswordCallback;


/**
 * De Class ServerPasswordCallback.
 */
public class ServerPasswordCallback implements CallbackHandler {

    /** De Constante DEFAULT_USERNAME. */
    private static final String DEFAULT_USERNAME = "1";

    /** De Constante DEFAULT_PASSWORD. */
    private static final String DEFAULT_PASSWORD = "123123";

    /** De init map. */
    private final Map<String, String> initMap;

    /**
     * Instantieert een nieuwe server password callback.
     *
     * @param eigenschappen de eigenschappen
     */
    public ServerPasswordCallback(final Eigenschappen eigenschappen) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        initMap = Collections.unmodifiableMap(map);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
     */
    @Override
    public void handle(final Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
        pc.setPassword(initMap.get(pc.getIdentifier()));
    }

}
