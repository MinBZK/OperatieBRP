/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Properties loader.
 */
@FunctionalInterface
public interface PropertiesLoader {

    /**
     * Load the properties.
     * @return properties
     * @throws IOException if an I/O error occurs when loading the properties
     */
    Properties loadProperties() throws IOException;
}
