/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.interceptor.helper;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


/** Unit test voor de {@link ServerPasswordCallback} class. */
public class ServerPasswordCallbackTest {

    private final ServerPasswordCallback serverPasswordCallback = new ServerPasswordCallback();

    @Before
    public void setup() {
        serverPasswordCallback.setPassword("serverkeypassword");
    }


    /**
     * Test voor het ophalen van het wachtwoord met een valide id.
     *
     * @throws UnsupportedCallbackException
     * @throws IOException
     */
    @Test
    public void testValidHandle() throws IOException, UnsupportedCallbackException {
        WSPasswordCallback wsPasswordCallback = new WSPasswordCallback("random", 1);
        Callback[] callbacks = { wsPasswordCallback };

        serverPasswordCallback.handle(callbacks);

        Assert.assertEquals("serverkeypassword", wsPasswordCallback.getPassword());
    }

    /**
     * Test voor het ophalen van een wachtwoord, maar met een onverwacht/verkeerd callback type.
     *
     * @throws UnsupportedCallbackException
     * @throws IOException
     */
    @Test(expected = UnsupportedCallbackException.class)
    public void testInvalideCallback() throws UnsupportedCallbackException, IOException {
        Callback mockCallback = Mockito.mock(Callback.class);
        Callback[] callbacks = { mockCallback };

        serverPasswordCallback.handle(callbacks);
    }

    /**
     * Test voor het ophalen van een wachtwoord, maar met een lege array van callbacks.
     *
     * @throws UnsupportedCallbackException
     * @throws IOException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLegeCallbackArray() throws UnsupportedCallbackException, IOException {
        Callback[] callbacks = { };

        serverPasswordCallback.handle(callbacks);
    }

    /**
     * Test voor het ophalen van een wachtwoord, maar met een <code>null</code> als callback in de array van callbacks.
     *
     * @throws UnsupportedCallbackException
     * @throws IOException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullInCallbackArray() throws UnsupportedCallbackException, IOException {
        Callback[] callbacks = { null };

        serverPasswordCallback.handle(callbacks);
    }

    /**
     * Test voor het ophalen van een wachtwoord, maar met een <code>null</code> als array van callbacks.
     *
     * @throws UnsupportedCallbackException
     * @throws IOException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCallbackArrayAlsNull() throws UnsupportedCallbackException, IOException {
        serverPasswordCallback.handle(null);
    }
}
