/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.access;

import javax.security.auth.Subject;

/**
 * Control access.
 */
@FunctionalInterface
public interface JMXAccessController {

    /**
     * Check access for a specific invocation on the mbean server.
     * @param subject subject
     * @param methodName method name
     * @param parameterValues parameter values
     */
    void checkAccess(Subject subject, String methodName, Object[] parameterValues);
}
