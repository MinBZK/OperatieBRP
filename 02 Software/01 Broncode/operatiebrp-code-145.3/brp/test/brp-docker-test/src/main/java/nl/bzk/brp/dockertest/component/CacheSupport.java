/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import nl.bzk.brp.test.common.TestclientExceptie;

/**
 * Interface voor componenten welke een cache hebben die geupdate
 * moet kunnen worden.
 */
public interface CacheSupport extends JMXSupport {

    String JMX_OPERATIE = "herlaadCaches";
    String OBJECT_NAME_BASIS_AUTAUT_CACHE = "-caches:name=Caches";

    /**
     * Update de cache.
     */
    default void refresh() {
        try {
            final ObjectName objectName = new ObjectName(getJmxDomain() + OBJECT_NAME_BASIS_AUTAUT_CACHE);
            LOGGER.info("Ververs cache: {}", objectName);
            voerUit(JMX_OPERATIE, objectName);
        } catch (MalformedObjectNameException| JMXException e) {
            throw new TestclientExceptie(String.format("Het is niet gelukt de cache van [%s] te verversen", getLogischeNaam()), e);
        }
    }

}
