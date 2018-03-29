/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.bevraging;

import java.io.IOException;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.delivery.bevraging.BevragingWebService;
import nl.bzk.brp.service.algemeen.request.OinResolver;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoekVerwerker;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

/**
 * Bevat de Spring configuratie voor de bevraging services.
 */
@ComponentScan(basePackages = "nl.bzk.brp.service.bevraging")
@PropertySource("classpath:test.properties")
public class BevragingConfiguratie {

    @Bean
    @SuppressWarnings("all")
    public static PropertyPlaceholderConfigurer ppc() throws IOException {
        final PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocations(new ClassPathResource("test.properties"));
        ppc.setIgnoreUnresolvablePlaceholders(true);
        return ppc;
    }

    /**
     * @return maakt de {@link BevragingApiServiceImpl}
     */
    @Bean
    @SuppressWarnings("all")
    BevragingApiServiceImpl maakBevragingApiService() {
        return new BevragingApiServiceImpl();
    }

    /**
     * @return maakt de {@link GeefDetailsPersoonApiServiceImpl}
     */
    @Bean
    @SuppressWarnings("all")
    GeefDetailsPersoonApiServiceImpl maakGeefDetailsPersoonApiService() {
        return new GeefDetailsPersoonApiServiceImpl();
    }

    /**
     * @return maakt de {@link ZoekPersoonApiServiceImpl}
     */
    @Bean
    @SuppressWarnings("all")
    ZoekPersoonApiServiceImpl maakZoekPersoonApiService() {
        return new ZoekPersoonApiServiceImpl();
    }

    /**
     * @return maakt de {@link GeefMedebewonersApiServiceImpl}
     */
    @Bean
    @SuppressWarnings("all")
    GeefMedebewonersApiServiceImpl maakGeefMedebewonersApiService() {
        return new GeefMedebewonersApiServiceImpl();
    }

    /**
     * @return maakt de {@link BevragingWebService}
     */
    @Bean
    @Inject
    @SuppressWarnings("all")
    BevragingWebService maakBevragingWebservice(final OinResolver oinResolver, final SchemaValidatorService schemaValidatorService,
                                                final Map<SoortDienst, BevragingVerzoekVerwerker<BevragingVerzoek>> bevragingVerzoekVerwerkerMap) {
        return new BevragingWebService(oinResolver, schemaValidatorService, bevragingVerzoekVerwerkerMap);
    }
}
