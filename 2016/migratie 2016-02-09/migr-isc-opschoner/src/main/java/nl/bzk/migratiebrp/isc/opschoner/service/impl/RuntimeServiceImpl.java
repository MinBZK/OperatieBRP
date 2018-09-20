/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.service.impl;

import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.opschoner.dao.RuntimeDao;
import nl.bzk.migratiebrp.isc.opschoner.service.RuntimeService;

/**
 * Implementatie van de lock service interface.
 */
public final class RuntimeServiceImpl implements RuntimeService {

    @Inject
    private RuntimeDao runtimeDao;

    @Override
    public void lockRuntime(final String runtimeNaam) {
        runtimeDao.voegRuntimeToe(runtimeNaam);
    }

    @Override
    public void unlockRuntime(final String runtimeNaam) {
        runtimeDao.verwijderRuntime(runtimeNaam);
    }

}
