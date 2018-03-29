/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.basis;

import nl.bzk.brp.tooling.apitest.service.afnemerindicatie.OnderhoudAfnemerindicatieApiService;
import nl.bzk.brp.tooling.apitest.service.beheer.BeheerSelectieAPIService;
import nl.bzk.brp.tooling.apitest.service.bevraging.BevragingApiService;
import nl.bzk.brp.tooling.apitest.service.mutatielevering.MutatieleveringApiService;
import nl.bzk.brp.tooling.apitest.service.selectie.SelectieAPIService;
import nl.bzk.brp.tooling.apitest.service.stuf.VerstuurStufBerichtApiService;
import nl.bzk.brp.tooling.apitest.service.synchronisatie.SynchronisatieApiService;
import nl.bzk.brp.tooling.apitest.service.vrijbericht.VerstuurVrijBerichtApiService;

/**
 * ApiService.
 */
public interface ApiService {

    /**
     * @return de {@link BevragingApiService}
     */
    BevragingApiService getBevragingApiService();

    /**
     * @return de {@link SynchronisatieApiService}
     */
    SynchronisatieApiService getSynchronisatieApiService();

    /**
     * @return de {@link VerstuurVrijBerichtApiService}
     */
    VerstuurVrijBerichtApiService getVerstuurVrijBerichtApiService();

    /**
     * @return de {@link VerstuurStufBerichtApiService}
     */
    VerstuurStufBerichtApiService getVerstuurStufBerichtApiService();

    /**
     * @return de {@link OnderhoudAfnemerindicatieApiService}
     */
    OnderhoudAfnemerindicatieApiService getOnderhoudAfnemerindicatieService();

    /**
     * @return de {@link MutatieleveringApiService}
     */
    MutatieleveringApiService getMutatieleveringApiService();

    /**
     * @return de {@link SelectieAPIService}
     */
    SelectieAPIService getSelectieApiService();

    /**
     * @return de {@link BeheerSelectieAPIService}
     */
    BeheerSelectieAPIService getBeheerSelectieApiService();
}
