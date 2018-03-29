/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.authenticator;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;

/**
 * Base configuration implementation.
 */
class BasicConfiguration extends Configuration {

    private final String name;
    private final AppConfigurationEntry[] entries;

    /**
     * Create a new configuration.
     * @param name name
     */
    BasicConfiguration(final String name, final AppConfigurationEntry[] entries) {
        this.name = name;
        this.entries = entries;
    }

    @Override
    public final AppConfigurationEntry[] getAppConfigurationEntry(final String name) {
        if (this.name.equals(name)) {
            return entries;
        } else {
            return new AppConfigurationEntry[]{};
        }
    }
}
