/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.stuf;

import java.io.IOException;
import javax.inject.Inject;
import nl.bzk.brp.delivery.stuf.StufWebService;
import nl.bzk.brp.service.algemeen.request.OinResolver;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.service.stuf.StufBerichtVerwerker;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

/**
 * StufConfiguratie.
 */
@ComponentScan({"nl.bzk.brp.service.stuf", "nl.bzk.brp.tooling.apitest.service.stuf"})
@PropertySource("classpath:test.properties")
public class StufConfiguratie {

    @Bean
    @SuppressWarnings("all")
    public static PropertyPlaceholderConfigurer ppc() throws IOException {
        final PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocations(new ClassPathResource("test.properties"));
        ppc.setIgnoreUnresolvablePlaceholders(true);
        return ppc;
    }

    /**
     * @return maakt de {@link StufWebService}
     */
    @Bean
    @SuppressWarnings("all")
    @Inject
    StufWebService maakStufWebService(final OinResolver oinResolver, final SchemaValidatorService schemaValidatorService,
                                      final StufBerichtVerwerker stufBerichtVerwerker) {
        return new StufWebService(oinResolver, schemaValidatorService, stufBerichtVerwerker);
    }
}
