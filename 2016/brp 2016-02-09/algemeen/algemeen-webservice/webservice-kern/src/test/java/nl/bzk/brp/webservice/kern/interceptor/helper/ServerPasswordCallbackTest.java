/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.kern.interceptor.helper;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


/** Unit test voor de {@link ServerPasswordCallback} class. */
public class ServerPasswordCallbackTest {

    public static final String SERVERKEYPASSWORD = "serverkeypassword";
    private final ServerPasswordCallback serverPasswordCallback = new ServerPasswordCallback();

    @Before
    public void setup() {
        serverPasswordCallback.setPassword(SERVERKEYPASSWORD);
    }

    /**
     * Test voor het ophalen van het wachtwoord met een valide id.
     *
     * @throws javax.security.auth.callback.UnsupportedCallbackException
     * @throws java.io.IOException
     */
    @Test
    public void testValidHandle() throws IOException, UnsupportedCallbackException {
        final WSPasswordCallback wsPasswordCallback = new WSPasswordCallback("random", WSPasswordCallback.CUSTOM_TOKEN);
        final Callback[] callbacks = { wsPasswordCallback };

        serverPasswordCallback.handle(callbacks);

        Assert.assertEquals(SERVERKEYPASSWORD, wsPasswordCallback.getPassword());
    }

    /**
     * Test voor het ophalen van een wachtwoord, maar met een onverwacht/verkeerd callback type.
     *
     * @throws javax.security.auth.callback.UnsupportedCallbackException
     * @throws java.io.IOException
     */
    @Test(expected = UnsupportedCallbackException.class)
    public void testInvalideCallback() throws UnsupportedCallbackException, IOException {
        final Callback mockCallback = Mockito.mock(Callback.class);
        final Callback[] callbacks = { mockCallback };

        serverPasswordCallback.handle(callbacks);
    }

    /**
     * Test voor het ophalen van een wachtwoord, maar met een lege array van callbacks.
     *
     * @throws javax.security.auth.callback.UnsupportedCallbackException
     * @throws java.io.IOException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLegeCallbackArray() throws UnsupportedCallbackException, IOException {
        final Callback[] callbacks = { };

        serverPasswordCallback.handle(callbacks);
    }

    /**
     * Test voor het ophalen van een wachtwoord, maar met een <code>null</code> als callback in de array van callbacks.
     *
     * @throws javax.security.auth.callback.UnsupportedCallbackException
     * @throws java.io.IOException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullInCallbackArray() throws UnsupportedCallbackException, IOException {
        final Callback[] callbacks = { null };

        serverPasswordCallback.handle(callbacks);
    }

    /**
     * Test voor het ophalen van een wachtwoord, maar met een <code>null</code> als array van callbacks.
     *
     * @throws javax.security.auth.callback.UnsupportedCallbackException
     * @throws java.io.IOException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCallbackArrayAlsNull() throws UnsupportedCallbackException, IOException {
        serverPasswordCallback.handle(null);
    }
}
