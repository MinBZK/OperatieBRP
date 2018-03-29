/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.synchronisatie;

import javax.inject.Inject;
import nl.bzk.brp.delivery.synchronisatie.SynchronisatieWebServiceImpl;
import nl.bzk.brp.service.algemeen.request.OinResolver;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.service.synchronisatie.persoon.SynchroniseerPersoonService;
import nl.bzk.brp.service.synchronisatie.stamgegeven.SynchroniseerStamgegevenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;

/**
 * Bevat de configuratie voor de module synchronisatie.
 */
@ComponentScan({"nl.bzk.brp.service.synchronisatie.persoon", "nl.bzk.brp.service.synchronisatie.stamgegeven"})
@Lazy
public class SynchronisatieConfiguratie {

    @Bean
    @SuppressWarnings("all")
    SynchronisatieApiServiceImpl maakSynchronisatieApiService() {
        return new SynchronisatieApiServiceImpl();
    }


    @Bean
    @Inject
    @SuppressWarnings("all")
    SynchronisatieWebServiceImpl maakSynchronisatieWebservice(final SynchroniseerPersoonService synchroniseerPersoonService,
                                                              final SynchroniseerStamgegevenService synchroniseerStamgegevenService,
                                                              final OinResolver oinResolver, final SchemaValidatorService schemaValidatorService) {
        return new SynchronisatieWebServiceImpl(synchroniseerPersoonService, synchroniseerStamgegevenService, schemaValidatorService, oinResolver);
    }

//    /**
//     * @return maakt de {@link BevragingWebService}
//     */
//    @Bean
//    @Inject
//    @SuppressWarnings("all")
//    BevragingWebService maakBevragingWebservice(final OinResolver oinResolver, final SchemaValidatorService schemaValidatorService,
//                                                final Map<SoortDienst, BevragingVerzoekVerwerker<BevragingVerzoek>> bevragingVerzoekVerwerkerMap) {
//        return new BevragingWebService(oinResolver, schemaValidatorService, bevragingVerzoekVerwerkerMap);
//    }
}
