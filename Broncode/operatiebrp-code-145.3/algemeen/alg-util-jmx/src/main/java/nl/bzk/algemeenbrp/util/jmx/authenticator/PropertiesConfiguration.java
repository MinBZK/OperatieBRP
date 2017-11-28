/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.authenticator;

import java.util.HashMap;
import java.util.Map;
import javax.security.auth.login.AppConfigurationEntry;
import nl.bzk.algemeenbrp.util.jmx.utils.PropertiesLoader;

/**
 * Property file based configuration.
 *
 * Checks nothing, always returns the configured principals.
 */
final class PropertiesConfiguration extends BasicConfiguration {

    static final String PROPERTIES_LOADER = "propertiesLoader";

    /**
     * Create a new configuration.
     * @param name name
     */
    PropertiesConfiguration(final String name, final PropertiesLoader propertiesLoader) {
        super(name, createEntries(propertiesLoader));
    }

    private static AppConfigurationEntry[] createEntries(final PropertiesLoader propertiesLoader) {
        final Map<String, Object> options = new HashMap<>();
        options.put(PROPERTIES_LOADER, propertiesLoader);

        return new AppConfigurationEntry[]{new AppConfigurationEntry(PropertiesLoginModule.class.getName(),
                AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, options),};
    }
}
