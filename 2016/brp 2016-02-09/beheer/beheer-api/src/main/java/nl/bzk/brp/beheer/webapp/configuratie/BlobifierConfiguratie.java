/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie;

import nl.bzk.brp.serialisatie.afnemerindicatie.AfnemerIndicatieSmileSerializer;
import nl.bzk.brp.serialisatie.persoon.PersoonHisVolledigSmileSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Blobifier service configuration.
 */
@Configuration
@ComponentScan(basePackages = { "nl.bzk.brp.blobifier" })
public class BlobifierConfiguratie {

    /**
     * @return persoon serializer.
     */
    @Bean
    @SuppressWarnings("checkstyle:designforextension")
    public PersoonHisVolledigSmileSerializer persoonHisVolledigSerializer() {
        return new PersoonHisVolledigSmileSerializer();
    }

    /**
     * @return afnemer indicaties serializer
     */
    @Bean
    @SuppressWarnings("checkstyle:designforextension")
    public AfnemerIndicatieSmileSerializer afnemerIndicatieSerializer() {
        return new AfnemerIndicatieSmileSerializer();
    }
}
