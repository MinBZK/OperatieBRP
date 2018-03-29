/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common.jmx;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

import org.springframework.jmx.export.naming.ObjectNamingStrategy;
import org.springframework.jmx.support.ObjectNameManager;

/**
 * Naming strategy die een prefix voor een objectname zet indien de managedbean de annotatie @UsePrefix bevat.
 */
public final class DynamicDomainNamingStrategy implements ObjectNamingStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private ObjectNamingStrategy delegate;
    private String domain;

    @Override
    public ObjectName getObjectName(final Object managedBean, final String beanKey) throws MalformedObjectNameException {
        final ObjectName objectName = delegate.getObjectName(managedBean, beanKey);

        if (managedBean.getClass().isAnnotationPresent(UseDynamicDomain.class)) {
            final String oldObjectName = objectName.getCanonicalName();
            final String newObjectName = replaceDomain(oldObjectName);
            LOGGER.info("Changing JMX objectname from {} to {}", oldObjectName, newObjectName);
            return ObjectNameManager.getInstance(newObjectName);
        } else {
            return objectName;
        }
    }

    private String replaceDomain(final String objectName) {
        return domain + objectName.substring(objectName.indexOf(':'));
    }

    /**
     * Zet het domein.
     * @param domain het te zetten domein
     */
    public void setDomain(final String domain) {
        this.domain = domain;
    }

    /**
     * Zet de delegate.
     * @param delegate de te zetten delegate
     */
    public void setDelegate(final ObjectNamingStrategy delegate) {
        this.delegate = delegate;
    }
}
