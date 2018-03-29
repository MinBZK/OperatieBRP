/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.authenticator;

import java.util.Properties;
import nl.bzk.algemeenbrp.util.jmx.utils.PropertiesLoader;

/**
 * Properties authenticator.
 */
public final class PropertiesAuthenticator extends AbstractAuthenticator {

    /**
     * Create a new properties authenticator.
     * @param properties properties to use
     */
    public PropertiesAuthenticator(final Properties properties) {
        this(() -> properties);
    }

    /**
     * Create a new default authenticator.
     * @param propertiesLoader properties loader to use
     */
    public PropertiesAuthenticator(final PropertiesLoader propertiesLoader) {
        super("PropertiesAuthenticator", new PropertiesConfiguration("PropertiesAuthenticator", propertiesLoader));
    }
}
