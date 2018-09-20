/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.interceptor.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.junit.Test;


/**
 * Unit test voor de {@link ServerPasswordCallback} class.
 */
public class ServerPasswordCallbackTest {

    private final ServerPasswordCallback serverPasswordCallback = new ServerPasswordCallback();

    /**
     * Test voor het ophalen van het wachtwoord met een valide id.
     *
     * @throws UnsupportedCallbackException
     * @throws IOException
     */
    @Test
    public void testValidHandle() throws IOException, UnsupportedCallbackException {
        WSPasswordCallback wsPasswordCallback = handleCallbackVoorId("serverkey");
        assertEquals("serverkeypassword", wsPasswordCallback.getPassword());
    }

    /**
     * Test voor het ophalen van het wachtwoord met een invalide id.
     *
     * @throws UnsupportedCallbackException
     * @throws IOException
     */
    @Test
    public void testInvalidHandle() throws IOException, UnsupportedCallbackException {
        WSPasswordCallback wsPasswordCallback = handleCallbackVoorId("test");
        assertNull(wsPasswordCallback.getPassword());
    }

    private WSPasswordCallback handleCallbackVoorId(final String id) throws IOException, UnsupportedCallbackException {
        WSPasswordCallback wsPasswordCallback = new WSPasswordCallback(id, 1);
        Callback[] callbacks = { wsPasswordCallback };

        serverPasswordCallback.handle(callbacks);
        return wsPasswordCallback;
    }

}
