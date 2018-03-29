/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.util;

import java.util.logging.Logger;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import org.springframework.jmx.export.naming.MetadataNamingStrategy;

/**
 * DomeinMetadataNamingStrategy. Verantwoordelijk voor het overschrijven van de default objectnaam van jmx
 * objecten met domein naam prefix
 */
public final class DomeinMetadataNamingStrategy extends MetadataNamingStrategy {

    private static final Logger LOGGER = Logger.getLogger(DomeinMetadataNamingStrategy.class.getName());

    private String domein;

    @Override
    public ObjectName getObjectName(final Object managedBean, final String beanKey) throws MalformedObjectNameException {
        final ObjectName objectNaam = super.getObjectName(managedBean, beanKey);
        final String nieuweObjectNaam = domein + "-" + objectNaam.toString();
        LOGGER.fine(String.format("converteer [%s] naar [%s]", objectNaam.toString(), nieuweObjectNaam));
        return ObjectName.getInstance(nieuweObjectNaam);
    }

    public void setDomein(final String domein) {
        this.domein = domein;
    }
}
