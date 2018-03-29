/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.access;

import javax.security.auth.Subject;

/**
 * Allow access to everything.
 */
public final class AllAccessController implements JMXAccessController {

    @Override
    public void checkAccess(final Subject subject, final String methodName, final Object[] parameterValues) {
        // Everything is allowed
    }
}
