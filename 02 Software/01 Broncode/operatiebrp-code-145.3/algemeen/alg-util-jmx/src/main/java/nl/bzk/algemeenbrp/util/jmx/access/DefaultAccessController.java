/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.access;

import javax.security.auth.Subject;

/**
 * Allow access to all read-only and notification methods.
 *
 * <ul>
 * <li>addNotificationListener</li>
 * <li>getAttribute</li>
 * <li>getAttributes</li>
 * <li>getDefaultDomain</li>
 * <li>getDomains</li>
 * <li>getMBeanCount</li>
 * <li>getMBeanInfo</li>
 * <li>getObjectInstance</li>
 * <li>isInstanceOf</li>
 * <li>isRegistered</li>
 * <li>queryMBeans</li>
 * <li>queryNames</li>
 * <li>removeNotificationListener</li>
 * </ul>
 */
public final class DefaultAccessController implements JMXAccessController {

    @Override
    public void checkAccess(final Subject subject, final String methodName, final Object[] parameterValues) {
        if (!Methods.READ_METHODS.contains(methodName)) {
            throw new SecurityException("Illegal access");
        }
    }

}
