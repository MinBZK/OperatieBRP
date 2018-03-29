/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.logging;

import org.jbpm.svc.Service;
import org.jbpm.svc.ServiceFactory;

/**
 * Logging service factory.
 */
public final class MigratieLoggingServiceFactory implements ServiceFactory {
    private static final long serialVersionUID = 1L;

    @Override
    public Service openService() {
        return new MigratieLoggingService();
    }

    @Override
    public void close() {
        // Interface methode, hoeft niks te sluiten
    }
}
