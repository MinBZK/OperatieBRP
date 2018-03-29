/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.bevraging;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * BevragingApiService implementatie.
 */
final class BevragingApiServiceImpl implements BevragingApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private GeefDetailsPersoonApiService geefDetailsPersoonApiService;
    @Inject
    private ZoekPersoonApiService        zoekPersoonApiService;
    @Inject
    private GeefMedebewonersApiService   geefMedebewonersApiService;

    @Override
    public GeefDetailsPersoonApiService getGeefDetailsPersoonApiService() {
        return geefDetailsPersoonApiService;
    }

    public ZoekPersoonApiService getZoekPersoonApiService() {
        return zoekPersoonApiService;
    }

    @Override
    public GeefMedebewonersApiService getGeefMedebewonersApiService() {
        return geefMedebewonersApiService;
    }
}
