/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.access;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link javax.management.MBeanServerConnection} methods.
 */
final class Methods {

    /**
     * Read methods.
     */
    static final Set<String> READ_METHODS = new HashSet<>(
            Arrays.asList("addNotificationListener", "getAttribute", "getAttributes", "getDefaultDomain", "getDomains",
                    "getMBeanCount", "getMBeanInfo", "getObjectInstance", "isInstanceOf", "isRegistered", "queryMBeans",
                    "queryNames", "removeNotificationListener"));

    /**
     * Write methods.
     */
    static final Set<String> WRITE_METHODS = new HashSet<>(
            Arrays.asList("invoke", "setAttribute", "setAttributes"));

    /**
     * Create methods.
     */
    static final Set<String> CREATE_METHODS = new HashSet<>(Arrays.asList("createMBean"));

    /**
     * Unregister methods.
     */
    static final Set<String> UNREGISTER_METHODS = new HashSet<>(Arrays.asList("unregisterMBean"));

    private Methods() {
        throw new IllegalStateException("Do not instantiate");
    }

}
