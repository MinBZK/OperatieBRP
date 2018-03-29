/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.algemeen;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.junit.Assert;
import org.junit.Test;

/**
 * ServerPasswordCallbackTest.
 */
public class ServerPasswordCallbackTest {

    @Test
    public void testServerCallback() throws IOException, UnsupportedCallbackException {
        final ServerPasswordCallback serverPasswordCallback = new ServerPasswordCallback();
        final String wachtwoord = "pwd";
        serverPasswordCallback.setPassword(wachtwoord);
        final WSPasswordCallback callback = new WSPasswordCallback("dummy", 1);
        serverPasswordCallback.handle(new Callback[]{callback});

        Assert.assertEquals(wachtwoord, callback.getPassword());
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
        serverPasswordCallback.handle(new Callback[]{callback});
    }
}
