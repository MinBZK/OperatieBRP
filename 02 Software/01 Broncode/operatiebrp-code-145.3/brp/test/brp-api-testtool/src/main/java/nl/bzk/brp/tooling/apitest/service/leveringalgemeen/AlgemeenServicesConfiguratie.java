/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.leveringalgemeen;

import nl.bzk.brp.delivery.algemeen.MaakPersoonBerichtServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring configuratie voor algemeen services.
 */
@ComponentScan({
    "nl.bzk.brp.service.algemeen",
    "nl.bzk.brp.service.maakbericht",
    "nl.bzk.algemeenbrp.services.objectsleutel",
    "nl.bzk.brp.archivering.service.algemeen",
    "nl.bzk.brp.protocollering.service.algemeen"})
public class AlgemeenServicesConfiguratie {

    /**
     * @return maakPersoonBerichtService
     */
    @Bean
    @SuppressWarnings("all")
    MaakPersoonBerichtServiceImpl maakPersoonBerichtService() {
        return new MaakPersoonBerichtServiceImpl();
    }

    /**
     * @return JmsService bean
     */
    @Bean
    @SuppressWarnings("all")
    LeverberichtStubServiceImpl jmsService() {
        return new LeverberichtStubServiceImpl();
    }

    /**
     * @return LogControleServiceImpl
     */
    @Bean
    LogControleServiceImpl maakLogControleServiceImpl() {
        return new LogControleServiceImpl();
    }


}
