/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.mailbox.client.impl;

import nl.bzk.migratiebrp.voisc.mailbox.client.exception.ConnectionException;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class KeystoreUtilTest {
    @Test
    public void success() {
        KeystoreUtil.loadKeystore(new ClassPathResource("keystores/keystore.jks"), "changeit".toCharArray());
    }

    @Test(expected = ConnectionException.class)
    public void keystoreNotFound() {
        KeystoreUtil.loadKeystore(new ClassPathResource("keystores/non-existing.jks"), "changeit".toCharArray());
    }

    @Test(expected = ConnectionException.class)
    public void incorrectPassword() {
        KeystoreUtil.loadKeystore(new ClassPathResource("keystores/keystore.jks"), "incorrect".toCharArray());
    }

    @Test(expected = ConnectionException.class)
    public void unknownKeystoreType() {
        KeystoreUtil.loadKeystore("unknown_type", new ClassPathResource("keystores/keystore.jks"), "changeit".toCharArray());
    }
}
