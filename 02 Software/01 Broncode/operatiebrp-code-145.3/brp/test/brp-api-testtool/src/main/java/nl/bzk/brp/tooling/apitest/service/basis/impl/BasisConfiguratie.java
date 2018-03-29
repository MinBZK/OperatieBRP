/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.basis.impl;

import java.io.IOException;
import nl.bzk.brp.test.common.xml.XPathHelper;
import nl.bzk.brp.tooling.apitest.service.afnemerindicatie.OnderhoudAfnemerindicatieConfiguratie;
import nl.bzk.brp.tooling.apitest.service.beheer.BeheerConfiguratie;
import nl.bzk.brp.tooling.apitest.service.bevraging.BevragingConfiguratie;
import nl.bzk.brp.tooling.apitest.service.cache.CacheConfiguratie;
import nl.bzk.brp.tooling.apitest.service.dataaccess.DataAccessConfiguratie;
import nl.bzk.brp.tooling.apitest.service.leveringalgemeen.AlgemeenServicesConfiguratie;
import nl.bzk.brp.tooling.apitest.service.leveringalgemeen.ArchiveringControleService;
import nl.bzk.brp.tooling.apitest.service.leveringalgemeen.ArchiveringControleServiceImpl;
import nl.bzk.brp.tooling.apitest.service.leveringalgemeen.ArchiveringRepositoryStub;
import nl.bzk.brp.tooling.apitest.service.leveringalgemeen.ProtocolleringControleServiceImpl;
import nl.bzk.brp.tooling.apitest.service.leveringalgemeen.ProtocolleringRepositoryStub;
import nl.bzk.brp.tooling.apitest.service.maakbericht.MaakBerichtServiceConfiguratie;
import nl.bzk.brp.tooling.apitest.service.mutatielevering.MutatieleveringConfiguratie;
import nl.bzk.brp.tooling.apitest.service.selectie.SelectieConfiguratie;
import nl.bzk.brp.tooling.apitest.service.stuf.StufConfiguratie;
import nl.bzk.brp.tooling.apitest.service.synchronisatie.SynchronisatieConfiguratie;
import nl.bzk.brp.tooling.apitest.service.vrijbericht.VrijBerichtConfiguratie;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

/**
 * Bevat de basisconfiguratie voor de te testen services.
 */
@Configuration
@Import({DataAccessConfiguratie.class, AlgemeenServicesConfiguratie.class,
        MaakBerichtServiceConfiguratie.class, CacheConfiguratie.class, VrijBerichtConfiguratie.class,
        OnderhoudAfnemerindicatieConfiguratie.class, SynchronisatieConfiguratie.class, MutatieleveringConfiguratie.class,
        BevragingConfiguratie.class, SelectieConfiguratie.class, StufConfiguratie.class, BeheerConfiguratie.class})
@PropertySource("classpath:test.properties")
public class BasisConfiguratie {

    /**
     * @return een StoryServiceImpl
     */
    @Bean
    @SuppressWarnings("all")
    StoryServiceImpl maakStoryInformatieService() {
        return new StoryServiceImpl();
    }

    /**
     * @return VerzoekServiceImpl
     */
    @Bean
    @SuppressWarnings("all")
    VerzoekServiceImpl maakVerzoekService() {
        return new VerzoekServiceImpl();
    }

    /**
     * @return ApiServiceImpl
     */
    @Bean
    @SuppressWarnings("all")
    ApiServiceImpl maakApiService() {
        return new ApiServiceImpl();
    }

    /**
     * @return BerichtControleServiceImpl
     */
    @Bean
    @SuppressWarnings("all")
    BerichtControleServiceImpl maakBerichtControleService() {
        return new BerichtControleServiceImpl();
    }

    @Bean
    @SuppressWarnings("all")
    ProtocolleringRepositoryStub maakProtocolleringRepositoryStub() {
        return new ProtocolleringRepositoryStub();
    }

    @Bean
    @SuppressWarnings("all")
    ProtocolleringControleServiceImpl maakProtocolleringControleService() {
        return new ProtocolleringControleServiceImpl();
    }


    @Bean
    @SuppressWarnings("all")
    ArchiveringRepositoryStub maakArchiveringRepository() {
        return new ArchiveringRepositoryStub();
    }

    @Bean
    @SuppressWarnings("all")
    ArchiveringControleService maakArchiveringControleService() {
        return new ArchiveringControleServiceImpl();
    }

    /**
     * @return OinResolverStub
     */
    @Bean
    @SuppressWarnings("all")
    OinResolverStub maakOinResolver() {
        return new OinResolverStub();
    }

    @Bean
    @SuppressWarnings("all")
    BlobMutatieServiceImpl maakBlobMutatieService() {
        return new BlobMutatieServiceImpl();
    }

    @Bean
    XPathHelper maakXpathHelper() {
        return new XPathHelper();
    }

    @Bean
    @SuppressWarnings("all")
    public static PropertyPlaceholderConfigurer ppc() throws IOException {
        final PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocations(new ClassPathResource("test.properties"));
        ppc.setIgnoreUnresolvablePlaceholders(true);
        return ppc;
    }

}
