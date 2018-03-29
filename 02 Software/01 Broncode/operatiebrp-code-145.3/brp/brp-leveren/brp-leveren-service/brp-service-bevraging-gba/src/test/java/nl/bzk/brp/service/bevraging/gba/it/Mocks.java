/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.it;

import java.util.Collections;
import nl.bzk.brp.archivering.service.algemeen.ArchiefService;
import nl.bzk.brp.protocollering.service.algemeen.ProtocolleringService;
import nl.bzk.brp.service.dalapi.AfnemerindicatieRepository;
import nl.bzk.brp.service.dalapi.GeefDetailsPersoonRepository;
import nl.bzk.brp.service.dalapi.LeveringsautorisatieRepository;
import nl.bzk.brp.service.dalapi.PartijRepository;
import nl.bzk.brp.service.dalapi.PersoonCacheRepository;
import nl.bzk.brp.service.dalapi.PersoonRepository;
import nl.bzk.brp.service.dalapi.StamTabelRepository;
import nl.bzk.brp.service.dalapi.ZoekPersoonDataOphalerService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

public class Mocks {

    @Bean
    public AfnemerindicatieRepository getAfnemerindicatieRepository() {
        return Mockito.mock(AfnemerindicatieRepository.class);
    }

    @Bean
    public GeefDetailsPersoonRepository getGeefDetailsPersoonRepository() {
        return Mockito.mock(GeefDetailsPersoonRepository.class);
    }

    @Bean
    public LeveringsautorisatieRepository getLeveringsautorisatieRepository() {
        return Mockito.mock(LeveringsautorisatieRepository.class);
    }

    @Bean
    public PersoonRepository getPersoonRepository() {
        return Mockito.mock(PersoonRepository.class);
    }

    @Bean
    public PartijRepository getPartijRepository() {
        final PartijRepository partijRepository = Mockito.mock(PartijRepository.class);
        Mockito.when(partijRepository.get()).thenReturn(Collections.emptyList());
        return partijRepository;
    }

    @Bean
    public PersoonCacheRepository getPersoonCacheRepository() {
        return Mockito.mock(PersoonCacheRepository.class);
    }

    @Bean
    public ProtocolleringService getProtocolleringService() {
        return Mockito.mock(ProtocolleringService.class);
    }

    @Bean
    public ArchiefService archiveringService() {
        return Mockito.mock(ArchiefService.class);
    }

    @Bean
    public StamTabelRepository getStamTabelRepository() {
        return Mockito.mock(StamTabelRepository.class);
    }

    @Bean(name = "jmxdomein")
    public String getJmxDomein() {
        return "test";
    }

    @Bean
    public ZoekPersoonDataOphalerService getZoekPersoonDataOphalerService() {
        return Mockito.mock(ZoekPersoonDataOphalerService.class);
    }

}
