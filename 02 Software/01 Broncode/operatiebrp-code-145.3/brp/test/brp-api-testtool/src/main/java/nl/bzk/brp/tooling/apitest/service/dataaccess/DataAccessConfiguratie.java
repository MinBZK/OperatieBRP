/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.dataaccess;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring config voor de DAL stubs.
 */
@Configuration
public class DataAccessConfiguratie {

    @Bean
    BeheerRepositoryStub maakBeheerRepository() {
        return new BeheerRepositoryStub();
    }

    /**
     * @return AdministratieveHandelingRepository stub
     */
    @Bean
    @SuppressWarnings("all")
    AdministratieveHandelingRepositoryStub maakAdministratieveHandelingRepository() {
        return new AdministratieveHandelingRepositoryStub();
    }

    /**
     * @return LeveringsautorisatieRepository stub
     */
    @Bean
    @SuppressWarnings("all")
    LeveringsautorisatieRepositoryStub maakLeveringsautorisatieRepository() {
        return new LeveringsautorisatieRepositoryStub();
    }

    /**
     * @return PartijRepository
     */
    @Bean
    @SuppressWarnings("all")
    PartijRepositoryStub maakPartijRepository() {
        return new PartijRepositoryStub();
    }

    /**
     * @return PersoonCacheRepository stub
     */
    @Bean
    @SuppressWarnings("all")
    PersoonCacheRepositoryStub maakPersoonCacheRepository() {
        return new PersoonCacheRepositoryStub();
    }

    /**
     * @return StamTabelRepository
     */
    @Bean
    @SuppressWarnings("all")
    StamTabelRepositoryStub maakStamTabelRepository() {
        return new StamTabelRepositoryStub();
    }

    /**
     * @return GeefDetailsPersoonRepositoryStub
     */
    @Bean
    @SuppressWarnings("all")
    GeefDetailsPersoonRepositoryStub maakGeefDetailsPersoonRepository() {
        return new GeefDetailsPersoonRepositoryStub();
    }

    /**
     * @return ZoekPersoonDataOphalerServiceStub
     */
    @Bean
    @SuppressWarnings("all")
    ZoekPersoonDataOphalerServiceStub maakZoekPersoonDataOphalerServiceStub() {
        return new ZoekPersoonDataOphalerServiceStub();
    }

    /**
     * @return AfnemerindicatieRepositoryStub
     */
    @Bean
    @SuppressWarnings("all")
    AfnemerindicatieRepositoryStub maakAfnemerindicatieRepositoryStub() {
        return new AfnemerindicatieRepositoryStub();
    }

}
