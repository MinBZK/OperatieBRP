/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.afnemerindicatie;

import javax.inject.Inject;
import nl.bzk.brp.delivery.afnemerindicatie.OnderhoudAfnemerindicatiesWebServiceImpl;
import nl.bzk.brp.service.afnemerindicatie.OnderhoudAfnemerindicatieService;
import nl.bzk.brp.service.algemeen.request.OinResolver;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Bevat de Spring configuratie voor de onderhoud afnemerindicatie services.
 */
@ComponentScan("nl.bzk.brp.service.afnemerindicatie")
public class OnderhoudAfnemerindicatieConfiguratie {

    @Bean
    @SuppressWarnings("all")
    OnderhoudAfnemerindicatieApiServiceImpl maakOnderhoudAfnemerindicatieApiService() {
        return new OnderhoudAfnemerindicatieApiServiceImpl();
    }

    @Bean
    @Inject
    @SuppressWarnings("all")
    OnderhoudAfnemerindicatiesWebServiceImpl maakAfnemerindicatieWebservice(final OnderhoudAfnemerindicatieService onderhoudAfnemerindicatieService, final
    OinResolver oinResolver, final SchemaValidatorService schemaValidatorService) {
        return new OnderhoudAfnemerindicatiesWebServiceImpl(onderhoudAfnemerindicatieService, oinResolver, schemaValidatorService);
    }

}
