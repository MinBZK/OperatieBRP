/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.ws.util;

import static org.junit.Assert.*;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.junit.Test;

public class ServerPasswordCallbackTest {

    @Test
    public void testServerCallback() throws IOException, UnsupportedCallbackException {
        final ServerPasswordCallback serverPasswordCallback = new ServerPasswordCallback();
        final String wachtwoord = "pwd";
        serverPasswordCallback.setPassword(wachtwoord);
        final WSPasswordCallback callback = new WSPasswordCallback("dummy", 1);
        serverPasswordCallback.handle(new Callback[]{ callback });
        assertEquals(wachtwoord, callback.getPassword());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testServerCallbackNull() throws IOException, UnsupportedCallbackException {
        final ServerPasswordCallback serverPasswordCallback = new ServerPasswordCallback();
        serverPasswordCallback.handle(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testServerCallbackNullArg() throws IOException, UnsupportedCallbackException {
        final ServerPasswordCallback serverPasswordCallback = new ServerPasswordCallback();
        serverPasswordCallback.handle(new Callback[]{null});
    }


    @Test(expected = IllegalArgumentException.class)
    public void testServerCallbackGeenArg() throws IOException, UnsupportedCallbackException {
        final ServerPasswordCallback serverPasswordCallback = new ServerPasswordCallback();
        final String wachtwoord = "pwd";
        serverPasswordCallback.setPassword(wachtwoord);
        serverPasswordCallback.handle(new Callback[]{});
    }

    @Test(expected = UnsupportedCallbackException.class)
    public void testServerCallbackVerkeerdArg() throws IOException, UnsupportedCallbackException {
        final ServerPasswordCallback serverPasswordCallback = new ServerPasswordCallback();
        final String wachtwoord = "pwd";
        serverPasswordCallback.setPassword(wachtwoord);
        final Callback callback = new Callback() {
        };
        serverPasswordCallback.handle(new Callback[]{ callback });
    }


}
