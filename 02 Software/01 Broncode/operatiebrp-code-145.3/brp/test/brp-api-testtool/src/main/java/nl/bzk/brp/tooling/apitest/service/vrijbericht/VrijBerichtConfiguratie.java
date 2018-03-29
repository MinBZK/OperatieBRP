/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.vrijbericht;

import java.io.IOException;
import javax.inject.Inject;
import nl.bzk.brp.delivery.vrijbericht.VrijBerichtWebService;
import nl.bzk.brp.service.algemeen.request.OinResolver;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.service.vrijbericht.VrijBerichtVerwerker;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

/**
 * VrijBerichtConfiguratie.
 */
@ComponentScan({"nl.bzk.brp.service.vrijbericht", "nl.bzk.brp.tooling.apitest.service.vrijbericht"})
@ImportResource({"beans.xml"})
@PropertySource("classpath:test.properties")
public class VrijBerichtConfiguratie {

    @Bean
    @SuppressWarnings("all")
    public static PropertyPlaceholderConfigurer ppc() throws IOException {
        final PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocations(new ClassPathResource("test.properties"));
        ppc.setIgnoreUnresolvablePlaceholders(true);
        return ppc;
    }

    /**
     * @return maakt de {@link VrijBerichtWebService}
     */
    @Bean
    @SuppressWarnings("all")
    @Inject
    VrijBerichtWebService maakVrijBerichtWebService(final OinResolver oinResolver, final SchemaValidatorService schemaValidatorService,
                                                    final VrijBerichtVerwerker vrijBerichtVerwerker) {
        return new VrijBerichtWebService(oinResolver, schemaValidatorService, vrijBerichtVerwerker);
    }
}
