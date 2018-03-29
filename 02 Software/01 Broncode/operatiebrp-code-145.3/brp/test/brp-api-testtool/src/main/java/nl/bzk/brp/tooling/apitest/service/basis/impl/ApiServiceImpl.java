/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.basis.impl;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.tooling.apitest.service.afnemerindicatie.OnderhoudAfnemerindicatieApiService;
import nl.bzk.brp.tooling.apitest.service.basis.ApiService;
import nl.bzk.brp.tooling.apitest.service.beheer.BeheerSelectieAPIService;
import nl.bzk.brp.tooling.apitest.service.bevraging.BevragingApiService;
import nl.bzk.brp.tooling.apitest.service.mutatielevering.MutatieleveringApiService;
import nl.bzk.brp.tooling.apitest.service.selectie.SelectieAPIService;
import nl.bzk.brp.tooling.apitest.service.stuf.VerstuurStufBerichtApiService;
import nl.bzk.brp.tooling.apitest.service.synchronisatie.SynchronisatieApiService;
import nl.bzk.brp.tooling.apitest.service.vrijbericht.VerstuurVrijBerichtApiService;

/**
 * Biedt toegang tot alle api services.
 */
final class ApiServiceImpl implements ApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private OnderhoudAfnemerindicatieApiService onderhoudAfnemerindicatieApiService;
    @Inject
    private BevragingApiService bevragingApiService;
    @Inject
    private SynchronisatieApiService synchronisatieApiService;
    @Inject
    private VerstuurVrijBerichtApiService vrijBerichtApiService;
    @Inject
    private VerstuurStufBerichtApiService stufBerichtApiService;
    @Inject
    private MutatieleveringApiService mutatieleveringApiService;
    @Inject
    private SelectieAPIService selectieAPIService;
    @Inject
    private BeheerSelectieAPIService beheerSelectieAPIService;

    @Override
    public BevragingApiService getBevragingApiService() {
        return bevragingApiService;
    }

    @Override
    public SynchronisatieApiService getSynchronisatieApiService() {
        return synchronisatieApiService;
    }

    @Override
    public VerstuurVrijBerichtApiService getVerstuurVrijBerichtApiService() {
        return vrijBerichtApiService;
    }

    @Override
    public VerstuurStufBerichtApiService getVerstuurStufBerichtApiService() {
        return stufBerichtApiService;
    }

    @Override
    public OnderhoudAfnemerindicatieApiService getOnderhoudAfnemerindicatieService() {
        return onderhoudAfnemerindicatieApiService;
    }

    @Override
    public MutatieleveringApiService getMutatieleveringApiService() {
        return mutatieleveringApiService;
    }

    @Override
    public SelectieAPIService getSelectieApiService() {
        return selectieAPIService;
    }

    @Override
    public BeheerSelectieAPIService getBeheerSelectieApiService() {
        return beheerSelectieAPIService;
    }

}
