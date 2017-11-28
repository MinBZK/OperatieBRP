/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.model;

import nl.bzk.algemeenbrp.util.xml.context.Context;

/**
 * Configuratie context helper.
 */
public final class ConfigurationHelper {

    private static final String CONFIGURATION_DATAKEY = "ConfigurationHelper$Configuration";

    private ConfigurationHelper() {
        // Niet instantieerbaar
    }

    /**
     * Zet de configuratie op de context.
     * 
     * @param context
     *            context
     * @param configuration
     *            configuratie
     */
    public static void setConfiguration(final Context context, final Configuration configuration) {
        context.setData(CONFIGURATION_DATAKEY, configuration);
    }

    /**
     * Geef de configuratie.
     * 
     * @param context
     *            context
     * @return configuratie
     */
    public static Configuration getConfiguration(final Context context) {
        return (Configuration) context.getData(CONFIGURATION_DATAKEY);
    }
}
