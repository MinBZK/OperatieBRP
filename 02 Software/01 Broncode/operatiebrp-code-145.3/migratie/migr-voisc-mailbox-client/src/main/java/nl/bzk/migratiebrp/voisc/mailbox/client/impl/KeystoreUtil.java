/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.mailbox.client.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.voisc.mailbox.client.exception.ConnectionException;
import nl.bzk.migratiebrp.voisc.spd.exception.MessagesCodes;
import org.springframework.core.io.Resource;

/**
 * Utility class voor het instantiëren van keystore objecten.
 */
final class KeystoreUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private KeystoreUtil() {
    }

    /**
     * Instantieert en retourneert een Keystore.
     * @param keystoreResource De keystore resource.
     * @param password Het wachtwoord om de keystore te kunnen openen.
     * @return De geinstantieerde keystore.
     */
    static KeyStore loadKeystore(final Resource keystoreResource, final char[] password) {
        return loadKeystore("JKS", keystoreResource, password);
    }

    /**
     * Instantieert en retourneert een Keystore.
     * @param keystoreType Het type van de keystore
     * @param keystoreResource De keystore resource.
     * @param password Het wachtwoord om de keystore te kunnen openen.
     * @return De geinstantieerde keystore.
     */
    static KeyStore loadKeystore(final String keystoreType, final Resource keystoreResource, final char[] password) {
        final KeyStore store;

        try (InputStream keyStoreInputStream = keystoreResource.getInputStream()) {
            store = KeyStore.getInstance(keystoreType);
            store.load(keyStoreInputStream, password);
        } catch (final FileNotFoundException ex) {
            throw new ConnectionException(MessagesCodes.ERRMSG_SSL_MISSING_KEYSTORE, new Object[]{keystoreResource.getFilename()}, ex);
        } catch (final IOException ex) {
            throw new ConnectionException(MessagesCodes.ERRMSG_SSL_INCORRECT_KEYSTORE_PASSWORD, new Object[]{keystoreResource.getFilename()}, ex);
        } catch (final GeneralSecurityException ex) {
            LOGGER.error("Kan de SSL-keystore niet laden", ex);
            throw new ConnectionException(MessagesCodes.ERRMSG_VOSPG_SSL_CONNECTION_ERROR, null, ex);
        }

        return store;
    }
}
